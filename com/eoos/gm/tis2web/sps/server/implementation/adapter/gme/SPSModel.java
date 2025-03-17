/*      */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*      */ 
/*      */ import com.eoos.datatype.VersionNumber;
/*      */ import com.eoos.datatype.gtwo.Pair;
/*      */ import com.eoos.datatype.gtwo.PairImpl;
/*      */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*      */ import com.eoos.gm.tis2web.frame.export.common.datatype.DBVersionInformation;
/*      */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*      */ import com.eoos.gm.tis2web.sps.common.VIT1Data;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.SPSException;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonException;
/*      */ import com.eoos.gm.tis2web.sps.common.impl.VIT1DataImpl;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSControllerList;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSControllerReference;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSModel;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSSession;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.xml.XMLSupport;
/*      */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*      */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*      */ import com.eoos.gm.tis2web.vc.v2.service.IVCFacade;
/*      */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*      */ import com.eoos.gm.tis2web.vc.v2.value.Make;
/*      */ import com.eoos.scsm.v2.util.Util;
/*      */ import java.sql.Connection;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.Statement;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import org.apache.log4j.Logger;
/*      */ 
/*      */ public class SPSModel
/*      */   implements SPSModel {
/*   40 */   private static Logger log = Logger.getLogger(SPSModel.class);
/*      */   
/*      */   public static final int UNKNOWN_SOFTWARE = -2;
/*      */   
/*      */   public static final int UNKNOWN_HARDWARE = -3;
/*      */   
/*      */   public static final int HARDWARE_MISMATCH = -5;
/*      */   
/*      */   public static final int UNKNOWN_VIN = -4;
/*      */   
/*      */   public static final int NOT_FOUND = -1;
/*      */   
/*      */   public static final int FOUND = 0;
/*      */   
/*      */   public static final int IGNORE = 0;
/*      */   
/*   56 */   public static final Integer DUMMY_ECU = Integer.valueOf(0);
/*      */   
/*   58 */   public static final Integer NO_ASBUILT_VINID = Integer.valueOf(-1);
/*      */   
/*   60 */   public static final Integer XML_DEVICE_ID = Integer.valueOf(253);
/*      */   
/*      */   protected VCProxy vc;
/*      */   
/*      */   protected Map settings;
/*      */   protected String model;
/*      */   protected boolean supportsAsBuilt;
/*      */   private IDatabaseLink db;
/*      */   private SPSSchemaAdapterGME adapter;
/*      */   
/*      */   public boolean hasAsBuiltSupport() {
/*   71 */     return this.supportsAsBuilt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private SPSModel(SPSSchemaAdapterGME adapter) {
/*   79 */     this.adapter = adapter;
/*   80 */     this.db = adapter.getDatabaseLink();
/*      */     try {
/*   82 */       this.vc = VCProxy.getInstance(adapter);
/*      */       
/*   84 */       checkDatabase();
/*   85 */       SPSLanguage.init(adapter);
/*   86 */       SPSInstructions.init(this.db);
/*   87 */       SPSVehicle.init(adapter);
/*   88 */       init();
/*      */       
/*   90 */       SPSOption.init(adapter);
/*   91 */       SPSControllerGME.init(adapter);
/*   92 */       SPSIdentType.init(adapter);
/*   93 */       SPSSecurity.init(adapter);
/*   94 */       SPSSoftware.init(adapter);
/*   95 */       SPSRequestInfoCategory.init(adapter);
/*   96 */       SPSHardwareKey.init(adapter);
/*   97 */       SPSType4Data.init(adapter);
/*   98 */       this.supportsAsBuilt = checkAsBuiltSupport();
/*   99 */     } catch (Exception e) {
/*  100 */       throw new RuntimeException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static SPSModel getInstance(SPSSchemaAdapterGME context) {
/*  105 */     synchronized (context.getSyncObject()) {
/*  106 */       SPSModel instance = (SPSModel)context.getObject(SPSModel.class);
/*  107 */       if (instance == null) {
/*  108 */         instance = new SPSModel(context);
/*  109 */         context.storeObject(SPSModel.class, instance);
/*      */       } 
/*  111 */       return instance;
/*      */     } 
/*      */   }
/*      */   
/*      */   VCProxy getVC() {
/*  116 */     return this.vc;
/*      */   }
/*      */ 
/*      */   
/*      */   public DBVersionInformation getVersionInfo() {
/*  121 */     Connection con = null;
/*  122 */     Statement stmt = null;
/*  123 */     ResultSet rs = null;
/*      */     try {
/*  125 */       con = this.db.requestConnection();
/*  126 */       stmt = con.createStatement();
/*  127 */       rs = stmt.executeQuery("SELECT * FROM RELEASE");
/*  128 */       if (rs.next()) {
/*  129 */         return new DBVersionInformation(null, rs.getString("RELEASE_ID"), rs.getDate("RELEASE_DATE"), rs.getString("DESCRIPTION"), rs.getString("VERSION"));
/*      */       }
/*  131 */       return null;
/*      */     }
/*  133 */     catch (Exception e) {
/*  134 */       log.error("loading sps-db version information failed (" + toString() + ").");
/*  135 */       return null;
/*      */     } finally {
/*      */       try {
/*  138 */         if (rs != null) {
/*  139 */           rs.close();
/*      */         }
/*  141 */         if (stmt != null) {
/*  142 */           stmt.close();
/*      */         }
/*  144 */         if (con != null) {
/*  145 */           this.db.releaseConnection(con);
/*      */         }
/*  147 */       } catch (Exception x) {}
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public String getModelInfo() {
/*  153 */     Connection con = null;
/*  154 */     Statement stmt = null;
/*  155 */     ResultSet rs = null;
/*      */     try {
/*  157 */       con = this.db.requestConnection();
/*  158 */       stmt = con.createStatement();
/*  159 */       rs = stmt.executeQuery("SELECT Version FROM SPS_Version WHERE Item = 'T2W_DBModel'");
/*  160 */       if (rs.next()) {
/*  161 */         return rs.getString(1).trim();
/*      */       }
/*  163 */       return "0.0.0.0";
/*      */     }
/*  165 */     catch (Exception e) {
/*  166 */       log.error("loading sps-db model version information failed (" + toString() + ").");
/*  167 */       return null;
/*      */     } finally {
/*      */       try {
/*  170 */         if (rs != null) {
/*  171 */           rs.close();
/*      */         }
/*  173 */         if (stmt != null) {
/*  174 */           stmt.close();
/*      */         }
/*  176 */         if (con != null) {
/*  177 */           this.db.releaseConnection(con);
/*      */         }
/*  179 */       } catch (Exception x) {}
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   boolean checkAsBuiltSupport() {
/*      */     try {
/*  186 */       if (this.model == null) {
/*  187 */         this.model = getModelInfo();
/*      */       }
/*  189 */       VersionNumber versionNumber = VersionNumber.parse(this.model);
/*  190 */       return versionNumber.isHigherThan("2.5.0.0");
/*  191 */     } catch (Exception x) {
/*  192 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   boolean checkSetting(Integer smc, String attribute) {
/*  197 */     Map smcSettings = (Map)this.settings.get(smc);
/*  198 */     if (smcSettings == null) {
/*  199 */       return true;
/*      */     }
/*  201 */     String value = (String)smcSettings.get(attribute.toLowerCase(Locale.ENGLISH));
/*  202 */     return (value == null || !value.equals("ignore"));
/*      */   }
/*      */ 
/*      */   
/*      */   boolean checkSettingExists(Integer smc, String attribute, String target) {
/*  207 */     Map smcSettings = (Map)this.settings.get(smc);
/*  208 */     if (smcSettings == null) {
/*  209 */       return false;
/*      */     }
/*  211 */     String value = (String)smcSettings.get(attribute.toLowerCase(Locale.ENGLISH));
/*  212 */     return (value != null && value.equalsIgnoreCase(target));
/*      */   }
/*      */ 
/*      */   
/*      */   boolean checkSetting(Integer smc, String attribute, String target) {
/*  217 */     Map smcSettings = (Map)this.settings.get(smc);
/*  218 */     if (smcSettings == null) {
/*  219 */       return false;
/*      */     }
/*  221 */     String value = (String)smcSettings.get(attribute.toLowerCase(Locale.ENGLISH));
/*  222 */     return (value != null && value.equalsIgnoreCase(target));
/*      */   }
/*      */ 
/*      */   
/*      */   List getRequestMethodData(SPSSession session, SPSControllerReference reference) throws Exception {
/*  227 */     return ((SPSControllerReference)reference).getRequestMethodData(session, this.adapter);
/*      */   }
/*      */   
/*      */   public List getProgrammingMessage(SPSLanguage language, List instructions) throws Exception {
/*  231 */     if (instructions == null) {
/*  232 */       return null;
/*      */     }
/*  234 */     List<String> htmls = new ArrayList();
/*  235 */     Iterator it = instructions.iterator();
/*  236 */     while (it.hasNext()) {
/*  237 */       Object instructionReference = it.next();
/*  238 */       String html = null;
/*  239 */       if (instructionReference instanceof Integer) {
/*  240 */         html = getProgrammingMessage(language, ((Integer)instructionReference).intValue());
/*  241 */       } else if (instructionReference instanceof SPSDocumentService.SPSDocumentReference) {
/*  242 */         html = getProgrammingMessage(language, ((SPSDocumentService.SPSDocumentReference)instructionReference).getProgrammingInstructionID());
/*  243 */         if (html == null) {
/*  244 */           log.error("failed to retrieve programming message '" + ((SPSDocumentService.SPSDocumentReference)instructionReference).getProgrammingInstructionID() + "'");
/*  245 */           html = ((SPSDocumentService.SPSDocumentReference)instructionReference).getHREF();
/*      */         } else {
/*  247 */           html = html + "<br/><br/>" + ((SPSDocumentService.SPSDocumentReference)instructionReference).getHREF();
/*      */         } 
/*      */       } 
/*  250 */       if (html != null) {
/*  251 */         htmls.add(html); continue;
/*  252 */       }  if (instructionReference instanceof Integer) {
/*  253 */         log.error("failed to retrieve programming message '" + instructionReference + "'");
/*      */       }
/*      */     } 
/*  256 */     return htmls;
/*      */   }
/*      */   
/*      */   public String getProgrammingMessage(SPSLanguage language, int messageID) throws Exception {
/*  260 */     Connection conn = null;
/*  261 */     DBMS.PreparedStatement stmt = null;
/*  262 */     ResultSet rs = null;
/*      */     try {
/*  264 */       conn = this.db.requestConnection();
/*  265 */       String sql = DBMS.getSQL(this.db, "SELECT Description FROM SPS_FieldFixDescription WHERE FFID = ? AND LanguageID = ?");
/*  266 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/*  267 */       stmt.setInt(1, messageID);
/*  268 */       stmt.setString(2, DBMS.getLanguageCode(this.db, language));
/*      */       
/*  270 */       rs = stmt.executeQuery();
/*  271 */       if (rs.next()) {
/*  272 */         String description = DBMS.getString(this.db, language, rs, 1);
/*  273 */         return description;
/*      */       }
/*      */     
/*  276 */     } catch (Exception e) {
/*  277 */       throw e;
/*      */     } finally {
/*      */       try {
/*  280 */         if (rs != null) {
/*  281 */           rs.close();
/*      */         }
/*  283 */         if (stmt != null) {
/*  284 */           stmt.close();
/*      */         }
/*  286 */         if (conn != null) {
/*  287 */           this.db.releaseConnection(conn);
/*      */         }
/*  289 */       } catch (Exception x) {}
/*      */     } 
/*      */     
/*  292 */     return null;
/*      */   }
/*      */   
/*      */   public SPSControllerList getControllers(SPSSession session, List devices, Value mode, String toolType) throws Exception {
/*  296 */     if (this.adapter.supportsSPSFunctions()) {
/*  297 */       if (devices == null && !((SPSSession)session).isCALID()) {
/*  298 */         SPSControllerList sPSControllerList1 = getVehicleControllerSequences(session);
/*  299 */         return sPSControllerList1;
/*      */       } 
/*  301 */       SPSControllerList sPSControllerList = getVehicleControllerFunctions(session, devices);
/*  302 */       return sPSControllerList;
/*      */     } 
/*      */     
/*  305 */     SPSControllerList list = getVehicleControllers(session, devices);
/*  306 */     return list;
/*      */   }
/*      */   
/*      */   int determineReturnValue(VIT1Data vit1, SPSVehicle vehicle, int ecu, boolean wildcardSW, boolean foundVID) {
/*  310 */     if (checkSetting(vehicle.getSalesMakeID(), "UnknSWV", "ignore"))
/*  311 */       return 0; 
/*  312 */     if (ecu == -2)
/*  313 */       return vit1.isValid() ? -2 : 0; 
/*  314 */     if (wildcardSW) {
/*  315 */       return (foundVID || !vit1.isValid()) ? -1 : -2;
/*      */     }
/*  317 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   boolean hasAsBuiltSupport(SPSSession session, VIT1Data vit1) {
/*  322 */     if (!hasAsBuiltSupport()) {
/*  323 */       return false;
/*      */     }
/*  325 */     Integer vinid = ((SPSSession)session).getAsBuiltVINID();
/*  326 */     if (vinid.equals(NO_ASBUILT_VINID)) {
/*  327 */       return false;
/*      */     }
/*  329 */     int device = vit1.getECUAdress();
/*  330 */     if (session.getController() != null) {
/*  331 */       return ((SPSControllerGME)session.getController()).hasAsBuiltSupport(device);
/*      */     }
/*  333 */     return ((SPSControllerReference)session.getControllerReference()).hasAsBuiltSupport(device);
/*      */   }
/*      */ 
/*      */   
/*      */   boolean isAsBuiltController(SPSSession session, int ecu) {
/*  338 */     if (!hasAsBuiltSupport()) {
/*  339 */       return false;
/*      */     }
/*  341 */     Integer vinid = ((SPSSession)session).getAsBuiltVINID();
/*  342 */     if (vinid.equals(NO_ASBUILT_VINID)) {
/*  343 */       return false;
/*      */     }
/*  345 */     boolean asbuilt = false;
/*  346 */     if (session.getController() != null) {
/*  347 */       asbuilt = ((SPSControllerGME)session.getController()).isAsBuiltController(ecu);
/*      */     } else {
/*  349 */       asbuilt = ((SPSControllerReference)session.getControllerReference()).isAsBuiltController(ecu);
/*      */     } 
/*  351 */     return asbuilt;
/*      */   }
/*      */   
/*      */   void selectAsBuiltController(SPSSession session) {
/*  355 */     ((SPSControllerReference)session.getControllerReference()).qualifyAsBuiltController();
/*      */   }
/*      */   
/*      */   void checkAvailableAsBuiltControllers(SPSSession session) {
/*  359 */     if (!hasAsBuiltSupport()) {
/*      */       return;
/*      */     }
/*  362 */     Integer vinid = ((SPSSession)session).getAsBuiltVINID();
/*  363 */     if (vinid.equals(NO_ASBUILT_VINID)) {
/*      */       return;
/*      */     }
/*  366 */     if (session.getControllerReference() != null) {
/*  367 */       Integer device = ((SPSControllerReference)session.getControllerReference()).getSystemTypeID();
/*  368 */       boolean asbuilt = ((SPSControllerReference)session.getControllerReference()).hasAsBuiltSupport(device.intValue());
/*  369 */       if (asbuilt) {
/*  370 */         selectAsBuiltController(session);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   int evaluateVIT1(SPSSession session, boolean isEngineController) throws Exception {
/*  376 */     VIT1Data vit1 = session.getVehicle().getVIT1();
/*  377 */     if (vit1 == null || vit1.getID() == null) {
/*  378 */       checkAvailableAsBuiltControllers(session);
/*  379 */       return -1;
/*      */     } 
/*  381 */     SPSVehicle vehicle = (SPSVehicle)session.getVehicle();
/*  382 */     if (skipPreEvaluation(session, vehicle, vit1)) {
/*  383 */       checkAvailableAsBuiltControllers(session);
/*  384 */       return 0;
/*      */     } 
/*  386 */     if (vit1.isValid()) {
/*  387 */       evaluateSNOET(vehicle, vit1, isEngineController);
/*  388 */       if (vehicle.getEngine() != null) {
/*      */         
/*  390 */         SPSOption engine = SPSOption.getEngineOption((SPSLanguage)session.getLanguage(), vehicle.getEngine(), this.adapter);
/*  391 */         if (engine != null) {
/*  392 */           log.debug("vc/vit1-identified engine=" + vehicle.getEngine());
/*  393 */           vehicle.setDefaultOption(engine);
/*      */         } 
/*      */       } 
/*      */     } 
/*  397 */     boolean asbuilt = hasAsBuiltSupport(session, vit1);
/*  398 */     if (asbuilt) {
/*  399 */       selectAsBuiltController(session);
/*      */     }
/*  401 */     List identTypeList = getIdentTypeList(vehicle, vit1, asbuilt);
/*  402 */     boolean wildcardSW = false;
/*  403 */     int ecu = searchECU(session, vit1, identTypeList, false);
/*      */     
/*  405 */     if (ecu == -2 || ecu == -3) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  410 */       wildcardSW = true;
/*  411 */       ecu = searchECU(session, vit1, identTypeList, true);
/*      */     } 
/*  413 */     if (ecu == -5) {
/*  414 */       ecu = -3;
/*      */     }
/*  416 */     if (ecu == -2)
/*  417 */       return determineReturnValue(vit1, vehicle, ecu, wildcardSW, false); 
/*  418 */     if (ecu < 0) {
/*  419 */       return determineReturnValue(vit1, vehicle, -2, wildcardSW, false);
/*      */     }
/*  421 */     log.debug("vit1-identified ecu=" + ecu);
/*  422 */     ((VIT1DataImpl)vit1).setECU(ecu);
/*  423 */     if (!vit1.isValid()) {
/*  424 */       return -1;
/*      */     }
/*  426 */     setFreeOptionsFromVIT1(session, this.adapter);
/*  427 */     List<Integer> vehicles = getVehicleList(ecu);
/*  428 */     if (vehicles == null) {
/*  429 */       return determineReturnValue(vit1, vehicle, ecu, wildcardSW, false);
/*      */     }
/*  431 */     List<Integer> vids = isAsBuiltController(session, ecu) ? getVehicleID(session, ecu) : null;
/*  432 */     if (vids != null) {
/*  433 */       vids.addAll(getVehicleID(vehicle));
/*      */     } else {
/*  435 */       vids = getVehicleID(vehicle);
/*      */     } 
/*  437 */     if (vids == null) {
/*  438 */       return determineReturnValue(vit1, vehicle, ecu, wildcardSW, false);
/*      */     }
/*  440 */     Iterator<Integer> it = vehicles.iterator();
/*  441 */     while (it.hasNext()) {
/*  442 */       Integer vid = it.next();
/*  443 */       boolean match = false;
/*  444 */       for (int i = 0; i < vids.size(); i++) {
/*  445 */         if (vid.equals(vids.get(i))) {
/*  446 */           match = true;
/*      */         }
/*      */       } 
/*  449 */       if (!match) {
/*  450 */         it.remove();
/*      */       }
/*      */     } 
/*  453 */     List<SPSVehicleCategory> vcategories = SPSVehicleCategory.loadVehicleCategories(this.db, (SPSLanguage)session.getLanguage(), vehicles, this.adapter);
/*      */ 
/*      */     
/*  456 */     if (vcategories == null) {
/*  457 */       if (vehicles.size() == 1 && isAsBuiltController(session, ecu)) {
/*      */         
/*  459 */         int vid = ((Integer)vehicles.get(0)).intValue();
/*  460 */         log.debug("vit1-identified as-built vid w/o options=" + vid);
/*  461 */         return vid;
/*  462 */       }  if (!asbuilt && needsProgrammingVCI(session)) {
/*  463 */         return -4;
/*      */       }
/*  465 */       return determineReturnValue(vit1, vehicle, ecu, wildcardSW, false);
/*      */     } 
/*  467 */     List<Integer> orders = SPSVehicleCategory.getOrderSequence(vcategories);
/*  468 */     for (int o = 0; o < orders.size(); o++) {
/*  469 */       Integer order = orders.get(o);
/*  470 */       List<SPSVehicleCategory> candidates = SPSVehicleCategory.getReductionCategories(vcategories, order);
/*  471 */       if (candidates != null) {
/*      */ 
/*      */         
/*  474 */         List rejects = new ArrayList();
/*  475 */         for (int i = 0; i < candidates.size(); i++) {
/*  476 */           SPSVehicleCategory vcategory = candidates.get(i);
/*  477 */           if (vcategory.getNoPreEval() <= 0) {
/*      */ 
/*      */             
/*  480 */             SPSOptionGroup group = vcategory.getGroup();
/*  481 */             if (vcategory.getCategory().getID().equals("RNG")) {
/*  482 */               if (group != null)
/*      */               {
/*      */                 
/*  485 */                 if (!group.matchVINRange((SPSVIN)vehicle.getVIN()) && 
/*  486 */                   !group.matchDefaultVINRange()) {
/*  487 */                   reject(rejects, vcategory);
/*      */                 }
/*      */               }
/*  490 */             } else if (vcategory.getCategory().getID().equals("VIN")) {
/*  491 */               if (group != null)
/*      */               {
/*      */                 
/*  494 */                 if (!group.matchVINCode((SPSVIN)vehicle.getVIN()))
/*  495 */                   reject(rejects, vcategory); 
/*      */               }
/*  497 */             } else if (vcategory.getCategory().getID().equals("V10")) {
/*  498 */               if (group != null)
/*      */               {
/*      */                 
/*  501 */                 if (!group.matchV10Code((SPSVIN)vehicle.getVIN()))
/*  502 */                   reject(rejects, vcategory); 
/*      */               }
/*  504 */             } else if (vcategory.getCategory().getID().equals("ENG")) {
/*  505 */               if (vehicle.getEngine() != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  510 */                 SPSOption engine = (SPSOption)vehicle.getOption("ENG");
/*  511 */                 if (engine != null && !vcategory.getCategory().match(engine)) {
/*  512 */                   reject(rejects, vcategory);
/*      */                 }
/*      */               } 
/*  515 */             } else if (vcategory.getCategory().getID().equals("HWO")) {
/*  516 */               if (group != null)
/*      */               {
/*      */                 
/*  519 */                 if (!group.matchHWO(vit1, vcategory.getCategory()))
/*  520 */                   reject(rejects, vcategory); 
/*      */               }
/*  522 */             } else if (!vcategory.getCategory().isOptionCategory()) {
/*  523 */               if (group != null)
/*      */               {
/*      */                 
/*  526 */                 if (!group.matchVIT1(vit1, vcategory.getCategory())) {
/*  527 */                   reject(rejects, vcategory);
/*      */                 }
/*      */               }
/*      */             }
/*      */             else {
/*      */               
/*  533 */               SPSOption option = (SPSOption)vehicle.getOption(vcategory.getCategory().getID());
/*  534 */               if (option != null && !vcategory.getCategory().match(option)) {
/*  535 */                 reject(rejects, vcategory);
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
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
/*  562 */         reject(rejects, vehicles, vcategories);
/*  563 */         if (vehicles.size() == 0)
/*      */           break; 
/*      */       } 
/*      */     } 
/*  567 */     if (vehicles.size() == 0) {
/*  568 */       return determineReturnValue(vit1, vehicle, ecu, wildcardSW, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  579 */     if (vehicles.size() == 1) {
/*  580 */       int vid = ((Integer)vehicles.get(0)).intValue();
/*  581 */       log.debug("vit1-identified vid=" + vid);
/*  582 */       for (int i = 0; i < vcategories.size(); i++) {
/*  583 */         SPSVehicleCategory vcategory = vcategories.get(i);
/*  584 */         if (vcategory.getNoPreEval() <= 0 && !vcategory.getCategory().isHWOCategory())
/*      */         {
/*      */           
/*  587 */           if (vcategory.getVehicle() == vid && vcategory.isOptionCategory()) {
/*  588 */             SPSOptionGroup group = vcategory.getGroup();
/*  589 */             if (group.getOptions() != null && group.getOptions().size() == 1)
/*  590 */               setDefaultOption(session, vehicle, vcategory, group.getOptions().get(0)); 
/*      */           } 
/*      */         }
/*      */       } 
/*  594 */       return vid;
/*      */     } 
/*  596 */     deriveDefaultOptions(session, vehicle, vcategories, vehicles);
/*  597 */     return determineReturnValue(vit1, vehicle, ecu, wildcardSW, (vehicles.size() > 1));
/*      */   }
/*      */ 
/*      */   
/*      */   protected static void reject(List<Integer> rejects, SPSVehicleCategory vcategory) {
/*  602 */     Integer vid = Integer.valueOf(vcategory.getVehicle());
/*  603 */     if (!rejects.contains(vid)) {
/*  604 */       rejects.add(vid);
/*      */     }
/*      */   }
/*      */   
/*      */   protected static void reject(List<Integer> rejects, List vehicles, List vcategories) {
/*  609 */     for (int i = 0; i < rejects.size(); i++) {
/*  610 */       Integer vid = rejects.get(i);
/*  611 */       Iterator<Integer> it = vehicles.iterator();
/*  612 */       while (it.hasNext()) {
/*  613 */         Integer vehicle = it.next();
/*  614 */         if (vehicle.equals(vid)) {
/*  615 */           it.remove();
/*      */         }
/*      */       } 
/*  618 */       reject(vid, vcategories);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected static void reject(Integer vid, List vcategories) {
/*  623 */     Iterator<SPSVehicleCategory> it = vcategories.iterator();
/*  624 */     while (it.hasNext()) {
/*  625 */       SPSVehicleCategory vcategory = it.next();
/*  626 */       if (vcategory.getVehicle() == vid.intValue()) {
/*  627 */         it.remove();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   protected static void setDefaultOption(SPSSession session, SPSVehicle vehicle, SPSVehicleCategory vcategory, SPSOption option) {
/*  633 */     if (vcategory.getCategory().getID().equals("ENG") && 
/*  634 */       vehicle.getEngine() != null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  640 */     option = SPSOption.getOption((SPSLanguage)session.getLanguage(), option);
/*  641 */     if (option.getType() == null) {
/*  642 */       option.setType(vcategory.getCategory());
/*      */     }
/*  644 */     vehicle.setDefaultOption(option);
/*      */   }
/*      */   
/*      */   protected static void deriveDefaultOptions(SPSSession session, SPSVehicle vehicle, List<SPSVehicleCategory> vcategories, List<Integer> vehicles) {
/*  648 */     if (vehicles == null || vehicles.size() == 0) {
/*      */       return;
/*      */     }
/*  651 */     int vid = ((Integer)vehicles.get(0)).intValue();
/*  652 */     for (int i = 0; i < vcategories.size(); i++) {
/*  653 */       SPSVehicleCategory vcategory = vcategories.get(i);
/*  654 */       if (vcategory.getNoPreEval() <= 0)
/*      */       {
/*      */         
/*  657 */         if (vcategory.getVehicle() == vid && vcategory.isOptionCategory()) {
/*  658 */           SPSOptionGroup group = vcategory.getGroup();
/*  659 */           if (group.getOptions() != null && group.getOptions().size() == 1) {
/*  660 */             SPSOption option = group.getOptions().get(0);
/*  661 */             if (isDefaultOption(vcategories, vehicles, option))
/*  662 */               setDefaultOption(session, vehicle, vcategory, option); 
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   protected static boolean isDefaultOption(List vcategories, List<Integer> vehicles, SPSOption option) {
/*  670 */     for (int i = 1; i < vehicles.size(); i++) {
/*  671 */       int vid = ((Integer)vehicles.get(i)).intValue();
/*  672 */       if (!isDefaultOption(vcategories, vid, option)) {
/*  673 */         return false;
/*      */       }
/*      */     } 
/*  676 */     return true;
/*      */   }
/*      */   
/*      */   protected static boolean isDefaultOption(List<SPSVehicleCategory> vcategories, int vid, SPSOption option) {
/*  680 */     for (int i = 0; i < vcategories.size(); i++) {
/*  681 */       SPSVehicleCategory vcategory = vcategories.get(i);
/*  682 */       if (vcategory.getVehicle() == vid && vcategory.isOptionCategory()) {
/*  683 */         SPSOptionGroup group = vcategory.getGroup();
/*  684 */         if (group.getOptions() != null && group.getOptions().size() == 1 && 
/*  685 */           option.equals(group.getOptions().get(0))) {
/*  686 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  691 */     return false;
/*      */   }
/*      */   
/*      */   static void setFreeOptionsFromVIT1(SPSSession session, SPSSchemaAdapterGME adapter) {
/*  695 */     SPSVehicle vehicle = (SPSVehicle)session.getVehicle();
/*  696 */     VIT1Data vit1 = vehicle.getVIT1();
/*  697 */     if (vit1 == null || vit1.getID() == null || vit1.getOptions() == null) {
/*      */       return;
/*      */     }
/*  700 */     List<Pair> options = vit1.getOptions();
/*  701 */     for (int i = 0; i < options.size(); i++) {
/*  702 */       Pair pair = options.get(i);
/*      */       try {
/*  704 */         String attribute = (String)pair.getFirst();
/*  705 */         String value = (String)pair.getSecond();
/*  706 */         SPSOption option = SPSOption.getOption((SPSLanguage)session.getLanguage(), attribute, value, adapter);
/*  707 */         if (option != null) {
/*  708 */           vehicle.setDefaultOption(option);
/*      */         }
/*  710 */       } catch (Exception x) {}
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected List getHardware(SPSVehicle vehicle, List<SPSControllerGME> controllers) throws Exception {
/*  717 */     List<SPSHardware> hardware = new ArrayList();
/*  718 */     Connection conn = null;
/*  719 */     DBMS.PreparedStatement stmt = null;
/*  720 */     ResultSet rs = null;
/*      */     try {
/*  722 */       conn = this.db.requestConnection();
/*  723 */       String sql = DBMS.getSQL(this.db, "SELECT h.ECUID, d.HWName, d.HWLocID FROM SPS_Hardware h, SPS_HWDescription d WHERE h.ECUID IN (#list#)  AND h.HWID = d.HWID", controllers.size());
/*  724 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/*  725 */       for (int i = 1; i <= controllers.size(); i++) {
/*  726 */         Integer ecu = null;
/*  727 */         if (controllers.get(i - 1) instanceof SPSControllerGME) {
/*  728 */           ecu = Integer.valueOf(((SPSControllerGME)controllers.get(i - 1)).getID());
/*      */         } else {
/*  730 */           ecu = (Integer)controllers.get(i - 1);
/*      */         } 
/*  732 */         stmt.setInt(i, ecu.intValue());
/*      */       } 
/*  734 */       rs = stmt.executeQuery();
/*  735 */       while (rs.next()) {
/*  736 */         Integer ecu = Integer.valueOf(rs.getInt(1));
/*  737 */         String hwname = rs.getString(2).trim();
/*  738 */         for (int j = 0; j < hardware.size(); j++) {
/*  739 */           SPSHardware hw = hardware.get(j);
/*  740 */           if (hw.getDescription().equals(hwname)) {
/*  741 */             hw.register(ecu);
/*  742 */             ecu = null;
/*      */             break;
/*      */           } 
/*      */         } 
/*  746 */         if (ecu != null) {
/*  747 */           hardware.add(new SPSHardware(ecu, hwname));
/*      */         }
/*      */       } 
/*  750 */     } catch (Exception e) {
/*  751 */       throw e;
/*      */     } finally {
/*      */       try {
/*  754 */         if (rs != null) {
/*  755 */           rs.close();
/*      */         }
/*  757 */         if (stmt != null) {
/*  758 */           stmt.close();
/*      */         }
/*  760 */         if (conn != null) {
/*  761 */           this.db.releaseConnection(conn);
/*      */         }
/*  763 */       } catch (Exception x) {}
/*      */     } 
/*      */     
/*  766 */     return (hardware.size() > 0) ? hardware : null;
/*      */   }
/*      */   
/*      */   protected List checkHardware(SPSVehicle vehicle, List<SPSControllerGME> controllers, VIT1Data vit1) throws Exception {
/*  770 */     if (controllers == null || controllers.size() == 0) {
/*  771 */       return null;
/*      */     }
/*  773 */     if (checkSetting(vehicle.getSalesMakeID(), "HWCheck")) {
/*  774 */       Map<Object, Object> hardware = new HashMap<Object, Object>();
/*  775 */       loadHardware(vit1, controllers, hardware);
/*  776 */       if (hardware.size() == 0) {
/*  777 */         return controllers;
/*      */       }
/*  779 */       List<?> rejects = new ArrayList();
/*  780 */       for (int i = 0; i < controllers.size(); i++) {
/*  781 */         Integer ecu = null;
/*  782 */         if (controllers.get(i) instanceof com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSControllerFunction) {
/*  783 */           VIT1Data vit1data = (VIT1Data)((SPSControllerGME)controllers.get(i)).getControllerData();
/*  784 */           if (vit1data != null) {
/*  785 */             ecu = Integer.valueOf(vit1data.getECU());
/*      */           } else {
/*  787 */             ecu = new Integer(((SPSControllerGME)controllers.get(i)).getID());
/*      */           } 
/*  789 */         } else if (controllers.get(i) instanceof SPSControllerGME) {
/*  790 */           ecu = new Integer(((SPSControllerGME)controllers.get(i)).getID());
/*      */         } else {
/*  792 */           ecu = (Integer)controllers.get(i);
/*      */         } 
/*  794 */         List<Pair> hw = (List)hardware.get(ecu);
/*  795 */         if (hw == null) {
/*  796 */           rejects.add(controllers.get(i));
/*      */         } else {
/*      */           
/*  799 */           boolean match = false;
/*  800 */           for (int j = 0; j < hw.size(); j++) {
/*  801 */             Pair pair = hw.get(j);
/*  802 */             String hwname = (String)pair.getFirst();
/*  803 */             List idents = (List)pair.getSecond();
/*  804 */             idents = assertValidLocation(idents);
/*  805 */             if (SPSIdentType.CheckFlexibleHWIdentifierForECUID(vit1, idents, hwname)) {
/*  806 */               match = true;
/*  807 */               if (controllers.get(i) instanceof SPSControllerGME) {
/*      */ 
/*      */                 
/*  810 */                 if (SPSIdentType.WILDCARD.equals(hwname)) {
/*  811 */                   hwname = null;
/*  812 */                   if (idents != null) {
/*  813 */                     hwname = SPSIdentType.getHWIdentFromVIT1(vit1, idents);
/*      */                   }
/*  815 */                   if (hwname == null) {
/*  816 */                     hwname = vit1.getHWNumber();
/*      */                   }
/*      */                 } 
/*  819 */                 registerHardware(controllers.get(i), hwname);
/*      */               } 
/*      */             } 
/*      */           } 
/*  823 */           if (!match)
/*  824 */             rejects.add(controllers.get(i)); 
/*      */         } 
/*      */       } 
/*  827 */       controllers.removeAll(rejects);
/*      */     } 
/*  829 */     return (controllers.size() == 0) ? null : controllers;
/*      */   }
/*      */   
/*      */   protected static void registerHardware(SPSControllerGME controller, String hardware) {
/*  833 */     if (hardware != null) {
/*  834 */       List<SPSPart> others = controller.getHardware();
/*  835 */       if (others != null) {
/*  836 */         for (int j = 0; j < others.size(); j++) {
/*  837 */           SPSPart other = others.get(j);
/*  838 */           if (other != null && other.getDescription() != null)
/*      */           {
/*      */             
/*  841 */             if (other.getDescription().equals(hardware));
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*  846 */       controller.setHardware(new SPSHardware(Integer.valueOf(controller.getID()), hardware));
/*      */     } 
/*      */   }
/*      */   
/*      */   protected static List assertValidLocation(List idents) {
/*  851 */     if (idents == null || idents.size() == 0) {
/*  852 */       return null;
/*      */     }
/*  854 */     return (idents.get(0) != null) ? idents : null;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void loadHardware(VIT1Data vit1, List<SPSControllerGME> controllers, Map<Integer, List> hardware) throws Exception {
/*  859 */     Connection conn = null;
/*  860 */     DBMS.PreparedStatement stmt = null;
/*  861 */     ResultSet rs = null;
/*      */     try {
/*  863 */       conn = this.db.requestConnection();
/*  864 */       String sql = DBMS.getSQL(this.db, "SELECT h.ECUID, d.HWName, d.HWLocID FROM SPS_Hardware h, SPS_HWDescription d WHERE h.ECUID IN (#list#)  AND h.HWID = d.HWID", controllers.size());
/*  865 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/*  866 */       for (int i = 1; i <= controllers.size(); i++) {
/*  867 */         Integer ecu = null;
/*  868 */         if (controllers.get(i - 1) instanceof com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSControllerFunction) {
/*  869 */           VIT1Data vit1data = (VIT1Data)((SPSControllerGME)controllers.get(i - 1)).getControllerData();
/*  870 */           if (vit1data != null) {
/*  871 */             ecu = Integer.valueOf(vit1data.getECU());
/*      */           } else {
/*  873 */             ecu = new Integer(((SPSControllerGME)controllers.get(i - 1)).getID());
/*      */           } 
/*  875 */         } else if (controllers.get(i - 1) instanceof SPSControllerGME) {
/*  876 */           ecu = new Integer(((SPSControllerGME)controllers.get(i - 1)).getID());
/*      */         } else {
/*  878 */           ecu = (Integer)controllers.get(i - 1);
/*      */         } 
/*  880 */         stmt.setInt(i, ecu.intValue());
/*      */       } 
/*  882 */       rs = stmt.executeQuery();
/*  883 */       while (rs.next()) {
/*  884 */         Integer ecu = Integer.valueOf(rs.getInt(1));
/*  885 */         String hwname = rs.getString(2).trim();
/*  886 */         Integer hwlocationID = Integer.valueOf(rs.getInt(3));
/*  887 */         List<PairImpl> hw = (List)hardware.get(ecu);
/*  888 */         if (hw == null) {
/*  889 */           hw = new ArrayList();
/*  890 */           hardware.put(ecu, hw);
/*      */         } 
/*  892 */         List ident = SPSIdentType.getHardwareLocation(hwlocationID, vit1.getID(), this.adapter);
/*  893 */         hw.add(new PairImpl(hwname, ident));
/*      */       } 
/*  895 */     } catch (Exception e) {
/*  896 */       throw e;
/*      */     } finally {
/*      */       try {
/*  899 */         if (rs != null) {
/*  900 */           rs.close();
/*      */         }
/*  902 */         if (stmt != null) {
/*  903 */           stmt.close();
/*      */         }
/*  905 */         if (conn != null) {
/*  906 */           this.db.releaseConnection(conn);
/*      */         }
/*  908 */       } catch (Exception x) {}
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void evaluateSNOET(SPSVehicle vehicle, VIT1Data vit1, boolean isEngineController) {
/*  914 */     if (checkSetting(vehicle.getSalesMakeID(), "SNOET") && vit1.getSNOET() != null)
/*  915 */       if (vit1.getECUAdress() == 24) {
/*      */         
/*  917 */         vehicle.setEngineTransmission(vit1.getSNOET());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*  924 */       else if (isEngineController) {
/*  925 */         vehicle.setEngine(vit1.getSNOET());
/*      */       }  
/*      */   }
/*      */   
/*      */   private static class IdentTypeRecord {
/*      */     Integer myc;
/*      */     String vinPattern;
/*      */     String snFrom;
/*      */     String snTo;
/*      */     int hwid;
/*      */     int identType;
/*      */     String vit1Ident;
/*      */     Integer vit1Pos;
/*      */     
/*      */     private IdentTypeRecord() {}
/*      */     
/*      */     public String toString() {
/*  942 */       return "my=" + this.myc + ";vin~" + this.vinPattern + ">=" + this.snFrom + "<=" + this.snTo + " hwid=" + this.hwid;
/*      */     }
/*      */   }
/*      */   
/*      */   protected List getIdentTypeList(SPSVehicle vehicle, VIT1Data vit1, boolean asbuilt) throws Exception {
/*  947 */     List identTypes = new ArrayList();
/*  948 */     Connection conn = null;
/*  949 */     DBMS.PreparedStatement stmt = null;
/*  950 */     ResultSet rs = null;
/*      */     try {
/*  952 */       conn = this.db.requestConnection();
/*  953 */       String sql = DBMS.getSQL(this.db, "SELECT DISTINCT b.IdentType, b.VIT1Ident, b.VIT1Pos, a.VINPattern, a.SNFrom, a.SNTo, a.HWID, a.ModelYearCode FROM SPS_Schema a, SPS_IdentDescription b WHERE a.WMI = ?  AND ((a.SalesMakeCode = ?) OR (a.SalesMakeCode is null))  AND a.Protocol = ?  AND b.VITType = ?  AND b.IdentType = a.IdentType");
/*  954 */       if (vehicle.getModelYearID() != null) {
/*  955 */         sql = sql + DBMS.getSQL(this.db, " AND (a.ModelYearCode is null OR a.ModelYearCode IN (?, 0, -1))");
/*      */       }
/*  957 */       if (vit1.getECUAdress() > 0) {
/*  958 */         sql = sql + DBMS.getSQL(this.db, " AND (a.SystemType is null OR a.SystemType IN (0, ?))");
/*      */       }
/*  960 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/*  961 */       int count = 1;
/*  962 */       SPSVIN vin = (SPSVIN)vehicle.getVIN();
/*  963 */       stmt.setString(count++, vin.getWMI());
/*  964 */       stmt.setInt(count++, vehicle.getSalesMakeID().intValue());
/*  965 */       stmt.setInt(count++, vit1.getProtocol());
/*  966 */       stmt.setString(count++, vit1.getID());
/*  967 */       if (vehicle.getModelYearID() != null) {
/*  968 */         stmt.setInt(count++, vehicle.getModelYearID().intValue());
/*      */       }
/*  970 */       if (vit1.getECUAdress() > 0) {
/*  971 */         stmt.setInt(count, vit1.getECUAdress());
/*      */       }
/*  973 */       List<IdentTypeRecord> records = new ArrayList();
/*  974 */       rs = stmt.executeQuery();
/*  975 */       while (rs.next()) {
/*  976 */         IdentTypeRecord record = new IdentTypeRecord();
/*  977 */         int my = rs.getInt(8);
/*  978 */         records.add(record);
/*  979 */         record.myc = rs.wasNull() ? null : Integer.valueOf(my);
/*  980 */         record.vinPattern = DBMS.trimString(rs.getString(4));
/*  981 */         record.snFrom = DBMS.trimString(rs.getString(5));
/*  982 */         record.snTo = DBMS.trimString(rs.getString(6));
/*  983 */         record.hwid = rs.getInt(7);
/*  984 */         record.identType = rs.getInt(1);
/*  985 */         record.vit1Ident = DBMS.trimString(rs.getString(2));
/*  986 */         record.vit1Pos = Integer.valueOf(rs.getInt(3));
/*  987 */         if (rs.wasNull()) {
/*  988 */           record.vit1Pos = null;
/*      */         }
/*      */       } 
/*  991 */       List wildcardRecords = extractWildcardRecords(records);
/*  992 */       evaluateIdentTypeList(identTypes, vit1, vin, records);
/*  993 */       if (identTypes.size() == 0 && wildcardRecords.size() > 0) {
/*  994 */         evaluateIdentTypeList(identTypes, vit1, vin, wildcardRecords);
/*      */       }
/*  996 */     } catch (Exception e) {
/*  997 */       throw e;
/*      */     } finally {
/*      */       try {
/* 1000 */         if (rs != null) {
/* 1001 */           rs.close();
/*      */         }
/* 1003 */         if (stmt != null) {
/* 1004 */           stmt.close();
/*      */         }
/* 1006 */         if (conn != null) {
/* 1007 */           this.db.releaseConnection(conn);
/*      */         }
/* 1009 */       } catch (Exception x) {}
/*      */     } 
/*      */     
/* 1012 */     return (identTypes.size() == 0) ? null : identTypes;
/*      */   }
/*      */   
/*      */   protected void evaluateIdentTypeList(List<SPSIdentType> identTypes, VIT1Data vit1, SPSVIN vin, List<IdentTypeRecord> records) throws Exception {
/* 1016 */     for (int i = 0; i < records.size(); i++) {
/* 1017 */       IdentTypeRecord record = records.get(i);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1023 */       if (matchVIN(vin, record.vinPattern, record.snFrom, record.snTo))
/*      */       {
/*      */         
/* 1026 */         if (record.hwid == -1 || record.hwid == 0 || checkHWID(vit1, Integer.valueOf(record.hwid))) {
/*      */ 
/*      */           
/* 1029 */           SPSIdentType ident = new SPSIdentType(Integer.valueOf(record.identType), record.vit1Ident, record.vit1Pos, Integer.valueOf(record.hwid));
/* 1030 */           identTypes.add(ident);
/*      */         }  } 
/*      */     } 
/*      */   }
/*      */   protected static List extractWildcardRecords(List records) {
/* 1035 */     List<IdentTypeRecord> wildcardRecords = new ArrayList();
/* 1036 */     Iterator<IdentTypeRecord> it = records.iterator();
/* 1037 */     while (it.hasNext()) {
/* 1038 */       IdentTypeRecord record = it.next();
/* 1039 */       if ((record.myc != null && record.myc.intValue() == -1) || record.hwid == -1 || "#".equals(record.vinPattern) || "#".equals(record.snFrom) || "#".equals(record.snTo)) {
/* 1040 */         wildcardRecords.add(record);
/* 1041 */         it.remove();
/*      */       } 
/*      */     } 
/* 1044 */     return wildcardRecords;
/*      */   }
/*      */   
/*      */   protected static boolean matchVIN(SPSVIN vin, String vinPattern, String snFrom, String snTo) {
/* 1048 */     if (!vin.matchWildcard(vinPattern, vin.toString().substring(3, 9))) {
/* 1049 */       return false;
/*      */     }
/* 1051 */     return vin.match(snFrom, snTo);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected int searchECU(SPSSession session, VIT1Data vit1, List<SPSIdentType> identTypes, boolean useWildcard) throws Exception {
/* 1057 */     if (identTypes == null) {
/* 1058 */       return -2;
/*      */     }
/* 1060 */     Set<Integer> hardware = new HashSet();
/* 1061 */     for (int i = 0; i < identTypes.size(); i++) {
/* 1062 */       SPSIdentType identType = identTypes.get(i);
/* 1063 */       if (identType.getHWID() != null) {
/* 1064 */         hardware.add(identType.getHWID());
/*      */       }
/*      */     } 
/* 1067 */     if (hardware.size() > 1) {
/* 1068 */       boolean noMatchingHardware = true;
/* 1069 */       Iterator<Integer> it = hardware.iterator();
/* 1070 */       while (it.hasNext()) {
/* 1071 */         Integer hwid = it.next();
/* 1072 */         if (!checkHWID(vit1, hwid)) {
/*      */           continue;
/*      */         }
/* 1075 */         noMatchingHardware = false;
/* 1076 */         List<SPSIdentType> hwidents = new ArrayList();
/* 1077 */         for (int j = 0; j < identTypes.size(); j++) {
/* 1078 */           SPSIdentType identType = identTypes.get(j);
/* 1079 */           if (hwid.equals(identType.getHWID())) {
/* 1080 */             hwidents.add(identType);
/*      */           }
/*      */         } 
/* 1083 */         if (hwidents.size() > 0) {
/* 1084 */           int result = findECU(session, vit1, hwidents, useWildcard);
/* 1085 */           if (result > 0) {
/* 1086 */             return result;
/*      */           }
/*      */         } 
/*      */       } 
/* 1090 */       return noMatchingHardware ? -5 : -2;
/*      */     } 
/* 1092 */     return findECU(session, vit1, identTypes, useWildcard);
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean checkHWID(VIT1Data vit1, Integer hwid) throws Exception {
/* 1097 */     IDatabaseLink db = this.adapter.getDatabaseLink();
/* 1098 */     Connection conn = null;
/* 1099 */     DBMS.PreparedStatement stmt = null;
/* 1100 */     ResultSet rs = null;
/*      */     try {
/* 1102 */       conn = db.requestConnection();
/* 1103 */       String sql = DBMS.getSQL(db, "SELECT HWLocID, HWName FROM SPS_HWDescription WHERE HWID = ?");
/* 1104 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 1105 */       stmt.setInt(1, hwid.intValue());
/* 1106 */       rs = stmt.executeQuery();
/* 1107 */       if (rs.next()) {
/* 1108 */         Integer hwlocid = Integer.valueOf(rs.getInt(1));
/* 1109 */         String hwname = rs.getString(2);
/* 1110 */         if (hwname != null) {
/* 1111 */           hwname = hwname.trim();
/*      */         }
/* 1113 */         return checkHWID(vit1, hwlocid, hwname);
/*      */       } 
/* 1115 */     } catch (Exception e) {
/* 1116 */       throw e;
/*      */     } finally {
/*      */       try {
/* 1119 */         if (rs != null) {
/* 1120 */           rs.close();
/*      */         }
/* 1122 */         if (stmt != null) {
/* 1123 */           stmt.close();
/*      */         }
/* 1125 */         if (conn != null) {
/* 1126 */           db.releaseConnection(conn);
/*      */         }
/* 1128 */       } catch (Exception x) {}
/*      */     } 
/*      */     
/* 1131 */     return false;
/*      */   }
/*      */   
/*      */   protected boolean checkHWID(VIT1Data vit1, Integer hwlocid, String hwname) throws Exception {
/* 1135 */     List idents = SPSIdentType.getHardwareLocation(hwlocid, vit1.getID(), this.adapter);
/* 1136 */     if (assertValidLocation(idents) == null) {
/* 1137 */       return false;
/*      */     }
/* 1139 */     String hardware = SPSIdentType.getHWIdentFromVIT1(vit1, idents);
/* 1140 */     return (hardware == null) ? false : hardware.equals(hwname);
/*      */   }
/*      */   
/*      */   protected int findECU(SPSSession session, VIT1Data vit1, List<SPSIdentType> identTypes, boolean useWildcard) throws Exception {
/* 1144 */     if (identTypes == null) {
/* 1145 */       return -2;
/*      */     }
/*      */     
/* 1148 */     Connection conn = null;
/* 1149 */     DBMS.PreparedStatement stmt = null;
/* 1150 */     ResultSet rs = null;
/*      */     try {
/* 1152 */       conn = this.db.requestConnection();
/* 1153 */       String sql = assembleECUSoftwareQuery(useWildcard, identTypes);
/* 1154 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 1155 */       int count = 0;
/* 1156 */       for (int i = 0; i < identTypes.size(); i++) {
/* 1157 */         SPSIdentType identType = identTypes.get(i);
/* 1158 */         stmt.setInt(++count, identType.getIdentType().intValue());
/* 1159 */         if (useWildcard) {
/* 1160 */           stmt.setString(++count, "*");
/*      */         } else {
/* 1162 */           String value = identType.getIdentValue(vit1);
/* 1163 */           if (value == null || value.length() == 0) {
/* 1164 */             value = "#NULL#";
/*      */           }
/* 1166 */           stmt.setString(++count, value);
/*      */         } 
/*      */       } 
/* 1169 */       List<SPSControllerGME> candidates = new ArrayList();
/* 1170 */       rs = stmt.executeQuery();
/* 1171 */       while (rs.next()) {
/* 1172 */         Integer candidate = Integer.valueOf(rs.getInt(1));
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1177 */         SPSControllerGME controller = new SPSControllerGME(session, null, null, null, candidate, this.adapter);
/* 1178 */         candidates.add(controller);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1188 */       if (candidates == null || candidates.size() == 0) {
/* 1189 */         return -2;
/*      */       }
/* 1191 */       SPSControllerGME ecu = candidates.get(0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1200 */       return ecu.getID();
/*      */     }
/* 1202 */     catch (Exception e) {
/* 1203 */       throw e;
/*      */     } finally {
/*      */       try {
/* 1206 */         if (rs != null) {
/* 1207 */           rs.close();
/*      */         }
/* 1209 */         if (stmt != null) {
/* 1210 */           stmt.close();
/*      */         }
/* 1212 */         if (conn != null) {
/* 1213 */           this.db.releaseConnection(conn);
/*      */         }
/* 1215 */       } catch (Exception x) {}
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected String assembleECUSoftwareQuery(boolean useWildcard, List identTypes) {
/* 1221 */     StringBuffer sql = new StringBuffer();
/* 1222 */     for (int i = 0; i < identTypes.size(); i++) {
/* 1223 */       if (i > 0) {
/* 1224 */         sql.append(" INTERSECT ");
/*      */       }
/* 1226 */       if (useWildcard) {
/* 1227 */         sql.append(DBMS.getSQL(this.db, "SELECT ECUID FROM SPS_ECUSoftware WHERE IdentType = ? AND IdentValue = ?"));
/*      */       } else {
/* 1229 */         sql.append(DBMS.getSQL(this.db, "SELECT ECUID FROM SPS_ECUSoftware WHERE IdentType = ? AND (IdentValue = ? OR IdentValue = '*')"));
/*      */       } 
/*      */     } 
/* 1232 */     return sql.toString();
/*      */   }
/*      */   
/*      */   protected boolean skipPreEvaluation(SPSSession session, SPSVehicle vehicle, VIT1Data vit1) throws Exception {
/* 1236 */     Integer device = ((SPSControllerReference)session.getControllerReference()).getSystemTypeID();
/* 1237 */     if (device == null) {
/* 1238 */       return false;
/*      */     }
/* 1240 */     List vids = getVehicles(vehicle, device);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1246 */     if (vids == null) {
/* 1247 */       return false;
/*      */     }
/* 1249 */     List<SPSVehicleCategory> vcategories = SPSVehicleCategory.loadVehicleCategories(this.db, (SPSLanguage)session.getLanguage(), vids, this.adapter);
/* 1250 */     if (vcategories == null) {
/* 1251 */       return false;
/*      */     }
/* 1253 */     Set<Integer> rngMatch = new HashSet();
/* 1254 */     Set<Integer> vinMatch = new HashSet();
/* 1255 */     Set<Integer> v10Match = new HashSet();
/* 1256 */     for (int i = 0; i < vcategories.size(); i++) {
/* 1257 */       SPSVehicleCategory vcategory = vcategories.get(i);
/* 1258 */       if (vcategory.getNoPreEval() <= 0) {
/*      */ 
/*      */         
/* 1261 */         SPSOptionGroup group = vcategory.getGroup();
/* 1262 */         if (vcategory.getCategory().getID().equals("RNG")) {
/* 1263 */           if (group != null)
/*      */           {
/*      */             
/* 1266 */             if (group.matchNullVINRange((SPSVIN)vehicle.getVIN()))
/* 1267 */               rngMatch.add(Integer.valueOf(vcategory.getVehicle())); 
/*      */           }
/* 1269 */         } else if (vcategory.getCategory().getID().equals("VIN")) {
/* 1270 */           if (group != null)
/*      */           {
/*      */             
/* 1273 */             if (group.matchVINCode((SPSVIN)vehicle.getVIN()))
/* 1274 */               vinMatch.add(Integer.valueOf(vcategory.getVehicle())); 
/*      */           }
/* 1276 */         } else if (vcategory.getCategory().getID().equals("V10") && 
/* 1277 */           group != null) {
/*      */ 
/*      */           
/* 1280 */           if (group.matchV10Code((SPSVIN)vehicle.getVIN()))
/* 1281 */             v10Match.add(Integer.valueOf(vcategory.getVehicle())); 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1285 */     rngMatch.retainAll(vinMatch);
/* 1286 */     if (!v10Match.isEmpty()) {
/* 1287 */       rngMatch.retainAll(v10Match);
/*      */     }
/* 1289 */     if (rngMatch.size() == 1) {
/*      */ 
/*      */       
/* 1292 */       int vid = ((Integer)rngMatch.iterator().next()).intValue();
/* 1293 */       log.debug("vit1-identified vid=" + vid);
/* 1294 */       for (int j = 0; j < vcategories.size(); j++) {
/* 1295 */         SPSVehicleCategory vcategory = vcategories.get(j);
/* 1296 */         if (vcategory.getVehicle() == vid && vcategory.isOptionCategory()) {
/* 1297 */           SPSOptionGroup group = vcategory.getGroup();
/* 1298 */           if (group.getOptions() != null && group.getOptions().size() == 1) {
/* 1299 */             setDefaultOption(session, vehicle, vcategory, group.getOptions().get(0));
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/* 1304 */     return (rngMatch.size() == 1);
/*      */   }
/*      */   
/*      */   protected List getVehicles(SPSVehicle vehicle, Integer device) throws Exception {
/* 1308 */     List<Integer> vehicles = new ArrayList();
/* 1309 */     IDatabaseLink db = this.adapter.getDatabaseLink();
/* 1310 */     Connection conn = null;
/* 1311 */     DBMS.PreparedStatement stmt = null;
/* 1312 */     ResultSet rs = null;
/*      */     try {
/* 1314 */       conn = db.requestConnection();
/* 1315 */       String sql = DBMS.getSQL(db, "SELECT v.VehicleID FROM SPS_Vehicle v WHERE v.SalesMakeCode = ?  AND v.ModelCode = ?  AND v.ModelYearCode = ?");
/* 1316 */       sql = sql + "  AND v.DeviceID = ?";
/* 1317 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 1318 */       stmt.setInt(1, vehicle.getSalesMakeID().intValue());
/* 1319 */       stmt.setInt(2, vehicle.getModelID().intValue());
/* 1320 */       stmt.setInt(3, vehicle.getModelYearID().intValue());
/* 1321 */       stmt.setInt(4, device.intValue());
/* 1322 */       rs = stmt.executeQuery();
/* 1323 */       while (rs.next()) {
/* 1324 */         vehicles.add(Integer.valueOf(rs.getInt(1)));
/*      */       }
/* 1326 */     } catch (Exception e) {
/* 1327 */       throw e;
/*      */     } finally {
/*      */       try {
/* 1330 */         if (rs != null) {
/* 1331 */           rs.close();
/*      */         }
/* 1333 */         if (stmt != null) {
/* 1334 */           stmt.close();
/*      */         }
/* 1336 */         if (conn != null) {
/* 1337 */           db.releaseConnection(conn);
/*      */         }
/* 1339 */       } catch (Exception x) {}
/*      */     } 
/*      */     
/* 1342 */     return (vehicles.size() == 0) ? null : vehicles;
/*      */   }
/*      */   
/*      */   protected List getVehicleList(int ecu) throws Exception {
/* 1346 */     List<Integer> vehicles = new ArrayList();
/* 1347 */     Connection conn = null;
/* 1348 */     DBMS.PreparedStatement stmt = null;
/* 1349 */     ResultSet rs = null;
/*      */     try {
/* 1351 */       conn = this.db.requestConnection();
/* 1352 */       String sql = DBMS.getSQL(this.db, "SELECT VehicleID FROM SPS_Configuration WHERE ECUID = ?");
/* 1353 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 1354 */       stmt.setInt(1, ecu);
/* 1355 */       rs = stmt.executeQuery();
/* 1356 */       while (rs.next()) {
/* 1357 */         vehicles.add(Integer.valueOf(rs.getInt(1)));
/*      */       }
/* 1359 */     } catch (Exception e) {
/* 1360 */       throw e;
/*      */     } finally {
/*      */       try {
/* 1363 */         if (rs != null) {
/* 1364 */           rs.close();
/*      */         }
/* 1366 */         if (stmt != null) {
/* 1367 */           stmt.close();
/*      */         }
/* 1369 */         if (conn != null) {
/* 1370 */           this.db.releaseConnection(conn);
/*      */         }
/* 1372 */       } catch (Exception x) {}
/*      */     } 
/*      */     
/* 1375 */     return (vehicles.size() == 0) ? null : vehicles;
/*      */   }
/*      */   
/*      */   public List getVehicleListVCI(int vci) throws Exception {
/* 1379 */     List<Integer> vehicles = new ArrayList();
/* 1380 */     Connection conn = null;
/* 1381 */     DBMS.PreparedStatement stmt = null;
/* 1382 */     ResultSet rs = null;
/*      */     try {
/* 1384 */       conn = this.db.requestConnection();
/* 1385 */       String sql = DBMS.getSQL(this.db, "SELECT v.VehicleID FROM SPS_AsBuilt_Vehicle v WHERE v.VCI = ?");
/* 1386 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 1387 */       stmt.setInt(1, vci);
/* 1388 */       rs = stmt.executeQuery();
/* 1389 */       while (rs.next()) {
/* 1390 */         vehicles.add(Integer.valueOf(rs.getInt(1)));
/*      */       }
/* 1392 */     } catch (Exception e) {
/* 1393 */       throw e;
/*      */     } finally {
/*      */       try {
/* 1396 */         if (rs != null) {
/* 1397 */           rs.close();
/*      */         }
/* 1399 */         if (stmt != null) {
/* 1400 */           stmt.close();
/*      */         }
/* 1402 */         if (conn != null) {
/* 1403 */           this.db.releaseConnection(conn);
/*      */         }
/* 1405 */       } catch (Exception x) {}
/*      */     } 
/*      */     
/* 1408 */     return (vehicles.size() == 0) ? null : vehicles;
/*      */   }
/*      */   
/*      */   int lookupAsBuiltVINID(SPSSession session, int ecu) {
/* 1412 */     if (session.getController() != null) {
/* 1413 */       return ((SPSControllerGME)session.getController()).getVINID();
/*      */     }
/* 1415 */     return ((SPSControllerReference)session.getControllerReference()).getVINID(ecu);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected List getVehicleID(SPSSession session, int ecu) throws Exception {
/* 1421 */     List<Integer> vehicles = new ArrayList();
/* 1422 */     Connection conn = null;
/* 1423 */     DBMS.PreparedStatement stmt = null;
/* 1424 */     ResultSet rs = null;
/*      */     try {
/* 1426 */       conn = this.db.requestConnection();
/* 1427 */       String sql = DBMS.getSQL(this.db, "SELECT DISTINCT v.VehicleID FROM SPS_AsBuilt_Vehicle v, SPS_AsBuilt_VCI s, SPS_Configuration c WHERE s.VINID = ?  AND c.ECUID = ?  AND v.VCI = s.VCI  AND v.VehicleID = c.VehicleID");
/* 1428 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 1429 */       stmt.setInt(1, lookupAsBuiltVINID(session, ecu));
/* 1430 */       stmt.setInt(2, ecu);
/* 1431 */       rs = stmt.executeQuery();
/* 1432 */       while (rs.next()) {
/* 1433 */         vehicles.add(Integer.valueOf(rs.getInt(1)));
/*      */       }
/* 1435 */     } catch (Exception e) {
/* 1436 */       throw e;
/*      */     } finally {
/*      */       try {
/* 1439 */         if (rs != null) {
/* 1440 */           rs.close();
/*      */         }
/* 1442 */         if (stmt != null) {
/* 1443 */           stmt.close();
/*      */         }
/* 1445 */         if (conn != null) {
/* 1446 */           this.db.releaseConnection(conn);
/*      */         }
/* 1448 */       } catch (Exception x) {}
/*      */     } 
/*      */     
/* 1451 */     return (vehicles.size() == 0) ? null : vehicles;
/*      */   }
/*      */   
/*      */   protected List getVehicleID(SPSVehicle vehicle) throws Exception {
/* 1455 */     List<Integer> vehicles = new ArrayList();
/* 1456 */     Connection conn = null;
/* 1457 */     DBMS.PreparedStatement stmt = null;
/* 1458 */     ResultSet rs = null;
/*      */     try {
/* 1460 */       conn = this.db.requestConnection();
/* 1461 */       String sql = DBMS.getSQL(this.db, "SELECT v.VehicleID FROM SPS_Vehicle v WHERE v.SalesMakeCode = ?  AND v.ModelCode = ?  AND v.ModelYearCode = ?");
/* 1462 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 1463 */       stmt.setInt(1, vehicle.getSalesMakeID().intValue());
/* 1464 */       stmt.setInt(2, vehicle.getModelID().intValue());
/* 1465 */       stmt.setInt(3, vehicle.getModelYearID().intValue());
/* 1466 */       rs = stmt.executeQuery();
/* 1467 */       while (rs.next()) {
/* 1468 */         vehicles.add(Integer.valueOf(rs.getInt(1)));
/*      */       }
/* 1470 */     } catch (Exception e) {
/* 1471 */       throw e;
/*      */     } finally {
/*      */       try {
/* 1474 */         if (rs != null) {
/* 1475 */           rs.close();
/*      */         }
/* 1477 */         if (stmt != null) {
/* 1478 */           stmt.close();
/*      */         }
/* 1480 */         if (conn != null) {
/* 1481 */           this.db.releaseConnection(conn);
/*      */         }
/* 1483 */       } catch (Exception x) {}
/*      */     } 
/*      */ 
/*      */     
/* 1487 */     return vehicles;
/*      */   }
/*      */   
/*      */   protected List checkAsBuiltControllerFunctions(SPSSession session, List devices) throws Exception {
/* 1491 */     if (!hasAsBuiltSupport()) {
/* 1492 */       return null;
/*      */     }
/* 1494 */     Integer vinid = ((SPSSession)session).getAsBuiltVINID();
/* 1495 */     if (!vinid.equals(NO_ASBUILT_VINID)) {
/* 1496 */       Connection conn = null;
/* 1497 */       DBMS.PreparedStatement stmt = null;
/* 1498 */       ResultSet rs = null;
/* 1499 */       List<SPSControllerGME> controllers = new ArrayList();
/*      */       try {
/* 1501 */         conn = this.db.requestConnection();
/* 1502 */         String sql = DBMS.getSQL(this.db, "SELECT v.VehicleID, s.DeviceID, s.VCI, c.ECUID, s.FunctionID FROM SPS_AsBuilt_Vehicle v left outer join SPS_Configuration c on v.VehicleID = c.VehicleID, SPS_AsBuilt_VCI s WHERE s.VINID = ? AND s.VCI = v.VCI");
/* 1503 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/* 1504 */         stmt.setInt(1, vinid.intValue());
/* 1505 */         rs = stmt.executeQuery();
/* 1506 */         while (rs.next()) {
/* 1507 */           Integer vehicleID = new Integer(rs.getInt(1));
/* 1508 */           Integer deviceID = new Integer(rs.getInt(2));
/* 1509 */           Integer vci = new Integer(rs.getInt(3));
/* 1510 */           Integer ecu = new Integer(rs.getInt(4));
/* 1511 */           Integer functionID = new Integer(rs.getInt(5));
/* 1512 */           if (!acceptDevice(devices, deviceID)) {
/*      */             continue;
/*      */           }
/* 1515 */           SPSControllerGME controller = null;
/* 1516 */           if (XMLSupport.hasXMLSupport(this.adapter, this.db, conn, functionID.intValue())) {
/* 1517 */             controller = new SPSControllerFunctionXML(session, vehicleID, functionID, deviceID, ecu, this.adapter);
/*      */           } else {
/* 1519 */             controller = new SPSControllerFunctionGME(session, vehicleID, functionID, deviceID, ecu, this.adapter);
/*      */           } 
/* 1521 */           controller.flagAsBuiltController();
/* 1522 */           controller.setVINID(vinid.intValue());
/* 1523 */           controller.setVCI(vci.intValue());
/* 1524 */           controllers.add(controller);
/*      */         } 
/* 1526 */         return (controllers.size() > 0) ? controllers : null;
/* 1527 */       } catch (Exception e) {
/* 1528 */         throw e;
/*      */       } finally {
/*      */         try {
/* 1531 */           if (rs != null) {
/* 1532 */             rs.close();
/*      */           }
/* 1534 */           if (stmt != null) {
/* 1535 */             stmt.close();
/*      */           }
/* 1537 */           if (conn != null) {
/* 1538 */             this.db.releaseConnection(conn);
/*      */           }
/* 1540 */         } catch (Exception x) {}
/*      */       } 
/*      */     } 
/*      */     
/* 1544 */     return null;
/*      */   }
/*      */   
/*      */   protected List checkAsBuiltControllers(SPSSession session, List devices) throws Exception {
/* 1548 */     if (!hasAsBuiltSupport()) {
/* 1549 */       return null;
/*      */     }
/* 1551 */     Integer vinid = ((SPSSession)session).getAsBuiltVINID();
/* 1552 */     if (!vinid.equals(NO_ASBUILT_VINID)) {
/* 1553 */       Connection conn = null;
/* 1554 */       DBMS.PreparedStatement stmt = null;
/* 1555 */       ResultSet rs = null;
/* 1556 */       List<SPSControllerGME> controllers = new ArrayList();
/*      */       try {
/* 1558 */         conn = this.db.requestConnection();
/* 1559 */         String sql = DBMS.getSQL(this.db, "SELECT v.VehicleID, s.DeviceID, s.ControllerID, s.VCI, c.ECUID FROM SPS_AsBuilt_Vehicle v, SPS_AsBuilt_VCI s, SPS_Configuration c WHERE s.VINID = ? AND s.VCI = v.VCI AND v.VehicleID = c.VehicleID (+)");
/* 1560 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/* 1561 */         stmt.setInt(1, vinid.intValue());
/* 1562 */         rs = stmt.executeQuery();
/* 1563 */         while (rs.next()) {
/* 1564 */           Integer vehicleID = Integer.valueOf(rs.getInt(1));
/* 1565 */           Integer deviceID = Integer.valueOf(rs.getInt(2));
/* 1566 */           Integer controllerID = Integer.valueOf(rs.getInt(3));
/* 1567 */           Integer vci = Integer.valueOf(rs.getInt(4));
/* 1568 */           Integer ecu = Integer.valueOf(rs.getInt(5));
/* 1569 */           if (!acceptDevice(devices, deviceID)) {
/*      */             continue;
/*      */           }
/* 1572 */           SPSControllerGME controller = null;
/* 1573 */           if (XMLSupport.hasXMLSupport(this.adapter, this.db, conn, controllerID.intValue())) {
/* 1574 */             controller = new SPSControllerXML(session, vehicleID, controllerID, deviceID, ecu, this.adapter);
/*      */           } else {
/* 1576 */             controller = new SPSControllerGME(session, vehicleID, controllerID, deviceID, ecu, this.adapter);
/*      */           } 
/* 1578 */           controller.flagAsBuiltController();
/* 1579 */           controller.setVINID(vinid.intValue());
/* 1580 */           controller.setVCI(vci.intValue());
/* 1581 */           controllers.add(controller);
/*      */         } 
/* 1583 */         return (controllers.size() > 0) ? controllers : null;
/* 1584 */       } catch (Exception e) {
/* 1585 */         throw e;
/*      */       } finally {
/*      */         try {
/* 1588 */           if (rs != null) {
/* 1589 */             rs.close();
/*      */           }
/* 1591 */           if (stmt != null) {
/* 1592 */             stmt.close();
/*      */           }
/* 1594 */           if (conn != null) {
/* 1595 */             this.db.releaseConnection(conn);
/*      */           }
/* 1597 */         } catch (Exception x) {}
/*      */       } 
/*      */     } 
/*      */     
/* 1601 */     return null;
/*      */   }
/*      */   
/*      */   protected SPSControllerGME checkAsBuiltController(List controllers, SPSControllerGME controller) {
/* 1605 */     if (controllers == null) {
/* 1606 */       return controller;
/*      */     }
/* 1608 */     Iterator<SPSControllerGME> it = controllers.iterator();
/* 1609 */     while (it.hasNext()) {
/* 1610 */       SPSControllerGME asbuilt = it.next();
/* 1611 */       if (asbuilt.match(controller)) {
/* 1612 */         log.debug("replace asbuilt: " + asbuilt);
/* 1613 */         it.remove();
/* 1614 */         return asbuilt;
/*      */       } 
/*      */     } 
/* 1617 */     return controller;
/*      */   }
/*      */   
/*      */   protected void insertAsBuiltControllers(SPSControllerList controllers, List asbuilt) {
/* 1621 */     if (asbuilt != null) {
/* 1622 */       Iterator<SPSControllerGME> it = asbuilt.iterator();
/* 1623 */       while (it.hasNext()) {
/* 1624 */         SPSControllerGME controller = it.next();
/* 1625 */         log.debug("add asbuilt: " + controller);
/* 1626 */         controllers.insert(controller, this.adapter);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public SPSControllerList prepareControllerList(SPSSession session, SPSControllerList controllers) throws Exception {
/* 1632 */     if (controllers.size() == 0) {
/* 1633 */       return null;
/*      */     }
/* 1635 */     List<Integer> vehicles = new ArrayList();
/* 1636 */     for (int j = 0; j < controllers.size(); j++) {
/* 1637 */       SPSControllerReference reference = (SPSControllerReference)controllers.get(j);
/* 1638 */       List<SPSControllerGME> members = ((SPSControllerReference)reference).getControllers();
/* 1639 */       for (int k = 0; k < members.size(); k++) {
/* 1640 */         SPSControllerGME controller = members.get(k);
/* 1641 */         Integer vehicleID = controller.getVehicleID();
/* 1642 */         if (!vehicles.contains(vehicleID)) {
/* 1643 */           vehicles.add(vehicleID);
/*      */         }
/*      */       } 
/*      */     } 
/* 1647 */     List<SPSVehicleCategory> vcategories = SPSVehicleCategory.loadVehicleCategories(this.db, (SPSLanguage)session.getLanguage(), vehicles, this.adapter);
/*      */ 
/*      */     
/* 1650 */     if (vcategories == null) {
/* 1651 */       return (controllers.size() == 0) ? null : controllers;
/*      */     }
/*      */ 
/*      */     
/* 1655 */     for (int i = 0; i < vcategories.size(); i++) {
/* 1656 */       SPSVehicleCategory vcategory = vcategories.get(i);
/* 1657 */       int vid = vcategory.getVehicle();
/* 1658 */       for (int k = 0; k < controllers.size(); k++) {
/* 1659 */         SPSControllerReference reference = (SPSControllerReference)controllers.get(k);
/* 1660 */         List<SPSControllerGME> members = ((SPSControllerReference)reference).getControllers();
/* 1661 */         for (int m = 0; m < members.size(); m++) {
/* 1662 */           SPSControllerGME controller = members.get(m);
/* 1663 */           if (vid == controller.getVehicleID().intValue()) {
/* 1664 */             controller.register(vcategory);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/* 1669 */     return (controllers.size() == 0) ? null : controllers;
/*      */   }
/*      */   
/*      */   public static boolean acceptDevice(List<Integer> devices, Integer system) {
/* 1673 */     if (devices == null) {
/* 1674 */       return true;
/*      */     }
/* 1676 */     for (int i = 0; i < devices.size(); i++) {
/* 1677 */       Integer device = devices.get(i);
/* 1678 */       if (device.equals(system)) {
/* 1679 */         return true;
/*      */       }
/*      */     } 
/* 1682 */     return false;
/*      */   }
/*      */   
/*      */   protected SPSControllerList getVehicleControllerFunctions(SPSSession session, List devices) throws Exception {
/* 1686 */     List asbuilt = checkAsBuiltControllerFunctions(session, devices);
/* 1687 */     SPSControllerList controllers = new SPSControllerList(session);
/*      */     
/* 1689 */     Connection conn = null;
/* 1690 */     DBMS.PreparedStatement stmt = null;
/* 1691 */     ResultSet rs = null;
/*      */     try {
/* 1693 */       conn = this.db.requestConnection();
/* 1694 */       String sql = DBMS.getSQL(this.db, "SELECT v.VehicleID, v.DeviceID, v.FunctionID, c.ECUID FROM SPS_Configuration c, SPS_Vehicle v WHERE v.VehicleID = c.VehicleID (+)  AND v.SalesMakeCode = ?  AND v.ModelCode = ?  AND v.ModelYearCode = ?");
/* 1695 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 1696 */       SPSVehicle vehicle = (SPSVehicle)session.getVehicle();
/* 1697 */       stmt.setInt(1, vehicle.getSalesMakeID().intValue());
/* 1698 */       stmt.setInt(2, vehicle.getModelID().intValue());
/* 1699 */       stmt.setInt(3, vehicle.getModelYearID().intValue());
/* 1700 */       rs = stmt.executeQuery();
/* 1701 */       while (rs.next()) {
/* 1702 */         Integer vehicleID = new Integer(rs.getInt(1));
/* 1703 */         Integer deviceID = new Integer(rs.getInt(2));
/* 1704 */         Integer functionID = new Integer(rs.getInt(3));
/* 1705 */         Integer ecu = new Integer(rs.getInt(4));
/* 1706 */         if (!acceptDevice(devices, deviceID)) {
/*      */           continue;
/*      */         }
/* 1709 */         if (!DUMMY_ECU.equals(ecu) && 
/* 1710 */           !SPSECU.Provider.getInstance(this.adapter).accept(ecu, vehicle.getModelYear())) {
/*      */           continue;
/*      */         }
/*      */         
/* 1714 */         SPSControllerGME controller = null;
/* 1715 */         if (XMLSupport.hasXMLSupport(this.adapter, this.db, conn, functionID.intValue())) {
/* 1716 */           controller = new SPSControllerFunctionXML(session, vehicleID, functionID, deviceID, ecu, this.adapter);
/*      */         } else {
/* 1718 */           controller = new SPSControllerFunctionGME(session, vehicleID, functionID, deviceID, ecu, this.adapter);
/*      */         } 
/* 1720 */         controller = checkAsBuiltController(asbuilt, controller);
/* 1721 */         controllers.insert(controller, this.adapter);
/*      */       } 
/* 1723 */       insertAsBuiltControllers(controllers, asbuilt);
/* 1724 */       return prepareControllerList(session, controllers);
/* 1725 */     } catch (Exception e) {
/* 1726 */       log.error("failed to create controller list (vin=" + session.getVehicle().getVIN() + ")");
/* 1727 */       throw e;
/*      */     } finally {
/*      */       try {
/* 1730 */         if (rs != null) {
/* 1731 */           rs.close();
/*      */         }
/* 1733 */         if (stmt != null) {
/* 1734 */           stmt.close();
/*      */         }
/* 1736 */         if (conn != null) {
/* 1737 */           this.db.releaseConnection(conn);
/*      */         }
/* 1739 */       } catch (Exception x) {}
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected SPSControllerList getVehicleControllerSequences(SPSSession session) throws Exception {
/* 1745 */     SPSControllerList controllers = new SPSControllerList(session);
/* 1746 */     Connection conn = null;
/* 1747 */     DBMS.PreparedStatement stmt = null;
/* 1748 */     ResultSet rs = null;
/*      */     try {
/* 1750 */       conn = this.db.requestConnection();
/* 1751 */       String sql = DBMS.getSQL(this.db, "SELECT v.BaseVehicleID, v.ControllerID, v.SequenceID, v.WMI, v.VDS, v.VIS, o.CategoryCode, o.OptionGroup, o.OptionOrder FROM SPS_BaseVehicle v left outer join SPS_BaseVehOption o on v.BaseVehicleID = o.BaseVehicleID WHERE v.SalesMakeCode = ? AND v.ModelCode = ? AND v.ModelYearCode = ?");
/* 1752 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 1753 */       SPSVehicle vehicle = (SPSVehicle)session.getVehicle();
/* 1754 */       stmt.setInt(1, vehicle.getSalesMakeID().intValue());
/* 1755 */       stmt.setInt(2, vehicle.getModelID().intValue());
/* 1756 */       stmt.setInt(3, vehicle.getModelYearID().intValue());
/* 1757 */       rs = stmt.executeQuery();
/* 1758 */       while (rs.next()) {
/* 1759 */         Integer baseVehicleID = new Integer(rs.getInt(1));
/* 1760 */         Integer controllerID = new Integer(rs.getInt(2));
/* 1761 */         Integer sequenceID = new Integer(rs.getInt(3));
/* 1762 */         String wmi = DBMS.trimString(rs.getString(4));
/* 1763 */         String vds = DBMS.trimString(rs.getString(5));
/* 1764 */         String vis = DBMS.trimString(rs.getString(6));
/* 1765 */         SPSVINCode pattern = new SPSVINCode(wmi, vds, vis);
/* 1766 */         if (!SPSRequestInfoCategory.accept(vehicle, pattern)) {
/*      */           continue;
/*      */         }
/* 1769 */         SPSControllerSequenceGME controller = null;
/* 1770 */         for (int i = 0; controller == null && i < controllers.size(); i++) {
/* 1771 */           SPSControllerReference ref = (SPSControllerReference)controllers.get(i);
/* 1772 */           List<SPSControllerSequenceGME> instances = ref.getControllers();
/* 1773 */           for (int j = 0; j < instances.size(); j++) {
/* 1774 */             SPSControllerSequenceGME candidate = instances.get(j);
/* 1775 */             if (baseVehicleID.equals(candidate.getBaseVehicleID())) {
/* 1776 */               controller = candidate;
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/* 1781 */         if (controller == null) {
/* 1782 */           controller = new SPSControllerSequenceGME(session, baseVehicleID, controllerID, sequenceID, this.adapter);
/* 1783 */           controllers.insert(controller, this.adapter);
/*      */         } 
/* 1785 */         String categoryID = DBMS.trimString(rs.getString(7));
/* 1786 */         if (!rs.wasNull()) {
/* 1787 */           SPSOptionCategory category = SPSOptionCategory.getOptionCategory(categoryID, this.adapter);
/* 1788 */           int groupID = rs.getInt(8);
/* 1789 */           SPSOptionGroup group = SPSOptionGroup.getOptionGroup(categoryID, groupID, this.adapter);
/* 1790 */           if (group == null) {
/* 1791 */             log.error("unknown option group (base vehicle option): '" + groupID + "'.");
/*      */             continue;
/*      */           } 
/* 1794 */           int order = rs.getInt(9);
/* 1795 */           SPSLanguage language = (SPSLanguage)session.getLanguage();
/* 1796 */           SPSBaseCategory bcategory = new SPSBaseCategory(language, baseVehicleID.intValue(), category, group, order);
/* 1797 */           controller.add(bcategory);
/*      */         } 
/*      */       } 
/* 1800 */       return controllers;
/* 1801 */     } catch (Exception e) {
/* 1802 */       log.error("failed to create controller list (vin=" + session.getVehicle().getVIN() + ")");
/* 1803 */       throw e;
/*      */     } finally {
/*      */       try {
/* 1806 */         if (rs != null) {
/* 1807 */           rs.close();
/*      */         }
/* 1809 */         if (stmt != null) {
/* 1810 */           stmt.close();
/*      */         }
/* 1812 */         if (conn != null) {
/* 1813 */           this.db.releaseConnection(conn);
/*      */         }
/* 1815 */       } catch (Exception x) {}
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected SPSControllerList getVehicleControllers(SPSSession session, List devices) throws Exception {
/* 1821 */     List asbuilt = checkAsBuiltControllers(session, devices);
/* 1822 */     SPSControllerList controllers = new SPSControllerList(session);
/* 1823 */     session.getVehicle().getVIT1();
/* 1824 */     Connection conn = null;
/* 1825 */     DBMS.PreparedStatement stmt = null;
/* 1826 */     ResultSet rs = null;
/*      */     try {
/* 1828 */       conn = this.db.requestConnection();
/* 1829 */       String sql = DBMS.getSQL(this.db, "SELECT v.VehicleID, v.DeviceID, v.ControllerID, c.ECUID FROM SPS_Configuration c, SPS_Vehicle v WHERE v.VehicleID = c.VehicleID (+)  AND v.SalesMakeCode = ?  AND v.ModelCode = ?  AND v.ModelYearCode = ?");
/* 1830 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 1831 */       SPSVehicle vehicle = (SPSVehicle)session.getVehicle();
/* 1832 */       stmt.setInt(1, vehicle.getSalesMakeID().intValue());
/* 1833 */       stmt.setInt(2, vehicle.getModelID().intValue());
/* 1834 */       stmt.setInt(3, vehicle.getModelYearID().intValue());
/* 1835 */       rs = stmt.executeQuery();
/* 1836 */       while (rs.next()) {
/*      */ 
/*      */         
/* 1839 */         Integer vehicleID = Integer.valueOf(rs.getInt(1));
/* 1840 */         Integer deviceID = Integer.valueOf(rs.getInt(2));
/* 1841 */         Integer controllerID = Integer.valueOf(rs.getInt(3));
/* 1842 */         Integer ecu = Integer.valueOf(rs.getInt(4));
/* 1843 */         if (!acceptDevice(devices, deviceID)) {
/*      */           continue;
/*      */         }
/* 1846 */         if (!DUMMY_ECU.equals(ecu) && 
/* 1847 */           !SPSECU.Provider.getInstance(this.adapter).accept(ecu, vehicle.getModelYear())) {
/*      */           continue;
/*      */         }
/*      */         
/* 1851 */         SPSControllerGME controller = null;
/* 1852 */         if (XMLSupport.hasXMLSupport(this.adapter, this.db, conn, controllerID.intValue())) {
/* 1853 */           controller = new SPSControllerXML(session, vehicleID, controllerID, deviceID, ecu, this.adapter);
/*      */         } else {
/* 1855 */           controller = new SPSControllerGME(session, vehicleID, controllerID, deviceID, ecu, this.adapter);
/*      */         } 
/* 1857 */         controller = checkAsBuiltController(asbuilt, controller);
/* 1858 */         controllers.insert(controller, this.adapter);
/*      */       } 
/* 1860 */       insertAsBuiltControllers(controllers, asbuilt);
/* 1861 */       return prepareControllerList(session, controllers);
/* 1862 */     } catch (Exception e) {
/* 1863 */       log.error("failed to create controller list (vin=" + session.getVehicle().getVIN() + ")");
/* 1864 */       throw e;
/*      */     } finally {
/*      */       try {
/* 1867 */         if (rs != null) {
/* 1868 */           rs.close();
/*      */         }
/* 1870 */         if (stmt != null) {
/* 1871 */           stmt.close();
/*      */         }
/* 1873 */         if (conn != null) {
/* 1874 */           this.db.releaseConnection(conn);
/*      */         }
/* 1876 */       } catch (Exception x) {}
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected String assembleVehicleList(List vehicles) {
/* 1882 */     StringBuffer buffer = new StringBuffer();
/* 1883 */     for (int i = 0; i < vehicles.size(); i++) {
/* 1884 */       if (i > 0) {
/* 1885 */         buffer.append(',');
/*      */       }
/* 1887 */       buffer.append(vehicles.get(i));
/*      */     } 
/* 1889 */     return buffer.toString();
/*      */   }
/*      */   
/*      */   protected SPSControllerList getVehicleControllerFunctionsVCI(SPSSession session, List vehicles) throws Exception {
/* 1893 */     SPSControllerList controllers = new SPSControllerList(session);
/* 1894 */     Connection conn = null;
/* 1895 */     DBMS.PreparedStatement stmt = null;
/* 1896 */     ResultSet rs = null;
/*      */     try {
/* 1898 */       conn = this.db.requestConnection();
/* 1899 */       String sql = DBMS.getSQL(this.db, "SELECT DISTINCT c.VehicleID, c.ECUID, i.DeviceID, i.FunctionID FROM SPS_Configuration c, SPS_ECUDescription d, SPS_AsBuilt_Vehicle v, SPS_AsBuilt_VCI i WHERE c.VehicleID IN (#VEHICLE_LIST#) AND d.ECUID = c.ECUID AND d.ServiceECUFlag = 0 AND d.ValidFrom <= ? AND d.ValidTo >= ? AND c.VehicleID = v.VehicleID AND v.VCI = i.VCI ");
/* 1900 */       sql = DBMS.replace(sql, "#VEHICLE_LIST#", assembleVehicleList(vehicles));
/* 1901 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 1902 */       SPSVehicle vehicle = (SPSVehicle)session.getVehicle();
/* 1903 */       stmt.setString(1, vehicle.getModelYear());
/* 1904 */       stmt.setString(2, vehicle.getModelYear());
/* 1905 */       rs = stmt.executeQuery();
/* 1906 */       while (rs.next()) {
/* 1907 */         Integer vehicleID = new Integer(rs.getInt(1));
/* 1908 */         Integer ecu = new Integer(rs.getInt(2));
/* 1909 */         Integer deviceID = new Integer(rs.getInt(3));
/* 1910 */         Integer functionID = new Integer(rs.getInt(4));
/* 1911 */         SPSControllerGME controller = null;
/* 1912 */         if (XMLSupport.hasXMLSupport(this.adapter, this.db, conn, functionID.intValue())) {
/* 1913 */           controller = new SPSControllerFunctionXML(session, vehicleID, functionID, deviceID, ecu, this.adapter);
/*      */         } else {
/* 1915 */           controller = new SPSControllerFunctionGME(session, vehicleID, functionID, deviceID, ecu, this.adapter);
/*      */         } 
/* 1917 */         controllers.insert(controller, this.adapter);
/*      */       } 
/* 1919 */       return prepareControllerList(session, controllers);
/* 1920 */     } catch (Exception e) {
/* 1921 */       throw e;
/*      */     } finally {
/*      */       try {
/* 1924 */         if (rs != null) {
/* 1925 */           rs.close();
/*      */         }
/* 1927 */         if (stmt != null) {
/* 1928 */           stmt.close();
/*      */         }
/* 1930 */         if (conn != null) {
/* 1931 */           this.db.releaseConnection(conn);
/*      */         }
/* 1933 */       } catch (Exception x) {}
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected SPSControllerList getVehicleControllersVCI(SPSSession session, List vehicles) throws Exception {
/* 1939 */     SPSControllerList controllers = new SPSControllerList(session);
/* 1940 */     Connection conn = null;
/* 1941 */     DBMS.PreparedStatement stmt = null;
/* 1942 */     ResultSet rs = null;
/*      */     try {
/* 1944 */       conn = this.db.requestConnection();
/* 1945 */       String sql = DBMS.getSQL(this.db, "SELECT DISTINCT c.VehicleID, c.ECUID, i.DeviceID, i.ControllerID FROM SPS_Configuration c, SPS_ECUDescription d, SPS_AsBuilt_Vehicle v, SPS_AsBuilt_VCI i WHERE c.VehicleID IN (#VEHICLE_LIST#) AND d.ECUID = c.ECUID AND d.ServiceECUFlag = 0 AND d.ValidFrom <= ? AND d.ValidTo >= ? AND c.VehicleID = v.VehicleID AND v.VCI = i.VCI ");
/* 1946 */       sql = DBMS.replace(sql, "#VEHICLE_LIST#", assembleVehicleList(vehicles));
/* 1947 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 1948 */       SPSVehicle vehicle = (SPSVehicle)session.getVehicle();
/* 1949 */       stmt.setString(1, vehicle.getModelYear());
/* 1950 */       stmt.setString(2, vehicle.getModelYear());
/* 1951 */       rs = stmt.executeQuery();
/* 1952 */       while (rs.next()) {
/* 1953 */         Integer vehicleID = Integer.valueOf(rs.getInt(1));
/* 1954 */         Integer ecu = Integer.valueOf(rs.getInt(2));
/* 1955 */         Integer deviceID = Integer.valueOf(rs.getInt(3));
/* 1956 */         Integer controllerID = Integer.valueOf(rs.getInt(4));
/* 1957 */         SPSControllerGME controller = null;
/* 1958 */         if (XMLSupport.hasXMLSupport(this.adapter, this.db, conn, controllerID.intValue())) {
/* 1959 */           controller = new SPSControllerXML(session, vehicleID, controllerID, deviceID, ecu, this.adapter);
/*      */         } else {
/* 1961 */           controller = new SPSControllerGME(session, vehicleID, controllerID, deviceID, ecu, this.adapter);
/*      */         } 
/* 1963 */         controllers.insert(controller, this.adapter);
/*      */       } 
/* 1965 */       return prepareControllerList(session, controllers);
/* 1966 */     } catch (Exception e) {
/* 1967 */       throw e;
/*      */     } finally {
/*      */       try {
/* 1970 */         if (rs != null) {
/* 1971 */           rs.close();
/*      */         }
/* 1973 */         if (stmt != null) {
/* 1974 */           stmt.close();
/*      */         }
/* 1976 */         if (conn != null) {
/* 1977 */           this.db.releaseConnection(conn);
/*      */         }
/* 1979 */       } catch (Exception x) {}
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean needsProgrammingVCI(SPSSession session) throws Exception {
/* 1985 */     VIT1Data vit1 = session.getVehicle().getVIT1();
/* 1986 */     Connection conn = null;
/* 1987 */     DBMS.PreparedStatement stmt = null;
/* 1988 */     ResultSet rs = null;
/*      */     try {
/* 1990 */       conn = this.db.requestConnection();
/* 1991 */       String sql = DBMS.getSQL(this.db, "SELECT v.VehicleID FROM   SPS_Vehicle v WHERE  v.SalesMakeCode = ? AND    v.ModelCode = ? AND    v.ModelYearCode = ? AND    v.DeviceID = ? INTERSECT SELECT c.VehicleID FROM   SPS_Configuration c");
/* 1992 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 1993 */       SPSVehicle vehicle = (SPSVehicle)session.getVehicle();
/* 1994 */       stmt.setInt(1, vehicle.getSalesMakeID().intValue());
/* 1995 */       stmt.setInt(2, vehicle.getModelID().intValue());
/* 1996 */       stmt.setInt(3, vehicle.getModelYearID().intValue());
/* 1997 */       stmt.setInt(4, vit1.getECUAdress());
/* 1998 */       rs = stmt.executeQuery();
/* 1999 */       if (rs.next()) {
/* 2000 */         return false;
/*      */       }
/* 2002 */       return true;
/*      */     }
/* 2004 */     catch (Exception e) {
/* 2005 */       throw e;
/*      */     } finally {
/*      */       try {
/* 2008 */         if (rs != null) {
/* 2009 */           rs.close();
/*      */         }
/* 2011 */         if (stmt != null) {
/* 2012 */           stmt.close();
/*      */         }
/* 2014 */         if (conn != null) {
/* 2015 */           this.db.releaseConnection(conn);
/*      */         }
/* 2017 */       } catch (Exception x) {}
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void checkDatabaseTable(Connection conn, String table, Integer code) throws Exception {
/* 2023 */     DBMS.PreparedStatement stmt = null;
/* 2024 */     ResultSet rs = null;
/*      */     try {
/* 2026 */       String sql = "SELECT count(*) FROM " + table;
/* 2027 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 2028 */       rs = stmt.executeQuery();
/* 2029 */       if (rs.next()) {
/* 2030 */         int rows = rs.getInt(1);
/* 2031 */         if (code.intValue() == (rows * 2 + table.length()) % 10) {
/*      */           return;
/*      */         }
/*      */       } 
/* 2035 */       log.error("Exception: Database Security Violated");
/* 2036 */       throw new SPSException(CommonException.DatabaseSecurityViolated);
/* 2037 */     } catch (Exception e) {
/* 2038 */       throw e;
/*      */     } finally {
/*      */       try {
/* 2041 */         if (rs != null) {
/* 2042 */           rs.close();
/*      */         }
/* 2044 */         if (stmt != null) {
/* 2045 */           stmt.close();
/*      */         }
/* 2047 */       } catch (Exception x) {}
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void checkDatabase() throws Exception {
/* 2053 */     Map<Object, Object> checks = new HashMap<Object, Object>();
/* 2054 */     Connection conn = null;
/* 2055 */     DBMS.PreparedStatement stmt = null;
/* 2056 */     ResultSet rs = null;
/*      */     try {
/* 2058 */       conn = this.db.requestConnection();
/* 2059 */       String sql = DBMS.getSQL(this.db, "SELECT TabName, Info FROM SPS_General");
/* 2060 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 2061 */       rs = stmt.executeQuery();
/* 2062 */       while (rs.next()) {
/* 2063 */         String table = rs.getString(1).trim();
/* 2064 */         Integer code = Integer.valueOf(rs.getInt(2));
/* 2065 */         checks.put(table, code);
/*      */       } 
/* 2067 */       rs.close();
/* 2068 */       rs = null;
/* 2069 */       stmt.close();
/* 2070 */       stmt = null;
/* 2071 */       if (checks.size() > 0) {
/* 2072 */         Iterator<String> it = checks.keySet().iterator();
/* 2073 */         while (it.hasNext()) {
/* 2074 */           String table = it.next();
/* 2075 */           checkDatabaseTable(conn, table, (Integer)checks.get(table));
/*      */         } 
/*      */       } 
/* 2078 */     } catch (Exception e) {
/* 2079 */       throw e;
/*      */     } finally {
/*      */       try {
/* 2082 */         if (rs != null) {
/* 2083 */           rs.close();
/*      */         }
/* 2085 */         if (stmt != null) {
/* 2086 */           stmt.close();
/*      */         }
/* 2088 */         if (conn != null) {
/* 2089 */           this.db.releaseConnection(conn);
/*      */         }
/* 2091 */       } catch (Exception x) {}
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected String getMakeDisplay(String make) {
/* 2097 */     if (make == null) {
/* 2098 */       return null;
/*      */     }
/*      */     try {
/* 2101 */       List<E> makes = this.vc.getDomain("Make");
/* 2102 */       for (int i = 0; i < makes.size(); i++) {
/* 2103 */         String display = makes.get(i).toString();
/* 2104 */         if (make.equalsIgnoreCase(display)) {
/* 2105 */           return display;
/*      */         }
/*      */       } 
/* 2108 */     } catch (Exception e) {}
/*      */     
/* 2110 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCalibrationVerificationNumber(String sessionID, String partNumber) {
/* 2120 */     Connection conn = null;
/* 2121 */     DBMS.PreparedStatement stmt = null;
/* 2122 */     ResultSet rs = null;
/*      */     try {
/* 2124 */       conn = this.db.requestConnection();
/* 2125 */       String sql = DBMS.getSQL(this.db, "SELECT DISTINCT SalesMakeCode, CVN FROM SPS_CVN WHERE LOWER(PartNumber) = ?");
/* 2126 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 2127 */       stmt.setString(1, partNumber.toLowerCase(Locale.ENGLISH));
/* 2128 */       String list = "";
/* 2129 */       String result = null;
/* 2130 */       rs = stmt.executeQuery();
/* 2131 */       while (rs.next()) {
/* 2132 */         Integer smc = Integer.valueOf(rs.getInt(1));
/* 2133 */         String make = SPSVehicle.getSalesMake(smc, this.adapter);
/* 2134 */         make = getMakeDisplay(make);
/* 2135 */         if (!authorizedSalesMake(sessionID, make)) {
/*      */           continue;
/*      */         }
/* 2138 */         String cvn = DBMS.trimString(rs.getString(2));
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2143 */         if (list.length() > 0) {
/* 2144 */           list = list + ", ";
/* 2145 */           result = null;
/*      */         } else {
/* 2147 */           result = cvn;
/*      */         } 
/* 2149 */         list = list + make + '/' + cvn;
/*      */       } 
/* 2151 */       return (result != null) ? result : ((list.length() > 0) ? list : null);
/* 2152 */     } catch (Exception e) {
/* 2153 */       log.error("failed to look-up cvn (" + partNumber + ")", e);
/* 2154 */       return null;
/*      */     } finally {
/*      */       try {
/* 2157 */         if (rs != null) {
/* 2158 */           rs.close();
/*      */         }
/* 2160 */         if (stmt != null) {
/* 2161 */           stmt.close();
/*      */         }
/* 2163 */         if (conn != null) {
/* 2164 */           this.db.releaseConnection(conn);
/*      */         }
/* 2166 */       } catch (Exception x) {}
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean authorizedSalesMake(String sessionID, String make) {
/* 2172 */     boolean ret = false;
/* 2173 */     if (!Util.isNullOrEmpty(make)) {
/*      */       try {
/* 2175 */         IVCFacade vcFacade = VCFacade.getInstance(ClientContextProvider.getInstance().getContext(sessionID, false));
/* 2176 */         Make _make = VehicleConfigurationUtil.toMake(make);
/* 2177 */         ret = vcFacade.getSalesmakeDomain().contains(_make);
/* 2178 */       } catch (Exception e) {
/* 2179 */         log.warn("unable to determine authorization status of " + String.valueOf(sessionID) + " for make: " + make + ", returning false");
/*      */       } 
/*      */     }
/* 2182 */     return ret;
/*      */   }
/*      */   
/*      */   public Integer getVINID(SPSSession session) {
/* 2186 */     Connection conn = null;
/* 2187 */     DBMS.PreparedStatement stmt = null;
/* 2188 */     ResultSet rs = null;
/*      */     try {
/* 2190 */       SPSVIN vin = (SPSVIN)session.getVehicle().getVIN();
/* 2191 */       String vds = "" + vin.getPosition(4) + vin.getPosition(5) + vin.getPosition(6) + vin.getPosition(7) + vin.getPosition(8);
/* 2192 */       conn = this.db.requestConnection();
/* 2193 */       String sql = DBMS.getSQL(this.db, "select distinct VINID from SPS_AsBuilt_VIN v, SPS_AsBuilt_VINDescription d, SPS_AsBuilt_VINSerial s where v.VINDescID = d.VINDescID  and d.WMI = ?  and d.VIN_4to8 = ?  and ((d.VIN_9 = ?) or (d.VIN_9 = '?'))  and d.VIN_10 = ?  and d.VIN_11 = ?  and v.VINSerID = s.VINSerID  and s.VIN_12to17 = ?");
/* 2194 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 2195 */       stmt.setString(1, vin.getWMI());
/* 2196 */       stmt.setString(2, vds);
/* 2197 */       stmt.setString(3, DBMS.toString(vin.getPosition(9)));
/* 2198 */       stmt.setString(4, DBMS.toString(vin.getPosition(10)));
/* 2199 */       stmt.setString(5, DBMS.toString(vin.getPosition(11)));
/* 2200 */       stmt.setString(6, vin.getSequence());
/* 2201 */       rs = stmt.executeQuery();
/* 2202 */       if (rs.next()) {
/* 2203 */         return Integer.valueOf(rs.getInt(1));
/*      */       }
/* 2205 */     } catch (Exception e) {
/*      */     
/*      */     } finally {
/*      */       try {
/* 2209 */         if (rs != null) {
/* 2210 */           rs.close();
/*      */         }
/* 2212 */         if (stmt != null) {
/* 2213 */           stmt.close();
/*      */         }
/* 2215 */         if (conn != null) {
/* 2216 */           this.db.releaseConnection(conn);
/*      */         }
/* 2218 */       } catch (Exception x) {}
/*      */     } 
/*      */     
/* 2221 */     return NO_ASBUILT_VINID;
/*      */   }
/*      */   
/*      */   protected void init() throws Exception {
/* 2225 */     this.settings = new HashMap<Object, Object>();
/* 2226 */     Connection conn = null;
/* 2227 */     DBMS.PreparedStatement stmt = null;
/* 2228 */     ResultSet rs = null;
/*      */     try {
/* 2230 */       conn = this.db.requestConnection();
/* 2231 */       String sql = DBMS.getSQL(this.db, "SELECT SalesMakeCode, Attribute, Value FROM SPS_DBSettings");
/* 2232 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 2233 */       rs = stmt.executeQuery();
/* 2234 */       while (rs.next()) {
/* 2235 */         Integer smc = Integer.valueOf(rs.getInt(1));
/* 2236 */         String make = SPSVehicle.getSalesMake(smc, this.adapter);
/* 2237 */         String attribute = rs.getString(2).trim();
/* 2238 */         String value = rs.getString(3).trim();
/* 2239 */         Map<Object, Object> smcSettings = (Map)this.settings.get(make);
/* 2240 */         if (smcSettings == null) {
/* 2241 */           smcSettings = new HashMap<Object, Object>();
/* 2242 */           this.settings.put(make, smcSettings);
/* 2243 */           this.settings.put(smc, smcSettings);
/*      */         } 
/* 2245 */         smcSettings.put(attribute.toLowerCase(Locale.ENGLISH), value.toLowerCase(Locale.ENGLISH));
/*      */       } 
/* 2247 */     } catch (Exception e) {
/* 2248 */       throw e;
/*      */     } finally {
/*      */       try {
/* 2251 */         if (rs != null) {
/* 2252 */           rs.close();
/*      */         }
/* 2254 */         if (stmt != null) {
/* 2255 */           stmt.close();
/*      */         }
/* 2257 */         if (conn != null) {
/* 2258 */           this.db.releaseConnection(conn);
/*      */         }
/* 2260 */       } catch (Exception x) {}
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isSecurityEnforced(Integer vehicleID) {
/* 2266 */     Connection conn = null;
/* 2267 */     DBMS.PreparedStatement stmt = null;
/* 2268 */     ResultSet rs = null;
/*      */     try {
/* 2270 */       conn = this.db.requestConnection();
/* 2271 */       String sql = DBMS.getSQL(this.db, "SELECT * FROM SPS_SecCodeRequired WHERE VehicleID = ?");
/* 2272 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 2273 */       stmt.setInt(1, vehicleID.intValue());
/* 2274 */       rs = stmt.executeQuery();
/* 2275 */       if (rs.next()) {
/* 2276 */         return true;
/*      */       }
/* 2278 */     } catch (Exception e) {
/* 2279 */       return false;
/*      */     } finally {
/*      */       try {
/* 2282 */         if (rs != null) {
/* 2283 */           rs.close();
/*      */         }
/* 2285 */         if (stmt != null) {
/* 2286 */           stmt.close();
/*      */         }
/* 2288 */         if (conn != null) {
/* 2289 */           this.db.releaseConnection(conn);
/*      */         }
/* 2291 */       } catch (Exception x) {}
/*      */     } 
/*      */     
/* 2294 */     return false;
/*      */   }
/*      */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSModel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */