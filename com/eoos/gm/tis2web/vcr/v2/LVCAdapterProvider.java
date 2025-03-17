/*    */ package com.eoos.gm.tis2web.vcr.v2;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.ConfigurationServiceProvider;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import com.eoos.propcfg.SubConfigurationWrapper;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ public class LVCAdapterProvider
/*    */ {
/* 13 */   private static LVCAdapterProvider instance = null;
/*    */   
/* 15 */   private Map instances = Collections.synchronizedMap(new HashMap<Object, Object>());
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static synchronized LVCAdapterProvider getInstance() {
/* 22 */     if (instance == null) {
/* 23 */       instance = new LVCAdapterProvider();
/*    */     }
/* 25 */     return instance;
/*    */   }
/*    */   
/*    */   static String getKey(Configuration cfgVC, Configuration cfgVCR) {
/* 29 */     StringBuffer ret = new StringBuffer();
/* 30 */     ret.append(cfgVC.getProperty("db.url"));
/* 31 */     ret.append("#");
/* 32 */     ret.append(cfgVC.getProperty("db.usr"));
/* 33 */     ret.append("#");
/* 34 */     ret.append(cfgVCR.getProperty("db.url"));
/* 35 */     ret.append("#");
/* 36 */     ret.append(cfgVCR.getProperty("db.usr"));
/* 37 */     return ret.toString();
/*    */   }
/*    */   
/*    */   private synchronized ILVCAdapter _getAdapter(Configuration cfgVC, Configuration cfgVCR) {
/* 41 */     String key = getKey(cfgVC, cfgVCR);
/* 42 */     ILVCAdapter ret = (ILVCAdapter)this.instances.get(key);
/* 43 */     if (ret == null) {
/* 44 */       ret = new LVCAdapter(cfgVC, cfgVCR);
/* 45 */       this.instances.put(key, ret);
/*    */     } 
/* 47 */     return ret;
/*    */   }
/*    */ 
/*    */   
/*    */   public static ILVCAdapter getAdapter(Configuration cfgVC, Configuration cfgVCR) {
/* 52 */     return getInstance()._getAdapter(cfgVC, cfgVCR);
/*    */   }
/*    */   
/*    */   public static ILVCAdapter getGlobalAdapter() {
/* 56 */     SubConfigurationWrapper subConfigurationWrapper1 = new SubConfigurationWrapper((Configuration)ConfigurationServiceProvider.getService(), "frame.adapter.vc.");
/* 57 */     SubConfigurationWrapper subConfigurationWrapper2 = new SubConfigurationWrapper((Configuration)ConfigurationServiceProvider.getService(), "frame.adapter.vcr.");
/*    */     
/* 59 */     return getInstance()._getAdapter((Configuration)subConfigurationWrapper1, (Configuration)subConfigurationWrapper2);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vcr\v2\LVCAdapterProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */