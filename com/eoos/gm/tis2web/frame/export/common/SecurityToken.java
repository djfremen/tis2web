/*    */ package com.eoos.gm.tis2web.frame.export.common;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SecurityToken
/*    */ {
/*    */   private ClientContext context;
/* 12 */   private Set tokens = new HashSet();
/*    */   
/*    */   private SecurityToken(ClientContext context) {
/* 15 */     this.context = context;
/*    */   }
/*    */   
/*    */   public static SecurityToken getInstance(ClientContext context) {
/* 19 */     synchronized (context.getLockObject()) {
/* 20 */       SecurityToken instance = (SecurityToken)context.getObject(SecurityToken.class);
/* 21 */       if (instance == null) {
/* 22 */         instance = new SecurityToken(context);
/* 23 */         context.storeObject(SecurityToken.class, instance);
/*    */       } 
/* 25 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String createToken() {
/* 30 */     String token = Util.shuffle(this.context.createID());
/* 31 */     this.tokens.add(token);
/* 32 */     return token;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean checkToken(String token) {
/* 37 */     return this.tokens.contains(token);
/*    */   }
/*    */   
/*    */   public boolean checkAndRemoveToken(String token) {
/* 41 */     return this.tokens.remove(token);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\SecurityToken.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */