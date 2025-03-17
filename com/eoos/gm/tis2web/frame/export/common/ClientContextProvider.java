/*     */ package com.eoos.gm.tis2web.frame.export.common;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClientContextProvider
/*     */ {
/*  16 */   private static final Logger log = Logger.getLogger(ClientContextProvider.class);
/*     */   
/*  18 */   private static ClientContextProvider instance = new ClientContextProvider();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  26 */   public long newContextCount = 0L;
/*     */   
/*  28 */   public long logoutContextCount = 0L;
/*     */   
/*  30 */   private Map instances = new ConcurrentHashMap<Object, Object>();
/*     */   
/*  32 */   private PersistentInformation persistenceCallback = new PersistentInformationImpl();
/*     */   
/*     */   private ClientContextProvider() {
/*  35 */     ApplicationContext.getInstance().addShutdownListener(new ApplicationContext.ShutdownListener() {
/*     */           public void onShutdown() {
/*  37 */             ClientContextProvider.this.invalidateAllSessions();
/*  38 */             ClientContextProvider.instance = null;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public static ClientContextProvider getInstance() {
/*  45 */     return instance;
/*     */   } public static interface PersistentInformation {
/*     */     void load(ClientContext param1ClientContext); void save(ClientContext param1ClientContext); }
/*     */   public ClientContext getContext(String sessionID) {
/*  49 */     return getContext(sessionID, false);
/*     */   }
/*     */   
/*     */   public ClientContext getContext(String sessionID, boolean create) {
/*  53 */     return getContext(sessionID, create, false, false);
/*     */   }
/*     */   
/*     */   public ClientContext getContext(String sessionID, boolean create, boolean publicAccess, boolean specialAccess) {
/*  57 */     ClientContext instance = (ClientContext)this.instances.get(sessionID);
/*  58 */     if (instance == null && create) {
/*  59 */       if (publicAccess) {
/*  60 */         instance = new ClientContextPublicImpl(sessionID);
/*     */       } else {
/*  62 */         instance = new ClientContextImpl(sessionID, specialAccess);
/*     */       } 
/*  64 */       if (instance instanceof ClientContext.Persistent) {
/*  65 */         this.persistenceCallback.load(instance);
/*     */       }
/*     */       
/*  68 */       this.instances.put(sessionID, instance);
/*  69 */       this.newContextCount++;
/*     */     } 
/*     */     
/*  72 */     return (instance != null) ? getProxy(instance) : null;
/*     */   }
/*     */   
/*     */   public ClientContext getTmpContext(String sessionID) {
/*  76 */     return getTmpContext(sessionID, null);
/*     */   }
/*     */   
/*     */   public ClientContext getTmpContext(String sessionID, Locale locale) {
/*  80 */     ClientContext ret = new ClientContextImpl(sessionID, false);
/*  81 */     if (locale != null) {
/*  82 */       ret.setLocale(locale);
/*     */     }
/*  84 */     return ret;
/*     */   }
/*     */   
/*     */   private ClientContextProxy getProxy(ClientContext context) {
/*  88 */     ClientContextProxy instance = (ClientContextProxy)context.getObject(ClientContextProxy.class);
/*  89 */     if (instance == null) {
/*  90 */       instance = new ClientContextProxy(context);
/*  91 */       context.storeObject(ClientContextProxy.class, instance);
/*     */     } 
/*  93 */     return instance;
/*     */   }
/*     */   
/*     */   public void invalidateAllSessions() {
/*  97 */     Iterator<?> iter = (new HashSet(this.instances.keySet())).iterator();
/*  98 */     while (iter.hasNext()) {
/*  99 */       invalidateSession((String)iter.next());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean invalidateSession(String sessionID) {
/* 105 */     ClientContext instance = (ClientContext)this.instances.remove(sessionID);
/* 106 */     if (instance != null) {
/* 107 */       if (instance instanceof ClientContext.Persistent) {
/* 108 */         log.debug("saving context" + instance);
/* 109 */         this.persistenceCallback.save(instance);
/*     */       } 
/* 111 */       log.debug("invalidating " + instance);
/* 112 */       if (instance instanceof ClientContext.Logout) {
/* 113 */         ((ClientContext.Logout)instance).logout();
/*     */       }
/* 115 */       this.logoutContextCount++;
/*     */     } else {
/*     */       
/* 118 */       log.debug("session already have been invalidated");
/*     */     } 
/*     */     
/* 121 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isActive(String sessionID) {
/* 125 */     boolean retValue = true;
/*     */     
/* 127 */     ClientContext instance = (ClientContext)this.instances.get(sessionID);
/* 128 */     retValue = (retValue && instance != null);
/* 129 */     retValue = (retValue && instance.isActive());
/*     */     
/* 131 */     return retValue;
/*     */   }
/*     */   
/*     */   public long getLastAccess(String sessionID) throws InvalidSessionException {
/* 135 */     ClientContext instance = (ClientContext)this.instances.get(sessionID);
/* 136 */     if (instance != null) {
/* 137 */       return instance.getLastAccess();
/*     */     }
/* 139 */     throw new InvalidSessionException(sessionID);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPublicAccess(String sessionID) {
/* 144 */     ClientContext instance = (ClientContext)this.instances.get(sessionID);
/* 145 */     return (instance != null && instance.isPublicAccess());
/*     */   }
/*     */   
/*     */   public int currentPublicAccessCount(String prefix) {
/* 149 */     int count = 0;
/* 150 */     Set contexts = new HashSet(this.instances.values());
/* 151 */     for (Iterator<ClientContext> iter = contexts.iterator(); iter.hasNext(); ) {
/* 152 */       ClientContext context = iter.next();
/* 153 */       if (context != null && context.isPublicAccess() && context.isActive() && (
/* 154 */         prefix == null || context.getSessionID().startsWith(prefix))) {
/* 155 */         count++;
/*     */       }
/*     */     } 
/*     */     
/* 159 */     return count;
/*     */   }
/*     */   
/*     */   public int currentSessionCount() {
/* 163 */     return this.instances.size();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\ClientContextProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */