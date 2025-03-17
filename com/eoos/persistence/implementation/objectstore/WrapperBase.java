/*    */ package com.eoos.persistence.implementation.objectstore;
/*    */ 
/*    */ import com.eoos.persistence.FullFeaturedObjectStore;
/*    */ import com.eoos.persistence.mixin.BulkLoad;
/*    */ import com.eoos.persistence.mixin.BulkStore;
/*    */ import java.util.Collection;
/*    */ 
/*    */ 
/*    */ public class WrapperBase
/*    */   implements FullFeaturedObjectStore
/*    */ {
/* 12 */   private FullFeaturedObjectStore wrappedStore = null;
/*    */   
/*    */   protected WrapperBase(FullFeaturedObjectStore objectStore) {
/* 15 */     this.wrappedStore = objectStore;
/*    */   }
/*    */   
/*    */   protected final FullFeaturedObjectStore getWrappedStore() {
/* 19 */     return this.wrappedStore;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void store(Object identifier, Object data) {
/* 27 */     getWrappedStore().store(identifier, data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object load(Object identifier) {
/* 35 */     return getWrappedStore().load(identifier);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void delete(Object identifier) {
/* 43 */     getWrappedStore().delete(identifier);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void load(BulkLoad.LoadAdapter adapter) {
/* 51 */     getWrappedStore().load(adapter);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void store(BulkStore.StoreAdapter adapter) {
/* 59 */     getWrappedStore().store(adapter);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Collection getIdentifiers() {
/* 67 */     return getWrappedStore().getIdentifiers();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean exists(Object identifier) {
/* 75 */     return getWrappedStore().exists(identifier);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\persistence\implementation\objectstore\WrapperBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */