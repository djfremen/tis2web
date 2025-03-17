/*    */ package com.eoos.gm.tis2web.vcr.implementation;
/*    */ 
/*    */ import com.eoos.gm.tis2web.vcr.service.cai.IVehicleOptionExpression;
/*    */ import com.eoos.gm.tis2web.vcr.service.cai.VCRAttribute;
/*    */ import com.eoos.gm.tis2web.vcr.service.cai.VCRTerm;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VehicleOptionExpression
/*    */   extends VCRExpressionImpl
/*    */   implements IVehicleOptionExpression
/*    */ {
/*    */   public void add(VCRTerm term) {
/* 25 */     addToTerms(term);
/*    */   }
/*    */ 
/*    */   
/*    */   private void addToTerms(VCRTerm term) {
/* 30 */     VCRTermImpl t = null;
/* 31 */     for (int i = 0; i < this.terms.size(); i++) {
/* 32 */       t = this.terms.get(i);
/* 33 */       if (term.getDomainID() == t.getDomainID()) {
/* 34 */         t.merge(term);
/*    */         return;
/*    */       } 
/* 37 */       if (t.gt(term)) {
/* 38 */         this.terms.add(i, term);
/*    */         return;
/*    */       } 
/*    */     } 
/* 42 */     this.terms.add(term);
/*    */   }
/*    */   
/*    */   public void add(VCRAttribute attribute) {
/* 46 */     addToTerms(new VCRTermImpl(attribute));
/*    */   }
/*    */   
/*    */   public boolean isInvalid() {
/* 50 */     return super.isInvalid();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vcr\implementation\VehicleOptionExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */