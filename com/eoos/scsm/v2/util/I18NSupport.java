/*    */ package com.eoos.scsm.v2.util;
/*    */ 
/*    */ import java.util.Locale;
/*    */ 
/*    */ public interface I18NSupport {
/*  6 */   public static final I18NSupport FALLBACK = new I18NSupport()
/*    */     {
/*    */       public String getText(Locale locale, String key) {
/*  9 */         return key;
/*    */       }
/*    */       
/*    */       public I18NSupport.FixedLocale fixLocale(Locale locale) {
/* 13 */         return I18NSupport.FixedLocale.FALLBACK;
/*    */       }
/*    */       
/*    */       public I18NSupport.FixedKey fixKey(final String key) {
/* 17 */         return new I18NSupport.FixedKey()
/*    */           {
/*    */             public String getText(Locale locale) {
/* 20 */               return key;
/*    */             }
/*    */           };
/*    */       }
/*    */     }; String getText(Locale paramLocale, String paramString);
/*    */   FixedLocale fixLocale(Locale paramLocale);
/*    */   FixedKey fixKey(String paramString);
/* 27 */   public static interface FixedLocale { public static final FixedLocale FALLBACK = new FixedLocale()
/*    */       {
/*    */         public String getText(String key) {
/* 30 */           return key;
/*    */         }
/*    */       };
/*    */     
/*    */     String getText(String param1String); }
/*    */ 
/*    */   
/*    */   public static interface FixedKey {
/*    */     String getText(Locale param1Locale);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v\\util\I18NSupport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */