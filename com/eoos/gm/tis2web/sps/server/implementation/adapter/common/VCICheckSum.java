/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.common;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.IOException;
/*    */ import java.security.MessageDigest;
/*    */ 
/*    */ public class VCICheckSum
/*    */ {
/*    */   public static long CheckSum(String filename) throws Exception {
/* 11 */     FileInputStream fis = null;
/*    */     try {
/* 13 */       File file = new File(filename);
/* 14 */       fis = new FileInputStream(file);
/* 15 */       byte[] buffer = new byte[fis.available()];
/* 16 */       if (fis.read(buffer) != buffer.length) {
/* 17 */         throw new IOException();
/*    */       }
/* 19 */       long checksum = 2148483646L;
/* 20 */       byte[] md5 = MessageDigest.getInstance("MD5").digest(buffer);
/* 21 */       for (int i = 0; i < md5.length; i += 4) {
/* 22 */         long digest = (md5[i + 3] << 24 | (md5[i + 2] & 0xFF) << 16 | (md5[i + 1] & 0xFF) << 8 | md5[i] & 0xFF);
/* 23 */         checksum += (digest < 0L) ? -digest : digest;
/*    */       } 
/* 25 */       return checksum;
/*    */     } finally {
/*    */       try {
/* 28 */         if (fis != null) {
/* 29 */           fis.close();
/*    */         }
/* 31 */       } catch (Exception x) {}
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void main(String[] args) {
/* 37 */     if (args == null || args.length != 1) {
/* 38 */       System.out.println("usage: VCICheckSum <file>");
/*    */     } else {
/*    */       try {
/* 41 */         System.out.println(args[0] + ": " + CheckSum(args[0]));
/* 42 */       } catch (Exception e) {
/* 43 */         System.err.println("VCICheckSum failed: " + e);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\common\VCICheckSum.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */