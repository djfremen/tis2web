/*     */ package com.eoos.gm.tis2web.ctoc.implementation.common.db;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOC;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCFactory;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCType;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.FileReference;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.IIOElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.si.service.cai.BLOBProperty;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOBlob;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOBlobImpl;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOProperty;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOType;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.sql.Blob;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.Statement;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class IOCache
/*     */   implements CTOCFactory {
/*  37 */   protected static Logger log = Logger.getLogger(IOCache.class);
/*     */   
/*     */   public static final String DOCUMENTS = "documents";
/*     */   
/*     */   public static final String IMAGES = "images";
/*     */   
/*  43 */   protected Map sios = new HashMap<Object, Object>();
/*     */   
/*     */   protected File sourceDirectory;
/*     */   
/*     */   protected IDatabaseLink poolDB;
/*     */   
/*     */   protected Connection sourceDB;
/*     */   
/*     */   protected String url;
/*     */   protected String user;
/*     */   protected String password;
/*     */   public static final String SELECT_DOCUMENT = "SELECT MIME_TYPE, FILE_BLOB FROM FILES WHERE ";
/*     */   public static final String SELECT_GRAPHIC = "SELECT MIME_TYPE, FILE_BLOB FROM FILES WHERE LOCALE = 'images' AND ";
/*     */   
/*     */   public IOCache(File sourceDirectory) throws Exception {
/*  58 */     this.sourceDirectory = sourceDirectory;
/*     */   }
/*     */   
/*     */   public IOCache(IDatabaseLink dblink) throws Exception {
/*  62 */     this.poolDB = dblink;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register(CTOC ctoc) {}
/*     */ 
/*     */   
/*     */   public SITOCElement make(CTOCType contentType, int contentID, int order, long propertyIndicator, VCR vcr) {
/*  71 */     SIOType sioType = SIOType.get(contentType.ord());
/*  72 */     IOElement sio = null;
/*  73 */     if (sioType == SIOType.HELP || sioType == SIOType.NEWS || sioType == SIOType.VERSION) {
/*  74 */       Integer sioID = Integer.valueOf(contentID);
/*  75 */       sio = (IOElement)this.sios.get(sioID);
/*  76 */       if (sio != null) {
/*  77 */         return (SITOCElement)sio;
/*     */       }
/*  79 */       sio = new IOElement(sioID, sioType, order, vcr);
/*     */     } else {
/*  81 */       throw new IllegalArgumentException();
/*     */     } 
/*  83 */     sio.setCache(this);
/*  84 */     this.sios.put(sio.getID(), sio);
/*  85 */     return (SITOCElement)sio;
/*     */   }
/*     */   
/*     */   public IIOElement getElement(int sioID) {
/*  89 */     return (IIOElement)this.sios.get(Integer.valueOf(sioID));
/*     */   }
/*     */   
/*     */   public IOElement getElement(Integer sioID) {
/*  93 */     return (IOElement)this.sios.get(sioID);
/*     */   }
/*     */   
/*     */   public File resolveFileReference(String subdirectory, LocaleInfo locale, String reference) {
/*  97 */     return resolveFileReference(this.sourceDirectory, subdirectory, locale, reference);
/*     */   }
/*     */   
/*     */   public static File resolveFileReference(File sourceDirectory, String subdirectory, LocaleInfo locale, String reference) {
/* 101 */     File file = new File(reference);
/* 102 */     if (!file.exists()) {
/* 103 */       String basename = file.getName();
/* 104 */       file = new File(sourceDirectory.getAbsolutePath() + File.separator + subdirectory + File.separator + basename);
/* 105 */       if (!file.exists()) {
/* 106 */         if (locale == null) {
/* 107 */           return null;
/*     */         }
/* 109 */         file = new File(sourceDirectory.getAbsolutePath() + File.separator + subdirectory + File.separator + locale.getLocale() + File.separator + basename);
/* 110 */         if (!file.exists()) {
/* 111 */           file = null;
/*     */         }
/*     */       } 
/*     */     } 
/* 115 */     return file;
/*     */   }
/*     */   
/*     */   public static byte[] loadFile(File file) throws Exception {
/* 119 */     BufferedInputStream in = null;
/*     */     try {
/*     */       try {
/* 122 */         in = new BufferedInputStream(new GZIPInputStream(new FileInputStream(file)));
/* 123 */       } catch (Exception e) {
/* 124 */         in = new BufferedInputStream(new FileInputStream(file));
/*     */       } 
/* 126 */       byte[] bytes = new byte[(int)file.length()];
/* 127 */       int offset = 0;
/* 128 */       int numRead = 0;
/* 129 */       while (offset < bytes.length && (numRead = in.read(bytes, offset, bytes.length - offset)) >= 0) {
/* 130 */         offset += numRead;
/*     */       }
/* 132 */       if (offset < bytes.length) {
/* 133 */         throw new IOException("failed to read file '" + file.getName() + "'.");
/*     */       }
/* 135 */       return bytes;
/* 136 */     } catch (Exception e) {
/* 137 */       throw e;
/*     */     } finally {
/*     */       try {
/* 140 */         in.close();
/* 141 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void loadDocument(SIOBlob blob, LocaleInfo locale, String target) throws Exception {
/* 149 */     Statement st = null;
/* 150 */     ResultSet rs = null;
/*     */     try {
/* 152 */       File file = new File(target);
/* 153 */       target = file.getName();
/* 154 */       String query = "SELECT MIME_TYPE, FILE_BLOB FROM FILES WHERE LOCALE = 'documents#" + locale.getLocale().toLowerCase(Locale.ENGLISH) + "' AND FILE_NAME = '" + target + "'";
/* 155 */       if (this.poolDB != null) {
/* 156 */         this.sourceDB = this.poolDB.requestConnection();
/*     */       }
/* 158 */       st = this.sourceDB.createStatement();
/* 159 */       rs = st.executeQuery(query);
/* 160 */       if (rs.next()) {
/* 161 */         String mime = rs.getString("MIME_TYPE");
/* 162 */         if (mime != null) {
/* 163 */           mime = Util.toThreadLocalMultiton(mime.trim());
/*     */         }
/* 165 */         blob.setProperty(BLOBProperty.MIMETYPE, mime);
/* 166 */         byte[] bytes = null;
/* 167 */         if (DBMS.isOracleDB(this.poolDB, this.url)) {
/* 168 */           Blob b = rs.getBlob("FILE_BLOB");
/* 169 */           bytes = b.getBytes(1L, (int)b.length());
/*     */         } else {
/* 171 */           bytes = (byte[])rs.getObject("FILE_BLOB");
/*     */         } 
/* 173 */         blob.setProperty(BLOBProperty.BLOB, bytes);
/*     */       } else {
/* 175 */         throw new IllegalArgumentException("failed to load io-document '" + target + "'.");
/*     */       } 
/* 177 */     } catch (Exception e) {
/* 178 */       if (this.poolDB == null && this.sourceDB != null) {
/*     */         try {
/* 180 */           this.sourceDB.close();
/* 181 */         } catch (Exception x) {}
/*     */         
/*     */         try {
/* 184 */           this.sourceDB = DriverManager.getConnection(this.url, this.user, this.password);
/* 185 */         } catch (Exception x) {
/* 186 */           log.error("failed to re-connect to " + this.url);
/*     */         } 
/*     */       } 
/* 189 */       throw e;
/*     */     } finally {
/*     */       try {
/* 192 */         if (rs != null) {
/* 193 */           rs.close();
/*     */         }
/* 195 */         if (st != null) {
/* 196 */           st.close();
/*     */         }
/* 198 */         if (this.poolDB != null && this.sourceDB != null) {
/* 199 */           this.poolDB.releaseConnection(this.sourceDB);
/* 200 */           this.sourceDB = null;
/*     */         } 
/* 202 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public SIOBlob getDocument(SIO sio, LocaleInfo locale) {
/*     */     try {
/* 209 */       FileReference reference = (FileReference)sio.getProperty((SITOCProperty)SIOProperty.File);
/* 210 */       if (reference == null || reference.get(locale) == null) {
/* 211 */         return null;
/*     */       }
/* 213 */       String bookmark = null;
/* 214 */       String target = reference.get(locale);
/* 215 */       int pos = target.indexOf('#');
/* 216 */       if (pos > 0) {
/* 217 */         bookmark = target.substring(pos + 1);
/* 218 */         target = target.substring(0, pos);
/*     */       } 
/* 220 */       SIOBlobImpl sIOBlobImpl = new SIOBlobImpl();
/* 221 */       if (this.poolDB != null || this.sourceDB != null) {
/* 222 */         loadDocument((SIOBlob)sIOBlobImpl, locale, target);
/*     */       } else {
/* 224 */         File file = resolveFileReference("documents", locale, target);
/* 225 */         if (file == null) {
/* 226 */           return null;
/*     */         }
/* 228 */         sIOBlobImpl.setProperty(BLOBProperty.BLOB, loadFile(file));
/* 229 */         sIOBlobImpl.setProperty(BLOBProperty.MIMETYPE, "text/html");
/*     */       } 
/* 231 */       sIOBlobImpl.setProperty(BLOBProperty.CHARSET, "UTF-8");
/* 232 */       if (bookmark != null) {
/* 233 */         sIOBlobImpl.setProperty(BLOBProperty.BOOKMARK, bookmark);
/*     */       }
/* 235 */       return (SIOBlob)sIOBlobImpl;
/* 236 */     } catch (Exception e) {
/* 237 */       log.error("failed to fetch document '" + sio.getID() + "' (locale=" + locale.getLocale() + ").", e);
/* 238 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected SIOBlob loadGraphic(String sioID) {
/* 245 */     Statement st = null;
/* 246 */     ResultSet rs = null;
/*     */     try {
/* 248 */       String query = "SELECT MIME_TYPE, FILE_BLOB FROM FILES WHERE LOCALE = 'images' AND  FILE_NAME = '" + sioID + "'";
/* 249 */       if (this.poolDB != null) {
/* 250 */         this.sourceDB = this.poolDB.requestConnection();
/*     */       }
/* 252 */       st = this.sourceDB.createStatement();
/* 253 */       rs = st.executeQuery(query);
/* 254 */       if (rs.next()) {
/* 255 */         SIOBlobImpl sIOBlobImpl = new SIOBlobImpl();
/* 256 */         String mime = rs.getString("MIME_TYPE");
/* 257 */         if (mime != null) {
/* 258 */           mime = Util.toThreadLocalMultiton(mime.trim());
/*     */         }
/* 260 */         sIOBlobImpl.setProperty(BLOBProperty.MIMETYPE, mime);
/* 261 */         byte[] bytes = null;
/* 262 */         if (DBMS.isOracleDB(this.poolDB, this.url)) {
/* 263 */           Blob b = rs.getBlob("FILE_BLOB");
/* 264 */           bytes = b.getBytes(1L, (int)b.length());
/*     */         } else {
/* 266 */           bytes = (byte[])rs.getObject("FILE_BLOB");
/*     */         } 
/* 268 */         sIOBlobImpl.setProperty(BLOBProperty.BLOB, bytes);
/* 269 */         return (SIOBlob)sIOBlobImpl;
/*     */       } 
/* 271 */       throw new IllegalArgumentException("failed to load io-graphic '" + sioID + "'.");
/*     */     }
/* 273 */     catch (Exception e) {
/* 274 */       log.error("failed to load graphic '" + sioID + "'.", e);
/* 275 */       if (this.poolDB == null && this.sourceDB != null) {
/*     */         try {
/* 277 */           this.sourceDB.close();
/* 278 */         } catch (Exception x) {}
/*     */         
/*     */         try {
/* 281 */           this.sourceDB = DriverManager.getConnection(this.url, this.user, this.password);
/* 282 */         } catch (Exception x) {
/* 283 */           log.error("failed to re-connect to " + this.url);
/*     */         } 
/*     */       } 
/* 286 */       return null;
/*     */     } finally {
/*     */       try {
/* 289 */         if (rs != null) {
/* 290 */           rs.close();
/*     */         }
/* 292 */         if (st != null) {
/* 293 */           st.close();
/*     */         }
/* 295 */         if (this.poolDB != null && this.sourceDB != null) {
/* 296 */           this.poolDB.releaseConnection(this.sourceDB);
/* 297 */           this.sourceDB = null;
/*     */         } 
/* 299 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public SIOBlob getGraphic(String sioID) {
/* 305 */     if (this.poolDB != null || this.sourceDB != null) {
/* 306 */       return loadGraphic(sioID);
/*     */     }
/*     */     try {
/* 309 */       File file = resolveFileReference("images", null, sioID);
/* 310 */       if (file == null) {
/* 311 */         return null;
/*     */       }
/* 313 */       SIOBlobImpl sIOBlobImpl = new SIOBlobImpl();
/* 314 */       String fname = file.getName().toUpperCase(Locale.ENGLISH);
/* 315 */       if (fname.endsWith(".GIF")) {
/* 316 */         sIOBlobImpl.setProperty(BLOBProperty.MIMETYPE, "image/gif");
/* 317 */       } else if (fname.endsWith(".JPG")) {
/* 318 */         sIOBlobImpl.setProperty(BLOBProperty.MIMETYPE, "image/jpeg");
/* 319 */       } else if (fname.endsWith(".TIF")) {
/* 320 */         sIOBlobImpl.setProperty(BLOBProperty.MIMETYPE, "image/tiff");
/* 321 */       } else if (fname.endsWith(".PNG")) {
/* 322 */         sIOBlobImpl.setProperty(BLOBProperty.MIMETYPE, "image/png");
/*     */       } 
/* 324 */       sIOBlobImpl.setProperty(BLOBProperty.BLOB, loadFile(file));
/* 325 */       return (SIOBlob)sIOBlobImpl;
/* 326 */     } catch (Exception e) {
/* 327 */       log.error("failed to load graphic '" + sioID + "'.", e);
/* 328 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\implementation\common\db\IOCache.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */