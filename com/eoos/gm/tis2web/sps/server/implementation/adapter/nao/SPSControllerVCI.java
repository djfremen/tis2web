/*      */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.nao;
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
/*      */ import org.apache.log4j.Logger;
/*      */ 
/*      */ public class SPSControllerVCI implements SPSController {
/*      */   private static final long serialVersionUID = 1L;
/*      */   static final int USE_DEFAULT_VCI = -1;
/*      */   protected transient SPSSession session;
/*      */   protected transient List hardware;
/*      */   protected transient Archive archive;
/*      */   protected transient PartFile file;
/*      */   protected transient SPSSaturnData saturn;
/*      */   protected int id;
/*   34 */   private static Logger log = Logger.getLogger(SPSControllerVCI.class); protected int descIndex; protected String description; protected Boolean j2534Dependency; protected String preRPOCode; protected String postRPOCode; protected int deviceID; protected int requestMethodID;
/*      */   
/*      */   public static boolean isXMLController(SPSSchemaAdapterNAO adapter, int controllerID, int descriptionIndex) {
/*   37 */     return StaticData.getInstance(adapter).isXMLControllerConfiguration(controllerID, descriptionIndex);
/*      */   }
/*      */   protected List programmingTypes; protected transient SPSProgrammingData pdata; protected int defaultVCI; protected int actualVCI; protected int VCI; protected List preProgrammingInstructions; protected List postProgrammingInstructions; protected int replaceInstruction; protected List replaceAttributes; protected String afterMarketFlag;
/*      */   protected Object data;
/*      */   protected boolean isCustomCalibrationController;
/*      */   protected boolean suppressSequenceControllerFlag;
/*      */   protected boolean securityCodeRequired;
/*      */   protected transient SPSSchemaAdapterNAO adapter;
/*      */   
/*      */   public static final class StaticData { private Map descriptions;
/*      */     private Map programmingTypes;
/*      */     
/*      */     public Map getDescriptions() {
/*   50 */       return this.descriptions;
/*      */     }
/*      */     private Map replacers; private List customCalibrationControllerList;
/*      */     public Map getProgrammingTypes() {
/*   54 */       return this.programmingTypes;
/*      */     }
/*      */     
/*      */     public Map getReplacers() {
/*   58 */       return this.replacers;
/*      */     }
/*      */ 
/*      */     
/*      */     public void init() {}
/*      */     
/*      */     public void initCustomCalibrationControllers(List controllers) {
/*   65 */       this.customCalibrationControllerList = controllers;
/*      */     }
/*      */     
/*      */     public boolean isCustomCalibrationController(int controllerID) {
/*   69 */       if (this.customCalibrationControllerList == null) {
/*   70 */         return false;
/*      */       }
/*   72 */       for (int i = 0; i < this.customCalibrationControllerList.size(); i++) {
/*   73 */         if (controllerID == ((Integer)this.customCalibrationControllerList.get(i)).intValue()) {
/*   74 */           return true;
/*      */         }
/*      */       } 
/*   77 */       return false;
/*      */     }
/*      */     
/*      */     public boolean isXMLControllerConfiguration(int controllerID, int descriptionIndex) {
/*   81 */       Integer progtype = (Integer)this.programmingTypes.get(controllerID + ":" + descriptionIndex);
/*   82 */       return XMLProgrammingTypes.CONFIGURATION.equals(progtype);
/*      */     }
/*      */     
/*      */     private StaticData(SPSSchemaAdapterNAO adapter) {
/*   86 */       IDatabaseLink dblink = adapter.getDatabaseLink();
/*   87 */       this.descriptions = new HashMap<Object, Object>();
/*   88 */       this.programmingTypes = new HashMap<Object, Object>();
/*   89 */       Connection conn = null;
/*   90 */       DBMS.PreparedStatement stmt = null;
/*   91 */       ResultSet rs = null;
/*      */       try {
/*   93 */         conn = dblink.requestConnection();
/*   94 */         String sql = DBMS.getSQL(dblink, "SELECT Controller_Id, Language_Code, Controller_Name, Description, Desc_Indx, Prog_Type_ID FROM Controller_Description");
/*      */         try {
/*   96 */           stmt = DBMS.prepareSQLStatement(conn, sql);
/*   97 */           rs = stmt.executeQuery();
/*   98 */         } catch (SQLException x) {
/*   99 */           stmt = DBMS.prepareSQLStatement(conn, DBMS.getSQL(dblink, "SELECT Controller_Id, Language_Code, Controller_Name, Description, Desc_Indx FROM Controller_Description"));
/*  100 */           rs = stmt.executeQuery();
/*      */         } 
/*  102 */         while (rs.next()) {
/*  103 */           Integer id = Integer.valueOf(rs.getInt(1));
/*  104 */           String lg = rs.getString(2);
/*  105 */           Integer descIndex = Integer.valueOf(rs.getInt("Desc_Indx"));
/*      */           
/*  107 */           String key = id.toString() + ":" + descIndex.toString();
/*  108 */           SPSDescription description = (SPSDescription)this.descriptions.get(key);
/*  109 */           if (description == null) {
/*  110 */             description = new SPSDescription();
/*  111 */             this.descriptions.put(key, description);
/*      */           } 
/*  113 */           SPSLanguage locale = SPSLanguage.getLanguage(lg, adapter);
/*  114 */           description.add(locale, rs.getString("Controller_Name").trim() + '\t' + DBMS.getString(dblink, locale, rs, "Description"));
/*      */ 
/*      */           
/*      */           try {
/*  118 */             this.programmingTypes.put(key, Integer.valueOf(rs.getInt("Prog_Type_ID")));
/*  119 */           } catch (SQLException x) {}
/*      */         } 
/*      */         
/*  122 */         SPSControllerVCI.log.info("loaded nao controller descriptions (" + this.descriptions.size() + " controller-ids).");
/*  123 */       } catch (Exception e) {
/*  124 */         throw new RuntimeException(e);
/*      */       } finally {
/*      */         try {
/*  127 */           if (rs != null) {
/*  128 */             rs.close();
/*  129 */             rs = null;
/*      */           } 
/*  131 */           if (stmt != null) {
/*  132 */             stmt.close();
/*  133 */             stmt = null;
/*      */           } 
/*  135 */         } catch (Exception x) {
/*  136 */           SPSControllerVCI.log.error("failed to clean-up: ", x);
/*      */         } 
/*      */       } 
/*      */       try {
/*  140 */         String sql = DBMS.getSQL(dblink, "SELECT REPLACE_ID, ATTRIBUTE FROM REPLACE_ATTRIBUTES");
/*  141 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*  142 */         rs = stmt.executeQuery();
/*  143 */         while (rs.next()) {
/*  144 */           Integer id = Integer.valueOf(rs.getInt(1));
/*  145 */           String attribute = rs.getString(2).trim();
/*  146 */           if (this.replacers == null) {
/*  147 */             this.replacers = new HashMap<Object, Object>();
/*      */           }
/*  149 */           List<String> attributes = (List)this.replacers.get(id);
/*  150 */           if (attributes == null) {
/*  151 */             attributes = new ArrayList();
/*  152 */             this.replacers.put(id, attributes);
/*      */           } 
/*  154 */           attributes.add(attribute);
/*      */         } 
/*  156 */       } catch (Exception e) {
/*      */       
/*      */       } finally {
/*      */         try {
/*  160 */           if (rs != null) {
/*  161 */             rs.close();
/*      */           }
/*  163 */           if (stmt != null) {
/*  164 */             stmt.close();
/*      */           }
/*  166 */           if (conn != null) {
/*  167 */             dblink.releaseConnection(conn);
/*      */           }
/*  169 */         } catch (Exception x) {
/*  170 */           SPSControllerVCI.log.error("failed to clean-up: ", x);
/*      */         } 
/*      */       } 
/*  173 */       COPQualification.init(dblink);
/*      */     }
/*      */     
/*      */     public static StaticData getInstance(SPSSchemaAdapterNAO adapter) {
/*  177 */       synchronized (adapter.getSyncObject()) {
/*  178 */         StaticData instance = (StaticData)adapter.getObject(StaticData.class);
/*  179 */         if (instance == null) {
/*  180 */           instance = new StaticData(adapter);
/*  181 */           adapter.storeObject(StaticData.class, instance);
/*      */         } 
/*  183 */         return instance;
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public static final class StaticData2
/*      */   {
/*      */     private IDatabaseLink quickUpdateDB;
/*      */     
/*      */     public IDatabaseLink getQuickUpdateDB() {
/*  193 */       return this.quickUpdateDB;
/*      */     }
/*      */     
/*      */     public void setQuickUpdateDB(IDatabaseLink dblink) {
/*  197 */       this.quickUpdateDB = dblink;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void init() {}
/*      */ 
/*      */     
/*      */     private StaticData2(SPSSchemaAdapterNAO adapter) {}
/*      */ 
/*      */     
/*      */     public static StaticData2 getInstance(SPSSchemaAdapterNAO adapter) {
/*  209 */       synchronized (adapter.getSyncObject()) {
/*  210 */         StaticData2 instance = (StaticData2)adapter.getObject(StaticData2.class);
/*  211 */         if (instance == null) {
/*  212 */           instance = new StaticData2(adapter);
/*  213 */           adapter.storeObject(StaticData2.class, instance);
/*      */         } 
/*  215 */         return instance;
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
/*      */ 
/*      */   
/*      */   SPSControllerVCI(SPSSession session, SPSControllerData data, SPSSchemaAdapterNAO adapter) throws Exception {
/*  282 */     this.session = session;
/*  283 */     this.adapter = adapter;
/*  284 */     this.id = data.getControllerID();
/*  285 */     this.isCustomCalibrationController = StaticData.getInstance(adapter).isCustomCalibrationController(this.id);
/*  286 */     this.descIndex = data.getDescriptionIndex();
/*  287 */     this.description = getDescription((SPSLanguage)session.getLanguage(), this.id, this.descIndex, adapter);
/*  288 */     if (this.description == null) {
/*  289 */       log.error("failed to look-up controller description (controller=" + this.id + ", description index=" + this.descIndex + ", locale=" + session.getLanguage().getLocale() + ")");
/*  290 */       this.description = getDescription((SPSLanguage)session.getLanguage(), this.id, this.descIndex, adapter);
/*  291 */       if (this.description == null) {
/*  292 */         this.description = "controller label missing (controller-ID=" + this.id + ", description index=" + this.descIndex + ")";
/*      */       }
/*      */     } 
/*  295 */     String j2534 = data.getJ2534().trim();
/*  296 */     if (j2534.equalsIgnoreCase("+J25")) {
/*  297 */       this.j2534Dependency = Boolean.TRUE;
/*  298 */     } else if (j2534.equalsIgnoreCase("-J25")) {
/*  299 */       this.j2534Dependency = Boolean.FALSE;
/*      */     } 
/*  301 */     this.preRPOCode = data.getPreRPOCode().trim();
/*  302 */     this.postRPOCode = data.getPostRPOCode().trim();
/*  303 */     this.deviceID = data.getDeviceID();
/*  304 */     this.requestMethodID = data.getRequestMethodID();
/*  305 */     this.programmingTypes = SPSProgrammingType.getProgrammingTypes((SPSLanguage)session.getLanguage(), data.getProgrammingMethods(), adapter);
/*  306 */     this.defaultVCI = data.getVCI();
/*  307 */     this.VCI = -1;
/*  308 */     this.preProgrammingInstructions = SPSProgrammingInstructions.tokenize(data.getPreProgrammingInstructions());
/*  309 */     this.postProgrammingInstructions = SPSProgrammingInstructions.tokenize(data.getPostProgrammingInstructions());
/*  310 */     if (adapter.getDatabaseLink().getDBMS() == 3) {
/*  311 */       this.afterMarketFlag = data.getAfterMarketFlag();
/*      */     }
/*      */     try {
/*  314 */       this.replaceInstruction = data.getReplaceInstruction();
/*  315 */       int replacer = data.getReplacer();
/*  316 */       if (replacer > 0) {
/*  317 */         this.replaceAttributes = (List)StaticData.getInstance(adapter).getReplacers().get(Integer.valueOf(replacer));
/*      */       }
/*  319 */     } catch (Exception x) {}
/*      */     
/*  321 */     if (data.getSuppressSequenceControllerFlag() != null && "Y".equalsIgnoreCase(data.getSuppressSequenceControllerFlag())) {
/*  322 */       this.suppressSequenceControllerFlag = true;
/*      */     }
/*  324 */     this.securityCodeRequired = data.isSecurityCodeRequired();
/*      */   }
/*      */   
/*      */   public boolean isSecurityCodeRequired() {
/*  328 */     return this.securityCodeRequired;
/*      */   }
/*      */   
/*      */   public boolean getSuppressSequenceControllerFlag() {
/*  332 */     return this.suppressSequenceControllerFlag;
/*      */   }
/*      */   
/*      */   public boolean isXMLProgramming() {
/*  336 */     Integer ptypeXML = (Integer)StaticData.getInstance(this.adapter).getProgrammingTypes().get(Integer.valueOf(this.id));
/*  337 */     return XMLProgrammingTypes.PROGRAMMING.equals(ptypeXML);
/*      */   }
/*      */   
/*      */   public boolean isXMLConfiguration() {
/*  341 */     Integer ptypeXML = (Integer)StaticData.getInstance(this.adapter).getProgrammingTypes().get(Integer.valueOf(this.id));
/*  342 */     return XMLProgrammingTypes.CONFIGURATION.equals(ptypeXML);
/*      */   }
/*      */   
/*      */   public boolean isType4() {
/*  346 */     Integer ptypeXML = (Integer)StaticData.getInstance(this.adapter).getProgrammingTypes().get(Integer.valueOf(this.id));
/*  347 */     return XMLProgrammingTypes.TYPE4.equals(ptypeXML);
/*      */   }
/*      */   
/*      */   public boolean requiresCustomCalibration() {
/*  351 */     return this.isCustomCalibrationController;
/*      */   }
/*      */   
/*      */   public Boolean hasJ2534Dependency() {
/*  355 */     return this.j2534Dependency;
/*      */   }
/*      */   
/*      */   public void setVCI(int vci) {
/*  359 */     this.VCI = vci;
/*      */   }
/*      */   
/*      */   public void setArchive(Archive archive) {
/*  363 */     if (this.archive != null && this.archive.getChangeReason().equals(archive.getChangeReason())) {
/*      */       return;
/*      */     }
/*  366 */     this.archive = archive;
/*      */   }
/*      */   
/*      */   public void setPartFile(PartFile file) {
/*  370 */     this.file = file;
/*      */   }
/*      */   
/*      */   public void setHardware(SPSPart part) {
/*  374 */     this.pdata = null;
/*  375 */     if (part == null) {
/*  376 */       this.hardware = null;
/*      */       return;
/*      */     } 
/*  379 */     if (this.hardware == null) {
/*  380 */       this.hardware = new ArrayList();
/*      */     }
/*  382 */     this.hardware.add(part);
/*      */   }
/*      */   
/*      */   public List getHardware() {
/*  386 */     return this.hardware;
/*      */   }
/*      */   
/*      */   public Archive getArchive() {
/*  390 */     return this.archive;
/*      */   }
/*      */   
/*      */   public void setControllerData(Object data) {
/*  394 */     this.data = data;
/*      */   }
/*      */   
/*      */   public Object getControllerData() {
/*  398 */     return this.data;
/*      */   }
/*      */   
/*      */   public int getID() {
/*  402 */     return this.id;
/*      */   }
/*      */   
/*      */   public int getVCI() {
/*  406 */     return this.actualVCI;
/*      */   }
/*      */   
/*      */   public int getControllerVCI() {
/*  410 */     return this.defaultVCI;
/*      */   }
/*      */   
/*      */   public String getAfterMarketFlag() {
/*  414 */     return this.afterMarketFlag;
/*      */   }
/*      */   
/*      */   public String getDescription() {
/*  418 */     return this.description;
/*      */   }
/*      */   
/*      */   public String getLabel() {
/*  422 */     return getLabel(this.id, this.descIndex, this.adapter);
/*      */   }
/*      */   
/*      */   public int getDescIndex() {
/*  426 */     return this.descIndex;
/*      */   }
/*      */   
/*      */   public int getDeviceID() {
/*  430 */     return this.deviceID;
/*      */   }
/*      */   
/*      */   public int getRequestMethodID() {
/*  434 */     return this.requestMethodID;
/*      */   }
/*      */   
/*      */   public List getProgrammingTypes() {
/*  438 */     return this.programmingTypes;
/*      */   }
/*      */   
/*      */   public List getPreProgrammingInstructions() {
/*  442 */     return this.preProgrammingInstructions;
/*      */   }
/*      */   
/*      */   public List getPostProgrammingInstructions() {
/*  446 */     return this.postProgrammingInstructions;
/*      */   }
/*      */   
/*      */   public int getReplaceInstruction() {
/*  450 */     return this.replaceInstruction;
/*      */   }
/*      */   
/*      */   public List getReplaceAttributes() {
/*  454 */     return this.replaceAttributes;
/*      */   }
/*      */   
/*      */   public List getPreSelectionOptions() throws Exception {
/*  458 */     return buildOption(this.preRPOCode, this.adapter);
/*      */   }
/*      */   
/*      */   public List getPostSelectionOptions() throws Exception {
/*  462 */     return buildOption(this.postRPOCode, this.adapter);
/*      */   }
/*      */   
/*      */   public SPSProgrammingData getProgrammingData(SPSSchemaAdapter adapter) throws Exception {
/*  466 */     if (this.pdata == null) {
/*  467 */       this.pdata = constructProgrammingData((SPSVehicle)this.session.getVehicle(), (SPSSchemaAdapterNAO)adapter);
/*      */     }
/*  469 */     return this.pdata;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void update(SPSSchemaAdapter adapter) throws Exception {
/*  475 */     SPSProgrammingData data = (SPSProgrammingData)this.session.getProgrammingData(adapter);
/*  476 */     data.update(this.hardware, (SPSSchemaAdapterNAO)adapter);
/*      */   }
/*      */   
/*      */   protected SPSProgrammingData handleArchiveProgrammingData(SPSProgrammingData data, SPSSchemaAdapterNAO adapter) throws Exception {
/*  480 */     data.setArchive((SPSLanguage)this.session.getLanguage(), this.archive, adapter);
/*      */     
/*  482 */     SPSCOP.checkBulletin(data.getModules().get(0), adapter);
/*  483 */     SPSVehicle vehicle = (SPSVehicle)this.session.getVehicle();
/*  484 */     checkCurrentCalibrationInformation(data.getModules(), vehicle.getVIT1(), adapter);
/*  485 */     if (this.saturn != null) {
/*  486 */       data.setOptionBytes(this.saturn.getOptionBytes(vehicle, adapter));
/*      */     }
/*  488 */     return data;
/*      */   }
/*      */   
/*      */   protected SPSProgrammingData handlePartFileProgrammingData(SPSProgrammingData data, SPSSchemaAdapterNAO adapter) throws Exception {
/*  492 */     data.setPartFile((SPSLanguage)this.session.getLanguage(), this.file, adapter);
/*  493 */     data.build((SPSLanguage)this.session.getLanguage(), this.hardware, adapter);
/*  494 */     if (this.hardware != null && this.hardware.size() == 0) {
/*  495 */       this.hardware = null;
/*      */     }
/*  497 */     SPSVehicle vehicle = (SPSVehicle)this.session.getVehicle();
/*  498 */     checkCurrentCalibrationInformation(data.getModules(), vehicle.getVIT1(), adapter);
/*  499 */     if (this.saturn != null) {
/*  500 */       data.setOptionBytes(this.saturn.getOptionBytes(vehicle, adapter));
/*      */     }
/*  502 */     return data;
/*      */   }
/*      */   
/*      */   protected void checkCurrentCalibrationInformation(List modules, VIT1Data vit1, SPSSchemaAdapterNAO adapter) {
/*  506 */     if (vit1 != null) {
/*  507 */       List parts = vit1.getParts();
/*  508 */       if (parts == null || parts.size() == 0) {
/*  509 */         parts = vit1.getParts(this.deviceID);
/*      */       }
/*  511 */       if (parts != null && parts.size() > 0) {
/*  512 */         boolean checkCVN = vit1.getReadCVN();
/*  513 */         List partnums = checkCVN ? vit1.getPartNumbers(this.deviceID) : null;
/*  514 */         if (checkCVN && parts.size() == 1 && modules.size() == 1) {
/*  515 */           partnums = vit1.getSubAssembly(this.deviceID);
/*      */         }
/*  517 */         addCurrentCalibrationInformation(modules, parts, partnums, checkCVN, adapter);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected SPSProgrammingData constructProgrammingData(SPSVehicle vehicle, SPSSchemaAdapterNAO adapter) throws Exception {
/*  524 */     SPSProgrammingData data = new SPSProgrammingData();
/*  525 */     data.setDeviceID(Integer.valueOf(this.deviceID));
/*  526 */     data.setVIN(vehicle.getVIN().toString());
/*  527 */     if (this.archive != null) {
/*  528 */       return handleArchiveProgrammingData(data, adapter);
/*      */     }
/*  530 */     if (this.file != null) {
/*  531 */       return handlePartFileProgrammingData(data, adapter);
/*      */     }
/*  533 */     this.actualVCI = this.defaultVCI;
/*  534 */     if (this.VCI >= 0) {
/*  535 */       this.actualVCI = this.VCI;
/*      */     }
/*  537 */     if (this.actualVCI == 0) {
/*  538 */       this.actualVCI = resolveVCI(vehicle.getVIN(), adapter);
/*  539 */       if (this.actualVCI == 0) {
/*  540 */         return null;
/*      */       }
/*      */     } 
/*  543 */     resolveVCI(data, Integer.toString(this.actualVCI), adapter);
/*      */     
/*  545 */     if (data.getModules() == null || data.getModules().isEmpty()) {
/*  546 */       return null;
/*      */     }
/*  548 */     Set usageCOP = COPQualification.qualify(adapter, vehicle, this.preRPOCode, this.postRPOCode);
/*  549 */     data.build((SPSLanguage)this.session.getLanguage(), this.hardware, usageCOP, adapter);
/*  550 */     if (this.hardware != null && this.hardware.size() == 0) {
/*  551 */       this.hardware = null;
/*      */     }
/*  553 */     checkCurrentCalibrationInformation(data.getModules(), vehicle.getVIT1(), adapter);
/*  554 */     if (this.saturn != null) {
/*  555 */       data.setOptionBytes(this.saturn.getOptionBytes(vehicle, adapter));
/*      */     }
/*  557 */     return data;
/*      */   }
/*      */   
/*      */   protected void addCurrentCalibrationInformation(List<SPSModule> modules, List<String> parts, List partnums, boolean checkCVN, SPSSchemaAdapterNAO adapter) {
/*  561 */     if ("replace".equalsIgnoreCase(this.session.getVehicle().getVIT1().getSPSMode()));
/*      */ 
/*      */     
/*  564 */     if (parts != null && parts.size() == 0) {
/*  565 */       parts = null;
/*      */     }
/*  567 */     for (int i = 0; i < modules.size(); i++) {
/*  568 */       SPSModule module = modules.get(i);
/*  569 */       if (i == 0 && modules.size() > 1) {
/*      */ 
/*      */         
/*  572 */         module.setCurrentCalibration((SPSLanguage)this.session.getLanguage(), null, adapter);
/*  573 */       } else if (parts != null && parts.size() == 1 && modules.size() == 1) {
/*  574 */         module.setCurrentCalibration((SPSLanguage)this.session.getLanguage(), parts.get(0), checkCVN, true, partnums, adapter);
/*  575 */       } else if (parts != null && parts.size() > 1 && modules.size() == 1) {
/*      */ 
/*      */         
/*  578 */         module.setCurrentCalibration(null, null, adapter);
/*      */       }
/*  580 */       else if (parts != null && parts.size() >= i) {
/*  581 */         String part = null;
/*  582 */         for (int j = 0; j < parts.size(); j++) {
/*  583 */           String p = parts.get(j);
/*  584 */           if (module.isCurrentCalibration(p)) {
/*  585 */             part = p;
/*      */             break;
/*      */           } 
/*      */         } 
/*  589 */         if (part != null) {
/*  590 */           module.setCurrentCalibration((SPSLanguage)this.session.getLanguage(), part, checkCVN, false, partnums, adapter);
/*      */         } else {
/*  592 */           module.setCurrentCalibration((SPSLanguage)this.session.getLanguage(), parts.get(i - 1), adapter);
/*      */         } 
/*      */       } else {
/*  595 */         module.setCurrentCalibration((SPSLanguage)this.session.getLanguage(), null, adapter);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void resolveVCI(SPSProgrammingData data, String vci, SPSSchemaAdapterNAO adapter) throws Exception {
/*  601 */     if (!resolveVCI_Quick_Update(StaticData2.getInstance(adapter).getQuickUpdateDB(), data, vci, adapter)) {
/*  602 */       resolveVCI(adapter.getDatabaseLink(), data, vci, adapter);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean resolveVCI(IDatabaseLink db, SPSProgrammingData data, String vci, SPSSchemaAdapterNAO adapter) throws Exception {
/*  608 */     if (adapter.getDatabaseLink().getDBMS() == 3) {
/*  609 */       return resolveVCI2(db, data, vci, adapter);
/*      */     }
/*      */     
/*  612 */     if (db == null) {
/*  613 */       return false;
/*      */     }
/*  615 */     Connection conn = null;
/*  616 */     DBMS.PreparedStatement stmt = null;
/*  617 */     ResultSet rs = null;
/*      */     try {
/*  619 */       conn = db.requestConnection();
/*  620 */       String sql = DBMS.getSQL(db, "Select Origin_Part_No_String, Module_Id_String From VCI_Origin_Members_String Where VCI = ?");
/*  621 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/*  622 */       stmt.setInt(1, Integer.parseInt(vci));
/*  623 */       rs = stmt.executeQuery();
/*  624 */       if (rs.next()) {
/*  625 */         String parts = rs.getString(1);
/*  626 */         String modules = rs.getString(2);
/*  627 */         StringTokenizer ptokenizer = new StringTokenizer(parts, ", ");
/*  628 */         StringTokenizer mtokenizer = new StringTokenizer(modules, ", ");
/*  629 */         while (ptokenizer.hasMoreTokens()) {
/*  630 */           String part = ptokenizer.nextToken();
/*  631 */           String module = mtokenizer.nextToken();
/*  632 */           data.add((SPSLanguage)this.session.getLanguage(), part, module, adapter);
/*      */         } 
/*  634 */         return true;
/*      */       } 
/*  636 */     } catch (Exception e) {
/*  637 */       throw e;
/*      */     } finally {
/*      */       try {
/*  640 */         if (rs != null) {
/*  641 */           rs.close();
/*      */         }
/*  643 */         if (stmt != null) {
/*  644 */           stmt.close();
/*      */         }
/*  646 */         if (conn != null) {
/*  647 */           db.releaseConnection(conn);
/*      */         }
/*  649 */       } catch (Exception x) {}
/*      */     } 
/*      */     
/*  652 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean resolveVCI2(IDatabaseLink db, SPSProgrammingData data, String vci, SPSSchemaAdapterNAO adapter) throws Exception {
/*  657 */     Connection conn = null;
/*  658 */     DBMS.PreparedStatement stmt = null;
/*  659 */     ResultSet rs = null;
/*      */     try {
/*  661 */       conn = db.requestConnection();
/*  662 */       String sql = DBMS.getSQL(db, "Select Origin_Part_No, Module_Id From VCI_Origin_Members Where VCI = ? Order By 2");
/*  663 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/*  664 */       stmt.setInt(1, Integer.parseInt(vci));
/*  665 */       rs = stmt.executeQuery();
/*  666 */       boolean hit = false;
/*  667 */       while (rs.next()) {
/*  668 */         int part = rs.getInt(1);
/*  669 */         int module = rs.getInt(2);
/*  670 */         data.add((SPSLanguage)this.session.getLanguage(), Integer.toString(part), Integer.toString(module), adapter);
/*  671 */         hit = true;
/*      */       } 
/*  673 */       return hit;
/*  674 */     } catch (Exception e) {
/*  675 */       throw e;
/*      */     } finally {
/*      */       try {
/*  678 */         if (rs != null) {
/*  679 */           rs.close();
/*      */         }
/*  681 */         if (stmt != null) {
/*  682 */           stmt.close();
/*      */         }
/*  684 */         if (conn != null) {
/*  685 */           db.releaseConnection(conn);
/*      */         }
/*  687 */       } catch (Exception x) {}
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected int resolveVCI(SPSVIN vin, SPSSchemaAdapterNAO adapter) throws Exception {
/*  693 */     int vci = resolveVCI_quickUpdate(StaticData2.getInstance(adapter).getQuickUpdateDB(), vin);
/*  694 */     if (vci == 0) {
/*  695 */       vci = resolveVCI(adapter.getDatabaseLink(), vin);
/*      */     }
/*  697 */     return vci;
/*      */   }
/*      */   
/*      */   protected boolean resolveVCI_Quick_Update(IDatabaseLink db, SPSProgrammingData data, String vci, SPSSchemaAdapterNAO adapter) throws Exception {
/*  701 */     if (db == null) {
/*  702 */       return false;
/*      */     }
/*  704 */     Connection conn = null;
/*  705 */     DBMS.PreparedStatement stmt = null;
/*  706 */     ResultSet rs = null;
/*      */     try {
/*  708 */       conn = db.requestConnection();
/*  709 */       String sql = DBMS.getSQL(db, "Select Origin_Part_No_String, Module_Id_String, Date_Time From VCI_Origin_Members_String Where VCI = ?");
/*  710 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/*  711 */       stmt.setInt(1, Integer.parseInt(vci));
/*  712 */       rs = stmt.executeQuery();
/*  713 */       if (rs.next()) {
/*  714 */         String parts = rs.getString(1);
/*  715 */         String modules = rs.getString(2);
/*  716 */         Timestamp TS = rs.getTimestamp(3);
/*  717 */         long timestamp = TS.getTime();
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  722 */         boolean validtimestamp = isValidtimestamp(timestamp);
/*  723 */         if (!validtimestamp) {
/*  724 */           return false;
/*      */         }
/*  726 */         StringTokenizer ptokenizer = new StringTokenizer(parts, ", ");
/*  727 */         StringTokenizer mtokenizer = new StringTokenizer(modules, ", ");
/*  728 */         while (ptokenizer.hasMoreTokens()) {
/*  729 */           String part = ptokenizer.nextToken();
/*  730 */           String module = mtokenizer.nextToken();
/*  731 */           data.add((SPSLanguage)this.session.getLanguage(), part, module, adapter);
/*      */         } 
/*  733 */         return true;
/*      */       } 
/*  735 */     } catch (Exception e) {
/*  736 */       throw e;
/*      */     } finally {
/*      */       try {
/*  739 */         if (rs != null) {
/*  740 */           rs.close();
/*      */         }
/*  742 */         if (stmt != null) {
/*  743 */           stmt.close();
/*      */         }
/*  745 */         if (conn != null) {
/*  746 */           db.releaseConnection(conn);
/*      */         }
/*  748 */       } catch (Exception x) {}
/*      */     } 
/*      */     
/*  751 */     return false;
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
/*  770 */     if (db == null) {
/*  771 */       return 0;
/*      */     }
/*  773 */     Connection conn = null;
/*  774 */     DBMS.PreparedStatement stmt = null;
/*  775 */     ResultSet rs = null;
/*      */     try {
/*  777 */       conn = db.requestConnection();
/*  778 */       String sql = DBMS.getSQL(db, "Select VIN_Id, Date_Time from VCI_AsBuilt_Id where VIN_1To8 = ? and VIN_10 = ? and VIN_11 = ?");
/*  779 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/*  780 */       stmt.setString(1, vin.toString().substring(0, 8));
/*  781 */       stmt.setString(2, DBMS.toString(vin.getPosition(10)));
/*  782 */       stmt.setString(3, DBMS.toString(vin.getPosition(11)));
/*  783 */       rs = stmt.executeQuery();
/*  784 */       if (rs.next()) {
/*  785 */         int vinid = rs.getInt(1);
/*  786 */         Timestamp TS = rs.getTimestamp(2);
/*  787 */         long timestamp = TS.getTime();
/*      */         
/*  789 */         boolean validtimestamp = isValidtimestamp(timestamp);
/*  790 */         if (vinid != 0 && validtimestamp == true) {
/*  791 */           rs.close();
/*  792 */           rs = null;
/*  793 */           stmt.close();
/*  794 */           stmt = null;
/*  795 */           return resolveVCI(conn, vinid, vin.getSequence(), db);
/*      */         } 
/*      */       } 
/*  798 */     } catch (Exception e) {
/*  799 */       throw e;
/*      */     } finally {
/*      */       try {
/*  802 */         if (rs != null) {
/*  803 */           rs.close();
/*      */         }
/*  805 */         if (stmt != null) {
/*  806 */           stmt.close();
/*      */         }
/*  808 */         if (conn != null) {
/*  809 */           db.releaseConnection(conn);
/*      */         }
/*  811 */       } catch (Exception x) {}
/*      */     } 
/*      */     
/*  814 */     return 0;
/*      */   }
/*      */   
/*      */   private boolean isValidtimestamp(long timestamp) {
/*  818 */     boolean valid = false;
/*  819 */     Long DBVERSION_TS = null;
/*  820 */     long dbversion_ts = -1L;
/*  821 */     DBVERSION_TS = this.adapter.getNAO_DB_VERSION_TIMESTAMP();
/*  822 */     dbversion_ts = DBVERSION_TS.longValue();
/*      */ 
/*      */ 
/*      */     
/*  826 */     if (timestamp > dbversion_ts) {
/*  827 */       valid = true;
/*      */     }
/*  829 */     return valid;
/*      */   }
/*      */ 
/*      */   
/*      */   protected int resolveVCI(IDatabaseLink db, SPSVIN vin) throws Exception {
/*  834 */     if (this.adapter.getDatabaseLink().getDBMS() == 3) {
/*  835 */       return resolveVCI(db, vin.toString());
/*      */     }
/*      */ 
/*      */     
/*  839 */     if (db == null) {
/*  840 */       return 0;
/*      */     }
/*  842 */     Connection conn = null;
/*  843 */     DBMS.PreparedStatement stmt = null;
/*  844 */     ResultSet rs = null;
/*      */     try {
/*  846 */       conn = db.requestConnection();
/*  847 */       String sql = DBMS.getSQL(db, "Select VIN_Id from VCI_AsBuilt_Id where VIN_1To8 = ? and VIN_10 = ? and VIN_11 = ?");
/*  848 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/*  849 */       stmt.setString(1, vin.toString().substring(0, 8));
/*  850 */       stmt.setString(2, DBMS.toString(vin.getPosition(10)));
/*  851 */       stmt.setString(3, DBMS.toString(vin.getPosition(11)));
/*  852 */       rs = stmt.executeQuery();
/*  853 */       if (rs.next()) {
/*  854 */         int vinid = rs.getInt(1);
/*  855 */         if (vinid != 0) {
/*  856 */           rs.close();
/*  857 */           rs = null;
/*  858 */           stmt.close();
/*  859 */           stmt = null;
/*  860 */           return resolveVCI(conn, vinid, vin.getSequence(), db);
/*      */         } 
/*      */       } 
/*  863 */     } catch (Exception e) {
/*  864 */       throw e;
/*      */     } finally {
/*      */       try {
/*  867 */         if (rs != null) {
/*  868 */           rs.close();
/*      */         }
/*  870 */         if (stmt != null) {
/*  871 */           stmt.close();
/*      */         }
/*  873 */         if (conn != null) {
/*  874 */           db.releaseConnection(conn);
/*      */         }
/*  876 */       } catch (Exception x) {}
/*      */     } 
/*      */     
/*  879 */     return 0;
/*      */   }
/*      */   
/*      */   protected int resolveVCI(Connection conn, int vinid, String sequence, IDatabaseLink dblink) throws Exception {
/*  883 */     DBMS.PreparedStatement stmt = null;
/*  884 */     ResultSet rs = null;
/*      */     try {
/*  886 */       String target = Integer.toString(this.id);
/*  887 */       String sql = DBMS.getSQL(dblink, "Select VCI_String, Controller_Id_String from VCI_AsBuilt_String where VIN_12To17 = ? and VIN_Id = ?");
/*  888 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/*  889 */       stmt.setString(1, sequence);
/*  890 */       stmt.setInt(2, vinid);
/*  891 */       rs = stmt.executeQuery();
/*  892 */       while (rs.next()) {
/*  893 */         String vcilist = rs.getString(1);
/*  894 */         String controllers = rs.getString(2);
/*  895 */         if (controllers.indexOf(target) >= 0) {
/*  896 */           return resolveVCI(target, controllers, vcilist);
/*      */         }
/*      */       } 
/*  899 */     } catch (Exception e) {
/*  900 */       throw e;
/*      */     } finally {
/*      */       try {
/*  903 */         if (rs != null) {
/*  904 */           rs.close();
/*      */         }
/*  906 */         if (stmt != null) {
/*  907 */           stmt.close();
/*      */         }
/*  909 */       } catch (Exception x) {}
/*      */     } 
/*      */     
/*  912 */     return 0;
/*      */   }
/*      */   
/*      */   protected int resolveVCI(String target, String controllers, String vcilist) {
/*  916 */     StringTokenizer ctokenizer = new StringTokenizer(controllers, ", ");
/*  917 */     StringTokenizer vtokenizer = new StringTokenizer(vcilist, ", ");
/*  918 */     while (ctokenizer.hasMoreTokens()) {
/*  919 */       String controller = ctokenizer.nextToken();
/*  920 */       String vci = vtokenizer.nextToken();
/*  921 */       if (target.equals(controller)) {
/*  922 */         return Integer.parseInt(vci);
/*      */       }
/*      */     } 
/*  925 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   protected int resolveVCI(IDatabaseLink db, String vin) throws Exception {
/*  930 */     Connection conn = null;
/*  931 */     DBMS.PreparedStatement stmt = null;
/*  932 */     ResultSet rs = null;
/*      */     try {
/*  934 */       conn = db.requestConnection();
/*  935 */       String sql = DBMS.getSQL(db, "Select Controller_Id, VCI from VCI_AsBuilt where VIN = ?");
/*  936 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/*  937 */       stmt.setString(1, vin);
/*  938 */       rs = stmt.executeQuery();
/*  939 */       while (rs.next()) {
/*  940 */         int controllerID = rs.getInt(1);
/*  941 */         int vci = rs.getInt(2);
/*  942 */         if (controllerID == this.id) {
/*  943 */           return vci;
/*      */         }
/*      */       } 
/*  946 */     } catch (Exception e) {
/*  947 */       throw e;
/*      */     } finally {
/*      */       try {
/*  950 */         if (rs != null) {
/*  951 */           rs.close();
/*      */         }
/*  953 */         if (stmt != null) {
/*  954 */           stmt.close();
/*      */         }
/*  956 */         if (conn != null) {
/*  957 */           db.releaseConnection(conn);
/*      */         }
/*  959 */       } catch (Exception x) {}
/*      */     } 
/*      */     
/*  962 */     return 0;
/*      */   }
/*      */   
/*      */   protected List buildOption(String rpo, SPSSchemaAdapterNAO adapter) throws Exception {
/*  966 */     List<? extends SPSOption> obOptions = null;
/*  967 */     if (rpo == this.postRPOCode) {
/*  968 */       SPSVehicle vehicle = (SPSVehicle)this.session.getVehicle();
/*  969 */       List optionBytes = vehicle.getOptionBytes(this.deviceID);
/*  970 */       if (optionBytes != null) {
/*  971 */         if (this.saturn == null) {
/*  972 */           this.saturn = new SPSSaturnData((SPSLanguage)this.session.getLanguage(), vehicle, this.deviceID);
/*  973 */           obOptions = this.saturn.getOptions(adapter);
/*      */         } else {
/*  975 */           obOptions = this.saturn.getOptions(vehicle);
/*      */         } 
/*      */       }
/*      */     } 
/*  979 */     if (!rpo.equals("~")) {
/*  980 */       List<SPSOption> options = new ArrayList();
/*  981 */       SPSOption option = SPSOption.getRPO((SPSLanguage)this.session.getLanguage(), rpo, adapter);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  990 */       if (option == null) {
/*  991 */         throw new Exception("unknown RPO code '" + rpo + "'.");
/*      */       }
/*  993 */       options.add(option);
/*  994 */       SPSOption group = ((SPSVehicle)this.session.getVehicle()).getOptionGroup(this.session, rpo, adapter);
/*  995 */       if (group != null) {
/*  996 */         group = handleMandatoryOptions(options, group, (SPSVehicle)this.session.getVehicle(), adapter);
/*  997 */         option.setType(group);
/*  998 */       } else if (option.getType() != null) {
/*  999 */         option.setType(SPSOption.getOptionGroup((SPSLanguage)this.session.getLanguage(), option.getType().getID(), adapter));
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1004 */       if (obOptions != null) {
/* 1005 */         options.addAll(obOptions);
/*      */       }
/* 1007 */       return options;
/* 1008 */     }  if (rpo == this.preRPOCode) {
/* 1009 */       List options = new ArrayList();
/*      */       
/* 1011 */       handleMandatoryOptions(options, null, (SPSVehicle)this.session.getVehicle(), adapter);
/* 1012 */       return (options.size() == 0) ? null : options;
/*      */     } 
/* 1014 */     return (obOptions != null) ? obOptions : null;
/*      */   }
/*      */ 
/*      */   
/*      */   protected SPSOption handleMandatoryOptions(List<SPSOption> options, SPSOption group, SPSVehicle vehicle, SPSSchemaAdapterNAO adapter) throws Exception {
/* 1019 */     List<Pair> mandatoryOptions = vehicle.getMandatoryOptions();
/* 1020 */     if (mandatoryOptions != null) {
/* 1021 */       String preOptionGroupID = (group != null) ? group.getID() : null;
/* 1022 */       for (int i = 0; i < mandatoryOptions.size(); i++) {
/* 1023 */         Pair pair = mandatoryOptions.get(i);
/* 1024 */         String groupID = (String)pair.getFirst();
/* 1025 */         if (preOptionGroupID == null || !preOptionGroupID.equals(groupID)) {
/* 1026 */           String rpo = (String)pair.getSecond();
/* 1027 */           SPSOption option = SPSOption.getRPO((SPSLanguage)this.session.getLanguage(), rpo, adapter);
/* 1028 */           if (option == null) {
/* 1029 */             throw new Exception("unknown RPO code '" + rpo + "'.");
/*      */           }
/* 1031 */           SPSOption ogroup = SPSOption.getOptionGroup((SPSLanguage)this.session.getLanguage(), Integer.parseInt(groupID), adapter);
/* 1032 */           option.setType(ogroup);
/* 1033 */           options.add(option);
/* 1034 */         } else if (preOptionGroupID != null && preOptionGroupID.equals(groupID)) {
/*      */ 
/*      */           
/* 1037 */           group = SPSOption.getOptionGroup((SPSLanguage)this.session.getLanguage(), Integer.parseInt(groupID), adapter);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1041 */     return group;
/*      */   }
/*      */   
/*      */   public int hashCode() {
/* 1045 */     return this.id;
/*      */   }
/*      */   
/*      */   @SuppressWarnings({"EQ_CHECK_FOR_OPERAND_NOT_COMPATIBLE_WITH_THIS"})
/*      */   public boolean equals(Object object) {
/* 1050 */     if (object == null)
/* 1051 */       return false; 
/* 1052 */     if (object instanceof SPSControllerVCI && ((SPSControllerVCI)object).getID() == this.id)
/* 1053 */       return true; 
/* 1054 */     if (object instanceof SPSControllerReference) {
/* 1055 */       return ((SPSControllerReference)object).accept(this);
/*      */     }
/* 1057 */     return false;
/*      */   }
/*      */   
/*      */   public String toString() {
/* 1061 */     return "id = " + this.id + " " + getDescription() + " (options=" + this.preRPOCode + "/" + this.postRPOCode + ")\r\n" + "[device=" + this.deviceID + ",reqid=" + this.requestMethodID + ",vci=" + this.VCI + ",pre/post=" + SPSProgrammingInstructions.toString(this.preProgrammingInstructions) + "->" + SPSProgrammingInstructions.toString(this.postProgrammingInstructions) + "]";
/*      */   }
/*      */   
/*      */   protected static String getDescription(SPSLanguage language, int id, int descIndex, SPSSchemaAdapterNAO adapter) {
/* 1065 */     String key = Integer.valueOf(id).toString() + ":" + Integer.valueOf(descIndex).toString();
/* 1066 */     Map descriptions = StaticData.getInstance(adapter).getDescriptions();
/* 1067 */     SPSDescription description = (descriptions == null) ? null : (SPSDescription)descriptions.get(key);
/* 1068 */     if (description != null) {
/* 1069 */       String label = description.get(language);
/* 1070 */       if (label == null && language.getLocale().compareTo("en_GB") != 0)
/*      */       {
/* 1072 */         label = getDescription(SPSLanguage.getLanguage("en_GB", adapter), id, descIndex, adapter);
/*      */       }
/* 1074 */       return label;
/*      */     } 
/* 1076 */     return null;
/*      */   }
/*      */   
/*      */   protected static String getLabel(int id, int descIndex, SPSSchemaAdapterNAO adapter) {
/* 1080 */     String key = Integer.valueOf(id).toString() + ":" + Integer.valueOf(descIndex).toString();
/* 1081 */     Map descriptions = StaticData.getInstance(adapter).getDescriptions();
/* 1082 */     SPSDescription description = (descriptions == null) ? null : (SPSDescription)descriptions.get(key);
/* 1083 */     if (description != null) {
/* 1084 */       return description.getDefaultLabel();
/*      */     }
/* 1086 */     return null;
/*      */   }
/*      */   
/*      */   static void init(SPSSchemaAdapterNAO adapter) throws Exception {
/* 1090 */     StaticData.getInstance(adapter).init();
/*      */   }
/*      */ 
/*      */   
/*      */   static void initCustomCalibrationControllers(SPSSchemaAdapterNAO adapter, String controllers) {
/*      */     try {
/* 1096 */       List<Integer> controllerList = new ArrayList();
/* 1097 */       controllers = controllers.trim();
/* 1098 */       StringTokenizer st = new StringTokenizer(controllers, ", ");
/* 1099 */       while (st.hasMoreTokens()) {
/* 1100 */         controllerList.add(Integer.valueOf(st.nextToken()));
/*      */       }
/* 1102 */       StaticData.getInstance(adapter).initCustomCalibrationControllers(controllerList);
/* 1103 */     } catch (Exception e) {
/* 1104 */       log.error("failed to parse custom calibrations controller list: ", e);
/*      */     } 
/*      */   }
/*      */   
/*      */   static void initQuickUpdate(SPSSchemaAdapterNAO adapter, IDatabaseLink db) throws Exception {
/* 1109 */     StaticData2.getInstance(adapter).setQuickUpdateDB(db);
/*      */   }
/*      */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\nao\SPSControllerVCI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */