/*     */ package com.eoos.scsm.v2.util;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.math.BigInteger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Counter
/*     */   implements ICounter, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private BigInteger backend;
/*  16 */   private long diff = 0L;
/*     */   
/*     */   public Counter(BigInteger value) {
/*  19 */     this.backend = value;
/*     */   }
/*     */   
/*     */   public Counter() {
/*  23 */     this(BigInteger.ZERO);
/*     */   }
/*     */   
/*     */   public void dec() {
/*  27 */     dec(1L);
/*     */   }
/*     */   
/*     */   public void dec(long decrement) {
/*  31 */     if (Long.MIN_VALUE + decrement < this.diff) {
/*  32 */       this.diff -= decrement;
/*     */     } else {
/*  34 */       flush();
/*  35 */       dec(decrement);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void inc() {
/*  40 */     inc(1L);
/*     */   }
/*     */   
/*     */   public void inc(long increment) {
/*  44 */     if (Long.MAX_VALUE - increment > this.diff) {
/*  45 */       this.diff += increment;
/*     */     } else {
/*  47 */       flush();
/*  48 */       inc(increment);
/*     */     } 
/*     */   }
/*     */   
/*     */   public BigInteger getCount() {
/*  53 */     flush();
/*  54 */     return this.backend;
/*     */   }
/*     */   
/*     */   private void flush() {
/*  58 */     if (this.diff != 0L) {
/*  59 */       this.backend = this.backend.add(BigInteger.valueOf(this.diff));
/*  60 */       this.diff = 0L;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static ICounter synchronizedCounter(final ICounter counter) {
/*  65 */     return new ICounter()
/*     */       {
/*     */         public synchronized void inc(long increment) {
/*  68 */           counter.inc(increment);
/*     */         }
/*     */         
/*     */         public synchronized void inc() {
/*  72 */           counter.inc();
/*     */         }
/*     */         
/*     */         public synchronized BigInteger getCount() {
/*  76 */           return counter.getCount();
/*     */         }
/*     */         
/*     */         public synchronized void dec(long decrement) {
/*  80 */           counter.dec(decrement);
/*     */         }
/*     */         
/*     */         public synchronized void dec() {
/*  84 */           counter.dec();
/*     */         }
/*     */         
/*     */         public synchronized void setCount(BigInteger count) {
/*  88 */           counter.setCount(count);
/*     */         }
/*     */         
/*     */         public synchronized int compareTo(Object o) {
/*  92 */           return counter.compareTo((T)o);
/*     */         }
/*     */ 
/*     */         
/*     */         public synchronized boolean equals(Object obj) {
/*  97 */           return counter.equals(obj);
/*     */         }
/*     */         
/*     */         public synchronized int hashCode() {
/* 101 */           return counter.hashCode();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public void setCount(BigInteger count) {
/* 107 */     this.backend = count;
/* 108 */     this.diff = 0L;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 112 */     return getCount().toString();
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 116 */     int ret = Counter.class.hashCode();
/* 117 */     ret = HashCalc.addHashCode(ret, getCount());
/* 118 */     return ret;
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj) {
/* 122 */     if (this == obj)
/* 123 */       return true; 
/* 124 */     if (obj instanceof ICounter) {
/* 125 */       return getCount().equals(((ICounter)obj).getCount());
/*     */     }
/* 127 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(Object other) {
/* 132 */     return getCount().compareTo(((ICounter)other).getCount());
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v\\util\Counter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */