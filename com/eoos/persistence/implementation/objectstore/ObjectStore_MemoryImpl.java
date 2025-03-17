/*    */ package com.eoos.persistence.implementation.objectstore;
/*    */ 
/*    */ import com.eoos.persistence.FullFeaturedObjectStore;
/*    */ import com.eoos.persistence.mixin.BulkLoad;
/*    */ import com.eoos.persistence.mixin.BulkStore;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ public class ObjectStore_MemoryImpl
/*    */   implements FullFeaturedObjectStore
/*    */ {
/* 15 */   protected Map map = new HashMap<Object, Object>();
/*    */   
/*    */   public void store(Object identifier, Object data) {
/* 18 */     this.map.put(identifier, data);
/*    */   }
/*    */   
/*    */   public Object load(Object identifier) {
/* 22 */     return this.map.get(identifier);
/*    */   }
/*    */   
/*    */   public void delete(Object identifier) {
/* 26 */     this.map.remove(identifier);
/*    */   }
/*    */ 
/*    */   
/*    */   public void load(BulkLoad.LoadAdapter adapter) {
/* 31 */     for (Iterator iter = adapter.getIdentifiers().iterator(); iter.hasNext(); ) {
/* 32 */       Object identifier = iter.next();
/* 33 */       adapter.setObject(identifier, load(identifier));
/*    */     } 
/*    */   }
/*    */   
/*    */   public void store(BulkStore.StoreAdapter adapter) {
/* 38 */     for (Iterator iter = adapter.getIdentifiers().iterator(); iter.hasNext(); ) {
/* 39 */       Object identifier = iter.next();
/* 40 */       store(identifier, adapter.getObject(identifier));
/*    */     } 
/*    */   }
/*    */   
/*    */   public Collection getIdentifiers() {
/* 45 */     return this.map.keySet();
/*    */   }
/*    */   
/*    */   public boolean exists(Object identifier) {
/* 49 */     return this.map.containsKey(identifier);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\persistence\implementation\objectstore\ObjectStore_MemoryImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */