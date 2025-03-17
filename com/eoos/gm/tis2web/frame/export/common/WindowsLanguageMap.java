/*    */ package com.eoos.gm.tis2web.frame.export.common;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.util.Enumeration;
/*    */ import java.util.HashMap;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ import java.util.ResourceBundle;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WindowsLanguageMap
/*    */ {
/* 17 */   private static final Logger log = Logger.getLogger(WindowsLanguageMap.class);
/*    */   
/*    */   private static final String RESOURCE_NAME = "win_lang_mapping";
/*    */   
/* 21 */   private static WindowsLanguageMap instance = null;
/*    */ 
/*    */   
/*    */   private static final String DEFAULT = "default";
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized WindowsLanguageMap getInstance() {
/* 29 */     if (instance == null) {
/* 30 */       instance = new WindowsLanguageMap();
/*    */     }
/* 32 */     return instance;
/*    */   }
/*    */   
/*    */   private Locale shortenLocale(Locale locale) {
/* 36 */     if (locale.getVariant() != null && locale.getVariant().length() > 0)
/* 37 */       return new Locale(locale.getLanguage(), locale.getCountry()); 
/* 38 */     if (locale.getCountry() != null && locale.getCountry().length() > 0) {
/* 39 */       return new Locale(locale.getLanguage(), "");
/*    */     }
/* 41 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   private String getWinLanguage(String locale) {
/* 46 */     String result = null;
/*    */     try {
/* 48 */       ResourceBundle resource = ResourceBundle.getBundle("win_lang_mapping");
/* 49 */       result = resource.getString(locale);
/*    */     }
/* 51 */     catch (Exception e) {
/* 52 */       log.warn("unable to retrieve language, returning null - exception: " + e, e);
/*    */     } 
/* 54 */     return result;
/*    */   }
/*    */   
/*    */   public synchronized String get(Locale locale) {
/* 58 */     String retValue = null;
/*    */     
/* 60 */     retValue = getWinLanguage((locale != null) ? locale.toString() : "default");
/* 61 */     while (retValue == null && locale != null) {
/* 62 */       locale = shortenLocale(locale);
/* 63 */       retValue = getWinLanguage((locale != null) ? locale.toString() : "default");
/*    */     } 
/*    */     
/* 66 */     return retValue;
/*    */   }
/*    */   
/*    */   public static Map<Locale, String> getMap() {
/* 70 */     Map<Locale, String> ret = new HashMap<Locale, String>();
/* 71 */     ResourceBundle resource = ResourceBundle.getBundle("win_lang_mapping");
/* 72 */     for (Enumeration<String> keys = resource.getKeys(); keys.hasMoreElements(); ) {
/* 73 */       String key = keys.nextElement();
/* 74 */       String value = resource.getString(key);
/* 75 */       ret.put(Util.parseLocale(key), value);
/*    */     } 
/*    */     
/* 78 */     return ret;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\WindowsLanguageMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */