/*    */ package com.eoos.gm.tis2web.sps.common.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.FunctionSelectionRequest;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.RequestGroup;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.request.SelectionRequestImpl;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ import java.util.List;
/*    */ 
/*    */ public class FunctionSelectionRequestImpl
/*    */   extends SelectionRequestImpl
/*    */   implements FunctionSelectionRequest {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public FunctionSelectionRequestImpl(RequestGroup requestGroup, Attribute attribute, List options) {
/* 15 */     super(requestGroup, attribute, options);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\impl\FunctionSelectionRequestImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */