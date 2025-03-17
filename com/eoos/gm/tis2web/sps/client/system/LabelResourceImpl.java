/*    */ package com.eoos.gm.tis2web.sps.client.system;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*    */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*    */ import java.util.Locale;
/*    */ import java.util.ResourceBundle;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class LabelResourceImpl
/*    */   implements LabelResource
/*    */ {
/* 12 */   private static final Logger log = Logger.getLogger(LabelResourceImpl.class);
/*    */   
/*    */   public static final String PATH = "com/eoos/gm/tis2web/sps/client/common";
/*    */   
/*    */   public static final String LABELS = "label";
/*    */   
/*    */   private static final String MESSAGES = "message";
/*    */   
/*    */   private static final String LCID = "win_lcid_mapping";
/* 21 */   private ResourceBundle labelResourceBundle = null;
/*    */   
/* 23 */   private ResourceBundle messageResourceBundle = null;
/*    */   
/* 25 */   private ResourceBundle lcidResourceBundle = null;
/*    */   
/* 27 */   private Locale currentLocale = null;
/*    */   
/*    */   public LabelResourceImpl() {
/* 30 */     this.currentLocale = ClientAppContextProvider.getClientAppContext().getLocale();
/* 31 */     this.labelResourceBundle = ResourceBundle.getBundle("com/eoos/gm/tis2web/sps/client/common/label", this.currentLocale);
/* 32 */     this.messageResourceBundle = ResourceBundle.getBundle("com/eoos/gm/tis2web/sps/client/common/message", this.currentLocale);
/* 33 */     this.lcidResourceBundle = ResourceBundle.getBundle("com/eoos/gm/tis2web/sps/client/common/win_lcid_mapping", this.currentLocale);
/*    */   }
/*    */   
/*    */   public LabelResourceImpl(Locale locale) {
/* 37 */     if (locale == null) {
/* 38 */       locale = Locale.US;
/*    */     }
/* 40 */     this.currentLocale = locale;
/* 41 */     this.labelResourceBundle = ResourceBundle.getBundle("com/eoos/gm/tis2web/sps/client/common/label", locale);
/* 42 */     this.messageResourceBundle = ResourceBundle.getBundle("com/eoos/gm/tis2web/sps/client/common/message", locale);
/* 43 */     this.lcidResourceBundle = ResourceBundle.getBundle("com/eoos/gm/tis2web/sps/client/common/win_lcid_mapping", locale);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getLabel(Locale locale, String key) {
/* 48 */     String result = null;
/*    */     
/*    */     try {
/* 51 */       if (locale != null && locale.toString().compareTo(this.currentLocale.toString()) != 0) {
/* 52 */         setLocale(locale);
/*    */       }
/* 54 */       result = this.labelResourceBundle.getString(key);
/* 55 */     } catch (Exception e) {
/* 56 */       log.warn("unable to find label for key (locale:" + String.valueOf(this.currentLocale) + "):" + key + " - exception:" + e);
/*    */     } 
/*    */     
/* 59 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getMessage(Locale locale, String key) {
/* 64 */     String result = null;
/*    */     try {
/* 66 */       if (locale != null && locale.toString().compareTo(this.currentLocale.toString()) != 0)
/*    */       {
/* 68 */         setLocale(locale);
/*    */       }
/* 70 */       result = this.messageResourceBundle.getString(key);
/* 71 */     } catch (Exception e) {
/* 72 */       log.warn("unable to find message for key (locale:" + String.valueOf(this.currentLocale) + "):" + key + " - exception:" + e);
/*    */     } 
/*    */     
/* 75 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getLCID(Locale locale, String key) {
/* 80 */     String result = null;
/*    */     try {
/* 82 */       if (locale != null && locale.toString().compareTo(this.currentLocale.toString()) != 0)
/*    */       {
/* 84 */         setLocale(locale);
/*    */       }
/* 86 */       result = this.lcidResourceBundle.getString(key);
/* 87 */     } catch (Exception e) {
/* 88 */       log.warn("unable to find lcid for key (locale:" + String.valueOf(this.currentLocale) + "):" + key + " - exception:" + e);
/*    */     } 
/*    */     
/* 91 */     return result;
/*    */   }
/*    */   
/*    */   private synchronized void setLocale(Locale locale) {
/* 95 */     if (this.currentLocale.toString().compareTo(locale.toString()) != 0) {
/* 96 */       this.labelResourceBundle = ResourceBundle.getBundle("com/eoos/gm/tis2web/sps/client/common/label", locale);
/* 97 */       this.messageResourceBundle = ResourceBundle.getBundle("com/eoos/gm/tis2web/sps/client/common/message", locale);
/* 98 */       this.lcidResourceBundle = ResourceBundle.getBundle("com/eoos/gm/tis2web/sps/client/common/win_lcid_mapping", this.currentLocale);
/* 99 */       this.currentLocale = locale;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\system\LabelResourceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */