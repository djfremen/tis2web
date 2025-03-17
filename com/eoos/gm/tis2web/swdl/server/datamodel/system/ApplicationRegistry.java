/*     */ package com.eoos.gm.tis2web.swdl.server.datamodel.system;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.application.Application;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.application.Language;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.application.Version;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.device.Device;
/*     */ import com.eoos.gm.tis2web.swdl.server.db.DatabaseAdapter;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ApplicationRegistry
/*     */   implements IApplicationRegistry
/*     */ {
/*  33 */   private static final Logger log = Logger.getLogger(ApplicationRegistry.class);
/*     */   
/*  35 */   private static ApplicationRegistry instance = null;
/*     */   
/*     */   private class State {
/*  38 */     public Map devappid2dbadapter = null;
/*     */     private State() {}
/*  40 */     public Map devappid2app = null;
/*     */     
/*  42 */     public Map devappdesc2app = null;
/*     */     
/*  44 */     public Map dev2apps = null;
/*     */     
/*     */     public long initializationTime;
/*     */   }
/*     */   
/*  49 */   private final Object SYNC_STATE = new Object();
/*     */   
/*  51 */   private State state = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private ApplicationRegistry() {
/*  56 */     DeviceRegistry.getInstance().addObserver(new DeviceRegistry.Observer()
/*     */         {
/*     */           public void onModification() {
/*  59 */             ApplicationRegistry.log.info("device registry modified - resetting");
/*  60 */             ApplicationRegistry.this.reset();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected long getInitializationTime() {
/*  68 */     return (getState()).initializationTime;
/*     */   }
/*     */   
/*     */   public void reset() {
/*  72 */     synchronized (this.SYNC_STATE) {
/*  73 */       this.state = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private State getState() {
/*  78 */     synchronized (this.SYNC_STATE) {
/*  79 */       if (this.state == null) {
/*  80 */         log.info("initializing");
/*  81 */         this.state = new State();
/*  82 */         this.state.initializationTime = System.currentTimeMillis();
/*  83 */         this.state.devappid2dbadapter = Collections.synchronizedMap(new LinkedHashMap<Object, Object>());
/*  84 */         this.state.dev2apps = Collections.synchronizedMap(new LinkedHashMap<Object, Object>());
/*  85 */         this.state.devappdesc2app = Collections.synchronizedMap(new LinkedHashMap<Object, Object>());
/*  86 */         Iterator<Device> itDev = DeviceRegistry.getInstance().getDevices().iterator();
/*  87 */         while (itDev.hasNext()) {
/*  88 */           Device dev = itDev.next();
/*  89 */           if (log.isDebugEnabled()) {
/*  90 */             log.debug("...retrieving applications for device: " + String.valueOf(dev));
/*     */           }
/*  92 */           Set<Application> apps = new LinkedHashSet();
/*  93 */           Iterator<DatabaseAdapter> itDbAdapter = DeviceRegistry.getInstance().getDatabaseAdapters(dev).iterator();
/*  94 */           while (itDbAdapter.hasNext()) {
/*  95 */             DatabaseAdapter dbAdapter = itDbAdapter.next();
/*  96 */             if (log.isDebugEnabled()) {
/*  97 */               log.debug("...retrieving application for database adapter: " + String.valueOf(dbAdapter));
/*     */             }
/*  99 */             Iterator<Application> itApp = dbAdapter.getApplications().iterator();
/* 100 */             while (itApp.hasNext()) {
/* 101 */               Application app = itApp.next();
/* 102 */               if (log.isDebugEnabled()) {
/* 103 */                 log.debug("...adding application: " + String.valueOf(app));
/*     */               }
/* 105 */               PairImpl pairImpl1 = new PairImpl(dev, app.getIdentifier());
/* 106 */               PairImpl pairImpl2 = new PairImpl(dev, app.getDescription());
/* 107 */               this.state.devappid2dbadapter.put(pairImpl1, dbAdapter);
/*     */               
/* 109 */               this.state.devappdesc2app.put(pairImpl2, app);
/* 110 */               apps.add(app);
/*     */             } 
/*     */           } 
/* 113 */           this.state.dev2apps.put(dev, apps);
/*     */         } 
/*     */       } 
/* 116 */       return this.state;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void init() {
/* 121 */     getState();
/*     */   }
/*     */   
/*     */   public static synchronized ApplicationRegistry getInstance() {
/* 125 */     if (instance == null) {
/* 126 */       instance = new ApplicationRegistry();
/*     */     }
/* 128 */     return instance;
/*     */   }
/*     */   
/*     */   public static IApplicationRegistry getInstance(ClientContext context) {
/* 132 */     synchronized (context.getLockObject()) {
/* 133 */       IApplicationRegistry instance = (IApplicationRegistry)context.getObject(IApplicationRegistry.class);
/* 134 */       return (instance != null) ? instance : newInstance(context);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static IApplicationRegistry newInstance(ClientContext context) {
/* 139 */     synchronized (context.getLockObject()) {
/* 140 */       IApplicationRegistry instance = new ApplicationRegistryWrapper(context, getInstance());
/* 141 */       context.storeObject(IApplicationRegistry.class, instance);
/* 142 */       return instance;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DatabaseAdapter getDatabaseAdapter(Device device, String applicationID) {
/* 152 */     return (DatabaseAdapter)(getState()).devappid2dbadapter.get(new PairImpl(device, applicationID));
/*     */   }
/*     */   
/*     */   public Set getApplications() {
/* 156 */     return new LinkedHashSet((getState()).devappid2app.values());
/*     */   }
/*     */   
/*     */   public Set getApplications(Device device) {
/* 160 */     return new LinkedHashSet((Set)(getState()).dev2apps.get(device));
/*     */   }
/*     */   
/*     */   public Version getNewestVersion(Device device, String appDesc, String versNo, String langID) {
/* 164 */     if (log.isDebugEnabled()) {
/* 165 */       log.debug("retrieving newest version for device: " + String.valueOf(device) + ", application: " + String.valueOf(appDesc) + ", current version number: " + String.valueOf(versNo) + " and language id: " + String.valueOf(langID));
/*     */     }
/* 167 */     Application app = (Application)(getState()).devappdesc2app.get(new PairImpl(device, appDesc));
/* 168 */     if (app == null) {
/* 169 */       log.warn("...unknown application: " + String.valueOf(appDesc) + ", returning null");
/* 170 */       return null;
/*     */     } 
/* 172 */     Version vers = getNewestVersion(app, versNo);
/* 173 */     Language lang = LanguageRegistry.getInstance().getLanguage(langID);
/* 174 */     if (vers == null || !vers.getLanguages().contains(lang)) {
/* 175 */       log.warn("...no corresponding version found");
/* 176 */       return null;
/*     */     } 
/* 178 */     if (log.isDebugEnabled()) {
/* 179 */       log.debug("...found version: " + String.valueOf(vers));
/*     */     }
/* 181 */     Version cloneVers = (Version)vers.clone();
/* 182 */     cloneVers.setLanNeutralFiles(Version.cloneSetFiles(vers.getLanNeutralFiles(), cloneVers));
/* 183 */     Map<?, ?> lan2files = Collections.synchronizedMap(new LinkedHashMap<Object, Object>());
/* 184 */     lan2files.put(lang, Version.cloneSetFiles(vers.getFiles(lang), cloneVers));
/* 185 */     if (lan2files.size() == 0)
/* 186 */       return null; 
/* 187 */     cloneVers.setLan2Files(lan2files);
/* 188 */     return cloneVers;
/*     */   }
/*     */ 
/*     */   
/*     */   private Version getNewestVersion(Application app, String versNo) {
/* 193 */     if (log.isDebugEnabled()) {
/* 194 */       log.debug("retrieving newest version for application: " + String.valueOf(app) + " and current version number: " + String.valueOf(versNo));
/*     */     }
/* 196 */     Version vers = null;
/* 197 */     Version versComp = null;
/* 198 */     Set versions = app.getVersions();
/* 199 */     if (log.isDebugEnabled()) {
/* 200 */       log.debug("...retrieved application versions: " + versions);
/*     */     }
/* 202 */     Iterator<Version> itVers = versions.iterator();
/* 203 */     if (itVers.hasNext()) {
/* 204 */       vers = itVers.next();
/* 205 */       while (itVers.hasNext()) {
/* 206 */         versComp = itVers.next();
/* 207 */         if (versComp.getNumber().compareTo(versNo) > 0 && versComp.getNumber().compareTo(vers.getNumber()) > 0)
/* 208 */           vers = versComp; 
/*     */       } 
/*     */     } 
/* 211 */     if (log.isDebugEnabled()) {
/* 212 */       log.debug("...returning " + String.valueOf(vers));
/*     */     }
/* 214 */     return vers;
/*     */   }
/*     */   
/*     */   public Version getNewestVersion(Device device, String appDesc, String langID) {
/* 218 */     if (log.isDebugEnabled()) {
/* 219 */       log.debug("retrieving newest version for device: " + String.valueOf(device) + ", application: " + String.valueOf(appDesc) + " and language id: " + String.valueOf(langID));
/*     */     }
/* 221 */     Application app = (Application)(getState()).devappdesc2app.get(new PairImpl(device, appDesc));
/* 222 */     if (app == null) {
/* 223 */       log.warn("...unknown application: " + String.valueOf(appDesc) + ", returning null");
/* 224 */       return null;
/*     */     } 
/* 226 */     Version vers = getNewestVersion(app);
/* 227 */     Language lang = LanguageRegistry.getInstance().getLanguage(langID);
/* 228 */     if (vers == null || !vers.getLanguages().contains(lang)) {
/* 229 */       log.warn("...no corresponding version found");
/* 230 */       return null;
/*     */     } 
/* 232 */     if (log.isDebugEnabled()) {
/* 233 */       log.debug("...found version: " + String.valueOf(vers));
/*     */     }
/* 235 */     Version cloneVers = (Version)vers.clone();
/* 236 */     cloneVers.setLanNeutralFiles(Version.cloneSetFiles(vers.getLanNeutralFiles(), cloneVers));
/* 237 */     Map<?, ?> lan2files = Collections.synchronizedMap(new LinkedHashMap<Object, Object>());
/* 238 */     lan2files.put(lang, Version.cloneSetFiles(vers.getFiles(lang), cloneVers));
/* 239 */     if (lan2files.size() == 0)
/* 240 */       return null; 
/* 241 */     cloneVers.setLan2Files(lan2files);
/* 242 */     return cloneVers;
/*     */   }
/*     */ 
/*     */   
/*     */   public Version getNewestVersion(Application app) {
/* 247 */     if (log.isDebugEnabled()) {
/* 248 */       log.debug("retrieving newest version for application: " + String.valueOf(app));
/*     */     }
/*     */     
/* 251 */     Version vers = null;
/* 252 */     Version versComp = null;
/* 253 */     Set versions = app.getVersions();
/* 254 */     if (log.isDebugEnabled()) {
/* 255 */       log.debug("...retrieved application versions: " + versions);
/*     */     }
/* 257 */     Iterator<Version> itVers = versions.iterator();
/* 258 */     if (itVers.hasNext()) {
/* 259 */       vers = itVers.next();
/* 260 */       while (itVers.hasNext()) {
/* 261 */         versComp = itVers.next();
/* 262 */         if (versComp.getDate().longValue() > vers.getDate().longValue())
/* 263 */           vers = versComp; 
/*     */       } 
/*     */     } 
/* 266 */     if (log.isDebugEnabled()) {
/* 267 */       log.debug("...returning version: " + String.valueOf(vers));
/*     */     }
/* 269 */     return vers;
/*     */   }
/*     */   
/*     */   public Version getVersion(Device device, String appDesc, String versNo, String langID) {
/* 273 */     if (log.isDebugEnabled()) {
/* 274 */       log.debug("retrieving version object for device: " + String.valueOf(device) + ", application: " + String.valueOf(appDesc) + ", version number: " + String.valueOf(versNo) + ", language id: " + String.valueOf(langID));
/*     */     }
/* 276 */     Application app = (Application)(getState()).devappdesc2app.get(new PairImpl(device, appDesc));
/* 277 */     if (app == null) {
/* 278 */       log.warn("...unknown application: " + String.valueOf(appDesc));
/* 279 */       return null;
/*     */     } 
/* 281 */     Version vers = getVersion(app, versNo);
/* 282 */     Language lang = LanguageRegistry.getInstance().getLanguage(langID);
/* 283 */     if (vers == null || !vers.getLanguages().contains(lang)) {
/* 284 */       log.warn("...no corresponding version found");
/* 285 */       return null;
/*     */     } 
/* 287 */     if (log.isDebugEnabled()) {
/* 288 */       log.debug("...found version object : " + String.valueOf(vers));
/*     */     }
/* 290 */     Version cloneVers = (Version)vers.clone();
/* 291 */     cloneVers.setLanNeutralFiles(Version.cloneSetFiles(vers.getLanNeutralFiles(), cloneVers));
/* 292 */     Map<?, ?> lan2files = Collections.synchronizedMap(new LinkedHashMap<Object, Object>());
/* 293 */     lan2files.put(lang, Version.cloneSetFiles(vers.getFiles(lang), cloneVers));
/* 294 */     cloneVers.setLan2Files(lan2files);
/* 295 */     return cloneVers;
/*     */   }
/*     */ 
/*     */   
/*     */   private Version getVersion(Application app, String versNo) {
/* 300 */     Version vers = null;
/* 301 */     Set versions = app.getVersions();
/* 302 */     Iterator<Version> itVers = versions.iterator();
/* 303 */     while (itVers.hasNext()) {
/* 304 */       Version tmp = itVers.next();
/* 305 */       if (tmp.getNumber().compareTo(versNo) == 0) {
/* 306 */         vers = tmp;
/*     */         break;
/*     */       } 
/*     */     } 
/* 310 */     return vers;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\server\datamodel\system\ApplicationRegistry.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */