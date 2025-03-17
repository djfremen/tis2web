/*    */ package com.eoos.gm.tis2web.sas.common.model.exception;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionCause;
/*    */ 
/*    */ public class WriteResponseException
/*    */   extends Exception
/*    */   implements ExceptionCause
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 10 */   private Throwable t = null;
/*    */ 
/*    */   
/*    */   public WriteResponseException() {}
/*    */ 
/*    */   
/*    */   public WriteResponseException(Throwable t) {
/* 17 */     this.t = t;
/*    */   }
/*    */   
/*    */   public Throwable getCause() {
/* 21 */     return this.t;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\common\model\exception\WriteResponseException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */