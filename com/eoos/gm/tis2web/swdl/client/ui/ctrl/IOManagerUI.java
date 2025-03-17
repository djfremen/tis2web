/*     */ package com.eoos.gm.tis2web.swdl.client.ui.ctrl;
/*     */ 
/*     */ import com.eoos.gm.tis2web.swdl.client.ctrl.IOManager;
/*     */ import com.eoos.gm.tis2web.swdl.client.ctrl.SDAppLoader;
/*     */ import com.eoos.gm.tis2web.swdl.client.ctrl.SDAppLoaderImpl;
/*     */ import com.eoos.gm.tis2web.swdl.client.ctrl.SDFileSel;
/*     */ import com.eoos.gm.tis2web.swdl.client.ctrl.SDFileSelImpl;
/*     */ import com.eoos.gm.tis2web.swdl.client.ctrl.SWCacheManager;
/*     */ import com.eoos.gm.tis2web.swdl.client.ctrl.ServerRequestor;
/*     */ import com.eoos.gm.tis2web.swdl.client.model.ApplicationInfo;
/*     */ import com.eoos.gm.tis2web.swdl.client.model.DeviceInfo;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.SDNotifHandler;
/*     */ import com.eoos.gm.tis2web.swdl.client.util.DomainUtil;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.ServerError;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.application.Version;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.dtc.TroubleCode;
/*     */ import com.eoos.gm.tis2web.swdl.common.system.Command;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import com.eoos.util.ZipUtil;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.PropertyConfigurator;
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
/*     */ public class IOManagerUI
/*     */ {
/*  39 */   private static IOManagerUI instance = null;
/*     */   
/*  41 */   private Logger log = Logger.getLogger(IOManagerUI.class);
/*     */   
/*  43 */   private IOSettings devSettings = new IODevSettingsImpl();
/*     */   
/*  45 */   private IOSettings settings = new IOSettingsImpl();
/*     */   
/*  47 */   private IOManager managerDrv = new IOManager();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized IOManagerUI getInstance() {
/*  54 */     if (instance == null) {
/*  55 */       instance = new IOManagerUI();
/*     */     }
/*  57 */     return instance;
/*     */   }
/*     */   
/*     */   public boolean initDevices() {
/*  61 */     Command command = new Command(1);
/*     */     
/*     */     try {
/*  64 */       Object obj = ServerRequestor.getInstance().sendRequest(command);
/*  65 */       if (obj != null && obj instanceof ServerError) {
/*  66 */         this.log.error("Server error when init devices, errNR: " + ((ServerError)obj).getError());
/*  67 */         return false;
/*     */       } 
/*  69 */       if (obj != null && obj instanceof Set) {
/*  70 */         SDCurrentContext.getInstance().setDevices((Set)obj);
/*  71 */         return true;
/*     */       } 
/*  73 */       this.log.error("Incorect object: when get devices from server.");
/*  74 */       return false;
/*     */     }
/*  76 */     catch (Exception e) {
/*  77 */       this.log.error("Exception when get the devices from Server, " + e, e);
/*  78 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void saveDevSettings(String device, String sett) {
/*  83 */     SDCurrentContext.getInstance().setDeviceSettings(device, sett);
/*     */   }
/*     */   
/*     */   public void saveLogSettings(boolean isLogging, boolean isDebug, String logFileName) {
/*  87 */     if (isLogging) {
/*  88 */       SDCurrentContext.getInstance().setDeviceSettings("Logging", "ERROR, CONSOLE, STDLOG");
/*  89 */       if (isDebug)
/*  90 */         SDCurrentContext.getInstance().setDeviceSettings("Logging", "DEBUG, CONSOLE, STDLOG"); 
/*     */     } else {
/*  92 */       SDCurrentContext.getInstance().setDeviceSettings("Logging", "OFF, CONSOLE, STDLOG");
/*  93 */     }  if (isDebug) {
/*  94 */       SDCurrentContext.getInstance().setDeviceSettings("LogDebug", "YES");
/*     */     } else {
/*  96 */       SDCurrentContext.getInstance().setDeviceSettings("LogDebug", "NO");
/*  97 */     }  SDCurrentContext.getInstance().setDeviceSettings("LogFileName", logFileName);
/*     */     
/*  99 */     updateLogSettings();
/*     */   }
/*     */   
/*     */   public static void updateLogSettings() {
/* 103 */     SDCurrentContext currCon = SDCurrentContext.getInstance();
/* 104 */     String log = (String)currCon.getSettings().get("Logging");
/* 105 */     AppProperties p = currCon.getLogProp();
/* 106 */     p.put("log4j.rootCategory", log);
/* 107 */     if (log.indexOf("OFF") != -1) {
/* 108 */       p.remove("log4j.appender.STDLOG.File");
/* 109 */       p.put("log4j.appender.STDLOG", "org.apache.log4j.ConsoleAppender");
/*     */     } else {
/* 111 */       p.put("log4j.appender.STDLOG", "org.apache.log4j.RollingFileAppender");
/* 112 */       p.put("log4j.appender.STDLOG.File", currCon.getSettings().get("LogFileName"));
/* 113 */       AppProperties logProp = new AppProperties();
/*     */       try {
/* 115 */         logProp.load("com/eoos/gm/tis2web/swdl/client/log4j.properties");
/* 116 */         p.put("log4j.appender.STDLOG.MaxFileSize", logProp.get("log4j.appender.STDLOG.MaxFileSize"));
/* 117 */         p.put("log4j.appender.STDLOG.MaxBackupIndex", logProp.get("log4j.appender.STDLOG.MaxBackupIndex"));
/* 118 */       } catch (Exception e) {}
/*     */     } 
/*     */     
/*     */     try {
/* 122 */       PropertyConfigurator.configure(p);
/* 123 */     } catch (Exception e) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateSettings() {
/* 128 */     this.devSettings.save("configuration.properties", SDCurrentContext.getInstance().getSettings());
/*     */   }
/*     */   
/*     */   public void loadSettings() {
/* 132 */     AppProperties p = SDCurrentContext.getInstance().getLogProp();
/*     */     try {
/* 134 */       p.load("com/eoos/gm/tis2web/swdl/client/log4j.properties");
/* 135 */       PropertyConfigurator.configure(p);
/* 136 */     } catch (Exception e) {}
/*     */     
/* 138 */     this.devSettings.load("configuration.properties", SDCurrentContext.getInstance().getSettings());
/* 139 */     updateLogSettings();
/* 140 */     this.settings.load("com/eoos/gm/tis2web/swdl/client/configuration.properties", SDCurrentContext.getInstance().getLocSettings());
/*     */   }
/*     */   
/*     */   public boolean initDevice(String device) {
/* 144 */     if (!this.managerDrv.selectDeviceDriver(device) || !this.managerDrv.initDevice((String)SDCurrentContext.getInstance().getSettings().get(device))) {
/* 145 */       return false;
/*     */     }
/* 147 */     return true;
/*     */   }
/*     */   
/*     */   public int getDeviceInfo() {
/* 151 */     DeviceInfo inf = this.managerDrv.getDeviceInfo();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 156 */     SDCurrentContext.getInstance().setDeviceInfo(inf);
/* 157 */     if (inf == null)
/* 158 */       return -1; 
/* 159 */     if (inf.EmptyCard())
/* 160 */       return -2; 
/* 161 */     return 3;
/*     */   }
/*     */   
/*     */   public int getVersDisscution() {
/* 165 */     Version currVers = (Version)SDCurrentContext.getInstance().getDeviceInfo().getVersionObject();
/* 166 */     Version newVers = (Version)SDCurrentContext.getInstance().getNewAppInfo().getVersionObject();
/* 167 */     DeviceInfo currDev = SDCurrentContext.getInstance().getDeviceInfo();
/* 168 */     String currMode = SDCurrentContext.getInstance().getSelectedMode();
/* 169 */     if (newVers.getSize().longValue() < 32L && currDev.isStrataCard()) {
/* 170 */       String addInfo = newVers.getAdditionalInfo();
/* 171 */       if (addInfo != null) {
/* 172 */         addInfo = addInfo.toUpperCase(Locale.ENGLISH);
/* 173 */         if (addInfo.indexOf("SUPPORTS_ALL_CARD_TYPES") == -1) {
/* 174 */           return 6;
/*     */         }
/*     */       } else {
/* 177 */         return 6;
/*     */       } 
/*     */     } 
/* 180 */     if (currDev.getCardSize() < newVers.getSize().longValue()) {
/* 181 */       return 4;
/*     */     }
/* 183 */     if (currMode.compareTo("Standard") == 0 && (currVers == null || currVers.getDate().longValue() >= newVers.getDate().longValue())) {
/* 184 */       return 0;
/*     */     }
/*     */     
/* 187 */     return 3;
/*     */   }
/*     */   
/*     */   public int getVersionDisscution() {
/* 191 */     Version newVers = (Version)SDCurrentContext.getInstance().getNewAppInfo().getVersionObject();
/* 192 */     DeviceInfo currDev = SDCurrentContext.getInstance().getDeviceInfo();
/* 193 */     String currMode = SDCurrentContext.getInstance().getSelectedMode();
/* 194 */     if (newVers.getSize().longValue() < 32L && currDev.isStrataCard()) {
/* 195 */       String addInfo = newVers.getAdditionalInfo();
/* 196 */       if (addInfo != null) {
/* 197 */         addInfo = addInfo.toUpperCase(Locale.ENGLISH);
/* 198 */         if (addInfo.indexOf("SUPPORTS_ALL_CARD_TYPES") == -1) {
/* 199 */           return 6;
/*     */         }
/*     */       } else {
/* 202 */         return 6;
/*     */       } 
/*     */     } 
/* 205 */     if (currDev.getCardSize() < newVers.getSize().longValue()) {
/* 206 */       return 4;
/*     */     }
/* 208 */     if (currMode.compareTo("Standard") == 0 && 
/* 209 */       currDev != null && newVers != null && 
/* 210 */       currDev.getVersion().compareTo(newVers.getNumber()) >= 0) {
/* 211 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 216 */     return 3;
/*     */   }
/*     */   
/*     */   public int getNewAppVers() {
/* 220 */     DeviceInfo currApp = SDCurrentContext.getInstance().getDeviceInfo();
/* 221 */     Command command = new Command(5);
/* 222 */     command.addParameter("device", SDCurrentContext.getInstance().getSelectedTool());
/* 223 */     command.addParameter("applicationdesc", currApp.getAppName());
/* 224 */     command.addParameter("languageid", currApp.getLanguage());
/*     */     
/*     */     try {
/* 227 */       Object obj = ServerRequestor.getInstance().sendRequest(command);
/* 228 */       if (obj != null && obj instanceof ServerError) {
/* 229 */         (SDCurrentContext.getInstance()).srvError = (ServerError)obj;
/* 230 */         this.log.error("Server error when get the new app. vers., erNR: " + (SDCurrentContext.getInstance()).srvError.getError());
/* 231 */         return -4;
/*     */       } 
/* 233 */       if (obj != null && obj instanceof Version) {
/* 234 */         SDCurrentContext.getInstance().setNewAppInfo(DomainUtil.Version2DeviceInfo((Version)obj));
/* 235 */         return getVersDisscution();
/*     */       } 
/* 237 */       this.log.error("Incorect object: when get the newest application from server.");
/* 238 */       return -1;
/*     */     }
/* 240 */     catch (Exception e) {
/* 241 */       this.log.error("Exception when get the newest application from Server, " + e, e);
/* 242 */       return -1;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getNewAppVersion() {
/* 247 */     DeviceInfo currApp = SDCurrentContext.getInstance().getDeviceInfo();
/* 248 */     Command command = new Command(12);
/* 249 */     command.addParameter("device", SDCurrentContext.getInstance().getSelectedTool());
/* 250 */     command.addParameter("applicationdesc", currApp.getAppName());
/* 251 */     command.addParameter("versionno", currApp.getVersion());
/* 252 */     command.addParameter("languageid", currApp.getLanguage());
/*     */     
/*     */     try {
/* 255 */       Object obj = ServerRequestor.getInstance().sendRequest(command);
/* 256 */       if (obj != null && obj instanceof ServerError) {
/* 257 */         if (((ServerError)obj).getError() == 5) {
/* 258 */           return -3;
/*     */         }
/* 260 */         (SDCurrentContext.getInstance()).srvError = (ServerError)obj;
/* 261 */         this.log.error("Server error when get the new app. vers., erNR: " + (SDCurrentContext.getInstance()).srvError.getError());
/* 262 */         return -4;
/*     */       } 
/* 264 */       if (obj != null && obj instanceof Version) {
/* 265 */         SDCurrentContext.getInstance().setNewAppInfo(DomainUtil.Version2DeviceInfo((Version)obj));
/* 266 */         return getVersionDisscution();
/*     */       } 
/* 268 */       this.log.error("Incorect object: when get the newest application from server.");
/* 269 */       return -1;
/*     */     }
/* 271 */     catch (Exception e) {
/* 272 */       this.log.error("Exception when get the newest application from Server, " + e, e);
/* 273 */       return -1;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getCurrVersion() {
/* 278 */     DeviceInfo currApp = SDCurrentContext.getInstance().getDeviceInfo();
/* 279 */     if (currApp == null) {
/* 280 */       return -1;
/*     */     }
/* 282 */     Command command = new Command(9);
/* 283 */     command.addParameter("device", SDCurrentContext.getInstance().getSelectedTool());
/* 284 */     command.addParameter("applicationdesc", currApp.getAppName());
/* 285 */     command.addParameter("versionno", currApp.getVersion());
/* 286 */     command.addParameter("languageid", currApp.getLanguage());
/*     */     
/*     */     try {
/* 289 */       Object obj = ServerRequestor.getInstance().sendRequest(command);
/* 290 */       if (obj == null) {
/* 291 */         return -1;
/*     */       }
/* 293 */       if (obj instanceof ServerError) {
/* 294 */         if (((ServerError)obj).getError() == 5) {
/* 295 */           return -3;
/*     */         }
/* 297 */         (SDCurrentContext.getInstance()).srvError = (ServerError)obj;
/* 298 */         this.log.error("Server error when get the current version, errNR: " + (SDCurrentContext.getInstance()).srvError.getError());
/* 299 */         return -4;
/*     */       } 
/* 301 */       if (obj instanceof Version) {
/* 302 */         SDCurrentContext.getInstance().setDeviceInfo(DomainUtil.Version2DeviceInfo((Version)obj));
/* 303 */         return 5;
/*     */       } 
/* 305 */       this.log.error("Incorect object: when get the current application from server.");
/* 306 */       return -1;
/*     */     }
/* 308 */     catch (Exception e) {
/* 309 */       this.log.error("Exception when get the current application from Server, " + e, e);
/* 310 */       return -1;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getLanguageObj() {
/* 315 */     DeviceInfo currApp = SDCurrentContext.getInstance().getDeviceInfo();
/* 316 */     if (currApp == null) {
/* 317 */       return -1;
/*     */     }
/* 319 */     Command command = new Command(10);
/* 320 */     command.addParameter("languageid", currApp.getLanguage());
/*     */     
/*     */     try {
/* 323 */       Object obj = ServerRequestor.getInstance().sendRequest(command);
/* 324 */       if (obj == null) {
/* 325 */         return -1;
/*     */       }
/* 327 */       if (obj instanceof ServerError) {
/* 328 */         (SDCurrentContext.getInstance()).srvError = (ServerError)obj;
/* 329 */         this.log.error("Server error when get the lang. obj., errNR: " + (SDCurrentContext.getInstance()).srvError.getError());
/* 330 */         return -4;
/*     */       } 
/* 332 */       if (obj instanceof com.eoos.gm.tis2web.swdl.common.domain.application.Language) {
/* 333 */         SDCurrentContext.getInstance().getDeviceInfo().setLanguageObj(obj);
/* 334 */         return 5;
/*     */       } 
/* 336 */       this.log.error("Incorect object: when get the current application language from server.");
/* 337 */       return -1;
/*     */     }
/* 339 */     catch (Exception e) {
/* 340 */       this.log.error("Exception when get the current application language from Server, " + e, e);
/* 341 */       return -1;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean StartDownload(SDNotifHandler notifHandler) {
/*     */     try {
/* 347 */       SDAppLoaderImpl sDAppLoaderImpl = new SDAppLoaderImpl(SDCurrentContext.getInstance().getNewAppInfo());
/* 348 */       SDFileSelImpl sDFileSelImpl = new SDFileSelImpl((SDAppLoader)sDAppLoaderImpl);
/* 349 */       return this.managerDrv.StartDownload((ApplicationInfo)SDCurrentContext.getInstance().getNewAppInfo(), sDAppLoaderImpl.getFilesInfo(), (SDFileSel)sDFileSelImpl, notifHandler, true);
/* 350 */     } catch (Exception e) {
/* 351 */       this.log.error("Exception when start download to device, " + e, e);
/*     */       
/* 353 */       return false;
/*     */     } 
/*     */   }
/*     */   public void initComPorts() {
/* 357 */     String[] comPorts = this.managerDrv.getComPorts();
/* 358 */     SDCurrentContext.getInstance().setComPorts(comPorts);
/*     */   }
/*     */   
/*     */   public boolean isComPortInstalled(String dev) {
/* 362 */     String[] comPorts = SDCurrentContext.getInstance().getComPorts();
/* 363 */     String devPort = SDCurrentContext.getInstance().getCurrDevicePort(dev);
/* 364 */     if (comPorts != null) {
/* 365 */       for (int i = 0; i < comPorts.length; i++) {
/* 366 */         if (comPorts[i].compareTo(devPort) == 0)
/* 367 */           return true; 
/*     */       } 
/* 369 */       if (devPort.compareTo("AUTO") == 0)
/* 370 */         return true; 
/*     */     } 
/* 372 */     return false;
/*     */   }
/*     */   
/*     */   public String TestDevice(String initParam) {
/* 376 */     if (!this.managerDrv.selectDeviceDriver(SDCurrentContext.getInstance().getCurrDevice().getDescription()))
/* 377 */       return "IDS_ERR_CONN_FAILED"; 
/* 378 */     switch (this.managerDrv.TestDeviceDriver(initParam)) {
/*     */       case 3:
/* 380 */         return "IDS_ERR_CONN_FAILED";
/*     */       case 5:
/* 382 */         return "IDS_ERR_T1_END_PROTOCOL";
/*     */       case 6:
/* 384 */         return "IDS_ERR_T1_PROTOCOL_NSTARTED";
/*     */       case 4:
/* 386 */         return "IDS_ERR_T1_START_PROTOCOL";
/*     */       case 2:
/* 388 */         return "IDS_ERR_NO_TECH_COMM";
/*     */       case 1:
/* 390 */         return "IDS_ERR_SD_FAILED";
/*     */       case 0:
/* 392 */         return "IDS_TEST_SUCCESSFUL";
/*     */     } 
/* 394 */     return "IDS_ERR_UNKNOWN";
/*     */   }
/*     */   
/*     */   public int getApplications() {
/* 398 */     Command command = new Command(2);
/* 399 */     command.addParameter("device", SDCurrentContext.getInstance().getSelectedTool());
/*     */     
/*     */     try {
/* 402 */       Object obj = ServerRequestor.getInstance().sendRequest(command);
/* 403 */       if (obj != null && obj instanceof ServerError) {
/* 404 */         (SDCurrentContext.getInstance()).srvError = (ServerError)obj;
/* 405 */         this.log.error("Server error when get applications, errNR: " + (SDCurrentContext.getInstance()).srvError.getError());
/* 406 */         return -4;
/*     */       } 
/* 408 */       if (obj != null && obj instanceof Set) {
/* 409 */         SDCurrentContext.getInstance().setApplications((Set)obj);
/* 410 */         return 5;
/*     */       } 
/* 412 */       this.log.error("Incorect object: when get applications from server.");
/* 413 */       return -1;
/*     */     }
/* 415 */     catch (Exception e) {
/* 416 */       this.log.error("Exception when get the applications from Server, " + e, e);
/* 417 */       return -1;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void DTCUpload() {
/*     */     try {
/* 423 */       String bacCode = System.getProperty("bac.code");
/* 424 */       String countryCode = System.getProperty("country.code");
/* 425 */       this.log.info("dealer code:" + String.valueOf(bacCode));
/* 426 */       if (bacCode == null || bacCode.compareTo("{BACCODE}") == 0 || bacCode.length() < 11) {
/* 427 */         this.log.info("skipping dtc upload due to missing or invalid dealer code");
/*     */         return;
/*     */       } 
/* 430 */       SWCacheManager cacheMan = new SWCacheManager();
/* 431 */       this.log.error("Get the DTCData from device");
/* 432 */       byte[] buff = this.managerDrv.DTCUpload(bacCode);
/* 433 */       if (buff == null) {
/* 434 */         this.log.error("No DTC data available");
/*     */         return;
/*     */       } 
/*     */       try {
/* 438 */         this.log.error("Compress DTCData (ZIP)");
/* 439 */         byte[] zbuff = ZipUtil.gzip(buff);
/* 440 */         buff = zbuff;
/* 441 */       } catch (Exception x) {}
/*     */       
/* 443 */       TroubleCode dtc = new TroubleCode("0", bacCode, countryCode, Long.valueOf(System.currentTimeMillis()), buff);
/*     */       try {
/* 445 */         this.log.error("Save DTC Data in cache");
/* 446 */         cacheMan.saveDTCDataInCache(dtc);
/* 447 */       } catch (Exception e) {
/* 448 */         this.log.error("Exception when get the DTCData from device, " + e, e);
/*     */       } 
/* 450 */     } catch (Exception e) {
/* 451 */       this.log.error("unable to perform DTCUpload (ignoring) - error:" + e, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean initUILanguage() {
/* 456 */     Locale locale = null;
/* 457 */     String langId = System.getProperty("language.id");
/* 458 */     if (langId != null) {
/* 459 */       locale = StringUtilities.getLocale(langId);
/*     */     } else {
/* 461 */       locale = Locale.getDefault();
/*     */     } 
/* 463 */     SDCurrentContext.getInstance().setUILanguage(locale);
/* 464 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\clien\\ui\ctrl\IOManagerUI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */