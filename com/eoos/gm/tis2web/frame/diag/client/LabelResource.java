/*    */ package com.eoos.gm.tis2web.frame.diag.client;
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
/* 13 */     this.bundle = ResourceBundle.getBundle("com/eoos/gm/tis2web/frame/diag/client/lbl_msg", locale);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getLabel(String key) {
/* 18 */     return getText(key);
/*    */   }
/*    */   
/*    */   public String getMessage(String key) {
/* 22 */     return getText(key);
/*    */   }
/*    */   
/*    */   public String getText(String key) {
/*    */     try {
/* 27 */       String ret = this.bundle.getString(key);
/* 28 */       if (ret == null) {
/* 29 */         ret = key;
/*    */       }
/* 31 */       return ret;
/* 32 */     } catch (MissingResourceException e) {
/* 33 */       return key;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\diag\client\LabelResource.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */