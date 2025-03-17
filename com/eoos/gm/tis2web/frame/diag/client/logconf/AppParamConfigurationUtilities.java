/*     */ package com.eoos.gm.tis2web.frame.diag.client.logconf;
/*     */ 
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileFilter;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import java.util.Vector;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import java.util.zip.Adler32;
/*     */ import java.util.zip.CheckedOutputStream;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipOutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AppParamConfigurationUtilities
/*     */ {
/*     */   static final int _SEQUENTIAL = 0;
/*     */   static final int _RECURSIVE = 1;
/*  33 */   private static Vector listDirTmpRestart = new Vector();
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
/*     */   protected static String getRegexpAsStringFromDosPattern(String pattern, boolean isFilenameWithDot) {
/*  47 */     Pattern p = Pattern.compile("[\\w\\s\\.&-~#]");
/*  48 */     Matcher m = null;
/*  49 */     String retP = "";
/*     */     
/*  51 */     char[] decomp = pattern.trim().toCharArray();
/*  52 */     Vector<String> vPattern = new Vector();
/*  53 */     int lastIndexOfDot = isFilenameWithDot ? pattern.lastIndexOf('.') : pattern.length();
/*  54 */     String extension = pattern.substring(lastIndexOfDot);
/*     */     int i;
/*  56 */     for (i = 0; i < lastIndexOfDot; i++) {
/*  57 */       if (decomp[i] == '*') {
/*  58 */         vPattern.add("[\\w\\s&-~#]*");
/*  59 */       } else if (decomp[i] == '.') {
/*  60 */         vPattern.add("\\.");
/*  61 */       } else if (decomp[i] == '?') {
/*  62 */         vPattern.add(".");
/*     */       } else {
/*  64 */         String tmp = decomp[i] + "";
/*  65 */         m = p.matcher(tmp);
/*  66 */         if (!m.matches()) {
/*  67 */           return "";
/*     */         }
/*  69 */         vPattern.add(tmp);
/*     */       } 
/*     */     } 
/*     */     
/*  73 */     if (isFilenameWithDot) {
/*  74 */       decomp = extension.trim().toCharArray();
/*  75 */       for (i = 0; i < extension.length(); i++) {
/*  76 */         if (decomp[i] == '*') {
/*  77 */           vPattern.add("[\\w\\s&-~#]*");
/*  78 */         } else if (decomp[i] == '.') {
/*  79 */           vPattern.add("\\.");
/*  80 */         } else if (decomp[i] == '?') {
/*  81 */           vPattern.add(".");
/*     */         } else {
/*  83 */           String tmp = decomp[i] + "";
/*  84 */           m = p.matcher(tmp);
/*  85 */           if (!m.matches()) {
/*  86 */             return "";
/*     */           }
/*  88 */           vPattern.add(tmp);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  93 */     for (Iterator<String> iterv = vPattern.iterator(); iterv.hasNext();) {
/*  94 */       retP = retP + iterv.next();
/*     */     }
/*     */     
/*  97 */     return retP;
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final void addToListDirTmpRestart(Object o) {
/* 152 */     listDirTmpRestart.add(o);
/*     */   }
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
/*     */   protected static List listDirectoriesForPattern(String dirName, final String[] regexp, int mode, boolean includeCurrentDirectory) {
/* 169 */     List<String> listFiles = new Vector();
/* 170 */     final String baseDir = dirName;
/* 171 */     File dir = new File(dirName);
/*     */     
/* 173 */     if (!dir.isDirectory()) {
/* 174 */       return null;
/*     */     }
/* 176 */     File[] files = dir.listFiles(new FileFilter() {
/*     */           public boolean accept(File file) {
/* 178 */             if (file.isDirectory()) {
/* 179 */               for (int iterReg = 0; iterReg < regexp.length; iterReg++) {
/* 180 */                 String[] unterv = regexp[iterReg].split("/");
/* 181 */                 if (unterv == null || unterv.length <= 1) {
/* 182 */                   Pattern p = Pattern.compile(regexp[iterReg]);
/* 183 */                   Matcher m = p.matcher(file.getName());
/* 184 */                   if (m.matches()) {
/* 185 */                     return true;
/*     */                   }
/*     */                 } else {
/* 188 */                   Pattern p = Pattern.compile(unterv[0]);
/* 189 */                   Matcher m = p.matcher(file.getName());
/* 190 */                   if (m.matches()) {
/* 191 */                     HashMap<Object, Object> hmpTmp = new HashMap<Object, Object>();
/* 192 */                     hmpTmp.put(baseDir + "\\" + unterv[0], regexp[iterReg].substring(unterv[0].length() + 1));
/* 193 */                     AppParamConfigurationUtilities.addToListDirTmpRestart(hmpTmp);
/* 194 */                     return false;
/*     */                   } 
/*     */                 } 
/*     */               } 
/* 198 */               return false;
/*     */             } 
/* 200 */             return false;
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 205 */     if (files != null) {
/* 206 */       for (int iterFiles = 0; iterFiles < files.length; iterFiles++) {
/* 207 */         if (files[iterFiles].isDirectory()) {
/* 208 */           listFiles.add(files[iterFiles].getAbsolutePath());
/* 209 */           if (mode == 1) {
/* 210 */             listFiles.addAll(listDirectoriesForPattern(files[iterFiles].getAbsolutePath(), regexp, mode, false));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 216 */     if (listDirTmpRestart.size() > 0) {
/* 217 */       for (int iterv = 0; iterv < listDirTmpRestart.size(); ) {
/* 218 */         HashMap hmpNext = listDirTmpRestart.get(0);
/* 219 */         String tmpDir = hmpNext.keySet().iterator().next().toString();
/* 220 */         listDirTmpRestart.remove(0);
/*     */         
/* 222 */         File fTmp = new File(tmpDir + "\\" + (String)hmpNext.get(tmpDir));
/* 223 */         if (fTmp.exists() && fTmp.isDirectory()) {
/*     */           
/* 225 */           String[] tSplit = ((String)hmpNext.get(tmpDir)).split("/");
/* 226 */           String finalDir = tmpDir + "\\";
/* 227 */           for (int iter = 0; iter < tSplit.length - 1; iter++) {
/* 228 */             finalDir = finalDir + tSplit[iter] + "\\";
/*     */           }
/* 230 */           listFiles.addAll(listDirectoriesForPattern(finalDir, new String[] { tSplit[tSplit.length - 1] }, mode, true));
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 235 */     if (includeCurrentDirectory) {
/* 236 */       String currentDirName = null;
/* 237 */       if (dirName.endsWith("\\")) {
/* 238 */         dirName = dirName.substring(dirName.length() - 2);
/*     */       }
/*     */       
/* 241 */       currentDirName = (new File(dirName)).getName();
/* 242 */       for (int iterReg = 0; iterReg < regexp.length; iterReg++) {
/* 243 */         Pattern p = Pattern.compile(regexp[iterReg]);
/* 244 */         Matcher m = p.matcher(currentDirName.toLowerCase());
/* 245 */         if (m.matches()) {
/* 246 */           listFiles.add(dir.getAbsolutePath());
/*     */         }
/*     */       } 
/*     */     } 
/* 250 */     ((Vector)listFiles).trimToSize();
/*     */     
/* 252 */     return listFiles;
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static List listFilesDirectoryForPattern(String baseDirectory, String dirName, final String[] regexp, final int mode) {
/* 295 */     List<HashMap<Object, Object>> listFiles = new Vector();
/* 296 */     File dir = new File(dirName);
/*     */     
/* 298 */     File[] files = dir.listFiles(new FileFilter() {
/*     */           public boolean accept(File file) {
/* 300 */             if (!file.isDirectory()) {
/* 301 */               for (int iterReg = 0; iterReg < regexp.length; iterReg++) {
/* 302 */                 Pattern p = Pattern.compile(regexp[iterReg]);
/* 303 */                 Matcher m = p.matcher(file.getName().toLowerCase());
/* 304 */                 if (m.matches()) {
/* 305 */                   return true;
/*     */                 }
/*     */               } 
/* 308 */               return false;
/*     */             } 
/* 310 */             if (mode == 0) {
/* 311 */               return false;
/*     */             }
/*     */             
/* 314 */             return true;
/*     */           }
/*     */         });
/*     */     
/* 318 */     if (files != null) {
/* 319 */       for (int iterFiles = 0; iterFiles < files.length; iterFiles++) {
/* 320 */         if (files[iterFiles].isDirectory()) {
/* 321 */           listFiles.addAll(listFilesDirectoryForPattern(baseDirectory, files[iterFiles].getAbsolutePath(), regexp, mode));
/*     */         } else {
/* 323 */           HashMap<Object, Object> hmp = new HashMap<Object, Object>();
/* 324 */           hmp.put(files[iterFiles].getAbsolutePath(), baseDirectory);
/* 325 */           listFiles.add(hmp);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 330 */     ((Vector)listFiles).trimToSize();
/*     */     
/* 332 */     return listFiles;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static HashMap getUniqueNextDiscriminant(Configuration configuration) {
/* 343 */     HashMap<Object, Object> keyMap = new HashMap<Object, Object>();
/*     */     
/* 345 */     for (Iterator<String> iter = configuration.getKeys().iterator(); iter.hasNext(); ) {
/* 346 */       String nextKey = iter.next();
/* 347 */       if (nextKey.startsWith(".")) {
/* 348 */         nextKey = nextKey.substring(1);
/*     */       }
/* 350 */       String fullKey = nextKey.substring(0, (nextKey.indexOf('.') != -1) ? nextKey.indexOf('.') : nextKey.length());
/*     */       
/* 352 */       keyMap.put(fullKey, fullKey);
/*     */     } 
/*     */     
/* 355 */     return keyMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static ArrayList getUniqueNextDiscriminantSorted(Configuration configuration) {
/* 367 */     ArrayList<Comparable> arrL = new ArrayList(getUniqueNextDiscriminant(configuration).keySet());
/* 368 */     Collections.sort(arrL);
/* 369 */     arrL.trimToSize();
/* 370 */     return arrL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void copyAFileTo(File src, File dest) throws IOException {
/* 381 */     if (!src.exists())
/* 382 */       throw new IOException("File not found '" + src.getAbsolutePath() + "'"); 
/* 383 */     BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(dest));
/* 384 */     BufferedInputStream in = new BufferedInputStream(new FileInputStream(src));
/*     */     
/* 386 */     byte[] read = new byte[128];
/* 387 */     int len = 128;
/* 388 */     while ((len = in.read(read)) > 0) {
/* 389 */       out.write(read, 0, len);
/*     */     }
/* 391 */     out.flush();
/* 392 */     out.close();
/* 393 */     in.close();
/*     */   }
/*     */   
/*     */   public static void makeFileFromProperties(Properties src, File dest) throws IOException {
/* 397 */     FileOutputStream fos = null;
/* 398 */     if (src != null && dest != null) {
/* 399 */       Enumeration<String> keySet = src.keys();
/* 400 */       Properties props = new Properties();
/*     */       
/* 402 */       while (keySet.hasMoreElements()) {
/* 403 */         String nextKey = keySet.nextElement();
/* 404 */         String nextValue = src.getProperty(nextKey);
/* 405 */         props.setProperty(nextKey, nextValue);
/*     */       } 
/*     */       try {
/* 408 */         fos = new FileOutputStream(dest);
/* 409 */         props.store(fos, "local.current.version");
/* 410 */       } catch (IOException ex) {
/* 411 */         throw new IllegalStateException("unable to store informations into: " + dest.getAbsolutePath());
/*     */       } finally {
/* 413 */         fos.flush();
/* 414 */         fos.close();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean _delete(File file) {
/* 426 */     boolean ret = true;
/* 427 */     if (!file.isDirectory()) {
/* 428 */       return file.delete();
/*     */     }
/* 430 */     File[] files = file.listFiles();
/* 431 */     for (int i = 0; i < files.length && ret; i++) {
/* 432 */       ret = _delete(files[i]);
/*     */     }
/* 434 */     ret = (ret && file.delete());
/* 435 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ZipUtility
/*     */   {
/*     */     static final int BUFFER = 2048;
/*     */ 
/*     */ 
/*     */     
/*     */     public static void zipFiles(String baseDirectory, FileOutputStream fos) {
/*     */       try {
/* 448 */         FileOutputStream dest = (fos != null) ? fos : new FileOutputStream("log.zip");
/*     */ 
/*     */         
/* 451 */         CheckedOutputStream checksum = new CheckedOutputStream(dest, new Adler32());
/*     */         
/* 453 */         BufferedOutputStream buff = new BufferedOutputStream(checksum);
/* 454 */         ZipOutputStream out = new ZipOutputStream(buff);
/*     */ 
/*     */         
/* 457 */         out.setMethod(8);
/* 458 */         out.setLevel(9);
/*     */ 
/*     */         
/* 461 */         byte[] data = new byte[2048];
/*     */ 
/*     */         
/* 464 */         Vector vFiles = (Vector)AppParamConfigurationUtilities.listFilesDirectoryForPattern(baseDirectory, baseDirectory, new String[] { "[\\w\\s\\.&-~#]*" }, 1);
/*     */         
/* 466 */         for (Iterator<String> itV = vFiles.iterator(); itV.hasNext(); ) {
/* 467 */           String currentFilePath = itV.next();
/*     */           
/* 469 */           FileInputStream fi = new FileInputStream(currentFilePath);
/* 470 */           BufferedInputStream buffi = new BufferedInputStream(fi, 2048);
/*     */           
/* 472 */           ZipEntry entry = new ZipEntry(currentFilePath);
/* 473 */           out.putNextEntry(entry);
/*     */           
/*     */           int count;
/* 476 */           while ((count = buffi.read(data, 0, 2048)) != -1) {
/* 477 */             out.write(data, 0, count);
/*     */           }
/*     */           
/* 480 */           out.closeEntry();
/* 481 */           buffi.close();
/*     */         } 
/*     */         
/* 484 */         out.close();
/* 485 */         buff.close();
/* 486 */         checksum.close();
/* 487 */         dest.close();
/*     */       }
/* 489 */       catch (Exception e) {
/* 490 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\diag\client\logconf\AppParamConfigurationUtilities.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */