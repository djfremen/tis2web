/*    */ package com.eoos.gm.tis2web.frame.export.common;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HttpContext
/*    */ {
/*    */   private String scheme;
/*    */   
/*    */   public HttpContext(ClientContext context) {}
/*    */   
/*    */   public static HttpContext getInstance(ClientContext context) {
/* 12 */     synchronized (context.getLockObject()) {
/* 13 */       HttpContext ret = (HttpContext)context.getObject(HttpContext.class);
/* 14 */       if (ret == null) {
/* 15 */         ret = new HttpContext(context);
/* 16 */         context.storeObject(HttpContext.class, ret);
/*    */       } 
/* 18 */       return ret;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void setRequestScheme(String scheme) {
/* 23 */     this.scheme = scheme;
/*    */   }
/*    */   
/*    */   public String getRequestScheme() {
/* 27 */     return this.scheme;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\HttpContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */