/*    */ package com.eoos.gm.tis2web.vc.implementation.io.db;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public class VehicleOptionGroup
/*    */ {
/*    */   protected VCRValueImpl group;
/*    */   protected List options;
/*    */   
/*    */   public VCRValueImpl getGroup() {
/* 11 */     return this.group;
/*    */   }
/*    */   
/*    */   public List getOptions() {
/* 15 */     return this.options;
/*    */   }
/*    */   
/*    */   public VehicleOptionGroup(VCRValueImpl group, List options) {
/* 19 */     this.group = group;
/* 20 */     this.options = options;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\implementation\io\db\VehicleOptionGroup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */