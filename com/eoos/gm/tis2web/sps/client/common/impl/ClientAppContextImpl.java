/*     */ package com.eoos.gm.tis2web.sps.client.common.impl;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.hwk.exception.SystemDriverNotInstalledException;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContext;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientSettings;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ExecutionMode;
/*     */ import com.eoos.gm.tis2web.sps.client.hardwarekey.impl.HardwareKey;
/*     */ import com.eoos.gm.tis2web.sps.client.serveracces.SPSClientFacadeProvider;
/*     */ import com.eoos.gm.tis2web.sps.client.settings.ObservableSubject;
/*     */ import com.eoos.gm.tis2web.sps.client.settings.PersistentClientSettings;
/*     */ import com.eoos.gm.tis2web.sps.client.settings.Registry;
/*     */ import com.eoos.gm.tis2web.sps.client.settings.SettingsObserver;
/*     */ import com.eoos.gm.tis2web.sps.client.settings.impl.ClientSettingsImpl;
/*     */ import com.eoos.gm.tis2web.sps.client.settings.impl.LogHandler;
/*     */ import com.eoos.gm.tis2web.sps.client.settings.impl.PersistentClientSettingsImpl;
/*     */ import com.eoos.gm.tis2web.sps.client.settings.impl.RegistryProviderImpl;
/*     */ import com.eoos.gm.tis2web.sps.client.starter.impl.Starter;
/*     */ import com.eoos.gm.tis2web.sps.client.system.LabelResourceImpl;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.Tool;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.ToolSimpleFactory;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.exceptions.NativeSubsystemException;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.SPSFrame;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.Brand;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.NavigationTableFilter;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.serveraccess.UnprivilegedUserException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClientAppContextImpl
/*     */   implements ClientAppContext
/*     */ {
/*     */   public static interface HWKData
/*     */   {
/*  45 */     public static final HWKData SYSDRV_NOT_INSTALLED = new HWKData()
/*     */       {
/*     */         public String getRepairShopCode() throws SystemDriverNotInstalledException {
/*  48 */           throw new SystemDriverNotInstalledException();
/*     */         }
/*     */         
/*     */         public String getHardwareKey32() throws SystemDriverNotInstalledException {
/*  52 */           throw new SystemDriverNotInstalledException();
/*     */         }
/*     */         
/*     */         public String getHardwareKey() throws SystemDriverNotInstalledException {
/*  56 */           throw new SystemDriverNotInstalledException();
/*     */         }
/*     */       };
/*     */ 
/*     */     
/*     */     String getHardwareKey() throws SystemDriverNotInstalledException;
/*     */     
/*     */     String getHardwareKey32() throws SystemDriverNotInstalledException;
/*     */     
/*     */     String getRepairShopCode() throws SystemDriverNotInstalledException;
/*     */   }
/*     */   
/*     */   public static class HWKDataImpl
/*     */     implements HWKData
/*     */   {
/*     */     private String rsc;
/*     */     private String hwk16;
/*     */     private String hwk32;
/*     */     
/*     */     public HWKDataImpl(String repairShopCode, String hwk16, String hwk32) {
/*  76 */       this.rsc = repairShopCode;
/*  77 */       this.hwk16 = hwk16;
/*  78 */       this.hwk32 = hwk32;
/*     */     }
/*     */     
/*     */     public String getHardwareKey() throws SystemDriverNotInstalledException {
/*  82 */       return this.hwk16;
/*     */     }
/*     */     
/*     */     public String getHardwareKey32() throws SystemDriverNotInstalledException {
/*  86 */       return this.hwk32;
/*     */     }
/*     */     
/*     */     public String getRepairShopCode() throws SystemDriverNotInstalledException {
/*  90 */       return this.rsc;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  95 */   private HWKData hwkData = null;
/*     */   
/*  97 */   private static ClientAppContextImpl instance = null;
/*     */   
/*     */   private boolean isInitialized = false;
/*     */   
/* 101 */   private List supportedBrandKeys = null;
/*     */   
/* 103 */   private List tools = new ArrayList();
/*     */   
/* 105 */   private PersistentClientSettingsImpl persistentSettings = null;
/*     */   
/* 107 */   private LogHandler logHandler = null;
/*     */   
/* 109 */   private Locale locale = null;
/*     */   
/* 111 */   private String sessionID = null;
/*     */   
/* 113 */   private String bacCode = null;
/*     */   
/* 115 */   private String countryCode = null;
/*     */   
/* 117 */   private String defaultMake = "undefined";
/*     */   
/* 119 */   private String clientVersion = "";
/*     */   
/*     */   private boolean isLocalInstallation;
/*     */   
/* 123 */   private String driverJ2534Map = null;
/*     */   
/* 125 */   private String navTableValidMap = null;
/*     */   
/*     */   private boolean testMode = false;
/*     */   
/* 129 */   private Integer toolAutoRetryDelay = null;
/*     */   
/* 131 */   private Integer warrantyCodeList = null;
/*     */   
/*     */   private boolean vci1001Enabled = false;
/*     */   
/* 135 */   private static Logger log = null;
/*     */ 
/*     */   
/* 138 */   private String lcid = null;
/*     */   
/* 140 */   private NavigationTableFilter navTableFilter = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized ClientAppContext getInstance() {
/* 146 */     if (instance == null) {
/* 147 */       instance = new ClientAppContextImpl();
/* 148 */       instance.startup();
/*     */     } 
/* 150 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void startup() {
/* 158 */     this.persistentSettings = new PersistentClientSettingsImpl();
/* 159 */     this.persistentSettings.loadSettings();
/* 160 */     this.persistentSettings.createSettingsDir();
/* 161 */     this.logHandler = new LogHandler();
/* 162 */     this.logHandler.init();
/* 163 */     this.persistentSettings.setLogger();
/* 164 */     this.logHandler.update((ObservableSubject)this.persistentSettings, "Initial log configuration");
/* 165 */     this.persistentSettings.registerObserver((SettingsObserver)this.logHandler);
/* 166 */     log = Logger.getLogger(ClientAppContextImpl.class);
/* 167 */     if (!isStartAllowed()) {
/* 168 */       getStartupProperties();
/* 169 */       LabelResourceImpl labelResourceImpl = new LabelResourceImpl(this.locale);
/* 170 */       String message = labelResourceImpl.getMessage(this.locale, "sps.second-instance");
/* 171 */       String title = labelResourceImpl.getLabel(this.locale, "app.title");
/* 172 */       SPSFrame.displayErrorMessage(title, message);
/* 173 */       System.exit(-1);
/*     */     } 
/*     */     
/* 176 */     String testModeStr = getClientSettings().getProperty("debug.testmode");
/* 177 */     if (testModeStr != null && testModeStr.compareToIgnoreCase("true") == 0) {
/* 178 */       this.testMode = true;
/*     */     }
/* 180 */     String toolAutoRetry = getClientSettings().getProperty("tool.autoretry.delay");
/* 181 */     if (toolAutoRetry != null) {
/*     */       try {
/* 183 */         this.toolAutoRetryDelay = Integer.valueOf(toolAutoRetry);
/* 184 */       } catch (Exception e) {
/* 185 */         log.error("Invalid tool auto-retry delay value: " + toolAutoRetry);
/*     */       } 
/* 187 */       log.debug("Tool auto-retry delay: " + this.toolAutoRetryDelay);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Integer getToolAutoRetryDelay() {
/* 192 */     return this.toolAutoRetryDelay;
/*     */   }
/*     */   
/*     */   public ClientSettings getClientSettings() {
/* 196 */     return (ClientSettings)new ClientSettingsImpl((PersistentClientSettings)this.persistentSettings);
/*     */   }
/*     */   
/*     */   public Locale getLocale() {
/* 200 */     return this.locale;
/*     */   }
/*     */   
/*     */   public String getClientVersion() {
/* 204 */     return this.clientVersion;
/*     */   }
/*     */   
/*     */   public String getDefaultMake() {
/* 208 */     return this.defaultMake;
/*     */   }
/*     */   
/*     */   public String getSessionID() {
/* 212 */     return this.sessionID;
/*     */   }
/*     */   
/*     */   public String getBACCode() {
/* 216 */     return this.bacCode;
/*     */   }
/*     */   
/*     */   public String getJ2534DriverUpdateInfo() {
/* 220 */     return this.driverJ2534Map;
/*     */   }
/*     */   
/*     */   public List getSupportedBrandKeys() {
/* 224 */     return new ArrayList(this.supportedBrandKeys);
/*     */   }
/*     */   
/*     */   public List getSupportedBrands() {
/* 228 */     List<Brand> result = new ArrayList();
/* 229 */     Iterator<String> it = this.supportedBrandKeys.iterator();
/* 230 */     while (it.hasNext()) {
/* 231 */       result.add(Brand.getInstance(it.next()));
/*     */     }
/* 233 */     return result;
/*     */   }
/*     */   
/*     */   public Integer getWarrantyCodeList() {
/* 237 */     return this.warrantyCodeList;
/*     */   }
/*     */   
/*     */   public boolean isVCI1001Enabled() {
/* 241 */     return this.vci1001Enabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() throws Exception {
/* 249 */     if (!this.isInitialized) {
/* 250 */       getStartupProperties();
/*     */       
/* 252 */       if (getClientSettings().toolsAreLocal()) {
/* 253 */         if (!Starter.getInstance().setEnvironment()) {
/* 254 */           log.error("setEnvironment failed");
/* 255 */           throw new NativeSubsystemException();
/*     */         } 
/* 257 */         getLocalTools();
/*     */         try {
/* 259 */           String enabled = getClientSettings().getProperty("remote.enabled");
/* 260 */           if (enabled != null && enabled.compareToIgnoreCase("TRUE") == 0) {
/* 261 */             throw new IllegalStateException();
/*     */           }
/* 263 */         } catch (Exception e) {
/* 264 */           log.error(e);
/*     */         } 
/*     */       } else {
/* 267 */         throw new IllegalStateException();
/*     */       } 
/* 269 */       this.isInitialized = true;
/*     */     } else {
/* 271 */       log.debug("Multiple init() calls");
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateJ2534Tools() {
/* 276 */     ArrayList<Tool> defaultTools = new ArrayList();
/* 277 */     Iterator<Tool> it = this.tools.iterator();
/* 278 */     while (it.hasNext()) {
/* 279 */       Tool nextTool = it.next();
/* 280 */       if (nextTool instanceof com.eoos.gm.tis2web.sps.client.tool.passthru.j2534.J2534Tool) {
/*     */         continue;
/*     */       }
/* 283 */       defaultTools.add(nextTool);
/*     */     } 
/*     */     
/* 286 */     this.tools = new ArrayList();
/* 287 */     addJ2534Tools();
/* 288 */     this.tools.addAll(defaultTools);
/*     */   }
/*     */   
/*     */   public String getHomeDir() {
/* 292 */     return System.getProperty("user.home") + System.getProperty("file.separator") + "sps";
/*     */   }
/*     */   
/*     */   public List getToolNames() {
/* 296 */     List<String> result = new ArrayList();
/* 297 */     Iterator<Tool> it = this.tools.iterator();
/* 298 */     while (it.hasNext()) {
/* 299 */       Tool tool = it.next();
/* 300 */       String name = tool.getId();
/* 301 */       if (name != null) {
/* 302 */         result.add(name);
/*     */       }
/*     */     } 
/* 305 */     return result;
/*     */   }
/*     */   
/*     */   public List getTools() {
/* 309 */     return this.tools;
/*     */   }
/*     */ 
/*     */   
/*     */   public Tool getTool(String name) {
/* 314 */     Tool result = null;
/* 315 */     Iterator<Tool> it = this.tools.iterator();
/* 316 */     while (it.hasNext()) {
/* 317 */       Tool tool = it.next();
/* 318 */       if (tool.getId().compareTo(name) == 0) {
/* 319 */         result = tool;
/*     */         break;
/*     */       } 
/*     */     } 
/* 323 */     return result;
/*     */   }
/*     */   
/*     */   public void registerSettingsObserver(SettingsObserver o) {
/* 327 */     this.persistentSettings.registerObserver(o);
/*     */   }
/*     */   
/*     */   public boolean removeSettingsObserver(SettingsObserver o) {
/* 331 */     return this.persistentSettings.removeObserver(o);
/*     */   }
/*     */   
/*     */   public synchronized void filterTools(List toolNames) {
/* 335 */     List<Tool> finalTools = new ArrayList();
/* 336 */     Iterator<String> it = toolNames.iterator();
/* 337 */     while (it.hasNext()) {
/* 338 */       Tool tool = getTool(it.next());
/* 339 */       if (tool != null) {
/* 340 */         finalTools.add(tool);
/*     */       }
/*     */     } 
/* 343 */     this.tools = finalTools;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void getStartupProperties() {
/* 350 */     StartupPropertiesImpl stProp = new StartupPropertiesImpl();
/* 351 */     this.clientVersion = stProp.getVersion();
/* 352 */     this.isLocalInstallation = "local".equals(stProp.getServerInstallationType());
/* 353 */     this.locale = stProp.getLocale();
/*     */ 
/*     */     
/* 356 */     LabelResourceImpl lresource = new LabelResourceImpl(this.locale);
/* 357 */     this.lcid = lresource.getLCID(this.locale, stProp.getLanguage());
/*     */     
/* 359 */     this.sessionID = stProp.getSessionID();
/* 360 */     this.defaultMake = stProp.getDefaultMake();
/* 361 */     this.supportedBrandKeys = stProp.getSupportedBrands();
/* 362 */     this.bacCode = stProp.getBACCode();
/* 363 */     this.countryCode = stProp.getCountryCode();
/* 364 */     this.driverJ2534Map = stProp.getJ2534();
/* 365 */     this.navTableValidMap = stProp.getNavtableValidationMap();
/* 366 */     String wcc = stProp.getWarrantyCodeList();
/* 367 */     if (wcc != null && !"0".equals(wcc.trim())) {
/* 368 */       this.warrantyCodeList = Integer.valueOf(wcc);
/*     */     }
/* 370 */     String vci1001Support = stProp.getVCI1001Enabled();
/* 371 */     this.vci1001Enabled = (vci1001Support != null && "enabled".equalsIgnoreCase(vci1001Support));
/* 372 */     log.info("Locale: " + this.locale);
/* 373 */     log.info("SessionID: " + this.sessionID);
/* 374 */     log.info("Default make: " + this.defaultMake);
/* 375 */     log.info("Supported brands: " + this.supportedBrandKeys.toString());
/* 376 */     log.info("BAC Code: " + this.bacCode);
/*     */   }
/*     */   
/*     */   private HWKData getHWKData() {
/* 380 */     synchronized (HWKData.class) {
/* 381 */       if (System.getProperty("sps-client-mode") != null) {
/* 382 */         this.hwkData = new HWKDataImpl("RSC", "HWK16", "HWK32");
/* 383 */       } else if (this.hwkData == null && this.isLocalInstallation) {
/*     */         try {
/* 385 */           String hwid = SPSClientFacadeProvider.getInstance().getFacade().getHardwareID();
/* 386 */           this.hwkData = new HWKDataImpl(hwid, hwid, hwid);
/* 387 */         } catch (Exception e) {
/* 388 */           log.debug("failed to acquire hardware-id");
/* 389 */           this.hwkData = new HWKDataImpl(null, null, null);
/*     */         } 
/* 391 */       } else if (this.hwkData == null && !this.isLocalInstallation) {
/* 392 */         String hwk = null;
/* 393 */         String hwk16 = null;
/* 394 */         String hwk32 = null;
/* 395 */         boolean driverNotInstalled = false;
/*     */         try {
/* 397 */           hwk = HardwareKey.getInstance().getHWK();
/* 398 */           hwk16 = HardwareKey.getInstance().getEncodedHWK_16();
/* 399 */           hwk32 = HardwareKey.getInstance().getEncodedHWK_32();
/* 400 */         } catch (SystemDriverNotInstalledException e) {
/* 401 */           log.debug("unable to read hwk, system driver is not installed");
/* 402 */           driverNotInstalled = true;
/*     */         } 
/*     */         
/* 405 */         if (hwk != null) {
/* 406 */           this.hwkData = new HWKDataImpl(hwk, hwk16, hwk32);
/*     */         } else {
/* 408 */           log.debug("could not read hwk, trying to retrieve replacement (privileged users)");
/*     */           try {
/* 410 */             String hwkReplacement = SPSClientFacadeProvider.getInstance().getFacade().getHWKReplacement();
/* 411 */             this.hwkData = new HWKDataImpl(hwkReplacement, hwkReplacement, hwkReplacement);
/* 412 */           } catch (UnprivilegedUserException e) {
/* 413 */             log.debug("...unable to retrieve hwk replacement, user not privileged");
/* 414 */             if (driverNotInstalled) {
/* 415 */               this.hwkData = HWKData.SYSDRV_NOT_INSTALLED;
/*     */             } else {
/* 417 */               this.hwkData = new HWKDataImpl(null, null, null);
/*     */             } 
/*     */           } 
/*     */         } 
/* 421 */         HardwareKey.getInstance().releaseLibraries();
/*     */       } 
/* 423 */       return this.hwkData;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getHardwareKey() throws SystemDriverNotInstalledException {
/* 428 */     return getHWKData().getHardwareKey();
/*     */   }
/*     */   
/*     */   public String getHardwareKey32() throws SystemDriverNotInstalledException {
/* 432 */     return getHWKData().getHardwareKey32();
/*     */   }
/*     */   
/*     */   public String getRepairShopCode() {
/*     */     try {
/* 437 */       return getHWKData().getRepairShopCode();
/* 438 */     } catch (SystemDriverNotInstalledException e) {
/* 439 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isStartAllowed() {
/* 444 */     boolean result = true;
/*     */     try {
/* 446 */       if (!ExecutionMode.isLoadTest()) {
/* 447 */         result = Starter.getInstance().isStartAllowed();
/*     */       }
/* 449 */     } catch (Exception e) {
/* 450 */       log.error("Cannot check for 1st instance. Continuing startup process.");
/*     */     } 
/* 452 */     log.debug("Single instance check returned: " + result);
/* 453 */     return result;
/*     */   }
/*     */   
/*     */   public boolean testMode() {
/* 457 */     return this.testMode;
/*     */   }
/*     */   
/*     */   public boolean DTCDebugMode() {
/* 461 */     boolean result = false;
/* 462 */     String dtcDebugStr = getClientSettings().getProperty("debug.dtc");
/* 463 */     if (dtcDebugStr != null && dtcDebugStr.compareToIgnoreCase("true") == 0) {
/* 464 */       result = true;
/*     */     }
/* 466 */     return result;
/*     */   }
/*     */   
/*     */   public boolean DTCTestMode() {
/* 470 */     boolean result = false;
/* 471 */     String dtcTestModeStr = getClientSettings().getProperty("debug.testmode.dtc");
/* 472 */     if (dtcTestModeStr != null && dtcTestModeStr.compareToIgnoreCase("true") == 0) {
/* 473 */       result = true;
/*     */     }
/* 475 */     return result;
/*     */   }
/*     */   
/*     */   private void getLocalTools() {
/* 479 */     addJ2534Tools();
/* 480 */     addDefaultTools();
/*     */   }
/*     */   
/*     */   private List getJ2534Tools() {
/* 484 */     List result = new ArrayList();
/* 485 */     Registry registry = (new RegistryProviderImpl()).getRegistry();
/* 486 */     if (registry != null) {
/* 487 */       this.tools.addAll(registry.getLocalTools());
/* 488 */       log.info("Total number of J2534 tools: " + this.tools.size());
/*     */     } else {
/* 490 */       log.error("No local registry found");
/*     */     } 
/* 492 */     return result;
/*     */   }
/*     */   
/*     */   private void addJ2534Tools() {
/* 496 */     this.tools.addAll(getJ2534Tools());
/*     */   }
/*     */   
/*     */   private void addDefaultTools() {
/* 500 */     this.tools.add(ToolSimpleFactory.getInstance().createTool("PT_LEGACY", null));
/* 501 */     this.tools.add(ToolSimpleFactory.getInstance().createTool("T2_REMOTE", null));
/* 502 */     this.tools.add(ToolSimpleFactory.getInstance().createTool("TEST_DRIVER", null));
/*     */   }
/*     */   
/*     */   public String getNavTableValidationMap() {
/* 506 */     return this.navTableValidMap;
/*     */   }
/*     */   
/*     */   public boolean isLocalServerInstallation() {
/* 510 */     return this.isLocalInstallation;
/*     */   }
/*     */   
/*     */   public String getCountryCode() {
/* 514 */     return this.countryCode;
/*     */   }
/*     */   
/*     */   public String getLCID() {
/* 518 */     return this.lcid;
/*     */   }
/*     */   
/*     */   public NavigationTableFilter getNavigationTableFilter() {
/* 522 */     if (this.navTableFilter == null) {
/* 523 */       this.navTableFilter = NavigationTableFilter.fromExternal(StartupPropertiesImpl.getNavTableFilter());
/*     */     }
/* 525 */     return this.navTableFilter;
/*     */   }
/*     */   
/*     */   public boolean DTCUploadEnabled() {
/* 529 */     return !Boolean.getBoolean("dtc.upload.disabled");
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\common\impl\ClientAppContextImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */