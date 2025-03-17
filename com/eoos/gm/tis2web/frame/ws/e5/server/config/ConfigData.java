/*     */ package com.eoos.gm.tis2web.frame.ws.e5.server.config;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.ConfigurationService;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import java.io.InputStream;
/*     */ import java.security.KeyStore;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ import sun.misc.BASE64Encoder;
/*     */ 
/*     */ public class ConfigData
/*     */ {
/*     */   protected static final String CONFIG_PREFIX = "frame.ws.e5.";
/*  22 */   private static final Logger log = Logger.getLogger(ConfigData.class);
/*  23 */   private static long tolerance = 20000L;
/*  24 */   private static long imtTolerance = 25000L;
/*  25 */   private static ConfigData instance = null;
/*  26 */   private static Map<String, String> authorizedMap = null;
/*  27 */   private static Map<String, String> imtAuthorizedMap = null;
/*  28 */   private static Set<String> encCerts = null;
/*     */   private static boolean detailedLog;
/*     */   private static boolean headerLog;
/*     */   private static boolean messageLog;
/*     */   private static boolean globaltis;
/*  33 */   private byte[] e5TokenPwd = null;
/*     */ 
/*     */   
/*     */   private ConfigData() {
/*  37 */     ConfigurationService cs = (ConfigurationService)FrameServiceProvider.getInstance().getService(ConfigurationService.class);
/*  38 */     cs.addObserver(new ConfigurationService.Observer()
/*     */         {
/*     */           public void onModification() {
/*  41 */             ConfigData.this.init();
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*  46 */     init();
/*     */   }
/*     */   
/*     */   public static synchronized ConfigData getInstance() {
/*  50 */     if (instance == null) {
/*  51 */       instance = new ConfigData();
/*     */     }
/*  53 */     return instance;
/*     */   }
/*     */   
/*     */   public boolean isDetailedLog() {
/*  57 */     return detailedLog;
/*     */   }
/*     */   
/*     */   public boolean isMessageLog() {
/*  61 */     return messageLog;
/*     */   }
/*     */   
/*     */   public boolean isHeaderLog() {
/*  65 */     return headerLog;
/*     */   }
/*     */   
/*     */   public boolean isGlobalTis() {
/*  69 */     return globaltis;
/*     */   }
/*     */   
/*     */   public byte[] getE5TokenPwd() {
/*  73 */     return this.e5TokenPwd;
/*     */   }
/*     */   
/*     */   public long getTolerance() {
/*  77 */     return tolerance;
/*     */   }
/*     */   
/*     */   public long getImtTolerance() {
/*  81 */     return imtTolerance;
/*     */   }
/*     */   
/*     */   public boolean isAuthorized(String id, String p) {
/*  85 */     boolean result = false;
/*     */     try {
/*  87 */       if (((String)authorizedMap.get(id)).compareTo(p) == 0) {
/*  88 */         result = true;
/*     */       }
/*  90 */     } catch (Exception e) {}
/*     */ 
/*     */     
/*  93 */     return result;
/*     */   }
/*     */   
/*     */   public String getPwd(String id) {
/*  97 */     String result = null;
/*     */     try {
/*  99 */       result = imtAuthorizedMap.get(id);
/*     */     }
/* 101 */     catch (Exception e) {}
/*     */     
/* 103 */     return result;
/*     */   }
/*     */   
/*     */   public boolean isKnownCert(String s) {
/* 107 */     return encCerts.contains(s);
/*     */   }
/*     */ 
/*     */   
/*     */   private void init() {
/* 112 */     authorizedMap = new HashMap<String, String>();
/* 113 */     imtAuthorizedMap = new HashMap<String, String>();
/* 114 */     encCerts = new HashSet<String>();
/* 115 */     detailedLog = false;
/* 116 */     messageLog = false;
/* 117 */     headerLog = false;
/* 118 */     String instType = ApplicationContext.getInstance().getProperty("frame.installation.type");
/* 119 */     globaltis = (instType != null && (instType.compareToIgnoreCase("standalone") == 0 || instType.compareToIgnoreCase("server") == 0));
/* 120 */     String logDetailStr = ApplicationContext.getInstance().getProperty("frame.ws.e5.log.detail");
/* 121 */     if (logDetailStr != null && logDetailStr.compareToIgnoreCase("true") == 0) {
/* 122 */       detailedLog = true;
/*     */     }
/* 124 */     String lmStr = ApplicationContext.getInstance().getProperty("frame.ws.e5.log.messages");
/* 125 */     if (lmStr != null && lmStr.compareToIgnoreCase("true") == 0) {
/* 126 */       messageLog = true;
/*     */     }
/* 128 */     String lhStr = ApplicationContext.getInstance().getProperty("frame.ws.e5.log.header");
/* 129 */     if (lhStr != null && lhStr.compareToIgnoreCase("true") == 0) {
/* 130 */       headerLog = true;
/*     */     }
/* 132 */     String s = ApplicationContext.getInstance().getProperty("frame.ws.e5.token.pwd");
/* 133 */     if (s != null) {
/* 134 */       if (s.length() >= 8) {
/* 135 */         this.e5TokenPwd = s.substring(0, 8).getBytes();
/*     */       } else {
/* 137 */         log.error("Invalid E5 Service token password (8 characters or more required).");
/*     */       } 
/*     */     }
/*     */     
/* 141 */     SubConfigurationWrapper userConfig = new SubConfigurationWrapper((Configuration)ApplicationContext.getInstance(), "frame.ws.e5.security.usr.");
/* 142 */     SubConfigurationWrapper pwdConfig = new SubConfigurationWrapper((Configuration)ApplicationContext.getInstance(), "frame.ws.e5.security.pwd.");
/* 143 */     if (userConfig != null) {
/* 144 */       Iterator<String> it = userConfig.getKeys().iterator();
/* 145 */       while (it.hasNext()) {
/* 146 */         String ndx = it.next();
/* 147 */         String name = userConfig.getProperty(ndx);
/* 148 */         String pwd = pwdConfig.getProperty(ndx);
/* 149 */         if (name.length() > 0 && pwd != null) {
/* 150 */           authorizedMap.put(name, pwd);
/*     */         }
/*     */       } 
/*     */     } 
/* 154 */     String toleranceStr = ApplicationContext.getInstance().getProperty("frame.ws.e5.security.ts.tolerance");
/*     */     try {
/* 156 */       tolerance = Long.parseLong(toleranceStr);
/* 157 */     } catch (Exception e) {
/* 158 */       log.info("Invalid E5Service token tolerance value: " + toleranceStr);
/*     */     } 
/* 160 */     log.info("E5Service security map initialized [" + authorizedMap.size() + " entries]");
/*     */     
/* 162 */     userConfig = new SubConfigurationWrapper((Configuration)ApplicationContext.getInstance(), "frame.ws.e5.security.imt.usr.");
/* 163 */     pwdConfig = new SubConfigurationWrapper((Configuration)ApplicationContext.getInstance(), "frame.ws.e5.security.imt.pwd.");
/* 164 */     if (userConfig != null) {
/* 165 */       Iterator<String> it = userConfig.getKeys().iterator();
/* 166 */       while (it.hasNext()) {
/* 167 */         String ndx = it.next();
/* 168 */         String name = userConfig.getProperty(ndx);
/* 169 */         String pwd = pwdConfig.getProperty(ndx);
/* 170 */         if (name.length() > 0 && pwd != null) {
/* 171 */           imtAuthorizedMap.put(name, pwd);
/*     */         }
/*     */       } 
/*     */     } 
/* 175 */     toleranceStr = ApplicationContext.getInstance().getProperty("frame.ws.e5.security.imt.ts.tolerance");
/*     */     try {
/* 177 */       imtTolerance = Long.parseLong(toleranceStr);
/* 178 */     } catch (Exception e) {
/* 179 */       log.info("Invalid E5Service IMT token tolerance value: " + toleranceStr);
/*     */     } 
/* 181 */     log.info("E5Service IMT security map initialized [" + imtAuthorizedMap.size() + " entries]");
/*     */     
/*     */     try {
/* 184 */       KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
/* 185 */       String ksPwd = ApplicationContext.getInstance().getProperty("frame.ws.e5.security.imt.ks.pwd");
/* 186 */       InputStream is = getClass().getResourceAsStream("/com/eoos/gm/tis2web/frame/ws/e5/server/config/.keystore-e5test");
/* 187 */       ks.load(is, ksPwd.toCharArray());
/* 188 */       Enumeration<String> en = ks.aliases();
/* 189 */       int i = 0;
/* 190 */       while (en.hasMoreElements()) {
/* 191 */         encCerts.add((new BASE64Encoder()).encode(ks.getCertificate(en.nextElement()).getEncoded()));
/* 192 */         i++;
/*     */       } 
/* 194 */       log.info("E5Service IMT security map initialized [" + i + " entries]");
/*     */     }
/* 196 */     catch (Exception e) {
/* 197 */       log.info("Could not init E5Service IMT keystore");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\e5\server\config\ConfigData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */