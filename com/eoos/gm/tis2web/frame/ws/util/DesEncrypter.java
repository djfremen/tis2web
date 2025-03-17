/*    */ package com.eoos.gm.tis2web.frame.ws.util;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import java.security.InvalidAlgorithmParameterException;
/*    */ import java.security.InvalidKeyException;
/*    */ import java.security.InvalidParameterException;
/*    */ import java.security.NoSuchAlgorithmException;
/*    */ import javax.crypto.BadPaddingException;
/*    */ import javax.crypto.Cipher;
/*    */ import javax.crypto.IllegalBlockSizeException;
/*    */ import javax.crypto.NoSuchPaddingException;
/*    */ import javax.crypto.spec.IvParameterSpec;
/*    */ import javax.crypto.spec.SecretKeySpec;
/*    */ import sun.misc.BASE64Decoder;
/*    */ import sun.misc.BASE64Encoder;
/*    */ 
/*    */ public class DesEncrypter
/*    */ {
/*    */   private Cipher encrypter;
/*    */   private Cipher decrypter;
/* 22 */   private byte[] key = new byte[] { 49, 50, 51, 52, 53, 54, 55, 56 };
/* 23 */   private byte[] iv = new byte[] { 49, 50, 51, 52, 53, 54, 55, 56 };
/*    */   
/*    */   public DesEncrypter() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException {
/* 26 */     initialize();
/*    */   }
/*    */   
/*    */   public DesEncrypter(byte[] key, byte[] iv) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException {
/* 30 */     if (key.length < 8) {
/* 31 */       throw new InvalidParameterException();
/*    */     }
/* 33 */     if (iv.length < 8) {
/* 34 */       throw new InvalidParameterException();
/*    */     }
/* 36 */     this.key = key;
/* 37 */     this.iv = iv;
/*    */     
/* 39 */     initialize();
/*    */   }
/*    */   
/*    */   private void initialize() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
/* 43 */     this.encrypter = Cipher.getInstance("DES/CBC/PKCS5Padding");
/* 44 */     this.encrypter.init(1, new SecretKeySpec(this.key, "DES"), new IvParameterSpec(this.iv));
/*    */     
/* 46 */     this.decrypter = Cipher.getInstance("DES/CBC/PKCS5Padding");
/* 47 */     this.decrypter.init(2, new SecretKeySpec(this.key, "DES"), new IvParameterSpec(this.iv));
/*    */   }
/*    */   
/*    */   public String encrypt(String str) throws UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
/* 51 */     byte[] utf8 = str.getBytes("UTF8");
/* 52 */     byte[] enc = this.encrypter.doFinal(utf8);
/* 53 */     return (new BASE64Encoder()).encode(enc);
/*    */   }
/*    */   
/*    */   public String decrypt(String str) throws IOException, IllegalBlockSizeException, BadPaddingException {
/* 57 */     byte[] dec = (new BASE64Decoder()).decodeBuffer(str);
/* 58 */     byte[] utf8 = this.decrypter.doFinal(dec);
/* 59 */     return new String(utf8, "UTF8");
/*    */   }
/*    */   
/*    */   public static void main(String[] args) {
/*    */     try {
/* 64 */       DesEncrypter encrypter = null;
/* 65 */       if (args.length == 0) {
/* 66 */         encrypter = new DesEncrypter();
/*    */       }
/* 68 */       else if (args[0] != null && args[0].length() >= 8) {
/* 69 */         byte[] pwd = args[0].substring(0, 8).getBytes();
/* 70 */         encrypter = new DesEncrypter(pwd, pwd);
/*    */       } else {
/* 72 */         System.out.println("Invalid token password (8 characters required).");
/* 73 */         System.exit(-1);
/*    */       } 
/*    */ 
/*    */ 
/*    */       
/* 78 */       String plainString = "Test plain string";
/* 79 */       System.out.println("Plain string: " + plainString);
/*    */       
/* 81 */       String encrypted = encrypter.encrypt(plainString);
/* 82 */       System.out.println("Encrypted string: " + encrypted);
/*    */       
/* 84 */       String decrypted = encrypter.decrypt(encrypted);
/* 85 */       System.out.println("Decrypted string: " + decrypted);
/*    */     }
/* 87 */     catch (Exception e) {
/* 88 */       System.out.println("Exception: " + e);
/* 89 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\w\\util\DesEncrypter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */