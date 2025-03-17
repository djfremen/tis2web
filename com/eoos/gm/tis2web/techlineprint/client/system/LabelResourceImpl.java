/*    */ package com.eoos.gm.tis2web.techlineprint.client.system;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*    */ import com.eoos.gm.tis2web.techlineprint.client.common.ClientAppContextProvider;
/*    */ import java.util.Locale;
/*    */ import java.util.ResourceBundle;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class LabelResourceImpl
/*    */   implements LabelResource
/*    */ {
/* 12 */   private static final Logger log = Logger.getLogger(LabelResourceImpl.class);
/*    */   
/*    */   public static final String PATH = "com/eoos/gm/tis2web/techlineprint/client/common";
/*    */   
/*    */   private static final String MESSAGES = "message";
/*    */   
/* 18 */   private ResourceBundle messageResourceBundle = null;
/*    */   
/* 20 */   private Locale currentLocale = null;
/*    */   
/*    */   public LabelResourceImpl() {
/* 23 */     this.currentLocale = ClientAppContextProvider.getClientAppContext().getLocale();
/* 24 */     this.messageResourceBundle = ResourceBundle.getBundle("com/eoos/gm/tis2web/techlineprint/client/common/message", this.currentLocale);
/*    */   }
/*    */   
/*    */   public LabelResourceImpl(Locale locale) {
/* 28 */     if (locale == null) {
/* 29 */       locale = Locale.US;
/*    */     }
/* 31 */     this.currentLocale = locale;
/* 32 */     this.messageResourceBundle = ResourceBundle.getBundle("com/eoos/gm/tis2web/techlineprint/client/common/message", locale);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getLabel(Locale locale, String key) {
/* 37 */     String result = null;
/*    */     
/* 39 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getMessage(Locale locale, String key) {
/* 44 */     String result = null;
/*    */     try {
/* 46 */       if (locale != null && locale.toString().compareTo(this.currentLocale.toString()) != 0)
/*    */       {
/* 48 */         setLocale(locale);
/*    */       }
/* 50 */       result = this.messageResourceBundle.getString(key);
/* 51 */     } catch (Exception e) {
/* 52 */       log.warn("unable to find message for key (locale:" + String.valueOf(this.currentLocale) + "):" + key + " - exception:" + e);
/*    */     } 
/*    */     
/* 55 */     return result;
/*    */   }
/*    */   
/*    */   private synchronized void setLocale(Locale locale) {
/* 59 */     if (this.currentLocale.toString().compareTo(locale.toString()) != 0) {
/* 60 */       this.messageResourceBundle = ResourceBundle.getBundle("com/eoos/gm/tis2web/techlineprint/client/common/message", locale);
/* 61 */       this.currentLocale = locale;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\techlineprint\client\system\LabelResourceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */