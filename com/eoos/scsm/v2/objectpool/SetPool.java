/*    */ package com.eoos.scsm.v2.objectpool;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.Counter;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.lang.ref.SoftReference;
/*    */ import java.util.Collection;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SetPool
/*    */ {
/* 15 */   private static final Logger log = Logger.getLogger(SetPool.class);
/*    */   
/*    */   private ObjectPool delegate;
/*    */   
/* 19 */   private Counter requestCount = new Counter();
/*    */   
/* 21 */   private Counter creationCount = new Counter();
/*    */ 
/*    */   
/*    */   private Util.ShutdownListener listener;
/*    */ 
/*    */   
/*    */   public SetPool() {
/* 28 */     this.delegate = ObjectPool.createInstance(new ObjectPool.SPI()
/*    */         {
/*    */           public void reset(Object object) {
/* 31 */             ((Set)object).clear();
/*    */           }
/*    */           
/*    */           public Object create() {
/* 35 */             SetPool.this.creationCount.inc();
/* 36 */             return new HashSet();
/*    */           }
/*    */         });
/*    */ 
/*    */     
/* 41 */     this.listener = Util.addShutdownListener(new Util.ShutdownListener()
/*    */         {
/*    */           public void onShutdown() {
/* 44 */             SetPool.log.info("object pool statistic - requests: " + SetPool.this.requestCount.getCount() + ", objects: " + SetPool.this.creationCount.getCount());
/*    */           }
/*    */         });
/*    */   }
/*    */   
/* 49 */   private static ThreadLocal instance = new ThreadLocal();
/*    */   
/*    */   public static SetPool getThreadInstance() {
/* 52 */     SoftReference<SetPool> srPool = instance.get();
/* 53 */     SetPool ret = (srPool != null) ? srPool.get() : null;
/* 54 */     if (ret == null) {
/* 55 */       ret = new SetPool();
/* 56 */       instance.set(new SoftReference<SetPool>(ret));
/*    */     } 
/* 58 */     return ret;
/*    */   }
/*    */   
/*    */   public Set get() {
/* 62 */     this.requestCount.inc();
/* 63 */     return (Set)this.delegate.get();
/*    */   }
/*    */ 
/*    */   
/*    */   public Set get(Collection c) {
/* 68 */     Set ret = get();
/* 69 */     ret.addAll(c);
/* 70 */     return ret;
/*    */   }
/*    */   
/*    */   public void free(Set set) {
/* 74 */     this.delegate.free(set);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\objectpool\SetPool.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */