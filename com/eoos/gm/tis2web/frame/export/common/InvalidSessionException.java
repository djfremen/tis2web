/*    */ package com.eoos.gm.tis2web.frame.export.common;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InvalidSessionException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*  9 */   private String sessionID = null;
/*    */ 
/*    */   
/*    */   public InvalidSessionException(String sessionID) {
/* 13 */     this.sessionID = sessionID;
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 17 */     return ("invalid session " + this.sessionID != null) ? this.sessionID : "";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\InvalidSessionException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */