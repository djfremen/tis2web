/*    */ package com.eoos.gm.tis2web.fts.implementation;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*    */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public interface IFTS
/*    */ {
/*    */   public static class MaximumExceededException
/*    */     extends Exception
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */   }
/*    */   
/* 15 */   public static final IFTS DUMMY = new IFTS()
/*    */     {
/*    */       public Collection query(LocaleInfo locale, VCR vcr, String sit, String field, String query, int operator) {
/* 18 */         return null;
/*    */       }
/*    */       
/*    */       public Collection query(VCR vcr, String sit, String field, String query, int operator, LocaleInfo locale) {
/* 22 */         return null;
/*    */       }
/*    */     };
/*    */   
/*    */   Collection query(LocaleInfo paramLocaleInfo, VCR paramVCR, String paramString1, String paramString2, String paramString3, int paramInt) throws MaximumExceededException;
/*    */   
/*    */   Collection query(VCR paramVCR, String paramString1, String paramString2, String paramString3, int paramInt, LocaleInfo paramLocaleInfo);
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\fts\implementation\IFTS.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */