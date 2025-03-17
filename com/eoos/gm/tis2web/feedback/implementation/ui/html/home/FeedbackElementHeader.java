/*    */ package com.eoos.gm.tis2web.feedback.implementation.ui.html.home;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class FeedbackElementHeader {
/*    */   private String Label;
/*    */   
/*    */   public FeedbackElementHeader() {
/*  9 */     this.templateName = "";
/* 10 */     this.params = new HashMap<Object, Object>();
/* 11 */     this.Label = "";
/* 12 */     setType("");
/*    */   }
/*    */   private String templateName; private Map params;
/*    */   protected String getType() {
/* 16 */     return getKeyValue("Type");
/*    */   }
/*    */   
/*    */   protected void setType(String value) {
/* 20 */     if (this.params != null)
/* 21 */       this.params.put("Type", value); 
/*    */   }
/*    */   
/*    */   protected String getChoosenLocale() {
/* 25 */     return getKeyValue("Choosen_Locale_Code");
/*    */   }
/*    */   
/*    */   protected void setChoosenLocale(String Locale) {
/* 29 */     if (this.params != null)
/* 30 */       this.params.put("Choosen_Locale_Code", Locale); 
/*    */   }
/*    */   
/*    */   protected String getFallbackLocale() {
/* 34 */     return getKeyValue("Fallback_Locale_Code");
/*    */   }
/*    */   
/*    */   public void setFallbackLocale(String Locale_US) {
/* 38 */     if (this.params != null)
/* 39 */       this.params.put("Fallback_Locale_Code", Locale_US); 
/*    */   }
/*    */   
/*    */   protected String getLocale() {
/* 43 */     return getKeyValue("Locale_Code");
/*    */   }
/*    */   
/*    */   protected void setLocale(String Locale) {
/* 47 */     if (this.params != null)
/* 48 */       this.params.put("Locale_Code", Locale); 
/*    */   }
/*    */   
/*    */   protected void setLabel(String label) {
/* 52 */     this.Label = label;
/*    */   }
/*    */   
/*    */   protected String getLabel() {
/* 56 */     return this.Label;
/*    */   }
/*    */   
/*    */   protected void setParam(String Name, String Value) {
/* 60 */     if (this.params != null)
/* 61 */       this.params.put(Name, Value); 
/*    */   }
/*    */   
/*    */   protected void setTemplateName(String parameter) {
/* 65 */     this.templateName = parameter;
/*    */   }
/*    */   
/*    */   protected String getTemplateName() {
/* 69 */     return this.templateName;
/*    */   }
/*    */   
/*    */   protected void setParams(Map parameter) {
/* 73 */     this.params.putAll(parameter);
/*    */   }
/*    */   
/*    */   protected Map getParams() {
/* 77 */     return this.params;
/*    */   }
/*    */   
/*    */   public String getKeyValue(String key) {
/* 81 */     String ret = "";
/* 82 */     if (this.params != null && this.params.containsKey(key))
/* 83 */       ret = (String)this.params.get(key); 
/* 84 */     return ret;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\feedback\implementatio\\ui\html\home\FeedbackElementHeader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */