/*    */ package com.eoos.gm.tis2web.si.client;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.I18NSupportV2;
/*    */ import java.util.Locale;
/*    */ import java.util.MissingResourceException;
/*    */ import java.util.ResourceBundle;
/*    */ 
/*    */ public class LabelResource
/*    */   implements I18NSupportV2.FixedLocale
/*    */ {
/* 11 */   private ResourceBundle bundle = null;
/*    */   
/*    */   public LabelResource(Locale locale) {
/* 14 */     this.bundle = ResourceBundle.getBundle("com/eoos/gm/tis2web/si/client/lbl_msg", (locale != null) ? locale : Locale.ENGLISH);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getText(String key, I18NSupportV2.Type type) {
/*    */     try {
/* 20 */       String ret = this.bundle.getString(key);
/* 21 */       if (ret == null) {
/* 22 */         ret = key;
/*    */       }
/* 24 */       return ret;
/* 25 */     } catch (MissingResourceException e) {
/* 26 */       return key;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getLabel(String key) {
/* 31 */     return getText(key, I18NSupportV2.Type.LABEL);
/*    */   }
/*    */   
/*    */   public String getMessage(String key) {
/* 35 */     return getText(key, I18NSupportV2.Type.MESSAGE);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\client\LabelResource.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */