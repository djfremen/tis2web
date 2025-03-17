/*    */ package com.eoos.gm.tis2web.sps.common.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.VINReadRequest;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.RequestGroup;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.request.ConfirmationRequestImpl;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ 
/*    */ public class VINReadRequestImpl
/*    */   extends ConfirmationRequestImpl implements VINReadRequest {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public VINReadRequestImpl(RequestGroup requestGroup, Attribute attribute, Value confirmationValue) {
/* 14 */     super(requestGroup, attribute, confirmationValue);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\impl\VINReadRequestImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */