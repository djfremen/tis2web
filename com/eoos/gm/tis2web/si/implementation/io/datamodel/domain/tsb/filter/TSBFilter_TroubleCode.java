/*    */ package com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.filter;
/*    */ 
/*    */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.TSBAdapter;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.TroubleCode;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIOTSB;
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
/*    */ public class TSBFilter_TroubleCode
/*    */   extends TSBFilter
/*    */ {
/*    */   protected TroubleCode troubleCode;
/*    */   
/*    */   public TSBFilter_TroubleCode(TroubleCode troubleCode) {
/* 22 */     this.troubleCode = troubleCode;
/*    */   }
/*    */   
/*    */   protected boolean include(SIOTSB tsb) {
/*    */     try {
/* 27 */       return TSBAdapter.matchTroubleCode(tsb, this.troubleCode);
/* 28 */     } catch (Exception e) {
/* 29 */       return false;
/*    */     } 
/*    */   }
/*    */   
/*    */   public TroubleCode getTroubleCode() {
/* 34 */     return this.troubleCode;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\datamodel\domain\tsb\filter\TSBFilter_TroubleCode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */