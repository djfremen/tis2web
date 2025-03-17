/*    */ package com.eoos.gm.tis2web.vcr.implementation;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*    */ import com.eoos.gm.tis2web.vc.service.cai.VCValue;
/*    */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*    */ 
/*    */ public class ProxyVCR extends VCRImpl {
/*  8 */   int vcr_id = -1;
/*    */   
/*    */   public ProxyVCR(VCRFactory factory) {
/* 11 */     super((ICRImpl)null, factory);
/*    */   }
/*    */   
/*    */   public ProxyVCR(int id, VCRFactory factory) {
/* 15 */     this(factory);
/* 16 */     this.vcr_id = id;
/*    */   }
/*    */   
/*    */   public boolean match(LocaleInfo locale, VCR vcr) {
/* 20 */     checkICR();
/* 21 */     return super.match(locale, vcr);
/*    */   }
/*    */   
/*    */   public boolean match(VCR vcr) {
/* 25 */     checkICR();
/* 26 */     return this.icr.match(vcr);
/*    */   }
/*    */   
/*    */   public boolean match(VCRExpressionImpl expression) {
/* 30 */     checkICR();
/* 31 */     return this.icr.match(expression);
/*    */   }
/*    */   
/*    */   public boolean match(VCRAttributeImpl attribute) {
/* 35 */     checkICR();
/* 36 */     return this.icr.match(attribute);
/*    */   }
/*    */   
/*    */   public boolean match(VCValue value) {
/* 40 */     checkICR();
/* 41 */     return this.icr.match(value);
/*    */   }
/*    */   
/*    */   protected void checkICR() {
/* 45 */     if (this.icr == null) {
/* 46 */       this.icr = this.factory.createICR(this, this.expressions);
/* 47 */       this.expressions = null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vcr\implementation\ProxyVCR.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */