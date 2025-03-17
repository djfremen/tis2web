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
/*    */ public class AWAWFormula
/*    */   extends AWFormula
/*    */ {
/*    */   public double calculate(W100000Handler handler, String algoCode, double min, boolean doCorrection) {
/* 21 */     double sign = 1.0D;
/*    */     
/* 23 */     if (min < 0.0D) {
/* 24 */       sign = -1.0D;
/*    */     }
/* 26 */     if (algoCode != null && "B".equalsIgnoreCase(algoCode)) {
/* 27 */       return sign * Math.ceil(Math.abs(min) / 5.0D);
/*    */     }
/*    */     
/* 30 */     if (Math.abs(min) > 0.99D) {
/* 31 */       double erg = (Math.abs(min) - 0.99D) / 5.0D;
/* 32 */       erg = sign * Math.ceil(erg);
/*    */ 
/*    */       
/* 35 */       if (doCorrection) {
/* 36 */         double fAddRes = 0.0D;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 46 */         erg += fAddRes;
/*    */       } 
/* 48 */       return erg;
/*    */     } 
/*    */ 
/*    */     
/* 52 */     if (doCorrection) {
/*    */       
/* 54 */       double fAddRes = 0.0D;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 63 */       return sign * 1.0D + fAddRes;
/*    */     } 
/*    */     
/* 66 */     return sign * 1.0D;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\datamodel\AWAWFormula.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */