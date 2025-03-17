/*     */ package com.eoos.gm.tis2web.si.v2;
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
/*     */ class SIDataAdapterProvider
/*     */ {
/*  24 */   private static final Logger log = Logger.getLogger(SIDataAdapterProvider.class);
/*     */   
/*  26 */   private static SIDataAdapterProvider instance = null;
/*     */   
/*  28 */   private final Object SYNC_DELEGATES = new Object();
/*     */   
/*  30 */   private Set delegates = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static synchronized SIDataAdapterProvider getInstance() {
/*  37 */     if (instance == null) {
/*  38 */       instance = new SIDataAdapterProvider();
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
/*  53 */         log.info("inspecting configuration and creating SI data adapters...");
/*  54 */         final ConfigurationService configSrv = ConfigurationServiceProvider.getService();
/*  55 */         final SubConfigurationWrapper cfg = new SubConfigurationWrapper((Configuration)configSrv, "component.si.adapter.");
/*  56 */         for (Iterator<String> iter = subConfigurationWrapper.getKeys().iterator(); iter.hasNext(); ) {
/*  57 */           String key = iter.next();
/*  58 */           String delegateID = null;
/*  59 */           if (key.startsWith("si.main.simain.") && (key.endsWith(".db.url") || key.endsWith(".db.data-source"))) {
/*     */             try {
/*  61 */               delegateID = key.substring("si.main.simain.".length(), Math.max(key.lastIndexOf(".db.url"), key.lastIndexOf(".db.data-source")));
/*  62 */             } catch (IndexOutOfBoundsException e) {
/*  63 */               log.error("unable to create adapter, missing adapter identifier, skipping - exception: ", e);
/*     */             } 
/*     */           }
/*  66 */           if (delegateID != null) {
/*  67 */             log.info("...found configuration for adapter " + delegateID + ", creating instance");
/*     */             try {
/*  69 */               SIDataAdapter delegate = new SIDataAdapterStd((Configuration)subConfigurationWrapper, delegateID);
/*  70 */               this.delegates.add(delegate);
/*  71 */             } catch (Exception e) {
/*  72 */               log.error("unable to create adapter, skipping - exception: ", e);
/*     */             } 
/*     */           } 
/*     */         } 
/*  76 */         final int configHash = ConfigurationUtil.configurationHash((Configuration)subConfigurationWrapper);
/*  77 */         configSrv.addObserver(new ConfigurationService.Observer()
/*     */             {
/*     */               public void onModification() {
/*  80 */                 if (ConfigurationUtil.configurationHash(cfg) != configHash) {
/*  81 */                   SIDataAdapterProvider.log.debug("adapter configuration has changed, resetting");
/*  82 */                   SIDataAdapterProvider.this.reset();
/*  83 */                   configSrv.removeObserver(this);
/*     */                 } 
/*     */               }
/*     */             });
/*  87 */         log.info("...done");
/*     */       } 
/*  89 */       return this.delegates;
/*     */     } 
/*     */   }
/*     */   
/*     */   private Collection getDataAdapters(IConfiguration cfg) {
/*  94 */     if (getDelegates().size() == 1) {
/*  95 */       return getDelegates();
/*     */     }
/*  97 */     Collection<SIDataAdapter> ret = new LinkedList();
/*  98 */     for (Iterator<SIDataAdapter> iter = getDelegates().iterator(); iter.hasNext(); ) {
/*  99 */       SIDataAdapter adapter = iter.next();
/* 100 */       if (adapter.supports(cfg)) {
/* 101 */         ret.add(adapter);
/*     */       }
/*     */     } 
/*     */     
/* 105 */     return ret;
/*     */   }
/*     */   
/*     */   public Collection getDataAdapters(ClientContext context) {
/* 109 */     if (context == null) {
/* 110 */       return getDelegates();
/*     */     }
/* 112 */     IConfiguration cfg = VCFacade.getInstance(context).getCfg();
/* 113 */     Collection ret = getDataAdapters(cfg);
/* 114 */     if (VehicleConfigurationUtil.getModel(cfg) != null && ret.size() > 1) {
/* 115 */       log.warn("THE ADAPTER SPLIT CRITERIA IS BROKEN, USING FIRST ADAPTER ONLY");
/* 116 */       ret = Collections.singletonList(CollectionUtil.getFirst(ret));
/*     */     } 
/* 118 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set getDataAdapters() {
/* 123 */     return getDelegates();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\v2\SIDataAdapterProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */