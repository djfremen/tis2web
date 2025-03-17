/*    */ package com.eoos.scsm.v2.cache.util;
/*    */ 
/*    */ import com.eoos.cache.Cache;
/*    */ import com.eoos.scsm.v2.cache.Cache;
/*    */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class V1Adapter
/*    */   implements Cache, Cache.KeyQuery, Cache.Size
/*    */ {
/*    */   private Cache cache;
/*    */   private Cache.Size cacheSize;
/*    */   
/*    */   public V1Adapter(Cache cache) {
/* 15 */     this.cache = cache;
/* 16 */     if (cache instanceof Cache.Size) {
/* 17 */       this.cacheSize = (Cache.Size)cache;
/*    */     } else {
/* 19 */       this.cacheSize = null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void clear() {
/* 24 */     this.cache.clear();
/*    */   }
/*    */   
/*    */   public Object lookup(Object key) {
/* 28 */     return this.cache.lookup(key);
/*    */   }
/*    */   
/*    */   public void remove(Object key) {
/* 32 */     this.cache.remove(key);
/*    */   }
/*    */   
/*    */   public void store(Object key, Object information) {
/* 36 */     this.cache.store(key, information);
/*    */   }
/*    */   
/*    */   public Set getKeys() {
/* 40 */     return CollectionUtil.toSet(this.cache.getKeys());
/*    */   }
/*    */   
/*    */   public int size() {
/* 44 */     if (this.cacheSize == null) {
/* 45 */       return getKeys().size();
/*    */     }
/* 47 */     return this.cacheSize.getSize();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\cach\\util\V1Adapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */