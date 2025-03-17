/*    */ package com.eoos.gm.tis2web.sas.server.implementation.serverside.handler.saab;
/*    */ import com.eoos.datatype.ExceptionCause;
/*    */ import com.eoos.gm.tis2web.sas.common.model.HardwareKey;
/*    */ import java.util.List;
/*    */ 
/*    */ public interface DatabaseAdapter {
/*    */   Integer getVersion() throws Exception;
/*    */   
/*    */   Integer getFreeShot() throws Exception;
/*    */   
/*    */   List getSecurityCodes(List paramList, HardwareKey paramHardwareKey) throws Exception;
/*    */   
/*    */   public static class Exception extends Exception implements ExceptionCause {
/* 14 */     private Throwable cause = null;
/*    */     
/*    */     private static final long serialVersionUID = 1L;
/*    */     
/*    */     public Exception() {}
/*    */     
/*    */     public Exception(String s) {
/* 21 */       super(s);
/*    */     }
/*    */     
/*    */     public Exception(Throwable cause) {
/* 25 */       this.cause = cause;
/*    */     }
/*    */     
/*    */     public Throwable getCause() {
/* 29 */       return this.cause;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\server\implementation\serverside\handler\saab\DatabaseAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */