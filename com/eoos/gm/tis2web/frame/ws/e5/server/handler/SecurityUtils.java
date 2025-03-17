/*    */ package com.eoos.gm.tis2web.frame.ws.e5.server.handler;
/*    */ 
/*    */ import java.security.MessageDigest;
/*    */ import javax.xml.ws.handler.soap.SOAPMessageContext;
/*    */ 
/*    */ 
/*    */ public class SecurityUtils
/*    */ {
/*    */   public static final String AUTHORIZED = "security.authorized";
/*    */   public static final String AUTHORIZED_BY = "security.authorized.by";
/*    */   public static final String USER_ID = "security.userid";
/*    */   
/*    */   public static boolean isAuthorized(SOAPMessageContext mc) {
/* 14 */     boolean result = false;
/* 15 */     if (mc.containsKey("security.authorized") && (
/* 16 */       (Boolean)mc.get("security.authorized")).booleanValue()) {
/* 17 */       result = true;
/*    */     }
/*    */     
/* 20 */     return result;
/*    */   }
/*    */   
/*    */   public byte[] getDigest(byte[] bytes, String alg) {
/* 24 */     byte[] result = null;
/*    */     try {
/* 26 */       MessageDigest md = MessageDigest.getInstance(alg);
/* 27 */       md.update(bytes);
/* 28 */       result = md.digest();
/*    */     }
/* 30 */     catch (Exception e) {}
/*    */     
/* 32 */     return result;
/*    */   }
/*    */   
/*    */   public byte[] addByteArrays(byte[] a1, byte[] a2) {
/* 36 */     byte[] result = new byte[a1.length + a2.length];
/* 37 */     int i = 0;
/* 38 */     for (byte b : a1) {
/* 39 */       result[i++] = b;
/*    */     }
/* 41 */     for (byte b : a2) {
/* 42 */       result[i++] = b;
/*    */     }
/* 44 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\e5\server\handler\SecurityUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */