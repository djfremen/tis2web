/*    */ package com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.filter;
/*    */ 
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
/*    */ public class TSBFilter_RemedyNumber
/*    */   extends TSBFilter
/*    */ {
/*    */   protected String remedyNumber;
/*    */   
/*    */   public TSBFilter_RemedyNumber(String remedyNumber) {
/* 20 */     this.remedyNumber = remedyNumber;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean include(SIOTSB tsb) {
/*    */     try {
/* 26 */       return this.remedyNumber.equalsIgnoreCase(tsb.getRemedyNumber());
/* 27 */     } catch (Exception e) {
/* 28 */       return false;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\datamodel\domain\tsb\filter\TSBFilter_RemedyNumber.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */