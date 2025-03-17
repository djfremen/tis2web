/*     */ package com.eoos.gm.tis2web.frame.scout;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.ConfigurationService;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.propcfg.util.ConfigurationUtil;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public abstract class SporefBaseScout
/*     */   extends BaseScout
/*     */ {
/*     */   protected static final String SPOREF_VALIDATION_ON = "SPOREF validation: ON";
/*     */   protected static final String SPOREF_VALIDATION_OFF = "SPOREF validation: OFF!!!";
/*     */   protected static final String DEFAULT_MANUFACTURER = "ALL";
/*     */   private static final String TICKET_VALIDATOR_CLASS = "com.eoos.gm.tis2web.frame.scout.SporefTicketValidator";
/*  27 */   static final Logger log = Logger.getLogger(SporefBaseScout.class);
/*     */   
/*     */   protected SporefTicketValidator sporefValidator;
/*     */   
/*     */   protected static Set singleEntry2Set(String _property) {
/*  32 */     Set<String> result = new HashSet();
/*  33 */     String property = ApplicationContext.getInstance().getProperty(_property);
/*  34 */     if (property != null) {
/*  35 */       StringTokenizer tok = new StringTokenizer(property, ",");
/*  36 */       while (tok.hasMoreElements()) {
/*  37 */         result.add(tok.nextToken());
/*     */       }
/*     */     } 
/*  40 */     return result;
/*     */   }
/*     */   
/*     */   protected static Set multipleEntries2Set(String keyPrefix) {
/*  44 */     Set<String> result = new HashSet();
/*  45 */     SubConfigurationWrapper subConfigurationWrapper = new SubConfigurationWrapper((Configuration)ApplicationContext.getInstance(), keyPrefix);
/*  46 */     Set keys = subConfigurationWrapper.getKeys();
/*  47 */     for (Iterator<String> iter = keys.iterator(); iter.hasNext(); ) {
/*  48 */       String key = iter.next();
/*  49 */       String portalId = subConfigurationWrapper.getProperty(key);
/*  50 */       result.add(portalId);
/*     */     } 
/*  52 */     return result;
/*     */   }
/*     */   
/*     */   protected String getOrigin(Map params) {
/*  56 */     return (String)params.get("portal_ID") + "/" + (String)params.get("origin");
/*     */   }
/*     */   
/*     */   protected boolean getSporefSettings(String toggleKey) {
/*  60 */     boolean result = true;
/*  61 */     if ((ApplicationContext.getInstance().getProperty(toggleKey) != null && ApplicationContext.getInstance().getProperty(toggleKey).equalsIgnoreCase("false")) || ApplicationContext.getInstance().developMode()) {
/*  62 */       result = false;
/*     */     } else {
/*     */       try {
/*  65 */         this.sporefValidator = (SporefTicketValidator)Class.forName("com.eoos.gm.tis2web.frame.scout.SporefTicketValidator").newInstance();
/*  66 */         log.info("SporefTicketValidator instance created.");
/*  67 */       } catch (Exception e) {
/*  68 */         log.error("Cannot create SporefTicketValidator instance: " + e);
/*     */       } 
/*     */     } 
/*  71 */     return result;
/*     */   }
/*     */   
/*     */   protected boolean isSporefValid(Map params, boolean sporefValidation) {
/*  75 */     boolean result = false;
/*  76 */     long ts = (new Date()).getTime();
/*  77 */     if (!sporefValidation) {
/*  78 */       result = true;
/*     */     } else {
/*     */       try {
/*  81 */         result = this.sporefValidator.isValid(params);
/*  82 */       } catch (Exception e) {
/*  83 */         log.error("Cannot validate sporef ticket: " + e);
/*     */       } 
/*     */     } 
/*  86 */     if (log.isDebugEnabled()) {
/*  87 */       long t = (new Date()).getTime() - ts;
/*  88 */       log.debug("Sporef ticket validation took: " + t + " ms");
/*     */     } 
/*  90 */     return result;
/*     */   }
/*     */   
/*     */   protected int calcConfigHash(List configKeys) {
/*  94 */     ConfigurationService configService = (ConfigurationService)FrameServiceProvider.getInstance().getService(ConfigurationService.class);
/*  95 */     return ConfigurationUtil.configurationHash((Configuration)configService, configKeys, ConfigurationUtil.MODE_FULL_KEYS);
/*     */   }
/*     */   
/*     */   protected boolean isCalidReference(Map params) {
/*  99 */     boolean result = false;
/* 100 */     String strVIN = (String)params.get("inputVIN");
/* 101 */     if (strVIN != null) {
/* 102 */       result = true;
/*     */     }
/* 104 */     return result;
/*     */   }
/*     */   
/*     */   protected Locale getLocaleByRequestParameter(String requestParameter, Map params, Set languages, String keyPrefix) {
/* 108 */     Locale result = null;
/* 109 */     String language = (String)params.get(requestParameter);
/* 110 */     String country = null;
/*     */     try {
/* 112 */       int n = language.indexOf('_');
/* 113 */       if (n > 0) {
/* 114 */         language = language.substring(0, n);
/* 115 */         country = language.substring(n + 1, language.length());
/*     */       } else {
/*     */         
/* 118 */         country = ApplicationContext.getInstance().getProperty(keyPrefix + "." + language);
/* 119 */         log.debug("Missing country in language parameter - trying: " + language + '_' + country);
/*     */       } 
/*     */       
/* 122 */       if (languages.contains(language + '_' + country)) {
/* 123 */         result = new Locale(language, country);
/*     */       }
/*     */     }
/* 126 */     catch (Exception e) {}
/*     */     
/* 128 */     if (result == null) {
/* 129 */       result = Locale.US;
/* 130 */       log.info("Cannot determine locale from language parameter - using en_US instead.");
/*     */     } 
/* 132 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\scout\SporefBaseScout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */