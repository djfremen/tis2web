/*    */ package com.eoos.scsm.v2.cache.util;
/*    */ 
/*    */ import com.eoos.scsm.v2.cache.Cache;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ public class BulkStoreFacade
/*    */   implements Cache.BulkStore
/*    */ {
/*    */   private Cache backend;
/*    */   private Cache.BulkStore bulkStore;
/*    */   
/*    */   public BulkStoreFacade(Cache backend) {
/* 15 */     this.backend = backend;
/* 16 */     if (this.backend instanceof Cache.BulkStore) {
/* 17 */       this.bulkStore = (Cache.BulkStore)this.backend;
/*    */     }
/*    */   }
/*    */   
/*    */   public void storeAll(Map map) {
/* 22 */     if (this.bulkStore != null) {
/* 23 */       this.bulkStore.storeAll(map);
/*    */     } else {
/* 25 */       for (Iterator<Map.Entry> iter = map.entrySet().iterator(); iter.hasNext(); ) {
/* 26 */         Map.Entry entry = iter.next();
/* 27 */         this.backend.store(entry.getKey(), entry.getValue());
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\cach\\util\BulkStoreFacade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */