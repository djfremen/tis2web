/*    */ package com.eoos.gm.tis2web.sas.client.req;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sas.common.model.VIN;
/*    */ 
/*    */ 
/*    */ public class VINValidationRequestImpl
/*    */   implements VINValidationRequest
/*    */ {
/*    */   private VIN vin;
/*    */   
/*    */   public VINValidationRequestImpl(VIN vin) {
/* 12 */     this.vin = vin;
/*    */   }
/*    */   
/*    */   public VIN getVIN() {
/* 16 */     return this.vin;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\client\req\VINValidationRequestImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */