/*    */ package com.eoos.util;
/*    */ 
/*    */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IDFactoryImpl
/*    */   implements Serializable, IDFactory
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 12 */   protected static final char[] alphabet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
/*    */   
/* 14 */   protected byte[] index = null;
/*    */   
/*    */   protected int size;
/*    */   
/*    */   public IDFactoryImpl(int minimalIdentifierSize) {
/* 19 */     this(minimalIdentifierSize, false);
/*    */   }
/*    */   
/*    */   public IDFactoryImpl(int minimalIdentifierSize, boolean randomInit) {
/* 23 */     this.size = (minimalIdentifierSize < 1) ? 1 : minimalIdentifierSize;
/* 24 */     this.index = new byte[this.size];
/* 25 */     if (randomInit) {
/* 26 */       for (int i = 0; i < this.size; i++) {
/* 27 */         this.index[i] = (byte)(int)Math.round(Math.random() * (alphabet.length - 1));
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected IDFactoryImpl() {}
/*    */   
/*    */   public synchronized void incrementSize(int sizeIncrement) {
/* 36 */     this.size += sizeIncrement;
/* 37 */     this.index = new byte[this.size];
/*    */   }
/*    */   
/*    */   public synchronized String getNextID() {
/* 41 */     String oid = retrieveID();
/* 42 */     incrementID();
/* 43 */     return oid;
/*    */   }
/*    */   
/*    */   private String retrieveID() {
/* 47 */     StringBuffer buffer = StringBufferPool.getThreadInstance().get(this.size);
/*    */     try {
/* 49 */       for (int i = 0; i < this.size; i++) {
/* 50 */         buffer.append(alphabet[this.index[i]]);
/*    */       }
/* 52 */       return buffer.toString();
/*    */     } finally {
/*    */       
/* 55 */       StringBufferPool.getThreadInstance().free(buffer);
/*    */     } 
/*    */   }
/*    */   
/*    */   private void incrementID() {
/* 60 */     incrementIndex(0);
/*    */   }
/*    */ 
/*    */   
/*    */   private void incrementIndex(int indexNumber) {
/* 65 */     if (indexNumber > this.size - 1) {
/*    */       
/* 67 */       this.size++;
/* 68 */       this.index = new byte[this.size];
/* 69 */     } else if (this.index[indexNumber] < alphabet.length - 1) {
/* 70 */       this.index[indexNumber] = (byte)(this.index[indexNumber] + 1);
/*    */     } else {
/* 72 */       this.index[indexNumber] = 0;
/* 73 */       incrementIndex(indexNumber + 1);
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean isUsed(char c) {
/* 78 */     for (int i = 0; i < alphabet.length; i++) {
/* 79 */       if (alphabet[i] == c) {
/* 80 */         return true;
/*    */       }
/*    */     } 
/* 83 */     return false;
/*    */   }
/*    */   
/*    */   public int getAlphabetSize() {
/* 87 */     return alphabet.length;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoo\\util\IDFactoryImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */