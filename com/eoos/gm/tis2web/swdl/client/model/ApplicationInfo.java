/*    */ package com.eoos.gm.tis2web.swdl.client.model;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ApplicationInfo
/*    */ {
/* 13 */   private String appName = null;
/* 14 */   private String version = null;
/* 15 */   private String versDate = null;
/* 16 */   private String language = null;
/*    */ 
/*    */   
/*    */   public ApplicationInfo(String name, String vers, String date, String lang) {
/* 20 */     this.appName = name;
/* 21 */     this.version = vers;
/* 22 */     this.versDate = date;
/* 23 */     this.language = lang;
/*    */   }
/*    */ 
/*    */   
/*    */   public ApplicationInfo() {}
/*    */   
/*    */   public String getAppName() {
/* 30 */     return this.appName;
/*    */   }
/*    */   
/*    */   public String getVersion() {
/* 34 */     return this.version;
/*    */   }
/*    */   
/*    */   public String getVersDate() {
/* 38 */     return this.versDate;
/*    */   }
/*    */   
/*    */   public String getLanguage() {
/* 42 */     return this.language;
/*    */   }
/*    */   
/*    */   public void setAppName(String name) {
/* 46 */     this.appName = name;
/*    */   }
/*    */   
/*    */   public void setVersion(String v) {
/* 50 */     this.version = v;
/*    */   }
/*    */   
/*    */   public void setVersDate(String date) {
/* 54 */     this.versDate = date;
/*    */   }
/*    */   
/*    */   public void setLanguage(String lan) {
/* 58 */     this.language = lan;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\client\model\ApplicationInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */