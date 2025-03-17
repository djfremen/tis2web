/*    */ package com.eoos.gm.tis2web.sps.client.util;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*    */ import com.eoos.gm.tis2web.sps.client.common.ClientSettings;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SPSClientUtil
/*    */ {
/* 15 */   private static Configuration cfg = null;
/*    */   
/*    */   public static Configuration getConfiguration() {
/* 18 */     if (cfg == null) {
/* 19 */       final ClientSettings settings = ClientAppContextProvider.getClientAppContext().getClientSettings();
/* 20 */       cfg = new Configuration()
/*    */         {
/*    */           public String getProperty(String key) {
/* 23 */             String ret = settings.getProperty(key);
/* 24 */             if (ret == null) {
/* 25 */               ret = System.getProperty(key);
/*    */             }
/* 27 */             return ret;
/*    */           }
/*    */           
/*    */           public Set getKeys() {
/* 31 */             return settings.getKeys();
/*    */           }
/*    */           
/*    */           public String getFullKey(String key) {
/* 35 */             return key;
/*    */           }
/*    */         };
/*    */     } 
/* 39 */     return cfg;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\util\SPSClientUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */