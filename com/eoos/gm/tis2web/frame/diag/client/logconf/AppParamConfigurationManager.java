/*     */ package com.eoos.gm.tis2web.frame.diag.client.logconf;
/*     */ 
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.propcfg.impl.PropertiesConfigurationImpl;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import java.util.Properties;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.Vector;
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
/*     */ public class AppParamConfigurationManager
/*     */ {
/*  30 */   private static final Logger log = Logger.getLogger(AppParamConfigurationManager.class);
/*     */   
/*  32 */   private static AppParamConfigurationManager instance = null;
/*     */   
/*     */   private boolean isPopulated = false;
/*     */   
/*     */   public static final int BUNDLE = 0;
/*     */   public static final int PROPERTY = 1;
/*     */   private static final String MAIL = "mail";
/*     */   private static final String JWS = "wstartclient";
/*     */   private static final String REGISTRY = "registry";
/*  41 */   private static final String HOMEPATH = System.getProperty("user.home");
/*     */   
/*     */   private ArrayList arrlAppParamConfigurations;
/*     */   
/*     */   private HashMap hmpLogFileList;
/*     */   
/*     */   private Properties propertyResource;
/*     */   
/*     */   private PropertiesConfigurationImpl cfgPropertyImpl;
/*     */   
/*     */   private Configuration cfgProperty;
/*     */   
/*     */   private String currentPrefix;
/*     */   private HashMap currentHmpParamsForApp;
/*     */   private Locale locale;
/*     */   
/*     */   private AppParamConfigurationManager() {
/*  58 */     this.locale = Locale.ENGLISH;
/*     */   }
/*     */   
/*     */   private void init() {
/*  62 */     this.arrlAppParamConfigurations.clear();
/*  63 */     this.hmpLogFileList.clear();
/*  64 */     this.propertyResource.clear();
/*  65 */     this.cfgPropertyImpl.getKeys().clear();
/*  66 */     this.cfgProperty.getKeys().clear();
/*  67 */     this.currentHmpParamsForApp.clear();
/*  68 */     this.isPopulated = false;
/*     */   }
/*     */   
/*     */   public static AppParamConfigurationManager getInstance() {
/*  72 */     if (instance == null) {
/*  73 */       instance = new AppParamConfigurationManager();
/*     */     }
/*  75 */     return instance;
/*     */   }
/*     */   
/*     */   public boolean isPopulated() {
/*  79 */     return this.isPopulated;
/*     */   }
/*     */   
/*     */   public Locale get_locale() {
/*  83 */     return this.locale;
/*     */   }
/*     */   
/*     */   private void set_locale(Locale locale) {
/*  87 */     this.locale = locale;
/*     */   }
/*     */   
/*     */   public AppParamConfigurationManager handleConfiguration(Locale locale) {
/*  91 */     instance.set_locale(locale);
/*  92 */     instance.initLocalConfiguration(locale);
/*  93 */     return instance;
/*     */   }
/*     */ 
/*     */   
/*     */   private void initLocalConfiguration(Locale locale) {
/*  98 */     String localParamVersion = null;
/*  99 */     String deliveredParamVersion = null;
/*     */     
/* 101 */     populateConfigurations(0, "log.configuration.bundle.path", true);
/*     */     
/* 103 */     File homeDir = new File(System.getProperty("user.home"));
/* 104 */     homeDir = new File(homeDir, "diag");
/*     */     
/*     */     try {
/* 107 */       AppParamConfigurationLabelResource resource = new AppParamConfigurationLabelResource(locale);
/* 108 */       String nameAppConfProp = resource.getResourceBundle().getString("log.configuration.name");
/* 109 */       File localAppConfProp = new File(homeDir + "\\" + nameAppConfProp + ".properties");
/* 110 */       if (localAppConfProp.exists()) {
/*     */ 
/*     */         
/* 113 */         Properties localProp = new Properties();
/* 114 */         localProp.load(new FileInputStream(localAppConfProp.getAbsolutePath()));
/* 115 */         localParamVersion = localProp.getProperty("configuration.version");
/* 116 */         deliveredParamVersion = getProperties().getProperty("configuration.version");
/* 117 */         boolean result = (localParamVersion == null || !compare(localParamVersion, deliveredParamVersion));
/* 118 */         if (result) {
/* 119 */           AppParamConfigurationUtilities.makeFileFromProperties(getProperties(), localAppConfProp);
/*     */         
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 125 */         AppParamConfigurationUtilities.makeFileFromProperties(getProperties(), localAppConfProp);
/*     */       } 
/* 127 */       getInstance().init();
/* 128 */       getInstance().populateConfigurations(1, localAppConfProp.getAbsolutePath(), true);
/* 129 */     } catch (Exception ex) {
/* 130 */       log.error(ex.toString());
/*     */       return;
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean compare(String A, String B) {
/* 136 */     return (A.compareTo(B) == 0);
/*     */   }
/*     */   
/*     */   private void loadProperties(String propertyLocation) throws FileNotFoundException, IOException {
/* 140 */     this.propertyResource = new Properties();
/* 141 */     this.propertyResource.load(new FileInputStream(propertyLocation));
/*     */   }
/*     */   
/*     */   private void loadProperties(Properties properties) {
/* 145 */     this.propertyResource = properties;
/*     */   }
/*     */   
/*     */   public Properties getProperties() {
/* 149 */     return this.propertyResource;
/*     */   }
/*     */   
/*     */   private void setPropertyImpl() {
/* 153 */     this.cfgPropertyImpl = new PropertiesConfigurationImpl(this.propertyResource);
/*     */   }
/*     */   
/*     */   private void initResources(int withBundleOrProperty, String propertyReference) {
/* 157 */     if (withBundleOrProperty == 0) {
/* 158 */       initWithBundle(propertyReference);
/*     */     } else {
/* 160 */       initWithPropertyResource(propertyReference);
/*     */     } 
/* 162 */     setPropertyImpl();
/* 163 */     this.currentHmpParamsForApp = new HashMap<Object, Object>();
/* 164 */     this.arrlAppParamConfigurations = new ArrayList();
/* 165 */     this.hmpLogFileList = new HashMap<Object, Object>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initWithPropertyResource(String propertyPathAndName) {
/*     */     try {
/* 174 */       loadProperties(propertyPathAndName);
/* 175 */     } catch (Exception ex) {
/* 176 */       log.error(ex.toString());
/*     */       return;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initWithBundle(String propertyName) {
/*     */     try {
/* 188 */       AppParamConfigurationLabelResource resource = new AppParamConfigurationLabelResource(this.locale);
/* 189 */       loadProperties(getPropertiesFromBundle(ResourceBundle.getBundle(resource.getResourceBundle().getString(propertyName))));
/* 190 */     } catch (Exception ex) {
/* 191 */       log.error(ex.toString());
/*     */       return;
/*     */     } 
/*     */   }
/*     */   
/*     */   public AppParamConfigurationMail getConfigurationMail() {
/* 197 */     Object confBase = null;
/*     */     
/* 199 */     for (Iterator iterConf = this.arrlAppParamConfigurations.iterator(); iterConf.hasNext(); ) {
/* 200 */       confBase = iterConf.next();
/* 201 */       if (((AppParamConfigurationBase)confBase).get_name().equalsIgnoreCase("mail") && ((AppParamConfigurationBase)confBase).get_container().equalsIgnoreCase("mail")) {
/* 202 */         return (AppParamConfigurationMail)confBase;
/*     */       }
/*     */     } 
/* 205 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setConfiguration(Configuration configuration) {
/* 212 */     this.cfgProperty = configuration;
/*     */   }
/*     */   
/*     */   public static Properties getPropertiesFromBundle(ResourceBundle bundle) {
/* 216 */     Properties properties = new Properties();
/* 217 */     for (Enumeration<String> enumer = bundle.getKeys(); enumer.hasMoreElements(); ) {
/* 218 */       String key = enumer.nextElement();
/* 219 */       properties.setProperty(key, bundle.getString(key));
/*     */     } 
/* 221 */     return properties;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setCurrentPrefix(String sPrefix) {
/* 228 */     this.currentPrefix = sPrefix;
/* 229 */     this.cfgProperty = (Configuration)new SubConfigurationWrapper((Configuration)this.cfgPropertyImpl, this.currentPrefix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AppParamConfigurations getAppConfigurationByName(String appConfName) {
/* 236 */     for (int iterList = 0; iterList < this.arrlAppParamConfigurations.size(); ) {
/* 237 */       AppParamConfigurationBase base = this.arrlAppParamConfigurations.get(iterList++);
/* 238 */       if (base.get_name().equalsIgnoreCase(appConfName)) {
/* 239 */         if (base.get_container().equalsIgnoreCase("wstartclient"))
/* 240 */           return this.arrlAppParamConfigurations.get(--iterList); 
/* 241 */         if (base.get_container().equalsIgnoreCase("registry"))
/* 242 */           return this.arrlAppParamConfigurations.get(--iterList); 
/*     */       } 
/*     */     } 
/* 245 */     return null;
/*     */   }
/*     */   
/*     */   public int getCountAppConfigurations() {
/* 249 */     int counter = 0;
/* 250 */     for (int iterList = 0; iterList < this.arrlAppParamConfigurations.size(); ) {
/* 251 */       AppParamConfigurationBase base = this.arrlAppParamConfigurations.get(iterList++);
/* 252 */       if (base.get_container().equalsIgnoreCase("wstartclient") || base.get_container().equalsIgnoreCase("registry"))
/* 253 */         counter++; 
/*     */     } 
/* 255 */     return counter;
/*     */   }
/*     */   
/*     */   public int getCountFileContainer() {
/* 259 */     return (this.hmpLogFileList != null && !this.hmpLogFileList.isEmpty()) ? this.hmpLogFileList.size() : 0;
/*     */   }
/*     */   
/*     */   public HashMap getHmpLogFileList() {
/* 263 */     return this.hmpLogFileList;
/*     */   }
/*     */   
/*     */   public int numberOfElements() {
/* 267 */     return this.arrlAppParamConfigurations.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addToConfigurationList(AppParamConfigurationBase configuration) {
/* 274 */     if (this.arrlAppParamConfigurations == null) {
/* 275 */       this.arrlAppParamConfigurations = new ArrayList();
/*     */     }
/* 277 */     this.arrlAppParamConfigurations.add(configuration);
/* 278 */     this.arrlAppParamConfigurations.trimToSize();
/*     */   }
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
/*     */   private void populateConfigurations(int withBundleOrProperty, String propertyNameOrFullPath, boolean populateLogListe) {
/* 291 */     initResources(withBundleOrProperty, propertyNameOrFullPath);
/*     */ 
/*     */     
/* 294 */     populateMailConfiguration();
/*     */ 
/*     */     
/* 297 */     populateAppConfiguration("wstartclient");
/* 298 */     populateAppConfiguration("registry");
/*     */ 
/*     */     
/* 301 */     if (populateLogListe) {
/* 302 */       populateLogFileList();
/*     */     }
/*     */ 
/*     */     
/* 306 */     this.isPopulated = true;
/*     */   }
/*     */   
/*     */   protected void populateMailConfiguration() {
/* 310 */     String fullKey = null;
/*     */ 
/*     */     
/* 313 */     setConfigurationForPrefix("app.");
/*     */ 
/*     */     
/* 316 */     fullKey = getFullKeyForSuffixValue("name", "mail", 1);
/*     */ 
/*     */     
/* 319 */     if (fullKey != null) {
/* 320 */       this.currentHmpParamsForApp.clear();
/* 321 */       this.currentHmpParamsForApp = getHmpForKeyAndDiscriminant(fullKey, 1, false);
/*     */       
/* 323 */       if (this.currentHmpParamsForApp != null) {
/* 324 */         AppParamConfigurationMail mailConfiguration = new AppParamConfigurationMail();
/* 325 */         mailConfiguration.set_container("mail");
/* 326 */         mailConfiguration.set_name("mail");
/*     */         
/* 328 */         for (Iterator<String> iterHmp = this.currentHmpParamsForApp.keySet().iterator(); iterHmp.hasNext(); ) {
/* 329 */           String nextKey = iterHmp.next();
/* 330 */           String value = (String)this.currentHmpParamsForApp.get(nextKey);
/* 331 */           if (nextKey.indexOf("default.archive.extension") != -1) {
/* 332 */             mailConfiguration.set_defaultArchiveExtension(value); continue;
/* 333 */           }  if (nextKey.indexOf("default.from") != -1) {
/* 334 */             mailConfiguration.set_defaultFrom(value); continue;
/* 335 */           }  if (nextKey.indexOf("default.to") != -1) {
/* 336 */             mailConfiguration.set_defaultTo(value); continue;
/* 337 */           }  if (nextKey.indexOf("default.subject") != -1) {
/* 338 */             mailConfiguration.set_defaultSubject(value); continue;
/* 339 */           }  if (nextKey.indexOf("default.body") != -1)
/* 340 */             mailConfiguration.set_defaultBody(value); 
/*     */         } 
/* 342 */         addToConfigurationList(mailConfiguration);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void populateAppConfiguration(String containerName) {
/* 349 */     AppParamConfigurations appParamConfigurations = null;
/* 350 */     String fullKey = "";
/* 351 */     int indexSearch = 1;
/*     */ 
/*     */     
/* 354 */     setConfigurationForPrefix("app.");
/*     */     
/* 356 */     while (indexSearch > 0) {
/*     */       
/* 358 */       fullKey = getFullKeyForSuffixValue("container", containerName, indexSearch++);
/* 359 */       if (fullKey != null) {
/* 360 */         this.currentHmpParamsForApp.clear();
/* 361 */         this.currentHmpParamsForApp = getHmpForKeyAndDiscriminant(fullKey, 1, false);
/*     */         
/* 363 */         if (containerName.equalsIgnoreCase("wstartclient")) {
/* 364 */           appParamConfigurations = new AppParamConfigurationWStart();
/* 365 */         } else if (containerName.equalsIgnoreCase("registry")) {
/* 366 */           appParamConfigurations = new AppParamConfigurationRegistry();
/*     */         } else {
/* 368 */           appParamConfigurations = new AppParamConfigurations();
/*     */         } 
/*     */         
/* 371 */         appParamConfigurations.set_container(containerName);
/*     */         
/* 373 */         if (this.currentHmpParamsForApp != null) {
/* 374 */           callPopulateAppConfigurationForContainerAndFullKey(appParamConfigurations, fullKey.substring(0, fullKey.length() - (new String("container")).length()));
/*     */         }
/*     */       } else {
/* 377 */         indexSearch = -1;
/*     */       } 
/*     */       
/* 380 */       setConfigurationForPrefix("app.");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void callPopulateAppConfigurationForContainerAndFullKey(AppParamConfigurations appParamConfigurations, String basePrefix) {
/* 386 */     AppParamLogConfiguration logConfiguration = null;
/*     */     
/* 388 */     appParamConfigurations.set_name(this.cfgProperty.getProperty("name"));
/*     */     
/* 390 */     setConfigurationForPrefix(basePrefix);
/* 391 */     SubConfigurationWrapper subConfigurationWrapper = new SubConfigurationWrapper(this.cfgProperty, "log.directory.");
/* 392 */     ArrayList discriminantSorted = AppParamConfigurationUtilities.getUniqueNextDiscriminantSorted((Configuration)subConfigurationWrapper);
/*     */     
/* 394 */     for (Iterator<String> iterDS = discriminantSorted.iterator(); iterDS.hasNext(); ) {
/* 395 */       String discriminant = iterDS.next();
/* 396 */       logConfiguration = new AppParamLogConfiguration();
/*     */       
/* 398 */       logConfiguration.set_filePattern(this.cfgProperty.getProperty("log.file." + discriminant + ".pattern"));
/* 399 */       if (logConfiguration.get_filePattern() == null) {
/* 400 */         logConfiguration.set_filePattern("*.log");
/*     */       }
/* 402 */       logConfiguration.set_dirBase(subConfigurationWrapper.getProperty(discriminant + ".base"));
/* 403 */       if (logConfiguration.get_dirBase() == null) {
/* 404 */         logConfiguration.set_dirBase(".");
/*     */       }
/* 406 */       logConfiguration.set_dirMode(subConfigurationWrapper.getProperty(discriminant + ".mode"));
/* 407 */       if (logConfiguration.get_dirMode() == null) {
/* 408 */         logConfiguration.set_dirMode("sequential");
/*     */       }
/* 410 */       logConfiguration.set_dirPattern(subConfigurationWrapper.getProperty(discriminant + ".pattern"));
/* 411 */       if (logConfiguration.get_dirPattern() == null) {
/* 412 */         logConfiguration.set_dirPattern("*");
/*     */       }
/* 414 */       if (appParamConfigurations.get_container().equalsIgnoreCase("wstartclient")) {
/* 415 */         populateAppConfigurationWebstart((AppParamConfigurationWStart)appParamConfigurations);
/* 416 */       } else if (appParamConfigurations.get_container().equalsIgnoreCase("registry")) {
/* 417 */         populateAppConfigurationRegistry((AppParamConfigurationRegistry)appParamConfigurations);
/*     */       } 
/* 419 */       appParamConfigurations.addToConfigurationList(logConfiguration);
/*     */     } 
/* 421 */     addToConfigurationList(appParamConfigurations);
/*     */   }
/*     */   
/*     */   private void populateAppConfigurationWebstart(AppParamConfigurationWStart appParamConfigurationMail) {
/* 425 */     appParamConfigurationMail.set_home(this.cfgProperty.getProperty("home"));
/*     */   }
/*     */   
/*     */   private void populateAppConfigurationRegistry(AppParamConfigurationRegistry appParamConfigurationRegistry) {
/* 429 */     appParamConfigurationRegistry.set_keyName(this.cfgProperty.getProperty("key.name"));
/* 430 */     appParamConfigurationRegistry.set_keyPath(this.cfgProperty.getProperty("key.path"));
/* 431 */     appParamConfigurationRegistry.set_keyRemoveLevel(Integer.valueOf(this.cfgProperty.getProperty("key.remove.level")).intValue());
/* 432 */     appParamConfigurationRegistry.set_keyRemoveText(this.cfgProperty.getProperty("key.remove.text"));
/* 433 */     appParamConfigurationRegistry.set_keyType(this.cfgProperty.getProperty("key.type"));
/*     */   }
/*     */   
/*     */   public void setConfigurationForPrefix(String prefix) {
/* 437 */     if (this.cfgPropertyImpl == null) {
/*     */       return;
/*     */     }
/*     */     
/* 441 */     if (prefix != null && !prefix.equals("") && !prefix.endsWith(".")) {
/* 442 */       prefix = prefix.concat(".");
/*     */     }
/* 444 */     setCurrentPrefix(prefix);
/*     */   }
/*     */   
/*     */   public String getFullKeyForSuffixValue(String suffix, String value, int indexSearch) {
/* 448 */     String nextKey = null;
/* 449 */     int iFound = 0;
/* 450 */     boolean found = false;
/* 451 */     for (Iterator<String> iter = this.cfgProperty.getKeys().iterator(); iter.hasNext(); ) {
/* 452 */       nextKey = iter.next();
/* 453 */       if (nextKey.endsWith(suffix) && this.cfgProperty.getProperty(nextKey).equalsIgnoreCase(value) && ++iFound == indexSearch) {
/* 454 */         nextKey = this.cfgProperty.getFullKey(nextKey);
/* 455 */         found = true;
/*     */         break;
/*     */       } 
/*     */     } 
/* 459 */     if (!found)
/* 460 */       nextKey = null; 
/* 461 */     return nextKey;
/*     */   }
/*     */   
/*     */   public HashMap getHmpForKeyAndDiscriminant(String key, int discriminantLevel, boolean returnEntireKey) {
/* 465 */     HashMap<Object, Object> HmpParamsForApp = new HashMap<Object, Object>();
/*     */     
/* 467 */     String[] decomp = key.split("\\.");
/* 468 */     if (decomp.length >= discriminantLevel) {
/* 469 */       String newPrefix = ""; int i;
/* 470 */       for (i = 0; i <= discriminantLevel;) {
/* 471 */         newPrefix = newPrefix.concat(decomp[i++]).concat(".");
/*     */       }
/*     */ 
/*     */       
/* 475 */       if (newPrefix != null && this.cfgProperty.getKeys().contains(newPrefix.substring(0, newPrefix.length() - 1))) {
/* 476 */         newPrefix = "";
/* 477 */         for (i = 0; i < discriminantLevel;)
/* 478 */           newPrefix = newPrefix.concat(decomp[i++]).concat("."); 
/* 479 */         newPrefix = newPrefix.concat(decomp[discriminantLevel]);
/*     */       } 
/*     */       
/* 482 */       setCurrentPrefix(newPrefix);
/*     */       
/* 484 */       for (Iterator<String> iter = this.cfgProperty.getKeys().iterator(); iter.hasNext(); ) {
/* 485 */         key = iter.next();
/* 486 */         if (returnEntireKey) {
/* 487 */           HmpParamsForApp.put(this.cfgProperty.getFullKey(key), this.cfgProperty.getProperty(key)); continue;
/*     */         } 
/* 489 */         HmpParamsForApp.put(key, this.cfgProperty.getProperty(key));
/*     */       } 
/*     */     } 
/*     */     
/* 493 */     return HmpParamsForApp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void populateLogFileList() {
/* 500 */     populateLogFileListJWStart();
/* 501 */     populateLogFileListRegistry();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void populateLogFileListJWStart() {
/* 511 */     for (Iterator<AppParamConfigurationBase> iterCfg = this.arrlAppParamConfigurations.iterator(); iterCfg.hasNext(); ) {
/* 512 */       AppParamConfigurationBase baseConf = iterCfg.next();
/* 513 */       if (baseConf.get_container().equalsIgnoreCase("wstartclient")) {
/* 514 */         AppParamConfigurationWStart wstartConf = (AppParamConfigurationWStart)baseConf;
/* 515 */         for (Iterator<AppParamLogConfiguration> iterW = wstartConf.get_vLogConfiguration().iterator(); iterW.hasNext(); ) {
/* 516 */           AppParamLogConfiguration logConf = iterW.next();
/*     */ 
/*     */           
/* 519 */           String baseDirectory = HOMEPATH + "\\" + wstartConf.get_home() + "\\" + (logConf.get_dirBase().equals(".") ? "" : (logConf.get_dirBase() + "\\"));
/*     */           
/* 521 */           fillFilesForConfigurations(wstartConf, logConf, baseDirectory);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void populateLogFileListRegistry() {
/* 529 */     for (Iterator<AppParamConfigurationBase> iterCfg = this.arrlAppParamConfigurations.iterator(); iterCfg.hasNext(); ) {
/* 530 */       AppParamConfigurationBase baseConf = iterCfg.next();
/* 531 */       if (baseConf.get_container().equalsIgnoreCase("registry")) {
/* 532 */         AppParamConfigurationRegistry registryConf = (AppParamConfigurationRegistry)baseConf;
/* 533 */         for (Iterator<AppParamLogConfiguration> iterR = registryConf.get_vLogConfiguration().iterator(); iterR.hasNext(); ) {
/* 534 */           AppParamLogConfiguration logConf = iterR.next();
/*     */ 
/*     */           
/* 537 */           int removeIndex = 0;
/* 538 */           String regPath = registryConf.get_keyPathAsString();
/* 539 */           String baseDirectory = ReadRegPath.getKeyForRegistryPath(regPath, registryConf.get_keyName(), registryConf.get_keyType());
/*     */           
/* 541 */           if (baseDirectory == null) {
/*     */             break;
/*     */           }
/*     */           
/* 545 */           String textToRemove = registryConf.get_keyRemoveText();
/* 546 */           if (textToRemove != null && !textToRemove.equals("")) {
/* 547 */             removeIndex = baseDirectory.lastIndexOf(textToRemove);
/* 548 */             if (removeIndex > 0) {
/* 549 */               baseDirectory = baseDirectory.substring(0, removeIndex);
/*     */             }
/* 551 */           } else if (registryConf.get_keyRemoveLevel() >= 0) {
/* 552 */             for (int iRemoveLevel = 0; iRemoveLevel < registryConf.get_keyRemoveLevel(); iRemoveLevel++) {
/* 553 */               removeIndex = baseDirectory.lastIndexOf("\\");
/* 554 */               if (removeIndex > 0) {
/* 555 */                 baseDirectory = baseDirectory.substring(0, removeIndex);
/*     */               }
/*     */             } 
/*     */           } else {
/*     */             break;
/*     */           } 
/* 561 */           if (baseDirectory == null || baseDirectory.equals("")) {
/*     */             break;
/*     */           }
/*     */           
/* 565 */           fillFilesForConfigurations(registryConf, logConf, baseDirectory);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String[] getStrTabFromPattern(String patternToSplit, String defaultValue, String splitStr) {
/* 572 */     String[] decomp = null;
/* 573 */     if (patternToSplit == null || patternToSplit.equals("")) {
/* 574 */       decomp = new String[] { defaultValue };
/*     */     } else {
/* 576 */       decomp = patternToSplit.split(splitStr);
/*     */     } 
/* 578 */     for (int iterDecomp = 0; iterDecomp < decomp.length; iterDecomp++) {
/* 579 */       decomp[iterDecomp] = AppParamConfigurationUtilities.getRegexpAsStringFromDosPattern(decomp[iterDecomp], false);
/*     */     }
/* 581 */     return decomp;
/*     */   }
/*     */ 
/*     */   
/*     */   private void fillFilesForConfigurations(AppParamConfigurations configurations, AppParamLogConfiguration logConf, String baseDirectory) {
/* 586 */     String[] dirsPatterns = getStrTabFromPattern(logConf.get_dirPattern(), "*", ",");
/*     */ 
/*     */     
/* 589 */     Vector vListDirs = new Vector();
/* 590 */     for (int iterDecomp = 0; iterDecomp < dirsPatterns.length; iterDecomp++) {
/* 591 */       Vector vListDirTmp = (Vector)AppParamConfigurationUtilities.listDirectoriesForPattern(baseDirectory, dirsPatterns, logConf.get_dirModeAsInt(), true);
/* 592 */       if (vListDirTmp != null && !vListDirTmp.isEmpty()) {
/* 593 */         vListDirs.addAll(vListDirTmp);
/*     */       }
/*     */     } 
/* 596 */     vListDirs.trimToSize();
/*     */ 
/*     */     
/* 599 */     String[] filesPattern = getStrTabFromPattern(logConf.get_filePattern(), "*.log", ",");
/* 600 */     if (this.hmpLogFileList == null) {
/* 601 */       this.hmpLogFileList = new HashMap<Object, Object>();
/*     */     }
/* 603 */     ArrayList vListFiles = new ArrayList();
/* 604 */     for (Iterator<String> iterVListDir = vListDirs.iterator(); iterVListDir.hasNext(); ) {
/* 605 */       Vector vListFilesTmp = (Vector)AppParamConfigurationUtilities.listFilesDirectoryForPattern(baseDirectory, iterVListDir.next(), filesPattern, logConf.get_dirModeAsInt());
/* 606 */       if (vListFilesTmp != null && !vListFilesTmp.isEmpty()) {
/* 607 */         vListFiles.addAll(vListFilesTmp);
/*     */       }
/*     */     } 
/* 610 */     vListFiles.trimToSize();
/*     */     
/* 612 */     if (!vListFiles.isEmpty())
/* 613 */       if (this.hmpLogFileList.containsKey(configurations.get_container() + "_" + configurations.get_name())) {
/* 614 */         ArrayList arrTmpList = (ArrayList)this.hmpLogFileList.get(configurations.get_container() + "_" + configurations.get_name());
/* 615 */         arrTmpList.addAll(vListFiles);
/* 616 */         arrTmpList.trimToSize();
/* 617 */         this.hmpLogFileList.put(configurations.get_container() + "_" + configurations.get_name(), arrTmpList);
/*     */       } else {
/* 619 */         this.hmpLogFileList.put(configurations.get_container() + "_" + configurations.get_name(), vListFiles);
/*     */       }  
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\diag\client\logconf\AppParamConfigurationManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */