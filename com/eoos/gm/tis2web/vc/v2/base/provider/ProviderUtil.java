/*    */ package com.eoos.gm.tis2web.vc.v2.base.provider;
/*    */ 
/*    */ import com.eoos.gm.tis2web.vc.v2.base.configuration.ConfigurationUtil;
/*    */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*    */ import java.util.Collection;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProviderUtil
/*    */ {
/*    */   public static CfgProvider createCfgProvider(Collection configurations, ConfigurationUtil cfgUtil) {
/* 14 */     final Set cfgs = CollectionUtil.toSet(cfgUtil.getDecorator().normalize(configurations, cfgUtil.valueUtil.valueManagement.getANY()));
/* 15 */     final Set keys = cfgUtil.getKeys(configurations, ConfigurationUtil.Mode.COMMON_KEYS);
/* 16 */     return new CfgProvider()
/*    */       {
/*    */         public long getLastModified() {
/* 19 */           return 0L;
/*    */         }
/*    */         
/*    */         public Set getKeys() {
/* 23 */           return keys;
/*    */         }
/*    */         
/*    */         public Set getConfigurations() {
/* 27 */           return cfgs;
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\base\provider\ProviderUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */