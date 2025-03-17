/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation.request;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.ConfirmationRequest;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.RequestGroup;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConfirmationRequestImpl
/*    */   extends AssignmentRequestImpl
/*    */   implements ConfirmationRequest, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Value confirmationValue;
/*    */   
/*    */   public ConfirmationRequestImpl(RequestGroup requestGroup, Attribute attribute, Value confirmationValue) {
/* 20 */     super(requestGroup, attribute);
/*    */   }
/*    */   
/*    */   public Value getConfirmationValue() {
/* 24 */     return this.confirmationValue;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\request\ConfirmationRequestImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */