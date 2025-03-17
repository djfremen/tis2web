/*      */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*      */ 
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
/*      */ import com.eoos.gm.tis2web.sps.common.FunctionSelectionRequest;
/*      */ import com.eoos.gm.tis2web.sps.common.RequestBuilder;
/*      */ import com.eoos.gm.tis2web.sps.common.TypeSelectionRequest;
/*      */ import com.eoos.gm.tis2web.sps.common.VIT1Data;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.SPSException;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Archive;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Controller;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingData;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingDataUnit;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Summary;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.lt.SPSLaborTimeConfiguration;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.AssignmentRequest;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.InstructionDisplayRequest;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.AVUtil;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonException;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonValue;
/*      */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ValueAdapter;
/*      */ import com.eoos.gm.tis2web.sps.common.impl.RequestBuilderImpl;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSArchive;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSController;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSLaborTime;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSProgrammingFile;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSProgrammingType;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSSchemaAdapter;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.dbinfo.DatabaseInfo;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.log.SPSEventLog;
/*      */ import com.eoos.gm.tis2web.sps.server.implementation.system.serverside.adapter.ServiceResolution;
/*      */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*      */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*      */ import com.eoos.gm.tis2web.sps.service.cai.Request;
/*      */ import com.eoos.gm.tis2web.sps.service.cai.RequestException;
/*      */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*      */ import com.eoos.gm.tis2web.vc.v2.provider.CfgProviderRetrieval;
/*      */ import com.eoos.gm.tis2web.vc.v2.vin.VINResolverRetrieval;
/*      */ import com.eoos.jdbc.ConnectionProvider;
/*      */ import com.eoos.propcfg.Configuration;
/*      */ import java.io.File;
/*      */ import java.sql.Connection;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import org.apache.log4j.Logger;
/*      */ 
/*      */ public class SPSSchemaAdapterGME
/*      */   extends SPSSchemaAdapter
/*      */   implements Configurable, CfgProviderRetrieval, VINResolverRetrieval {
/*   70 */   private static Logger log = Logger.getLogger(SPSSchemaAdapterGME.class);
/*      */   
/*   72 */   public static RequestBuilder builder = (RequestBuilder)new RequestBuilderImpl();
/*      */   
/*      */   public static final int VCI_ARCHIVE_A = 1001;
/*      */   
/*      */   public static final int VCI_ARCHIVE_B = 1002;
/*      */   
/*      */   public static final String ARCHIVE_LOCATION = "vci-archives";
/*      */   
/*   80 */   private final Object SYNC_DBLINK = new Object();
/*      */   
/*   82 */   private IDatabaseLink databaseLink = null;
/*      */   
/*      */   private ConnectionProvider connectionProvider;
/*      */   
/*      */   private boolean supportsSPSFunctions;
/*      */   
/*      */   public SPSSchemaAdapterGME(Configuration configuration) throws Exception {
/*   89 */     super(configuration);
/*   90 */     if ("true".equalsIgnoreCase(configuration.getProperty("sql-logging"))) {
/*   91 */       DBMS.enableLogging();
/*      */     }
/*   93 */     if ("true".equalsIgnoreCase(configuration.getProperty("spsfunctions"))) {
/*   94 */       this.supportsSPSFunctions = true;
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean supportsSPSFunctions() {
/*   99 */     return this.supportsSPSFunctions;
/*      */   }
/*      */   
/*      */   public void init() throws Exception {
/*  103 */     getModel().init();
/*      */   }
/*      */   
/*      */   public IDatabaseLink getDatabaseLink() {
/*  107 */     synchronized (this.SYNC_DBLINK) {
/*  108 */       if (this.databaseLink == null) {
/*  109 */         log.info("initializing database link");
/*      */         try {
/*  111 */           final DatabaseLink backend = DatabaseLink.openDatabase(this.configuration, "db");
/*  112 */           this.connectionProvider = ConNvent.create(new ConnectionProvider()
/*      */               {
/*      */                 public void releaseConnection(Connection connection) {
/*  115 */                   backend.releaseConnection(connection);
/*      */                 }
/*      */                 
/*      */                 public Connection getConnection() {
/*      */                   try {
/*  120 */                     return backend.requestConnection();
/*  121 */                   } catch (Exception e) {
/*  122 */                     throw new RuntimeException(e);
/*      */                   } 
/*      */                 }
/*      */               },  30000L);
/*      */           
/*  127 */           this.databaseLink = (IDatabaseLink)new DatabaseLinkWrapper(backend)
/*      */             {
/*      */               public void releaseConnection(Connection connection) {
/*  130 */                 SPSSchemaAdapterGME.this.connectionProvider.releaseConnection(connection);
/*      */               }
/*      */               
/*      */               public Connection requestConnection() throws Exception {
/*  134 */                 return SPSSchemaAdapterGME.this.connectionProvider.getConnection();
/*      */               }
/*      */             };
/*      */         }
/*  138 */         catch (Exception e) {
/*  139 */           log.warn("... failed to initialize database link - exception:" + e + ", using fallback-db ");
/*      */           try {
/*  141 */             this.databaseLink = (IDatabaseLink)DatabaseLink.openDatabase(this.configuration, "fallback-db");
/*  142 */           } catch (Exception ex) {
/*  143 */             log.error("... unable to initialize fallback database link - exception: " + ex);
/*  144 */             throw new RuntimeException(e);
/*      */           } 
/*      */         } 
/*      */       } 
/*  148 */       return this.databaseLink;
/*      */     } 
/*      */   }
/*      */   
/*      */   private SPSModel getModel() {
/*  153 */     return SPSModel.getInstance(this);
/*      */   }
/*      */   
/*      */   public String getCalibrationVerificationNumber(String sessionID, String partNumber) {
/*  157 */     return getModel().getCalibrationVerificationNumber(sessionID, partNumber);
/*      */   }
/*      */   
/*      */   protected boolean assertVehicleState(SPSSession session, AttributeValueMap data, List<SPSOption> options) {
/*  161 */     SPSVehicle vehicle = (SPSVehicle)session.getVehicle();
/*  162 */     List<SPSOption> selections = vehicle.getOptions();
/*  163 */     List<SPSOption> obsolete = new ArrayList();
/*  164 */     List<SPSOption> modifications = new ArrayList();
/*  165 */     List<Value> additions = new ArrayList();
/*  166 */     if (selections != null) {
/*      */       
/*  168 */       boolean modification = false;
/*  169 */       for (int i = 0; i < selections.size(); i++) {
/*  170 */         SPSOption option = selections.get(i);
/*  171 */         SPSOption group = (SPSOption)option.getType();
/*  172 */         if (group != null) {
/*      */ 
/*      */           
/*  175 */           Value value = data.getValue((Attribute)group);
/*  176 */           if (value == null) {
/*  177 */             Object confirmation = data.getValue(CommonAttribute.CONFIRMED_OPTIONS);
/*  178 */             if (confirmation == null)
/*      */             {
/*      */ 
/*      */               
/*  182 */               obsolete.add(option); } 
/*  183 */           } else if (modification) {
/*  184 */             obsolete.add(option);
/*  185 */           } else if (!option.equals(value)) {
/*  186 */             modifications.add(option);
/*  187 */             additions.add(value);
/*  188 */             modification = true;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  193 */     if (options != null) {
/*  194 */       for (int i = 0; i < options.size(); i++) {
/*  195 */         SPSOption group = options.get(i);
/*  196 */         Value value = data.getValue((Attribute)group);
/*  197 */         if (value != null && !isSelectedVehicleOption(vehicle, value)) {
/*  198 */           additions.add(value);
/*      */         }
/*      */       } 
/*      */     }
/*  202 */     if (obsolete.size() > 0) {
/*  203 */       selections.removeAll(obsolete);
/*      */     }
/*  205 */     if (modifications.size() > 0) {
/*  206 */       selections.removeAll(modifications);
/*      */     }
/*  208 */     if (additions.size() > 0) {
/*  209 */       for (int i = 0; i < additions.size(); i++) {
/*  210 */         SPSOption option = (SPSOption)additions.get(i);
/*  211 */         vehicle.setOption(option);
/*      */       } 
/*      */     }
/*  214 */     return (obsolete.size() == 0 && modifications.size() == 0);
/*      */   }
/*      */   
/*      */   boolean isSelectedVehicleOption(SPSVehicle vehicle, Value value) {
/*  218 */     List<SPSOption> selections = vehicle.getOptions();
/*  219 */     if (selections != null) {
/*  220 */       for (int i = 0; i < selections.size(); i++) {
/*  221 */         SPSOption option = selections.get(i);
/*  222 */         if (option.equals(value)) {
/*  223 */           return true;
/*      */         }
/*      */       } 
/*      */     }
/*  227 */     return false;
/*      */   }
/*      */   
/*      */   protected void checkVehicleState(SPSSession session, AttributeValueMap data) throws Exception {
/*  231 */     if (session.getVehicle() != null && data.getValue(CommonAttribute.HARDWARE_NUMBER) != null) {
/*      */       
/*  233 */       SPSVehicle vehicle = (SPSVehicle)session.getVehicle();
/*  234 */       String hardware = (String)((ValueAdapter)data.getValue(CommonAttribute.HARDWARE_NUMBER)).getAdaptee();
/*  235 */       if (vehicle.getHardware() != null && 
/*  236 */         !vehicle.getHardware().equals(hardware)) {
/*  237 */         reset(session, CommonAttribute.CONTROLLER);
/*      */       }
/*      */       
/*  240 */       vehicle.setHardware(hardware);
/*      */     } 
/*  242 */     if (!assertVIN(session, data)) {
/*  243 */       reset(session);
/*      */     }
/*  245 */     SPSControllerReference reference = (SPSControllerReference)session.getControllerReference();
/*  246 */     if (reference != null) {
/*  247 */       if (!assertVehicleState(session, data, reference.getOptions()))
/*      */       {
/*      */ 
/*      */ 
/*      */         
/*  252 */         reset(session, CommonAttribute.CONTROLLER);
/*      */       }
/*  254 */       if (data.getValue(CommonAttribute.HARDWARE) != null) {
/*  255 */         reference.setHardware((SPSHardware)data.getValue(CommonAttribute.HARDWARE));
/*      */       }
/*      */     } 
/*  258 */     SPSControllerList controllers = (SPSControllerList)session.getControllers();
/*  259 */     if (controllers != null && 
/*  260 */       !assertVehicleState(session, data, controllers.getOptions()))
/*      */     {
/*      */       
/*  263 */       reset(session);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void clearVIT1DerivedOptions(SPSVehicle vehicle) {
/*  269 */     if (vehicle.getOptions() != null && vehicle.getOptionsVIT1() != null) {
/*  270 */       vehicle.getOptions().removeAll(vehicle.getOptionsVIT1());
/*  271 */       List<SPSOption> optionsVIT1 = vehicle.getOptionsVIT1();
/*  272 */       Iterator<SPSOption> it = vehicle.getOptions().iterator();
/*  273 */       while (it.hasNext()) {
/*  274 */         SPSOption option = it.next();
/*  275 */         for (int i = 0; i < optionsVIT1.size(); i++) {
/*  276 */           SPSOption optionVIT1 = optionsVIT1.get(i);
/*  277 */           if (optionVIT1.getType().equals(option.getType())) {
/*  278 */             it.remove();
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*  283 */       vehicle.getOptionsVIT1().clear();
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void checkControllerSelection(SPSSession session, AttributeValueMap data) throws Exception {
/*  288 */     SPSControllerReference reference = (SPSControllerReference)session.getControllerReference();
/*  289 */     if (reference != null) {
/*  290 */       SPSControllerReference selection = (SPSControllerReference)data.getValue(CommonAttribute.CONTROLLER);
/*  291 */       if (!reference.equals(selection)) {
/*  292 */         if (data.getValue(CommonAttribute.VIT1) != null) {
/*  293 */           data.remove(CommonAttribute.VIT1);
/*      */         }
/*  295 */         reset(session, CommonAttribute.CONTROLLER);
/*      */       }
/*  297 */       else if (reference != null && supportsSPSFunctions() && 
/*  298 */         isPassThru(data)) {
/*  299 */         Value value = data.getValue(CommonAttribute.SEQUENCE);
/*  300 */         if (value != null) {
/*  301 */           String sequence = (String)((ValueAdapter)value).getAdaptee();
/*  302 */           if (!sequence.equals(selection.getSequence())) {
/*  303 */             reset(session, CommonAttribute.CONTROLLER);
/*  304 */             clearVIT1DerivedOptions((SPSVehicle)session.getVehicle());
/*      */           } 
/*  306 */         } else if (selection.getSequence() != null) {
/*  307 */           reset(session, CommonAttribute.CONTROLLER);
/*  308 */           clearVIT1DerivedOptions((SPSVehicle)session.getVehicle());
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  314 */     SPSProgrammingType method = session.getProgrammingType();
/*  315 */     if (method != null) {
/*  316 */       SPSProgrammingType selection = (SPSProgrammingType)data.getValue(CommonAttribute.CONTROLLER_METHOD);
/*  317 */       if (!method.equals(selection)) {
/*      */         try {
/*  319 */           if (data.getValue(CommonAttribute.VIT1) != null) {
/*  320 */             data.remove(CommonAttribute.VIT1);
/*      */           }
/*  322 */         } catch (Exception x) {}
/*      */         
/*  324 */         reset(session, CommonAttribute.CONTROLLER);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void checkHardwareRegistry(AttributeValueMap data) throws Exception {
/*  330 */     if (!SPSHardwareKey.getInstance(this).isActivated()) {
/*      */       return;
/*      */     }
/*  333 */     Value modeValue = data.getValue(CommonAttribute.EXECUTION_MODE);
/*  334 */     String sessionID = (String)((ValueAdapter)data.getValue(CommonAttribute.SESSION_ID)).getAdaptee();
/*  335 */     boolean checkHWK = (modeValue == null || modeValue != CommonValue.EXECUTION_MODE_INFO);
/*  336 */     if (checkHWK && !aclNoHWKGranted(sessionID)) {
/*  337 */       String hwk = (String)AVUtil.accessValue(data, CommonAttribute.HARDWARE_KEY);
/*  338 */       if (hwk == null)
/*  339 */         throw new RequestException(builder.makeHardwareKeyRequest()); 
/*  340 */       if (!SPSHardwareKey.getInstance(this).check(hwk)) {
/*  341 */         log.error("unregistered/invalid hardware-key: " + hwk);
/*  342 */         throw new SPSException(CommonException.NoAuthorization);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected boolean checkVINChange(SPSVehicle vehicle, SPSVIN vin, AttributeValueMap data) {
/*  348 */     if (getModel().checkSetting(vehicle.getSalesMakeID(), "VINChange", "disabled")) {
/*  349 */       String toolvin = (String)AVUtil.accessValue(data, CommonAttribute.TOOL_VIN);
/*  350 */       return vin.toString().equalsIgnoreCase(toolvin);
/*      */     } 
/*  352 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   protected SPSSession lookupSession(AttributeValueMap data) throws Exception {
/*  357 */     String sessionID = (String)AVUtil.accessValue(data, CommonAttribute.SESSION_ID);
/*  358 */     Value modeValue = data.getValue(CommonAttribute.EXECUTION_MODE);
/*  359 */     boolean isCALID = (modeValue != null && modeValue == CommonValue.EXECUTION_MODE_INFO);
/*  360 */     String sessionKey = isCALID ? ("CALID-" + SPSSession.class.getName()) : SPSSession.class.getName();
/*  361 */     SPSSession session = (SPSSession)ClientContextProvider.getInstance().getContext(sessionID).getObject(sessionKey);
/*  362 */     if (session == null || !assertSessionSignature(session, data) || !assertSessionTag(session, data) || !assertVIN(session, data)) {
/*  363 */       data.set(CommonAttribute.SPS_ADAPTER_TYPE, CommonValue.SPS_GME);
/*  364 */       Locale locale = (Locale)AVUtil.accessValue(data, CommonAttribute.LOCALE);
/*  365 */       SPSLanguage language = SPSLanguage.getLanguage(SPSLanguage.lookupLocale(locale), this);
/*  366 */       if (language == null) {
/*  367 */         log.info("Exception: Unsupported Locale");
/*  368 */         throw new SPSException(CommonException.UnsupportedLocale);
/*      */       } 
/*  370 */       SPSVIN vin = new SPSVIN(sessionID, data, getModel(), (String)AVUtil.accessValue(data, CommonAttribute.VIN));
/*  371 */       if (!vin.validate()) {
/*  372 */         throw new SPSException(CommonException.InvalidVIN);
/*      */       }
/*  374 */       SPSVehicle vehicle = new SPSVehicle(vin, this);
/*  375 */       if (!isCALID && !checkVINChange(vehicle, vin, data)) {
/*  376 */         throw new SPSException(CommonException.ChangedVIN);
/*      */       }
/*  378 */       setVehicleAttribute(data, CommonAttribute.SALESMAKE, new ValueAdapter(vehicle.getSalesMake()));
/*  379 */       setVehicleAttribute(data, CommonAttribute.MODEL, new ValueAdapter(vehicle.getModel()));
/*  380 */       setVehicleAttribute(data, CommonAttribute.MODELYEAR, new ValueAdapter(vehicle.getModelYear()));
/*  381 */       String engine = (String)AVUtil.accessValue(data, CommonAttribute.ENGINE);
/*  382 */       if (engine != null) {
/*  383 */         vehicle.setEngine(engine);
/*  384 */       } else if (vin.getEngineVC() != null) {
/*  385 */         data.set(CommonAttribute.ENGINE, (Value)new ValueAdapter(vehicle.getEngine()));
/*      */       } 
/*  387 */       String transmission = (String)AVUtil.accessValue(data, CommonAttribute.TRANSMISSION);
/*  388 */       if (transmission != null) {
/*  389 */         vehicle.setTransmission(transmission);
/*  390 */       } else if (vin.getTransmissionVC() != null) {
/*  391 */         data.set(CommonAttribute.TRANSMISSION, (Value)new ValueAdapter(vehicle.getTransmission()));
/*      */       } 
/*  393 */       ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/*  394 */       if (SPSHardwareKey.getInstance(this).checkHWK(context, vehicle.getSalesMakeID())) {
/*  395 */         checkHardwareRegistry(data);
/*      */       }
/*  397 */       if (supportsSPSFunctions()) {
/*  398 */         data.set(CommonAttribute.SPS_FUNCTION_SUPPORTED, CommonValue.ENABLE);
/*      */       }
/*  400 */       long signature = System.currentTimeMillis();
/*  401 */       data.set(CommonAttribute.SESSION_SIGNATURE, (Value)new ValueAdapter(new Long(signature)));
/*  402 */       Long tag = (Long)AVUtil.accessValue(data, CommonAttribute.SESSION_TAG);
/*  403 */       log.debug("sps session timestamp (ms): client=" + tag.longValue() + " server=" + signature + " delta=" + (tag.longValue() - signature));
/*  404 */       session = new SPSSession(signature, tag.longValue(), getModel(), language, vehicle, sessionID);
/*  405 */       session.setMode(isCALID);
/*  406 */       context.storeObject(sessionKey, session);
/*  407 */       if (getModel().checkSettingExists(((SPSVehicle)session.getVehicle()).getSalesMakeID(), "ClearDTC", "disabled")) {
/*  408 */         data.set(CommonAttribute.CLEAR_DTCS, CommonValue.DISABLE);
/*      */       }
/*  410 */       if (!isCALID) {
/*  411 */         session.registerLogoutListener(context, SPSEventLog.ADAPTER_GME);
/*      */       }
/*      */     } 
/*      */     
/*  415 */     session.fixDataStorage(data);
/*  416 */     return session;
/*      */   }
/*      */   
/*      */   protected void setVehicleAttribute(AttributeValueMap data, Attribute attribute, ValueAdapter value) throws Exception {
/*  420 */     Value setting = data.getValue(attribute);
/*  421 */     if (setting == null) {
/*  422 */       data.set(attribute, (Value)value);
/*  423 */     } else if (setting instanceof ValueAdapter && 
/*  424 */       !value.getAdaptee().equals(((ValueAdapter)setting).getAdaptee())) {
/*  425 */       log.error("VC/SIDS vehicle attribute mismatch: " + value.getAdaptee() + " vs " + ((ValueAdapter)setting).getAdaptee());
/*  426 */       throw new SPSException(CommonException.ServerSideFailure);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected List checkDevices(AttributeValueMap data) throws Exception {
/*  432 */     if (isPassThru(data)) {
/*  433 */       return null;
/*      */     }
/*  435 */     return (List)AVUtil.accessValue(data, CommonAttribute.VIT1_DEVICE_LIST);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void checkAsBuiltVIN(SPSSession session) {
/*  440 */     if (session.getAsBuiltVINID() == null) {
/*  441 */       Integer vinid = getModel().getVINID(session);
/*  442 */       if (!vinid.equals(SPSModel.NO_ASBUILT_VINID)) {
/*  443 */         log.debug("as built vinid = " + vinid);
/*      */       }
/*  445 */       session.setAsBuiltVINID(vinid);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected Controller _getController(AttributeValueMap data) throws Exception {
/*  450 */     SPSSession session = lookupSession(data);
/*  451 */     checkControllerSelection(session, data);
/*  452 */     checkVehicleState(session, data);
/*  453 */     if (session.getController() != null) {
/*  454 */       Object confirmation = data.getValue(CommonAttribute.CONFIRMED_OPTIONS);
/*  455 */       if (confirmation == null || CommonValue.OK.equals(confirmation)) {
/*  456 */         return (Controller)session.getController();
/*      */       }
/*      */     } 
/*  459 */     checkAsBuiltVIN(session);
/*  460 */     List devices = checkDevices(data);
/*  461 */     if (data.getValue(CommonAttribute.PROCEED_SAME_VIN) == null) {
/*  462 */       Value permission = CommonValue.DISABLE;
/*  463 */       if (isPassThru(data) && 
/*  464 */         aclProceedSameVINGranted(session.getSessionID())) {
/*  465 */         permission = CommonValue.OK;
/*      */       }
/*      */       
/*  468 */       data.set(CommonAttribute.PROCEED_SAME_VIN, permission);
/*      */     } 
/*  470 */     SPSControllerList controllers = (SPSControllerList)session.getControllers(devices, (Value)null, (String)null);
/*  471 */     if (controllers == null) {
/*  472 */       log.info("Exception: No Controllers found");
/*  473 */       throw new SPSException(CommonException.NoControllers);
/*      */     } 
/*  475 */     session.fixDataStorage(data);
/*  476 */     SPSControllerReference reference = (SPSControllerReference)data.getValue(CommonAttribute.CONTROLLER);
/*  477 */     if (reference != null && supportsSPSFunctions()) {
/*  478 */       if (isPassThru(data) && !session.isCALID()) {
/*  479 */         Value sequence = data.getValue(CommonAttribute.SEQUENCE);
/*  480 */         if (sequence == null)
/*  481 */           throw new RequestException(makeSequenceRequest(reference)); 
/*  482 */         if (needsQualifySequence(reference) || (session.getControllerReference() == null && data.getValue(CommonAttribute.VIT1) == null)) {
/*  483 */           reference.qualifySequence((String)((ValueAdapter)sequence).getAdaptee());
/*  484 */           reference.evaluateDefaultSelections(this, controllers);
/*  485 */           List options = reference.getPreOptions();
/*  486 */           if (options != null) {
/*  487 */             controllers.qualify(options, this);
/*  488 */             AssignmentRequest request = makeOptionSelectionRequest(session, (SPSVehicle)session.getVehicle(), options);
/*  489 */             throw new RequestException(request);
/*      */           } 
/*      */         } 
/*      */       } else {
/*  493 */         Value function = data.getValue(CommonAttribute.FUNCTION);
/*  494 */         if (function == null) {
/*  495 */           FunctionSelectionRequest request = builder.makeFunctionSelectionRequest(CommonAttribute.FUNCTION, reference.getProgrammingFunctions(), null);
/*  496 */           throw new RequestException(request);
/*      */         } 
/*  498 */         reference.qualifyFunction((String)((ValueAdapter)function).getAdaptee());
/*      */       } 
/*      */     }
/*      */     
/*  502 */     SPSProgrammingType method = (SPSProgrammingType)data.getValue(CommonAttribute.CONTROLLER_METHOD);
/*  503 */     if (reference != null && method == null) {
/*  504 */       TypeSelectionRequest request = builder.makeTypeSelectionRequest(CommonAttribute.CONTROLLER_METHOD, reference.getProgrammingMethods(), null);
/*  505 */       throw new RequestException(request);
/*      */     } 
/*  507 */     if (reference != null && method != null) {
/*  508 */       Value modeValue = data.getValue(CommonAttribute.EXECUTION_MODE);
/*  509 */       boolean needVIT1 = (modeValue == null || modeValue != CommonValue.EXECUTION_MODE_INFO);
/*  510 */       if (needVIT1 && data.getValue(CommonAttribute.VIT1) == null)
/*  511 */         throw new RequestException(makeVIT1Request(session, data, reference)); 
/*  512 */       if (needVIT1 && session.getVehicle().getVIT1() == null) {
/*  513 */         VIT1Data vit1 = (VIT1Data)AVUtil.accessValue(data, CommonAttribute.VIT1);
/*  514 */         session.getVehicle().setVIT1(vit1);
/*  515 */         if (isPassThru(data) && 
/*  516 */           !supportsSPSFunctions())
/*      */         {
/*      */           
/*  519 */           filterControllerList(vit1, controllers);
/*      */         }
/*      */       } 
/*      */       
/*  523 */       if (method.getID().equals(SPSProgrammingType.VCI)) {
/*  524 */         session.setController(data, reference, method, this);
/*  525 */         return (Controller)session.getController();
/*  526 */       }  if (session.getProgrammingType() != null) {
/*  527 */         session.update(this);
/*      */       } else {
/*  529 */         session.setController(data, reference, method, this);
/*      */       } 
/*  531 */       if (session.getController() != null) {
/*  532 */         return (Controller)session.getController();
/*      */       }
/*  534 */       AssignmentRequest request = makeOptionSelectionRequest(session, (SPSVehicle)session.getVehicle(), reference.getOptions());
/*  535 */       session.resetHWOCategory();
/*  536 */       throw new RequestException(request);
/*      */     } 
/*  538 */     throw new RequestException(makeControllerSelectionRequest(session, controllers));
/*      */   }
/*      */ 
/*      */   
/*      */   protected AssignmentRequest makeSequenceRequest(SPSControllerReference reference) {
/*  543 */     return (AssignmentRequest)builder.makeSequenceSelectionRequest(CommonAttribute.SEQUENCE, reference.getControllers(), null);
/*      */   }
/*      */   
/*      */   protected boolean needsQualifySequence(SPSControllerReference reference) {
/*  547 */     return (reference.getSequence() == null);
/*      */   }
/*      */   
/*      */   protected void filterControllerList(VIT1Data vit1, SPSControllerList controllers) throws Exception {
/*  551 */     int device = vit1.getECUAdress();
/*  552 */     Iterator<SPSControllerReference> it = controllers.iterator();
/*  553 */     while (it.hasNext()) {
/*  554 */       SPSControllerReference reference = it.next();
/*  555 */       reference.qualifyDevice(device);
/*  556 */       if (reference.getControllers().size() == 0) {
/*  557 */         it.remove();
/*      */       }
/*      */     } 
/*  560 */     if (controllers.size() == 0) {
/*  561 */       log.info("Exception: No Controllers found");
/*  562 */       throw new SPSException(CommonException.NoControllers);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected SPSOption checkEngineOption(List<SPSOption> options, String engine) {
/*  567 */     for (int i = 0; options != null && i < options.size(); i++) {
/*  568 */       SPSOption option = options.get(i);
/*  569 */       if (SPSVehicle.normalize(option.getDescription()).equals(engine)) {
/*  570 */         return option;
/*      */       }
/*      */     } 
/*  573 */     return null;
/*      */   }
/*      */   
/*      */   protected AssignmentRequest makeOptionSelectionRequest(SPSSession session, SPSVehicle vehicle, List<SPSOptionCategory> options) throws Exception {
/*  577 */     SPSOptionCategory category = options.get(0);
/*  578 */     if (session.isCALID() && category.isHWOCategory()) {
/*  579 */       List optionsHWO = session.retrieveHWOCategory();
/*  580 */       SPSBaseCategory.getHWNumberCALID(vehicle, null, optionsHWO);
/*      */     } 
/*      */ 
/*      */     
/*  584 */     SPSOption defaultSelection = (SPSOption)vehicle.getOption(category.getID());
/*  585 */     options = category.getOptions();
/*  586 */     if (((SPSVIN)vehicle.getVIN()).getEngineVC() != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  592 */       String engine = ((SPSVIN)vehicle.getVIN()).getEngineVC();
/*  593 */       SPSOption option = checkEngineOption(options, SPSVehicle.normalize(engine));
/*  594 */       if (option != null) {
/*  595 */         options = new ArrayList<SPSOptionCategory>();
/*  596 */         options.add(option);
/*  597 */         defaultSelection = option;
/*  598 */       } else if (category.getID().equals("ENG")) {
/*  599 */         throw new SPSException(CommonException.SoftwareNotAvailable);
/*      */       } 
/*      */     } 
/*  602 */     Collections.sort(options);
/*      */     
/*  604 */     if (defaultSelection != null) {
/*  605 */       for (int i = 0; options != null && i < options.size(); i++) {
/*  606 */         SPSOption option = options.get(i);
/*  607 */         if (option.getID().equals(defaultSelection.getID())) {
/*  608 */           defaultSelection = option;
/*      */           break;
/*      */         } 
/*      */       } 
/*  612 */       return (AssignmentRequest)builder.makeSelectionRequest((Attribute)category, options, (Value)defaultSelection, null);
/*  613 */     }  if (options.size() == 1) {
/*  614 */       return (AssignmentRequest)builder.makeSelectionRequest((Attribute)category, options, (Value)options.get(0), null);
/*      */     }
/*  616 */     return (AssignmentRequest)builder.makeSelectionRequest((Attribute)category, options, null);
/*      */   }
/*      */ 
/*      */   
/*      */   protected Request makeVIT1Request(SPSSession session, AttributeValueMap data, SPSControllerReference reference) throws Exception {
/*  621 */     List<SPSRequestMethodData> rdata = null;
/*  622 */     if (isPassThru(data)) {
/*  623 */       rdata = getModel().getRequestMethodData(session, reference);
/*      */     }
/*  625 */     if (rdata == null && reference.getOptions() != null) {
/*  626 */       SPSControllerList controllers = (SPSControllerList)session.getControllers();
/*  627 */       controllers.qualify(reference.getOptions(), this);
/*  628 */       return (Request)makeOptionSelectionRequest(session, (SPSVehicle)session.getVehicle(), reference.getOptions());
/*      */     } 
/*  630 */     if (rdata == null) {
/*  631 */       if (isPassThru(data)) {
/*  632 */         if (SPSRequestInfoCategory.hasRequestInfo(this)) {
/*  633 */           throw new SPSException(CommonException.UnsupportedController);
/*      */         }
/*  635 */         List devices = (List)AVUtil.accessValue(data, CommonAttribute.VIT1_DEVICE_LIST);
/*  636 */         if (!SPSModel.acceptDevice(devices, reference.getSystemTypeID())) {
/*  637 */           throw new SPSException(CommonException.UnsupportedController);
/*      */         }
/*      */       } 
/*      */       
/*  641 */       SPSRequestMethodData rmd = new SPSRequestMethodData(reference.getSystemTypeID());
/*  642 */       rdata = new ArrayList();
/*  643 */       rdata.add(rmd);
/*      */     } 
/*  645 */     return (Request)builder.makeVIT1Request(rdata);
/*      */   }
/*      */   
/*      */   protected AssignmentRequest makeControllerSelectionRequest(SPSSession session, SPSControllerList controllers) {
/*  649 */     if (controllers.size() > 1) {
/*  650 */       Map<Object, Object> systems = new HashMap<Object, Object>();
/*  651 */       Iterator<SPSControllerReference> it = controllers.iterator();
/*  652 */       while (it.hasNext()) {
/*  653 */         SPSControllerReference reference = it.next();
/*  654 */         String system = reference.getDescription();
/*  655 */         if (systems.get(system) != null) {
/*  656 */           SPSControllerReference master = (SPSControllerReference)systems.get(system);
/*  657 */           controllers.merge(master, reference);
/*  658 */           it.remove(); continue;
/*      */         } 
/*  660 */         systems.put(system, reference);
/*      */       } 
/*      */     } 
/*      */     
/*  664 */     if (supportsSPSFunctions()) {
/*  665 */       Iterator<SPSControllerReference> it = controllers.iterator();
/*  666 */       while (it.hasNext()) {
/*  667 */         SPSControllerReference reference = it.next();
/*  668 */         if (!session.isCALID()) {
/*  669 */           reference.getProgrammingSequences();
/*  670 */           reference.getProgrammingFunctions();
/*      */         } 
/*      */       } 
/*      */     } 
/*  674 */     return (AssignmentRequest)builder.makeControllerSelectionRequest(CommonAttribute.CONTROLLER, (List)controllers, null);
/*      */   }
/*      */   
/*      */   protected void loadProgrammingFile(SPSSession session, AttributeValueMap data, long vci) throws Exception {
/*  678 */     SPSProgrammingFile pfile = null;
/*      */     try {
/*  680 */       vci -= session.getVehicle().getVIN().getSequenceNo();
/*  681 */       vci -= 2147483647L;
/*  682 */       pfile = SPSProgrammingFile.load(getDatabaseLink(), vci);
/*  683 */       if (pfile != null && 
/*  684 */         "zip".equalsIgnoreCase(pfile.getType())) {
/*  685 */         data.set(CommonAttribute.ARCHIVE, (Value)new ValueAdapter(new SPSArchive(session.getLanguage(), (File)pfile.getFile(), pfile.getSecurityCodeFlag())));
/*      */         
/*      */         return;
/*      */       } 
/*  689 */       String dir = this.configuration.getProperty("vci-archives");
/*  690 */       File file = new File(dir, vci + "." + "zip");
/*  691 */       if (file.exists()) {
/*  692 */         data.set(CommonAttribute.ARCHIVE, (Value)new ValueAdapter(new SPSArchive(session.getLanguage(), file)));
/*      */       }
/*  694 */     } catch (Exception e) {
/*  695 */       log.error("failed to handle vci=" + vci, e);
/*  696 */       throw new SPSException(CommonException.INVALID_ARCHIVE_VCI);
/*      */     } finally {
/*  698 */       if (pfile != null && "zip".equalsIgnoreCase(pfile.getType())) {
/*      */         try {
/*  700 */           ((File)pfile.getFile()).delete();
/*  701 */         } catch (Exception x) {
/*  702 */           log.error("failed to delete temporary file: " + pfile.getFile(), x);
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   protected boolean isProgrammingByFileVCI(long vci) {
/*  709 */     return (vci > 2147483647L);
/*      */   }
/*      */   
/*      */   protected boolean isArchiveVCI(long vci) {
/*  713 */     return !isProgrammingByFileVCI(vci);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void handleVCI(SPSSession session, AttributeValueMap data) throws Exception {
/*  718 */     String vci = (String)AVUtil.accessValue(data, CommonAttribute.VCI);
/*  719 */     long input = -1L;
/*      */     try {
/*  721 */       input = Long.parseLong(vci);
/*  722 */     } catch (Exception e) {
/*  723 */       throw new SPSException(CommonException.InvalidVCIInput);
/*      */     } 
/*  725 */     if (isArchiveVCI(input)) {
/*  726 */       if (!getModel().hasAsBuiltSupport()) {
/*  727 */         throw new SPSException(CommonException.INVALID_ARCHIVE_VCI);
/*      */       }
/*  729 */       handleDealerVCI(session, data, input);
/*  730 */     } else if (isProgrammingByFileVCI(input)) {
/*  731 */       if (data.getValue(CommonAttribute.ARCHIVE) == null) {
/*  732 */         loadProgrammingFile(session, data, input);
/*      */       }
/*  734 */       SPSControllerGME controller = (SPSControllerGME)session.getController();
/*  735 */       if (data.getValue(CommonAttribute.ARCHIVE) != null) {
/*  736 */         controller.setArchive((Archive)AVUtil.accessValue(data, CommonAttribute.ARCHIVE));
/*      */       } else {
/*  738 */         throw new SPSException(CommonException.INVALID_ARCHIVE_VCI);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void handleDealerVCI(SPSSession session, AttributeValueMap data, long vci) throws Exception {
/*  744 */     if (session.getDealerVCI() >= 0L) {
/*      */       return;
/*      */     }
/*      */     try {
/*  748 */       vci -= session.getVehicle().getVIN().getSequenceNo();
/*  749 */       List vehicles = getModel().getVehicleListVCI((int)vci);
/*      */ 
/*      */ 
/*      */       
/*  753 */       if (vehicles == null) {
/*  754 */         throw new SPSException(CommonException.InvalidDealerVCI);
/*      */       }
/*  756 */       if (session.selectControllerByVehicleID(this, vehicles)) {
/*  757 */         if (session.getController() == null) {
/*  758 */           throw new RequestException(makeOptionSelectionRequest(session, (SPSVehicle)session.getVehicle(), session.getControllerReference().getOptions()));
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  763 */         VIT1Data vit1 = (VIT1Data)AVUtil.accessValue(data, CommonAttribute.VIT1);
/*  764 */         int device = vit1.getECUAdress();
/*  765 */         if (((SPSControllerGME)session.getController()).getSystemTypeID().intValue() != device) {
/*  766 */           throw new SPSException(CommonException.NonMatchingVCI);
/*      */         }
/*      */         
/*  769 */         session.setDealerVCI(vci);
/*      */       } else {
/*  771 */         throw new SPSException(CommonException.InvalidDealerVCI);
/*      */       }
/*      */     
/*  774 */     } catch (Exception e) {
/*  775 */       if (e instanceof RequestException) {
/*  776 */         throw e;
/*      */       }
/*  778 */       throw new SPSException(CommonException.InvalidDealerVCI);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected AssignmentRequest makeVCIInputRequest() {
/*  783 */     return (AssignmentRequest)builder.makeInputRequest(CommonAttribute.VCI, null, null);
/*      */   }
/*      */   
/*      */   protected boolean isProgrammingSequence(SPSSession session) {
/*  787 */     return session.getController() instanceof SPSControllerSequenceGME;
/*      */   }
/*      */   
/*      */   protected ProgrammingData _getProgrammingData(AttributeValueMap data) throws Exception {
/*  791 */     SPSSession session = lookupSession(data);
/*  792 */     if (session.getProgrammingType().getID().equals(SPSProgrammingType.VCI)) {
/*  793 */       if (data.getValue(CommonAttribute.VCI) == null) {
/*  794 */         throw new RequestException(makeVCIInputRequest());
/*      */       }
/*  796 */       handleVCI(session, data);
/*      */     } 
/*      */     
/*  799 */     SPSProgrammingData pdata = (SPSProgrammingData)session.getProgrammingData(this);
/*  800 */     if (!isProgrammingSequence(session) && pdata == null) {
/*  801 */       log.info("Exception: No valid COP");
/*  802 */       throw new SPSException(CommonException.NoValidCOP);
/*  803 */     }  if (data.getValue(CommonAttribute.PROGRAMMING_DATA_SELECTION) == null) {
/*  804 */       data.set(CommonAttribute.PROGRAMMING_DATA_SELECTION, CommonValue.OK);
/*  805 */       data.set(CommonAttribute.CONTROLLER_NAME, (Value)new ValueAdapter(session.getController().getDescription()));
/*      */     } 
/*  807 */     if (isProgrammingSequence(session)) {
/*  808 */       if (data.getValue(CommonAttribute.TYPE4_DATA) == null) {
/*  809 */         String buildfile = ((SPSControllerSequenceGME)session.getController()).getBuildFile();
/*  810 */         if (buildfile != null) {
/*  811 */           data.set(CommonAttribute.TYPE4_DATA, (Value)new ValueAdapter(buildfile));
/*  812 */           byte[] strings = SPSType4Data.getInstance(this).getType4Strings(session.getLanguage());
/*  813 */           if (strings != null) {
/*  814 */             data.set(CommonAttribute.TYPE4_STRINGS, (Value)new ValueAdapter(strings));
/*      */           }
/*      */         }
/*      */       
/*      */       } 
/*  819 */     } else if (session.getController() instanceof SPSControllerXML) {
/*  820 */       if (data.getValue(CommonAttribute.TYPE4_DATA) == null) {
/*  821 */         String buildfile = ((SPSControllerXML)session.getController()).getBuildFile();
/*  822 */         data.set(CommonAttribute.TYPE4_DATA, (Value)new ValueAdapter(buildfile));
/*  823 */         byte[] strings = SPSType4Data.getInstance(this).getType4Strings(session.getLanguage());
/*  824 */         if (strings != null) {
/*  825 */           data.set(CommonAttribute.TYPE4_STRINGS, (Value)new ValueAdapter(strings));
/*      */         }
/*      */       } 
/*  828 */     } else if (session.getController() instanceof SPSControllerGME && SPSModel.XML_DEVICE_ID.equals(((SPSControllerGME)session.getController()).getSystemTypeID()) && 
/*  829 */       data.getValue(CommonAttribute.TYPE4_STRINGS) == null) {
/*  830 */       byte[] strings = SPSType4Data.getInstance(this).getType4Strings(session.getLanguage());
/*  831 */       if (strings != null) {
/*  832 */         data.set(CommonAttribute.TYPE4_STRINGS, (Value)new ValueAdapter(strings));
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  837 */     if (data.getValue(CommonAttribute.SECURITY_ENABLED) == null && 
/*  838 */       !aclEnforceSPSSecurity(session.getSessionID()) && 
/*  839 */       isSecurityEnforced(session)) {
/*  840 */       throw new SPSException(CommonException.NoSecurityClearance);
/*      */     }
/*      */ 
/*      */     
/*  844 */     if (data.getValue(CommonAttribute.SECURITY_CODE) == null) {
/*  845 */       Value modeValue = data.getValue(CommonAttribute.EXECUTION_MODE);
/*  846 */       boolean isCALID = (modeValue != null && modeValue == CommonValue.EXECUTION_MODE_INFO);
/*      */ 
/*      */       
/*  849 */       SPSControllerGME controller = (SPSControllerGME)session.getController();
/*  850 */       boolean isSecurityCodeRequired = false;
/*  851 */       if (isProgrammingSequence(session)) {
/*  852 */         isSecurityCodeRequired = (!isCALID && ((SPSControllerSequenceGME)session.getController()).isSecurityCodeRequired(this));
/*  853 */       } else if (controller.getArchive() != null) {
/*  854 */         SPSArchive archive = (SPSArchive)controller.getArchive();
/*  855 */         if (archive != null) {
/*  856 */           isSecurityCodeRequired = archive.getSecurityCodeFlag();
/*      */         }
/*      */       } else {
/*  859 */         isSecurityCodeRequired = (!isCALID && SPSSecurityCode.getInstance(this).isSecurityCodeRequired(controller.getVehicleID()));
/*      */       } 
/*  861 */       if (isSecurityCodeRequired) {
/*  862 */         String vin = session.getVehicle().getVIN().toString();
/*  863 */         String securityCode = SPSSecurityCode.getInstance(this).getSecurityCode(vin);
/*  864 */         if (securityCode == null) {
/*  865 */           throw new RequestException(builder.makeInputRequest(CommonAttribute.SECURITY_CODE, null, null));
/*      */         }
/*  867 */         data.set(CommonAttribute.SECURITY_CODE, (Value)new ValueAdapter(securityCode));
/*      */       } else {
/*      */         
/*  870 */         data.set(CommonAttribute.SECURITY_CODE, CommonValue.DISABLE);
/*      */       } 
/*      */     } 
/*  873 */     if (data.getValue(CommonAttribute.SUMMARY) == null) {
/*  874 */       Value modeValue = data.getValue(CommonAttribute.EXECUTION_MODE);
/*  875 */       boolean isCALID = (modeValue != null && modeValue == CommonValue.EXECUTION_MODE_INFO);
/*  876 */       if (!isCALID && data.getValue(CommonAttribute.LABOR_TIME_CONFIGURATION) == null) {
/*  877 */         ServiceID serviceID = (ServiceID)AVUtil.accessValue(data, ServiceResolution.ATTR_SERVICE_ID);
/*  878 */         SPSLaborTimeConfiguration ltcfg = (serviceID == null) ? null : SPSLaborTime.getLaborTimeConfiguration(serviceID);
/*  879 */         if (ltcfg != null) {
/*  880 */           data.set(CommonAttribute.LABOR_TIME_CONFIGURATION, (Value)new ValueAdapter(ltcfg));
/*      */         } else {
/*  882 */           data.set(CommonAttribute.LABOR_TIME_CONFIGURATION, CommonValue.DISABLE);
/*      */         } 
/*      */       } 
/*  885 */       if (!isCALID && isProgrammingSequence(session)) {
/*  886 */         ((SPSControllerSequenceGME)session.getController()).setCurrentSoftware(this, getModel());
/*  887 */         ((SPSControllerSequenceGME)session.getController()).checkSecurity(this, data, builder);
/*  888 */         throw new RequestException(displaySummaryScreen(session, data, this));
/*      */       } 
/*  890 */       VIT1Data vit1 = session.getVehicle().getVIT1();
/*  891 */       if (((SPSControllerGME)session.getController()).getCurrentSoftware() == null && vit1 != null) {
/*  892 */         SPSSoftware current = null;
/*  893 */         if (vit1.getECU() > 0) {
/*  894 */           current = SPSSoftware.load((SPSLanguage)session.getLanguage(), vit1, vit1.getECU(), this);
/*      */         } else {
/*  896 */           List identTypeList = getModel().getIdentTypeList((SPSVehicle)session.getVehicle(), vit1, false);
/*  897 */           current = SPSSoftware.load((SPSLanguage)session.getLanguage(), vit1, identTypeList, this);
/*      */         } 
/*  899 */         if (current != null) {
/*  900 */           SPSControllerGME controller = (SPSControllerGME)session.getController();
/*  901 */           controller.setCurrentSoftware(current);
/*      */         } 
/*      */       } 
/*  904 */       checkCalibrationInfoDealerVCI(session, data);
/*  905 */       String hwk = (String)AVUtil.accessValue(data, CommonAttribute.HARDWARE_KEY32);
/*  906 */       if (hwk == null && !isCALID) {
/*  907 */         throw new RequestException(builder.makeHardwareKeyRequest());
/*      */       }
/*  909 */       if (hwk != null && !SPSSecurity.getInstance(this).check(hwk, session.getController().getID())) {
/*  910 */         log.info("Exception: No Authorization");
/*  911 */         throw new SPSException(CommonException.NoAuthorization);
/*  912 */       }  if (!isCALID && !checkOptionSecurity(session, hwk)) {
/*  913 */         log.info("Exception: No Authorization");
/*  914 */         throw new SPSException(CommonException.NoAuthorization);
/*      */       } 
/*  916 */       throw new RequestException(displaySummaryScreen(session, data, this));
/*      */     } 
/*      */ 
/*      */     
/*  920 */     if (session.getDealerVCI() <= 0L && data.getValue(CommonAttribute.VCI) != null) {
/*  921 */       return (ProgrammingData)pdata;
/*      */     }
/*  923 */     if (isProgrammingSequence(session)) {
/*  924 */       ((SPSControllerSequenceGME)session.getController()).prepareProgrammingData(this);
/*  925 */     } else if (pdata.getCalibrationFiles() == null) {
/*  926 */       List<ProgrammingDataUnit> blobs = new ArrayList();
/*  927 */       List<SPSModule> modules = pdata.getModules();
/*  928 */       if (modules == null) {
/*  929 */         log.info("Exception: Software Not Available");
/*  930 */         throw new SPSException(CommonException.SoftwareNotAvailable);
/*      */       } 
/*  932 */       for (int i = 0; i < modules.size(); i++) {
/*  933 */         SPSModule module = modules.get(i);
/*  934 */         ProgrammingDataUnit blob = module.getCalibrationFileInfo((SPSPart)module.getSelectedPart(), this, false);
/*  935 */         if (blob == null) {
/*  936 */           log.info("Exception: Missing Calibration File");
/*  937 */           throw new SPSException(CommonException.MissingCalibrationFile);
/*      */         } 
/*  939 */         blobs.add(blob);
/*      */       } 
/*      */       
/*  942 */       pdata.setCalibrationFiles(blobs);
/*      */     } 
/*  944 */     return (ProgrammingData)pdata;
/*      */   }
/*      */   
/*      */   protected boolean isSecurityEnforced(SPSSession session) {
/*  948 */     SPSControllerGME controller = (SPSControllerGME)session.getController();
/*  949 */     if (isProgrammingSequence(session)) {
/*  950 */       return ((SPSControllerSequenceGME)controller).isSecurityEnforced(getModel());
/*      */     }
/*  952 */     return getModel().isSecurityEnforced(controller.getVehicleID());
/*      */   }
/*      */ 
/*      */   
/*      */   protected void checkCalibrationInfoDealerVCI(SPSSession session, AttributeValueMap data) {
/*  957 */     Value modeValue = data.getValue(CommonAttribute.EXECUTION_MODE);
/*  958 */     if (modeValue == CommonValue.EXECUTION_MODE_INFO && data.getValue(CommonAttribute.DEALER_VCI) == null) {
/*  959 */       SPSControllerGME controller = (SPSControllerGME)session.getController();
/*  960 */       if (controller.getVCI() > 0) {
/*  961 */         int vci = controller.getVCI() + session.getVehicle().getVIN().getSequenceNo();
/*  962 */         data.set(CommonAttribute.DEALER_VCI, (Value)new ValueAdapter(new Integer(vci)));
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected boolean checkOptionSecurity(SPSSession session, String hwk) throws Exception {
/*  968 */     SPSControllerGME controller = (SPSControllerGME)session.getController();
/*  969 */     if (controller.getSoftware().identical(controller.getCurrentSoftware()) && (
/*  970 */       session.getVehicle().getVIT1() == null || !session.getVehicle().getVIT1().isValid())) {
/*  971 */       return true;
/*      */     }
/*      */     
/*  974 */     return SPSSecurity.getInstance(this).checkOptions(hwk, (SPSVehicle)session.getVehicle());
/*      */   }
/*      */   
/*      */   public AssignmentRequest displaySummaryScreen(SPSSession session, AttributeValueMap data, SPSSchemaAdapterGME adapter) throws Exception {
/*  978 */     SPSVehicle vehicle = (SPSVehicle)session.getVehicle();
/*      */     
/*  980 */     List<SPSOption> options = vehicle.getOptions();
/*  981 */     if (options != null) {
/*  982 */       for (int i = 0; i < options.size(); i++) {
/*  983 */         SPSOption option = options.get(i);
/*  984 */         if (option.getType() != null) {
/*  985 */           SPSOption existing = (SPSOption)data.getValue((Attribute)option.getType());
/*  986 */           if (existing == null || !existing.equals(option)) {
/*  987 */             data.set((Attribute)option.getType(), (Value)option);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*  992 */     if (isProgrammingSequence(session)) {
/*  993 */       List list = ((SPSControllerSequenceGME)session.getController()).prepareSummary(this);
/*  994 */       return (AssignmentRequest)builder.makeDisplaySummaryRequest(CommonAttribute.SUMMARY, list);
/*      */     } 
/*  996 */     SPSProgrammingData pdata = (SPSProgrammingData)session.getProgrammingData(adapter);
/*  997 */     List modules = pdata.getModules();
/*  998 */     SPSControllerGME controller = (SPSControllerGME)session.getController();
/*  999 */     Summary summary = new SPSSummary(controller.getCurrentSoftware(), controller.getSoftware(), controller.getHistory(), modules);
/* 1000 */     return (AssignmentRequest)builder.makeDisplaySummaryRequest(CommonAttribute.SUMMARY, summary);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkSoftwareUpdate(SPSSession session, AttributeValueMap data) throws Exception {
/* 1009 */     SPSControllerGME controller = (SPSControllerGME)session.getController();
/* 1010 */     if (controller.getSoftware().identical(controller.getCurrentSoftware()) && 
/* 1011 */       getModel().checkSettingExists(((SPSVehicle)session.getVehicle()).getSalesMakeID(), "SW Selection", "no_equal") && 
/* 1012 */       session.getVehicle().getVIT1() != null && session.getVehicle().getVIT1().isValid()) {
/* 1013 */       log.info("Exception: No Calibration Update");
/* 1014 */       throw new SPSException(CommonException.NoCalibrationUpdate);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isProgrammingByFileVCI(AttributeValueMap data) {
/* 1022 */     String vci = (String)AVUtil.accessValue(data, CommonAttribute.VCI);
/* 1023 */     long input = -1L;
/*      */     try {
/* 1025 */       input = Long.parseLong(vci);
/* 1026 */     } catch (Exception e) {}
/*      */     
/* 1028 */     return isProgrammingByFileVCI(input);
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
/*      */   protected Boolean _reprogram(ProgrammingData pdata, AttributeValueMap data) throws Exception {
/* 1040 */     SPSSession session = lookupSession(data);
/* 1041 */     if (data.getValue(CommonAttribute.SECURITY_CHECK) == null) {
/* 1042 */       String hwk = (String)AVUtil.accessValue(data, CommonAttribute.HARDWARE_KEY32);
/* 1043 */       if (isProgrammingSequence(session)) {
/* 1044 */         ((SPSControllerSequenceGME)session.getController()).checkExclusiveSecurity(this, hwk);
/*      */       } else {
/* 1046 */         int ecu = session.getController().getID();
/* 1047 */         if (!SPSSecurity.getInstance(this).checkExclusiveSecurity(hwk, ecu)) {
/* 1048 */           log.info("Exception: No Authorization");
/* 1049 */           throw new SPSException(CommonException.NoAuthorization);
/*      */         } 
/*      */       } 
/* 1052 */       data.set(CommonAttribute.SECURITY_CHECK, CommonValue.OK);
/*      */     } 
/* 1054 */     if (data.getValue(CommonAttribute.PRE_PROGRAMMING_INSTRUCTIONS) == null) {
/* 1055 */       SPSController controller = session.getController();
/* 1056 */       if (controller.getPreProgrammingInstructions() != null) {
/* 1057 */         List instructions = getModel().getProgrammingMessage((SPSLanguage)session.getLanguage(), controller.getPreProgrammingInstructions());
/* 1058 */         if (instructions != null) {
/* 1059 */           String content = "pre-prog-instructions";
/* 1060 */           InstructionDisplayRequest instructionDisplayRequest = builder.makeInstructionDisplayRequest(CommonAttribute.PRE_PROGRAMMING_INSTRUCTIONS, content, instructions);
/* 1061 */           instructionDisplayRequest.setAutoSubmit(false);
/* 1062 */           throw new RequestException(instructionDisplayRequest);
/*      */         } 
/* 1064 */         data.set(CommonAttribute.PRE_PROGRAMMING_INSTRUCTIONS, CommonValue.FAIL);
/*      */       } 
/*      */     } 
/*      */     
/* 1068 */     if ((data.getValue(CommonAttribute.VCI) == null || isProgrammingByFileVCI(data)) && 
/* 1069 */       data.getValue(CommonAttribute.CONFIRM_PROGRAMMING_DATA_DOWNLOAD_START) == null) {
/* 1070 */       if (data.getValue(CommonAttribute.VCI) == null) {
/* 1071 */         if (isProgrammingSequence(session)) {
/* 1072 */           ((SPSControllerSequenceGME)session.getController()).checkSoftwareUpdate(getModel());
/*      */         } else {
/* 1074 */           checkSoftwareUpdate(session, data);
/*      */         } 
/*      */       }
/* 1077 */       List blobs = (pdata == null) ? null : pdata.getCalibrationFiles();
/* 1078 */       if (isProgrammingSequence(session)) {
/* 1079 */         blobs = ((SPSControllerSequenceGME)session.getController()).getCalibrationFiles();
/*      */       }
/* 1081 */       throw new RequestException(builder.makeProgrammingDataDownloadRequest(CommonAttribute.CONFIRM_PROGRAMMING_DATA_DOWNLOAD_START, blobs));
/*      */     } 
/*      */     
/* 1084 */     if (data.getValue(CommonAttribute.CONFIRM_REPROGRAM_DISPLAY) == null) {
/* 1085 */       throw new RequestException(builder.makeReprogramDisplayRequest());
/*      */     }
/* 1087 */     if (data.getValue(CommonAttribute.VCI) == null && 
/* 1088 */       data.getValue(CommonAttribute.CONFIRM_PROGRAMMING_DATA_DOWNLOAD_FINISHED) == null) {
/* 1089 */       throw new RequestException(builder.makeProgrammingDataDownloadContinuationRequest());
/*      */     }
/*      */     
/* 1092 */     if (data.getValue(CommonAttribute.WARRANTY_CLAIM_CODE) == null) {
/* 1093 */       SPSVehicle vehicle = (SPSVehicle)session.getVehicle();
/* 1094 */       if (getModel().checkSetting(vehicle.getSalesMakeID(), "WarrantyClaimCode_Display", "enabled")) {
/* 1095 */         data.set(CommonAttribute.WARRANTY_CLAIM_CODE, (Value)new ValueAdapter(computeWarrantyClaimCode(session, data)));
/*      */       }
/*      */     } 
/* 1098 */     if (data.getValue(CommonAttribute.INSTRUCTION_DOWNLOAD) == null) {
/* 1099 */       String toolType = (String)AVUtil.accessValue(data, CommonAttribute.DEVICE);
/* 1100 */       String content = toolType + "-" + "final-instructions";
/* 1101 */       SPSController controller = session.getController();
/* 1102 */       List instructions = null;
/* 1103 */       if (controller.getPostProgrammingInstructions() != null) {
/* 1104 */         instructions = getModel().getProgrammingMessage((SPSLanguage)session.getLanguage(), controller.getPostProgrammingInstructions());
/* 1105 */         content = content + "=" + instructions;
/* 1106 */         data.set(CommonAttribute.POST_PROGRAMMING_INSTRUCTIONS, CommonValue.OK);
/*      */       } 
/* 1108 */       InstructionDisplayRequest instructionDisplayRequest = builder.makeInstructionDisplayRequest(CommonAttribute.FINAL_INSTRUCTIONS, content, instructions);
/* 1109 */       data.set(CommonAttribute.INSTRUCTION_DOWNLOAD, (Value)new ValueAdapter(instructionDisplayRequest));
/*      */     } 
/* 1111 */     if (data.getValue(CommonAttribute.REPROGRAM) == null) {
/* 1112 */       if (isProgrammingSequence(session)) {
/* 1113 */         ((SPSControllerSequenceGME)session.getController()).makeReprogramRequest(this, getModel(), builder);
/*      */       } else {
/* 1115 */         throw new RequestException(builder.makeReprogramRequest(CommonAttribute.REPROGRAM, pdata));
/*      */       } 
/*      */     }
/* 1118 */     if (data.getValue(CommonAttribute.REPROGRAM_PROTOCOL) == null) {
/* 1119 */       SPSEvents.logReprogramEvent(data, session, this);
/* 1120 */       data.set(CommonAttribute.REPROGRAM_PROTOCOL, CommonValue.OK);
/* 1121 */       Value success = data.getValue(CommonAttribute.REPROGRAM);
/* 1122 */       if (!success.equals(CommonValue.OK)) {
/* 1123 */         session.done();
/* 1124 */         return Boolean.TRUE;
/*      */       } 
/*      */     } 
/* 1127 */     if (data.getValue(CommonAttribute.FINAL_INSTRUCTIONS) == null) {
/* 1128 */       AssignmentRequest request = (AssignmentRequest)AVUtil.accessValue(data, CommonAttribute.INSTRUCTION_DOWNLOAD);
/* 1129 */       throw new RequestException(request);
/*      */     } 
/* 1131 */     String sessionID = (String)AVUtil.accessValue(data, CommonAttribute.SESSION_ID);
/* 1132 */     getModel().getVC().setVC(sessionID, (SPSVehicle)session.getVehicle());
/* 1133 */     session.done();
/* 1134 */     return Boolean.TRUE;
/*      */   }
/*      */   
/*      */   protected String computeSequenceWarrantyClaimCode(SPSSession session, AttributeValueMap data) {
/* 1138 */     return "S" + ((SPSControllerSequenceGME)session.getController()).getSequenceID();
/*      */   }
/*      */   
/*      */   protected String computeWarrantyClaimCode(SPSSession session, AttributeValueMap data) {
/* 1142 */     String prefix = null;
/* 1143 */     int dealerVCI = 0;
/* 1144 */     if (isProgrammingSequence(session)) {
/* 1145 */       prefix = "S" + ((SPSControllerSequenceGME)session.getController()).getSequenceID();
/* 1146 */       dealerVCI = session.getVehicle().getVIN().getSequenceNo() + ((SPSControllerSequenceGME)session.getController()).getWarrantyClaimCodeID().intValue();
/*      */     } else {
/* 1148 */       Integer deviceID = ((SPSControllerGME)session.getController()).getSystemTypeID();
/* 1149 */       prefix = Integer.toHexString(deviceID.intValue());
/* 1150 */       dealerVCI = session.getVehicle().getVIN().getSequenceNo() + ((SPSControllerGME)session.getController()).getVehicleID().intValue();
/*      */     } 
/* 1152 */     String wcc = Integer.toHexString(dealerVCI);
/* 1153 */     if (wcc.length() > 3) {
/* 1154 */       wcc = wcc.substring(wcc.length() - 3);
/*      */     } else {
/* 1156 */       while (wcc.length() < 3) {
/* 1157 */         wcc = "0" + wcc;
/*      */       }
/*      */     } 
/* 1160 */     return prefix + wcc;
/*      */   }
/*      */   
/*      */   protected byte[] _getData(ProgrammingDataUnit blob, AttributeValueMap data) throws RequestException, Exception {
/* 1164 */     SPSSession session = lookupSession(data);
/* 1165 */     if (isProgrammingSequence(session))
/* 1166 */       return ((SPSControllerSequenceGME)session.getController()).getProgrammingDataUnit(this, blob); 
/* 1167 */     if (data.getValue(CommonAttribute.ARCHIVE) != null) {
/* 1168 */       SPSArchive archive = (SPSArchive)((SPSControllerGME)session.getController()).getArchive();
/* 1169 */       if (archive != null) {
/* 1170 */         List<ProgrammingDataUnit> units = archive.getCalibrationUnits();
/* 1171 */         List<byte[]> calibrations = archive.getCalibrationFiles();
/* 1172 */         for (int j = 0; j < calibrations.size(); j++) {
/* 1173 */           ProgrammingDataUnit unit = units.get(j);
/* 1174 */           if (unit.getBlobName().equals(blob.getBlobName())) {
/* 1175 */             return calibrations.get(j);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1181 */     SPSProgrammingData pdata = (SPSProgrammingData)session.getProgrammingData(this);
/* 1182 */     List<SPSModule> modules = pdata.getModules();
/* 1183 */     for (int i = 0; i < modules.size(); i++) {
/* 1184 */       SPSModule module = modules.get(i);
/* 1185 */       if (blob.getBlobID().intValue() == module.getOrder()) {
/* 1186 */         byte[] bytes = module.getCalibrationFile((SPSPart)module.getSelectedPart(), this, false);
/* 1187 */         if (bytes != null) {
/* 1188 */           return bytes;
/*      */         }
/*      */       } 
/*      */     } 
/* 1192 */     log.info("Exception: Missing Calibration File");
/* 1193 */     throw new SPSException(CommonException.MissingCalibrationFile);
/*      */   }
/*      */   
/*      */   public String getHTML(String locale, String id) {
/*      */     try {
/* 1198 */       SPSLanguage language = SPSLanguage.getLanguage(locale, this);
/* 1199 */       if (language == null) {
/* 1200 */         language = SPSLanguage.getLanguage(SPSLanguage.lookupLocale(locale), this);
/*      */       }
/* 1202 */       return SPSInstructions.loadHTML(language, id);
/* 1203 */     } catch (Exception e) {
/*      */       
/* 1205 */       return null;
/*      */     } 
/*      */   }
/*      */   public byte[] getImage(String id) {
/*      */     try {
/* 1210 */       return SPSInstructions.loadImage(id);
/* 1211 */     } catch (Exception e) {
/*      */       
/* 1213 */       return null;
/*      */     } 
/*      */   }
/*      */   public Object getVersionInfo() {
/*      */     try {
/* 1218 */       List<DBVersionInformation> versionInfo = new LinkedList();
/* 1219 */       if (ApplicationContext.getInstance().isStandalone()) {
/*      */         
/* 1221 */         String version = this.configuration.getProperty("db.version");
/* 1222 */         String str1 = this.configuration.getProperty("db.description");
/* 1223 */         versionInfo.add(new DBVersionInformation(null, "", new Date(), str1, version));
/*      */       } else {
/*      */         
/* 1226 */         versionInfo.add(getModel().getVersionInfo());
/*      */       } 
/* 1228 */       DBVersionInformation vcDBInfo = VCProxy.getInstance(this).getDatabaseInfo();
/* 1229 */       String description = "VC data provision: [" + vcDBInfo.getReleaseDescription() + "]";
/* 1230 */       versionInfo.add(new DBVersionInformation(vcDBInfo.getCreator(), vcDBInfo.getReleaseID(), vcDBInfo.getReleaseDate(), description, vcDBInfo.getReleaseVersion()));
/*      */       
/* 1232 */       return versionInfo;
/*      */     }
/* 1234 */     catch (Exception e) {
/* 1235 */       log.warn("unable to retrieve version information, returning null - exception:" + e, e);
/* 1236 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   public DatabaseInfo getDatabaseInfo(AttributeValueMap avMap, Attribute lastRequestedAttribute) throws Exception {
/* 1241 */     return null;
/*      */   }
/*      */   
/*      */   private boolean aclNoHWKGranted(String sessionID) {
/* 1245 */     boolean result = false;
/* 1246 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/*      */     try {
/* 1248 */       ACLService aclService = ACLServiceProvider.getInstance().getService();
/* 1249 */       Map usrGroup2Manuf = context.getSharedContext().getUsrGroup2ManufMap();
/* 1250 */       Set<String> tmp = new HashSet();
/* 1251 */       tmp.add("no-hwk");
/* 1252 */       result = (aclService.getAuthorizedResources("AdapterData", tmp, usrGroup2Manuf, context.getSharedContext().getCountry()).size() != 0);
/*      */     }
/* 1254 */     catch (Exception e) {
/* 1255 */       log.warn("unable to decide, setting result to false - exception: " + e);
/*      */     } 
/*      */     
/* 1258 */     return result;
/*      */   }
/*      */   
/*      */   private boolean aclProceedSameVINGranted(String sessionID) {
/* 1262 */     boolean result = false;
/* 1263 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/*      */     try {
/* 1265 */       ACLService aclService = ACLServiceProvider.getInstance().getService();
/* 1266 */       Map usrGroup2Manuf = context.getSharedContext().getUsrGroup2ManufMap();
/* 1267 */       Set<String> tmp = new HashSet();
/* 1268 */       tmp.add("proceed-same-vin");
/* 1269 */       result = (aclService.getAuthorizedResources("AdapterData", tmp, usrGroup2Manuf, context.getSharedContext().getCountry()).size() != 0);
/*      */     }
/* 1271 */     catch (Exception e) {
/* 1272 */       log.warn("unable to decide, setting result to false - exception: " + e);
/*      */     } 
/*      */     
/* 1275 */     return result;
/*      */   }
/*      */   
/*      */   private boolean aclEnforceSPSSecurity(String sessionID) {
/* 1279 */     boolean result = false;
/* 1280 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/*      */     try {
/* 1282 */       ACLService aclService = ACLServiceProvider.getInstance().getService();
/* 1283 */       Map usrGroup2Manuf = context.getSharedContext().getUsrGroup2ManufMap();
/* 1284 */       Set<String> tmp = new HashSet();
/* 1285 */       tmp.add("sps-security-enabled");
/* 1286 */       result = (aclService.getAuthorizedResources("AdapterData", tmp, usrGroup2Manuf, context.getSharedContext().getCountry()).size() != 0);
/*      */     }
/* 1288 */     catch (Exception e) {
/* 1289 */       log.warn("unable to decide, setting result to false - exception: " + e);
/*      */     } 
/*      */     
/* 1292 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void _reset() {}
/*      */ 
/*      */   
/*      */   public Set getCfgProviders() {
/* 1300 */     return VCProxy.getInstance(this).getCfgProviders();
/*      */   }
/*      */   
/*      */   public Set getVINResolvers(ClientContext context) {
/* 1304 */     return VCProxy.getInstance(this).getVINResolvers(context);
/*      */   }
/*      */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSSchemaAdapterGME.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */