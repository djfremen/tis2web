/*    */ package com.eoos.gm.tis2web.sas.common.system;
/*    */ import com.eoos.gm.tis2web.sas.common.model.HardwareKey;
/*    */ import com.eoos.gm.tis2web.sas.common.model.SalesOrganisation;
/*    */ 
/*    */ public interface ISASServer extends SecurityRequestHandler {
/*    */   List getTools() throws Exception;
/*    */   
/*    */   boolean allowAccess(SalesOrganisation paramSalesOrganisation) throws Exception;
/*    */   
/*    */   boolean isValid(HardwareKey paramHardwareKey) throws Exception;
/*    */   
/*    */   HardwareKey getHWKReplacement() throws UnprivilegedUserException;
/*    */   
/*    */   String getHardwareID() throws Exception;
/*    */   
/*    */   public static class Exception extends Exception implements ExceptionCause {
/* 17 */     private Throwable cause = null;
/*    */     
/*    */     private static final long serialVersionUID = 1L;
/*    */     
/*    */     public Exception() {}
/*    */     
/*    */     public Exception(Throwable cause) {
/* 24 */       this.cause = cause;
/*    */     }
/*    */     
/*    */     public Exception(String s) {
/* 28 */       super(s);
/*    */     }
/*    */     
/*    */     public Throwable getCause() {
/* 32 */       return this.cause;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\common\system\ISASServer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */