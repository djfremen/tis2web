/*     */ package com.eoos.gm.tis2web.lt.v2;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.LTCTOCService;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.fts.service.FTSService;
/*     */ import com.eoos.gm.tis2web.fts.service.FTSServiceChain;
/*     */ import com.eoos.gm.tis2web.lt.service.cai.LT;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ 
/*     */ 
/*     */ public class LTDataAdapterFacade
/*     */   implements LTDataAdapter
/*     */ {
/*  32 */   private ClientContext context = null;
/*     */   
/*     */   private LTDataAdapterFacade(ClientContext context) {
/*  35 */     this.context = context;
/*     */   }
/*     */   
/*     */   public static synchronized LTDataAdapterFacade getInstance() {
/*  39 */     ApplicationContext applicationContext = ApplicationContext.getInstance();
/*  40 */     LTDataAdapterFacade ret = (LTDataAdapterFacade)applicationContext.getObject(LTDataAdapterFacade.class);
/*  41 */     if (ret == null) {
/*  42 */       ret = new LTDataAdapterFacade(null);
/*  43 */       applicationContext.storeObject(LTDataAdapterFacade.class, ret);
/*     */     } 
/*  45 */     return ret;
/*     */   }
/*     */   
/*     */   public static LTDataAdapterFacade getInstance(ClientContext context) {
/*  49 */     synchronized (context.getLockObject()) {
/*  50 */       LTDataAdapterFacade ret = (LTDataAdapterFacade)context.getObject(LTDataAdapterFacade.class);
/*  51 */       if (ret == null) {
/*  52 */         ret = new LTDataAdapterFacade(context);
/*  53 */         context.storeObject(LTDataAdapterFacade.class, ret);
/*     */       } 
/*  55 */       return ret;
/*     */     } 
/*     */   }
/*     */   
/*     */   public LT getLT() {
/*  60 */     Collection adapters = LTDataAdapterProvider.getInstance().getDataAdapters(this.context);
/*  61 */     if (Util.isNullOrEmpty(adapters))
/*  62 */       return null; 
/*  63 */     if (adapters.size() == 1) {
/*  64 */       return ((LTDataAdapter)CollectionUtil.getFirst(adapters)).getLT();
/*     */     }
/*  66 */     return new LTChain(adapters);
/*     */   }
/*     */ 
/*     */   
/*     */   public LTCTOCService getLTCTOCService() {
/*  71 */     Collection adapters = LTDataAdapterProvider.getInstance().getDataAdapters(this.context);
/*  72 */     if (Util.isNullOrEmpty(adapters))
/*  73 */       return null; 
/*  74 */     if (adapters.size() == 1) {
/*  75 */       return ((LTDataAdapter)CollectionUtil.getFirst(adapters)).getLTCTOCService();
/*     */     }
/*  77 */     return new LTCTOCServiceChain(adapters);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Set getCfgProviders() {
/*  83 */     Set ret = new LinkedHashSet();
/*  84 */     for (Iterator<LTDataAdapter> iter = LTDataAdapterProvider.getInstance().getDataAdapters().iterator(); iter.hasNext();) {
/*  85 */       ret.addAll(((LTDataAdapter)iter.next()).getCfgProviders());
/*     */     }
/*  87 */     return ret;
/*     */   }
/*     */   
/*     */   public boolean supports(IConfiguration cfg) {
/*  91 */     for (Iterator<LTDataAdapter> iter = LTDataAdapterProvider.getInstance().getDataAdapters().iterator(); iter.hasNext();) {
/*  92 */       if (((LTDataAdapter)iter.next()).supports(cfg)) {
/*  93 */         return true;
/*     */       }
/*     */     } 
/*  96 */     return false;
/*     */   }
/*     */   
/*     */   public Collection getCTOCs() {
/* 100 */     Collection ret = new LinkedList();
/* 101 */     ExecutorService executor = new ThreadPoolExecutor(2, 5, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
/*     */     try {
/* 103 */       List futures = new LinkedList();
/* 104 */       for (Iterator<LTDataAdapter> iterator = LTDataAdapterProvider.getInstance().getDataAdapters().iterator(); iterator.hasNext(); ) {
/* 105 */         final LTDataAdapter adapter = iterator.next();
/* 106 */         futures.add(executor.submit(new Callable()
/*     */               {
/*     */                 public Object call() throws Exception {
/* 109 */                   return adapter.getLTCTOCService().getCTOC();
/*     */                 }
/*     */               }));
/*     */       } 
/*     */       
/* 114 */       for (Iterator<Future> iter = futures.iterator(); iter.hasNext(); ) {
/* 115 */         Future f = iter.next();
/* 116 */         ret.add(f.get());
/*     */       } 
/*     */       
/* 119 */       return ret;
/*     */     }
/* 121 */     catch (InterruptedException e) {
/* 122 */       Thread.currentThread().interrupt();
/* 123 */       return Collections.EMPTY_SET;
/* 124 */     } catch (ExecutionException e) {
/* 125 */       throw Util.toRuntimeException(e.getCause());
/*     */     } finally {
/* 127 */       executor.shutdown();
/*     */     } 
/*     */   }
/*     */   
/*     */   public ILVCAdapter getLVCAdapter() {
/* 132 */     Collection adapters = LTDataAdapterProvider.getInstance().getDataAdapters(this.context);
/* 133 */     if (Util.isNullOrEmpty(adapters))
/* 134 */       return null; 
/* 135 */     if (adapters.size() == 1) {
/* 136 */       return ((LTDataAdapter)CollectionUtil.getFirst(adapters)).getLVCAdapter();
/*     */     }
/* 138 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public Set getVINResolvers(ClientContext context) {
/* 143 */     Set ret = new LinkedHashSet();
/* 144 */     for (Iterator<LTDataAdapter> iter = LTDataAdapterProvider.getInstance().getDataAdapters().iterator(); iter.hasNext();) {
/* 145 */       ret.addAll(((LTDataAdapter)iter.next()).getVINResolvers(context));
/*     */     }
/* 147 */     return ret;
/*     */   }
/*     */   
/*     */   public FTSService getFTSService() {
/* 151 */     Collection adapters = LTDataAdapterProvider.getInstance().getDataAdapters(this.context);
/* 152 */     if (Util.isNullOrEmpty(adapters))
/* 153 */       return null; 
/* 154 */     if (adapters.size() == 1) {
/* 155 */       return ((LTDataAdapter)CollectionUtil.getFirst(adapters)).getFTSService();
/*     */     }
/* 157 */     return (FTSService)new FTSServiceChain(adapters);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\v2\LTDataAdapterFacade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */