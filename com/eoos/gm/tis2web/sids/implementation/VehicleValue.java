/*    */ package com.eoos.gm.tis2web.sids.implementation;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class VehicleValue
/*    */ {
/*    */   protected Integer id;
/*    */   protected Map descriptions;
/*    */   
/*    */   public Integer getID() {
/* 14 */     return this.id;
/*    */   }
/*    */   
/*    */   public String getDescription(Locale locale) {
/* 18 */     String description = (String)this.descriptions.get(locale);
/* 19 */     if (description == null) {
/* 20 */       description = lookupDescription(locale.getLanguage(), locale.getCountry());
/*    */     }
/* 22 */     return description;
/*    */   }
/*    */   
/*    */   public VehicleValue(Integer id) {
/* 26 */     this.id = id;
/* 27 */     this.descriptions = new HashMap<Object, Object>();
/*    */   }
/*    */   
/*    */   public void add(Locale locale, String description) {
/* 31 */     if (description != null) {
/* 32 */       description = description.trim();
/* 33 */       if (description.length() == 0) {
/* 34 */         throw new DataModelException("missing value description (value=" + this.id + ",locale=" + locale + ")");
/*    */       }
/*    */     } else {
/* 37 */       throw new DataModelException("missing value description (value=" + this.id + ",locale=" + locale + ")");
/*    */     } 
/* 39 */     this.descriptions.put(locale, description);
/*    */   }
/*    */   
/*    */   protected String lookupDescription(String language, String country) {
/* 43 */     String fallback = null;
/* 44 */     Iterator<Locale> it = this.descriptions.keySet().iterator();
/* 45 */     while (it.hasNext()) {
/* 46 */       Locale locale = it.next();
/* 47 */       if (locale.getLanguage().equals(language))
/* 48 */         return (String)this.descriptions.get(locale); 
/* 49 */       if (locale.getLanguage().equals("en")) {
/* 50 */         fallback = (String)this.descriptions.get(locale);
/*    */       }
/*    */     } 
/* 53 */     return fallback;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 57 */     return "value-id:" + this.id + "(" + getDescription(Locale.ENGLISH) + ")";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sids\implementation\VehicleValue.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */