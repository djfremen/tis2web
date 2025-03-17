/*    */ package com.eoos.gm.tis2web.vc.v2.vin;public interface VIN extends Serializable {
/*    */   char getModelyearCode();
/*    */   
/*    */   String getWMI();
/*    */   
/*    */   String toString();
/*    */   
/*    */   public static final class InvalidVINException extends Exception {
/*  9 */     private String vin = null;
/*    */     
/*    */     private static final long serialVersionUID = 1L;
/*    */     
/*    */     public InvalidVINException() {}
/*    */     
/*    */     public InvalidVINException(String vin) {
/* 16 */       this.vin = vin;
/*    */     }
/*    */     
/*    */     public String getVIN() {
/* 20 */       return this.vin;
/*    */     }
/*    */   }
/*    */   
/*    */   public static final class UnsupportedVINException
/*    */     extends Exception
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/* 28 */     private String vin = null;
/*    */ 
/*    */     
/*    */     public UnsupportedVINException() {}
/*    */ 
/*    */     
/*    */     public UnsupportedVINException(String vin) {
/* 35 */       this.vin = vin;
/*    */     }
/*    */     
/*    */     public String getVIN() {
/* 39 */       return this.vin;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\vin\VIN.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */