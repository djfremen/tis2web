/*    */ package com.eoos.gm.tis2web.sps.common.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.ControllerSecurityCodeRequest;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.RequestGroup;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.request.AssignmentRequestImpl;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ 
/*    */ public class ControllerSecurityCodeRequestImpl
/*    */   extends AssignmentRequestImpl
/*    */   implements ControllerSecurityCodeRequest {
/*    */   private String vin;
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public ControllerSecurityCodeRequestImpl(RequestGroup requestGroup, Attribute attribute, String vin) {
/* 16 */     super(requestGroup, attribute);
/* 17 */     this.vin = vin;
/*    */   }
/*    */   
/*    */   public Value getDefaultValue() {
/* 21 */     return null;
/*    */   }
/*    */   
/*    */   public String getControllerVIN() {
/* 25 */     return this.vin;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\impl\ControllerSecurityCodeRequestImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */