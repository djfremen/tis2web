/*    */ package com.eoos.gm.tis2web.vc.implementation.io.db;
/*    */ 
/*    */ import com.eoos.gm.tis2web.vc.service.cai.VCRLabel;
/*    */ 
/*    */ public class VCLabel
/*    */   implements VCRLabel {
/*    */   protected Integer id;
/*    */   protected String label;
/*    */   
/*    */   public Integer getID() {
/* 11 */     return this.id;
/*    */   }
/*    */   
/*    */   public String getLabel(Integer localeID) {
/* 15 */     return this.label;
/*    */   }
/*    */   
/*    */   public VCLabel(Integer id, String label) {
/* 19 */     this.id = id;
/* 20 */     this.label = label;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 24 */     return this.label;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\implementation\io\db\VCLabel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */