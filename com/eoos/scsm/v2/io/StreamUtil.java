/*     */ package com.eoos.scsm.v2.io;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.InterruptedIOException;
/*     */ import java.io.LineNumberReader;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintWriter;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import java.util.zip.GZIPOutputStream;
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
/*     */ public class StreamUtil
/*     */ {
/*  38 */   private static final Logger log = Logger.getLogger(StreamUtil.class);
/*     */   
/*  40 */   public static final OutputStream NILOutputStream = new OutputStream()
/*     */     {
/*     */       public void write(byte[] b, int off, int len) throws IOException {}
/*     */ 
/*     */ 
/*     */       
/*     */       public void write(byte[] b) throws IOException {}
/*     */ 
/*     */ 
/*     */       
/*     */       public void write(int b) throws IOException {}
/*     */     };
/*     */ 
/*     */ 
/*     */   
/*  55 */   public static final InputStream EMPTY_InputStream = new InputStream()
/*     */     {
/*     */       public int read() throws IOException
/*     */       {
/*  59 */         return -1;
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkInterruption() throws InterruptedIOException {
/*  68 */     if (Thread.interrupted()) {
/*  69 */       Thread.currentThread().interrupt();
/*  70 */       throw new InterruptedIOException();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void transfer(InputStream is, OutputStream os) throws IOException {
/*  75 */     transfer(is, os, 10240);
/*     */   }
/*     */   
/*     */   public static void transfer(InputStream is, OutputStream os, int bufferSize) throws IOException {
/*  79 */     byte[] buffer = new byte[Math.max(1, bufferSize)];
/*  80 */     if (buffer.length < 500) {
/*  81 */       log.debug("WARNING: Low buffer size : " + buffer.length);
/*     */     }
/*  83 */     int count = 0;
/*     */     try {
/*  85 */       while ((count = is.read(buffer)) != -1) {
/*  86 */         checkInterruption();
/*  87 */         os.write(buffer, 0, count);
/*     */       } 
/*  89 */       os.flush();
/*  90 */     } catch (InterruptedIOException e) {
/*  91 */       log.debug("transfer has been interrupted, rethrowing InterruptedIOException");
/*  92 */       throw e;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static byte[] readFully(InputStream is) throws IOException {
/*  97 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*     */     try {
/*  99 */       transfer(is, baos);
/*     */     } finally {
/* 101 */       baos.close();
/*     */     } 
/* 103 */     return baos.toByteArray();
/*     */   }
/*     */   
/*     */   public static void writeFully(byte[] data, OutputStream os) throws IOException {
/* 107 */     os.write(data);
/*     */   }
/*     */   
/*     */   public static void writeToOutputStream(Object object, OutputStream os) throws IOException {
/* 111 */     if (object instanceof InputStream) {
/* 112 */       transfer((InputStream)object, os);
/* 113 */     } else if (object instanceof byte[]) {
/* 114 */       os.write((byte[])object);
/* 115 */     } else if (object instanceof java.io.Serializable) {
/* 116 */       ObjectOutputStream oos = new ObjectOutputStream(os);
/* 117 */       oos.writeObject(object);
/*     */     } else {
/* 119 */       throw new IllegalArgumentException();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void close(InputStream is, Logger log) {
/*     */     try {
/* 125 */       is.close();
/* 126 */     } catch (Exception e) {
/* 127 */       log.error("unable to close stream, ignoring - exception:" + e, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void close(OutputStream os, Logger log) {
/*     */     try {
/* 134 */       os.flush();
/* 135 */       os.close();
/* 136 */     } catch (Exception e) {
/* 137 */       log.error("unable to close stream, ignoring - exception:" + e, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object readObject(File file) throws Exception {
/* 143 */     ObjectInputStream ois = new ObjectInputStream(new GZIPInputStream(new BufferedInputStream(new FileInputStream(file))));
/*     */     try {
/* 145 */       return ois.readObject();
/*     */     } finally {
/* 147 */       close(ois, log);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void writeObject(File file, Object object) throws Exception {
/* 152 */     ObjectOutputStream oos = new ObjectOutputStream(new GZIPOutputStream(new BufferedOutputStream(new FileOutputStream(file))));
/*     */     try {
/* 154 */       oos.writeObject(object);
/*     */     } finally {
/* 156 */       close(oos, log);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static long sizeOf(InputStream is, OutputStream pushbackStream) throws IOException {
/* 162 */     byte[] buffer = new byte[1024];
/* 163 */     long ret = 0L;
/* 164 */     int count = 0;
/* 165 */     while ((count = is.read(buffer)) != -1) {
/* 166 */       checkInterruption();
/* 167 */       pushbackStream.write(buffer, 0, count);
/* 168 */       ret += count;
/*     */     } 
/* 170 */     pushbackStream.flush();
/* 171 */     return ret;
/*     */   }
/*     */   
/*     */   public static List<String> readTextFile(File file, Charset charset) throws IOException {
/* 175 */     List<String> ret = new LinkedList<String>();
/* 176 */     LineNumberReader lnr = new LineNumberReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(file)), charset));
/*     */     try {
/* 178 */       String line = null;
/* 179 */       while ((line = lnr.readLine()) != null) {
/* 180 */         ret.add(line);
/*     */       }
/*     */     } finally {
/*     */       
/* 184 */       lnr.close();
/*     */     } 
/* 186 */     return ret;
/*     */   }
/*     */   
/*     */   public static void writeTextFile(File file, Charset charset, List<? extends CharSequence> lines) throws IOException {
/* 190 */     PrintWriter pw = new PrintWriter(new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(file)), charset));
/*     */     try {
/* 192 */       for (CharSequence line : lines) {
/* 193 */         pw.println(line);
/*     */       }
/*     */     } finally {
/* 196 */       pw.close();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\io\StreamUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */