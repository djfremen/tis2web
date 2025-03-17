/*     */ package com.eoos.gm.tis2web.frame.export.common;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContext;
/*     */ import com.eoos.html.DispatchMap;
/*     */ import com.eoos.html.Dispatchable;
/*     */ import com.eoos.html.base.ApplicationContext;
/*     */ import com.eoos.html.base.ClientContextBaseImpl;
/*     */ import com.eoos.ref.v3.IReference;
/*     */ import com.eoos.ref.v3.LastAccessRecordingRef;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.text.DateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClientContextPublicImpl
/*     */   extends ClientContextBaseImpl
/*     */   implements ClientContext, ClientContext.Logout
/*     */ {
/*  26 */   private static final Logger log = Logger.getLogger(ClientContextPublicImpl.class);
/*     */   
/*     */   private String sessionID;
/*     */   
/*  30 */   private long lastLifeSign = System.currentTimeMillis();
/*     */ 
/*     */ 
/*     */   
/*     */   public ClientContextPublicImpl(String sessionID) {
/*  35 */     log.debug("creating public context for " + sessionID);
/*  36 */     this.sessionID = sessionID;
/*  37 */     this.locale = null;
/*     */   }
/*     */   
/*     */   public void logout() {
/*  41 */     log.debug("executing logout for " + this);
/*  42 */     notifyLogoutListeners();
/*  43 */     log.debug("removing all dispatchables");
/*  44 */     clearAllDispatchables();
/*  45 */     this.dispatchables = null;
/*  46 */     log.debug("clearing objectstore");
/*  47 */     this.objectStore.clear();
/*  48 */     this.objectStore = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ApplicationContext getApplicationContext() {
/*  53 */     return ApplicationContext.getInstance();
/*     */   }
/*     */   
/*     */   public Locale getLocale() {
/*  57 */     return this.locale;
/*     */   }
/*     */   
/*     */   public String getSessionID() {
/*  61 */     return this.sessionID;
/*     */   }
/*     */   
/*     */   protected boolean hasActiveDispatchable() {
/*  65 */     boolean retValue = false;
/*  66 */     if (this.dispatchables != null) {
/*  67 */       synchronized (this.dispatchables) {
/*  68 */         Iterator<LastAccessRecordingRef> iter = this.dispatchables.iterator();
/*  69 */         while (iter.hasNext() && !retValue) {
/*  70 */           LastAccessRecordingRef ref = iter.next();
/*  71 */           if (System.currentTimeMillis() - ref.getLastAccess() < getSessionTimeout()) {
/*  72 */             log.debug(this + " has active dispatchable " + ref);
/*  73 */             retValue = true;
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } else {
/*  79 */       retValue = false;
/*     */     } 
/*  81 */     if (!retValue) {
/*  82 */       log.debug(this + " does not have any active dispatchable");
/*     */     }
/*  84 */     return retValue;
/*     */   }
/*     */   
/*     */   public void keepAlive() {
/*  88 */     this.lastLifeSign = System.currentTimeMillis();
/*  89 */     log.debug(this + "received keep alive request");
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isKeptAlive() {
/*  94 */     boolean retValue = (System.currentTimeMillis() - this.lastLifeSign < getSessionTimeout());
/*  95 */     log.debug(this + " is " + (retValue ? "" : "NOT ") + "kept alive (last live sign: " + new Date(this.lastLifeSign) + ")");
/*  96 */     return retValue;
/*     */   }
/*     */   
/*     */   public SharedContext getSharedContext() {
/* 100 */     return SharedContext.getInstance(this);
/*     */   }
/*     */   
/*     */   public String getRequestURL() {
/* 104 */     return super.getRequestURL();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 108 */     return getClass().getName() + "[" + this.sessionID + "]";
/*     */   }
/*     */   
/*     */   public void registerDispatchable(Dispatchable dispatchable) {
/* 112 */     LastAccessRecordingRef lastAccessRecordingRef = new LastAccessRecordingRef(dispatchable);
/* 113 */     this.dispatchables.add(lastAccessRecordingRef);
/* 114 */     DispatchMap.getInstance().register((IReference)lastAccessRecordingRef);
/* 115 */     log.debug("registered dispatchable " + String.valueOf(lastAccessRecordingRef) + " for " + this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void unregisterDispatchable(Dispatchable dispatchable) {
/* 120 */     for (Iterator<LastAccessRecordingRef> iter = this.dispatchables.iterator(); iter.hasNext(); ) {
/* 121 */       LastAccessRecordingRef ref = iter.next();
/* 122 */       if (Util.equals(ref.snoop(), dispatchable)) {
/* 123 */         iter.remove();
/*     */       }
/*     */     } 
/* 126 */     DispatchMap.getInstance().unregister(dispatchable);
/*     */   }
/*     */   
/*     */   public void clearAllDispatchables() {
/* 130 */     Iterator<IReference> iter = this.dispatchables.iterator();
/* 131 */     while (iter.hasNext()) {
/* 132 */       IReference ref = iter.next();
/* 133 */       log.debug("unregistering " + String.valueOf(ref));
/* 134 */       DispatchMap.getInstance().unregister(ref);
/*     */     } 
/* 136 */     this.dispatchables.clear();
/*     */   }
/*     */   
/*     */   public String formatDate(Date date) {
/* 140 */     DateFormat df = DateFormat.getDateInstance(3, getLocale());
/* 141 */     return df.format(date);
/*     */   }
/*     */   
/*     */   protected static long getSessionTimeout() {
/* 145 */     return ApplicationContext.getInstance().getSessionTimeout(true);
/*     */   }
/*     */   
/*     */   public long getLastAccess() {
/* 149 */     long retValue = 0L;
/* 150 */     synchronized (this.dispatchables) {
/* 151 */       Iterator<LastAccessRecordingRef> iter = this.dispatchables.iterator();
/* 152 */       while (iter.hasNext()) {
/* 153 */         LastAccessRecordingRef ref = iter.next();
/* 154 */         retValue = Math.max(retValue, ref.getLastAccess());
/*     */       } 
/*     */     } 
/* 157 */     retValue = Math.max(retValue, this.lastLifeSign);
/* 158 */     return retValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPublicAccess() {
/* 163 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isActive() {
/* 167 */     return (isKeptAlive() || hasActiveDispatchable());
/*     */   }
/*     */   
/*     */   public boolean isSpecialAccess() {
/* 171 */     return false;
/*     */   }
/*     */   
/*     */   public String getUserGroup() {
/* 175 */     return (String)CollectionUtil.getFirst(SharedContext.getInstance(this).getUserGroups());
/*     */   }
/*     */   
/*     */   public boolean offerSpecialAccess() {
/* 179 */     return false;
/*     */   }
/*     */   
/*     */   public void setLocale(Locale locale) {
/* 183 */     this.locale = locale;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\ClientContextPublicImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */