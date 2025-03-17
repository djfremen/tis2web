/*    */ package com.eoos.scsm.v2.objectpool;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.Counter;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.lang.ref.SoftReference;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StringBufferPool
/*    */ {
/* 12 */   private static final Logger log = Logger.getLogger(StringBufferPool.class);
/*    */   
/*    */   private static final int LENGTH_THRESHOLD = 51200;
/*    */   
/*    */   private ObjectPool delegate;
/*    */   
/* 18 */   private Counter requestCount = new Counter();
/*    */   
/* 20 */   private Counter creationCount = new Counter();
/*    */ 
/*    */   
/*    */   private Util.ShutdownListener listener;
/*    */ 
/*    */   
/*    */   private StringBufferPool() {
/* 27 */     this.delegate = ObjectPool.createInstance(new ObjectPool.SPI()
/*    */         {
/*    */           public void reset(Object object) {
/* 30 */             ((StringBuffer)object).setLength(0);
/*    */           }
/*    */           
/*    */           public Object create() {
/* 34 */             StringBufferPool.this.creationCount.inc();
/* 35 */             return new StringBuffer();
/*    */           }
/*    */         });
/*    */ 
/*    */     
/* 40 */     this.listener = Util.addShutdownListener(new Util.ShutdownListener()
/*    */         {
/*    */           public void onShutdown() {
/* 43 */             StringBufferPool.log.info("object pool statistic - requests: " + StringBufferPool.this.requestCount.getCount() + ", objects: " + StringBufferPool.this.creationCount.getCount());
/*    */           }
/*    */         });
/*    */   }
/*    */   
/* 48 */   private static ThreadLocal instance = new ThreadLocal();
/*    */   
/*    */   public static StringBufferPool getThreadInstance() {
/* 51 */     SoftReference<StringBufferPool> srPool = instance.get();
/* 52 */     StringBufferPool ret = (srPool != null) ? srPool.get() : null;
/* 53 */     if (ret == null) {
/* 54 */       ret = new StringBufferPool();
/* 55 */       instance.set(new SoftReference<StringBufferPool>(ret));
/*    */     } 
/* 57 */     return ret;
/*    */   }
/*    */   
/*    */   public StringBuffer get() {
/* 61 */     this.requestCount.inc();
/* 62 */     return (StringBuffer)this.delegate.get();
/*    */   }
/*    */ 
/*    */   
/*    */   public StringBuffer get(int length) {
/* 67 */     StringBuffer ret = get();
/* 68 */     ret.ensureCapacity(length);
/* 69 */     return ret;
/*    */   }
/*    */   
/*    */   public StringBuffer get(CharSequence sequence) {
/* 73 */     StringBuffer ret = get();
/* 74 */     ret.append(sequence);
/* 75 */     return ret;
/*    */   }
/*    */   
/*    */   public void free(StringBuffer buffer) {
/* 79 */     if (buffer != null && buffer.capacity() < 51200) {
/* 80 */       this.delegate.free(buffer);
/*    */     } else {
/* 82 */       log.debug("dropping buffer because it is too large");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\objectpool\StringBufferPool.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */