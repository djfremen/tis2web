/*    */ package com.eoos.gm.tis2web.sas.common.model.exception;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionCause;
/*    */ 
/*    */ 
/*    */ public class SalesOrganisationReadException
/*    */   extends Exception
/*    */   implements ExceptionCause
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 11 */   private Throwable t = null;
/*    */ 
/*    */   
/*    */   public SalesOrganisationReadException() {}
/*    */ 
/*    */   
/*    */   public SalesOrganisationReadException(String s) {
/* 18 */     super(s);
/*    */   }
/*    */   
/*    */   public SalesOrganisationReadException(Throwable t) {
/* 22 */     this.t = t;
/*    */   }
/*    */   
/*    */   public Throwable getCause() {
/* 26 */     return this.t;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\common\model\exception\SalesOrganisationReadException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */