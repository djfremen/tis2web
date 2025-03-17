/*    */ package com.eoos.gm.tis2web.ctoc.implementation.io;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*    */ import java.util.HashSet;
/*    */ import java.util.Iterator;
/*    */ import java.util.Set;
/*    */ import java.util.StringTokenizer;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class CTOCSupportedLanguages
/*    */ {
/* 15 */   private static final Logger log = Logger.getLogger(CTOCSupportedLanguages.class);
/*    */   
/* 17 */   private static CTOCSupportedLanguages ctocSupportedLanguages = null;
/*    */   
/* 19 */   private static Set languagesCache = null;
/*    */ 
/*    */ 
/*    */   
/*    */   private static boolean allLanguagesCached = false;
/*    */ 
/*    */ 
/*    */   
/*    */   public static CTOCSupportedLanguages getInstance() {
/* 28 */     if (ctocSupportedLanguages == null) {
/*    */       try {
/* 30 */         init();
/* 31 */       } catch (Exception e) {
/* 32 */         log.error("unable to init CTOCSupportedLanguages - error:" + e, e);
/*    */       } 
/*    */     }
/* 35 */     return ctocSupportedLanguages;
/*    */   }
/*    */   
/*    */   private static void init() throws Exception {
/* 39 */     ctocSupportedLanguages = new CTOCSupportedLanguages();
/* 40 */     languagesCache = new HashSet();
/* 41 */     StringBuffer languagesLocale = new StringBuffer();
/* 42 */     String ctocCache = ApplicationContext.getInstance().getProperty("frame.ctoc.cache");
/* 43 */     if (ctocCache != null && ctocCache.equalsIgnoreCase("true")) {
/* 44 */       String languages = ApplicationContext.getInstance().getProperty("component.ctocs.languages.cache");
/* 45 */       if (languages != null) {
/* 46 */         StringTokenizer tokenizer = new StringTokenizer(languages, ",");
/* 47 */         while (tokenizer.hasMoreTokens()) {
/* 48 */           LocaleInfo locale = LocaleInfoProvider.getInstance().getLocale(tokenizer.nextToken());
/* 49 */           if (locale != null) {
/* 50 */             languagesCache.add(locale);
/* 51 */             languagesLocale.append(locale.getLocale() + " ");
/*    */           } 
/*    */         } 
/*    */       } 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 59 */       if (languagesCache.size() == 0) {
/* 60 */         languagesCache.addAll(LocaleInfoProvider.getInstance().getLocales());
/* 61 */         allLanguagesCached = true;
/* 62 */         for (Iterator<LocaleInfo> it = LocaleInfoProvider.getInstance().getLocales().iterator(); it.hasNext(); ) {
/* 63 */           LocaleInfo locale = it.next();
/* 64 */           languagesLocale.append(locale + " ");
/*    */         } 
/*    */       } 
/*    */     } 
/* 68 */     log.info("The ctoc languages cached are:" + languagesLocale);
/*    */   }
/*    */ 
/*    */   
/*    */   public Set getCachedLanguages() {
/* 73 */     return languagesCache;
/*    */   }
/*    */   
/*    */   public boolean areAllLanguagesCached() {
/* 77 */     return allLanguagesCached;
/*    */   }
/*    */   
/*    */   public boolean isCachedLanguage(LocaleInfo locale) {
/* 81 */     return languagesCache.contains(locale);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\implementation\io\CTOCSupportedLanguages.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */