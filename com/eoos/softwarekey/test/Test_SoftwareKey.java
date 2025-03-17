/*     */ package com.eoos.softwarekey.test;
/*     */ 
/*     */ import com.eoos.softwarekey.common.SoftwareKey;
/*     */ import com.eoos.softwarekey.impl.SoftwareKeyImpl;
/*     */ import java.util.Locale;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.spec.IvParameterSpec;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Test_SoftwareKey
/*     */ {
/*     */   public static String byteToHex(byte b) {
/*  17 */     char[] hexDigit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
/*  18 */     int high = b >> 4 & 0xF;
/*  19 */     int low = b & 0xF;
/*  20 */     char[] array = { hexDigit[high], hexDigit[low] };
/*  21 */     return new String(array);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String bytesToHex(byte[] b) {
/*  26 */     StringBuffer strbResult = new StringBuffer();
/*  27 */     for (int i = 0; i < b.length; i++) {
/*  28 */       strbResult.append(byteToHex(b[i]));
/*     */     }
/*  30 */     return strbResult.toString();
/*     */   }
/*     */   
/*     */   public static byte hexCharToDigit(char charInput) {
/*  34 */     switch (charInput) {
/*     */       case '0':
/*  36 */         return 0;
/*     */       case '1':
/*  38 */         return 1;
/*     */       case '2':
/*  40 */         return 2;
/*     */       case '3':
/*  42 */         return 3;
/*     */       case '4':
/*  44 */         return 4;
/*     */       case '5':
/*  46 */         return 5;
/*     */       case '6':
/*  48 */         return 6;
/*     */       case '7':
/*  50 */         return 7;
/*     */       case '8':
/*  52 */         return 8;
/*     */       case '9':
/*  54 */         return 9;
/*     */       case 'a':
/*  56 */         return 10;
/*     */       case 'b':
/*  58 */         return 11;
/*     */       case 'c':
/*  60 */         return 12;
/*     */       case 'd':
/*  62 */         return 13;
/*     */       case 'e':
/*  64 */         return 14;
/*     */       case 'f':
/*  66 */         return 15;
/*     */     } 
/*     */     
/*  69 */     throw new IllegalArgumentException("illegal character:" + charInput);
/*     */   }
/*     */ 
/*     */   
/*     */   private static byte hexToByte(char highByte, char lowByte) {
/*  74 */     byte high = hexCharToDigit(highByte);
/*  75 */     byte low = hexCharToDigit(lowByte);
/*     */     
/*  77 */     return (byte)(high * 16 + low);
/*     */   }
/*     */ 
/*     */   
/*     */   public static byte[] hexToBytes(String strBytes) {
/*  82 */     byte[] abyteResult = new byte[strBytes.length() / 2];
/*  83 */     char[] charArray = strBytes.toCharArray();
/*  84 */     byte b = -1;
/*  85 */     int len = strBytes.length();
/*     */     
/*  87 */     for (int i = 0; i < len; i += 2) {
/*  88 */       b = hexToByte(charArray[i], charArray[i + 1]);
/*  89 */       abyteResult[i / 2] = b;
/*     */     } 
/*  91 */     return abyteResult;
/*     */   }
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
/*     */   public static String asHex(byte[] buf) {
/* 104 */     StringBuffer strbuf = new StringBuffer(buf.length * 2);
/*     */ 
/*     */     
/* 107 */     for (int i = 0; i < buf.length; i++) {
/* 108 */       if ((buf[i] & 0xFF) < 16) {
/* 109 */         strbuf.append("0");
/*     */       }
/* 111 */       strbuf.append(Long.toString((buf[i] & 0xFF), 16));
/*     */     } 
/*     */     
/* 114 */     return strbuf.toString().toUpperCase(Locale.ENGLISH);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/* 121 */     System.out.println("Begin test");
/*     */     
/* 123 */     SoftwareKey swk = SoftwareKeyImpl.getInstance();
/*     */     
/*     */     try {
/* 126 */       SecretKeySpec secretKey = null;
/* 127 */       IvParameterSpec initVector = null;
/*     */ 
/*     */ 
/*     */       
/* 131 */       if (args.length > 0 && args[0].compareToIgnoreCase("varSeed") == 0) {
/*     */ 
/*     */         
/* 134 */         swk.initialize(Test_SoftwareKeyCallback.getInstance());
/*     */ 
/*     */ 
/*     */         
/* 138 */         secretKey = new SecretKeySpec(hexToBytes(Test_SoftwareKeyCallback.get_AES_Key().toLowerCase(Locale.ENGLISH)), "AES");
/*     */ 
/*     */ 
/*     */         
/* 142 */         initVector = new IvParameterSpec(hexToBytes(Test_SoftwareKeyCallback.get_AES_IV().toLowerCase(Locale.ENGLISH).toLowerCase(Locale.ENGLISH)));
/*     */       }
/*     */       else {
/*     */         
/* 146 */         swk.initialize(Test_SoftwareKeyConstSeedCallback.getInstance());
/*     */ 
/*     */ 
/*     */         
/* 150 */         secretKey = new SecretKeySpec(hexToBytes(Test_SoftwareKeyConstSeedCallback.get_AES_Key().toLowerCase(Locale.ENGLISH)), "AES");
/*     */ 
/*     */ 
/*     */         
/* 154 */         initVector = new IvParameterSpec(hexToBytes(Test_SoftwareKeyConstSeedCallback.get_AES_IV().toLowerCase(Locale.ENGLISH).toLowerCase(Locale.ENGLISH)));
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 159 */       Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
/* 160 */       cipher.init(2, secretKey, initVector);
/*     */ 
/*     */ 
/*     */       
/* 164 */       String hwid = swk.getHWID();
/* 165 */       System.out.println("HWID ( encoded ): " + hwid);
/*     */ 
/*     */ 
/*     */       
/* 169 */       byte[] encodedHWID = hexToBytes(hwid.toLowerCase(Locale.ENGLISH));
/* 170 */       byte[] decodedHWID = cipher.doFinal(encodedHWID);
/*     */       
/* 172 */       String plainHWID = new String(decodedHWID, 0, decodedHWID.length);
/* 173 */       System.out.println("HWID ( decoded ): " + plainHWID);
/*     */ 
/*     */ 
/*     */       
/* 177 */       boolean valid = swk.isValidHWID(hwid);
/* 178 */       System.out.println("HWID validation ( with a valid HWID ): " + (valid ? "HWID is valid. Test - OK" : "HWID is invalid. Test - FAILED"));
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 183 */         valid = swk.isValidHWID("AAAAAAAAAAAAAAAABBBBBBBBBBBBBBBBCCCCCCCCCCCCCCCCDDDDDDDDDDDDDDDD");
/* 184 */         System.out.println("HWID validation ( with an invalid HWID ): " + (valid ? "HWID is valid. Test - FAILED" : "HWID is invalid. Test - OK"));
/* 185 */       } catch (Exception e) {
/* 186 */         System.out.println("HWID validation ( with an invalid HWID ): HWID is invalid. Test - OK");
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 191 */       String hwInfo = swk.getHWInfo();
/* 192 */       System.out.println("HWInfo ( encoded ): " + hwInfo);
/*     */ 
/*     */ 
/*     */       
/* 196 */       byte[] encodedHWInfo = hexToBytes(hwInfo.toLowerCase(Locale.ENGLISH));
/* 197 */       byte[] decodedHWInfo = cipher.doFinal(encodedHWInfo);
/*     */       
/* 199 */       String plainHWInfo = new String(decodedHWInfo, 0, decodedHWInfo.length);
/* 200 */       System.out.println("HWID ( decoded ): " + plainHWInfo);
/*     */     }
/* 202 */     catch (Exception e) {
/* 203 */       System.out.println("Exception occured: " + e);
/*     */     } 
/*     */     
/* 206 */     System.out.println("End test");
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\softwarekey\test\Test_SoftwareKey.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */