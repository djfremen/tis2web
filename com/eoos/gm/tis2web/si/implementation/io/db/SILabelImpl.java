/*    */ package com.eoos.gm.tis2web.si.implementation.io.db;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SILabel;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ public class SILabelImpl
/*    */   implements SILabel
/*    */ {
/* 11 */   protected HashMap labels = new HashMap<Object, Object>();
/*    */   
/*    */   public String get(LocaleInfo locale) {
/* 14 */     return (String)this.labels.get(locale);
/*    */   }
/*    */   
/*    */   public void add(LocaleInfo locale, String label) {
/* 18 */     this.labels.put(locale, label);
/*    */   }
/*    */   
/*    */   public boolean isSupportedLocale(LocaleInfo locale) {
/* 22 */     if (this.labels == null) {
/* 23 */       return false;
/*    */     }
/* 25 */     return this.labels.containsKey(locale);
/*    */   }
/*    */   
/*    */   public boolean match(SILabel other) {
/* 29 */     if (other == null || this.labels.size() != ((SILabelImpl)other).labels.size()) {
/* 30 */       return false;
/*    */     }
/* 32 */     for (Iterator<LocaleInfo> iter = this.labels.keySet().iterator(); iter.hasNext(); ) {
/* 33 */       LocaleInfo locale = iter.next();
/* 34 */       if (!get(locale).equals(other.get(locale))) {
/* 35 */         return false;
/*    */       }
/*    */     } 
/* 38 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\db\SILabelImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */