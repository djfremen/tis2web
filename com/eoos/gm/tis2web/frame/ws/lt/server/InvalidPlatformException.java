/*    */ package com.eoos.gm.tis2web.frame.ws.lt.server;
/*    */ 
/*    */ public class InvalidPlatformException
/*    */   extends Exception {
/*    */   private static final long serialVersionUID = 1L;
/*  6 */   private String instType = null;
/*    */   
/*    */   public InvalidPlatformException(String _instType) {
/*  9 */     this.instType = _instType;
/*    */   }
/*    */   
/*    */   public String getInstType() {
/* 13 */     return this.instType;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\lt\server\InvalidPlatformException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */