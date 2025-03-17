/*    */ package com.eoos.gm.tis2web.fts.implementation;
/*    */ 
/*    */ import org.apache.lucene.analysis.TokenStream;
/*    */ 
/*    */ public class FilterCJK extends FilterTIS {
/*    */   public FilterCJK(TokenStream in, FilterTIS.Callback callback) {
/*  7 */     super(in, callback);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean accept(String token) {
/* 13 */     for (int i = 0; i < token.length(); i++) {
/*    */       
/* 15 */       char c = token.charAt(i);
/* 16 */       if (i == 0 && !Character.isLetter(c) && c <= 'ÿ')
/* 17 */         return false; 
/* 18 */       if (!Character.isLetter(c) && c <= 'ÿ') {
/* 19 */         return false;
/*    */       }
/*    */     } 
/* 22 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\fts\implementation\FilterCJK.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */