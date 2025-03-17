/*     */ package com.eoos.gm.tis2web.vc.v2.provider;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.provider.CfgProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCService;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCServiceProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VehicleCfgStorage;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VIN;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VINResolver;
/*     */ import com.eoos.scsm.v2.collection.MultiMap;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ public class StoredCfgDataProvider
/*     */   implements CfgProvider, VINResolver
/*     */ {
/*  22 */   private final Object SYNC = new Object();
/*     */   
/*  24 */   private Set cfgs = Collections.synchronizedSet(new HashSet());
/*     */   
/*  26 */   private Map vinToCfg = Collections.synchronizedMap((Map<?, ?>)new MultiMap());
/*     */   
/*  28 */   private long modificationDate = 0L;
/*     */   
/*     */   private StoredCfgDataProvider(ClientContext context, VCService _vcService) {
/*  31 */     final VCService vcService = (_vcService != null) ? _vcService : VCServiceProvider.getInstance().getService(context);
/*     */     
/*  33 */     vcService.addObserver(new VehicleCfgStorage.Observer()
/*     */         {
/*     */           public void onVehicleConfigurationChange() {
/*  36 */             StoredCfgDataProvider.this.addCfg(vcService.getVIN(), vcService.getCfg());
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StoredCfgDataProvider getInstance(ClientContext context) {
/*  45 */     return getInstance(context, null);
/*     */   }
/*     */   
/*     */   public static StoredCfgDataProvider getInstance(ClientContext context, VCService vcService) {
/*  49 */     synchronized (context.getLockObject()) {
/*  50 */       StoredCfgDataProvider instance = (StoredCfgDataProvider)context.getObject(StoredCfgDataProvider.class);
/*  51 */       if (instance == null) {
/*  52 */         instance = new StoredCfgDataProvider(context, vcService);
/*  53 */         context.storeObject(StoredCfgDataProvider.class, instance);
/*     */       } 
/*  55 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addCfg(VIN vin, IConfiguration cfg) {
/*  60 */     synchronized (this.SYNC) {
/*  61 */       if (cfg != null) {
/*  62 */         IConfiguration configToAdd = cfg;
/*  63 */         configToAdd = VehicleConfigurationUtil.cfgUtil.normalize(configToAdd, getKeys(), null);
/*  64 */         this.cfgs.add(configToAdd);
/*  65 */         if (vin != null) {
/*  66 */           this.vinToCfg.put(vin, cfg);
/*     */         }
/*  68 */         this.modificationDate = System.currentTimeMillis();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Set getConfigurations() {
/*  75 */     synchronized (this.SYNC) {
/*  76 */       return new HashSet(this.cfgs);
/*     */     } 
/*     */   }
/*     */   
/*     */   public long getLastModified() {
/*  81 */     synchronized (this.SYNC) {
/*  82 */       return this.modificationDate;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {}
/*     */ 
/*     */   
/*     */   public Set resolveVIN(VIN vin) throws VIN.InvalidVINException {
/*  91 */     Set<?> ret = (Set)this.vinToCfg.get(vin);
/*  92 */     if (ret != null) {
/*  93 */       return Collections.unmodifiableSet(ret);
/*     */     }
/*  95 */     return Collections.EMPTY_SET;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set getKeys() {
/* 100 */     return VehicleConfigurationUtil.KEY_SET;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\provider\StoredCfgDataProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */