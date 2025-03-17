/*     */ package com.eoos.gm.tis2web.frame.ws.lt.server.security;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.ConfigurationService;
/*     */ import com.eoos.gm.tis2web.frame.ws.util.DesEncrypter;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class WsSecurityData
/*     */ {
/*  21 */   private static final Logger log = Logger.getLogger(WsSecurityData.class);
/*  22 */   private static final Logger wsDetailsLog = Logger.getLogger("wslog");
/*  23 */   private static long tolerance = 20000L;
/*  24 */   private static long samlTolerance = 300000L;
/*  25 */   private static WsSecurityData instance = null;
/*  26 */   private static Map<String, String> authorizedMap = null;
/*  27 */   private static Set<String> issuerSet = null;
/*     */   
/*     */   private static boolean detailedLog;
/*     */   private static boolean headerLog;
/*     */   private static boolean messageLog;
/*     */   private static boolean offByConfig;
/*     */   private static boolean globaltis;
/*     */   
/*     */   private WsSecurityData() {
/*  36 */     ConfigurationService cs = (ConfigurationService)FrameServiceProvider.getInstance().getService(ConfigurationService.class);
/*  37 */     cs.addObserver(new ConfigurationService.Observer()
/*     */         {
/*     */           public void onModification() {
/*  40 */             WsSecurityData.this.init();
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*  45 */     init();
/*     */   }
/*     */   
/*     */   public static synchronized WsSecurityData getInstance() {
/*  49 */     if (instance == null) {
/*  50 */       instance = new WsSecurityData();
/*     */     }
/*  52 */     return instance;
/*     */   }
/*     */   
/*     */   protected boolean isAuthorized(String name, String pwd) {
/*  56 */     boolean result = false;
/*  57 */     if (name != null && pwd != null && authorizedMap.get(name) != null && ((String)authorizedMap.get(name)).compareTo(pwd) == 0) {
/*  58 */       result = true;
/*     */     }
/*  60 */     return result;
/*     */   }
/*     */   
/*     */   protected boolean isAuthorized(String name, String pwd, String uid) {
/*  64 */     boolean result = false;
/*     */     try {
/*  66 */       String u = uid.substring(0, name.length());
/*  67 */       String t = uid.substring(name.length() + 3, uid.length());
/*  68 */       if (u.compareTo(name) == 0 && isAuthorized(name, pwd) && isValid(t)) {
/*  69 */         result = true;
/*     */       }
/*  71 */     } catch (Exception e) {}
/*     */ 
/*     */     
/*  74 */     return result;
/*     */   }
/*     */   
/*     */   protected boolean isAuthorizedBySamlToken(Map<String, String> dataMap) {
/*  78 */     boolean authorized = false;
/*  79 */     String issuer = dataMap.get("issuer");
/*  80 */     String issueInst = dataMap.get("issueinstant");
/*  81 */     String subject = dataMap.get("username");
/*  82 */     Date cDate = new Date();
/*  83 */     long cTime = cDate.getTime();
/*     */     try {
/*  85 */       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
/*  86 */       String tsStr = dataMap.get("issueinstant");
/*  87 */       if (tsStr.endsWith("Z")) {
/*  88 */         tsStr = tsStr.substring(0, tsStr.length() - 1) + "+0000";
/*     */       }
/*  90 */       long iTime = sdf.parse(tsStr).getTime();
/*  91 */       if (cTime - samlTolerance <= iTime && iTime <= cTime + samlTolerance && issuerSet.contains(issuer)) {
/*  92 */         authorized = true;
/*     */       }
/*     */     }
/*  95 */     catch (Exception e) {
/*  96 */       wsDetailsLog.info("Cannot evaluate SAML token.");
/*     */     } 
/*  98 */     if (!authorized) {
/*  99 */       wsDetailsLog.info("Invalid SAML token for Subject/Issuer/IssueInstant/time  [" + subject + "/" + issuer + "/" + issueInst + "/" + cDate.toString() + "].");
/*     */     }
/* 101 */     return authorized;
/*     */   }
/*     */   
/*     */   public boolean isDetailedLog() {
/* 105 */     return detailedLog;
/*     */   }
/*     */   
/*     */   public boolean isMessageLog() {
/* 109 */     return messageLog;
/*     */   }
/*     */   
/*     */   public boolean isHeaderLog() {
/* 113 */     return headerLog;
/*     */   }
/*     */   
/*     */   public boolean securityOffByConfig() {
/* 117 */     return offByConfig;
/*     */   }
/*     */   
/*     */   public boolean isGlobalTis() {
/* 121 */     return globaltis;
/*     */   }
/*     */ 
/*     */   
/*     */   private void init() {
/* 126 */     authorizedMap = new HashMap<String, String>();
/* 127 */     issuerSet = new HashSet<String>();
/* 128 */     detailedLog = false;
/* 129 */     messageLog = false;
/* 130 */     headerLog = false;
/* 131 */     offByConfig = (ApplicationContext.getInstance().getProperty("frame.ws.lt.security.disabled") != null && ApplicationContext.getInstance().getProperty("frame.ws.lt.security.disabled").compareToIgnoreCase("true") == 0);
/* 132 */     String instType = ApplicationContext.getInstance().getProperty("frame.installation.type");
/* 133 */     globaltis = (instType != null && (instType.compareToIgnoreCase("standalone") == 0 || instType.compareToIgnoreCase("server") == 0));
/* 134 */     String logDetailStr = ApplicationContext.getInstance().getProperty("frame.ws.lt.log.detail");
/* 135 */     if (logDetailStr != null && logDetailStr.compareToIgnoreCase("true") == 0) {
/* 136 */       detailedLog = true;
/*     */     }
/* 138 */     String lmStr = ApplicationContext.getInstance().getProperty("frame.ws.lt.log.messages");
/* 139 */     if (lmStr != null && lmStr.compareToIgnoreCase("true") == 0) {
/* 140 */       messageLog = true;
/*     */     }
/* 142 */     String lhStr = ApplicationContext.getInstance().getProperty("frame.ws.lt.log.header");
/* 143 */     if (lhStr != null && lhStr.compareToIgnoreCase("true") == 0) {
/* 144 */       headerLog = true;
/*     */     }
/* 146 */     SubConfigurationWrapper userConfig = new SubConfigurationWrapper((Configuration)ApplicationContext.getInstance(), "frame.ws.lt.security.usr.");
/* 147 */     SubConfigurationWrapper pwdConfig = new SubConfigurationWrapper((Configuration)ApplicationContext.getInstance(), "frame.ws.lt.security.pwd.");
/* 148 */     if (userConfig != null) {
/* 149 */       Iterator<String> it = userConfig.getKeys().iterator();
/* 150 */       while (it.hasNext()) {
/* 151 */         String ndx = it.next();
/* 152 */         String name = userConfig.getProperty(ndx);
/* 153 */         String pwd = pwdConfig.getProperty(ndx);
/* 154 */         if (name.length() > 0 && pwd != null) {
/* 155 */           authorizedMap.put(name, pwd);
/*     */         }
/*     */       } 
/*     */     } 
/* 159 */     SubConfigurationWrapper issuerConfig = new SubConfigurationWrapper((Configuration)ApplicationContext.getInstance(), "frame.ws.lt.security.issuer.");
/* 160 */     if (issuerConfig != null) {
/* 161 */       Iterator<String> it = issuerConfig.getKeys().iterator();
/* 162 */       while (it.hasNext()) {
/* 163 */         String ndx = it.next();
/* 164 */         String issuer = issuerConfig.getProperty(ndx);
/* 165 */         issuerSet.add(issuer);
/*     */       } 
/*     */     } 
/* 168 */     String samlToleranceStr = ApplicationContext.getInstance().getProperty("frame.ws.lt.security.samltolerance");
/*     */     try {
/* 170 */       samlTolerance = Long.parseLong(samlToleranceStr);
/* 171 */     } catch (Exception e) {
/* 172 */       log.info("Invalid SAML token tolerance value: " + samlToleranceStr);
/*     */     } 
/* 174 */     String toleranceStr = ApplicationContext.getInstance().getProperty("frame.ws.lt.security.tolerance");
/*     */     try {
/* 176 */       tolerance = Long.parseLong(toleranceStr);
/* 177 */     } catch (Exception e) {
/* 178 */       log.info("Invalid tolerance value: " + toleranceStr);
/*     */     } 
/* 180 */     log.info("LTService security map initialized [" + authorizedMap.size() + " entries]");
/* 181 */     log.info("LTService SAML issuer set initialized [" + issuerSet.size() + " entries]");
/* 182 */     log.info("LTService SAML tolerance initialized [" + samlTolerance + "ms]");
/*     */   }
/*     */   
/*     */   private boolean isValid(String tk) {
/* 186 */     boolean result = false;
/*     */     try {
/* 188 */       DesEncrypter encr = new DesEncrypter();
/* 189 */       String dStr = encr.decrypt(tk);
/* 190 */       long crTime = Long.parseLong(dStr);
/* 191 */       long myTime = (new Date()).getTime();
/* 192 */       if (myTime - tolerance <= crTime && crTime <= myTime + tolerance) {
/* 193 */         result = true;
/*     */       }
/* 195 */     } catch (Exception e) {}
/*     */ 
/*     */     
/* 198 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\lt\server\security\WsSecurityData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */