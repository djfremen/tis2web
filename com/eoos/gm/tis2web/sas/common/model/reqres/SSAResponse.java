/*    */ package com.eoos.gm.tis2web.sas.common.model.reqres;public interface SSAResponse extends SecurityAccessResponse { Integer getDBVersion();
/*    */   
/*    */   Integer getFreeShots();
/*    */   
/*    */   SecurityCodes getSecCodes(VIN paramVIN);
/*    */   
/*    */   boolean isIncomplete();
/*    */   
/*    */   public static interface SecurityCodes {
/* 10 */     public static final SecurityCodes NULL_CODES = new SecurityCodes() {
/*    */         public String getImmobilizerSecurityCode() {
/* 12 */           return null;
/*    */         }
/*    */         
/*    */         public String getInfotainmentSecurityCode() {
/* 16 */           return null;
/*    */         }
/*    */       };
/*    */     
/*    */     String getImmobilizerSecurityCode();
/*    */     
/*    */     String getInfotainmentSecurityCode();
/*    */   } }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\common\model\reqres\SSAResponse.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */