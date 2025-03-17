/*      */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.nao;
/*      */ 
/*      */ import com.eoos.datatype.gtwo.PairImpl;
/*      */ import com.eoos.datatype.marker.Configurable;
/*      */ import com.eoos.gm.tis2web.acl.service.ACLService;
/*      */ import com.eoos.gm.tis2web.acl.service.ACLServiceProvider;
/*      */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*      */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*      */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*      */ import com.eoos.gm.tis2web.frame.export.common.datatype.DBVersionInformation;
/*      */ import com.eoos.gm.tis2web.frame.export.common.util.ConNvent;
/*      */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*      */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLinkWrapper;
/*      */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*      */ import com.eoos.gm.tis2web.sids.service.cai.ServiceID;
/*      */ import com.eoos.gm.tis2web.sps.common.RequestBuilder;
/*      */ import com.eoos.gm.tis2web.sps.common.TypeSelectionRequest;
/*      */ import com.eoos.gm.tis2web.sps.common.VIT1Data;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.DisplayableValue;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.ListValue;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.PairValue;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.SPSException;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Archive;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Controller;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Part;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.PartFile;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingData;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingDataUnit;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.lt.SPSLaborTimeConfiguration;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.AssignmentRequest;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.DisplayRequest;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.InstructionDisplayRequest;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.AVUtil;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonException;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonValue;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.DisplayableValueImpl;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ListValueImpl;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ValueAdapter;
/*      */ import com.eoos.gm.tis2web.sps.common.impl.RequestBuilderImpl;
/*      */ import com.eoos.gm.tis2web.sps.common.implementation.SPSBlobImpl;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSArchive;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSController;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSControllerReference;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSLaborTime;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSPartFile;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSProgrammingFile;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSProgrammingType;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSSchemaAdapter;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSSession;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.dbinfo.DatabaseInfo;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.log.SPSEventLog;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.system.serverside.adapter.ServiceResolution;
/*      */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*      */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*      */ import com.eoos.gm.tis2web.sps.service.cai.Request;
/*      */ import com.eoos.gm.tis2web.sps.service.cai.RequestException;
/*      */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*      */ import com.eoos.jdbc.ConnectionProvider;
/*      */ import com.eoos.propcfg.Configuration;
/*      */ import java.io.File;
/*      */ import java.sql.Connection;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.Date;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import org.apache.log4j.Logger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SPSSchemaAdapterNAO
/*      */   extends SPSSchemaAdapter
/*      */   implements Configurable
/*      */ {
/*      */   public static final int SKIP_PROGRAMMING_VCI = 10;
/*      */   public static final int VCI_ARCHIVE_A = 1001;
/*      */   public static final int VCI_ARCHIVE_B = 1002;
/*      */   public static final String PART_FILE_LOCATION = "vci-part-files";
/*      */   public static final String ARCHIVE_LOCATION = "vci-archives";
/*      */   public static final String TCSC_QUICK_UPDATE = "tcsc.db";
/*      */   public static final String ONSTAR_CONTROLLERS = "OnStarCustomCalibration";
/*      */   public static final String CUSTOM_CALIBRATIONS = "customcal.db";
/*   95 */   private static Logger log = Logger.getLogger(SPSSchemaAdapterNAO.class);
/*      */   
/*   97 */   protected static RequestBuilder builder = (RequestBuilder)new RequestBuilderImpl();
/*      */   
/*   99 */   private final Object SYNC_DBLINK = new Object();
/*      */   
/*      */   private Long NAO_DB_VERSION_TIMESTAMP;
/*      */   
/*  103 */   private IDatabaseLink databaseLink = null;
/*      */   
/*  105 */   private ConnectionProvider connectionProvider = null;
/*      */   
/*      */   public SPSSchemaAdapterNAO(Configuration configuration) throws Exception {
/*  108 */     super(configuration);
/*      */   }
/*      */ 
/*      */   
/*      */   public void init() throws Exception {
/*  113 */     getModel().init();
/*  114 */     initQuickUpdate(this.configuration);
/*  115 */     initCustomCalibrations(this.configuration);
/*      */   }
/*      */   
/*      */   public Long getNAO_DB_VERSION_TIMESTAMP() {
/*  119 */     if (this.NAO_DB_VERSION_TIMESTAMP == null) {
/*  120 */       this.NAO_DB_VERSION_TIMESTAMP = Long.valueOf(getModel().getNAO_DB_Version_time_stamp());
/*      */     }
/*  122 */     return this.NAO_DB_VERSION_TIMESTAMP;
/*      */   }
/*      */   
/*      */   public IDatabaseLink getDatabaseLink() {
/*  126 */     synchronized (this.SYNC_DBLINK) {
/*  127 */       if (this.databaseLink == null) {
/*  128 */         log.info("initializing database link");
/*      */         try {
/*  130 */           final DatabaseLink backend = DatabaseLink.openDatabase(this.configuration, "db");
/*  131 */           this.connectionProvider = ConNvent.create(new ConnectionProvider()
/*      */               {
/*      */                 public void releaseConnection(Connection connection) {
/*  134 */                   backend.releaseConnection(connection);
/*      */                 }
/*      */                 
/*      */                 public Connection getConnection() {
/*      */                   try {
/*  139 */                     return backend.requestConnection();
/*  140 */                   } catch (Exception e) {
/*  141 */                     throw new RuntimeException(e);
/*      */                   } 
/*      */                 }
/*      */               },  30000L);
/*      */           
/*  146 */           this.databaseLink = (IDatabaseLink)new DatabaseLinkWrapper(backend)
/*      */             {
/*      */               public void releaseConnection(Connection connection) {
/*  149 */                 SPSSchemaAdapterNAO.this.connectionProvider.releaseConnection(connection);
/*      */               }
/*      */               
/*      */               public Connection requestConnection() throws Exception {
/*  153 */                 return SPSSchemaAdapterNAO.this.connectionProvider.getConnection();
/*      */               }
/*      */             };
/*      */         
/*      */         }
/*  158 */         catch (Exception e) {
/*  159 */           log.warn("... failed to initialize database link - exception:" + e + ", using fallback-db ");
/*      */           try {
/*  161 */             this.databaseLink = (IDatabaseLink)DatabaseLink.openDatabase(this.configuration, "fallback-db");
/*  162 */           } catch (Exception ex) {
/*  163 */             log.error("... unable to initialize fallback database link - exception: " + ex);
/*  164 */             throw new RuntimeException(e);
/*      */           } 
/*      */         } 
/*      */       } 
/*  168 */       return this.databaseLink;
/*      */     } 
/*      */   }
/*      */   
/*      */   private SPSModel getModel() {
/*  173 */     return SPSModel.getInstance(this);
/*      */   }
/*      */   
/*      */   public String getCalibrationVerificationNumber(String sessionID, String partNumber) {
/*  177 */     return SPSPart.getCVN(partNumber, this);
/*      */   }
/*      */   
/*      */   protected boolean assertVehicleState(SPSSession session, AttributeValueMap data, List<SPSOption> options) {
/*  181 */     SPSVehicle vehicle = (SPSVehicle)session.getVehicle();
/*  182 */     List<SPSOption> selections = vehicle.getOptions();
/*  183 */     List<SPSOption> obsolete = new ArrayList();
/*  184 */     List<SPSOption> modifications = new ArrayList();
/*  185 */     List<Value> additions = new ArrayList();
/*  186 */     if (selections != null)
/*      */     {
/*  188 */       for (int i = 0; i < selections.size(); i++) {
/*  189 */         SPSOption option = selections.get(i);
/*  190 */         SPSOption group = (SPSOption)option.getType();
/*  191 */         if (group != null) {
/*      */ 
/*      */           
/*  194 */           Value value = data.getValue((Attribute)group);
/*  195 */           if (value == null) {
/*  196 */             obsolete.add(option);
/*  197 */           } else if (!option.equals(value)) {
/*  198 */             modifications.add(option);
/*  199 */             additions.add(value);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*  204 */     if (options != null) {
/*  205 */       for (int i = 0; i < options.size(); i++) {
/*  206 */         SPSOption option = options.get(i);
/*  207 */         SPSOption group = (SPSOption)option.getType();
/*  208 */         if (group != null) {
/*      */ 
/*      */           
/*  211 */           Value value = data.getValue((Attribute)group);
/*  212 */           if (value != null && !isSelectedVehicleOption(vehicle, value)) {
/*  213 */             additions.add(value);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*  218 */     if (obsolete.size() > 0) {
/*  219 */       selections.removeAll(obsolete);
/*      */     }
/*  221 */     if (modifications.size() > 0) {
/*  222 */       selections.removeAll(modifications);
/*      */     }
/*  224 */     if (additions.size() > 0) {
/*  225 */       for (int i = 0; i < additions.size(); i++) {
/*  226 */         SPSOption option = (SPSOption)additions.get(i);
/*  227 */         vehicle.setOption(option);
/*      */       } 
/*      */     }
/*  230 */     return (obsolete.size() == 0 && modifications.size() == 0);
/*      */   }
/*      */   
/*      */   boolean isSelectedVehicleOption(SPSVehicle vehicle, Value value) {
/*  234 */     List<SPSOption> selections = vehicle.getOptions();
/*  235 */     if (selections != null) {
/*  236 */       for (int i = 0; i < selections.size(); i++) {
/*  237 */         SPSOption option = selections.get(i);
/*  238 */         if (option.equals(value)) {
/*  239 */           return true;
/*      */         }
/*      */       } 
/*      */     }
/*  243 */     return false;
/*      */   }
/*      */   
/*      */   protected SPSSession lookupSession(AttributeValueMap data) throws Exception {
/*  247 */     String sessionID = (String)AVUtil.accessValue(data, CommonAttribute.SESSION_ID);
/*  248 */     Value modeValue = data.getValue(CommonAttribute.EXECUTION_MODE);
/*  249 */     boolean isCALID = (modeValue != null && modeValue == CommonValue.EXECUTION_MODE_INFO);
/*  250 */     String sessionKey = isCALID ? ("CALID-" + SPSSession.class.getName()) : SPSSession.class.getName();
/*  251 */     SPSSession session = (SPSSession)ClientContextProvider.getInstance().getContext(sessionID).getObject(sessionKey);
/*  252 */     if (session == null || !assertSessionTag(session, data) || !assertVIN(session, data)) {
/*  253 */       data.set(CommonAttribute.SPS_ADAPTER_TYPE, CommonValue.SPS_NAO);
/*  254 */       Locale locale = (Locale)AVUtil.accessValue(data, CommonAttribute.LOCALE);
/*  255 */       SPSLanguage language = SPSLanguage.getLanguage(SPSLanguage.lookupLocale(locale), this);
/*  256 */       if (language == null) {
/*  257 */         throw new SPSException(CommonException.UnsupportedLocale);
/*      */       }
/*  259 */       SPSVIN vin = new SPSVIN((String)AVUtil.accessValue(data, CommonAttribute.VIN));
/*  260 */       if (!vin.validate()) {
/*  261 */         throw new SPSException(CommonException.InvalidVIN);
/*      */       }
/*  263 */       SPSVehicle vehicle = new SPSVehicle(getModel(), vin);
/*  264 */       long signature = System.currentTimeMillis();
/*  265 */       data.set(CommonAttribute.SESSION_SIGNATURE, (Value)new ValueAdapter(Long.valueOf(signature)));
/*  266 */       Long tag = (Long)AVUtil.accessValue(data, CommonAttribute.SESSION_TAG);
/*  267 */       session = new SPSSession(signature, tag.longValue(), getModel(), language, vehicle, sessionID);
/*  268 */       log.debug("sps session timestamp (ms): client=" + tag.longValue() + " server=" + signature + " delta=" + (tag.longValue() - signature));
/*  269 */       ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/*  270 */       context.storeObject(sessionKey, session);
/*  271 */       if (!isCALID) {
/*  272 */         session.registerLogoutListener(context, SPSEventLog.ADAPTER_NAO);
/*      */       } else {
/*  274 */         handleSecurityCodeFlagCALID(sessionID, data, vin);
/*      */       } 
/*      */     } 
/*  277 */     session.fixDataStorage(data);
/*  278 */     return session;
/*      */   }
/*      */   
/*      */   protected void handleSecurityCodeFlagCALID(String sessionID, AttributeValueMap data, SPSVIN vin) {
/*  282 */     boolean granted = false;
/*  283 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/*      */     try {
/*  285 */       ACLService aclService = ACLServiceProvider.getInstance().getService();
/*  286 */       Map usrGroup2Manuf = context.getSharedContext().getUsrGroup2ManufMap();
/*  287 */       Set<String> tmp = new HashSet();
/*  288 */       tmp.add("security-code-access");
/*  289 */       granted = (aclService.getAuthorizedResources("AdapterData", tmp, usrGroup2Manuf, context.getSharedContext().getCountry()).size() != 0);
/*  290 */     } catch (Exception e) {
/*  291 */       log.warn("unable to decide, setting result to false - exception: " + e);
/*      */     } 
/*  293 */     if (granted) {
/*      */       
/*      */       try {
/*  296 */         String securityCode = SPSSecurityCode.getInstance(this).getSecurityCode(vin.toString());
/*  297 */         if (securityCode != null) {
/*  298 */           data.set(CommonAttribute.SECURITY_CODE, (Value)new ValueAdapter(securityCode));
/*      */         }
/*  300 */       } catch (Exception e) {
/*  301 */         log.error("failed to query security code (VIN=" + vin.toString() + ")", e);
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
/*      */   protected void checkVehicleState(SPSSession session, AttributeValueMap data) throws Exception {
/*  321 */     if (!assertVIN(session, data)) {
/*  322 */       reset(session);
/*      */     }
/*  324 */     SPSControllerReference reference = session.getControllerReference();
/*  325 */     if (reference != null && 
/*  326 */       !assertVehicleState(session, data, reference.getOptions()))
/*      */     {
/*      */ 
/*      */ 
/*      */       
/*  331 */       reset(session, CommonAttribute.CONTROLLER);
/*      */     }
/*      */     
/*  334 */     SPSControllerList controllers = (SPSControllerList)session.getControllers();
/*  335 */     if (controllers != null) {
/*  336 */       if (!assertVehicleState(session, data, controllers.getOptions()))
/*      */       {
/*      */         
/*  339 */         reset(session);
/*      */       }
/*  341 */       if (!controllers.match(data)) {
/*  342 */         reset(session);
/*  343 */         session.getVehicle().setOption(controllers.getControllerTypeSelection());
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void checkControllerSelection(SPSSession session, AttributeValueMap data) throws Exception {
/*  349 */     SPSControllerReference reference = session.getControllerReference();
/*  350 */     if (reference != null) {
/*  351 */       SPSControllerReference selection = (SPSControllerReference)data.getValue(CommonAttribute.CONTROLLER);
/*  352 */       if (!reference.equals(selection)) {
/*  353 */         reset(session, CommonAttribute.CONTROLLER);
/*      */       }
/*      */     } 
/*  356 */     SPSProgrammingType method = session.getProgrammingType();
/*  357 */     if (method != null) {
/*  358 */       SPSProgrammingType selection = (SPSProgrammingType)data.getValue(CommonAttribute.CONTROLLER_METHOD);
/*  359 */       if (!method.equals(selection)) {
/*  360 */         reset(session, CommonAttribute.CONTROLLER);
/*  361 */       } else if (data.getValue(CommonAttribute.HARDWARE) == null) {
/*  362 */         if (session.getController() != null && session.getController() instanceof SPSControllerVCI && session.getController().getHardware() != null) {
/*  363 */           if (session.getVehicle().getVIT1() != null) {
/*  364 */             VIT1Data vit1 = session.getVehicle().getVIT1();
/*  365 */             if (vit1.getHWNumber() != null && vit1.getHWNumber().trim().length() > 0) {
/*      */               return;
/*      */             }
/*      */           } 
/*  369 */           reset(session, CommonAttribute.HARDWARE);
/*  370 */         } else if (session.getController() != null && session.getController() instanceof SPSProgrammingSequence && session.getController().getHardware() != null) {
/*  371 */           reset(session, CommonAttribute.HARDWARE);
/*      */         }
/*      */       
/*      */       }
/*  375 */       else if (session.getController() != null && session.getController() instanceof SPSControllerVCI && session.getController().getHardware() != null) {
/*  376 */         SPSHardware part = session.getController().getHardware().get(0);
/*  377 */         SPSHardware hardware = (SPSHardware)AVUtil.accessValue(data, CommonAttribute.HARDWARE);
/*  378 */         if (!hardware.equals(part)) {
/*  379 */           reset(session, CommonAttribute.HARDWARE);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void assertSessionMode(SPSSession session, AttributeValueMap data) throws Exception {
/*  387 */     Value mode = data.getValue(CommonAttribute.MODE);
/*  388 */     if (mode != null && session.getMode() != null && !mode.equals(session.getMode())) {
/*  389 */       reset(session);
/*      */     }
/*      */   }
/*      */   
/*      */   protected List checkDevices(AttributeValueMap data) throws Exception {
/*  394 */     if (isPassThru(data)) {
/*  395 */       return null;
/*      */     }
/*  397 */     Long navInfo = (Long)AVUtil.accessValue(data, CommonAttribute.VIT1_NAVIGATION_INFO);
/*  398 */     if (navInfo != null && getModel().verifyVTDNavigationInfo(navInfo)) {
/*  399 */       List<Integer> list = new ArrayList();
/*  400 */       list.add(Integer.valueOf(254));
/*  401 */       return list;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  409 */     List<Integer> devices = (List)AVUtil.accessValue(data, CommonAttribute.VIT1_DEVICE_LIST);
/*  410 */     Integer seeds = (Integer)AVUtil.accessValue(data, CommonAttribute.VIT1_SEED_COUNT);
/*  411 */     if (seeds.intValue() > 1) {
/*  412 */       devices.add(Integer.valueOf(254));
/*      */     }
/*  414 */     return devices;
/*      */   }
/*      */ 
/*      */   
/*      */   protected Controller _getController(AttributeValueMap data) throws Exception {
/*  419 */     SPSController ret = null;
/*      */     
/*  421 */     SPSSession session = lookupSession(data);
/*      */     
/*  423 */     assertSessionMode(session, data);
/*  424 */     checkControllerSelection(session, data);
/*  425 */     checkVehicleState(session, data);
/*  426 */     ret = session.getController();
/*  427 */     if (ret == null) {
/*  428 */       List devices = checkDevices(data);
/*  429 */       if (data.getValue(CommonAttribute.PROCEED_SAME_VIN) == null) {
/*  430 */         Value permission = CommonValue.DISABLE;
/*  431 */         if (isPassThru(data) && 
/*  432 */           aclProceedSameVINGranted(session.getSessionID())) {
/*  433 */           permission = CommonValue.OK;
/*      */         }
/*      */         
/*  436 */         data.set(CommonAttribute.PROCEED_SAME_VIN, permission);
/*      */       } 
/*  438 */       Value mode = data.getValue(CommonAttribute.MODE);
/*  439 */       String toolType = (String)AVUtil.accessValue(data, CommonAttribute.DEVICE);
/*  440 */       SPSControllerList controllers = (SPSControllerList)session.getControllers(devices, mode, toolType);
/*  441 */       if (controllers == null || !filterType4Executables(controllers, data)) {
/*  442 */         throw new SPSException(CommonException.NoControllers);
/*      */       }
/*  444 */       boolean isCALID = false;
/*  445 */       Value modeValue = data.getValue(CommonAttribute.EXECUTION_MODE);
/*  446 */       if (modeValue != null && modeValue == CommonValue.EXECUTION_MODE_INFO) {
/*  447 */         isCALID = true;
/*      */       }
/*  449 */       if (isCALID) {
/*  450 */         controllers.removeProgrammingSequences();
/*      */       } else {
/*  452 */         controllers.suppressProgrammingSequenceControllers();
/*  453 */         handleClearDTCs(data, session);
/*      */       } 
/*      */       
/*  456 */       session.fixDataStorage(data);
/*      */       
/*  458 */       List options = controllers.getOptions();
/*  459 */       if (options != null) {
/*      */ 
/*      */ 
/*      */         
/*  463 */         if (session.getVehicle().getOptions() != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  468 */           session.update(this);
/*      */ 
/*      */           
/*  471 */           options = controllers.getOptions();
/*  472 */           if (options == null) {
/*  473 */             throw new RequestException(makeControllerSelectionRequest(controllers));
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  479 */         throw new RequestException(makeOptionSelectionRequest(options));
/*      */       } 
/*      */       
/*  482 */       SPSControllerReference reference = (SPSControllerReference)data.getValue(CommonAttribute.CONTROLLER);
/*  483 */       SPSProgrammingType method = (SPSProgrammingType)data.getValue(CommonAttribute.CONTROLLER_METHOD);
/*  484 */       if (reference != null && method == null) {
/*  485 */         TypeSelectionRequest request = builder.makeTypeSelectionRequest(CommonAttribute.CONTROLLER_METHOD, reference.getProgrammingMethods(), null);
/*  486 */         throw new RequestException(request);
/*      */       } 
/*  488 */       if (reference != null && method != null) {
/*  489 */         if (session.getProgrammingType() != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  494 */           session.update(this);
/*      */         } else {
/*  496 */           handleSaturnAttributes(session, data);
/*  497 */           session.setController(data, reference, method, this);
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
/*  512 */         if (session.getController() != null) {
/*  513 */           checkCustomCalibrationIndicator(data, session);
/*  514 */           ret = session.getController();
/*      */         } else {
/*  516 */           throw new RequestException(makeOptionSelectionRequest(reference.getOptions()));
/*      */         } 
/*      */       } else {
/*      */         
/*  520 */         throw new RequestException(makeControllerSelectionRequest(controllers));
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  526 */       if (AVUtil.accessValue(data, CommonAttribute.DEVICE_ID) == null) {
/*  527 */         data.set(CommonAttribute.DEVICE_ID, (Value)new ValueAdapter(Integer.valueOf(ret.getDeviceID())));
/*      */       }
/*  529 */     } catch (UnsupportedOperationException e) {}
/*      */     
/*  531 */     return (Controller)ret;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void handleClearDTCs(AttributeValueMap data, SPSSession session) {
/*  536 */     if (data.getValue(CommonAttribute.CLEAR_DTCS) == null && 
/*  537 */       session.getClearDTCFlag()) {
/*      */       
/*  539 */       data.set(CommonAttribute.CLEAR_DTCS, CommonValue.OK);
/*  540 */       if (session.getVehicleLink() > 0) {
/*  541 */         data.set(CommonAttribute.VEHICLE_LINK, (Value)new ValueAdapter(Integer.valueOf(session.getVehicleLink())));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void checkCustomCalibrationIndicator(AttributeValueMap data, SPSSession session) {
/*  548 */     if (session.getController() instanceof SPSControllerVCI && (
/*  549 */       (SPSControllerVCI)session.getController()).requiresCustomCalibration() && 
/*  550 */       data.getValue(CommonAttribute.CONTROLLER_REQUIRES_CUSTOM_CALIBRATION) == null) {
/*  551 */       data.set(CommonAttribute.CONTROLLER_REQUIRES_CUSTOM_CALIBRATION, CommonValue.OK);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean requiresCustomCalibration(AttributeValueMap data) {
/*  558 */     return (data.getValue(CommonAttribute.CONTROLLER_REQUIRES_CUSTOM_CALIBRATION) != null);
/*      */   }
/*      */   
/*      */   protected AssignmentRequest makeControllerSelectionRequest(List controllers) {
/*  562 */     return (AssignmentRequest)builder.makeControllerSelectionRequest(CommonAttribute.CONTROLLER, controllers, null);
/*      */   }
/*      */   
/*      */   protected AssignmentRequest makeVCIInputRequest() {
/*  566 */     return (AssignmentRequest)builder.makeInputRequest(CommonAttribute.VCI, null, null);
/*      */   }
/*      */   
/*      */   protected AssignmentRequest makeOptionSelectionRequest(List<SPSOption> options) throws Exception {
/*  570 */     SPSOption option = options.get(0);
/*  571 */     SPSOption group = (SPSOption)option.getType();
/*  572 */     if (group == null)
/*      */     {
/*  574 */       throw new SPSException(CommonException.NoControllerSelectionPossible);
/*      */     }
/*  576 */     List<SPSOption> list = new ArrayList();
/*  577 */     list.add(option);
/*  578 */     for (int i = 1; i < options.size(); i++) {
/*  579 */       SPSOption alternative = options.get(i);
/*  580 */       if (alternative.getType().equals(group)) {
/*  581 */         list.add(alternative);
/*      */       }
/*      */     } 
/*  584 */     if (list.size() == 1);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  589 */     Collections.sort(list, new SPSOptionSort());
/*  590 */     return (AssignmentRequest)builder.makeSelectionRequest((Attribute)group, list, null);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void handleSaturnAttributes(SPSSession session, AttributeValueMap data) throws Exception {
/*  595 */     Value modeValue = data.getValue(CommonAttribute.EXECUTION_MODE);
/*  596 */     if (modeValue != null && modeValue == CommonValue.EXECUTION_MODE_INFO) {
/*  597 */       Value saturn = data.getValue(CommonAttribute.SATURN_DATA);
/*  598 */       if (saturn == null) {
/*  599 */         SPSControllerReference reference = (SPSControllerReference)data.getValue(CommonAttribute.CONTROLLER);
/*  600 */         if ("reprogrammable".equals(reference.getType())) {
/*  601 */           List<SPSControllerVCI> controllers = reference.getControllers();
/*  602 */           if (controllers != null) {
/*  603 */             Set<Integer> devices = new HashSet();
/*  604 */             for (int i = 0; i < controllers.size(); i++) {
/*  605 */               SPSControllerVCI controller = controllers.get(i);
/*  606 */               Integer device = Integer.valueOf(controller.getDeviceID());
/*  607 */               if (!devices.contains(device)) {
/*      */ 
/*      */                 
/*  610 */                 devices.add(device);
/*  611 */                 SPSSaturnData sdata = new SPSSaturnData(session, device.intValue());
/*  612 */                 List options = sdata.getOptions(this);
/*  613 */                 if (options != null) {
/*  614 */                   data.set(CommonAttribute.SATURN_DATA, (Value)new ValueAdapter(sdata));
/*  615 */                   throw new RequestException(makeOptionSelectionRequest(options));
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*  620 */         }  data.set(CommonAttribute.SATURN_DATA, CommonValue.OK);
/*  621 */       } else if (saturn != CommonValue.OK) {
/*  622 */         SPSSaturnData sdata = (SPSSaturnData)((ValueAdapter)saturn).getAdaptee();
/*  623 */         List options = sdata.getOptions((SPSVehicle)session.getVehicle());
/*  624 */         assertVehicleState(session, data, options);
/*      */         
/*  626 */         options = sdata.getOptions((SPSVehicle)session.getVehicle());
/*      */ 
/*      */ 
/*      */         
/*  630 */         if (options != null) {
/*  631 */           throw new RequestException(makeOptionSelectionRequest(options));
/*      */         }
/*  633 */         data.set(CommonAttribute.SATURN_DATA, CommonValue.OK);
/*      */       } 
/*      */     } 
/*      */     
/*  637 */     SPSVehicle vehicle = (SPSVehicle)session.getVehicle();
/*  638 */     Map optionBytes = (Map)AVUtil.accessValue(data, CommonAttribute.VIT1_OPTION_BYTES);
/*  639 */     if (optionBytes != null) {
/*  640 */       vehicle.setOptionBytes(optionBytes);
/*      */     }
/*  642 */     Map adaptiveBytes = (Map)AVUtil.accessValue(data, CommonAttribute.VIT1_ADAPTIVE_BYTES);
/*  643 */     if (adaptiveBytes != null)
/*  644 */       vehicle.setAdaptiveBytes(adaptiveBytes); 
/*      */   }
/*      */   
/*      */   public static class SPSOptionSort
/*      */     implements Comparator
/*      */   {
/*      */     public int compare(Object o1, Object o2) {
/*  651 */       return ((SPSOption)o1).getDenotation(Locale.US).compareTo(((SPSOption)o2).getDenotation(Locale.US));
/*      */     }
/*      */   }
/*      */   
/*      */   protected boolean filterType4Executables(SPSControllerList controllers, AttributeValueMap data) {
/*  656 */     Value modeValue = data.getValue(CommonAttribute.EXECUTION_MODE);
/*  657 */     if (modeValue != null && modeValue == CommonValue.EXECUTION_MODE_INFO) {
/*  658 */       Iterator<SPSControllerReference> it = controllers.iterator();
/*  659 */       while (it.hasNext()) {
/*  660 */         boolean remove = false;
/*  661 */         SPSControllerReference reference = it.next();
/*  662 */         List<SPSController> instances = reference.getControllers();
/*  663 */         for (int i = 0; instances != null && i < instances.size(); i++) {
/*  664 */           SPSController controller = instances.get(i);
/*  665 */           if (controller instanceof SPSControllerVCI && controller.getDeviceID() == 253) {
/*  666 */             remove = true;
/*      */           }
/*      */         } 
/*  669 */         if (remove) {
/*  670 */           it.remove();
/*      */         }
/*      */       } 
/*      */     } 
/*  674 */     return !controllers.isEmpty();
/*      */   }
/*      */   
/*      */   protected void handleType4Executables(SPSSession session, AttributeValueMap data, SPSProgrammingData pdata) throws Exception {
/*  678 */     if (data.getValue(CommonAttribute.TYPE4_DATA) == null) {
/*  679 */       String content = "type4-instructions";
/*  680 */       DisplayRequest request = builder.makeDisplayRequest(CommonAttribute.TYPE4_DATA, null, content);
/*  681 */       request.setAutoSubmit(false);
/*  682 */       throw new RequestException(request);
/*      */     } 
/*  684 */     if (data.getValue(CommonAttribute.TYPE4_DATA).equals(CommonValue.OK)) {
/*  685 */       List<PairImpl> files = new SPSType4Data(session.getLanguage().getLocale(), this);
/*  686 */       if (session.getController() instanceof SPSControllerXML) {
/*  687 */         SPSVehicle vehicle = (SPSVehicle)session.getVehicle();
/*  688 */         byte[] bytes = ((SPSControllerXML)session.getController()).getBuildFile(vehicle).getBytes("ASCII");
/*  689 */         files.add(new PairImpl("BuildRecord.bld", bytes));
/*  690 */         String ECUPIN = determineECUPIN((SPSControllerXML)session.getController());
/*  691 */         files.add(new PairImpl("ECUPIN.TXT", ECUPIN.getBytes("ASCII")));
/*      */       } 
/*  693 */       loadCalibrationFileInformation(data, pdata);
/*  694 */       List<ProgrammingDataUnit> calibrations = pdata.getCalibrationFiles();
/*      */       
/*  696 */       for (int i = 0; i < calibrations.size(); i++) {
/*  697 */         ProgrammingDataUnit file = calibrations.get(i);
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
/*  711 */         files.add(new PairImpl(file.getBlobName(), null));
/*      */       } 
/*      */       
/*  714 */       data.set(CommonAttribute.TYPE4_DATA, (Value)new ListValueImpl(files));
/*  715 */       if (data.getValue(CommonAttribute.LABOR_TIME_CONFIGURATION) == null) {
/*  716 */         handleLaborTimeConfiguration(data);
/*      */       }
/*  718 */       if (data.getValue(CommonAttribute.CONFIRM_PROGRAMMING_DATA_DOWNLOAD_START) == null) {
/*  719 */         throw new RequestException(builder.makeProgrammingDataDownloadRequest(CommonAttribute.CONFIRM_PROGRAMMING_DATA_DOWNLOAD_START, calibrations));
/*      */       }
/*      */     } 
/*  722 */     if (data.getValue(CommonAttribute.CONFIRM_PROGRAMMING_DATA_DOWNLOAD_FINISHED) == null) {
/*  723 */       throw new RequestException(builder.makeProgrammingDataDownloadContinuationRequest());
/*      */     }
/*  725 */     throw new RequestException(builder.makeReprogramRequest(CommonAttribute.REPROGRAM, pdata));
/*      */   }
/*      */ 
/*      */   
/*      */   protected void loadProgrammingFile(SPSSession session, AttributeValueMap data, long vci) {
/*  730 */     SPSProgrammingFile pfile = null;
/*      */     try {
/*  732 */       vci -= session.getVehicle().getVIN().getSequenceNo();
/*  733 */       vci -= 2147483647L;
/*  734 */       pfile = SPSProgrammingFile.load(vci, this);
/*  735 */       if (pfile != null) {
/*  736 */         if ("zip".equalsIgnoreCase(pfile.getType())) {
/*  737 */           data.set(CommonAttribute.ARCHIVE, (Value)new ValueAdapter(new SPSArchive(session.getLanguage(), (File)pfile.getFile())));
/*      */         } else {
/*  739 */           data.set(CommonAttribute.PART_FILE, (Value)new ValueAdapter(new SPSPartFile((byte[])pfile.getFile())));
/*      */         } 
/*      */         return;
/*      */       } 
/*  743 */       String dir = this.configuration.getProperty("vci-archives");
/*  744 */       File file = new File(dir, vci + "." + "zip");
/*  745 */       if (file.exists()) {
/*  746 */         data.set(CommonAttribute.ARCHIVE, (Value)new ValueAdapter(new SPSArchive(session.getLanguage(), file)));
/*      */       } else {
/*  748 */         dir = this.configuration.getProperty("sps.schema.adapter.NAO.class.vci-part-files");
/*  749 */         file = new File(dir, vci + "." + "prt");
/*  750 */         if (file.exists()) {
/*  751 */           data.set(CommonAttribute.PART_FILE, (Value)new ValueAdapter(new SPSPartFile(file)));
/*      */         }
/*      */       } 
/*  754 */     } catch (Exception e) {
/*  755 */       log.error("failed to handle vci=" + vci, e);
/*      */     } finally {
/*  757 */       if (pfile != null && "zip".equalsIgnoreCase(pfile.getType())) {
/*      */         try {
/*  759 */           ((File)pfile.getFile()).delete();
/*  760 */         } catch (Exception x) {
/*  761 */           log.error("failed to delete temporary file: " + pfile.getFile(), x);
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   protected boolean isProgrammingByFileVCI(long vci) {
/*  768 */     return (vci > 2147483647L);
/*      */   }
/*      */   
/*      */   protected boolean isArchiveVCI(long vci) {
/*  772 */     return (1001L == vci || 1002L == vci);
/*      */   }
/*      */   
/*      */   protected void handleVCI(SPSSession session, AttributeValueMap data) throws Exception {
/*  776 */     String vci = (String)AVUtil.accessValue(data, CommonAttribute.VCI);
/*  777 */     long input = -1L;
/*      */     try {
/*  779 */       input = Long.parseLong(vci);
/*  780 */     } catch (Exception e) {
/*  781 */       throw new SPSException(CommonException.InvalidVCIInput);
/*      */     } 
/*  783 */     if (isArchiveVCI(input)) {
/*  784 */       if (data.getValue(CommonAttribute.ARCHIVE) == null && data.getValue(CommonAttribute.PART_FILE) == null) {
/*  785 */         if (!checkVCI1001Permission(session.getSessionID())) {
/*  786 */           throw new SPSException(CommonException.VCI1001NotEnabled);
/*      */         }
/*  788 */         throw new RequestException(builder.makeProgrammingByFileRequest());
/*      */       } 
/*  790 */       SPSControllerVCI controller = (SPSControllerVCI)session.getController();
/*  791 */       if (data.getValue(CommonAttribute.ARCHIVE) != null) {
/*  792 */         controller.setArchive((Archive)AVUtil.accessValue(data, CommonAttribute.ARCHIVE));
/*      */       } else {
/*  794 */         controller.setPartFile((PartFile)AVUtil.accessValue(data, CommonAttribute.PART_FILE));
/*      */       }
/*      */     
/*  797 */     } else if (isProgrammingByFileVCI(input)) {
/*  798 */       if (data.getValue(CommonAttribute.ARCHIVE) == null && data.getValue(CommonAttribute.PART_FILE) == null) {
/*  799 */         loadProgrammingFile(session, data, input);
/*      */       }
/*  801 */       SPSControllerVCI controller = (SPSControllerVCI)session.getController();
/*  802 */       if (data.getValue(CommonAttribute.ARCHIVE) != null) {
/*  803 */         controller.setArchive((Archive)AVUtil.accessValue(data, CommonAttribute.ARCHIVE));
/*  804 */       } else if (data.getValue(CommonAttribute.PART_FILE) != null) {
/*  805 */         controller.setPartFile((PartFile)AVUtil.accessValue(data, CommonAttribute.PART_FILE));
/*      */       } else {
/*  807 */         throw new SPSException(CommonException.INVALID_ARCHIVE_VCI);
/*      */       } 
/*      */     } else {
/*  810 */       SPSControllerVCI controller = (SPSControllerVCI)session.getController();
/*  811 */       if (controller != null) {
/*  812 */         input -= session.getVehicle().getVIN().getSequenceNo();
/*  813 */         if (input <= 0L) {
/*  814 */           throw new SPSException(CommonException.InvalidVCIInput);
/*      */         }
/*  816 */         if (controller.getVCI() <= 0) {
/*      */           
/*  818 */           controller.setVCI((int)input);
/*  819 */         } else if (controller.getVCI() != input) {
/*  820 */           reset(session, CommonAttribute.PROGRAMMING_DATA_SELECTION);
/*  821 */           controller.setVCI((int)input);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected boolean checkVCI1001Permission(String sessionID) {
/*  828 */     boolean granted = false;
/*      */     try {
/*  830 */       ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/*  831 */       ACLService aclService = ACLServiceProvider.getInstance().getService();
/*  832 */       Map usrGroup2Manuf = context.getSharedContext().getUsrGroup2ManufMap();
/*  833 */       Set<String> tmp = new HashSet();
/*  834 */       tmp.add("sps-vci-1001");
/*  835 */       granted = (aclService.getAuthorizedResources("AdapterData", tmp, usrGroup2Manuf, context.getSharedContext().getCountry()).size() != 0);
/*  836 */     } catch (Exception e) {
/*  837 */       log.warn("unable to decide, setting result to false - exception: " + e);
/*      */     } 
/*  839 */     return granted;
/*      */   }
/*      */   
/*      */   protected Request makeVIT1Request(SPSSession session) throws Exception {
/*  843 */     SPSControllerVCI controller = (SPSControllerVCI)session.getController();
/*  844 */     int requestMethodID = controller.getRequestMethodID();
/*  845 */     int deviceID = controller.getDeviceID();
/*  846 */     List rdata = getModel().getRequestMethodData(requestMethodID, deviceID);
/*      */ 
/*      */ 
/*      */     
/*  850 */     return (Request)builder.makeVIT1Request(rdata);
/*      */   }
/*      */   
/*      */   public String determineECUPIN(SPSControllerXML controller) throws SPSException {
/*      */     try {
/*  855 */       int requestMethodID = controller.getRequestMethodID();
/*  856 */       int deviceID = controller.getDeviceID();
/*  857 */       List<SPSRequestMethodData> rdata = getModel().getRequestMethodData(requestMethodID, deviceID);
/*  858 */       SPSRequestMethodData rmd = rdata.get(0);
/*  859 */       controller.setPINLink(rmd.getPINLink());
/*  860 */       if (controller.getDeviceID() != rmd.getDeviceID()) {
/*  861 */         log.warn("RMD - Controller DeviceID Mismatch: " + controller.getDeviceID() + " vs. " + rmd.getDeviceID());
/*      */       }
/*  863 */       String ECUPIN = rmd.getDeviceID() + "\r\n" + rmd.getProtocol() + "\r\n" + rmd.getPINLink();
/*  864 */       return ECUPIN;
/*  865 */     } catch (Exception e) {
/*  866 */       log.error("failed to access pin link information: " + controller.toString());
/*  867 */       throw new SPSException(CommonException.NoPinLinkInformation);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void handleVIT1(SPSSession session, AttributeValueMap data) throws Exception {
/*  872 */     if (data.getValue(CommonAttribute.VIT1) == null || session.requiresControllerVIT1()) {
/*  873 */       session.recordRequestControllerVIT1();
/*  874 */       throw new RequestException(makeVIT1Request(session));
/*  875 */     }  if (session.getVehicle().getVIT1() == null) {
/*  876 */       VIT1Data vit1 = (VIT1Data)AVUtil.accessValue(data, CommonAttribute.VIT1);
/*  877 */       session.getVehicle().setVIT1(vit1);
/*      */ 
/*      */       
/*  880 */       if (vit1.getHWNumber() != null && vit1.getHWNumber().trim().length() > 0) {
/*      */         try {
/*  882 */           SPSHardware hardware = new SPSHardware(1, vit1.getHWNumber());
/*  883 */           session.getController().setHardware(hardware);
/*  884 */         } catch (Exception x) {
/*  885 */           log.warn("failed to register hardware '" + vit1.getHWNumber() + "'.");
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void handleVIT1PS(SPSSession session, AttributeValueMap data) throws Exception {
/*  892 */     SPSProgrammingSequence controller = (SPSProgrammingSequence)session.getController();
/*  893 */     List rmd = controller.handleVIT1(getModel(), session, data);
/*  894 */     if (rmd != null) {
/*  895 */       throw new RequestException(builder.makeVIT1Request(rmd));
/*      */     }
/*      */   }
/*      */   
/*      */   protected void handleOdometer(SPSSession session, AttributeValueMap data) throws Exception {
/*  900 */     VIT1Data vit1 = session.getVehicle().getVIT1();
/*  901 */     if (vit1.getOdometer() != null && 
/*  902 */       data.getValue(CommonAttribute.ODOMETER) == null) {
/*  903 */       DisplayableValue reading = DisplayableValueImpl.getInstance(vit1.getOdometer());
/*  904 */       throw new RequestException(builder.makeInputRequest(CommonAttribute.ODOMETER, reading, null));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void handleKeycode(SPSSession session, AttributeValueMap data) throws Exception {
/*  910 */     VIT1Data vit1 = session.getVehicle().getVIT1();
/*  911 */     if (vit1.getKeycode() != null && 
/*  912 */       data.getValue(CommonAttribute.KEYCODE) == null) {
/*  913 */       DisplayableValue value = DisplayableValueImpl.getInstance(vit1.getKeycode());
/*  914 */       throw new RequestException(builder.makeInputRequest(CommonAttribute.KEYCODE, value, null));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected ProgrammingData handleHardwareList(SPSSession session, AttributeValueMap data, SPSProgrammingData pdata) throws Exception {
/*  920 */     SPSHardware selection = (SPSHardware)data.getValue(CommonAttribute.HARDWARE);
/*  921 */     if (selection != null) {
/*      */ 
/*      */       
/*  924 */       if (!checkHardwareSelection(selection, pdata.getHardwareList(), session.getController().getHardware())) {
/*  925 */         reset(session, CommonAttribute.HARDWARE);
/*  926 */         return getProgrammingData(data);
/*      */       } 
/*  928 */       session.getController().setHardware(selection);
/*  929 */       session.update(this);
/*      */     }
/*  931 */     else if (session.getController().getHardware() != null) {
/*  932 */       reset(session, CommonAttribute.HARDWARE);
/*  933 */       return getProgrammingData(data);
/*      */     } 
/*  935 */     if (pdata.getHardwareList() != null) {
/*  936 */       throw new RequestException(makeHardwareSelectionRequest(pdata));
/*      */     }
/*  938 */     return null;
/*      */   }
/*      */   
/*      */   protected void handleSummary(SPSSession session, AttributeValueMap data, SPSProgrammingData pdata) throws Exception {
/*  942 */     ListValue parts = (ListValue)data.getValue(CommonAttribute.PROGRAMMING_DATA_SELECTION);
/*  943 */     List<SPSModule> modules = pdata.getModules();
/*  944 */     for (int i = 0; i < modules.size(); i++) {
/*  945 */       SPSModule module = modules.get(i);
/*  946 */       Part part = parts.getItems().get(i);
/*  947 */       module.setSelectedPart(part);
/*      */     } 
/*  949 */     if (pdata.getModules().size() == 1) {
/*  950 */       SPSPart part = (SPSPart)((SPSModule)pdata.getModules().get(0)).getSelectedPart();
/*  951 */       if (part != null) {
/*  952 */         pdata.setSSECUSVN(part.getPartNumber());
/*      */       }
/*      */     } 
/*  955 */     throw new RequestException(displaySummaryScreen(session, data));
/*      */   }
/*      */   
/*      */   protected boolean isType4ReprogrammingCompleted(SPSSession session, AttributeValueMap data) {
/*  959 */     if (session.getController() instanceof SPSControllerVCI) {
/*  960 */       SPSControllerVCI controller = (SPSControllerVCI)session.getController();
/*  961 */       if (controller.getDeviceID() == 253 && data.getValue(CommonAttribute.REPROGRAM) != null) {
/*  962 */         return true;
/*      */       }
/*      */     } 
/*  965 */     return false;
/*      */   }
/*      */   
/*      */   protected boolean isReplaceMode(SPSSession session, AttributeValueMap data) throws Exception {
/*  969 */     if (session.getController() instanceof SPSControllerVCI) {
/*  970 */       if (data.getValue(CommonAttribute.MODE).equals(CommonValue.REPLACE_AND_REPROGRAM)) {
/*  971 */         return isPassThru(data);
/*      */       }
/*  973 */     } else if (session.getController() instanceof SPSProgrammingSequence && 
/*  974 */       data.getValue(CommonAttribute.MODE).equals(CommonValue.REPLACE_AND_REPROGRAM)) {
/*  975 */       return isPassThru(data);
/*      */     } 
/*      */     
/*  978 */     return false;
/*      */   }
/*      */   
/*      */   protected static void displayReplaceInstructions(SPSSession session, AttributeValueMap data, SPSSchemaAdapterNAO adapter) throws Exception {
/*  982 */     if (data.getValue(CommonAttribute.REPLACE_INSTRUCTIONS) == null) {
/*  983 */       SPSControllerVCI controller = (SPSControllerVCI)session.getController();
/*  984 */       String toolType = (String)AVUtil.accessValue(data, CommonAttribute.DEVICE);
/*  985 */       String content = toolType + "-" + "replace-instructions";
/*  986 */       if (controller.replaceInstruction == 0) {
/*  987 */         data.set(CommonAttribute.REPLACE_INSTRUCTIONS, CommonValue.OK); return;
/*      */       } 
/*  989 */       if (controller.replaceInstruction > 1) {
/*  990 */         String instructions = SPSModel.getInstance(adapter).getReplaceMessageVCI((SPSLanguage)session.getLanguage(), controller.replaceInstruction);
/*  991 */         content = content + "=" + instructions;
/*      */       } 
/*  993 */       DisplayRequest displayRequest = builder.makeDisplayRequest(CommonAttribute.REPLACE_INSTRUCTIONS, null, content);
/*  994 */       displayRequest.setAutoSubmit(false);
/*  995 */       throw new RequestException(displayRequest);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected String accessVIN(SPSSession session) {
/*      */     
/* 1001 */     try { if (session.getController().getDeviceID() == 253)
/* 1002 */         return null; 
/* 1003 */       if (session.getVehicle().getVIT1() != null) {
/* 1004 */         String vin = session.getVehicle().getVIT1().getVIT().getAttrValue("vin");
/* 1005 */         return vin;
/*      */       }  }
/* 1007 */     catch (Exception e) {  }
/* 1008 */     catch (Throwable x) {}
/*      */     
/* 1010 */     return null;
/*      */   }
/*      */   
/*      */   protected boolean needsControllerVIT1(SPSSession session) {
/* 1014 */     if (session.getController().getDeviceID() != 253) {
/* 1015 */       return true;
/*      */     }
/* 1017 */     return (session.getController().getRequestMethodID() > 0);
/*      */   }
/*      */ 
/*      */   
/*      */   protected ProgrammingData _getProgrammingData(AttributeValueMap data) throws Exception {
/* 1022 */     SPSSession session = lookupSession(data);
/* 1023 */     if (session.getProgrammingType().getID().equals(SPSProgrammingType.VCI)) {
/* 1024 */       if (data.getValue(CommonAttribute.VCI) == null)
/* 1025 */         throw new RequestException(makeVCIInputRequest()); 
/* 1026 */       if (isType4ReprogrammingCompleted(session, data)) {
/* 1027 */         return (ProgrammingData)session.getProgrammingData(this);
/*      */       }
/* 1029 */       handleVCI(session, data);
/*      */     }
/* 1031 */     else if (session.getController() instanceof SPSControllerVCI) {
/*      */       
/* 1033 */       if (isType4ReprogrammingCompleted(session, data))
/*      */       {
/* 1035 */         return (ProgrammingData)session.getProgrammingData(this);
/*      */       }
/* 1037 */       SPSControllerVCI controller = (SPSControllerVCI)session.getController();
/* 1038 */       controller.setVCI(-1);
/*      */     } 
/* 1040 */     Value modeValue = data.getValue(CommonAttribute.EXECUTION_MODE);
/* 1041 */     if (modeValue == null || modeValue != CommonValue.EXECUTION_MODE_INFO) {
/* 1042 */       if (session.getController() instanceof SPSControllerVCI) {
/* 1043 */         if (isReplaceMode(session, data)) {
/* 1044 */           SPSControllerVCI controller = (SPSControllerVCI)session.getController();
/* 1045 */           if (controller.getControllerData() != null)
/*      */           {
/* 1047 */             if (data.getValue(CommonAttribute.REPLACE_INSTRUCTIONS) == null) {
/* 1048 */               controller.setControllerData(null);
/*      */             }
/*      */           }
/* 1051 */           if (controller.getReplaceAttributes() != null && needsControllerVIT1(session)) {
/* 1052 */             if (data.getValue(CommonAttribute.VIT1) == null)
/*      */             {
/* 1054 */               throw new RequestException(makeVIT1Request(session)); } 
/* 1055 */             if (controller.getControllerData() == null) {
/*      */               
/* 1057 */               VIT1Data vit1 = (VIT1Data)AVUtil.accessValue(data, CommonAttribute.VIT1);
/* 1058 */               controller.setControllerData(vit1);
/* 1059 */               data.remove(CommonAttribute.VIT1);
/* 1060 */               data.set(CommonAttribute.VIT1_TRANSFER, (Value)new ListValueImpl(controller.getReplaceAttributes()));
/* 1061 */             } else if (session.getVehicle().getVIT1() == null) {
/*      */               
/* 1063 */               VIT1Data vit1 = (VIT1Data)AVUtil.accessValue(data, CommonAttribute.VIT1);
/* 1064 */               vit1.merge((VIT1Data)controller.getControllerData(), controller.getReplaceAttributes());
/*      */             } 
/*      */           } 
/* 1067 */           displayReplaceInstructions(session, data, this);
/*      */         } 
/* 1069 */         if (needsControllerVIT1(session)) {
/* 1070 */           handleVIT1(session, data);
/*      */         }
/* 1072 */         if (session.getController().getDeviceID() != 253) {
/* 1073 */           handleOdometer(session, data);
/* 1074 */           handleKeycode(session, data);
/*      */         } 
/* 1076 */       } else if (session.getController() instanceof SPSProgrammingSequence) {
/* 1077 */         if (data.getValue(CommonAttribute.WARRANTY_CLAIM_CODE) == null) {
/* 1078 */           data.set(CommonAttribute.WARRANTY_CLAIM_CODE, (Value)new ValueAdapter(computeWarrantyClaimCode(session, data)));
/*      */         }
/* 1080 */         handleVIT1PS(session, data);
/*      */       } 
/*      */     }
/* 1083 */     if (data.getValue(CommonAttribute.PROGRAMMING_DATA_SELECTION) == null && data.getValue(CommonAttribute.TYPE4_DATA) == null) {
/* 1084 */       reset(session, CommonAttribute.PROGRAMMING_DATA_SELECTION);
/*      */     }
/* 1086 */     SPSProgrammingData pdata = (SPSProgrammingData)session.getProgrammingData(this);
/* 1087 */     if (pdata == null) {
/* 1088 */       if (isProgrammingSequence(session))
/*      */       {
/* 1090 */         return getProgrammingDataPS(session, data); } 
/* 1091 */       if (isNonProgrammingVCI(session)) {
/* 1092 */         if (modeValue == null || modeValue != CommonValue.EXECUTION_MODE_INFO) {
/* 1093 */           return (ProgrammingData)new SPSProgrammingData();
/*      */         }
/* 1095 */         AssignmentRequest request = displayPreProgrammingInstructions(session, data);
/* 1096 */         if (request != null) {
/* 1097 */           throw new RequestException(request);
/*      */         }
/*      */       } else {
/*      */         
/* 1101 */         throw new SPSException(CommonException.NoValidCOP);
/*      */       } 
/*      */     } 
/* 1104 */     boolean isCALID = (modeValue != null && modeValue == CommonValue.EXECUTION_MODE_INFO);
/* 1105 */     if (!isCALID && session.getController() instanceof SPSControllerVCI && ((SPSControllerVCI)session.getController()).isSecurityCodeRequired()) {
/* 1106 */       if (data.getValue(CommonAttribute.SECURITY_CODE) == null || data.getValue(CommonAttribute.SECURITY_CODE) == CommonValue.DISABLE) {
/* 1107 */         String vin = session.getVehicle().getVIN().toString();
/* 1108 */         String securityCode = SPSSecurityCode.getInstance(this).getSecurityCode(vin);
/* 1109 */         if (securityCode == null) {
/* 1110 */           throw new RequestException(builder.makeInputRequest(CommonAttribute.SECURITY_CODE, null, null));
/*      */         }
/* 1112 */         log.debug("Security Code " + securityCode + " (Vehicle-VIN " + vin + ")");
/* 1113 */         data.set(CommonAttribute.SECURITY_CODE, (Value)new ValueAdapter(securityCode));
/*      */       } 
/*      */       
/* 1116 */       if (data.getValue(CommonAttribute.CONTROLLER_SECURITY_CODE) == null) {
/* 1117 */         ValueAdapter valueAdapter; Value value = CommonValue.DISABLE;
/* 1118 */         String vin = accessVIN(session);
/* 1119 */         if (vin != null && vin.trim().length() == 0) {
/* 1120 */           vin = null;
/*      */         }
/* 1122 */         if (vin != null) {
/* 1123 */           log.debug("Determine Security Code (Vehicle-VIN " + session.getVehicle().getVIN() + ", Controller-VIN " + vin + ")");
/*      */         }
/* 1125 */         if (vin != null && !vin.equals(session.getVehicle().getVIN().toString())) {
/* 1126 */           String securityCodeCtrl = SPSSecurityCode.getInstance(this).getSecurityCode(vin);
/* 1127 */           if (securityCodeCtrl == null) {
/* 1128 */             throw new RequestException(builder.makeControllerSecurityCodeRequest(CommonAttribute.CONTROLLER_SECURITY_CODE, null, null, vin));
/*      */           }
/* 1130 */           log.debug("Security Code - Controller " + securityCodeCtrl + " (Controller-VIN " + vin + ")");
/* 1131 */           valueAdapter = new ValueAdapter(securityCodeCtrl);
/*      */         } 
/*      */         
/* 1134 */         data.set(CommonAttribute.CONTROLLER_SECURITY_CODE, (Value)valueAdapter);
/*      */       }
/*      */     
/* 1137 */     } else if (!isCALID || data.getValue(CommonAttribute.SECURITY_CODE) == null) {
/* 1138 */       data.set(CommonAttribute.SECURITY_CODE, CommonValue.DISABLE);
/* 1139 */     } else if (data.getValue(CommonAttribute.CONTROLLER_SECURITY_CODE) == null) {
/* 1140 */       data.set(CommonAttribute.CONTROLLER_SECURITY_CODE, CommonValue.DISABLE);
/*      */     } 
/*      */     
/* 1143 */     if (session.getController() instanceof SPSControllerVCI && ((SPSControllerVCI)session.getController()).getReplaceAttributes() != null) {
/* 1144 */       pdata.setVIT1TransferAttributes(((SPSControllerVCI)session.getController()).getReplaceAttributes());
/*      */     }
/* 1146 */     if (session.getController() instanceof SPSControllerVCI && data.getValue(CommonAttribute.DEALER_VCI) == null) {
/*      */       try {
/* 1148 */         int vci = session.getVehicle().getVIN().getSequenceNo() + ((SPSControllerVCI)session.getController()).getVCI();
/* 1149 */         data.set(CommonAttribute.DEALER_VCI, (Value)new ValueAdapter(Integer.valueOf(vci)));
/* 1150 */       } catch (Exception e) {
/* 1151 */         data.set(CommonAttribute.DEALER_VCI, (Value)new ValueAdapter(Integer.valueOf(((SPSControllerVCI)session.getController()).getVCI())));
/*      */       } 
/*      */     }
/* 1154 */     if (pdata.getHardwareList() != null) {
/* 1155 */       ProgrammingData update = handleHardwareList(session, data, pdata);
/* 1156 */       if (update != null) {
/* 1157 */         return update;
/*      */       }
/*      */     } 
/* 1160 */     if (session.getController() instanceof SPSControllerVCI && session.getController().getDeviceID() == 253) {
/* 1161 */       handleType4Executables(session, data, pdata);
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
/*      */ 
/*      */ 
/*      */     
/* 1175 */     ListValue parts = (ListValue)data.getValue(CommonAttribute.PROGRAMMING_DATA_SELECTION);
/* 1176 */     if (parts == null) {
/* 1177 */       throw new RequestException(builder.makeProgrammingDataSelectionRequest(CommonAttribute.PROGRAMMING_DATA_SELECTION, pdata.getModules()));
/*      */     }
/* 1179 */     if (data.getValue(CommonAttribute.ODOMETER) != null) {
/* 1180 */       String reading = (String)AVUtil.accessValue(data, CommonAttribute.ODOMETER);
/* 1181 */       pdata.setOdometer(reading);
/*      */     } 
/* 1183 */     if (data.getValue(CommonAttribute.KEYCODE) != null) {
/* 1184 */       String keycode = (String)AVUtil.accessValue(data, CommonAttribute.KEYCODE);
/* 1185 */       if (keycode.equals(session.getVehicle().getVIT1().getKeycode())) {
/* 1186 */         keycode = 'V' + keycode;
/*      */       }
/* 1188 */       pdata.setKeycode(keycode);
/*      */     } 
/* 1190 */     if (!isCALID && data.getValue(CommonAttribute.LABOR_TIME_CONFIGURATION) == null) {
/* 1191 */       handleLaborTimeConfiguration(data);
/*      */     }
/* 1193 */     if (data.getValue(CommonAttribute.SUMMARY) == null && !(session.getController() instanceof SPSControllerPROM)) {
/* 1194 */       handleSummary(session, data, pdata);
/*      */     }
/* 1196 */     if (needsCalibrationFileDownload(session, data) && pdata.getCalibrationFiles() == null) {
/* 1197 */       loadCalibrationFileInformation(data, pdata);
/*      */     }
/* 1199 */     return (ProgrammingData)pdata;
/*      */   }
/*      */   
/*      */   protected void handleLaborTimeConfiguration(AttributeValueMap data) {
/* 1203 */     ServiceID serviceID = (ServiceID)AVUtil.accessValue(data, ServiceResolution.ATTR_SERVICE_ID);
/* 1204 */     SPSLaborTimeConfiguration ltcfg = (serviceID == null) ? null : SPSLaborTime.getLaborTimeConfiguration(serviceID);
/* 1205 */     if (ltcfg != null) {
/* 1206 */       data.set(CommonAttribute.LABOR_TIME_CONFIGURATION, (Value)new ValueAdapter(ltcfg));
/*      */     } else {
/* 1208 */       data.set(CommonAttribute.LABOR_TIME_CONFIGURATION, CommonValue.DISABLE);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected String computeWarrantyClaimCode(SPSSession session, AttributeValueMap data) {
/* 1214 */     String psIDHexStr = null;
/* 1215 */     String wcc = null;
/* 1216 */     String VINtmepStr = null;
/* 1217 */     SPSProgrammingSequence SPSps = (SPSProgrammingSequence)session.getController();
/* 1218 */     int SPSpsID = SPSps.getProgrammingSequenceID();
/*      */     
/* 1220 */     psIDHexStr = getProgrammingSequenceIDHexString(SPSpsID);
/* 1221 */     String VINString = (String)AVUtil.accessValue(data, CommonAttribute.VIN);
/* 1222 */     VINtmepStr = VINString.substring(11, 17);
/* 1223 */     int vinint = Integer.parseInt(VINtmepStr);
/* 1224 */     String tempVIN = Integer.toHexString(vinint);
/*      */ 
/*      */ 
/*      */     
/* 1228 */     if (tempVIN.length() == 1)
/* 1229 */       tempVIN = "0000" + tempVIN; 
/* 1230 */     if (tempVIN.length() == 2)
/* 1231 */       tempVIN = "000" + tempVIN; 
/* 1232 */     if (tempVIN.length() == 3)
/* 1233 */       tempVIN = "00" + tempVIN; 
/* 1234 */     if (tempVIN.length() == 4)
/* 1235 */       tempVIN = "0" + tempVIN; 
/* 1236 */     String finalVIN = tempVIN.substring(3, 5);
/* 1237 */     wcc = psIDHexStr + finalVIN;
/* 1238 */     return wcc;
/*      */   }
/*      */   
/*      */   private String getProgrammingSequenceIDHexString(int SPSpsID) {
/* 1242 */     String psIDHexStr = null;
/* 1243 */     String tempStr = null;
/* 1244 */     if (SPSpsID <= 255) {
/* 1245 */       tempStr = Integer.toHexString(SPSpsID);
/* 1246 */       if (tempStr.length() == 1)
/* 1247 */         tempStr = "0" + tempStr; 
/* 1248 */       psIDHexStr = "S" + tempStr;
/* 1249 */     } else if (SPSpsID > 255 && SPSpsID <= 511) {
/* 1250 */       tempStr = Integer.toHexString(SPSpsID);
/* 1251 */       psIDHexStr = "T" + tempStr.substring(1, 3);
/* 1252 */     } else if (SPSpsID > 511 && SPSpsID <= 767) {
/* 1253 */       tempStr = Integer.toHexString(SPSpsID);
/* 1254 */       psIDHexStr = "U" + tempStr.substring(1, 3);
/* 1255 */     } else if (SPSpsID > 767 && SPSpsID <= 1023) {
/* 1256 */       tempStr = Integer.toHexString(SPSpsID);
/* 1257 */       psIDHexStr = "V" + tempStr.substring(1, 3);
/* 1258 */     } else if (SPSpsID > 1023 && SPSpsID <= 1279) {
/* 1259 */       tempStr = Integer.toHexString(SPSpsID);
/* 1260 */       psIDHexStr = "W" + tempStr.substring(1, 3);
/* 1261 */     } else if (SPSpsID > 1279 && SPSpsID <= 1535) {
/* 1262 */       tempStr = Integer.toHexString(SPSpsID);
/* 1263 */       psIDHexStr = "X" + tempStr.substring(1, 3);
/* 1264 */     } else if (SPSpsID > 1535 && SPSpsID <= 1791) {
/* 1265 */       tempStr = Integer.toHexString(SPSpsID);
/* 1266 */       psIDHexStr = "Y" + tempStr.substring(1, 3);
/* 1267 */     } else if (SPSpsID > 1791 && SPSpsID <= 2047) {
/* 1268 */       tempStr = Integer.toHexString(SPSpsID);
/* 1269 */       psIDHexStr = "Z" + tempStr.substring(1, 3);
/*      */     } 
/*      */     
/* 1272 */     return psIDHexStr;
/*      */   }
/*      */   
/*      */   protected void handleHardwareListPS(SPSSession session, AttributeValueMap data, SPSProgrammingSequence sequence) throws Exception {
/* 1276 */     PairValue selection = (PairValue)data.getValue(CommonAttribute.HARDWARE);
/* 1277 */     if (selection != null) {
/* 1278 */       String controllerID = selection.getID();
/* 1279 */       SPSHardware hardware = (SPSHardware)selection.getValue();
/* 1280 */       if (sequence.acceptHardwareSelection(controllerID, hardware)) {
/* 1281 */         sequence.setHardware(controllerID, hardware);
/*      */       } else {
/* 1283 */         reset(session, CommonAttribute.HARDWARE);
/*      */       } 
/* 1285 */       sequence.update(this);
/*      */     } 
/* 1287 */     if (sequence.getHardwareList() != null) {
/* 1288 */       throw new RequestException(makeHardwareSelectionRequest(sequence.getHardwareList(), sequence));
/*      */     }
/*      */   }
/*      */   
/*      */   protected void handleSecurityCodePS(SPSSession session, AttributeValueMap data, SPSProgrammingSequence sequence) throws Exception {
/* 1293 */     Value modeValue = data.getValue(CommonAttribute.EXECUTION_MODE);
/* 1294 */     boolean isCALID = (modeValue != null && modeValue == CommonValue.EXECUTION_MODE_INFO);
/* 1295 */     if (isCALID || !sequence.isSecurityCodeRequired()) {
/* 1296 */       data.set(CommonAttribute.SECURITY_CODE, CommonValue.DISABLE);
/* 1297 */       data.set(CommonAttribute.CONTROLLER_SECURITY_CODE, CommonValue.DISABLE);
/*      */       return;
/*      */     } 
/* 1300 */     if (data.getValue(CommonAttribute.SECURITY_CODE) == null || data.getValue(CommonAttribute.SECURITY_CODE) == CommonValue.DISABLE) {
/* 1301 */       String vin = session.getVehicle().getVIN().toString();
/* 1302 */       String securityCode = SPSSecurityCode.getInstance(this).getSecurityCode(vin);
/* 1303 */       if (securityCode == null) {
/* 1304 */         throw new RequestException(builder.makeInputRequest(CommonAttribute.SECURITY_CODE, null, null));
/*      */       }
/* 1306 */       log.debug("Security Code " + securityCode + " (Vehicle-VIN " + vin + ")");
/* 1307 */       data.set(CommonAttribute.SECURITY_CODE, (Value)new ValueAdapter(securityCode));
/*      */     } 
/*      */     
/* 1310 */     if (data.getValue(CommonAttribute.CONTROLLER_SECURITY_CODE) == null) {
/* 1311 */       ValueAdapter valueAdapter; Value value = CommonValue.DISABLE;
/* 1312 */       String vin = sequence.getReplacedVIN();
/* 1313 */       log.debug("Determine Security Code (Vehicle-VIN " + session.getVehicle().getVIN() + ", Controller-VIN " + vin + ")");
/* 1314 */       if (vin != null && !vin.equals(session.getVehicle().getVIN().toString())) {
/* 1315 */         String securityCodeCtrl = SPSSecurityCode.getInstance(this).getSecurityCode(vin);
/* 1316 */         if (securityCodeCtrl == null) {
/* 1317 */           throw new RequestException(builder.makeControllerSecurityCodeRequest(CommonAttribute.CONTROLLER_SECURITY_CODE, null, null, vin));
/*      */         }
/* 1319 */         log.debug("Security Code - Controller " + securityCodeCtrl + " (Controller-VIN " + vin + ")");
/* 1320 */         valueAdapter = new ValueAdapter(securityCodeCtrl);
/*      */       } 
/*      */       
/* 1323 */       data.set(CommonAttribute.CONTROLLER_SECURITY_CODE, (Value)valueAdapter);
/* 1324 */     } else if (data.getValue(CommonAttribute.CONTROLLER_SECURITY_CODE) == null) {
/* 1325 */       data.set(CommonAttribute.CONTROLLER_SECURITY_CODE, CommonValue.DISABLE);
/*      */     } 
/*      */   }
/*      */   
/*      */   public ProgrammingData getProgrammingDataPS(SPSSession session, AttributeValueMap data) throws Exception {
/* 1330 */     SPSProgrammingSequence sequence = (SPSProgrammingSequence)session.getController();
/* 1331 */     handleSecurityCodePS(session, data, sequence);
/* 1332 */     PairValue selection = (PairValue)data.getValue(CommonAttribute.HARDWARE);
/* 1333 */     if (data.getValue(CommonAttribute.HARDWARE) != null) {
/* 1334 */       sequence.checkModifiedHardwareSelection(selection.getID(), (SPSHardware)selection.getValue(), this);
/*      */     }
/* 1336 */     if (sequence.getHardwareList() != null)
/*      */     {
/* 1338 */       handleHardwareListPS(session, data, sequence);
/*      */     }
/* 1340 */     if (!sequence.isType4OnlySequence()) {
/* 1341 */       ListValue parts = (ListValue)data.getValue(CommonAttribute.PROGRAMMING_DATA_SELECTION);
/* 1342 */       if (parts == null) {
/* 1343 */         throw new RequestException(builder.makeProgrammingDataSelectionRequest(CommonAttribute.PROGRAMMING_DATA_SELECTION, sequence));
/*      */       }
/*      */     } 
/* 1346 */     if (data.getValue(CommonAttribute.ODOMETER) != null) {
/* 1347 */       String reading = (String)AVUtil.accessValue(data, CommonAttribute.ODOMETER);
/* 1348 */       sequence.setOdometer(reading);
/*      */     } 
/* 1350 */     if (data.getValue(CommonAttribute.KEYCODE) != null) {
/* 1351 */       String keycode = (String)AVUtil.accessValue(data, CommonAttribute.KEYCODE);
/* 1352 */       if (keycode.equals(session.getVehicle().getVIT1().getKeycode())) {
/* 1353 */         keycode = 'V' + keycode;
/*      */       }
/* 1355 */       sequence.setKeycode(keycode);
/*      */     } 
/* 1357 */     if (data.getValue(CommonAttribute.SUMMARY) == null) {
/* 1358 */       sequence.evaluateProgrammingDataSelection(data);
/* 1359 */       Value modeValue = data.getValue(CommonAttribute.EXECUTION_MODE);
/* 1360 */       boolean isCALID = (modeValue != null && modeValue == CommonValue.EXECUTION_MODE_INFO);
/* 1361 */       if (!isCALID && data.getValue(CommonAttribute.LABOR_TIME_CONFIGURATION) == null) {
/* 1362 */         handleLaborTimeConfiguration(data);
/*      */       }
/* 1364 */       if (!sequence.isType4OnlySequence()) {
/* 1365 */         throw new RequestException(builder.makeDisplaySummaryRequest(CommonAttribute.SUMMARY));
/*      */       }
/* 1367 */       throw new RequestException(builder.makeDisplaySummaryRequest(CommonAttribute.SUMMARY, sequence));
/*      */     } 
/*      */     
/* 1370 */     sequence.loadCalibrationFileInformation(data, this);
/* 1371 */     return null;
/*      */   }
/*      */   
/*      */   protected boolean needsCalibrationFileDownload(SPSSession session, AttributeValueMap data) {
/* 1375 */     Value modeValue = data.getValue(CommonAttribute.EXECUTION_MODE);
/* 1376 */     if (modeValue == null || modeValue != CommonValue.EXECUTION_MODE_INFO) {
/* 1377 */       if (isProgrammingSequence(session)) {
/* 1378 */         if (CommonValue.FAIL.equals(data.getValue(CommonAttribute.SAME_CALIBRATIONS))) {
/* 1379 */           return false;
/*      */         }
/* 1381 */       } else if (data.getValue(CommonAttribute.ARCHIVE) == null) {
/* 1382 */         if (!(session.getController() instanceof SPSControllerPROM)) {
/* 1383 */           return !isNonProgrammingVCI(session);
/*      */         }
/*      */       } else {
/* 1386 */         String vci = (String)AVUtil.accessValue(data, CommonAttribute.VCI);
/* 1387 */         long input = -1L;
/*      */         try {
/* 1389 */           input = Long.parseLong(vci);
/* 1390 */         } catch (Exception e) {}
/*      */         
/* 1392 */         return isProgrammingByFileVCI(input);
/*      */       } 
/*      */     }
/* 1395 */     return false;
/*      */   }
/*      */   
/*      */   protected void loadCalibrationFileInformation(AttributeValueMap data, SPSProgrammingData pdata) throws Exception {
/* 1399 */     List<SPSModule> modules = pdata.getModules();
/* 1400 */     if (modules.size() == 1) {
/* 1401 */       SPSModule module = modules.get(0);
/* 1402 */       Part part = module.getSelectedPart();
/* 1403 */       if (!SPSPart.isEndItemPart(part.getPartNumber(), this)) {
/* 1404 */         throw new SPSException(CommonException.NoValidEndItemPart);
/*      */       }
/* 1406 */       pdata.setCalibrationFiles(SPSModule.getCalibrationFileInfo(part.getID(), this));
/*      */     } else {
/* 1408 */       List<Part> parts = new ArrayList();
/* 1409 */       for (int i = 0; i < modules.size(); i++) {
/* 1410 */         SPSModule module = modules.get(i);
/* 1411 */         parts.add(module.getSelectedPart());
/*      */       } 
/* 1413 */       List<SPSBlobImpl> calibrations = SPSModule.getCalibrationFileInfo(parts, this, requiresCustomCalibration(data));
/* 1414 */       pdata.setCalibrationFiles(calibrations);
/* 1415 */       for (int j = 0; j < calibrations.size(); j++) {
/* 1416 */         SPSBlobImpl calibration = calibrations.get(j);
/* 1417 */         String partNo = calibration.getBlobName();
/* 1418 */         SPSModule module = findModule(modules, partNo);
/* 1419 */         calibration.setBlobID(Integer.valueOf(module.getID()));
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected SPSModule findModule(List<SPSModule> modules, String partNo) {
/* 1425 */     for (int i = 0; i < modules.size(); i++) {
/* 1426 */       SPSModule module = modules.get(i);
/* 1427 */       Part part = module.getSelectedPart();
/* 1428 */       if (partNo.equals(part.getPartNumber())) {
/* 1429 */         return module;
/*      */       }
/*      */     } 
/* 1432 */     return null;
/*      */   }
/*      */   
/*      */   protected boolean checkHardwareSelection(SPSHardware selection, List<SPSHardwareIndex> required, List<SPSHardware> hardware) {
/* 1436 */     if (hardware != null) {
/* 1437 */       for (int j = 0; j < hardware.size(); j++) {
/* 1438 */         SPSHardware hw = hardware.get(j);
/* 1439 */         if (hw.equals(selection)) {
/* 1440 */           return true;
/*      */         }
/*      */       } 
/*      */     }
/* 1444 */     for (int i = 0; i < required.size(); i++) {
/*      */       
/* 1446 */       SPSHardwareIndex hwidx = required.get(i);
/* 1447 */       if (hwidx.contains(selection)) {
/* 1448 */         return true;
/*      */       }
/*      */     } 
/* 1451 */     return true;
/*      */   }
/*      */   
/*      */   protected AssignmentRequest makeHardwareSelectionRequest(SPSProgrammingData pdata) throws Exception {
/* 1455 */     return makeHardwareSelectionRequest(pdata.getHardwareList(), (SPSProgrammingSequence)null);
/*      */   }
/*      */ 
/*      */   
/*      */   protected AssignmentRequest makeHardwareSelectionRequest(List<SPSHardwareIndex> hardware, SPSProgrammingSequence sequence) throws Exception {
/* 1460 */     String description = ((SPSHardwareIndex)hardware.get(0)).getDescription();
/* 1461 */     List<SPSHardware> parts = new ArrayList();
/* 1462 */     for (int i = 0; i < hardware.size(); i++) {
/* 1463 */       SPSHardwareIndex hw = hardware.get(i);
/* 1464 */       if (hw.getParts() != null) {
/* 1465 */         for (int j = 0; j < hw.getParts().size(); j++) {
/* 1466 */           SPSHardware part = hw.getParts().get(j);
/* 1467 */           if (!parts.contains(part)) {
/* 1468 */             parts.add(part);
/*      */           }
/*      */         } 
/*      */       }
/*      */     } 
/* 1473 */     if (sequence != null) {
/* 1474 */       parts = sequence.filterConstraints(parts, this);
/* 1475 */       Collections.sort(parts);
/* 1476 */       String id = sequence.getControllerID4HWSelection();
/* 1477 */       if (sequence.hasSequenceHardwareDependency()) {
/* 1478 */         String label = sequence.getLabel();
/* 1479 */         int indexTab = label.indexOf("\t");
/* 1480 */         if (indexTab >= 0) {
/* 1481 */           label = label.substring(0, indexTab);
/*      */         }
/* 1483 */         return (AssignmentRequest)builder.makeHardwareSelectionRequest(CommonAttribute.HARDWARE, description, parts, id, label);
/*      */       } 
/* 1485 */       return (AssignmentRequest)builder.makeHardwareSelectionRequest(CommonAttribute.HARDWARE, description, parts, id);
/*      */     } 
/*      */     
/* 1488 */     Collections.sort(parts);
/* 1489 */     return (AssignmentRequest)builder.makeHardwareSelectionRequest(CommonAttribute.HARDWARE, description, parts);
/*      */   }
/*      */ 
/*      */   
/*      */   protected AssignmentRequest displayPreProgrammingInstructions(SPSSession session, AttributeValueMap data) throws Exception {
/* 1494 */     SPSController controller = session.getController();
/* 1495 */     if (controller.getPreProgrammingInstructions() != null) {
/* 1496 */       if (isProgrammingSequence(session) && 
/* 1497 */         CommonValue.FAIL.equals(data.getValue(CommonAttribute.SAME_CALIBRATIONS))) {
/* 1498 */         return null;
/*      */       }
/*      */       
/* 1501 */       List instructions = getModel().getProgrammingMessageVCI((SPSLanguage)session.getLanguage(), controller.getPreProgrammingInstructions());
/* 1502 */       if (instructions != null) {
/* 1503 */         InstructionDisplayRequest instructionDisplayRequest = builder.makeInstructionDisplayRequest(CommonAttribute.PRE_PROGRAMMING_INSTRUCTIONS, "pre-prog-instructions", instructions);
/* 1504 */         instructionDisplayRequest.setAutoSubmit(false);
/* 1505 */         throw new RequestException(instructionDisplayRequest);
/*      */       } 
/* 1507 */       data.set(CommonAttribute.PRE_PROGRAMMING_INSTRUCTIONS, CommonValue.FAIL);
/*      */     } 
/*      */     
/* 1510 */     return null;
/*      */   }
/*      */   
/*      */   protected static AssignmentRequest displaySummaryScreen(SPSSession session, AttributeValueMap data) {
/* 1514 */     return (AssignmentRequest)builder.makeDisplaySummaryRequest(CommonAttribute.SUMMARY);
/*      */   }
/*      */   
/*      */   protected boolean isNonProgrammingVCI(SPSSession session) {
/* 1518 */     if (session.getController() instanceof SPSControllerVCI) {
/* 1519 */       SPSControllerVCI controller = (SPSControllerVCI)session.getController();
/* 1520 */       return (controller.getVCI() == 10);
/*      */     } 
/* 1522 */     return false;
/*      */   }
/*      */   
/*      */   protected static boolean isProgrammingSequence(SPSSession session) {
/* 1526 */     return session.getController() instanceof SPSProgrammingSequence;
/*      */   }
/*      */   
/*      */   protected boolean checkProgrammingData(AttributeValueMap data, SPSSession session) throws Exception {
/* 1530 */     if (data.getValue(CommonAttribute.SAME_CALIBRATIONS) != null)
/* 1531 */       return true; 
/* 1532 */     if (isProgrammingSequence(session)) {
/* 1533 */       SPSProgrammingSequence sequence = (SPSProgrammingSequence)session.getController();
/* 1534 */       List<SPSProgrammingData> pdatas = sequence.getProgrammingDataList();
/* 1535 */       boolean sameCalibration = true;
/* 1536 */       for (int i = 0; i < pdatas.size(); i++) {
/* 1537 */         SPSProgrammingData sPSProgrammingData = pdatas.get(i);
/* 1538 */         if (sPSProgrammingData.getDeviceID().intValue() != 253) {
/* 1539 */           if (!checkModules(data, session, sPSProgrammingData)) {
/* 1540 */             return false;
/*      */           }
/* 1542 */           sameCalibration = (sameCalibration && sPSProgrammingData.skipSameCalibration());
/*      */         } else {
/* 1544 */           sameCalibration = false;
/*      */         } 
/*      */       } 
/* 1547 */       if (sameCalibration)
/*      */       {
/* 1549 */         data.set(CommonAttribute.SAME_CALIBRATIONS, CommonValue.FAIL);
/*      */       }
/* 1551 */       return true;
/*      */     } 
/* 1553 */     SPSProgrammingData pdata = (SPSProgrammingData)session.getProgrammingData(this);
/* 1554 */     return checkModules(data, session, pdata);
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean checkModules(AttributeValueMap data, SPSSession session, SPSProgrammingData pdata) throws Exception {
/* 1559 */     if (!pdata.isValid()) {
/* 1560 */       return false;
/*      */     }
/* 1562 */     List<SPSModule> modules = pdata.getModules();
/*      */     
/* 1564 */     boolean valid = false;
/* 1565 */     boolean suspect = false;
/* 1566 */     for (int i = 0; i < modules.size(); i++) {
/* 1567 */       SPSModule module = modules.get(i);
/* 1568 */       if (modules.size() == 1 || (modules.size() > 1 && !module.getID().equals("0"))) {
/* 1569 */         if (module.isCalibrationUpdate()) {
/* 1570 */           valid = true;
/*      */         }
/* 1572 */         if (module.getCurrentCalibration() != null && module.getCurrentCalibration().startsWith("*")) {
/* 1573 */           suspect = true;
/*      */         }
/*      */       } 
/* 1576 */       if (module.isMismatchCVN());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1582 */     if (!suspect || data.getValue(CommonAttribute.MODE).equals(CommonValue.REPLACE_AND_REPROGRAM) || 
/* 1583 */       session.getProgrammingType().getID().equals(SPSProgrammingType.VCI)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1590 */       if (valid) {
/* 1591 */         return true;
/*      */       }
/* 1593 */       if (session.getController() instanceof SPSProgrammingSequence) {
/* 1594 */         pdata.indicateSameCalibration();
/*      */         
/* 1596 */         return true;
/*      */       } 
/*      */       
/* 1599 */       throw new SPSException(CommonException.ConfirmSameCalibration);
/*      */     } 
/*      */     pdata.invalidate();
/*      */     throw new SPSException(CommonException.InvalidCurrentCalibration);
/*      */   }
/*      */   protected Boolean _reprogram(ProgrammingData pdata, AttributeValueMap data) throws Exception {
/* 1605 */     SPSSession session = lookupSession(data);
/* 1606 */     if (data.getValue(CommonAttribute.REPROGRAM) == null) {
/* 1607 */       if (!isNonProgrammingVCI(session) && !(session.getController() instanceof SPSControllerPROM) && 
/* 1608 */         !checkProgrammingData(data, session)) {
/* 1609 */         return Boolean.TRUE;
/*      */       }
/*      */       
/* 1612 */       if (data.getValue(CommonAttribute.PRE_PROGRAMMING_INSTRUCTIONS) == null && !(session.getController() instanceof SPSControllerPROM)) {
/* 1613 */         AssignmentRequest request = displayPreProgrammingInstructions(session, data);
/* 1614 */         if (request != null) {
/* 1615 */           throw new RequestException(request);
/*      */         }
/*      */       } 
/* 1618 */       if (isNonProgrammingVCI(session) || session.getController() instanceof SPSControllerPROM) {
/* 1619 */         return Boolean.TRUE;
/*      */       }
/* 1621 */       if (data.getValue(CommonAttribute.CONFIRM_PROGRAMMING_DATA_DOWNLOAD_START) == null && 
/* 1622 */         needsCalibrationFileDownload(session, data) && !isNonProgrammingVCI(session)) {
/* 1623 */         List blobs = null;
/* 1624 */         if (session.getController() instanceof SPSProgrammingSequence) {
/* 1625 */           blobs = ((SPSProgrammingSequence)session.getController()).getCalibrationFiles();
/*      */         } else {
/* 1627 */           blobs = pdata.getCalibrationFiles();
/*      */         } 
/* 1629 */         throw new RequestException(builder.makeProgrammingDataDownloadRequest(CommonAttribute.CONFIRM_PROGRAMMING_DATA_DOWNLOAD_START, blobs));
/*      */       } 
/*      */       
/* 1632 */       if (data.getValue(CommonAttribute.CONFIRM_REPROGRAM_DISPLAY) == null) {
/* 1633 */         boolean skip = false;
/* 1634 */         if (isProgrammingSequence(session) && 
/* 1635 */           CommonValue.FAIL.equals(data.getValue(CommonAttribute.SAME_CALIBRATIONS))) {
/* 1636 */           skip = true;
/*      */         }
/*      */         
/* 1639 */         if (!skip) {
/* 1640 */           throw new RequestException(builder.makeReprogramDisplayRequest());
/*      */         }
/*      */       } 
/* 1643 */       if (data.getValue(CommonAttribute.CONFIRM_PROGRAMMING_DATA_DOWNLOAD_FINISHED) == null && 
/* 1644 */         needsCalibrationFileDownload(session, data)) {
/* 1645 */         throw new RequestException(builder.makeProgrammingDataDownloadContinuationRequest());
/*      */       }
/*      */       
/* 1648 */       if (session.getController() instanceof SPSProgrammingSequence) {
/* 1649 */         SPSProgrammingSequence sequence = (SPSProgrammingSequence)session.getController();
/* 1650 */         throw new RequestException(builder.makeReprogramRequest(CommonAttribute.REPROGRAM, sequence.getSequence(), sequence.getProgrammingDataList(), sequence.getType4Data(), sequence.getInstructions(getModel()), sequence.getFailureHandling()));
/*      */       } 
/* 1652 */       throw new RequestException(builder.makeReprogramRequest(CommonAttribute.REPROGRAM, pdata));
/*      */     } 
/*      */     
/* 1655 */     if (data.getValue(CommonAttribute.REPROGRAM_PROTOCOL) == null) {
/* 1656 */       SPSEvents.logReprogramEvent(data, session, this);
/* 1657 */       data.set(CommonAttribute.REPROGRAM_PROTOCOL, CommonValue.OK);
/* 1658 */       Value success = data.getValue(CommonAttribute.REPROGRAM);
/* 1659 */       if (!success.equals(CommonValue.OK)) {
/* 1660 */         session.done();
/* 1661 */         return Boolean.TRUE;
/*      */       } 
/*      */     } 
/* 1664 */     if (data.getValue(CommonAttribute.FINAL_INSTRUCTIONS) == null) {
/* 1665 */       String toolType = (String)AVUtil.accessValue(data, CommonAttribute.DEVICE);
/* 1666 */       String content = toolType + "-" + "final-instructions";
/* 1667 */       List instructions = null;
/* 1668 */       SPSController controller = session.getController();
/* 1669 */       if (controller.getPostProgrammingInstructions() != null) {
/* 1670 */         if (isProgrammingSequence(session) && 
/* 1671 */           CommonValue.FAIL.equals(data.getValue(CommonAttribute.SAME_CALIBRATIONS))) {
/* 1672 */           session.done();
/* 1673 */           return Boolean.TRUE;
/*      */         } 
/*      */         
/* 1676 */         instructions = getModel().getProgrammingMessageVCI((SPSLanguage)session.getLanguage(), controller.getPostProgrammingInstructions());
/*      */       } 
/* 1678 */       InstructionDisplayRequest instructionDisplayRequest = builder.makeInstructionDisplayRequest(CommonAttribute.FINAL_INSTRUCTIONS, content, instructions);
/* 1679 */       throw new RequestException(instructionDisplayRequest);
/*      */     } 
/* 1681 */     session.done();
/* 1682 */     return Boolean.TRUE;
/*      */   }
/*      */   
/*      */   protected byte[] _getData(ProgrammingDataUnit blob, AttributeValueMap data) throws RequestException, Exception {
/* 1686 */     SPSSession session = lookupSession(data);
/* 1687 */     if (session != null && 
/* 1688 */       data.getValue(CommonAttribute.ARCHIVE) != null) {
/* 1689 */       SPSArchive archive = (SPSArchive)((SPSControllerVCI)session.getController()).getArchive();
/* 1690 */       if (archive != null) {
/* 1691 */         List<ProgrammingDataUnit> units = archive.getCalibrationUnits();
/* 1692 */         List<byte[]> calibrations = archive.getCalibrationFiles();
/* 1693 */         for (int i = 0; i < calibrations.size(); i++) {
/* 1694 */           ProgrammingDataUnit unit = units.get(i);
/* 1695 */           if (unit.getBlobName().equals(blob.getBlobName())) {
/* 1696 */             return calibrations.get(i);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1702 */     Integer calibration = Integer.valueOf(blob.getBlobName());
/* 1703 */     byte[] bytes = SPSModule.getCalibrationFile(calibration.intValue(), this, requiresCustomCalibration(data));
/* 1704 */     if (bytes != null) {
/* 1705 */       return bytes;
/*      */     }
/* 1707 */     throw new SPSException(CommonException.MissingCalibrationFile);
/*      */   }
/*      */   
/*      */   public String getBulletin(String locale, String bulletin) {
/*      */     try {
/* 1712 */       SPSLanguage language = SPSLanguage.getLanguage(SPSLanguage.lookupLocale(locale), this);
/* 1713 */       if (language == null) {
/* 1714 */         return null;
/*      */       }
/* 1716 */       return SPSBulletin.loadBulletin(language, bulletin, this);
/* 1717 */     } catch (Exception e) {
/*      */       
/* 1719 */       return null;
/*      */     } 
/*      */   }
/*      */   public String getHTML(String locale, String id) {
/*      */     try {
/* 1724 */       SPSLanguage language = SPSLanguage.getLanguage(SPSLanguage.lookupLocale(locale), this);
/* 1725 */       if (language == null) {
/* 1726 */         return null;
/*      */       }
/* 1728 */       return SPSInstructions.loadHTML(language, id, this);
/* 1729 */     } catch (Exception e) {
/*      */       
/* 1731 */       return null;
/*      */     } 
/*      */   }
/*      */   public byte[] getImage(String id) {
/*      */     try {
/* 1736 */       return SPSInstructions.loadImage(id, this);
/* 1737 */     } catch (Exception e) {
/*      */       
/* 1739 */       return null;
/*      */     } 
/*      */   }
/*      */   private boolean aclProceedSameVINGranted(String sessionID) {
/* 1743 */     boolean result = false;
/* 1744 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/*      */     try {
/* 1746 */       ACLService aclService = ACLServiceProvider.getInstance().getService();
/* 1747 */       Map usrGroup2Manuf = context.getSharedContext().getUsrGroup2ManufMap();
/* 1748 */       Set<String> tmp = new HashSet();
/* 1749 */       tmp.add("proceed-same-vin");
/* 1750 */       result = (aclService.getAuthorizedResources("AdapterData", tmp, usrGroup2Manuf, context.getSharedContext().getCountry()).size() != 0);
/*      */     }
/* 1752 */     catch (Exception e) {
/* 1753 */       log.warn("unable to decide, setting result to false - exception: " + e);
/*      */     } 
/*      */     
/* 1756 */     return result;
/*      */   }
/*      */   
/*      */   public Object getVersionInfo() {
/*      */     try {
/* 1761 */       if (ApplicationContext.getInstance().isStandalone()) {
/* 1762 */         List<DBVersionInformation> versionInfo = new ArrayList(1);
/* 1763 */         String version = this.configuration.getProperty("db.version");
/* 1764 */         String description = this.configuration.getProperty("db.description");
/* 1765 */         versionInfo.add(new DBVersionInformation(null, "", new Date(), description, version));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1775 */         return versionInfo;
/*      */       } 
/* 1777 */       return getModel().getVersionInfo();
/*      */     }
/* 1779 */     catch (Exception e) {
/* 1780 */       log.warn("unable to retrieve version information, returning null - exception:" + e, e);
/* 1781 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void initQuickUpdate(Configuration configuration) {
/*      */     try {
/* 1787 */       if (configuration.getProperty("tcsc.db.drv") != null || configuration.getProperty("tcsc.db.data-source") != null) {
/* 1788 */         DatabaseLink databaseLink = DatabaseLink.openDatabase(configuration, "tcsc.db");
/* 1789 */         SPSControllerVCI.initQuickUpdate(this, (IDatabaseLink)databaseLink);
/* 1790 */         SPSSecurityCode.initQuickUpdate(this, (IDatabaseLink)databaseLink);
/*      */       } else {
/* 1792 */         log.info("TCSC Quick Update not configured");
/*      */       } 
/* 1794 */     } catch (Exception ex) {
/* 1795 */       log.debug("failed to initialize TCSC Quick Update", ex);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void initCustomCalibrations(Configuration configuration) {
/*      */     try {
/* 1801 */       if (configuration.getProperty("customcal.db.drv") != null || configuration.getProperty("customcal.db.data-source") != null) {
/* 1802 */         DatabaseLink databaseLink = DatabaseLink.openDatabase(configuration, "customcal.db");
/* 1803 */         SPSModule.initCustomCalibrations(this, (IDatabaseLink)databaseLink);
/* 1804 */         String controllers = configuration.getProperty("OnStarCustomCalibration");
/* 1805 */         if (controllers != null) {
/* 1806 */           SPSControllerVCI.initCustomCalibrationControllers(this, controllers);
/*      */         } else {
/* 1808 */           log.info("No controllers with custom calibrations configured");
/*      */         } 
/*      */       } else {
/* 1811 */         log.info("Custom Calibrations not configured");
/*      */       } 
/* 1813 */     } catch (Exception ex) {
/* 1814 */       log.debug("failed to initialize Custom Calibrations Database Connection", ex);
/*      */     } 
/*      */   }
/*      */   
/*      */   public DatabaseInfo getDatabaseInfo(AttributeValueMap avMap, Attribute lastRequestedAttribute) throws Exception {
/* 1819 */     SPSSession session = lookupSession(avMap);
/* 1820 */     if (session != null) {
/* 1821 */       SPSDatabaseInfo dbinfo = new SPSDatabaseInfo(session, avMap);
/*      */       try {
/* 1823 */         dbinfo.handle(lastRequestedAttribute, this);
/* 1824 */       } catch (Exception x) {}
/*      */       
/* 1826 */       return dbinfo;
/*      */     } 
/* 1828 */     return null;
/*      */   }
/*      */   
/*      */   protected void _reset() {}
/*      */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\nao\SPSSchemaAdapterNAO.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */