/*    */ package com.eoos.context;
/*    */ 
/*    */ import com.eoos.filter.Filter;
/*    */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.HashSet;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
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
/*    */ public class Context
/*    */   implements IContext
/*    */ {
/* 24 */   private Map objectMap = new ConcurrentHashMap<Object, Object>();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected final Map getObjectMap() {
/* 30 */     return Collections.unmodifiableMap(this.objectMap);
/*    */   }
/*    */   
/*    */   public void storeObject(Object identifier, Object object) {
/* 34 */     this.objectMap.put(identifier, object);
/*    */   }
/*    */   
/*    */   public Object getObject(Object identifier) {
/* 38 */     return this.objectMap.get(identifier);
/*    */   }
/*    */   
/*    */   public Collection getObjects(Filter filter) {
/* 42 */     return CollectionUtil.filterAndReturn(new HashSet(this.objectMap.values()), filter);
/*    */   }
/*    */   
/*    */   public void clear() {
/* 46 */     this.objectMap.clear();
/*    */   }
/*    */   
/*    */   public void removeObject(Object identifier) {
/* 50 */     this.objectMap.remove(identifier);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\context\Context.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */