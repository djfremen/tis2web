/*     */ package com.eoos.gm.tis2web.lt.v2;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.service.LTCTOCServiceImpl;
/*     */ import com.eoos.gm.tis2web.ctoc.service.LTCTOCService;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCFactory;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.fts.implementation.service.FTSServiceImpl;
/*     */ import com.eoos.gm.tis2web.fts.service.FTSService;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.LTImpl;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.db.FTSLTElementImpl;
/*     */ import com.eoos.gm.tis2web.lt.service.cai.LT;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import com.eoos.gm.tis2web.vcr.v2.LVCAdapterProvider;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class LTDataAdapterStd
/*     */   implements LTDataAdapter
/*     */ {
/*  32 */   private static final Logger log = Logger.getLogger(LTDataAdapterStd.class);
/*     */   private Configuration cfg;
/*     */   
/*     */   private class State {
/*     */     private LTImpl lt;
/*     */     private LTCTOCService ctocService;
/*     */     private ILVCAdapter lvc;
/*     */     private FTSService.Retrieval ftsRetrieval;
/*     */     
/*     */     private State() {} }
/*  42 */   private final Object SYNC_STATE = new Object();
/*  43 */   private State state = null;
/*     */ 
/*     */   
/*     */   private String delegateID;
/*     */   
/*     */   private FTSService.Callback callbackFTSService;
/*     */ 
/*     */   
/*     */   public LTDataAdapterStd(Configuration cfg, String delegateID) {
/*  52 */     this.callbackFTSService = new FTSService.Callback()
/*     */       {
/*     */         public Integer getSioId(String sioId) {
/*  55 */           if (sioId.charAt(0) == '-') {
/*  56 */             sioId = sioId.substring(1);
/*  57 */             return Integer.valueOf(sioId.toString());
/*     */           } 
/*  59 */           return null;
/*     */         }
/*     */         
/*     */         public List loadSIOs(List sios) {
/*  63 */           Map nodes = (LTDataAdapterStd.this.getState()).ctocService.getCTOC().lookupMOs(sios);
/*  64 */           return new ArrayList(nodes.values());
/*     */         }
/*     */         
/*     */         public SITOCElement lookupSIO(Integer sioId) {
/*  68 */           return (SITOCElement)(LTDataAdapterStd.this.getState()).ctocService.getCTOC().lookupMO(sioId);
/*     */         }
/*     */         
/*     */         public Object createElement(String sioId) {
/*  72 */           if (sioId.charAt(0) == '-') {
/*  73 */             sioId = sioId.substring(1);
/*  74 */             return new FTSLTElementImpl(Integer.valueOf(sioId), LTDataAdapterStd.this.getLTCTOCService(), LTDataAdapterStd.this.getLVCAdapter().createRetrievalImpl());
/*     */           } 
/*  76 */           return null;
/*     */         }
/*     */       };
/*     */     this.cfg = cfg;
/*     */     this.delegateID = delegateID;
/*     */   } private State initState() {
/*  82 */     final State ret = new State();
/*  83 */     log.debug("creating LT implementation for adapter " + this + " ...");
/*     */     
/*     */     try {
/*  86 */       ret.lvc = LVCAdapterProvider.getAdapter((Configuration)new SubConfigurationWrapper(this.cfg, "lt.vc.ltvc." + this.delegateID + "."), (Configuration)new SubConfigurationWrapper(this.cfg, "lt.vcr.ltvcr." + this.delegateID + "."));
/*  87 */       ret.ftsRetrieval = new FTSService.Retrieval() {
/*  88 */           private FTSService service = null;
/*     */           
/*     */           public synchronized FTSService getFTSService() {
/*  91 */             if (this.service == null) {
/*  92 */               this.service = (FTSService)new FTSServiceImpl((Configuration)new SubConfigurationWrapper(LTDataAdapterStd.this.cfg, "lt.fts.ltfts." + LTDataAdapterStd.this.delegateID + "."), LTDataAdapterStd.this.callbackFTSService, ret.lvc.createRetrievalImpl());
/*     */             }
/*     */             
/*  95 */             return this.service;
/*     */           }
/*     */         };
/*     */       
/*  99 */       IDatabaseLink dbLT = DatabaseLink.openDatabase((Configuration)new SubConfigurationWrapper(this.cfg, "lt.main.ltmain." + this.delegateID + ".db."));
/* 100 */       IDatabaseLink dbICL = null;
/*     */       try {
/* 102 */         SubConfigurationWrapper subConfigurationWrapper = new SubConfigurationWrapper(this.cfg, "lt.icl.lticl." + this.delegateID + ".db.");
/* 103 */         if (subConfigurationWrapper.getKeys().size() > 0) {
/* 104 */           dbICL = DatabaseLink.openDatabase((Configuration)new SubConfigurationWrapper(this.cfg, "lt.icl.lticl." + this.delegateID + ".db."));
/*     */         } else {
/* 106 */           log.debug("no separate icl database specified, using lt database");
/* 107 */           dbICL = dbLT;
/*     */         } 
/* 109 */       } catch (Exception e) {
/* 110 */         log.error("unable to create database connector for ICL DB, ignoring - exception: " + e, e);
/*     */       } 
/* 112 */       ret.lt = new LTImpl(dbLT, dbICL, ret.lvc.createRetrievalImpl());
/*     */       
/* 114 */       ret.ctocService = (LTCTOCService)new LTCTOCServiceImpl((Configuration)new SubConfigurationWrapper(this.cfg, "lt.ctoc.ltctoc." + this.delegateID + "."), (CTOCFactory)ret.lt.getLTCache(), ret.lvc.createRetrievalImpl(), ret.ftsRetrieval);
/*     */       
/* 116 */       return ret;
/* 117 */     } catch (Exception e) {
/* 118 */       throw Util.toRuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private State getState() {
/* 124 */     synchronized (this.SYNC_STATE) {
/* 125 */       if (this.state == null) {
/* 126 */         this.state = initState();
/*     */       }
/* 128 */       return this.state;
/*     */     } 
/*     */   }
/*     */   
/*     */   public LT getLT() {
/* 133 */     return (LT)(getState()).lt;
/*     */   }
/*     */   
/*     */   public LTCTOCService getLTCTOCService() {
/* 137 */     return (getState()).ctocService;
/*     */   }
/*     */   
/*     */   public Integer getModelCode(IConfiguration cfg) {
/* 141 */     String make = VehicleConfigurationUtil.toString(VehicleConfigurationUtil.getMake(cfg));
/* 142 */     Integer smc = getLT().getSmc(make);
/* 143 */     return getLT().getMc(smc, VehicleConfigurationUtil.toString(VehicleConfigurationUtil.getModel(cfg)));
/*     */   }
/*     */   
/*     */   public boolean supports(IConfiguration cfg) {
/* 147 */     return getLTCTOCService().supports(cfg);
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {}
/*     */   
/*     */   public Set getCfgProviders() {
/* 154 */     return Collections.singleton(getLTCTOCService());
/*     */   }
/*     */   
/*     */   public ILVCAdapter getLVCAdapter() {
/* 158 */     return (getState()).lvc;
/*     */   }
/*     */   
/*     */   public Set getVINResolvers(ClientContext context) {
/* 162 */     return Collections.singleton(getLVCAdapter());
/*     */   }
/*     */   
/*     */   public FTSService getFTSService() {
/* 166 */     return (getState()).ftsRetrieval.getFTSService();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\v2\LTDataAdapterStd.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */