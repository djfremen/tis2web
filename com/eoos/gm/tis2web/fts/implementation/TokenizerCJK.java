/*    */ package com.eoos.gm.tis2web.fts.implementation;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.Reader;
/*    */ import org.apache.lucene.analysis.Token;
/*    */ import org.apache.lucene.analysis.Tokenizer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TokenizerCJK
/*    */   extends Tokenizer
/*    */ {
/* 13 */   protected int position = 0;
/*    */   
/* 15 */   protected char buffered_entity = Character.MIN_VALUE;
/*    */   
/*    */   public TokenizerCJK(Reader input) {
/* 18 */     this.input = input;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Token next() throws IOException {
/* 24 */     int start = this.position;
/* 25 */     StringBuffer token = new StringBuffer();
/*    */     
/* 27 */     if (this.buffered_entity != '\000') {
/* 28 */       token.append(this.buffered_entity);
/* 29 */       this.buffered_entity = Character.MIN_VALUE;
/* 30 */       return new Token(token.toString(), start, this.position++);
/*    */     } 
/*    */     while (true) {
/* 33 */       int ch = this.input.read();
/* 34 */       if (ch < 0) {
/* 35 */         return (token.length() > 0) ? new Token(token.toString(), start, this.position) : null;
/*    */       }
/* 37 */       char c = (char)ch;
/* 38 */       if (ch > 255 && Character.UnicodeBlock.of(c) != Character.UnicodeBlock.GENERAL_PUNCTUATION) {
/* 39 */         if (token.length() > 0) {
/* 40 */           this.buffered_entity = c;
/* 41 */           return new Token(token.toString(), start, this.position);
/*    */         } 
/* 43 */         token.append(c);
/* 44 */         return new Token(token.toString(), this.position, this.position);
/*    */       } 
/*    */       
/* 47 */       if (!Character.isLetterOrDigit(c)) {
/* 48 */         if (token.length() > 0) {
/* 49 */           return new Token(token.toString(), start, this.position);
/*    */         }
/* 51 */         start++;
/*    */       } else {
/* 53 */         c = Character.toLowerCase(c);
/* 54 */         token.append(c);
/*    */       } 
/*    */       
/* 57 */       this.position++;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\fts\implementation\TokenizerCJK.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */