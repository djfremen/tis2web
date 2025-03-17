/*    */ package com.eoos.propcfg;
/*    */ 
/*    */ import java.text.NumberFormat;
/*    */ import java.util.Locale;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TypeDecorator
/*    */   implements Configuration
/*    */ {
/*    */   private Configuration configuration;
/*    */   
/*    */   public TypeDecorator(Configuration configuration) {
/* 15 */     this.configuration = configuration;
/*    */   }
/*    */   
/*    */   public Number getNumber(String key, Locale locale) {
/* 19 */     Number retValue = null;
/* 20 */     String value = this.configuration.getProperty(key);
/* 21 */     if (value != null) {
/*    */       try {
/* 23 */         NumberFormat nf = NumberFormat.getNumberInstance(locale);
/* 24 */         retValue = nf.parse(value);
/* 25 */       } catch (Exception e) {}
/*    */     }
/*    */     
/* 28 */     return retValue;
/*    */   }
/*    */   
/*    */   public Number getNumber(String key) {
/* 32 */     return getNumber(key, Locale.ENGLISH);
/*    */   }
/*    */   
/*    */   public static boolean getBoolean(Configuration configuration, String key, boolean defaultValue) {
/* 36 */     boolean ret = defaultValue;
/* 37 */     String value = configuration.getProperty(key);
/* 38 */     Boolean tmp = null;
/* 39 */     if (value != null && (tmp = Boolean.valueOf(value)) != null) {
/* 40 */       ret = tmp.booleanValue();
/*    */     }
/* 42 */     return ret;
/*    */   }
/*    */   
/*    */   public Boolean getBoolean(String key) {
/* 46 */     Boolean retValue = null;
/* 47 */     String value = this.configuration.getProperty(key);
/* 48 */     if (value != null) {
/*    */       try {
/* 50 */         retValue = Boolean.valueOf(value);
/* 51 */       } catch (Exception e) {}
/*    */     }
/*    */     
/* 54 */     return retValue;
/*    */   }
/*    */   
/*    */   public static boolean getBoolean(String key, Configuration cfg, boolean defaultValue) {
/* 58 */     boolean ret = defaultValue;
/* 59 */     String value = cfg.getProperty(key);
/* 60 */     if (value != null) {
/* 61 */       ret = Boolean.valueOf(value.trim()).booleanValue();
/*    */     }
/* 63 */     return ret;
/*    */   }
/*    */   
/*    */   public String getProperty(String key) {
/* 67 */     return this.configuration.getProperty(key);
/*    */   }
/*    */   
/*    */   public Set getKeys() {
/* 71 */     return this.configuration.getKeys();
/*    */   }
/*    */   
/*    */   public String getFullKey(String key) {
/* 75 */     return this.configuration.getFullKey(key);
/*    */   }
/*    */   
/*    */   public static int getInt(Configuration configuration, String key) {
/* 79 */     String value = configuration.getProperty(key);
/* 80 */     return Integer.parseInt(value.trim());
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\propcfg\TypeDecorator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */