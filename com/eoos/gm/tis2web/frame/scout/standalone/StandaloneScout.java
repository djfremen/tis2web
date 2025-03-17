/*     */ package com.eoos.gm.tis2web.frame.scout.standalone;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.LoginInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.DealerCode;
/*     */ import com.eoos.gm.tis2web.frame.scout.BaseScout;
/*     */ import com.eoos.gm.tis2web.frame.scout.InteractionException;
/*     */ import com.eoos.gm.tis2web.frame.scout.ScoutException;
/*     */ import com.eoos.gm.tis2web.registration.standalone.authorization.service.SoftwareKeyProvider;
/*     */ import com.eoos.gm.tis2web.registration.standalone.authorization.service.SoftwareKeyService;
/*     */ import com.eoos.gm.tis2web.registration.standalone.authorization.service.cai.exceptions.HardwareKeyException;
/*     */ import com.eoos.gm.tis2web.registration.standalone.authorization.service.cai.exceptions.InvalidLicenseException;
/*     */ import com.eoos.gm.tis2web.registration.standalone.authorization.service.cai.exceptions.LicenseFileException;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class StandaloneScout extends BaseScout {
/*     */   private static Set languages;
/*     */   
/*     */   public StandaloneScout() {
/*  31 */     if (languages == null) {
/*  32 */       languages = singleEntry2Set("frame.scout.standalone.languages");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static final String REGISTRATION = "registration";
/*     */   
/*     */   private static final String SUPPORTED_LANGUAGES_KEY = "frame.scout.standalone.languages";
/*     */   
/*     */   private static final String DEFAULT_LOCALE_COUNTRY_KEY = "frame.scout.standalone.langcountry";
/*  42 */   private static final Logger log = Logger.getLogger(StandaloneScout.class);
/*     */   
/*     */   public synchronized LoginInfo getLoginInfo(Map params) {
/*  45 */     long startTime = (new Date()).getTime();
/*  46 */     boolean isStandalone = ApplicationContext.getInstance().isStandalone();
/*  47 */     if (!isStandalone) {
/*  48 */       return null;
/*     */     }
/*  50 */     LoginInfo loginInfo = new LoginInfo();
/*  51 */     Locale locale = getLocaleByBrowserSettings(params, languages, "frame.scout.standalone.langcountry");
/*  52 */     loginInfo.setLocale(locale);
/*  53 */     loginInfo.setCountry(getCountry(params, locale));
/*  54 */     String user = null;
/*  55 */     if (ApplicationContext.getInstance().isStandaloneServer()) {
/*  56 */       user = (String)params.get("user");
/*  57 */       if (user == null || user.trim().length() == 0) {
/*  58 */         ClientContext context = ClientContextProvider.getInstance().getTmpContext("registration." + ApplicationContext.getInstance().createID(), locale);
/*  59 */         LoginDialog ld = new LoginDialog(context);
/*  60 */         throw new InteractionException(new ResultObject(0, ld.getHtmlCode(params)));
/*     */       } 
/*     */     } else {
/*  63 */       user = "TIS2WEB";
/*     */     } 
/*  65 */     loginInfo.setUser(user);
/*  66 */     long SWKStartTime = (new Date()).getTime();
/*  67 */     SoftwareKeyService service = SoftwareKeyProvider.getInstance().getService();
/*  68 */     long deltaTime = (new Date()).getTime() - SWKStartTime;
/*  69 */     log.debug("Got SWKService after: " + deltaTime + " ms");
/*     */     try {
/*  71 */       service.acquireSoftwareKey((Configuration)ApplicationContext.getInstance());
/*  72 */       deltaTime = (new Date()).getTime() - SWKStartTime;
/*  73 */       log.debug("Acquired SWK after: " + deltaTime + " ms");
/*     */       
/*  75 */       if (ClientContextProvider.getInstance().getContext(user, false) == null && ClientContextProvider.getInstance().currentSessionCount() >= service.getMaxSessionCount()) {
/*  76 */         throw new ScoutException("licensed.session.count.exeeded");
/*     */       }
/*  78 */       String groupACL = null;
/*  79 */       if (!service.hasValidAuthorization()) {
/*  80 */         groupACL = "registration";
/*     */       } else {
/*  82 */         groupACL = service.getGroupACL();
/*     */       } 
/*  84 */       deltaTime = (new Date()).getTime() - SWKStartTime;
/*  85 */       log.debug("Got SWKServiceInfo after: " + deltaTime + " ms");
/*  86 */       loginInfo.setT2WGroup(groupACL);
/*  87 */       Map<Object, Object> map = new HashMap<Object, Object>();
/*  88 */       HashSet<String> manufacturers = new HashSet();
/*  89 */       manufacturers.add("ALL");
/*  90 */       map.put(groupACL, manufacturers);
/*  91 */       loginInfo.setGroup2ManufMap(map);
/*  92 */       loginInfo.setDealerCode(getDealerCode(groupACL));
/*  93 */       loginInfo.setAuthorized();
/*  94 */       long time = (new Date()).getTime() - startTime;
/*  95 */       log.debug("StandaloneScout.getLoginInfo took: " + time + " ms");
/*  96 */       return loginInfo;
/*  97 */     } catch (ScoutException e) {
/*  98 */       throw e;
/*  99 */     } catch (LicenseFileException el) {
/* 100 */       throw new ScoutException("frame.registration.license.exception");
/* 101 */     } catch (InvalidLicenseException ei) {
/* 102 */       throw new ScoutException("frame.registration.license.invalid");
/* 103 */     } catch (HardwareKeyException eh) {
/* 104 */       throw new ScoutException("frame.registration.hardwarekey.failure");
/* 105 */     } catch (Exception e) {
/* 106 */       throw new ScoutException("frame.registration.login.failed");
/*     */     } 
/*     */   }
/*     */   
/*     */   private DealerCode getDealerCode(String group) {
/* 111 */     DealerCode result = new DealerCode();
/* 112 */     String bac = ApplicationContext.getInstance().getProperty("frame.scout.standalone.bac");
/* 113 */     HashMap<Object, Object> dlrCodes = new HashMap<Object, Object>();
/* 114 */     if (bac != null) {
/* 115 */       dlrCodes.put(group, bac);
/*     */     }
/* 117 */     result.setDealerCodes(dlrCodes);
/* 118 */     return result;
/*     */   }
/*     */   
/*     */   private static Set singleEntry2Set(String _property) {
/* 122 */     Set<String> result = new HashSet();
/* 123 */     String property = ApplicationContext.getInstance().getProperty(_property);
/* 124 */     if (property != null) {
/* 125 */       StringTokenizer tok = new StringTokenizer(property, ",");
/* 126 */       while (tok.hasMoreElements()) {
/* 127 */         result.add(tok.nextToken());
/*     */       }
/*     */     } 
/* 130 */     return result;
/*     */   }
/*     */   
/*     */   private String getCountry(Map params, Locale locale) {
/* 134 */     int FROM_REQUEST = 0;
/* 135 */     int FROM_LOCALE = 1;
/* 136 */     int DEFAULT = 2;
/* 137 */     int countrySelectedBy = -1;
/* 138 */     String result = null;
/* 139 */     String requestedCountry = null;
/*     */     
/* 141 */     if (params.get("country") != null) {
/* 142 */       requestedCountry = (String)params.get("country");
/* 143 */       if (requestedCountry.length() >= 2) {
/* 144 */         result = requestedCountry;
/* 145 */         countrySelectedBy = 0;
/*     */       } 
/*     */     } 
/*     */     
/* 149 */     if (result == null && locale != null && locale.getCountry() != null) {
/* 150 */       result = locale.getCountry();
/* 151 */       countrySelectedBy = 1;
/*     */     } 
/*     */     
/* 154 */     if (result == null) {
/* 155 */       result = "US";
/* 156 */       countrySelectedBy = 2;
/*     */     } 
/* 158 */     if (log.isDebugEnabled()) {
/* 159 */       log.debug("Requested country: " + requestedCountry);
/* 160 */       String source = null;
/* 161 */       if (countrySelectedBy == 0) {
/* 162 */         source = "request";
/* 163 */       } else if (countrySelectedBy == 1) {
/* 164 */         source = "Locale";
/* 165 */       } else if (countrySelectedBy == 2) {
/* 166 */         source = "default";
/*     */       } else {
/* 168 */         source = "E_R_R_O_R!!!!!";
/*     */       } 
/* 170 */       log.debug("Final country: " + result + " (selected by " + source + ")");
/*     */     } 
/* 172 */     return result;
/*     */   }
/*     */   
/*     */   public String getScoutClassName() {
/* 176 */     return getClass().getName();
/*     */   }
/*     */   
/*     */   protected boolean isValidLoginInfo(LoginInfo loginInfo) {
/* 180 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\scout\standalone\StandaloneScout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */