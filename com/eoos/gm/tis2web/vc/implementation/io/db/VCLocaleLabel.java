/*    */ package com.eoos.gm.tis2web.vc.implementation.io.db;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*    */ import com.eoos.gm.tis2web.vc.service.cai.VCRLabel;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class VCLocaleLabel
/*    */   implements VCRLabel
/*    */ {
/*    */   protected Integer id;
/*    */   protected HashMap labels;
/*    */   
/*    */   public Integer getID() {
/* 14 */     return this.id;
/*    */   }
/*    */   
/*    */   public String getLabel(Integer localeID) {
/* 18 */     return (this.labels == null) ? null : (String)this.labels.get(localeID);
/*    */   }
/*    */   
/*    */   public VCLocaleLabel(Integer id) {
/* 22 */     this.id = id;
/*    */   }
/*    */   
/*    */   public void add(Integer localeID, String label) {
/* 26 */     if (this.labels == null) {
/* 27 */       this.labels = new HashMap<Object, Object>();
/*    */     }
/* 29 */     this.labels.put(localeID, label);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 33 */     return getLabel(LocaleInfoProvider.getInstance().getLocale("en_GB").getLocaleID());
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\implementation\io\db\VCLocaleLabel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */