/*    */ package com.eoos.gm.tis2web.ctoc.service.cai;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ 
/*    */ public class FileReference
/*    */ {
/*    */   protected Integer labelID;
/*    */   protected HashMap references;
/*    */   
/*    */   public String get(LocaleInfo locale) {
/* 14 */     return (String)this.references.get(locale);
/*    */   }
/*    */   
/*    */   public Collection getFiles() {
/* 18 */     return this.references.values();
/*    */   }
/*    */   
/*    */   public FileReference() {
/* 22 */     this.references = new HashMap<Object, Object>();
/*    */   }
/*    */   
/*    */   public void add(LocaleInfo locale, String reference) {
/* 26 */     this.references.put(locale, reference);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\service\cai\FileReference.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */