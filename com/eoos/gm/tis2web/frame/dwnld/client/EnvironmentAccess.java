/*    */ package com.eoos.gm.tis2web.frame.dwnld.client;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ 
/*    */ public class EnvironmentAccess
/*    */ {
/*    */   private static String emptyToNull(String string) {
/*  8 */     if (Util.isNullOrEmpty(string)) {
/*  9 */       return null;
/*    */     }
/* 11 */     return string;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String getSessionID() {
/* 16 */     return emptyToNull(System.getProperty("session.id"));
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\client\EnvironmentAccess.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */