/*    */ package com.eoos.gm.tis2web.vc.v2.base.provider;
/*    */ 
/*    */ import com.eoos.gm.tis2web.vc.v2.base.configuration.ConfigurationUtil;
/*    */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*    */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*    */ import java.util.Collections;
/*    */ import java.util.LinkedHashSet;
/*    */ import java.util.Set;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class CfgDataProviderImpl
/*    */   implements CfgDataProvider, CfgProvider, IntersectingCfgsCalculation
/*    */ {
/* 14 */   private static final Logger log = Logger.getLogger(CfgDataProviderImpl.class);
/*    */   
/*    */   private CfgProvider configProvider;
/*    */   
/*    */   private IntersectingCfgsCalculation intersectingCfgsCalc;
/*    */   
/* 20 */   private final Object SYNC_CFGS = new Object();
/*    */   
/* 22 */   private Set cfgs = null;
/*    */   
/* 24 */   private long cfgsTimestamp = 0L;
/*    */   
/*    */   public CfgDataProviderImpl(CfgProvider configProvider, ConfigurationUtil cfgUtil) {
/* 27 */     this.configProvider = configProvider;
/*    */     
/* 29 */     this.intersectingCfgsCalc = new IntersectingCfgsCalculation_STDImpl(this, cfgUtil);
/*    */   }
/*    */ 
/*    */   
/*    */   public Set getConfigurations() {
/* 34 */     synchronized (this.SYNC_CFGS) {
/* 35 */       if (this.cfgs == null || this.cfgsTimestamp < this.configProvider.getLastModified()) {
/* 36 */         this.cfgs = Collections.unmodifiableSet(this.configProvider.getConfigurations());
/* 37 */         this.cfgsTimestamp = this.configProvider.getLastModified();
/*    */       } 
/* 39 */       return this.cfgs;
/*    */     } 
/*    */   }
/*    */   
/*    */   public Set getKeys() {
/* 44 */     return this.configProvider.getKeys();
/*    */   }
/*    */   
/*    */   public Set getValues(final Object key, IConfiguration currentCfg) {
/* 48 */     if (log.isDebugEnabled()) {
/* 49 */       log.debug("retrieving values for key: " + key + " and currentCfg: " + currentCfg);
/*    */     }
/* 51 */     if (!getKeys().contains(key)) {
/* 52 */       log.debug("...key is not suppported by this data provider, returning empty set");
/* 53 */       return Collections.EMPTY_SET;
/*    */     } 
/*    */     
/* 56 */     final Set ret = new LinkedHashSet();
/*    */     
/* 58 */     Set supportedCfgs = getIntersectingConfigs(currentCfg);
/* 59 */     if (supportedCfgs != null && supportedCfgs.size() > 0) {
/* 60 */       if (log.isDebugEnabled()) {
/* 61 */         log.debug("...found " + supportedCfgs.size() + " intersecting cfgs");
/*    */       }
/*    */       try {
/* 64 */         CollectionUtil.foreach(supportedCfgs, new CollectionUtil.ForeachCallback()
/*    */             {
/*    */               public Object executeOperation(Object item) throws Exception {
/* 67 */                 IConfiguration cfg = (IConfiguration)item;
/* 68 */                 Object value = cfg.getValue(key);
/* 69 */                 if (value != null) {
/* 70 */                   ret.add(value);
/*    */                 }
/* 72 */                 return null;
/*    */               }
/*    */             });
/*    */       
/*    */       }
/* 77 */       catch (Exception e) {
/* 78 */         throw new RuntimeException(e);
/*    */       } 
/*    */     } 
/*    */     
/* 82 */     if (log.isDebugEnabled()) {
/* 83 */       log.debug("...result: " + ret);
/*    */     }
/* 85 */     return ret;
/*    */   }
/*    */   
/*    */   public long getLastModified() {
/* 89 */     return this.configProvider.getLastModified();
/*    */   }
/*    */   
/*    */   public Set getIntersectingConfigs(IConfiguration currentCfg) {
/* 93 */     return this.intersectingCfgsCalc.getIntersectingConfigs(currentCfg);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\base\provider\CfgDataProviderImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */