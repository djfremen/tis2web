/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VINValue
/*    */   extends ValueAdapter
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public VINValue(String vin) {
/* 11 */     super(vin);
/*    */   }
/*    */   
/*    */   public String getVIN() {
/* 15 */     return (String)getAdaptee();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\VINValue.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */