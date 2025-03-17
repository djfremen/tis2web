/*    */ package com.eoos.util;
/*    */ 
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LocaleTransformer
/*    */ {
/*    */   public static Locale transform(Locale locale) {
/* 21 */     Locale result = null;
/*    */     try {
/* 23 */       if (locale.getVariant() != null && locale.getVariant().length() != 0) {
/* 24 */         result = new Locale(locale.getLanguage(), locale.getCountry());
/* 25 */       } else if (locale.getCountry() != null && locale.getCountry().length() != 0) {
/* 26 */         result = new Locale(locale.getLanguage(), "");
/*    */       } else {
/* 28 */         result = null;
/*    */       } 
/* 30 */     } catch (Exception e) {
/* 31 */       result = null;
/*    */     } 
/* 33 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoo\\util\LocaleTransformer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */