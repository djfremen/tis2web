/*    */ package com.eoos.ref.v3;
/*    */ 
/*    */ import com.eoos.scsm.v2.objectpool.ObjectPool;
/*    */ import com.eoos.scsm.v2.util.Counter;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.lang.ref.Reference;
/*    */ import java.lang.ref.SoftReference;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IReferenceAdapterPool
/*    */ {
/*    */   public static final class PoolableIReferenceAdapter
/*    */     implements IReference
/*    */   {
/* 17 */     private Reference reference = null;
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public void setReferent(Reference reference) {
/* 23 */       this.reference = reference;
/*    */     }
/*    */     
/*    */     public Object get() {
/* 27 */       return this.reference.get();
/*    */     }
/*    */     
/*    */     public void clear() {
/* 31 */       this.reference.clear();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/* 36 */   private static final Logger log = Logger.getLogger(IReferenceAdapterPool.class);
/*    */   
/* 38 */   public static final IReferenceAdapterPool GLOBAL_INSTANCE = new IReferenceAdapterPool();
/*    */   
/*    */   private ObjectPool delegate;
/*    */   
/* 42 */   private Counter requestCount = new Counter();
/*    */   
/* 44 */   private Counter creationCount = new Counter();
/*    */ 
/*    */   
/*    */   private Util.ShutdownListener listener;
/*    */ 
/*    */   
/*    */   private IReferenceAdapterPool() {
/* 51 */     this.delegate = ObjectPool.createInstance(new ObjectPool.SPI()
/*    */         {
/*    */           public void reset(Object object) {
/* 54 */             ((IReferenceAdapterPool.PoolableIReferenceAdapter)object).clear();
/*    */           }
/*    */           
/*    */           public Object create() {
/* 58 */             IReferenceAdapterPool.this.creationCount.inc();
/* 59 */             return new IReferenceAdapterPool.PoolableIReferenceAdapter();
/*    */           }
/*    */         });
/*    */ 
/*    */     
/* 64 */     this.listener = Util.addShutdownListener(new Util.ShutdownListener()
/*    */         {
/*    */           public void onShutdown() {
/* 67 */             IReferenceAdapterPool.log.info("object pool statistic - requests: " + IReferenceAdapterPool.this.requestCount.getCount() + ", objects: " + IReferenceAdapterPool.this.creationCount.getCount());
/*    */           }
/*    */         });
/*    */   }
/*    */   
/* 72 */   private static ThreadLocal instance = new ThreadLocal();
/*    */   
/*    */   public static IReferenceAdapterPool getThreadInstance() {
/* 75 */     SoftReference<IReferenceAdapterPool> srPool = instance.get();
/* 76 */     IReferenceAdapterPool ret = (srPool != null) ? srPool.get() : null;
/* 77 */     if (ret == null) {
/* 78 */       ret = new IReferenceAdapterPool();
/* 79 */       instance.set(new SoftReference<IReferenceAdapterPool>(ret));
/*    */     } 
/* 81 */     return ret;
/*    */   }
/*    */   
/*    */   public PoolableIReferenceAdapter get() {
/* 85 */     this.requestCount.inc();
/* 86 */     return (PoolableIReferenceAdapter)this.delegate.get();
/*    */   }
/*    */ 
/*    */   
/*    */   public PoolableIReferenceAdapter get(Reference reference) {
/* 91 */     PoolableIReferenceAdapter ret = get();
/* 92 */     ret.setReferent(reference);
/* 93 */     return ret;
/*    */   }
/*    */   
/*    */   public void free(IReference obj) {
/* 97 */     if (obj instanceof PoolableIReferenceAdapter)
/* 98 */       this.delegate.free(obj); 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\ref\v3\IReferenceAdapterPool.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */