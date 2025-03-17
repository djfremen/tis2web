/*    */ package com.eoos.gm.tis2web.frame.export.common.datatype;
/*    */ 
/*    */ import java.net.InetAddress;
/*    */ import java.net.UnknownHostException;
/*    */ 
/*    */ public class Address
/*    */ {
/*  8 */   private InetAddress addr = null;
/*    */   
/*    */   private boolean isValid = true;
/*    */ 
/*    */   
/*    */   public Address() {
/*    */     try {
/* 15 */       this.addr = InetAddress.getLocalHost();
/* 16 */     } catch (UnknownHostException e) {
/* 17 */       this.isValid = false;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getHostAddress() {
/* 22 */     String result = null;
/* 23 */     if (this.addr != null && this.isValid) {
/* 24 */       result = this.addr.getHostAddress();
/*    */     }
/* 26 */     return result;
/*    */   }
/*    */   
/*    */   public String getHostName() {
/* 30 */     String result = null;
/* 31 */     if (this.addr != null && this.isValid) {
/* 32 */       result = this.addr.getHostName();
/*    */     }
/* 34 */     return result;
/*    */   }
/*    */   
/*    */   public String getCanonicalHostName() {
/* 38 */     String result = null;
/* 39 */     if (this.addr != null && this.isValid) {
/* 40 */       result = this.addr.getCanonicalHostName();
/*    */     }
/* 42 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\datatype\Address.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */