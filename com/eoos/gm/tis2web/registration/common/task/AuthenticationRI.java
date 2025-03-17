/*    */ package com.eoos.gm.tis2web.registration.common.task;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AuthenticationRI
/*    */   implements RegistrationSTE.Authentication
/*    */ {
/*    */   private String user;
/*    */   private String pwd;
/*    */   
/*    */   public AuthenticationRI(String user, String pwd) {
/* 12 */     this.user = user;
/* 13 */     this.pwd = pwd;
/*    */   }
/*    */   
/*    */   public String getPassword() {
/* 17 */     return this.pwd;
/*    */   }
/*    */   
/*    */   public String getUser() {
/* 21 */     return this.user;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\common\task\AuthenticationRI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */