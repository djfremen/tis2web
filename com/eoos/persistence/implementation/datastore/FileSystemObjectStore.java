/*     */ package com.eoos.persistence.implementation.datastore;
/*     */ 
/*     */ import com.eoos.file.FileUtil;
/*     */ import com.eoos.io.StreamUtil;
/*     */ import com.eoos.persistence.ObjectStore;
/*     */ import com.eoos.persistence.StoreException;
/*     */ import com.eoos.persistence.mixin.Content;
/*     */ import com.eoos.persistence.mixin.Existence;
/*     */ import com.eoos.util.AssertUtil;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FileSystemObjectStore
/*     */   implements ObjectStore, Existence, Content
/*     */ {
/*  35 */   private File directory = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FileSystemObjectStore(File directory) {
/*  42 */     this(directory, false);
/*     */   }
/*     */   
/*     */   public FileSystemObjectStore(File directory, boolean create) {
/*  46 */     AssertUtil.ensure(directory, AssertUtil.NOT_NULL);
/*     */     
/*  48 */     if (create) {
/*  49 */       FileUtil.ensureExistence(directory);
/*     */     }
/*     */     
/*  52 */     if (!directory.isDirectory()) {
/*  53 */       throw new IllegalArgumentException("not an existing directory: " + directory.getPath());
/*     */     }
/*  55 */     this.directory = directory;
/*     */   }
/*     */ 
/*     */   
/*     */   public void store(Object identifier, Object data) {
/*     */     try {
/*  61 */       FileOutputStream fos = (FileOutputStream)getOutputStream(identifier);
/*     */       try {
/*  63 */         fos.write((byte[])data);
/*     */       } finally {
/*  65 */         fos.close();
/*     */       } 
/*  67 */     } catch (IOException e) {
/*  68 */       throw new StoreException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void append(Object identifier, byte[] data) throws IOException {
/*  74 */     FileOutputStream fos = (FileOutputStream)getAppendingOutputStream(identifier);
/*     */     try {
/*  76 */       fos.write(data);
/*     */     } finally {
/*  78 */       fos.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Object load(Object identifier) {
/*  84 */     byte[] result = null;
/*     */     
/*  86 */     try { FileInputStream fis = (FileInputStream)getInputStream(identifier);
/*     */       try {
/*  88 */         result = StreamUtil.readFully(fis);
/*  89 */         return result;
/*     */       } finally {
/*     */         
/*  92 */         fis.close();
/*     */       }  }
/*  94 */     catch (FileNotFoundException e) {  }
/*  95 */     catch (IOException e)
/*  96 */     { throw new StoreException(e); }
/*     */     
/*  98 */     return result;
/*     */   }
/*     */   
/*     */   public OutputStream getOutputStream(Object identifier) throws IOException {
/* 102 */     File file = new File(this.directory, String.valueOf(identifier));
/*     */ 
/*     */     
/* 105 */     if (file.exists()) {
/* 106 */       file.delete();
/*     */     } else {
/*     */       
/* 109 */       file.getParentFile().mkdirs();
/* 110 */       file.createNewFile();
/*     */     } 
/*     */     
/* 113 */     FileOutputStream fos = new FileOutputStream(file);
/* 114 */     return fos;
/*     */   }
/*     */   
/*     */   public OutputStream getAppendingOutputStream(Object identifier) throws IOException {
/* 118 */     File file = new File(this.directory, String.valueOf(identifier));
/*     */     
/* 120 */     FileOutputStream fos = new FileOutputStream(file.getCanonicalPath(), true);
/* 121 */     return fos;
/*     */   }
/*     */   
/*     */   public InputStream getInputStream(Object identifier) throws IOException {
/* 125 */     File file = new File(this.directory, String.valueOf(identifier));
/* 126 */     FileInputStream fis = new FileInputStream(file);
/* 127 */     return fis;
/*     */   }
/*     */   
/*     */   public void delete(Object identifier) {
/*     */     try {
/* 132 */       File file = new File(this.directory, String.valueOf(identifier));
/*     */ 
/*     */       
/* 135 */       if (file.exists()) {
/* 136 */         file.delete();
/*     */       }
/*     */     }
/* 139 */     catch (Exception e) {
/* 140 */       throw new StoreException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean exists(Object identifier) {
/*     */     try {
/* 147 */       File file = new File(this.directory, String.valueOf(identifier));
/* 148 */       return file.exists();
/* 149 */     } catch (Exception e) {
/* 150 */       throw new StoreException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Collection getIdentifiers() {
/* 155 */     Collection<String> retValue = new HashSet();
/*     */     try {
/* 157 */       Iterator<File> iter = Arrays.<File>asList(this.directory.listFiles()).iterator();
/* 158 */       while (iter.hasNext()) {
/* 159 */         File _file = iter.next();
/* 160 */         if (_file.isFile()) {
/* 161 */           retValue.add(_file.getName());
/*     */         }
/*     */       } 
/* 164 */       return retValue;
/* 165 */     } catch (Exception e) {
/* 166 */       throw new StoreException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public long getSize() {
/*     */     try {
/* 173 */       long size = 0L;
/* 174 */       Iterator<File> iter = Arrays.<File>asList(this.directory.listFiles()).iterator();
/* 175 */       while (iter.hasNext()) {
/* 176 */         File _file = iter.next();
/* 177 */         if (_file.isFile()) {
/* 178 */           size += _file.length();
/*     */         }
/*     */       } 
/* 181 */       return size;
/* 182 */     } catch (Exception e) {
/* 183 */       throw new StoreException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public List getFiles() {
/* 188 */     List<File> retValue = new LinkedList();
/* 189 */     Iterator<File> iter = Arrays.<File>asList(this.directory.listFiles()).iterator();
/* 190 */     while (iter.hasNext()) {
/* 191 */       File _file = iter.next();
/* 192 */       if (_file.isFile()) {
/* 193 */         retValue.add(_file);
/*     */       }
/*     */     } 
/* 196 */     return retValue;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\persistence\implementation\datastore\FileSystemObjectStore.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */