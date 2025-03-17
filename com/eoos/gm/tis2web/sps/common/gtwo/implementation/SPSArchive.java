/*     */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.sps.common.export.SPSBlob;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Archive;
/*     */ import com.eoos.gm.tis2web.sps.common.implementation.SPSBlobImpl;
/*     */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipFile;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class SPSArchive
/*     */   implements Archive
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  33 */   private static final Logger log = Logger.getLogger(SPSArchive.class);
/*  34 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*     */   
/*  36 */   protected String comment = "";
/*     */   protected String content;
/*     */   protected String part;
/*  39 */   protected List descriptions = new ArrayList();
/*  40 */   protected List blobs = new ArrayList();
/*  41 */   protected transient List files = new ArrayList();
/*  42 */   protected transient List directory = new ArrayList();
/*  43 */   protected transient Map parts = new HashMap<Object, Object>();
/*  44 */   protected transient int duplicates = 0;
/*  45 */   protected transient Map modules = new HashMap<Object, Object>();
/*     */   protected transient int numcals;
/*     */   
/*     */   public String getPartNo() {
/*  49 */     return this.part;
/*     */   }
/*     */   
/*     */   public List getCalibrationUnits() {
/*  53 */     return this.blobs;
/*     */   }
/*     */   
/*     */   public List getCalibrationFiles() {
/*  57 */     return this.files;
/*     */   }
/*     */   
/*     */   public List getDescriptions() {
/*  61 */     return this.descriptions;
/*     */   }
/*     */   
/*     */   public String getComment() {
/*  65 */     return this.comment;
/*     */   }
/*     */   
/*     */   public String getChangeReason() {
/*  69 */     return this.content;
/*     */   }
/*     */   
/*     */   public SPSArchive(Locale locale, String filename) throws Exception {
/*  73 */     log.debug("loading archive: " + filename);
/*  74 */     loadDirectory(locale, new File(filename));
/*  75 */     loadBlobs(locale, new File(filename));
/*  76 */     if (this.blobs.size() + this.duplicates != this.directory.size() || this.numcals != this.blobs.size() + this.duplicates) {
/*  77 */       log.error("archive: blob count mismatch");
/*  78 */       throw new IOException();
/*     */     }  int i;
/*  80 */     for (i = 0; i < this.numcals; i++) {
/*  81 */       Pair pair = this.directory.get(i);
/*  82 */       String dir = (String)pair.getFirst();
/*  83 */       Integer filesize = (Integer)pair.getSecond();
/*  84 */       SPSBlobImpl blob = (SPSBlobImpl)this.parts.get(dir.toLowerCase(Locale.ENGLISH));
/*  85 */       if (blob.getBlobSize().equals(filesize)) {
/*  86 */         if (blob.getBlobID() == null) {
/*  87 */           blob.setModuleID(lookupModuleID(dir));
/*     */         }
/*     */       } else {
/*  90 */         log.error("archive: blob size mismatch");
/*  91 */         throw new IOException();
/*     */       } 
/*     */     } 
/*  94 */     Collections.sort(this.blobs, new ModuleSortOrder());
/*  95 */     for (i = 0; i < this.blobs.size(); i++) {
/*  96 */       SPSBlobImpl blob = this.blobs.get(i);
/*  97 */       byte[] data = blob.getData();
/*  98 */       log.debug(blob.getBlobID() + ": " + blob.getBlobName() + " (" + data.length + " bytes)");
/*  99 */       this.files.add(data);
/* 100 */       blob.setData(null);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void loadDirectory(Locale locale, File file) throws Exception {
/* 105 */     ZipFile zip = null;
/* 106 */     InputStream is = null;
/* 107 */     StructureInputStream sis = null;
/* 108 */     ByteArrayOutputStream stream = null;
/*     */     try {
/* 110 */       zip = new ZipFile(file);
/* 111 */       Enumeration<? extends ZipEntry> entries = zip.entries();
/* 112 */       if (entries != null) {
/* 113 */         while (entries.hasMoreElements()) {
/* 114 */           ZipEntry zipEntry = entries.nextElement();
/* 115 */           if (zipEntry == null || 
/* 116 */             zipEntry.isDirectory())
/*     */             continue; 
/* 118 */           if (zipEntry.getName().toLowerCase(Locale.ENGLISH).endsWith(".tbl")) {
/* 119 */             this.part = zipEntry.getName().substring(0, zipEntry.getName().lastIndexOf("."));
/* 120 */             sis = new StructureInputStream(zip.getInputStream(zipEntry), zipEntry.getSize());
/* 121 */             this.numcals = sis.readShortLE();
/* 122 */             StringBuffer text = new StringBuffer(308 + this.numcals * 100);
/* 123 */             String label = resourceProvider.getLabel(locale, "sps.archive.contents");
/* 124 */             text.append(label + ":\n");
/* 125 */             for (int i = 0; i < 4; i++) {
/* 126 */               String line = sis.readString(77);
/* 127 */               if (line.length() > 0) {
/* 128 */                 text.append(line + '\n');
/* 129 */                 this.comment += line + " ";
/*     */               } 
/*     */             } 
/* 132 */             Set<String> calibrations = new HashSet();
/* 133 */             for (int j = 0; j < this.numcals; j++) {
/* 134 */               String dir = sis.readString(50);
/* 135 */               String desc = sis.readString(40);
/* 136 */               Integer chModid = Integer.valueOf(sis.readChar());
/*     */               
/* 138 */               char fmt = sis.readChar();
/* 139 */               int filesize = sis.readIntLE();
/* 140 */               this.descriptions.add(desc);
/* 141 */               this.modules.put(chModid, dir);
/* 142 */               this.directory.add(new PairImpl(dir, Integer.valueOf(filesize)));
/* 143 */               calibrations.add(dir);
/* 144 */               text.append(chModid.toString() + '\t' + dir + '\t' + desc + '\t' + '\n');
/*     */             } 
/* 146 */             this.duplicates = this.directory.size() - calibrations.size();
/* 147 */             this.content = text.toString();
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 153 */     } catch (Exception e) {
/* 154 */       log.error("failed to load archive " + file.getName(), e);
/* 155 */       throw e;
/*     */     } finally {
/* 157 */       if (stream != null) {
/*     */         try {
/* 159 */           stream.close();
/* 160 */         } catch (Exception x) {}
/*     */       }
/*     */       
/* 163 */       if (is != null) {
/*     */         try {
/* 165 */           is.close();
/* 166 */         } catch (Exception x) {}
/*     */       }
/*     */       
/* 169 */       if (sis != null) {
/*     */         try {
/* 171 */           sis.close();
/* 172 */         } catch (Exception x) {}
/*     */       }
/*     */       
/* 175 */       if (zip != null) {
/*     */         try {
/* 177 */           zip.close();
/* 178 */         } catch (Exception x) {}
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void loadBlobs(Locale locale, File file) throws Exception {
/* 185 */     ZipFile zip = null;
/* 186 */     InputStream is = null;
/* 187 */     StructureInputStream sis = null;
/* 188 */     ByteArrayOutputStream stream = null;
/*     */     try {
/* 190 */       zip = new ZipFile(file);
/* 191 */       Enumeration<? extends ZipEntry> entries = zip.entries();
/* 192 */       if (entries != null) {
/* 193 */         byte[] buf = new byte[4096];
/* 194 */         while (entries.hasMoreElements()) {
/* 195 */           ZipEntry zipEntry = entries.nextElement();
/* 196 */           if (zipEntry == null || 
/* 197 */             zipEntry.isDirectory())
/*     */             continue; 
/* 199 */           if (zipEntry.getName().toLowerCase(Locale.ENGLISH).endsWith(".tbl"))
/*     */             continue; 
/* 201 */           String part = zipEntry.getName().substring(0, zipEntry.getName().lastIndexOf("."));
/* 202 */           is = zip.getInputStream(zipEntry);
/* 203 */           if (is != null) {
/* 204 */             stream = new ByteArrayOutputStream(buf.length);
/* 205 */             int count = 0;
/* 206 */             while ((count = is.read(buf)) != -1) {
/* 207 */               stream.write(buf, 0, count);
/*     */             }
/* 209 */             byte[] data = stream.toByteArray();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 216 */             List<Integer> modules = lookupModuleIDs(part);
/* 217 */             for (int i = 0; i < modules.size(); i++) {
/* 218 */               Integer module = modules.get(i);
/* 219 */               SPSBlobImpl sPSBlobImpl = new SPSBlobImpl(part, module, Integer.valueOf(data.length), null, null, null);
/* 220 */               sPSBlobImpl.setData(data);
/* 221 */               this.blobs.add(sPSBlobImpl);
/* 222 */               if (!this.parts.containsKey(zipEntry.getName())) {
/* 223 */                 this.parts.put(zipEntry.getName().toLowerCase(Locale.ENGLISH), sPSBlobImpl);
/*     */               }
/*     */             } 
/*     */           } 
/* 227 */           this.duplicates = 0;
/* 228 */           is.close();
/* 229 */           is = null;
/*     */         }
/*     */       
/*     */       }
/*     */     
/* 234 */     } catch (Exception e) {
/* 235 */       log.error("failed to load archive " + file.getName(), e);
/* 236 */       throw e;
/*     */     } finally {
/* 238 */       if (stream != null) {
/*     */         try {
/* 240 */           stream.close();
/* 241 */         } catch (Exception x) {}
/*     */       }
/*     */       
/* 244 */       if (is != null) {
/*     */         try {
/* 246 */           is.close();
/* 247 */         } catch (Exception x) {}
/*     */       }
/*     */       
/* 250 */       if (sis != null) {
/*     */         try {
/* 252 */           sis.close();
/* 253 */         } catch (Exception x) {}
/*     */       }
/*     */       
/* 256 */       if (zip != null) {
/*     */         try {
/* 258 */           zip.close();
/* 259 */         } catch (Exception x) {}
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Integer lookupModuleID(String part) {
/* 266 */     part = part.toLowerCase(Locale.ENGLISH);
/* 267 */     Iterator<Map.Entry> it = this.modules.entrySet().iterator();
/* 268 */     while (it.hasNext()) {
/* 269 */       Map.Entry entry = it.next();
/* 270 */       if (((String)entry.getValue()).toLowerCase(Locale.ENGLISH).startsWith(part)) {
/* 271 */         return (Integer)entry.getKey();
/*     */       }
/*     */     } 
/* 274 */     return null;
/*     */   }
/*     */   
/*     */   protected List lookupModuleIDs(String part) {
/* 278 */     List<Integer> result = new ArrayList();
/* 279 */     part = part.toLowerCase(Locale.ENGLISH);
/* 280 */     Iterator<Map.Entry> it = this.modules.entrySet().iterator();
/* 281 */     while (it.hasNext()) {
/* 282 */       Map.Entry entry = it.next();
/* 283 */       if (((String)entry.getValue()).toLowerCase(Locale.ENGLISH).startsWith(part)) {
/* 284 */         result.add((Integer)entry.getKey());
/*     */       }
/*     */     } 
/* 287 */     return result;
/*     */   }
/*     */   
/*     */   protected static class ModuleSortOrder implements Comparator {
/*     */     public int compare(Object o1, Object o2) {
/* 292 */       if (o1 instanceof SPSBlob && o2 instanceof SPSBlob) {
/* 293 */         SPSBlob b1 = (SPSBlob)o1;
/* 294 */         SPSBlob b2 = (SPSBlob)o2;
/* 295 */         return b1.getBlobID().compareTo(b2.getBlobID());
/*     */       } 
/* 297 */       return 0;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main(String[] args) throws Exception {
/* 303 */     SPSArchive archive = new SPSArchive(Locale.GERMAN, "c:\\Projects\\gm\\webTIS\\Issues\\december 2009\\11707\\080409.zip");
/* 304 */     System.out.println(archive.getDescriptions());
/* 305 */     List<SPSBlob> blobs = archive.getCalibrationUnits();
/* 306 */     for (int i = 0; i < blobs.size(); i++)
/* 307 */       System.out.println(((SPSBlob)blobs.get(i)).getBlobName()); 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\SPSArchive.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */