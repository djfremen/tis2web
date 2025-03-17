/*    */ package com.eoos.gm.tis2web.fts.implementation;
/*    */ 
/*    */ import java.io.Reader;
/*    */ import org.apache.lucene.analysis.Analyzer;
/*    */ import org.apache.lucene.analysis.TokenStream;
/*    */ import org.apache.lucene.analysis.snowball.SnowballFilter;
/*    */ 
/*    */ 
/*    */ public class DefaultAnalyzer
/*    */   extends Analyzer
/*    */ {
/*    */   private String stemmer;
/*    */   private String tokenizer;
/*    */   private FilterTIS.Callback callback;
/*    */   
/*    */   public DefaultAnalyzer(String stemmer, String tokenizer, FilterTIS.Callback callback) {
/* 17 */     if (stemmer != null && !stemmer.equals("null")) {
/* 18 */       this.stemmer = stemmer;
/*    */     }
/* 20 */     this.tokenizer = tokenizer;
/* 21 */     this.callback = callback;
/*    */   } public TokenStream tokenStream(String fieldName, Reader reader) {
/*    */     FilterTIS filterTIS;
/*    */     SnowballFilter snowballFilter;
/* 25 */     TokenStream result = null;
/* 26 */     if ("TokenizerTIS".equals(this.tokenizer)) {
/* 27 */       TokenizerTIS tokenizerTIS = new TokenizerTIS(reader);
/* 28 */       filterTIS = new FilterTIS((TokenStream)tokenizerTIS, this.callback);
/* 29 */     } else if ("TokenizerCJK".equals(this.tokenizer)) {
/* 30 */       TokenizerCJK tokenizerCJK = new TokenizerCJK(reader);
/* 31 */       filterTIS = new FilterCJK((TokenStream)tokenizerCJK, this.callback);
/* 32 */     } else if ("TokenizerThai".equals(this.tokenizer)) {
/* 33 */       TokenizerThai tokenizerThai = new TokenizerThai(reader);
/* 34 */       filterTIS = new FilterThai((TokenStream)tokenizerThai, this.callback);
/*    */     } 
/* 36 */     if (this.stemmer != null) {
/* 37 */       snowballFilter = new SnowballFilter((TokenStream)filterTIS, this.stemmer);
/*    */     }
/* 39 */     return (TokenStream)snowballFilter;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\fts\implementation\DefaultAnalyzer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */