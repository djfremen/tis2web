/*      */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*      */ 
/*      */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*      */ import com.eoos.gm.tis2web.sps.common.RequestBuilder;
/*      */ import com.eoos.gm.tis2web.sps.common.VIT1;
/*      */ import com.eoos.gm.tis2web.sps.common.VIT1Data;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.SPSException;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Archive;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingDataUnit;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Summary;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.AVUtil;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonException;
/*      */ import com.eoos.gm.tis2web.sps.common.impl.VIT1DataImpl;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSControllerFunction;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSControllerSequence;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSPart;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSProgrammingData;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSSchemaAdapter;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSSession;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.xml.XMLSupport;
/*      */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*      */ import com.eoos.gm.tis2web.sps.service.cai.RequestException;
/*      */ import java.sql.Connection;
/*      */ import java.sql.ResultSet;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.StringTokenizer;
/*      */ import org.apache.log4j.Logger;
/*      */ 
/*      */ public class SPSControllerSequenceGME
/*      */   extends SPSControllerGME implements SPSControllerSequence {
/*      */   private static final long serialVersionUID = 1L;
/*   40 */   private static Logger log = Logger.getLogger(SPSModel.class);
/*      */   
/*      */   private static final boolean TEST_MODE = false;
/*      */   
/*      */   private Integer sequenceID;
/*      */   
/*      */   private String display;
/*      */   
/*      */   private Integer displayOrder;
/*      */   private transient Integer baseVehicleID;
/*      */   private transient Integer vehicleID;
/*      */   private transient Integer deviceID;
/*      */   private transient Integer ecuID;
/*      */   private transient List options;
/*      */   private transient SPSControllerList references;
/*      */   private transient List functions;
/*      */   private transient SPSControllerGME function;
/*      */   private transient VIT1Data vit1;
/*      */   private transient List pdata;
/*      */   
/*      */   public void checkFunctionOperation() {
/*   61 */     if (this.function == null) {
/*   62 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean checkAsBuiltController(int ecu) {
/*   68 */     if (this.function != null) {
/*   69 */       int position = -1;
/*   70 */       for (int i = 0; i < this.functions.size(); i++) {
/*   71 */         if (this.function == this.functions.get(i)) {
/*   72 */           position = i;
/*      */           break;
/*      */         } 
/*      */       } 
/*   76 */       if (position >= 0) {
/*   77 */         SPSControllerReference reference = (SPSControllerReference)this.references.get(position);
/*   78 */         if (reference.isAsBuiltController(ecu)) {
/*   79 */           return true;
/*      */         }
/*      */       } 
/*   82 */       return false;
/*      */     } 
/*   84 */     for (int j = 0; j < this.references.size(); j++) {
/*   85 */       SPSControllerReference reference = (SPSControllerReference)this.references.get(j);
/*   86 */       if (reference.isAsBuiltController(ecu)) {
/*   87 */         return true;
/*      */       }
/*      */     } 
/*   90 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean checkHardware() throws Exception {
/*   95 */     initSequence();
/*      */     while (true) {
/*   97 */       if (!this.function.checkHardware()) {
/*   98 */         return false;
/*      */       }
/*  100 */       if (!advanceSequence())
/*  101 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean equals(Object object) {
/*  106 */     if (object instanceof SPSControllerSequenceGME) {
/*  107 */       return this.sequenceID.equals(((SPSControllerSequenceGME)object).getSequenceID());
/*      */     }
/*  109 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void flagAsBuiltController() {
/*  115 */     checkFunctionOperation();
/*  116 */     this.function.flagAsBuiltController();
/*      */   }
/*      */ 
/*      */   
/*      */   public Archive getArchive() {
/*  121 */     checkFunctionOperation();
/*  122 */     return this.function.getArchive();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getControllerID() {
/*  127 */     if (this.function == null) {
/*  128 */       return super.getControllerID();
/*      */     }
/*  130 */     return this.function.getControllerID();
/*      */   }
/*      */ 
/*      */   
/*      */   SPSSoftware getCurrentSoftware() {
/*  135 */     checkFunctionOperation();
/*  136 */     return this.function.getCurrentSoftware();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getDeviceID() {
/*  141 */     checkFunctionOperation();
/*  142 */     return this.function.getDeviceID();
/*      */   }
/*      */ 
/*      */   
/*      */   public List getHardware() {
/*  147 */     checkFunctionOperation();
/*  148 */     return this.function.getHardware();
/*      */   }
/*      */ 
/*      */   
/*      */   List getHistory() throws Exception {
/*  153 */     checkFunctionOperation();
/*  154 */     return this.function.getHistory();
/*      */   }
/*      */ 
/*      */   
/*      */   public List getPostProgrammingInstructions() {
/*  159 */     SPSControllerReference reference = (SPSControllerReference)this.references.get(this.references.size() - 1);
/*  160 */     SPSControllerGME controller = reference.getSelectedController();
/*  161 */     if (controller.getPostProgrammingInstructions() != null) {
/*  162 */       return controller.getPostProgrammingInstructions();
/*      */     }
/*  164 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public List getPreProgrammingInstructions() {
/*  169 */     SPSControllerReference reference = (SPSControllerReference)this.references.get(0);
/*  170 */     SPSControllerGME controller = reference.getSelectedController();
/*  171 */     if (controller.getPreProgrammingInstructions() != null) {
/*  172 */       return controller.getPreProgrammingInstructions();
/*      */     }
/*  174 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public SPSProgrammingData getProgrammingData(SPSSchemaAdapter adapter) throws Exception {
/*  179 */     if (this.pdata != null) {
/*  180 */       return null;
/*      */     }
/*  182 */     this.pdata = new ArrayList();
/*  183 */     for (int j = 0; j < this.references.size(); j++) {
/*  184 */       if (needsSwitchVIT1()) {
/*  185 */         SPSControllerGME sfunction = this.functions.get(j);
/*  186 */         this.session.getVehicle().setVIT1((VIT1Data)sfunction.getControllerData());
/*      */       } 
/*  188 */       SPSControllerReference reference = (SPSControllerReference)this.references.get(j);
/*  189 */       SPSControllerGME controller = reference.getSelectedController();
/*  190 */       SPSProgrammingData data = (SPSProgrammingData)controller.getProgrammingData(adapter);
/*  191 */       if (data != null) {
/*  192 */         ((SPSSession)this.session).checkACLResourceNoHWK(data);
/*  193 */         this.pdata.add(data);
/*      */       } else {
/*  195 */         throw new SPSException(CommonException.NoValidCOP);
/*      */       } 
/*      */     } 
/*  198 */     if (needsSwitchVIT1()) {
/*  199 */       this.session.getVehicle().setVIT1(this.vit1);
/*      */     }
/*  201 */     return null;
/*      */   }
/*      */   
/*      */   public String getBuildFile() {
/*  205 */     String result = null;
/*  206 */     for (int j = 0; j < this.references.size(); j++) {
/*  207 */       SPSControllerReference reference = (SPSControllerReference)this.references.get(j);
/*  208 */       SPSControllerGME controller = reference.getSelectedController();
/*  209 */       if (controller instanceof SPSControllerXML) {
/*  210 */         String file = ((SPSControllerXML)controller).getBuildFile();
/*  211 */         if (result == null) {
/*  212 */           result = file;
/*      */         } else {
/*  214 */           result = mergeRPO(result, file);
/*      */         } 
/*      */       } 
/*      */     } 
/*  218 */     return result;
/*      */   }
/*      */   
/*      */   protected String mergeRPO(String buildfile1, String buildfile2) {
/*  222 */     StringBuffer buildfile = new StringBuffer();
/*  223 */     HashSet<String> rpo = new HashSet();
/*  224 */     StringTokenizer tokenizer = new StringTokenizer(buildfile1);
/*  225 */     int count = 0;
/*  226 */     while (tokenizer.hasMoreTokens()) {
/*  227 */       String token = tokenizer.nextToken();
/*  228 */       if (count++ > 1) {
/*  229 */         rpo.add(token); continue;
/*      */       } 
/*  231 */       buildfile.append(token + "\r\n");
/*      */     } 
/*      */     
/*  234 */     tokenizer = new StringTokenizer(buildfile2);
/*  235 */     count = 0;
/*  236 */     while (tokenizer.hasMoreTokens()) {
/*  237 */       String token = tokenizer.nextToken();
/*  238 */       if (count++ > 1) {
/*  239 */         rpo.add(token);
/*      */       }
/*      */     } 
/*  242 */     Iterator<String> it = rpo.iterator();
/*  243 */     count = 0;
/*  244 */     while (it.hasNext()) {
/*  245 */       if (count++ > 0) {
/*  246 */         buildfile.append("\r\n");
/*      */       }
/*  248 */       buildfile.append(it.next());
/*      */     } 
/*  250 */     return buildfile.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getRequestMethodID() {
/*  255 */     checkFunctionOperation();
/*  256 */     return this.function.getRequestMethodID();
/*      */   }
/*      */ 
/*      */   
/*      */   SPSSoftware getSoftware() throws Exception {
/*  261 */     checkFunctionOperation();
/*  262 */     return this.function.getSoftware();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getSystemTypeID() {
/*  267 */     checkFunctionOperation();
/*  268 */     return this.function.getSystemTypeID();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getVCI() {
/*  273 */     checkFunctionOperation();
/*  274 */     return this.function.getVCI();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getVehicleID() {
/*  279 */     checkFunctionOperation();
/*  280 */     return this.function.getVehicleID();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getVINID() {
/*  285 */     checkFunctionOperation();
/*  286 */     return this.function.getVINID();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasAsBuiltSupport(int device) {
/*  291 */     if (this.function != null) {
/*  292 */       int position = -1;
/*  293 */       for (int i = 0; i < this.functions.size(); i++) {
/*  294 */         if (this.function == this.functions.get(i)) {
/*  295 */           position = i;
/*      */           break;
/*      */         } 
/*      */       } 
/*  299 */       if (position >= 0) {
/*  300 */         SPSControllerReference reference = (SPSControllerReference)this.references.get(position);
/*  301 */         if (reference.hasAsBuiltSupport(device)) {
/*  302 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/*  306 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAsBuiltController() {
/*  311 */     if (this.function != null) {
/*  312 */       int position = -1;
/*  313 */       for (int i = 0; i < this.functions.size(); i++) {
/*  314 */         if (this.function == this.functions.get(i)) {
/*  315 */           position = i;
/*      */           break;
/*      */         } 
/*      */       } 
/*  319 */       if (position >= 0) {
/*  320 */         SPSControllerReference reference = (SPSControllerReference)this.references.get(position);
/*  321 */         reference.qualifyAsBuiltController();
/*      */       } 
/*      */     } 
/*  324 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAsBuiltController(int ecu) {
/*  329 */     if (this.function != null) {
/*  330 */       int position = -1;
/*  331 */       for (int i = 0; i < this.functions.size(); i++) {
/*  332 */         if (this.function == this.functions.get(i)) {
/*  333 */           position = i;
/*      */           break;
/*      */         } 
/*      */       } 
/*  337 */       if (position >= 0) {
/*  338 */         SPSControllerReference reference = (SPSControllerReference)this.references.get(position);
/*  339 */         if (reference.isAsBuiltController(ecu)) {
/*  340 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/*  344 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean match(SPSControllerGME controller) {
/*  349 */     checkFunctionOperation();
/*  350 */     return this.function.match(controller);
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean matchHardware(SPSControllerGME controller) {
/*  355 */     checkFunctionOperation();
/*  356 */     return this.function.matchHardware(controller);
/*      */   }
/*      */ 
/*      */   
/*      */   boolean qualify(int order) throws Exception {
/*  361 */     if (this.references == null) {
/*  362 */       return qualifySequence(order);
/*      */     }
/*  364 */     for (int j = 0; j < this.references.size(); j++) {
/*  365 */       if (needsSwitchVIT1()) {
/*  366 */         SPSControllerGME sfunction = this.functions.get(j);
/*  367 */         this.session.getVehicle().setVIT1((VIT1Data)sfunction.getControllerData());
/*      */       } 
/*  369 */       SPSControllerReference reference = (SPSControllerReference)this.references.get(j);
/*      */       try {
/*  371 */         reference.qualify(this.session.getVehicle().getOptions(), this.adapter);
/*  372 */       } catch (SPSException s) {
/*  373 */         return false;
/*      */       } 
/*  375 */       if (reference.getControllers().size() == 0) {
/*  376 */         return false;
/*      */       }
/*      */     } 
/*  379 */     if (needsSwitchVIT1()) {
/*  380 */       this.session.getVehicle().setVIT1(this.vit1);
/*      */     }
/*  382 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected List queryPostProgrammingInstruction() throws Exception {
/*  388 */     checkFunctionOperation();
/*  389 */     return this.function.queryPostProgrammingInstruction();
/*      */   }
/*      */ 
/*      */   
/*      */   protected List queryPreProgrammingInstruction() throws Exception {
/*  394 */     checkFunctionOperation();
/*  395 */     return this.function.queryPreProgrammingInstruction();
/*      */   }
/*      */ 
/*      */   
/*      */   void register(SPSControllerGME predecessor) {
/*  400 */     checkFunctionOperation();
/*  401 */     this.function.register(predecessor);
/*      */   }
/*      */ 
/*      */   
/*      */   void register(SPSVehicleCategory vcategory) {
/*  406 */     checkFunctionOperation();
/*  407 */     this.function.register(vcategory);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void requestHardwareSelection(List hardware) throws Exception {
/*  412 */     checkFunctionOperation();
/*  413 */     this.function.requestHardwareSelection(hardware);
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
/*      */   public void setArchive(Archive archive) {
/*  425 */     checkFunctionOperation();
/*  426 */     this.function.setArchive(archive);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public List getPreSelectionOptions() throws Exception {
/*  432 */     return getSequenceOptions();
/*      */   }
/*      */   
/*      */   public void setControllerData(Object data) {
/*  436 */     this.function.setControllerData(data);
/*      */   }
/*      */ 
/*      */   
/*      */   void setCurrentSoftware(SPSSoftware current) {
/*  441 */     checkFunctionOperation();
/*  442 */     this.function.setCurrentSoftware(current);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setHardware(SPSPart part) {
/*  447 */     for (int j = 0; j < this.references.size(); j++) {
/*  448 */       SPSControllerReference reference = (SPSControllerReference)this.references.get(j);
/*  449 */       List<SPSControllerGME> controllers = reference.getControllers();
/*  450 */       for (int i = 0; i < controllers.size(); i++) {
/*  451 */         SPSControllerGME controller = controllers.get(i);
/*  452 */         controller.setHardware(part);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setVCI(int vci) {
/*  459 */     checkFunctionOperation();
/*  460 */     this.function.setVCI(vci);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setVINID(int vinid) {
/*  465 */     checkFunctionOperation();
/*  466 */     this.function.setVINID(vinid);
/*      */   }
/*      */ 
/*      */   
/*      */   public void update(SPSSchemaAdapter adapter) throws Exception {
/*  471 */     checkFunctionOperation();
/*  472 */     this.function.update(adapter);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean needsSwitchVIT1() {
/*  478 */     return (this.vit1 != null || this.session.getVehicle().getVIT1().getVIT() instanceof VIT1);
/*      */   }
/*      */   
/*      */   public void initSequence() {
/*  482 */     getFunctions();
/*  483 */     this.function = this.functions.get(0);
/*  484 */     if (needsSwitchVIT1()) {
/*  485 */       if (this.vit1 == null) {
/*  486 */         this.vit1 = this.session.getVehicle().getVIT1();
/*  487 */         Iterator<SPSControllerGME> it = this.functions.iterator();
/*  488 */         while (it.hasNext()) {
/*  489 */           SPSControllerGME sfunction = it.next();
/*  490 */           VIT1DataImpl vIT1DataImpl = new VIT1DataImpl(sfunction.getSystemTypeID(), (VIT1)this.vit1.getVIT());
/*  491 */           sfunction.setControllerData(vIT1DataImpl);
/*      */         } 
/*      */       } 
/*  494 */       this.session.getVehicle().setVIT1((VIT1Data)this.function.getControllerData());
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean advanceSequence() {
/*  499 */     if (this.function == null) {
/*  500 */       throw new UnsupportedOperationException();
/*      */     }
/*  502 */     Iterator<SPSControllerGME> it = this.functions.iterator();
/*  503 */     while (it.hasNext()) {
/*  504 */       if (this.function == null) {
/*  505 */         this.function = it.next();
/*  506 */         if (needsSwitchVIT1()) {
/*  507 */           this.session.getVehicle().setVIT1((VIT1Data)this.function.getControllerData());
/*      */         }
/*  509 */         return true;
/*  510 */       }  if (this.function == it.next()) {
/*  511 */         this.function = null;
/*      */       }
/*      */     } 
/*  514 */     if (needsSwitchVIT1()) {
/*  515 */       this.session.getVehicle().setVIT1(this.vit1);
/*      */     }
/*  517 */     return false;
/*      */   }
/*      */   
/*      */   public Integer getSequenceID() {
/*  521 */     return this.sequenceID;
/*      */   }
/*      */   
/*      */   public Integer getBaseVehicleID() {
/*  525 */     return this.baseVehicleID;
/*      */   }
/*      */   
/*      */   public String getDisplay() {
/*  529 */     return this.display;
/*      */   }
/*      */   
/*      */   public Integer getDisplayOrder() {
/*  533 */     return this.displayOrder;
/*      */   }
/*      */   
/*      */   public int getID() {
/*  537 */     if (this.ecu == null && this.functions != null) {
/*  538 */       for (int i = 0; i < this.functions.size(); i++) {
/*  539 */         SPSControllerGME function = this.functions.get(i);
/*  540 */         if (function.getControllerID().equals(super.getControllerID())) {
/*  541 */           this.ecu = new Integer(function.getID());
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     }
/*  546 */     if (this.ecu != null) {
/*  547 */       return super.getID();
/*      */     }
/*  549 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */   
/*      */   public SPSControllerSequenceGME(SPSSession session, Integer baseVehicleID, Integer id, Integer sequenceID, SPSSchemaAdapterGME adapter) {
/*  554 */     super(session, null, id, null, null, adapter);
/*  555 */     this.sequenceID = sequenceID;
/*  556 */     this.baseVehicleID = baseVehicleID;
/*  557 */     SPSSequenceDisplay display = getSequenceDescription(sequenceID, adapter);
/*  558 */     this.display = display.getDescription((SPSLanguage)session.getLanguage());
/*  559 */     this.displayOrder = display.getDisplayOrder((SPSLanguage)session.getLanguage());
/*      */   }
/*      */   
/*      */   public List getFunctions() {
/*  563 */     if (this.functions == null) {
/*  564 */       this.functions = new ArrayList();
/*  565 */       this.references = new SPSControllerList(this.session);
/*  566 */       List<?> flist = getSequence(this.sequenceID, this.adapter).getFunctions();
/*  567 */       Collections.sort(flist, new Comparator() {
/*      */             public int compare(Object o1, Object o2) {
/*  569 */               Integer order1 = ((SPSSequenceFunction)o1).getFunctionOrder();
/*  570 */               Integer order2 = ((SPSSequenceFunction)o2).getFunctionOrder();
/*  571 */               return order1.compareTo(order2);
/*      */             }
/*      */           });
/*  574 */       Map xml = null;
/*  575 */       IDatabaseLink dblink = this.adapter.getDatabaseLink();
/*  576 */       Connection conn = null;
/*      */       try {
/*  578 */         conn = dblink.requestConnection();
/*  579 */         for (int i = 0; i < flist.size(); i++) {
/*  580 */           SPSSequenceFunction function = (SPSSequenceFunction)flist.get(i);
/*  581 */           SPSControllerReference reference = new SPSControllerReference(this.session, this, function);
/*  582 */           this.references.add(reference);
/*  583 */           Integer vinid = ((SPSSession)this.session).getAsBuiltVINID();
/*  584 */           if (!vinid.equals(SPSModel.NO_ASBUILT_VINID)) {
/*  585 */             evaluateSPSAsBuiltVehicle(xml, dblink, conn, vinid, function.getFunctionID(), function, reference);
/*      */           } else {
/*  587 */             evaluateSPSVehicle(xml, dblink, conn, function.getFunctionID(), function, reference);
/*      */           } 
/*  589 */           SPSControllerGME controller = null;
/*  590 */           if (hasXMLSupport(xml, this.adapter, dblink, conn, function.getFunctionID())) {
/*  591 */             controller = new SPSControllerFunctionXML(this.session, null, function.getControllerID(), function.getFunctionID(), this.deviceID, null, this.adapter);
/*  592 */             ((SPSControllerFunctionXML)controller).setRequestInfoID(function.getRequestInfoID());
/*  593 */             ((SPSControllerFunctionXML)controller).setOnSameSW(function.getOnSameSW());
/*      */           } else {
/*  595 */             controller = new SPSControllerFunctionGME(this.session, null, function.getControllerID(), function.getFunctionID(), this.deviceID, null, this.adapter);
/*  596 */             ((SPSControllerFunctionGME)controller).setRequestInfoID(function.getRequestInfoID());
/*  597 */             ((SPSControllerFunctionGME)controller).setOnSameSW(function.getOnSameSW());
/*      */           } 
/*  599 */           this.functions.add(controller);
/*      */         } 
/*  601 */         SPSModel.getInstance(this.adapter).prepareControllerList(this.session, this.references);
/*  602 */       } catch (Exception e) {
/*  603 */         throw new RuntimeException(e);
/*      */       } finally {
/*      */         try {
/*  606 */           if (conn != null) {
/*  607 */             dblink.releaseConnection(conn);
/*      */           }
/*  609 */         } catch (Exception x) {
/*  610 */           log.error("failed to clean-up: ", x);
/*      */         } 
/*      */       } 
/*      */     } 
/*  614 */     return this.functions;
/*      */   }
/*      */   
/*      */   private void evaluateSPSAsBuiltVehicle(Map xml, IDatabaseLink dblink, Connection conn, Integer vinid, Integer functionID, SPSSequenceFunction function, SPSControllerReference reference) {
/*  618 */     DBMS.PreparedStatement stmt = null;
/*  619 */     ResultSet rs = null;
/*      */     try {
/*  621 */       String sql = DBMS.getSQL(dblink, "SELECT v.VehicleID, s.DeviceID, s.VCI, c.ECUID FROM SPS_AsBuilt_Vehicle v left outer join SPS_Configuration c on v.VehicleID = c.VehicleID, SPS_AsBuilt_VCI s WHERE s.VINID = ? AND s.VCI = v.VCI AND s.FunctionID = ?");
/*  622 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/*  623 */       stmt.setInt(1, vinid.intValue());
/*  624 */       stmt.setInt(2, functionID.intValue());
/*  625 */       this.deviceID = null;
/*  626 */       rs = stmt.executeQuery();
/*  627 */       while (rs.next()) {
/*  628 */         Integer vehicleID = new Integer(rs.getInt(1));
/*  629 */         this.deviceID = new Integer(rs.getInt(2));
/*  630 */         Integer vci = new Integer(rs.getInt(3));
/*  631 */         Integer ecu = new Integer(rs.getInt(4));
/*  632 */         SPSControllerGME controller = null;
/*  633 */         if (XMLSupport.hasXMLSupport(this.adapter, dblink, conn, functionID.intValue())) {
/*  634 */           controller = new SPSControllerFunctionXML(this.session, vehicleID, functionID, this.deviceID, ecu, this.adapter);
/*  635 */           ((SPSControllerFunctionXML)controller).setRequestInfoID(function.getRequestInfoID());
/*  636 */           ((SPSControllerFunctionXML)controller).setOnSameSW(function.getOnSameSW());
/*      */         } else {
/*  638 */           controller = new SPSControllerFunctionGME(this.session, vehicleID, functionID, this.deviceID, ecu, this.adapter);
/*  639 */           ((SPSControllerFunctionGME)controller).setRequestInfoID(function.getRequestInfoID());
/*  640 */           ((SPSControllerFunctionGME)controller).setOnSameSW(function.getOnSameSW());
/*      */         } 
/*  642 */         controller.flagAsBuiltController();
/*  643 */         controller.setVINID(vinid.intValue());
/*  644 */         controller.setVCI(vci.intValue());
/*  645 */         reference.add(controller, this.adapter);
/*      */       } 
/*  647 */       if (this.deviceID == null) {
/*  648 */         evaluateSPSVehicle(xml, dblink, conn, function.getFunctionID(), function, reference);
/*      */       }
/*  650 */     } catch (RuntimeException e) {
/*  651 */       throw e;
/*  652 */     } catch (Exception e) {
/*  653 */       throw new RuntimeException(e);
/*      */     } finally {
/*      */       try {
/*  656 */         if (rs != null) {
/*  657 */           rs.close();
/*      */         }
/*  659 */         if (stmt != null) {
/*  660 */           stmt.close();
/*      */         }
/*  662 */       } catch (Exception x) {
/*  663 */         log.error("failed to clean-up: ", x);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void evaluateSPSVehicle(Map xml, IDatabaseLink dblink, Connection conn, Integer functionID, SPSSequenceFunction function, SPSControllerReference reference) {
/*  669 */     SPSVehicle vehicle = (SPSVehicle)this.session.getVehicle();
/*  670 */     DBMS.PreparedStatement stmt = null;
/*  671 */     ResultSet rs = null;
/*      */     try {
/*  673 */       String sql = DBMS.getSQL(dblink, "SELECT v.VehicleID, v.DeviceID, c.ECUID FROM SPS_Configuration c right outer join SPS_Vehicle v on v.VehicleID = c.VehicleID WHERE v.SalesMakeCode = ?  AND v.ModelCode = ?  AND v.ModelYearCode = ? AND v.FunctionID = ?");
/*  674 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/*  675 */       stmt.setInt(1, vehicle.getSalesMakeID().intValue());
/*  676 */       stmt.setInt(2, vehicle.getModelID().intValue());
/*  677 */       stmt.setInt(3, vehicle.getModelYearID().intValue());
/*  678 */       stmt.setInt(4, functionID.intValue());
/*  679 */       rs = stmt.executeQuery();
/*  680 */       while (rs.next()) {
/*  681 */         this.vehicleID = new Integer(rs.getInt(1));
/*  682 */         this.deviceID = new Integer(rs.getInt(2));
/*  683 */         this.ecuID = new Integer(rs.getInt(3));
/*  684 */         if (rs.wasNull()) {
/*      */           continue;
/*      */         }
/*  687 */         SPSControllerGME controller = null;
/*  688 */         if (hasXMLSupport(xml, this.adapter, dblink, conn, function.getFunctionID())) {
/*  689 */           controller = new SPSControllerFunctionXML(this.session, this.vehicleID, function.getControllerID(), function.getFunctionID(), this.deviceID, this.ecuID, this.adapter);
/*  690 */           ((SPSControllerFunctionXML)controller).setRequestInfoID(function.getRequestInfoID());
/*  691 */           ((SPSControllerFunctionXML)controller).setOnSameSW(function.getOnSameSW());
/*      */         } else {
/*  693 */           controller = new SPSControllerFunctionGME(this.session, this.vehicleID, function.getControllerID(), function.getFunctionID(), this.deviceID, this.ecuID, this.adapter);
/*  694 */           ((SPSControllerFunctionGME)controller).setRequestInfoID(function.getRequestInfoID());
/*  695 */           ((SPSControllerFunctionGME)controller).setOnSameSW(function.getOnSameSW());
/*      */         } 
/*  697 */         reference.add(controller, this.adapter);
/*      */       } 
/*  699 */     } catch (RuntimeException e) {
/*  700 */       throw e;
/*  701 */     } catch (Exception e) {
/*  702 */       throw new RuntimeException(e);
/*      */     } finally {
/*      */       try {
/*  705 */         if (rs != null) {
/*  706 */           rs.close();
/*      */         }
/*  708 */         if (stmt != null) {
/*  709 */           stmt.close();
/*      */         }
/*  711 */       } catch (Exception x) {
/*  712 */         log.error("failed to clean-up: ", x);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean hasXMLSupport(Map xml, SPSSchemaAdapterGME adapter, IDatabaseLink dblink, Connection conn, Integer functionID) throws Exception {
/*  718 */     return XMLSupport.hasXMLSupport(adapter, dblink, conn, functionID.intValue());
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
/*      */   public boolean checkQualifiedSequence() throws Exception {
/*  731 */     for (int j = 0; j < this.references.size(); j++) {
/*  732 */       if (needsSwitchVIT1()) {
/*  733 */         SPSControllerGME sfunction = this.functions.get(j);
/*  734 */         this.session.getVehicle().setVIT1((VIT1Data)sfunction.getControllerData());
/*      */       } 
/*  736 */       SPSControllerReference reference = (SPSControllerReference)this.references.get(j);
/*      */       try {
/*  738 */         if (reference.check() == null) {
/*  739 */           return false;
/*      */         }
/*  741 */       } catch (SPSException s) {
/*  742 */         return false;
/*      */       } 
/*      */     } 
/*  745 */     if (needsSwitchVIT1()) {
/*  746 */       this.session.getVehicle().setVIT1(this.vit1);
/*      */     }
/*  748 */     return true;
/*      */   }
/*      */   
/*      */   public boolean qualifySequence(int order) throws Exception {
/*  752 */     return SPSVehicleCategory.qualify(this.session, this, this.options, order);
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
/*      */   protected List getSequenceOptions() throws Exception {
/*  765 */     if (this.options == null) {
/*  766 */       return null;
/*      */     }
/*  768 */     List<SPSOptionCategory> result = new ArrayList();
/*  769 */     for (int i = 0; i < this.options.size(); i++) {
/*  770 */       SPSBaseCategory bcategory = this.options.get(i);
/*  771 */       if (!bcategory.isEvaluated()) {
/*  772 */         result.add(bcategory.getCategory());
/*      */       }
/*      */     } 
/*  775 */     return result;
/*      */   }
/*      */   
/*      */   public List getPostSelectionOptions() throws Exception {
/*  779 */     List options = new ArrayList();
/*  780 */     for (int j = 0; j < this.references.size(); j++) {
/*  781 */       SPSControllerReference reference = (SPSControllerReference)this.references.get(j);
/*  782 */       options.addAll(reference.collectPostOptions());
/*      */     } 
/*  784 */     return options;
/*      */   }
/*      */   
/*      */   public List getOptionCategories() throws Exception {
/*  788 */     List categories = new ArrayList();
/*  789 */     if (this.options != null) {
/*  790 */       categories.addAll(this.options);
/*      */     }
/*  792 */     for (int j = 0; j < this.references.size(); j++) {
/*  793 */       SPSControllerReference reference = (SPSControllerReference)this.references.get(j);
/*  794 */       List<SPSControllerGME> controllers = reference.getControllers();
/*  795 */       for (int i = 0; i < controllers.size(); i++) {
/*  796 */         SPSControllerGME controller = controllers.get(i);
/*  797 */         List vgategories = controller.getOptionCategories();
/*  798 */         if (vgategories != null) {
/*  799 */           categories.addAll(vgategories);
/*      */         }
/*      */       } 
/*      */     } 
/*  803 */     return categories;
/*      */   }
/*      */   
/*      */   public void add(SPSBaseCategory bcategory) {
/*  807 */     if (this.options == null) {
/*  808 */       this.options = new ArrayList();
/*      */     }
/*  810 */     this.options.add(bcategory);
/*      */   }
/*      */   
/*      */   public boolean supportsVCI() {
/*  814 */     SPSSequence sequence = getSequence(this.sequenceID, this.adapter);
/*  815 */     return (sequence.getFunctions().size() == 1);
/*      */   }
/*      */   
/*      */   public boolean isSecurityCodeRequired(SPSSchemaAdapterGME adapter) throws Exception {
/*  819 */     for (int j = 0; j < this.references.size(); j++) {
/*  820 */       SPSControllerReference reference = (SPSControllerReference)this.references.get(j);
/*  821 */       SPSControllerGME controller = reference.getSelectedController();
/*  822 */       if (SPSSecurityCode.getInstance(adapter).isSecurityCodeRequired(controller.getVehicleID())) {
/*  823 */         return true;
/*      */       }
/*      */     } 
/*  826 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isSecurityEnforced(SPSModel model) {
/*  830 */     if (isTestMode())
/*  831 */       return false; 
/*  832 */     for (int j = 0; j < this.references.size(); j++) {
/*  833 */       SPSControllerReference reference = (SPSControllerReference)this.references.get(j);
/*  834 */       SPSControllerGME controller = reference.getSelectedController();
/*  835 */       if (model.isSecurityEnforced(controller.getVehicleID())) {
/*  836 */         return true;
/*      */       }
/*      */     } 
/*  839 */     return false;
/*      */   }
/*      */   
/*      */   public void setCurrentSoftware(SPSSchemaAdapterGME adapter, SPSModel model) throws Exception {
/*  843 */     for (int j = 0; j < this.references.size(); j++) {
/*  844 */       if (needsSwitchVIT1()) {
/*  845 */         SPSControllerGME sfunction = this.functions.get(j);
/*  846 */         this.session.getVehicle().setVIT1((VIT1Data)sfunction.getControllerData());
/*      */       } 
/*  848 */       SPSControllerReference reference = (SPSControllerReference)this.references.get(j);
/*  849 */       SPSControllerGME controller = reference.getSelectedController();
/*  850 */       if (controller.getCurrentSoftware() == null) {
/*  851 */         SPSSoftware current = null;
/*  852 */         VIT1Data vit1 = this.session.getVehicle().getVIT1();
/*  853 */         if (vit1.getECU() > 0) {
/*  854 */           current = SPSSoftware.load((SPSLanguage)this.session.getLanguage(), vit1, vit1.getECU(), adapter);
/*      */         } else {
/*  856 */           List identTypeList = model.getIdentTypeList((SPSVehicle)this.session.getVehicle(), vit1, false);
/*  857 */           current = SPSSoftware.load((SPSLanguage)this.session.getLanguage(), vit1, identTypeList, adapter);
/*      */         } 
/*  859 */         if (current != null) {
/*  860 */           controller.setCurrentSoftware(current);
/*      */         }
/*      */       } 
/*      */     } 
/*  864 */     if (needsSwitchVIT1()) {
/*  865 */       this.session.getVehicle().setVIT1(this.vit1);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean isTestMode() {
/*  870 */     return false;
/*      */   }
/*      */   
/*      */   public void checkSecurity(SPSSchemaAdapterGME adapter, AttributeValueMap data, RequestBuilder builder) throws SPSException, Exception {
/*  874 */     if (isTestMode())
/*      */       return; 
/*  876 */     String hwk = (String)AVUtil.accessValue(data, CommonAttribute.HARDWARE_KEY32);
/*  877 */     if (hwk == null) {
/*  878 */       throw new RequestException(builder.makeHardwareKeyRequest());
/*      */     }
/*  880 */     if (hwk != null) {
/*  881 */       for (int i = 0; i < this.references.size(); i++) {
/*  882 */         SPSControllerReference reference = (SPSControllerReference)this.references.get(i);
/*  883 */         SPSControllerGME controller = reference.getSelectedController();
/*  884 */         if (!SPSSecurity.getInstance(adapter).check(hwk, controller.getID())) {
/*  885 */           log.info("Exception: No Authorization");
/*  886 */           throw new SPSException(CommonException.NoAuthorization);
/*      */         } 
/*      */       } 
/*      */     }
/*  890 */     boolean skipOptionCheck = false;
/*  891 */     for (int j = 0; j < this.references.size(); j++) {
/*  892 */       SPSControllerReference reference = (SPSControllerReference)this.references.get(j);
/*  893 */       SPSControllerGME controller = reference.getSelectedController();
/*  894 */       if (controller.getSoftware().identical(controller.getCurrentSoftware()) && 
/*  895 */         !this.session.getVehicle().getVIT1().isValid()) {
/*  896 */         skipOptionCheck = true;
/*      */       }
/*      */     } 
/*      */     
/*  900 */     if (!skipOptionCheck && !SPSSecurity.getInstance(adapter).checkOptions(hwk, (SPSVehicle)this.session.getVehicle())) {
/*  901 */       log.info("Exception: No Authorization");
/*  902 */       throw new SPSException(CommonException.NoAuthorization);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void checkExclusiveSecurity(SPSSchemaAdapterGME adapter, String hwk) throws SPSException, Exception {
/*  907 */     for (int j = 0; j < this.references.size(); j++) {
/*  908 */       SPSControllerReference reference = (SPSControllerReference)this.references.get(j);
/*  909 */       SPSControllerGME controller = reference.getSelectedController();
/*  910 */       int ecu = controller.getID();
/*  911 */       if (!SPSSecurity.getInstance(adapter).checkExclusiveSecurity(hwk, ecu)) {
/*  912 */         log.info("Exception: No Authorization");
/*  913 */         throw new SPSException(CommonException.NoAuthorization);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected String assembleFunctionLabel(SPSControllerGME controller) {
/*  919 */     String label = controller.getDescription((SPSLanguage)this.session.getLanguage());
/*  920 */     int idx = label.indexOf('\t');
/*  921 */     if (idx >= 0) {
/*  922 */       label = label.substring(0, idx);
/*      */     }
/*  924 */     return label + " - " + controller.getDescription();
/*      */   }
/*      */   
/*      */   public List prepareSummary(SPSSchemaAdapterGME adapter) throws Exception {
/*  928 */     List<Summary> functions = new ArrayList();
/*  929 */     for (int j = 0; j < this.references.size(); j++) {
/*  930 */       SPSControllerReference reference = (SPSControllerReference)this.references.get(j);
/*  931 */       SPSControllerGME controller = reference.getSelectedController();
/*  932 */       SPSProgrammingData data = this.pdata.get(j);
/*  933 */       String label = assembleFunctionLabel(controller);
/*  934 */       Summary summary = new SPSSummary(label, controller.getCurrentSoftware(), controller.getSoftware(), controller.getHistory(), data.getModules());
/*  935 */       functions.add(summary);
/*      */     } 
/*  937 */     return functions;
/*      */   }
/*      */   
/*      */   public void prepareProgrammingData(SPSSchemaAdapterGME adapter) throws Exception {
/*  941 */     for (int j = 0; j < this.references.size(); j++) {
/*  942 */       SPSProgrammingData data = this.pdata.get(j);
/*  943 */       if (data.getCalibrationFiles() == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  948 */         List<ProgrammingDataUnit> blobs = new ArrayList();
/*  949 */         List<SPSModule> modules = data.getModules();
/*  950 */         if (modules == null) {
/*  951 */           log.info("Exception: Software Not Available");
/*  952 */           throw new SPSException(CommonException.SoftwareNotAvailable);
/*      */         } 
/*  954 */         for (int i = 0; i < modules.size(); i++) {
/*  955 */           SPSModule module = modules.get(i);
/*  956 */           ProgrammingDataUnit blob = module.getCalibrationFileInfo((SPSPart)module.getSelectedPart(), adapter, false);
/*  957 */           if (blob == null) {
/*  958 */             log.info("Exception: Missing Calibration File");
/*  959 */             throw new SPSException(CommonException.MissingCalibrationFile);
/*      */           } 
/*  961 */           blobs.add(blob);
/*      */         } 
/*      */         
/*  964 */         data.setCalibrationFiles(blobs);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   public List getCalibrationFiles() {
/*  969 */     List blobs = new ArrayList();
/*  970 */     for (int j = 0; j < this.pdata.size(); j++) {
/*  971 */       SPSProgrammingData data = this.pdata.get(j);
/*  972 */       blobs.addAll(data.getCalibrationFiles());
/*      */     } 
/*  974 */     return blobs;
/*      */   }
/*      */   
/*      */   public byte[] getProgrammingDataUnit(SPSSchemaAdapterGME adapter, ProgrammingDataUnit blob) throws Exception {
/*  978 */     for (int j = 0; j < this.pdata.size(); j++) {
/*  979 */       SPSProgrammingData data = this.pdata.get(j);
/*  980 */       if (matchProgrammingData(data, blob)) {
/*      */ 
/*      */         
/*  983 */         List<SPSModule> modules = data.getModules();
/*  984 */         for (int i = 0; i < modules.size(); i++) {
/*  985 */           SPSModule module = modules.get(i);
/*  986 */           if (blob.getBlobID().intValue() == module.getOrder()) {
/*  987 */             byte[] bytes = module.getCalibrationFile((SPSPart)module.getSelectedPart(), adapter, false);
/*  988 */             if (bytes != null) {
/*  989 */               return bytes;
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  995 */     log.info("Exception: Missing Calibration File");
/*  996 */     throw new SPSException(CommonException.MissingCalibrationFile);
/*      */   }
/*      */   
/*      */   protected boolean matchProgrammingData(SPSProgrammingData pdata, ProgrammingDataUnit target) {
/* 1000 */     List<ProgrammingDataUnit> blobs = pdata.getCalibrationFiles();
/* 1001 */     for (int i = 0; i < blobs.size(); i++) {
/* 1002 */       ProgrammingDataUnit blob = blobs.get(i);
/* 1003 */       if (blob.getBlobName().equals(target.getBlobName())) {
/* 1004 */         return true;
/*      */       }
/*      */     } 
/* 1007 */     return false;
/*      */   }
/*      */   
/*      */   public void checkSoftwareUpdate(SPSModel model) throws SPSException, Exception {
/* 1011 */     if (!model.checkSettingExists(((SPSVehicle)this.session.getVehicle()).getSalesMakeID(), "SW Selection", "no_equal")) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1017 */     for (int j = 0; j < this.functions.size(); j++) {
/* 1018 */       SPSControllerFunction function = this.functions.get(j);
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
/* 1035 */       if (!"Ignore".equalsIgnoreCase(function.getOnSameSW())) {
/*      */ 
/*      */         
/* 1038 */         SPSControllerReference reference = (SPSControllerReference)this.references.get(j);
/* 1039 */         SPSControllerGME controller = reference.getSelectedController();
/* 1040 */         if (controller.getSoftware().identical(controller.getCurrentSoftware())) {
/* 1041 */           log.info("Exception: No Calibration Update");
/* 1042 */           throw new SPSException(CommonException.NoCalibrationUpdate);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   public void makeReprogramRequest(SPSSchemaAdapterGME adapter, SPSModel model, RequestBuilder builder) throws Exception {
/* 1048 */     List<String> functions = new ArrayList();
/* 1049 */     List<String> preInstructions = new ArrayList();
/* 1050 */     List<String> postInstructions = new ArrayList();
/* 1051 */     for (int j = 0; j < this.references.size(); j++) {
/* 1052 */       SPSControllerReference reference = (SPSControllerReference)this.references.get(j);
/* 1053 */       SPSControllerGME controller = reference.getSelectedController();
/* 1054 */       functions.add(assembleFunctionLabel(controller));
/* 1055 */       if (controller.getPreProgrammingInstructions() != null) {
/* 1056 */         List instructions = model.getProgrammingMessage((SPSLanguage)this.session.getLanguage(), controller.getPreProgrammingInstructions());
/* 1057 */         preInstructions.add(instructions.get(0));
/*      */       } else {
/* 1059 */         preInstructions.add("");
/*      */       } 
/* 1061 */       if (controller.getPostProgrammingInstructions() != null) {
/* 1062 */         List instructions = model.getProgrammingMessage((SPSLanguage)this.session.getLanguage(), controller.getPostProgrammingInstructions());
/* 1063 */         postInstructions.add(instructions.get(0));
/*      */       } else {
/* 1065 */         postInstructions.add("");
/*      */       } 
/*      */     } 
/* 1068 */     throw new RequestException(builder.makeReprogramRequest(CommonAttribute.REPROGRAM, functions, this.pdata, preInstructions, postInstructions));
/*      */   }
/*      */   
/*      */   public Integer getWarrantyClaimCodeID() {
/* 1072 */     return ((SPSControllerReference)this.references.get(0)).getSelectedController().getVehicleID();
/*      */   }
/*      */   
/*      */   public List getSequenceFunctions() {
/* 1076 */     List<SPSControllerGME> functions = new ArrayList();
/* 1077 */     for (int j = 0; j < this.references.size(); j++) {
/* 1078 */       SPSControllerReference reference = (SPSControllerReference)this.references.get(j);
/* 1079 */       SPSControllerGME controller = reference.getSelectedController();
/* 1080 */       functions.add(controller);
/*      */     } 
/* 1082 */     return functions;
/*      */   }
/*      */   
/*      */   public void reset() {
/* 1086 */     if (this.references == null) {
/*      */       return;
/*      */     }
/* 1089 */     for (int j = 0; j < this.references.size(); j++) {
/* 1090 */       SPSControllerReference reference = (SPSControllerReference)this.references.get(j);
/* 1091 */       reference.reset();
/*      */     } 
/*      */   }
/*      */   
/*      */   public String toString() {
/* 1096 */     return "cid = " + this.id + " (" + getDescription() + "), sid=" + this.sequenceID + " (" + getSequenceDescription(this.sequenceID, this.adapter).getDescription() + ")";
/*      */   }
/*      */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSControllerSequenceGME.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */