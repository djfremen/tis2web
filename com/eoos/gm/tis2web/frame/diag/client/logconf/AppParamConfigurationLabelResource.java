/*    */ package com.eoos.gm.tis2web.frame.diag.client.logconf;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.I18NSupport;
/*    */ import java.util.Locale;
/*    */ import java.util.MissingResourceException;
/*    */ import java.util.ResourceBundle;
/*    */ 
/*    */ public class AppParamConfigurationLabelResource implements I18NSupport.FixedLocale {
/*  9 */   private static ResourceBundle _bundle = null;
/*    */   
/*    */   public AppParamConfigurationLabelResource(Locale locale) {
/* 12 */     _bundle = ResourceBundle.getBundle("com/eoos/gm/tis2web/frame/diag/client/logconf/app_conf_bundle", locale);
/*    */   }
/*    */   
/*    */   public String getLabel(String key) {
/* 16 */     return getText(key);
/*    */   }
/*    */   
/*    */   public String getMessage(String key) {
/* 20 */     return getText(key);
/*    */   }
/*    */   
/*    */   public String getText(String key) {
/*    */     try {
/* 25 */       String ret = _bundle.getString(key);
/* 26 */       if (ret == null) {
/* 27 */         ret = key;
/*    */       }
/* 29 */       return ret;
/* 30 */     } catch (MissingResourceException e) {
/* 31 */       return key;
/*    */     } 
/*    */   }
/*    */   
/*    */   public ResourceBundle getResourceBundle() {
/* 36 */     return _bundle;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\diag\client\logconf\AppParamConfigurationLabelResource.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */