/*    */ package com.eoos.gm.tis2web.sps.client.tool.passthru.j2534.device.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.tool.passthru.j2534.device.J2534Error;
/*    */ 
/*    */ public class J2534ErrorImpl
/*    */   implements J2534Error {
/*  7 */   private int errorCode = 0;
/*  8 */   private String errorDescription = null;
/*    */   
/*    */   public J2534ErrorImpl(int errorCode, String errorDescription) {
/* 11 */     this.errorCode = errorCode;
/* 12 */     this.errorDescription = errorDescription;
/*    */   }
/*    */   
/*    */   public int getErrorCode() {
/* 16 */     return this.errorCode;
/*    */   }
/*    */   
/*    */   public String getErrorDescription() {
/* 20 */     return this.errorDescription;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 24 */     StringBuffer buffer = new StringBuffer();
/* 25 */     buffer.append("Error Code: ");
/* 26 */     buffer.append(Integer.toString(this.errorCode) + "\n");
/* 27 */     buffer.append("Error Description: ");
/* 28 */     buffer.append(this.errorDescription);
/* 29 */     return buffer.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\passthru\j2534\device\impl\J2534ErrorImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */