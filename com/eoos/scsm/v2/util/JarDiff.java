/*     */ package com.eoos.scsm.v2.util;
/*     */ 
/*     */ import com.eoos.log.Log4jUtil;
/*     */ import com.eoos.scsm.v2.io.StreamUtil;
/*     */ import com.eoos.util.v2.StopWatch;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.LineNumberReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintWriter;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipException;
/*     */ import java.util.zip.ZipFile;
/*     */ import java.util.zip.ZipInputStream;
/*     */ import java.util.zip.ZipOutputStream;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class JarDiff
/*     */ {
/*  39 */   private static final Logger log = Logger.getLogger(JarDiff.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String NAME_INDEX = "META-INF/INDEX.JD";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String NAME_MANIFEST = "META-INF/MANIFEST.MF";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JARContentCallback createCallback(File archive) throws ZipException, IOException {
/*  59 */     final ZipFile jarFile = new ZipFile(archive);
/*  60 */     return new JARContentCallback()
/*     */       {
/*     */         public InputStream getInputStream(ZipEntry entry) throws IOException {
/*  63 */           return jarFile.getInputStream(entry);
/*     */         }
/*     */         
/*     */         public ZipEntry getEntry(String name) {
/*  67 */           return jarFile.getEntry(name);
/*     */         }
/*     */         
/*     */         public Enumeration getEntries() {
/*  71 */           return jarFile.entries();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public static JARContentCallback createCallback(ZipInputStream jis, final boolean inMemory) throws IOException {
/*  78 */     final Map<Object, Object> entryToData = new HashMap<Object, Object>();
/*  79 */     final Map<Object, Object> nameToEntry = new HashMap<Object, Object>();
/*  80 */     ZipEntry entry = null;
/*  81 */     while ((entry = jis.getNextEntry()) != null) {
/*  82 */       OutputStream os = null;
/*  83 */       ByteArrayOutputStream baos = null;
/*  84 */       File file = null;
/*  85 */       if (inMemory) {
/*  86 */         baos = new ByteArrayOutputStream();
/*  87 */         os = baos;
/*     */       } else {
/*  89 */         file = Util.createTmpFile();
/*  90 */         OutputStream fos = new BufferedOutputStream(new FileOutputStream(file));
/*  91 */         os = fos;
/*     */       } 
/*     */       try {
/*  94 */         StreamUtil.transfer(jis, os);
/*     */       } finally {
/*  96 */         os.close();
/*     */       } 
/*  98 */       if (inMemory) {
/*  99 */         entryToData.put(entry, baos.toByteArray());
/*     */       } else {
/* 101 */         entryToData.put(entry, file);
/*     */       } 
/* 103 */       nameToEntry.put(entry.getName().toLowerCase(), entry);
/* 104 */       jis.closeEntry();
/*     */     } 
/*     */ 
/*     */     
/* 108 */     final Vector vector = new Vector(entryToData.keySet());
/*     */     
/* 110 */     return new JARContentCallback()
/*     */       {
/*     */         public InputStream getInputStream(ZipEntry entry) throws IOException {
/* 113 */           if (inMemory) {
/* 114 */             return new ByteArrayInputStream((byte[])entryToData.get(entry));
/*     */           }
/* 116 */           return new BufferedInputStream(new FileInputStream((File)entryToData.get(entry)));
/*     */         }
/*     */ 
/*     */         
/*     */         public ZipEntry getEntry(String name) {
/* 121 */           return (ZipEntry)nameToEntry.get(name.toLowerCase());
/*     */         }
/*     */         
/*     */         public Enumeration getEntries() {
/* 125 */           return vector.elements();
/*     */         }
/*     */       };
/*     */   } public static interface JARContentCallback {
/*     */     Enumeration getEntries(); ZipEntry getEntry(String param1String);
/*     */     InputStream getInputStream(ZipEntry param1ZipEntry) throws IOException; }
/*     */   private static String getFilename(String name) {
/* 132 */     int index = name.lastIndexOf("/");
/* 133 */     if (index != -1 && !name.endsWith("/")) {
/* 134 */       return name.substring(index);
/*     */     }
/* 136 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void createJarDiff(JARContentCallback oldJar, JARContentCallback newJar, ZipOutputStream os) throws IOException {
/* 141 */     createJarDiff(oldJar, newJar, os, true);
/*     */   }
/*     */   
/*     */   public static void createJarDiff(JARContentCallback oldJar, JARContentCallback newJar, ZipOutputStream os, boolean checkMoved) throws IOException {
/* 145 */     log.debug("creating jar diff...");
/* 146 */     Set<String> preservedOldEntries = new HashSet();
/*     */     
/* 148 */     Set<ZipEntry> newEntries = new HashSet();
/* 149 */     for (Enumeration<ZipEntry> enumeration = newJar.getEntries(); enumeration.hasMoreElements(); ) {
/* 150 */       ZipEntry entry = enumeration.nextElement();
/* 151 */       ZipEntry entryOld = oldJar.getEntry(entry.getName());
/* 152 */       if (entryOld != null) {
/* 153 */         InputStream is1 = newJar.getInputStream(entry);
/* 154 */         InputStream is2 = oldJar.getInputStream(entryOld);
/*     */         try {
/* 156 */           if (Util.equalStreams(is1, is2, null)) {
/* 157 */             log.debug("...found preserved entry: " + entryOld.getName());
/* 158 */             preservedOldEntries.add(entryOld.getName());
/*     */             continue;
/*     */           } 
/* 161 */           log.debug("...found modified entry: " + entry.getName());
/* 162 */           preservedOldEntries.add(entryOld.getName());
/* 163 */           newEntries.add(entry);
/*     */         } finally {
/*     */           
/* 166 */           is1.close();
/* 167 */           is2.close();
/*     */         }  continue;
/*     */       } 
/* 170 */       log.debug("...found new entry: " + entry.getName());
/* 171 */       newEntries.add(entry);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 176 */     ZipEntry indexFile = new ZipEntry("META-INF/INDEX.JD");
/* 177 */     os.putNextEntry(indexFile);
/* 178 */     PrintWriter pw = new PrintWriter(new OutputStreamWriter(os, Charset.forName("UTF-8")));
/* 179 */     pw.println("version 1.0");
/* 180 */     for (Enumeration<ZipEntry> enumeration1 = oldJar.getEntries(); enumeration1.hasMoreElements(); ) {
/* 181 */       ZipEntry entry = enumeration1.nextElement();
/* 182 */       if (!preservedOldEntries.contains(entry.getName())) {
/* 183 */         if (checkMoved) {
/* 184 */           log.debug("...found entry to remove (candidate): " + entry.getName() + ", checking if it has been moved...");
/* 185 */           String filename = getFilename(normalizedName(entry.getName()));
/* 186 */           if (filename != null) {
/* 187 */             for (Iterator<ZipEntry> iterator = newEntries.iterator(); iterator.hasNext(); ) {
/* 188 */               ZipEntry entry2 = iterator.next();
/* 189 */               if (normalizedName(entry2.getName()).endsWith(filename)) {
/* 190 */                 log.debug("...found added entry with same name: " + entry2.getName() + " , checking data");
/* 191 */                 InputStream is1 = oldJar.getInputStream(entry);
/* 192 */                 InputStream is2 = newJar.getInputStream(entry2);
/*     */                 try {
/* 194 */                   if (Util.equalStreams(is1, is2, null)) {
/* 195 */                     log.debug("...found moved entry: " + entry.getName() + " (moved to " + entry2.getName() + ")");
/* 196 */                     iterator.remove();
/* 197 */                     pw.println("move " + entry.getName() + " " + entry2.getName());
/*     */                     continue;
/*     */                   } 
/* 200 */                   log.debug("...data different");
/*     */                 } finally {
/*     */                   
/* 203 */                   is1.close();
/* 204 */                   is2.close();
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           }
/*     */         } 
/* 210 */         log.debug("...found removed entry: " + entry.getName());
/* 211 */         pw.println("remove " + entry.getName());
/*     */       } 
/*     */     } 
/* 214 */     pw.flush();
/* 215 */     os.closeEntry();
/*     */     
/* 217 */     log.debug("...writing additional entries");
/* 218 */     for (Iterator<ZipEntry> iter = newEntries.iterator(); iter.hasNext(); ) {
/* 219 */       ZipEntry entry = iter.next();
/* 220 */       ZipEntry diffEntry = new ZipEntry(entry.getName());
/* 221 */       os.putNextEntry(diffEntry);
/* 222 */       InputStream is = newJar.getInputStream(entry);
/*     */       try {
/* 224 */         StreamUtil.transfer(is, os);
/*     */       } finally {
/* 226 */         is.close();
/*     */       } 
/* 228 */       os.closeEntry();
/*     */     } 
/*     */     
/* 231 */     os.flush();
/*     */   }
/*     */ 
/*     */   
/*     */   private static String normalizedName(String name) {
/* 236 */     if (name != null) {
/* 237 */       return name.trim().toLowerCase(Locale.ENGLISH);
/*     */     }
/* 239 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void applyJarDiff(JARContentCallback baseJar, JARContentCallback diffJar, ZipOutputStream os) throws IOException {
/* 244 */     Set<String> handledEntries = new HashSet();
/* 245 */     for (Enumeration<ZipEntry> enumeration1 = diffJar.getEntries(); enumeration1.hasMoreElements(); ) {
/* 246 */       ZipEntry entry = enumeration1.nextElement();
/* 247 */       String name = entry.getName();
/* 248 */       if (name.equalsIgnoreCase("META-INF/INDEX.JD")) {
/* 249 */         InputStream isIndexFile = diffJar.getInputStream(entry);
/*     */         try {
/* 251 */           InputStreamReader reader = new InputStreamReader(isIndexFile, Charset.forName("UTF-8"));
/* 252 */           LineNumberReader lnr = new LineNumberReader(reader);
/* 253 */           String line = null;
/* 254 */           while ((line = lnr.readLine()) != null) {
/* 255 */             String[] parts = line.split("\\s+");
/* 256 */             if (parts != null && parts.length > 1) {
/* 257 */               if (parts[0].trim().equalsIgnoreCase("remove")) {
/* 258 */                 handledEntries.add(parts[1].trim().toLowerCase(Locale.ENGLISH)); continue;
/* 259 */               }  if (parts[0].trim().equalsIgnoreCase("move")) {
/* 260 */                 String oldName = parts[1].trim();
/* 261 */                 String newName = parts[2].trim();
/* 262 */                 handledEntries.add(oldName.toLowerCase(Locale.ENGLISH));
/* 263 */                 ZipEntry zipEntry = new ZipEntry(newName);
/* 264 */                 os.putNextEntry(zipEntry);
/* 265 */                 InputStream inputStream = baseJar.getInputStream(baseJar.getEntry(oldName));
/*     */                 try {
/* 267 */                   StreamUtil.transfer(inputStream, os);
/*     */                 } finally {
/* 269 */                   inputStream.close();
/*     */                 } 
/* 271 */                 os.closeEntry();
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } finally {
/* 276 */           isIndexFile.close();
/*     */         } 
/*     */         continue;
/*     */       } 
/* 280 */       handledEntries.add(name.toLowerCase(Locale.ENGLISH));
/* 281 */       ZipEntry newEntry = new ZipEntry(name);
/* 282 */       os.putNextEntry(newEntry);
/* 283 */       InputStream is = diffJar.getInputStream(entry);
/*     */       try {
/* 285 */         StreamUtil.transfer(is, os);
/*     */       } finally {
/* 287 */         is.close();
/*     */       } 
/* 289 */       os.closeEntry();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 294 */     for (Enumeration<ZipEntry> enumeration = baseJar.getEntries(); enumeration.hasMoreElements(); ) {
/* 295 */       ZipEntry entry = enumeration.nextElement();
/* 296 */       String name = entry.getName();
/* 297 */       if (!handledEntries.contains(name.toLowerCase(Locale.ENGLISH))) {
/* 298 */         ZipEntry newEntry = new ZipEntry(name);
/* 299 */         os.putNextEntry(newEntry);
/* 300 */         StreamUtil.transfer(baseJar.getInputStream(entry), os);
/* 301 */         os.closeEntry();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main(String[] args) throws IOException {
/* 308 */     Log4jUtil.attachConsoleAppender();
/* 309 */     String jar1 = "E:\\apache-tomcat-6.0.14\\webapps\\tis2web\\kdr\\client\\scsm-2.0.71.jar";
/* 310 */     String jar2 = "E:\\apache-tomcat-6.0.14\\webapps\\tis2web\\kdr\\client\\scsm.jar";
/* 311 */     String diff = "e:/tmp/scsm.jardiff";
/* 312 */     ZipInputStream jarFile = new ZipInputStream(new FileInputStream(new File(jar1)));
/* 313 */     ZipInputStream jarFile2 = new ZipInputStream(new FileInputStream(new File(jar2)));
/*     */     
/* 315 */     boolean inMemory = false;
/* 316 */     File result = new File(diff);
/*     */     
/* 318 */     ZipOutputStream os = new ZipOutputStream(new FileOutputStream(result));
/*     */     
/*     */     try {
/* 321 */       StopWatch sw = StopWatch.getInstance().start();
/* 322 */       createJarDiff(createCallback(jarFile, inMemory), createCallback(jarFile2, inMemory), os);
/* 323 */       log.info("creating diff: " + sw.stop() + " ms");
/*     */     } finally {
/* 325 */       os.close();
/*     */     } 
/*     */     
/* 328 */     StopWatch sw2 = StopWatch.getInstance().start();
/*     */     
/*     */     try {
/* 331 */       jnlp.sample.jardiff.JarDiff.main(new String[] { "-d", "-c", "-n", "-o", "e:/tmp/scsm.std.jardiff", jar1, jar2 });
/*     */     } finally {
/* 333 */       log.info("creating diff: " + sw2.stop() + " ms");
/*     */     } 
/* 335 */     log.info("******************");
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v\\util\JarDiff.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */