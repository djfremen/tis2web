/*    */ package com.eoos.persistence.implementation.objectstore;
/*    */ 
/*    */ import com.eoos.cache.Cache;
/*    */ import com.eoos.persistence.FullFeaturedObjectStore;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ObjectStoreCacheWrapper
/*    */   extends WrapperBase
/*    */ {
/*    */   private Cache cache;
/*    */   
/*    */   public ObjectStoreCacheWrapper(FullFeaturedObjectStore objectStore, Cache cache) {
/* 17 */     super(objectStore);
/* 18 */     this.cache = cache;
/*    */   }
/*    */   
/*    */   protected final Cache getCache() {
/* 22 */     return this.cache;
/*    */   }
/*    */   
/*    */   public void store(Object identifier, Object data) {
/* 26 */     synchronized (getCache()) {
/* 27 */       getCache().store(identifier, data);
/*    */     } 
/* 29 */     getWrappedStore().store(identifier, data);
/*    */   }
/*    */ 
/*    */   
/*    */   public Object load(Object identifier) {
/* 34 */     synchronized (getCache()) {
/* 35 */       Object retValue = getCache().lookup(identifier);
/* 36 */       if (retValue == null) {
/* 37 */         retValue = getWrappedStore().load(identifier);
/* 38 */         getCache().store(identifier, retValue);
/*    */       } 
/* 40 */       return retValue;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void delete(Object identifier) {
/* 45 */     synchronized (getCache()) {
/* 46 */       getCache().remove(identifier);
/*    */     } 
/* 48 */     getWrappedStore().delete(identifier);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\persistence\implementation\objectstore\ObjectStoreCacheWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */