/*    */ package com.eoos.scsm.v2.cache;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.BufferedOutputStream;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StreamCache_FileRI
/*    */   implements StreamCache, StreamCache.StreamSize
/*    */ {
/*    */   private File dir;
/*    */   private Callback callback;
/*    */   
/*    */   public StreamCache_FileRI(File dir, Callback callback) {
/* 25 */     if (dir == null || !dir.isDirectory() || !dir.canRead() || !dir.canWrite()) {
/* 26 */       String msg = "";
/* 27 */       if (dir == null) {
/* 28 */         msg = "directory argument is null";
/* 29 */       } else if (!dir.isDirectory()) {
/* 30 */         msg = "directory parameter does not denote a directory";
/* 31 */       } else if (!dir.canRead()) {
/* 32 */         msg = "unable to read from directory";
/* 33 */       } else if (!dir.canWrite()) {
/* 34 */         msg = "unable to write to directory";
/*    */       } 
/* 36 */       throw new IllegalArgumentException(msg);
/*    */     } 
/* 38 */     this.dir = dir;
/* 39 */     this.callback = callback;
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 44 */     if (!Util.deleteDir(this.dir)) {
/* 45 */       throw new IllegalStateException();
/*    */     }
/*    */     
/* 48 */     if (!this.dir.mkdir()) {
/* 49 */       throw new IllegalStateException();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public InputStream lookup(Object key) throws IOException {
/* 55 */     File file = new File(this.dir, this.callback.toFilename(key));
/* 56 */     if (file.exists()) {
/* 57 */       return new BufferedInputStream(new FileInputStream(file));
/*    */     }
/* 59 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public OutputStream getStorageStream(Object key) throws IOException {
/* 65 */     File file = new File(this.dir, this.callback.toFilename(key));
/* 66 */     return new BufferedOutputStream(new FileOutputStream(file));
/*    */   }
/*    */   
/*    */   public long getLength(Object key) throws IOException {
/* 70 */     File file = new File(this.dir, this.callback.toFilename(key));
/* 71 */     if (file.exists()) {
/* 72 */       return file.length();
/*    */     }
/* 74 */     return 0L;
/*    */   }
/*    */   
/*    */   public static interface Callback {
/*    */     String toFilename(Object param1Object);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\cache\StreamCache_FileRI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */