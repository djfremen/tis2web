/*      */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.nao;
/*      */ 
/*      */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*      */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*      */ import com.eoos.gm.tis2web.frame.export.common.datatype.DBVersionInformation;
/*      */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonValue;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSController;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSControllerList;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSModel;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSSession;
/*      */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*      */ import com.eoos.tokenizer.v2.CSTokenizer;
/*      */ import com.eoos.util.v2.StringUtilities;
/*      */ import java.sql.Connection;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Statement;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import org.apache.log4j.Logger;
/*      */ 
/*      */ public class SPSModel implements SPSModel {
/*   34 */   private static Logger log = Logger.getLogger(SPSModel.class);
/*      */   
/*      */   public static final String SECURITY_VTD = "VTD";
/*      */   
/*      */   public static final String SECURITY_CONTENT_THEFT_ON = "CTON";
/*      */   
/*      */   public static final String SECURITY_CONTENT_THEFT_OFF = "CTOFF";
/*      */   
/*      */   public static final String SECURITY_PIN_DISPLAY = "PD";
/*      */   
/*      */   public static final int PROGRAMMING_SEQUENCE = 252;
/*      */   
/*      */   public static final int TYPE4_DEVICE = 253;
/*      */   
/*      */   public static final int VTD_DEVICE = 254;
/*      */   
/*      */   public static final int VTD_HOLDEN = 255;
/*      */   
/*      */   public static final int PROTOCOL_CLASS2 = 1;
/*      */   
/*      */   public static final int PROTOCOL_GMLAN = 3;
/*      */   
/*   56 */   protected Map security = new HashMap<Object, Object>();
/*      */   
/*      */   protected SPSSchemaAdapterNAO adapter;
/*      */   
/*      */   private SPSModel(SPSSchemaAdapterNAO adapter) {
/*   61 */     this.adapter = adapter;
/*      */     try {
/*   63 */       SPSLanguage.init(adapter);
/*   64 */       SPSOption.init(adapter);
/*   65 */       SPSCOP.init(adapter);
/*   66 */       SPSPart.init(adapter);
/*   67 */       SPSProgrammingType.init(adapter);
/*   68 */       SPSControllerVCI.init(adapter);
/*   69 */       SPSVIN.init(adapter);
/*   70 */       SPSOptionPROM.init(adapter);
/*   71 */       SPSControllerPROM.init(adapter);
/*   72 */       SPSType4Data.init(adapter);
/*   73 */       SPSProgrammingSequence.init(adapter);
/*   74 */       SPSSecurityCode.getInstance(adapter).init();
/*   75 */       StaticData.getInstance(adapter).init();
/*   76 */     } catch (RuntimeException e) {
/*   77 */       throw e;
/*   78 */     } catch (Exception e) {
/*   79 */       throw new RuntimeException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void init() {}
/*      */ 
/*      */   
/*      */   public static SPSModel getInstance(SPSSchemaAdapterNAO adapter) {
/*   88 */     synchronized (adapter.getSyncObject()) {
/*   89 */       SPSModel instance = (SPSModel)adapter.getObject(SPSModel.class);
/*   90 */       if (instance == null) {
/*   91 */         instance = new SPSModel(adapter);
/*   92 */         adapter.storeObject(SPSModel.class, instance);
/*      */       } 
/*   94 */       return instance;
/*      */     } 
/*      */   }
/*      */   
/*      */   IDatabaseLink getDatabaseLink() {
/*   99 */     return this.adapter.getDatabaseLink();
/*      */   }
/*      */   
/*      */   public long getNAO_DB_Version_time_stamp() {
/*  103 */     IDatabaseLink db = this.adapter.getDatabaseLink();
/*  104 */     Connection con = null;
/*  105 */     Statement stmt = null;
/*  106 */     ResultSet rs = null;
/*  107 */     long timestamp = -1L;
/*      */     try {
/*  109 */       con = db.requestConnection();
/*  110 */       stmt = con.createStatement();
/*  111 */       rs = stmt.executeQuery("SELECT Date_time FROM DB_VERSION");
/*  112 */       if (rs.next()) {
/*  113 */         Timestamp TS = rs.getTimestamp(1);
/*  114 */         timestamp = TS.getTime();
/*  115 */         return timestamp;
/*      */       } 
/*  117 */       return -1L;
/*      */     }
/*  119 */     catch (Exception e) {
/*  120 */       log.error("loading Nao db version information failed (" + toString() + ").");
/*  121 */       return -1L;
/*      */     } finally {
/*      */       try {
/*  124 */         if (rs != null) {
/*  125 */           rs.close();
/*      */         }
/*  127 */         if (stmt != null) {
/*  128 */           stmt.close();
/*      */         }
/*  130 */         if (con != null) {
/*  131 */           db.releaseConnection(con);
/*      */         }
/*  133 */       } catch (Exception x) {}
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public DBVersionInformation getVersionInfo() {
/*  140 */     IDatabaseLink db = this.adapter.getDatabaseLink();
/*  141 */     Connection con = null;
/*  142 */     Statement stmt = null;
/*  143 */     ResultSet rs = null;
/*      */     try {
/*  145 */       con = db.requestConnection();
/*  146 */       stmt = con.createStatement();
/*  147 */       rs = stmt.executeQuery("SELECT * FROM RELEASE");
/*  148 */       if (rs.next()) {
/*  149 */         return new DBVersionInformation(null, rs.getString("RELEASE_ID"), rs.getDate("RELEASE_DATE"), rs.getString("DESCRIPTION"), rs.getString("VERSION"));
/*      */       }
/*  151 */       return null;
/*      */     }
/*  153 */     catch (Exception e) {
/*  154 */       log.error("loading sps-db version information failed (" + toString() + ").");
/*  155 */       return null;
/*      */     } finally {
/*      */       try {
/*  158 */         if (rs != null) {
/*  159 */           rs.close();
/*      */         }
/*  161 */         if (stmt != null) {
/*  162 */           stmt.close();
/*      */         }
/*  164 */         if (con != null) {
/*  165 */           db.releaseConnection(con);
/*      */         }
/*  167 */       } catch (Exception x) {}
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public List getRequestMethodData(int requestMethodID, int deviceID) throws Exception {
/*  173 */     IDatabaseLink db = this.adapter.getDatabaseLink();
/*  174 */     List<SPSRequestMethodData> result = new ArrayList();
/*  175 */     Connection conn = null;
/*  176 */     DBMS.PreparedStatement stmt = null;
/*  177 */     ResultSet rs = null;
/*      */     try {
/*  179 */       conn = db.requestConnection();
/*  180 */       String sql = DBMS.getSQL(db, "SELECT ReqMethID,DeviceID,Protocol,Pin_Link,ReadVIN,VINReadType,VINAddresses,ReadParts,PartReadType,PartAddresses,PartFormat,PartLength,PartStartByte,ReadHWID,HWIDReadType,HWIDAddresses,HWIDFormat,HWIDLength,HWIDStartByte,ReadSecuritySeed,SpecialReqType,ReadCVN FROM Request_Method_Data where ReqMethID=?");
/*  181 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/*  182 */       stmt.setInt(1, requestMethodID);
/*  183 */       rs = stmt.executeQuery();
/*  184 */       while (rs.next()) {
/*  185 */         SPSRequestMethodData rmd = new SPSRequestMethodData(rs);
/*  186 */         result.add(rmd);
/*      */       } 
/*  188 */     } catch (Exception e) {
/*  189 */       throw e;
/*      */     } finally {
/*      */       try {
/*  192 */         if (rs != null) {
/*  193 */           rs.close();
/*      */         }
/*  195 */         if (stmt != null) {
/*  196 */           stmt.close();
/*      */         }
/*  198 */         if (conn != null) {
/*  199 */           db.releaseConnection(conn);
/*      */         }
/*  201 */       } catch (Exception x) {}
/*      */     } 
/*      */     
/*  204 */     if (result.size() == 0) {
/*  205 */       result.add(new SPSRequestMethodData(deviceID));
/*      */     }
/*  207 */     return result;
/*      */   }
/*      */   
/*      */   public List getProgrammingMessageVCI(SPSLanguage language, List instructions) throws Exception {
/*  211 */     if (instructions == null) {
/*  212 */       return null;
/*      */     }
/*  214 */     List<String> htmls = new ArrayList();
/*  215 */     Iterator<Integer> it = instructions.iterator();
/*  216 */     while (it.hasNext()) {
/*  217 */       Integer id = it.next();
/*  218 */       String html = getProgrammingMessageVCI(language, id.intValue());
/*  219 */       if (html != null) {
/*  220 */         htmls.add(html); continue;
/*      */       } 
/*  222 */       log.error("failed to retrieve vci message '" + id + "'");
/*      */     } 
/*      */     
/*  225 */     return (htmls.size() > 0) ? htmls : null;
/*      */   }
/*      */   
/*      */   public String getProgrammingMessageVCI(SPSLanguage language, int messageID) throws Exception {
/*  229 */     IDatabaseLink db = this.adapter.getDatabaseLink();
/*  230 */     Connection conn = null;
/*  231 */     DBMS.PreparedStatement stmt = null;
/*  232 */     ResultSet rs = null;
/*      */     try {
/*  234 */       conn = db.requestConnection();
/*  235 */       String sql = DBMS.getSQL(db, "SELECT Description FROM Pre_Post_Prog_Msg where Message_Id=? and Language_Code=?");
/*  236 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/*  237 */       stmt.setInt(1, messageID);
/*  238 */       stmt.setString(2, language.getID());
/*  239 */       rs = stmt.executeQuery();
/*  240 */       if (rs.next()) {
/*  241 */         String description = rs.getString(1);
/*  242 */         return (description != null) ? description.trim() : "";
/*      */       } 
/*  244 */     } catch (Exception e) {
/*  245 */       throw e;
/*      */     } finally {
/*      */       try {
/*  248 */         if (rs != null) {
/*  249 */           rs.close();
/*      */         }
/*  251 */         if (stmt != null) {
/*  252 */           stmt.close();
/*      */         }
/*  254 */         if (conn != null) {
/*  255 */           db.releaseConnection(conn);
/*      */         }
/*  257 */       } catch (Exception x) {}
/*      */     } 
/*      */     
/*  260 */     return null;
/*      */   }
/*      */   
/*      */   public String getReplaceMessageVCI(SPSLanguage language, int messageID) throws Exception {
/*  264 */     IDatabaseLink db = this.adapter.getDatabaseLink();
/*  265 */     Connection conn = null;
/*  266 */     DBMS.PreparedStatement stmt = null;
/*  267 */     ResultSet rs = null;
/*      */     try {
/*  269 */       conn = db.requestConnection();
/*  270 */       String sql = DBMS.getSQL(db, "SELECT DESCRIPTION FROM REPLACE_INSTRUCTIONS WHERE REPLACE_MESSAGE_ID=? and LANGUAGE_CODE=?");
/*  271 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/*  272 */       stmt.setInt(1, messageID);
/*  273 */       stmt.setString(2, language.getID());
/*  274 */       rs = stmt.executeQuery();
/*  275 */       if (rs.next()) {
/*  276 */         return rs.getString(1);
/*      */       }
/*  278 */       rs.close();
/*  279 */       stmt.setString(2, language.getLocale());
/*  280 */       rs = stmt.executeQuery();
/*  281 */       if (rs.next()) {
/*  282 */         return rs.getString(1);
/*      */       }
/*      */     }
/*  285 */     catch (Exception e) {
/*  286 */       throw e;
/*      */     } finally {
/*      */       try {
/*  289 */         if (rs != null) {
/*  290 */           rs.close();
/*      */         }
/*  292 */         if (stmt != null) {
/*  293 */           stmt.close();
/*      */         }
/*  295 */         if (conn != null) {
/*  296 */           db.releaseConnection(conn);
/*      */         }
/*  298 */       } catch (Exception x) {}
/*      */     } 
/*      */     
/*  301 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getVTDSecurityInfo(SPSVIN vin, String type) throws Exception {
/*  314 */     IDatabaseLink db = this.adapter.getDatabaseLink();
/*  315 */     Connection conn = null;
/*  316 */     DBMS.PreparedStatement stmt = null;
/*  317 */     ResultSet rs = null;
/*  318 */     StringBuffer security = new StringBuffer();
/*  319 */     security.append(type + ';');
/*      */     try {
/*  321 */       conn = db.requestConnection();
/*  322 */       String sql = DBMS.getSQL(db, "SELECT DISTINCT Device_Id, Algo_No FROM VTD_Security_Info WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?) AND (Engine = '~' OR Engine = ?) AND (Plant = '~' OR Plant = ?)");
/*  323 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/*  324 */       stmt.setString(1, DBMS.toString(vin.getModelYear()));
/*  325 */       stmt.setString(2, DBMS.toString(vin.getMake()));
/*  326 */       stmt.setString(3, DBMS.toString(vin.getLine()));
/*  327 */       stmt.setString(4, DBMS.toString(vin.getSeries()));
/*  328 */       stmt.setString(5, DBMS.toString(vin.getEngine()));
/*  329 */       stmt.setString(6, DBMS.toString(vin.getPosition(11)));
/*  330 */       rs = stmt.executeQuery();
/*  331 */       while (rs.next()) {
/*  332 */         int device = rs.getInt(1);
/*  333 */         int algorithm = rs.getInt(2);
/*  334 */         security.append(device + 58 + algorithm + 59);
/*      */       } 
/*  336 */       return (security.length() > type.length() + 1) ? security.toString() : null;
/*  337 */     } catch (Exception e) {
/*  338 */       throw e;
/*      */     } finally {
/*      */       try {
/*  341 */         if (rs != null) {
/*  342 */           rs.close();
/*      */         }
/*  344 */         if (stmt != null) {
/*  345 */           stmt.close();
/*      */         }
/*  347 */         if (conn != null) {
/*  348 */           db.releaseConnection(conn);
/*      */         }
/*  350 */       } catch (Exception x) {}
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean verifyVTDNavigationInfo(Long navInfo) throws Exception {
/*  358 */     IDatabaseLink db = this.adapter.getDatabaseLink();
/*  359 */     Connection conn = null;
/*  360 */     DBMS.PreparedStatement stmt = null;
/*  361 */     ResultSet rs = null;
/*      */     try {
/*  363 */       conn = db.requestConnection();
/*  364 */       String sql = DBMS.getSQL(db, "SELECT count(*) FROM VTDNavInfo WHERE Value = ?");
/*  365 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/*  366 */       stmt.setLong(1, navInfo.longValue());
/*  367 */       rs = stmt.executeQuery();
/*  368 */       if (rs.next()) {
/*  369 */         int count = rs.getInt(1);
/*  370 */         return (count > 0);
/*      */       } 
/*  372 */     } catch (Exception e) {
/*  373 */       throw e;
/*      */     } finally {
/*      */       try {
/*  376 */         if (rs != null) {
/*  377 */           rs.close();
/*      */         }
/*  379 */         if (stmt != null) {
/*  380 */           stmt.close();
/*      */         }
/*  382 */         if (conn != null) {
/*  383 */           db.releaseConnection(conn);
/*      */         }
/*  385 */       } catch (Exception x) {}
/*      */     } 
/*      */     
/*  388 */     return false;
/*      */   }
/*      */   
/*      */   public SPSControllerList getControllers(SPSSession session, List devices, Value mode, String toolType) throws Exception {
/*  392 */     return getSupportedControllers(session, devices, mode, toolType);
/*      */   }
/*      */   
/*      */   protected boolean checkJ2534(SPSController controller, String toolType) {
/*  396 */     if (toolType == null || toolType.equals("TEST_DRIVER")) {
/*  397 */       return true;
/*      */     }
/*      */ 
/*      */     
/*  401 */     Boolean j2534Dependency = ((SPSControllerVCI)controller).hasJ2534Dependency();
/*  402 */     if (j2534Dependency == null)
/*  403 */       return true; 
/*  404 */     if (j2534Dependency.equals(Boolean.TRUE)) {
/*  405 */       return (toolType.indexOf("J2534") >= 0);
/*      */     }
/*  407 */     return (toolType.indexOf("J2534") < 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean acceptDevice(List<Integer> devices, SPSController controller, String toolType) {
/*  413 */     if (devices == null) {
/*  414 */       return (controller.getDeviceID() == 253) ? checkJ2534(controller, toolType) : true;
/*      */     }
/*  416 */     for (int i = 0; i < devices.size(); i++) {
/*  417 */       Integer device = devices.get(i);
/*  418 */       if (device.intValue() == controller.getDeviceID()) {
/*  419 */         return (device.intValue() == 253) ? checkJ2534(controller, toolType) : true;
/*      */       }
/*      */     } 
/*  422 */     return false;
/*      */   }
/*      */   
/*      */   protected boolean isXMLController(SPSSchemaAdapterNAO adapter, SPSControllerData data) {
/*  426 */     if (data.getDeviceID() != 253) {
/*  427 */       return false;
/*      */     }
/*  429 */     return SPSControllerVCI.isXMLController(adapter, data.getControllerID(), data.getDescriptionIndex());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected String strip10380(String sql) {
/*  435 */     return StringUtilities.replace(sql, ",Suppress_Sequence_Controller", "");
/*      */   }
/*      */ 
/*      */   
/*      */   protected String strip8746(String sql) {
/*  440 */     return StringUtilities.replace(sql, ",SecCode_required", "");
/*      */   }
/*      */   
/*      */   protected void setParametersVIN(SPSVIN vin, DBMS.PreparedStatement stmt) throws SQLException {
/*  444 */     stmt.setString(1, DBMS.toString(vin.getModelYear()));
/*  445 */     stmt.setString(2, DBMS.toString(vin.getMake()));
/*  446 */     stmt.setString(3, DBMS.toString(vin.getLine()));
/*  447 */     stmt.setString(4, DBMS.toString(vin.getSeries()));
/*  448 */     stmt.setString(5, DBMS.toString(vin.getEngine()));
/*      */   }
/*      */   
/*      */   protected SPSControllerList getSupportedControllers(SPSSession session, List devices, Value mode, String toolType) throws Exception {
/*  452 */     SPSControllerList list = new SPSControllerList();
/*  453 */     SPSControllerList pslist = new SPSControllerList();
/*      */ 
/*      */     
/*  456 */     List<SPSProgrammingSequence> sequences = new ArrayList();
/*  457 */     list.registerSession(session);
/*  458 */     boolean clearDTCs = false;
/*  459 */     int vehicleLink = 0;
/*  460 */     IDatabaseLink db = this.adapter.getDatabaseLink();
/*  461 */     Connection conn = null;
/*  462 */     DBMS.PreparedStatement stmt = null;
/*  463 */     ResultSet rs = null;
/*  464 */     List<SPSControllerData> records = new ArrayList();
/*      */     try {
/*  466 */       conn = db.requestConnection();
/*  467 */       String sql = DBMS.getSQL(db, "SELECT Controller_Id,RPO_Code,Pre_RPO_Code,Post_RPO_Code,Device_Id,ReqMthID,Programming_Methods,Vci,Pre_Prog_Msgs,Post_Prog_Msgs,VIN_Pos,VIN_Value,VIN_Digit_4_OR_6,Beginning_Sequence,Ending_Sequence,REPLACE_MESSAGE_ID,REPLACE_ID,Desc_Indx,Sec_Id,Suppress_Sequence_Controller,SecCode_Required,Engine,Series,Line FROM Supported_Controllers WHERE Model_Year = ? AND Make = ? AND Line = ? AND (Series = '~' OR Series = ?) AND (Engine = '~' OR Engine = ?)");
/*  468 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/*  469 */       SPSVIN vin = (SPSVIN)session.getVehicle().getVIN();
/*  470 */       setParametersVIN(vin, stmt);
/*      */       try {
/*  472 */         rs = stmt.executeQuery();
/*  473 */       } catch (Exception e) {
/*  474 */         stmt.close();
/*  475 */         if (e.getMessage().toUpperCase().indexOf("SUPPRESS_SEQUENCE_CONTROLLER") > 0) {
/*  476 */           stmt = DBMS.prepareSQLStatement(conn, strip10380(sql));
/*  477 */         } else if (e.getMessage().toUpperCase().indexOf("SECCODE_REQUIRED") > 0) {
/*  478 */           stmt = DBMS.prepareSQLStatement(conn, strip8746(sql));
/*      */         } 
/*  480 */         setParametersVIN(vin, stmt);
/*  481 */         rs = stmt.executeQuery();
/*      */       } 
/*  483 */       while (rs.next()) {
/*  484 */         records.add(new SPSControllerData(db, rs));
/*      */       }
/*  486 */     } catch (Exception e) {
/*  487 */       throw e;
/*      */     } finally {
/*      */       try {
/*  490 */         if (rs != null) {
/*  491 */           rs.close();
/*      */         }
/*  493 */         if (stmt != null) {
/*  494 */           stmt.close();
/*      */         }
/*  496 */         if (conn != null) {
/*  497 */           db.releaseConnection(conn);
/*      */         }
/*  499 */       } catch (Exception x) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  504 */     Set groups = Collections.EMPTY_SET;
/*      */     try {
/*  506 */       ClientContext context = ClientContextProvider.getInstance().getContext(session.getSessionID());
/*  507 */       groups = context.getSharedContext().getUsrGroup2ManufMap().keySet();
/*  508 */     } catch (Exception e) {
/*  509 */       log.warn("unable to determine user groups - exception:" + e + ", continuing without any group");
/*      */     } 
/*  511 */     records = reduceRecords(records);
/*  512 */     for (int i = 0; i < records.size(); i++) {
/*  513 */       SPSControllerData data = records.get(i);
/*  514 */       if (!checkAccess(data.getSecurityID(), groups) && !"sps-local".equals(session.getSessionID())) {
/*  515 */         log.debug("skipped controller: " + data.getControllerID() + " - access denied for user: " + session.getSessionID());
/*      */       } else {
/*      */         
/*  518 */         SPSQualification qualification = new SPSQualification(data);
/*  519 */         if (qualification.qualifies((SPSVehicle)session.getVehicle()) || "sps-local".equals(session.getSessionID())) {
/*  520 */           int device = data.getDeviceID();
/*  521 */           if (device != 255)
/*      */           {
/*  523 */             if (device == 252) {
/*  524 */               if (!"T2_REMOTE".equals(toolType)) {
/*      */ 
/*      */                 
/*  527 */                 int id = data.getControllerID();
/*  528 */                 int psid = data.getProgrammingSequenceID();
/*  529 */                 List preProgrammingInstructions = SPSProgrammingInstructions.tokenize(data.getPreProgrammingInstructions());
/*  530 */                 List postProgrammingInstructions = SPSProgrammingInstructions.tokenize(data.getPostProgrammingInstructions());
/*  531 */                 int descIndex = data.getDescriptionIndex();
/*  532 */                 SPSProgrammingSequence sequence = new SPSProgrammingSequence(session, id, descIndex, psid, preProgrammingInstructions, postProgrammingInstructions, this.adapter);
/*  533 */                 sequences.add(sequence);
/*      */               } 
/*      */             } else {
/*  536 */               SPSController controller = isXMLController(this.adapter, data) ? new SPSControllerXML(session, data, this.adapter) : new SPSControllerVCI(session, data, this.adapter);
/*  537 */               if (controller.getDescription() != null)
/*      */               {
/*      */                 
/*  540 */                 if (acceptDevice(devices, controller, toolType)) {
/*  541 */                   SPSRequestMethodData rmd = getTableDrivenRequestMethodData(controller);
/*  542 */                   if (rmd != null) {
/*  543 */                     clearDTCs = (clearDTCs || rmd.getProtocol() == 1 || rmd.getProtocol() == 3);
/*  544 */                     vehicleLink = determineVehicleLink(vehicleLink, rmd);
/*      */                   } 
/*  546 */                   list.insert(controller);
/*      */                 } else {
/*  548 */                   log.debug("skipped controller: " + data.getControllerID() + " (device restriction)");
/*      */                 
/*      */                 }
/*      */ 
/*      */               
/*      */               }
/*      */             
/*      */             }
/*      */ 
/*      */           
/*      */           }
/*      */         }
/*      */         else {
/*      */           
/*  562 */           log.debug("skipped controller: " + data.getControllerID() + " (VIN restriction)");
/*      */         } 
/*      */       } 
/*  565 */     }  session.setClearDTCFlag(clearDTCs);
/*  566 */     session.setVehicleLink(vehicleLink);
/*  567 */     if (sequences.size() > 0) {
/*  568 */       handleProgrammingSequences(session, mode, list, pslist, sequences);
/*      */     }
/*  570 */     Collection<SPSControllerPROM> proms = getSupportedControllersPROM(session);
/*  571 */     if (proms != null) {
/*  572 */       while (proms.size() > 1) {
/*  573 */         Iterator<SPSControllerPROM> it = proms.iterator();
/*  574 */         SPSControllerPROM min = proms.iterator().next();
/*  575 */         while (it.hasNext()) {
/*  576 */           SPSControllerPROM controller = it.next();
/*  577 */           if (min.getPROMPartNo() > controller.getPROMPartNo()) {
/*  578 */             min = controller;
/*      */           }
/*      */         } 
/*  581 */         list.insert(min);
/*  582 */         proms.remove(min);
/*      */       } 
/*  584 */       list.insert(proms.iterator().next());
/*      */     } 
/*  586 */     list.qualify(session.getVehicle().getOptions(), this.adapter);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  591 */     return list;
/*      */   }
/*      */   
/*      */   protected List reduceRecords(List<SPSControllerData> records) {
/*  595 */     Set<String> controllers = new HashSet();
/*  596 */     for (int i = 0; i < records.size(); i++) {
/*  597 */       SPSControllerData data = records.get(i);
/*  598 */       controllers.add(data.getControllerIdentity() + ":" + data.getDescriptionIndex());
/*      */     } 
/*  600 */     List result = new ArrayList();
/*  601 */     Iterator<String> it = controllers.iterator();
/*  602 */     while (it.hasNext()) {
/*  603 */       String controller = it.next();
/*  604 */       result.addAll(reduceRecords(controller, records));
/*      */     } 
/*  606 */     return result;
/*      */   }
/*      */   
/*      */   protected List reduceRecords(String controller, List<SPSControllerData> records) {
/*  610 */     List<SPSControllerData> subset = new ArrayList();
/*  611 */     for (int i = 0; i < records.size(); i++) {
/*  612 */       SPSControllerData data = records.get(i);
/*  613 */       if (controller.equals(data.getControllerIdentity() + ":" + data.getDescriptionIndex())) {
/*  614 */         subset.add(data);
/*      */       }
/*      */     } 
/*  617 */     List bestMatch = reduceRecords(controller, "Engine", subset);
/*  618 */     if (bestMatch.size() == 1) {
/*  619 */       return bestMatch;
/*      */     }
/*  621 */     bestMatch = reduceRecords(controller, "Series", bestMatch);
/*  622 */     if (bestMatch.size() == 1) {
/*  623 */       return bestMatch;
/*      */     }
/*  625 */     return reduceRecords(controller, "Line", bestMatch);
/*      */   }
/*      */   
/*      */   protected List reduceRecords(String controller, String criteria, List<SPSControllerData> records) {
/*  629 */     List<SPSControllerData> bestMatch = new ArrayList();
/*  630 */     for (int i = 0; i < records.size(); i++) {
/*  631 */       SPSControllerData data = records.get(i);
/*  632 */       if (criteria.equals("Engine") && !data.getEngine().equals("~")) {
/*  633 */         bestMatch.add(data);
/*      */       }
/*  635 */       if (criteria.equals("Series") && !data.getSeries().equals("~")) {
/*  636 */         bestMatch.add(data);
/*      */       }
/*  638 */       if (criteria.equals("Line") && !data.getLine().equals("~")) {
/*  639 */         bestMatch.add(data);
/*      */       }
/*      */     } 
/*  642 */     if (bestMatch.size() > 0) {
/*  643 */       return bestMatch;
/*      */     }
/*  645 */     return records;
/*      */   }
/*      */ 
/*      */   
/*      */   protected SPSRequestMethodData getTableDrivenRequestMethodData(SPSController controller) {
/*  650 */     int rmid = controller.getRequestMethodID();
/*  651 */     if (rmid == 0) {
/*  652 */       return null;
/*      */     }
/*  654 */     List<SPSRequestMethodData> rmd = StaticData.getInstance(this.adapter).getRequestMethodData();
/*  655 */     for (int i = 0; i < rmd.size(); i++) {
/*  656 */       SPSRequestMethodData data = rmd.get(i);
/*  657 */       if (data.getDeviceID() == controller.getDeviceID() && data.getRequestMethodID() == controller.getRequestMethodID()) {
/*  658 */         return data;
/*      */       }
/*      */     } 
/*  661 */     return null;
/*      */   }
/*      */   
/*      */   protected int determineVehicleLink(int vehicleLink, SPSRequestMethodData rmd) {
/*  665 */     if (rmd.getProtocol() == 3 && 
/*  666 */       rmd.getPINLink() > vehicleLink) {
/*  667 */       vehicleLink = rmd.getPINLink();
/*      */     }
/*      */     
/*  670 */     return vehicleLink;
/*      */   }
/*      */   
/*      */   private String getSecurityList(String secID) {
/*  674 */     synchronized (this.security) {
/*  675 */       return (String)this.security.get(secID);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void storeSecurityList(String secID, String secList) {
/*  680 */     synchronized (this.security) {
/*  681 */       this.security.put(secID, secList);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean checkAccess(String secID, Collection userGroups) {
/*  687 */     if (secID == null || secID.trim().length() == 0) {
/*  688 */       return true;
/*      */     }
/*  690 */     String secList = getSecurityList(secID);
/*  691 */     if (secList == null) {
/*  692 */       secList = loadSecurityList(secID);
/*  693 */       if (secList == null) {
/*  694 */         return true;
/*      */       }
/*  696 */       storeSecurityList(secID, secList);
/*      */     } 
/*      */     
/*  699 */     boolean ret = false;
/*      */     try {
/*  701 */       if (secList != null && userGroups != null) {
/*  702 */         for (CSTokenizer tokenizer = new CSTokenizer(secList, ","); tokenizer.hasNext() && !ret; ) {
/*  703 */           String secToken = ((String)tokenizer.next()).trim();
/*  704 */           ret = userGroups.contains(secToken);
/*      */         } 
/*      */       }
/*  707 */     } catch (Exception e) {
/*  708 */       log.warn("unable to check access permission - exception:" + e + ", returning false");
/*  709 */       ret = false;
/*      */     } 
/*  711 */     return ret;
/*      */   }
/*      */   
/*      */   protected String loadSecurityList(String secID) {
/*  715 */     Connection conn = null;
/*  716 */     IDatabaseLink db = this.adapter.getDatabaseLink();
/*  717 */     DBMS.PreparedStatement stmt = null;
/*  718 */     ResultSet rs = null;
/*      */     
/*  720 */     try { conn = db.requestConnection();
/*  721 */       String sql = DBMS.getSQL(db, "SELECT Sec_List FROM Security_Groups WHERE Sec_Id = ?");
/*  722 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/*  723 */       stmt.setString(1, secID);
/*  724 */       rs = stmt.executeQuery();
/*  725 */       if (rs.next()) {
/*  726 */         return rs.getString(1);
/*      */       } }
/*  728 */     catch (Exception e) {  }
/*      */     finally
/*      */     { try {
/*  731 */         if (rs != null) {
/*  732 */           rs.close();
/*      */         }
/*  734 */         if (stmt != null) {
/*  735 */           stmt.close();
/*      */         }
/*  737 */         if (conn != null) {
/*  738 */           db.releaseConnection(conn);
/*      */         }
/*  740 */       } catch (Exception x) {} }
/*      */ 
/*      */     
/*  743 */     return null;
/*      */   }
/*      */   
/*      */   protected void handleProgrammingSequences(SPSSession session, Value mode, SPSControllerList controllers, SPSControllerList pscontrollers, List<SPSProgrammingSequence> sequences) {
/*  747 */     boolean modeReplaceReprogram = (mode != null && mode.equals(CommonValue.REPLACE_AND_REPROGRAM));
/*  748 */     for (int i = 0; i < sequences.size(); i++) {
/*  749 */       SPSProgrammingSequence ps = sequences.get(i);
/*  750 */       if (ps.isValid(session, modeReplaceReprogram, controllers, pscontrollers, this.adapter)) {
/*  751 */         controllers.add(new SPSControllerReference(ps));
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   protected Collection getSupportedControllersPROM(SPSSession session) throws Exception {
/*  757 */     Map<Object, Object> controllers = new HashMap<Object, Object>();
/*  758 */     SPSVIN vin = (SPSVIN)session.getVehicle().getVIN();
/*  759 */     char make = vin.getTruckMake(this.adapter);
/*  760 */     String vinLine = vin.getVINLine(make, this.adapter);
/*  761 */     if (vinLine == null) {
/*  762 */       return null;
/*      */     }
/*  764 */     String engineRPO = getEngineRPO(vin, make);
/*  765 */     if (engineRPO == null) {
/*  766 */       return null;
/*      */     }
/*  768 */     IDatabaseLink db = this.adapter.getDatabaseLink();
/*  769 */     Connection conn = null;
/*  770 */     DBMS.PreparedStatement stmt = null;
/*  771 */     ResultSet rs = null;
/*  772 */     char series = vin.getSeries();
/*  773 */     if (vin.getModelYear() < 'F' && !vin.isTruck()) {
/*  774 */       series = '~';
/*      */     }
/*      */     try {
/*  777 */       conn = db.requestConnection();
/*  778 */       String sql = DBMS.getSQL(db, "SELECT DISTINCT a.PROM_No, a.RPO_Code, a.RPO_Type, a.RPO_Exists, a.RPO_Code_Label_ID FROM PROM_Supported_PROMs a, PROM_Supported_PROMs b WHERE b.Model_Year = ? AND b.Make = ? AND b.Line = ? AND (b.Series = ? OR b.Series = '~') AND b.RPO_Code = ? AND a.Model_Year = b.Model_Year AND a.Make = b.Make AND a.Line = b.Line AND a.Series = b.Series AND a.PROM_No = b.PROM_No");
/*  779 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/*  780 */       stmt.setString(1, DBMS.toString(vin.getModelYear()));
/*  781 */       stmt.setString(2, DBMS.toString(make));
/*  782 */       stmt.setString(3, DBMS.toString(vinLine.charAt(0)));
/*  783 */       stmt.setString(4, DBMS.toString(series));
/*  784 */       stmt.setString(5, engineRPO);
/*  785 */       rs = stmt.executeQuery();
/*  786 */       while (rs.next()) {
/*  787 */         Integer prom = Integer.valueOf(rs.getInt(1));
/*  788 */         String rpoCode = rs.getString(2).trim();
/*  789 */         String rpoType = rs.getString(3).trim();
/*  790 */         String rpoExists = rs.getString(4).trim();
/*  791 */         int rpoCodeLabel = rs.getInt(5);
/*  792 */         SPSControllerPROM controller = (SPSControllerPROM)controllers.get(prom);
/*  793 */         if (controller == null) {
/*  794 */           controller = new SPSControllerPROM(session, prom, this.adapter);
/*  795 */           controllers.put(prom, controller);
/*      */         } 
/*  797 */         if (rpoCodeLabel == 2 && rpoExists.equals("Y")) {
/*  798 */           controller.registerEmissionRPOType(rpoType, rpoCode, this.adapter);
/*      */         }
/*  800 */         if (engineRPO.equals(rpoCode)) {
/*  801 */           controller.registerEngineRPO(engineRPO, this.adapter); continue;
/*      */         } 
/*  803 */         controller.registerRPO(rpoCode, rpoType, rpoCodeLabel, this.adapter);
/*      */       }
/*      */     
/*  806 */     } catch (Exception e) {
/*  807 */       throw e;
/*      */     } finally {
/*      */       try {
/*  810 */         if (rs != null) {
/*  811 */           rs.close();
/*      */         }
/*  813 */         if (stmt != null) {
/*  814 */           stmt.close();
/*      */         }
/*  816 */       } catch (Exception x) {}
/*      */     } 
/*      */     
/*  819 */     if (controllers.size() == 0) {
/*      */       try {
/*  821 */         if (conn != null) {
/*  822 */           db.releaseConnection(conn);
/*      */         }
/*  824 */       } catch (Exception x) {}
/*      */       
/*  826 */       return null;
/*      */     } 
/*      */     try {
/*  829 */       String sql = DBMS.getSQL(db, "SELECT DISTINCT PROM_No, Transm_Type, Air_Flag, PROM_Part_No, Broadcast_Code, Program_Part_No, OEM_No, ECU_Part_No, Scanner_ID FROM PROM_Data WHERE PROM_No IN (#list#)", controllers.size());
/*  830 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/*  831 */       Iterator<Integer> it = controllers.keySet().iterator();
/*  832 */       int pos = 0;
/*  833 */       while (it.hasNext()) {
/*  834 */         stmt.setInt(++pos, ((Integer)it.next()).intValue());
/*      */       }
/*  836 */       rs = stmt.executeQuery();
/*  837 */       while (rs.next()) {
/*  838 */         Integer prom = Integer.valueOf(rs.getInt(1));
/*  839 */         String tt = rs.getString(2).trim();
/*      */         
/*  841 */         String af = rs.getString(3).trim().toLowerCase(Locale.ENGLISH);
/*      */         
/*  843 */         SPSControllerPROM controller = (SPSControllerPROM)controllers.get(prom);
/*  844 */         controller.registerTransmissionFlag(tt);
/*  845 */         controller.registerAirconditionFlag(af);
/*  846 */         controller.setPROMPartNo(rs.getInt(4));
/*  847 */         controller.setBroadcastCode(rs.getString(5).trim());
/*  848 */         controller.setProgramPartNo(rs.getString(6).trim());
/*  849 */         controller.setOEMNo(rs.getInt(7));
/*  850 */         controller.setECUPartNo(rs.getInt(8));
/*  851 */         controller.setScannerID(rs.getInt(9));
/*      */       } 
/*  853 */     } catch (Exception e) {
/*  854 */       throw e;
/*      */     } finally {
/*      */       try {
/*  857 */         if (rs != null) {
/*  858 */           rs.close();
/*      */         }
/*  860 */         if (stmt != null) {
/*  861 */           stmt.close();
/*      */         }
/*  863 */         if (conn != null) {
/*  864 */           db.releaseConnection(conn);
/*      */         }
/*  866 */       } catch (Exception x) {}
/*      */     } 
/*      */     
/*  869 */     handlePROMOptions(controllers.values());
/*  870 */     return (controllers.size() > 0) ? controllers.values() : null;
/*      */   }
/*      */   
/*      */   protected boolean isWildcard(String flag) {
/*  874 */     return flag.equals("~");
/*      */   }
/*      */   
/*      */   protected void handlePROMOptions(Collection controllers) {
/*  878 */     if (controllers == null || controllers.size() == 0) {
/*      */       return;
/*      */     }
/*  881 */     handleTransmissionOptions(controllers);
/*  882 */     handleAirconditionOptions(controllers);
/*  883 */     handleEmissionTypeOptions(controllers);
/*      */   }
/*      */   
/*      */   protected void handleTransmissionOptions(Collection<SPSControllerPROM> controllers) {
/*  887 */     if (controllers == null || controllers.size() == 0) {
/*      */       return;
/*      */     }
/*  890 */     String defaultFlag = null;
/*  891 */     boolean hasWildcards = false;
/*      */ 
/*      */     
/*  894 */     Iterator<SPSControllerPROM> it = controllers.iterator();
/*  895 */     while (it.hasNext()) {
/*  896 */       SPSControllerPROM controller = it.next();
/*  897 */       List<String> transmissions = controller.getTransmissionFlags();
/*  898 */       if (transmissions == null || transmissions.size() == 2) {
/*  899 */         hasWildcards = true;
/*      */         continue;
/*      */       } 
/*  902 */       String tflag = transmissions.get(0);
/*  903 */       if (isWildcard(tflag)) {
/*  904 */         hasWildcards = true; continue;
/*  905 */       }  if (defaultFlag == null) {
/*  906 */         defaultFlag = tflag; continue;
/*  907 */       }  if (!defaultFlag.equals(tflag)) {
/*  908 */         defaultFlag = "~";
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  913 */     if (defaultFlag != null && (isWildcard(defaultFlag) || hasWildcards)) {
/*  914 */       it = controllers.iterator();
/*  915 */       while (it.hasNext()) {
/*  916 */         SPSControllerPROM controller = it.next();
/*  917 */         controller.evaluateTransmissionFlags(this.adapter);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void handleAirconditionOptions(Collection<SPSControllerPROM> controllers) {
/*  923 */     if (controllers == null || controllers.size() == 0) {
/*      */       return;
/*      */     }
/*  926 */     String defaultFlag = null;
/*  927 */     boolean hasWildcards = false;
/*      */ 
/*      */     
/*  930 */     Iterator<SPSControllerPROM> it = controllers.iterator();
/*  931 */     while (it.hasNext()) {
/*  932 */       SPSControllerPROM controller = it.next();
/*  933 */       List<String> airconditions = controller.getAirconditionFlags();
/*  934 */       if (airconditions == null || airconditions.size() == 2) {
/*  935 */         hasWildcards = true;
/*      */         continue;
/*      */       } 
/*  938 */       String aflag = airconditions.get(0);
/*  939 */       if (isWildcard(aflag)) {
/*  940 */         hasWildcards = true; continue;
/*  941 */       }  if (defaultFlag == null) {
/*  942 */         defaultFlag = aflag; continue;
/*  943 */       }  if (!defaultFlag.equals(aflag)) {
/*  944 */         defaultFlag = "~";
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  949 */     if (defaultFlag != null && (isWildcard(defaultFlag) || hasWildcards)) {
/*  950 */       it = controllers.iterator();
/*  951 */       while (it.hasNext()) {
/*  952 */         SPSControllerPROM controller = it.next();
/*  953 */         controller.evaluateAirconditionFlags(this.adapter);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void handleEmissionTypeOptions(Collection<SPSControllerPROM> controllers) {
/*  959 */     if (controllers == null || controllers.size() == 0) {
/*      */       return;
/*      */     }
/*  962 */     Set options = new HashSet();
/*  963 */     boolean isQualifier = false;
/*  964 */     Iterator<SPSControllerPROM> it = controllers.iterator();
/*  965 */     while (it.hasNext() && !isQualifier) {
/*  966 */       SPSControllerPROM controller = it.next();
/*  967 */       List<SPSOption> emissions = controller.getEmissionTypes();
/*  968 */       if (emissions == null || emissions.size() == 4)
/*      */         continue; 
/*  970 */       if (options.isEmpty()) {
/*  971 */         options.addAll(emissions); continue;
/*  972 */       }  if (options.size() != emissions.size()) {
/*  973 */         isQualifier = true; continue;
/*      */       } 
/*  975 */       for (int i = 0; i < emissions.size(); i++) {
/*  976 */         SPSOption option = emissions.get(i);
/*  977 */         if (!options.contains(option)) {
/*  978 */           isQualifier = true;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*  984 */     if (isQualifier) {
/*  985 */       it = controllers.iterator();
/*  986 */       while (it.hasNext()) {
/*  987 */         SPSControllerPROM controller = it.next();
/*  988 */         controller.evaluateEmissionTypes();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getEngineRPO(SPSVIN vin, char make) throws Exception {
/*  999 */     IDatabaseLink db = this.adapter.getDatabaseLink();
/* 1000 */     Connection conn = null;
/* 1001 */     DBMS.PreparedStatement stmt = null;
/* 1002 */     ResultSet rs = null;
/*      */     try {
/* 1004 */       conn = db.requestConnection();
/* 1005 */       String sql = DBMS.getSQL(db, "SELECT ENG_RPO FROM PROM_VIN_Engine Where Model_Year = ? AND ENG_CorT = ? AND ENG_Divuse = ? AND ENG_Code = ? AND (ENG_Body Like ? OR ENG_Body Like ? OR ENG_Body = '*')");
/* 1006 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 1007 */       String vtype = vin.isTruck() ? "T" : "C";
/* 1008 */       if (vin.isTruck() && vin.getLine() == 'W' && vin.getSeries() == '8') {
/* 1009 */         vtype = "C";
/*      */       }
/* 1011 */       stmt.setString(1, DBMS.toString(vin.getModelYear()));
/* 1012 */       stmt.setString(2, vtype);
/* 1013 */       stmt.setString(3, DBMS.toString(make));
/* 1014 */       stmt.setString(4, DBMS.toString(vin.getEngine()));
/* 1015 */       stmt.setString(5, vin.getLineSeries().charAt(0) + "%");
/* 1016 */       stmt.setString(6, "%," + vin.getLineSeries().charAt(0) + "%");
/* 1017 */       rs = stmt.executeQuery();
/* 1018 */       if (rs.next()) {
/* 1019 */         return rs.getString(1).trim();
/*      */       }
/* 1021 */     } catch (Exception e) {
/* 1022 */       throw e;
/*      */     } finally {
/*      */       try {
/* 1025 */         if (rs != null) {
/* 1026 */           rs.close();
/*      */         }
/* 1028 */         if (stmt != null) {
/* 1029 */           stmt.close();
/*      */         }
/* 1031 */         if (conn != null) {
/* 1032 */           db.releaseConnection(conn);
/*      */         }
/* 1034 */       } catch (Exception x) {}
/*      */     } 
/*      */     
/* 1037 */     return null;
/*      */   }
/*      */   
/*      */   public static final class StaticData
/*      */   {
/* 1042 */     private List rmd = new ArrayList();
/*      */     
/*      */     private StaticData(SPSSchemaAdapterNAO adapter) {
/* 1045 */       IDatabaseLink db = adapter.getDatabaseLink();
/* 1046 */       Connection conn = null;
/* 1047 */       DBMS.PreparedStatement stmt = null;
/* 1048 */       ResultSet rs = null;
/*      */       try {
/* 1050 */         conn = db.requestConnection();
/* 1051 */         String sql = DBMS.getSQL(db, "SELECT ReqMethID,DeviceID,Protocol,Pin_Link,ReadVIN,VINReadType,VINAddresses,ReadParts,PartReadType,PartAddresses,PartFormat,PartLength,PartStartByte,ReadHWID,HWIDReadType,HWIDAddresses,HWIDFormat,HWIDLength,HWIDStartByte,ReadSecuritySeed,SpecialReqType,ReadCVN FROM Request_Method_Data");
/* 1052 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/* 1053 */         rs = stmt.executeQuery();
/* 1054 */         while (rs.next()) {
/* 1055 */           SPSRequestMethodData data = new SPSRequestMethodData(rs);
/* 1056 */           this.rmd.add(data);
/*      */         } 
/* 1058 */       } catch (Exception e) {
/* 1059 */         throw new RuntimeException(e);
/*      */       } finally {
/*      */         try {
/* 1062 */           if (rs != null) {
/* 1063 */             rs.close();
/*      */           }
/* 1065 */           if (stmt != null) {
/* 1066 */             stmt.close();
/*      */           }
/* 1068 */           if (conn != null) {
/* 1069 */             db.releaseConnection(conn);
/*      */           }
/* 1071 */         } catch (Exception x) {
/* 1072 */           SPSModel.log.error("failed to clean-up: ", x);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public List getRequestMethodData() {
/* 1078 */       return this.rmd;
/*      */     }
/*      */ 
/*      */     
/*      */     public void init() {}
/*      */     
/*      */     public static StaticData getInstance(SPSSchemaAdapterNAO adapter) {
/* 1085 */       synchronized (adapter.getSyncObject()) {
/* 1086 */         StaticData instance = (StaticData)adapter.getObject(StaticData.class);
/* 1087 */         if (instance == null) {
/* 1088 */           instance = new StaticData(adapter);
/* 1089 */           adapter.storeObject(StaticData.class, instance);
/*      */         } 
/* 1091 */         return instance;
/*      */       } 
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\nao\SPSModel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */