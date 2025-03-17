/*     */ package com.eoos.gm.tis2web.frame.scout.gme;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.LoginInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.LogoutTask;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.DealerCode;
/*     */ import com.eoos.gm.tis2web.frame.scout.BaseScout;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.TimerTask;
/*     */ import java.util.Vector;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.xmlrpc.XmlRpcClient;
/*     */ 
/*     */ public class GmeImtScout
/*     */   extends BaseScout
/*     */ {
/*  25 */   private static final Long NULL_LONG = Long.valueOf(0L);
/*     */ 
/*     */   
/*     */   private static final String GROUP_PREFIX = "ug_";
/*     */ 
/*     */   
/*     */   private static final String PORTAL_VALIDATE_METHOD = "validateSession";
/*     */   
/*  33 */   private static final Logger log = Logger.getLogger(GmeImtScout.class);
/*     */ 
/*     */ 
/*     */   
/*     */   public LoginInfo getLoginInfo(Map params) {
/*  38 */     LoginInfo loginInfo = new LoginInfo();
/*  39 */     loginInfo.setUser(retrieveUser(params));
/*  40 */     loginInfo.setLoginGroup(getLoginGroup(params));
/*  41 */     loginInfo.setLocale(getLocale(params));
/*  42 */     loginInfo.setCountry(retrieveCountry(params));
/*  43 */     DealerCode dealerCode = new DealerCode();
/*  44 */     loginInfo.setGroup2ManufMap(getGroup2ManufMap(params));
/*  45 */     loginInfo.setDealerCode(dealerCode);
/*  46 */     loginInfo.setFromAddr(retrieveSourceAddr(params));
/*  47 */     Long duration = null;
/*  48 */     if (isValidLoginInfo(loginInfo) && (duration = validByPortal(params)) != null) {
/*  49 */       loginInfo.setAuthorized();
/*  50 */       scheduleLogout(loginInfo.getUser(), new Date(System.currentTimeMillis() + duration.longValue()));
/*     */     } 
/*  52 */     return loginInfo;
/*     */   }
/*     */   
/*     */   private void scheduleLogout(final String user, Date date) {
/*  56 */     final LogoutTask logoutTask = new LogoutTask(user);
/*  57 */     TimerTask tt = Util.createTimerTask(new Runnable()
/*     */         {
/*     */           public void run() {
/*  60 */             GmeImtScout.log.debug("running scheduled logout (subscription expired) for IMT portal session: " + String.valueOf(user));
/*  61 */             logoutTask.execute();
/*     */           }
/*     */         });
/*     */     
/*  65 */     Util.getTimer().schedule(tt, date);
/*     */   }
/*     */   
/*     */   public String getScoutClassName() {
/*  69 */     return getClass().getName();
/*     */   }
/*     */   
/*     */   protected boolean isValidLoginInfo(LoginInfo _loginInfo) {
/*  73 */     boolean result = false;
/*  74 */     if (_loginInfo.getUser() != null && _loginInfo.getGroup2ManufMap() != null && !_loginInfo.getGroup2ManufMap().isEmpty()) {
/*  75 */       result = true;
/*     */     }
/*  77 */     return result;
/*     */   }
/*     */   
/*     */   private Locale getLocale(Map params) {
/*  81 */     Locale result = null;
/*  82 */     String language = (String)params.get("language");
/*  83 */     if (language != null) {
/*     */       try {
/*  85 */         int n = language.indexOf('_');
/*  86 */         if (n > -1) {
/*  87 */           result = new Locale(language.substring(0, n), language.substring(n + 1, language.length()));
/*     */         }
/*     */       }
/*  90 */       catch (Exception e) {
/*  91 */         log.warn("unable to retrieve locale, returning null - exception: " + e, e);
/*     */       } 
/*     */     }
/*  94 */     return (result != null) ? result : Locale.US;
/*     */   }
/*     */   
/*     */   private String getLoginGroup(Map params) {
/*  98 */     return "N/A";
/*     */   }
/*     */   
/*     */   private String retrieveCountry(Map params) {
/* 102 */     String result = (String)params.get("country");
/* 103 */     if (result == null) {
/* 104 */       result = "DE";
/*     */     }
/* 106 */     return result;
/*     */   }
/*     */   
/*     */   private Map getGroup2ManufMap(Map params) {
/* 110 */     Map<Object, Object> usrGroup2Manuf = new HashMap<Object, Object>();
/*     */     try {
/* 112 */       Iterator<String> it = params.keySet().iterator();
/* 113 */       while (it.hasNext()) {
/* 114 */         String s = it.next();
/* 115 */         if (s.startsWith("ug_")) {
/* 116 */           HashSet<String> set = new HashSet();
/* 117 */           String manufacturers = (String)params.get(s);
/* 118 */           StringTokenizer st = new StringTokenizer(manufacturers);
/* 119 */           while (st.hasMoreTokens()) {
/* 120 */             set.add(st.nextToken());
/*     */           }
/* 122 */           usrGroup2Manuf.put(s.substring("ug_".length(), s.length()), set);
/*     */         } 
/*     */       } 
/* 125 */     } catch (Exception e) {
/* 126 */       log.warn("unable to retrieve group/manufacturer mapping, returning empty map - exception: " + e, e);
/*     */     } 
/* 128 */     return usrGroup2Manuf;
/*     */   }
/*     */   
/*     */   private Long validByPortal(Map params) {
/* 132 */     Long result = null;
/*     */     try {
/* 134 */       String securityID = (String)params.get("id");
/* 135 */       String user = (String)params.get("user");
/* 136 */       if (securityID == null)
/* 137 */         securityID = ""; 
/* 138 */       if (user == null)
/* 139 */         user = ""; 
/* 140 */       String portalURL = ApplicationContext.getInstance().getProperty("frame.portal.imt.url.rpc");
/* 141 */       log.debug("validating IMT portal session: " + String.valueOf(user));
/* 142 */       result = verifyPortalSession(portalURL, user, securityID);
/* 143 */       if (NULL_LONG.equals(result)) {
/* 144 */         result = null;
/*     */       }
/* 146 */       log.debug("...received remaining duration response :" + String.valueOf(result) + ((result == null) ? " (INVALID)" : " (VALID)"));
/* 147 */     } catch (Exception e) {
/* 148 */       log.error("unable to verify session, indicating invalidity -exception:" + e, e);
/* 149 */       result = null;
/*     */     } 
/* 151 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   private Long verifyPortalSession(String url, String sessionID, String securityID) throws Exception {
/* 156 */     XmlRpcClient proxy = null;
/* 157 */     Long result = null;
/* 158 */     Vector<String> params = new Vector();
/* 159 */     params.add(URLEncoder.encode(sessionID, "utf-8"));
/* 160 */     params.add(securityID);
/* 161 */     proxy = new XmlRpcClient(url);
/* 162 */     log.debug("validating session: " + String.valueOf(sessionID));
/* 163 */     String _result = (String)proxy.execute("validateSession", params);
/* 164 */     result = Long.decode(_result);
/* 165 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\scout\gme\GmeImtScout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */