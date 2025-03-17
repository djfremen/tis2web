/*    */ package com.eoos.datatype;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.io.PrintWriter;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ExceptionWrapper
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 12 */   protected Throwable e = null;
/*    */ 
/*    */   
/*    */   public ExceptionWrapper(Throwable e) {
/* 16 */     this.e = e;
/*    */   }
/*    */   
/*    */   public String getLocalizedMessage() {
/* 20 */     return this.e.getLocalizedMessage();
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 24 */     return this.e.getMessage();
/*    */   }
/*    */   
/*    */   public void printStackTrace(PrintStream s) {
/* 28 */     super.printStackTrace(s);
/* 29 */     if (this.e != null) {
/* 30 */       s.println("*** Wrapped Exception Trace - Begin ***");
/* 31 */       this.e.printStackTrace(s);
/* 32 */       s.println("*** Wrapped Exception Trace - End   ***");
/*    */     } 
/*    */   }
/*    */   
/*    */   public void printStackTrace(PrintWriter s) {
/* 37 */     super.printStackTrace(s);
/* 38 */     if (this.e != null) {
/* 39 */       s.println("*** Wrapped Exception Trace - Begin ***");
/* 40 */       this.e.printStackTrace(s);
/* 41 */       s.println("*** Wrapped Exception Trace - End   ***");
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 47 */     return getClass().getName();
/*    */   }
/*    */   
/*    */   public Throwable getWrappedException() {
/* 51 */     return this.e;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\datatype\ExceptionWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */