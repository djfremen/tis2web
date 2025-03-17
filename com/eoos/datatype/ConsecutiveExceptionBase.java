/*    */ package com.eoos.datatype;
/*    */ 
/*    */ 
/*    */ public class ConsecutiveExceptionBase
/*    */   extends Exception
/*    */   implements ConsecutiveException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*  9 */   private Throwable cause = null;
/*    */ 
/*    */   
/*    */   public ConsecutiveExceptionBase(Throwable cause) {
/* 13 */     this.cause = cause;
/*    */   }
/*    */   
/*    */   public Throwable getCause() {
/* 17 */     return this.cause;
/*    */   }
/*    */   
/*    */   public Throwable getRootCause() {
/* 21 */     Throwable cause = getCause();
/* 22 */     while (cause instanceof ConsecutiveException) {
/* 23 */       cause = ((ConsecutiveException)cause).getCause();
/*    */     }
/* 25 */     return cause;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\datatype\ConsecutiveExceptionBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */