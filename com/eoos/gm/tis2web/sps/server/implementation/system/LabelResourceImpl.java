/*    */ package com.eoos.gm.tis2web.sps.server.implementation.system;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
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
/* 17 */     return ApplicationContext.getInstance().getLabel(locale, key);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMessage(Locale locale, String key) {
/* 24 */     return ApplicationContext.getInstance().getMessage(locale, key);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\system\LabelResourceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */