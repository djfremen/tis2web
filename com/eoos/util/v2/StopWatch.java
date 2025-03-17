/*    */ package com.eoos.util.v2;
/*    */ 
/*    */ import com.eoos.scsm.v2.objectpool.ObjectPool;
/*    */ import java.lang.ref.SoftReference;
/*    */ 
/*    */ 
/*    */ public final class StopWatch
/*    */ {
/*  9 */   private static SoftReference<ObjectPool> srPool = null;
/*    */   
/* 11 */   private long tsStart = -1L;
/*    */   
/* 13 */   private long tsStop = -1L;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public StopWatch start() {
/* 19 */     this.tsStart = System.currentTimeMillis();
/* 20 */     this.tsStop = -1L;
/* 21 */     return this;
/*    */   }
/*    */   
/*    */   public boolean started() {
/* 25 */     return (this.tsStart != -1L);
/*    */   }
/*    */   
/*    */   public boolean stopped() {
/* 29 */     return (this.tsStop != -1L);
/*    */   }
/*    */   
/*    */   private static synchronized ObjectPool getPool() {
/* 33 */     ObjectPool instance = null;
/* 34 */     if (srPool == null || (instance = srPool.get()) == null) {
/* 35 */       instance = ObjectPool.createInstance(new ObjectPool.SPI()
/*    */           {
/*    */             public void reset(Object object) {
/* 38 */               StopWatch sw = (StopWatch)object;
/* 39 */               sw.tsStart = -1L;
/* 40 */               sw.tsStop = -1L;
/*    */             }
/*    */             
/*    */             public Object create() {
/* 44 */               return new StopWatch();
/*    */             }
/*    */           });
/*    */       
/* 48 */       srPool = new SoftReference<ObjectPool>(instance);
/*    */     } 
/* 50 */     return instance;
/*    */   }
/*    */   
/*    */   public static StopWatch getInstance() {
/* 54 */     return (StopWatch)getPool().get();
/*    */   }
/*    */   
/*    */   public static void freeInstance(StopWatch sw) {
/* 58 */     if (sw != null) {
/* 59 */       getPool().free(sw);
/*    */     }
/*    */   }
/*    */   
/*    */   public long stop() {
/* 64 */     if (this.tsStart == -1L) {
/* 65 */       throw new IllegalStateException("not started");
/*    */     }
/* 67 */     this.tsStop = System.currentTimeMillis();
/* 68 */     return this.tsStop - this.tsStart;
/*    */   }
/*    */   
/*    */   public long getElapsedTime() {
/* 72 */     if (this.tsStart == -1L) {
/* 73 */       throw new IllegalStateException("not started");
/*    */     }
/*    */     
/* 76 */     if (this.tsStop == -1L) {
/* 77 */       return System.currentTimeMillis() - this.tsStart;
/*    */     }
/* 79 */     return this.tsStop - this.tsStart;
/*    */   }
/*    */   
/*    */   private StopWatch() {}
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoo\\util\v2\StopWatch.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */