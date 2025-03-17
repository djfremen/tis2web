/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.global;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.DBVersionInformation;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sids.service.cai.ServiceID;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonValue;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSController;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSControllerList;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSModel;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSSession;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import com.eoos.util.v2.StringUtilities;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class SPSModel implements SPSModel {
/*  32 */   private static Logger log = Logger.getLogger(SPSModel.class);
/*     */   
/*     */   public static final String SECURITY_VTD = "VTD";
/*     */   
/*     */   public static final String SECURITY_CONTENT_THEFT_ON = "CTON";
/*     */   
/*     */   public static final String SECURITY_CONTENT_THEFT_OFF = "CTOFF";
/*     */   
/*     */   public static final String SECURITY_PIN_DISPLAY = "PD";
/*     */   
/*     */   public static final int PROGRAMMING_SEQUENCE = 252;
/*     */   
/*     */   public static final int TYPE4_DEVICE = 253;
/*     */   
/*     */   public static final int VTD_DEVICE = 254;
/*     */   
/*     */   public static final int VTD_HOLDEN = 255;
/*     */   
/*     */   public static final int PROTOCOL_CLASS2 = 1;
/*     */   
/*     */   public static final int PROTOCOL_GMLAN = 3;
/*     */   
/*  54 */   protected Map security = new HashMap<Object, Object>();
/*     */   
/*  56 */   protected Map settings = new HashMap<Object, Object>();
/*     */   protected SPSSchemaAdapterGlobal adapter;
/*     */   
/*     */   public static final class DBSetting
/*     */   {
/*     */     protected String VIN;
/*     */     protected String attribute;
/*     */     protected String value;
/*     */     
/*     */     public DBSetting(String vin, String attribute, String value) {
/*  66 */       this.VIN = vin;
/*  67 */       this.attribute = attribute;
/*  68 */       this.value = value;
/*     */     }
/*     */     
/*     */     public String getAttribute() {
/*  72 */       return this.attribute;
/*     */     }
/*     */     
/*     */     public String getValue() {
/*  76 */       return this.value;
/*     */     }
/*     */     
/*     */     public String getVIN() {
/*  80 */       return this.VIN;
/*     */     }
/*     */   }
/*     */   
/*     */   private SPSModel(SPSSchemaAdapterGlobal adapter) {
/*  85 */     this.adapter = adapter;
/*     */     try {
/*  87 */       initDBSettings(adapter);
/*  88 */       SPSLanguage.init(adapter);
/*  89 */       SPSOption.init(adapter);
/*  90 */       SPSOptionList.init(adapter);
/*  91 */       SPSCOP.init(adapter);
/*  92 */       SPSPart.init(adapter);
/*  93 */       SPSProgrammingType.init(adapter);
/*  94 */       SPSControllerVCI.init(adapter);
/*  95 */       SPSType4Data.init(adapter);
/*  96 */       SPSProgrammingSequence.init(adapter);
/*  97 */       SPSSecurityCode.getInstance(adapter).init();
/*  98 */       StaticData.getInstance(adapter).init();
/*  99 */     } catch (RuntimeException e) {
/* 100 */       throw e;
/* 101 */     } catch (Exception e) {
/* 102 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {}
/*     */   
/*     */   public long getNAO_DB_Version_time_stamp() {
/* 110 */     IDatabaseLink db = this.adapter.getDatabaseLink();
/* 111 */     Connection con = null;
/* 112 */     Statement stmt = null;
/* 113 */     ResultSet rs = null;
/* 114 */     long timestamp = -1L;
/*     */     try {
/* 116 */       con = db.requestConnection();
/* 117 */       stmt = con.createStatement();
/* 118 */       rs = stmt.executeQuery("SELECT Date_time FROM DB_VERSION");
/* 119 */       if (rs.next()) {
/* 120 */         Timestamp TS = rs.getTimestamp(1);
/* 121 */         timestamp = TS.getTime();
/* 122 */         return timestamp;
/*     */       } 
/* 124 */       return -1L;
/*     */     }
/* 126 */     catch (Exception e) {
/* 127 */       log.error("loading Nao db version information failed (" + toString() + ").");
/* 128 */       return -1L;
/*     */     } finally {
/*     */       try {
/* 131 */         if (rs != null) {
/* 132 */           rs.close();
/*     */         }
/* 134 */         if (stmt != null) {
/* 135 */           stmt.close();
/*     */         }
/* 137 */         if (con != null) {
/* 138 */           db.releaseConnection(con);
/*     */         }
/* 140 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initDBSettings(SPSSchemaAdapterGlobal adapter) throws Exception {
/* 146 */     this.settings = new HashMap<Object, Object>();
/* 147 */     Connection conn = null;
/* 148 */     DBMS.PreparedStatement stmt = null;
/* 149 */     ResultSet rs = null;
/* 150 */     IDatabaseLink dblink = adapter.getDatabaseLink();
/*     */     try {
/* 152 */       conn = dblink.requestConnection();
/* 153 */       String sql = DBMS.getSQL(dblink, "SELECT ServiceCode, VIN, Attribute, Value FROM DB_Settings");
/* 154 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 155 */       rs = stmt.executeQuery();
/* 156 */       while (rs.next()) {
/* 157 */         String serviceCode = rs.getString(1).trim();
/* 158 */         String vin = rs.getString(2).trim();
/* 159 */         String attribute = rs.getString(3).trim();
/* 160 */         String value = rs.getString(4).trim();
/* 161 */         List<DBSetting> sidsSettings = (List)this.settings.get(serviceCode.toUpperCase(Locale.ENGLISH));
/* 162 */         if (sidsSettings == null) {
/* 163 */           sidsSettings = new ArrayList();
/* 164 */           this.settings.put(serviceCode.toUpperCase(Locale.ENGLISH), sidsSettings);
/*     */         } 
/* 166 */         sidsSettings.add(new DBSetting(vin, attribute.toLowerCase(Locale.ENGLISH), value.toLowerCase(Locale.ENGLISH)));
/*     */       } 
/* 168 */     } catch (Exception e) {
/* 169 */       log.warn("Table DB_Settings not available");
/*     */     } finally {
/*     */       
/*     */       try {
/* 173 */         if (rs != null) {
/* 174 */           rs.close();
/*     */         }
/* 176 */         if (stmt != null) {
/* 177 */           stmt.close();
/*     */         }
/* 179 */         if (conn != null) {
/* 180 */           dblink.releaseConnection(conn);
/*     */         }
/* 182 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matchVIN(String filterVIN, String vin) {
/* 188 */     if (filterVIN.equals("#") || filterVIN.equals("*")) {
/* 189 */       return true;
/*     */     }
/* 191 */     for (int i = 0, j = 0; i < vin.length() && j < filterVIN.length(); i++) {
/* 192 */       char c = vin.charAt(i);
/* 193 */       char t = filterVIN.charAt(j);
/* 194 */       if (t == '?') {
/* 195 */         j++;
/* 196 */       } else if (t == '*') {
/* 197 */         if (j == filterVIN.length() - 1) {
/* 198 */           return true;
/*     */         }
/* 200 */         t = filterVIN.charAt(++j);
/* 201 */         boolean found = false;
/* 202 */         for (; i < vin.length(); i++) {
/* 203 */           c = vin.charAt(i);
/* 204 */           if (c == t) {
/* 205 */             found = true;
/*     */           }
/*     */         } 
/* 208 */         if (!found) {
/* 209 */           return false;
/*     */         }
/*     */       } else {
/* 212 */         if (c != t) {
/* 213 */           return false;
/*     */         }
/* 215 */         j++;
/*     */       } 
/*     */     } 
/* 218 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean checkSetting(ServiceID serviceID, String vin, String attribute, String target) {
/* 223 */     List<DBSetting> sidsSettings = (List)this.settings.get(serviceID.toString().toUpperCase(Locale.ENGLISH));
/* 224 */     if (sidsSettings != null) {
/* 225 */       for (int i = 0; i < sidsSettings.size(); i++) {
/* 226 */         DBSetting setting = sidsSettings.get(i);
/* 227 */         if (setting.getAttribute().equalsIgnoreCase(attribute) && (
/* 228 */           setting.getVIN() == null || matchVIN(setting.getVIN(), vin))) {
/* 229 */           return setting.getValue().equalsIgnoreCase(target);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 234 */     return false;
/*     */   }
/*     */   
/*     */   public static SPSModel getInstance(SPSSchemaAdapterGlobal adapter) {
/* 238 */     synchronized (adapter.getSyncObject()) {
/* 239 */       SPSModel instance = (SPSModel)adapter.getObject(SPSModel.class);
/* 240 */       if (instance == null) {
/* 241 */         instance = new SPSModel(adapter);
/* 242 */         adapter.storeObject(SPSModel.class, instance);
/*     */       } 
/* 244 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   IDatabaseLink getDatabaseLink() {
/* 249 */     return this.adapter.getDatabaseLink();
/*     */   }
/*     */ 
/*     */   
/*     */   public DBVersionInformation getVersionInfo() {
/* 254 */     IDatabaseLink db = this.adapter.getDatabaseLink();
/* 255 */     Connection con = null;
/* 256 */     Statement stmt = null;
/* 257 */     ResultSet rs = null;
/*     */     try {
/* 259 */       con = db.requestConnection();
/* 260 */       stmt = con.createStatement();
/* 261 */       rs = stmt.executeQuery("SELECT * FROM RELEASE");
/* 262 */       if (rs.next()) {
/* 263 */         return new DBVersionInformation(null, rs.getString("RELEASE_ID"), rs.getDate("RELEASE_DATE"), rs.getString("DESCRIPTION"), rs.getString("VERSION"));
/*     */       }
/* 265 */       return null;
/*     */     }
/* 267 */     catch (Exception e) {
/* 268 */       log.error("loading sps-db version information failed (" + toString() + ").");
/* 269 */       return null;
/*     */     } finally {
/*     */       try {
/* 272 */         if (rs != null) {
/* 273 */           rs.close();
/*     */         }
/* 275 */         if (stmt != null) {
/* 276 */           stmt.close();
/*     */         }
/* 278 */         if (con != null) {
/* 279 */           db.releaseConnection(con);
/*     */         }
/* 281 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List getRequestMethodData(int requestMethodID, int deviceID) throws Exception {
/* 287 */     IDatabaseLink db = this.adapter.getDatabaseLink();
/* 288 */     List<SPSRequestMethodData> result = new ArrayList();
/* 289 */     Connection conn = null;
/* 290 */     DBMS.PreparedStatement stmt = null;
/* 291 */     ResultSet rs = null;
/*     */     try {
/* 293 */       conn = db.requestConnection();
/* 294 */       String sql = DBMS.getSQL(db, "SELECT ReqMethID,DeviceID,Protocol,Pin_Link,ReadVIN,VINReadType,VINAddresses,ReadParts,PartReadType,PartAddresses,PartFormat,PartLength,PartStartByte,ReadHWID,HWIDReadType,HWIDAddresses,HWIDFormat,HWIDLength,HWIDStartByte,ReadSecuritySeed,SpecialReqType,ReadCVN FROM Request_Method_Data where ReqMethID=?");
/* 295 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 296 */       stmt.setInt(1, requestMethodID);
/* 297 */       rs = stmt.executeQuery();
/* 298 */       while (rs.next()) {
/* 299 */         SPSRequestMethodData rmd = new SPSRequestMethodData(rs);
/* 300 */         result.add(rmd);
/*     */       } 
/* 302 */     } catch (Exception e) {
/* 303 */       throw e;
/*     */     } finally {
/*     */       try {
/* 306 */         if (rs != null) {
/* 307 */           rs.close();
/*     */         }
/* 309 */         if (stmt != null) {
/* 310 */           stmt.close();
/*     */         }
/* 312 */         if (conn != null) {
/* 313 */           db.releaseConnection(conn);
/*     */         }
/* 315 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 318 */     if (result.size() == 0) {
/* 319 */       result.add(new SPSRequestMethodData(deviceID));
/*     */     }
/* 321 */     return result;
/*     */   }
/*     */   
/*     */   public List getProgrammingMessageVCI(SPSLanguage language, List instructions) throws Exception {
/* 325 */     if (instructions == null) {
/* 326 */       return null;
/*     */     }
/* 328 */     List<String> htmls = new ArrayList();
/* 329 */     Iterator<Integer> it = instructions.iterator();
/* 330 */     while (it.hasNext()) {
/* 331 */       Integer id = it.next();
/* 332 */       String html = getProgrammingMessageVCI(language, id.intValue());
/* 333 */       if (html != null) {
/* 334 */         htmls.add(html); continue;
/*     */       } 
/* 336 */       log.error("failed to retrieve vci message '" + id + "'");
/*     */     } 
/*     */     
/* 339 */     return (htmls.size() > 0) ? htmls : null;
/*     */   }
/*     */   
/*     */   public String getProgrammingMessageVCI(SPSLanguage language, int messageID) throws Exception {
/* 343 */     IDatabaseLink db = this.adapter.getDatabaseLink();
/* 344 */     Connection conn = null;
/* 345 */     DBMS.PreparedStatement stmt = null;
/* 346 */     ResultSet rs = null;
/*     */     try {
/* 348 */       conn = db.requestConnection();
/* 349 */       String sql = DBMS.getSQL(db, "SELECT Description FROM Pre_Post_Prog_Msg where Message_Id=? and Language_Code=?");
/* 350 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 351 */       stmt.setInt(1, messageID);
/* 352 */       stmt.setString(2, language.getID());
/* 353 */       rs = stmt.executeQuery();
/* 354 */       if (rs.next()) {
/* 355 */         String description = rs.getString(1);
/* 356 */         return (description != null) ? description.trim() : "";
/*     */       } 
/* 358 */     } catch (Exception e) {
/* 359 */       throw e;
/*     */     } finally {
/*     */       try {
/* 362 */         if (rs != null) {
/* 363 */           rs.close();
/*     */         }
/* 365 */         if (stmt != null) {
/* 366 */           stmt.close();
/*     */         }
/* 368 */         if (conn != null) {
/* 369 */           db.releaseConnection(conn);
/*     */         }
/* 371 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 374 */     return null;
/*     */   }
/*     */   
/*     */   public String getReplaceMessageVCI(SPSLanguage language, int messageID) throws Exception {
/* 378 */     IDatabaseLink db = this.adapter.getDatabaseLink();
/* 379 */     Connection conn = null;
/* 380 */     DBMS.PreparedStatement stmt = null;
/* 381 */     ResultSet rs = null;
/*     */     try {
/* 383 */       conn = db.requestConnection();
/* 384 */       String sql = DBMS.getSQL(db, "SELECT DESCRIPTION FROM REPLACE_INSTRUCTIONS WHERE Replace_Message_Id=? and Language_Code=?");
/* 385 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 386 */       stmt.setInt(1, messageID);
/* 387 */       stmt.setString(2, language.getID());
/* 388 */       rs = stmt.executeQuery();
/* 389 */       if (rs.next()) {
/* 390 */         return rs.getString(1);
/*     */       }
/* 392 */     } catch (Exception e) {
/* 393 */       throw e;
/*     */     } finally {
/*     */       try {
/* 396 */         if (rs != null) {
/* 397 */           rs.close();
/*     */         }
/* 399 */         if (stmt != null) {
/* 400 */           stmt.close();
/*     */         }
/* 402 */         if (conn != null) {
/* 403 */           db.releaseConnection(conn);
/*     */         }
/* 405 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 408 */     return null;
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
/*     */   public String getVTDSecurityInfo(SPSVIN vin, String type) throws Exception {
/* 421 */     IDatabaseLink db = this.adapter.getDatabaseLink();
/* 422 */     Connection conn = null;
/* 423 */     DBMS.PreparedStatement stmt = null;
/* 424 */     ResultSet rs = null;
/* 425 */     StringBuffer security = new StringBuffer();
/* 426 */     security.append(type + ';');
/*     */     try {
/* 428 */       conn = db.requestConnection();
/* 429 */       String sql = DBMS.getSQL(db, "SELECT DISTINCT Device_Id, Algo_No FROM VTD_Security_Info WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?) AND (Engine = '~' OR Engine = ?) AND (Plant = '~' OR Plant = ?)");
/* 430 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 431 */       stmt.setString(1, DBMS.toString(vin.getModelYear()));
/* 432 */       stmt.setString(2, DBMS.toString(vin.getMake()));
/* 433 */       stmt.setString(3, DBMS.toString(vin.getLine()));
/* 434 */       stmt.setString(4, DBMS.toString(vin.getSeries()));
/* 435 */       stmt.setString(5, DBMS.toString(vin.getEngine()));
/* 436 */       stmt.setString(6, DBMS.toString(vin.getPosition(11)));
/* 437 */       rs = stmt.executeQuery();
/* 438 */       while (rs.next()) {
/* 439 */         int device = rs.getInt(1);
/* 440 */         int algorithm = rs.getInt(2);
/* 441 */         security.append(device + 58 + algorithm + 59);
/*     */       } 
/* 443 */       return (security.length() > type.length() + 1) ? security.toString() : null;
/* 444 */     } catch (Exception e) {
/* 445 */       throw e;
/*     */     } finally {
/*     */       try {
/* 448 */         if (rs != null) {
/* 449 */           rs.close();
/*     */         }
/* 451 */         if (stmt != null) {
/* 452 */           stmt.close();
/*     */         }
/* 454 */         if (conn != null) {
/* 455 */           db.releaseConnection(conn);
/*     */         }
/* 457 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean verifyVTDNavigationInfo(Long navInfo) throws Exception {
/* 465 */     IDatabaseLink db = this.adapter.getDatabaseLink();
/* 466 */     Connection conn = null;
/* 467 */     DBMS.PreparedStatement stmt = null;
/* 468 */     ResultSet rs = null;
/*     */     try {
/* 470 */       conn = db.requestConnection();
/* 471 */       String sql = DBMS.getSQL(db, "SELECT count(*) FROM VTDNavInfo WHERE Value = ?");
/* 472 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 473 */       stmt.setLong(1, navInfo.longValue());
/* 474 */       rs = stmt.executeQuery();
/* 475 */       if (rs.next()) {
/* 476 */         int count = rs.getInt(1);
/* 477 */         return (count > 0);
/*     */       } 
/* 479 */     } catch (Exception e) {
/* 480 */       throw e;
/*     */     } finally {
/*     */       try {
/* 483 */         if (rs != null) {
/* 484 */           rs.close();
/*     */         }
/* 486 */         if (stmt != null) {
/* 487 */           stmt.close();
/*     */         }
/* 489 */         if (conn != null) {
/* 490 */           db.releaseConnection(conn);
/*     */         }
/* 492 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 495 */     return false;
/*     */   }
/*     */   
/*     */   public SPSControllerList getControllers(SPSSession session, List devices, Value mode, String toolType) throws Exception {
/* 499 */     return getSupportedControllers(session, devices, mode, toolType);
/*     */   }
/*     */   
/*     */   protected boolean checkJ2534(SPSController controller, String toolType) {
/* 503 */     if (toolType == null) {
/* 504 */       return true;
/*     */     }
/*     */     
/* 507 */     Boolean j2534Dependency = ((SPSControllerVCI)controller).hasJ2534Dependency();
/* 508 */     if (j2534Dependency == null)
/* 509 */       return true; 
/* 510 */     if (j2534Dependency.equals(Boolean.TRUE)) {
/* 511 */       return (toolType.indexOf("J2534") >= 0);
/*     */     }
/* 513 */     return (toolType.indexOf("J2534") < 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean acceptDevice(List<Integer> devices, SPSController controller, String toolType) {
/* 519 */     if (devices == null) {
/* 520 */       return (controller.getDeviceID() == 253) ? checkJ2534(controller, toolType) : true;
/*     */     }
/* 522 */     for (int i = 0; i < devices.size(); i++) {
/* 523 */       Integer device = devices.get(i);
/* 524 */       if (device.intValue() == controller.getDeviceID()) {
/* 525 */         return (device.intValue() == 253) ? checkJ2534(controller, toolType) : true;
/*     */       }
/*     */     } 
/* 528 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean isXMLController(SPSSchemaAdapterGlobal adapter, SPSControllerData data) {
/* 532 */     if (data.getDeviceID() != 253)
/* 533 */       return false; 
/* 534 */     if (data.getVCI() == 0) {
/* 535 */       return SPSControllerVCI.isXMLController(adapter, data.getControllerID(), data.getDescriptionIndex());
/*     */     }
/* 537 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String strip10380(String sql) {
/* 543 */     return StringUtilities.replace(sql, ",Suppress_Sequence_Controller", "");
/*     */   }
/*     */ 
/*     */   
/*     */   protected String strip8746(String sql) {
/* 548 */     return StringUtilities.replace(sql, ",SecCode_required", "");
/*     */   }
/*     */ 
/*     */   
/*     */   protected String strip8780(String sql) {
/* 553 */     return StringUtilities.replace(sql, ",Option_List_Id", "");
/*     */   }
/*     */   
/*     */   protected ResultSet executeQuery(SPSVIN vin, String sql, Connection conn, DBMS.PreparedStatement stmt) throws Exception {
/*     */     try {
/* 558 */       setParametersVIN(vin, stmt);
/* 559 */       return stmt.executeQuery();
/* 560 */     } catch (Exception e) {
/* 561 */       stmt.close();
/* 562 */       if (e.getMessage().toUpperCase().indexOf("SUPPRESS_SEQUENCE_CONTROLLER") > 0) {
/* 563 */         stmt = DBMS.prepareSQLStatement(conn, strip10380(sql));
/* 564 */         return executeQuery(vin, sql, conn, stmt);
/* 565 */       }  if (e.getMessage().toUpperCase().indexOf("SECCODE_REQUIRED") > 0) {
/* 566 */         stmt = DBMS.prepareSQLStatement(conn, strip8746(sql));
/* 567 */         return executeQuery(vin, sql, conn, stmt);
/* 568 */       }  if (e.getMessage().toUpperCase().indexOf("Option_List_Id") > 0) {
/* 569 */         stmt = DBMS.prepareSQLStatement(conn, strip8780(sql));
/* 570 */         return executeQuery(vin, sql, conn, stmt);
/*     */       } 
/* 572 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setParametersVIN(SPSVIN vin, DBMS.PreparedStatement stmt) throws SQLException {
/* 578 */     stmt.setString(1, DBMS.toString(vin.getModelYear()));
/* 579 */     stmt.setString(2, DBMS.toString(vin.getMake()));
/* 580 */     stmt.setString(3, DBMS.toString(vin.getLine()));
/* 581 */     stmt.setString(4, DBMS.toString(vin.getSeries()));
/* 582 */     stmt.setString(5, DBMS.toString(vin.getEngine()));
/*     */   }
/*     */   
/*     */   protected SPSControllerList getSupportedControllers(SPSSession session, List devices, Value mode, String toolType) throws Exception {
/* 586 */     SPSControllerList list = new SPSControllerList();
/* 587 */     SPSControllerList pslist = new SPSControllerList();
/*     */ 
/*     */     
/* 590 */     List<SPSProgrammingSequence> sequences = new ArrayList();
/* 591 */     list.registerSession(session);
/* 592 */     boolean clearDTCs = false;
/* 593 */     int vehicleLink = 0;
/* 594 */     IDatabaseLink db = this.adapter.getDatabaseLink();
/* 595 */     Connection conn = null;
/* 596 */     DBMS.PreparedStatement stmt = null;
/* 597 */     ResultSet rs = null;
/* 598 */     List<SPSControllerData> records = new ArrayList();
/*     */     try {
/* 600 */       conn = db.requestConnection();
/* 601 */       String sql = DBMS.getSQL(db, "SELECT Controller_Id,RPO_Code,Pre_RPO_Code,Post_RPO_Code,Device_Id,ReqMthID,Programming_Methods,Vci,Pre_Prog_Msgs,Post_Prog_Msgs,VIN_Pos,VIN_Value,VIN_Digit_4_OR_6,Beginning_Sequence,Ending_Sequence,REPLACE_MESSAGE_ID,REPLACE_ID,Desc_Indx,Sec_Id,Suppress_Sequence_Controller,SecCode_required,Option_List_Id,Engine,Series,Line FROM Supported_Controllers WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?) AND (Engine = '~' OR Engine = ?)");
/* 602 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 603 */       SPSVIN vin = (SPSVIN)session.getVehicle().getVIN();
/* 604 */       rs = executeQuery(vin, sql, conn, stmt);
/* 605 */       while (rs.next()) {
/* 606 */         records.add(new SPSControllerData(db, rs));
/*     */       }
/* 608 */     } catch (Exception e) {
/* 609 */       throw e;
/*     */     } finally {
/*     */       try {
/* 612 */         if (rs != null) {
/* 613 */           rs.close();
/*     */         }
/* 615 */         if (stmt != null) {
/* 616 */           stmt.close();
/*     */         }
/* 618 */         if (conn != null) {
/* 619 */           db.releaseConnection(conn);
/*     */         }
/* 621 */       } catch (Exception x) {}
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 626 */       ClientContext context = ClientContextProvider.getInstance().getContext(session.getSessionID());
/* 627 */       context.getSharedContext().getUsrGroup2ManufMap().keySet();
/* 628 */     } catch (Exception e) {
/* 629 */       log.warn("unable to determine user groups - exception:" + e + ", continuing without any group");
/*     */     } 
/*     */     
/* 632 */     records = reduceRecords(records);
/* 633 */     for (int i = 0; i < records.size(); i++) {
/* 634 */       SPSControllerData data = records.get(i);
/*     */ 
/*     */ 
/*     */       
/* 638 */       SPSQualification qualification = new SPSQualification(data);
/* 639 */       if (qualification.qualifies((SPSVehicle)session.getVehicle())) {
/* 640 */         int device = data.getDeviceID();
/* 641 */         if (device != 255)
/*     */         {
/* 643 */           if (device == 252) {
/* 644 */             if (!"T2_REMOTE".equals(toolType)) {
/*     */ 
/*     */               
/* 647 */               int id = data.getControllerID();
/* 648 */               int psid = data.getProgrammingSequenceID();
/* 649 */               List preProgrammingInstructions = SPSProgrammingInstructions.tokenize(data.getPreProgrammingInstructions());
/* 650 */               List postProgrammingInstructions = SPSProgrammingInstructions.tokenize(data.getPostProgrammingInstructions());
/* 651 */               int descIndex = data.getDescriptionIndex();
/* 652 */               SPSProgrammingSequence sequence = new SPSProgrammingSequence(session, id, descIndex, psid, preProgrammingInstructions, postProgrammingInstructions, this.adapter);
/* 653 */               sequences.add(sequence);
/*     */             } 
/*     */           } else {
/* 656 */             SPSController controller = isXMLController(this.adapter, data) ? new SPSControllerXML(session, data, this.adapter) : new SPSControllerVCI(session, data, this.adapter);
/* 657 */             if (controller.getDescription() != null)
/*     */             {
/*     */               
/* 660 */               if (acceptDevice(devices, controller, toolType)) {
/* 661 */                 SPSRequestMethodData rmd = getTableDrivenRequestMethodData(controller);
/* 662 */                 if (rmd != null) {
/* 663 */                   clearDTCs = (clearDTCs || rmd.getProtocol() == 1 || rmd.getProtocol() == 3);
/* 664 */                   vehicleLink = determineVehicleLink(vehicleLink, rmd);
/*     */                 } 
/* 666 */                 list.insert(controller);
/*     */               
/*     */               }
/*     */ 
/*     */             
/*     */             }
/*     */           
/*     */           }
/*     */ 
/*     */         
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 680 */         log.debug("skipped controller: " + data.getControllerID() + " - access denied for user: " + session.getSessionID());
/*     */       } 
/*     */     } 
/* 683 */     session.setClearDTCFlag(clearDTCs);
/* 684 */     session.setVehicleLink(vehicleLink);
/* 685 */     if (sequences.size() > 0) {
/* 686 */       handleProgrammingSequences(session, mode, list, pslist, sequences);
/*     */     }
/* 688 */     list.qualify(session.getVehicle().getOptions(), this.adapter);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 693 */     return list;
/*     */   }
/*     */   
/*     */   protected List reduceRecords(List<SPSControllerData> records) {
/* 697 */     Set<String> controllers = new HashSet();
/* 698 */     for (int i = 0; i < records.size(); i++) {
/* 699 */       SPSControllerData data = records.get(i);
/* 700 */       controllers.add(data.getControllerIdentity() + ":" + data.getDescriptionIndex());
/*     */     } 
/* 702 */     List result = new ArrayList();
/* 703 */     Iterator<String> it = controllers.iterator();
/* 704 */     while (it.hasNext()) {
/* 705 */       String controller = it.next();
/* 706 */       result.addAll(reduceRecords(controller, records));
/*     */     } 
/* 708 */     return result;
/*     */   }
/*     */   
/*     */   protected List reduceRecords(String controller, List<SPSControllerData> records) {
/* 712 */     List<SPSControllerData> subset = new ArrayList();
/* 713 */     for (int i = 0; i < records.size(); i++) {
/* 714 */       SPSControllerData data = records.get(i);
/* 715 */       if (controller.equals(data.getControllerIdentity() + ":" + data.getDescriptionIndex())) {
/* 716 */         subset.add(data);
/*     */       }
/*     */     } 
/* 719 */     List bestMatch = reduceRecords(controller, "Engine", subset);
/* 720 */     if (bestMatch.size() == 1) {
/* 721 */       return bestMatch;
/*     */     }
/* 723 */     bestMatch = reduceRecords(controller, "Series", bestMatch);
/* 724 */     if (bestMatch.size() == 1) {
/* 725 */       return bestMatch;
/*     */     }
/* 727 */     return reduceRecords(controller, "Line", bestMatch);
/*     */   }
/*     */   
/*     */   protected List reduceRecords(String controller, String criteria, List<SPSControllerData> records) {
/* 731 */     List<SPSControllerData> bestMatch = new ArrayList();
/* 732 */     for (int i = 0; i < records.size(); i++) {
/* 733 */       SPSControllerData data = records.get(i);
/* 734 */       if (criteria.equals("Engine") && !data.getEngine().equals("~")) {
/* 735 */         bestMatch.add(data);
/*     */       }
/* 737 */       if (criteria.equals("Series") && !data.getSeries().equals("~")) {
/* 738 */         bestMatch.add(data);
/*     */       }
/* 740 */       if (criteria.equals("Line") && !data.getLine().equals("~")) {
/* 741 */         bestMatch.add(data);
/*     */       }
/*     */     } 
/* 744 */     if (bestMatch.size() > 0) {
/* 745 */       return bestMatch;
/*     */     }
/* 747 */     return records;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SPSRequestMethodData getTableDrivenRequestMethodData(SPSController controller) {
/* 752 */     int rmid = controller.getRequestMethodID();
/* 753 */     if (rmid == 0) {
/* 754 */       return null;
/*     */     }
/* 756 */     List<SPSRequestMethodData> rmd = StaticData.getInstance(this.adapter).getRequestMethodData();
/* 757 */     for (int i = 0; i < rmd.size(); i++) {
/* 758 */       SPSRequestMethodData data = rmd.get(i);
/* 759 */       if (data.getDeviceID() == controller.getDeviceID() && data.getRequestMethodID() == controller.getRequestMethodID()) {
/* 760 */         return data;
/*     */       }
/*     */     } 
/* 763 */     return null;
/*     */   }
/*     */   
/*     */   protected int determineVehicleLink(int vehicleLink, SPSRequestMethodData rmd) {
/* 767 */     if (rmd.getProtocol() == 3 && 
/* 768 */       rmd.getPINLink() > vehicleLink) {
/* 769 */       vehicleLink = rmd.getPINLink();
/*     */     }
/*     */     
/* 772 */     return vehicleLink;
/*     */   }
/*     */   
/*     */   protected String loadSecurityList(String secID) {
/* 776 */     Connection conn = null;
/* 777 */     IDatabaseLink db = this.adapter.getDatabaseLink();
/* 778 */     DBMS.PreparedStatement stmt = null;
/* 779 */     ResultSet rs = null;
/*     */     
/* 781 */     try { conn = db.requestConnection();
/* 782 */       String sql = DBMS.getSQL(db, "SELECT Sec_List FROM Security_Groups WHERE Sec_Id = ?");
/* 783 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 784 */       stmt.setString(1, secID);
/* 785 */       rs = stmt.executeQuery();
/* 786 */       if (rs.next()) {
/* 787 */         return rs.getString(1);
/*     */       } }
/* 789 */     catch (Exception e) {  }
/*     */     finally
/*     */     { try {
/* 792 */         if (rs != null) {
/* 793 */           rs.close();
/*     */         }
/* 795 */         if (stmt != null) {
/* 796 */           stmt.close();
/*     */         }
/* 798 */         if (conn != null) {
/* 799 */           db.releaseConnection(conn);
/*     */         }
/* 801 */       } catch (Exception x) {} }
/*     */ 
/*     */     
/* 804 */     return null;
/*     */   }
/*     */   
/*     */   protected void handleProgrammingSequences(SPSSession session, Value mode, SPSControllerList controllers, SPSControllerList pscontrollers, List<SPSProgrammingSequence> sequences) {
/* 808 */     boolean modeReplaceReprogram = (mode != null && mode.equals(CommonValue.REPLACE_AND_REPROGRAM));
/* 809 */     for (int i = 0; i < sequences.size(); i++) {
/* 810 */       SPSProgrammingSequence ps = sequences.get(i);
/* 811 */       if (ps.isValid(session, modeReplaceReprogram, controllers, pscontrollers, this.adapter)) {
/* 812 */         controllers.add(new SPSControllerReference(ps));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean isWildcard(String flag) {
/* 818 */     return flag.equals("~");
/*     */   }
/*     */   
/*     */   public static final class StaticData
/*     */   {
/* 823 */     private List rmd = new ArrayList();
/*     */     
/*     */     private StaticData(SPSSchemaAdapterGlobal adapter) {
/* 826 */       IDatabaseLink db = adapter.getDatabaseLink();
/* 827 */       Connection conn = null;
/* 828 */       DBMS.PreparedStatement stmt = null;
/* 829 */       ResultSet rs = null;
/*     */       try {
/* 831 */         conn = db.requestConnection();
/* 832 */         String sql = DBMS.getSQL(db, "SELECT ReqMethID,DeviceID,Protocol,Pin_Link,ReadVIN,VINReadType,VINAddresses,ReadParts,PartReadType,PartAddresses,PartFormat,PartLength,PartStartByte,ReadHWID,HWIDReadType,HWIDAddresses,HWIDFormat,HWIDLength,HWIDStartByte,ReadSecuritySeed,SpecialReqType,ReadCVN FROM Request_Method_Data");
/* 833 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/* 834 */         rs = stmt.executeQuery();
/* 835 */         while (rs.next()) {
/* 836 */           SPSRequestMethodData data = new SPSRequestMethodData(rs);
/* 837 */           this.rmd.add(data);
/*     */         } 
/* 839 */       } catch (Exception e) {
/* 840 */         throw new RuntimeException(e);
/*     */       } finally {
/*     */         try {
/* 843 */           if (rs != null) {
/* 844 */             rs.close();
/*     */           }
/* 846 */           if (stmt != null) {
/* 847 */             stmt.close();
/*     */           }
/* 849 */           if (conn != null) {
/* 850 */             db.releaseConnection(conn);
/*     */           }
/* 852 */         } catch (Exception x) {
/* 853 */           SPSModel.log.error("failed to clean-up: ", x);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     public List getRequestMethodData() {
/* 859 */       return this.rmd;
/*     */     }
/*     */ 
/*     */     
/*     */     public void init() {}
/*     */     
/*     */     public static StaticData getInstance(SPSSchemaAdapterGlobal adapter) {
/* 866 */       synchronized (adapter.getSyncObject()) {
/* 867 */         StaticData instance = (StaticData)adapter.getObject(StaticData.class);
/* 868 */         if (instance == null) {
/* 869 */           instance = new StaticData(adapter);
/* 870 */           adapter.storeObject(StaticData.class, instance);
/*     */         } 
/* 872 */         return instance;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\global\SPSModel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */