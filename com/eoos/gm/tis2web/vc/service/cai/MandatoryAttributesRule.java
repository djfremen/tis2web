/*    */ package com.eoos.gm.tis2web.vc.service.cai;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public interface MandatoryAttributesRule
/*    */ {
/*    */   boolean isMandatory(String paramString, VehicleContextData paramVehicleContextData);
/*    */   
/*    */   public static class FixedImpl
/*    */     implements MandatoryAttributesRule
/*    */   {
/* 14 */     protected List mandatoryAttributes = null;
/*    */     
/*    */     public FixedImpl(List mandatoryAttributes) {
/* 17 */       this.mandatoryAttributes = (mandatoryAttributes != null) ? mandatoryAttributes : new ArrayList();
/*    */     }
/*    */     
/*    */     public boolean isMandatory(String domainID, VehicleContextData vcd) {
/* 21 */       return this.mandatoryAttributes.contains(domainID);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\service\cai\MandatoryAttributesRule.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */