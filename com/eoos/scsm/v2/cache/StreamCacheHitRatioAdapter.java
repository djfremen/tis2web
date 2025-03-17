/*    */ package com.eoos.scsm.v2.cache;
/*    */ 
/*    */ import com.eoos.math.AverageCalculator;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.math.BigDecimal;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StreamCacheHitRatioAdapter
/*    */   implements StreamCache
/*    */ {
/* 19 */   private static final Logger log = Logger.getLogger(StreamCacheHitRatioAdapter.class);
/*    */   
/* 21 */   private static final BigDecimal ONE = BigDecimal.valueOf(1L);
/* 22 */   private static final BigDecimal ZERO = BigDecimal.valueOf(0L);
/*    */   
/*    */   private StreamCache backend;
/* 25 */   private AverageCalculator ac = new AverageCalculator(2);
/*    */   
/*    */   public StreamCacheHitRatioAdapter(StreamCache cache) {
/* 28 */     this.backend = cache;
/*    */   }
/*    */   
/*    */   public void clear() {
/* 32 */     this.backend.clear();
/*    */   }
/*    */   
/*    */   public InputStream lookup(Object key) throws IOException {
/* 36 */     InputStream ret = this.backend.lookup(key);
/* 37 */     if (ret != null) {
/* 38 */       this.ac.add(ONE);
/* 39 */       if (log.isDebugEnabled()) {
/* 40 */         log.debug("cache lookup successful: " + String.valueOf(key) + " -> " + Util.toString(ret));
/*    */       }
/*    */     } else {
/* 43 */       this.ac.add(ZERO);
/*    */     } 
/* 45 */     return ret;
/*    */   }
/*    */   
/*    */   public BigDecimal getHitRatio() {
/* 49 */     return this.ac.getCurrentAverage();
/*    */   }
/*    */   
/*    */   public OutputStream getStorageStream(Object key) throws IOException {
/* 53 */     return this.backend.getStorageStream(key);
/*    */   }
/*    */   
/*    */   public StreamCache getBackendCache() {
/* 57 */     return this.backend;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\cache\StreamCacheHitRatioAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */