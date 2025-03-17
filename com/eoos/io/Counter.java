/*    */ package com.eoos.io;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.math.BigInteger;
/*    */ 
/*    */ public class Counter
/*    */   implements Serializable {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private static final long MAX_VALUE = 9223372036854775807L;
/* 10 */   private int overflow = 0;
/*    */   private long counter;
/*    */   
/*    */   public Counter(long initial) {
/* 14 */     this.counter = initial;
/*    */   }
/*    */   
/*    */   public Counter() {
/* 18 */     this(0L);
/*    */   }
/*    */   
/*    */   public synchronized void inc() {
/* 22 */     if (this.counter == Long.MAX_VALUE) {
/* 23 */       this.overflow++;
/* 24 */       this.counter = 0L;
/*    */     } else {
/* 26 */       this.counter++;
/*    */     } 
/*    */   }
/*    */   
/*    */   public synchronized void inc(long value) {
/* 31 */     if (Long.MAX_VALUE - this.counter < value) {
/* 32 */       this.overflow++;
/* 33 */       this.counter = value - Long.MAX_VALUE - this.counter;
/*    */     } else {
/* 35 */       this.counter += value;
/*    */     } 
/*    */   }
/*    */   
/*    */   public synchronized BigInteger getValue() {
/* 40 */     BigInteger result = BigInteger.valueOf(this.counter);
/* 41 */     if (this.overflow > 0) {
/* 42 */       result = result.add(BigInteger.valueOf(this.overflow).multiply(BigInteger.valueOf(Long.MAX_VALUE)));
/*    */     }
/* 44 */     return result;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 48 */     return super.toString() + "[" + getValue() + "]";
/*    */   }
/*    */   
/*    */   public static void main(String[] args) {
/* 52 */     Counter counter = new Counter();
/* 53 */     for (int i = 0; i < 100; i++) {
/* 54 */       counter.inc(10L);
/*    */     }
/* 56 */     System.out.println(counter);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\io\Counter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */