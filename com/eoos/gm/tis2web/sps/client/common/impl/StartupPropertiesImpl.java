/*     */ package com.eoos.gm.tis2web.sps.client.common.impl;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StartupPropertiesImpl
/*     */ {
/*     */   private static final String SPS_VERSION = "sps.version";
/*     */   private static final String SERVER_INSTALLATION = "server.installation";
/*     */   private static final String LANGUAGE = "language.id";
/*     */   private static final String SESSIONID = "session.id";
/*     */   private static final String DEFAULTMAKE = "make.default";
/*     */   private static final String BAC_CODE = "bac.code";
/*     */   private static final String J2534_MAP = "device.driver.map";
/*     */   private static final String NAVTABLE_VALID_MAP = "navtable.validation.map";
/*     */   private static final String COUNTRYCODE = "country.code";
/*     */   private static final String NAVTABLE_FILTER = "navtable.filter";
/*     */   private static final String WARRANTY_CODE_LIST = "warranty.code.list";
/*     */   private static final String VCI1001_ENABLED = "vci.1001.enabled";
/*  37 */   private static Logger log = Logger.getLogger(StartupPropertiesImpl.class);
/*     */   
/*     */   protected Locale getLocale() {
/*  40 */     Locale result = null;
/*  41 */     String lid = System.getProperty("language.id");
/*     */     try {
/*  43 */       int i = lid.indexOf('_');
/*  44 */       if (i < 0) {
/*  45 */         result = new Locale(lid, "");
/*     */       } else {
/*  47 */         String lang = lid.substring(0, i);
/*  48 */         String country = lid.substring(i + 1, lid.length());
/*  49 */         result = new Locale(lang, country);
/*     */       } 
/*  51 */     } catch (Exception e) {
/*  52 */       log.warn("No locale in jnlp parameters");
/*     */     } 
/*  54 */     if (result == null) {
/*  55 */       String lang = System.getProperty("user.language");
/*  56 */       String country = System.getProperty("user.region");
/*     */       try {
/*  58 */         result = new Locale(lang, country);
/*  59 */       } catch (Exception e) {
/*  60 */         log.warn("Cannot create locale from JVM system properties");
/*  61 */         result = Locale.US;
/*     */       } 
/*     */     } 
/*  64 */     return result;
/*     */   }
/*     */   
/*     */   protected String getSessionID() {
/*  68 */     return System.getProperty("session.id");
/*     */   }
/*     */   
/*     */   protected String getDefaultMake() {
/*  72 */     return System.getProperty("make.default");
/*     */   }
/*     */   
/*     */   protected List getSupportedBrands() {
/*  76 */     List<String> brands = new ArrayList();
/*  77 */     String str = System.getProperty("brands");
/*  78 */     if (str != null) {
/*  79 */       StringTokenizer tok = new StringTokenizer(str, ",");
/*  80 */       while (tok.hasMoreTokens()) {
/*  81 */         brands.add(tok.nextToken().trim());
/*     */       }
/*     */     } 
/*  84 */     return brands;
/*     */   }
/*     */   
/*     */   protected String getBACCode() {
/*  88 */     return System.getProperty("bac.code");
/*     */   }
/*     */   
/*     */   protected String getJ2534() {
/*  92 */     return System.getProperty("device.driver.map");
/*     */   }
/*     */   
/*     */   protected String getNavtableValidationMap() {
/*  96 */     return System.getProperty("navtable.validation.map");
/*     */   }
/*     */   
/*     */   protected String getVersion() {
/* 100 */     return System.getProperty("sps.version");
/*     */   }
/*     */   
/*     */   protected String getLanguage() {
/* 104 */     return System.getProperty("language.id");
/*     */   }
/*     */   
/*     */   protected String getServerInstallationType() {
/* 108 */     return System.getProperty("server.installation");
/*     */   }
/*     */   
/*     */   public String getCountryCode() {
/* 112 */     return System.getProperty("country.code");
/*     */   }
/*     */   
/*     */   public static String getNavTableFilter() {
/* 116 */     return System.getProperty("navtable.filter");
/*     */   }
/*     */   
/*     */   protected String getWarrantyCodeList() {
/* 120 */     return System.getProperty("warranty.code.list");
/*     */   }
/*     */   
/*     */   protected String getVCI1001Enabled() {
/* 124 */     return System.getProperty("vci.1001.enabled");
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\common\impl\StartupPropertiesImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */