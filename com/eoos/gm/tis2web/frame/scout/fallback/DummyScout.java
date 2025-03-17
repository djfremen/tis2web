/*     */ package com.eoos.gm.tis2web.frame.scout.fallback;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.LoginInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.DealerCode;
/*     */ import com.eoos.gm.tis2web.frame.scout.BaseScout;
/*     */ import com.eoos.gm.tis2web.frame.scout.IScout;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class DummyScout
/*     */   extends BaseScout
/*     */   implements IScout {
/*  18 */   private static final Logger log = Logger.getLogger(DummyScout.class);
/*     */ 
/*     */   
/*     */   private static final String GROUP_PREFIX = "ug_";
/*     */ 
/*     */   
/*     */   public LoginInfo getLoginInfo(Map params) {
/*  25 */     LoginInfo loginInfo = new LoginInfo();
/*  26 */     loginInfo.setUser(retrieveUser(params));
/*  27 */     loginInfo.setLoginGroup(getLoginGroup(params));
/*  28 */     loginInfo.setLocale(getLocale(params));
/*  29 */     loginInfo.setCountry(retrieveCountry(params));
/*  30 */     DealerCode dealerCode = new DealerCode();
/*  31 */     loginInfo.setGroup2ManufMap(getGroup2ManufMap(params));
/*  32 */     loginInfo.setDealerCode(dealerCode);
/*  33 */     loginInfo.setFromAddr(retrieveSourceAddr(params));
/*  34 */     if (isValidLoginInfo(loginInfo) && validByPortal(params)) {
/*  35 */       loginInfo.setAuthorized();
/*     */     }
/*  37 */     return loginInfo;
/*     */   }
/*     */   
/*     */   public String getScoutClassName() {
/*  41 */     return getClass().getName();
/*     */   }
/*     */   
/*     */   protected boolean isValidLoginInfo(LoginInfo _loginInfo) {
/*  45 */     boolean result = false;
/*  46 */     if (_loginInfo.getUser() != null && _loginInfo.getGroup2ManufMap() != null && !_loginInfo.getGroup2ManufMap().isEmpty()) {
/*  47 */       result = true;
/*     */     }
/*  49 */     return result;
/*     */   }
/*     */   
/*     */   private Locale getLocale(Map params) {
/*  53 */     Locale result = null;
/*  54 */     String language = (String)params.get("language");
/*     */     try {
/*  56 */       int n = language.indexOf('_');
/*  57 */       if (n > -1) {
/*  58 */         result = new Locale(language.substring(0, n), language.substring(n + 1, language.length()));
/*     */       }
/*  60 */       if (result == null) {
/*  61 */         result = Locale.US;
/*     */       }
/*  63 */     } catch (Exception e) {
/*  64 */       log.warn("unable to retrieve locale, returning null - exception: " + e, e);
/*     */     } 
/*  66 */     return result;
/*     */   }
/*     */   
/*     */   private String getLoginGroup(Map params) {
/*  70 */     return "N/A";
/*     */   }
/*     */   
/*     */   private String retrieveCountry(Map params) {
/*  74 */     String result = (String)params.get("country");
/*  75 */     if (result == null) {
/*  76 */       result = "DE";
/*     */     }
/*  78 */     return result;
/*     */   }
/*     */   
/*     */   private Map getGroup2ManufMap(Map params) {
/*  82 */     Map<Object, Object> usrGroup2Manuf = new HashMap<Object, Object>();
/*     */     try {
/*  84 */       Iterator<String> it = params.keySet().iterator();
/*  85 */       while (it.hasNext()) {
/*  86 */         String s = it.next();
/*  87 */         if (s.startsWith("ug_")) {
/*  88 */           HashSet<String> set = new HashSet();
/*  89 */           String manufacturers = (String)params.get(s);
/*  90 */           StringTokenizer st = new StringTokenizer(manufacturers);
/*  91 */           while (st.hasMoreTokens()) {
/*  92 */             set.add(st.nextToken());
/*     */           }
/*  94 */           usrGroup2Manuf.put(s.substring("ug_".length(), s.length()), set);
/*     */         } 
/*     */       } 
/*  97 */     } catch (Exception e) {
/*  98 */       log.warn("unable to retieve group/manufacturer mapping, returning empty map - exception: " + e, e);
/*     */     } 
/*     */     
/* 101 */     return usrGroup2Manuf;
/*     */   }
/*     */   
/*     */   private boolean validByPortal(Map params) {
/* 105 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\scout\fallback\DummyScout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */