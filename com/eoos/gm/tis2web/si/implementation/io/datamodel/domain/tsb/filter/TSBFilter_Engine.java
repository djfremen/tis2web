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
/*    */ public class TSBFilter_Engine
/*    */   extends TSBFilter
/*    */ {
/*    */   protected VCValue value;
/*    */   
/*    */   public TSBFilter_Engine(VCValue engineValue) {
/* 20 */     this.value = engineValue;
/*    */   }
/*    */   
/*    */   protected boolean include(SIOTSB tsb) {
/*    */     try {
/* 25 */       return tsb.matchEngine(this.value);
/* 26 */     } catch (Exception e) {
/* 27 */       return false;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\datamodel\domain\tsb\filter\TSBFilter_Engine.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */