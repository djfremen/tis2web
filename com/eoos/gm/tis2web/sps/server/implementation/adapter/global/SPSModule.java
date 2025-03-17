/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.global;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.SPSException;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.COP;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Part;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonException;
/*     */ import com.eoos.gm.tis2web.sps.common.implementation.SPSBlobImpl;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSModule;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSPart;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSSchemaAdapter;
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
/*  29 */   private static Logger log = Logger.getLogger(SPSModule.class); protected String id; protected String description; protected String calibration; protected SPSPart origin; protected Part part; protected boolean mismatchCVN; protected List allCVNs; protected boolean prom; protected boolean eil; private static final String QUERY_KNOWN_PART_COP = "select new_part_no from cop where new_part_no=?";
/*     */   private static final String QUERY_KNOWN_PART_EIL = "select calibration_part_no from eil where calibration_part_no=?";
/*     */   private static final String QUERY_KNOWN_PART_HARDWARELIST = "select hardware_list from hardware_list where hardware_list like ?";
/*     */   private static final String QUERY_KNOWN_PART_CVN = "select part_no from cvn where part_no=?";
/*     */   
/*     */   public static final class StaticData { public IDatabaseLink getCustomCalibrationDB() {
/*  35 */       return this.customCalibrationDB;
/*     */     }
/*     */     private IDatabaseLink customCalibrationDB;
/*     */     public void setCustomCalibrationDB(IDatabaseLink dblink) {
/*  39 */       this.customCalibrationDB = dblink;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void init() {}
/*     */ 
/*     */     
/*     */     private StaticData(SPSSchemaAdapterGlobal adapter) {}
/*     */ 
/*     */     
/*     */     public static StaticData getInstance(SPSSchemaAdapterGlobal adapter) {
/*  51 */       synchronized (adapter.getSyncObject()) {
/*  52 */         StaticData instance = (StaticData)adapter.getObject(StaticData.class);
/*  53 */         if (instance == null) {
/*  54 */           instance = new StaticData(adapter);
/*  55 */           adapter.storeObject(StaticData.class, instance);
/*     */         } 
/*  57 */         return instance;
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
/*  83 */     this.id = id;
/*  84 */     this.description = null;
/*  85 */     this.origin = origin;
/*  86 */     this.calibration = ApplicationContext.getInstance().getLabel(language.getJavaLocale(), "sps.calibration.unknown");
/*     */   }
/*     */ 
/*     */   
/*     */   public SPSModule(SPSLanguage language, int id, SPSPart origin) {
/*  91 */     this.id = Integer.toString(id);
/*  92 */     String prefix = ApplicationContext.getInstance().getLabel(language.getJavaLocale(), "sps.prom.module");
/*  93 */     this.description = prefix + " " + id;
/*  94 */     this.origin = origin;
/*  95 */     this.prom = true;
/*     */   }
/*     */   
/*     */   public boolean isPROM() {
/*  99 */     return this.prom;
/*     */   }
/*     */   
/*     */   public boolean isEIL() {
/* 103 */     return this.eil;
/*     */   }
/*     */   
/*     */   public void flagEIL() {
/* 107 */     this.eil = true;
/*     */   }
/*     */   
/*     */   public String getDenotation(Locale locale) {
/* 111 */     return this.description;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 115 */     return this.description;
/*     */   }
/*     */   
/*     */   public String getID() {
/* 119 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 123 */     return this.description;
/*     */   }
/*     */   
/*     */   public String getCurrentCalibration() {
/* 127 */     return this.calibration;
/*     */   }
/*     */   
/*     */   public Part getOriginPart() {
/* 131 */     return (Part)this.origin;
/*     */   }
/*     */   
/*     */   public Part getSelectedPart() {
/* 135 */     return this.part;
/*     */   }
/*     */   
/*     */   public void setSelectedPart(Part part) {
/* 139 */     this.part = part;
/*     */   }
/*     */   
/*     */   public boolean isSelectablePart(Part part) {
/* 143 */     return (part.getCOP() == null);
/*     */   }
/*     */   
/*     */   boolean isCurrentCalibration(String part) {
/* 147 */     return isCurrentCalibration((Part)this.origin, part);
/*     */   }
/*     */   
/*     */   void setCurrentCalibration(SPSLanguage language, String part, SPSSchemaAdapterGlobal adapter) {
/* 151 */     setCurrentCalibration(language, part, false, false, null, adapter);
/*     */   }
/*     */   
/*     */   void setCurrentCalibration(SPSLanguage language, String part, boolean checkCVN, boolean isEIL, List partnums, SPSSchemaAdapterGlobal adapter) {
/* 155 */     if (part == null) {
/* 156 */       if (language == null) {
/* 157 */         this.calibration = "*";
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 163 */     if (part.length() > 8) {
/* 164 */       checkCVN = false;
/*     */     }
/* 166 */     else if (partnums == null || partnums.size() == 0) {
/* 167 */       checkCVN = false;
/*     */     } 
/* 169 */     if (isCurrentCalibration((Part)this.origin, part)) {
/* 170 */       if (checkCVN) {
/* 171 */         checkCVN(isEIL, part, partnums, adapter);
/*     */       }
/* 173 */     } else if (isRecognizedPart(part, adapter)) {
/* 174 */       this.calibration = part;
/*     */ 
/*     */ 
/*     */       
/* 178 */       if (checkCVN) {
/* 179 */         checkCVN(isEIL, part, partnums, adapter);
/*     */       }
/*     */     } else {
/* 182 */       this.calibration = "*" + part;
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
/* 194 */   private static final String[] QUERYS_KNOWN_PART = new String[] { "select new_part_no from cop where new_part_no=?", "select calibration_part_no from eil where calibration_part_no=?", "select hardware_list from hardware_list where hardware_list like ?", "select part_no from cvn where part_no=?" };
/*     */   
/*     */   private boolean isRecognizedPart(String part, SPSSchemaAdapterGlobal adapter) {
/* 197 */     log.debug("determine if part " + String.valueOf(part) + " is known ");
/* 198 */     boolean retValue = false;
/*     */     try {
/* 200 */       part = part.trim();
/* 201 */       IDatabaseLink dbLink = adapter.getDatabaseLink();
/* 202 */       Connection connection = dbLink.requestConnection();
/*     */       try {
/* 204 */         for (int i = 0; i < QUERYS_KNOWN_PART.length && !retValue; i++) {
/* 205 */           log.debug("... executing query: " + QUERYS_KNOWN_PART[i]);
/* 206 */           DBMS.PreparedStatement statement = DBMS.prepareSQLStatement(connection, QUERYS_KNOWN_PART[i]);
/*     */           try {
/* 208 */             if (QUERYS_KNOWN_PART[i] == "select hardware_list from hardware_list where hardware_list like ?") {
/* 209 */               statement.setString(1, "%" + part + "%");
/*     */             } else {
/* 211 */               statement.setInt(1, Integer.parseInt(part));
/*     */             } 
/* 213 */             ResultSet rs = statement.executeQuery();
/*     */             try {
/* 215 */               retValue = rs.next();
/*     */             } finally {
/*     */               try {
/* 218 */                 rs.close();
/* 219 */               } catch (Exception e) {}
/*     */             } 
/*     */           } finally {
/*     */             
/*     */             try {
/* 224 */               statement.close();
/* 225 */             } catch (Exception e) {}
/*     */           }
/*     */         
/*     */         } 
/*     */       } finally {
/*     */         
/* 231 */         dbLink.releaseConnection(connection);
/*     */       } 
/* 233 */     } catch (Exception e) {
/* 234 */       log.error("unable to determine if part " + String.valueOf(part) + " is known, returing false - exception:" + e, e);
/*     */     } 
/* 236 */     log.debug("...result: " + retValue);
/* 237 */     return retValue;
/*     */   }
/*     */   
/*     */   protected void checkCVN(boolean isEIL, String part, List partnums, SPSSchemaAdapterGlobal adapter) {
/* 241 */     String cvn = extractCVN(partnums, part);
/* 242 */     if (!isEIL) {
/* 243 */       String dbcvn = lookupCVN(part, adapter);
/*     */       
/* 245 */       addAllCVN(part, dbcvn, cvn);
/* 246 */       if (dbcvn == null || cvn == null || !SPSCVN.match(dbcvn, cvn)) {
/* 247 */         this.mismatchCVN = true;
/*     */       }
/*     */     } else {
/*     */       try {
/* 251 */         List<String> parts = getCalibrationInfo(Integer.parseInt(part), adapter);
/* 252 */         if (parts != null) {
/* 253 */           for (int i = 1; i < parts.size(); i++) {
/*     */             
/* 255 */             String pno = parts.get(i);
/* 256 */             String dbcvn = lookupCVN(pno, adapter);
/* 257 */             cvn = extractCVN(partnums, pno);
/*     */             
/* 259 */             if (cvn != null) {
/* 260 */               addAllCVN(part, dbcvn, cvn);
/*     */             }
/* 262 */             if (dbcvn == null || cvn == null || !SPSCVN.match(dbcvn, cvn)) {
/* 263 */               this.mismatchCVN = true;
/*     */             }
/*     */           } 
/*     */         }
/* 267 */       } catch (Exception e) {
/* 268 */         log.error("failed to check cvn (eil='" + part + "')", e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected String extractCVN(List<String> partnums, String part) {
/* 274 */     if (partnums != null) {
/* 275 */       String target = part + '-';
/* 276 */       for (int i = 0; i < partnums.size(); i++) {
/* 277 */         String pno = partnums.get(i);
/* 278 */         if (pno.startsWith(target)) {
/* 279 */           return pno.substring(pno.lastIndexOf('-') + 1);
/*     */         }
/*     */       } 
/*     */     } 
/* 283 */     return null;
/*     */   }
/*     */   
/*     */   protected void addAllCVN(String part, String cvnDB, String cvnVIT1) {
/* 287 */     if (this.allCVNs == null) {
/* 288 */       this.allCVNs = new ArrayList();
/*     */     }
/* 290 */     SPSCVN record = new SPSCVN(part, cvnDB, cvnVIT1);
/* 291 */     this.allCVNs.add(record);
/*     */   }
/*     */   
/*     */   public List getAllCVNs() {
/* 295 */     return this.allCVNs;
/*     */   }
/*     */   
/*     */   protected String lookupCVN(String part, SPSSchemaAdapterGlobal adapter) {
/* 299 */     return SPSPart.getCVN(part, adapter);
/*     */   }
/*     */   
/*     */   protected boolean isCurrentCalibration(Part part, String calibration) {
/* 303 */     if (calibration.equals(part.getPartNumber())) {
/* 304 */       this.calibration = calibration;
/* 305 */       return true;
/*     */     } 
/* 307 */     List<COP> cop = part.getCOP();
/* 308 */     if (cop != null) {
/* 309 */       for (int i = 0; i < cop.size(); i++) {
/* 310 */         COP link = cop.get(i);
/* 311 */         if (isCurrentCalibration(link.getPart(), calibration)) {
/* 312 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 317 */     return false;
/*     */   }
/*     */   
/*     */   boolean isCalibrationUpdate() {
/* 321 */     if (this.calibration == null || this.mismatchCVN) {
/* 322 */       return true;
/*     */     }
/* 324 */     return !this.calibration.equals(this.part.getPartNumber());
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isMismatchCVN() {
/* 329 */     return this.mismatchCVN;
/*     */   }
/*     */   
/*     */   void setDescription(String description) {
/* 333 */     this.description = description;
/*     */   }
/*     */   
/*     */   void init() {
/* 337 */     if (this.description == null) {
/* 338 */       this.description = this.origin.getShortDescription(null);
/* 339 */       if (this.description == null) {
/* 340 */         this.description = this.origin.getDescription(null);
/*     */       }
/*     */     } 
/* 343 */     this.part = findDefaultPart(this.origin.getCOP());
/*     */   }
/*     */   
/*     */   protected Part findDefaultPart(List<COP> cop) {
/* 347 */     if (cop == null)
/* 348 */       return (Part)this.origin; 
/* 349 */     if (cop.size() > 1)
/*     */     {
/* 351 */       return null;
/*     */     }
/* 353 */     COP link = cop.get(0);
/* 354 */     if (link.getMode() == 1) {
/* 355 */       link.setMode(3);
/* 356 */       return link.getPart();
/*     */     } 
/* 358 */     return findDefaultPart(link.getPart().getCOP());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static List getCalibrationInfo(int part, SPSSchemaAdapterGlobal adapter) throws Exception {
/* 364 */     IDatabaseLink dblink = adapter.getDatabaseLink();
/* 365 */     List<String> calibrations = new ArrayList();
/* 366 */     Connection conn = null;
/* 367 */     DBMS.PreparedStatement stmt = null;
/* 368 */     ResultSet rs = null;
/*     */     try {
/* 370 */       conn = dblink.requestConnection();
/* 371 */       String sql = DBMS.getSQL(dblink, "SELECT Module_Id, Calibration_Part_No FROM EIL WHERE End_Item_No = ? ORDER BY Module_Id");
/* 372 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 373 */       stmt.setInt(1, part);
/* 374 */       rs = stmt.executeQuery();
/* 375 */       while (rs.next()) {
/*     */         
/* 377 */         Integer calibration = Integer.valueOf(rs.getInt(2));
/* 378 */         calibrations.add(calibration.toString());
/*     */       } 
/* 380 */     } catch (Exception e) {
/* 381 */       throw e;
/*     */     } finally {
/*     */       try {
/* 384 */         if (rs != null) {
/* 385 */           rs.close();
/*     */         }
/* 387 */         if (stmt != null) {
/* 388 */           stmt.close();
/*     */         }
/* 390 */         if (conn != null) {
/* 391 */           dblink.releaseConnection(conn);
/*     */         }
/* 393 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 396 */     return calibrations;
/*     */   }
/*     */   
/*     */   public static List getCalibrationInfoEIL(int part, SPSSchemaAdapterGlobal adapter) throws Exception {
/* 400 */     IDatabaseLink dblink = adapter.getDatabaseLink();
/* 401 */     List<List<Integer>> calibrations = new ArrayList();
/* 402 */     Connection conn = null;
/* 403 */     DBMS.PreparedStatement stmt = null;
/* 404 */     ResultSet rs = null;
/*     */     try {
/* 406 */       conn = dblink.requestConnection();
/* 407 */       String sql = DBMS.getSQL(dblink, "SELECT Module_Id, Calibration_Part_No FROM EIL WHERE End_Item_No = ? ORDER BY Module_Id");
/* 408 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 409 */       stmt.setInt(1, part);
/* 410 */       rs = stmt.executeQuery();
/* 411 */       while (rs.next()) {
/* 412 */         int module = rs.getInt(1);
/* 413 */         Integer calibration = Integer.valueOf(rs.getInt(2));
/* 414 */         List<Integer> data = new ArrayList();
/* 415 */         data.add(Integer.valueOf(module));
/* 416 */         data.add(calibration);
/* 417 */         calibrations.add(data);
/*     */       } 
/* 419 */     } catch (Exception e) {
/* 420 */       throw e;
/*     */     } finally {
/*     */       try {
/* 423 */         if (rs != null) {
/* 424 */           rs.close();
/*     */         }
/* 426 */         if (stmt != null) {
/* 427 */           stmt.close();
/*     */         }
/* 429 */         if (conn != null) {
/* 430 */           dblink.releaseConnection(conn);
/*     */         }
/* 432 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 435 */     return calibrations;
/*     */   }
/*     */   
/*     */   public static List getCalibrationFileInfo(int part, SPSSchemaAdapterGlobal adapter) throws Exception {
/* 439 */     IDatabaseLink dblink = adapter.getDatabaseLink();
/* 440 */     List<SPSBlobImpl> files = new ArrayList();
/* 441 */     Connection conn = null;
/* 442 */     DBMS.PreparedStatement stmt = null;
/* 443 */     ResultSet rs = null;
/*     */     try {
/* 445 */       conn = dblink.requestConnection();
/* 446 */       String sql = DBMS.getSQL(dblink, "SELECT a.Module_Id, a.Calibration_Part_No, FILESIZE, FILECHECKSUM  FROM EIL a LEFT OUTER JOIN Calibration_Files b ON a.Calibration_Part_No = b.Calibration_Part_No WHERE a.End_Item_No = ? ORDER BY a.Module_Id");
/* 447 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 448 */       stmt.setInt(1, part);
/* 449 */       rs = stmt.executeQuery();
/* 450 */       while (rs.next()) {
/* 451 */         int module = rs.getInt(1);
/* 452 */         Integer calibration = Integer.valueOf(rs.getInt(2));
/* 453 */         Integer size = Integer.valueOf(rs.getInt(3));
/* 454 */         if (rs.wasNull()) {
/* 455 */           throw new SPSException(CommonException.MissingCalibrationFile);
/*     */         }
/* 457 */         if (dblink.getDBMS() == 2) {
/* 458 */           files.add(new SPSBlobImpl(calibration.toString(), Integer.valueOf(module), size, null, null, null)); continue;
/*     */         } 
/* 460 */         String checksum = rs.getString(4);
/* 461 */         files.add(new SPSBlobImpl(calibration.toString(), Integer.valueOf(module), size, StringUtilities.hexToBytes(checksum), null, null));
/*     */       }
/*     */     
/* 464 */     } catch (Exception e) {
/* 465 */       if (dblink.getDBMS() == 3) {
/* 466 */         return new ArrayList();
/*     */       }
/* 468 */       throw e;
/*     */     } finally {
/*     */       
/*     */       try {
/* 472 */         if (rs != null) {
/* 473 */           rs.close();
/*     */         }
/* 475 */         if (stmt != null) {
/* 476 */           stmt.close();
/*     */         }
/* 478 */         if (conn != null) {
/* 479 */           dblink.releaseConnection(conn);
/*     */         }
/* 481 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 484 */     if (files.size() == 0) {
/* 485 */       throw new SPSException(CommonException.MissingCalibrationFile);
/*     */     }
/* 487 */     return files;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List getCalibrationFileInfo(List<SPSPart> parts, SPSSchemaAdapterGlobal adapter, boolean requiresCustomCalibration) throws Exception {
/* 492 */     IDatabaseLink dblink = requiresCustomCalibration ? StaticData.getInstance(adapter).getCustomCalibrationDB() : adapter.getDatabaseLink();
/* 493 */     List files = new ArrayList();
/* 494 */     Map<Object, Object> map = new HashMap<Object, Object>();
/* 495 */     Connection conn = null;
/* 496 */     DBMS.PreparedStatement stmt = null;
/* 497 */     ResultSet rs = null;
/*     */     try {
/* 499 */       conn = dblink.requestConnection();
/* 500 */       String sql = DBMS.getSQL(dblink, "SELECT Calibration_Part_No, FILESIZE, FILECHECKSUM FROM Calibration_Files WHERE Calibration_Part_No IN (#list#)", parts.size());
/* 501 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 502 */       for (int j = 0; j < parts.size(); j++) {
/* 503 */         SPSPart calibration = parts.get(j);
/* 504 */         stmt.setInt(j + 1, calibration.getID());
/*     */       } 
/* 506 */       rs = stmt.executeQuery();
/* 507 */       while (rs.next()) {
/* 508 */         Integer calibration = Integer.valueOf(rs.getInt(1));
/* 509 */         Integer size = Integer.valueOf(rs.getInt(2));
/*     */         
/* 511 */         if (dblink.getDBMS() == 2) {
/* 512 */           map.put(calibration, new SPSBlobImpl(calibration.toString(), calibration, size, null, null, null)); continue;
/*     */         } 
/* 514 */         String checksum = rs.getString(3);
/* 515 */         map.put(calibration, new SPSBlobImpl(calibration.toString(), calibration, size, StringUtilities.hexToBytes(checksum), null, null));
/*     */       }
/*     */     
/* 518 */     } catch (Exception e) {
/* 519 */       if (dblink.getDBMS() == 3) {
/* 520 */         return new ArrayList();
/*     */       }
/* 522 */       throw e;
/*     */     } finally {
/*     */       
/*     */       try {
/* 526 */         if (rs != null) {
/* 527 */           rs.close();
/*     */         }
/* 529 */         if (stmt != null) {
/* 530 */           stmt.close();
/*     */         }
/* 532 */         if (conn != null) {
/* 533 */           dblink.releaseConnection(conn);
/*     */         }
/* 535 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 538 */     if (map.size() != parts.size()) {
/* 539 */       throw new SPSException(CommonException.MissingCalibrationFile);
/*     */     }
/* 541 */     for (int i = 0; i < parts.size(); i++) {
/* 542 */       SPSPart calibration = parts.get(i);
/* 543 */       files.add(map.get(Integer.valueOf(calibration.getID())));
/*     */     } 
/* 545 */     return files;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getCalibrationFile(SPSPart calibration, SPSSchemaAdapter adapter, boolean requiresCustomCalibration) throws Exception {
/* 550 */     return getCalibrationFile(calibration.getID(), (SPSSchemaAdapterGlobal)adapter, requiresCustomCalibration);
/*     */   }
/*     */   
/*     */   public static byte[] getCalibrationFile(int calibration, SPSSchemaAdapterGlobal adapter, boolean requiresCustomCalibration) throws Exception {
/* 554 */     IDatabaseLink dblink = requiresCustomCalibration ? StaticData.getInstance(adapter).getCustomCalibrationDB() : adapter.getDatabaseLink();
/* 555 */     Connection conn = null;
/* 556 */     DBMS.PreparedStatement stmt = null;
/* 557 */     ResultSet rs = null;
/*     */     try {
/* 559 */       conn = dblink.requestConnection();
/* 560 */       String sql = DBMS.getSQL(dblink, "SELECT Calibration_File FROM Calibration_Files WHERE Calibration_Part_No = ?");
/* 561 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 562 */       stmt.setInt(1, calibration);
/* 563 */       rs = stmt.executeQuery();
/* 564 */       if (rs.next()) {
/* 565 */         if (dblink.getDBMS() == 2) {
/* 566 */           return rs.getBytes(1);
/*     */         }
/* 568 */         Blob blob = rs.getBlob(1);
/* 569 */         return blob.getBytes(1L, (int)blob.length());
/*     */       } 
/*     */       
/* 572 */       return null;
/*     */     }
/* 574 */     catch (Exception e) {
/* 575 */       throw e;
/*     */     } finally {
/*     */       try {
/* 578 */         if (rs != null) {
/* 579 */           rs.close();
/*     */         }
/* 581 */         if (stmt != null) {
/* 582 */           stmt.close();
/*     */         }
/* 584 */         if (conn != null) {
/* 585 */           dblink.releaseConnection(conn);
/*     */         }
/* 587 */       } catch (Exception x) {
/* 588 */         log.error("failed to clean-up: ", x);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   static void initCustomCalibrations(SPSSchemaAdapterGlobal adapter, IDatabaseLink db) throws Exception {
/* 594 */     StaticData.getInstance(adapter).setCustomCalibrationDB(db);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\global\SPSModule.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */