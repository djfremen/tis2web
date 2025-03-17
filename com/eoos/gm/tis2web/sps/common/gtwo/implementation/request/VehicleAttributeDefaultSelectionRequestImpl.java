/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation.request;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.DefaultValueRetrieval;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.SelectionRequest;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ 
/*    */ public class VehicleAttributeDefaultSelectionRequestImpl
/*    */   extends VehicleAttributeSelectionRequestImpl implements DefaultValueRetrieval {
/*    */   private static final long serialVersionUID = 1L;
/* 10 */   private Value defaultValue = null;
/*    */   
/*    */   public VehicleAttributeDefaultSelectionRequestImpl(SelectionRequest request, Value defaultValue) {
/* 13 */     super(request);
/* 14 */     this.defaultValue = defaultValue;
/*    */   }
/*    */   
/*    */   public Value getDefaultValue() {
/* 18 */     return this.defaultValue;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\request\VehicleAttributeDefaultSelectionRequestImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */