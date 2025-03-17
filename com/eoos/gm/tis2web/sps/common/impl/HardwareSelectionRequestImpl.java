/*    */ package com.eoos.gm.tis2web.sps.common.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.HardwareSelectionRequest;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.RequestGroup;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.request.SelectionRequestImpl;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ import java.util.List;
/*    */ 
/*    */ public class HardwareSelectionRequestImpl
/*    */   extends SelectionRequestImpl
/*    */   implements HardwareSelectionRequest {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected String description;
/*    */   protected String id;
/*    */   protected String label;
/*    */   
/*    */   public HardwareSelectionRequestImpl(RequestGroup requestGroup, Attribute attribute, List options, String hardwareDescription) {
/* 18 */     super(requestGroup, attribute, options);
/* 19 */     this.description = hardwareDescription;
/*    */   }
/*    */   
/*    */   public HardwareSelectionRequestImpl(RequestGroup requestGroup, Attribute attribute, List options, String hardwareDescription, String controllerID) {
/* 23 */     super(requestGroup, attribute, options);
/* 24 */     this.description = hardwareDescription;
/* 25 */     this.id = controllerID;
/* 26 */     this.label = controllerID;
/*    */   }
/*    */   
/*    */   public HardwareSelectionRequestImpl(RequestGroup requestGroup, Attribute attribute, List options, String hardwareDescription, String controllerID, String label) {
/* 30 */     super(requestGroup, attribute, options);
/* 31 */     this.description = hardwareDescription;
/* 32 */     this.id = controllerID;
/* 33 */     this.label = label;
/*    */   }
/*    */   
/*    */   public String getHardwareDescription() {
/* 37 */     return this.description;
/*    */   }
/*    */   
/*    */   public String getControllerID() {
/* 41 */     return this.id;
/*    */   }
/*    */   
/*    */   public String getLabel() {
/* 45 */     return this.label;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\impl\HardwareSelectionRequestImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */