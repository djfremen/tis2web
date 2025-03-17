/*    */ package com.eoos.gm.tis2web.common;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.InetSocketAddress;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MissingAuthenticationException
/*    */   extends IOException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected InetSocketAddress address;
/*    */   private String scheme;
/*    */   private String realm;
/*    */   
/*    */   public MissingAuthenticationException(InetSocketAddress address, String scheme, String realm) {
/* 17 */     this.address = address;
/* 18 */     this.scheme = scheme;
/* 19 */     this.realm = realm;
/*    */   }
/*    */   
/*    */   public InetSocketAddress getAddress() {
/* 23 */     return this.address;
/*    */   }
/*    */   
/*    */   public String getScheme() {
/* 27 */     return this.scheme;
/*    */   }
/*    */   
/*    */   public String getRealm() {
/* 31 */     return this.realm;
/*    */   }
/*    */   
/*    */   public static void rethrow(Exception e) throws MissingAuthenticationException {
/* 35 */     if (e instanceof MissingAuthenticationException)
/* 36 */       throw (MissingAuthenticationException)e; 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\common\MissingAuthenticationException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */