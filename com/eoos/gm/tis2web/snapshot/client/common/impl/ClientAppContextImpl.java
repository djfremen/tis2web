/*     */ package com.eoos.gm.tis2web.snapshot.client.common.impl;
/*     */ 
/*     */ import com.eoos.gm.tis2web.snapshot.client.common.ClientAppContext;
/*     */ import com.eoos.gm.tis2web.snapshot.client.common.export.exceptions.NativeSubsystemException;
/*     */ import com.eoos.gm.tis2web.snapshot.client.starter.impl.StarterImpl;
/*     */ import com.eoos.gm.tis2web.snapshot.client.system.LabelResourceImpl;
/*     */ import java.util.Locale;
/*     */ import javax.swing.JOptionPane;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClientAppContextImpl
/*     */   implements ClientAppContext
/*     */ {
/*  23 */   private static ClientAppContextImpl instance = null;
/*     */   
/*     */   private boolean isInitialized = false;
/*     */   
/*  27 */   private Locale locale = null;
/*     */   
/*  29 */   private String sessionID = null;
/*     */   
/*  31 */   private String bacCode = null;
/*     */   
/*  33 */   private String nativeLanguage = null;
/*     */   
/*     */   private boolean hasMail = false;
/*     */   
/*  37 */   private String installationType = "standalone";
/*     */   
/*  39 */   private String clientVersion = "1.0.1";
/*     */   
/*     */   private String countryCode;
/*     */   
/*  43 */   private static Logger log = Logger.getLogger(ClientAppContextImpl.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized ClientAppContext getInstance() {
/*  49 */     if (instance == null) {
/*  50 */       instance = new ClientAppContextImpl();
/*  51 */       instance.startup();
/*     */     } 
/*  53 */     return instance;
/*     */   }
/*     */   
/*     */   private void startup() {
/*  57 */     if (!isStartAllowed()) {
/*  58 */       getStartupProperties();
/*  59 */       LabelResourceImpl labelResourceImpl = new LabelResourceImpl(this.locale);
/*  60 */       String message = labelResourceImpl.getMessage(this.locale, "snapshot.second-instance");
/*  61 */       displayErrorMessage(message);
/*  62 */       System.exit(-1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void init() throws Exception {
/*  67 */     if (!this.isInitialized) {
/*  68 */       getStartupProperties();
/*     */       
/*  70 */       if (!StarterImpl.getInstance().setEnvironment()) {
/*  71 */         log.error("setEnvironment failed");
/*  72 */         throw new NativeSubsystemException();
/*     */       } 
/*  74 */       this.isInitialized = true;
/*     */     } else {
/*  76 */       log.debug("Multiple init() calls");
/*     */     } 
/*     */   }
/*     */   
/*     */   public Locale getLocale() {
/*  81 */     return this.locale;
/*     */   }
/*     */   
/*     */   public String getClientVersion() {
/*  85 */     return this.clientVersion;
/*     */   }
/*     */   
/*     */   public String getSessionID() {
/*  89 */     return this.sessionID;
/*     */   }
/*     */   
/*     */   public String getBACCode() {
/*  93 */     return this.bacCode;
/*     */   }
/*     */   
/*     */   public String getNativeLanguage() {
/*  97 */     return this.nativeLanguage;
/*     */   }
/*     */   
/*     */   public String getHomeDir() {
/* 101 */     return System.getProperty("user.home") + System.getProperty("file.separator") + "snapshot";
/*     */   }
/*     */   
/*     */   public boolean mailEnabled() {
/* 105 */     return this.hasMail;
/*     */   }
/*     */   
/*     */   public String getInstallationType() {
/* 109 */     return this.installationType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void getStartupProperties() {
/* 116 */     StartupPropertiesImpl stProp = new StartupPropertiesImpl();
/* 117 */     this.locale = stProp.getLocale();
/* 118 */     this.sessionID = stProp.getSessionID();
/* 119 */     this.bacCode = stProp.getBACCode();
/* 120 */     this.nativeLanguage = stProp.getNativeLan();
/* 121 */     this.hasMail = stProp.getMailConfiguration();
/* 122 */     this.installationType = stProp.getInstallationType();
/* 123 */     this.countryCode = stProp.getCountryCode();
/* 124 */     log.info("Locale: " + this.locale);
/* 125 */     log.info("SessionID: " + this.sessionID);
/* 126 */     log.info("BAC Code: " + this.bacCode);
/* 127 */     log.info("Native Language Acronym: " + this.nativeLanguage);
/* 128 */     log.info("hasMail: " + this.hasMail);
/* 129 */     log.info("InstallationType: " + this.installationType);
/* 130 */     log.info("CountryCode: " + this.countryCode);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isStartAllowed() {
/* 135 */     boolean result = true;
/*     */     try {
/* 137 */       result = StarterImpl.getInstance().isStartAllowed();
/* 138 */     } catch (Exception e) {
/* 139 */       log.error("Cannot check for 1st instance. Continuing startup process.");
/*     */     } 
/* 141 */     log.debug("Single instance check returned: " + result);
/* 142 */     return result;
/*     */   }
/*     */   
/*     */   public String getTech2WinComPort() {
/*     */     try {
/* 147 */       return StarterImpl.getInstance().getTech2WinComPort();
/* 148 */     } catch (Exception e) {
/* 149 */       log.error("Could not get Tech2Win COM port" + e);
/*     */       
/* 151 */       return null;
/*     */     } 
/*     */   }
/*     */   private void displayErrorMessage(String msg) {
/*     */     try {
/* 156 */       JOptionPane.showMessageDialog(null, msg, "Exception", 0);
/* 157 */     } catch (Exception except) {
/* 158 */       log.error("unable to display error message, -exception: " + except.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getCountryCode() {
/* 163 */     return this.countryCode;
/*     */   }
/*     */   
/*     */   public boolean dtcUploadEnabled() {
/* 167 */     return !Boolean.getBoolean("dtc.upload.disabled");
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\snapshot\client\common\impl\ClientAppContextImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */