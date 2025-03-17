/*     */ package com.eoos.gm.tis2web.si.implementation.io.db;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOC;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCDomain;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.DBVersionInformation;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LGSIT;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.ConNvent;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.Tis2webUtil;
/*     */ import com.eoos.gm.tis2web.si.service.cai.BLOBProperty;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOBlob;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOBlobImpl;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOLU;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOProperty;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOProxy;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOReference;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOTSBImpl;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOType;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import com.eoos.jdbc.CachedRetrievalSupportV3;
/*     */ import com.eoos.jdbc.ConnectionProvider;
/*     */ import com.eoos.jdbc.IStatementManager;
/*     */ import com.eoos.jdbc.JDBCUtil;
/*     */ import com.eoos.jdbc.StatementManagerV2;
/*     */ import com.eoos.scsm.v2.io.StreamUtil;
/*     */ import com.eoos.scsm.v2.util.UncheckedInterruptedException;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.Transforming;
/*     */ import java.io.InputStream;
/*     */ import java.sql.Blob;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class SIStore
/*     */ {
/*  56 */   protected static Logger log = Logger.getLogger(SIStore.class);
/*  57 */   protected DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:ss:mm.SSS");
/*     */ 
/*     */   
/*     */   protected DBMS dbms;
/*     */ 
/*     */   
/*     */   protected DBVersionInformation version;
/*     */ 
/*     */   
/*     */   public static final String DELIMITER = ",";
/*     */   
/*     */   private ConnectionProvider connectionProvider;
/*     */   
/*     */   private ILVCAdapter lvcAdapter;
/*     */   
/*     */   private CachedRetrievalSupportV3 propertiesSupport;
/*     */   
/*     */   private CachedRetrievalSupportV3 subjectSupport;
/*     */   
/*     */   private CachedRetrievalSupportV3 cprSupport;
/*     */   
/*     */   private CachedRetrievalSupportV3 tsbSupport;
/*     */   
/*     */   private CachedRetrievalSupportV3 inspectionsSupport;
/*     */   
/*     */   private CachedRetrievalSupportV3 wdSupport;
/*     */   
/*     */   private CachedRetrievalSupportV3 chapterCPRSupport;
/*     */   
/*     */   private IStatementManager stmtMngrTechScreenData;
/*     */   
/*     */   private IStatementManager statementManager;
/*     */ 
/*     */   
/*     */   DBVersionInformation getVersionInfo() {
/*  92 */     return this.version;
/*     */   }
/*     */   
/*     */   public SIStore(final DBMS dbms, ILVCAdapter lvcAdapter) throws Exception {
/*  96 */     this.dbms = dbms;
/*  97 */     this.lvcAdapter = lvcAdapter;
/*  98 */     this.connectionProvider = ConNvent.create(new ConnectionProvider()
/*     */         {
/*     */           public void releaseConnection(Connection connection) {
/* 101 */             dbms.releaseConnection(connection);
/*     */           }
/*     */           
/*     */           public Connection getConnection() {
/*     */             try {
/* 106 */               return dbms.requestConnection();
/* 107 */             } catch (Exception e) {
/* 108 */               throw new RuntimeException(e);
/*     */             } 
/*     */           }
/*     */         },  300000L);
/*     */     
/* 113 */     this.statementManager = (IStatementManager)new StatementManagerV2(this.connectionProvider);
/* 114 */     this.propertiesSupport = new CachedRetrievalSupportV3("SI-PROPERTIES", this.connectionProvider, Tis2webUtil.createStdCache());
/* 115 */     this.subjectSupport = new CachedRetrievalSupportV3("SI-SUBJECT", this.connectionProvider, Tis2webUtil.createStdCache());
/* 116 */     this.cprSupport = new CachedRetrievalSupportV3("SI-CPR", this.connectionProvider, Tis2webUtil.createStdCache());
/* 117 */     this.wdSupport = new CachedRetrievalSupportV3("SI-WD", this.connectionProvider, Tis2webUtil.createStdCache());
/* 118 */     this.chapterCPRSupport = new CachedRetrievalSupportV3("SI-CPRChapter", this.connectionProvider, Tis2webUtil.createStdCache());
/* 119 */     this.tsbSupport = new CachedRetrievalSupportV3("SI-TSB", this.connectionProvider, Tis2webUtil.createStdCache());
/* 120 */     this.inspectionsSupport = new CachedRetrievalSupportV3("SI-INSPECTIONS", this.connectionProvider, Tis2webUtil.createStdCache());
/* 121 */     this.stmtMngrTechScreenData = (IStatementManager)new StatementManagerV2(this.connectionProvider);
/* 122 */     this.version = loadVersionInformation();
/*     */   }
/*     */ 
/*     */   
/*     */   private Connection getReadOnlyConnection() throws Exception {
/* 127 */     Connection ret = this.connectionProvider.getConnection();
/* 128 */     if (!ret.isReadOnly()) {
/* 129 */       ret.setReadOnly(true);
/*     */     }
/* 131 */     if (!ret.getAutoCommit()) {
/* 132 */       ret.setAutoCommit(false);
/*     */     }
/* 134 */     return ret;
/*     */   }
/*     */   
/*     */   private void releaseConnection(Connection connection) {
/* 138 */     this.connectionProvider.releaseConnection(connection);
/*     */   }
/*     */   
/*     */   private void release(Connection conn, Statement stmt, ResultSet rs) {
/* 142 */     this.dbms.release(null, stmt, rs);
/* 143 */     releaseConnection(conn);
/*     */   }
/*     */   
/*     */   private final class CRSCallback implements CachedRetrievalSupportV3.Callback, CachedRetrievalSupportV3.Callback.EmptyResultFallback {
/*     */     private final Collection requestedLocales;
/*     */     private final LocaleInfo locale;
/*     */     private final SIStore.FallbackLocaleCallback localeCallback;
/*     */     
/*     */     private CRSCallback(Collection requestedLocales, LocaleInfo locale, SIStore.FallbackLocaleCallback localeCallback) {
/* 152 */       this.requestedLocales = requestedLocales;
/* 153 */       this.locale = locale;
/* 154 */       this.localeCallback = localeCallback;
/*     */     }
/*     */     
/*     */     public void setParameters(Object identifier, PreparedStatement stmt) throws SQLException {
/* 158 */       stmt.setInt(2, ((Integer)identifier).intValue());
/*     */     }
/*     */     
/*     */     public void initStatement(PreparedStatement stmt) throws SQLException {
/* 162 */       stmt.setInt(1, this.locale.getLocaleID().intValue());
/*     */     }
/*     */     
/*     */     public Object createObject(Object identifier, ResultSet rs) throws Exception {
/* 166 */       String subject = rs.getString(1);
/* 167 */       if (Util.isNullOrEmpty(subject) && this.localeCallback != null) {
/* 168 */         return createObject(identifier);
/*     */       }
/* 170 */       return subject;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object createObject(Object identifier) throws Exception {
/* 175 */       String subject = null;
/* 176 */       List fbl = this.localeCallback.getFallbackLocaleIDs((Integer)identifier, this.locale);
/* 177 */       if (fbl != null) {
/* 178 */         for (Iterator<Integer> iterLocale = fbl.iterator(); iterLocale.hasNext() && Util.isNullOrEmpty(subject); ) {
/* 179 */           LocaleInfo fallbackLocale = LocaleInfoProvider.getInstance().getLocale(iterLocale.next());
/* 180 */           subject = (String)SIStore.this.getSubjects(Collections.singleton(identifier), fallbackLocale, this.localeCallback, this.requestedLocales).get(identifier);
/*     */         } 
/*     */       }
/* 183 */       return subject;
/*     */     }
/*     */     
/*     */     public Object createKey(Object identifier) {
/* 187 */       return String.valueOf(identifier) + "#" + this.locale.getLocaleID();
/*     */     }
/*     */     
/*     */     public String getQuery() {
/* 191 */       return "SELECT A.SUBJECT FROM SIO_SUBJECT A WHERE A.LOCALE=? AND A.SIO_ID=?";
/*     */     }
/*     */   }
/*     */   
/*     */   private DBVersionInformation loadVersionInformation() throws Exception {
/* 196 */     Connection con = null;
/* 197 */     Statement stmt = null;
/* 198 */     ResultSet rs = null;
/*     */     try {
/* 200 */       con = getReadOnlyConnection();
/* 201 */       stmt = con.createStatement();
/* 202 */       rs = stmt.executeQuery(this.dbms.getSQL("SELECT * FROM RELEASE"));
/* 203 */       if (rs.next()) {
/* 204 */         return new DBVersionInformation(rs.getString("RELEASE_ID"), rs.getDate("RELEASE_DATE"), rs.getString("DESCRIPTION"), rs.getString("VERSION"));
/*     */       }
/* 206 */       return null;
/*     */     }
/* 208 */     catch (Exception e) {
/* 209 */       log.error("loading version information failed.");
/* 210 */       throw e;
/*     */     } finally {
/* 212 */       release(con, stmt, rs);
/*     */     } 
/*     */   }
/*     */   
/*     */   SIOBlob loadDocument(int sioID, int localeID) throws Exception {
/* 217 */     SIOBlob blob = null;
/* 218 */     long timestamp = 0L;
/*     */     
/* 220 */     PreparedStatement stmt = this.statementManager.getStatement("SELECT TEXT_CHARSET, TEXT FROM SIO_TEXT WHERE LOCALE = ? AND SIO_ID = ?");
/*     */     try {
/* 222 */       stmt.setInt(1, localeID);
/* 223 */       stmt.setInt(2, sioID);
/* 224 */       Util.checkInterruption2();
/* 225 */       ResultSet rs = stmt.executeQuery();
/*     */       try {
/* 227 */         if (rs.next()) {
/* 228 */           Util.checkInterruption2();
/* 229 */           String charset = Util.trim(rs.getString("TEXT_CHARSET"));
/*     */           
/* 231 */           SIOBlobImpl sIOBlobImpl = new SIOBlobImpl();
/* 232 */           sIOBlobImpl.setProperty(BLOBProperty.CHARSET, charset);
/* 233 */           byte[] data = null;
/* 234 */           if (this.dbms.isOracleDB()) {
/* 235 */             timestamp = System.currentTimeMillis();
/* 236 */             Blob b = rs.getBlob("TEXT");
/* 237 */             data = b.getBytes(1L, (int)b.length());
/*     */           } else {
/* 239 */             data = (byte[])rs.getObject("TEXT");
/*     */           } 
/* 241 */           sIOBlobImpl.setProperty(BLOBProperty.BLOB, data);
/*     */         } else {
/* 243 */           blob = null;
/*     */         } 
/*     */       } finally {
/* 246 */         JDBCUtil.close(rs, log);
/*     */       } 
/* 248 */       return blob;
/* 249 */     } catch (UncheckedInterruptedException e) {
/* 250 */       throw e;
/*     */     }
/* 252 */     catch (Exception e) {
/* 253 */       if (timestamp > 0L) {
/* 254 */         synchronized (this.dateFormat) {
/* 255 */           log.error("failed to load text blob (sio-id='" + sioID + "')[" + this.dateFormat.format(new Date(timestamp)) + " -> " + this.dateFormat.format(new Date(System.currentTimeMillis())) + "].", e);
/*     */         } 
/*     */       } else {
/* 258 */         log.error("failed to load text blob (sio-id='" + sioID + "').", e);
/*     */       } 
/* 260 */       throw e;
/*     */     } finally {
/* 262 */       this.statementManager.releaseStatement(stmt);
/*     */     } 
/*     */   }
/*     */   
/*     */   public enum BlobType
/*     */   {
/* 268 */     GRAPHIC, IMAGE;
/*     */   }
/*     */   
/*     */   public String getMimeType(int sioID) {
/*     */     try {
/* 273 */       PreparedStatement stmt = this.statementManager.getStatement("SELECT A.GRAPHIC_MIMETYPE FROM SIO_GRAPHIC A WHERE A.GRAPHIC_ID = ?");
/*     */       try {
/* 275 */         stmt.setInt(1, sioID);
/* 276 */         ResultSet rs = stmt.executeQuery();
/*     */         try {
/* 278 */           if (rs.next()) {
/* 279 */             return rs.getString(1);
/*     */           }
/*     */         } finally {
/*     */           
/* 283 */           JDBCUtil.close(rs);
/*     */         } 
/*     */       } finally {
/*     */         
/* 287 */         this.statementManager.releaseStatement(stmt);
/*     */       } 
/* 289 */     } catch (Exception e) {
/* 290 */       log.error("unable to determine mime-type for SIO-ID: " + sioID + ", returning null - exception: " + e, e);
/*     */     } 
/* 292 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getMimeType4Image(int sioID) {
/*     */     try {
/* 298 */       PreparedStatement stmt = this.statementManager.getStatement("SELECT A.GRAPHIC_MIMETYPE FROM SIO_IMAGE A WHERE A.GRAPHIC_ID = ?");
/*     */       try {
/* 300 */         stmt.setInt(1, sioID);
/* 301 */         ResultSet rs = stmt.executeQuery();
/*     */         try {
/* 303 */           if (rs.next()) {
/* 304 */             return rs.getString(1);
/*     */           }
/*     */         } finally {
/*     */           
/* 308 */           JDBCUtil.close(rs);
/*     */         } 
/*     */       } finally {
/*     */         
/* 312 */         this.statementManager.releaseStatement(stmt);
/*     */       } 
/* 314 */     } catch (Exception e) {
/* 315 */       log.error("unable to determine mime-type in SIO_IMAGE Table for SIO-ID: " + sioID + ", returning null - exception: " + e, e);
/*     */     } 
/* 317 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   SIOBlob loadGraphic(int sioID) throws Exception {
/* 322 */     SIOBlob blob = null;
/* 323 */     long timestamp = 0L;
/*     */     
/* 325 */     PreparedStatement stmt = this.statementManager.getStatement("SELECT GRAPHIC_MIMETYPE, GRAPHIC FROM SIO_GRAPHIC WHERE GRAPHIC_ID = ?");
/*     */     
/*     */     try {
/* 328 */       stmt.setInt(1, sioID);
/* 329 */       ResultSet rs = stmt.executeQuery();
/*     */       try {
/* 331 */         if (rs.next()) {
/* 332 */           SIOBlobImpl sIOBlobImpl = new SIOBlobImpl();
/* 333 */           String mime = rs.getString("GRAPHIC_MIMETYPE");
/* 334 */           if (mime != null) {
/* 335 */             mime = mime.trim();
/*     */           }
/* 337 */           sIOBlobImpl.setProperty(BLOBProperty.MIMETYPE, mime);
/* 338 */           byte[] data = null;
/* 339 */           if (this.dbms.isOracleDB()) {
/* 340 */             timestamp = System.currentTimeMillis();
/* 341 */             Blob b = rs.getBlob("GRAPHIC");
/* 342 */             data = b.getBytes(1L, (int)b.length());
/*     */           } else {
/* 344 */             data = (byte[])rs.getObject("GRAPHIC");
/*     */           } 
/* 346 */           sIOBlobImpl.setProperty(BLOBProperty.BLOB, data);
/*     */         } else {
/* 348 */           blob = null;
/*     */         } 
/*     */       } finally {
/* 351 */         JDBCUtil.close(rs, log);
/*     */       } 
/* 353 */       return blob;
/* 354 */     } catch (Exception e) {
/* 355 */       if (timestamp > 0L) {
/* 356 */         synchronized (this.dateFormat) {
/* 357 */           log.error("failed to load graphic blob (sio-id='" + sioID + "')[" + this.dateFormat.format(new Date(timestamp)) + " -> " + this.dateFormat.format(new Date(System.currentTimeMillis())) + "].", e);
/*     */         } 
/*     */       } else {
/* 360 */         log.error("failed to load graphic blob (sio-id='" + sioID + "').", e);
/*     */       } 
/* 362 */       throw e;
/*     */     } finally {
/* 364 */       this.statementManager.releaseStatement(stmt);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   SIOBlob loadImage(int sioID) throws Exception {
/* 370 */     SIOBlob blob = null;
/* 371 */     long timestamp = 0L;
/* 372 */     PreparedStatement stmt = this.statementManager.getStatement("SELECT GRAPHIC_MIMETYPE, GRAPHIC FROM SIO_IMAGE WHERE GRAPHIC_ID = ?");
/*     */     
/*     */     try {
/* 375 */       stmt.setInt(1, sioID);
/* 376 */       ResultSet rs = stmt.executeQuery();
/*     */       try {
/* 378 */         if (rs.next()) {
/* 379 */           SIOBlobImpl sIOBlobImpl = new SIOBlobImpl();
/* 380 */           String mime = rs.getString("GRAPHIC_MIMETYPE");
/* 381 */           if (mime != null) {
/* 382 */             mime = mime.trim();
/*     */           }
/* 384 */           sIOBlobImpl.setProperty(BLOBProperty.MIMETYPE, mime);
/* 385 */           byte[] data = null;
/* 386 */           if (this.dbms.isOracleDB()) {
/* 387 */             timestamp = System.currentTimeMillis();
/* 388 */             Blob b = rs.getBlob("GRAPHIC");
/* 389 */             data = b.getBytes(1L, (int)b.length());
/*     */           } else {
/* 391 */             data = (byte[])rs.getObject("GRAPHIC");
/*     */           } 
/* 393 */           sIOBlobImpl.setProperty(BLOBProperty.BLOB, data);
/*     */         } else {
/*     */           
/* 396 */           blob = null;
/*     */         } 
/*     */       } finally {
/* 399 */         JDBCUtil.close(rs, log);
/*     */       } 
/* 401 */       return blob;
/* 402 */     } catch (Exception e) {
/* 403 */       if (timestamp > 0L) {
/* 404 */         synchronized (this.dateFormat) {
/* 405 */           log.error("failed to load image blob (sio-id='" + sioID + "')[" + this.dateFormat.format(new Date(timestamp)) + " -> " + this.dateFormat.format(new Date(System.currentTimeMillis())) + "].", e);
/*     */         } 
/*     */       } else {
/* 408 */         log.error("failed to load image blob (sio-id='" + sioID + "').", e);
/*     */       } 
/* 410 */       throw e;
/*     */     } finally {
/* 412 */       this.statementManager.releaseStatement(stmt);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List loadCPRs(List esCodes) {
/* 418 */     Map<Integer, List> result = getCPRIDs(esCodes);
/*     */     
/* 420 */     List ret = new LinkedList();
/* 421 */     for (Iterator iter = esCodes.iterator(); iter.hasNext(); ) {
/* 422 */       List cprs = result.get(iter.next());
/* 423 */       if (!Util.isNullOrEmpty(cprs)) {
/* 424 */         ret.addAll(cprs);
/*     */       }
/*     */     } 
/* 427 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   private Map<Integer, List> getCPRIDs(Collection esCodes) {
/*     */     try {
/* 433 */       return this.cprSupport.getObjects(esCodes, new CachedRetrievalSupportV3.Callback()
/*     */           {
/*     */             public void setParameters(Object identifier, PreparedStatement stmt) throws SQLException {
/* 436 */               stmt.setInt(1, ((Integer)identifier).intValue());
/*     */             }
/*     */ 
/*     */             
/*     */             public void initStatement(PreparedStatement stmt) throws SQLException {}
/*     */             
/*     */             public Object createObject(Object identifier, ResultSet rs) throws Exception {
/* 443 */               List<Integer> ret = new LinkedList();
/*     */               while (true) {
/* 445 */                 ret.add(Integer.valueOf(rs.getInt("SIO_ID")));
/* 446 */                 if (!rs.next())
/* 447 */                   return ret; 
/*     */               } 
/*     */             }
/*     */             public Object createKey(Object identifier) {
/* 451 */               return identifier;
/*     */             }
/*     */             
/*     */             public String getQuery() {
/* 455 */               return "SELECT REFERENCE_KEY,SIO_ID FROM SIO_REFERENCES WHERE REFERENCE_TYPE=" + SIOReference.CPR.ord() + " AND REFERENCE_KEY=?";
/*     */             }
/*     */           });
/* 458 */     } catch (Exception e) {
/* 459 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Map<Integer, String> getSubjects(Collection sioIDs, LocaleInfo locale, FallbackLocaleCallback callback, Collection<LocaleInfo> requestedLocales) {
/* 468 */     if (Util.isNullOrEmpty(sioIDs) || requestedLocales.contains(locale)) {
/* 469 */       return Collections.EMPTY_MAP;
/*     */     }
/* 471 */     requestedLocales.add(locale);
/*     */     
/*     */     try {
/* 474 */       return this.subjectSupport.getObjects(sioIDs, new CRSCallback(requestedLocales, locale, callback));
/*     */     }
/* 476 */     catch (Exception e) {
/* 477 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private LGSIT getLGSIT(SIOType sioType) {
/* 485 */     if (sioType == SIOType.SI)
/* 486 */       return LGSIT.SI; 
/* 487 */     if (sioType == SIOType.CPR)
/* 488 */       return LGSIT.CPR; 
/* 489 */     if (sioType == SIOType.WD)
/* 490 */       return LGSIT.WD; 
/* 491 */     if (sioType == SIOType.MajorOperation) {
/* 492 */       return LGSIT.LT;
/*     */     }
/* 494 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSubject(final SIO sio, LocaleInfo locale) {
/* 499 */     return getSubjects(Collections.singleton(sio.getID()), locale, new FallbackLocaleCallback()
/*     */         {
/*     */           public List getFallbackLocaleIDs(Integer sioID, LocaleInfo currentLocale) {
/* 502 */             List ret = currentLocale.getLocaleFLC(SIStore.this.getLGSIT((SIOType)sio.getType()));
/* 503 */             return (ret != null) ? ret : Collections.EMPTY_LIST;
/*     */           }
/*     */         },  new HashSet()).get(sio.getID());
/*     */   }
/*     */   
/*     */   public String getSubject(Integer sioID, LocaleInfo locale) {
/* 509 */     return getSubjects(Collections.singleton(sioID), locale, null, new HashSet()).get(sioID);
/*     */   }
/*     */   
/*     */   public Map getProperties(SIO sio) {
/*     */     try {
/* 514 */       return _getProperties(Collections.singleton(sio.getID())).get(sio.getID());
/* 515 */     } catch (Exception e) {
/* 516 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<Integer, Map> getProperties(Collection sios) {
/*     */     try {
/* 525 */       Transforming transforming = new Transforming()
/*     */         {
/*     */           public Object transform(Object object) {
/* 528 */             if (object instanceof SIO) {
/* 529 */               return ((SIO)object).getID();
/*     */             }
/* 531 */             return object;
/*     */           }
/*     */         };
/*     */ 
/*     */       
/* 536 */       return _getProperties(Util.transformCollection(sios, transforming));
/* 537 */     } catch (Exception e) {
/* 538 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Map<Integer, Map> _getProperties(Collection sios) throws Exception {
/* 543 */     return this.propertiesSupport.getObjects(sios, new CachedRetrievalSupportV3.Callback()
/*     */         {
/*     */           public Object createKey(Object identifier) {
/* 546 */             return identifier;
/*     */           }
/*     */           
/*     */           public Object createObject(Object identifier, ResultSet rs) throws Exception {
/* 550 */             Map<Object, Object> properties = new HashMap<Object, Object>();
/*     */             while (true) {
/* 552 */               int propertyType = rs.getInt("PROPERTY_TYPE");
/* 553 */               SIOProperty property = SIOProperty.get(propertyType);
/* 554 */               String sproperty = Util.trim(rs.getString("PROPERTY"));
/* 555 */               properties.put(property, sproperty);
/* 556 */               if (!rs.next())
/* 557 */                 return properties; 
/*     */             } 
/*     */           }
/*     */           
/*     */           public void initStatement(PreparedStatement stmt) throws SQLException {}
/*     */           
/*     */           public void setParameters(Object identifier, PreparedStatement stmt) throws SQLException {
/* 564 */             stmt.setInt(1, ((Integer)identifier).intValue());
/*     */           }
/*     */ 
/*     */           
/*     */           public String getQuery() {
/* 569 */             return "SELECT  PROPERTY_TYPE, PROPERTY FROM SIO_PROPERTY WHERE SIO_ID=?";
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public List getRelatedLUs(Integer sioID) {
/* 575 */     List<Integer> ret = null;
/*     */     try {
/* 577 */       PreparedStatement stmt = this.statementManager.getStatement("SELECT REL_ID FROM SIO_REL WHERE SIO_ID =? ");
/*     */       
/*     */       try {
/* 580 */         stmt.setInt(1, sioID.intValue());
/* 581 */         ResultSet rs = stmt.executeQuery();
/*     */         try {
/* 583 */           while (rs.next()) {
/* 584 */             if (ret == null) {
/* 585 */               ret = new LinkedList();
/*     */             }
/* 587 */             ret.add(Integer.valueOf(rs.getInt(1)));
/*     */           } 
/*     */         } finally {
/* 590 */           JDBCUtil.close(rs, log);
/*     */         } 
/*     */       } finally {
/*     */         
/* 594 */         this.statementManager.releaseStatement(stmt);
/*     */       }
/*     */     
/* 597 */     } catch (Exception e) {
/* 598 */       log.error("loading sio / related literature units failed (sio-id='" + sioID + "').");
/*     */     } 
/* 600 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   List loadDocumentByLU(String lu) {
/* 605 */     List<Integer> result = null;
/*     */     try {
/* 607 */       PreparedStatement stmt = this.statementManager.getStatement(this.dbms.getSQL(DBMS.SELECT_LU));
/*     */       try {
/* 609 */         stmt.setString(1, lu);
/* 610 */         ResultSet rs = stmt.executeQuery();
/*     */         try {
/* 612 */           while (rs.next()) {
/* 613 */             Integer sioID = Integer.valueOf(rs.getInt("SIO_ID"));
/* 614 */             if (result == null) {
/* 615 */               result = new LinkedList();
/*     */             }
/* 617 */             result.add(sioID);
/*     */           } 
/*     */         } finally {
/* 620 */           JDBCUtil.close(rs, log);
/*     */         } 
/*     */       } finally {
/*     */         
/* 624 */         this.statementManager.releaseStatement(stmt);
/*     */       }
/*     */     
/* 627 */     } catch (Exception e) {
/* 628 */       log.error("loading sio / lu failed (es='" + lu + "').");
/*     */     } 
/* 630 */     return result;
/*     */   }
/*     */   
/*     */   List loadDocumentByES(String es) {
/* 634 */     List<Integer> ret = null;
/*     */     try {
/* 636 */       PreparedStatement stmt = this.statementManager.getStatement(this.dbms.getSQL(DBMS.SELECT_CPR_Chapter));
/*     */       try {
/* 638 */         stmt.setString(1, es.toString());
/* 639 */         ResultSet rs = stmt.executeQuery();
/*     */         try {
/* 641 */           while (rs.next()) {
/* 642 */             if (ret == null) {
/* 643 */               ret = new LinkedList();
/*     */             }
/* 645 */             ret.add(Integer.valueOf(rs.getInt(1)));
/*     */           } 
/*     */         } finally {
/* 648 */           JDBCUtil.close(rs, log);
/*     */         } 
/*     */       } finally {
/*     */         
/* 652 */         this.statementManager.releaseStatement(stmt);
/*     */       }
/*     */     
/* 655 */     } catch (Exception e) {
/* 656 */       log.error("loading sio / cpr failed (es='" + es + "').");
/*     */     } 
/* 658 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   List loadDocumentByWD(String wd) {
/* 663 */     List<Integer> ret = null;
/*     */     try {
/* 665 */       PreparedStatement stmt = this.statementManager.getStatement(this.dbms.getSQL(DBMS.SELECT_WD));
/*     */       try {
/* 667 */         stmt.setString(1, wd.toString());
/* 668 */         ResultSet rs = stmt.executeQuery();
/*     */         try {
/* 670 */           while (rs.next()) {
/* 671 */             if (ret == null) {
/* 672 */               ret = new LinkedList();
/*     */             }
/* 674 */             ret.add(Integer.valueOf(rs.getInt(1)));
/*     */           } 
/*     */         } finally {
/* 677 */           JDBCUtil.close(rs, log);
/*     */         } 
/*     */       } finally {
/*     */         
/* 681 */         this.statementManager.releaseStatement(stmt);
/*     */       }
/*     */     
/* 684 */     } catch (Exception e) {
/* 685 */       log.error("loading sio / cpr failed (es='" + wd + "').");
/*     */     } 
/* 687 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   List loadDocumentByPublicationID(String publicationID) {
/* 693 */     List<Integer> result = new LinkedList();
/*     */     try {
/* 695 */       PreparedStatement stmt = this.statementManager.getStatement(this.dbms.getSQL(DBMS.SELECT_PUBLICATION));
/*     */       try {
/* 697 */         stmt.setString(1, publicationID);
/* 698 */         ResultSet rs = stmt.executeQuery();
/*     */         try {
/* 700 */           while (rs.next()) {
/* 701 */             Integer sioID = Integer.valueOf(rs.getInt("SIO_ID"));
/* 702 */             result.add(sioID);
/*     */           } 
/*     */         } finally {
/* 705 */           JDBCUtil.close(rs, log);
/*     */         } 
/*     */       } finally {
/*     */         
/* 709 */         this.statementManager.releaseStatement(stmt);
/*     */       }
/*     */     
/* 712 */     } catch (Exception e) {
/* 713 */       log.error("loading sio / publicationID failed (publicationID='" + publicationID + "').");
/*     */     } 
/* 715 */     return result;
/*     */   }
/*     */   
/*     */   List loadWiringDiagramsIds(String es) {
/*     */     try {
/* 720 */       Map wds = this.wdSupport.getObjects(Collections.singleton(Integer.valueOf(es)), new CachedRetrievalSupportV3.Callback()
/*     */           {
/*     */             public void setParameters(Object identifier, PreparedStatement stmt) throws SQLException {
/* 723 */               stmt.setInt(1, ((Integer)identifier).intValue());
/*     */             }
/*     */ 
/*     */             
/*     */             public void initStatement(PreparedStatement stmt) throws SQLException {}
/*     */             
/*     */             public Object createObject(Object identifier, ResultSet rs) throws Exception {
/* 730 */               List<Integer> ret = new LinkedList();
/*     */               while (true) {
/* 732 */                 ret.add(Integer.valueOf(rs.getInt("SIO_ID")));
/* 733 */                 if (!rs.next())
/* 734 */                   return ret; 
/*     */               } 
/*     */             }
/*     */             public Object createKey(Object identifier) {
/* 738 */               return identifier;
/*     */             }
/*     */             
/*     */             public String getQuery() {
/* 742 */               return "SELECT REFERENCE_KEY,SIO_ID FROM SIO_REFERENCES WHERE REFERENCE_TYPE=" + SIOReference.WD.ord() + " AND REFERENCE_KEY=?";
/*     */             }
/*     */           });
/*     */       
/* 746 */       if (wds != null)
/* 747 */         return (List)wds.get(Integer.valueOf(es)); 
/* 748 */       return null;
/* 749 */     } catch (Exception e) {
/* 750 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   Integer lookupChapterCPRId(Integer teststep) {
/*     */     try {
/* 756 */       Map chapterCPR = this.chapterCPRSupport.getObjects(Collections.singleton(teststep), new CachedRetrievalSupportV3.Callback()
/*     */           {
/*     */             public void setParameters(Object identifier, PreparedStatement stmt) throws SQLException {
/* 759 */               stmt.setInt(1, ((Integer)identifier).intValue());
/*     */             }
/*     */ 
/*     */             
/*     */             public void initStatement(PreparedStatement stmt) throws SQLException {}
/*     */             
/*     */             public Object createObject(Object identifier, ResultSet rs) throws Exception {
/* 766 */               Integer result = null;
/* 767 */               if (rs.next()) {
/* 768 */                 String xproperty = Util.trim(rs.getString("REFERENCE_KEY"));
/* 769 */                 result = Integer.valueOf(xproperty);
/*     */               } 
/* 771 */               return result;
/*     */             }
/*     */             
/*     */             public Object createKey(Object identifier) {
/* 775 */               return identifier;
/*     */             }
/*     */             
/*     */             public String getQuery() {
/* 779 */               return "SELECT REFERENCE_KEY,SIO_ID FROM SIO_REFERENCES WHERE REFERENCE_TYPE=" + SIOReference.CPR.ord() + " AND REFERENCE_KEY=?";
/*     */             }
/*     */           });
/* 782 */       if (chapterCPR != null)
/* 783 */         return (Integer)chapterCPR.get(teststep); 
/* 784 */       return null;
/* 785 */     } catch (Exception e) {
/* 786 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected List loadDTCs() {
/* 791 */     Connection conn = null;
/* 792 */     PreparedStatement stmt = null;
/* 793 */     ResultSet rs = null;
/* 794 */     List<String> dtcs = new LinkedList();
/*     */     try {
/* 796 */       conn = getReadOnlyConnection();
/* 797 */       stmt = conn.prepareStatement(this.dbms.getSQL("SELECT TROUBLECODE FROM DTC"));
/* 798 */       rs = stmt.executeQuery();
/* 799 */       rs.setFetchSize(10000);
/* 800 */       int count = 0;
/* 801 */       while (rs.next()) {
/* 802 */         String dtc = rs.getString("TROUBLECODE");
/* 803 */         dtcs.add(dtc);
/* 804 */         count++;
/*     */       } 
/* 806 */       Collections.sort(dtcs);
/* 807 */       log.debug("loaded " + count + " dtcs.");
/* 808 */       return dtcs;
/* 809 */     } catch (Exception e) {
/* 810 */       log.error("loading dtcs failed. -exception: " + e, e);
/*     */     } finally {
/* 812 */       release(conn, stmt, rs);
/*     */     } 
/* 814 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected List loadInspections(final Integer sitQ, final CTOC ctoc) {
/*     */     try {
/* 820 */       Map inspectionsIds = this.inspectionsSupport.getObjects(Collections.singleton(sitQ), new CachedRetrievalSupportV3.Callback()
/*     */           {
/*     */             public void setParameters(Object identifier, PreparedStatement stmt) throws SQLException {
/* 823 */               stmt.setInt(1, sitQ.intValue());
/*     */             }
/*     */ 
/*     */             
/*     */             public void initStatement(PreparedStatement stmt) throws SQLException {}
/*     */             
/*     */             public Object createObject(Object identifier, ResultSet rs) throws Exception {
/* 830 */               List<SITOCElement> ret = new LinkedList();
/*     */               while (true) {
/* 832 */                 int sioId = Integer.valueOf(rs.getInt("SIO_ID")).intValue();
/* 833 */                 SITOCElement sio = ctoc.loadContent(Integer.valueOf(sioId));
/* 834 */                 SIStore.this.getProperties((SIO)sio);
/* 835 */                 ret.add(sio);
/* 836 */                 if (!rs.next())
/* 837 */                   return ret; 
/*     */               } 
/*     */             }
/*     */             public Object createKey(Object identifier) {
/* 841 */               return sitQ;
/*     */             }
/*     */             
/*     */             public String getQuery() {
/* 845 */               return "SELECT SIO_ID FROM INSPECTIONS WHERE TOC_PARENT = ?";
/*     */             }
/*     */           });
/* 848 */       return (List)inspectionsIds.get(sitQ);
/*     */     }
/* 850 */     catch (Exception e) {
/* 851 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected List getTSBs(final Integer tsbSITNodeId, final CTOC ctoc) {
/*     */     try {
/* 858 */       Map tsbIds = this.tsbSupport.getObjects(Collections.singleton(Integer.valueOf(CTOCDomain.TSB.ord())), new CachedRetrievalSupportV3.Callback()
/*     */           {
/*     */             public void setParameters(Object identifier, PreparedStatement stmt) throws SQLException {
/* 861 */               stmt.setString(1, "%" + tsbSITNodeId + "%");
/*     */             }
/*     */ 
/*     */             
/*     */             public void initStatement(PreparedStatement stmt) throws SQLException {}
/*     */ 
/*     */             
/*     */             public Object createObject(Object identifier, ResultSet rs) throws Exception {
/* 869 */               List<SIOTSBImpl> ret = new LinkedList();
/*     */               while (true) {
/* 871 */                 Integer sioId = Integer.valueOf(rs.getInt("SIO_ID"));
/* 872 */                 SITOCElement sio = ctoc.loadContent(sioId);
/* 873 */                 SIStore.this.getProperties((SIO)sio);
/* 874 */                 if (sio instanceof SIOLU) {
/* 875 */                   String assemblies = (String)((SIOLU)sio).getProperty((SITOCProperty)SIOProperty.AssemblyGoups);
/* 876 */                   if (assemblies != null) {
/*     */ 
/*     */                     
/* 879 */                     StringTokenizer t = new StringTokenizer(assemblies, ",");
/* 880 */                     while (t.hasMoreTokens())
/* 881 */                     { String agSIO = t.nextToken();
/* 882 */                       SIOTSBImpl proxy = new SIOTSBImpl((SIOLU)sio, agSIO, SIStore.this.lvcAdapter);
/* 883 */                       ret.add(proxy); } 
/*     */                   } 
/* 885 */                 } else if (sio instanceof SIOProxy) {
/* 886 */                   String agSIO = (String)((SIOProxy)sio).getProperty((SITOCProperty)SIOProperty.AssemblyGoups);
/* 887 */                   SIOTSBImpl proxy = new SIOTSBImpl((SIOLU)((SIOProxy)sio).getSIO(), agSIO, SIStore.this.lvcAdapter);
/* 888 */                   ret.add(proxy);
/*     */                 } 
/* 890 */                 if (!rs.next())
/* 891 */                   return ret; 
/*     */               } 
/*     */             }
/*     */             public Object createKey(Object identifier) {
/* 895 */               return Integer.valueOf(CTOCDomain.TSB.ord());
/*     */             }
/*     */             
/*     */             public String getQuery() {
/* 899 */               return "SELECT SIO_ID FROM SIO_PROPERTY WHERE PROPERTY_TYPE=4 AND PROPERTY LIKE ?";
/*     */             }
/*     */           });
/* 902 */       return (List)tsbIds.get(Integer.valueOf(CTOCDomain.TSB.ord()));
/*     */     }
/* 904 */     catch (Exception e) {
/* 905 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected synchronized Map provideVCRs(List vcrs) {
/* 910 */     return this.lvcAdapter.getVCRs(vcrs);
/*     */   }
/*     */   
/*     */   public byte[] getScreenData(String identifier) throws Exception {
/* 914 */     byte[] result = null;
/*     */     
/* 916 */     PreparedStatement stmt = this.stmtMngrTechScreenData.getStatement("select a.transfer_file from tech3xblobs a where transfer_name=?");
/*     */     try {
/* 918 */       stmt.setString(1, Util.toUpperCase(identifier));
/* 919 */       ResultSet rs = stmt.executeQuery();
/*     */       try {
/* 921 */         if (rs.next()) {
/* 922 */           Blob blob = rs.getBlob(1);
/* 923 */           InputStream is = blob.getBinaryStream();
/*     */           try {
/* 925 */             result = StreamUtil.readFully(is);
/*     */           } finally {
/* 927 */             is.close();
/*     */           } 
/*     */         } 
/* 930 */         return result;
/*     */       } finally {
/*     */         
/* 933 */         JDBCUtil.close(rs, log);
/*     */       } 
/*     */     } finally {
/*     */       
/* 937 */       this.stmtMngrTechScreenData.releaseStatement(stmt);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static interface FallbackLocaleCallback {
/*     */     List getFallbackLocaleIDs(Integer param1Integer, LocaleInfo param1LocaleInfo);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\db\SIStore.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */