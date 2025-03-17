/*    */ package com.eoos.gm.tis2web.kdr.client;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import java.util.MissingResourceException;
/*    */ import java.util.ResourceBundle;
/*    */ 
/*    */ public class LabelResource {
/*  8 */   private ResourceBundle bundle = null;
/*    */   
/*    */   public LabelResource(Locale locale) {
/* 11 */     this.bundle = ResourceBundle.getBundle("com/eoos/gm/tis2web/kdr/client/lbl_msg", locale);
/*    */   }
/*    */ 
/*    */   
/*    */   private String getI15edText(String key) {
/*    */     try {
/* 17 */       String ret = this.bundle.getString(key);
/* 18 */       if (ret == null) {
/* 19 */         ret = key;
/*    */       }
/* 21 */       return ret;
/* 22 */     } catch (MissingResourceException e) {
/* 23 */       return key;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getLabel(String key) {
/* 28 */     return getI15edText(key);
/*    */   }
/*    */   
/*    */   public String getMessage(String key) {
/* 32 */     return getI15edText(key);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\kdr\client\LabelResource.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */