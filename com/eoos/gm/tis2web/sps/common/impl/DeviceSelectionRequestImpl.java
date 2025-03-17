/*    */ package com.eoos.gm.tis2web.sps.common.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.DeviceSelectionRequest;
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
/*    */ 
/*    */ 
/*    */ public class DeviceSelectionRequestImpl
/*    */   extends SelectionRequestImpl
/*    */   implements DeviceSelectionRequest
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Object controller;
/*    */   private String type;
/*    */   
/*    */   public DeviceSelectionRequestImpl(RequestGroup requestGroup, Attribute attribute, List options, Object controller, String type) {
/* 25 */     super(requestGroup, attribute, options);
/* 26 */     this.controller = controller;
/* 27 */     this.type = type;
/*    */   }
/*    */   
/*    */   public Object getController() {
/* 31 */     return this.controller;
/*    */   }
/*    */   
/*    */   public String getType() {
/* 35 */     return this.type;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\impl\DeviceSelectionRequestImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */