/*     */ package com.eoos.gm.tis2web.si.v2;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.SICTOCService;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOC;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.fts.service.FTSService;
/*     */ import com.eoos.gm.tis2web.fts.service.FTSServiceChain;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SI;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import com.eoos.gm.tis2web.vcr.v2.LVCAdapterChain;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class SIDataAdapterFacade
/*     */   implements SIDataAdapter
/*     */ {
/*  24 */   private ClientContext context = null;
/*     */   
/*     */   private SIDataAdapterFacade(ClientContext context) {
/*  27 */     this.context = context;
/*     */   }
/*     */   
/*     */   public static synchronized SIDataAdapterFacade getInstance() {
/*  31 */     ApplicationContext applicationContext = ApplicationContext.getInstance();
/*  32 */     SIDataAdapterFacade ret = (SIDataAdapterFacade)applicationContext.getObject(SIDataAdapterFacade.class);
/*  33 */     if (ret == null) {
/*  34 */       ret = new SIDataAdapterFacade(null);
/*  35 */       applicationContext.storeObject(SIDataAdapterFacade.class, ret);
/*     */     } 
/*  37 */     return ret;
/*     */   }
/*     */   
/*     */   public static SIDataAdapterFacade getInstance(ClientContext context) {
/*  41 */     synchronized (context.getLockObject()) {
/*  42 */       SIDataAdapterFacade ret = (SIDataAdapterFacade)context.getObject(SIDataAdapterFacade.class);
/*  43 */       if (ret == null) {
/*  44 */         ret = new SIDataAdapterFacade(context);
/*  45 */         context.storeObject(SIDataAdapterFacade.class, ret);
/*     */       } 
/*  47 */       return ret;
/*     */     } 
/*     */   }
/*     */   
/*     */   public SI getSI() {
/*  52 */     Collection adapters = SIDataAdapterProvider.getInstance().getDataAdapters(this.context);
/*  53 */     if (Util.isNullOrEmpty(adapters))
/*  54 */       return null; 
/*  55 */     if (adapters.size() == 1) {
/*  56 */       return ((SIDataAdapter)CollectionUtil.getFirst(adapters)).getSI();
/*     */     }
/*  58 */     return new SIChain(adapters);
/*     */   }
/*     */ 
/*     */   
/*     */   public SICTOCService getSICTOCService() {
/*  63 */     Collection adapters = SIDataAdapterProvider.getInstance().getDataAdapters(this.context);
/*  64 */     if (Util.isNullOrEmpty(adapters))
/*  65 */       return null; 
/*  66 */     if (adapters.size() == 1) {
/*  67 */       return ((SIDataAdapter)CollectionUtil.getFirst(adapters)).getSICTOCService();
/*     */     }
/*  69 */     return new SICTOCServiceChain(adapters);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set getCfgProviders() {
/*  74 */     Set ret = new LinkedHashSet();
/*  75 */     for (Iterator<SIDataAdapter> iter = SIDataAdapterProvider.getInstance().getDataAdapters().iterator(); iter.hasNext();) {
/*  76 */       ret.addAll(((SIDataAdapter)iter.next()).getCfgProviders());
/*     */     }
/*  78 */     return ret;
/*     */   }
/*     */   
/*     */   public boolean supports(IConfiguration cfg) {
/*  82 */     for (Iterator<SIDataAdapter> iter = SIDataAdapterProvider.getInstance().getDataAdapters().iterator(); iter.hasNext();) {
/*  83 */       if (((SIDataAdapter)iter.next()).supports(cfg)) {
/*  84 */         return true;
/*     */       }
/*     */     } 
/*  87 */     return false;
/*     */   }
/*     */   
/*     */   public Collection getCTOCs() {
/*  91 */     Collection<CTOC> ret = new LinkedList();
/*  92 */     for (Iterator<SIDataAdapter> iter = SIDataAdapterProvider.getInstance().getDataAdapters(this.context).iterator(); iter.hasNext();) {
/*  93 */       ret.add(((SIDataAdapter)iter.next()).getSICTOCService().getCTOC());
/*     */     }
/*  95 */     return ret;
/*     */   }
/*     */   
/*     */   public ILVCAdapter getLVCAdapter() {
/*  99 */     Collection adapters = SIDataAdapterProvider.getInstance().getDataAdapters(this.context);
/* 100 */     if (Util.isNullOrEmpty(adapters))
/* 101 */       return null; 
/* 102 */     if (adapters.size() == 1) {
/* 103 */       return ((SIDataAdapter)CollectionUtil.getFirst(adapters)).getLVCAdapter();
/*     */     }
/* 105 */     return (ILVCAdapter)new LVCAdapterChain(adapters);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set getVINResolvers(ClientContext context) {
/* 110 */     Set ret = new LinkedHashSet();
/* 111 */     for (Iterator<SIDataAdapter> iter = SIDataAdapterProvider.getInstance().getDataAdapters().iterator(); iter.hasNext();) {
/* 112 */       ret.addAll(((SIDataAdapter)iter.next()).getVINResolvers(context));
/*     */     }
/* 114 */     return ret;
/*     */   }
/*     */   
/*     */   public FTSService getFTSService() {
/* 118 */     Collection adapters = SIDataAdapterProvider.getInstance().getDataAdapters(this.context);
/* 119 */     if (Util.isNullOrEmpty(adapters))
/* 120 */       return null; 
/* 121 */     if (adapters.size() == 1) {
/* 122 */       return ((SIDataAdapter)CollectionUtil.getFirst(adapters)).getFTSService();
/*     */     }
/* 124 */     return (FTSService)new FTSServiceChain(adapters);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\v2\SIDataAdapterFacade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */