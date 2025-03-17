/*    */ package com.eoos.gm.tis2web.sas.common.model.reqres;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionCause;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public interface SecurityRequestHandler {
/*    */   SecurityAccessResponse handle(SecurityAccessRequest paramSecurityAccessRequest, String paramString) throws Exception;
/*    */   
/*    */   String toString();
/*    */   
/*    */   public static class Exception
/*    */     extends Exception
/*    */     implements Serializable, ExceptionCause {
/*    */     private static final long serialVersionUID = 1L;
/* 15 */     private Throwable cause = null;
/*    */ 
/*    */     
/*    */     public Exception() {}
/*    */ 
/*    */     
/*    */     public Exception(String s) {
/* 22 */       super(s);
/*    */     }
/*    */     
/*    */     public Exception(Throwable cause) {
/* 26 */       this.cause = cause;
/*    */     }
/*    */     
/*    */     public Throwable getCause() {
/* 30 */       return this.cause;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\common\model\reqres\SecurityRequestHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */