/*     */ package com.eoos.gm.tis2web.frame.scout.dev;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.LoginInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.scout.BaseScout;
/*     */ import java.util.HashSet;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NAODevelopmentScout
/*     */   extends BaseScout
/*     */ {
/*     */   private static Set naoOrigins;
/*     */   private static Set languages;
/*  21 */   private static final Logger log = Logger.getLogger(NAODevelopmentScout.class);
/*     */   
/*     */   public NAODevelopmentScout() {
/*  24 */     init();
/*     */   }
/*     */   
/*     */   public LoginInfo getLoginInfo(Map params) {
/*  28 */     LoginInfo loginInfo = null;
/*  29 */     loginInfo = new LoginInfo();
/*  30 */     loginInfo.setUser(retrieveUser(params));
/*  31 */     loginInfo.setLoginGroup(getLoginGroup(params));
/*  32 */     Locale locale = getLocale(params);
/*  33 */     loginInfo.setLocale(locale);
/*  34 */     loginInfo.setSPOREFCountry((String)params.get("country"));
/*  35 */     loginInfo.setCountry(getCountry(params, locale));
/*  36 */     loginInfo.setDivisions((String)params.get("divisions"));
/*  37 */     loginInfo.setFromAddr(retrieveSourceAddr(params));
/*  38 */     loginInfo.setConfParam(getConfParam(params));
/*  39 */     loginInfo.setOrigin(getOrigin(params));
/*     */     
/*  41 */     loginInfo.setAuthorized();
/*  42 */     return loginInfo;
/*     */   }
/*     */   
/*     */   public String getScoutClassName() {
/*  46 */     return getClass().getName();
/*     */   }
/*     */   
/*     */   private void init() {
/*  50 */     log.info("Reading allowed origins and supported languages from configuration ... ");
/*  51 */     if (naoOrigins == null) {
/*  52 */       naoOrigins = initSet("frame.scout.nao.origins");
/*     */     }
/*  54 */     if (languages == null) {
/*  55 */       languages = initSet("frame.scout.nao.languages");
/*     */     }
/*  57 */     log.info("Reading origins and languages completed.");
/*     */   }
/*     */ 
/*     */   
/*     */   private Set initSet(String _property) {
/*  62 */     Set<String> result = new HashSet();
/*  63 */     String property = ApplicationContext.getInstance().getProperty(_property);
/*  64 */     if (property != null) {
/*  65 */       StringTokenizer tok = new StringTokenizer(property, ",");
/*  66 */       while (tok.hasMoreElements()) {
/*  67 */         result.add(tok.nextToken());
/*     */       }
/*     */     } 
/*  70 */     return result;
/*     */   }
/*     */   
/*     */   private String getLoginGroup(Map params) {
/*  74 */     return (String)params.get("group");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Locale getLocale(Map params) {
/*  84 */     Locale locale = null;
/*     */     try {
/*  86 */       String language = (String)params.get("Accept-Language");
/*     */       
/*  88 */       if (language == null) {
/*  89 */         language = (String)params.get("accept-language");
/*     */       }
/*  91 */       log.debug("Potential language(s): " + language);
/*  92 */       int n = language.indexOf(',');
/*  93 */       if (n > -1) {
/*  94 */         language = language.substring(0, n);
/*     */       }
/*  96 */       if ((n = language.indexOf(';')) > -1) {
/*  97 */         language = language.substring(0, n);
/*     */       }
/*     */ 
/*     */       
/* 101 */       String lang = null;
/* 102 */       String country = null;
/* 103 */       n = language.indexOf('-');
/* 104 */       if (n < 0) {
/* 105 */         n = language.indexOf('_');
/*     */       }
/* 107 */       if (n > -1) {
/* 108 */         lang = language.substring(0, n);
/* 109 */         country = language.substring(n + 1, language.length()).toUpperCase(Locale.ENGLISH);
/*     */       } else {
/*     */         
/* 112 */         lang = language;
/* 113 */         country = ApplicationContext.getInstance().getProperty("frame.scout.nao.langcountry." + lang);
/* 114 */         log.debug("Missing country portion for locale - trying: " + lang + '_' + country);
/*     */       } 
/*     */       
/* 117 */       if (languages.contains(lang + '_' + country)) {
/* 118 */         locale = new Locale(lang, country);
/*     */       } else {
/*     */         
/* 121 */         country = ApplicationContext.getInstance().getProperty("frame.scout.nao.langcountry." + lang);
/* 122 */         log.debug("Unsupported country - trying: " + lang + '_' + country);
/* 123 */         if (languages.contains(lang + '_' + country)) {
/* 124 */           locale = new Locale(lang, country);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 129 */       if (locale == null) {
/* 130 */         log.debug("Unsupported language: " + language + ", using en_US instead");
/* 131 */         locale = Locale.US;
/*     */       } 
/* 133 */     } catch (Exception e) {
/* 134 */       log.debug("fall back to en_US");
/* 135 */       locale = Locale.US;
/*     */     } 
/* 137 */     log.debug("Locale: " + locale.toString());
/* 138 */     return locale;
/*     */   }
/*     */   
/*     */   public static String getCountry(Map params, Locale locale) {
/* 142 */     int FROM_REQUEST = 0;
/* 143 */     int FROM_LOCALE = 1;
/* 144 */     int DEFAULT = 2;
/* 145 */     int countrySelectedBy = -1;
/* 146 */     String result = null;
/* 147 */     String requestedCountry = null;
/*     */     
/* 149 */     if (params.get("country") != null) {
/* 150 */       requestedCountry = (String)params.get("country");
/* 151 */       if (requestedCountry.length() >= 2) {
/* 152 */         result = requestedCountry;
/* 153 */         countrySelectedBy = 0;
/*     */       } 
/*     */     } 
/*     */     
/* 157 */     if (result == null && locale != null && locale.getCountry() != null) {
/* 158 */       result = locale.getCountry();
/* 159 */       countrySelectedBy = 1;
/*     */     } 
/*     */     
/* 162 */     if (result == null) {
/* 163 */       result = "US";
/* 164 */       countrySelectedBy = 2;
/*     */     } 
/* 166 */     if (log.isDebugEnabled()) {
/* 167 */       log.debug("Requested country: " + requestedCountry);
/* 168 */       String source = null;
/* 169 */       if (countrySelectedBy == 0) {
/* 170 */         source = "request";
/* 171 */       } else if (countrySelectedBy == 1) {
/* 172 */         source = "Locale";
/* 173 */       } else if (countrySelectedBy == 2) {
/* 174 */         source = "default";
/*     */       } else {
/* 176 */         source = "E_R_R_O_R!!!!!";
/*     */       } 
/* 178 */       log.debug("Final country: " + result + " (selected by " + source + ")");
/*     */     } 
/* 180 */     return result;
/*     */   }
/*     */   
/*     */   private String getOrigin(Map params) {
/* 184 */     return (String)params.get("origin");
/*     */   }
/*     */   
/*     */   private String getConfParam(Map params) {
/* 188 */     String result = null;
/* 189 */     String confParamName = ApplicationContext.getInstance().getProperty("frame.scout.nao.login.logparam");
/* 190 */     if (confParamName != null) {
/* 191 */       result = (String)params.get(confParamName);
/*     */     }
/* 193 */     return result;
/*     */   }
/*     */   
/*     */   protected boolean isValidLoginInfo(LoginInfo _loginInfo) {
/* 197 */     boolean result = false;
/* 198 */     if (_loginInfo.getUser() != null && _loginInfo.getGroup2ManufMap() != null && !_loginInfo.getGroup2ManufMap().isEmpty() && naoOrigins.contains(_loginInfo.getOrigin())) {
/* 199 */       result = true;
/*     */     }
/* 201 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\scout\dev\NAODevelopmentScout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */