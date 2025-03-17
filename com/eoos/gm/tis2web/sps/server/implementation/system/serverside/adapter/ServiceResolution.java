/*     */ package com.eoos.gm.tis2web.sps.server.implementation.system.serverside.adapter;
/*     */ 
/*     */ import com.eoos.datatype.marker.Configurable;
/*     */ import com.eoos.gm.tis2web.acl.service.ACLService;
/*     */ import com.eoos.gm.tis2web.acl.service.ACLServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContext;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.ConfigurationService;
/*     */ import com.eoos.gm.tis2web.sids.service.ServiceIDService;
/*     */ import com.eoos.gm.tis2web.sids.service.ServiceIDServiceProvider;
/*     */ import com.eoos.gm.tis2web.sids.service.cai.NoServiceIDException;
/*     */ import com.eoos.gm.tis2web.sids.service.cai.ServiceID;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.UnsupportedVehicleException;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Controller;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingData;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingDataUnit;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.AVUtil;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.AttributeImpl;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonValue;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ValueAdapter;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.request.VINRequestImpl;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.dbinfo.DatabaseInfo;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.RequestException;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import com.eoos.gm.tis2web.vc.v2.provider.CfgProviderRetrieval;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VINResolverRetrieval;
/*     */ import com.eoos.observable.IObservableSupport;
/*     */ import com.eoos.observable.Notification;
/*     */ import com.eoos.observable.ObservableSupport;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.propcfg.util.ConfigurationUtil;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.util.ClassUtil;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServiceResolution
/*     */   implements SchemaAdapterAPI, CfgProviderRetrieval, VINResolverRetrieval
/*     */ {
/*  61 */   private static final Logger log = Logger.getLogger(ServiceResolution.class);
/*     */   
/*  63 */   public static final Attribute ATTR_SERVICE_ID = AttributeImpl.getInstance("attribute.service.id");
/*     */   
/*  65 */   private static ServiceResolution instance = null;
/*     */   
/*  67 */   private final Object SYNC_STATE = new Object();
/*     */   
/*     */   private class State {
/*  70 */     private Map serviceIDToAdapter = new LinkedHashMap<Object, Object>();
/*     */     private State() {}
/*  72 */     private final Collection configKeys = new LinkedList();
/*     */     
/*  74 */     private int configHash = 0;
/*     */   }
/*     */ 
/*     */   
/*  78 */   private State state = null;
/*     */   
/*  80 */   private IObservableSupport observableSupport = (IObservableSupport)new ObservableSupport();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   private static final Notification NOTIFICATION_RESET = new Notification()
/*     */     {
/*     */       public void notify(Object observer) {
/*  89 */         ((ServiceResolution.ResetObserver)observer).onReset();
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   private ServiceResolution() {
/*  95 */     ConfigurationService configService = (ConfigurationService)FrameServiceProvider.getInstance().getService(ConfigurationService.class);
/*  96 */     configService.addObserver(new ConfigurationService.Observer()
/*     */         {
/*     */           public void onModification() {
/*  99 */             synchronized (ServiceResolution.this.SYNC_STATE) {
/* 100 */               if (ServiceResolution.this.state != null && ServiceResolution
/* 101 */                 .configHash(ServiceResolution.this.state.configKeys) != ServiceResolution.this.state.configHash) {
/* 102 */                 ServiceResolution.log.info("configuration changed - resetting and notifying observers");
/* 103 */                 ServiceResolution.this.reset();
/*     */               } 
/*     */             } 
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 111 */     init();
/*     */   }
/*     */ 
/*     */   
/*     */   private State getState() {
/* 116 */     synchronized (this.SYNC_STATE) {
/* 117 */       if (this.state == null) {
/* 118 */         this.state = new State();
/* 119 */         log.info("initializing adapter map ...");
/* 120 */         ConfigurationService configurationService = (ConfigurationService)FrameServiceProvider.getInstance().getService(ConfigurationService.class);
/* 121 */         SubConfigurationWrapper adapterIDs = new SubConfigurationWrapper((Configuration)configurationService, "component.sps.adapter.");
/* 122 */         this.state.configKeys.add("component.sps.adapter");
/* 123 */         for (Iterator<String> iter = adapterIDs.getKeys().iterator(); iter.hasNext(); ) {
/* 124 */           String key = iter.next();
/* 125 */           if (key.endsWith(".class")) {
/* 126 */             String identifier = key.substring(0, key.length() - ".class".length());
/* 127 */             log.info("... creating schema adapter for " + String.valueOf(identifier));
/*     */             try {
/* 129 */               SubConfigurationWrapper adapterConf = new SubConfigurationWrapper((Configuration)configurationService, "component.sps.adapter." + String.valueOf(identifier) + ".");
/*     */               
/* 131 */               SchemaAdapterSPI adapter = null;
/* 132 */               Class<?> clazz = Class.forName(adapterConf.getProperty("class"));
/*     */               
/* 134 */               if (ClassUtil.getAllInterfaces(clazz).contains(Configurable.class)) {
/* 135 */                 adapter = clazz.getConstructor(new Class[] { Configuration.class }).newInstance(new Object[] { adapterConf });
/*     */               } else {
/* 137 */                 adapter = (SchemaAdapterSPI)clazz.newInstance();
/*     */               } 
/*     */               
/* 140 */               SchemaAdapterWrapper.InitMode initMode = SchemaAdapterWrapper.InitMode.LAZY;
/*     */               try {
/* 142 */                 if ("eager".equalsIgnoreCase(adapterConf.getProperty("init.mode").trim())) {
/* 143 */                   initMode = SchemaAdapterWrapper.InitMode.EAGER;
/*     */                 }
/* 145 */               } catch (Exception e) {}
/*     */               
/* 147 */               adapter = new SchemaAdapterWrapper(identifier, initMode, adapter);
/* 148 */               log.debug("... created schema adapter for " + String.valueOf(identifier));
/*     */               
/* 150 */               ServiceIDService serviceIDService = ServiceIDServiceProvider.getInstance().getService();
/* 151 */               SubConfigurationWrapper subConfigurationWrapper1 = new SubConfigurationWrapper((Configuration)adapterConf, "sid-mapping.");
/* 152 */               for (Iterator<String> iter2 = subConfigurationWrapper1.getKeys().iterator(); iter2.hasNext(); ) {
/* 153 */                 ServiceID serviceID = serviceIDService.getServiceID(subConfigurationWrapper1.getProperty(iter2.next()));
/* 154 */                 this.state.serviceIDToAdapter.put(serviceID, adapter);
/* 155 */                 log.debug("... added mapping for serviceID:" + serviceID + " -> " + String.valueOf(adapter));
/*     */               } 
/* 157 */             } catch (Exception e) {
/* 158 */               log.error("... unable to create schema adapter :" + String.valueOf(identifier) + " - exception:" + e, e);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 164 */         if (this.state.serviceIDToAdapter.size() == 0) {
/* 165 */           log.error("... no service adapter mappings defined - perhaps missing configuration entries ?!?");
/*     */         }
/* 167 */         this.state.configHash = configHash(this.state.configKeys);
/*     */       } 
/* 169 */       return this.state;
/*     */     } 
/*     */   }
/*     */   
/*     */   private Map getAdapterMap() {
/* 174 */     return (getState()).serviceIDToAdapter;
/*     */   }
/*     */   
/*     */   private static int configHash(Collection keys) {
/* 178 */     ConfigurationService configService = (ConfigurationService)FrameServiceProvider.getInstance().getService(ConfigurationService.class);
/* 179 */     return ConfigurationUtil.configurationHash((Configuration)configService, keys, ConfigurationUtil.MODE_PREFIXES);
/*     */   }
/*     */   
/*     */   public void reset() {
/* 183 */     synchronized (this.SYNC_STATE) {
/* 184 */       State state = getState();
/* 185 */       if (state != null) {
/* 186 */         for (Iterator<SchemaAdapterSPI> iter = getAdapterMap().values().iterator(); iter.hasNext(); ) {
/* 187 */           SchemaAdapterSPI adapter = iter.next();
/* 188 */           adapter.close();
/*     */         } 
/* 190 */         this.state = null;
/*     */       } 
/* 192 */       this.state = null;
/*     */     } 
/*     */     
/* 195 */     this.observableSupport.notifyObservers(NOTIFICATION_RESET, IObservableSupport.Mode.ASYNCHRONOUS_NOTIFY);
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/* 200 */     getAdapterMap();
/*     */   }
/*     */   
/*     */   public static synchronized ServiceResolution getInstance() {
/* 204 */     if (instance == null) {
/* 205 */       instance = new ServiceResolution();
/*     */     }
/* 207 */     return instance;
/*     */   }
/*     */   
/*     */   protected static Collection getAuthorizedResources(ClientContext context) {
/*     */     try {
/* 212 */       SharedContext sc = context.getSharedContext();
/* 213 */       ACLService aclMI = ACLServiceProvider.getInstance().getService();
/* 214 */       Set resources = aclMI.getAuthorizedResources("Adapter", sc.getUsrGroup2ManufMap(), sc.getCountry());
/* 215 */       return resources;
/* 216 */     } catch (Exception e) {
/* 217 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkServiceID(ClientContext context, ServiceID serviceID) throws Exception {
/* 222 */     Collection acl = getAuthorizedResources(context);
/* 223 */     if (acl != null) {
/* 224 */       Iterator it = acl.iterator();
/* 225 */       while (it.hasNext()) {
/* 226 */         Object resource = it.next();
/* 227 */         if (serviceID.toString().equalsIgnoreCase(resource.toString())) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 234 */     if (ApplicationContext.getInstance().developMode()) {
/* 235 */       log.warn("DEVELOP MODE !!!! allowing access to " + String.valueOf(serviceID));
/*     */       return;
/*     */     } 
/* 238 */     throw new NoServiceIDException();
/*     */   }
/*     */ 
/*     */   
/*     */   private ServiceID getServiceID(AttributeValueMap avMap) throws Exception {
/* 243 */     ServiceID serviceID = null;
/* 244 */     if ((serviceID = (ServiceID)AVUtil.accessValue(avMap, ATTR_SERVICE_ID)) == null) {
/* 245 */       Value value = avMap.getValue(CommonAttribute.VIN);
/* 246 */       if (value == null) {
/* 247 */         throw new RequestException(new VINRequestImpl(null));
/*     */       }
/* 249 */       String vin = (String)((ValueAdapter)value).getAdaptee();
/* 250 */       ServiceIDService service = ServiceIDServiceProvider.getInstance().getService();
/*     */       
/* 252 */       String sessionID = (String)AVUtil.accessValue(avMap, CommonAttribute.SESSION_ID);
/* 253 */       ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/*     */       
/* 255 */       Locale locale = ClientContextProvider.getInstance().getContext(sessionID).getLocale();
/*     */       
/* 257 */       serviceID = service.getServiceID(locale, vin, avMap);
/*     */       
/* 259 */       Value modeValue = avMap.getValue(CommonAttribute.EXECUTION_MODE);
/* 260 */       boolean isCALID = (modeValue != null && modeValue == CommonValue.EXECUTION_MODE_INFO);
/* 261 */       if (System.getProperty("sps-client-mode") != null) {
/* 262 */         isCALID = true;
/*     */       }
/* 264 */       if (!isCALID) {
/* 265 */         checkServiceID(context, serviceID);
/*     */       }
/*     */       
/* 268 */       avMap.set(ATTR_SERVICE_ID, (Value)new ValueAdapter(serviceID));
/*     */     } 
/*     */     
/* 271 */     return serviceID;
/*     */   }
/*     */   
/*     */   private SchemaAdapter getSchemaAdapter(AttributeValueMap avMap) throws Exception {
/* 275 */     ServiceID serviceID = getServiceID(avMap);
/* 276 */     SchemaAdapter retValue = (SchemaAdapter)getAdapterMap().get(serviceID);
/* 277 */     if (retValue == null) {
/* 278 */       throw new UnsupportedVehicleException(serviceID);
/*     */     }
/* 280 */     return retValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection getSchemaAdapters() {
/* 285 */     return new LinkedHashSet(getAdapterMap().values());
/*     */   }
/*     */   
/*     */   public Boolean reprogram(ProgrammingData data, AttributeValueMap avMap) throws RequestException, Exception {
/* 289 */     return getSchemaAdapter(avMap).reprogram(data, avMap);
/*     */   }
/*     */   
/*     */   public ProgrammingData getProgrammingData(AttributeValueMap avMap) throws RequestException, Exception {
/* 293 */     return getSchemaAdapter(avMap).getProgrammingData(avMap);
/*     */   }
/*     */ 
/*     */   
/*     */   public Controller getController(AttributeValueMap avMap) throws RequestException, Exception {
/* 298 */     return getSchemaAdapter(avMap).getController(avMap);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getData(ProgrammingDataUnit dataUnit, AttributeValueMap avMap) throws Exception {
/* 303 */     return getSchemaAdapter(avMap).getData(dataUnit, avMap);
/*     */   }
/*     */   
/*     */   public Collection getCalibrationVerificationNumber(String sessionID, String partNumber) {
/* 307 */     Collection<CVNResultImpl> retValue = new HashSet();
/* 308 */     for (Iterator<SchemaAdapterWrapper> iter = getAdapterMap().values().iterator(); iter.hasNext(); ) {
/* 309 */       SchemaAdapterWrapper wrapper = iter.next();
/* 310 */       String cvn = wrapper.getCalibrationVerificationNumber(sessionID, partNumber);
/* 311 */       if (cvn != null) {
/* 312 */         retValue.add(new CVNResultImpl(cvn, wrapper.getIdentifier()));
/*     */       }
/*     */     } 
/* 315 */     return retValue;
/*     */   }
/*     */   
/*     */   public String getBulletin(String locale, String bulletin) {
/* 319 */     for (Iterator<SchemaAdapterWrapper> iter = getAdapterMap().values().iterator(); iter.hasNext(); ) {
/* 320 */       SchemaAdapterWrapper wrapper = iter.next();
/* 321 */       String html = wrapper.getBulletin(locale, bulletin);
/* 322 */       if (html != null) {
/* 323 */         return html;
/*     */       }
/*     */     } 
/* 326 */     return null;
/*     */   }
/*     */   
/*     */   public String getHTML(String locale, String id) {
/* 330 */     for (Iterator<SchemaAdapterWrapper> iter = getAdapterMap().values().iterator(); iter.hasNext(); ) {
/* 331 */       SchemaAdapterWrapper wrapper = iter.next();
/* 332 */       String html = wrapper.getHTML(locale, id);
/* 333 */       if (html != null) {
/* 334 */         return html;
/*     */       }
/*     */     } 
/* 337 */     return null;
/*     */   }
/*     */   
/*     */   public byte[] getImage(String id) {
/* 341 */     for (Iterator<SchemaAdapterWrapper> iter = getAdapterMap().values().iterator(); iter.hasNext(); ) {
/* 342 */       SchemaAdapterWrapper wrapper = iter.next();
/* 343 */       byte[] img = wrapper.getImage(id);
/* 344 */       if (img != null) {
/* 345 */         return img;
/*     */       }
/*     */     } 
/* 348 */     return null;
/*     */   }
/*     */   
/*     */   public Object getVersionInfo() {
/* 352 */     List<Object> ret = new LinkedList();
/* 353 */     for (Iterator<SchemaAdapter> iter = getSchemaAdapters().iterator(); iter.hasNext(); ) {
/* 354 */       SchemaAdapter adapter = iter.next();
/* 355 */       Object versionInfo = adapter.getVersionInfo();
/* 356 */       if (versionInfo instanceof com.eoos.gm.tis2web.frame.export.common.datatype.DBVersionInformation) {
/* 357 */         ret.add(versionInfo); continue;
/* 358 */       }  if (versionInfo instanceof Collection) {
/* 359 */         ret.addAll((Collection)versionInfo);
/*     */       }
/*     */     } 
/* 362 */     return ret;
/*     */   }
/*     */   
/*     */   public DatabaseInfo getDatabaseInfo(AttributeValueMap avMap, Attribute lastRequestedAttribute) throws Exception {
/* 366 */     return getSchemaAdapter(avMap).getDatabaseInfo(avMap, lastRequestedAttribute);
/*     */   }
/*     */   
/*     */   public void addResetObserver(ResetObserver observer) {
/* 370 */     this.observableSupport.addObserver(observer);
/*     */   }
/*     */   
/*     */   public String getIdentifier(SchemaAdapter adapter) {
/* 374 */     if (adapter instanceof SchemaAdapterWrapper) {
/* 375 */       return ((SchemaAdapterWrapper)adapter).getIdentifier();
/*     */     }
/* 377 */     return String.valueOf("SchemaAdapter@" + System.identityHashCode(adapter));
/*     */   }
/*     */ 
/*     */   
/*     */   public SchemaAdapter getSchemaAdapter(String schemaAdapterIdentifier) {
/* 382 */     for (Iterator<SchemaAdapterWrapper> iter = getSchemaAdapters().iterator(); iter.hasNext(); ) {
/* 383 */       SchemaAdapterWrapper saw = iter.next();
/* 384 */       if (saw.getIdentifier().equals(schemaAdapterIdentifier)) {
/* 385 */         return saw;
/*     */       }
/*     */     } 
/* 388 */     return null;
/*     */   }
/*     */   
/*     */   public Set getCfgProviders() {
/* 392 */     final Set ret = new HashSet();
/*     */     try {
/* 394 */       CollectionUtil.foreach(getSchemaAdapters(), new CollectionUtil.ForeachCallback()
/*     */           {
/*     */             public Object executeOperation(Object item) throws Exception {
/* 397 */               SchemaAdapterWrapper saw = (SchemaAdapterWrapper)item;
/*     */               try {
/* 399 */                 ret.addAll(saw.getCfgProviders());
/* 400 */               } catch (Exception e) {
/* 401 */                 ServiceResolution.log.error("unable to retrieve ConfigDataProviders from adapter " + String.valueOf(saw) + ", skipping - exception:" + e, e);
/*     */               } 
/* 403 */               return null;
/*     */             }
/*     */           });
/*     */     }
/* 407 */     catch (Exception e) {
/* 408 */       throw new RuntimeException(e);
/*     */     } 
/* 410 */     return ret;
/*     */   }
/*     */   
/*     */   public Set getVINResolvers(final ClientContext context) {
/* 414 */     final Set ret = new HashSet();
/*     */     try {
/* 416 */       CollectionUtil.foreach(getSchemaAdapters(), new CollectionUtil.ForeachCallback()
/*     */           {
/*     */             public Object executeOperation(Object item) throws Exception {
/* 419 */               SchemaAdapterWrapper saw = (SchemaAdapterWrapper)item;
/*     */               try {
/* 421 */                 ret.addAll(saw.getVINResolvers(context));
/*     */               }
/* 423 */               catch (Exception e) {
/* 424 */                 ServiceResolution.log.error("unable to retrieve VINResolvers from adapter " + String.valueOf(saw) + ", skipping - exception:" + e, e);
/*     */               } 
/* 426 */               return null;
/*     */             }
/*     */           });
/*     */     }
/* 430 */     catch (Exception e) {
/* 431 */       throw new RuntimeException(e);
/*     */     } 
/* 433 */     return ret;
/*     */   }
/*     */   
/*     */   public static interface ResetObserver {
/*     */     void onReset();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\system\serverside\adapter\ServiceResolution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */