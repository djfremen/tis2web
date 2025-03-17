/*     */ package com.eoos.softwarekey.impl;
/*     */ 
/*     */ import com.eoos.softwarekey.common.SoftwareKey;
/*     */ import com.eoos.softwarekey.common.SoftwareKeyCallback;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SoftwareKeyImpl
/*     */   implements SoftwareKey
/*     */ {
/*     */   private static final String LIBRARY = "SWKBridge";
/*     */   private static final int AES_KEY_LENGTH = 32;
/*  29 */   private static SoftwareKeyImpl instance = null; private native String nativeGetHWID();
/*  30 */   private SoftwareKeyCallback skc = null;
/*     */   
/*     */   public static SoftwareKey getInstance() {
/*  33 */     synchronized (SoftwareKeyImpl.class) {
/*     */       try {
/*  35 */         System.loadLibrary("SWKBridge");
/*  36 */         if (instance == null) {
/*  37 */           instance = new SoftwareKeyImpl();
/*     */         }
/*  39 */       } catch (UnsatisfiedLinkError e) {
/*  40 */         System.out.println("Could not load library" + e);
/*  41 */       } catch (Exception e) {
/*  42 */         System.out.println("Unknown exception" + e);
/*     */       } 
/*  44 */       return instance;
/*     */     } 
/*     */   }
/*     */   private native String nativeGetHWInfo();
/*     */   
/*     */   private native boolean nativeIsValidHWID(String paramString);
/*     */   
/*     */   public void initialize(SoftwareKeyCallback skc) {
/*  52 */     if (skc != null) {
/*  53 */       this.skc = skc;
/*     */     } else {
/*  55 */       throw new IllegalArgumentException("Invalid SWK callback reference: null pointer");
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getHWID() {
/*  60 */     synchronized (SoftwareKeyImpl.class) {
/*  61 */       if (this.skc != null) {
/*  62 */         return nativeGetHWID();
/*     */       }
/*  64 */       throw new IllegalStateException("Invalid SWK callback reference: null pointer");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHWInfo() {
/*  70 */     synchronized (SoftwareKeyImpl.class) {
/*  71 */       if (this.skc != null) {
/*  72 */         return nativeGetHWInfo();
/*     */       }
/*  74 */       throw new IllegalStateException("Invalid SWK callback reference: null pointer");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValidHWID(String hwid) {
/*  80 */     synchronized (SoftwareKeyImpl.class) {
/*  81 */       if (this.skc != null) {
/*  82 */         return nativeIsValidHWID(hwid);
/*     */       }
/*  84 */       throw new IllegalStateException("Invalid SWK callback reference: null pointer");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static String get_AES_IV() {
/*  90 */     synchronized (SoftwareKeyImpl.class) {
/*  91 */       SoftwareKeyImpl swk = (SoftwareKeyImpl)getInstance();
/*  92 */       if (swk.skc != null) {
/*  93 */         StringBuffer iv = new StringBuffer(Long.toHexString(swk.skc.getSoftwareKeySeed()));
/*  94 */         if (iv.length() > 32) {
/*  95 */           iv.delete(32, iv.length());
/*  96 */         } else if (iv.length() < 32) {
/*  97 */           while (iv.length() < 32) {
/*  98 */             iv.append(iv);
/*     */           }
/* 100 */           if (iv.length() > 32) {
/* 101 */             iv.delete(32, iv.length());
/*     */           }
/*     */         } 
/* 104 */         return iv.toString();
/*     */       } 
/* 106 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static String get_AES_Key() {
/* 112 */     synchronized (SoftwareKeyImpl.class) {
/* 113 */       SoftwareKeyImpl swk = (SoftwareKeyImpl)getInstance();
/* 114 */       if (swk.skc != null) {
/* 115 */         StringBuffer key = new StringBuffer(Long.toHexString(swk.skc.getSoftwareKeySeed()));
/* 116 */         key.reverse();
/* 117 */         if (key.length() > 32) {
/* 118 */           key.delete(32, key.length());
/* 119 */         } else if (key.length() < 32) {
/* 120 */           while (key.length() < 32) {
/* 121 */             key.append(key);
/*     */           }
/* 123 */           if (key.length() > 32) {
/* 124 */             key.delete(32, key.length());
/*     */           }
/*     */         } 
/* 127 */         return key.toString();
/*     */       } 
/* 129 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\softwarekey\impl\SoftwareKeyImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */