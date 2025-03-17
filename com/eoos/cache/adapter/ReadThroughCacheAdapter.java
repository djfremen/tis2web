/*    */ package com.eoos.cache.adapter;
/*    */ 
/*    */ import com.eoos.cache.Cache;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReadThroughCacheAdapter
/*    */ {
/*    */   private Cache cache;
/*    */   private Retrieval retrieval;
/*    */   
/*    */   public ReadThroughCacheAdapter(Cache cache, Retrieval retrieval) {
/* 18 */     this.cache = cache;
/* 19 */     this.retrieval = retrieval;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object get(Object identifier) {
/* 24 */     Object retValue = this.cache.lookup(identifier);
/* 25 */     if (retValue == null) {
/* 26 */       retValue = this.retrieval.get(identifier);
/* 27 */       this.cache.store(identifier, retValue);
/*    */     } 
/* 29 */     return retValue;
/*    */   }
/*    */   
/*    */   public static interface Retrieval {
/*    */     Object get(Object param1Object);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\cache\adapter\ReadThroughCacheAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */