/*    */ package com.eoos.scsm.v2.util;
/*    */ 
/*    */ import java.util.Locale;
/*    */ 
/*    */ public interface I18NSupportV2 {
/*    */   public enum Type {
/*  7 */     LABEL, MESSAGE;
/*    */   }
/*    */   
/* 10 */   public static final I18NSupportV2 FALLBACK = new I18NSupportV2()
/*    */     {
/*    */       public String getText(Locale locale, String key, I18NSupportV2.Type type) {
/* 13 */         return key;
/*    */       }
/*    */       
/*    */       public I18NSupportV2.FixedLocale fixLocale(Locale locale) {
/* 17 */         return I18NSupportV2.FixedLocale.FALLBACK;
/*    */       }
/*    */       
/*    */       public I18NSupportV2.FixedKey fixKey(final String key) {
/* 21 */         return new I18NSupportV2.FixedKey()
/*    */           {
/*    */             public String getText(Locale locale, I18NSupportV2.Type type) {
/* 24 */               return key;
/*    */             }
/*    */           };
/*    */       }
/*    */     }; String getText(Locale paramLocale, String paramString, Type paramType);
/*    */   FixedLocale fixLocale(Locale paramLocale);
/*    */   FixedKey fixKey(String paramString);
/* 31 */   public static interface FixedLocale { public static final FixedLocale FALLBACK = new FixedLocale()
/*    */       {
/*    */         public String getText(String key, I18NSupportV2.Type type) {
/* 34 */           return key;
/*    */         }
/*    */       };
/*    */     
/*    */     String getText(String param1String, I18NSupportV2.Type param1Type); }
/*    */ 
/*    */   
/*    */   public static interface FixedKey {
/*    */     String getText(Locale param1Locale, I18NSupportV2.Type param1Type);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v\\util\I18NSupportV2.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */