/*     */ package com.eoos.gm.tis2web.tech2view.client.common.impl;
/*     */ 
/*     */ import com.eoos.gm.tis2web.tech2view.client.common.ClientAppContext;
/*     */ import com.eoos.gm.tis2web.tech2view.client.common.export.exceptions.NativeSubsystemException;
/*     */ import com.eoos.gm.tis2web.tech2view.client.starter.impl.StarterImpl;
/*     */ import com.eoos.gm.tis2web.tech2view.client.system.LabelResourceImpl;
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
/*     */   private boolean isInitialized = false;
/*  25 */   private Locale locale = null;
/*  26 */   private String sessionID = null;
/*  27 */   private String nativeLan = null;
/*  28 */   private String clientVersion = "1.0.1";
/*  29 */   private static Logger log = Logger.getLogger(ClientAppContextImpl.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized ClientAppContext getInstance() {
/*  38 */     if (instance == null) {
/*  39 */       instance = new ClientAppContextImpl();
/*  40 */       instance.startup();
/*     */     } 
/*  42 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void startup() {
/*  50 */     if (!isStartAllowed()) {
/*  51 */       getStartupProperties();
/*  52 */       LabelResourceImpl labelResourceImpl = new LabelResourceImpl(this.locale);
/*  53 */       String message = labelResourceImpl.getMessage(this.locale, "tech2view.second-instance");
/*  54 */       displayErrorMessage(message);
/*  55 */       System.exit(-1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void init() throws Exception {
/*  60 */     if (!this.isInitialized) {
/*  61 */       getStartupProperties();
/*     */       
/*  63 */       if (!StarterImpl.getInstance().setEnvironment()) {
/*  64 */         log.error("setEnvironment failed");
/*  65 */         throw new NativeSubsystemException();
/*     */       } 
/*  67 */       this.isInitialized = true;
/*     */     } else {
/*  69 */       log.debug("Multiple init() calls");
/*     */     } 
/*     */   }
/*     */   
/*     */   public Locale getLocale() {
/*  74 */     return this.locale;
/*     */   }
/*     */   
/*     */   public String getClientVersion() {
/*  78 */     return this.clientVersion;
/*     */   }
/*     */   
/*     */   public String getSessionID() {
/*  82 */     return this.sessionID;
/*     */   }
/*     */   
/*     */   public String getNativeLan() {
/*  86 */     return this.nativeLan;
/*     */   }
/*     */   
/*     */   public String getHomeDir() {
/*  90 */     return System.getProperty("user.home") + System.getProperty("file.separator") + "tech2view";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void getStartupProperties() {
/*  97 */     StartupPropertiesImpl stProp = new StartupPropertiesImpl();
/*  98 */     this.locale = stProp.getLocale();
/*  99 */     this.sessionID = stProp.getSessionID();
/* 100 */     this.nativeLan = stProp.getNativeLan();
/* 101 */     log.info("Locale: " + this.locale);
/* 102 */     log.info("SessionID: " + this.sessionID);
/* 103 */     log.info("Native Language Acronym: " + this.nativeLan);
/*     */   }
/*     */   
/*     */   public boolean isStartAllowed() {
/* 107 */     boolean result = true;
/*     */     try {
/* 109 */       result = StarterImpl.getInstance().isStartAllowed();
/* 110 */     } catch (Exception e) {
/* 111 */       log.error("Cannot check for 1st instance. Continuing startup process.");
/*     */     } 
/* 113 */     log.debug("Single instance check returned: " + result);
/* 114 */     return result;
/*     */   }
/*     */   
/*     */   public String getTech2WinComPort() {
/*     */     try {
/* 119 */       return StarterImpl.getInstance().getTech2WinComPort();
/* 120 */     } catch (Exception e) {
/* 121 */       log.error("Could not get Tech2Win COM port");
/*     */       
/* 123 */       return null;
/*     */     } 
/*     */   }
/*     */   private void displayErrorMessage(String msg) {
/*     */     try {
/* 128 */       JOptionPane.showMessageDialog(null, msg, "Exception", 0);
/* 129 */     } catch (Exception except) {
/* 130 */       log.error("unable to display error message, -exception: " + except.getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\tech2view\client\common\impl\ClientAppContextImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */