/*     */ package com.eoos.gm.tis2web.frame.implementation.service;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.ModuleInformation;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.FrameService;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.StorageService;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FrameServiceImpl
/*     */   implements FrameService
/*     */ {
/*     */   private String type;
/*     */   private String id;
/*  25 */   private static final Object DISPLAY_HEIGHT = "displayheigth";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getType() {
/*  33 */     return this.type;
/*     */   }
/*     */   
/*     */   public String getID() {
/*  37 */     return this.id;
/*     */   }
/*     */   
/*     */   public boolean isActive(String sessionID) {
/*  41 */     return ClientContextProvider.getInstance().isActive(sessionID);
/*     */   }
/*     */   
/*     */   public Boolean setUsrGroup2ManufMap(Map usrGroup2Manuf, String sessionID) {
/*  45 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/*  46 */     context.storeObject("Frame.UsrGroup2Manuf.ID", usrGroup2Manuf);
/*  47 */     return new Boolean(true);
/*     */   }
/*     */   
/*     */   public Map getUsrGroup2ManufMap(String sessionID) {
/*  51 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/*  52 */     return (Map)context.getObject("Frame.UsrGroup2Manuf.ID");
/*     */   }
/*     */   
/*     */   public synchronized Boolean setObject(Serializable o, String sessionID) {
/*  56 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/*  57 */     Object<?> id = (Object<?>)o.getClass();
/*  58 */     context.storeObject(id, o);
/*  59 */     return new Boolean(true);
/*     */   }
/*     */   
/*     */   public synchronized Object getObject(Object id, String sessionID) {
/*  63 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/*  64 */     return context.getObject(id);
/*     */   }
/*     */   
/*     */   public Locale getLocale(String sessionID) {
/*  68 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/*     */     
/*  70 */     Locale locale = (Locale)context.getObject("Frame.Locale.ID");
/*     */     
/*  72 */     return locale;
/*     */   }
/*     */   
/*     */   public Boolean setLocale(Locale locale, String sessionID) {
/*  76 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/*     */     
/*  78 */     context.storeObject("Frame.Locale.ID", locale);
/*     */ 
/*     */     
/*  81 */     return new Boolean(true);
/*     */   }
/*     */   
/*     */   public String getCountry(String sessionID) {
/*  85 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/*  86 */     return (String)context.getObject("Frame.Country.ID");
/*     */   }
/*     */   
/*     */   public Boolean setCountry(String country, String sessionID) {
/*  90 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/*  91 */     context.storeObject("Frame.Country.ID", country);
/*  92 */     return new Boolean(true);
/*     */   }
/*     */   
/*     */   public Integer getDisplayHeight(String sessionID) {
/*  96 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/*  97 */     return (Integer)context.getObject(DISPLAY_HEIGHT);
/*     */   }
/*     */   
/*     */   public Boolean setDisplayHeight(Integer height, String sessionID) {
/* 101 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/* 102 */     context.storeObject(DISPLAY_HEIGHT, height);
/* 103 */     return new Boolean(true);
/*     */   }
/*     */   
/*     */   public boolean invalidateSession(String sessionID) {
/* 107 */     return ClientContextProvider.getInstance().invalidateSession(sessionID);
/*     */   }
/*     */   
/*     */   public Boolean setPersistentObject(String sessionID, Serializable key, Serializable object) {
/* 111 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/* 112 */     synchronized (context.getLockObject()) {
/* 113 */       Map<Object, Object> persistentObjects = (Map)context.getObject("persistent.objects");
/* 114 */       if (persistentObjects == null) {
/* 115 */         persistentObjects = new HashMap<Object, Object>();
/* 116 */         context.storeObject("persistent.objects", persistentObjects);
/*     */       } 
/* 118 */       persistentObjects.put(key, object);
/*     */     } 
/* 120 */     return new Boolean(false);
/*     */   }
/*     */   
/*     */   public Serializable getPersistentObject(String sessionID, Serializable key) {
/* 124 */     Serializable retValue = null;
/*     */     
/* 126 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/* 127 */     synchronized (context.getLockObject()) {
/* 128 */       Map persistentObjects = (Map)context.getObject("persistent.objects");
/* 129 */       if (persistentObjects != null) {
/* 130 */         retValue = (Serializable)persistentObjects.get(key);
/*     */       }
/*     */     } 
/* 133 */     return retValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean setTextEncoding(Integer encoding, String sessionID) {
/* 143 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/* 144 */     context.storeObject("text.download.encoding", encoding);
/* 145 */     return new Boolean(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getTextEncoding(String sessionID) {
/* 154 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/* 155 */     return (Integer)context.getObject("text.download.encoding");
/*     */   }
/*     */ 
/*     */   
/*     */   public ModuleInformation getModuleInformation() {
/* 160 */     return null;
/*     */   }
/*     */   
/*     */   public Object getIdentifier() {
/* 164 */     return getID();
/*     */   }
/*     */   
/*     */   public void storePersistentObject(String key, Serializable object) throws Exception {
/* 168 */     StorageService storage = (StorageService)FrameServiceProvider.getInstance().getService(StorageService.class);
/* 169 */     StorageService.ObjectStore objectStore = storage.getObjectStoreFacade();
/* 170 */     objectStore.store(key, object);
/*     */   }
/*     */   
/*     */   public Object getPersistentObject(String key) throws Exception {
/* 174 */     StorageService storage = (StorageService)FrameServiceProvider.getInstance().getService(StorageService.class);
/* 175 */     StorageService.ObjectStore objectStore = storage.getObjectStoreFacade();
/* 176 */     return objectStore.load(key);
/*     */   }
/*     */   
/*     */   public boolean registerUID(String id, long validity) throws IOException {
/* 180 */     return UIDStore.getInstance().registerUID(id, validity);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\service\FrameServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */