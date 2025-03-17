/*    */ package com.eoos.gm.tis2web.frame.implementation.jnlp;
/*    */ 
/*    */ import java.net.URLStreamHandler;
/*    */ import java.net.URLStreamHandlerFactory;
/*    */ 
/*    */ public class DatabaseURLStreamHandlerFactory
/*    */   implements URLStreamHandlerFactory {
/*    */   public URLStreamHandler createURLStreamHandler(String protocol) {
/*  9 */     if ("db".equalsIgnoreCase(protocol)) {
/* 10 */       return new DatabaseURLStreamHandler();
/*    */     }
/* 12 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\jnlp\DatabaseURLStreamHandlerFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */