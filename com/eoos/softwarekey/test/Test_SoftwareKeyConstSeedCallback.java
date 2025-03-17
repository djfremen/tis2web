/*    */ package com.eoos.softwarekey.test;
/*    */ 
/*    */ import com.eoos.softwarekey.common.SoftwareKeyCallback;
/*    */ 
/*    */ public class Test_SoftwareKeyConstSeedCallback
/*    */   implements SoftwareKeyCallback {
/*    */   private static final int AES_KEY_LENGTH = 32;
/*  8 */   private static Test_SoftwareKeyConstSeedCallback instance = null;
/*    */   
/*    */   public static SoftwareKeyCallback getInstance() {
/* 11 */     if (instance == null) {
/* 12 */       instance = new Test_SoftwareKeyConstSeedCallback();
/*    */     }
/* 14 */     return instance;
/*    */   }
/*    */   
/* 17 */   private long softwareKeySeed = 0L;
/*    */   
/*    */   private Test_SoftwareKeyConstSeedCallback() {
/* 20 */     this.softwareKeySeed = 1234567890L;
/*    */   }
/*    */ 
/*    */   
/*    */   public long getSoftwareKeySeed() {
/* 25 */     return this.softwareKeySeed;
/*    */   }
/*    */   
/*    */   public static String get_AES_IV() {
/* 29 */     StringBuffer iv = new StringBuffer(Long.toHexString(getInstance().getSoftwareKeySeed()));
/* 30 */     if (iv.length() > 32) {
/* 31 */       iv.delete(32, iv.length());
/* 32 */     } else if (iv.length() < 32) {
/* 33 */       while (iv.length() < 32) {
/* 34 */         iv.append(iv);
/*    */       }
/* 36 */       if (iv.length() > 32) {
/* 37 */         iv.delete(32, iv.length());
/*    */       }
/*    */     } 
/* 40 */     return iv.toString();
/*    */   }
/*    */   
/*    */   public static String get_AES_Key() {
/* 44 */     StringBuffer key = new StringBuffer(Long.toHexString(getInstance().getSoftwareKeySeed()));
/* 45 */     key.reverse();
/* 46 */     if (key.length() > 32) {
/* 47 */       key.delete(32, key.length());
/* 48 */     } else if (key.length() < 32) {
/* 49 */       while (key.length() < 32) {
/* 50 */         key.append(key);
/*    */       }
/* 52 */       if (key.length() > 32) {
/* 53 */         key.delete(32, key.length());
/*    */       }
/*    */     } 
/* 56 */     return key.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\softwarekey\test\Test_SoftwareKeyConstSeedCallback.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */