/*    */ package com.eoos.persistence.util;
/*    */ 
/*    */ import com.eoos.persistence.ObjectStore;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class StoreDispatcher
/*    */   implements ObjectStore
/*    */ {
/*    */   public void store(Object identifier, Object data) {
/* 17 */     getStore(identifier).store(identifier, data);
/*    */   }
/*    */   
/*    */   protected abstract ObjectStore getStore(Object paramObject);
/*    */   
/*    */   public Object load(Object identifier) {
/* 23 */     return getStore(identifier).load(identifier);
/*    */   }
/*    */   
/*    */   public void delete(Object identifier) {
/* 27 */     getStore(identifier).delete(identifier);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\persistenc\\util\StoreDispatcher.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */