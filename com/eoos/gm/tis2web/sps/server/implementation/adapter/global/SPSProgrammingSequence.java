/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.global;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.common.VIT1Data;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.ListValue;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.SPSException;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Part;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingSequence;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.AVUtil;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonException;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ListValueImpl;
/*     */ import com.eoos.gm.tis2web.sps.common.implementation.SPSBlobImpl;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSController;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSPart;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSProgrammingData;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSSchemaAdapter;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSSession;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*     */ import com.eoos.jdbc.JDBCUtil;
/*     */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class SPSProgrammingSequence
/*     */   implements SPSController, ProgrammingSequence {
/*  36 */   protected static final transient Logger log = Logger.getLogger(SPSProgrammingSequence.class);
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final String SELECTED = "selected";
/*     */   public static final String VIT1 = "vit1";
/*     */   public static final String CONSTRUCTED = "constructed";
/*     */   public static final String COP = "cop";
/*     */   public static final String LOADED = "loaded";
/*     */   protected transient SPSSession session;
/*     */   protected transient List state;
/*     */   protected transient List vit1s;
/*     */   protected transient List pdata;
/*     */   protected transient List hardware;
/*     */   protected transient List sequence;
/*     */   
/*     */   public boolean hasSequenceHardwareDependency() {
/*  51 */     return false;
/*     */   }
/*     */   protected int id; protected int psid; protected String description; protected String label; protected List programmingTypes; protected List preProgrammingInstructions; protected List postProgrammingInstructions; protected List controllers; protected List modules; protected List type4; protected Object data;
/*     */   protected int hwIDX;
/*     */   
/*     */   public static final class StaticData { private Map sequences;
/*     */     private Map constraints;
/*     */     
/*     */     private StaticData(SPSSchemaAdapterGlobal adapter) {
/*  60 */       IDatabaseLink dblink = adapter.getDatabaseLink();
/*  61 */       boolean checkReplaceModeSupportedFlag = true;
/*  62 */       this.sequences = new HashMap<Object, Object>();
/*  63 */       this.constraints = new HashMap<Object, Object>();
/*     */       try {
/*  65 */         Connection conn = dblink.requestConnection();
/*     */         
/*     */         try {
/*  68 */           DBMS.PreparedStatement stmt = null;
/*  69 */           ResultSet rs = null;
/*     */           
/*     */           try {
/*  72 */             String str = DBMS.getSQL(dblink, "SELECT PROGRAMMING_SEQUENCE_ID, CONTROLLER_ORDER, CONTROLLER_ID, PRE_PROGRAMMING_INST, POST_PROGRAMMING_INST, ON_FAILURE, REPLACE_MODE_SUPPORTED FROM PROGRAMMING_SEQUENCE ORDER BY 1,2");
/*  73 */             stmt = DBMS.prepareSQLStatement(conn, str);
/*  74 */             rs = stmt.executeQuery();
/*  75 */           } catch (Exception x) {
/*  76 */             checkReplaceModeSupportedFlag = false;
/*  77 */             String str = DBMS.getSQL(dblink, "SELECT PROGRAMMING_SEQUENCE_ID, CONTROLLER_ORDER, CONTROLLER_ID, PRE_PROGRAMMING_INST, POST_PROGRAMMING_INST, ON_FAILURE FROM PROGRAMMING_SEQUENCE ORDER BY 1,2");
/*  78 */             if (stmt != null) {
/*  79 */               stmt.close();
/*     */             }
/*  81 */             stmt = DBMS.prepareSQLStatement(conn, str);
/*  82 */             rs = stmt.executeQuery();
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
/*     */           }
/*     */           finally {
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
/* 105 */             JDBCUtil.close(rs, SPSProgrammingSequence.log);
/* 106 */             if (stmt != null) {
/* 107 */               stmt.close();
/*     */             }
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 113 */           String sql = DBMS.getSQL(dblink, "SELECT PROGRAMMING_SEQUENCE_ID, PART_NO FROM PROGRAMMING_SEQUENCE_CONSTRNTS");
/* 114 */           DBMS.PreparedStatement preparedStatement1 = DBMS.prepareSQLStatement(conn, sql);
/*     */           try {
/* 116 */             ResultSet resultSet = preparedStatement1.executeQuery();
/*     */             try {
/* 118 */               while (resultSet.next()) {
/* 119 */                 List<SPSPart> members; Integer id = Integer.valueOf(resultSet.getInt(1));
/* 120 */                 int part = resultSet.getInt(2);
/* 121 */                 List constraint = (List)this.constraints.get(id);
/* 122 */                 if (constraint == null) {
/* 123 */                   members = new ArrayList();
/* 124 */                   this.constraints.put(id, members);
/*     */                 } 
/* 126 */                 members.add(new SPSPart(part, adapter));
/*     */               } 
/*     */             } finally {
/* 129 */               JDBCUtil.close(resultSet, SPSProgrammingSequence.log);
/*     */             } 
/*     */           } finally {
/* 132 */             preparedStatement1.close();
/*     */           } 
/*     */         } finally {
/* 135 */           dblink.releaseConnection(conn);
/*     */         } 
/* 137 */       } catch (Exception e) {
/* 138 */         SPSProgrammingSequence.log.error("failed to load programming sequence constraints");
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void init() {}
/*     */ 
/*     */     
/*     */     public Map getSequences() {
/* 148 */       return this.sequences;
/*     */     }
/*     */     
/*     */     public Map getConstraints() {
/* 152 */       return this.constraints;
/*     */     }
/*     */     
/*     */     public static StaticData getInstance(SPSSchemaAdapterGlobal adapter) {
/* 156 */       synchronized (adapter.getSyncObject()) {
/* 157 */         StaticData instance = (StaticData)adapter.getObject(StaticData.class);
/* 158 */         if (instance == null) {
/* 159 */           instance = new StaticData(adapter);
/* 160 */           adapter.storeObject(StaticData.class, instance);
/*     */         } 
/* 162 */         return instance;
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
/*     */   SPSProgrammingSequence(SPSSession session, int id, int descIndex, int psid, List preProgrammingInstructions, List postProgrammingInstructions, SPSSchemaAdapterGlobal adapter) throws Exception {
/* 206 */     this.session = session;
/* 207 */     this.id = id;
/* 208 */     this.psid = psid;
/* 209 */     this.description = getDescription((SPSLanguage)session.getLanguage(), id, descIndex, adapter);
/* 210 */     this.label = getLabel(id, descIndex, adapter);
/* 211 */     this.programmingTypes = SPSProgrammingType.getProgrammingTypes((SPSLanguage)session.getLanguage(), SPSProgrammingType.NORMAL.intValue(), adapter);
/* 212 */     this.preProgrammingInstructions = preProgrammingInstructions;
/* 213 */     this.postProgrammingInstructions = postProgrammingInstructions;
/* 214 */     this.controllers = null;
/*     */   }
/*     */   
/*     */   public List getSequence() {
/* 218 */     return this.controllers;
/*     */   }
/*     */   
/*     */   public List getProgrammingData(int sequence) {
/* 222 */     return this.modules.get(sequence);
/*     */   }
/*     */   
/*     */   public List getProgrammingDataList() {
/* 226 */     return this.pdata;
/*     */   }
/*     */   
/*     */   public List getType4Data() {
/* 230 */     return this.type4;
/*     */   }
/*     */   
/*     */   public List getInstructions(SPSModel model) throws Exception {
/* 234 */     List<PairImpl> instructions = new ArrayList();
/* 235 */     for (int i = 0; i < this.sequence.size(); i++) {
/* 236 */       ProgrammingSequenceMember member = this.sequence.get(i);
/* 237 */       List pre = null;
/* 238 */       if (member.preProgrammingInstructions != null) {
/* 239 */         pre = model.getProgrammingMessageVCI((SPSLanguage)this.session.getLanguage(), member.preProgrammingInstructions);
/* 240 */         pre = addMessageTypeID(pre, "intermediate-pre-prog-instructions");
/*     */       } 
/* 242 */       List post = null;
/* 243 */       if (member.postProgrammingInstructions != null) {
/* 244 */         post = model.getProgrammingMessageVCI((SPSLanguage)this.session.getLanguage(), member.postProgrammingInstructions);
/* 245 */         post = addMessageTypeID(post, "intermediate-post-prog-instructions");
/*     */       } 
/* 247 */       PairImpl pairImpl = new PairImpl(pre, post);
/* 248 */       instructions.add(pairImpl);
/*     */     } 
/* 250 */     return instructions;
/*     */   }
/*     */   
/*     */   protected List addMessageTypeID(List<String> msgs, String itype) {
/* 254 */     if (msgs != null) {
/* 255 */       List<String> imsgs = new ArrayList();
/* 256 */       for (int i = 0; i < msgs.size(); i++) {
/* 257 */         imsgs.add(itype + "=" + msgs.get(i));
/*     */       }
/*     */       
/* 260 */       return imsgs;
/*     */     } 
/* 262 */     return null;
/*     */   }
/*     */   
/*     */   public List getFailureHandling() {
/* 266 */     List<Integer> reaction = new ArrayList();
/* 267 */     for (int i = 0; i < this.sequence.size(); i++) {
/* 268 */       ProgrammingSequenceMember member = this.sequence.get(i);
/* 269 */       if (member.onFailure <= 0) {
/* 270 */         reaction.add(Integer.valueOf(member.onFailure));
/*     */       } else {
/* 272 */         Integer position = null;
/* 273 */         for (int j = 0; j < this.sequence.size(); j++) {
/* 274 */           ProgrammingSequenceMember target = this.sequence.get(j);
/* 275 */           if (target.order == member.onFailure) {
/* 276 */             position = Integer.valueOf(j);
/*     */             break;
/*     */           } 
/*     */         } 
/* 280 */         if (position != null) {
/* 281 */           reaction.add(position);
/*     */         } else {
/* 283 */           reaction.add(Integer.valueOf(-1));
/* 284 */           log.error("missing failure handling target: " + this.psid);
/*     */         } 
/*     */       } 
/*     */     } 
/* 288 */     return reaction;
/*     */   }
/*     */   
/*     */   public void setHardware(SPSPart part) {
/* 292 */     if (part == null) {
/* 293 */       for (int i = 0; i < this.controllers.size(); i++) {
/* 294 */         SPSControllerVCI controller = getController(i);
/* 295 */         controller.setHardware(null);
/*     */       } 
/* 297 */       this.hardware = null;
/* 298 */       this.modules = null;
/* 299 */       this.pdata = null;
/* 300 */       this.hwIDX = -1;
/*     */     } else {
/*     */       
/* 303 */       throw new IllegalArgumentException();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setHardware(String controllerID, SPSPart part) {
/* 308 */     if (part == null) {
/* 309 */       throw new IllegalArgumentException();
/*     */     }
/* 311 */     if (this.hardware == null) {
/* 312 */       this.hardware = new ArrayList();
/* 313 */       for (int j = 0; j < this.controllers.size(); j++) {
/* 314 */         this.hardware.add(null);
/*     */       }
/*     */     } 
/* 317 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 318 */       SPSProgrammingData data = this.pdata.get(i);
/*     */       
/* 320 */       SPSControllerVCI controller = getController(i);
/* 321 */       if (match(data, (SPSHardware)part) && controller.getDescription().startsWith(controllerID + '\t')) {
/*     */ 
/*     */         
/* 324 */         log.debug("set ps-hardware: " + part.getPartNumber() + " -> " + controller.getDescription());
/* 325 */         this.hardware.set(i, part);
/* 326 */         controller.setHardware(part);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List getHardware() {
/* 333 */     return this.hardware;
/*     */   }
/*     */   
/*     */   public void setControllerData(Object data) {
/* 337 */     this.data = data;
/*     */   }
/*     */   
/*     */   public int getID() {
/* 341 */     return this.id;
/*     */   }
/*     */   
/*     */   public int getProgrammingSequenceID() {
/* 345 */     return this.psid;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 349 */     return this.description;
/*     */   }
/*     */   
/*     */   public String getLabel() {
/* 353 */     return this.label;
/*     */   }
/*     */   
/*     */   public int getDeviceID() {
/* 357 */     return 252;
/*     */   }
/*     */   
/*     */   public int getRequestMethodID() {
/* 361 */     throw new IllegalArgumentException();
/*     */   }
/*     */   
/*     */   public List getProgrammingTypes() {
/* 365 */     return this.programmingTypes;
/*     */   }
/*     */   
/*     */   public List getPreProgrammingInstructions() {
/* 369 */     return this.preProgrammingInstructions;
/*     */   }
/*     */   
/*     */   public List getPostProgrammingInstructions() {
/* 373 */     return this.postProgrammingInstructions;
/*     */   }
/*     */   
/*     */   public List getPreSelectionOptions() throws Exception {
/* 377 */     return collectPreSelectionOptions();
/*     */   }
/*     */   
/*     */   public List getPostSelectionOptions() throws Exception {
/* 381 */     return collectPostSelectionOptions();
/*     */   }
/*     */   
/*     */   public SPSProgrammingData getProgrammingData(SPSSchemaAdapter adapter) throws Exception {
/* 385 */     return buildProgrammingData((SPSSchemaAdapterGlobal)adapter);
/*     */   }
/*     */   
/*     */   public Object getControllerData() {
/* 389 */     return this.data;
/*     */   }
/*     */   
/*     */   public List getCalibrationFiles() {
/* 393 */     List blobs = new ArrayList();
/* 394 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 395 */       SPSProgrammingData data = this.pdata.get(i);
/* 396 */       blobs.addAll(data.getCalibrationFiles());
/*     */     } 
/* 398 */     return blobs;
/*     */   }
/*     */   
/*     */   protected boolean requiresCustomCalibration(AttributeValueMap data) {
/* 402 */     return (data.getValue(CommonAttribute.CONTROLLER_REQUIRES_CUSTOM_CALIBRATION) != null);
/*     */   }
/*     */   
/*     */   protected void loadCalibrationFileInformation(AttributeValueMap data, SPSSchemaAdapterGlobal adapter) throws Exception {
/* 406 */     boolean requestCustomCalibration = requiresCustomCalibration(data);
/* 407 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 408 */       if (!this.state.get(i).equals("loaded")) {
/* 409 */         collectCalibrationFileInformation(getData(i), adapter, requestCustomCalibration);
/* 410 */         this.state.set(i, "loaded");
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void collectCalibrationFileInformation(SPSProgrammingData data, SPSSchemaAdapterGlobal adapter, boolean requestCustomCalibration) throws Exception {
/* 416 */     List<SPSModule> modules = data.getModules();
/* 417 */     if (modules.size() == 1) {
/* 418 */       SPSModule module = modules.get(0);
/* 419 */       Part part = module.getSelectedPart();
/* 420 */       if (!SPSPart.isEndItemPart(part.getPartNumber(), adapter)) {
/* 421 */         throw new SPSException(CommonException.NoValidEndItemPart);
/*     */       }
/* 423 */       data.setCalibrationFiles(SPSModule.getCalibrationFileInfo(part.getID(), adapter));
/*     */     } else {
/* 425 */       List<Part> parts = new ArrayList();
/* 426 */       for (int i = 0; i < modules.size(); i++) {
/* 427 */         SPSModule module = modules.get(i);
/* 428 */         parts.add(module.getSelectedPart());
/*     */       } 
/* 430 */       List<SPSBlobImpl> calibrations = SPSModule.getCalibrationFileInfo(parts, adapter, requestCustomCalibration);
/* 431 */       data.setCalibrationFiles(calibrations);
/* 432 */       for (int j = 0; j < calibrations.size(); j++) {
/* 433 */         SPSBlobImpl calibration = calibrations.get(j);
/* 434 */         String partNo = calibration.getBlobName();
/* 435 */         SPSModule module = findModule(modules, partNo);
/* 436 */         calibration.setBlobID(Integer.valueOf(module.getID()));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected SPSModule findModule(List<SPSModule> modules, String partNo) {
/* 442 */     for (int i = 0; i < modules.size(); i++) {
/* 443 */       SPSModule module = modules.get(i);
/* 444 */       Part part = module.getSelectedPart();
/* 445 */       if (partNo.equals(part.getPartNumber())) {
/* 446 */         return module;
/*     */       }
/*     */     } 
/* 449 */     return null;
/*     */   }
/*     */   
/*     */   public void evaluateProgrammingDataSelection(AttributeValueMap xdata) throws Exception {
/* 453 */     ListValue parts = (ListValue)xdata.getValue(CommonAttribute.PROGRAMMING_DATA_SELECTION);
/* 454 */     int pos = 0;
/* 455 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 456 */       SPSControllerReference controller = this.controllers.get(i);
/* 457 */       if (!controller.isType4Application()) {
/*     */ 
/*     */         
/* 460 */         SPSProgrammingData data = this.pdata.get(i);
/* 461 */         List<SPSModule> modules = data.getModules();
/* 462 */         for (int j = 0; j < modules.size(); j++) {
/* 463 */           SPSModule module = modules.get(j);
/* 464 */           Part part = parts.getItems().get(pos++);
/* 465 */           module.setSelectedPart(part);
/*     */         } 
/* 467 */         if (data.getModules().size() == 1) {
/* 468 */           SPSPart part = (SPSPart)((SPSModule)data.getModules().get(0)).getSelectedPart();
/* 469 */           if (part != null)
/* 470 */             getData(i).setSSECUSVN(part.getPartNumber()); 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getControllerID4HWSelection() {
/* 477 */     String id = ((SPSControllerReference)this.controllers.get(this.hwIDX)).getDescription();
/* 478 */     int indexTab = id.indexOf("\t");
/* 479 */     if (indexTab >= 0) {
/* 480 */       id = id.substring(0, indexTab);
/*     */     }
/* 482 */     return id;
/*     */   }
/*     */   
/*     */   public void checkModifiedHardwareSelection(String controllerID, SPSHardware selection, SPSSchemaAdapterGlobal adapter) throws Exception {
/* 486 */     if (this.hardware != null) {
/* 487 */       int i; for (i = 0; i < this.hardware.size(); i++) {
/* 488 */         SPSHardware hw = this.hardware.get(i);
/* 489 */         SPSControllerVCI controller = getController(i);
/* 490 */         if (controller.getDescription().startsWith(controllerID + '\t')) {
/*     */ 
/*     */           
/* 493 */           this.hwIDX = i;
/* 494 */           if (hw == null || hw.equals(selection))
/*     */             return; 
/*     */         } 
/*     */       } 
/* 498 */       log.debug("reset ps-hardware: " + getControllerID4HWSelection());
/* 499 */       for (i = this.hwIDX; i < this.controllers.size(); i++) {
/*     */ 
/*     */ 
/*     */         
/* 503 */         if (this.hardware.get(i) != null) {
/* 504 */           this.hardware.set(i, null);
/* 505 */           SPSControllerVCI controller = getController(i);
/* 506 */           controller.setHardware(null);
/*     */         } 
/*     */       } 
/* 509 */       this.pdata = null;
/* 510 */       buildProgrammingData(adapter);
/* 511 */       initModuleList();
/* 512 */       for (i = 0; i < this.controllers.size(); i++) {
/* 513 */         SPSProgrammingData data = this.pdata.get(i);
/* 514 */         if (this.hwIDX < i && this.state.get(i).equals("constructed")) {
/* 515 */           List hardware = data.getHardwareList();
/* 516 */           if (hardware == null) {
/* 517 */             this.modules.set(i, data.getModules());
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void update(SPSSchemaAdapter adapter) throws Exception {
/* 525 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 526 */       SPSProgrammingData data = this.pdata.get(i);
/* 527 */       if (this.state.get(i).equals("constructed") && 
/* 528 */         this.hwIDX == i && data.getHardwareList() != null) {
/*     */         
/* 530 */         SPSControllerVCI controller = getController(i);
/* 531 */         this.session.getVehicle().setVIT1(getVIT1(i));
/* 532 */         getData(i).update(controller.getHardware(), (SPSSchemaAdapterGlobal)adapter);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean acceptHardwareSelection(String controllerID, SPSHardware part) throws Exception {
/* 539 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 540 */       SPSProgrammingData data = this.pdata.get(i);
/*     */       
/* 542 */       SPSControllerVCI controller = getController(i);
/* 543 */       if (match(data, part) && controller.getDescription().startsWith(controllerID + '\t')) {
/*     */ 
/*     */         
/* 546 */         List<SPSHardware> hardware = controller.getHardware();
/* 547 */         if (hardware != null) {
/* 548 */           for (int j = 0; j < hardware.size(); j++) {
/* 549 */             SPSHardware hw = hardware.get(j);
/* 550 */             if (hw.equals(part)) {
/* 551 */               return true;
/*     */             }
/*     */           } 
/*     */         } else {
/* 555 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/* 559 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean match(SPSProgrammingData data, SPSHardware part) {
/*     */     try {
/* 569 */       List<SPSHardwareIndex> hardware = data.getHardwareList();
/* 570 */       if (hardware != null) {
/* 571 */         for (int i = 0; i < hardware.size(); i++) {
/* 572 */           SPSHardwareIndex hwidx = hardware.get(i);
/* 573 */           if (hwidx.contains(part)) {
/* 574 */             return true;
/*     */           }
/*     */         } 
/*     */       }
/* 578 */     } catch (Exception x) {}
/*     */     
/* 580 */     return false;
/*     */   }
/*     */   
/*     */   public List getHardwareList() throws Exception {
/* 584 */     if (this.modules == null) {
/* 585 */       initModuleList();
/*     */     }
/* 587 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 588 */       SPSProgrammingData data = this.pdata.get(i);
/* 589 */       if (this.state.get(i).equals("constructed")) {
/* 590 */         List hardware = data.getHardwareList();
/* 591 */         if (hardware != null) {
/* 592 */           this.hwIDX = i;
/*     */ 
/*     */           
/* 595 */           return hardware;
/*     */         } 
/* 597 */         this.modules.set(i, data.getModules());
/* 598 */         this.state.set(i, "cop");
/*     */       } 
/*     */     } 
/*     */     
/* 602 */     return null;
/*     */   }
/*     */   
/*     */   protected void initModuleList() {
/* 606 */     this.modules = new ArrayList();
/* 607 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 608 */       this.modules.add(null);
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean hasConstraints(SPSSchemaAdapterGlobal adapter) {
/* 613 */     return (StaticData.getInstance(adapter).getConstraints().get(Integer.valueOf(this.id)) != null);
/*     */   }
/*     */   
/*     */   public List filterConstraints(List hardware, SPSSchemaAdapterGlobal adapter) {
/* 617 */     if (hasConstraints(adapter)) {
/* 618 */       List constraint = (List)StaticData.getInstance(adapter).getConstraints().get(Integer.valueOf(this.id));
/* 619 */       Iterator<SPSHardware> it = hardware.iterator();
/* 620 */       while (it.hasNext()) {
/* 621 */         SPSHardware hw = it.next();
/* 622 */         if (match(constraint, hw)) {
/* 623 */           it.remove();
/*     */         }
/*     */       } 
/*     */     } 
/* 627 */     return hardware;
/*     */   }
/*     */   
/*     */   protected boolean match(List<SPSPart> constraints, SPSHardware hardware) {
/* 631 */     for (int i = 0; i < constraints.size(); i++) {
/* 632 */       SPSPart part = constraints.get(i);
/* 633 */       if (part.equals(hardware)) {
/* 634 */         return true;
/*     */       }
/*     */     } 
/* 637 */     return false;
/*     */   }
/*     */   
/*     */   protected SPSProgrammingData buildProgrammingData(SPSSchemaAdapterGlobal adapter) throws Exception {
/* 641 */     if (this.pdata != null) {
/* 642 */       return null;
/*     */     }
/* 644 */     this.pdata = new ArrayList();
/* 645 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 646 */       SPSControllerVCI controller = getController(i);
/* 647 */       this.session.getVehicle().setVIT1(getVIT1(i));
/* 648 */       SPSProgrammingData data = controller.getProgrammingData(adapter);
/* 649 */       if (data == null) {
/* 650 */         throw new SPSException(CommonException.NoValidCOP);
/*     */       }
/* 652 */       this.pdata.add(data);
/* 653 */       if (controller.getDeviceID() == 253) {
/* 654 */         if (this.type4 == null) {
/* 655 */           this.type4 = new ArrayList();
/*     */         }
/* 657 */         SPSType4Data tdata = new SPSType4Data(this.session.getLanguage().getLocale(), adapter);
/*     */ 
/*     */         
/* 660 */         PairImpl pairImpl = new PairImpl(Integer.valueOf(controller.getID()), new ListValueImpl(tdata));
/* 661 */         this.type4.add(pairImpl);
/*     */       } 
/* 663 */       this.state.set(i, "constructed");
/*     */     } 
/* 665 */     return null;
/*     */   }
/*     */   
/*     */   public void setOdometer(String reading) {
/* 669 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 670 */       getData(i).setOdometer(reading);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setKeycode(String keycode) {
/* 675 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 676 */       getData(i).setKeycode(keycode);
/*     */     }
/*     */   }
/*     */   
/*     */   protected List handleVIT1(SPSModel model, SPSSession session, AttributeValueMap data) throws Exception {
/* 681 */     checkMemberControllers();
/* 682 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 683 */       SPSControllerVCI controller = getController(i);
/* 684 */       if (this.state.get(i).equals("selected")) {
/* 685 */         if (controller.getDeviceID() == 253) {
/* 686 */           this.vit1s.set(i, null);
/* 687 */           this.state.set(i, "vit1");
/*     */         } else {
/*     */           
/* 690 */           if (this.vit1s.get(i) == null) {
/* 691 */             int requestMethodID = controller.getRequestMethodID();
/* 692 */             int deviceID = controller.getDeviceID();
/* 693 */             this.vit1s.set(i, "selected");
/* 694 */             return model.getRequestMethodData(requestMethodID, deviceID);
/*     */           } 
/* 696 */           VIT1Data vit1 = (VIT1Data)AVUtil.accessValue(data, CommonAttribute.VIT1);
/* 697 */           if (vit1.getHWNumber() != null && vit1.getHWNumber().trim().length() > 0) {
/*     */             try {
/* 699 */               SPSHardware hardware = new SPSHardware(vit1.getHWNumber());
/* 700 */               controller.setHardware(hardware);
/* 701 */             } catch (Exception x) {}
/*     */           }
/*     */           
/* 704 */           this.vit1s.set(i, vit1);
/* 705 */           this.state.set(i, "vit1");
/*     */         } 
/*     */       }
/*     */     } 
/* 709 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getRequestMethodData(SPSModel model) throws Exception {
/* 720 */     checkMemberControllers();
/* 721 */     List rmd = new ArrayList();
/* 722 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 723 */       SPSControllerVCI controller = getController(i);
/* 724 */       int requestMethodID = controller.getRequestMethodID();
/* 725 */       int deviceID = controller.getDeviceID();
/* 726 */       List rdata = model.getRequestMethodData(requestMethodID, deviceID);
/* 727 */       rmd.addAll(rdata);
/*     */     } 
/* 729 */     return (rmd.size() > 0) ? rmd : null;
/*     */   }
/*     */   
/*     */   protected SPSProgrammingData getData(int i) {
/* 733 */     return this.pdata.get(i);
/*     */   }
/*     */   
/*     */   protected SPSControllerVCI getController(int i) {
/* 737 */     SPSControllerReference reference = this.controllers.get(i);
/* 738 */     return reference.getControllers().get(0);
/*     */   }
/*     */   
/*     */   protected VIT1Data getVIT1(int i) {
/* 742 */     return this.vit1s.get(i);
/*     */   }
/*     */   
/*     */   protected void checkMemberControllers() throws Exception {
/* 746 */     if (this.state == null) {
/* 747 */       this.state = new ArrayList();
/* 748 */       this.vit1s = new ArrayList();
/*     */     } 
/* 750 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 751 */       SPSControllerReference reference = this.controllers.get(i);
/* 752 */       List controllers = reference.getControllers();
/* 753 */       if (controllers == null || controllers.size() != 1)
/* 754 */         throw new SPSException(CommonException.UnsupportedController); 
/* 755 */       if (this.state.size() < this.controllers.size()) {
/* 756 */         this.state.add("selected");
/* 757 */         this.vit1s.add(null);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean qualify(List options, SPSSchemaAdapterGlobal adapter) throws Exception {
/* 763 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 764 */       SPSControllerReference reference = this.controllers.get(i);
/* 765 */       reference.qualify(options, adapter);
/* 766 */       List controllers = reference.getControllers();
/* 767 */       if (controllers == null || controllers.size() == 0) {
/* 768 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 772 */     return true;
/*     */   }
/*     */   
/*     */   protected List collectPreSelectionOptions() throws Exception {
/* 776 */     List options = new ArrayList();
/* 777 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 778 */       SPSControllerReference reference = this.controllers.get(i);
/* 779 */       List preoptions = reference.getPreOptions();
/* 780 */       if (preoptions != null) {
/* 781 */         options.addAll(preoptions);
/*     */       }
/*     */     } 
/* 784 */     return (options.size() > 0) ? options : null;
/*     */   }
/*     */   
/*     */   protected List collectPostSelectionOptions() throws Exception {
/* 788 */     List options = new ArrayList();
/* 789 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 790 */       SPSControllerReference reference = this.controllers.get(i);
/* 791 */       List postoptions = reference.getPostOptions();
/* 792 */       if (postoptions != null) {
/* 793 */         options.addAll(postoptions);
/*     */       }
/*     */     } 
/* 796 */     return (options.size() > 0) ? options : null;
/*     */   }
/*     */   
/*     */   public void reset() {
/* 800 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 801 */       SPSControllerReference reference = this.controllers.get(i);
/* 802 */       reference.reset();
/*     */     } 
/* 804 */     this.state = null;
/* 805 */     this.vit1s = null;
/* 806 */     this.pdata = null;
/* 807 */     this.modules = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValid(SPSSession session, boolean modeReplaceReprogram, SPSControllerList controllers, SPSControllerList pscontrollers, SPSSchemaAdapterGlobal adapter) {
/* 812 */     if (controllers == null || StaticData.getInstance(adapter).getSequences().size() == 0) {
/* 813 */       return false;
/*     */     }
/* 815 */     Iterator<Map.Entry> it = StaticData.getInstance(adapter).getSequences().entrySet().iterator();
/* 816 */     while (it.hasNext()) {
/* 817 */       Map.Entry entry = it.next();
/* 818 */       Integer id = (Integer)entry.getKey();
/* 819 */       if (id.intValue() != this.psid) {
/*     */         continue;
/*     */       }
/* 822 */       Pair sequence = (Pair)entry.getValue();
/* 823 */       List<ProgrammingSequenceMember> members = (List)sequence.getFirst();
/* 824 */       List<SPSControllerReference> references = new ArrayList();
/* 825 */       for (int i = 0; i < members.size() && id != null; i++) {
/* 826 */         ProgrammingSequenceMember member = members.get(i);
/* 827 */         if (modeReplaceReprogram && !member.replaceModeSupported) {
/* 828 */           return false;
/*     */         }
/* 830 */         SPSControllerReference reference = match(controllers, member.controller);
/* 831 */         if (reference == null) {
/* 832 */           reference = match(pscontrollers, member.controller);
/*     */         }
/* 834 */         if (reference != null) {
/* 835 */           references.add(reference);
/*     */         } else {
/* 837 */           id = null;
/*     */         } 
/*     */       } 
/* 840 */       if (id != null && references.size() > 0) {
/* 841 */         this.controllers = references;
/* 842 */         this.sequence = members;
/* 843 */         return true;
/*     */       } 
/*     */     } 
/* 846 */     return false;
/*     */   }
/*     */   
/*     */   protected static SPSControllerReference match(SPSControllerList controllers, Integer controller) {
/* 850 */     Iterator<SPSControllerReference> it = controllers.iterator();
/* 851 */     while (it.hasNext()) {
/* 852 */       SPSControllerReference reference = it.next();
/* 853 */       if (reference.match(controller.intValue())) {
/* 854 */         return reference;
/*     */       }
/*     */     } 
/* 857 */     return null;
/*     */   }
/*     */   
/*     */   protected static String getDescription(SPSLanguage language, int id, int descIndex, SPSSchemaAdapterGlobal adapter) {
/* 861 */     return SPSControllerVCI.getDescription(language, id, descIndex, adapter);
/*     */   }
/*     */   
/*     */   protected static String getLabel(int id, int descIndex, SPSSchemaAdapterGlobal adapter) {
/* 865 */     return SPSControllerVCI.getLabel(id, descIndex, adapter);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 869 */     return getID();
/*     */   }
/*     */   
/*     */   @SuppressWarnings({"EQ_CHECK_FOR_OPERAND_NOT_COMPATIBLE_WITH_THIS"})
/*     */   public boolean equals(Object object) {
/* 874 */     if (object == null)
/* 875 */       return false; 
/* 876 */     if (object instanceof SPSController && ((SPSController)object).getID() == getID())
/* 877 */       return true; 
/* 878 */     if (object instanceof SPSControllerReference) {
/* 879 */       return ((SPSControllerReference)object).accept(this);
/*     */     }
/* 881 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   static void init(SPSSchemaAdapterGlobal adapter) throws Exception {
/* 886 */     StaticData.getInstance(adapter).init();
/*     */   }
/*     */   
/*     */   public static class ProgrammingSequenceMember {
/*     */     protected int order;
/*     */     protected Integer controller;
/*     */     protected List preProgrammingInstructions;
/*     */     protected List postProgrammingInstructions;
/*     */     protected int onFailure;
/*     */     protected boolean replaceModeSupported;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\global\SPSProgrammingSequence.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */