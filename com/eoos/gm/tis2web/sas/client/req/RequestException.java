/*    */ package com.eoos.gm.tis2web.sas.client.req;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RequestException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Request request;
/*    */   
/*    */   public RequestException(Request request) {
/* 12 */     this.request = request;
/*    */   }
/*    */   
/*    */   public Request getRequest() {
/* 16 */     return this.request;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\client\req\RequestException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */