/*    */ package com.eoos.propcfg.util;
/*    */ 
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import java.util.Properties;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class SystemPropertiesAdapter
/*    */   implements Configuration
/*    */ {
/* 10 */   private static SystemPropertiesAdapter instance = null;
/*    */   
/* 12 */   private Properties env = null;
/*    */   
/*    */   private SystemPropertiesAdapter() {
/* 15 */     this.env = System.getProperties();
/*    */   }
/*    */   
/*    */   public static synchronized SystemPropertiesAdapter getInstance() {
/* 19 */     if (instance == null) {
/* 20 */       instance = new SystemPropertiesAdapter();
/*    */     }
/* 22 */     return instance;
/*    */   }
/*    */   
/*    */   public String getFullKey(String key) {
/* 26 */     return key;
/*    */   }
/*    */   
/*    */   public Set getKeys() {
/* 30 */     return this.env.keySet();
/*    */   }
/*    */   
/*    */   public String getProperty(String key) {
/* 34 */     return this.env.getProperty(key);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\propcf\\util\SystemPropertiesAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */