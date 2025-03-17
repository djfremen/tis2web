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
/*    */ public class WriteThroughCacheAdapter
/*    */ {
/*    */   private Cache cache;
/*    */   private WriteBackOperation writeBackOperation;
/*    */   
/*    */   public WriteThroughCacheAdapter(Cache cache, WriteBackOperation writeBackOperation) {
/* 18 */     this.cache = cache;
/* 19 */     this.writeBackOperation = writeBackOperation;
/*    */   }
/*    */   
/*    */   public Object write(Object identifier, Object object) {
/* 23 */     this.cache.store(identifier, object);
/* 24 */     return this.writeBackOperation.write(identifier, object);
/*    */   }
/*    */   
/*    */   public static interface WriteBackOperation {
/*    */     Object write(Object param1Object1, Object param1Object2);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\cache\adapter\WriteThroughCacheAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */