/*    */ package com.eoos.scsm.v2.cache;
/*    */ 
/*    */ import com.eoos.ref.v3.IReference;
/*    */ import com.eoos.ref.v3.IReferenceAdapterPool;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.lang.ref.ReferenceQueue;
/*    */ import java.lang.ref.SoftReference;
/*    */ import java.lang.ref.WeakReference;
/*    */ import java.util.concurrent.atomic.AtomicInteger;
/*    */ 
/*    */ public class SoftCache
/*    */   extends AbstractCache {
/*    */   private static final long CLEANUP_TRIGGER_INTERVAL = 30000L;
/*    */   
/*    */   private static class Cleanup
/*    */     implements Runnable {
/*    */     private WeakReference wrCache;
/*    */     
/*    */     private Cleanup(SoftCache cache) {
/* 20 */       this.wrCache = new WeakReference<SoftCache>(cache);
/*    */     }
/*    */     
/*    */     public void run() {
/* 24 */       SoftCache cache = this.wrCache.get();
/* 25 */       if (cache != null) {
/* 26 */         cache.cleanup();
/* 27 */         cache.scheduleCleanupTrigger();
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/* 32 */   private ReferenceQueue queue = new ReferenceQueue();
/*    */   
/* 34 */   private AtomicInteger invalidationCount = new AtomicInteger(0);
/*    */   
/*    */   public SoftCache() {
/* 37 */     scheduleCleanupTrigger();
/*    */   }
/*    */   
/*    */   private void scheduleCleanupTrigger() {
/* 41 */     Util.getTimer().schedule(Util.createTimerTask(new Cleanup(this)), 30000L);
/*    */   }
/*    */   
/*    */   public SoftCache(Integer expectedSize) {
/* 45 */     super(expectedSize);
/*    */   }
/*    */ 
/*    */   
/*    */   protected IReference createReference(Object information) {
/* 50 */     IReferenceAdapterPool.PoolableIReferenceAdapter ret = IReferenceAdapterPool.GLOBAL_INSTANCE.get();
/* 51 */     ret.setReferent(new SoftReference(information, this.queue));
/* 52 */     return (IReference)ret;
/*    */   }
/*    */   
/*    */   public void cleanup() {
/* 56 */     while (this.queue.poll() != null) {
/* 57 */       this.invalidationCount.incrementAndGet();
/*    */     }
/* 59 */     if (this.invalidationCount.get() > getCleanupThreshold()) {
/* 60 */       this.invalidationCount.set(0);
/* 61 */       super.cleanup();
/*    */     } 
/*    */   }
/*    */   
/*    */   protected int getCleanupThreshold() {
/* 66 */     return 100;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\cache\SoftCache.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */