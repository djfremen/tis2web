/*    */ package com.eoos.scsm.v2.collection;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedHashSet;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class MultiMap
/*    */   extends HashMap
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public static interface Settings
/*    */   {
/* 16 */     public static final Settings DEFAULT = new Settings()
/*    */       {
/*    */         public Collection createBackingCollection(Object key) {
/* 19 */           return new LinkedHashSet();
/*    */         }
/*    */       };
/*    */ 
/*    */     
/*    */     Collection createBackingCollection(Object param1Object);
/*    */   }
/*    */   
/* 27 */   private Settings settings = Settings.DEFAULT;
/*    */ 
/*    */   
/*    */   public MultiMap() {}
/*    */   
/*    */   public MultiMap(Settings settings) {
/* 33 */     this.settings = settings;
/*    */   }
/*    */   
/*    */   public boolean containsValue(Object value) {
/* 37 */     boolean found = false;
/* 38 */     Iterator<V> iter = values().iterator();
/* 39 */     while (iter.hasNext() && !found) {
/* 40 */       Collection collection = (Collection)iter.next();
/* 41 */       found = collection.contains(value);
/*    */     } 
/*    */     
/* 44 */     return found;
/*    */   }
/*    */   
/*    */   public Object put(Object key, Object value) {
/* 48 */     Collection<Object> collection = (Collection)get(key);
/* 49 */     if (collection == null) {
/* 50 */       collection = this.settings.createBackingCollection(key);
/* 51 */       super.put(key, collection);
/*    */     } 
/*    */     
/* 54 */     Object retValue = null;
/* 55 */     if (!collection.add(value)) {
/* 56 */       retValue = value;
/*    */     }
/* 58 */     return retValue;
/*    */   }
/*    */   
/*    */   public void removeValue(Object key, Object value) {
/* 62 */     Collection collection = (Collection)get(key);
/* 63 */     if (collection != null) {
/* 64 */       collection.remove(value);
/*    */     }
/*    */   }
/*    */   
/*    */   public void removeValue(Object value) {
/* 69 */     Iterator<V> iter = values().iterator();
/* 70 */     while (iter.hasNext()) {
/* 71 */       Collection collection = (Collection)iter.next();
/* 72 */       if (collection.contains(value)) {
/* 73 */         collection.remove(value);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void putAll(Map<?, ?> t) {
/* 84 */     if (!(t instanceof MultiMap)) {
/* 85 */       throw new UnsupportedOperationException("map has to be of type MultiMap");
/*    */     }
/* 87 */     super.putAll(t);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\collection\MultiMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */