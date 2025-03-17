/*     */ package com.eoos.scsm.v2.multiton.v4;
/*     */ 
/*     */ import com.eoos.scsm.v2.util.AssertUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WeakMultitonSupport
/*     */   implements IMultitonSupport
/*     */ {
/*  18 */   private static final Logger log = Logger.getLogger(WeakMultitonSupport.class);
/*     */   
/*  20 */   private Map instances = Collections.synchronizedMap(new WeakHashMap<Object, Object>());
/*     */   
/*     */   private IMultitonSupport.CreationCallback creationCallback;
/*     */   
/*  24 */   private IMultitonSupport.CreationCallbackExt extCreationCallback = null;
/*     */   
/*     */   public WeakMultitonSupport(IMultitonSupport.CreationCallback creationCallback) {
/*  27 */     AssertUtil.ensureNotNull(creationCallback);
/*  28 */     this.creationCallback = creationCallback;
/*  29 */     if (creationCallback instanceof IMultitonSupport.CreationCallbackExt) {
/*  30 */       this.extCreationCallback = (IMultitonSupport.CreationCallbackExt)creationCallback;
/*  31 */       if (Util.beParanoid()) {
/*  32 */         this.extCreationCallback = MultitonUtil.createCheckWrapper(this.extCreationCallback);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static WeakMultitonSupport create(IMultitonSupport.CreationCallback callback) {
/*  39 */     return new WeakMultitonSupport(callback);
/*     */   }
/*     */   
/*     */   public synchronized Object getInstance(Object key, boolean create) {
/*  43 */     Object instance = null;
/*  44 */     if (log.isDebugEnabled()) {
/*  45 */       log.debug("retrieving instance for: " + Util.toString(key));
/*     */     }
/*  47 */     WeakReference refInstance = (WeakReference)this.instances.get(key);
/*  48 */     instance = (refInstance != null) ? refInstance.get() : null;
/*     */     
/*  50 */     if (instance == null && create) {
/*  51 */       if (log.isDebugEnabled()) {
/*  52 */         log.debug("...no instance found (" + ((refInstance == null) ? "no entry in instance map" : "instance finalized") + ")");
/*     */       }
/*  54 */       if (this.extCreationCallback != null) {
/*  55 */         key = this.extCreationCallback.createStorageReplacement(key);
/*     */       }
/*  57 */       instance = this.creationCallback.createInstance(key);
/*  58 */       if (log.isDebugEnabled()) {
/*  59 */         log.debug("...created: " + Util.toString(instance));
/*     */       }
/*  61 */       this.instances.put(key, new WeakReference(instance));
/*     */     } else {
/*     */       
/*  64 */       log.debug("...instance found");
/*     */     } 
/*  66 */     if (log.isDebugEnabled()) {
/*  67 */       log.debug("...returning: " + Util.toString(instance));
/*     */     }
/*     */     
/*  70 */     return instance;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void removeInstance(Object object, IMultitonSupport.ArgumentType type) {
/*  75 */     if (object != null) {
/*  76 */       AssertUtil.ensure(type, AssertUtil.NOT_NULL);
/*  77 */       if (type == KEY) {
/*  78 */         this.instances.remove(object);
/*     */       } else {
/*  80 */         synchronized (this.instances) {
/*  81 */           for (Iterator<Map.Entry> iter = this.instances.entrySet().iterator(); iter.hasNext(); ) {
/*  82 */             Map.Entry entry = iter.next();
/*  83 */             WeakReference wr = (WeakReference)entry.getValue();
/*  84 */             Object value = (wr != null) ? wr.get() : null;
/*  85 */             if (object.equals(value)) {
/*  86 */               iter.remove();
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void visitInstances(IMultitonSupport.Visitor visitor) {
/*  95 */     synchronized (this.instances) {
/*  96 */       for (Iterator<WeakReference> iter = this.instances.values().iterator(); iter.hasNext(); ) {
/*  97 */         WeakReference wr = iter.next();
/*  98 */         Object value = (wr != null) ? wr.get() : null;
/*  99 */         if (value != null)
/* 100 */           visitor.onVisit(value); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\multiton\v4\WeakMultitonSupport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */