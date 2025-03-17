/*    */ package com.eoos.gm.tis2web.ctoc.service.cai;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CTOCLabel
/*    */ {
/*    */   protected Integer labelID;
/*    */   protected Map labels;
/*    */   
/*    */   public Integer getID() {
/* 15 */     return this.labelID;
/*    */   }
/*    */   
/*    */   public String get(LocaleInfo locale) {
/* 19 */     if (this.labels == null) {
/* 20 */       return null;
/*    */     }
/* 22 */     String label = (String)this.labels.get(locale);
/* 23 */     return label;
/*    */   }
/*    */   
/*    */   public CTOCLabel(Integer id) {
/* 27 */     this.labelID = id;
/*    */   }
/*    */   
/*    */   public void add(LocaleInfo locale, String label) {
/* 31 */     if (this.labels == null) {
/* 32 */       this.labels = new HashMap<Object, Object>();
/*    */     }
/* 34 */     this.labels.put(locale, label);
/*    */   }
/*    */   
/*    */   public boolean isSupportedLocale(LocaleInfo locale) {
/* 38 */     if (this.labels == null) {
/* 39 */       return false;
/*    */     }
/* 41 */     return this.labels.containsKey(locale);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\service\cai\CTOCLabel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */