/*    */ package com.eoos.gm.tis2web.sids.implementation.test;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
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
/*    */ public class SIAttrValMap
/*    */   implements AttributeValueMap
/*    */ {
/* 22 */   public Map map = new HashMap<Object, Object>();
/*    */   
/*    */   public void set(Attribute attribute, Value value) {
/* 25 */     this.map.put(attribute, value);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void remove(Attribute attribute) {
/* 32 */     this.map.remove(attribute);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Collection getAttributes() {
/* 40 */     return this.map.keySet();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Value getValue(Attribute attribute) {
/* 47 */     return (Value)this.map.get(attribute);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void main(String[] args) {}
/*    */   
/*    */   public void exchange(Attribute attribute, Value value) {
/* 54 */     if (this.map.containsKey(attribute))
/* 55 */       this.map.put(attribute, value); 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sids\implementation\test\SIAttrValMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */