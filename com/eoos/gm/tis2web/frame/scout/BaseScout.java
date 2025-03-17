/*     */ package com.eoos.gm.tis2web.frame.scout;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.LoginInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BaseScout
/*     */   implements IScout
/*     */ {
/*  20 */   private static final Logger log = Logger.getLogger(BaseScout.class);
/*     */   
/*  22 */   private static final Map<String, String> explicitMapping = Collections.synchronizedMap(new HashMap<String, String>());
/*     */   static {
/*  24 */     explicitMapping.put("nn_no", "no_no");
/*  25 */     explicitMapping.put("nb_no", "no_no");
/*  26 */     explicitMapping.put("nn", "no");
/*  27 */     explicitMapping.put("nb", "no");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static Locale getLocaleByBrowserSettings(Map params, Set languages, String keyPrefix) {
/*  37 */     Locale locale = null;
/*     */     try {
/*  39 */       String language = (String)params.get("Accept-Language");
/*     */       
/*  41 */       if (language == null) {
/*  42 */         language = (String)params.get("accept-language");
/*     */       }
/*  44 */       log.debug("Potential language(s): " + language);
/*  45 */       int n = language.indexOf(',');
/*  46 */       if (n > -1) {
/*  47 */         language = language.substring(0, n);
/*     */       }
/*  49 */       if ((n = language.indexOf(';')) > -1) {
/*  50 */         language = language.substring(0, n);
/*     */       }
/*     */       
/*  53 */       language = language.replace('-', '_');
/*  54 */       String replacement = explicitMapping.get(language.toLowerCase(Locale.ENGLISH));
/*  55 */       if (replacement != null) {
/*  56 */         log.debug("found explicit mapping, replacing language '" + language + "' with '" + replacement + "'");
/*  57 */         language = replacement;
/*     */       } 
/*     */ 
/*     */       
/*  61 */       String[] parts = language.split("_");
/*  62 */       String lang = parts[0];
/*  63 */       String country = null;
/*  64 */       if (parts.length > 1) {
/*  65 */         country = parts[1].toUpperCase(Locale.ENGLISH);
/*     */       }
/*     */       
/*  68 */       if (country == null || !languages.contains(lang + "_" + country)) {
/*     */         
/*  70 */         country = ApplicationContext.getInstance().getProperty(keyPrefix + "." + lang);
/*  71 */         log.debug("Missing or unsupported country portion for locale - trying: " + lang + '_' + String.valueOf(country));
/*     */       } 
/*     */       
/*  74 */       if (languages.contains(lang + "_" + country)) {
/*  75 */         locale = new Locale(lang, country);
/*     */       } else {
/*  77 */         log.debug("Unsupported language: " + language + ", using en_US instead");
/*  78 */         locale = Locale.US;
/*     */       } 
/*  80 */     } catch (Exception e) {
/*  81 */       log.debug("fall back to en_US");
/*  82 */       locale = Locale.US;
/*     */     } 
/*  84 */     log.debug("Locale: " + locale.toString());
/*  85 */     return locale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean portalMapped = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String retrieveSourceAddr(Map params) {
/* 105 */     String result = null;
/*     */     try {
/* 107 */       HttpServletRequest request = (HttpServletRequest)params.get("request");
/* 108 */       result = request.getRemoteAddr();
/* 109 */     } catch (Exception e) {}
/*     */     
/* 111 */     return result;
/*     */   }
/*     */   
/*     */   public String getScoutId() {
/* 115 */     String result = getScoutClassName();
/*     */     try {
/* 117 */       result = getScoutClassName().substring(getScoutClassName().lastIndexOf(".") + 1, getScoutClassName().length());
/* 118 */     } catch (Exception e) {}
/*     */ 
/*     */     
/* 121 */     return result;
/*     */   }
/*     */   
/*     */   public void setPortalMapped() {
/* 125 */     this.portalMapped = true;
/*     */   }
/*     */   
/*     */   public boolean isPortalMapped() {
/* 129 */     return this.portalMapped;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 133 */     return getScoutClassName();
/*     */   }
/*     */   
/*     */   protected String retrieveUser(Map params) {
/* 137 */     String result = (String)params.get("user");
/* 138 */     String portalId = getPortalId(params);
/* 139 */     if (portalId != null && portalId.length() != 0) {
/* 140 */       result = portalId + "." + result;
/*     */     }
/* 142 */     return result;
/*     */   }
/*     */   
/*     */   protected String getPortalId(Map params) {
/* 146 */     return (String)params.get("portal_ID");
/*     */   }
/*     */   
/*     */   public abstract LoginInfo getLoginInfo(Map paramMap);
/*     */   
/*     */   public abstract String getScoutClassName();
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\scout\BaseScout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */