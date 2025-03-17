/*    */ package com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.av;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ import java.util.Collection;
/*    */ import java.util.HashSet;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AVMapReadThroughAdapter
/*    */   implements CustomAVMap
/*    */ {
/*    */   private CustomAVMap backend;
/*    */   private CustomAVMap writeMap;
/*    */   
/*    */   public AVMapReadThroughAdapter(CustomAVMap backend) {
/* 20 */     this.backend = backend;
/* 21 */     this.writeMap = new CustomAVMapImpl();
/*    */   }
/*    */   
/*    */   public void set(Attribute attribute, Value value) {
/* 25 */     this.writeMap.set(attribute, value);
/*    */   }
/*    */   
/*    */   public void remove(Attribute attribute) {
/* 29 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public Collection getAttributes() {
/* 33 */     Collection retValue = new HashSet();
/* 34 */     retValue.addAll(this.backend.getAttributes());
/* 35 */     retValue.addAll(this.writeMap.getAttributes());
/* 36 */     return retValue;
/*    */   }
/*    */   
/*    */   public Value getValue(Attribute attribute) {
/* 40 */     Value value = this.writeMap.getValue(attribute);
/* 41 */     if (value == null) {
/* 42 */       value = this.backend.getValue(attribute);
/*    */     }
/* 44 */     return value;
/*    */   }
/*    */   
/*    */   public void explicitSet(Attribute attribute, Value value) {
/* 48 */     this.writeMap.explicitSet(attribute, value);
/*    */   }
/*    */   
/*    */   public List getExplicitEntries() {
/* 52 */     List retValue = this.backend.getExplicitEntries();
/* 53 */     retValue.addAll(this.writeMap.getExplicitEntries());
/* 54 */     return retValue;
/*    */   }
/*    */   
/*    */   public void exchange(Attribute attribute, Value value) {
/* 58 */     this.writeMap.exchange(attribute, value);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementatio\\ui\html\calibrationinfo\home\av\AVMapReadThroughAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */