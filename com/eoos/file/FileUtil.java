/*     */ package com.eoos.file;
/*     */ 
/*     */ import com.eoos.io.StreamUtil;
/*     */ import com.eoos.util.v2.StringUtilities;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipOutputStream;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FileUtil
/*     */ {
/*  26 */   private static final Logger log = Logger.getLogger(FileUtil.class);
/*     */   
/*     */   public static abstract class ComparatorBase
/*     */     implements Comparator {
/*     */     public int compare(Object o1, Object o2) {
/*     */       try {
/*  32 */         return compareFiles((File)o1, (File)o2);
/*  33 */       } catch (Exception e) {
/*  34 */         FileUtil.log.warn("unable to compare " + String.valueOf(o1) + " and " + String.valueOf(o2) + " - exception:" + e);
/*  35 */         return 0;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected abstract int compareFiles(File param1File1, File param1File2) throws Exception;
/*     */   }
/*     */   
/*  43 */   public static final Comparator COMPARATOR_NAME = new ComparatorBase()
/*     */     {
/*     */       protected int compareFiles(File file1, File file2) throws Exception {
/*  46 */         return file1.getAbsolutePath().compareTo(file2.getAbsolutePath());
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*  51 */   public static final Comparator COMPARATOR_SIZE = new ComparatorBase()
/*     */     {
/*     */       protected int compareFiles(File file1, File file2) throws Exception {
/*  54 */         return Long.valueOf(file1.length() - file2.length()).intValue();
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*  59 */   public static final Comparator COMPARATOR_DATE = new ComparatorBase()
/*     */     {
/*     */       protected int compareFiles(File file1, File file2) throws Exception {
/*  62 */         return Long.valueOf(file1.lastModified() - file2.lastModified()).intValue();
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*  67 */   public static final String LINE_SEPARATOR = System.getProperty("line.separator");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean ensureExistence(File dir) {
/*  74 */     if (dir.exists()) {
/*  75 */       return true;
/*     */     }
/*  77 */     return dir.mkdirs();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void add(File file, ZipOutputStream zos, String prefix) {
/*  82 */     if (file.isDirectory()) {
/*  83 */       if (prefix != null) {
/*  84 */         prefix = prefix + "/" + file.getName();
/*     */       } else {
/*  86 */         prefix = file.getName();
/*     */       } 
/*  88 */       File[] files = file.listFiles();
/*  89 */       if (files != null) {
/*  90 */         for (Iterator<File> iter = Arrays.<File>asList(files).iterator(); iter.hasNext(); ) {
/*  91 */           File _file = iter.next();
/*  92 */           add(_file, zos, prefix);
/*     */         } 
/*     */       }
/*     */     } else {
/*     */       try {
/*  97 */         InputStream fis = new BufferedInputStream(new FileInputStream(file));
/*  98 */         ZipEntry entry = new ZipEntry((prefix != null) ? (prefix + "/" + file.getName()) : file.getName());
/*  99 */         zos.putNextEntry(entry);
/* 100 */         StreamUtil.transfer(fis, zos);
/* 101 */         fis.close();
/* 102 */         log.info("added file:" + file.getAbsolutePath());
/* 103 */       } catch (Exception e) {
/* 104 */         log.error("unable to add file:" + file.getAbsolutePath() + " - error:" + e, e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void moveToZIP(File directory, ZipOutputStream zos) throws Exception {
/* 110 */     add(directory, zos, null);
/* 111 */     zos.closeEntry();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void recursiveDelete(File file, boolean filesOnly) {
/* 116 */     if (file.isFile()) {
/* 117 */       file.delete();
/*     */     } else {
/* 119 */       File[] files = file.listFiles();
/* 120 */       for (int i = 0; i < files.length; i++) {
/* 121 */         recursiveDelete(files[i], filesOnly);
/*     */       }
/* 123 */       if (!filesOnly) {
/* 124 */         file.delete();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean backup(File directory) throws Exception {
/* 130 */     if (directory.exists()) {
/* 131 */       File parentDir = directory.getAbsoluteFile().getParentFile();
/* 132 */       if (parentDir != null) {
/* 133 */         File backupFile = new File(parentDir, directory.getName() + ".bak.zip");
/* 134 */         if (backupFile.exists() && !backupFile.delete()) {
/* 135 */           throw new IOException("unable to delete old backup file");
/*     */         }
/*     */         
/* 138 */         OutputStream fos = new BufferedOutputStream(new FileOutputStream(backupFile));
/* 139 */         ZipOutputStream zos = new ZipOutputStream(fos);
/*     */         try {
/* 141 */           zos.setLevel(9);
/*     */           
/* 143 */           moveToZIP(directory, zos);
/*     */         } finally {
/* 145 */           zos.close();
/*     */         } 
/*     */         
/* 148 */         return true;
/*     */       } 
/* 150 */       throw new IllegalArgumentException("unable to backup a root directory");
/*     */     } 
/*     */     
/* 153 */     return false;
/*     */   }
/*     */   
/*     */   public static void delete(File directory, boolean backup) throws Exception {
/* 157 */     if (directory.exists()) {
/* 158 */       if (backup && 
/* 159 */         !backup(directory)) {
/* 160 */         throw new IOException("unable to backup");
/*     */       }
/*     */       
/* 163 */       recursiveDelete(directory, false);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void clean(File directory, boolean backup) throws Exception {
/* 168 */     if (directory.exists()) {
/* 169 */       if (backup && 
/* 170 */         !backup(directory)) {
/* 171 */         throw new IOException("unable to backup");
/*     */       }
/*     */       
/* 174 */       recursiveDelete(directory, true);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean isEmptyDirectory(File dir) {
/* 179 */     boolean retValue = true;
/* 180 */     if (!dir.isDirectory()) {
/* 181 */       retValue = false;
/*     */     } else {
/* 183 */       File[] files = dir.listFiles();
/* 184 */       for (int i = 0; i < files.length && retValue; i++) {
/* 185 */         retValue = (retValue && isEmptyDirectory(files[i]));
/*     */       }
/*     */     } 
/* 188 */     return retValue;
/*     */   }
/*     */   
/*     */   public static String buildFileName(String parent, String child) {
/* 192 */     File file = new File(parent, child);
/* 193 */     return file.getPath();
/*     */   }
/*     */   
/*     */   public static File relativize(File parent, File file) {
/* 197 */     String path = file.getAbsolutePath();
/* 198 */     path = StringUtilities.replace(path, parent.getAbsolutePath() + File.separatorChar, "");
/* 199 */     return new File(path);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\file\FileUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */