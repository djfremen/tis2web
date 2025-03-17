/*    */ package com.eoos.gm.tis2web.vc.v2.base.provider;
/*    */ 
/*    */ import com.eoos.gm.tis2web.vc.v2.base.NotNormalizedException;
/*    */ import com.eoos.gm.tis2web.vc.v2.base.configuration.ConfigurationUtil;
/*    */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.LinkedHashSet;
/*    */ import java.util.Set;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class IntersectingCfgsCalculation_Impl2
/*    */   implements IntersectingCfgsCalculation
/*    */ {
/* 15 */   private static final Logger log = Logger.getLogger(IntersectingCfgsCalculation_Impl2.class);
/*    */   
/*    */   private CfgProvider configProvider;
/*    */   
/*    */   private ConfigurationUtil cfgUtil;
/*    */   
/*    */   public IntersectingCfgsCalculation_Impl2(CfgProvider backend, ConfigurationUtil cfgUtil) {
/* 22 */     this.configProvider = backend;
/* 23 */     this.cfgUtil = cfgUtil;
/*    */   }
/*    */   
/*    */   public Set getIntersectingConfigs(IConfiguration currentCfg) {
/* 27 */     if (log.isDebugEnabled()) {
/* 28 */       log.debug("retrieving supporting configurations for currentCfg: " + currentCfg);
/*    */     }
/*    */     
/* 31 */     if (currentCfg.equals(this.cfgUtil.getEmptyConfiguration())) {
/* 32 */       return new LinkedHashSet(this.configProvider.getConfigurations());
/*    */     }
/*    */     
/* 35 */     Collection configsToExaminate = this.configProvider.getConfigurations();
/*    */     
/* 37 */     if (log.isDebugEnabled()) {
/* 38 */       log.debug("...examine " + configsToExaminate.size() + " configurations");
/*    */     }
/*    */     
/* 41 */     Set<IConfiguration> intersection = new LinkedHashSet();
/* 42 */     for (IConfiguration cfg : configsToExaminate) {
/*    */       try {
/* 44 */         IConfiguration tmp = this.cfgUtil.intersect(cfg, currentCfg, ConfigurationUtil.EXPAND_WITH_ANY);
/* 45 */         if (tmp != null) {
/* 46 */           intersection.add(tmp);
/*    */         }
/* 48 */       } catch (NotNormalizedException e) {
/* 49 */         throw new RuntimeException(e);
/*    */       } 
/*    */     } 
/* 52 */     return Collections.unmodifiableSet(intersection);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\base\provider\IntersectingCfgsCalculation_Impl2.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */