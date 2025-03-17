/*    */ package com.eoos.math;
/*    */ 
/*    */ import java.math.BigDecimal;
/*    */ import java.math.BigInteger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AverageCalculator
/*    */ {
/* 11 */   private BigInteger valueCount = BigInteger.valueOf(0L);
/*    */   
/* 13 */   private BigDecimal lastAverage = BigDecimal.valueOf(0L);
/*    */   
/*    */   public AverageCalculator(int scale) {
/* 16 */     this.lastAverage = BigDecimal.valueOf(0L).setScale(scale);
/*    */   }
/*    */   
/*    */   public synchronized void add(BigDecimal value) {
/* 20 */     this.valueCount = this.valueCount.add(BigInteger.ONE);
/* 21 */     BigDecimal newAverage = this.lastAverage.add(value.subtract(this.lastAverage).divide(new BigDecimal(this.valueCount), 4));
/* 22 */     this.lastAverage = newAverage;
/*    */   }
/*    */   
/*    */   public synchronized BigDecimal getCurrentAverage() {
/* 26 */     return this.lastAverage;
/*    */   }
/*    */   
/*    */   public synchronized BigInteger getValueCount() {
/* 30 */     return this.valueCount;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\math\AverageCalculator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */