/*     */ package com.eoos.gm.tis2web.frame.export.common;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContext;
/*     */ import com.eoos.html.Dispatchable;
/*     */ import com.eoos.html.base.ApplicationContext;
/*     */ import com.eoos.html.base.ClientContextBase;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.Date;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClientContextProxy
/*     */   implements ClientContext
/*     */ {
/*  16 */   private WeakReference wrContext = null;
/*     */   
/*     */   public ClientContextProxy(ClientContext context) {
/*  19 */     if (context == null) {
/*  20 */       throw new IllegalArgumentException("context must not be null");
/*     */     }
/*  22 */     this.wrContext = new WeakReference<ClientContext>(context);
/*     */   }
/*     */ 
/*     */   
/*     */   public ClientContext getBackend() {
/*  27 */     return this.wrContext.get();
/*     */   }
/*     */   
/*     */   public ApplicationContext getApplicationContext() {
/*  31 */     return getBackend().getApplicationContext();
/*     */   }
/*     */   
/*     */   public Locale getLocale() {
/*  35 */     return getBackend().getLocale();
/*     */   }
/*     */   
/*     */   public String getSessionID() {
/*  39 */     return getBackend().getSessionID();
/*     */   }
/*     */   
/*     */   public void keepAlive() {
/*  43 */     getBackend().keepAlive();
/*     */   }
/*     */   
/*     */   public SharedContext getSharedContext() {
/*  47 */     return getBackend().getSharedContext();
/*     */   }
/*     */   
/*     */   public String getRequestURL() {
/*  51 */     return getBackend().getRequestURL();
/*     */   }
/*     */   
/*     */   public void registerDispatchable(Dispatchable dispatchable) {
/*  55 */     getBackend().registerDispatchable(dispatchable);
/*     */   }
/*     */   
/*     */   public void clearAllDispatchables() {
/*  59 */     getBackend().clearAllDispatchables();
/*     */   }
/*     */   
/*     */   public String formatDate(Date date) {
/*  63 */     return getBackend().formatDate(date);
/*     */   }
/*     */   
/*     */   public void addLogoutListener(ClientContextBase.LogoutListener listener) {
/*  67 */     getBackend().addLogoutListener(listener);
/*     */   }
/*     */   
/*     */   public void removeLogoutListener(ClientContextBase.LogoutListener listener) {
/*  71 */     getBackend().removeLogoutListener(listener);
/*     */   }
/*     */   
/*     */   public void setRequestURL(String requestURL) {
/*  75 */     getBackend().setRequestURL(requestURL);
/*     */   }
/*     */   
/*     */   public Object getObject(Object identifier) {
/*  79 */     return getBackend().getObject(identifier);
/*     */   }
/*     */   
/*     */   public void storeObject(Object identifier, Object object) {
/*  83 */     getBackend().storeObject(identifier, object);
/*     */   }
/*     */   
/*     */   public String getLabel(String key) {
/*  87 */     return getBackend().getLabel(key);
/*     */   }
/*     */   
/*     */   public String getMessage(String key) {
/*  91 */     return getBackend().getMessage(key);
/*     */   }
/*     */   
/*     */   public void unregisterDispatchable(Dispatchable dispatchable) {
/*  95 */     getBackend().unregisterDispatchable(dispatchable);
/*     */   }
/*     */   
/*     */   public String createID() {
/*  99 */     return getBackend().createID();
/*     */   }
/*     */ 
/*     */   
/*     */   public String constructDispatchURL(Dispatchable dispatchable, String method) {
/* 104 */     return getBackend().constructDispatchURL(dispatchable, method);
/*     */   }
/*     */   
/*     */   public String constructURL(String suffix) {
/* 108 */     return getBackend().constructURL(suffix);
/*     */   }
/*     */   
/*     */   public Object getLockObject() {
/* 112 */     return getBackend().getLockObject();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 116 */     return "Proxy for [" + String.valueOf((new StringBuilder()).append(this.wrContext.get()).append("]").toString());
/*     */   }
/*     */   
/*     */   public boolean isPublicAccess() {
/* 120 */     return getBackend().isPublicAccess();
/*     */   }
/*     */   
/*     */   public boolean isActive() {
/* 124 */     return getBackend().isActive();
/*     */   }
/*     */   
/*     */   public long getLastAccess() {
/* 128 */     return getBackend().getLastAccess();
/*     */   }
/*     */   
/*     */   public boolean isSpecialAccess() {
/* 132 */     return getBackend().isSpecialAccess();
/*     */   }
/*     */   
/*     */   public String getUserGroup() {
/* 136 */     return getBackend().getUserGroup();
/*     */   }
/*     */   
/*     */   public boolean offerSpecialAccess() {
/* 140 */     return getBackend().offerSpecialAccess();
/*     */   }
/*     */   
/*     */   public void setLocale(Locale locale) {
/* 144 */     getBackend().setLocale(locale);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\ClientContextProxy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */