/*    */ package com.eoos.gm.tis2web.sas.client.system;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*    */ import java.util.Locale;
/*    */ import java.util.ResourceBundle;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class LabelResourceImpl
/*    */   implements LabelResource
/*    */ {
/* 11 */   private static final Logger log = Logger.getLogger(LabelResourceImpl.class);
/*    */ 
/*    */   
/*    */   private static final String RESOURCE_NAME_LABELS = "com/eoos/gm/tis2web/sas/client/label";
/*    */ 
/*    */   
/*    */   private static final String RESOURCE_NAME_MESSAGES = "com/eoos/gm/tis2web/sas/client/message";
/*    */ 
/*    */   
/*    */   public String getLabel(Locale locale, String key) {
/* 21 */     String result = null;
/*    */     
/*    */     try {
/* 24 */       ResourceBundle resource = ResourceBundle.getBundle("com/eoos/gm/tis2web/sas/client/label", locale);
/* 25 */       result = resource.getString(key);
/*    */     }
/* 27 */     catch (Exception e) {
/* 28 */       log.warn("unable to retrieve label for key:" + String.valueOf(key) + " -  exception: " + e);
/*    */     } 
/* 30 */     return result;
/*    */   }
/*    */   
/*    */   public String getMessage(Locale locale, String key) {
/* 34 */     String result = null;
/*    */     
/*    */     try {
/* 37 */       ResourceBundle resource = ResourceBundle.getBundle("com/eoos/gm/tis2web/sas/client/message", locale);
/* 38 */       result = resource.getString(key);
/*    */     }
/* 40 */     catch (Exception e) {
/* 41 */       log.warn("unable to retrieve message for key:" + String.valueOf(key) + " -  exception: " + e);
/*    */     } 
/* 43 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\client\system\LabelResourceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */