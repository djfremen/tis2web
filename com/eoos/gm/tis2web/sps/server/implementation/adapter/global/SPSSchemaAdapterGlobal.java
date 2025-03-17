/*      */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.global;
/*      */ 
/*      */ import com.eoos.datatype.gtwo.PairImpl;
/*      */ import com.eoos.datatype.marker.Configurable;
/*      */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*      */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*      */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*      */ import com.eoos.gm.tis2web.frame.export.common.datatype.DBVersionInformation;
/*      */ import com.eoos.gm.tis2web.frame.export.common.util.ConNvent;
/*      */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*      */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLinkWrapper;
/*      */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*      */ import com.eoos.gm.tis2web.sids.service.cai.ServiceID;
/*      */ import com.eoos.gm.tis2web.sps.common.HardwareSelectionRequest;
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
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.AssignmentRequest;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.DisplayRequest;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.InstructionDisplayRequest;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.AVUtil;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.AttributeImpl;
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
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSPartFile;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSProgrammingFile;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSProgrammingType;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSSchemaAdapter;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSSession;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.dbinfo.DatabaseInfo;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.log.SPSEventLog;
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
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import org.apache.log4j.Logger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SPSSchemaAdapterGlobal
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
/*   87 */   private static Logger log = Logger.getLogger(SPSSchemaAdapterGlobal.class);
/*      */   
/*   89 */   protected static RequestBuilder builder = (RequestBuilder)new RequestBuilderImpl();
/*      */   
/*   91 */   private final Object SYNC_DBLINK = new Object();
/*      */   
/*      */   private Long NAO_DB_VERSION_TIMESTAMP;
/*      */   
/*   95 */   private IDatabaseLink databaseLink = null;
/*      */   
/*      */   private ConnectionProvider connectionProvider;
/*      */   
/*      */   public SPSSchemaAdapterGlobal(Configuration configuration) throws Exception {
/*  100 */     super(configuration);
/*      */   }
/*      */ 
/*      */   
/*      */   public void init() throws Exception {
/*  105 */     getModel().init();
/*  106 */     initQuickUpdate(this.configuration);
/*  107 */     initCustomCalibrations(this.configuration);
/*      */   }
/*      */   
/*      */   public Long getNAO_DB_VERSION_TIMESTAMP() {
/*  111 */     if (this.NAO_DB_VERSION_TIMESTAMP == null) {
/*  112 */       this.NAO_DB_VERSION_TIMESTAMP = Long.valueOf(getModel().getNAO_DB_Version_time_stamp());
/*      */     }
/*  114 */     return this.NAO_DB_VERSION_TIMESTAMP;
/*      */   }
/*      */   
/*      */   public IDatabaseLink getDatabaseLink() {
/*  118 */     synchronized (this.SYNC_DBLINK) {
/*  119 */       if (this.databaseLink == null) {
/*  120 */         log.info("initializing database link");
/*      */         try {
/*  122 */           final DatabaseLink backend = DatabaseLink.openDatabase(this.configuration, "db");
/*  123 */           this.connectionProvider = ConNvent.create(new ConnectionProvider()
/*      */               {
/*      */                 public void releaseConnection(Connection connection) {
/*  126 */                   backend.releaseConnection(connection);
/*      */                 }
/*      */                 
/*      */                 public Connection getConnection() {
/*      */                   try {
/*  131 */                     return backend.requestConnection();
/*  132 */                   } catch (Exception e) {
/*  133 */                     throw new RuntimeException(e);
/*      */                   } 
/*      */                 }
/*      */               },  30000L);
/*      */           
/*  138 */           this.databaseLink = (IDatabaseLink)new DatabaseLinkWrapper(backend)
/*      */             {
/*      */               public void releaseConnection(Connection connection) {
/*  141 */                 SPSSchemaAdapterGlobal.this.connectionProvider.releaseConnection(connection);
/*      */               }
/*      */               
/*      */               public Connection requestConnection() throws Exception {
/*  145 */                 return SPSSchemaAdapterGlobal.this.connectionProvider.getConnection();
/*      */               }
/*      */             };
/*      */         }
/*  149 */         catch (Exception e) {
/*  150 */           log.warn("... failed to initialize database link - exception:" + e + ", using fallback-db ");
/*      */           try {
/*  152 */             this.databaseLink = (IDatabaseLink)DatabaseLink.openDatabase(this.configuration, "fallback-db");
/*  153 */           } catch (Exception ex) {
/*  154 */             log.error("... unable to initialize fallback database link - exception: " + ex);
/*  155 */             throw new RuntimeException(e);
/*      */           } 
/*      */         } 
/*      */       } 
/*  159 */       return this.databaseLink;
/*      */     } 
/*      */   }
/*      */   
/*      */   private SPSModel getModel() {
/*  164 */     return SPSModel.getInstance(this);
/*      */   }
/*      */   
/*      */   public String getCalibrationVerificationNumber(String sessionID, String partNumber) {
/*  168 */     return SPSPart.getCVN(partNumber, this);
/*      */   }
/*      */   
/*      */   protected boolean assertVehicleState(SPSSession session, AttributeValueMap data, List<SPSOption> options) {
/*  172 */     SPSVehicle vehicle = (SPSVehicle)session.getVehicle();
/*  173 */     List<SPSOption> selections = vehicle.getOptions();
/*  174 */     List<SPSOption> obsolete = new ArrayList();
/*  175 */     List<SPSOption> modifications = new ArrayList();
/*  176 */     List<Value> additions = new ArrayList();
/*  177 */     if (selections != null)
/*      */     {
/*  179 */       for (int i = 0; i < selections.size(); i++) {
/*  180 */         SPSOption option = selections.get(i);
/*  181 */         SPSOption group = (SPSOption)option.getType();
/*  182 */         if (group != null) {
/*      */ 
/*      */           
/*  185 */           Value value = data.getValue((Attribute)group);
/*  186 */           if (value == null) {
/*  187 */             obsolete.add(option);
/*  188 */           } else if (!option.equals(value)) {
/*  189 */             modifications.add(option);
/*  190 */             additions.add(value);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*  195 */     if (options != null) {
/*  196 */       for (int i = 0; i < options.size(); i++) {
/*  197 */         SPSOption option = options.get(i);
/*  198 */         SPSOption group = (SPSOption)option.getType();
/*  199 */         if (group != null) {
/*      */ 
/*      */           
/*  202 */           Value value = data.getValue((Attribute)group);
/*  203 */           if (value != null && !isSelectedVehicleOption(vehicle, value)) {
/*  204 */             additions.add(value);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*  209 */     if (obsolete.size() > 0) {
/*  210 */       selections.removeAll(obsolete);
/*      */     }
/*  212 */     if (modifications.size() > 0) {
/*  213 */       selections.removeAll(modifications);
/*      */     }
/*  215 */     if (additions.size() > 0) {
/*  216 */       for (int i = 0; i < additions.size(); i++) {
/*  217 */         SPSOption option = (SPSOption)additions.get(i);
/*  218 */         vehicle.setOption(option);
/*      */       } 
/*      */     }
/*  221 */     return (obsolete.size() == 0 && modifications.size() == 0);
/*      */   }
/*      */   
/*      */   boolean isSelectedVehicleOption(SPSVehicle vehicle, Value value) {
/*  225 */     List<SPSOption> selections = vehicle.getOptions();
/*  226 */     if (selections != null) {
/*  227 */       for (int i = 0; i < selections.size(); i++) {
/*  228 */         SPSOption option = selections.get(i);
/*  229 */         if (option.equals(value)) {
/*  230 */           return true;
/*      */         }
/*      */       } 
/*      */     }
/*  234 */     return false;
/*      */   }
/*      */   
/*      */   protected SPSSession lookupSession(AttributeValueMap data) throws Exception {
/*  238 */     String sessionID = (String)AVUtil.accessValue(data, CommonAttribute.SESSION_ID);
/*  239 */     Value modeValue = data.getValue(CommonAttribute.EXECUTION_MODE);
/*  240 */     boolean isCALID = (modeValue != null && modeValue == CommonValue.EXECUTION_MODE_INFO);
/*  241 */     String sessionKey = isCALID ? ("CALID-" + SPSSession.class.getName()) : SPSSession.class.getName();
/*  242 */     SPSSession session = (SPSSession)ClientContextProvider.getInstance().getContext(sessionID).getObject(sessionKey);
/*  243 */     if (session == null || !assertSessionTag(session, data) || !assertVIN(session, data)) {
/*  244 */       Locale locale = (Locale)AVUtil.accessValue(data, CommonAttribute.LOCALE);
/*  245 */       SPSLanguage language = SPSLanguage.getLanguage(SPSLanguage.lookupLocale(locale), this);
/*  246 */       if (language == null) {
/*  247 */         throw new SPSException(CommonException.UnsupportedLocale);
/*      */       }
/*  249 */       SPSVIN vin = new SPSVIN((String)AVUtil.accessValue(data, CommonAttribute.VIN));
/*  250 */       if (!vin.validate()) {
/*  251 */         throw new SPSException(CommonException.InvalidVIN);
/*      */       }
/*  253 */       SPSVehicle vehicle = new SPSVehicle(getModel(), vin);
/*  254 */       long signature = System.currentTimeMillis();
/*  255 */       data.set(CommonAttribute.SESSION_SIGNATURE, (Value)new ValueAdapter(Long.valueOf(signature)));
/*  256 */       Long tag = (Long)AVUtil.accessValue(data, CommonAttribute.SESSION_TAG);
/*  257 */       session = new SPSSession(signature, tag.longValue(), getModel(), language, vehicle, sessionID);
/*  258 */       log.debug("sps session timestamp (ms): client=" + tag.longValue() + " server=" + signature + " delta=" + (tag.longValue() - signature));
/*  259 */       ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/*  260 */       context.storeObject(sessionKey, session);
/*  261 */       if (!isCALID) {
/*  262 */         session.registerLogoutListener(context, SPSEventLog.ADAPTER_NAO);
/*      */       }
/*      */     } 
/*  265 */     session.fixDataStorage(data);
/*  266 */     return session;
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
/*  284 */     if (!assertVIN(session, data)) {
/*  285 */       reset(session);
/*      */     }
/*  287 */     SPSControllerReference reference = session.getControllerReference();
/*  288 */     if (reference != null && 
/*  289 */       !assertVehicleState(session, data, reference.getOptions()))
/*      */     {
/*      */ 
/*      */ 
/*      */       
/*  294 */       reset(session, CommonAttribute.CONTROLLER);
/*      */     }
/*      */     
/*  297 */     SPSControllerList controllers = (SPSControllerList)session.getControllers();
/*  298 */     if (controllers != null) {
/*  299 */       if (!assertVehicleState(session, data, controllers.getOptions()))
/*      */       {
/*      */         
/*  302 */         reset(session);
/*      */       }
/*  304 */       if (!controllers.match(data)) {
/*  305 */         reset(session);
/*  306 */         session.getVehicle().setOption(controllers.getControllerTypeSelection());
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void checkControllerSelection(SPSSession session, AttributeValueMap data) throws Exception {
/*  312 */     SPSControllerReference reference = session.getControllerReference();
/*  313 */     if (reference != null) {
/*  314 */       SPSControllerReference selection = (SPSControllerReference)data.getValue(CommonAttribute.CONTROLLER);
/*  315 */       if (!reference.equals(selection)) {
/*  316 */         reset(session, CommonAttribute.CONTROLLER);
/*      */       }
/*      */     } 
/*  319 */     SPSProgrammingType method = session.getProgrammingType();
/*  320 */     if (method != null) {
/*  321 */       SPSProgrammingType selection = (SPSProgrammingType)data.getValue(CommonAttribute.CONTROLLER_METHOD);
/*  322 */       if (!method.equals(selection)) {
/*  323 */         reset(session, CommonAttribute.CONTROLLER);
/*  324 */       } else if (data.getValue(CommonAttribute.HARDWARE) == null) {
/*  325 */         if (session.getController() != null && session.getController() instanceof SPSControllerVCI && session.getController().getHardware() != null) {
/*  326 */           if (session.getVehicle().getVIT1() != null) {
/*  327 */             VIT1Data vit1 = session.getVehicle().getVIT1();
/*  328 */             if (vit1.getHWNumber() != null && vit1.getHWNumber().trim().length() > 0) {
/*      */               return;
/*      */             }
/*      */           } 
/*  332 */           reset(session, CommonAttribute.HARDWARE);
/*  333 */         } else if (session.getController() != null && session.getController() instanceof SPSProgrammingSequence && session.getController().getHardware() != null) {
/*  334 */           reset(session, CommonAttribute.HARDWARE);
/*      */         }
/*      */       
/*      */       }
/*  338 */       else if (session.getController() != null && session.getController() instanceof SPSControllerVCI && session.getController().getHardware() != null) {
/*  339 */         SPSHardware part = session.getController().getHardware().get(0);
/*  340 */         SPSHardware hardware = (SPSHardware)AVUtil.accessValue(data, CommonAttribute.HARDWARE);
/*  341 */         if (!hardware.equals(part)) {
/*  342 */           reset(session, CommonAttribute.HARDWARE);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void assertSessionMode(SPSSession session, AttributeValueMap data) throws Exception {
/*  350 */     Value mode = data.getValue(CommonAttribute.MODE);
/*  351 */     if (mode != null && session.getMode() != null && !mode.equals(session.getMode())) {
/*  352 */       reset(session);
/*      */     }
/*      */   }
/*      */   
/*      */   protected List checkDevices(AttributeValueMap data) throws Exception {
/*  357 */     if (isPassThru(data)) {
/*  358 */       return null;
/*      */     }
/*  360 */     Long navInfo = (Long)AVUtil.accessValue(data, CommonAttribute.VIT1_NAVIGATION_INFO);
/*  361 */     if (navInfo != null && getModel().verifyVTDNavigationInfo(navInfo)) {
/*  362 */       List<Integer> list = new ArrayList();
/*  363 */       list.add(Integer.valueOf(254));
/*  364 */       return list;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  372 */     List<Integer> devices = (List)AVUtil.accessValue(data, CommonAttribute.VIT1_DEVICE_LIST);
/*  373 */     Integer seeds = (Integer)AVUtil.accessValue(data, CommonAttribute.VIT1_SEED_COUNT);
/*  374 */     if (seeds.intValue() > 1) {
/*  375 */       devices.add(Integer.valueOf(254));
/*      */     }
/*  377 */     return devices;
/*      */   }
/*      */ 
/*      */   
/*      */   protected Controller _getController(AttributeValueMap data) throws Exception {
/*  382 */     SPSController ret = null;
/*      */     
/*  384 */     SPSSession session = lookupSession(data);
/*      */     
/*  386 */     assertSessionMode(session, data);
/*  387 */     checkControllerSelection(session, data);
/*  388 */     checkVehicleState(session, data);
/*  389 */     ret = session.getController();
/*  390 */     if (ret == null) {
/*  391 */       List devices = checkDevices(data);
/*  392 */       Value mode = data.getValue(CommonAttribute.MODE);
/*  393 */       String toolType = (String)AVUtil.accessValue(data, CommonAttribute.DEVICE);
/*  394 */       SPSControllerList controllers = (SPSControllerList)session.getControllers(devices, mode, toolType);
/*  395 */       if (controllers == null || !filterType4Executables(controllers, data)) {
/*  396 */         throw new SPSException(CommonException.NoControllers);
/*      */       }
/*  398 */       boolean isCALID = false;
/*  399 */       Value modeValue = data.getValue(CommonAttribute.EXECUTION_MODE);
/*  400 */       if (modeValue != null && modeValue == CommonValue.EXECUTION_MODE_INFO) {
/*  401 */         isCALID = true;
/*      */       }
/*  403 */       if (isCALID) {
/*  404 */         controllers.removeProgrammingSequences();
/*      */       } else {
/*  406 */         controllers.suppressProgrammingSequenceControllers();
/*  407 */         handleClearDTCs(data, session);
/*      */       } 
/*      */       
/*  410 */       session.fixDataStorage(data);
/*      */       
/*  412 */       List options = controllers.getOptions();
/*  413 */       if (options != null) {
/*      */ 
/*      */ 
/*      */         
/*  417 */         if (session.getVehicle().getOptions() != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  422 */           session.update(this);
/*      */ 
/*      */           
/*  425 */           options = controllers.getOptions();
/*  426 */           if (options == null) {
/*  427 */             throw new RequestException(makeControllerSelectionRequest(controllers));
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  433 */         throw new RequestException(makeOptionSelectionRequest(options));
/*      */       } 
/*      */       
/*  436 */       SPSControllerReference reference = (SPSControllerReference)data.getValue(CommonAttribute.CONTROLLER);
/*  437 */       SPSProgrammingType method = (SPSProgrammingType)data.getValue(CommonAttribute.CONTROLLER_METHOD);
/*  438 */       if (reference != null && method == null) {
/*  439 */         TypeSelectionRequest request = builder.makeTypeSelectionRequest(CommonAttribute.CONTROLLER_METHOD, reference.getProgrammingMethods(), null);
/*  440 */         throw new RequestException(request);
/*      */       } 
/*  442 */       if (reference != null && method != null) {
/*  443 */         if (session.getProgrammingType() != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  448 */           session.update(this);
/*      */         } else {
/*  450 */           session.setController(data, reference, method, this);
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
/*  465 */         if (session.getController() != null) {
/*  466 */           checkCustomCalibrationIndicator(data, session);
/*  467 */           ret = session.getController();
/*      */         } else {
/*  469 */           throw new RequestException(makeOptionSelectionRequest(reference.getOptions()));
/*      */         } 
/*      */       } else {
/*      */         
/*  473 */         throw new RequestException(makeControllerSelectionRequest(controllers));
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  479 */       if (AVUtil.accessValue(data, CommonAttribute.DEVICE_ID) == null) {
/*  480 */         data.set(CommonAttribute.DEVICE_ID, (Value)new ValueAdapter(Integer.valueOf(ret.getDeviceID())));
/*      */       }
/*  482 */     } catch (UnsupportedOperationException e) {}
/*      */     
/*  484 */     return (Controller)ret;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void handleClearDTCs(AttributeValueMap data, SPSSession session) {
/*  489 */     if (data.getValue(CommonAttribute.CLEAR_DTCS) == null && 
/*  490 */       session.getClearDTCFlag()) {
/*      */       
/*  492 */       data.set(CommonAttribute.CLEAR_DTCS, CommonValue.OK);
/*  493 */       if (session.getVehicleLink() > 0) {
/*  494 */         data.set(CommonAttribute.VEHICLE_LINK, (Value)new ValueAdapter(Integer.valueOf(session.getVehicleLink())));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void checkCustomCalibrationIndicator(AttributeValueMap data, SPSSession session) {
/*  501 */     if (session.getController() instanceof SPSControllerVCI && (
/*  502 */       (SPSControllerVCI)session.getController()).requiresCustomCalibration() && 
/*  503 */       data.getValue(CommonAttribute.CONTROLLER_REQUIRES_CUSTOM_CALIBRATION) == null) {
/*  504 */       data.set(CommonAttribute.CONTROLLER_REQUIRES_CUSTOM_CALIBRATION, CommonValue.OK);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean requiresCustomCalibration(AttributeValueMap data) {
/*  511 */     return (data.getValue(CommonAttribute.CONTROLLER_REQUIRES_CUSTOM_CALIBRATION) != null);
/*      */   }
/*      */   
/*      */   protected AssignmentRequest makeControllerSelectionRequest(List controllers) {
/*  515 */     return (AssignmentRequest)builder.makeControllerSelectionRequest(CommonAttribute.CONTROLLER, controllers, null);
/*      */   }
/*      */   
/*      */   protected AssignmentRequest makeVCIInputRequest() {
/*  519 */     return (AssignmentRequest)builder.makeInputRequest(CommonAttribute.VCI, null, null);
/*      */   }
/*      */   
/*      */   protected AssignmentRequest makeOptionSelectionRequest(List<SPSOption> options) throws Exception {
/*  523 */     SPSOption option = options.get(0);
/*  524 */     SPSOption group = (SPSOption)option.getType();
/*  525 */     if (group == null)
/*      */     {
/*  527 */       throw new SPSException(CommonException.NoControllerSelectionPossible);
/*      */     }
/*  529 */     List<SPSOption> list = new ArrayList();
/*  530 */     list.add(option);
/*  531 */     for (int i = 1; i < options.size(); i++) {
/*  532 */       SPSOption alternative = options.get(i);
/*  533 */       if (alternative.getType().equals(group)) {
/*  534 */         list.add(alternative);
/*      */       }
/*      */     } 
/*  537 */     if (list.size() == 1);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  542 */     return (AssignmentRequest)builder.makeSelectionRequest((Attribute)group, list, null);
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean filterType4Executables(SPSControllerList controllers, AttributeValueMap data) {
/*  547 */     Value modeValue = data.getValue(CommonAttribute.EXECUTION_MODE);
/*  548 */     if (modeValue != null && modeValue == CommonValue.EXECUTION_MODE_INFO) {
/*  549 */       Iterator<SPSControllerReference> it = controllers.iterator();
/*  550 */       while (it.hasNext()) {
/*  551 */         boolean remove = false;
/*  552 */         SPSControllerReference reference = it.next();
/*  553 */         List<SPSController> instances = reference.getControllers();
/*  554 */         for (int i = 0; instances != null && i < instances.size(); i++) {
/*  555 */           SPSController controller = instances.get(i);
/*  556 */           if (controller instanceof SPSControllerVCI && controller.getDeviceID() == 253) {
/*  557 */             remove = true;
/*      */           }
/*      */         } 
/*  560 */         if (remove) {
/*  561 */           it.remove();
/*      */         }
/*      */       } 
/*      */     } 
/*  565 */     return !controllers.isEmpty();
/*      */   }
/*      */   
/*      */   protected void handleType4Executables(SPSSession session, AttributeValueMap data, SPSProgrammingData pdata) throws Exception {
/*  569 */     if (data.getValue(CommonAttribute.TYPE4_DATA) == null) {
/*  570 */       String content = "type4-instructions";
/*  571 */       DisplayRequest request = builder.makeDisplayRequest(CommonAttribute.TYPE4_DATA, null, content);
/*  572 */       request.setAutoSubmit(false);
/*  573 */       throw new RequestException(request);
/*      */     } 
/*  575 */     List<PairImpl> files = new SPSType4Data(session.getLanguage().getLocale(), this);
/*  576 */     loadCalibrationFileInformation(data, pdata);
/*  577 */     List<ProgrammingDataUnit> calibrations = pdata.getCalibrationFiles();
/*  578 */     for (int i = 0; i < calibrations.size(); i++) {
/*  579 */       ProgrammingDataUnit file = calibrations.get(i);
/*  580 */       Integer executable = Integer.valueOf(file.getBlobName());
/*  581 */       byte[] bytes = SPSModule.getCalibrationFile(executable.intValue(), this, requiresCustomCalibration(data));
/*  582 */       if (bytes == null) {
/*  583 */         throw new SPSException(CommonException.MissingCalibrationFile);
/*      */       }
/*  585 */       if (session.getController() instanceof SPSControllerXML) {
/*  586 */         if (i == 0) {
/*      */           
/*  588 */           files.add(new PairImpl("Type4App.dll", bytes));
/*      */         } else {
/*  590 */           files.add(new PairImpl("XMLFile.xml", bytes));
/*      */         } 
/*      */       } else {
/*  593 */         files.add(new PairImpl(file.getBlobName(), bytes));
/*      */       } 
/*      */     } 
/*  596 */     if (session.getController() instanceof SPSControllerXML) {
/*  597 */       SPSVehicle vehicle = (SPSVehicle)session.getVehicle();
/*  598 */       byte[] bytes = ((SPSControllerXML)session.getController()).getBuildFile(vehicle).getBytes("ASCII");
/*  599 */       files.add(new PairImpl("BuildRecord.bld", bytes));
/*      */     } 
/*  601 */     data.set(CommonAttribute.TYPE4_DATA, (Value)new ListValueImpl(files));
/*  602 */     throw new RequestException(builder.makeReprogramRequest(CommonAttribute.REPROGRAM, pdata));
/*      */   }
/*      */ 
/*      */   
/*      */   protected void loadProgrammingFile(SPSSession session, AttributeValueMap data, long vci) {
/*  607 */     SPSProgrammingFile pfile = null;
/*      */     try {
/*  609 */       vci -= session.getVehicle().getVIN().getSequenceNo();
/*  610 */       vci -= 2147483647L;
/*  611 */       pfile = SPSProgrammingFile.load(vci, this);
/*  612 */       if (pfile != null) {
/*  613 */         if ("zip".equalsIgnoreCase(pfile.getType())) {
/*  614 */           data.set(CommonAttribute.ARCHIVE, (Value)new ValueAdapter(new SPSArchive(session.getLanguage(), (File)pfile.getFile())));
/*      */         } else {
/*  616 */           data.set(CommonAttribute.PART_FILE, (Value)new ValueAdapter(new SPSPartFile((byte[])pfile.getFile())));
/*      */         } 
/*      */         return;
/*      */       } 
/*  620 */       String dir = this.configuration.getProperty("vci-archives");
/*  621 */       File file = new File(dir, vci + "." + "zip");
/*  622 */       if (file.exists()) {
/*  623 */         data.set(CommonAttribute.ARCHIVE, (Value)new ValueAdapter(new SPSArchive(session.getLanguage(), file)));
/*      */       } else {
/*  625 */         dir = this.configuration.getProperty("sps.schema.adapter.global.class.vci-part-files");
/*  626 */         file = new File(dir, vci + "." + "prt");
/*  627 */         if (file.exists()) {
/*  628 */           data.set(CommonAttribute.PART_FILE, (Value)new ValueAdapter(new SPSPartFile(file)));
/*      */         }
/*      */       } 
/*  631 */     } catch (Exception e) {
/*  632 */       log.error("failed to handle vci=" + vci, e);
/*      */     } finally {
/*  634 */       if (pfile != null && "zip".equalsIgnoreCase(pfile.getType())) {
/*      */         try {
/*  636 */           ((File)pfile.getFile()).delete();
/*  637 */         } catch (Exception x) {
/*  638 */           log.error("failed to delete temporary file: " + pfile.getFile(), x);
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   protected boolean isProgrammingByFileVCI(long vci) {
/*  645 */     return (vci > 2147483647L);
/*      */   }
/*      */   
/*      */   protected boolean isArchiveVCI(long vci) {
/*  649 */     return (1001L == vci || 1002L == vci);
/*      */   }
/*      */   
/*      */   protected void handleVCI(SPSSession session, AttributeValueMap data) throws Exception {
/*  653 */     String vci = (String)AVUtil.accessValue(data, CommonAttribute.VCI);
/*  654 */     long input = -1L;
/*      */     try {
/*  656 */       input = Long.parseLong(vci);
/*  657 */     } catch (Exception e) {
/*  658 */       throw new SPSException(CommonException.InvalidVCIInput);
/*      */     } 
/*  660 */     if (isArchiveVCI(input)) {
/*  661 */       if (data.getValue(CommonAttribute.ARCHIVE) == null && data.getValue(CommonAttribute.PART_FILE) == null) {
/*  662 */         throw new RequestException(builder.makeProgrammingByFileRequest());
/*      */       }
/*  664 */       SPSControllerVCI controller = (SPSControllerVCI)session.getController();
/*  665 */       if (data.getValue(CommonAttribute.ARCHIVE) != null) {
/*  666 */         controller.setArchive((Archive)AVUtil.accessValue(data, CommonAttribute.ARCHIVE));
/*      */       } else {
/*  668 */         controller.setPartFile((PartFile)AVUtil.accessValue(data, CommonAttribute.PART_FILE));
/*      */       }
/*      */     
/*  671 */     } else if (isProgrammingByFileVCI(input)) {
/*  672 */       if (data.getValue(CommonAttribute.ARCHIVE) == null && data.getValue(CommonAttribute.PART_FILE) == null) {
/*  673 */         loadProgrammingFile(session, data, input);
/*      */       }
/*  675 */       SPSControllerVCI controller = (SPSControllerVCI)session.getController();
/*  676 */       if (data.getValue(CommonAttribute.ARCHIVE) != null) {
/*  677 */         controller.setArchive((Archive)AVUtil.accessValue(data, CommonAttribute.ARCHIVE));
/*  678 */       } else if (data.getValue(CommonAttribute.PART_FILE) != null) {
/*  679 */         controller.setPartFile((PartFile)AVUtil.accessValue(data, CommonAttribute.PART_FILE));
/*      */       } else {
/*  681 */         throw new SPSException(CommonException.INVALID_ARCHIVE_VCI);
/*      */       } 
/*      */     } else {
/*  684 */       SPSControllerVCI controller = (SPSControllerVCI)session.getController();
/*  685 */       if (controller != null) {
/*  686 */         input -= session.getVehicle().getVIN().getSequenceNo();
/*  687 */         if (input <= 0L) {
/*  688 */           throw new SPSException(CommonException.InvalidVCIInput);
/*      */         }
/*  690 */         if (controller.getVCI() <= 0) {
/*      */           
/*  692 */           controller.setVCI((int)input);
/*  693 */         } else if (controller.getVCI() != input) {
/*  694 */           reset(session, CommonAttribute.PROGRAMMING_DATA_SELECTION);
/*  695 */           controller.setVCI((int)input);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected Request makeVIT1Request(SPSSession session) throws Exception {
/*  702 */     SPSControllerVCI controller = (SPSControllerVCI)session.getController();
/*  703 */     int requestMethodID = controller.getRequestMethodID();
/*  704 */     int deviceID = controller.getDeviceID();
/*  705 */     List rdata = getModel().getRequestMethodData(requestMethodID, deviceID);
/*  706 */     return (Request)builder.makeVIT1Request(rdata);
/*      */   }
/*      */   
/*      */   protected void handleVIT1(SPSSession session, AttributeValueMap data) throws Exception {
/*  710 */     if (data.getValue(CommonAttribute.VIT1) == null)
/*  711 */       throw new RequestException(makeVIT1Request(session)); 
/*  712 */     if (session.getVehicle().getVIT1() == null) {
/*  713 */       VIT1Data vit1 = (VIT1Data)AVUtil.accessValue(data, CommonAttribute.VIT1);
/*  714 */       session.getVehicle().setVIT1(vit1);
/*      */ 
/*      */       
/*  717 */       if (vit1.getHWNumber() != null && vit1.getHWNumber().trim().length() > 0) {
/*      */         try {
/*  719 */           SPSHardware hardware = new SPSHardware(vit1.getHWNumber());
/*  720 */           session.getController().setHardware(hardware);
/*  721 */         } catch (Exception x) {
/*  722 */           log.warn("failed to register hardware '" + vit1.getHWNumber() + "'.");
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void handleVIT1PS(SPSSession session, AttributeValueMap data) throws Exception {
/*  729 */     SPSProgrammingSequence controller = (SPSProgrammingSequence)session.getController();
/*  730 */     List rmd = controller.handleVIT1(getModel(), session, data);
/*  731 */     if (rmd != null) {
/*  732 */       throw new RequestException(builder.makeVIT1Request(rmd));
/*      */     }
/*      */   }
/*      */   
/*      */   protected void handleOdometer(SPSSession session, AttributeValueMap data) throws Exception {
/*  737 */     VIT1Data vit1 = session.getVehicle().getVIT1();
/*  738 */     if (vit1.getOdometer() != null && 
/*  739 */       data.getValue(CommonAttribute.ODOMETER) == null) {
/*  740 */       DisplayableValue reading = DisplayableValueImpl.getInstance(vit1.getOdometer());
/*  741 */       throw new RequestException(builder.makeInputRequest(CommonAttribute.ODOMETER, reading, null));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void handleKeycode(SPSSession session, AttributeValueMap data) throws Exception {
/*  747 */     VIT1Data vit1 = session.getVehicle().getVIT1();
/*  748 */     if (vit1.getKeycode() != null && 
/*  749 */       data.getValue(CommonAttribute.KEYCODE) == null) {
/*  750 */       DisplayableValue value = DisplayableValueImpl.getInstance(vit1.getKeycode());
/*  751 */       throw new RequestException(builder.makeInputRequest(CommonAttribute.KEYCODE, value, null));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected ProgrammingData handleHardwareList(SPSSession session, AttributeValueMap data, SPSProgrammingData pdata) throws Exception {
/*  757 */     SPSHardware selection = (SPSHardware)data.getValue(CommonAttribute.HARDWARE);
/*  758 */     if (selection != null) {
/*      */ 
/*      */       
/*  761 */       if (!checkHardwareSelection(selection, pdata.getHardwareList(), session.getController().getHardware())) {
/*  762 */         reset(session, CommonAttribute.HARDWARE);
/*  763 */         return getProgrammingData(data);
/*      */       } 
/*  765 */       session.getController().setHardware(selection);
/*  766 */       session.update(this);
/*      */     }
/*  768 */     else if (session.getController().getHardware() != null) {
/*  769 */       reset(session, CommonAttribute.HARDWARE);
/*  770 */       return getProgrammingData(data);
/*      */     } 
/*  772 */     if (pdata.getHardwareList() != null) {
/*  773 */       throw new RequestException(makeHardwareSelectionRequest(pdata));
/*      */     }
/*  775 */     return null;
/*      */   }
/*      */   
/*      */   protected void handleSummary(SPSSession session, AttributeValueMap data, SPSProgrammingData pdata) throws Exception {
/*  779 */     ListValue parts = (ListValue)data.getValue(CommonAttribute.PROGRAMMING_DATA_SELECTION);
/*  780 */     List<SPSModule> modules = pdata.getModules();
/*  781 */     for (int i = 0; i < modules.size(); i++) {
/*  782 */       SPSModule module = modules.get(i);
/*  783 */       Part part = parts.getItems().get(i);
/*  784 */       module.setSelectedPart(part);
/*      */     } 
/*  786 */     if (pdata.getModules().size() == 1) {
/*  787 */       SPSPart part = (SPSPart)((SPSModule)pdata.getModules().get(0)).getSelectedPart();
/*  788 */       if (part != null) {
/*  789 */         pdata.setSSECUSVN(part.getPartNumber());
/*      */       }
/*      */     } 
/*  792 */     throw new RequestException(displaySummaryScreen(session, data));
/*      */   }
/*      */   
/*      */   protected boolean isType4ReprogrammingCompleted(SPSSession session, AttributeValueMap data) {
/*  796 */     if (session.getController() instanceof SPSControllerVCI) {
/*  797 */       SPSControllerVCI controller = (SPSControllerVCI)session.getController();
/*  798 */       if (controller.getDeviceID() == 253 && data.getValue(CommonAttribute.REPROGRAM) != null) {
/*  799 */         return true;
/*      */       }
/*      */     } 
/*  802 */     return false;
/*      */   }
/*      */   
/*      */   protected boolean isReplaceMode(SPSSession session, AttributeValueMap data) throws Exception {
/*  806 */     if (session.getController() instanceof SPSControllerVCI && 
/*  807 */       data.getValue(CommonAttribute.MODE).equals(CommonValue.REPLACE_AND_REPROGRAM)) {
/*  808 */       return isPassThru(data);
/*      */     }
/*      */     
/*  811 */     return false;
/*      */   }
/*      */   
/*      */   protected static void displayReplaceInstructions(SPSSession session, AttributeValueMap data, SPSSchemaAdapterGlobal adapter) throws Exception {
/*  815 */     if (data.getValue(CommonAttribute.REPLACE_INSTRUCTIONS) == null) {
/*  816 */       SPSControllerVCI controller = (SPSControllerVCI)session.getController();
/*  817 */       String toolType = (String)AVUtil.accessValue(data, CommonAttribute.DEVICE);
/*  818 */       String content = toolType + "-" + "replace-instructions";
/*  819 */       if (controller.replaceInstruction == 0) {
/*  820 */         data.set(CommonAttribute.REPLACE_INSTRUCTIONS, CommonValue.OK); return;
/*      */       } 
/*  822 */       if (controller.replaceInstruction > 1) {
/*  823 */         String instructions = SPSModel.getInstance(adapter).getReplaceMessageVCI((SPSLanguage)session.getLanguage(), controller.replaceInstruction);
/*  824 */         content = content + "=" + instructions;
/*      */       } 
/*  826 */       DisplayRequest displayRequest = builder.makeDisplayRequest(CommonAttribute.REPLACE_INSTRUCTIONS, null, content);
/*  827 */       displayRequest.setAutoSubmit(false);
/*  828 */       throw new RequestException(displayRequest);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected ProgrammingData _getProgrammingData(AttributeValueMap data) throws Exception {
/*  833 */     SPSSession session = lookupSession(data);
/*  834 */     if (session.getProgrammingType().getID().equals(SPSProgrammingType.VCI)) {
/*  835 */       if (data.getValue(CommonAttribute.VCI) == null)
/*  836 */         throw new RequestException(makeVCIInputRequest()); 
/*  837 */       if (isType4ReprogrammingCompleted(session, data)) {
/*  838 */         return (ProgrammingData)session.getProgrammingData(this);
/*      */       }
/*  840 */       handleVCI(session, data);
/*      */     }
/*  842 */     else if (session.getController() instanceof SPSControllerVCI) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  850 */       if (isType4ReprogrammingCompleted(session, data)) {
/*  851 */         return (ProgrammingData)session.getProgrammingData(this);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  856 */       SPSControllerVCI controller = (SPSControllerVCI)session.getController();
/*  857 */       controller.setVCI(-1);
/*      */     } 
/*  859 */     Value modeValue = data.getValue(CommonAttribute.EXECUTION_MODE);
/*  860 */     if (modeValue == null || modeValue != CommonValue.EXECUTION_MODE_INFO) {
/*  861 */       if (session.getController() instanceof SPSControllerVCI) {
/*  862 */         if (isReplaceMode(session, data)) {
/*      */           
/*  864 */           SPSControllerVCI controller = (SPSControllerVCI)session.getController();
/*  865 */           if (controller.getControllerData() != null)
/*      */           {
/*      */ 
/*      */             
/*  869 */             if (data.getValue(CommonAttribute.REPLACE_INSTRUCTIONS) == null) {
/*  870 */               controller.setControllerData(null);
/*      */             }
/*      */           }
/*  873 */           if (controller.getReplaceAttributes() != null && session.getController().getDeviceID() != 253) {
/*  874 */             if (data.getValue(CommonAttribute.VIT1) == null)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  880 */               throw new RequestException(makeVIT1Request(session)); } 
/*  881 */             if (controller.getControllerData() == null) {
/*      */               
/*  883 */               VIT1Data vit1 = (VIT1Data)AVUtil.accessValue(data, CommonAttribute.VIT1);
/*  884 */               controller.setControllerData(vit1);
/*  885 */               data.remove(CommonAttribute.VIT1);
/*  886 */               data.set(CommonAttribute.VIT1_TRANSFER, (Value)new ListValueImpl(controller.getReplaceAttributes()));
/*  887 */             } else if (session.getVehicle().getVIT1() == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  893 */               VIT1Data vit1 = (VIT1Data)AVUtil.accessValue(data, CommonAttribute.VIT1);
/*  894 */               vit1.merge((VIT1Data)controller.getControllerData(), controller.getReplaceAttributes());
/*      */             } 
/*      */           } 
/*  897 */           displayReplaceInstructions(session, data, this);
/*      */         } 
/*  899 */         if (session.getController().getDeviceID() != 253) {
/*  900 */           handleVIT1(session, data);
/*  901 */           handleOdometer(session, data);
/*  902 */           handleKeycode(session, data);
/*      */         } 
/*  904 */       } else if (session.getController() instanceof SPSProgrammingSequence) {
/*  905 */         if (data.getValue(CommonAttribute.WARRANTY_CLAIM_CODE) == null) {
/*  906 */           data.set(CommonAttribute.WARRANTY_CLAIM_CODE, (Value)new ValueAdapter(computeWarrantyClaimCode(session, data)));
/*      */         }
/*  908 */         handleVIT1PS(session, data);
/*      */       } 
/*      */     }
/*  911 */     if ((modeValue == null || modeValue != CommonValue.EXECUTION_MODE_INFO) && 
/*  912 */       session.getController() instanceof SPSControllerVCI) {
/*  913 */       ServiceID serviceID = (ServiceID)AVUtil.accessValue(data, AttributeImpl.getInstance("attribute.service.id"));
/*  914 */       String vin = session.getVehicle().getVIT1().getVIT().getAttrValue("vin");
/*  915 */       if (SPSModel.getInstance(this).checkSetting(serviceID, vin, "HWCheck", "enabled")) {
/*  916 */         SPSHardwareIndex hwidx = SPSHardwareIndex.getHardwareIndexVCI(((SPSControllerVCI)session.getController()).getVCI(), this);
/*  917 */         if (!SPSHardwareIndex.checkHardware(hwidx, session.getVehicle().getVIT1())) {
/*  918 */           if (SPSModel.getInstance(this).checkSetting(serviceID, vin, "HWCheck", "enabled")) {
/*  919 */             HardwareSelectionRequest hardwareSelectionRequest = builder.makeHardwareSelectionRequest(CommonAttribute.HARDWARE, hwidx.getDescription(), hwidx.getParts());
/*  920 */             throw new RequestException(hardwareSelectionRequest);
/*      */           } 
/*  922 */           throw new SPSException(CommonException.InvalidHardware);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  928 */     if (data.getValue(CommonAttribute.PROGRAMMING_DATA_SELECTION) == null) {
/*  929 */       reset(session, CommonAttribute.PROGRAMMING_DATA_SELECTION);
/*      */     }
/*  931 */     SPSProgrammingData pdata = (SPSProgrammingData)session.getProgrammingData(this);
/*  932 */     if (pdata == null) {
/*  933 */       if (isProgrammingSequence(session))
/*      */       {
/*      */         
/*  936 */         return getProgrammingDataPS(session, data); } 
/*  937 */       if (isNonProgrammingVCI(session)) {
/*  938 */         if (modeValue == null || modeValue != CommonValue.EXECUTION_MODE_INFO) {
/*  939 */           return (ProgrammingData)new SPSProgrammingData();
/*      */         }
/*      */         
/*  942 */         AssignmentRequest request = displayPreProgrammingInstructions(session, data);
/*  943 */         if (request != null) {
/*  944 */           throw new RequestException(request);
/*      */         }
/*      */       } else {
/*      */         
/*  948 */         throw new SPSException(CommonException.NoValidCOP);
/*      */       } 
/*      */     } 
/*  951 */     boolean isCALID = (modeValue != null && modeValue == CommonValue.EXECUTION_MODE_INFO);
/*  952 */     if (!isCALID && session.getController() instanceof SPSControllerVCI && ((SPSControllerVCI)session.getController()).isSecurityCodeRequired()) {
/*  953 */       if (data.getValue(CommonAttribute.SECURITY_CODE) == null) {
/*  954 */         String vin = session.getVehicle().getVIN().toString();
/*  955 */         String securityCode = SPSSecurityCode.getInstance(this).getSecurityCode(vin);
/*  956 */         if (securityCode == null) {
/*  957 */           throw new RequestException(builder.makeInputRequest(CommonAttribute.SECURITY_CODE, null, null));
/*      */         }
/*  959 */         data.set(CommonAttribute.SECURITY_CODE, (Value)new ValueAdapter(securityCode));
/*      */       } 
/*      */       
/*  962 */       if (data.getValue(CommonAttribute.CONTROLLER_SECURITY_CODE) == null) {
/*  963 */         ValueAdapter valueAdapter; Value value = CommonValue.DISABLE;
/*  964 */         session.getController();
/*  965 */         String vin = session.getVehicle().getVIT1().getVIT().getAttrValue("vin");
/*  966 */         if (vin != null && !vin.equals(session.getVehicle().getVIN().toString())) {
/*  967 */           String securityCodeCtrl = SPSSecurityCode.getInstance(this).getSecurityCode(vin);
/*  968 */           if (securityCodeCtrl == null) {
/*  969 */             throw new RequestException(builder.makeInputRequest(CommonAttribute.CONTROLLER_SECURITY_CODE, null, null));
/*      */           }
/*  971 */           valueAdapter = new ValueAdapter(securityCodeCtrl);
/*      */         } 
/*      */         
/*  974 */         data.set(CommonAttribute.CONTROLLER_SECURITY_CODE, (Value)valueAdapter);
/*      */       } 
/*      */     } else {
/*  977 */       data.set(CommonAttribute.SECURITY_CODE, CommonValue.DISABLE);
/*  978 */       data.set(CommonAttribute.CONTROLLER_SECURITY_CODE, CommonValue.DISABLE);
/*      */     } 
/*  980 */     if (session.getController() instanceof SPSControllerVCI && ((SPSControllerVCI)session.getController()).getReplaceAttributes() != null) {
/*  981 */       pdata.setVIT1TransferAttributes(((SPSControllerVCI)session.getController()).getReplaceAttributes());
/*      */     }
/*  983 */     if (session.getController() instanceof SPSControllerVCI && data.getValue(CommonAttribute.DEALER_VCI) == null) {
/*      */       try {
/*  985 */         int vci = session.getVehicle().getVIN().getSequenceNo() + ((SPSControllerVCI)session.getController()).getVCI();
/*  986 */         data.set(CommonAttribute.DEALER_VCI, (Value)new ValueAdapter(Integer.valueOf(vci)));
/*  987 */       } catch (Exception e) {
/*  988 */         data.set(CommonAttribute.DEALER_VCI, (Value)new ValueAdapter(Integer.valueOf(((SPSControllerVCI)session.getController()).getVCI())));
/*      */       } 
/*      */     }
/*  991 */     if (pdata.getHardwareList() != null) {
/*      */       
/*  993 */       ProgrammingData update = handleHardwareList(session, data, pdata);
/*  994 */       if (update != null) {
/*  995 */         return update;
/*      */       }
/*      */     } 
/*  998 */     if (session.getController() instanceof SPSControllerVCI && session.getController().getDeviceID() == 253) {
/*  999 */       handleType4Executables(session, data, pdata);
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
/* 1013 */     ListValue parts = (ListValue)data.getValue(CommonAttribute.PROGRAMMING_DATA_SELECTION);
/* 1014 */     if (parts == null) {
/* 1015 */       throw new RequestException(builder.makeProgrammingDataSelectionRequest(CommonAttribute.PROGRAMMING_DATA_SELECTION, pdata.getModules()));
/*      */     }
/* 1017 */     if (data.getValue(CommonAttribute.ODOMETER) != null) {
/* 1018 */       String reading = (String)AVUtil.accessValue(data, CommonAttribute.ODOMETER);
/* 1019 */       pdata.setOdometer(reading);
/*      */     } 
/* 1021 */     if (data.getValue(CommonAttribute.KEYCODE) != null) {
/* 1022 */       String keycode = (String)AVUtil.accessValue(data, CommonAttribute.KEYCODE);
/* 1023 */       if (keycode.equals(session.getVehicle().getVIT1().getKeycode())) {
/* 1024 */         keycode = 'V' + keycode;
/*      */       }
/* 1026 */       pdata.setKeycode(keycode);
/*      */     } 
/* 1028 */     if (data.getValue(CommonAttribute.SUMMARY) == null) {
/* 1029 */       handleSummary(session, data, pdata);
/*      */     }
/* 1031 */     if (needsCalibrationFileDownload(session, data) && pdata.getCalibrationFiles() == null) {
/* 1032 */       loadCalibrationFileInformation(data, pdata);
/*      */     }
/* 1034 */     return (ProgrammingData)pdata;
/*      */   }
/*      */ 
/*      */   
/*      */   protected String computeWarrantyClaimCode(SPSSession session, AttributeValueMap data) {
/* 1039 */     String psIDHexStr = null;
/* 1040 */     String wcc = null;
/* 1041 */     String VINtmepStr = null;
/* 1042 */     SPSProgrammingSequence SPSps = (SPSProgrammingSequence)session.getController();
/* 1043 */     int SPSpsID = SPSps.getProgrammingSequenceID();
/*      */     
/* 1045 */     psIDHexStr = getProgrammingSequenceIDHexString(SPSpsID);
/* 1046 */     String VINString = (String)AVUtil.accessValue(data, CommonAttribute.VIN);
/* 1047 */     VINtmepStr = VINString.substring(11, 17);
/* 1048 */     int vinint = Integer.parseInt(VINtmepStr);
/* 1049 */     String tempVIN = Integer.toHexString(vinint);
/*      */ 
/*      */ 
/*      */     
/* 1053 */     if (tempVIN.length() == 1)
/* 1054 */       tempVIN = "0000" + tempVIN; 
/* 1055 */     if (tempVIN.length() == 2)
/* 1056 */       tempVIN = "000" + tempVIN; 
/* 1057 */     if (tempVIN.length() == 3)
/* 1058 */       tempVIN = "00" + tempVIN; 
/* 1059 */     if (tempVIN.length() == 4)
/* 1060 */       tempVIN = "0" + tempVIN; 
/* 1061 */     String finalVIN = tempVIN.substring(3, 5);
/* 1062 */     wcc = psIDHexStr + finalVIN;
/* 1063 */     return wcc;
/*      */   }
/*      */   
/*      */   private String getProgrammingSequenceIDHexString(int SPSpsID) {
/* 1067 */     String psIDHexStr = null;
/* 1068 */     String tempStr = null;
/* 1069 */     if (SPSpsID <= 255) {
/* 1070 */       tempStr = Integer.toHexString(SPSpsID);
/* 1071 */       if (tempStr.length() == 1)
/* 1072 */         tempStr = "0" + tempStr; 
/* 1073 */       psIDHexStr = "S" + tempStr;
/* 1074 */     } else if (SPSpsID > 255 && SPSpsID <= 511) {
/* 1075 */       tempStr = Integer.toHexString(SPSpsID);
/* 1076 */       psIDHexStr = "T" + tempStr.substring(1, 3);
/* 1077 */     } else if (SPSpsID > 512 && SPSpsID >= 767) {
/* 1078 */       tempStr = Integer.toHexString(SPSpsID);
/* 1079 */       psIDHexStr = "U" + tempStr.substring(1, 3);
/* 1080 */     } else if (SPSpsID > 768 && SPSpsID >= 1023) {
/* 1081 */       tempStr = Integer.toHexString(SPSpsID);
/* 1082 */       psIDHexStr = "V" + tempStr.substring(1, 3);
/* 1083 */     } else if (SPSpsID > 1024 && SPSpsID >= 1279) {
/* 1084 */       tempStr = Integer.toHexString(SPSpsID);
/* 1085 */       psIDHexStr = "W" + tempStr.substring(1, 3);
/* 1086 */     } else if (SPSpsID > 1280 && SPSpsID >= 1535) {
/* 1087 */       tempStr = Integer.toHexString(SPSpsID);
/* 1088 */       psIDHexStr = "X" + tempStr.substring(1, 3);
/* 1089 */     } else if (SPSpsID > 1536 && SPSpsID >= 1791) {
/* 1090 */       tempStr = Integer.toHexString(SPSpsID);
/* 1091 */       psIDHexStr = "Y" + tempStr.substring(1, 3);
/* 1092 */     } else if (SPSpsID > 1792 && SPSpsID >= 2047) {
/* 1093 */       tempStr = Integer.toHexString(SPSpsID);
/* 1094 */       psIDHexStr = "Z" + tempStr.substring(1, 3);
/*      */     } 
/*      */     
/* 1097 */     return psIDHexStr;
/*      */   }
/*      */   
/*      */   protected void handleHardwareListPS(SPSSession session, AttributeValueMap data, SPSProgrammingSequence sequence) throws Exception {
/* 1101 */     PairValue selection = (PairValue)data.getValue(CommonAttribute.HARDWARE);
/* 1102 */     if (selection != null) {
/* 1103 */       String controllerID = selection.getID();
/* 1104 */       SPSHardware hardware = (SPSHardware)selection.getValue();
/* 1105 */       if (sequence.acceptHardwareSelection(controllerID, hardware)) {
/* 1106 */         sequence.setHardware(controllerID, hardware);
/*      */       } else {
/* 1108 */         reset(session, CommonAttribute.HARDWARE);
/*      */       } 
/* 1110 */       sequence.update(this);
/*      */     } 
/* 1112 */     if (sequence.getHardwareList() != null) {
/* 1113 */       throw new RequestException(makeHardwareSelectionRequest(sequence.getHardwareList(), sequence));
/*      */     }
/*      */   }
/*      */   
/*      */   public ProgrammingData getProgrammingDataPS(SPSSession session, AttributeValueMap data) throws Exception {
/* 1118 */     SPSProgrammingSequence sequence = (SPSProgrammingSequence)session.getController();
/* 1119 */     PairValue selection = (PairValue)data.getValue(CommonAttribute.HARDWARE);
/* 1120 */     if (data.getValue(CommonAttribute.HARDWARE) != null) {
/* 1121 */       sequence.checkModifiedHardwareSelection(selection.getID(), (SPSHardware)selection.getValue(), this);
/*      */     }
/* 1123 */     if (sequence.getHardwareList() != null)
/*      */     {
/* 1125 */       handleHardwareListPS(session, data, sequence);
/*      */     }
/* 1127 */     ListValue parts = (ListValue)data.getValue(CommonAttribute.PROGRAMMING_DATA_SELECTION);
/* 1128 */     if (parts == null) {
/* 1129 */       throw new RequestException(builder.makeProgrammingDataSelectionRequest(CommonAttribute.PROGRAMMING_DATA_SELECTION, sequence));
/*      */     }
/* 1131 */     if (data.getValue(CommonAttribute.ODOMETER) != null) {
/* 1132 */       String reading = (String)AVUtil.accessValue(data, CommonAttribute.ODOMETER);
/* 1133 */       sequence.setOdometer(reading);
/*      */     } 
/* 1135 */     if (data.getValue(CommonAttribute.KEYCODE) != null) {
/* 1136 */       String keycode = (String)AVUtil.accessValue(data, CommonAttribute.KEYCODE);
/* 1137 */       if (keycode.equals(session.getVehicle().getVIT1().getKeycode())) {
/* 1138 */         keycode = 'V' + keycode;
/*      */       }
/* 1140 */       sequence.setKeycode(keycode);
/*      */     } 
/* 1142 */     if (data.getValue(CommonAttribute.SUMMARY) == null) {
/* 1143 */       sequence.evaluateProgrammingDataSelection(data);
/* 1144 */       throw new RequestException(builder.makeDisplaySummaryRequest(CommonAttribute.SUMMARY));
/*      */     } 
/* 1146 */     sequence.loadCalibrationFileInformation(data, this);
/* 1147 */     return null;
/*      */   }
/*      */   
/*      */   protected boolean needsCalibrationFileDownload(SPSSession session, AttributeValueMap data) {
/* 1151 */     Value modeValue = data.getValue(CommonAttribute.EXECUTION_MODE);
/* 1152 */     if (modeValue == null || modeValue != CommonValue.EXECUTION_MODE_INFO) {
/* 1153 */       if (isProgrammingSequence(session)) {
/* 1154 */         if (CommonValue.FAIL.equals(data.getValue(CommonAttribute.SAME_CALIBRATIONS)))
/* 1155 */           return false; 
/*      */       } else {
/* 1157 */         if (data.getValue(CommonAttribute.ARCHIVE) == null) {
/* 1158 */           return !isNonProgrammingVCI(session);
/*      */         }
/* 1160 */         String vci = (String)AVUtil.accessValue(data, CommonAttribute.VCI);
/* 1161 */         long input = -1L;
/*      */         try {
/* 1163 */           input = Long.parseLong(vci);
/* 1164 */         } catch (Exception e) {}
/*      */         
/* 1166 */         return isProgrammingByFileVCI(input);
/*      */       } 
/*      */     }
/* 1169 */     return false;
/*      */   }
/*      */   
/*      */   protected void loadCalibrationFileInformation(AttributeValueMap data, SPSProgrammingData pdata) throws Exception {
/* 1173 */     List<SPSModule> modules = pdata.getModules();
/* 1174 */     if (modules.size() == 1) {
/* 1175 */       SPSModule module = modules.get(0);
/* 1176 */       Part part = module.getSelectedPart();
/* 1177 */       if (!SPSPart.isEndItemPart(part.getPartNumber(), this)) {
/* 1178 */         throw new SPSException(CommonException.NoValidEndItemPart);
/*      */       }
/* 1180 */       pdata.setCalibrationFiles(SPSModule.getCalibrationFileInfo(part.getID(), this));
/*      */     } else {
/* 1182 */       List<Part> parts = new ArrayList();
/* 1183 */       for (int i = 0; i < modules.size(); i++) {
/* 1184 */         SPSModule module = modules.get(i);
/* 1185 */         parts.add(module.getSelectedPart());
/*      */       } 
/* 1187 */       List<SPSBlobImpl> calibrations = SPSModule.getCalibrationFileInfo(parts, this, requiresCustomCalibration(data));
/* 1188 */       pdata.setCalibrationFiles(calibrations);
/* 1189 */       for (int j = 0; j < calibrations.size(); j++) {
/* 1190 */         SPSBlobImpl calibration = calibrations.get(j);
/* 1191 */         String partNo = calibration.getBlobName();
/* 1192 */         SPSModule module = findModule(modules, partNo);
/* 1193 */         calibration.setBlobID(Integer.valueOf(module.getID()));
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected SPSModule findModule(List<SPSModule> modules, String partNo) {
/* 1199 */     for (int i = 0; i < modules.size(); i++) {
/* 1200 */       SPSModule module = modules.get(i);
/* 1201 */       Part part = module.getSelectedPart();
/* 1202 */       if (partNo.equals(part.getPartNumber())) {
/* 1203 */         return module;
/*      */       }
/*      */     } 
/* 1206 */     return null;
/*      */   }
/*      */   
/*      */   protected boolean checkHardwareSelection(SPSHardware selection, List<SPSHardwareIndex> required, List<SPSHardware> hardware) {
/* 1210 */     if (hardware != null) {
/* 1211 */       for (int j = 0; j < hardware.size(); j++) {
/* 1212 */         SPSHardware hw = hardware.get(j);
/* 1213 */         if (hw.equals(selection)) {
/* 1214 */           return true;
/*      */         }
/*      */       } 
/*      */     }
/* 1218 */     for (int i = 0; i < required.size(); i++) {
/*      */       
/* 1220 */       SPSHardwareIndex hwidx = required.get(i);
/* 1221 */       if (hwidx.contains(selection)) {
/* 1222 */         return true;
/*      */       }
/*      */     } 
/* 1225 */     return true;
/*      */   }
/*      */   
/*      */   protected AssignmentRequest makeHardwareSelectionRequest(SPSProgrammingData pdata) throws Exception {
/* 1229 */     return makeHardwareSelectionRequest(pdata.getHardwareList(), (SPSProgrammingSequence)null);
/*      */   }
/*      */ 
/*      */   
/*      */   protected AssignmentRequest makeHardwareSelectionRequest(List<SPSHardwareIndex> hardware, SPSProgrammingSequence sequence) throws Exception {
/* 1234 */     String description = ((SPSHardwareIndex)hardware.get(0)).getDescription();
/* 1235 */     List<SPSHardware> parts = new ArrayList();
/* 1236 */     for (int i = 0; i < hardware.size(); i++) {
/* 1237 */       SPSHardwareIndex hw = hardware.get(i);
/* 1238 */       if (hw.getParts() != null) {
/* 1239 */         for (int j = 0; j < hw.getParts().size(); j++) {
/* 1240 */           SPSHardware part = hw.getParts().get(j);
/* 1241 */           if (!parts.contains(part)) {
/* 1242 */             parts.add(part);
/*      */           }
/*      */         } 
/*      */       }
/*      */     } 
/* 1247 */     if (sequence != null) {
/* 1248 */       parts = sequence.filterConstraints(parts, this);
/* 1249 */       Collections.sort(parts);
/* 1250 */       String id = sequence.getControllerID4HWSelection();
/* 1251 */       return (AssignmentRequest)builder.makeHardwareSelectionRequest(CommonAttribute.HARDWARE, description, parts, id);
/*      */     } 
/* 1253 */     Collections.sort(parts);
/* 1254 */     return (AssignmentRequest)builder.makeHardwareSelectionRequest(CommonAttribute.HARDWARE, description, parts);
/*      */   }
/*      */ 
/*      */   
/*      */   protected AssignmentRequest displayPreProgrammingInstructions(SPSSession session, AttributeValueMap data) throws Exception {
/* 1259 */     SPSController controller = session.getController();
/* 1260 */     if (controller.getPreProgrammingInstructions() != null) {
/* 1261 */       if (isProgrammingSequence(session) && 
/* 1262 */         CommonValue.FAIL.equals(data.getValue(CommonAttribute.SAME_CALIBRATIONS))) {
/* 1263 */         return null;
/*      */       }
/*      */       
/* 1266 */       List instructions = getModel().getProgrammingMessageVCI((SPSLanguage)session.getLanguage(), controller.getPreProgrammingInstructions());
/* 1267 */       if (instructions != null) {
/* 1268 */         InstructionDisplayRequest instructionDisplayRequest = builder.makeInstructionDisplayRequest(CommonAttribute.PRE_PROGRAMMING_INSTRUCTIONS, "pre-prog-instructions", instructions);
/* 1269 */         instructionDisplayRequest.setAutoSubmit(false);
/* 1270 */         throw new RequestException(instructionDisplayRequest);
/*      */       } 
/* 1272 */       data.set(CommonAttribute.PRE_PROGRAMMING_INSTRUCTIONS, CommonValue.FAIL);
/*      */     } 
/*      */     
/* 1275 */     return null;
/*      */   }
/*      */   
/*      */   protected static AssignmentRequest displaySummaryScreen(SPSSession session, AttributeValueMap data) {
/* 1279 */     return (AssignmentRequest)builder.makeDisplaySummaryRequest(CommonAttribute.SUMMARY);
/*      */   }
/*      */   
/*      */   protected boolean isNonProgrammingVCI(SPSSession session) {
/* 1283 */     if (session.getController() instanceof SPSControllerVCI) {
/* 1284 */       SPSControllerVCI controller = (SPSControllerVCI)session.getController();
/* 1285 */       return (controller.getVCI() == 10);
/*      */     } 
/* 1287 */     return false;
/*      */   }
/*      */   
/*      */   protected static boolean isProgrammingSequence(SPSSession session) {
/* 1291 */     return session.getController() instanceof SPSProgrammingSequence;
/*      */   }
/*      */   
/*      */   protected boolean checkProgrammingData(AttributeValueMap data, SPSSession session) throws Exception {
/* 1295 */     if (data.getValue(CommonAttribute.SAME_CALIBRATIONS) != null)
/* 1296 */       return true; 
/* 1297 */     if (isProgrammingSequence(session)) {
/* 1298 */       SPSProgrammingSequence sequence = (SPSProgrammingSequence)session.getController();
/* 1299 */       List<SPSProgrammingData> pdatas = sequence.getProgrammingDataList();
/* 1300 */       boolean sameCalibration = true;
/* 1301 */       for (int i = 0; i < pdatas.size(); i++) {
/* 1302 */         SPSProgrammingData sPSProgrammingData = pdatas.get(i);
/* 1303 */         if (sPSProgrammingData.getDeviceID().intValue() != 253) {
/* 1304 */           if (!checkModules(data, session, sPSProgrammingData)) {
/* 1305 */             return false;
/*      */           }
/* 1307 */           sameCalibration = (sameCalibration && sPSProgrammingData.skipSameCalibration());
/*      */         } else {
/* 1309 */           sameCalibration = false;
/*      */         } 
/*      */       } 
/* 1312 */       if (sameCalibration)
/*      */       {
/* 1314 */         data.set(CommonAttribute.SAME_CALIBRATIONS, CommonValue.FAIL);
/*      */       }
/* 1316 */       return true;
/*      */     } 
/* 1318 */     SPSProgrammingData pdata = (SPSProgrammingData)session.getProgrammingData(this);
/* 1319 */     return checkModules(data, session, pdata);
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean checkModules(AttributeValueMap data, SPSSession session, SPSProgrammingData pdata) throws Exception {
/* 1324 */     if (!pdata.isValid()) {
/* 1325 */       return false;
/*      */     }
/* 1327 */     List<SPSModule> modules = pdata.getModules();
/*      */     
/* 1329 */     boolean valid = false;
/* 1330 */     boolean suspect = false;
/* 1331 */     for (int i = 0; i < modules.size(); i++) {
/* 1332 */       SPSModule module = modules.get(i);
/* 1333 */       if (modules.size() == 1 || (modules.size() > 1 && !module.getID().equals("0"))) {
/* 1334 */         if (module.isCalibrationUpdate()) {
/* 1335 */           valid = true;
/*      */         }
/* 1337 */         if (module.getCurrentCalibration() != null && module.getCurrentCalibration().startsWith("*")) {
/* 1338 */           suspect = true;
/*      */         }
/*      */       } 
/* 1341 */       if (module.isMismatchCVN());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1347 */     if (!suspect || data.getValue(CommonAttribute.MODE).equals(CommonValue.REPLACE_AND_REPROGRAM) || 
/* 1348 */       session.getProgrammingType().getID().equals(SPSProgrammingType.VCI)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1355 */       if (valid) {
/* 1356 */         return true;
/*      */       }
/* 1358 */       if (session.getController() instanceof SPSProgrammingSequence) {
/* 1359 */         pdata.indicateSameCalibration();
/*      */         
/* 1361 */         return true;
/*      */       } 
/*      */       
/* 1364 */       throw new SPSException(CommonException.ConfirmSameCalibration);
/*      */     } 
/*      */     pdata.invalidate();
/*      */     throw new SPSException(CommonException.InvalidCurrentCalibration);
/*      */   }
/*      */   protected Boolean _reprogram(ProgrammingData pdata, AttributeValueMap data) throws Exception {
/* 1370 */     SPSSession session = lookupSession(data);
/* 1371 */     if (data.getValue(CommonAttribute.REPROGRAM) == null) {
/* 1372 */       if (!isNonProgrammingVCI(session) && 
/* 1373 */         !checkProgrammingData(data, session)) {
/* 1374 */         return Boolean.TRUE;
/*      */       }
/*      */       
/* 1377 */       if (data.getValue(CommonAttribute.PRE_PROGRAMMING_INSTRUCTIONS) == null) {
/* 1378 */         AssignmentRequest request = displayPreProgrammingInstructions(session, data);
/* 1379 */         if (request != null) {
/* 1380 */           throw new RequestException(request);
/*      */         }
/*      */       } 
/* 1383 */       if (isNonProgrammingVCI(session)) {
/* 1384 */         return Boolean.TRUE;
/*      */       }
/* 1386 */       if (data.getValue(CommonAttribute.CONFIRM_PROGRAMMING_DATA_DOWNLOAD_START) == null && 
/* 1387 */         needsCalibrationFileDownload(session, data) && !isNonProgrammingVCI(session)) {
/* 1388 */         List blobs = null;
/* 1389 */         if (session.getController() instanceof SPSProgrammingSequence) {
/* 1390 */           blobs = ((SPSProgrammingSequence)session.getController()).getCalibrationFiles();
/*      */         } else {
/* 1392 */           blobs = pdata.getCalibrationFiles();
/*      */         } 
/* 1394 */         throw new RequestException(builder.makeProgrammingDataDownloadRequest(CommonAttribute.CONFIRM_PROGRAMMING_DATA_DOWNLOAD_START, blobs));
/*      */       } 
/*      */       
/* 1397 */       if (data.getValue(CommonAttribute.CONFIRM_REPROGRAM_DISPLAY) == null) {
/* 1398 */         boolean skip = false;
/* 1399 */         if (isProgrammingSequence(session) && 
/* 1400 */           CommonValue.FAIL.equals(data.getValue(CommonAttribute.SAME_CALIBRATIONS))) {
/* 1401 */           skip = true;
/*      */         }
/*      */         
/* 1404 */         if (!skip) {
/* 1405 */           throw new RequestException(builder.makeReprogramDisplayRequest());
/*      */         }
/*      */       } 
/* 1408 */       if (data.getValue(CommonAttribute.CONFIRM_PROGRAMMING_DATA_DOWNLOAD_FINISHED) == null && 
/* 1409 */         needsCalibrationFileDownload(session, data)) {
/* 1410 */         throw new RequestException(builder.makeProgrammingDataDownloadContinuationRequest());
/*      */       }
/*      */       
/* 1413 */       if (session.getController() instanceof SPSProgrammingSequence) {
/* 1414 */         SPSProgrammingSequence sequence = (SPSProgrammingSequence)session.getController();
/* 1415 */         throw new RequestException(builder.makeReprogramRequest(CommonAttribute.REPROGRAM, sequence.getSequence(), sequence.getProgrammingDataList(), sequence.getType4Data(), sequence.getInstructions(getModel()), sequence.getFailureHandling()));
/*      */       } 
/* 1417 */       throw new RequestException(builder.makeReprogramRequest(CommonAttribute.REPROGRAM, pdata));
/*      */     } 
/*      */     
/* 1420 */     if (data.getValue(CommonAttribute.REPROGRAM_PROTOCOL) == null) {
/* 1421 */       SPSEvents.logReprogramEvent(data, session, this);
/* 1422 */       data.set(CommonAttribute.REPROGRAM_PROTOCOL, CommonValue.OK);
/* 1423 */       Value success = data.getValue(CommonAttribute.REPROGRAM);
/* 1424 */       if (!success.equals(CommonValue.OK)) {
/* 1425 */         return Boolean.TRUE;
/*      */       }
/*      */     } 
/* 1428 */     if (data.getValue(CommonAttribute.FINAL_INSTRUCTIONS) == null) {
/* 1429 */       String toolType = (String)AVUtil.accessValue(data, CommonAttribute.DEVICE);
/* 1430 */       String content = toolType + "-" + "final-instructions";
/* 1431 */       List instructions = null;
/* 1432 */       SPSController controller = session.getController();
/* 1433 */       if (controller.getPostProgrammingInstructions() != null) {
/* 1434 */         if (isProgrammingSequence(session) && 
/* 1435 */           CommonValue.FAIL.equals(data.getValue(CommonAttribute.SAME_CALIBRATIONS))) {
/* 1436 */           return Boolean.TRUE;
/*      */         }
/*      */         
/* 1439 */         instructions = getModel().getProgrammingMessageVCI((SPSLanguage)session.getLanguage(), controller.getPostProgrammingInstructions());
/*      */       } 
/* 1441 */       InstructionDisplayRequest instructionDisplayRequest = builder.makeInstructionDisplayRequest(CommonAttribute.FINAL_INSTRUCTIONS, content, instructions);
/* 1442 */       throw new RequestException(instructionDisplayRequest);
/*      */     } 
/* 1444 */     return Boolean.TRUE;
/*      */   }
/*      */   
/*      */   protected byte[] _getData(ProgrammingDataUnit blob, AttributeValueMap data) throws RequestException, Exception {
/* 1448 */     SPSSession session = lookupSession(data);
/* 1449 */     if (session != null && 
/* 1450 */       data.getValue(CommonAttribute.ARCHIVE) != null) {
/* 1451 */       SPSArchive archive = (SPSArchive)((SPSControllerVCI)session.getController()).getArchive();
/* 1452 */       if (archive != null) {
/* 1453 */         List<ProgrammingDataUnit> units = archive.getCalibrationUnits();
/* 1454 */         List<byte[]> calibrations = archive.getCalibrationFiles();
/* 1455 */         for (int i = 0; i < calibrations.size(); i++) {
/* 1456 */           ProgrammingDataUnit unit = units.get(i);
/* 1457 */           if (unit.getBlobName().equals(blob.getBlobName())) {
/* 1458 */             return calibrations.get(i);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1464 */     Integer calibration = Integer.valueOf(blob.getBlobName());
/* 1465 */     byte[] bytes = SPSModule.getCalibrationFile(calibration.intValue(), this, requiresCustomCalibration(data));
/* 1466 */     if (bytes != null) {
/* 1467 */       return bytes;
/*      */     }
/* 1469 */     throw new SPSException(CommonException.MissingCalibrationFile);
/*      */   }
/*      */   
/*      */   public String getBulletin(String locale, String bulletin) {
/*      */     try {
/* 1474 */       SPSLanguage language = SPSLanguage.getLanguage(SPSLanguage.lookupLocale(locale), this);
/* 1475 */       if (language == null) {
/* 1476 */         return null;
/*      */       }
/* 1478 */       return SPSBulletin.loadBulletin(language, bulletin, this);
/* 1479 */     } catch (Exception e) {
/*      */       
/* 1481 */       return null;
/*      */     } 
/*      */   }
/*      */   public String getHTML(String locale, String id) {
/*      */     try {
/* 1486 */       SPSLanguage language = SPSLanguage.getLanguage(SPSLanguage.lookupLocale(locale), this);
/* 1487 */       if (language == null) {
/* 1488 */         return null;
/*      */       }
/* 1490 */       return SPSInstructions.loadHTML(language, id, this);
/* 1491 */     } catch (Exception e) {
/*      */       
/* 1493 */       return null;
/*      */     } 
/*      */   }
/*      */   public byte[] getImage(String id) {
/*      */     try {
/* 1498 */       return SPSInstructions.loadImage(id, this);
/* 1499 */     } catch (Exception e) {
/*      */       
/* 1501 */       return null;
/*      */     } 
/*      */   }
/*      */   public Object getVersionInfo() {
/*      */     try {
/* 1506 */       if (ApplicationContext.getInstance().isStandalone()) {
/* 1507 */         List<DBVersionInformation> versionInfo = new ArrayList(1);
/* 1508 */         String version = this.configuration.getProperty("db.version");
/* 1509 */         String description = this.configuration.getProperty("db.description");
/* 1510 */         versionInfo.add(new DBVersionInformation(null, "", new Date(), description, version));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1520 */         return versionInfo;
/*      */       } 
/* 1522 */       return getModel().getVersionInfo();
/*      */     }
/* 1524 */     catch (Exception e) {
/* 1525 */       log.warn("unable to retrieve version information, returning null - exception:" + e, e);
/* 1526 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void initQuickUpdate(Configuration configuration) {
/*      */     try {
/* 1532 */       if (configuration.getProperty("tcsc.db.drv") != null || configuration.getProperty("tcsc.db.data-source") != null) {
/* 1533 */         DatabaseLink databaseLink = DatabaseLink.openDatabase(configuration, "tcsc.db");
/* 1534 */         SPSControllerVCI.initQuickUpdate(this, (IDatabaseLink)databaseLink);
/*      */       } else {
/* 1536 */         log.info("TCSC Quick Update not configured");
/*      */       } 
/* 1538 */     } catch (Exception ex) {
/* 1539 */       log.debug("failed to initialize TCSC Quick Update", ex);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void initCustomCalibrations(Configuration configuration) {
/*      */     try {
/* 1545 */       if (configuration.getProperty("customcal.db.drv") != null || configuration.getProperty("customcal.db.data-source") != null) {
/* 1546 */         DatabaseLink databaseLink = DatabaseLink.openDatabase(configuration, "customcal.db");
/* 1547 */         SPSModule.initCustomCalibrations(this, (IDatabaseLink)databaseLink);
/* 1548 */         String controllers = configuration.getProperty("OnStarCustomCalibration");
/* 1549 */         if (controllers != null) {
/* 1550 */           SPSControllerVCI.initCustomCalibrationControllers(this, controllers);
/*      */         } else {
/* 1552 */           log.info("No controllers with custom calibrations configured");
/*      */         } 
/*      */       } else {
/* 1555 */         log.info("Custom Calibrations not configured");
/*      */       } 
/* 1557 */     } catch (Exception ex) {
/* 1558 */       log.debug("failed to initialize Custom Calibrations Database Connection", ex);
/*      */     } 
/*      */   }
/*      */   
/*      */   public DatabaseInfo getDatabaseInfo(AttributeValueMap avMap, Attribute lastRequestedAttribute) throws Exception {
/* 1563 */     SPSSession session = lookupSession(avMap);
/* 1564 */     if (session != null) {
/* 1565 */       SPSDatabaseInfo dbinfo = new SPSDatabaseInfo(session, avMap);
/*      */       try {
/* 1567 */         dbinfo.handle(lastRequestedAttribute, this);
/* 1568 */       } catch (Exception x) {}
/*      */       
/* 1570 */       return dbinfo;
/*      */     } 
/* 1572 */     return null;
/*      */   }
/*      */   
/*      */   protected void _reset() {}
/*      */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\global\SPSSchemaAdapterGlobal.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */