/*    */ package com.eoos.gm.tis2web.sas.common.model.exception;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sas.common.model.VIN;
/*    */ 
/*    */ public class InvalidVINException
/*    */   extends Exception {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private VIN vin;
/*    */   
/*    */   public InvalidVINException(VIN vin) {
/* 11 */     this.vin = vin;
/*    */   }
/*    */   
/*    */   public VIN getVIN() {
/* 15 */     return this.vin;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\common\model\exception\InvalidVINException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */