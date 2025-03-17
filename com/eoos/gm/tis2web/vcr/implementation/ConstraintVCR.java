/*    */ package com.eoos.gm.tis2web.vcr.implementation;
/*    */ 
/*    */ public class ConstraintVCR extends VCRImpl {
/*    */   public ConstraintVCR(VCRFactory factory) {
/*  5 */     super(factory);
/*    */   }
/*    */   
/*    */   protected void checkICR() {
/*  9 */     if (this.icr == null) {
/* 10 */       this.icr = new ICRImpl(this.expressions, false);
/* 11 */       this.expressions = null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vcr\implementation\ConstraintVCR.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */