/*    */ package com.eoos.gm.tis2web.frame.export.common.i18n;
/*    */ 
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LabelResourceWrapper
/*    */   implements LabelResource
/*    */ {
/*    */   private LabelResource backend;
/*    */   
/*    */   public LabelResourceWrapper(LabelResource backend) {
/* 13 */     this.backend = backend;
/* 14 */     if (this.backend == null) {
/* 15 */       this.backend = new LabelResource() {
/*    */           public String getLabel(Locale locale, String key) {
/* 17 */             return null;
/*    */           }
/*    */           
/*    */           public String getMessage(Locale locale, String key) {
/* 21 */             return null;
/*    */           }
/*    */         };
/*    */     }
/*    */   }
/*    */   
/*    */   public String getLabel(Locale locale, String key) {
/* 28 */     String retValue = this.backend.getLabel(locale, key);
/* 29 */     if (retValue == null) {
/* 30 */       retValue = key;
/*    */     }
/* 32 */     return retValue;
/*    */   }
/*    */   
/*    */   public String getMessage(Locale locale, String key) {
/* 36 */     String retValue = this.backend.getMessage(locale, key);
/* 37 */     if (retValue == null) {
/* 38 */       retValue = key;
/*    */     }
/* 40 */     return retValue;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\i18n\LabelResourceWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */