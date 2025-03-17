/*     */ package com.eoos.gm.tis2web.frame.implementation.service;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.ConfigurationService;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.ResourceService;
/*     */ import com.eoos.gm.tis2web.util.FileStreamFactory;
/*     */ import com.eoos.jdbc.JDBCUtil;
/*     */ import com.eoos.log.Log4jUtil;
/*     */ import com.eoos.observable.IObservableSupport;
/*     */ import com.eoos.observable.Notification;
/*     */ import com.eoos.observable.ObservableSupport;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.propcfg.TypeDecorator;
/*     */ import com.eoos.propcfg.impl.PropertiesConfigurationDBStaticImpl;
/*     */ import com.eoos.propcfg.impl.PropertiesConfigurationImpl;
/*     */ import com.eoos.propcfg.util.AccessStatisticFacade;
/*     */ import com.eoos.propcfg.util.ConfigurationChain;
/*     */ import com.eoos.propcfg.util.ConfigurationUtil;
/*     */ import com.eoos.propcfg.util.DynamicValueFacade;
/*     */ import com.eoos.propcfg.util.KeyRemovalFacade;
/*     */ import com.eoos.propcfg.util.OverwriteAssertionFacade;
/*     */ import com.eoos.propcfg.util.ReferenceValueWrapper;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.math.BigInteger;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConfigurationServiceImpl
/*     */   implements ConfigurationService
/*     */ {
/*  50 */   private static final Logger log = Logger.getLogger(ConfigurationServiceImpl.class);
/*     */   
/*  52 */   private static final Logger confLogger = Logger.getLogger(ConfigurationServiceImpl.class.getName() + ".CONFIGURATION");
/*     */   
/*  54 */   private final Object SYNC = new Object();
/*     */   
/*  56 */   private Configuration configuration = null;
/*     */   
/*  58 */   private Properties dynamicDelta = new Properties();
/*     */   
/*     */   private ResourceService resourceService;
/*     */   
/*     */   private int configHash;
/*     */   
/*  64 */   private static final Notification NOTIFICATION_MODIFIED = new Notification()
/*     */     {
/*     */       public void notify(Object observer) {
/*  67 */         ((ConfigurationService.Observer)observer).onModification();
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*  72 */   private IObservableSupport observableSupport = (IObservableSupport)new ObservableSupport();
/*     */   
/*     */   public ConfigurationServiceImpl(ResourceService resourceService) {
/*  75 */     this.resourceService = resourceService;
/*     */     
/*  77 */     init();
/*     */     
/*  79 */     ApplicationContext.getInstance().addShutdownListener(new ApplicationContext.ShutdownListener()
/*     */         {
/*     */           public void onShutdown() {
/*  82 */             if (ConfigurationServiceImpl.this.configuration != null && ConfigurationServiceImpl.this.configuration instanceof AccessStatisticFacade) {
/*  83 */               Log4jUtil.logSynchronized(ConfigurationServiceImpl.log, new Log4jUtil.Callback()
/*     */                   {
/*     */                     public void writeLog(Logger log) {
/*  86 */                       log.info("******************************************ACCESS STATISTICS");
/*  87 */                       final AccessStatisticFacade asf = (AccessStatisticFacade)ConfigurationServiceImpl.this.configuration;
/*  88 */                       Set allKeys = new HashSet(ConfigurationServiceImpl.this.configuration.getKeys());
/*  89 */                       allKeys.addAll(asf.getRequestedKeys());
/*  90 */                       List keys = CollectionUtil.toSortedList(allKeys, new Comparator()
/*     */                           {
/*     */                             public int compare(Object o1, Object o2) {
/*  93 */                               BigInteger b1 = asf.getAccessCount((String)o1);
/*  94 */                               BigInteger b2 = asf.getAccessCount((String)o2);
/*  95 */                               int ret = b2.subtract(b1).intValue();
/*  96 */                               if (ret == 0) {
/*  97 */                                 ret = ((String)o1).compareTo((String)o2);
/*     */                               }
/*     */                               
/* 100 */                               return ret;
/*     */                             }
/*     */                           });
/*     */                       
/* 104 */                       for (Iterator<String> iter = keys.iterator(); iter.hasNext(); ) {
/* 105 */                         String key = iter.next();
/* 106 */                         BigInteger accessCount = asf.getAccessCount(key);
/* 107 */                         boolean missing = (ConfigurationServiceImpl.this.configuration.getProperty(key) == null);
/* 108 */                         log.info("#" + accessCount + "\t" + key + "\t" + (missing ? " !!UNDEFINED!!" : ""));
/*     */                       } 
/* 110 */                       log.info("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
/*     */                     }
/*     */                   });
/*     */             }
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void init() {
/*     */     try {
/* 124 */       this.configuration = createConfiguration();
/* 125 */       this.configHash = ConfigurationUtil.configurationHash(this.configuration);
/*     */       
/* 127 */       this.configuration = (Configuration)new AccessStatisticFacade(this.configuration);
/* 128 */     } catch (Exception e) {
/* 129 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void dumpConfiguration() {
/* 134 */     if (confLogger.isDebugEnabled()) {
/* 135 */       confLogger.debug("");
/* 136 */       confLogger.debug("********************************************************************************BEGIN");
/* 137 */       List<Comparable> keys = new LinkedList(this.configuration.getKeys());
/* 138 */       Collections.sort(keys);
/* 139 */       for (Iterator<Comparable> iter = keys.iterator(); iter.hasNext(); ) {
/* 140 */         String key = (String)iter.next();
/* 141 */         String value = this.configuration.getProperty(key);
/* 142 */         if (ConfigurationUtil.isPossiblyPasswordKey(key)) {
/* 143 */           value = "**********";
/*     */         }
/* 145 */         confLogger.debug(key + "=" + String.valueOf(value));
/*     */       } 
/* 147 */       confLogger.debug(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
/*     */     } 
/*     */   }
/*     */   
/*     */   private PropertiesConfigurationImpl createCfg_Resource(String resourceName) throws IOException {
/* 152 */     log.debug("...creating configuration from resource: " + String.valueOf(resourceName));
/* 153 */     Properties cfgProperties = new Properties();
/* 154 */     InputStream is = this.resourceService.getResource(resourceName);
/*     */     try {
/* 156 */       cfgProperties.load(FileStreamFactory.getInstance().decode(is));
/*     */     } finally {
/* 158 */       is.close();
/*     */     } 
/* 160 */     return new PropertiesConfigurationImpl(cfgProperties);
/*     */   }
/*     */   
/*     */   private static Configuration createCfg_DB(IDatabaseLink dblink, Configuration cfg) throws Exception {
/* 164 */     log.debug("...creating configuration from db: " + dblink.getDatabaseLinkDescription());
/* 165 */     String table = cfg.getProperty("table.name");
/* 166 */     if (table == null || table.trim().length() == 0) {
/* 167 */       table = "configuration";
/*     */     }
/* 169 */     String cnKey = cfg.getProperty("col.name.key");
/* 170 */     if (cnKey == null || cnKey.trim().length() == 0) {
/* 171 */       cnKey = "key";
/*     */     }
/* 173 */     String cnValue = cfg.getProperty("col.name.value");
/* 174 */     if (cnValue == null || cnValue.trim().length() == 0) {
/* 175 */       cnValue = "value";
/*     */     }
/*     */     
/* 178 */     Connection connection = dblink.requestConnection_ConfigurationService();
/*     */     try {
/* 180 */       Properties properties = new Properties();
/* 181 */       PreparedStatement stmt = connection.prepareStatement("SELECT " + cnKey + ", " + cnValue + " FROM " + table);
/*     */       try {
/* 183 */         ResultSet rs = stmt.executeQuery();
/*     */         try {
/* 185 */           while (rs.next()) {
/* 186 */             String key = rs.getString(1);
/* 187 */             String value = rs.getString(2);
/* 188 */             if (value == null) {
/* 189 */               value = "";
/*     */             }
/* 191 */             properties.put(key, value);
/*     */           } 
/* 193 */           return (Configuration)new PropertiesConfigurationDBStaticImpl(properties);
/*     */         } finally {
/* 195 */           JDBCUtil.close(rs);
/*     */         } 
/*     */       } finally {
/* 198 */         JDBCUtil.close(stmt);
/*     */       } 
/*     */     } finally {
/* 201 */       dblink.releaseConnection_ConfigurationService(connection);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Configuration createConfigurationChain(final Configuration masterCfg) throws Exception {
/* 207 */     List<String> ids = new LinkedList();
/* 208 */     for (Iterator<String> iterator1 = ConfigurationUtil.getKeys(masterCfg, "config.", ".resource").iterator(); iterator1.hasNext(); ) {
/* 209 */       String key = iterator1.next();
/* 210 */       ids.add(key.split("\\.")[1]);
/*     */     } 
/* 212 */     for (Iterator<String> iter = ConfigurationUtil.getKeys(masterCfg, "config.", ".db.url").iterator(); iter.hasNext(); ) {
/* 213 */       String key = iter.next();
/* 214 */       ids.add(key.split("\\.")[1]);
/*     */     } 
/*     */     
/* 217 */     if (ids.size() > 0) {
/* 218 */       log.debug("...initializing config chain ...");
/*     */       
/* 220 */       Collections.sort(ids, new Comparator<String>()
/*     */           {
/*     */             public int compare(Object o1, Object o2) {
/* 223 */               int i1 = 9999;
/*     */               try {
/* 225 */                 i1 = TypeDecorator.getInt(masterCfg, "config." + o1 + ".order");
/* 226 */               } catch (Exception e) {}
/*     */               
/* 228 */               int i2 = 9999;
/*     */               try {
/* 230 */                 i2 = TypeDecorator.getInt(masterCfg, "config." + o2 + ".order");
/* 231 */               } catch (Exception e) {}
/*     */               
/* 233 */               return i1 - i2;
/*     */             }
/*     */           });
/*     */ 
/*     */ 
/*     */       
/* 239 */       Configuration[] cfgs = new Configuration[ids.size() + 1];
/* 240 */       cfgs[ids.size()] = masterCfg;
/* 241 */       for (int i = 0; i < ids.size(); i++) {
/* 242 */         log.debug("...initializing config: " + ids.get(i));
/* 243 */         if (masterCfg.getProperty("config." + ids.get(i) + ".resource") != null) {
/* 244 */           cfgs[i] = createConfigurationChain((Configuration)createCfg_Resource(masterCfg.getProperty("config." + ids.get(i) + ".resource")));
/* 245 */         } else if (masterCfg.getProperty("config." + ids.get(i) + ".db.url") != null) {
/* 246 */           DatabaseLink databaseLink = DatabaseLink.openDatabase(masterCfg, "config." + ids.get(i) + ".db");
/* 247 */           cfgs[i] = createConfigurationChain(createCfg_DB((IDatabaseLink)databaseLink, (Configuration)new SubConfigurationWrapper(masterCfg, "config." + ids.get(i) + ".db.")));
/*     */         } else {
/* 249 */           log.warn("unable to initialize config: " + ids.get(i) + " (unable to determine type)");
/*     */         } 
/*     */       } 
/* 252 */       return (Configuration)new ConfigurationChain(cfgs);
/*     */     } 
/*     */     
/* 255 */     return masterCfg;
/*     */   }
/*     */   
/*     */   private Configuration createConfiguration() throws Exception {
/*     */     ConfigurationChain configurationChain;
/* 260 */     log.debug("creating configuration ...");
/*     */     
/* 262 */     log.debug("... basic configuration (resource: configuration.properties)");
/* 263 */     Configuration ret = createConfigurationChain((Configuration)createCfg_Resource("/configuration.properties"));
/*     */     
/* 265 */     if (this.dynamicDelta.size() > 0) {
/* 266 */       configurationChain = new ConfigurationChain(new Configuration[] { (Configuration)new PropertiesConfigurationImpl(this.dynamicDelta), ret });
/*     */     }
/* 268 */     ReferenceValueWrapper referenceValueWrapper = new ReferenceValueWrapper((Configuration)configurationChain);
/* 269 */     KeyRemovalFacade keyRemovalFacade = new KeyRemovalFacade((Configuration)referenceValueWrapper);
/* 270 */     OverwriteAssertionFacade overwriteAssertionFacade = new OverwriteAssertionFacade((Configuration)keyRemovalFacade);
/* 271 */     DynamicValueFacade dynamicValueFacade = new DynamicValueFacade((Configuration)overwriteAssertionFacade)
/*     */       {
/*     */         protected String getReplacement(String key)
/*     */         {
/* 275 */           if ("PORT".equalsIgnoreCase(key))
/* 276 */             return String.valueOf(ApplicationContext.getInstance().getPort()); 
/* 277 */           if ("CLUSTER_URL".equals(key)) {
/* 278 */             return ApplicationContext.getInstance().getLocalURL().toString();
/*     */           }
/* 280 */           return super.getReplacement(key);
/*     */         }
/*     */       };
/*     */ 
/*     */ 
/*     */     
/* 286 */     log.debug("...done creating configuration");
/* 287 */     return (Configuration)dynamicValueFacade;
/*     */   }
/*     */ 
/*     */   
/*     */   private Configuration getConfiguration() {
/* 292 */     synchronized (this.SYNC) {
/* 293 */       return this.configuration;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getProperty(String key) {
/* 298 */     return getConfiguration().getProperty(key);
/*     */   }
/*     */   
/*     */   public Set getKeys() {
/* 302 */     return getConfiguration().getKeys();
/*     */   }
/*     */   
/*     */   public Object getIdentifier() {
/* 306 */     return getClass();
/*     */   }
/*     */   
/*     */   public void addObserver(ConfigurationService.Observer observer) {
/* 310 */     this.observableSupport.addObserver(observer);
/*     */   }
/*     */   
/*     */   public void removeObserver(ConfigurationService.Observer observer) {
/* 314 */     this.observableSupport.removeObserver(observer);
/*     */   }
/*     */   
/*     */   public void update() {
/* 318 */     boolean notify = false;
/* 319 */     synchronized (this.SYNC) {
/* 320 */       int oldHash = this.configHash;
/* 321 */       init();
/* 322 */       notify = (this.configHash != oldHash);
/*     */       
/* 324 */       if (notify) {
/* 325 */         log.info("...configuration has changed, notifying observers");
/* 326 */         this.observableSupport.notifyObservers(NOTIFICATION_MODIFIED, IObservableSupport.Mode.ASYNCHRONOUS_NOTIFY);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFullKey(String key) {
/* 333 */     return this.configuration.getFullKey(key);
/*     */   }
/*     */   
/*     */   public void remove(String key) {
/* 337 */     this.dynamicDelta.setProperty(key, "#remove");
/* 338 */     update();
/*     */   }
/*     */   
/*     */   public void setProperty(String key, String value) {
/* 342 */     this.dynamicDelta.setProperty(key, value);
/* 343 */     update();
/*     */   }
/*     */   
/*     */   public void setProperties(Properties properties) {
/* 347 */     if (properties == null) {
/* 348 */       this.dynamicDelta.clear();
/*     */     } else {
/* 350 */       this.dynamicDelta.putAll(properties);
/*     */     } 
/* 352 */     update();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\service\ConfigurationServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */