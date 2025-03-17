/*    */ package com.eoos.gm.tis2web.frame.implementation.jnlp;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.URL;
/*    */ import java.net.URLConnection;
/*    */ import java.net.URLStreamHandler;
/*    */ 
/*    */ public class DatabaseURLStreamHandler
/*    */   extends URLStreamHandler {
/*    */   protected URLConnection openConnection(URL url) throws IOException {
/* 11 */     return new DatabaseResourceURL(url);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\jnlp\DatabaseURLStreamHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */