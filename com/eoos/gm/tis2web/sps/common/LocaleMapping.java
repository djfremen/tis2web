/*    */ package com.eoos.gm.tis2web.sps.common;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LocaleMapping
/*    */ {
/* 15 */   private static Map Locale2WinLang = null;
/*    */   
/*    */   static {
/* 18 */     Locale2WinLang = new HashMap<Object, Object>();
/*    */     
/* 20 */     if (Locale2WinLang != null) {
/* 21 */       Locale2WinLang.put(Locale.SIMPLIFIED_CHINESE, "CHS");
/* 22 */       Locale2WinLang.put(Locale.TRADITIONAL_CHINESE, "CHT");
/* 23 */       Locale2WinLang.put(new Locale("cz", "", ""), "CSY");
/* 24 */       Locale2WinLang.put(new Locale("dk", "", ""), "DAN");
/* 25 */       Locale2WinLang.put(Locale.GERMAN, "DEU");
/* 26 */       Locale2WinLang.put(new Locale("gr", "", ""), "ELL");
/* 27 */       Locale2WinLang.put(Locale.ENGLISH, "ENU");
/* 28 */       Locale2WinLang.put(new Locale("es", "", ""), "ESN");
/* 29 */       Locale2WinLang.put(new Locale("fi", "", ""), "FIN");
/* 30 */       Locale2WinLang.put(Locale.FRENCH, "FRA");
/* 31 */       Locale2WinLang.put(new Locale("hu", "", ""), "HUN");
/* 32 */       Locale2WinLang.put(Locale.ITALIAN, "ITA");
/* 33 */       Locale2WinLang.put(Locale.JAPANESE, "JPN");
/* 34 */       Locale2WinLang.put(Locale.KOREAN, "KOR");
/* 35 */       Locale2WinLang.put(new Locale("nl", "", ""), "NLD");
/* 36 */       Locale2WinLang.put(new Locale("no", "", ""), "NON");
/* 37 */       Locale2WinLang.put(new Locale("pl", "", ""), "PLK");
/* 38 */       Locale2WinLang.put(new Locale("pt", "BR", ""), "PTB");
/* 39 */       Locale2WinLang.put(new Locale("pt", "", ""), "PTG");
/* 40 */       Locale2WinLang.put(new Locale("ru", "", ""), "RUS");
/* 41 */       Locale2WinLang.put(new Locale("se", "", ""), "SVE");
/* 42 */       Locale2WinLang.put(new Locale("th", "", ""), "THA");
/* 43 */       Locale2WinLang.put(new Locale("tr", "", ""), "TRK");
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String getWindowsLanguage(Locale locale) {
/* 51 */     String windowsLanguage = null;
/*    */     
/* 53 */     if (locale != null) {
/* 54 */       windowsLanguage = (String)Locale2WinLang.get(locale);
/*    */     }
/*    */     
/* 57 */     if (windowsLanguage == null) {
/* 58 */       windowsLanguage = "ENU";
/*    */     }
/* 60 */     return windowsLanguage;
/*    */   }
/*    */   
/*    */   public static Locale getLocale(String windowsLanguage) {
/* 64 */     Locale locale = Locale.ENGLISH;
/* 65 */     return locale;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\LocaleMapping.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */