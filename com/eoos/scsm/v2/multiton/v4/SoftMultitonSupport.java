/*     */ package com.eoos.scsm.v2.multiton.v4;
/*     */ 
/*     */ import com.eoos.math.AverageCalculator;
/*     */ import com.eoos.scsm.v2.util.AssertUtil;
/*     */ import com.eoos.scsm.v2.util.LockObjectProvider;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.math.BigDecimal;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.WeakHashMap;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SoftMultitonSupport
/*     */   implements IMultitonSupport
/*     */ {
/*  27 */   static final Logger log = Logger.getLogger(SoftMultitonSupport.class);
/*     */   
/*  29 */   private Map instances = Collections.synchronizedMap(new WeakHashMap<Object, Object>());
/*     */   
/*     */   private IMultitonSupport.CreationCallback creationCallback;
/*     */   
/*  33 */   private ReferenceQueue refQueue = new ReferenceQueue();
/*     */   
/*  35 */   private AverageCalculator hitRatio = new AverageCalculator(2);
/*     */   
/*  37 */   private int peek = 0;
/*     */   
/*  39 */   private AtomicLong tsLastCleanup = new AtomicLong(System.currentTimeMillis());
/*     */ 
/*     */   
/*     */   private Util.ShutdownListener listener;
/*     */   
/*  44 */   private LockObjectProvider lockProvider = new LockObjectProvider();
/*     */   
/*     */   private IMultitonSupport.CreationCallbackExt extCreationCallback;
/*     */   
/*     */   public SoftMultitonSupport(IMultitonSupport.CreationCallback creationCallback) {
/*  49 */     AssertUtil.ensureNotNull(creationCallback);
/*  50 */     this.creationCallback = creationCallback;
/*  51 */     if (creationCallback instanceof IMultitonSupport.CreationCallbackExt) {
/*  52 */       this.extCreationCallback = (IMultitonSupport.CreationCallbackExt)creationCallback;
/*  53 */       if (Util.beParanoid()) {
/*  54 */         this.extCreationCallback = MultitonUtil.createCheckWrapper(this.extCreationCallback);
/*     */       }
/*     */     } 
/*  57 */     final Throwable allocationStack = new Throwable();
/*  58 */     this.listener = Util.addShutdownListener(new Util.ShutdownListener()
/*     */         {
/*     */           public void onShutdown() {
/*  61 */             synchronized (SoftMultitonSupport.log) {
/*  62 */               CharSequence trace = Util.compactStackTrace(allocationStack, 1, 5);
/*  63 */               SoftMultitonSupport.log.info("statistics for soft multiton support ...");
/*  64 */               SoftMultitonSupport.log.info("...(creation trace: " + trace + ")");
/*  65 */               SoftMultitonSupport.log.info("...hit ratio: " + SoftMultitonSupport.this.hitRatio.getCurrentAverage() + " hit/request");
/*  66 */               SoftMultitonSupport.log.info("...total request count: " + SoftMultitonSupport.this.hitRatio.getValueCount());
/*  67 */               SoftMultitonSupport.log.info("...maximum size:" + SoftMultitonSupport.this.peek);
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getInstance(Object key) {
/*  75 */     return getInstance(key, true);
/*     */   }
/*     */   
/*     */   public Object getInstance(Object key, boolean create) {
/*  79 */     if (System.currentTimeMillis() - this.tsLastCleanup.get() > 60000L) {
/*  80 */       cleanup();
/*     */     }
/*  82 */     synchronized (this.lockProvider.getLockObject(key)) {
/*  83 */       Object instance = null;
/*  84 */       if (log.isDebugEnabled()) {
/*  85 */         log.debug("retrieving instance for: " + Util.toString(key));
/*     */       }
/*     */       
/*  88 */       SoftReference sr = (SoftReference)this.instances.get(key);
/*  89 */       instance = (sr != null) ? sr.get() : null;
/*  90 */       if (instance == null && create) {
/*  91 */         this.hitRatio.add(BigDecimal.ZERO);
/*  92 */         if (log.isDebugEnabled()) {
/*  93 */           log.debug("...no instance found, creating instance for key: " + Util.toString(key));
/*     */         }
/*  95 */         if (this.extCreationCallback != null) {
/*  96 */           key = this.extCreationCallback.createStorageReplacement(key);
/*     */         }
/*     */         
/*  99 */         instance = this.creationCallback.createInstance(key);
/* 100 */         if (log.isDebugEnabled()) {
/* 101 */           log.debug("...created: " + Util.toString(instance));
/*     */         }
/* 103 */         this.instances.put(key, new SoftReference(instance, this.refQueue));
/* 104 */         this.peek = Math.max(this.peek, this.instances.size());
/*     */       } else {
/* 106 */         this.hitRatio.add(BigDecimal.ONE);
/* 107 */         log.debug("...instance found");
/*     */       } 
/* 109 */       if (log.isDebugEnabled()) {
/* 110 */         log.debug("...returning: " + Util.toString(instance));
/*     */       }
/* 112 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void cleanup() {
/* 117 */     this.tsLastCleanup.set(System.currentTimeMillis());
/* 118 */     log.debug("cleanup");
/* 119 */     Set<Reference> toClear = new HashSet();
/* 120 */     Reference ref = null;
/* 121 */     while ((ref = this.refQueue.poll()) != null) {
/* 122 */       toClear.add(ref);
/*     */     }
/*     */     
/* 125 */     if (toClear.size() > 0) {
/* 126 */       int count = 0;
/* 127 */       synchronized (this.instances) {
/* 128 */         for (Iterator<Map.Entry> iter = this.instances.entrySet().iterator(); iter.hasNext(); ) {
/* 129 */           Map.Entry entry = iter.next();
/* 130 */           if (toClear.contains(entry.getValue())) {
/* 131 */             count++;
/* 132 */             iter.remove();
/*     */           } 
/*     */         } 
/*     */       } 
/* 136 */       if (count > 0) {
/* 137 */         log.debug("...removed " + count + " obsolete entries");
/*     */       } else {
/* 139 */         log.debug("...no obsolete entries found");
/*     */       } 
/*     */     } else {
/* 142 */       log.debug("...empty reference queue -> skipped cleanup");
/*     */     } 
/*     */   }
/*     */   
/*     */   public synchronized void removeInstance(Object object, IMultitonSupport.ArgumentType type) {
/* 147 */     if (object != null) {
/* 148 */       AssertUtil.ensure(type, AssertUtil.NOT_NULL);
/* 149 */       if (type == KEY) {
/* 150 */         this.instances.remove(object);
/*     */       } else {
/* 152 */         synchronized (this.instances) {
/* 153 */           for (Iterator<Map.Entry> iter = this.instances.entrySet().iterator(); iter.hasNext(); ) {
/* 154 */             Map.Entry entry = iter.next();
/* 155 */             SoftReference sr = (SoftReference)entry.getValue();
/* 156 */             Object value = (sr != null) ? sr.get() : null;
/* 157 */             if (object.equals(value)) {
/* 158 */               iter.remove();
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void visitInstances(IMultitonSupport.Visitor visitor) {
/* 167 */     synchronized (this.instances) {
/* 168 */       for (Iterator<SoftReference> iter = this.instances.values().iterator(); iter.hasNext(); ) {
/* 169 */         SoftReference sr = iter.next();
/* 170 */         Object value = (sr != null) ? sr.get() : null;
/* 171 */         if (value != null) {
/* 172 */           visitor.onVisit(value);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static SoftMultitonSupport create(IMultitonSupport.CreationCallback creationCallback) {
/* 179 */     return new SoftMultitonSupport(creationCallback);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\multiton\v4\SoftMultitonSupport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */