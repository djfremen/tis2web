/*    */ package com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.filter;
/*    */ 
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIOTSB;
/*    */ import com.eoos.gm.tis2web.vc.service.cai.VCValue;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TSBFilter_Model
/*    */   extends TSBFilter
/*    */ {
/*    */   protected VCValue value;
/*    */   
/*    */   public TSBFilter_Model(VCValue modelValue) {
/* 21 */     this.value = modelValue;
/*    */   }
/*    */   
/*    */   protected boolean include(SIOTSB tsb) {
/*    */     try {
/* 26 */       return tsb.matchModel(this.value);
/* 27 */     } catch (Exception e) {
/* 28 */       return false;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\datamodel\domain\tsb\filter\TSBFilter_Model.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */