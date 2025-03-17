/*    */ package com.eoos.gm.tis2web.sids.implementation;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.HashSet;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ServiceIDAttributeMap
/*    */   extends HashMap
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String serviceID;
/*    */   private int add_VehID;
/*    */   
/*    */   public ServiceIDAttributeMap(String serviceID, int add_VehID) {
/* 22 */     this.serviceID = serviceID;
/* 23 */     this.add_VehID = add_VehID;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getServiceID() {
/* 30 */     return this.serviceID;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setServiceID(String serviceID) {
/* 38 */     this.serviceID = serviceID;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void addValuesMap(Map attrMap, Map unknownAttrs) {
/* 44 */     Iterator<Map.Entry<K, V>> it = entrySet().iterator();
/* 45 */     while (it.hasNext()) {
/* 46 */       Map.Entry entry = it.next();
/* 47 */       if (!attrMap.containsKey(entry.getKey())) {
/* 48 */         Set valSet = (Set)unknownAttrs.get(entry.getKey());
/* 49 */         if (valSet == null) {
/* 50 */           valSet = new HashSet();
/* 51 */           unknownAttrs.put(entry.getKey(), valSet);
/*    */         } 
/* 53 */         valSet.add(entry.getValue());
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getAdd_VehID() {
/* 63 */     return this.add_VehID;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sids\implementation\ServiceIDAttributeMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */