/*    */ package com.eoos.gm.tis2web.snapshot.client.common.impl;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StartupPropertiesImpl
/*    */ {
/*    */   private static final String LANGUAGE = "language.id";
/*    */   private static final String SESSIONID = "session.id";
/*    */   private static final String BAC_CODE = "bac.code";
/*    */   protected static final String DIR = "snapshot";
/*    */   protected static final String NATIVE_LAN = "language.native.id";
/*    */   protected static final String MAIL_ENABLED = "mail.enabled";
/*    */   protected static final String INSTALLATION_TYPE = "installation.type";
/*    */   private static final String COUNTRYCODE = "country.code";
/* 21 */   private static Logger log = Logger.getLogger(StartupPropertiesImpl.class);
/*    */   
/*    */   protected Locale getLocale() {
/* 24 */     Locale result = null;
/* 25 */     String lid = System.getProperty("language.id");
/*    */     try {
/* 27 */       int i = lid.indexOf('_');
/* 28 */       if (i < 0) {
/* 29 */         result = new Locale(lid, "");
/*    */       } else {
/* 31 */         String lang = lid.substring(0, i);
/* 32 */         String country = lid.substring(i + 1, lid.length());
/* 33 */         result = new Locale(lang, country);
/*    */       } 
/* 35 */     } catch (Exception e) {
/* 36 */       log.warn("No locale in jnlp parameters");
/*    */     } 
/* 38 */     if (result == null) {
/* 39 */       String lang = System.getProperty("user.language");
/* 40 */       String country = System.getProperty("user.region");
/*    */       try {
/* 42 */         result = new Locale(lang, country);
/* 43 */       } catch (Exception e) {
/* 44 */         log.warn("Cannot create locale from JVM system properties");
/* 45 */         result = Locale.US;
/*    */       } 
/*    */     } 
/* 48 */     return result;
/*    */   }
/*    */   
/*    */   protected String getSessionID() {
/* 52 */     return System.getProperty("session.id");
/*    */   }
/*    */   
/*    */   protected String getCountryCode() {
/* 56 */     return System.getProperty("country.code");
/*    */   }
/*    */   
/*    */   protected String getBACCode() {
/* 60 */     return System.getProperty("bac.code");
/*    */   }
/*    */   
/*    */   protected String getNativeLan() {
/* 64 */     return System.getProperty("language.native.id");
/*    */   }
/*    */   
/*    */   protected boolean getMailConfiguration() {
/* 68 */     boolean result = false;
/*    */     try {
/* 70 */       if (System.getProperty("mail.enabled").compareTo("true") == 0) {
/* 71 */         result = true;
/*    */       }
/* 73 */     } catch (Exception e) {}
/*    */     
/* 75 */     return result;
/*    */   }
/*    */   
/*    */   protected String getInstallationType() {
/* 79 */     String result = "standalone";
/*    */     try {
/* 81 */       if (System.getProperty("installation.type").compareTo("local") == 0) {
/* 82 */         result = "local";
/* 83 */       } else if (System.getProperty("installation.type").compareTo("central") == 0) {
/* 84 */         result = "central";
/*    */       } 
/* 86 */     } catch (Exception e) {}
/*    */     
/* 88 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\snapshot\client\common\impl\StartupPropertiesImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */