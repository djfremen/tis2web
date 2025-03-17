/*     */ package com.eoos.scsm.v2.cache;
/*     */ 
/*     */ import com.eoos.ref.v3.IReference;
/*     */ import com.eoos.ref.v3.IReferenceAdapterPool;
/*     */ import com.eoos.ref.v3.TimedMutationReference;
/*     */ import com.eoos.scsm.v2.util.Counter;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractCache
/*     */   implements Cache, Cache.Size, Cache.KeyQuery, Cache.Cleanup
/*     */ {
/*  30 */   private static final Logger log = Logger.getLogger(AbstractCache.class);
/*     */   
/*  32 */   private static ConcurrentLinkedQueue<WeakReference<AbstractCache>> instances = new ConcurrentLinkedQueue<WeakReference<AbstractCache>>();
/*     */   
/*     */   private IReference<Map> refMap;
/*     */   
/*  36 */   private Integer expectedSize = null;
/*     */   
/*     */   public AbstractCache() {
/*  39 */     this(null);
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractCache(Integer expectedSize) {
/*  44 */     this.expectedSize = expectedSize;
/*  45 */     this.refMap = createBackingMapReference(createBackingMap(expectedSize));
/*  46 */     instances.add(new WeakReference<AbstractCache>(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ClearanceResult clearAllCaches() {
/*  56 */     final int[] counter = new int[2];
/*  57 */     for (Iterator<WeakReference<AbstractCache>> iter = instances.iterator(); iter.hasNext(); ) {
/*  58 */       AbstractCache cache = ((WeakReference<AbstractCache>)iter.next()).get();
/*  59 */       if (cache == null) {
/*  60 */         iter.remove(); continue;
/*     */       } 
/*  62 */       counter[0] = counter[0] + 1;
/*  63 */       int entryCount = cache._clear();
/*  64 */       if (entryCount != -1) {
/*  65 */         counter[1] = counter[1] + entryCount;
/*     */       }
/*     */     } 
/*     */     
/*  69 */     return new ClearanceResult()
/*     */       {
/*     */         public int getEntryCount() {
/*  72 */           return counter[1];
/*     */         }
/*     */         
/*     */         public int getCacheCount() {
/*  76 */           return counter[0];
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   protected Integer getExpectedSize() {
/*  82 */     return this.expectedSize;
/*     */   } public static interface ClearanceResult {
/*     */     int getCacheCount(); int getEntryCount(); }
/*     */   protected Map createBackingMap(Integer expectedSize) {
/*  86 */     if (expectedSize == null) {
/*  87 */       return new ConcurrentHashMap<Object, Object>();
/*     */     }
/*  89 */     return new ConcurrentHashMap<Object, Object>(expectedSize.intValue());
/*     */   }
/*     */ 
/*     */   
/*     */   protected IReference<Map> createBackingMapReference(Map map) {
/*  94 */     return (IReference<Map>)new TimedMutationReference(map, TimedMutationReference.Type.HARD, TimedMutationReference.Type.SOFT, 60000L, true);
/*     */   }
/*     */   
/*     */   protected synchronized Map getMap() {
/*  98 */     Map ret = (Map)this.refMap.get();
/*  99 */     if (ret == null) {
/* 100 */       ret = createBackingMap(this.expectedSize);
/* 101 */       this.refMap = createBackingMapReference(ret);
/*     */     } 
/* 103 */     return ret;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 107 */     _clear();
/*     */   }
/*     */   
/*     */   public int _clear() {
/* 111 */     int ret = -1;
/* 112 */     Map map = (Map)this.refMap.get();
/* 113 */     if (map != null) {
/* 114 */       ret = map.size();
/*     */     }
/* 116 */     this.refMap.clear();
/* 117 */     return ret;
/*     */   }
/*     */   
/*     */   public Object lookup(Object key) {
/* 121 */     Object ret = null;
/* 122 */     IReference ref = (IReference)getMap().get(key);
/* 123 */     if (ref != null) {
/* 124 */       ret = ref.get();
/* 125 */       if (ret == null) {
/*     */         try {
/* 127 */           getMap().remove(key);
/*     */         } finally {
/* 129 */           IReferenceAdapterPool.GLOBAL_INSTANCE.free(ref);
/*     */         } 
/*     */       }
/*     */     } 
/* 133 */     return ret;
/*     */   }
/*     */   
/*     */   public void remove(Object key) {
/* 137 */     IReference ref = (IReference)getMap().remove(key);
/* 138 */     if (ref != null) {
/* 139 */       IReferenceAdapterPool.GLOBAL_INSTANCE.free(ref);
/*     */     }
/*     */   }
/*     */   
/*     */   public void store(Object key, Object information) {
/* 144 */     if (information != null) {
/* 145 */       getMap().put(key, createReference(information));
/*     */     } else {
/* 147 */       getMap().remove(key);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int size() {
/* 152 */     return getMap().size();
/*     */   }
/*     */   
/*     */   public Set getKeys() {
/* 156 */     return getMap().keySet();
/*     */   }
/*     */   
/*     */   protected abstract IReference createReference(Object paramObject);
/*     */   
/*     */   public void cleanup() {
/* 162 */     int prio = Util.incPriority();
/*     */     try {
/* 164 */       Map map = getMap();
/* 165 */       synchronized (map) {
/* 166 */         log.debug("cleanup...");
/* 167 */         Counter counter = new Counter();
/* 168 */         for (Iterator<Map.Entry> iter = map.entrySet().iterator(); iter.hasNext(); ) {
/* 169 */           Map.Entry entry = iter.next();
/* 170 */           IReference ref = (IReference)entry.getValue();
/* 171 */           boolean remove = (ref == null);
/* 172 */           if (ref instanceof IReference.Snoopable) {
/* 173 */             remove = (remove || ((IReference.Snoopable)ref).snoop() == null);
/* 174 */           } else if (!remove) {
/* 175 */             remove = (remove || ref.get() == null);
/*     */           } 
/* 177 */           if (remove) {
/* 178 */             iter.remove();
/* 179 */             counter.inc();
/* 180 */             IReferenceAdapterPool.GLOBAL_INSTANCE.free(ref);
/*     */           } 
/*     */         } 
/* 183 */         log.debug("...removed " + counter + " obsolete entries");
/*     */       } 
/*     */     } finally {
/* 186 */       Thread.currentThread().setPriority(prio);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\cache\AbstractCache.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */