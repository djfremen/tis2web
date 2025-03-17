/*    */ package com.eoos.gm.tis2web.frame.export.common;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import java.util.MissingResourceException;
/*    */ import java.util.ResourceBundle;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WindowsEncodingMap
/*    */ {
/* 13 */   private static final Logger log = Logger.getLogger(WindowsEncodingMap.class);
/*    */   
/*    */   private static final String RESOURCE_NAME = "windows_encodings";
/*    */   
/* 17 */   private static WindowsEncodingMap instance = null;
/*    */ 
/*    */   
/*    */   private static final String DEFAULT = "default";
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized WindowsEncodingMap getInstance() {
/* 25 */     if (instance == null) {
/* 26 */       instance = new WindowsEncodingMap();
/*    */     }
/* 28 */     return instance;
/*    */   }
/*    */   
/*    */   private Locale shortenLocale(Locale locale) {
/* 32 */     if (locale.getVariant() != null && locale.getVariant().length() > 0)
/* 33 */       return new Locale(locale.getLanguage(), locale.getCountry()); 
/* 34 */     if (locale.getCountry() != null && locale.getCountry().length() > 0) {
/* 35 */       return new Locale(locale.getLanguage(), "");
/*    */     }
/* 37 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   private String getEncoding(String locale) {
/* 42 */     String result = null;
/*    */     try {
/* 44 */       ResourceBundle resource = ResourceBundle.getBundle("windows_encodings");
/* 45 */       result = resource.getString(locale);
/* 46 */     } catch (MissingResourceException e) {
/*    */     
/* 48 */     } catch (Exception e) {
/* 49 */       log.warn("unable to retrieve encoding, returning null - exception: " + e, e);
/*    */     } 
/* 51 */     return result;
/*    */   }
/*    */   
/*    */   public String get(Locale locale) {
/* 55 */     String retValue = null;
/*    */     
/* 57 */     retValue = getEncoding((locale != null) ? locale.toString() : "default");
/* 58 */     while (retValue == null && locale != null) {
/* 59 */       locale = shortenLocale(locale);
/* 60 */       retValue = getEncoding((locale != null) ? locale.toString() : "default");
/*    */     } 
/*    */     
/* 63 */     return retValue;
/*    */   }
/*    */   
/*    */   public static void main(String[] args) {
/* 67 */     Locale[] locales = Locale.getAvailableLocales();
/* 68 */     for (int i = 0; i < locales.length; i++)
/* 69 */       System.out.println(locales[i].toString() + "\t:" + getInstance().get(locales[i])); 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\WindowsEncodingMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */