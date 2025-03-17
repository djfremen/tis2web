/*     */ package com.eoos.gm.tis2web.si.v2;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.service.SICTOCServiceImpl;
/*     */ import com.eoos.gm.tis2web.ctoc.service.SICTOCService;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.CachingStrategy;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.fts.implementation.service.FTSServiceImpl;
/*     */ import com.eoos.gm.tis2web.fts.service.FTSService;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.SIImpl;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.db.FTSSIElementImpl;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SI;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import com.eoos.gm.tis2web.vcr.v2.LVCAdapterProvider;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class SIDataAdapterStd implements SIDataAdapter {
/*     */   private Configuration cfg;
/*     */   
/*     */   private class State {
/*     */     private SIImpl si;
/*     */     private SICTOCService sictocService;
/*     */     private ILVCAdapter lvcAdapter;
/*     */     private FTSService.Retrieval ftsRetrieval;
/*     */     
/*     */     private State() {}
/*     */   }
/*  36 */   private final Object SYNC_STATE = new Object();
/*  37 */   private State state = null;
/*     */ 
/*     */   
/*     */   private String delegateID;
/*     */   
/*     */   private FTSService.Callback callbackFTSService;
/*     */ 
/*     */   
/*     */   public SIDataAdapterStd(Configuration cfg, String delegateID) {
/*  46 */     this.callbackFTSService = new FTSService.Callback()
/*     */       {
/*     */         public List loadSIOs(List sios) {
/*  49 */           return SIDataAdapterStd.this.getSI().loadSIOs(sios);
/*     */         }
/*     */         
/*     */         public Integer getSioId(String sioId) {
/*  53 */           if (sioId.charAt(0) != '-') {
/*  54 */             return Integer.valueOf(sioId);
/*     */           }
/*  56 */           return null;
/*     */         }
/*     */         
/*     */         public SITOCElement lookupSIO(Integer sioId) {
/*  60 */           return (SITOCElement)SIDataAdapterStd.this.getSI().lookupSIO(sioId.intValue());
/*     */         }
/*     */ 
/*     */         
/*     */         public Object createElement(String sioId) {
/*  65 */           if (sioId.charAt(0) != '-') {
/*  66 */             return new FTSSIElementImpl(Integer.valueOf(sioId), (SIDataAdapterStd.this.getState()).si.createRetrievalImpl(), SIDataAdapterStd.this.getLVCAdapter().createRetrievalImpl());
/*     */           }
/*  68 */           return null;
/*     */         }
/*     */       };
/*     */     this.cfg = cfg;
/*     */     this.delegateID = delegateID;
/*     */   } private State initState() {
/*  74 */     final State ret = new State();
/*     */     try {
/*  76 */       ret.lvcAdapter = LVCAdapterProvider.getAdapter((Configuration)new SubConfigurationWrapper(this.cfg, "si.vc.sivc." + this.delegateID + "."), (Configuration)new SubConfigurationWrapper(this.cfg, "si.vcr.sivcr." + this.delegateID + "."));
/*     */       
/*  78 */       ret.ftsRetrieval = new FTSService.Retrieval() {
/*  79 */           private FTSService service = null;
/*     */           
/*     */           public synchronized FTSService getFTSService() {
/*  82 */             if (this.service == null) {
/*  83 */               this.service = (FTSService)new FTSServiceImpl((Configuration)new SubConfigurationWrapper(SIDataAdapterStd.this.cfg, "si.fts.sifts." + SIDataAdapterStd.this.delegateID + "."), SIDataAdapterStd.this.callbackFTSService, ret.lvcAdapter.createRetrievalImpl());
/*     */             }
/*  85 */             return this.service;
/*     */           }
/*     */         };
/*  88 */       new CachingStrategy();
/*  89 */       IDatabaseLink dbLink = DatabaseLink.openDatabase((Configuration)new SubConfigurationWrapper(this.cfg, "si.main.simain." + this.delegateID + ".db."));
/*  90 */       ret.sictocService = (SICTOCService)new SICTOCServiceImpl((Configuration)new SubConfigurationWrapper(this.cfg, "si.ctoc.sictoc." + this.delegateID + "."), (ILVCAdapter.Retrieval)new ILVCAdapter.Retrieval.RI(ret.lvcAdapter), new SI.Retrieval()
/*     */           {
/*     */             public SI getSI() {
/*  93 */               return SIDataAdapterStd.this.getSI();
/*     */             }
/*     */           },  ret.ftsRetrieval);
/*     */ 
/*     */       
/*  98 */       ret.si = new SIImpl(dbLink, ret.lvcAdapter, ret.sictocService);
/*  99 */       return ret;
/* 100 */     } catch (Exception e) {
/* 101 */       throw Util.toRuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private State getState() {
/* 107 */     synchronized (this.SYNC_STATE) {
/* 108 */       if (this.state == null) {
/* 109 */         this.state = initState();
/*     */       }
/* 111 */       return this.state;
/*     */     } 
/*     */   }
/*     */   
/*     */   public SI getSI() {
/* 116 */     return (SI)(getState()).si;
/*     */   }
/*     */   
/*     */   public SICTOCService getSICTOCService() {
/* 120 */     return (getState()).sictocService;
/*     */   }
/*     */   
/*     */   public boolean supports(IConfiguration cfg) {
/* 124 */     return getSICTOCService().supports(cfg);
/*     */   }
/*     */   
/*     */   public Set getCfgProviders() {
/* 128 */     return Collections.singleton(getSICTOCService());
/*     */   }
/*     */   
/*     */   public ILVCAdapter getLVCAdapter() {
/* 132 */     return (getState()).lvcAdapter;
/*     */   }
/*     */   
/*     */   public Set getVINResolvers(ClientContext context) {
/* 136 */     return Collections.singleton(getLVCAdapter());
/*     */   }
/*     */   
/*     */   public FTSService getFTSService() {
/* 140 */     return (getState()).ftsRetrieval.getFTSService();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\v2\SIDataAdapterStd.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */