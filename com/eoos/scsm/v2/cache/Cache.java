/*    */ package com.eoos.scsm.v2.cache;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface Cache
/*    */ {
/* 16 */   public static final DummyCache DUMMY = new DummyCache()
/*    */     {
/*    */       public void store(Object key, Object information) {}
/*    */ 
/*    */       
/*    */       public Object lookup(Object key) {
/* 22 */         return null;
/*    */       }
/*    */ 
/*    */       
/*    */       public void clear() {}
/*    */       
/*    */       public int size() {
/* 29 */         return 0;
/*    */       }
/*    */       
/*    */       public Set getKeys() {
/* 33 */         return Collections.EMPTY_SET;
/*    */       }
/*    */       
/*    */       public void cleanup() {}
/*    */     };
/*    */   
/*    */   Object lookup(Object paramObject);
/*    */   
/*    */   void store(Object paramObject1, Object paramObject2);
/*    */   
/*    */   void clear();
/*    */   
/*    */   public static interface Cleanup {
/*    */     void cleanup();
/*    */   }
/*    */   
/*    */   public static interface BulkStore {
/*    */     void storeAll(Map param1Map);
/*    */   }
/*    */   
/*    */   public static interface KeyQuery {
/*    */     Set getKeys();
/*    */   }
/*    */   
/*    */   public static interface Size {
/*    */     int size();
/*    */   }
/*    */   
/*    */   public static interface DummyCache extends Cache, Size, KeyQuery, Cleanup {}
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\cache\Cache.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */