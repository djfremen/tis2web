/*    */ package com.eoos.gm.tis2web.frame.dwnld.client.api;
/*    */ 
/*    */ import com.eoos.gm.tis2web.common.Authentication;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Authentication
/*    */   implements Authentication, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String user;
/*    */   private String pwd;
/*    */   
/*    */   public Authentication(String user, String password) {
/* 26 */     this.user = user;
/* 27 */     this.pwd = password;
/*    */   }
/*    */   
/*    */   public String getUser() {
/* 31 */     return this.user;
/*    */   }
/*    */   
/*    */   public String getPassword() {
/* 35 */     return this.pwd;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\client\api\Authentication.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */