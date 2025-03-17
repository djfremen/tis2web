/*    */ package com.eoos.scsm.v2.jnlp;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JNLPException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private int code;
/*    */   public static final int COULD_NOT_LOCATE_RESOURCE = 10;
/*    */   public static final int COULD_NOT_LOCATE_REQUESTED_VERSION = 11;
/*    */   public static final int UNSUPPORTED_OS = 20;
/*    */   public static final int UNSUPPORTED_ARCH = 21;
/*    */   public static final int UNSUPPORTED_LOCALE = 22;
/*    */   public static final int UNSUPPORTED_JRE_VERSION = 23;
/*    */   public static final int UNKNOWN = 99;
/*    */   
/*    */   public JNLPException(int code, String message) {
/* 24 */     super(message);
/* 25 */     this.code = code;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCode() {
/* 30 */     return this.code;
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 34 */     return super.getMessage();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\jnlp\JNLPException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */