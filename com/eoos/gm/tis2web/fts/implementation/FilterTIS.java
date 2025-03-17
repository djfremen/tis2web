/*     */ package com.eoos.gm.tis2web.fts.implementation;
/*     */ 
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VC;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCRDomain;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCRValue;
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import org.apache.lucene.analysis.Token;
/*     */ import org.apache.lucene.analysis.TokenFilter;
/*     */ import org.apache.lucene.analysis.TokenStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FilterTIS
/*     */   extends TokenFilter
/*     */ {
/*     */   protected Trie trie;
/*     */   protected StringBuffer buffer;
/*     */   protected int maxKeywordLength;
/*     */   protected int minimumTokenLength;
/*     */   
/*     */   public void clear() {
/*  26 */     for (int i = 0; i < this.buffer.capacity(); i++) {
/*  27 */       this.buffer.setCharAt(i, ' ');
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void push(String token) {
/*  33 */     if (token.length() > this.buffer.capacity()) {
/*  34 */       clear();
/*     */     } else {
/*  36 */       int capacity = this.buffer.capacity();
/*  37 */       int length = token.length();
/*  38 */       int pos = length - 1; int i;
/*  39 */       for (i = capacity - 1; i >= length; i--) {
/*  40 */         this.buffer.setCharAt(i, this.buffer.charAt(i - length));
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  47 */       for (i = 0; i < length; i++) {
/*  48 */         this.buffer.setCharAt(pos - i, token.charAt(i));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static String makeKey(String value) {
/*  55 */     value = value.toLowerCase(Locale.ENGLISH);
/*  56 */     StringBuffer key = new StringBuffer();
/*     */     
/*  58 */     for (int i = value.length() - 1; i >= 0; i--) {
/*  59 */       char c = value.charAt(i);
/*  60 */       if (Character.isLetterOrDigit(c)) {
/*  61 */         key.append(c);
/*     */       }
/*     */     } 
/*  64 */     return key.toString();
/*     */   }
/*     */   
/*     */   public static String makeToken(String value) {
/*  68 */     value = value.toLowerCase(Locale.ENGLISH);
/*  69 */     StringBuffer token = new StringBuffer();
/*     */     
/*  71 */     for (int i = 0; i < value.length(); i++) {
/*  72 */       char c = value.charAt(i);
/*  73 */       if (Character.isLetterOrDigit(c)) {
/*  74 */         token.append(c);
/*     */       }
/*     */     } 
/*  77 */     return token.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FilterTIS(TokenStream in, Callback callback) {
/*  87 */     super(in);
/*  88 */     this.minimumTokenLength = callback.getMinimalTokenLength();
/*  89 */     this.trie = new Trie();
/*  90 */     this.maxKeywordLength = 0;
/*  91 */     VCRDomain domain = (VCRDomain)callback.getVC().getDomain("Engine");
/*  92 */     Iterator<VCRValue> engines = domain.getValues().iterator();
/*  93 */     while (engines.hasNext()) {
/*  94 */       VCRValue engine = engines.next();
/*  95 */       String key = makeKey(engine.toString());
/*  96 */       this.maxKeywordLength = Math.max(this.maxKeywordLength, key.length());
/*  97 */       this.trie.insertItem(key, engine);
/*     */     } 
/*  99 */     this.trie.compress();
/* 100 */     this.buffer = new StringBuffer(this.maxKeywordLength);
/* 101 */     this.buffer.setLength(this.maxKeywordLength);
/* 102 */     clear();
/*     */   }
/*     */   
/*     */   public Token next() throws IOException {
/* 106 */     for (Token token = this.input.next(); token != null; token = this.input.next()) {
/* 107 */       if (this.trie == null) {
/* 108 */         return token;
/*     */       }
/* 110 */       push(token.termText());
/* 111 */       Object engine = this.trie.matchElement(this.buffer.toString());
/* 112 */       if (engine != Trie.NO_SUCH_KEY) {
/* 113 */         String labelEngine = makeToken(((VCRValue)engine).toString());
/* 114 */         if (token.termText().startsWith("sit")) {
/* 115 */           String label = makeToken(((VCRValue)engine).toString());
/* 116 */           return new Token(label, token.endOffset() - label.length(), token.endOffset());
/*     */         } 
/* 118 */         if (token.termText().startsWith(labelEngine)) {
/* 119 */           return new Token(labelEngine, token.endOffset() - labelEngine.length(), token.endOffset());
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 124 */         if (accept(token.termText())) {
/* 125 */           return token;
/*     */         }
/* 127 */       } else if (accept(token.termText())) {
/* 128 */         return token;
/*     */       } 
/*     */     } 
/* 131 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean accept(String token) {
/* 136 */     if (token.length() < this.minimumTokenLength) {
/* 137 */       return false;
/*     */     }
/*     */     
/* 140 */     for (int i = 0; i < this.minimumTokenLength; i++) {
/* 141 */       char c = token.charAt(i);
/* 142 */       if (i == 0 && !Character.isLetter(c))
/* 143 */         return false; 
/* 144 */       if (!Character.isLetterOrDigit(c)) {
/* 145 */         return false;
/*     */       }
/*     */     } 
/* 148 */     return true;
/*     */   }
/*     */   
/*     */   public static interface Callback {
/*     */     VC getVC();
/*     */     
/*     */     int getMinimalTokenLength();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\fts\implementation\FilterTIS.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */