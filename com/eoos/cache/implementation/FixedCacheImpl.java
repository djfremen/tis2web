/*    */ package com.eoos.cache.implementation;
/*    */ 
/*    */ import com.eoos.cache.Cache;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FixedCacheImpl
/*    */   implements Cache, Cache.Size
/*    */ {
/* 15 */   private Map store = Collections.synchronizedMap(new HashMap<Object, Object>());
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object lookup(Object key) {
/* 28 */     return this.store.get(key);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void store(Object key, Object information) {
/* 35 */     this.store.put(key, information);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void remove(Object key) {
/* 42 */     this.store.remove(key);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Collection getKeys() {
/* 49 */     return this.store.keySet();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void clear() {
/* 56 */     this.store.clear();
/*    */   }
/*    */   
/*    */   public int getSize() {
/* 60 */     return this.store.size();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\cache\implementation\FixedCacheImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */