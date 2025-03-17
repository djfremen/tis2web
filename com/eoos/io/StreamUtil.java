/*     */ package com.eoos.io;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public class StreamUtil
/*     */ {
/*     */   public static void transfer(InputStream is, OutputStream os) throws IOException {
/*  29 */     byte[] buffer = new byte[Math.max(102400, is.available())];
/*  30 */     int count = 0;
/*  31 */     while ((count = is.read(buffer)) != -1) {
/*  32 */       os.write(buffer, 0, count);
/*     */     }
/*  34 */     os.flush();
/*     */   }
/*     */   
/*     */   public static byte[] readFully(InputStream is) throws IOException {
/*  38 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*  39 */     transfer(is, baos);
/*  40 */     baos.close();
/*  41 */     return baos.toByteArray();
/*     */   }
/*     */   
/*     */   public static void writeFully(byte[] data, OutputStream os) throws IOException {
/*  45 */     ByteArrayInputStream bais = new ByteArrayInputStream(data);
/*  46 */     transfer(bais, os);
/*  47 */     bais.close();
/*     */   }
/*     */   
/*     */   public static void writeToOutputStream(Object object, OutputStream os) throws IOException {
/*  51 */     if (object instanceof InputStream) {
/*  52 */       transfer((InputStream)object, os);
/*  53 */     } else if (object instanceof byte[]) {
/*  54 */       os.write((byte[])object);
/*  55 */     } else if (object instanceof java.io.Serializable) {
/*  56 */       ObjectOutputStream oos = new ObjectOutputStream(os);
/*  57 */       oos.writeObject(object);
/*     */     } else {
/*  59 */       throw new IllegalArgumentException();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void close(InputStream is, Logger log) {
/*     */     try {
/*  65 */       is.close();
/*  66 */     } catch (Exception e) {
/*  67 */       log.error("unable to close stream, ignoring - exception:" + e, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void close(OutputStream os, Logger log) {
/*     */     try {
/*  74 */       os.flush();
/*  75 */       os.close();
/*  76 */     } catch (Exception e) {
/*  77 */       log.error("unable to close stream, ignoring - exception:" + e, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Runnable connect(final InputStream is, final OutputStream os, final ExceptionNotification callback) {
/*  87 */     return new Runnable()
/*     */       {
/*     */         public void run() {
/*     */           try {
/*  91 */             StreamUtil.transfer(is, os);
/*  92 */           } catch (IOException e) {
/*  93 */             if (callback != null)
/*  94 */               callback.onException(e); 
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public static interface ExceptionNotification {
/*     */     void onException(Exception param1Exception); }
/*     */   
/*     */   public static Runnable connect(InputStream is, OutputStream os) {
/* 104 */     return connect(is, os, null);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\io\StreamUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */