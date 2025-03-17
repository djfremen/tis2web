/*     */ package com.eoos.mail;
/*     */ 
/*     */ import com.eoos.scsm.v2.io.OutputStreamByteCount;
/*     */ import com.eoos.scsm.v2.io.StreamUtil;
/*     */ import com.eoos.scsm.v2.util.Counter;
/*     */ import com.eoos.scsm.v2.util.ICounter;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.math.BigInteger;
/*     */ import java.security.DigestOutputStream;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.util.Arrays;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class SerializableDataSource
/*     */   implements DataSource, Serializable
/*     */ {
/*  30 */   private static final Logger log = Logger.getLogger(SerializableDataSource.class);
/*     */   
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   private DataSource delegate;
/*     */   
/*     */   public SerializableDataSource(DataSource delegate) {
/*  37 */     this.delegate = delegate;
/*     */   }
/*     */   
/*     */   public String getContentType() {
/*  41 */     return this.delegate.getContentType();
/*     */   }
/*     */   
/*     */   public InputStream getInputStream() throws IOException {
/*  45 */     return this.delegate.getInputStream();
/*     */   }
/*     */   
/*     */   public String getName() {
/*  49 */     return this.delegate.getName();
/*     */   }
/*     */   
/*     */   public OutputStream getOutputStream() throws IOException {
/*  53 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream out) throws IOException {
/*  57 */     out.writeObject(this.delegate.getName());
/*  58 */     out.writeObject(this.delegate.getContentType());
/*     */     
/*  60 */     InputStream is = getInputStream();
/*     */     try {
/*  62 */       Util.StreamMeta meta = Util.getStreamMeta(is);
/*  63 */       out.writeObject(meta.getSize());
/*  64 */       out.writeObject(meta.getHash());
/*     */     } finally {
/*  66 */       StreamUtil.close(is, log);
/*     */     } 
/*     */     
/*  69 */     is = getInputStream();
/*     */     try {
/*  71 */       StreamUtil.transfer(is, out);
/*     */     } finally {
/*  73 */       StreamUtil.close(is, log);
/*     */     } 
/*     */   } private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/*     */     MessageDigest digest;
/*     */     DigestOutputStream digestOutputStream;
/*  78 */     final String name = (String)in.readObject();
/*  79 */     final String contentType = (String)in.readObject();
/*  80 */     BigInteger size = (BigInteger)in.readObject();
/*  81 */     byte[] hash = (byte[])in.readObject();
/*     */     
/*  83 */     BufferedInputStream bis = new BufferedInputStream(in);
/*     */     
/*  85 */     final File tmpFile = Util.createTmpFile();
/*     */     
/*     */     try {
/*  88 */       digest = MessageDigest.getInstance("MD5");
/*  89 */     } catch (NoSuchAlgorithmException e) {
/*  90 */       throw new RuntimeException(e);
/*     */     } 
/*     */ 
/*     */     
/*  94 */     Counter counter = new Counter();
/*  95 */     OutputStreamByteCount outputStreamByteCount = new OutputStreamByteCount(new BufferedOutputStream(new FileOutputStream(tmpFile)), (ICounter)counter);
/*     */     try {
/*  97 */       digestOutputStream = new DigestOutputStream((OutputStream)outputStreamByteCount, digest);
/*  98 */       StreamUtil.transfer(bis, digestOutputStream);
/*     */     } finally {
/* 100 */       StreamUtil.close(digestOutputStream, log);
/*     */     } 
/*     */     
/* 103 */     if (!counter.getCount().equals(size)) {
/* 104 */       throw new IOException("invalid stream length");
/*     */     }
/*     */     
/* 107 */     if (!Arrays.equals(hash, digest.digest())) {
/* 108 */       throw new IOException("corrupted data (invalid digest)");
/*     */     }
/*     */     
/* 111 */     this.delegate = new DataSource()
/*     */       {
/*     */         public String getName() {
/* 114 */           return name;
/*     */         }
/*     */         
/*     */         public InputStream getInputStream() throws IOException {
/* 118 */           return new BufferedInputStream(new FileInputStream(tmpFile));
/*     */         }
/*     */         
/*     */         public String getContentType() {
/* 122 */           return contentType;
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\mail\SerializableDataSource.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */