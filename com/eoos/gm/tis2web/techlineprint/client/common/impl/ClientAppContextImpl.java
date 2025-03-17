/*     */ package com.eoos.gm.tis2web.techlineprint.client.common.impl;
/*     */ 
/*     */ import com.eoos.gm.tis2web.techlineprint.client.common.ClientAppContext;
/*     */ import com.eoos.gm.tis2web.techlineprint.client.common.export.exceptions.NativeSubsystemException;
/*     */ import com.eoos.gm.tis2web.techlineprint.client.starter.impl.StarterImpl;
/*     */ import com.eoos.gm.tis2web.techlineprint.client.system.LabelResourceImpl;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*  25 */   private static ClientAppContextImpl instance = null;
/*     */   private boolean isInitialized = false;
/*  27 */   private Locale locale = null;
/*  28 */   private String sessionID = null;
/*  29 */   private String nativeLan = null;
/*  30 */   private String clientVersion = "1.0.1";
/*  31 */   private List<String> devices = null;
/*  32 */   private static Logger log = Logger.getLogger(ClientAppContextImpl.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized ClientAppContext getInstance() {
/*  41 */     if (instance == null) {
/*  42 */       instance = new ClientAppContextImpl();
/*  43 */       instance.startup();
/*     */     } 
/*  45 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void startup() {
/*  53 */     if (!isStartAllowed()) {
/*  54 */       getStartupProperties();
/*  55 */       LabelResourceImpl labelResourceImpl = new LabelResourceImpl(this.locale);
/*  56 */       String message = labelResourceImpl.getMessage(this.locale, "techlineprint.second-instance");
/*  57 */       displayErrorMessage(message);
/*  58 */       System.exit(-1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void init() throws Exception {
/*  63 */     if (!this.isInitialized) {
/*  64 */       getStartupProperties();
/*     */       
/*  66 */       if (!StarterImpl.getInstance().setEnvironment()) {
/*  67 */         log.error("setEnvironment failed");
/*  68 */         throw new NativeSubsystemException();
/*     */       } 
/*  70 */       this.isInitialized = true;
/*     */     } else {
/*  72 */       log.debug("Multiple init() calls");
/*     */     } 
/*     */   }
/*     */   
/*     */   public Locale getLocale() {
/*  77 */     return this.locale;
/*     */   }
/*     */   
/*     */   public String getClientVersion() {
/*  81 */     return this.clientVersion;
/*     */   }
/*     */   
/*     */   public String getSessionID() {
/*  85 */     return this.sessionID;
/*     */   }
/*     */   
/*     */   public String getNativeLan() {
/*  89 */     return this.nativeLan;
/*     */   }
/*     */   
/*     */   public String getHomeDir() {
/*  93 */     return System.getProperty("user.home") + System.getProperty("file.separator") + "techlineprint";
/*     */   }
/*     */   
/*     */   public List<String> getSupportedDevices() {
/*  97 */     return new ArrayList<String>(this.devices);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void getStartupProperties() {
/* 104 */     StartupPropertiesImpl stProp = new StartupPropertiesImpl();
/* 105 */     this.locale = stProp.getLocale();
/* 106 */     this.sessionID = stProp.getSessionID();
/* 107 */     this.nativeLan = stProp.getNativeLan();
/* 108 */     this.devices = stProp.getSupportedDevices();
/* 109 */     log.info("Locale: " + this.locale);
/* 110 */     log.info("SessionID: " + this.sessionID);
/* 111 */     log.info("Native Language Acronym: " + this.nativeLan);
/* 112 */     log.info("Supported devices: " + this.devices.toString());
/*     */   }
/*     */   
/*     */   public boolean isStartAllowed() {
/* 116 */     boolean result = true;
/*     */     try {
/* 118 */       result = StarterImpl.getInstance().isStartAllowed();
/* 119 */     } catch (Exception e) {
/* 120 */       log.error("Cannot check for 1st instance. Continuing startup process.");
/*     */     } 
/* 122 */     log.debug("Single instance check returned: " + result);
/* 123 */     return result;
/*     */   }
/*     */   
/*     */   public String getTech2WinComPort() {
/*     */     try {
/* 128 */       return StarterImpl.getInstance().getTech2WinComPort();
/* 129 */     } catch (Exception e) {
/* 130 */       log.error("Could not get Tech2Win COM port");
/*     */       
/* 132 */       return null;
/*     */     } 
/*     */   }
/*     */   private void displayErrorMessage(String msg) {
/*     */     try {
/* 137 */       JOptionPane.showMessageDialog(null, msg, "Exception", 0);
/* 138 */     } catch (Exception except) {
/* 139 */       log.error("unable to display error message, -exception: " + except.getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\techlineprint\client\common\impl\ClientAppContextImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */