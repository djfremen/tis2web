/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation.request;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.RequestGroup;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.SelectionRequest;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SelectionRequestImpl
/*    */   extends AssignmentRequestImpl
/*    */   implements SelectionRequest
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private List options;
/*    */   
/*    */   public SelectionRequestImpl(RequestGroup requestGroup, Attribute attribute, List options) {
/* 18 */     super(requestGroup, attribute);
/* 19 */     this.options = options;
/*    */   }
/*    */   
/*    */   public List getOptions() {
/* 23 */     return this.options;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\request\SelectionRequestImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */