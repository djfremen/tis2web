/*    */ package com.eoos.gm.tis2web.frame.ws.lt.server.wrapper;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.WindowsLanguageReverseMap;
/*    */ import com.eoos.gm.tis2web.frame.export.declaration.service.ConfigurationService;
/*    */ import com.eoos.gm.tis2web.lt.v2.LTDataAdapterFacade;
/*    */ import java.util.HashMap;
/*    */ import java.util.HashSet;
/*    */ import java.util.Iterator;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ public class QualifierData
/*    */ {
/* 17 */   private static QualifierData instance = null;
/*    */   
/*    */   private Set<String> ltLanguages;
/*    */   private Set<String> ltGuiLanguages;
/*    */   private Set<String> ltCountries;
/*    */   private Map<Locale, String> localeCountryMap;
/*    */   
/*    */   private QualifierData() {
/* 25 */     ConfigurationService cs = (ConfigurationService)FrameServiceProvider.getInstance().getService(ConfigurationService.class);
/* 26 */     cs.addObserver(new ConfigurationService.Observer()
/*    */         {
/*    */           public void onModification() {
/* 29 */             QualifierData.this.init();
/*    */           }
/*    */         });
/*    */ 
/*    */     
/* 34 */     init();
/*    */   }
/*    */   
/*    */   public static synchronized QualifierData getInstance() {
/* 38 */     if (instance == null) {
/* 39 */       instance = new QualifierData();
/*    */     }
/* 41 */     return instance;
/*    */   }
/*    */   
/*    */   public Map<Locale, String> getLocaleCountryMap() {
/* 45 */     return this.localeCountryMap;
/*    */   }
/*    */   
/*    */   protected Set<String> getLtLanguages() {
/* 49 */     return this.ltLanguages;
/*    */   }
/*    */   
/*    */   protected Set<String> getLtGuiLanguages() {
/* 53 */     return this.ltGuiLanguages;
/*    */   }
/*    */   
/*    */   protected Set<String> getLtCountries() {
/* 57 */     return this.ltCountries;
/*    */   }
/*    */   
/*    */   private void init() {
/* 61 */     this.ltLanguages = new HashSet<String>(WindowsLanguageReverseMap.getInstance().getIcopCountries());
/* 62 */     this.ltLanguages.retainAll(LTDataAdapterFacade.getInstance().getLT().getWinLanguagesTable());
/*    */     
/* 64 */     this.ltGuiLanguages = LTDataAdapterFacade.getInstance().getLT().getSupportedWinLangs();
/*    */     
/* 66 */     this.ltCountries = new HashSet<String>(WindowsLanguageReverseMap.getInstance().getIcopCountries());
/* 67 */     this.ltCountries.retainAll(LTDataAdapterFacade.getInstance().getLT().getWinLanguagesTable());
/*    */     
/* 69 */     Iterator<String> it = this.ltCountries.iterator();
/* 70 */     this.localeCountryMap = new HashMap<Locale, String>();
/* 71 */     while (it.hasNext()) {
/* 72 */       String winCountry = it.next();
/* 73 */       Locale locale = WindowsLanguageReverseMap.getInstance().getLocale(winCountry);
/* 74 */       this.localeCountryMap.put(locale, winCountry);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\lt\server\wrapper\QualifierData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */