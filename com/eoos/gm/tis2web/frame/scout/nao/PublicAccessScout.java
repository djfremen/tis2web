/*     */ package com.eoos.gm.tis2web.frame.scout.nao;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.LoginInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.DealerCode;
/*     */ import com.eoos.gm.tis2web.frame.scout.BaseScout;
/*     */ import com.eoos.io.Counter;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class PublicAccessScout
/*     */   extends BaseScout
/*     */ {
/*  21 */   private static final Logger log = Logger.getLogger(PublicAccessScout.class);
/*     */   
/*  23 */   private final Object SYNC_COUNTER = new Object();
/*     */   
/*  25 */   private Map ipToCounter = new HashMap<Object, Object>();
/*     */   
/*  27 */   private int maximalSessionCount_TOTAL = -1;
/*     */   
/*  29 */   private int maximalSessionCount_IP = -1;
/*     */   
/*     */   public PublicAccessScout() {
/*     */     try {
/*  33 */       this.maximalSessionCount_TOTAL = Integer.parseInt(ApplicationContext.getInstance().getProperty("frame.scout.nao.public.access.max.session.count.total"));
/*  34 */     } catch (Exception e) {
/*  35 */       log.warn("unable to read configuration property \"scout.nao.public.access.max.session.count.total\", login restrictions are turned off");
/*     */     } 
/*     */     try {
/*  38 */       this.maximalSessionCount_IP = Integer.parseInt(ApplicationContext.getInstance().getProperty("frame.scout.nao.public.access.max.session.count.ip"));
/*  39 */     } catch (Exception e) {
/*  40 */       log.warn("unable to read configuration property \"scout.nao.public.access.max.session.count.ip\", login restrictions are turned off");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public LoginInfo getLoginInfo(Map params) {
/*  46 */     LoginInfo loginInfo = null;
/*  47 */     if (usedPlainURL(params)) {
/*  48 */       loginInfo = new LoginInfo();
/*  49 */       loginInfo.setPublicAccess(true);
/*     */       
/*  51 */       loginInfo.setUser(retrieveUser(params));
/*  52 */       loginInfo.setLoginGroup("CALID-PUBLIC");
/*  53 */       loginInfo.setLocale(getLocale(params));
/*  54 */       loginInfo.setCountry(null);
/*  55 */       loginInfo.setGroup2ManufMap(getGroup2ManufMap(params));
/*  56 */       loginInfo.setDealerCode(DealerCode.NULL);
/*  57 */       loginInfo.setFromAddr(retrieveSourceAddr(params));
/*  58 */       loginInfo.setConfParam("");
/*  59 */       loginInfo.setOrigin("CALID-PUBLIC");
/*  60 */       boolean authorized = true;
/*  61 */       authorized = (authorized && (this.maximalSessionCount_TOTAL < 0 || ClientContextProvider.getInstance().currentPublicAccessCount(null) < this.maximalSessionCount_TOTAL));
/*  62 */       authorized = (authorized && (this.maximalSessionCount_IP < 0 || ClientContextProvider.getInstance().currentPublicAccessCount(retrieveSourceAddr(params)) < this.maximalSessionCount_IP));
/*  63 */       if (authorized) {
/*  64 */         loginInfo.setAuthorized();
/*     */       }
/*     */     } else {
/*  67 */       log.debug("ignoring login request, because it contains extra path information or query parameters ");
/*     */     } 
/*  69 */     return loginInfo;
/*     */   }
/*     */   
/*     */   private boolean usedPlainURL(Map params) {
/*  73 */     log.debug("checking if request had paramters...");
/*  74 */     boolean retValue = true;
/*  75 */     HttpServletRequest request = (HttpServletRequest)params.get("request");
/*     */     
/*  77 */     String user = (String)params.get("user");
/*  78 */     log.debug("....user parameter: " + String.valueOf(user));
/*  79 */     retValue = (retValue && (user == null || user.trim().length() == 0));
/*  80 */     log.debug("....query string: " + String.valueOf(request.getQueryString()));
/*  81 */     retValue = (retValue && request.getQueryString() == null);
/*  82 */     log.debug("....path info: " + String.valueOf(request.getPathInfo()));
/*  83 */     retValue = (retValue && request.getPathInfo() == null);
/*  84 */     log.debug("....servlet path: " + String.valueOf(request.getServletPath()));
/*  85 */     retValue = (retValue && (request.getServletPath().length() == 0 || (request.getServletPath().length() == 1 && request.getServletPath().compareTo("/") == 0)));
/*     */     
/*  87 */     log.debug("....processing result ->" + (retValue ? "no parameters used" : "parameters used !!"));
/*  88 */     return retValue;
/*     */   }
/*     */   
/*     */   private Counter getCounter(String ipAddress) {
/*  92 */     synchronized (this.SYNC_COUNTER) {
/*  93 */       Counter retValue = (Counter)this.ipToCounter.get(ipAddress);
/*  94 */       if (retValue == null) {
/*  95 */         retValue = new Counter();
/*  96 */         this.ipToCounter.put(ipAddress, retValue);
/*     */       } 
/*  98 */       return retValue;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected String retrieveUser(Map params) {
/* 103 */     StringBuffer retValue = new StringBuffer(retrieveSourceAddr(params));
/* 104 */     Counter counter = getCounter(getScoutClassName());
/* 105 */     synchronized (counter) {
/* 106 */       counter.inc();
/* 107 */       retValue.append(".");
/* 108 */       retValue.append(counter.getValue());
/*     */     } 
/* 110 */     return retValue.toString();
/*     */   }
/*     */   
/*     */   public String getScoutClassName() {
/* 114 */     return getClass().getName();
/*     */   }
/*     */   
/*     */   private Locale getLocale(Map params) {
/* 118 */     HttpServletRequest request = (HttpServletRequest)params.get("request");
/* 119 */     return request.getLocale();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Map getGroup2ManufMap(Map params) {
/* 131 */     Map<Object, Object> group2ManufMap = new HashMap<Object, Object>();
/* 132 */     Set<String> sms = new HashSet();
/* 133 */     sms.add("ALL");
/* 134 */     group2ManufMap.put("public", sms);
/* 135 */     return group2ManufMap;
/*     */   }
/*     */   
/*     */   protected boolean isValidLoginInfo(LoginInfo loginInfo) {
/* 139 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\scout\nao\PublicAccessScout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */