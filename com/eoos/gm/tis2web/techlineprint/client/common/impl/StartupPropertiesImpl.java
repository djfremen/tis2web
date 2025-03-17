/*    */ package com.eoos.gm.tis2web.techlineprint.client.common.impl;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Locale;
/*    */ import java.util.StringTokenizer;
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
/*    */   protected static final String DIR = "techlineprint";
/*    */   protected static final String NATIVE_LAN = "language.native.id";
/* 19 */   private static Logger log = Logger.getLogger(StartupPropertiesImpl.class);
/*    */   
/*    */   protected Locale getLocale() {
/* 22 */     Locale result = null;
/* 23 */     String lid = System.getProperty("language.id");
/*    */     try {
/* 25 */       int i = lid.indexOf('_');
/* 26 */       if (i < 0) {
/* 27 */         result = new Locale(lid, "");
/*    */       } else {
/* 29 */         String lang = lid.substring(0, i);
/* 30 */         String country = lid.substring(i + 1, lid.length());
/* 31 */         result = new Locale(lang, country);
/*    */       } 
/* 33 */     } catch (Exception e) {
/* 34 */       log.warn("No locale in jnlp parameters");
/*    */     } 
/* 36 */     if (result == null) {
/* 37 */       String lang = System.getProperty("user.language");
/* 38 */       String country = System.getProperty("user.region");
/*    */       try {
/* 40 */         result = new Locale(lang, country);
/* 41 */       } catch (Exception e) {
/* 42 */         log.warn("Cannot create locale from JVM system properties");
/* 43 */         result = Locale.US;
/*    */       } 
/*    */     } 
/* 46 */     return result;
/*    */   }
/*    */   
/*    */   protected String getSessionID() {
/* 50 */     return System.getProperty("session.id");
/*    */   }
/*    */   
/*    */   protected String getBACCode() {
/* 54 */     return System.getProperty("bac.code");
/*    */   }
/*    */   
/*    */   protected String getNativeLan() {
/* 58 */     return System.getProperty("language.native.id");
/*    */   }
/*    */   
/*    */   protected List<String> getSupportedDevices() {
/* 62 */     List<String> devices = new ArrayList<String>();
/* 63 */     String str = System.getProperty("devices");
/* 64 */     if (str != null) {
/* 65 */       StringTokenizer tok = new StringTokenizer(str, ",");
/* 66 */       while (tok.hasMoreTokens()) {
/* 67 */         devices.add(tok.nextToken().trim());
/*    */       }
/*    */     } 
/* 70 */     return devices;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\techlineprint\client\common\impl\StartupPropertiesImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */