/*     */ package com.eoos.scsm.v2.objectpool;
/*     */ 
/*     */ import com.eoos.context.IContext;
/*     */ import com.eoos.scsm.v2.util.Counter;
/*     */ import com.eoos.scsm.v2.util.LockObjectProvider;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class HashMapPool
/*     */ {
/*  15 */   private static final Logger log = Logger.getLogger(HashMapPool.class);
/*     */   
/*  17 */   private static LockObjectProvider LOCKPROVIDER = new LockObjectProvider();
/*     */   
/*     */   private ObjectPool delegate;
/*     */   
/*  21 */   private Counter requestCount = new Counter();
/*     */   
/*  23 */   private Counter creationCount = new Counter();
/*     */   
/*     */   private Util.ShutdownListener listener;
/*     */ 
/*     */   
/*     */   public HashMapPool(final int expectedMapSize) {
/*  29 */     ObjectPool.SPI spi = null;
/*  30 */     if (expectedMapSize == -1) {
/*  31 */       spi = new ObjectPool.SPI()
/*     */         {
/*     */           public void reset(Object object) {
/*  34 */             ((Map)object).clear();
/*     */           }
/*     */           
/*     */           public Object create() {
/*  38 */             HashMapPool.this.creationCount.inc();
/*  39 */             return new HashMap<Object, Object>();
/*     */           }
/*     */         };
/*     */     } else {
/*     */       
/*  44 */       spi = new ObjectPool.SPI()
/*     */         {
/*     */           public void reset(Object object) {
/*  47 */             ((Map)object).clear();
/*     */           }
/*     */           
/*     */           public Object create() {
/*  51 */             HashMapPool.this.creationCount.inc();
/*  52 */             return new HashMap<Object, Object>(expectedMapSize);
/*     */           }
/*     */         };
/*     */     } 
/*     */ 
/*     */     
/*  58 */     this.delegate = ObjectPool.createInstance(spi);
/*     */     
/*  60 */     this.listener = Util.addShutdownListener(new Util.ShutdownListener()
/*     */         {
/*     */           public void onShutdown() {
/*  63 */             HashMapPool.log.info("object pool statistic - requests: " + HashMapPool.this.requestCount.getCount() + ", objects: " + HashMapPool.this.creationCount.getCount());
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public static HashMapPool getInstance(IContext context) {
/*  69 */     synchronized (LOCKPROVIDER.getLockObject(context)) {
/*  70 */       HashMapPool instance = null;
/*  71 */       SoftReference<HashMapPool> srInstance = (SoftReference)context.getObject(HashMapPool.class);
/*  72 */       if (srInstance == null || (instance = srInstance.get()) == null) {
/*  73 */         instance = new HashMapPool(-1);
/*  74 */         context.storeObject(HashMapPool.class, new SoftReference<HashMapPool>(instance));
/*     */       } 
/*  76 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*  80 */   private static ThreadLocal instance = new ThreadLocal();
/*     */   
/*     */   public static HashMapPool getThreadInstance() {
/*  83 */     SoftReference<HashMapPool> srPool = instance.get();
/*  84 */     HashMapPool ret = (srPool != null) ? srPool.get() : null;
/*  85 */     if (ret == null) {
/*  86 */       ret = new HashMapPool(-1);
/*  87 */       instance.set(new SoftReference<HashMapPool>(ret));
/*     */     } 
/*  89 */     return ret;
/*     */   }
/*     */   
/*     */   public HashMap get() {
/*  93 */     this.requestCount.inc();
/*  94 */     return (HashMap)this.delegate.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public HashMap get(Map m) {
/*  99 */     HashMap ret = get();
/* 100 */     ret.putAll(m);
/* 101 */     return ret;
/*     */   }
/*     */   
/*     */   public void free(Map map) {
/* 105 */     this.delegate.free(map);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\objectpool\HashMapPool.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */