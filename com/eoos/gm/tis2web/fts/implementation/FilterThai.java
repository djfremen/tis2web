/*    */ package com.eoos.gm.tis2web.fts.implementation;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.apache.lucene.analysis.Token;
/*    */ import org.apache.lucene.analysis.TokenStream;
/*    */ 
/*    */ public class FilterThai
/*    */   extends FilterTIS {
/*    */   public FilterThai(TokenStream in, FilterTIS.Callback callback) {
/* 10 */     super(in, callback);
/*    */   }
/*    */   
/*    */   public final Token next() throws IOException {
/* 14 */     for (Token token = this.input.next(); token != null; token = this.input.next()) {
/* 15 */       if (accept(token.termText()))
/*    */       {
/* 17 */         return token;
/*    */       }
/*    */     } 
/* 20 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean accept(String token) {
/* 27 */     if (token.length() < 2) {
/* 28 */       return false;
/*    */     }
/*    */     
/* 31 */     for (int i = 0; i < token.length(); i++) {
/*    */       
/* 33 */       char c = token.charAt(i);
/* 34 */       if (i == 0 && !Character.isLetter(c) && c <= 'ÿ')
/* 35 */         return false; 
/* 36 */       if (!Character.isLetter(c) && c <= 'ÿ') {
/* 37 */         return false;
/*    */       }
/*    */     } 
/* 40 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\fts\implementation\FilterThai.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */