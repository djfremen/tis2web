/*    */ package com.eoos.gm.tis2web.frame.export.common.i18n;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LabelResourceImpl
/*    */   implements LabelResource
/*    */ {
/*    */   public String getLabel(Locale locale, String key) {
/* 16 */     return ApplicationContext.getInstance().getLabel(locale, key);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMessage(Locale locale, String key) {
/* 23 */     return ApplicationContext.getInstance().getMessage(locale, key);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\i18n\LabelResourceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */