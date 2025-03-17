/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation.request;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.SelectionRequest;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.VehicleAttributeSelectionRequest;
/*    */ 
/*    */ public class VehicleAttributeSelectionRequestImpl
/*    */   extends SelectionRequestImpl implements VehicleAttributeSelectionRequest {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public VehicleAttributeSelectionRequestImpl(SelectionRequest request) {
/* 11 */     super(request.getRequestGroup(), request.getAttribute(), request.getOptions());
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\request\VehicleAttributeSelectionRequestImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */