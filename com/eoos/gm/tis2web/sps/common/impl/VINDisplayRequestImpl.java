/*    */ package com.eoos.gm.tis2web.sps.common.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.VINDisplayRequest;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.RequestGroup;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.request.InputRequestImpl;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VINDisplayRequestImpl
/*    */   extends InputRequestImpl
/*    */   implements VINDisplayRequest
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Value defaultValue;
/*    */   
/*    */   public VINDisplayRequestImpl(RequestGroup requestGroup, Attribute attribute, Value defaultValue) {
/* 24 */     super(requestGroup, attribute);
/* 25 */     this.defaultValue = defaultValue;
/*    */   }
/*    */   
/*    */   public Value getDefaultValue() {
/* 29 */     return this.defaultValue;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\impl\VINDisplayRequestImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */