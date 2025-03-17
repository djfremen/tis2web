/*    */ package com.eoos.gm.tis2web.sps.common.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.VINValidationRequest;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.DefaultValueRetrieval;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.RequestGroup;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.request.InputRequestImpl;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VINValidationRequestImpl
/*    */   extends InputRequestImpl
/*    */   implements VINValidationRequest, DefaultValueRetrieval
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Value vin;
/*    */   
/*    */   public VINValidationRequestImpl(RequestGroup requestGroup, Attribute attribute, Value vin) {
/* 22 */     super(requestGroup, attribute);
/* 23 */     this.vin = vin;
/*    */   }
/*    */ 
/*    */   
/*    */   public Value getDefaultValue() {
/* 28 */     return this.vin;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\impl\VINValidationRequestImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */