/*    */ package com.eoos.gm.tis2web.si.implementation.statcont.datamodel.system;
/*    */ 
/*    */ import com.eoos.scsm.v2.cache.Cache;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RTCache
/*    */ {
/* 11 */   private static final Logger log = Logger.getLogger(RTCache.class);
/*    */ 
/*    */   
/*    */   private Cache cache;
/*    */ 
/*    */   
/*    */   private Retrieval retrieval;
/*    */ 
/*    */ 
/*    */   
/*    */   public RTCache(Cache cache, Retrieval retrieval) {
/* 22 */     this.cache = cache;
/* 23 */     this.retrieval = retrieval;
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] get(String path) throws Exception {
/* 28 */     byte[] retValue = null;
/*    */     
/*    */     try {
/* 31 */       retValue = (byte[])this.cache.lookup(path);
/* 32 */       if (retValue == null) {
/* 33 */         retValue = this.retrieval.get(path);
/* 34 */         this.cache.store(path, retValue);
/*    */       } 
/* 36 */     } catch (Exception e) {
/* 37 */       log.warn("unable to cache data for identifier:" + String.valueOf(path) + " - exception:" + e, e);
/*    */     } 
/*    */     
/* 40 */     return retValue;
/*    */   }
/*    */   
/*    */   public static interface Retrieval {
/*    */     byte[] get(String param1String) throws Exception;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\statcont\datamodel\system\RTCache.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */