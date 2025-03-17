/*    */ package com.eoos.gm.tis2web.lt.icop.cfgclient;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.I18NSupport;
/*    */ import java.util.Locale;
/*    */ import java.util.MissingResourceException;
/*    */ import java.util.ResourceBundle;
/*    */ 
/*    */ public class LabelResource
/*    */   implements I18NSupport.FixedLocale {
/* 10 */   private ResourceBundle bundle = null;
/*    */   
/*    */   public LabelResource(Locale locale) {
/* 13 */     this.bundle = ResourceBundle.getBundle("com/eoos/gm/tis2web/lt/icop/cfgclient/lbl_msg", (locale != null) ? locale : Locale.ENGLISH);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getText(String key) {
/*    */     try {
/* 19 */       String ret = this.bundle.getString(key);
/* 20 */       if (ret == null) {
/* 21 */         ret = key;
/*    */       }
/* 23 */       return ret;
/* 24 */     } catch (MissingResourceException e) {
/* 25 */       return key;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getLabel(String key) {
/* 30 */     return getText(key);
/*    */   }
/*    */   
/*    */   public String getMessage(String key) {
/* 34 */     return getText(key);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\icop\cfgclient\LabelResource.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */