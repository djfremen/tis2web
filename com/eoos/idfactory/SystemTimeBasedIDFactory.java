/*    */ package com.eoos.idfactory;
/*    */ 
/*    */ import com.eoos.instantiation.Singleton;
/*    */ import java.math.BigInteger;
/*    */ 
/*    */ public class SystemTimeBasedIDFactory
/*    */   implements IDFactory, Singleton {
/*  8 */   private static SystemTimeBasedIDFactory instance = null;
/*    */   
/* 10 */   private long lastID = -1L;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized SystemTimeBasedIDFactory getInstance() {
/* 17 */     if (instance == null) {
/* 18 */       instance = new SystemTimeBasedIDFactory();
/*    */     }
/* 20 */     return instance;
/*    */   }
/*    */   
/*    */   public synchronized Object createID() {
/* 24 */     long retValue = System.currentTimeMillis();
/* 25 */     if (retValue <= this.lastID) {
/* 26 */       retValue = this.lastID + 1L;
/*    */     }
/* 28 */     this.lastID = retValue;
/* 29 */     return BigInteger.valueOf(retValue);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\idfactory\SystemTimeBasedIDFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */