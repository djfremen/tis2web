/*    */ package com.eoos.collection.implementation;
/*    */ 
/*    */ import com.eoos.collection.MinimalMap;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MinimalMapAdapter
/*    */   implements MinimalMap
/*    */ {
/*    */   private Map map;
/*    */   
/*    */   public MinimalMapAdapter(Map map) {
/* 16 */     this.map = map;
/*    */   }
/*    */ 
/*    */   
/*    */   public Map getAdaptee() {
/* 21 */     return this.map;
/*    */   }
/*    */   
/*    */   public Object get(Object key) {
/* 25 */     return getAdaptee().get(key);
/*    */   }
/*    */   
/*    */   public Object put(Object key, Object value) {
/* 29 */     return getAdaptee().put(key, value);
/*    */   }
/*    */   
/*    */   public Object remove(Object key) {
/* 33 */     return getAdaptee().remove(key);
/*    */   }
/*    */   
/*    */   public Set entrySet() {
/* 37 */     return getAdaptee().entrySet();
/*    */   }
/*    */   
/*    */   public String toString() {
/* 41 */     return super.toString() + "[map:" + String.valueOf(getAdaptee()) + "]";
/*    */   }
/*    */   
/*    */   public Set getKeys() {
/* 45 */     return getAdaptee().keySet();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\collection\implementation\MinimalMapAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */