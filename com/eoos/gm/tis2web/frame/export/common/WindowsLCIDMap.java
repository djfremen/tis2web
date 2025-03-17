/*    */ package com.eoos.gm.tis2web.frame.export.common;
/*    */ 
/*    */ import java.util.Enumeration;
/*    */ import java.util.Locale;
/*    */ import java.util.ResourceBundle;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class WindowsLCIDMap
/*    */ {
/*    */   private static final String RESOURCE_NAME = "win_lcid_mapping";
/* 12 */   private static WindowsLCIDMap instance = null;
/*    */   
/*    */   private static final String DEFAULT = "default";
/*    */   
/* 16 */   private static Logger log = Logger.getLogger(WindowsLCIDMap.class);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized WindowsLCIDMap getInstance() {
/* 22 */     if (instance == null) {
/* 23 */       instance = new WindowsLCIDMap();
/*    */     }
/* 25 */     return instance;
/*    */   }
/*    */   
/*    */   private Locale shortenLocale(Locale locale) {
/* 29 */     if (locale.getVariant() != null && locale.getVariant().length() > 0)
/* 30 */       return new Locale(locale.getLanguage(), locale.getCountry()); 
/* 31 */     if (locale.getCountry() != null && locale.getCountry().length() > 0) {
/* 32 */       return new Locale(locale.getLanguage(), "");
/*    */     }
/* 34 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   private String getWinLCID(String locale) {
/* 39 */     String result = null;
/*    */     try {
/* 41 */       ResourceBundle resource = ResourceBundle.getBundle("win_lcid_mapping");
/* 42 */       result = resource.getString(locale);
/*    */     }
/* 44 */     catch (Exception e) {
/* 45 */       log.warn("unable to retrieve language code, returning null - exception: " + e, e);
/*    */     } 
/* 47 */     return result;
/*    */   }
/*    */   
/*    */   public synchronized String get(Locale locale) {
/* 51 */     String retValue = null;
/*    */     
/* 53 */     retValue = getWinLCID((locale != null) ? locale.toString() : "default");
/* 54 */     while (retValue == null && locale != null) {
/* 55 */       locale = shortenLocale(locale);
/* 56 */       retValue = getWinLCID((locale != null) ? locale.toString() : "default");
/*    */     } 
/*    */     
/* 59 */     return retValue;
/*    */   }
/*    */   
/*    */   public synchronized String getLocale(String winLCID) {
/* 63 */     String locale = null;
/*    */     try {
/* 65 */       ResourceBundle resource = ResourceBundle.getBundle("win_lcid_mapping");
/* 66 */       Enumeration<String> enumeration = resource.getKeys();
/* 67 */       while (enumeration.hasMoreElements()) {
/* 68 */         locale = enumeration.nextElement();
/* 69 */         String lcid = resource.getString(locale);
/* 70 */         if (lcid.equalsIgnoreCase(winLCID)) {
/* 71 */           return locale;
/*    */         }
/*    */       }
/*    */     
/* 75 */     } catch (Exception e) {
/* 76 */       log.error("unable to find locale for lcid:'" + winLCID + "' ,exception:" + e.getMessage());
/*    */     } 
/* 78 */     return locale;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\WindowsLCIDMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */