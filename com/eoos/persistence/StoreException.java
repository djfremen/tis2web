/*    */ package com.eoos.persistence;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.io.PrintWriter;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StoreException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 12 */   protected Exception e = null;
/*    */ 
/*    */   
/*    */   public StoreException(Exception e) {
/* 16 */     this.e = e;
/*    */   }
/*    */ 
/*    */   
/*    */   public StoreException() {}
/*    */ 
/*    */   
/*    */   public StoreException(String s) {
/* 24 */     super(s);
/*    */   }
/*    */   
/*    */   public String getLocalizedMessage() {
/* 28 */     String retValue = super.getLocalizedMessage();
/* 29 */     if (this.e != null) {
/* 30 */       retValue = retValue + "(" + this.e.getLocalizedMessage() + ")";
/*    */     }
/* 32 */     return retValue;
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 36 */     String retValue = super.getMessage();
/* 37 */     if (this.e != null) {
/* 38 */       retValue = retValue + "(" + this.e.getMessage() + ")";
/*    */     }
/* 40 */     return retValue;
/*    */   }
/*    */   
/*    */   public void printStackTrace() {
/* 44 */     super.printStackTrace();
/* 45 */     if (this.e != null) {
/* 46 */       System.err.println("\n");
/* 47 */       System.err.println("*** Wrapped Exception Trace: ***");
/* 48 */       this.e.printStackTrace();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void printStackTrace(PrintStream s) {
/* 53 */     super.printStackTrace(s);
/* 54 */     if (this.e != null) {
/* 55 */       s.println("\n");
/* 56 */       s.println("*** Wrapped Exception Trace: ***");
/* 57 */       this.e.printStackTrace(s);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void printStackTrace(PrintWriter s) {
/* 62 */     super.printStackTrace(s);
/* 63 */     if (this.e != null) {
/* 64 */       s.println("\n");
/* 65 */       s.println("*** Wrapped Exception Trace: ***");
/* 66 */       this.e.printStackTrace(s);
/*    */     } 
/*    */   }
/*    */   
/*    */   public String toString() {
/* 71 */     String retValue = super.toString();
/* 72 */     if (this.e != null) {
/* 73 */       retValue = retValue + "(" + this.e.toString() + ")";
/*    */     }
/* 75 */     return retValue;
/*    */   }
/*    */   
/*    */   public Exception getWrappedException() {
/* 79 */     return this.e;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\persistence\StoreException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */