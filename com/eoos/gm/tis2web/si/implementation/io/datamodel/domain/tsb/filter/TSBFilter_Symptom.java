/*    */ package com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.filter;
/*    */ 
/*    */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.Symptom;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.TSBAdapter;
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
/*    */ public class TSBFilter_Symptom
/*    */   extends TSBFilter
/*    */ {
/*    */   protected Symptom symptom;
/*    */   
/*    */   public TSBFilter_Symptom(Symptom symptom) {
/* 22 */     this.symptom = symptom;
/*    */   }
/*    */   
/*    */   protected boolean include(SIOTSB tsb) {
/*    */     try {
/* 27 */       return TSBAdapter.matchSymptom(tsb, this.symptom);
/* 28 */     } catch (Exception e) {
/* 29 */       return false;
/*    */     } 
/*    */   }
/*    */   
/*    */   public Symptom getSymptom() {
/* 34 */     return this.symptom;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\datamodel\domain\tsb\filter\TSBFilter_Symptom.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */