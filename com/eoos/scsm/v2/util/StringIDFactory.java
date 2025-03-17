/*    */ package com.eoos.scsm.v2.util;
/*    */ 
/*    */ import com.eoos.idfactory.IDFactory;
/*    */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*    */ 
/*    */ public class StringIDFactory
/*    */   implements IDFactory
/*    */ {
/*    */   private String alphabet;
/*    */   private long currentIndex;
/*    */   
/*    */   public StringIDFactory(String alphabet, String currentID) {
/* 13 */     if (!checkAlphabet(alphabet)) {
/* 14 */       throw new IllegalArgumentException();
/*    */     }
/* 16 */     this.alphabet = alphabet;
/* 17 */     if (currentID == null || currentID.length() == 0) {
/* 18 */       this.currentIndex = 0L;
/*    */     } else {
/* 20 */       this.currentIndex = toIndex(currentID);
/*    */     } 
/*    */   }
/*    */   
/*    */   public StringIDFactory(String alphabet) {
/* 25 */     this(alphabet, null);
/*    */   }
/*    */   
/*    */   private static boolean checkAlphabet(String alphabet) {
/* 29 */     if (alphabet != null && alphabet.trim().length() > 0) {
/* 30 */       for (int i = 0; i < alphabet.length(); i++) {
/* 31 */         char c = alphabet.charAt(i);
/* 32 */         if (alphabet.lastIndexOf(c) != i) {
/* 33 */           return false;
/*    */         }
/*    */       } 
/* 36 */       return true;
/*    */     } 
/*    */     
/* 39 */     return false;
/*    */   }
/*    */   
/*    */   private long toLong(char c) {
/* 43 */     return this.alphabet.indexOf(c);
/*    */   }
/*    */   
/*    */   private char toChar(long index) {
/* 47 */     return this.alphabet.charAt((int)index);
/*    */   }
/*    */   
/*    */   private long toIndex(String id) {
/* 51 */     long result = 0L;
/* 52 */     for (int i = 0; i < id.length(); i++) {
/* 53 */       result += toLong(id.charAt(i)) * Math.round(Math.pow(this.alphabet.length(), i));
/*    */     }
/* 55 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   private String toID(long index) {
/* 60 */     StringBuffer ret = StringBufferPool.getThreadInstance().get();
/*    */     try {
/* 62 */       long remaining = index;
/*    */       do {
/* 64 */         ret.append(toChar(remaining % this.alphabet.length()));
/* 65 */       } while ((remaining /= this.alphabet.length()) > 0L);
/* 66 */       return ret.toString();
/*    */     } finally {
/*    */       
/* 69 */       StringBufferPool.getThreadInstance().free(ret);
/*    */     } 
/*    */   }
/*    */   
/*    */   public Object createID() {
/* 74 */     return toID(this.currentIndex++);
/*    */   }
/*    */   
/*    */   public Object createNextID(String currentID) {
/* 78 */     long index = toIndex(currentID);
/* 79 */     index++;
/* 80 */     return toID(index);
/*    */   }
/*    */   
/*    */   public static void main(String[] args) {
/* 84 */     StringIDFactory idf = new StringIDFactory("abcd", null);
/* 85 */     System.out.println(idf.createNextID("dd"));
/* 86 */     System.out.println(idf.toIndex("a"));
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v\\util\StringIDFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */