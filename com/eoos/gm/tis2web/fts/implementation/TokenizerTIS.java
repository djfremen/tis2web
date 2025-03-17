/*    */ package com.eoos.gm.tis2web.fts.implementation;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.Reader;
/*    */ import org.apache.lucene.analysis.Token;
/*    */ import org.apache.lucene.analysis.Tokenizer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TokenizerTIS
/*    */   extends Tokenizer
/*    */ {
/* 13 */   protected int position = 0;
/*    */   
/*    */   public TokenizerTIS(Reader input) {
/* 16 */     this.input = input;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Token next() throws IOException {
/* 22 */     int start = this.position;
/* 23 */     StringBuffer token = new StringBuffer();
/*    */     
/*    */     while (true) {
/* 26 */       int ch = this.input.read();
/* 27 */       if (ch < 0) {
/* 28 */         return (token.length() > 0) ? new Token(token.toString(), start, this.position) : null;
/*    */       }
/* 30 */       char c = (char)ch;
/* 31 */       if (!Character.isLetterOrDigit(c)) {
/* 32 */         if (token.length() > 0) {
/* 33 */           return new Token(token.toString(), start, this.position);
/*    */         }
/* 35 */         start++;
/*    */       } else {
/* 37 */         if (c == 'ä' || c == 'Ä') {
/* 38 */           token.append('a');
/* 39 */           c = 'e';
/* 40 */         } else if (c == 'ö' || c == 'Ö') {
/* 41 */           token.append('o');
/* 42 */           c = 'e';
/* 43 */         } else if (c == 'ü' || c == 'Ü') {
/* 44 */           token.append('u');
/* 45 */           c = 'e';
/* 46 */         } else if (c == 'ß') {
/* 47 */           token.append('s');
/* 48 */           c = 's';
/*    */         } 
/* 50 */         c = Character.toLowerCase(c);
/* 51 */         token.append(c);
/*    */       } 
/*    */       
/* 54 */       this.position++;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\fts\implementation\TokenizerTIS.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */