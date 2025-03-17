/*    */ package com.eoos.util;
/*    */ 
/*    */ import com.eoos.io.StreamUtil;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.zip.ZipEntry;
/*    */ import java.util.zip.ZipOutputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Util
/*    */ {
/*    */   public static boolean equals(Object obj, Object obj2) {
/* 21 */     return (obj == null) ? ((obj2 == null)) : obj.equals(obj2);
/*    */   }
/*    */   
/*    */   public static void rethrow(Throwable t) throws Exception {
/* 25 */     if (t instanceof Exception) {
/* 26 */       throw (Exception)t;
/*    */     }
/* 28 */     throw (Error)t;
/*    */   }
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
/*    */   public static void createZIP(OutputStream os, CZCallback callback) throws IOException {
/* 43 */     ZipOutputStream zos = new ZipOutputStream(os);
/*    */     try {
/* 45 */       zos.setLevel(callback.getLevel());
/* 46 */       for (int i = 0; i < callback.getEntryCount(); i++) {
/* 47 */         InputStream is = callback.getInputStream(i);
/*    */         try {
/* 49 */           ZipEntry entry = new ZipEntry(callback.getName(i));
/* 50 */           zos.putNextEntry(entry);
/* 51 */           StreamUtil.transfer(is, zos);
/*    */         } finally {
/* 53 */           is.close();
/*    */         } 
/*    */       } 
/*    */     } finally {
/* 57 */       zos.close();
/*    */     } 
/*    */   }
/*    */   
/*    */   public static interface CZCallback {
/*    */     int getLevel();
/*    */     
/*    */     int getEntryCount();
/*    */     
/*    */     InputStream getInputStream(int param1Int);
/*    */     
/*    */     String getName(int param1Int);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoo\\util\Util.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */