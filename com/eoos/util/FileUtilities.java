/*    */ package com.eoos.util;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileFilter;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import java.net.URI;
/*    */ import java.net.URLDecoder;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FileUtilities
/*    */ {
/*    */   public static StringBuffer readTextResource(ClassLoader classLoader, String name, String encoding) throws ResourceNotFoundException, UnsupportedEncodingException, IOException {
/*    */     InputStreamReader isr;
/* 24 */     StringBuffer strbResult = new StringBuffer();
/*    */     
/* 26 */     InputStream is = classLoader.getResourceAsStream(name);
/* 27 */     if (is == null) {
/* 28 */       throw new ResourceNotFoundException("resource: " + name + " not found");
/*    */     }
/*    */     
/* 31 */     if (encoding == null) {
/* 32 */       isr = new InputStreamReader(is);
/*    */     } else {
/* 34 */       isr = new InputStreamReader(is, encoding);
/*    */     } 
/*    */     
/* 37 */     char[] buffer = new char[(is.available() == 0) ? 1024 : is.available()];
/* 38 */     int count = 0;
/* 39 */     while ((count = isr.read(buffer)) != -1) {
/* 40 */       strbResult.append(buffer, 0, count);
/*    */     }
/* 42 */     isr.close();
/*    */     
/* 44 */     return strbResult;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void getFiles(List<File> files, File dir, boolean recurse, FileFilter filter) throws IOException {
/* 52 */     File[] aFiles = dir.listFiles(filter);
/* 53 */     if (aFiles == null) {
/*    */       return;
/*    */     }
/* 56 */     for (int i = 0; i < aFiles.length; i++) {
/* 57 */       File file = aFiles[i];
/* 58 */       if (file.isFile()) {
/* 59 */         files.add(file);
/* 60 */       } else if (file.isDirectory() && recurse) {
/* 61 */         getFiles(files, file, recurse, filter);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public static File toRelativeFile(File rootDir, File file) {
/* 67 */     URI rootURI = rootDir.toURI();
/* 68 */     URI fileURI = rootURI.relativize(file.toURI());
/*    */     try {
/* 70 */       return new File(URLDecoder.decode(fileURI.toASCIIString(), "utf-8"));
/* 71 */     } catch (UnsupportedEncodingException e) {
/* 72 */       throw new Error(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static List getFiles(File dir, boolean recurse) throws IOException {
/* 77 */     return getFiles(dir, recurse, null);
/*    */   }
/*    */   
/*    */   public static List getFiles(File dir, boolean recurse, FileFilter filter) throws IOException {
/* 81 */     List result = new ArrayList();
/* 82 */     getFiles(result, dir, recurse, filter);
/* 83 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoo\\util\FileUtilities.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */