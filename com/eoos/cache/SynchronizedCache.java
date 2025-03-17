/*    */ package com.eoos.cache;
/*    */ 
/*    */ import java.util.Collection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SynchronizedCache
/*    */   implements Cache
/*    */ {
/*    */   private Cache backend;
/*    */   
/*    */   public SynchronizedCache(Cache cache) {
/* 17 */     this.backend = cache;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized Object lookup(Object key) {
/* 24 */     return this.backend.lookup(key);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized void store(Object key, Object information) {
/* 31 */     this.backend.store(key, information);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized void remove(Object key) {
/* 38 */     this.backend.remove(key);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized Collection getKeys() {
/* 45 */     return this.backend.getKeys();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized void clear() {
/* 52 */     this.backend.clear();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\cache\SynchronizedCache.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */