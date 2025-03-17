/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.common;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.sps.common.export.SPSBlob;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Archive;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.StructureInputStream;
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
/*     */ 
/*     */ public class SPSArchive
/*     */   implements Archive
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  36 */   private static final Logger log = Logger.getLogger(SPSArchive.class);
/*     */   
/*  38 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*     */   
/*  40 */   protected String comment = "";
/*     */   
/*     */   protected String content;
/*     */   
/*     */   protected String part;
/*     */   
/*  46 */   protected List descriptions = new ArrayList();
/*     */   
/*  48 */   protected List blobs = new ArrayList();
/*     */   
/*     */   protected boolean needsSecurityCode;
/*     */   
/*  52 */   protected transient List files = new ArrayList();
/*     */   
/*  54 */   protected transient List directory = new ArrayList();
/*  55 */   protected transient Map parts = new HashMap<Object, Object>();
/*  56 */   protected transient int duplicates = 0;
/*  57 */   protected transient Map modules = new HashMap<Object, Object>();
/*     */   
/*     */   protected transient int numcals;
/*     */   
/*     */   public String getPartNo() {
/*  62 */     return this.part;
/*     */   }
/*     */   
/*     */   public List getCalibrationUnits() {
/*  66 */     return this.blobs;
/*     */   }
/*     */   
/*     */   public List getCalibrationFiles() {
/*  70 */     return this.files;
/*     */   }
/*     */   
/*     */   public List getDescriptions() {
/*  74 */     return this.descriptions;
/*     */   }
/*     */   
/*     */   public String getComment() {
/*  78 */     return this.comment;
/*     */   }
/*     */   
/*     */   public String getChangeReason() {
/*  82 */     return this.content;
/*     */   }
/*     */   
/*     */   public boolean getSecurityCodeFlag() {
/*  86 */     return this.needsSecurityCode;
/*     */   }
/*     */   
/*     */   public SPSArchive(SPSLanguage language, File file, boolean needsSecurityCode) throws Exception {
/*  90 */     this(language, file);
/*  91 */     this.needsSecurityCode = needsSecurityCode;
/*     */   }
/*     */   
/*     */   public SPSArchive(SPSLanguage language, File file) throws Exception {
/*  95 */     loadDirectory(language, file);
/*  96 */     loadBlobs(language, file);
/*  97 */     if (this.blobs.size() + this.duplicates != this.directory.size() || this.numcals != this.blobs.size() + this.duplicates) {
/*  98 */       log.error("archive: blob count mismatch");
/*  99 */       throw new IOException();
/*     */     }  int i;
/* 101 */     for (i = 0; i < this.numcals; i++) {
/* 102 */       Pair pair = this.directory.get(i);
/* 103 */       String dir = (String)pair.getFirst();
/* 104 */       Integer filesize = (Integer)pair.getSecond();
/* 105 */       SPSBlobImpl blob = (SPSBlobImpl)this.parts.get(dir.toLowerCase(Locale.ENGLISH));
/* 106 */       if (blob.getBlobSize().equals(filesize)) {
/* 107 */         if (blob.getBlobID() == null) {
/* 108 */           blob.setModuleID(lookupModuleID(dir));
/*     */         }
/*     */       } else {
/* 111 */         log.error("archive: blob size mismatch");
/* 112 */         throw new IOException();
/*     */       } 
/*     */     } 
/* 115 */     Collections.sort(this.blobs, new ModuleSortOrder());
/* 116 */     for (i = 0; i < this.blobs.size(); i++) {
/* 117 */       SPSBlobImpl blob = this.blobs.get(i);
/* 118 */       byte[] data = blob.getData();
/* 119 */       this.files.add(data);
/* 120 */       blob.setData(null);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void loadDirectory(SPSLanguage language, File file) throws Exception {
/* 125 */     ZipFile zip = null;
/* 126 */     InputStream is = null;
/* 127 */     StructureInputStream sis = null;
/* 128 */     ByteArrayOutputStream stream = null;
/*     */     try {
/* 130 */       zip = new ZipFile(file);
/* 131 */       Enumeration<? extends ZipEntry> entries = zip.entries();
/* 132 */       if (entries != null) {
/* 133 */         while (entries.hasMoreElements()) {
/* 134 */           ZipEntry zipEntry = entries.nextElement();
/* 135 */           if (zipEntry == null || 
/* 136 */             zipEntry.isDirectory())
/*     */             continue; 
/* 138 */           if (zipEntry.getName().toLowerCase(Locale.ENGLISH).endsWith(".tbl")) {
/* 139 */             this.part = zipEntry.getName().substring(0, zipEntry.getName().lastIndexOf("."));
/* 140 */             sis = new StructureInputStream(zip.getInputStream(zipEntry), zipEntry.getSize());
/* 141 */             this.numcals = sis.readShortLE();
/* 142 */             StringBuffer text = new StringBuffer(308 + this.numcals * 100);
/* 143 */             String label = ApplicationContext.getInstance().getLabel(language.getJavaLocale(), "sps.archive.contents");
/* 144 */             text.append(label + ":\n");
/* 145 */             for (int i = 0; i < 4; i++) {
/* 146 */               String line = sis.readString(77);
/* 147 */               if (line.length() > 0) {
/* 148 */                 text.append(line + '\n');
/* 149 */                 this.comment += line + " ";
/*     */               } 
/*     */             } 
/* 152 */             Set<String> calibrations = new HashSet();
/* 153 */             for (int j = 0; j < this.numcals; j++) {
/* 154 */               String dir = sis.readString(50);
/* 155 */               String desc = sis.readString(40);
/* 156 */               Integer chModid = Integer.valueOf(sis.readChar());
/*     */               
/* 158 */               char fmt = sis.readChar();
/* 159 */               int filesize = sis.readIntLE();
/* 160 */               this.descriptions.add(desc);
/* 161 */               this.modules.put(chModid, dir);
/* 162 */               this.directory.add(new PairImpl(dir, Integer.valueOf(filesize)));
/* 163 */               calibrations.add(dir);
/* 164 */               text.append(chModid.toString() + '\t' + dir + '\t' + desc + '\t' + '\n');
/*     */             } 
/* 166 */             this.duplicates = this.directory.size() - calibrations.size();
/* 167 */             this.content = text.toString();
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 173 */     } catch (Exception e) {
/* 174 */       log.error("failed to load archive " + file.getName(), e);
/* 175 */       throw e;
/*     */     } finally {
/* 177 */       if (stream != null) {
/*     */         try {
/* 179 */           stream.close();
/* 180 */         } catch (Exception x) {}
/*     */       }
/*     */       
/* 183 */       if (is != null) {
/*     */         try {
/* 185 */           is.close();
/* 186 */         } catch (Exception x) {}
/*     */       }
/*     */       
/* 189 */       if (sis != null) {
/*     */         try {
/* 191 */           sis.close();
/* 192 */         } catch (Exception x) {}
/*     */       }
/*     */       
/* 195 */       if (zip != null) {
/*     */         try {
/* 197 */           zip.close();
/* 198 */         } catch (Exception x) {}
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void loadBlobs(SPSLanguage language, File file) throws Exception {
/* 205 */     ZipFile zip = null;
/* 206 */     InputStream is = null;
/* 207 */     StructureInputStream sis = null;
/* 208 */     ByteArrayOutputStream stream = null;
/*     */     try {
/* 210 */       zip = new ZipFile(file);
/* 211 */       Enumeration<? extends ZipEntry> entries = zip.entries();
/* 212 */       if (entries != null) {
/* 213 */         byte[] buf = new byte[4096];
/* 214 */         while (entries.hasMoreElements()) {
/* 215 */           ZipEntry zipEntry = entries.nextElement();
/* 216 */           if (zipEntry == null || 
/* 217 */             zipEntry.isDirectory())
/*     */             continue; 
/* 219 */           if (zipEntry.getName().toLowerCase(Locale.ENGLISH).endsWith(".tbl"))
/*     */             continue; 
/* 221 */           String part = zipEntry.getName().substring(0, zipEntry.getName().lastIndexOf("."));
/* 222 */           is = zip.getInputStream(zipEntry);
/* 223 */           if (is != null) {
/* 224 */             stream = new ByteArrayOutputStream(buf.length);
/* 225 */             int count = 0;
/* 226 */             while ((count = is.read(buf)) != -1) {
/* 227 */               stream.write(buf, 0, count);
/*     */             }
/* 229 */             byte[] data = stream.toByteArray();
/* 230 */             List<Integer> modules = lookupModuleIDs(part);
/* 231 */             for (int i = 0; i < modules.size(); i++) {
/* 232 */               Integer module = modules.get(i);
/* 233 */               SPSBlobImpl sPSBlobImpl = new SPSBlobImpl(part, module, Integer.valueOf(data.length), null, null, null);
/* 234 */               sPSBlobImpl.setData(data);
/* 235 */               this.blobs.add(sPSBlobImpl);
/* 236 */               if (!this.parts.containsKey(zipEntry.getName())) {
/* 237 */                 this.parts.put(zipEntry.getName().toLowerCase(Locale.ENGLISH), sPSBlobImpl);
/*     */               }
/*     */             } 
/*     */           } 
/* 241 */           this.duplicates = 0;
/* 242 */           is.close();
/* 243 */           is = null;
/*     */         }
/*     */       
/*     */       }
/*     */     
/* 248 */     } catch (Exception e) {
/* 249 */       log.error("failed to load archive " + file.getName(), e);
/* 250 */       throw e;
/*     */     } finally {
/* 252 */       if (stream != null) {
/*     */         try {
/* 254 */           stream.close();
/* 255 */         } catch (Exception x) {}
/*     */       }
/*     */       
/* 258 */       if (is != null) {
/*     */         try {
/* 260 */           is.close();
/* 261 */         } catch (Exception x) {}
/*     */       }
/*     */       
/* 264 */       if (sis != null) {
/*     */         try {
/* 266 */           sis.close();
/* 267 */         } catch (Exception x) {}
/*     */       }
/*     */       
/* 270 */       if (zip != null) {
/*     */         try {
/* 272 */           zip.close();
/* 273 */         } catch (Exception x) {}
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Integer lookupModuleID(String part) {
/* 280 */     part = part.toLowerCase(Locale.ENGLISH);
/* 281 */     Iterator<Map.Entry> it = this.modules.entrySet().iterator();
/* 282 */     while (it.hasNext()) {
/* 283 */       Map.Entry entry = it.next();
/* 284 */       if (((String)entry.getValue()).toLowerCase(Locale.ENGLISH).startsWith(part)) {
/* 285 */         return (Integer)entry.getKey();
/*     */       }
/*     */     } 
/* 288 */     return null;
/*     */   }
/*     */   
/*     */   protected List lookupModuleIDs(String part) {
/* 292 */     List<Integer> result = new ArrayList();
/* 293 */     part = part.toLowerCase(Locale.ENGLISH);
/* 294 */     Iterator<Map.Entry> it = this.modules.entrySet().iterator();
/* 295 */     while (it.hasNext()) {
/* 296 */       Map.Entry entry = it.next();
/* 297 */       if (((String)entry.getValue()).toLowerCase(Locale.ENGLISH).startsWith(part)) {
/* 298 */         result.add((Integer)entry.getKey());
/*     */       }
/*     */     } 
/* 301 */     return result;
/*     */   }
/*     */   
/*     */   protected static class ModuleSortOrder implements Comparator {
/*     */     public int compare(Object o1, Object o2) {
/* 306 */       if (o1 instanceof SPSBlob && o2 instanceof SPSBlob) {
/* 307 */         SPSBlob b1 = (SPSBlob)o1;
/* 308 */         SPSBlob b2 = (SPSBlob)o2;
/* 309 */         return b1.getBlobID().compareTo(b2.getBlobID());
/*     */       } 
/* 311 */       return 0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\common\SPSArchive.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */