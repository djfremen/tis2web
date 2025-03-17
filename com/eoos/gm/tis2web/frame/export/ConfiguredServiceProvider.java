/*     */ package com.eoos.gm.tis2web.frame.export;
/*     */ 
/*     */ import com.eoos.cache.Cache;
/*     */ import com.eoos.cache.SynchronizedCache;
/*     */ import com.eoos.cache.implementation.FixedCacheImpl;
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.datatype.marker.Configurable;
/*     */ import com.eoos.filter.Filter;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.service.Service;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.ConfigurationService;
/*     */ import com.eoos.instantiation.Singleton;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.util.ClassUtil;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConfiguredServiceProvider
/*     */ {
/*  31 */   private static final Logger log = Logger.getLogger(ConfiguredServiceProvider.class);
/*     */   
/*  33 */   private final Object SYNC_SERVICES = new Object();
/*     */   
/*  35 */   private Set serviceImplementations = null;
/*     */ 
/*     */   
/*  38 */   private Cache responseCache = (Cache)new SynchronizedCache((Cache)new FixedCacheImpl());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/*  44 */     getServices();
/*     */   }
/*     */   
/*     */   private Set getServices() {
/*  48 */     synchronized (this.SYNC_SERVICES) {
/*     */       
/*  50 */       if (this.serviceImplementations == null) {
/*     */         
/*     */         try {
/*  53 */           log.debug("initializing configured services");
/*  54 */           this.serviceImplementations = Collections.synchronizedSet(new LinkedHashSet());
/*     */ 
/*     */           
/*  57 */           Configuration configuration = (Configuration)FrameServiceProvider.getInstance().getService(ConfigurationService.class);
/*     */           
/*  59 */           SubConfigurationWrapper subConfigurationWrapper = new SubConfigurationWrapper(configuration, "component.");
/*  60 */           for (Iterator<?> iter = (new TreeSet(subConfigurationWrapper.getKeys())).iterator(); iter.hasNext(); ) {
/*  61 */             String key = (String)iter.next();
/*  62 */             if (key.endsWith(".implementation.class") && key.indexOf(".adapter.") == -1) {
/*  63 */               final String id = key.substring(0, key.length() - ".implementation.class".length());
/*     */               try {
/*  65 */                 SubConfigurationWrapper subConfigurationWrapper1 = new SubConfigurationWrapper(configuration, "component." + id + ".") {
/*     */                     public String getProperty(String key) {
/*  67 */                       if (key.equals("id")) {
/*  68 */                         return id;
/*     */                       }
/*  70 */                       return super.getProperty(key);
/*     */                     }
/*     */ 
/*     */                     
/*     */                     public Set getKeys() {
/*  75 */                       Set<String> retValue = super.getKeys();
/*  76 */                       retValue.add("id");
/*  77 */                       return retValue;
/*     */                     }
/*     */                   };
/*     */ 
/*     */                 
/*  82 */                 Class<?> clazz = Class.forName(subConfigurationWrapper1.getProperty("implementation.class"));
/*     */ 
/*     */                 
/*  85 */                 Service service = null;
/*  86 */                 Collection interfaces = ClassUtil.getAllInterfaces(clazz);
/*  87 */                 if (interfaces.contains(Configurable.class)) {
/*  88 */                   if (interfaces.contains(Singleton.class)) {
/*  89 */                     service = (Service)clazz.getMethod("createInstance", new Class[] { Configuration.class }).invoke(null, new Object[] { subConfigurationWrapper1 });
/*     */                   } else {
/*  91 */                     service = clazz.getConstructor(new Class[] { Configuration.class }).newInstance(new Object[] { subConfigurationWrapper1 });
/*     */                   }
/*     */                 
/*  94 */                 } else if (interfaces.contains(Singleton.class)) {
/*  95 */                   service = (Service)clazz.getMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
/*     */                 } else {
/*  97 */                   service = (Service)clazz.newInstance();
/*     */                 } 
/*     */ 
/*     */                 
/* 101 */                 this.serviceImplementations.add(service);
/* 102 */                 log.info("added configured service: " + id);
/* 103 */               } catch (Exception e) {
/* 104 */                 log.error("error occured instantiating service :" + id + " - skipping service - error:" + e, e);
/*     */               }
/*     */             
/*     */             }
/*     */           
/*     */           }
/*     */         
/* 111 */         } catch (Exception e) {
/* 112 */           log.error("unable to init - error:" + e, e);
/* 113 */           throw new ExceptionWrapper(e);
/*     */         } 
/*     */       }
/*     */       
/* 117 */       return this.serviceImplementations;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static ConfiguredServiceProvider getInstance() {
/* 123 */     ConfiguredServiceProvider instance = (ConfiguredServiceProvider)ApplicationContext.getInstance().getObject(ConfiguredServiceProvider.class);
/* 124 */     if (instance == null) {
/* 125 */       instance = new ConfiguredServiceProvider();
/* 126 */       ApplicationContext.getInstance().storeObject(ConfiguredServiceProvider.class, instance);
/*     */     } 
/* 128 */     return instance;
/*     */   }
/*     */   
/*     */   private Collection getServices(Filter filter) {
/* 132 */     return CollectionUtil.filterAndReturn(new LinkedHashSet(getServices()), filter);
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection getServices(final Class serviceInterface) {
/* 137 */     Filter filter = new Filter()
/*     */       {
/*     */         public boolean include(Object obj) {
/* 140 */           boolean retValue = false;
/*     */           try {
/* 142 */             retValue = serviceInterface.isAssignableFrom(obj.getClass());
/* 143 */           } catch (Exception e) {
/* 144 */             ConfiguredServiceProvider.log.warn("unable to determine inclusion status, returning false - exception: " + e, e);
/*     */           } 
/*     */           
/* 147 */           return retValue;
/*     */         }
/*     */       };
/*     */     
/* 151 */     return getServices(filter);
/*     */   }
/*     */   
/*     */   public Service getService(Class serviceInterface) {
/* 155 */     Service service = (Service)this.responseCache.lookup(serviceInterface);
/* 156 */     if (service == null) {
/* 157 */       service = (Service)CollectionUtil.getFirst(getServices(serviceInterface));
/* 158 */       this.responseCache.store(serviceInterface, service);
/*     */     } 
/*     */     
/* 161 */     return service;
/*     */   }
/*     */   
/*     */   public Service getService(final Object identifier) {
/* 165 */     Service service = (Service)this.responseCache.lookup(identifier);
/* 166 */     if (service == null) {
/* 167 */       Filter filter = new Filter() {
/*     */           public boolean include(Object obj) {
/* 169 */             boolean retValue = false;
/*     */             try {
/* 171 */               retValue = identifier.equals(((Service)obj).getIdentifier());
/* 172 */             } catch (Exception e) {
/* 173 */               ConfiguredServiceProvider.log.warn("unable to determine inclusion status, returning false - exception: " + e, e);
/*     */             } 
/*     */             
/* 176 */             return retValue;
/*     */           }
/*     */         };
/* 179 */       service = (Service)CollectionUtil.getFirst(getServices(filter));
/* 180 */       this.responseCache.store(identifier, service);
/*     */     } 
/* 182 */     return service;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\ConfiguredServiceProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */