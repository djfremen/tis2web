/*      */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.global;
/*      */ import com.eoos.datatype.gtwo.Pair;
/*      */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*      */ import com.eoos.gm.tis2web.sps.common.VIT1Data;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Archive;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.PartFile;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSDescription;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSPart;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSProgrammingData;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSSchemaAdapter;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSSession;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSVIN;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.xml.XMLProgrammingTypes;
/*      */ import java.sql.Connection;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.StringTokenizer;
/*      */ 
/*      */ public class SPSControllerVCI implements SPSController {
/*      */   private static final long serialVersionUID = 1L;
/*      */   static final int USE_DEFAULT_VCI = -1;
/*      */   protected transient SPSSession session;
/*      */   protected transient List hardware;
/*      */   protected transient Archive archive;
/*      */   protected transient PartFile file;
/*      */   protected int id;
/*      */   protected int descIndex;
/*   33 */   private static Logger log = Logger.getLogger(SPSControllerVCI.class); protected String description; protected Boolean j2534Dependency; protected String preRPOCode; protected String postRPOCode; protected int deviceID; protected int requestMethodID; protected List programmingTypes;
/*      */   
/*      */   public static boolean isXMLController(SPSSchemaAdapterGlobal adapter, int controllerID, int descriptionIndex) {
/*   36 */     return StaticData.getInstance(adapter).isXMLControllerConfiguration(controllerID, descriptionIndex);
/*      */   }
/*      */   protected int defaultVCI; protected int actualVCI; protected int VCI; protected List preProgrammingInstructions; protected List postProgrammingInstructions; protected int replaceInstruction; protected List replaceAttributes; protected String afterMarketFlag; protected Object data;
/*      */   protected boolean isCustomCalibrationController;
/*      */   protected boolean suppressSequenceControllerFlag;
/*      */   protected boolean securityCodeRequired;
/*      */   protected int optionListID;
/*      */   protected transient SPSSchemaAdapterGlobal adapter;
/*      */   
/*      */   public static final class StaticData { private Map descriptions;
/*      */     private Map programmingTypes;
/*      */     
/*      */     public Map getDescriptions() {
/*   49 */       return this.descriptions;
/*      */     }
/*      */     private Map replacers; private List customCalibrationControllerList;
/*      */     public Map getProgrammingTypes() {
/*   53 */       return this.programmingTypes;
/*      */     }
/*      */     
/*      */     public Map getReplacers() {
/*   57 */       return this.replacers;
/*      */     }
/*      */ 
/*      */     
/*      */     public void init() {}
/*      */     
/*      */     public void initCustomCalibrationControllers(List controllers) {
/*   64 */       this.customCalibrationControllerList = controllers;
/*      */     }
/*      */     
/*      */     public boolean isCustomCalibrationController(int controllerID) {
/*   68 */       if (this.customCalibrationControllerList == null) {
/*   69 */         return false;
/*      */       }
/*   71 */       for (int i = 0; i < this.customCalibrationControllerList.size(); i++) {
/*   72 */         if (controllerID == ((Integer)this.customCalibrationControllerList.get(i)).intValue()) {
/*   73 */           return true;
/*      */         }
/*      */       } 
/*   76 */       return false;
/*      */     }
/*      */     
/*      */     public boolean isXMLControllerConfiguration(int controllerID, int descriptionIndex) {
/*   80 */       Integer progtype = (Integer)this.programmingTypes.get(controllerID + ":" + descriptionIndex);
/*   81 */       return XMLProgrammingTypes.CONFIGURATION.equals(progtype);
/*      */     }
/*      */     
/*      */     private StaticData(SPSSchemaAdapterGlobal adapter) {
/*   85 */       IDatabaseLink dblink = adapter.getDatabaseLink();
/*   86 */       this.descriptions = new HashMap<Object, Object>();
/*   87 */       this.programmingTypes = new HashMap<Object, Object>();
/*   88 */       Connection conn = null;
/*   89 */       DBMS.PreparedStatement stmt = null;
/*   90 */       ResultSet rs = null;
/*      */       try {
/*   92 */         conn = dblink.requestConnection();
/*   93 */         String sql = DBMS.getSQL(dblink, "SELECT Controller_Id, Language_Code, Controller_Name, Description, Desc_Indx, Prog_Type_ID FROM Controller_Description");
/*      */         try {
/*   95 */           stmt = DBMS.prepareSQLStatement(conn, sql);
/*   96 */           rs = stmt.executeQuery();
/*   97 */         } catch (SQLException x) {
/*   98 */           stmt = DBMS.prepareSQLStatement(conn, DBMS.getSQL(dblink, "SELECT Controller_Id, Language_Code, Controller_Name, Description, Desc_Indx FROM Controller_Description"));
/*   99 */           rs = stmt.executeQuery();
/*      */         } 
/*  101 */         while (rs.next()) {
/*  102 */           Integer id = Integer.valueOf(rs.getInt(1));
/*  103 */           String lg = rs.getString(2);
/*  104 */           Integer descIndex = Integer.valueOf(rs.getInt("Desc_Indx"));
/*      */           
/*  106 */           String key = id.toString() + ":" + descIndex.toString();
/*  107 */           SPSDescription description = (SPSDescription)this.descriptions.get(key);
/*  108 */           if (description == null) {
/*  109 */             description = new SPSDescription();
/*  110 */             this.descriptions.put(key, description);
/*      */           } 
/*  112 */           SPSLanguage locale = SPSLanguage.getLanguage(lg, adapter);
/*  113 */           description.add(locale, rs.getString("Controller_Name").trim() + '\t' + DBMS.getString(dblink, locale, rs, "Description"));
/*      */ 
/*      */           
/*      */           try {
/*  117 */             this.programmingTypes.put(key, Integer.valueOf(rs.getInt("Prog_Type_ID")));
/*  118 */           } catch (SQLException x) {}
/*      */         } 
/*      */         
/*  121 */         SPSControllerVCI.log.info("loaded nao controller descriptions (" + this.descriptions.size() + " controller-ids).");
/*  122 */       } catch (Exception e) {
/*  123 */         throw new RuntimeException(e);
/*      */       } finally {
/*      */         try {
/*  126 */           if (rs != null) {
/*  127 */             rs.close();
/*  128 */             rs = null;
/*      */           } 
/*  130 */           if (stmt != null) {
/*  131 */             stmt.close();
/*  132 */             stmt = null;
/*      */           } 
/*  134 */         } catch (Exception x) {
/*  135 */           SPSControllerVCI.log.error("failed to clean-up: ", x);
/*      */         } 
/*      */       } 
/*      */       try {
/*  139 */         String sql = DBMS.getSQL(dblink, "SELECT REPLACE_ID, ATTRIBUTE FROM REPLACE_ATTRIBUTES");
/*  140 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*  141 */         rs = stmt.executeQuery();
/*  142 */         while (rs.next()) {
/*  143 */           Integer id = Integer.valueOf(rs.getInt(1));
/*  144 */           String attribute = rs.getString(2).trim();
/*  145 */           if (this.replacers == null) {
/*  146 */             this.replacers = new HashMap<Object, Object>();
/*      */           }
/*  148 */           List<String> attributes = (List)this.replacers.get(id);
/*  149 */           if (attributes == null) {
/*  150 */             attributes = new ArrayList();
/*  151 */             this.replacers.put(id, attributes);
/*      */           } 
/*  153 */           attributes.add(attribute);
/*      */         } 
/*  155 */       } catch (Exception e) {
/*  156 */         SPSControllerVCI.log.warn("unable to load, ignoring - exception: " + e, e);
/*      */       } finally {
/*      */         try {
/*  159 */           if (rs != null) {
/*  160 */             rs.close();
/*      */           }
/*  162 */           if (stmt != null) {
/*  163 */             stmt.close();
/*      */           }
/*  165 */           if (conn != null) {
/*  166 */             dblink.releaseConnection(conn);
/*      */           }
/*  168 */         } catch (Exception x) {
/*  169 */           SPSControllerVCI.log.error("failed to clean-up: ", x);
/*      */         } 
/*      */       } 
/*  172 */       COPQualification.init(dblink);
/*      */     }
/*      */     
/*      */     public static StaticData getInstance(SPSSchemaAdapterGlobal adapter) {
/*  176 */       synchronized (adapter.getSyncObject()) {
/*  177 */         StaticData instance = (StaticData)adapter.getObject(StaticData.class);
/*  178 */         if (instance == null) {
/*  179 */           instance = new StaticData(adapter);
/*  180 */           adapter.storeObject(StaticData.class, instance);
/*      */         } 
/*  182 */         return instance;
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public static final class StaticData2
/*      */   {
/*      */     private IDatabaseLink quickUpdateDB;
/*      */     
/*      */     public IDatabaseLink getQuickUpdateDB() {
/*  192 */       return this.quickUpdateDB;
/*      */     }
/*      */     
/*      */     public void setQuickUpdateDB(IDatabaseLink dblink) {
/*  196 */       this.quickUpdateDB = dblink;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void init() {}
/*      */ 
/*      */     
/*      */     private StaticData2(SPSSchemaAdapterGlobal adapter) {}
/*      */ 
/*      */     
/*      */     public static StaticData2 getInstance(SPSSchemaAdapterGlobal adapter) {
/*  208 */       synchronized (adapter.getSyncObject()) {
/*  209 */         StaticData2 instance = (StaticData2)adapter.getObject(StaticData2.class);
/*  210 */         if (instance == null) {
/*  211 */           instance = new StaticData2(adapter);
/*  212 */           adapter.storeObject(StaticData2.class, instance);
/*      */         } 
/*  214 */         return instance;
/*      */       } 
/*      */     }
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
/*      */   SPSControllerVCI(SPSSession session, SPSControllerData data, SPSSchemaAdapterGlobal adapter) throws Exception {
/*  279 */     this.session = session;
/*  280 */     this.adapter = adapter;
/*  281 */     this.id = data.getControllerID();
/*  282 */     this.isCustomCalibrationController = StaticData.getInstance(adapter).isCustomCalibrationController(this.id);
/*  283 */     this.descIndex = data.getDescriptionIndex();
/*  284 */     this.description = getDescription((SPSLanguage)session.getLanguage(), this.id, this.descIndex, adapter);
/*  285 */     if (this.description == null) {
/*  286 */       log.error("failed to look-up controller description (controller=" + this.id + ", description index=" + this.descIndex + ", locale=" + session.getLanguage().getLocale() + ")");
/*  287 */       this.description = getDescription((SPSLanguage)session.getLanguage(), this.id, this.descIndex, adapter);
/*  288 */       if (this.description == null) {
/*  289 */         this.description = "controller label missing (controller-ID=" + this.id + ", description index=" + this.descIndex + ")";
/*      */       }
/*      */     } 
/*  292 */     String j2534 = data.getJ2534().trim();
/*  293 */     if (j2534.equalsIgnoreCase("+J25")) {
/*  294 */       this.j2534Dependency = Boolean.TRUE;
/*  295 */     } else if (j2534.equalsIgnoreCase("-J25")) {
/*  296 */       this.j2534Dependency = Boolean.FALSE;
/*      */     } 
/*  298 */     this.preRPOCode = data.getPreRPOCode().trim();
/*  299 */     this.postRPOCode = data.getPostRPOCode().trim();
/*  300 */     this.deviceID = data.getDeviceID();
/*  301 */     this.requestMethodID = data.getRequestMethodID();
/*  302 */     this.programmingTypes = SPSProgrammingType.getProgrammingTypes((SPSLanguage)session.getLanguage(), data.getProgrammingMethods(), adapter);
/*  303 */     this.defaultVCI = data.getVCI();
/*  304 */     this.VCI = -1;
/*  305 */     this.preProgrammingInstructions = SPSProgrammingInstructions.tokenize(data.getPreProgrammingInstructions());
/*  306 */     this.postProgrammingInstructions = SPSProgrammingInstructions.tokenize(data.getPostProgrammingInstructions());
/*  307 */     if (adapter.getDatabaseLink().getDBMS() == 3) {
/*  308 */       this.afterMarketFlag = data.getAfterMarketFlag();
/*      */     }
/*      */     try {
/*  311 */       this.replaceInstruction = data.getReplaceInstruction();
/*  312 */       int replacer = data.getReplacer();
/*  313 */       if (replacer > 0) {
/*  314 */         this.replaceAttributes = (List)StaticData.getInstance(adapter).getReplacers().get(Integer.valueOf(replacer));
/*      */       }
/*  316 */     } catch (Exception x) {}
/*      */     
/*  318 */     if (data.getSuppressSequenceControllerFlag() != null && "Y".equalsIgnoreCase(data.getSuppressSequenceControllerFlag())) {
/*  319 */       this.suppressSequenceControllerFlag = true;
/*      */     }
/*  321 */     this.securityCodeRequired = data.isSecurityCodeRequired();
/*  322 */     this.optionListID = data.getOptionListID();
/*      */   }
/*      */   
/*      */   public boolean isSecurityCodeRequired() {
/*  326 */     return this.securityCodeRequired;
/*      */   }
/*      */   
/*      */   public boolean getSuppressSequenceControllerFlag() {
/*  330 */     return this.suppressSequenceControllerFlag;
/*      */   }
/*      */   
/*      */   public boolean isXMLProgramming() {
/*  334 */     Integer ptypeXML = (Integer)StaticData.getInstance(this.adapter).getProgrammingTypes().get(Integer.valueOf(this.id));
/*  335 */     return XMLProgrammingTypes.PROGRAMMING.equals(ptypeXML);
/*      */   }
/*      */   
/*      */   public boolean isXMLConfiguration() {
/*  339 */     Integer ptypeXML = (Integer)StaticData.getInstance(this.adapter).getProgrammingTypes().get(Integer.valueOf(this.id));
/*  340 */     return XMLProgrammingTypes.CONFIGURATION.equals(ptypeXML);
/*      */   }
/*      */   
/*      */   public boolean isType4() {
/*  344 */     Integer ptypeXML = (Integer)StaticData.getInstance(this.adapter).getProgrammingTypes().get(Integer.valueOf(this.id));
/*  345 */     return XMLProgrammingTypes.TYPE4.equals(ptypeXML);
/*      */   }
/*      */   
/*      */   public boolean requiresCustomCalibration() {
/*  349 */     return this.isCustomCalibrationController;
/*      */   }
/*      */   
/*      */   public Boolean hasJ2534Dependency() {
/*  353 */     return this.j2534Dependency;
/*      */   }
/*      */   
/*      */   public void setVCI(int vci) {
/*  357 */     this.VCI = vci;
/*      */   }
/*      */   
/*      */   public void setArchive(Archive archive) {
/*  361 */     if (this.archive != null && this.archive.getChangeReason().equals(archive.getChangeReason())) {
/*      */       return;
/*      */     }
/*  364 */     this.archive = archive;
/*      */   }
/*      */   
/*      */   public void setPartFile(PartFile file) {
/*  368 */     this.file = file;
/*      */   }
/*      */   
/*      */   public void setHardware(SPSPart part) {
/*  372 */     if (part == null) {
/*  373 */       this.hardware = null;
/*      */       return;
/*      */     } 
/*  376 */     if (this.hardware == null) {
/*  377 */       this.hardware = new ArrayList();
/*      */     }
/*  379 */     this.hardware.add(part);
/*      */   }
/*      */   
/*      */   public List getHardware() {
/*  383 */     return this.hardware;
/*      */   }
/*      */   
/*      */   public Archive getArchive() {
/*  387 */     return this.archive;
/*      */   }
/*      */   
/*      */   public void setControllerData(Object data) {
/*  391 */     this.data = data;
/*      */   }
/*      */   
/*      */   public Object getControllerData() {
/*  395 */     return this.data;
/*      */   }
/*      */   
/*      */   public int getID() {
/*  399 */     return this.id;
/*      */   }
/*      */   
/*      */   public int getVCI() {
/*  403 */     return this.actualVCI;
/*      */   }
/*      */   
/*      */   public int getControllerVCI() {
/*  407 */     return this.defaultVCI;
/*      */   }
/*      */   
/*      */   public String getAfterMarketFlag() {
/*  411 */     return this.afterMarketFlag;
/*      */   }
/*      */   
/*      */   public String getDescription() {
/*  415 */     return this.description;
/*      */   }
/*      */   
/*      */   public String getLabel() {
/*  419 */     return getLabel(this.id, this.descIndex, this.adapter);
/*      */   }
/*      */   
/*      */   public int getDescIndex() {
/*  423 */     return this.descIndex;
/*      */   }
/*      */   
/*      */   public int getDeviceID() {
/*  427 */     return this.deviceID;
/*      */   }
/*      */   
/*      */   public int getRequestMethodID() {
/*  431 */     return this.requestMethodID;
/*      */   }
/*      */   
/*      */   public List getProgrammingTypes() {
/*  435 */     return this.programmingTypes;
/*      */   }
/*      */   
/*      */   public List getPreProgrammingInstructions() {
/*  439 */     return this.preProgrammingInstructions;
/*      */   }
/*      */   
/*      */   public List getPostProgrammingInstructions() {
/*  443 */     return this.postProgrammingInstructions;
/*      */   }
/*      */   
/*      */   public int getReplaceInstruction() {
/*  447 */     return this.replaceInstruction;
/*      */   }
/*      */   
/*      */   public List getReplaceAttributes() {
/*  451 */     return this.replaceAttributes;
/*      */   }
/*      */   
/*      */   public List getPreSelectionOptions() throws Exception {
/*  455 */     return null;
/*      */   }
/*      */   
/*      */   public List getPostSelectionOptions() throws Exception {
/*  459 */     return getOptionList();
/*      */   }
/*      */   
/*      */   public SPSProgrammingData getProgrammingData(SPSSchemaAdapter adapter) throws Exception {
/*  463 */     return constructProgrammingData((SPSVehicle)this.session.getVehicle(), (SPSSchemaAdapterGlobal)adapter);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void update(SPSSchemaAdapter adapter) throws Exception {
/*  469 */     SPSProgrammingData data = (SPSProgrammingData)this.session.getProgrammingData(adapter);
/*  470 */     data.update(this.hardware, (SPSSchemaAdapterGlobal)adapter);
/*      */   }
/*      */   
/*      */   protected SPSProgrammingData handleArchiveProgrammingData(SPSProgrammingData data, SPSSchemaAdapterGlobal adapter) throws Exception {
/*  474 */     data.setArchive((SPSLanguage)this.session.getLanguage(), this.archive, adapter);
/*      */     
/*  476 */     SPSCOP.checkBulletin(data.getModules().get(0), adapter);
/*  477 */     SPSVehicle vehicle = (SPSVehicle)this.session.getVehicle();
/*  478 */     checkCurrentCalibrationInformation(data.getModules(), vehicle.getVIT1(), adapter);
/*  479 */     return data;
/*      */   }
/*      */   
/*      */   protected SPSProgrammingData handlePartFileProgrammingData(SPSProgrammingData data, SPSSchemaAdapterGlobal adapter) throws Exception {
/*  483 */     data.setPartFile((SPSLanguage)this.session.getLanguage(), this.file, adapter);
/*  484 */     data.build((SPSLanguage)this.session.getLanguage(), this.hardware, adapter);
/*  485 */     if (this.hardware != null && this.hardware.size() == 0) {
/*  486 */       this.hardware = null;
/*      */     }
/*  488 */     SPSVehicle vehicle = (SPSVehicle)this.session.getVehicle();
/*  489 */     checkCurrentCalibrationInformation(data.getModules(), vehicle.getVIT1(), adapter);
/*  490 */     return data;
/*      */   }
/*      */   
/*      */   protected void checkCurrentCalibrationInformation(List modules, VIT1Data vit1, SPSSchemaAdapterGlobal adapter) {
/*  494 */     if (vit1 != null) {
/*  495 */       List parts = vit1.getParts();
/*  496 */       if (parts == null || parts.size() == 0) {
/*  497 */         parts = vit1.getParts(this.deviceID);
/*      */       }
/*  499 */       if (parts != null && parts.size() > 0) {
/*  500 */         boolean checkCVN = vit1.getReadCVN();
/*  501 */         List partnums = checkCVN ? vit1.getPartNumbers(this.deviceID) : null;
/*  502 */         if (checkCVN && parts.size() == 1 && modules.size() == 1) {
/*  503 */           partnums = vit1.getSubAssembly(this.deviceID);
/*      */         }
/*  505 */         addCurrentCalibrationInformation(modules, parts, partnums, checkCVN, adapter);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected SPSProgrammingData constructProgrammingData(SPSVehicle vehicle, SPSSchemaAdapterGlobal adapter) throws Exception {
/*  512 */     SPSProgrammingData data = new SPSProgrammingData();
/*  513 */     data.setDeviceID(Integer.valueOf(this.deviceID));
/*  514 */     data.setVIN(vehicle.getVIN().toString());
/*  515 */     if (this.archive != null) {
/*  516 */       return handleArchiveProgrammingData(data, adapter);
/*      */     }
/*  518 */     if (this.file != null) {
/*  519 */       return handlePartFileProgrammingData(data, adapter);
/*      */     }
/*  521 */     this.actualVCI = this.defaultVCI;
/*  522 */     if (this.VCI >= 0) {
/*  523 */       this.actualVCI = this.VCI;
/*      */     }
/*  525 */     if (this.actualVCI == 0) {
/*  526 */       this.actualVCI = resolveVCI(vehicle.getVIN(), adapter);
/*  527 */       if (this.actualVCI == 0) {
/*  528 */         return null;
/*      */       }
/*      */     } 
/*  531 */     resolveVCI(data, Integer.toString(this.actualVCI), adapter);
/*      */     
/*  533 */     if (data.getModules() == null || data.getModules().isEmpty()) {
/*  534 */       return null;
/*      */     }
/*  536 */     Set usageCOP = COPQualification.qualify(adapter, vehicle, this.preRPOCode, this.postRPOCode);
/*  537 */     data.build((SPSLanguage)this.session.getLanguage(), this.hardware, usageCOP, adapter);
/*  538 */     if (this.hardware != null && this.hardware.size() == 0) {
/*  539 */       this.hardware = null;
/*      */     }
/*  541 */     checkCurrentCalibrationInformation(data.getModules(), vehicle.getVIT1(), adapter);
/*  542 */     return data;
/*      */   }
/*      */   
/*      */   protected void addCurrentCalibrationInformation(List<SPSModule> modules, List<String> parts, List partnums, boolean checkCVN, SPSSchemaAdapterGlobal adapter) {
/*  546 */     if ("replace".equalsIgnoreCase(this.session.getVehicle().getVIT1().getSPSMode()));
/*      */ 
/*      */     
/*  549 */     if (parts != null && parts.size() == 0) {
/*  550 */       parts = null;
/*      */     }
/*  552 */     for (int i = 0; i < modules.size(); i++) {
/*  553 */       SPSModule module = modules.get(i);
/*  554 */       if (i == 0 && modules.size() > 1) {
/*      */ 
/*      */         
/*  557 */         module.setCurrentCalibration((SPSLanguage)this.session.getLanguage(), null, adapter);
/*  558 */       } else if (parts != null && parts.size() == 1 && modules.size() == 1) {
/*  559 */         module.setCurrentCalibration((SPSLanguage)this.session.getLanguage(), parts.get(0), checkCVN, true, partnums, adapter);
/*  560 */       } else if (parts != null && parts.size() > 1 && modules.size() == 1) {
/*      */ 
/*      */         
/*  563 */         module.setCurrentCalibration(null, null, adapter);
/*      */       }
/*  565 */       else if (parts != null && parts.size() >= i) {
/*  566 */         String part = null;
/*  567 */         for (int j = 0; j < parts.size(); j++) {
/*  568 */           String p = parts.get(j);
/*  569 */           if (module.isCurrentCalibration(p)) {
/*  570 */             part = p;
/*      */             break;
/*      */           } 
/*      */         } 
/*  574 */         if (part != null) {
/*  575 */           module.setCurrentCalibration((SPSLanguage)this.session.getLanguage(), part, checkCVN, false, partnums, adapter);
/*      */         } else {
/*  577 */           module.setCurrentCalibration((SPSLanguage)this.session.getLanguage(), parts.get(i - 1), adapter);
/*      */         } 
/*      */       } else {
/*  580 */         module.setCurrentCalibration((SPSLanguage)this.session.getLanguage(), null, adapter);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void resolveVCI(SPSProgrammingData data, String vci, SPSSchemaAdapterGlobal adapter) throws Exception {
/*  586 */     if (!resolveVCI_Quick_Update(StaticData2.getInstance(adapter).getQuickUpdateDB(), data, vci, adapter)) {
/*  587 */       resolveVCI(adapter.getDatabaseLink(), data, vci, adapter);
/*      */     }
/*      */   }
/*      */   
/*      */   protected boolean resolveVCI_Quick_Update(IDatabaseLink db, SPSProgrammingData data, String vci, SPSSchemaAdapterGlobal adapter) throws Exception {
/*  592 */     if (db == null) {
/*  593 */       return false;
/*      */     }
/*  595 */     Connection conn = null;
/*  596 */     DBMS.PreparedStatement stmt = null;
/*  597 */     ResultSet rs = null;
/*      */     try {
/*  599 */       conn = db.requestConnection();
/*  600 */       String sql = DBMS.getSQL(db, "Select Origin_Part_No_String, Module_Id_String, Date_Time From VCI_Origin_Members_String Where VCI = ?");
/*  601 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/*  602 */       stmt.setInt(1, Integer.parseInt(vci));
/*  603 */       rs = stmt.executeQuery();
/*  604 */       if (rs.next()) {
/*  605 */         String parts = rs.getString(1);
/*  606 */         String modules = rs.getString(2);
/*  607 */         Timestamp TS = rs.getTimestamp(3);
/*  608 */         long timestamp = TS.getTime();
/*      */ 
/*      */         
/*  611 */         boolean validtimestamp = isValidtimestamp(timestamp);
/*  612 */         if (!validtimestamp) {
/*  613 */           return false;
/*      */         }
/*  615 */         StringTokenizer ptokenizer = new StringTokenizer(parts, ", ");
/*  616 */         StringTokenizer mtokenizer = new StringTokenizer(modules, ", ");
/*  617 */         while (ptokenizer.hasMoreTokens()) {
/*  618 */           String part = ptokenizer.nextToken();
/*  619 */           String module = mtokenizer.nextToken();
/*  620 */           data.add((SPSLanguage)this.session.getLanguage(), part, module, adapter);
/*      */         } 
/*  622 */         return true;
/*      */       } 
/*  624 */     } catch (Exception e) {
/*  625 */       throw e;
/*      */     } finally {
/*      */       try {
/*  628 */         if (rs != null) {
/*  629 */           rs.close();
/*      */         }
/*  631 */         if (stmt != null) {
/*  632 */           stmt.close();
/*      */         }
/*  634 */         if (conn != null) {
/*  635 */           db.releaseConnection(conn);
/*      */         }
/*  637 */       } catch (Exception x) {}
/*      */     } 
/*      */     
/*  640 */     return false;
/*      */   }
/*      */   
/*      */   protected boolean resolveVCI(IDatabaseLink db, SPSProgrammingData data, String vci, SPSSchemaAdapterGlobal adapter) throws Exception {
/*  644 */     if (db == null) {
/*  645 */       return false;
/*      */     }
/*  647 */     Connection conn = null;
/*  648 */     DBMS.PreparedStatement stmt = null;
/*  649 */     ResultSet rs = null;
/*      */     try {
/*  651 */       conn = db.requestConnection();
/*  652 */       String sql = DBMS.getSQL(db, "Select Origin_Part_No_String, Module_Id_String From VCI_Origin_Members_String Where VCI = ?");
/*  653 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/*  654 */       stmt.setInt(1, Integer.parseInt(vci));
/*  655 */       rs = stmt.executeQuery();
/*  656 */       if (rs.next()) {
/*  657 */         String parts = rs.getString(1);
/*  658 */         String modules = rs.getString(2);
/*  659 */         StringTokenizer ptokenizer = new StringTokenizer(parts, ", ");
/*  660 */         StringTokenizer mtokenizer = new StringTokenizer(modules, ", ");
/*  661 */         while (ptokenizer.hasMoreTokens()) {
/*  662 */           String part = ptokenizer.nextToken();
/*  663 */           String module = mtokenizer.nextToken();
/*  664 */           data.add((SPSLanguage)this.session.getLanguage(), part, module, adapter);
/*      */         } 
/*  666 */         return true;
/*      */       } 
/*  668 */     } catch (Exception e) {
/*  669 */       throw e;
/*      */     } finally {
/*      */       try {
/*  672 */         if (rs != null) {
/*  673 */           rs.close();
/*      */         }
/*  675 */         if (stmt != null) {
/*  676 */           stmt.close();
/*      */         }
/*  678 */         if (conn != null) {
/*  679 */           db.releaseConnection(conn);
/*      */         }
/*  681 */       } catch (Exception x) {}
/*      */     } 
/*      */     
/*  684 */     return false;
/*      */   }
/*      */   
/*      */   protected int resolveVCI(SPSVIN vin, SPSSchemaAdapterGlobal adapter) throws Exception {
/*  688 */     int vci = resolveVCI_quickUpdate(StaticData2.getInstance(adapter).getQuickUpdateDB(), vin);
/*  689 */     if (vci == 0) {
/*  690 */       vci = resolveVCI(adapter.getDatabaseLink(), vin);
/*      */     }
/*  692 */     return vci;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int resolveVCI_quickUpdate(IDatabaseLink db, SPSVIN vin) throws Exception {
/*  711 */     if (db == null) {
/*  712 */       return 0;
/*      */     }
/*  714 */     Connection conn = null;
/*  715 */     DBMS.PreparedStatement stmt = null;
/*  716 */     ResultSet rs = null;
/*      */     try {
/*  718 */       conn = db.requestConnection();
/*  719 */       String sql = DBMS.getSQL(db, "Select VIN_Id, Date_Time from VCI_AsBuilt_Id where VIN_1To8 = ? and VIN_10 = ? and VIN_11 = ?");
/*  720 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/*  721 */       stmt.setString(1, vin.toString().substring(0, 8));
/*  722 */       stmt.setString(2, DBMS.toString(vin.getPosition(10)));
/*  723 */       stmt.setString(3, DBMS.toString(vin.getPosition(11)));
/*  724 */       rs = stmt.executeQuery();
/*  725 */       if (rs.next()) {
/*  726 */         int vinid = rs.getInt(1);
/*  727 */         Timestamp TS = rs.getTimestamp(2);
/*  728 */         long timestamp = TS.getTime();
/*      */         
/*  730 */         boolean validtimestamp = isValidtimestamp(timestamp);
/*  731 */         if (vinid != 0 && validtimestamp == true) {
/*  732 */           rs.close();
/*  733 */           rs = null;
/*  734 */           stmt.close();
/*  735 */           stmt = null;
/*  736 */           return resolveVCI(conn, vinid, vin.getSequence(), db);
/*      */         } 
/*      */       } 
/*  739 */     } catch (Exception e) {
/*  740 */       throw e;
/*      */     } finally {
/*      */       try {
/*  743 */         if (rs != null) {
/*  744 */           rs.close();
/*      */         }
/*  746 */         if (stmt != null) {
/*  747 */           stmt.close();
/*      */         }
/*  749 */         if (conn != null) {
/*  750 */           db.releaseConnection(conn);
/*      */         }
/*  752 */       } catch (Exception x) {}
/*      */     } 
/*      */     
/*  755 */     return 0;
/*      */   }
/*      */   
/*      */   private boolean isValidtimestamp(long timestamp) {
/*  759 */     boolean valid = false;
/*  760 */     Long DBVERSION_TS = null;
/*  761 */     long dbversion_ts = -1L;
/*  762 */     DBVERSION_TS = this.adapter.getNAO_DB_VERSION_TIMESTAMP();
/*  763 */     dbversion_ts = DBVERSION_TS.longValue();
/*      */ 
/*      */     
/*  766 */     if (timestamp > dbversion_ts) {
/*  767 */       valid = true;
/*      */     }
/*  769 */     return valid;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int resolveVCI(IDatabaseLink db, SPSVIN vin) throws Exception {
/*  788 */     if (db == null) {
/*  789 */       return 0;
/*      */     }
/*  791 */     Connection conn = null;
/*  792 */     DBMS.PreparedStatement stmt = null;
/*  793 */     ResultSet rs = null;
/*      */     try {
/*  795 */       conn = db.requestConnection();
/*  796 */       String sql = DBMS.getSQL(db, "Select VIN_Id from VCI_AsBuilt_Id where VIN_1To8 = ? and VIN_10 = ? and VIN_11 = ?");
/*  797 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/*  798 */       stmt.setString(1, vin.toString().substring(0, 8));
/*  799 */       stmt.setString(2, DBMS.toString(vin.getPosition(10)));
/*  800 */       stmt.setString(3, DBMS.toString(vin.getPosition(11)));
/*  801 */       rs = stmt.executeQuery();
/*  802 */       if (rs.next()) {
/*  803 */         int vinid = rs.getInt(1);
/*  804 */         if (vinid != 0) {
/*  805 */           rs.close();
/*  806 */           rs = null;
/*  807 */           stmt.close();
/*  808 */           stmt = null;
/*  809 */           return resolveVCI(conn, vinid, vin.getSequence(), db);
/*      */         } 
/*      */       } 
/*  812 */     } catch (Exception e) {
/*  813 */       throw e;
/*      */     } finally {
/*      */       try {
/*  816 */         if (rs != null) {
/*  817 */           rs.close();
/*      */         }
/*  819 */         if (stmt != null) {
/*  820 */           stmt.close();
/*      */         }
/*  822 */         if (conn != null) {
/*  823 */           db.releaseConnection(conn);
/*      */         }
/*  825 */       } catch (Exception x) {}
/*      */     } 
/*      */     
/*  828 */     return 0;
/*      */   }
/*      */   
/*      */   protected int resolveVCI(Connection conn, int vinid, String sequence, IDatabaseLink dblink) throws Exception {
/*  832 */     DBMS.PreparedStatement stmt = null;
/*  833 */     ResultSet rs = null;
/*      */     try {
/*  835 */       String target = Integer.toString(this.id);
/*  836 */       String sql = DBMS.getSQL(dblink, "Select VCI_String, Controller_Id_String from VCI_AsBuilt_String where VIN_12To17 = ? and VIN_Id = ?");
/*  837 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/*  838 */       stmt.setString(1, sequence);
/*  839 */       stmt.setInt(2, vinid);
/*  840 */       rs = stmt.executeQuery();
/*  841 */       while (rs.next()) {
/*  842 */         String vcilist = rs.getString(1);
/*  843 */         String controllers = rs.getString(2);
/*  844 */         if (controllers.indexOf(target) >= 0) {
/*  845 */           return resolveVCI(target, controllers, vcilist);
/*      */         }
/*      */       } 
/*  848 */     } catch (Exception e) {
/*  849 */       throw e;
/*      */     } finally {
/*      */       try {
/*  852 */         if (rs != null) {
/*  853 */           rs.close();
/*      */         }
/*  855 */         if (stmt != null) {
/*  856 */           stmt.close();
/*      */         }
/*  858 */       } catch (Exception x) {}
/*      */     } 
/*      */     
/*  861 */     return 0;
/*      */   }
/*      */   
/*      */   protected int resolveVCI(String target, String controllers, String vcilist) {
/*  865 */     StringTokenizer ctokenizer = new StringTokenizer(controllers, ", ");
/*  866 */     StringTokenizer vtokenizer = new StringTokenizer(vcilist, ", ");
/*  867 */     while (ctokenizer.hasMoreTokens()) {
/*  868 */       String controller = ctokenizer.nextToken();
/*  869 */       String vci = vtokenizer.nextToken();
/*  870 */       if (target.equals(controller)) {
/*  871 */         return Integer.parseInt(vci);
/*      */       }
/*      */     } 
/*  874 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   protected List getOptionList() throws Exception {
/*  879 */     SPSOptionList options = SPSOptionList.getOptionList(this.adapter, Integer.valueOf(this.optionListID));
/*      */     
/*  881 */     return options;
/*      */   }
/*      */ 
/*      */   
/*      */   protected List buildOption(String rpo, SPSSchemaAdapterGlobal adapter) throws Exception {
/*  886 */     List<? extends SPSOption> obOptions = null;
/*  887 */     if (!rpo.equals("~")) {
/*  888 */       List<SPSOption> options = new ArrayList();
/*  889 */       SPSOption option = SPSOption.getRPO((SPSLanguage)this.session.getLanguage(), rpo, adapter);
/*      */       
/*  891 */       if (option == null) {
/*  892 */         throw new Exception("unknown RPO code '" + rpo + "'.");
/*      */       }
/*  894 */       options.add(option);
/*  895 */       SPSOption group = ((SPSVehicle)this.session.getVehicle()).getOptionGroup(this.session, rpo, adapter);
/*  896 */       if (group != null) {
/*  897 */         group = handleMandatoryOptions(options, group, (SPSVehicle)this.session.getVehicle(), adapter);
/*  898 */         option.setType(group);
/*  899 */       } else if (option.getType() != null) {
/*  900 */         option.setType(SPSOption.getOptionGroup((SPSLanguage)this.session.getLanguage(), option.getType().getID(), adapter));
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  905 */       if (obOptions != null) {
/*  906 */         options.addAll(obOptions);
/*      */       }
/*  908 */       return options;
/*  909 */     }  if (rpo == this.preRPOCode) {
/*  910 */       List options = new ArrayList();
/*      */       
/*  912 */       handleMandatoryOptions(options, null, (SPSVehicle)this.session.getVehicle(), adapter);
/*  913 */       return (options.size() == 0) ? null : options;
/*      */     } 
/*  915 */     return (obOptions != null) ? obOptions : null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected SPSOption handleMandatoryOptions(List<SPSOption> options, SPSOption group, SPSVehicle vehicle, SPSSchemaAdapterGlobal adapter) throws Exception {
/*  921 */     List<Pair> mandatoryOptions = vehicle.getMandatoryOptions();
/*  922 */     if (mandatoryOptions != null) {
/*  923 */       String preOptionGroupID = (group != null) ? group.getID() : null;
/*  924 */       for (int i = 0; i < mandatoryOptions.size(); i++) {
/*  925 */         Pair pair = mandatoryOptions.get(i);
/*  926 */         String groupID = (String)pair.getFirst();
/*  927 */         if (preOptionGroupID == null || !preOptionGroupID.equals(groupID)) {
/*  928 */           String rpo = (String)pair.getSecond();
/*  929 */           SPSOption option = SPSOption.getRPO((SPSLanguage)this.session.getLanguage(), rpo, adapter);
/*  930 */           if (option == null) {
/*  931 */             throw new Exception("unknown RPO code '" + rpo + "'.");
/*      */           }
/*  933 */           SPSOption ogroup = SPSOption.getOptionGroup((SPSLanguage)this.session.getLanguage(), Integer.parseInt(groupID), adapter);
/*  934 */           option.setType(ogroup);
/*  935 */           options.add(option);
/*  936 */         } else if (preOptionGroupID != null && preOptionGroupID.equals(groupID)) {
/*      */ 
/*      */           
/*  939 */           group = SPSOption.getOptionGroup((SPSLanguage)this.session.getLanguage(), Integer.parseInt(groupID), adapter);
/*      */         } 
/*      */       } 
/*      */     } 
/*  943 */     return group;
/*      */   }
/*      */   
/*      */   public int hashCode() {
/*  947 */     return this.id;
/*      */   }
/*      */   
/*      */   @SuppressWarnings({"EQ_CHECK_FOR_OPERAND_NOT_COMPATIBLE_WITH_THIS"})
/*      */   public boolean equals(Object object) {
/*  952 */     if (object == null)
/*  953 */       return false; 
/*  954 */     if (object instanceof SPSControllerVCI && ((SPSControllerVCI)object).getID() == this.id)
/*  955 */       return true; 
/*  956 */     if (object instanceof SPSControllerReference) {
/*  957 */       return ((SPSControllerReference)object).accept(this);
/*      */     }
/*  959 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/*  964 */     return "id = " + this.id + " " + getDescription() + " (options=" + this.preRPOCode + "/" + this.postRPOCode + ")\r\n" + "[device=" + this.deviceID + ",reqid=" + this.requestMethodID + ",vci=" + this.VCI + ",pre/post=" + SPSProgrammingInstructions.toString(this.preProgrammingInstructions) + "->" + SPSProgrammingInstructions.toString(this.postProgrammingInstructions) + "]";
/*      */   }
/*      */   
/*      */   protected static String getDescription(SPSLanguage language, int id, int descIndex, SPSSchemaAdapterGlobal adapter) {
/*  968 */     String key = Integer.valueOf(id).toString() + ":" + Integer.valueOf(descIndex).toString();
/*  969 */     Map descriptions = StaticData.getInstance(adapter).getDescriptions();
/*  970 */     SPSDescription description = (descriptions == null) ? null : (SPSDescription)descriptions.get(key);
/*  971 */     if (description != null) {
/*  972 */       String label = description.get(language);
/*  973 */       if (label == null && language.getLocale().compareTo("en_GB") != 0)
/*      */       {
/*  975 */         label = getDescription(SPSLanguage.getLanguage("en_GB", adapter), id, descIndex, adapter);
/*      */       }
/*  977 */       return label;
/*      */     } 
/*  979 */     return null;
/*      */   }
/*      */   
/*      */   protected static String getLabel(int id, int descIndex, SPSSchemaAdapterGlobal adapter) {
/*  983 */     String key = Integer.valueOf(id).toString() + ":" + Integer.valueOf(descIndex).toString();
/*  984 */     Map descriptions = StaticData.getInstance(adapter).getDescriptions();
/*  985 */     SPSDescription description = (descriptions == null) ? null : (SPSDescription)descriptions.get(key);
/*  986 */     if (description != null) {
/*  987 */       return description.getDefaultLabel();
/*      */     }
/*  989 */     return null;
/*      */   }
/*      */   
/*      */   static void init(SPSSchemaAdapterGlobal adapter) throws Exception {
/*  993 */     StaticData.getInstance(adapter).init();
/*      */   }
/*      */ 
/*      */   
/*      */   static void initCustomCalibrationControllers(SPSSchemaAdapterGlobal adapter, String controllers) {
/*      */     try {
/*  999 */       List<Integer> controllerList = new ArrayList();
/* 1000 */       controllers = controllers.trim();
/* 1001 */       StringTokenizer st = new StringTokenizer(controllers, ", ");
/* 1002 */       while (st.hasMoreTokens()) {
/* 1003 */         controllerList.add(Integer.valueOf(st.nextToken()));
/*      */       }
/* 1005 */       StaticData.getInstance(adapter).initCustomCalibrationControllers(controllerList);
/* 1006 */     } catch (Exception e) {
/* 1007 */       log.error("failed to parse custom calibrations controller list: ", e);
/*      */     } 
/*      */   }
/*      */   
/*      */   static void initQuickUpdate(SPSSchemaAdapterGlobal adapter, IDatabaseLink db) throws Exception {
/* 1012 */     StaticData2.getInstance(adapter).setQuickUpdateDB(db);
/*      */   }
/*      */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\global\SPSControllerVCI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */