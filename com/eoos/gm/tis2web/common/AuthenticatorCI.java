/*    */ package com.eoos.gm.tis2web.common;
/*    */ 
/*    */ import java.net.Authenticator;
/*    */ import java.net.InetSocketAddress;
/*    */ import java.net.PasswordAuthentication;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class AuthenticatorCI
/*    */   extends Authenticator {
/* 10 */   private static final Logger log = Logger.getLogger(AuthenticatorCI.class);
/*    */   private AuthenticationQuery query;
/*    */   
/*    */   public AuthenticatorCI(AuthenticationQuery query) {
/* 14 */     this.query = query;
/*    */   }
/*    */   
/*    */   protected PasswordAuthentication getPasswordAuthentication() {
/* 18 */     log.debug("authentication query for host: " + String.valueOf(getRequestingHost()) + " (scheme: " + String.valueOf(getRequestingScheme()) + ")");
/*    */     
/* 20 */     Authentication auth = this.query.getAuthentication(new InetSocketAddress(getRequestingHost(), getRequestingPort()), getRequestingScheme(), getRequestingPrompt());
/* 21 */     if (auth != null) {
/* 22 */       return new PasswordAuthentication(auth.getUser(), auth.getPassword().toCharArray());
/*    */     }
/* 24 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\common\AuthenticatorCI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */