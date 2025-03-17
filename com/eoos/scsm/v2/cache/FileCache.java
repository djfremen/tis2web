/*     */ package com.eoos.scsm.v2.cache;
/*     */ 
/*     */ import com.eoos.io.StreamUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
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
/*     */ public class FileCache
/*     */   implements Cache
/*     */ {
/*  28 */   private final Object SYNC = new Object();
/*     */   
/*     */   private File dir;
/*     */   
/*     */   private Callback callback;
/*     */   
/*  34 */   private FileAccessObserver observer = null;
/*     */   
/*     */   public FileCache(File dir, Callback callback) {
/*  37 */     if (dir == null || !dir.isDirectory() || !dir.canRead() || !dir.canWrite()) {
/*  38 */       throw new IllegalArgumentException();
/*     */     }
/*  40 */     this.dir = dir;
/*  41 */     this.callback = callback;
/*  42 */     if (callback instanceof FileAccessObserver) {
/*  43 */       this.observer = (FileAccessObserver)callback;
/*     */     }
/*     */   }
/*     */   
/*     */   private void notifyFileAccessObserver(File file) {
/*  48 */     if (this.observer != null) {
/*  49 */       this.observer.onAccess(file);
/*     */     }
/*     */   }
/*     */   
/*     */   public void clear() {
/*  54 */     synchronized (this.SYNC) {
/*  55 */       if (!Util.deleteDir(this.dir)) {
/*  56 */         throw new IllegalStateException();
/*     */       }
/*  58 */       if (!this.dir.mkdir()) {
/*  59 */         throw new IllegalStateException();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object lookup(Object key) {
/*  65 */     byte[] data = null;
/*  66 */     File file = new File(this.dir, this.callback.toFilename(key));
/*  67 */     synchronized (this.SYNC) {
/*  68 */       if (file.canRead()) {
/*     */         try {
/*  70 */           InputStream fis = new BufferedInputStream(new FileInputStream(file));
/*     */           try {
/*  72 */             data = StreamUtil.readFully(fis);
/*     */           } finally {
/*  74 */             fis.close();
/*     */           } 
/*  76 */           notifyFileAccessObserver(file);
/*  77 */         } catch (Exception e) {
/*  78 */           throw new RuntimeException(e);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/*  83 */     return (data != null) ? this.callback.deserialize(data) : null;
/*     */   }
/*     */   
/*     */   public void store(Object key, Object information) {
/*  87 */     synchronized (this.SYNC) {
/*  88 */       File file = new File(this.dir, this.callback.toFilename(key));
/*     */       try {
/*  90 */         OutputStream fos = new BufferedOutputStream(new FileOutputStream(file));
/*     */         
/*     */         try {
/*  93 */           byte[] data = this.callback.serialize(information);
/*  94 */           StreamUtil.writeFully(data, fos);
/*     */         } finally {
/*  96 */           fos.close();
/*     */         } 
/*  98 */         notifyFileAccessObserver(file);
/*  99 */       } catch (Exception e) {
/* 100 */         throw new RuntimeException(e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(Object key) {
/* 108 */     synchronized (this.SYNC) {
/* 109 */       File file = new File(this.dir, this.callback.toFilename(key));
/* 110 */       if (file.exists() && 
/* 111 */         !file.delete())
/* 112 */         throw new IllegalStateException(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static interface FileAccessObserver {
/*     */     void onAccess(File param1File);
/*     */   }
/*     */   
/*     */   public static interface Callback {
/*     */     String toFilename(Object param1Object);
/*     */     
/*     */     byte[] serialize(Object param1Object);
/*     */     
/*     */     Object deserialize(byte[] param1ArrayOfbyte);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\cache\FileCache.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */