/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.nao;
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.common.VIT1Data;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.ListValue;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.SPSException;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Part;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingDataUnit;
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
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class SPSProgrammingSequence implements SPSController, ProgrammingSequence {
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
/*     */   protected int id;
/*     */   protected int psid;
/*     */   protected String description;
/*     */   protected String label;
/*     */   protected List programmingTypes;
/*     */   protected List preProgrammingInstructions;
/*     */   protected List postProgrammingInstructions;
/*     */   protected List controllers;
/*     */   protected List modules;
/*  51 */   protected static transient Logger log = Logger.getLogger(SPSProgrammingSequence.class);
/*     */   protected List type4;
/*     */   protected Object data;
/*     */   protected int hwIDX;
/*     */   protected boolean hasSequenceHardwareDependency;
/*     */   
/*     */   public static final class StaticData {
/*     */     private StaticData(SPSSchemaAdapterNAO adapter) {
/*  59 */       IDatabaseLink dblink = adapter.getDatabaseLink();
/*  60 */       boolean checkReplaceModeSupportedFlag = true;
/*  61 */       this.sequences = new HashMap<Object, Object>();
/*  62 */       this.constraints = new HashMap<Object, Object>();
/*     */       try {
/*  64 */         Connection conn = dblink.requestConnection();
/*     */         
/*     */         try {
/*  67 */           DBMS.PreparedStatement stmt = null;
/*  68 */           ResultSet rs = null;
/*     */ 
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
/* 106 */             stmt.close();
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 111 */           String sql = DBMS.getSQL(dblink, "SELECT PROGRAMMING_SEQUENCE_ID, PART_NO FROM PROGRAMMING_SEQUENCE_CONSTRNTS");
/* 112 */           DBMS.PreparedStatement preparedStatement1 = DBMS.prepareSQLStatement(conn, sql);
/*     */           try {
/* 114 */             ResultSet resultSet = preparedStatement1.executeQuery();
/*     */             try {
/* 116 */               while (resultSet.next()) {
/* 117 */                 List<SPSPart> members; Integer id = Integer.valueOf(resultSet.getInt(1));
/* 118 */                 int part = resultSet.getInt(2);
/* 119 */                 List constraint = (List)this.constraints.get(id);
/* 120 */                 if (constraint == null) {
/* 121 */                   members = new ArrayList();
/* 122 */                   this.constraints.put(id, members);
/*     */                 } 
/* 124 */                 members.add(new SPSPart(part, adapter));
/*     */               } 
/*     */             } finally {
/* 127 */               JDBCUtil.close(resultSet, SPSProgrammingSequence.log);
/*     */             } 
/*     */           } finally {
/* 130 */             preparedStatement1.close();
/*     */           } 
/*     */         } finally {
/*     */           
/* 134 */           dblink.releaseConnection(conn);
/*     */         } 
/* 136 */       } catch (Exception e) {
/* 137 */         SPSProgrammingSequence.log.error("failed to load programming sequence constraints");
/*     */       } 
/*     */     }
/*     */     
/*     */     private Map sequences;
/*     */     private Map constraints;
/*     */     
/*     */     public void init() {}
/*     */     
/*     */     public Map getSequences() {
/* 147 */       return this.sequences;
/*     */     }
/*     */     
/*     */     public Map getConstraints() {
/* 151 */       return this.constraints;
/*     */     }
/*     */     
/*     */     public static StaticData getInstance(SPSSchemaAdapterNAO adapter) {
/* 155 */       synchronized (adapter.getSyncObject()) {
/* 156 */         StaticData instance = (StaticData)adapter.getObject(StaticData.class);
/* 157 */         if (instance == null) {
/* 158 */           instance = new StaticData(adapter);
/* 159 */           adapter.storeObject(StaticData.class, instance);
/*     */         } 
/* 161 */         return instance;
/*     */       } 
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
/*     */   SPSProgrammingSequence(SPSSession session, int id, int descIndex, int psid, List preProgrammingInstructions, List postProgrammingInstructions, SPSSchemaAdapterNAO adapter) throws Exception {
/* 207 */     this.session = session;
/* 208 */     this.id = id;
/* 209 */     this.psid = psid;
/* 210 */     this.description = getDescription((SPSLanguage)session.getLanguage(), id, descIndex, adapter);
/* 211 */     this.label = getLabel(id, descIndex, adapter);
/* 212 */     this.programmingTypes = SPSProgrammingType.getProgrammingTypes((SPSLanguage)session.getLanguage(), SPSProgrammingType.NORMAL.intValue(), adapter);
/* 213 */     this.preProgrammingInstructions = preProgrammingInstructions;
/* 214 */     this.postProgrammingInstructions = postProgrammingInstructions;
/* 215 */     this.controllers = null;
/*     */   }
/*     */   
/*     */   public List getSequence() {
/* 219 */     return this.controllers;
/*     */   }
/*     */   
/*     */   public List getProgrammingData(int sequence) {
/* 223 */     return this.modules.get(sequence);
/*     */   }
/*     */   
/*     */   public List getProgrammingDataList() {
/* 227 */     return this.pdata;
/*     */   }
/*     */   
/*     */   public List getType4Data() {
/* 231 */     return this.type4;
/*     */   }
/*     */   
/*     */   public List getInstructions(SPSModel model) throws Exception {
/* 235 */     List<PairImpl> instructions = new ArrayList();
/* 236 */     for (int i = 0; i < this.sequence.size(); i++) {
/* 237 */       ProgrammingSequenceMember member = this.sequence.get(i);
/* 238 */       List pre = null;
/* 239 */       if (member.preProgrammingInstructions != null) {
/* 240 */         pre = model.getProgrammingMessageVCI((SPSLanguage)this.session.getLanguage(), member.preProgrammingInstructions);
/* 241 */         pre = addMessageTypeID(pre, "intermediate-pre-prog-instructions");
/*     */       } 
/* 243 */       List post = null;
/* 244 */       if (member.postProgrammingInstructions != null) {
/* 245 */         post = model.getProgrammingMessageVCI((SPSLanguage)this.session.getLanguage(), member.postProgrammingInstructions);
/* 246 */         post = addMessageTypeID(post, "intermediate-post-prog-instructions");
/*     */       } 
/* 248 */       PairImpl pairImpl = new PairImpl(pre, post);
/* 249 */       instructions.add(pairImpl);
/*     */     } 
/* 251 */     return instructions;
/*     */   }
/*     */   
/*     */   protected List addMessageTypeID(List<String> msgs, String itype) {
/* 255 */     if (msgs != null) {
/* 256 */       List<String> imsgs = new ArrayList();
/* 257 */       for (int i = 0; i < msgs.size(); i++) {
/* 258 */         imsgs.add(itype + "=" + msgs.get(i));
/*     */       }
/*     */       
/* 261 */       return imsgs;
/*     */     } 
/* 263 */     return null;
/*     */   }
/*     */   
/*     */   public List getFailureHandling() {
/* 267 */     List<Integer> reaction = new ArrayList();
/* 268 */     for (int i = 0; i < this.sequence.size(); i++) {
/* 269 */       ProgrammingSequenceMember member = this.sequence.get(i);
/* 270 */       if (member.onFailure <= 0) {
/* 271 */         reaction.add(Integer.valueOf(member.onFailure));
/*     */       } else {
/* 273 */         Integer position = null;
/* 274 */         for (int j = 0; j < this.sequence.size(); j++) {
/* 275 */           ProgrammingSequenceMember target = this.sequence.get(j);
/* 276 */           if (target.order == member.onFailure) {
/* 277 */             position = Integer.valueOf(j);
/*     */             break;
/*     */           } 
/*     */         } 
/* 281 */         if (position != null) {
/* 282 */           reaction.add(position);
/*     */         } else {
/* 284 */           reaction.add(Integer.valueOf(-1));
/* 285 */           log.error("missing failure handling target: " + this.psid);
/*     */         } 
/*     */       } 
/*     */     } 
/* 289 */     return reaction;
/*     */   }
/*     */   
/*     */   public void setHardware(SPSPart part) {
/* 293 */     if (part == null) {
/* 294 */       for (int i = 0; i < this.controllers.size(); i++) {
/* 295 */         SPSControllerVCI controller = getController(i);
/* 296 */         controller.setHardware(null);
/*     */       } 
/* 298 */       this.hardware = null;
/* 299 */       this.modules = null;
/* 300 */       this.pdata = null;
/* 301 */       this.hwIDX = -1;
/*     */     } else {
/*     */       
/* 304 */       throw new IllegalArgumentException();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setHardware(String controllerID, SPSPart part) {
/* 309 */     if (part == null) {
/* 310 */       throw new IllegalArgumentException();
/*     */     }
/* 312 */     if (this.hardware == null) {
/* 313 */       this.hardware = new ArrayList();
/* 314 */       for (int j = 0; j < this.controllers.size(); j++) {
/* 315 */         this.hardware.add(null);
/*     */       }
/*     */     } 
/* 318 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 319 */       SPSProgrammingData data = this.pdata.get(i);
/* 320 */       if (match(data, (SPSHardware)part)) {
/* 321 */         SPSControllerVCI controller = getController(i);
/* 322 */         if (!controller.getDescription().startsWith(controllerID + '\t') && hasSequenceHardwareDependency()) {
/*     */           
/* 324 */           log.debug("set ps-hardware (12551): " + part.getPartNumber() + " -> " + controller.getDescription());
/*     */         } else {
/* 326 */           log.debug("set ps-hardware: " + part.getPartNumber() + " -> " + controller.getDescription());
/*     */         } 
/* 328 */         this.hardware.set(i, part);
/* 329 */         controller.setHardware(part);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List getHardware() {
/* 336 */     return this.hardware;
/*     */   }
/*     */   
/*     */   public void setControllerData(Object data) {
/* 340 */     this.data = data;
/*     */   }
/*     */   
/*     */   public int getID() {
/* 344 */     return this.id;
/*     */   }
/*     */   
/*     */   public int getProgrammingSequenceID() {
/* 348 */     return this.psid;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 352 */     return this.description;
/*     */   }
/*     */   
/*     */   public String getLabel() {
/* 356 */     return this.label;
/*     */   }
/*     */   
/*     */   public int getDeviceID() {
/* 360 */     return 252;
/*     */   }
/*     */   
/*     */   public int getRequestMethodID() {
/* 364 */     throw new IllegalArgumentException();
/*     */   }
/*     */   
/*     */   public List getProgrammingTypes() {
/* 368 */     return this.programmingTypes;
/*     */   }
/*     */   
/*     */   public List getPreProgrammingInstructions() {
/* 372 */     return this.preProgrammingInstructions;
/*     */   }
/*     */   
/*     */   public List getPostProgrammingInstructions() {
/* 376 */     return this.postProgrammingInstructions;
/*     */   }
/*     */   
/*     */   public List getPreSelectionOptions() throws Exception {
/* 380 */     return collectPreSelectionOptions();
/*     */   }
/*     */   
/*     */   public List getPostSelectionOptions() throws Exception {
/* 384 */     return collectPostSelectionOptions();
/*     */   }
/*     */   
/*     */   public SPSProgrammingData getProgrammingData(SPSSchemaAdapter adapter) throws Exception {
/* 388 */     return buildProgrammingData((SPSSchemaAdapterNAO)adapter);
/*     */   }
/*     */   
/*     */   public Object getControllerData() {
/* 392 */     return this.data;
/*     */   }
/*     */   
/*     */   public List getCalibrationFiles() {
/* 396 */     List blobs = new ArrayList();
/* 397 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 398 */       SPSProgrammingData data = this.pdata.get(i);
/* 399 */       blobs.addAll(data.getCalibrationFiles());
/*     */     } 
/* 401 */     return blobs;
/*     */   }
/*     */   
/*     */   protected boolean requiresCustomCalibration(AttributeValueMap data) {
/* 405 */     return (data.getValue(CommonAttribute.CONTROLLER_REQUIRES_CUSTOM_CALIBRATION) != null);
/*     */   }
/*     */   
/*     */   protected void loadCalibrationFileInformation(AttributeValueMap data, SPSSchemaAdapterNAO adapter) throws Exception {
/* 409 */     boolean requestCustomCalibration = requiresCustomCalibration(data);
/* 410 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 411 */       if (!this.state.get(i).equals("loaded")) {
/* 412 */         collectCalibrationFileInformation(getData(i), adapter, requestCustomCalibration);
/* 413 */         SPSControllerVCI controller = getController(i);
/* 414 */         if (controller instanceof SPSControllerXML) {
/* 415 */           handleCalibrationFilesXMLController(i);
/*     */         }
/* 417 */         this.state.set(i, "loaded");
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void handleCalibrationFilesXMLController(int pos) {
/* 423 */     Pair pair = this.type4.get(pos);
/* 424 */     ListValueImpl tdata = (ListValueImpl)pair.getSecond();
/* 425 */     List<ProgrammingDataUnit> calibrations = getData(pos).getCalibrationFiles();
/* 426 */     for (int i = 0; i < calibrations.size(); i++) {
/* 427 */       ProgrammingDataUnit file = calibrations.get(i);
/* 428 */       tdata.getItems().add(new PairImpl(file.getBlobName(), null));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void collectCalibrationFileInformation(SPSProgrammingData data, SPSSchemaAdapterNAO adapter, boolean requestCustomCalibration) throws Exception {
/* 434 */     List<SPSModule> modules = data.getModules();
/* 435 */     if (modules.size() == 1) {
/* 436 */       SPSModule module = modules.get(0);
/* 437 */       Part part = module.getSelectedPart();
/* 438 */       if (!SPSPart.isEndItemPart(part.getPartNumber(), adapter)) {
/* 439 */         throw new SPSException(CommonException.NoValidEndItemPart);
/*     */       }
/* 441 */       data.setCalibrationFiles(SPSModule.getCalibrationFileInfo(part.getID(), adapter));
/*     */     } else {
/* 443 */       List<Part> parts = new ArrayList();
/* 444 */       for (int i = 0; i < modules.size(); i++) {
/* 445 */         SPSModule module = modules.get(i);
/* 446 */         parts.add(module.getSelectedPart());
/*     */       } 
/* 448 */       List<SPSBlobImpl> calibrations = SPSModule.getCalibrationFileInfo(parts, adapter, requestCustomCalibration);
/* 449 */       data.setCalibrationFiles(calibrations);
/* 450 */       for (int j = 0; j < calibrations.size(); j++) {
/* 451 */         SPSBlobImpl calibration = calibrations.get(j);
/* 452 */         String partNo = calibration.getBlobName();
/* 453 */         SPSModule module = findModule(modules, partNo);
/* 454 */         calibration.setBlobID(Integer.valueOf(module.getID()));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected SPSModule findModule(List<SPSModule> modules, String partNo) {
/* 460 */     for (int i = 0; i < modules.size(); i++) {
/* 461 */       SPSModule module = modules.get(i);
/* 462 */       Part part = module.getSelectedPart();
/* 463 */       if (partNo.equals(part.getPartNumber())) {
/* 464 */         return module;
/*     */       }
/*     */     } 
/* 467 */     return null;
/*     */   }
/*     */   
/*     */   public void evaluateProgrammingDataSelection(AttributeValueMap xdata) throws Exception {
/* 471 */     ListValue parts = (ListValue)xdata.getValue(CommonAttribute.PROGRAMMING_DATA_SELECTION);
/* 472 */     int pos = 0;
/* 473 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 474 */       SPSControllerReference controller = this.controllers.get(i);
/* 475 */       if (!controller.isType4Application()) {
/*     */ 
/*     */         
/* 478 */         SPSProgrammingData data = this.pdata.get(i);
/* 479 */         List<SPSModule> modules = data.getModules();
/* 480 */         for (int j = 0; j < modules.size(); j++) {
/* 481 */           SPSModule module = modules.get(j);
/* 482 */           Part part = parts.getItems().get(pos++);
/* 483 */           module.setSelectedPart(part);
/*     */         } 
/* 485 */         if (data.getModules().size() == 1) {
/* 486 */           SPSPart part = (SPSPart)((SPSModule)data.getModules().get(0)).getSelectedPart();
/* 487 */           if (part != null)
/* 488 */             getData(i).setSSECUSVN(part.getPartNumber()); 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getControllerID4HWSelection() {
/* 495 */     String id = ((SPSControllerReference)this.controllers.get(this.hwIDX)).getDescription();
/* 496 */     int indexTab = id.indexOf("\t");
/* 497 */     if (indexTab >= 0) {
/* 498 */       id = id.substring(0, indexTab);
/*     */     }
/* 500 */     return id;
/*     */   }
/*     */   
/*     */   public void checkModifiedHardwareSelection(String controllerID, SPSHardware selection, SPSSchemaAdapterNAO adapter) throws Exception {
/* 504 */     if (this.hardware != null) {
/* 505 */       int i; for (i = 0; i < this.hardware.size(); i++) {
/* 506 */         SPSHardware hw = this.hardware.get(i);
/* 507 */         SPSControllerVCI controller = getController(i);
/* 508 */         if (controller.getDescription().startsWith(controllerID + '\t')) {
/*     */ 
/*     */           
/* 511 */           this.hwIDX = i;
/* 512 */           if (hw == null || hw.equals(selection))
/*     */             return; 
/*     */         } 
/*     */       } 
/* 516 */       log.debug("reset ps-hardware: " + getControllerID4HWSelection());
/* 517 */       for (i = this.hwIDX; i < this.controllers.size(); i++) {
/*     */ 
/*     */ 
/*     */         
/* 521 */         if (this.hardware.get(i) != null) {
/* 522 */           this.hardware.set(i, null);
/* 523 */           SPSControllerVCI controller = getController(i);
/* 524 */           controller.setHardware(null);
/*     */         } 
/*     */       } 
/* 527 */       this.pdata = null;
/* 528 */       buildProgrammingData(adapter);
/* 529 */       initModuleList();
/* 530 */       for (i = 0; i < this.controllers.size(); i++) {
/* 531 */         SPSProgrammingData data = this.pdata.get(i);
/* 532 */         if (this.hwIDX < i && this.state.get(i).equals("constructed")) {
/* 533 */           List hardware = data.getHardwareList();
/* 534 */           if (hardware == null) {
/* 535 */             this.modules.set(i, data.getModules());
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean hasSequenceHardwareDependency(List<SPSHardwareIndex> hardware) {
/*     */     try {
/* 544 */       SPSHardwareIndex hwidx = hardware.get(0);
/* 545 */       return (hwidx.getID() < 0);
/* 546 */     } catch (Exception e) {
/* 547 */       log.error("failed to check for sequence hardware dependency", e);
/* 548 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean hasSequenceHardwareDependency() {
/* 553 */     return this.hasSequenceHardwareDependency;
/*     */   }
/*     */   
/*     */   public void update(SPSSchemaAdapter adapter) throws Exception {
/* 557 */     boolean update = false;
/* 558 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 559 */       SPSProgrammingData data = this.pdata.get(i);
/* 560 */       if (this.state.get(i).equals("constructed")) {
/* 561 */         if (this.hwIDX == i && data.getHardwareList() != null) {
/*     */           
/* 563 */           SPSControllerVCI controller = getController(i);
/* 564 */           this.session.getVehicle().setVIT1(getVIT1(i));
/* 565 */           getData(i).update(controller.getHardware(), (SPSSchemaAdapterNAO)adapter);
/* 566 */           update = true;
/* 567 */         } else if (update && hasSequenceHardwareDependency() && this.hardware.get(i) != null) {
/* 568 */           SPSControllerVCI controller = getController(i);
/* 569 */           this.session.getVehicle().setVIT1(getVIT1(i));
/* 570 */           getData(i).update(controller.getHardware(), (SPSSchemaAdapterNAO)adapter);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean acceptHardwareSelection(String controllerID, SPSHardware part) throws Exception {
/* 577 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 578 */       SPSProgrammingData data = this.pdata.get(i);
/*     */       
/* 580 */       SPSControllerVCI controller = getController(i);
/* 581 */       if (match(data, part) && controller.getDescription().startsWith(controllerID + '\t')) {
/*     */ 
/*     */         
/* 584 */         List<SPSHardware> hardware = controller.getHardware();
/* 585 */         if (hardware != null) {
/* 586 */           for (int j = 0; j < hardware.size(); j++) {
/* 587 */             SPSHardware hw = hardware.get(j);
/* 588 */             if (hw.equals(part)) {
/* 589 */               return true;
/*     */             }
/*     */           } 
/*     */         } else {
/* 593 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/* 597 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean match(SPSProgrammingData data, SPSHardware part) {
/*     */     try {
/* 607 */       List<SPSHardwareIndex> hardware = data.getHardwareList();
/* 608 */       if (hardware != null) {
/* 609 */         for (int i = 0; i < hardware.size(); i++) {
/* 610 */           SPSHardwareIndex hwidx = hardware.get(i);
/* 611 */           if (hwidx.contains(part)) {
/* 612 */             return true;
/*     */           }
/*     */         } 
/*     */       }
/* 616 */     } catch (Exception x) {}
/*     */     
/* 618 */     return false;
/*     */   }
/*     */   
/*     */   public List getHardwareList() throws Exception {
/* 622 */     if (this.modules == null) {
/* 623 */       initModuleList();
/*     */     }
/* 625 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 626 */       SPSProgrammingData data = this.pdata.get(i);
/* 627 */       if (this.state.get(i).equals("constructed")) {
/* 628 */         List hardware = data.getHardwareList();
/* 629 */         if (hardware != null) {
/* 630 */           this.hwIDX = i;
/*     */ 
/*     */           
/* 633 */           this.hasSequenceHardwareDependency = hasSequenceHardwareDependency(hardware);
/* 634 */           return hardware;
/*     */         } 
/* 636 */         this.modules.set(i, data.getModules());
/* 637 */         this.state.set(i, "cop");
/*     */       } 
/*     */     } 
/*     */     
/* 641 */     return null;
/*     */   }
/*     */   
/*     */   protected void initModuleList() {
/* 645 */     this.modules = new ArrayList();
/* 646 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 647 */       this.modules.add(null);
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean hasConstraints(SPSSchemaAdapterNAO adapter) {
/* 652 */     return (StaticData.getInstance(adapter).getConstraints().get(Integer.valueOf(this.id)) != null);
/*     */   }
/*     */   
/*     */   public List filterConstraints(List hardware, SPSSchemaAdapterNAO adapter) {
/* 656 */     if (hasConstraints(adapter)) {
/* 657 */       List constraint = (List)StaticData.getInstance(adapter).getConstraints().get(Integer.valueOf(this.id));
/* 658 */       Iterator<SPSHardware> it = hardware.iterator();
/* 659 */       while (it.hasNext()) {
/* 660 */         SPSHardware hw = it.next();
/* 661 */         if (match(constraint, hw)) {
/* 662 */           it.remove();
/*     */         }
/*     */       } 
/*     */     } 
/* 666 */     return hardware;
/*     */   }
/*     */   
/*     */   protected boolean match(List<SPSPart> constraints, SPSHardware hardware) {
/* 670 */     for (int i = 0; i < constraints.size(); i++) {
/* 671 */       SPSPart part = constraints.get(i);
/* 672 */       if (part.equals(hardware)) {
/* 673 */         return true;
/*     */       }
/*     */     } 
/* 676 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isType4OnlySequence() {
/* 680 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 681 */       SPSControllerVCI controller = getController(i);
/* 682 */       if (controller.getDeviceID() != 253) {
/* 683 */         return false;
/*     */       }
/*     */     } 
/* 686 */     return true;
/*     */   }
/*     */   
/*     */   protected SPSProgrammingData buildProgrammingData(SPSSchemaAdapterNAO adapter) throws Exception {
/* 690 */     if (this.pdata != null) {
/* 691 */       return null;
/*     */     }
/* 693 */     this.pdata = new ArrayList();
/* 694 */     this.type4 = new ArrayList();
/* 695 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 696 */       SPSControllerVCI controller = getController(i);
/* 697 */       this.session.getVehicle().setVIT1(getVIT1(i));
/* 698 */       SPSProgrammingData data = controller.getProgrammingData(adapter);
/* 699 */       if (data == null) {
/* 700 */         throw new SPSException(CommonException.NoValidCOP);
/*     */       }
/* 702 */       if (controller.isSecurityCodeRequired()) {
/* 703 */         data.flagSecurityCodeRequired();
/*     */       }
/* 705 */       this.pdata.add(data);
/* 706 */       if (controller.getDeviceID() == 253) {
/* 707 */         SPSType4Data tdata = new SPSType4Data(this.session.getLanguage().getLocale(), adapter);
/*     */         
/* 709 */         if (controller instanceof SPSControllerXML) {
/* 710 */           SPSVehicle vehicle = (SPSVehicle)this.session.getVehicle();
/* 711 */           byte[] bytes = ((SPSControllerXML)controller).getBuildFile(vehicle).getBytes("ASCII");
/* 712 */           tdata.add((E)new PairImpl("BuildRecord.bld", bytes));
/* 713 */           String ECUPIN = adapter.determineECUPIN((SPSControllerXML)controller);
/* 714 */           tdata.add((E)new PairImpl("ECUPIN.TXT", ECUPIN.getBytes("ASCII")));
/*     */         } 
/*     */         
/* 717 */         PairImpl pairImpl = new PairImpl(Integer.valueOf(controller.getID()), new ListValueImpl(tdata));
/* 718 */         this.type4.add(pairImpl);
/*     */       } else {
/* 720 */         this.type4.add(null);
/*     */       } 
/* 722 */       this.state.set(i, "constructed");
/*     */     } 
/* 724 */     return null;
/*     */   }
/*     */   
/*     */   public void setOdometer(String reading) {
/* 728 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 729 */       getData(i).setOdometer(reading);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setKeycode(String keycode) {
/* 734 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 735 */       getData(i).setKeycode(keycode);
/*     */     }
/*     */   }
/*     */   
/*     */   protected List handleVIT1(SPSModel model, SPSSession session, AttributeValueMap data) throws Exception {
/* 740 */     checkMemberControllers();
/* 741 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 742 */       SPSControllerVCI controller = getController(i);
/* 743 */       if (this.state.get(i).equals("selected")) {
/* 744 */         if (controller.getDeviceID() == 253 && controller.getRequestMethodID() == 0) {
/* 745 */           this.vit1s.set(i, null);
/* 746 */           this.state.set(i, "vit1");
/*     */         } else {
/*     */           
/* 749 */           if (this.vit1s.get(i) == null) {
/* 750 */             int requestMethodID = controller.getRequestMethodID();
/* 751 */             int deviceID = controller.getDeviceID();
/* 752 */             this.vit1s.set(i, "selected");
/* 753 */             return model.getRequestMethodData(requestMethodID, deviceID);
/*     */           } 
/* 755 */           VIT1Data vit1 = (VIT1Data)AVUtil.accessValue(data, CommonAttribute.VIT1);
/* 756 */           if (vit1.getHWNumber() != null && vit1.getHWNumber().trim().length() > 0) {
/*     */             try {
/* 758 */               SPSHardware hardware = new SPSHardware(1, vit1.getHWNumber());
/* 759 */               controller.setHardware(hardware);
/* 760 */             } catch (Exception x) {}
/*     */           }
/*     */           
/* 763 */           this.vit1s.set(i, vit1);
/* 764 */           this.state.set(i, "vit1");
/*     */         } 
/*     */       }
/*     */     } 
/* 768 */     return null;
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
/* 779 */     checkMemberControllers();
/* 780 */     List rmd = new ArrayList();
/* 781 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 782 */       SPSControllerVCI controller = getController(i);
/* 783 */       int requestMethodID = controller.getRequestMethodID();
/* 784 */       int deviceID = controller.getDeviceID();
/* 785 */       List rdata = model.getRequestMethodData(requestMethodID, deviceID);
/* 786 */       rmd.addAll(rdata);
/*     */     } 
/* 788 */     return (rmd.size() > 0) ? rmd : null;
/*     */   }
/*     */   
/*     */   protected SPSProgrammingData getData(int i) {
/* 792 */     return this.pdata.get(i);
/*     */   }
/*     */   
/*     */   protected SPSControllerVCI getController(int i) {
/* 796 */     SPSControllerReference reference = this.controllers.get(i);
/* 797 */     return reference.getControllers().get(0);
/*     */   }
/*     */   
/*     */   protected VIT1Data getVIT1(int i) {
/* 801 */     return this.vit1s.get(i);
/*     */   }
/*     */   
/*     */   protected void checkMemberControllers() throws Exception {
/* 805 */     if (this.state == null) {
/* 806 */       this.state = new ArrayList();
/* 807 */       this.vit1s = new ArrayList();
/*     */     } 
/* 809 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 810 */       SPSControllerReference reference = this.controllers.get(i);
/* 811 */       List controllers = reference.getControllers();
/* 812 */       if (controllers == null || controllers.size() != 1)
/* 813 */         throw new SPSException(CommonException.UnsupportedController); 
/* 814 */       if (this.state.size() < this.controllers.size()) {
/* 815 */         this.state.add("selected");
/* 816 */         this.vit1s.add(null);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean qualify(List options, SPSSchemaAdapterNAO adapter) throws Exception {
/* 822 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 823 */       SPSControllerReference reference = this.controllers.get(i);
/* 824 */       reference.qualify(options, adapter);
/* 825 */       List controllers = reference.getControllers();
/* 826 */       if (controllers == null || controllers.size() == 0) {
/* 827 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 831 */     return true;
/*     */   }
/*     */   
/*     */   protected List collectPreSelectionOptions() throws Exception {
/* 835 */     List options = new ArrayList();
/* 836 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 837 */       SPSControllerReference reference = this.controllers.get(i);
/* 838 */       List preoptions = reference.getPreOptions();
/* 839 */       if (preoptions != null) {
/* 840 */         options.addAll(preoptions);
/*     */       }
/*     */     } 
/* 843 */     return (options.size() > 0) ? options : null;
/*     */   }
/*     */   
/*     */   protected List collectPostSelectionOptions() throws Exception {
/* 847 */     List options = new ArrayList();
/* 848 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 849 */       SPSControllerReference reference = this.controllers.get(i);
/* 850 */       List postoptions = reference.getPostOptions();
/* 851 */       if (postoptions != null) {
/* 852 */         options.addAll(postoptions);
/*     */       }
/*     */     } 
/* 855 */     return (options.size() > 0) ? options : null;
/*     */   }
/*     */   
/*     */   public void reset() {
/* 859 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 860 */       SPSControllerReference reference = this.controllers.get(i);
/* 861 */       reference.reset();
/*     */     } 
/* 863 */     this.state = null;
/* 864 */     this.vit1s = null;
/* 865 */     this.pdata = null;
/* 866 */     this.modules = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValid(SPSSession session, boolean modeReplaceReprogram, SPSControllerList controllers, SPSControllerList pscontrollers, SPSSchemaAdapterNAO adapter) {
/* 871 */     if (controllers == null || StaticData.getInstance(adapter).getSequences().size() == 0) {
/* 872 */       return false;
/*     */     }
/* 874 */     Iterator<Map.Entry> it = StaticData.getInstance(adapter).getSequences().entrySet().iterator();
/* 875 */     while (it.hasNext()) {
/* 876 */       Map.Entry entry = it.next();
/* 877 */       Integer id = (Integer)entry.getKey();
/* 878 */       if (id.intValue() != this.psid) {
/*     */         continue;
/*     */       }
/* 881 */       Pair sequence = (Pair)entry.getValue();
/* 882 */       List<ProgrammingSequenceMember> members = (List)sequence.getFirst();
/* 883 */       List<SPSControllerReference> references = new ArrayList();
/* 884 */       for (int i = 0; i < members.size() && id != null; i++) {
/* 885 */         ProgrammingSequenceMember member = members.get(i);
/* 886 */         if (modeReplaceReprogram && !member.replaceModeSupported) {
/* 887 */           return false;
/*     */         }
/* 889 */         SPSControllerReference reference = match(controllers, member.controller);
/* 890 */         if (reference == null) {
/* 891 */           reference = match(pscontrollers, member.controller);
/*     */         }
/* 893 */         if (reference != null) {
/* 894 */           references.add(reference);
/*     */         } else {
/* 896 */           id = null;
/*     */         } 
/*     */       } 
/* 899 */       if (id != null && references.size() > 0) {
/* 900 */         this.controllers = references;
/* 901 */         this.sequence = members;
/* 902 */         return true;
/*     */       } 
/*     */     } 
/* 905 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isSecurityCodeRequired() {
/* 909 */     for (int i = 0; i < this.controllers.size(); i++) {
/* 910 */       SPSControllerVCI controller = getController(i);
/* 911 */       if (controller.isSecurityCodeRequired()) {
/* 912 */         return true;
/*     */       }
/*     */     } 
/* 915 */     return false;
/*     */   }
/*     */   
/*     */   public String getReplacedVIN() {
/*     */     
/* 920 */     try { for (int i = 0; i < this.controllers.size(); i++) {
/* 921 */         SPSControllerVCI controller = getController(i);
/* 922 */         if (controller.isSecurityCodeRequired()) {
/* 923 */           VIT1Data vit1 = getVIT1(i);
/* 924 */           if (vit1 != null && vit1.getVIT().getAttrValue("vin") != null) {
/* 925 */             String vin = vit1.getVIT().getAttrValue("vin");
/* 926 */             if (vin != null && vin.trim().length() > 0) {
/* 927 */               return vin.trim();
/*     */             }
/*     */           } 
/*     */         } 
/*     */       }  }
/* 932 */     catch (Exception e) {  }
/* 933 */     catch (Throwable x) {}
/*     */     
/* 935 */     return null;
/*     */   }
/*     */   
/*     */   protected static SPSControllerReference match(SPSControllerList controllers, Integer controller) {
/* 939 */     Iterator<SPSControllerReference> it = controllers.iterator();
/* 940 */     while (it.hasNext()) {
/* 941 */       SPSControllerReference reference = it.next();
/* 942 */       if (reference.match(controller.intValue())) {
/* 943 */         return reference;
/*     */       }
/*     */     } 
/* 946 */     return null;
/*     */   }
/*     */   
/*     */   protected static String getDescription(SPSLanguage language, int id, int descIndex, SPSSchemaAdapterNAO adapter) {
/* 950 */     return SPSControllerVCI.getDescription(language, id, descIndex, adapter);
/*     */   }
/*     */   
/*     */   protected static String getLabel(int id, int descIndex, SPSSchemaAdapterNAO adapter) {
/* 954 */     return SPSControllerVCI.getLabel(id, descIndex, adapter);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 958 */     return getID();
/*     */   }
/*     */   
/*     */   @SuppressWarnings({"EQ_CHECK_FOR_OPERAND_NOT_COMPATIBLE_WITH_THIS"})
/*     */   public boolean equals(Object object) {
/* 963 */     if (object == null)
/* 964 */       return false; 
/* 965 */     if (object instanceof SPSController && ((SPSController)object).getID() == getID())
/* 966 */       return true; 
/* 967 */     if (object instanceof SPSControllerReference) {
/* 968 */       return ((SPSControllerReference)object).accept(this);
/*     */     }
/* 970 */     return false;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 974 */     return this.label + " (id=" + this.id + ",ps=" + this.psid + ")";
/*     */   }
/*     */   
/*     */   static void init(SPSSchemaAdapterNAO adapter) throws Exception {
/* 978 */     StaticData.getInstance(adapter).init();
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


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\nao\SPSProgrammingSequence.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */