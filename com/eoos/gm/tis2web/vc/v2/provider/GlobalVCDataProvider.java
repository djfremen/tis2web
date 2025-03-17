/*     */ package com.eoos.gm.tis2web.vc.v2.provider;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.ConfiguredServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.Tis2webUtil;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.provider.CDPCacheAdapter;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.provider.CfgDataProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.provider.CfgDataProviderImpl;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.provider.CfgProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class GlobalVCDataProvider
/*     */   implements CfgDataProvider
/*     */ {
/*  24 */   private static final Logger log = Logger.getLogger(GlobalVCDataProvider.class);
/*     */   
/*  26 */   private static CfgDataProvider instance = null;
/*     */   
/*  28 */   private Set providers = new LinkedHashSet();
/*     */ 
/*     */   
/*     */   private GlobalVCDataProvider(ClientContext context) {
/*  32 */     if (context != null) {
/*  33 */       this.providers.add(new CfgDataProviderImpl(StoredCfgDataProvider.getInstance(context), VehicleConfigurationUtil.cfgUtil));
/*     */     }
/*  35 */     Set<CfgProvider> cfgProviders = new LinkedHashSet();
/*     */     
/*  37 */     for (Iterator<CfgDataProvider> iterator2 = ConfiguredServiceProvider.getInstance().getServices(CfgDataProvider.class).iterator(); iterator2.hasNext(); ) {
/*  38 */       CfgDataProvider dp1 = iterator2.next();
/*  39 */       this.providers.add(dp1);
/*     */     } 
/*  41 */     for (Iterator<CfgDataProviderRetrieval> iterator1 = ConfiguredServiceProvider.getInstance().getServices(CfgDataProviderRetrieval.class).iterator(); iterator1.hasNext(); ) {
/*  42 */       CfgDataProviderRetrieval retrieval = iterator1.next();
/*  43 */       this.providers.addAll(retrieval.getCfgDataProviders(context));
/*     */     } 
/*     */     
/*  46 */     for (Iterator<CfgProvider> iterator = ConfiguredServiceProvider.getInstance().getServices(CfgProvider.class).iterator(); iterator.hasNext(); ) {
/*  47 */       CfgProvider dp1 = iterator.next();
/*  48 */       cfgProviders.add(dp1);
/*     */     } 
/*     */     
/*  51 */     for (Iterator<CfgProviderRetrieval> iter = ConfiguredServiceProvider.getInstance().getServices(CfgProviderRetrieval.class).iterator(); iter.hasNext(); ) {
/*  52 */       CfgProviderRetrieval retrieval = iter.next();
/*  53 */       cfgProviders.addAll(retrieval.getCfgProviders());
/*     */     } 
/*     */     
/*  56 */     if (!Util.isNullOrEmpty(cfgProviders)) {
/*  57 */       if (context != null) {
/*  58 */         this.providers.add(new CfgDataProviderImpl(ContextualCfgProviderFacade.getInstance(context, new CfgProviderSet(cfgProviders)), VehicleConfigurationUtil.cfgUtil));
/*     */       } else {
/*  60 */         this.providers.add(new CfgDataProviderImpl(new CfgProviderSet(cfgProviders), VehicleConfigurationUtil.cfgUtil));
/*     */       } 
/*     */     }
/*     */     
/*  64 */     if (log.isDebugEnabled()) {
/*  65 */       log.debug("retrieved " + this.providers.size() + " vc data providers ");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static CfgDataProvider getInstance(ClientContext context) {
/*  71 */     synchronized (context.getLockObject()) {
/*  72 */       CfgDataProvider cfgDataProvider = (CfgDataProvider)context.getObject(GlobalVCDataProvider.class);
/*  73 */       if (cfgDataProvider == null) {
/*  74 */         cfgDataProvider = new GlobalVCDataProvider(context);
/*  75 */         CDPCacheAdapter cDPCacheAdapter = new CDPCacheAdapter(cfgDataProvider, Tis2webUtil.createStdCache());
/*  76 */         if (ApplicationContext.getInstance().developMode()) {
/*  77 */           cfgDataProvider = (CfgDataProvider)Tis2webUtil.hookWithExecutionTimeStatistics(cDPCacheAdapter, context);
/*     */         }
/*  79 */         context.storeObject(GlobalVCDataProvider.class, cfgDataProvider);
/*     */       } 
/*  81 */       return cfgDataProvider;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static synchronized CfgDataProvider getInstance() {
/*  86 */     if (instance == null) {
/*  87 */       instance = new GlobalVCDataProvider(null);
/*  88 */       instance = (CfgDataProvider)new CDPCacheAdapter(instance, Tis2webUtil.createStdCache());
/*     */     } 
/*  90 */     return instance;
/*     */   }
/*     */   
/*     */   public Set getKeys() {
/*  94 */     return CollectionUtil.toSet(VehicleConfigurationUtil.KEYS);
/*     */   }
/*     */   
/*     */   public Set getValues(Object key, IConfiguration currentCfg) {
/*  98 */     Set ret = new HashSet();
/*  99 */     for (Iterator<CfgDataProvider> iter = this.providers.iterator(); iter.hasNext(); ) {
/* 100 */       Set values = ((CfgDataProvider)iter.next()).getValues(key, currentCfg);
/* 101 */       ret.addAll(values);
/*     */     } 
/* 103 */     return ret;
/*     */   }
/*     */   
/*     */   public long getLastModified() {
/* 107 */     long ret = 0L;
/* 108 */     for (Iterator<CfgDataProvider> iter = this.providers.iterator(); iter.hasNext();) {
/* 109 */       ret = Math.max(((CfgDataProvider)iter.next()).getLastModified(), ret);
/*     */     }
/* 111 */     return ret;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\provider\GlobalVCDataProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */