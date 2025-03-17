/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.common;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class SPSLanguage {
/*     */   protected Integer localeID;
/*     */   protected String locale;
/*     */   protected String tla;
/*     */   protected String dbid;
/*  17 */   private static Logger log = Logger.getLogger(SPSLanguage.class);
/*     */   protected Locale java;
/*     */   
/*  20 */   private static final class StaticData { private static StaticData instance = null;
/*     */     
/*  22 */     private HashMap locales = new HashMap<Object, Object>();
/*     */     
/*  24 */     private List localesID = new ArrayList();
/*     */     
/*  26 */     private HashMap localesTLA = new HashMap<Object, Object>();
/*     */     
/*     */     private StaticData() {
/*     */       try {
/*  30 */         Collection all = LocaleInfoProvider.getInstance().getLocales();
/*  31 */         Iterator<LocaleInfo> it = all.iterator();
/*  32 */         while (it.hasNext()) {
/*  33 */           LocaleInfo locale = it.next();
/*  34 */           SPSLanguage l = new SPSLanguage(locale.getLocaleID().intValue(), locale.getLocale(), locale.getLocaleTLA());
/*  35 */           this.locales.put(l.getLocale(), l);
/*  36 */           this.localesID.add(l);
/*  37 */           this.localesTLA.put(l.getLocaleTLA(), l);
/*     */         } 
/*  39 */         if (this.locales.size() > 0) {
/*     */           return;
/*     */         }
/*  42 */       } catch (Exception e) {
/*  43 */         SPSLanguage.log.error("failed to initialize sps-language module - exception:" + e);
/*  44 */         throw (e instanceof RuntimeException) ? (RuntimeException)e : new RuntimeException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public static synchronized StaticData getInstance() {
/*  50 */       if (instance == null) {
/*  51 */         instance = new StaticData();
/*     */       }
/*  53 */       return instance;
/*     */     }
/*     */     
/*     */     public Map getLocales() {
/*  57 */       return this.locales;
/*     */     }
/*     */     
/*     */     public List getLocalesID() {
/*  61 */       return this.localesID;
/*     */     }
/*     */     
/*     */     public Map getLocalesTLA() {
/*  65 */       return this.localesTLA;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void init() {} }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SPSLanguage(int localeID, String locale, String tla) {
/*  84 */     this.localeID = Integer.valueOf(localeID);
/*  85 */     this.locale = locale;
/*  86 */     this.tla = tla;
/*  87 */     int delimiter = locale.indexOf('_');
/*  88 */     if (delimiter >= 0) {
/*  89 */       String language = locale.substring(0, delimiter);
/*  90 */       String country = locale.substring(delimiter + 1);
/*  91 */       this.java = new Locale(language, country);
/*     */     } else {
/*  93 */       throw new IllegalArgumentException("invalid locale:" + locale);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getID() {
/*  98 */     return this.dbid;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getLocaleID() {
/* 104 */     return this.localeID;
/*     */   }
/*     */   
/*     */   public String getLocale() {
/* 108 */     return this.locale;
/*     */   }
/*     */   
/*     */   public Locale getJavaLocale() {
/* 112 */     return this.java;
/*     */   }
/*     */   
/*     */   public String getLocaleTLA() {
/* 116 */     return this.tla;
/*     */   }
/*     */   
/*     */   protected SPSLanguage(SPSLanguage language, String dbid) {
/* 120 */     this.localeID = language.getLocaleID();
/* 121 */     this.locale = language.getLocale();
/* 122 */     this.java = language.getJavaLocale();
/* 123 */     this.dbid = dbid;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SPSLanguage() {}
/*     */   
/*     */   public int hashCode() {
/* 130 */     return this.localeID.intValue();
/*     */   }
/*     */   
/*     */   public boolean equals(Object object) {
/* 134 */     if (object == null)
/* 135 */       return false; 
/* 136 */     if (object instanceof SPSLanguage) {
/* 137 */       return ((SPSLanguage)object).localeID.equals(this.localeID);
/*     */     }
/* 139 */     return false;
/*     */   }
/*     */   
/*     */   public static String lookupLocale(Locale locale) {
/* 143 */     String localeCode = locale.toString();
/* 144 */     if (StaticData.getInstance().getLocales().get(localeCode) != null) {
/* 145 */       return localeCode;
/*     */     }
/* 147 */     String language = locale.getLanguage();
/* 148 */     Iterator<String> it = StaticData.getInstance().getLocales().keySet().iterator();
/* 149 */     while (it.hasNext()) {
/* 150 */       String lkey = it.next();
/* 151 */       if (lkey.startsWith(language)) {
/* 152 */         return lkey;
/*     */       }
/*     */     } 
/* 155 */     if (locale == Locale.ENGLISH) {
/* 156 */       throw new IllegalArgumentException();
/*     */     }
/* 158 */     return lookupLocale(Locale.ENGLISH);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String lookupLocale(String locale) {
/* 163 */     if (StaticData.getInstance().getLocales().get(locale) != null) {
/* 164 */       return locale;
/*     */     }
/* 166 */     int delimiter = locale.indexOf('_');
/* 167 */     if (delimiter >= 0)
/* 168 */       return lookupLanguage(locale.substring(0, delimiter)); 
/* 169 */     if ("en".equalsIgnoreCase(locale)) {
/* 170 */       return lookupLocale(Locale.ENGLISH);
/*     */     }
/* 172 */     return lookupLanguage(locale);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String lookupLanguage(String language) {
/* 178 */     Iterator<String> it = StaticData.getInstance().getLocales().keySet().iterator();
/* 179 */     while (it.hasNext()) {
/* 180 */       String lkey = it.next();
/* 181 */       if (lkey.startsWith(language)) {
/* 182 */         return lkey;
/*     */       }
/*     */     } 
/* 185 */     return lookupLocale(Locale.ENGLISH);
/*     */   }
/*     */   
/*     */   public static synchronized void init() throws Exception {
/* 189 */     StaticData.getInstance().init();
/*     */   }
/*     */ 
/*     */   
/*     */   protected static Map getLocalesTLA() {
/* 194 */     return StaticData.getInstance().getLocalesTLA();
/*     */   }
/*     */   
/*     */   protected static List getLocalesID() {
/* 198 */     return StaticData.getInstance().getLocalesID();
/*     */   }
/*     */   
/*     */   protected static Map getLocales() {
/* 202 */     return StaticData.getInstance().getLocales();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\common\SPSLanguage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */