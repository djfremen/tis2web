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
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClientContextImpl
/*     */   extends ClientContextBaseImpl
/*     */   implements ClientContext, ClientContext.Logout, ClientContext.Persistent
/*     */ {
/*  28 */   private static final Logger log = Logger.getLogger(ClientContextImpl.class);
/*     */   
/*     */   private String sessionID;
/*     */   
/*  32 */   private long keepAliveTick = System.currentTimeMillis();
/*     */ 
/*     */ 
/*     */   
/*     */   public ClientContextImpl(String sessionID, boolean specialAccess) {
/*  37 */     log.debug("creating context for " + sessionID);
/*  38 */     this.sessionID = sessionID;
/*  39 */     this.locale = null;
/*  40 */     log.info("created context for sessionID:" + sessionID);
/*  41 */     log.debug("created " + this);
/*  42 */     SharedContext.getInstance(this).setSpecialAccess(specialAccess);
/*     */   }
/*     */   
/*     */   public void logout() {
/*  46 */     log.debug("executing logout for " + this);
/*  47 */     notifyLogoutListeners();
/*  48 */     log.debug("removing all dispatchables");
/*  49 */     clearAllDispatchables();
/*  50 */     this.dispatchables = null;
/*  51 */     log.debug("clearing objectstore");
/*  52 */     this.objectStore.clear();
/*  53 */     this.objectStore = null;
/*     */   }
/*     */   
/*     */   public ApplicationContext getApplicationContext() {
/*  57 */     return ApplicationContext.getInstance();
/*     */   }
/*     */   
/*     */   public Locale getLocale() {
/*  61 */     return this.locale;
/*     */   }
/*     */   
/*     */   public String getSessionID() {
/*  65 */     return this.sessionID;
/*     */   }
/*     */   
/*     */   protected boolean hasActiveDispatchable() {
/*  69 */     boolean retValue = false;
/*  70 */     if (this.dispatchables != null) {
/*  71 */       synchronized (this.dispatchables) {
/*  72 */         Iterator<LastAccessRecordingRef> iter = this.dispatchables.iterator();
/*  73 */         while (iter.hasNext() && !retValue) {
/*  74 */           LastAccessRecordingRef ref = iter.next();
/*  75 */           if (System.currentTimeMillis() - ref.getLastAccess() < getSessionTimeout()) {
/*  76 */             log.debug(this + " has active dispatchable " + ref);
/*  77 */             retValue = true;
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } else {
/*  83 */       retValue = false;
/*     */     } 
/*  85 */     if (!retValue) {
/*  86 */       log.debug(this + " does not have any active dispatchable");
/*     */     }
/*  88 */     return retValue;
/*     */   }
/*     */   
/*     */   public void keepAlive() {
/*  92 */     this.keepAliveTick = System.currentTimeMillis();
/*  93 */     log.debug(this + "received keep alive request");
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isKeptAlive() {
/*  98 */     boolean retValue = (System.currentTimeMillis() - this.keepAliveTick < getSessionTimeout());
/*  99 */     log.debug(this + " is " + (retValue ? "" : "NOT ") + "kept alive (last live sign: " + new Date(this.keepAliveTick) + ")");
/* 100 */     return retValue;
/*     */   }
/*     */   
/*     */   public SharedContext getSharedContext() {
/* 104 */     return SharedContext.getInstance(this);
/*     */   }
/*     */   
/*     */   public String getRequestURL() {
/* 108 */     return super.getRequestURL();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 112 */     return "ClientContextImpl[" + this.sessionID + "]";
/*     */   }
/*     */   
/*     */   public void registerDispatchable(Dispatchable dispatchable) {
/* 116 */     LastAccessRecordingRef lastAccessRecordingRef = new LastAccessRecordingRef(dispatchable);
/* 117 */     this.dispatchables.add(lastAccessRecordingRef);
/* 118 */     DispatchMap.getInstance().register((IReference)lastAccessRecordingRef);
/* 119 */     log.debug("registered dispatchable " + String.valueOf(dispatchable.getIdentifier()) + " for " + this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void unregisterDispatchable(Dispatchable dispatchable) {
/* 124 */     for (Iterator<LastAccessRecordingRef> iter = this.dispatchables.iterator(); iter.hasNext(); ) {
/* 125 */       LastAccessRecordingRef ref = iter.next();
/* 126 */       if (Util.equals(ref.snoop(), dispatchable)) {
/* 127 */         iter.remove();
/*     */       }
/*     */     } 
/* 130 */     DispatchMap.getInstance().unregister(dispatchable);
/*     */   }
/*     */   
/*     */   public void clearAllDispatchables() {
/* 134 */     Iterator<IReference> iter = this.dispatchables.iterator();
/* 135 */     while (iter.hasNext()) {
/* 136 */       IReference ref = iter.next();
/* 137 */       log.debug("unregistering " + String.valueOf(ref));
/* 138 */       DispatchMap.getInstance().unregister(ref);
/*     */     } 
/* 140 */     this.dispatchables.clear();
/*     */   }
/*     */   
/*     */   public String formatDate(Date date) {
/* 144 */     DateFormat df = DateFormat.getDateInstance(3, getLocale());
/* 145 */     return df.format(date);
/*     */   }
/*     */   
/*     */   protected static long getSessionTimeout() {
/* 149 */     return ApplicationContext.getInstance().getSessionTimeout(false);
/*     */   }
/*     */   
/*     */   public long getLastAccess() {
/* 153 */     long retValue = 0L;
/* 154 */     synchronized (this.dispatchables) {
/* 155 */       Iterator<LastAccessRecordingRef> iter = this.dispatchables.iterator();
/* 156 */       while (iter.hasNext()) {
/* 157 */         LastAccessRecordingRef ref = iter.next();
/* 158 */         retValue = Math.max(retValue, ref.getLastAccess());
/*     */       } 
/*     */     } 
/* 161 */     retValue = Math.max(retValue, this.keepAliveTick);
/* 162 */     return retValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPublicAccess() {
/* 167 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isActive() {
/* 171 */     return (isKeptAlive() || hasActiveDispatchable());
/*     */   }
/*     */   
/*     */   public boolean isSpecialAccess() {
/* 175 */     if (ApplicationContext.getInstance().developMode()) {
/* 176 */       log.debug("!!!!DEVELOP MODE: indicating special access");
/* 177 */       return true;
/*     */     } 
/* 179 */     return getSharedContext().isSpecialAccess();
/*     */   }
/*     */ 
/*     */   
/* 183 */   private static final Pattern P1 = Pattern.compile("(?i)eoos");
/*     */   
/*     */   public boolean offerSpecialAccess() {
/* 186 */     boolean ret = false;
/* 187 */     if ("admin".equalsIgnoreCase(getUserGroup())) {
/* 188 */       Matcher m = P1.matcher(this.sessionID);
/* 189 */       if (m.find()) {
/* 190 */         ret = (m.start() == this.sessionID.indexOf('.') + 1);
/*     */       }
/*     */     } 
/* 193 */     return ret;
/*     */   }
/*     */   
/*     */   public String getUserGroup() {
/* 197 */     return (String)CollectionUtil.getFirst(SharedContext.getInstance(this).getUserGroups());
/*     */   }
/*     */   
/*     */   public void setLocale(Locale locale) {
/* 201 */     this.locale = locale;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\ClientContextImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */