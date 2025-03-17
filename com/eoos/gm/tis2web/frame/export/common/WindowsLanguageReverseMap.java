/*    */ package com.eoos.gm.tis2web.frame.export.common;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.util.Enumeration;
/*    */ import java.util.HashMap;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ import java.util.Properties;
/*    */ import java.util.Set;
/*    */ import java.util.StringTokenizer;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class WindowsLanguageReverseMap
/*    */ {
/* 16 */   private static final Logger log = Logger.getLogger(WindowsLanguageReverseMap.class);
/*    */   private static final String RESOURCE_NAME = "win_lang_reverse_mapping.properties";
/* 18 */   private static WindowsLanguageReverseMap instance = null;
/*    */   
/* 20 */   private Map<String, Locale> w2lMap = new HashMap<String, Locale>();
/* 21 */   private Map<String, String> icop2tisCountryMap = new HashMap<String, String>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void init() {
/*    */     try {
/* 29 */       Properties p = new Properties();
/* 30 */       InputStream is = getClass().getClassLoader().getResourceAsStream("win_lang_reverse_mapping.properties");
/* 31 */       p.load(is);
/* 32 */       Enumeration<Object> en = p.keys();
/* 33 */       while (en.hasMoreElements()) {
/* 34 */         String k = (String)en.nextElement();
/* 35 */         String v = p.getProperty(k);
/* 36 */         StringTokenizer st = new StringTokenizer(v, "_");
/* 37 */         String lang = st.nextToken();
/* 38 */         String country = null;
/* 39 */         if (st.hasMoreTokens()) {
/* 40 */           country = st.nextToken();
/*    */         }
/* 42 */         if (v != null) {
/* 43 */           Locale locale = (country != null) ? new Locale(lang, country) : new Locale(lang);
/* 44 */           this.w2lMap.put(k, locale);
/* 45 */           this.icop2tisCountryMap.put(k, country);
/*    */         } 
/*    */       } 
/* 48 */     } catch (Exception e) {
/* 49 */       log.error("Cannot build reverse language map. Web Service \"LTService\" will not work properly.");
/* 50 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */   
/*    */   public static synchronized WindowsLanguageReverseMap getInstance() {
/* 55 */     if (instance == null) {
/* 56 */       instance = new WindowsLanguageReverseMap();
/* 57 */       instance.init();
/*    */     } 
/* 59 */     return instance;
/*    */   }
/*    */   
/*    */   public Locale getLocale(String winAcr) {
/* 63 */     Locale locale = this.w2lMap.get(winAcr);
/* 64 */     if (locale == null) {
/* 65 */       locale = Locale.UK;
/*    */     }
/* 67 */     return locale;
/*    */   }
/*    */   
/*    */   public boolean isSupported(String winLang) {
/* 71 */     boolean result = false;
/* 72 */     if (this.w2lMap.keySet().contains(winLang)) {
/* 73 */       result = true;
/*    */     }
/* 75 */     return result;
/*    */   }
/*    */   
/*    */   public Set<String> getIcopCountries() {
/* 79 */     return this.icop2tisCountryMap.keySet();
/*    */   }
/*    */   
/*    */   public String getT2wCountry(String icopCountry) {
/* 83 */     return this.icop2tisCountryMap.get(icopCountry);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\WindowsLanguageReverseMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */