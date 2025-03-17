/*     */ package com.eoos.util;
/*     */ 
/*     */ import com.eoos.collection.MinimalMap;
/*     */ import com.eoos.collection.implementation.MinimalMapAdapter;
/*     */ import com.eoos.log.Log4jUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MultitonSupport
/*     */ {
/*  19 */   private static final Logger devlog = DevLogger.getLogger(MultitonSupport.class);
/*     */ 
/*     */   
/*     */   private MinimalMap instances;
/*     */ 
/*     */   
/*     */   private CreationCallback creationCallback;
/*     */ 
/*     */   
/*     */   private Util.ShutdownListener listener;
/*     */ 
/*     */ 
/*     */   
/*     */   public MultitonSupport(CreationCallback creationCallback) {
/*  33 */     this.creationCallback = creationCallback;
/*  34 */     this.instances = createInstanceStorageMap();
/*  35 */     if (devlog.isDebugEnabled())
/*  36 */       this.listener = Util.addShutdownListener(new Util.ShutdownListener()
/*     */           {
/*     */             public void onShutdown() {
/*     */               try {
/*  40 */                 Log4jUtil.logSynchronized(MultitonSupport.devlog, new Log4jUtil.Callback()
/*     */                     {
/*     */                       public void writeLog(Logger log) {
/*  43 */                         MultitonSupport.devlog.debug("************* dumping registered instances (" + String.valueOf(MultitonSupport.this) + ")");
/*  44 */                         for (Iterator iter = MultitonSupport.this.instances.getKeys().iterator(); iter.hasNext(); ) {
/*  45 */                           Object key = iter.next();
/*  46 */                           MultitonSupport.devlog.debug("key: " + String.valueOf(key));
/*  47 */                           MultitonSupport.devlog.debug("instance: " + String.valueOf(MultitonSupport.this.instances.get(key)));
/*     */                         } 
/*     */                         
/*  50 */                         MultitonSupport.devlog.debug("******************************************************************");
/*     */                       }
/*     */                     });
/*     */               
/*     */               }
/*  55 */               catch (Throwable t) {}
/*     */             }
/*     */           }); 
/*     */   }
/*     */   
/*     */   public static interface CreationCallback {
/*     */     Object createObject(Object param1Object);
/*     */   }
/*     */   
/*     */   protected MinimalMap createInstanceStorageMap() {
/*  65 */     return (MinimalMap)new MinimalMapAdapter(new HashMap<Object, Object>());
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getInstance(Object identifier) {
/*  70 */     Object instance = this.instances.get(identifier);
/*  71 */     if (instance == null) {
/*  72 */       instance = this.creationCallback.createObject(identifier);
/*  73 */       this.instances.put(identifier, instance);
/*     */     } 
/*  75 */     return instance;
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection getInstances() {
/*  80 */     Collection<Object> retValue = new HashSet();
/*  81 */     for (Iterator iter = this.instances.getKeys().iterator(); iter.hasNext(); ) {
/*  82 */       Object key = iter.next();
/*  83 */       Object instance = this.instances.get(key);
/*  84 */       if (instance != null) {
/*  85 */         retValue.add(instance);
/*     */       }
/*     */     } 
/*  88 */     return retValue;
/*     */   }
/*     */   
/*     */   public boolean existsInstance(Object identifier) {
/*  92 */     return (this.instances.get(identifier) != null);
/*     */   }
/*     */   
/*     */   public void removeInstance(Object identifier) {
/*  96 */     this.instances.remove(identifier);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 100 */     return getClass().getName() + "[" + String.valueOf(this.creationCallback.toString()) + "]";
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoo\\util\MultitonSupport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */