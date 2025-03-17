/*    */ package com.eoos.gm.tis2web.vc.implementation.io.db;
/*    */ 
/*    */ import com.eoos.gm.tis2web.vc.service.cai.VCRDomain;
/*    */ import com.eoos.gm.tis2web.vc.service.cai.VCRLabel;
/*    */ import com.eoos.gm.tis2web.vc.service.cai.VCRValue;
/*    */ import com.eoos.gm.tis2web.vc.service.cai.VCValue;
/*    */ 
/*    */ 
/*    */ public class VCRValueImpl
/*    */   implements VCValue, VCRValue
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected VCRDomain domain;
/*    */   protected Integer value_id;
/*    */   protected VCRLabel label;
/*    */   
/*    */   public Integer getDomainID() {
/* 18 */     return this.domain.getDomainID();
/*    */   }
/*    */   
/*    */   public Integer getValueID() {
/* 22 */     return this.value_id;
/*    */   }
/*    */   
/*    */   public VCRLabel getLabel() {
/* 26 */     return this.label;
/*    */   }
/*    */   
/*    */   public void setLabel(VCRLabel label) {
/* 30 */     this.label = label;
/*    */   }
/*    */   
/*    */   public VCRValueImpl(VCRDomain domain, int value_id, VCRLabel label) {
/* 34 */     this.domain = domain;
/* 35 */     this.value_id = Integer.valueOf(value_id);
/* 36 */     this.label = label;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 40 */     return this.label.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\implementation\io\db\VCRValueImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */