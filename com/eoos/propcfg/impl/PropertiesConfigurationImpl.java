/*    */ package com.eoos.propcfg.impl;
/*    */ 
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import java.util.HashSet;
/*    */ import java.util.Properties;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PropertiesConfigurationImpl
/*    */   implements Configuration, Configuration.Modifyable
/*    */ {
/*    */   private Properties properties;
/*    */   
/*    */   public PropertiesConfigurationImpl(Properties properties) {
/* 17 */     this.properties = properties;
/*    */   }
/*    */   
/*    */   public String getProperty(String key) {
/* 21 */     String retValue = this.properties.getProperty(key);
/* 22 */     return (retValue != null) ? retValue.trim() : null;
/*    */   }
/*    */   
/*    */   public Set getKeys() {
/* 26 */     Set retValue = new HashSet();
/* 27 */     retValue.addAll(this.properties.keySet());
/* 28 */     return retValue;
/*    */   }
/*    */   
/*    */   public void setProperty(String key, String value) {
/* 32 */     if (value != null) {
/* 33 */       this.properties.setProperty(key, value);
/*    */     } else {
/* 35 */       this.properties.remove(key);
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getFullKey(String key) {
/* 40 */     return key;
/*    */   }
/*    */   
/*    */   public void remove(String key) {
/* 44 */     this.properties.remove(key);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\propcfg\impl\PropertiesConfigurationImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */