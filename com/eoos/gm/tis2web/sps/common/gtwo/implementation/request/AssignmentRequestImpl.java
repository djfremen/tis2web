/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation.request;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.AssignmentRequest;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.RequestGroup;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AssignmentRequestImpl
/*    */   implements AssignmentRequest, Serializable
/*    */ {
/*    */   protected static final long serialVersionUID = 1L;
/*    */   private RequestGroup requestGroup;
/*    */   private Attribute attribute;
/*    */   private boolean autoSubmit = false;
/*    */   
/*    */   public AssignmentRequestImpl(RequestGroup requestGroup, Attribute attribute) {
/* 20 */     this.requestGroup = requestGroup;
/* 21 */     this.attribute = attribute;
/*    */   }
/*    */ 
/*    */   
/*    */   public RequestGroup getRequestGroup() {
/* 26 */     return this.requestGroup;
/*    */   }
/*    */   
/*    */   public Attribute getAttribute() {
/* 30 */     return this.attribute;
/*    */   }
/*    */   
/*    */   public void setRequestGroup(RequestGroup requestGroup) {
/* 34 */     this.requestGroup = requestGroup;
/*    */   }
/*    */   
/*    */   public boolean autoSubmit() {
/* 38 */     return this.autoSubmit;
/*    */   }
/*    */   
/*    */   public void setAutoSubmit(boolean autoSubmit) {
/* 42 */     this.autoSubmit = autoSubmit;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\request\AssignmentRequestImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */