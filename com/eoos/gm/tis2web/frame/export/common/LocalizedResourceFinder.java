/*    */ package com.eoos.gm.tis2web.frame.export.common;
/*    */ 
/*    */ import com.eoos.util.LocaleTransformer;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ public class LocalizedResourceFinder
/*    */ {
/*    */   protected String baseDir;
/*    */   protected Locale locale;
/*    */   
/*    */   public LocalizedResourceFinder(String baseDir, Locale locale) {
/* 14 */     this.baseDir = baseDir;
/* 15 */     if (!this.baseDir.endsWith("/")) {
/* 16 */       this.baseDir += "/";
/*    */     }
/* 18 */     this.locale = locale;
/* 19 */     if (this.locale == null) {
/* 20 */       this.locale = Locale.getDefault();
/*    */     }
/*    */   }
/*    */   
/*    */   private String localeToDir(Locale locale) {
/* 25 */     String retValue = "/";
/* 26 */     if (locale != null) {
/* 27 */       StringBuffer tmp = new StringBuffer(String.valueOf(locale));
/* 28 */       StringUtilities.replace(tmp, "_", "/");
/* 29 */       tmp.append("/");
/* 30 */       retValue = tmp.toString();
/*    */     } 
/* 32 */     return retValue;
/*    */   }
/*    */   
/*    */   public String findResource(String identifier) {
/* 36 */     Locale locale = this.locale;
/* 37 */     ApplicationContext ac = ApplicationContext.getInstance();
/* 38 */     boolean exists = false;
/*    */     
/* 40 */     String resourceName = null;
/*    */     
/* 42 */     while (!exists && locale != null) {
/* 43 */       resourceName = this.baseDir + localeToDir(locale) + String.valueOf(identifier);
/* 44 */       exists = ac.existsResource(resourceName);
/* 45 */       locale = LocaleTransformer.transform(locale);
/*    */     } 
/*    */ 
/*    */     
/* 49 */     if (!exists) {
/* 50 */       resourceName = this.baseDir + identifier;
/* 51 */       exists = ac.existsResource(resourceName);
/*    */     } 
/*    */     
/* 54 */     return exists ? resourceName : null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\LocalizedResourceFinder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */