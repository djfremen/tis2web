/*    */ package com.eoos.gm.tis2web.vc.implementation.io.vin;
/*    */ 
/*    */ import com.eoos.gm.tis2web.vc.service.cai.VCRvin;
/*    */ import com.eoos.gm.tis2web.vc.service.cai.VCValue;
/*    */ 
/*    */ 
/*    */ public class VCRvinImpl
/*    */   implements VCRvin
/*    */ {
/*    */   protected Integer structure_id;
/*    */   protected String vin_element;
/*    */   protected VCValue value;
/*    */   
/*    */   public Integer getStructureID() {
/* 15 */     return this.structure_id;
/*    */   }
/*    */   
/*    */   public String getVINElement() {
/* 19 */     return this.vin_element;
/*    */   }
/*    */   
/*    */   public VCValue getValue() {
/* 23 */     return this.value;
/*    */   }
/*    */   
/*    */   public String getKey() {
/* 27 */     return this.structure_id + "-" + this.vin_element + "-" + this.value.getDomainID() + ":" + this.value.getValueID();
/*    */   }
/*    */   
/*    */   public VCRvinImpl(Integer structure_id, String vin_element, VCValue value) {
/* 31 */     this.structure_id = structure_id;
/* 32 */     this.vin_element = vin_element;
/* 33 */     this.value = value;
/*    */   }
/*    */   
/*    */   public boolean equals(VCRvin instance) {
/* 37 */     return (this.structure_id.equals(instance.getStructureID()) && this.vin_element.equals(instance.getVINElement()) && this.value == instance.getValue());
/*    */   }
/*    */   
/*    */   public boolean match(String vin, int pos) {
/* 41 */     pos--;
/* 42 */     for (int i = 0; i < this.vin_element.length(); i++, pos++) {
/* 43 */       char target = this.vin_element.charAt(i);
/* 44 */       if (target != '*')
/*    */       {
/* 46 */         if (target != vin.charAt(pos))
/* 47 */           return false; 
/*    */       }
/*    */     } 
/* 50 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\implementation\io\vin\VCRvinImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */