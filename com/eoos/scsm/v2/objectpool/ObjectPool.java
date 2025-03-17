/*    */ package com.eoos.scsm.v2.objectpool;
/*    */ 
/*    */ import java.util.Queue;
/*    */ import java.util.concurrent.ConcurrentLinkedQueue;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ObjectPool
/*    */   implements IObjectPool
/*    */ {
/*    */   private SPI spi;
/* 18 */   private final Queue objects = new ConcurrentLinkedQueue();
/*    */   
/*    */   private ObjectPool(SPI spi) {
/* 21 */     this.spi = spi;
/*    */   }
/*    */   
/*    */   public static ObjectPool createInstance(SPI spi) {
/* 25 */     return new ObjectPool(spi);
/*    */   }
/*    */   
/*    */   public Object get() {
/* 29 */     Object ret = this.objects.poll();
/* 30 */     if (ret == null) {
/* 31 */       ret = this.spi.create();
/*    */     }
/* 33 */     return ret;
/*    */   }
/*    */ 
/*    */   
/*    */   public void free(Object object) {
/* 38 */     if (object != null) {
/* 39 */       this.spi.reset(object);
/* 40 */       this.objects.add(object);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void clear() {
/* 45 */     this.objects.clear();
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean traceFailedInsertion() {
/* 50 */     return false;
/*    */   }
/*    */   
/*    */   public static interface SPI {
/*    */     Object create();
/*    */     
/*    */     void reset(Object param1Object);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\objectpool\ObjectPool.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */