/*    */ package com.eoos.gm.tis2web.sps.common.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.ControllerSelectionRequest;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.RequestGroup;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.request.SelectionRequestImpl;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ControllerSelectionRequestImpl
/*    */   extends SelectionRequestImpl
/*    */   implements ControllerSelectionRequest
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public ControllerSelectionRequestImpl(RequestGroup requestGroup, Attribute attribute, List options) {
/* 21 */     super(requestGroup, attribute, options);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\impl\ControllerSelectionRequestImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */