/*     */ package com.eoos.gm.tis2web.lt.v2;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.ConfigurationServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.ConfigurationService;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.propcfg.util.ConfigurationUtil;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ class LTDataAdapterProvider
/*     */ {
/*  24 */   private static final Logger log = Logger.getLogger(LTDataAdapterProvider.class);
/*     */   
/*  26 */   private static LTDataAdapterProvider instance = null;
/*     */   
/*  28 */   private final Object SYNC_DELEGATES = new Object();
/*     */   
/*  30 */   private Set delegates = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static synchronized LTDataAdapterProvider getInstance() {
/*  37 */     if (instance == null) {
/*  38 */       instance = new LTDataAdapterProvider();
/*     */     }
/*  40 */     return instance;
/*     */   }
/*     */   
/*     */   public void reset() {
/*  44 */     synchronized (this.SYNC_DELEGATES) {
/*  45 */       this.delegates = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private Set getDelegates() {
/*  50 */     synchronized (this.SYNC_DELEGATES) {
/*  51 */       if (this.delegates == null) {
/*  52 */         this.delegates = new LinkedHashSet();
/*  53 */         log.info("inspecting configuration and creating LT data adapters...");
/*  54 */         final ConfigurationService configSrv = ConfigurationServiceProvider.getService();
/*  55 */         final SubConfigurationWrapper cfg = new SubConfigurationWrapper((Configuration)configSrv, "component.lt.adapter.");
/*  56 */         for (Iterator<String> iter = subConfigurationWrapper.getKeys().iterator(); iter.hasNext(); ) {
/*  57 */           String key = iter.next();
/*  58 */           String delegateID = null;
/*  59 */           if (key.startsWith("lt.main.ltmain.") && (key.endsWith(".db.url") || key.endsWith(".db.data-source"))) {
/*     */             try {
/*  61 */               delegateID = key.substring("lt.main.ltmain.".length(), Math.max(key.lastIndexOf(".db.url"), key.lastIndexOf(".db.data-source")));
/*  62 */             } catch (IndexOutOfBoundsException e) {
/*  63 */               log.error("unable to create adapter, missing adapter identifier, skipping - exception: ", e);
/*     */             } 
/*     */           }
/*  66 */           if (delegateID != null) {
/*     */             
/*  68 */             log.info("...found configuration for adapter " + delegateID + ", creating instance");
/*     */             try {
/*  70 */               LTDataAdapter delegate = new LTDataAdapterStd((Configuration)subConfigurationWrapper, delegateID);
/*  71 */               this.delegates.add(delegate);
/*  72 */             } catch (Exception e) {
/*  73 */               log.error("unable to create adapter " + delegateID + ", skipping - exception: ", e);
/*     */             } 
/*     */           } 
/*     */         } 
/*  77 */         final int configHash = ConfigurationUtil.configurationHash((Configuration)subConfigurationWrapper);
/*  78 */         configSrv.addObserver(new ConfigurationService.Observer()
/*     */             {
/*     */               public void onModification() {
/*  81 */                 if (ConfigurationUtil.configurationHash(cfg) != configHash) {
/*  82 */                   LTDataAdapterProvider.log.debug("adapter configuration has changed, resetting");
/*  83 */                   LTDataAdapterProvider.this.reset();
/*  84 */                   configSrv.removeObserver(this);
/*     */                 } 
/*     */               }
/*     */             });
/*  88 */         log.info("...done");
/*     */       } 
/*  90 */       return this.delegates;
/*     */     } 
/*     */   }
/*     */   
/*     */   private Collection getDataAdapters(IConfiguration cfg) {
/*  95 */     if (getDelegates().size() == 1) {
/*  96 */       return getDelegates();
/*     */     }
/*     */     
/*  99 */     Collection<LTDataAdapter> ret = new LinkedList();
/*     */     
/* 101 */     for (Iterator<LTDataAdapter> iter = getDelegates().iterator(); iter.hasNext(); ) {
/* 102 */       LTDataAdapter adapter = iter.next();
/* 103 */       if (adapter.supports(cfg)) {
/* 104 */         ret.add(adapter);
/*     */       }
/*     */     } 
/*     */     
/* 108 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection getDataAdapters(ClientContext context) {
/* 113 */     if (context == null) {
/* 114 */       return getDelegates();
/*     */     }
/* 116 */     IConfiguration cfg = VCFacade.getInstance(context).getCfg();
/* 117 */     Collection ret = getDataAdapters(cfg);
/* 118 */     if (VehicleConfigurationUtil.getModel(cfg) != null && ret.size() > 1) {
/* 119 */       log.warn("THE ADAPTER SPLIT CRITERIA IS BROKEN, USING FIRST ADAPTER ONLY");
/* 120 */       ret = Collections.singletonList(CollectionUtil.getFirst(ret));
/*     */     } 
/* 122 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set getDataAdapters() {
/* 127 */     return getDelegates();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\v2\LTDataAdapterProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */