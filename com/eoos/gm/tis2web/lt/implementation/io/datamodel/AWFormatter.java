/*    */ package com.eoos.gm.tis2web.lt.implementation.io.datamodel;
/*    */ 
/*    */ import java.text.NumberFormat;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AWFormatter
/*    */ {
/* 16 */   long multiplier = 1L;
/*    */ 
/*    */   
/*    */   public AWFormatter(long multiplier) {
/* 20 */     this.multiplier = multiplier;
/*    */   }
/*    */   
/*    */   public String format(Locale locale, W100000Handler handler, String algoCode, AWFormula usedformula, double awfloat, boolean doCorrection) {
/* 24 */     String strRet = "" + awfloat;
/*    */     
/* 26 */     if (awfloat < 0.0D) {
/*    */       
/* 28 */       awfloat = usedformula.calculate(handler, algoCode, Math.abs(awfloat), doCorrection);
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 33 */       awfloat = -awfloat;
/*    */     } else {
/* 35 */       awfloat = usedformula.calculate(handler, algoCode, awfloat, doCorrection);
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 40 */     if (this.multiplier != 1L) {
/* 41 */       awfloat *= this.multiplier;
/*    */     }
/*    */     
/* 44 */     NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
/* 45 */     nf.setMaximumFractionDigits(1);
/* 46 */     nf.setMinimumFractionDigits(0);
/*    */     
/* 48 */     strRet = nf.format(awfloat);
/* 49 */     if (!(usedformula instanceof AWHourFormula)) {
/* 50 */       strRet = strRet.replace(".", ",");
/*    */     }
/* 52 */     return strRet;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\datamodel\AWFormatter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */