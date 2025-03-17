/*     */ package com.eoos.gm.tis2web.swdl.client.ui.ctrl;
/*     */ 
/*     */ import com.eoos.gm.tis2web.swdl.client.model.DeviceInfo;
/*     */ import com.eoos.gm.tis2web.swdl.client.util.DeviceComparator;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.ServerError;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.device.Device;
/*     */ import java.io.File;
/*     */ import java.util.Comparator;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
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
/*     */ public class SDCurrentContext
/*     */ {
/*     */   public static final int SRV_ERR = -4;
/*     */   public static final int APPVERS_NOT_FOUND = -3;
/*     */   public static final int EMPTY_CARD = -2;
/*     */   public static final int ERROR = -1;
/*     */   public static final int NO_NEW_VERS_FOUND = 0;
/*     */   public static final int VERS_DIFF_SIZE = 1;
/*     */   public static final int VERS_NOT_FOUND = 2;
/*     */   public static final int NEW_VERS_FOUND = 3;
/*     */   public static final int NEW_VERS_DIFF_CARDSIZE = 4;
/*     */   public static final int SUCCESS = 5;
/*     */   public static final int NO_STRATA_SUPPORT = 6;
/*     */   public static final int DEVICE_ERROR = 0;
/*     */   public static final int SERVER_ERROR = 1;
/*     */   public static final int NO_ERROR = 0;
/*     */   public static final int ERROR_CREATE_DIR = 1;
/*     */   public static final int ERROR_BAD_FILE = 2;
/*     */   public static final int ERROR_SRV_DOWN = 3;
/*     */   public static final int ERROR_SAVE_INF = 4;
/*     */   public static final int ERROR_MAKE_SPACE = 5;
/*     */   public static final String STANDARD = "Standard";
/*     */   public static final String CUSTOM = "Custom";
/*     */   public static final String AUTO = "AUTO";
/*     */   public static final String LOGGING = "Logging";
/*     */   public static final String LOGDEBUG = "LogDebug";
/*     */   public static final String LOGFILENAME = "LogFileName";
/*     */   public static final String CACHEPATH = "CachePath";
/*     */   public static final String CACHESIZE = "CacheSize";
/*     */   public static final String CHECK2TIMES = "CheckTech2Times";
/*     */   public static final String SUPPORTS_ALL_CARD_TYPES = "SUPPORTS_ALL_CARD_TYPES";
/*  59 */   private static SDCurrentContext instance = null;
/*  60 */   private Device selectedTool = null;
/*  61 */   private Device currDevice = Device.TECH2;
/*  62 */   private DeviceInfo devInf = null;
/*  63 */   private Hashtable dev2settings = new Hashtable<Object, Object>();
/*  64 */   private Hashtable locSettings = new Hashtable<Object, Object>();
/*  65 */   private DeviceInfo newAppInfo = null;
/*  66 */   private String[] comPorts = new String[2];
/*  67 */   private AppProperties logProp = new AppProperties();
/*  68 */   private Device[] devices = new Device[0];
/*  69 */   private Set applications = null;
/*     */   private String selectedMode;
/*  71 */   private Locale uiLan = new Locale("en", "");
/*  72 */   public ServerError srvError = null;
/*     */ 
/*     */   
/*     */   public SDCurrentContext() {
/*  76 */     this.dev2settings.put(Device.TECH1.getDescription(), "COM1,19200");
/*  77 */     this.dev2settings.put(Device.TECH2.getDescription(), "AUTO,115200");
/*  78 */     this.dev2settings.put("Logging", "OFF, CONSOLE, STDLOG");
/*  79 */     this.dev2settings.put("LogDebug", "NO");
/*  80 */     this.dev2settings.put("CacheSize", "20");
/*  81 */     this.dev2settings.put("CheckTech2Times", "Yes");
/*  82 */     this.dev2settings.put("CachePath", System.getProperty("user.home") + File.separator + "swdl" + File.separator + "swdlCache");
/*  83 */     this.dev2settings.put("LogFileName", System.getProperty("user.home") + File.separator + "swdl" + File.separator + "swdlLog.txt");
/*     */     
/*  85 */     this.comPorts[0] = "COM1";
/*  86 */     this.comPorts[1] = "COM2";
/*     */   }
/*     */   
/*     */   public static synchronized SDCurrentContext getInstance() {
/*  90 */     if (instance == null) {
/*  91 */       instance = new SDCurrentContext();
/*     */     }
/*  93 */     return instance;
/*     */   }
/*     */   
/*     */   public void setSelectedTool(Device tool) {
/*  97 */     this.selectedTool = tool;
/*     */   }
/*     */   
/*     */   public Device getSelectedTool() {
/* 101 */     return this.selectedTool;
/*     */   }
/*     */   
/*     */   public void setDeviceInfo(DeviceInfo di) {
/* 105 */     this.devInf = di;
/*     */   }
/*     */   
/*     */   public DeviceInfo getDeviceInfo() {
/* 109 */     return this.devInf;
/*     */   }
/*     */   
/*     */   public Device getCurrDevice() {
/* 113 */     return this.currDevice;
/*     */   }
/*     */   
/*     */   public void setCurrDevice(Device device) {
/* 117 */     this.currDevice = device;
/*     */   }
/*     */   
/*     */   public void setSettings(Hashtable settings) {
/* 121 */     this.dev2settings = settings;
/*     */   }
/*     */   
/*     */   public void setDeviceSettings(String dev, String sett) {
/* 125 */     this.dev2settings.put(dev, sett);
/*     */   }
/*     */   
/*     */   public String getCurrDevicePort(String dev) {
/* 129 */     String sett = (String)this.dev2settings.get(dev);
/* 130 */     if (sett != null) {
/* 131 */       return sett.substring(0, sett.indexOf(','));
/*     */     }
/* 133 */     return "COM1";
/*     */   }
/*     */   
/*     */   public String getCurrDeviceSpeed(String dev) {
/* 137 */     String sett = (String)this.dev2settings.get(dev);
/* 138 */     if (sett != null) {
/* 139 */       return sett.substring(sett.indexOf(',') + 1);
/*     */     }
/* 141 */     return "9600";
/*     */   }
/*     */   
/*     */   public Hashtable getSettings() {
/* 145 */     return this.dev2settings;
/*     */   }
/*     */   
/*     */   public DeviceInfo getNewAppInfo() {
/* 149 */     return this.newAppInfo;
/*     */   }
/*     */   
/*     */   public void setNewAppInfo(DeviceInfo appinf) {
/* 153 */     this.newAppInfo = appinf;
/*     */   }
/*     */   
/*     */   public Hashtable getLocSettings() {
/* 157 */     return this.locSettings;
/*     */   }
/*     */   
/*     */   public String[] getComPorts() {
/* 161 */     return this.comPorts;
/*     */   }
/*     */   
/*     */   public void setComPorts(String[] comPorts) {
/* 165 */     this.comPorts = comPorts;
/*     */   }
/*     */   
/*     */   public AppProperties getLogProp() {
/* 169 */     return this.logProp;
/*     */   }
/*     */   
/*     */   public Device[] getDevices() {
/* 173 */     return this.devices;
/*     */   }
/*     */   
/*     */   public void setDevices(Set devices) {
/* 177 */     TreeSet sortDevices = new TreeSet((Comparator<?>)new DeviceComparator());
/* 178 */     sortDevices.addAll(devices);
/* 179 */     this.devices = (Device[])sortDevices.toArray((Object[])this.devices);
/*     */   }
/*     */   
/*     */   public Set getApplications() {
/* 183 */     return this.applications;
/*     */   }
/*     */   
/*     */   public void setApplications(Set apps) {
/* 187 */     this.applications = apps;
/*     */   }
/*     */   
/*     */   public String getSelectedMode() {
/* 191 */     return this.selectedMode;
/*     */   }
/*     */   
/*     */   public boolean isCustomMode() {
/* 195 */     return getSelectedMode().equalsIgnoreCase("Custom");
/*     */   }
/*     */   
/*     */   public boolean isStandardMode() {
/* 199 */     return getSelectedMode().equalsIgnoreCase("Standard");
/*     */   }
/*     */   
/*     */   public void setSelectedMode(String selectedMode) {
/* 203 */     this.selectedMode = selectedMode;
/*     */   }
/*     */   
/*     */   public Locale getUILanguage() {
/* 207 */     return this.uiLan;
/*     */   }
/*     */   
/*     */   public void setUILanguage(Locale uiLan) {
/* 211 */     this.uiLan = uiLan;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\clien\\ui\ctrl\SDCurrentContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */