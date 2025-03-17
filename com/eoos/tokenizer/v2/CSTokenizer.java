/*    */ package com.eoos.tokenizer.v2;
/*    */ 
/*    */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*    */ import com.eoos.util.v2.Alphabet;
/*    */ import com.eoos.util.v2.StringUtilities;
/*    */ import java.util.NoSuchElementException;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CSTokenizer
/*    */   implements Tokenizer
/*    */ {
/*    */   private String string;
/*    */   private String delimiter;
/* 15 */   private int index = 0;
/*    */   
/*    */   public CSTokenizer(CharSequence cs, String[] delimiters) {
/* 18 */     this.delimiter = determineDelimiter(cs.toString(), delimiters);
/*    */     
/* 20 */     StringBuffer tmp = StringBufferPool.getThreadInstance().get(cs.toString());
/*    */     
/*    */     try {
/* 23 */       for (int i = 0; i < delimiters.length; i++) {
/* 24 */         String orgDelimiter = delimiters[i];
/* 25 */         StringUtilities.replace(tmp, orgDelimiter, this.delimiter);
/*    */       } 
/* 27 */       this.string = tmp.toString();
/*    */     } finally {
/*    */       
/* 30 */       StringBufferPool.getThreadInstance().free(tmp);
/*    */     } 
/*    */   }
/*    */   
/*    */   public CSTokenizer(CharSequence cs, String delimiter) {
/* 35 */     this(cs, new String[] { delimiter });
/*    */   }
/*    */   
/*    */   private String determineDelimiter(String tmp, String[] originalDelimiters) {
/* 39 */     Alphabet a = Alphabet.alphabetOf("#abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
/*    */ 
/*    */     
/* 42 */     for (int i = 0; i < originalDelimiters.length; i++) {
/* 43 */       Character c = new Character(originalDelimiters[i].charAt(0));
/* 44 */       a = a.remove(c);
/*    */     } 
/*    */     
/* 47 */     if (a.size() == 0) {
/* 48 */       throw new IllegalStateException();
/*    */     }
/* 50 */     String retValue = a.createRandomWord(2);
/* 51 */     while (tmp.indexOf(retValue) != -1) {
/* 52 */       retValue = retValue + a.createRandomWord(1);
/*    */     }
/* 54 */     return retValue;
/*    */   }
/*    */   
/*    */   public void remove() {
/* 58 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public boolean hasNext() {
/* 62 */     return (this.string.indexOf(this.delimiter, this.index) != -1 || this.index < this.string.length());
/*    */   }
/*    */   
/*    */   public Object next() {
/* 66 */     if (hasNext()) {
/* 67 */       Object retValue = null;
/* 68 */       int pastEndIndex = this.string.indexOf(this.delimiter, this.index);
/* 69 */       if (pastEndIndex == -1 && this.index < this.string.length()) {
/* 70 */         pastEndIndex = this.string.length();
/*    */       }
/*    */       
/* 73 */       retValue = this.string.substring(this.index, pastEndIndex);
/* 74 */       this.index = pastEndIndex + this.delimiter.length();
/* 75 */       return retValue;
/*    */     } 
/* 77 */     throw new NoSuchElementException();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\tokenizer\v2\CSTokenizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */