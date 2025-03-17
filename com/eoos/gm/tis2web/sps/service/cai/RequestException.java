/*    */ package com.eoos.gm.tis2web.sps.service.cai;
/*    */ 
/*    */ public class RequestException
/*    */   extends Exception {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Request request;
/*    */   
/*    */   public RequestException(Request request) {
/*  9 */     this.request = request;
/*    */   }
/*    */   
/*    */   public Request getRequest() {
/* 13 */     return this.request;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 17 */     return "RequestException(" + String.valueOf(this.request) + ")";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\service\cai\RequestException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */