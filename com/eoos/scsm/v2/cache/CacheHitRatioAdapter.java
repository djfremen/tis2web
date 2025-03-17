/*    */ package com.eoos.scsm.v2.cache;
/*    */ 
/*    */ import com.eoos.math.AverageCalculator;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.math.BigDecimal;
/*    */ import java.math.BigInteger;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CacheHitRatioAdapter
/*    */   implements Cache
/*    */ {
/* 17 */   private static final Logger log = Logger.getLogger(CacheHitRatioAdapter.class);
/*    */   
/* 19 */   private static final BigDecimal ONE = BigDecimal.valueOf(1L);
/* 20 */   private static final BigDecimal ZERO = BigDecimal.valueOf(0L);
/*    */   
/*    */   private Cache backend;
/* 23 */   private AverageCalculator ac = new AverageCalculator(2);
/*    */   
/*    */   public CacheHitRatioAdapter(Cache cache) {
/* 26 */     this.backend = cache;
/*    */   }
/*    */   
/*    */   public void clear() {
/* 30 */     this.backend.clear();
/*    */   }
/*    */   
/*    */   public Object lookup(Object key) {
/* 34 */     Object ret = this.backend.lookup(key);
/* 35 */     if (ret != null) {
/* 36 */       this.ac.add(ONE);
/* 37 */       if (log.isDebugEnabled()) {
/* 38 */         log.debug("cache lookup successful: " + String.valueOf(key) + " -> " + Util.toString(ret));
/*    */       }
/*    */     } else {
/* 41 */       this.ac.add(ZERO);
/*    */     } 
/* 43 */     return ret;
/*    */   }
/*    */   
/*    */   public void store(Object key, Object information) {
/* 47 */     this.backend.store(key, information);
/*    */   }
/*    */   
/*    */   public BigDecimal getHitRatio() {
/* 51 */     return this.ac.getCurrentAverage();
/*    */   }
/*    */   
/*    */   public BigInteger getRequestCount() {
/* 55 */     return this.ac.getValueCount();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\cache\CacheHitRatioAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */