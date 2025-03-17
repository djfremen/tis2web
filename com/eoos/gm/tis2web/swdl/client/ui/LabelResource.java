/*    */ package com.eoos.gm.tis2web.swdl.client.ui;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.util.Locale;
/*    */ import java.util.MissingResourceException;
/*    */ import java.util.ResourceBundle;
/*    */ 
/*    */ public class LabelResource
/*    */ {
/* 10 */   private static LabelResource instance = null;
/*    */   
/*    */   private ResourceBundle bundle;
/*    */   
/*    */   private LabelResource() {
/* 15 */     Locale locale = Util.parseLocale(System.getProperty("language.id"));
/* 16 */     this.bundle = ResourceBundle.getBundle("com.eoos.gm.tis2web.swdl.client.resources.sdstrings", locale);
/*    */   }
/*    */   
/*    */   public static synchronized LabelResource getInstance() {
/* 20 */     if (instance == null) {
/* 21 */       instance = new LabelResource();
/*    */     }
/* 23 */     return instance;
/*    */   }
/*    */ 
/*    */   
/*    */   private String getI15edText(ResourceBundle bundle, String key) {
/*    */     try {
/* 29 */       String ret = bundle.getString(key);
/* 30 */       if (ret == null) {
/* 31 */         ret = key;
/*    */       }
/* 33 */       return ret;
/* 34 */     } catch (MissingResourceException e) {
/* 35 */       return key;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getLabel(String key) {
/* 40 */     return getI15edText(this.bundle, key);
/*    */   }
/*    */   
/*    */   public String getMessage(String key) {
/* 44 */     return getI15edText(this.bundle, key);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\clien\\ui\LabelResource.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */