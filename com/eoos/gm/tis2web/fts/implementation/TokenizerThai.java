/*     */ package com.eoos.gm.tis2web.fts.implementation;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import org.apache.lucene.analysis.Token;
/*     */ import org.apache.lucene.analysis.Tokenizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TokenizerThai
/*     */   extends Tokenizer
/*     */ {
/*  13 */   protected int position = 0;
/*     */   
/*  15 */   protected char buffered_char = Character.MIN_VALUE;
/*     */   
/*  17 */   protected StringBuffer buffer = null;
/*     */   
/*  19 */   protected int buffer_position = 0;
/*     */   
/*     */   protected boolean thai = false;
/*     */   
/*     */   public TokenizerThai(Reader input) {
/*  24 */     this.input = input;
/*     */   }
/*     */   
/*     */   public Token next() throws IOException {
/*  28 */     if (this.thai && this.buffer.length() > 1) {
/*  29 */       return emitThaiBigram();
/*     */     }
/*     */ 
/*     */     
/*  33 */     int start = this.position;
/*  34 */     StringBuffer token = new StringBuffer();
/*  35 */     for (;; this.position++) {
/*  36 */       int ch; if (this.buffered_char == '\000') {
/*  37 */         ch = this.input.read();
/*     */       } else {
/*     */         
/*  40 */         ch = this.buffered_char;
/*  41 */         this.buffered_char = Character.MIN_VALUE;
/*     */       } 
/*  43 */       if (ch < 0) {
/*  44 */         if (this.thai && this.buffer.length() > 1) {
/*     */           
/*  46 */           this.buffered_char = (char)ch;
/*     */           
/*  48 */           return emitThaiBigram();
/*     */         } 
/*  50 */         return (token.length() > 0) ? new Token(token.toString(), start, this.position) : null;
/*     */       } 
/*     */       
/*  53 */       char c = (char)ch;
/*  54 */       if (ch > 255 && Character.UnicodeBlock.of(c) != Character.UnicodeBlock.GENERAL_PUNCTUATION) {
/*  55 */         if (this.thai) {
/*  56 */           this.buffer.append(c);
/*     */         } else {
/*     */           
/*  59 */           String tmp = (token.length() > 0) ? token.toString() : null;
/*  60 */           this.buffer = new StringBuffer();
/*  61 */           this.buffer_position = 0;
/*  62 */           this.buffer.append(c);
/*     */           
/*  64 */           this.thai = true;
/*  65 */           if (tmp != null) {
/*  66 */             return new Token(token.toString(), this.position, this.position);
/*     */           }
/*     */         } 
/*     */       } else {
/*  70 */         if (this.thai && this.buffer.length() > 1) {
/*     */ 
/*     */           
/*  73 */           this.buffered_char = c;
/*  74 */           return emitThaiBigram();
/*     */         } 
/*  76 */         if (!Character.isLetterOrDigit(c)) {
/*  77 */           if (token.length() > 0) {
/*  78 */             return new Token(token.toString(), start, this.position);
/*     */           }
/*  80 */           start++;
/*     */         } else {
/*  82 */           c = Character.toLowerCase(c);
/*  83 */           token.append(c);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public Token emitThaiBigram() {
/*  90 */     int start_position = this.position - this.buffer.length() + this.buffer_position;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  96 */     String bigram = this.buffer.substring(this.buffer_position, this.buffer_position + 2);
/*  97 */     if (++this.buffer_position >= this.buffer.length() - 1) {
/*  98 */       this.thai = false;
/*  99 */       this.buffer = null;
/*     */     } 
/* 101 */     return new Token(bigram, start_position, start_position + 1);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\fts\implementation\TokenizerThai.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */