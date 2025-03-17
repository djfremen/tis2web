/*    */ package com.eoos.gm.tis2web.lt.implementation.io.datamodel;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AWHourFormula
/*    */   extends AWAWFormula
/*    */ {
/*    */   public double calculate(W100000Handler handler, String algoCode, double min, boolean doCorrection) {
/* 21 */     if (algoCode != null && "B".equalsIgnoreCase(algoCode)) {
/* 22 */       double d1 = 1.0D;
/* 23 */       if (min < 0.0D) {
/* 24 */         d1 = -1.0D;
/* 25 */         min = -min;
/*    */       } 
/* 27 */       double d2 = Math.ceil(Math.abs(min) / 6.0D) / 10.0D;
/* 28 */       return d1 * d2;
/*    */     } 
/*    */     
/* 31 */     double aw = super.calculate(handler, algoCode, min, false);
/*    */ 
/*    */     
/* 34 */     double sign = 1.0D;
/*    */     
/* 36 */     if (aw < 0.0D) {
/* 37 */       sign = -1.0D;
/* 38 */       aw = -aw;
/*    */     } 
/*    */     
/* 41 */     aw = aw / 12.0D - 0.01D;
/*    */ 
/*    */     
/* 44 */     double temp = aw * 10.0D - 0.5D;
/* 45 */     temp = Math.ceil(temp);
/* 46 */     temp /= 10.0D * sign;
/*    */ 
/*    */     
/* 49 */     if (doCorrection) {
/* 50 */       double fAddRes = 0.0D;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 59 */       temp += fAddRes;
/*    */     } 
/* 61 */     return temp;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\datamodel\AWHourFormula.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */