/*    */ package com.eoos.scsm.v2.collection;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class SequentialMap
/*    */   implements SimpleMap {
/*  9 */   private SequentialMap delegate = null;
/*    */   
/* 11 */   private Object key = null;
/*    */   
/* 13 */   private Object value = null;
/*    */   
/*    */   public SequentialMap(SequentialMap backend, Object key, Object value) {
/* 16 */     this.delegate = backend;
/* 17 */     this.key = key;
/* 18 */     this.value = value;
/*    */   }
/*    */   
/*    */   public SequentialMap() {
/* 22 */     this(null, null, null);
/*    */   }
/*    */   
/*    */   public Object get(Object key) {
/* 26 */     if (Util.equals(this.key, key)) {
/* 27 */       return this.value;
/*    */     }
/* 29 */     return (this.delegate != null) ? this.delegate.get(key) : null;
/*    */   }
/*    */ 
/*    */   
/*    */   public SimpleMap put(Object key, Object value) {
/* 34 */     return new SequentialMap(this, key, value);
/*    */   }
/*    */   
/*    */   public SimpleMap remove(Object key) {
/* 38 */     return new SequentialMap(this, key, null);
/*    */   }
/*    */   
/*    */   private void collectAssignments(Map<Object, Object> map) {
/* 42 */     if (this.delegate != null) {
/* 43 */       this.delegate.collectAssignments(map);
/*    */     }
/* 45 */     map.put(this.key, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map toMap() {
/* 50 */     Map<Object, Object> ret = new LinkedHashMap<Object, Object>();
/* 51 */     collectAssignments(ret);
/* 52 */     return ret;
/*    */   }
/*    */   
/*    */   private String _toString() {
/* 56 */     String ret = String.valueOf(this.key) + "=" + String.valueOf(this.value);
/* 57 */     if (this.delegate != null) {
/* 58 */       ret = ret + ", " + this.delegate._toString();
/*    */     }
/* 60 */     return ret;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 65 */     return "[" + _toString() + "]";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\collection\SequentialMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */