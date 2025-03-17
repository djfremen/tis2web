/*    */ package com.eoos.gm.tis2web.common;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ public class AuthenticationRI
/*    */   implements Authentication, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String user;
/*    */   private String pwd;
/*    */   
/*    */   public AuthenticationRI(String user, String pwd) {
/* 14 */     this.user = user;
/* 15 */     this.pwd = pwd;
/*    */   }
/*    */   
/*    */   public String getPassword() {
/* 19 */     return this.pwd;
/*    */   }
/*    */   
/*    */   public String getUser() {
/* 23 */     return this.user;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\common\AuthenticationRI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */