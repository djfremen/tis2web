/*    */ package com.eoos.gm.tis2web.tech2view.client.common.impl;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StartupPropertiesImpl
/*    */ {
/*    */   private static final String LANGUAGE = "language.id";
/*    */   private static final String SESSIONID = "session.id";
/*    */   private static final String BAC_CODE = "bac.code";
/*    */   protected static final String DIR = "tech2view";
/*    */   protected static final String NATIVE_LAN = "language.native.id";
/* 16 */   private static Logger log = Logger.getLogger(StartupPropertiesImpl.class);
/*    */   
/*    */   protected Locale getLocale() {
/* 19 */     Locale result = null;
/* 20 */     String lid = System.getProperty("language.id");
/*    */     try {
/* 22 */       int i = lid.indexOf('_');
/* 23 */       if (i < 0) {
/* 24 */         result = new Locale(lid, "");
/*    */       } else {
/* 26 */         String lang = lid.substring(0, i);
/* 27 */         String country = lid.substring(i + 1, lid.length());
/* 28 */         result = new Locale(lang, country);
/*    */       } 
/* 30 */     } catch (Exception e) {
/* 31 */       log.warn("No locale in jnlp parameters");
/*    */     } 
/* 33 */     if (result == null) {
/* 34 */       String lang = System.getProperty("user.language");
/* 35 */       String country = System.getProperty("user.region");
/*    */       try {
/* 37 */         result = new Locale(lang, country);
/* 38 */       } catch (Exception e) {
/* 39 */         log.warn("Cannot create locale from JVM system properties");
/* 40 */         result = Locale.US;
/*    */       } 
/*    */     } 
/* 43 */     return result;
/*    */   }
/*    */   
/*    */   protected String getSessionID() {
/* 47 */     return System.getProperty("session.id");
/*    */   }
/*    */   
/*    */   protected String getBACCode() {
/* 51 */     return System.getProperty("bac.code");
/*    */   }
/*    */   
/*    */   protected String getNativeLan() {
/* 55 */     return System.getProperty("language.native.id");
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\tech2view\client\common\impl\StartupPropertiesImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */