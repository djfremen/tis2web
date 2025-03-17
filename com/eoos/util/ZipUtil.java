/*    */ package com.eoos.util;
/*    */ 
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.util.zip.GZIPInputStream;
/*    */ import java.util.zip.GZIPOutputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ZipUtil
/*    */ {
/*    */   public static byte[] gzip(byte[] data) throws Exception {
/* 24 */     ByteArrayInputStream bais = new ByteArrayInputStream(data);
/*    */     try {
/* 26 */       ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 27 */       GZIPOutputStream zos = new GZIPOutputStream(baos);
/*    */       try {
/* 29 */         byte[] buffer = new byte[1024];
/* 30 */         int count = 0;
/* 31 */         while ((count = bais.read(buffer)) != -1) {
/* 32 */           zos.write(buffer, 0, count);
/*    */         }
/*    */       } finally {
/* 35 */         zos.close();
/*    */       } 
/*    */       
/* 38 */       return baos.toByteArray();
/*    */     } finally {
/* 40 */       bais.close();
/*    */     } 
/*    */   }
/*    */   
/*    */   public static byte[] gunzip(byte[] data) throws Exception {
/* 45 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 46 */     ByteArrayInputStream bais = new ByteArrayInputStream(data);
/* 47 */     GZIPInputStream zis = new GZIPInputStream(bais);
/*    */     try {
/* 49 */       byte[] buffer = new byte[1024];
/* 50 */       int count = 0;
/* 51 */       while ((count = zis.read(buffer)) != -1) {
/* 52 */         baos.write(buffer, 0, count);
/*    */       }
/*    */     } finally {
/* 55 */       baos.close();
/* 56 */       zis.close();
/*    */     } 
/*    */     
/* 59 */     return baos.toByteArray();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoo\\util\ZipUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */