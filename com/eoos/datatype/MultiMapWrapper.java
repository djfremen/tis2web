/*     */ package com.eoos.datatype;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MultiMapWrapper
/*     */   implements Map
/*     */ {
/*     */   private Map backend;
/*     */   
/*     */   public MultiMapWrapper(Map backend) {
/*  18 */     this.backend = backend;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/*  25 */     return this.backend.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  32 */     return this.backend.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsKey(Object key) {
/*  39 */     return this.backend.containsKey(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsValue(Object value) {
/*  46 */     boolean found = false;
/*  47 */     synchronized (this.backend) {
/*  48 */       Iterator<Collection> iter = this.backend.values().iterator();
/*  49 */       while (iter.hasNext() && !found) {
/*  50 */         Collection collection = iter.next();
/*  51 */         found = collection.contains(value);
/*     */       } 
/*     */     } 
/*  54 */     return found;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object get(Object key) {
/*  61 */     return this.backend.get(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object put(Object key, Object value) {
/*  68 */     Collection<Object> collection = (Collection)this.backend.get(key);
/*  69 */     if (collection == null) {
/*  70 */       collection = createBackingCollection();
/*  71 */       this.backend.put(key, collection);
/*     */     } 
/*     */     
/*  74 */     Object retValue = null;
/*  75 */     if (!collection.add(value)) {
/*  76 */       retValue = value;
/*     */     }
/*  78 */     return retValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object remove(Object key) {
/*  85 */     return this.backend.remove(key);
/*     */   }
/*     */   
/*     */   public void removeValue(Object key, Object value) {
/*  89 */     Collection collection = (Collection)this.backend.get(key);
/*  90 */     if (collection != null) {
/*  91 */       collection.remove(value);
/*     */     }
/*     */   }
/*     */   
/*     */   public void removeValue(Object value) {
/*  96 */     synchronized (this.backend) {
/*  97 */       Iterator<Collection> iter = this.backend.values().iterator();
/*  98 */       while (iter.hasNext()) {
/*  99 */         Collection collection = iter.next();
/* 100 */         if (collection.contains(value)) {
/* 101 */           collection.remove(value);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map t) {
/* 111 */     if (!(t instanceof MultiMapWrapper)) {
/* 112 */       throw new UnsupportedOperationException("map has to be of type MultiMap");
/*     */     }
/* 114 */     this.backend.putAll(t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 122 */     this.backend.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set keySet() {
/* 129 */     return this.backend.keySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection values() {
/* 136 */     return this.backend.values();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set entrySet() {
/* 143 */     return this.backend.entrySet();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 147 */     return this.backend.toString();
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 151 */     return this.backend.hashCode();
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj) {
/* 155 */     return this.backend.equals(obj);
/*     */   }
/*     */   
/*     */   public static MultiMapWrapper reverse(Map map) {
/* 159 */     MultiMapWrapper retValue = new MultiMapWrapper(new HashMap<Object, Object>());
/* 160 */     for (Iterator<Map.Entry> iter = map.entrySet().iterator(); iter.hasNext(); ) {
/* 161 */       Map.Entry entry = iter.next();
/* 162 */       retValue.put(entry.getValue(), entry.getKey());
/*     */     } 
/*     */     
/* 165 */     return retValue;
/*     */   }
/*     */   
/*     */   protected Collection createBackingCollection() {
/* 169 */     return new LinkedHashSet();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\datatype\MultiMapWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */