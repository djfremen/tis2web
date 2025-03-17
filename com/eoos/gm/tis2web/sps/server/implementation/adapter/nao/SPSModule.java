/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.nao;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.DownloadServer;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.SPSException;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.COP;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Part;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonException;
/*     */ import com.eoos.gm.tis2web.sps.common.implementation.SPSBlobImpl;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSModule;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSPart;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSSchemaAdapter;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.sql.Blob;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class SPSModule
/*     */   implements SPSModule
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  31 */   private static Logger log = Logger.getLogger(SPSModule.class); protected String id; protected String description; protected String calibration; protected SPSPart origin; protected Part part; protected boolean mismatchCVN; protected List allCVNs; protected boolean prom; protected boolean eil; private static final String QUERY_KNOWN_PART_COP = "select New_Part_No from COP where New_Part_No=?";
/*     */   private static final String QUERY_KNOWN_PART_EIL = "select Calibration_Part_No from EIL where Calibration_Part_No=?";
/*     */   private static final String QUERY_KNOWN_PART_HARDWARELIST = "select Hardware_List from Hardware_List where Hardware_List like ?";
/*     */   private static final String QUERY_KNOWN_PART_CVN = "select Part_No from CVN where Part_No=?";
/*     */   
/*     */   public static final class StaticData { public IDatabaseLink getCustomCalibrationDB() {
/*  37 */       return this.customCalibrationDB;
/*     */     }
/*     */     private IDatabaseLink customCalibrationDB;
/*     */     public void setCustomCalibrationDB(IDatabaseLink dblink) {
/*  41 */       this.customCalibrationDB = dblink;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void init() {}
/*     */ 
/*     */     
/*     */     private StaticData(SPSSchemaAdapterNAO adapter) {}
/*     */ 
/*     */     
/*     */     public static StaticData getInstance(SPSSchemaAdapterNAO adapter) {
/*  53 */       synchronized (adapter.getSyncObject()) {
/*  54 */         StaticData instance = (StaticData)adapter.getObject(StaticData.class);
/*  55 */         if (instance == null) {
/*  56 */           instance = new StaticData(adapter);
/*  57 */           adapter.storeObject(StaticData.class, instance);
/*     */         } 
/*  59 */         return instance;
/*     */       } 
/*     */     } }
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
/*     */   public SPSModule(SPSLanguage language, String id, SPSPart origin) {
/*  85 */     this.id = id;
/*  86 */     this.description = null;
/*  87 */     this.origin = origin;
/*  88 */     this.calibration = ApplicationContext.getInstance().getLabel(language.getJavaLocale(), "sps.calibration.unknown");
/*     */   }
/*     */ 
/*     */   
/*     */   public SPSModule(SPSLanguage language, int id, SPSPart origin) {
/*  93 */     this.id = Integer.toString(id);
/*  94 */     String prefix = ApplicationContext.getInstance().getLabel(language.getJavaLocale(), "sps.prom.module");
/*  95 */     this.description = prefix + " " + id;
/*  96 */     this.origin = origin;
/*  97 */     this.prom = true;
/*     */   }
/*     */   
/*     */   public boolean isPROM() {
/* 101 */     return this.prom;
/*     */   }
/*     */   
/*     */   public boolean isEIL() {
/* 105 */     return this.eil;
/*     */   }
/*     */   
/*     */   public void flagEIL() {
/* 109 */     this.eil = true;
/*     */   }
/*     */   
/*     */   public String getDenotation(Locale locale) {
/* 113 */     return this.description;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 117 */     return this.description;
/*     */   }
/*     */   
/*     */   public String getID() {
/* 121 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 125 */     return this.description;
/*     */   }
/*     */   
/*     */   public String getCurrentCalibration() {
/* 129 */     return this.calibration;
/*     */   }
/*     */   
/*     */   public Part getOriginPart() {
/* 133 */     return (Part)this.origin;
/*     */   }
/*     */   
/*     */   public Part getSelectedPart() {
/* 137 */     return this.part;
/*     */   }
/*     */   
/*     */   public void setSelectedPart(Part part) {
/* 141 */     this.part = part;
/*     */   }
/*     */   
/*     */   public boolean isSelectablePart(Part part) {
/* 145 */     return (part.getCOP() == null);
/*     */   }
/*     */   
/*     */   boolean isCurrentCalibration(String part) {
/* 149 */     return isCurrentCalibration((Part)this.origin, part);
/*     */   }
/*     */   
/*     */   void setCurrentCalibration(SPSLanguage language, String part, SPSSchemaAdapterNAO adapter) {
/* 153 */     setCurrentCalibration(language, part, false, false, null, adapter);
/*     */   }
/*     */   
/*     */   void setCurrentCalibration(SPSLanguage language, String part, boolean checkCVN, boolean isEIL, List partnums, SPSSchemaAdapterNAO adapter) {
/* 157 */     if (part == null) {
/* 158 */       if (language == null) {
/* 159 */         this.calibration = "*";
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 165 */     if (part.length() > 8) {
/* 166 */       checkCVN = false;
/*     */     }
/* 168 */     else if (partnums == null || partnums.size() == 0) {
/* 169 */       checkCVN = false;
/*     */     } 
/* 171 */     if (isCurrentCalibration((Part)this.origin, part)) {
/* 172 */       if (checkCVN) {
/* 173 */         checkCVN(isEIL, part, partnums, adapter);
/*     */       }
/* 175 */     } else if (isRecognizedPart(part, adapter)) {
/* 176 */       this.calibration = part;
/*     */ 
/*     */ 
/*     */       
/* 180 */       if (checkCVN) {
/* 181 */         checkCVN(isEIL, part, partnums, adapter);
/*     */       }
/*     */     } else {
/* 184 */       this.calibration = "*" + part;
/*     */     } 
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
/* 196 */   private static final String[] QUERYS_KNOWN_PART = new String[] { "select New_Part_No from COP where New_Part_No=?", "select Calibration_Part_No from EIL where Calibration_Part_No=?", "select Hardware_List from Hardware_List where Hardware_List like ?", "select Part_No from CVN where Part_No=?" };
/*     */   
/*     */   private boolean isRecognizedPart(String part, SPSSchemaAdapterNAO adapter) {
/* 199 */     log.debug("determine if part " + String.valueOf(part) + " is known ");
/* 200 */     boolean retValue = false;
/*     */     try {
/* 202 */       part = part.trim();
/* 203 */       IDatabaseLink dbLink = adapter.getDatabaseLink();
/* 204 */       Connection connection = dbLink.requestConnection();
/*     */       try {
/* 206 */         for (int i = 0; i < QUERYS_KNOWN_PART.length && !retValue; i++) {
/* 207 */           log.debug("... executing query: " + QUERYS_KNOWN_PART[i]);
/* 208 */           DBMS.PreparedStatement statement = DBMS.prepareSQLStatement(connection, QUERYS_KNOWN_PART[i]);
/*     */           try {
/* 210 */             if (QUERYS_KNOWN_PART[i] == "select Hardware_List from Hardware_List where Hardware_List like ?") {
/* 211 */               statement.setString(1, "%" + part + "%");
/*     */             } else {
/* 213 */               statement.setInt(1, Integer.parseInt(part));
/*     */             } 
/* 215 */             ResultSet rs = statement.executeQuery();
/*     */             try {
/* 217 */               retValue = rs.next();
/*     */             } finally {
/*     */               try {
/* 220 */                 rs.close();
/* 221 */               } catch (Exception e) {}
/*     */             } 
/*     */           } finally {
/*     */             
/*     */             try {
/* 226 */               statement.close();
/* 227 */             } catch (Exception e) {}
/*     */           }
/*     */         
/*     */         } 
/*     */       } finally {
/*     */         
/* 233 */         dbLink.releaseConnection(connection);
/*     */       } 
/* 235 */     } catch (Exception e) {
/* 236 */       if (part != null && part.length() == 0) {
/* 237 */         log.info("unable to determine if part " + String.valueOf(part) + " is known, returning false");
/*     */       } else {
/* 239 */         log.error("unable to determine if part " + String.valueOf(part) + " is known, returning false - exception:", e);
/*     */       } 
/*     */     } 
/* 242 */     log.debug("...result: " + retValue);
/* 243 */     return retValue;
/*     */   }
/*     */   
/*     */   protected void checkCVN(boolean isEIL, String part, List partnums, SPSSchemaAdapterNAO adapter) {
/* 247 */     String cvn = extractCVN(partnums, part);
/* 248 */     if (!isEIL) {
/* 249 */       String dbcvn = lookupCVN(part, adapter);
/*     */       
/* 251 */       addAllCVN(part, dbcvn, cvn);
/* 252 */       if (dbcvn == null || cvn == null || !SPSCVN.match(dbcvn, cvn)) {
/* 253 */         this.mismatchCVN = true;
/*     */       }
/*     */     } else {
/*     */       try {
/* 257 */         List<String> parts = getCalibrationInfo(Integer.parseInt(part), adapter);
/* 258 */         if (parts != null) {
/* 259 */           for (int i = 1; i < parts.size(); i++) {
/*     */             
/* 261 */             String pno = parts.get(i);
/* 262 */             String dbcvn = lookupCVN(pno, adapter);
/* 263 */             cvn = extractCVN(partnums, pno);
/*     */             
/* 265 */             if (cvn != null) {
/* 266 */               addAllCVN(part, dbcvn, cvn);
/*     */             }
/* 268 */             if (dbcvn == null || cvn == null || !SPSCVN.match(dbcvn, cvn)) {
/* 269 */               this.mismatchCVN = true;
/*     */             }
/*     */           } 
/*     */         }
/* 273 */       } catch (Exception e) {
/* 274 */         log.error("failed to check cvn (eil='" + part + "')", e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected String extractCVN(List<String> partnums, String part) {
/* 280 */     if (partnums != null) {
/* 281 */       String target = part + '-';
/* 282 */       for (int i = 0; i < partnums.size(); i++) {
/* 283 */         String pno = partnums.get(i);
/* 284 */         if (pno.startsWith(target)) {
/* 285 */           return pno.substring(pno.lastIndexOf('-') + 1);
/*     */         }
/*     */       } 
/*     */     } 
/* 289 */     return null;
/*     */   }
/*     */   
/*     */   protected void addAllCVN(String part, String cvnDB, String cvnVIT1) {
/* 293 */     if (this.allCVNs == null) {
/* 294 */       this.allCVNs = new ArrayList();
/*     */     }
/* 296 */     SPSCVN record = new SPSCVN(part, cvnDB, cvnVIT1);
/* 297 */     this.allCVNs.add(record);
/*     */   }
/*     */   
/*     */   public List getAllCVNs() {
/* 301 */     return this.allCVNs;
/*     */   }
/*     */   
/*     */   protected String lookupCVN(String part, SPSSchemaAdapterNAO adapter) {
/* 305 */     return SPSPart.getCVN(part, adapter);
/*     */   }
/*     */   
/*     */   protected boolean isCurrentCalibration(Part part, String calibration) {
/* 309 */     if (calibration.equals(part.getPartNumber())) {
/* 310 */       this.calibration = calibration;
/* 311 */       return true;
/*     */     } 
/* 313 */     List<COP> cop = part.getCOP();
/* 314 */     if (cop != null) {
/* 315 */       for (int i = 0; i < cop.size(); i++) {
/* 316 */         COP link = cop.get(i);
/* 317 */         if (isCurrentCalibration(link.getPart(), calibration)) {
/* 318 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 323 */     return false;
/*     */   }
/*     */   
/*     */   boolean isCalibrationUpdate() {
/* 327 */     if (this.calibration == null) {
/* 328 */       return true;
/*     */     }
/* 330 */     return !this.calibration.equals(this.part.getPartNumber());
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isMismatchCVN() {
/* 335 */     return this.mismatchCVN;
/*     */   }
/*     */   
/*     */   void setDescription(String description) {
/* 339 */     this.description = description;
/*     */   }
/*     */   
/*     */   void init() {
/* 343 */     if (this.description == null) {
/* 344 */       this.description = this.origin.getShortDescription(null);
/* 345 */       if (this.description == null) {
/* 346 */         this.description = this.origin.getDescription(null);
/*     */       }
/*     */     } 
/* 349 */     this.part = findDefaultPart(this.origin.getCOP());
/*     */   }
/*     */   
/*     */   protected Part findDefaultPart(List<COP> cop) {
/* 353 */     if (cop == null)
/* 354 */       return (Part)this.origin; 
/* 355 */     if (cop.size() > 1)
/*     */     {
/* 357 */       return null;
/*     */     }
/* 359 */     COP link = cop.get(0);
/* 360 */     if (link.getMode() == 1) {
/* 361 */       link.setMode(3);
/* 362 */       return link.getPart();
/*     */     } 
/* 364 */     return findDefaultPart(link.getPart().getCOP());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static List getCalibrationInfo(int part, SPSSchemaAdapterNAO adapter) throws Exception {
/* 370 */     IDatabaseLink dblink = adapter.getDatabaseLink();
/* 371 */     List<String> calibrations = new ArrayList();
/* 372 */     Connection conn = null;
/* 373 */     DBMS.PreparedStatement stmt = null;
/* 374 */     ResultSet rs = null;
/*     */     try {
/* 376 */       conn = dblink.requestConnection();
/* 377 */       String sql = DBMS.getSQL(dblink, "SELECT Module_Id, Calibration_Part_No FROM EIL WHERE End_Item_No = ? ORDER BY Module_Id");
/* 378 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 379 */       stmt.setInt(1, part);
/* 380 */       rs = stmt.executeQuery();
/* 381 */       while (rs.next()) {
/* 382 */         rs.getInt(1);
/* 383 */         Integer calibration = Integer.valueOf(rs.getInt(2));
/* 384 */         calibrations.add(calibration.toString());
/*     */       } 
/* 386 */     } catch (Exception e) {
/* 387 */       throw e;
/*     */     } finally {
/*     */       try {
/* 390 */         if (rs != null) {
/* 391 */           rs.close();
/*     */         }
/* 393 */         if (stmt != null) {
/* 394 */           stmt.close();
/*     */         }
/* 396 */         if (conn != null) {
/* 397 */           dblink.releaseConnection(conn);
/*     */         }
/* 399 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 402 */     return calibrations;
/*     */   }
/*     */   
/*     */   public static List getCalibrationInfoEIL(int part, SPSSchemaAdapterNAO adapter) throws Exception {
/* 406 */     IDatabaseLink dblink = adapter.getDatabaseLink();
/* 407 */     List<List<Integer>> calibrations = new ArrayList();
/* 408 */     Connection conn = null;
/* 409 */     DBMS.PreparedStatement stmt = null;
/* 410 */     ResultSet rs = null;
/*     */     try {
/* 412 */       conn = dblink.requestConnection();
/* 413 */       String sql = DBMS.getSQL(dblink, "SELECT Module_Id, Calibration_Part_No FROM EIL WHERE End_Item_No = ? ORDER BY Module_Id");
/* 414 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 415 */       stmt.setInt(1, part);
/* 416 */       rs = stmt.executeQuery();
/* 417 */       while (rs.next()) {
/* 418 */         int module = rs.getInt(1);
/* 419 */         Integer calibration = Integer.valueOf(rs.getInt(2));
/* 420 */         List<Integer> data = new ArrayList();
/* 421 */         data.add(Integer.valueOf(module));
/* 422 */         data.add(calibration);
/* 423 */         calibrations.add(data);
/*     */       } 
/* 425 */     } catch (Exception e) {
/* 426 */       throw e;
/*     */     } finally {
/*     */       try {
/* 429 */         if (rs != null) {
/* 430 */           rs.close();
/*     */         }
/* 432 */         if (stmt != null) {
/* 433 */           stmt.close();
/*     */         }
/* 435 */         if (conn != null) {
/* 436 */           dblink.releaseConnection(conn);
/*     */         }
/* 438 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 441 */     return calibrations;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static String strip11567EIL(String sql) {
/* 446 */     return StringUtilities.replace(sql, ", b.DOWNLOAD_SITE", "");
/*     */   }
/*     */   
/*     */   public static List getCalibrationFileInfo(int part, SPSSchemaAdapterNAO adapter) throws Exception {
/* 450 */     IDatabaseLink dblink = adapter.getDatabaseLink();
/* 451 */     List<SPSBlobImpl> files = new ArrayList();
/* 452 */     Connection conn = null;
/* 453 */     DBMS.PreparedStatement stmt = null;
/* 454 */     ResultSet rs = null;
/*     */     try {
/* 456 */       conn = dblink.requestConnection();
/* 457 */       String sql = DBMS.getSQL(dblink, "SELECT a.Module_Id, a.Calibration_Part_No, FILESIZE, FILECHECKSUM, b.DOWNLOAD_SITE  FROM EIL a LEFT OUTER JOIN Calibration_Files b ON a.Calibration_Part_No = b.Calibration_Part_No WHERE a.End_Item_No = ? ORDER BY a.Module_Id");
/* 458 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 459 */       stmt.setInt(1, part);
/*     */       try {
/* 461 */         rs = stmt.executeQuery();
/* 462 */       } catch (Exception s) {
/* 463 */         stmt.close();
/* 464 */         stmt = DBMS.prepareSQLStatement(conn, strip11567EIL(sql));
/* 465 */         stmt.setInt(1, part);
/* 466 */         rs = stmt.executeQuery();
/*     */       } 
/* 468 */       while (rs.next()) {
/* 469 */         int module = rs.getInt(1);
/* 470 */         Integer calibration = Integer.valueOf(rs.getInt(2));
/* 471 */         Integer size = Integer.valueOf(rs.getInt(3));
/* 472 */         if (rs.wasNull()) {
/* 473 */           throw new SPSException(CommonException.MissingCalibrationFile);
/*     */         }
/* 475 */         String site = null;
/*     */         try {
/* 477 */           site = rs.getString(5);
/* 478 */         } catch (Exception x) {}
/*     */         
/* 480 */         DownloadServer dwnldServer = Util.isNullOrEmpty(site) ? null : adapter.getCalibrationDataDownloadSite();
/* 481 */         if (dblink.getDBMS() == 2) {
/* 482 */           files.add(new SPSBlobImpl(calibration.toString(), Integer.valueOf(module), size, null, dwnldServer, null)); continue;
/*     */         } 
/* 484 */         String checksum = rs.getString(4);
/* 485 */         files.add(new SPSBlobImpl(calibration.toString(), Integer.valueOf(module), size, StringUtilities.hexToBytes(checksum), dwnldServer, null));
/*     */       }
/*     */     
/* 488 */     } catch (Exception e) {
/* 489 */       if (dblink.getDBMS() == 3) {
/* 490 */         return new ArrayList();
/*     */       }
/* 492 */       throw e;
/*     */     } finally {
/*     */       
/*     */       try {
/* 496 */         if (rs != null) {
/* 497 */           rs.close();
/*     */         }
/* 499 */         if (stmt != null) {
/* 500 */           stmt.close();
/*     */         }
/* 502 */         if (conn != null) {
/* 503 */           dblink.releaseConnection(conn);
/*     */         }
/* 505 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 508 */     if (files.size() == 0) {
/* 509 */       throw new SPSException(CommonException.MissingCalibrationFile);
/*     */     }
/* 511 */     return files;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static String strip11567(String sql) {
/* 517 */     return StringUtilities.replace(sql, ", DOWNLOAD_SITE", "");
/*     */   }
/*     */   
/*     */   public static List getCalibrationFileInfo(List<SPSPart> parts, SPSSchemaAdapterNAO adapter, boolean requiresCustomCalibration) throws Exception {
/* 521 */     IDatabaseLink dblink = requiresCustomCalibration ? StaticData.getInstance(adapter).getCustomCalibrationDB() : adapter.getDatabaseLink();
/* 522 */     List files = new ArrayList();
/* 523 */     Map<Object, Object> map = new HashMap<Object, Object>();
/* 524 */     Connection conn = null;
/* 525 */     DBMS.PreparedStatement stmt = null;
/* 526 */     ResultSet rs = null;
/*     */     try {
/* 528 */       conn = dblink.requestConnection();
/* 529 */       String sql = DBMS.getSQL(dblink, "SELECT Calibration_Part_No, FILESIZE, FILECHECKSUM, DOWNLOAD_SITE FROM Calibration_Files WHERE Calibration_Part_No IN (#list#)", parts.size());
/* 530 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 531 */       for (int j = 0; j < parts.size(); j++) {
/* 532 */         SPSPart calibration = parts.get(j);
/* 533 */         stmt.setInt(j + 1, calibration.getID());
/*     */       } 
/*     */       try {
/* 536 */         rs = stmt.executeQuery();
/* 537 */       } catch (Exception s) {
/* 538 */         stmt.close();
/* 539 */         stmt = DBMS.prepareSQLStatement(conn, strip11567(sql));
/* 540 */         for (int k = 0; k < parts.size(); k++) {
/* 541 */           SPSPart calibration = parts.get(k);
/* 542 */           stmt.setInt(k + 1, calibration.getID());
/*     */         } 
/* 544 */         rs = stmt.executeQuery();
/*     */       } 
/* 546 */       while (rs.next()) {
/* 547 */         Integer calibration = Integer.valueOf(rs.getInt(1));
/* 548 */         Integer size = Integer.valueOf(rs.getInt(2));
/* 549 */         String site = null;
/*     */         try {
/* 551 */           site = rs.getString(4);
/* 552 */         } catch (Exception x) {}
/*     */         
/* 554 */         DownloadServer dwnldServer = Util.isNullOrEmpty(site) ? null : adapter.getCalibrationDataDownloadSite();
/* 555 */         if (dblink.getDBMS() == 2) {
/* 556 */           map.put(calibration, new SPSBlobImpl(calibration.toString(), calibration, size, null, dwnldServer, null)); continue;
/*     */         } 
/* 558 */         String checksum = rs.getString(3);
/* 559 */         map.put(calibration, new SPSBlobImpl(calibration.toString(), calibration, size, StringUtilities.hexToBytes(checksum), dwnldServer, null));
/*     */       }
/*     */     
/* 562 */     } catch (Exception e) {
/* 563 */       if (dblink.getDBMS() == 3) {
/* 564 */         return new ArrayList();
/*     */       }
/* 566 */       throw e;
/*     */     } finally {
/*     */       
/*     */       try {
/* 570 */         if (rs != null) {
/* 571 */           rs.close();
/*     */         }
/* 573 */         if (stmt != null) {
/* 574 */           stmt.close();
/*     */         }
/* 576 */         if (conn != null) {
/* 577 */           dblink.releaseConnection(conn);
/*     */         }
/* 579 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 582 */     if (map.size() != parts.size()) {
/* 583 */       throw new SPSException(CommonException.MissingCalibrationFile);
/*     */     }
/* 585 */     for (int i = 0; i < parts.size(); i++) {
/* 586 */       SPSPart calibration = parts.get(i);
/* 587 */       files.add(map.get(Integer.valueOf(calibration.getID())));
/*     */     } 
/* 589 */     return files;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getCalibrationFile(SPSPart calibration, SPSSchemaAdapter adapter, boolean requiresCustomCalibration) throws Exception {
/* 594 */     return getCalibrationFile(calibration.getID(), (SPSSchemaAdapterNAO)adapter, requiresCustomCalibration);
/*     */   }
/*     */   
/*     */   public static byte[] getCalibrationFile(int calibration, SPSSchemaAdapterNAO adapter, boolean requiresCustomCalibration) throws Exception {
/* 598 */     IDatabaseLink dblink = requiresCustomCalibration ? StaticData.getInstance(adapter).getCustomCalibrationDB() : adapter.getDatabaseLink();
/* 599 */     Connection conn = null;
/* 600 */     DBMS.PreparedStatement stmt = null;
/* 601 */     ResultSet rs = null;
/*     */     try {
/* 603 */       conn = dblink.requestConnection();
/* 604 */       String sql = DBMS.getSQL(dblink, "SELECT Calibration_File FROM Calibration_Files WHERE Calibration_Part_No = ?");
/* 605 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 606 */       stmt.setInt(1, calibration);
/* 607 */       rs = stmt.executeQuery();
/* 608 */       if (rs.next()) {
/* 609 */         if (dblink.getDBMS() == 2) {
/* 610 */           return rs.getBytes(1);
/*     */         }
/* 612 */         Blob blob = rs.getBlob(1);
/* 613 */         return blob.getBytes(1L, (int)blob.length());
/*     */       } 
/*     */       
/* 616 */       return null;
/*     */     }
/* 618 */     catch (Exception e) {
/* 619 */       throw e;
/*     */     } finally {
/*     */       try {
/* 622 */         if (rs != null) {
/* 623 */           rs.close();
/*     */         }
/* 625 */         if (stmt != null) {
/* 626 */           stmt.close();
/*     */         }
/* 628 */         if (conn != null) {
/* 629 */           dblink.releaseConnection(conn);
/*     */         }
/* 631 */       } catch (Exception x) {
/* 632 */         log.error("failed to clean-up: ", x);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   static void initCustomCalibrations(SPSSchemaAdapterNAO adapter, IDatabaseLink db) throws Exception {
/* 638 */     StaticData.getInstance(adapter).setCustomCalibrationDB(db);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\nao\SPSModule.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */