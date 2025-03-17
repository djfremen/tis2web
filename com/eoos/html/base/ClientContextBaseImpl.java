/*     */ package com.eoos.html.base;
/*     */ 
/*     */ import com.eoos.html.DispatchMap;
/*     */ import com.eoos.html.Dispatchable;
/*     */ import com.eoos.observable.IObservableSupport;
/*     */ import com.eoos.observable.Notification;
/*     */ import com.eoos.observable.ObservableSupport;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ClientContextBaseImpl
/*     */   implements ClientContextBase
/*     */ {
/*  26 */   private static final Logger log = Logger.getLogger(ClientContextBaseImpl.class);
/*     */   
/*  28 */   protected Locale locale = Locale.getDefault();
/*     */   
/*  30 */   protected Collection dispatchables = new HashSet();
/*     */   
/*  32 */   protected Map objectStore = new HashMap<Object, Object>();
/*     */   
/*  34 */   protected String requestURL = null;
/*     */   
/*  36 */   private IObservableSupport logoutListeners = (IObservableSupport)new ObservableSupport();
/*     */   
/*  38 */   private static final Notification NOTIFICATION_LOGOUT = new Notification() {
/*     */       public void notify(Object observer) {
/*  40 */         ClientContextBaseImpl.log.debug("sending logout notification to " + String.valueOf(observer));
/*  41 */         ((ClientContextBase.LogoutListener)observer).onLogout();
/*     */       }
/*     */     };
/*     */   
/*  45 */   private final Object lock = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addLogoutListener(ClientContextBase.LogoutListener listener) {
/*  52 */     this.logoutListeners.addObserver(listener);
/*     */   }
/*     */   
/*     */   public void removeLogoutListener(ClientContextBase.LogoutListener listener) {
/*  56 */     this.logoutListeners.removeObserver(listener);
/*     */   }
/*     */   
/*     */   protected void notifyLogoutListeners() {
/*  60 */     this.logoutListeners.notifyObservers(NOTIFICATION_LOGOUT, IObservableSupport.Mode.SYNCHRONOUS_NOTIFY);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRequestURL(String requestURL) {
/*  65 */     this.requestURL = requestURL;
/*     */   }
/*     */   
/*     */   public Object getObject(Object identifier) {
/*  69 */     return this.objectStore.get(identifier);
/*     */   }
/*     */   
/*     */   public void storeObject(Object identifier, Object object) {
/*  73 */     this.objectStore.put(identifier, object);
/*     */   }
/*     */   
/*     */   public String getLabel(String key) {
/*  77 */     return getApplicationContext().getLabel(getLocale(), key, key);
/*     */   }
/*     */   
/*     */   public String getMessage(String key) {
/*  81 */     return getApplicationContext().getMessage(getLocale(), key, key);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Locale getLocale() {
/*  87 */     return this.locale;
/*     */   }
/*     */   
/*     */   public void registerDispatchable(Dispatchable dispatchable) {
/*  91 */     this.dispatchables.add(dispatchable);
/*  92 */     DispatchMap.getInstance().register(dispatchable);
/*     */   }
/*     */   
/*     */   public void unregisterDispatchable(Dispatchable dispatchable) {
/*  96 */     this.dispatchables.remove(dispatchable);
/*  97 */     DispatchMap.getInstance().unregister(dispatchable);
/*     */   }
/*     */   
/*     */   public void clearAllDispatchables() {
/* 101 */     Iterator<Dispatchable> iter = this.dispatchables.iterator();
/* 102 */     while (iter.hasNext()) {
/* 103 */       DispatchMap.getInstance().unregister(iter.next());
/*     */     }
/* 105 */     this.dispatchables.clear();
/*     */   }
/*     */   
/*     */   public synchronized String createID() {
/* 109 */     return "A" + getApplicationContext().createID();
/*     */   }
/*     */   
/*     */   public String constructDispatchURL(Dispatchable dispatchable, String method) {
/* 113 */     String baseURL = getRequestURL();
/* 114 */     if (baseURL.length() > 0 && !baseURL.endsWith("/")) {
/* 115 */       baseURL = baseURL + "/";
/*     */     }
/* 117 */     return baseURL + "?target=" + dispatchable.getIdentifier() + "&amp;target.method=" + method;
/*     */   }
/*     */   
/*     */   public String constructURL(String suffix) {
/* 121 */     if (!suffix.startsWith("/")) {
/* 122 */       suffix = "/" + suffix;
/*     */     }
/* 124 */     return getRequestURL() + suffix;
/*     */   }
/*     */   
/*     */   protected String getRequestURL() {
/* 128 */     return (this.requestURL != null) ? this.requestURL : "";
/*     */   }
/*     */   
/*     */   public Object getLockObject() {
/* 132 */     return this.lock;
/*     */   }
/*     */   
/*     */   protected abstract ApplicationContext getApplicationContext();
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\base\ClientContextBaseImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */