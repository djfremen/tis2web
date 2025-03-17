/*     */ package com.eoos.softwarekey.test;
/*     */ 
/*     */ import com.eoos.gm.tis2web.registration.service.cai.SoftwareKeyException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.Locale;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Test_DecryptFax
/*     */ {
/*  13 */   private String subscriberID = null;
/*     */   
/*     */   private String getSubscriberID() {
/*  16 */     return this.subscriberID;
/*     */   }
/*     */   
/*     */   private void setSubscriberID(String subscriberID) {
/*  20 */     this.subscriberID = subscriberID;
/*     */   }
/*     */   
/*     */   private byte[] toBytes(String hex) {
/*  24 */     byte[] bytes = new byte[hex.length() / 2];
/*  25 */     for (int i = 0; i < hex.length() / 2; i++) {
/*  26 */       bytes[i] = (byte)Integer.parseInt(hex.substring(i * 2, i * 2 + 2), 16);
/*     */     }
/*  28 */     return bytes;
/*     */   }
/*     */   
/*     */   public static String toHex(byte b) {
/*  32 */     Integer I = Integer.valueOf(b << 24 >>> 24);
/*  33 */     int i = I.intValue();
/*  34 */     if (i < 16) {
/*  35 */       return "0" + Integer.toString(i, 16);
/*     */     }
/*  37 */     return Integer.toString(i, 16);
/*     */   }
/*     */   
/*     */   public static String bytesToHex(boolean format, byte[] data, int length) {
/*  41 */     StringBuffer buffer = new StringBuffer();
/*  42 */     for (int i = 0; i < length; i++) {
/*  43 */       buffer.append(toHex(data[i]));
/*  44 */       if (format && (i + 1) % 2 == 0 && i < length - 1) {
/*  45 */         buffer.append("-");
/*     */       }
/*     */     } 
/*  48 */     return buffer.toString().toUpperCase(Locale.ENGLISH);
/*     */   }
/*     */   
/*     */   private static String pad(String value, int length) {
/*  52 */     if (value == null) {
/*  53 */       value = "";
/*     */     }
/*  55 */     StringBuffer buffer = new StringBuffer();
/*  56 */     buffer.append(value);
/*  57 */     while (buffer.length() < length) {
/*  58 */       buffer.append('~');
/*     */     }
/*  60 */     return buffer.toString().substring(0, length);
/*     */   }
/*     */   
/*     */   private static byte[] keyAES(String key) {
/*  64 */     byte[] buffer = null;
/*     */     try {
/*  66 */       buffer = ("##" + pad(key, 10) + "####").getBytes("US-ASCII");
/*  67 */     } catch (UnsupportedEncodingException e) {
/*  68 */       buffer = ("##" + pad(key, 10) + "####").getBytes();
/*     */     } 
/*  70 */     return buffer;
/*     */   }
/*     */   
/*     */   private byte[] decryptAES(byte[] key, byte[] encryption) throws Exception {
/*  74 */     SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
/*  75 */     Cipher cipher = Cipher.getInstance("AES");
/*  76 */     cipher.init(2, skeySpec);
/*  77 */     return cipher.doFinal(encryption);
/*     */   }
/*     */   
/*     */   private byte[] decrypt(String encryption) {
/*     */     try {
/*  82 */       byte[] key = keyAES(getSubscriberID());
/*  83 */       return decryptAES(key, toBytes(encryption));
/*  84 */     } catch (Exception e) {
/*  85 */       System.out.println("Exception: " + e);
/*  86 */       if (!"TIS2WEB".equals(getSubscriberID())) {
/*     */         try {
/*  88 */           byte[] key = keyAES("TIS2WEB");
/*  89 */           return decryptAES(key, toBytes(encryption));
/*  90 */         } catch (Exception x) {
/*  91 */           System.out.println("Exception: " + e);
/*     */         } 
/*     */       }
/*  94 */       throw new SoftwareKeyException();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/* 102 */     System.out.println("Begin test");
/*     */     
/* 104 */     Test_DecryptFax tdf = new Test_DecryptFax();
/*     */     
/* 106 */     tdf.setSubscriberID("R126595162");
/*     */ 
/*     */     
/* 109 */     String payload = "D34D7A8171BC7E02310F4466B4A08A5EE9665CAD28D44A84930E600B1F3B1352";
/*     */     
/*     */     try {
/* 112 */       tdf.decrypt(payload);
/* 113 */     } catch (Exception e) {
/* 114 */       System.out.println("Exception: " + e);
/*     */     } 
/*     */     
/* 117 */     byte[] bytes = tdf.toBytes(payload);
/* 118 */     String hexStrFalse = bytesToHex(false, bytes, bytes.length);
/* 119 */     bytesToHex(true, bytes, bytes.length);
/*     */     
/* 121 */     if (payload.compareTo(hexStrFalse) == 0) {
/* 122 */       System.out.println("Strings are equal");
/*     */     } else {
/* 124 */       System.out.println("Strings are not equal");
/*     */     } 
/*     */     
/* 127 */     System.out.println("End test");
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\softwarekey\test\Test_DecryptFax.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */