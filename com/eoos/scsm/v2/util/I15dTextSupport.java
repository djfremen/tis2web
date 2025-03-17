/*    */ package com.eoos.scsm.v2.util;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ public class I15dTextSupport
/*    */   implements I15dText, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Map map;
/*    */   private Locale defaultLocale;
/*    */   
/*    */   public I15dTextSupport(Locale defaultLocale, Map map) {
/* 16 */     this.defaultLocale = defaultLocale;
/* 17 */     this.map = map;
/*    */   }
/*    */   
/*    */   private Locale transform(Locale locale) {
/* 21 */     Locale ret = null;
/* 22 */     if (locale != null && !locale.equals(this.defaultLocale)) {
/* 23 */       if (locale.getVariant() != null && locale.getVariant().length() != 0) {
/* 24 */         ret = new Locale(locale.getLanguage(), locale.getCountry());
/* 25 */       } else if (locale.getCountry() != null && locale.getCountry().length() != 0) {
/* 26 */         ret = new Locale(locale.getLanguage(), "");
/*    */       } else {
/* 28 */         ret = this.defaultLocale;
/*    */       } 
/*    */     }
/*    */     
/* 32 */     return ret;
/*    */   }
/*    */   
/*    */   public String getText(Locale locale) {
/* 36 */     if (locale == null) {
/* 37 */       return getText(this.defaultLocale);
/*    */     }
/* 39 */     String ret = null;
/* 40 */     while (ret == null && locale != null) {
/* 41 */       ret = (String)this.map.get(locale);
/* 42 */       locale = transform(locale);
/*    */     } 
/* 44 */     return ret;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 48 */     int ret = I15dTextSupport.class.hashCode();
/* 49 */     ret = HashCalc.addHashCode(ret, this.map);
/* 50 */     ret = HashCalc.addHashCode(ret, this.defaultLocale);
/* 51 */     return ret;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 55 */     if (this == obj)
/* 56 */       return true; 
/* 57 */     if (obj instanceof I15dTextSupport) {
/* 58 */       I15dTextSupport other = (I15dTextSupport)obj;
/* 59 */       boolean ret = this.map.equals(other.map);
/* 60 */       ret = (ret && this.defaultLocale.equals(other.defaultLocale));
/* 61 */       return ret;
/*    */     } 
/* 63 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v\\util\I15dTextSupport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */