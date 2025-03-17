/*    */ package com.eoos.gm.tis2web.vc.implementation.io.db;
/*    */ 
/*    */ import com.eoos.gm.tis2web.vc.service.cai.VCDomain;
/*    */ import com.eoos.gm.tis2web.vc.service.cai.VCRConstraint;
/*    */ import com.eoos.gm.tis2web.vc.service.cai.VCRValue;
/*    */ import com.eoos.gm.tis2web.vc.service.cai.VCValue;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class VCRConstraintImpl
/*    */   implements VCRConstraint
/*    */ {
/*    */   protected Integer constraint_id;
/* 14 */   protected ArrayList elements = new ArrayList();
/*    */   
/*    */   public void setConstraintID(int value) {
/* 17 */     this.constraint_id = Integer.valueOf(value);
/*    */   }
/*    */   
/*    */   public Integer getConstraintID() {
/* 21 */     return this.constraint_id;
/*    */   }
/*    */   
/*    */   public void addElement(VCValue value) {
/* 25 */     this.elements.add(value);
/*    */   }
/*    */   
/*    */   public List getElements() {
/* 29 */     return this.elements;
/*    */   }
/*    */ 
/*    */   
/*    */   public VCRConstraintImpl() {}
/*    */   
/*    */   public VCRConstraintImpl(int constraint_id) {
/* 36 */     setConstraintID(constraint_id);
/*    */   }
/*    */   
/*    */   public boolean match(VCRConstraint constraint) {
/* 40 */     List<VCRValue> elements = constraint.getElements();
/* 41 */     for (int k = 0; k < elements.size(); k++) {
/* 42 */       VCRValue value = elements.get(k);
/* 43 */       if (!match(value)) {
/* 44 */         return false;
/*    */       }
/*    */     } 
/* 47 */     return (elements.size() == constraint.getElements().size());
/*    */   }
/*    */   
/*    */   protected boolean match(VCRValue value) {
/* 51 */     for (int k = 0; k < this.elements.size(); k++) {
/* 52 */       VCRValue v = this.elements.get(k);
/* 53 */       if (v == value) {
/* 54 */         return true;
/*    */       }
/*    */     } 
/* 57 */     return false;
/*    */   }
/*    */   
/*    */   public VCValue getElement(VCDomain domain) {
/* 61 */     for (int k = 0; k < this.elements.size(); k++) {
/* 62 */       VCRValueImpl value = this.elements.get(k);
/* 63 */       if (value.getDomainID().equals(domain.getDomainID())) {
/* 64 */         return value;
/*    */       }
/*    */     } 
/* 67 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\implementation\io\db\VCRConstraintImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */