/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation.avmap;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ import java.io.Serializable;
/*    */ import java.util.Collection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ImmutableWrapper
/*    */   implements AttributeValueMap, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private AttributeValueMap backend;
/*    */   
/*    */   public ImmutableWrapper(AttributeValueMap avMap) {
/* 19 */     this.backend = avMap;
/*    */   }
/*    */   
/*    */   public void set(Attribute attribute, Value value) {
/* 23 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public void exchange(Attribute attribute, Value value) {
/* 27 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public void remove(Attribute attribute) {
/* 31 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection getAttributes() {
/* 36 */     return this.backend.getAttributes();
/*    */   }
/*    */   
/*    */   public Value getValue(Attribute attribute) {
/* 40 */     return this.backend.getValue(attribute);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\avmap\ImmutableWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */