/*     */ package com.eoos.gm.tis2web.swdl.server.datamodel.system;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.ConfigurationService;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.device.Device;
/*     */ import com.eoos.gm.tis2web.swdl.server.db.DatabaseAdapter;
/*     */ import com.eoos.gm.tis2web.swdl.server.db.DatabaseAdapterImpl;
/*     */ import com.eoos.gm.tis2web.swdl.server.db.DatabaseAdapter_CacheImpl;
/*     */ import com.eoos.observable.IObservableSupport;
/*     */ import com.eoos.observable.Notification;
/*     */ import com.eoos.observable.ObservableSupport;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.propcfg.util.ConfigurationUtil;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DeviceRegistry
/*     */ {
/*  45 */   private static final Notification NOTIFICATION_MODIFIED = new Notification()
/*     */     {
/*     */       public void notify(Object observer) {
/*  48 */         ((DeviceRegistry.Observer)observer).onModification();
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*  53 */   private static final Logger log = Logger.getLogger(DeviceRegistry.class);
/*     */   
/*  55 */   private static DeviceRegistry instance = null;
/*     */   
/*  57 */   private final IObservableSupport observableSupport = (IObservableSupport)new ObservableSupport();
/*     */   
/*     */   protected class State {
/*  60 */     public Map deviceToDBAdapters = null;
/*     */     
/*  62 */     private int configHash = 0;
/*     */     
/*  64 */     private Collection configKeys = new LinkedList();
/*     */   }
/*     */ 
/*     */   
/*  68 */   private final Object SYNC_STATE = new Object();
/*     */   
/*  70 */   private State state = null;
/*     */ 
/*     */   
/*     */   private DeviceRegistry() {
/*  74 */     ConfigurationService configService = (ConfigurationService)FrameServiceProvider.getInstance().getService(ConfigurationService.class);
/*  75 */     configService.addObserver(new ConfigurationService.Observer()
/*     */         {
/*     */           public void onModification() {
/*  78 */             synchronized (DeviceRegistry.this.SYNC_STATE) {
/*  79 */               if (DeviceRegistry.this.state != null && DeviceRegistry
/*  80 */                 .configHash(DeviceRegistry.this.state.configKeys) != DeviceRegistry.this.state.configHash) {
/*  81 */                 DeviceRegistry.log.info("configuration changed - resetting and notifying observers");
/*  82 */                 DeviceRegistry.this.reset();
/*     */               } 
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int configHash(Collection keys) {
/*  93 */     ConfigurationService configService = (ConfigurationService)FrameServiceProvider.getInstance().getService(ConfigurationService.class);
/*  94 */     return ConfigurationUtil.configurationHash((Configuration)configService, keys, ConfigurationUtil.MODE_PREFIXES);
/*     */   }
/*     */   
/*     */   protected State getState() {
/*  98 */     synchronized (this.SYNC_STATE) {
/*  99 */       if (this.state == null) {
/* 100 */         log.info("initializing");
/* 101 */         this.state = new State();
/* 102 */         this.state.deviceToDBAdapters = Collections.synchronizedMap(new HashMap<Object, Object>());
/*     */         
/* 104 */         Set<String> databaseIDs = new HashSet();
/* 105 */         this.state.configKeys.add("component.swdl.adapter");
/* 106 */         SubConfigurationWrapper subConfigurationWrapper = new SubConfigurationWrapper((Configuration)ApplicationContext.getInstance(), "component.swdl.adapter.");
/*     */         
/* 108 */         for (Iterator<String> iterator1 = subConfigurationWrapper.getKeys().iterator(); iterator1.hasNext(); ) {
/* 109 */           String key = iterator1.next();
/* 110 */           int index = key.indexOf(".db");
/* 111 */           if (index != -1) {
/* 112 */             String id = key.substring(0, index);
/* 113 */             if (!id.endsWith("swdl_languages")) {
/* 114 */               databaseIDs.add(id);
/*     */             }
/*     */           } 
/*     */         } 
/* 118 */         if (log.isDebugEnabled()) {
/* 119 */           log.debug("...constructing and registering database adapters for databases:" + databaseIDs);
/*     */         }
/* 121 */         for (Iterator<String> iter = databaseIDs.iterator(); iter.hasNext();) {
/*     */           try {
/* 123 */             DatabaseAdapter_CacheImpl databaseAdapter_CacheImpl; String id = iter.next();
/* 124 */             Device device = Device.getTech(subConfigurationWrapper.getProperty(id + ".device"));
/* 125 */             Set<String> resourceIDs = new HashSet();
/* 126 */             SubConfigurationWrapper subConfigurationWrapper1 = new SubConfigurationWrapper((Configuration)subConfigurationWrapper, id + ".acl.resource.id.");
/* 127 */             for (Iterator<String> iterResourceEntry = subConfigurationWrapper1.getKeys().iterator(); iterResourceEntry.hasNext(); ) {
/* 128 */               String key = iterResourceEntry.next();
/* 129 */               resourceIDs.add(subConfigurationWrapper1.getProperty(key));
/*     */             } 
/* 131 */             DatabaseAdapter dbAdapter = null;
/* 132 */             String prefix = "component.swdl.adapter." + id + ".db";
/* 133 */             DatabaseLink databaseLink = DatabaseLink.openDatabase((Configuration)ApplicationContext.getInstance(), prefix);
/* 134 */             if (ApplicationContext.getInstance().isStandalone()) {
/* 135 */               String version = subConfigurationWrapper.getProperty(id + "." + "db.version");
/* 136 */               String description = subConfigurationWrapper.getProperty(id + "." + "db.description");
/* 137 */               databaseAdapter_CacheImpl = new DatabaseAdapter_CacheImpl(new DatabaseAdapterImpl(device.getDescription(), (IDatabaseLink)databaseLink, description, version, resourceIDs));
/*     */             } else {
/* 139 */               databaseAdapter_CacheImpl = new DatabaseAdapter_CacheImpl(new DatabaseAdapterImpl(device.getDescription(), (IDatabaseLink)databaseLink, resourceIDs));
/*     */             } 
/* 141 */             Set<?> adapters = (Set)this.state.deviceToDBAdapters.get(device);
/* 142 */             if (adapters == null) {
/* 143 */               adapters = Collections.synchronizedSet(new HashSet());
/* 144 */               this.state.deviceToDBAdapters.put(device, adapters);
/*     */             } 
/* 146 */             adapters.add(databaseAdapter_CacheImpl);
/*     */           }
/* 148 */           catch (Exception e) {
/* 149 */             log.error("unable to init swdl database adapter - error:" + e, e);
/*     */           } 
/*     */         } 
/* 152 */         this.state.configHash = configHash(this.state.configKeys);
/*     */         
/* 154 */         if (log.isDebugEnabled()) {
/* 155 */           log.debug("....finished initialization - device registry contents:" + this.state.deviceToDBAdapters);
/*     */         }
/*     */       } 
/*     */       
/* 159 */       return this.state;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static synchronized DeviceRegistry getInstance() {
/* 165 */     if (instance == null) {
/* 166 */       instance = new DeviceRegistry();
/*     */     }
/* 168 */     return instance;
/*     */   }
/*     */   
/*     */   public Set getDevices() {
/* 172 */     return new HashSet((getState()).deviceToDBAdapters.keySet());
/*     */   }
/*     */   
/*     */   public Set getDatabaseAdapters(Device device) {
/* 176 */     return (Set)(getState()).deviceToDBAdapters.get(device);
/*     */   }
/*     */   
/*     */   public Set getDatabaseAdapters() {
/* 180 */     Set result = new HashSet();
/* 181 */     for (Iterator<Device> iter = (getState()).deviceToDBAdapters.keySet().iterator(); iter.hasNext(); ) {
/* 182 */       Device element = iter.next();
/* 183 */       Set adapters = getDatabaseAdapters(element);
/* 184 */       result.addAll(adapters);
/*     */     } 
/* 186 */     return result;
/*     */   }
/*     */   
/*     */   public void addObserver(Observer observer) {
/* 190 */     this.observableSupport.addObserver(observer);
/*     */   }
/*     */   
/*     */   public void removeObserver(Observer observer) {
/* 194 */     this.observableSupport.removeObserver(observer);
/*     */   }
/*     */   
/*     */   public void reset() {
/* 198 */     synchronized (this.SYNC_STATE) {
/* 199 */       this.state = null;
/*     */     } 
/* 201 */     this.observableSupport.notifyObservers(NOTIFICATION_MODIFIED, IObservableSupport.Mode.ASYNCHRONOUS_NOTIFY);
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/* 206 */     getState();
/*     */   }
/*     */   
/*     */   public static interface Observer {
/*     */     void onModification();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\server\datamodel\system\DeviceRegistry.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */