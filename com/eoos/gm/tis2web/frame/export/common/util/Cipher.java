/*    */ package com.eoos.gm.tis2web.frame.export.common.util;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ public class Cipher {
/*    */   public static String encrypt(long seed, String plain) {
/*  7 */     String key = Long.toHexString(seed);
/*  8 */     String klength = Integer.toHexString(key.length());
/*  9 */     return klength + key + transform(1, seed, plain);
/*    */   }
/*    */   
/*    */   public static String decrypt(String cipher) {
/* 13 */     int klength = Integer.parseInt(cipher.substring(0, 1), 16);
/* 14 */     long seed = Long.parseLong(cipher.substring(1, klength + 1), 16);
/* 15 */     return transform(-1, seed, cipher.substring(klength + 1));
/*    */   }
/*    */   
/*    */   protected static char rotate(char c, int key) {
/* 19 */     int res = (c - 32 + key + 126) % 126 + 32;
/* 20 */     return (char)res;
/*    */   }
/*    */   
/*    */   protected static String transform(int direction, long seed, String text) {
/* 24 */     StringBuffer cipher = new StringBuffer(text.length());
/* 25 */     byte[] pad = new byte[text.length()];
/* 26 */     Random rand = new Random(seed);
/* 27 */     rand.nextBytes(pad);
/*    */ 
/*    */     
/* 30 */     for (int i = 0; i < text.length(); i++) {
/* 31 */       char c = text.charAt(i);
/* 32 */       byte p = pad[i];
/* 33 */       c = rotate(c, (direction * (p - 32) + 126) % 126);
/* 34 */       cipher.append(c);
/*    */     } 
/* 36 */     return cipher.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\util\Cipher.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */