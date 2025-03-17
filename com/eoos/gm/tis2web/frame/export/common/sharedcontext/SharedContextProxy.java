/*     */ package com.eoos.gm.tis2web.frame.export.common.sharedcontext;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import java.util.Collections;
/*     */ import java.util.ConcurrentModificationException;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
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
/*     */ 
/*     */ public class SharedContextProxy
/*     */ {
/*  25 */   private static final Logger log = Logger.getLogger(SharedContextProxy.class);
/*     */   
/*     */   private ClientContext context;
/*     */   
/*  29 */   private String defaultSalesmake = null;
/*     */   
/*  31 */   private Integer displayHeight = null;
/*     */   
/*  33 */   private String country = null;
/*     */   
/*  35 */   private Map usrGroup2Manuf = null;
/*     */   
/*  37 */   private LocaleInfo localeInfo = null;
/*     */   
/*  39 */   private List observers = Collections.synchronizedList(new LinkedList());
/*     */   
/*     */   private boolean initialized = false;
/*     */ 
/*     */   
/*     */   private SharedContextProxy(ClientContext context) {
/*  45 */     this.context = context;
/*     */   }
/*     */ 
/*     */   
/*     */   public static SharedContextProxy getInstance(ClientContext context) {
/*  50 */     synchronized (context.getLockObject()) {
/*  51 */       SharedContextProxy instance = (SharedContextProxy)context.getObject(SharedContextProxy.class);
/*  52 */       if (instance == null) {
/*  53 */         instance = new SharedContextProxy(context);
/*  54 */         context.storeObject(SharedContextProxy.class, instance);
/*     */       } 
/*  56 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean defaultSalesmakeChanged(String oldDefaultSalesmake, String newDefaultSalesmake) {
/*  61 */     if (oldDefaultSalesmake == null && newDefaultSalesmake == null)
/*  62 */       return false; 
/*  63 */     if (oldDefaultSalesmake == null || newDefaultSalesmake == null) {
/*  64 */       return true;
/*     */     }
/*  66 */     return !oldDefaultSalesmake.equals(newDefaultSalesmake);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean displayHeightChanged(Integer oldHeight, Integer newHeight) {
/*  71 */     if (oldHeight == null && newHeight == null)
/*  72 */       return false; 
/*  73 */     if (oldHeight == null || newHeight == null) {
/*  74 */       return true;
/*     */     }
/*  76 */     return !oldHeight.equals(newHeight);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void update() {
/*     */     try {
/*  82 */       if (!this.initialized) {
/*  83 */         this.country = this.context.getSharedContext().getCountry();
/*  84 */         this.usrGroup2Manuf = this.context.getSharedContext().getUsrGroup2ManufMap();
/*  85 */         this.localeInfo = LocaleInfoProvider.getInstance().getLocale(this.context.getLocale());
/*  86 */         this.initialized = true;
/*     */       } 
/*  88 */       String newDefaultSalesmake = this.context.getSharedContext().getDefaultSalesmake();
/*  89 */       Integer newDisplayHeight = this.context.getSharedContext().getDisplayHeight();
/*  90 */       notifyObservers(newDefaultSalesmake, newDisplayHeight);
/*  91 */       this.defaultSalesmake = newDefaultSalesmake;
/*  92 */       this.displayHeight = newDisplayHeight;
/*     */     }
/*  94 */     catch (Exception e) {
/*  95 */       log.error("unable to update vcr -error:" + e, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private synchronized void notifyObservers(String newDefaultSalesmake, Integer newDisplayHeight) {
/* 100 */     boolean cDefaultSalesmake = defaultSalesmakeChanged(this.defaultSalesmake, newDefaultSalesmake);
/* 101 */     boolean cHeight = displayHeightChanged(this.displayHeight, newDisplayHeight);
/*     */     
/* 103 */     if (cDefaultSalesmake || cHeight)
/* 104 */       synchronized (this.observers) {
/* 105 */         boolean done = false;
/* 106 */         int retries = 0;
/*     */         while (true) {
/*     */           try {
/* 109 */             Iterator<SharedContextObserver> iter = this.observers.iterator();
/* 110 */             while (iter.hasNext()) {
/* 111 */               SharedContextObserver observer = iter.next();
/* 112 */               notifyObserver(observer, cDefaultSalesmake, this.defaultSalesmake, newDefaultSalesmake, cHeight, this.displayHeight, newDisplayHeight);
/*     */             } 
/* 114 */             done = true;
/* 115 */           } catch (ConcurrentModificationException cme) {
/*     */ 
/*     */ 
/*     */             
/* 119 */             if (++retries == 3) {
/* 120 */               done = true;
/* 121 */               throw cme;
/*     */             } 
/*     */           } 
/* 124 */           if (done)
/*     */             return; 
/*     */         } 
/*     */       }  
/*     */   }
/*     */   private synchronized void notifyObserver(SharedContextObserver observer, boolean cDefaultSalesmake, String thisDefaultSalesmake, String newDefaultSalesmake, boolean cHeight, Integer thisDisplayHeight, Integer newDisplayHeight) {
/* 130 */     if (cDefaultSalesmake) {
/* 131 */       observer.onDefaultSalesmakeChange(thisDefaultSalesmake, newDefaultSalesmake);
/*     */     }
/* 133 */     if (cHeight) {
/* 134 */       observer.onDisplayHeightChange(thisDisplayHeight, newDisplayHeight);
/*     */     }
/*     */   }
/*     */   
/*     */   public synchronized void addObserver(SharedContextObserver observer) {
/* 139 */     this.observers.add(observer);
/*     */   }
/*     */   
/*     */   public synchronized String getDefaultSalesmake() {
/* 143 */     return this.defaultSalesmake;
/*     */   }
/*     */   
/*     */   public synchronized Integer getDisplayHeight() {
/* 147 */     return this.displayHeight;
/*     */   }
/*     */   
/*     */   public synchronized String getCountry() {
/* 151 */     return this.country;
/*     */   }
/*     */   
/*     */   public synchronized Map getUsrGroup2Manuf() {
/* 155 */     return this.usrGroup2Manuf;
/*     */   }
/*     */   
/*     */   public synchronized LocaleInfo getLocaleInfo() {
/* 159 */     return this.localeInfo;
/*     */   }
/*     */   
/*     */   public synchronized String getCurrentSalesmake() {
/* 163 */     return VCFacade.getInstance(this.context).getCurrentSalesmake();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\sharedcontext\SharedContextProxy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */