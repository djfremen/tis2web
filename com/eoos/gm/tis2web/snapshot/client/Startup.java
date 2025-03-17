/*     */ package com.eoos.gm.tis2web.snapshot.client;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.SoftwareKeyCheckClient;
/*     */ import com.eoos.gm.tis2web.snapshot.client.app.impl.DTCUploadProvider;
/*     */ import com.eoos.gm.tis2web.snapshot.client.app.impl.SnapshotBridgeImpl;
/*     */ import com.eoos.gm.tis2web.snapshot.client.common.ClientAppContext;
/*     */ import com.eoos.gm.tis2web.snapshot.client.common.ClientAppContextProvider;
/*     */ import com.eoos.util.Log4jUtil;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.LogManager;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class Startup
/*     */ {
/*  20 */   private static final Logger log = Logger.getLogger(Startup.class);
/*     */   
/*  22 */   private static String APP_PROPERTY_LANGUAGE = "language";
/*     */   
/*  24 */   private static String APP_PROPERTY_EMAIL = "email";
/*     */   
/*  26 */   private static String APP_PROPERTY_DTCUPLOAD = "dtcupload";
/*     */   
/*  28 */   private static String APP_PROPERTY_ENABLED = "enabled";
/*     */   
/*  30 */   private static String APP_PROPERTY_DISABLED = "disabled";
/*     */   
/*  32 */   private static String APP_PROPERTY_TECH2WIN_COM_PORT = "tech2win_com_port";
/*     */   
/*  34 */   private static String DEFAULT_LANGUAGE = "ENU";
/*     */ 
/*     */   
/*     */   private static Pair[] getApplicationProperties() {
/*  38 */     List<Pair> properties = new ArrayList<Pair>();
/*     */     
/*  40 */     ClientAppContext appContext = ClientAppContextProvider.getClientAppContext();
/*  41 */     String nativeLanguage = appContext.getNativeLanguage();
/*     */     
/*  43 */     if (nativeLanguage != null && nativeLanguage.length() != 0) {
/*  44 */       properties.add(new PairImpl(APP_PROPERTY_LANGUAGE, nativeLanguage));
/*     */     } else {
/*  46 */       properties.add(new PairImpl(APP_PROPERTY_LANGUAGE, DEFAULT_LANGUAGE));
/*     */     } 
/*     */     
/*  49 */     if (appContext.mailEnabled() == true) {
/*  50 */       properties.add(new PairImpl(APP_PROPERTY_EMAIL, APP_PROPERTY_ENABLED));
/*     */     } else {
/*  52 */       properties.add(new PairImpl(APP_PROPERTY_EMAIL, APP_PROPERTY_DISABLED));
/*     */     } 
/*     */     
/*  55 */     if (appContext.dtcUploadEnabled()) {
/*  56 */       properties.add(new PairImpl(APP_PROPERTY_DTCUPLOAD, APP_PROPERTY_ENABLED));
/*     */     } else {
/*  58 */       log.debug("disabling dtc upload");
/*  59 */       properties.add(new PairImpl(APP_PROPERTY_DTCUPLOAD, APP_PROPERTY_DISABLED));
/*     */     } 
/*     */     
/*  62 */     String tech2WinComport = appContext.getTech2WinComPort();
/*  63 */     log.debug("Tech2Win COM port: " + tech2WinComport);
/*     */     
/*  65 */     if (tech2WinComport != null && tech2WinComport.length() > 0) {
/*  66 */       properties.add(new PairImpl(APP_PROPERTY_TECH2WIN_COM_PORT, tech2WinComport));
/*     */     }
/*     */     
/*  69 */     return properties.<Pair>toArray(new Pair[properties.size()]);
/*     */   }
/*     */   
/*     */   public static void main(String[] args) throws Exception {
/*  73 */     LogManager.getLoggerRepository().resetConfiguration();
/*  74 */     File logFile = new File(System.getProperty("user.home"));
/*  75 */     logFile = new File(logFile, "snapshot");
/*  76 */     logFile.mkdirs();
/*  77 */     logFile = new File(logFile, "snapshot-client.log");
/*  78 */     Log4jUtil.attachConsoleAppender(Logger.getLogger("com.eoos.gm.tis2web.snapshot"), null);
/*  79 */     Log4jUtil.attachRollingFileAppender(Logger.getLogger("com.eoos.gm.tis2web.snapshot"), logFile);
/*     */     
/*     */     try {
/*  82 */       ClientAppContext appContext = ClientAppContextProvider.getClientAppContext();
/*  83 */       appContext.init();
/*     */ 
/*     */       
/*  86 */       if (SoftwareKeyCheckClient.checkSoftwareKey("com.eoos.gm.tis2web.snapshot.client.common.message") == 0) {
/*     */         
/*  88 */         Thread th = new Thread() {
/*     */             public void run() {
/*     */               try {
/*  91 */                 Startup.log.info("begin to check for stored dtcs.");
/*  92 */                 DTCUploadProvider.getDTCUpload().upload(null);
/*     */                 
/*  94 */                 Startup.log.info("finish to check for stored dtcs.");
/*  95 */               } catch (Exception e) {
/*  96 */                 Startup.log.error(e, e);
/*     */               } 
/*     */             }
/*     */           };
/* 100 */         th.start();
/*     */         
/*     */         try {
/* 103 */           if (!SnapshotBridgeImpl.getInstance().createInstance()) {
/* 104 */             throw new RuntimeException("Could not create application instance");
/*     */           }
/*     */           
/* 107 */           if (!SnapshotBridgeImpl.getInstance().setProperties(getApplicationProperties())) {
/* 108 */             throw new RuntimeException("Could not set application properties");
/*     */           }
/*     */           
/* 111 */           if (!SnapshotBridgeImpl.getInstance().installDTCCallback()) {
/* 112 */             throw new RuntimeException("Could not install callbacks");
/*     */           }
/*     */           
/* 115 */           if (!SnapshotBridgeImpl.getInstance().installEmailCallback()) {
/* 116 */             throw new RuntimeException("Could not install callbacks");
/*     */           }
/*     */           
/* 119 */           if (!SnapshotBridgeImpl.getInstance().startUI()) {
/* 120 */             throw new RuntimeException("Could not start ui");
/*     */           }
/*     */           
/* 123 */           if (!SnapshotBridgeImpl.getInstance().uninstallDTCCallback()) {
/* 124 */             throw new RuntimeException("Could not uninstall callbacks");
/*     */           }
/*     */           
/* 127 */           if (!SnapshotBridgeImpl.getInstance().uninstallEmailCallback()) {
/* 128 */             throw new RuntimeException("Could not uninstall callbacks");
/*     */           }
/*     */           
/* 131 */           if (!SnapshotBridgeImpl.getInstance().discardInstance()) {
/* 132 */             throw new RuntimeException("Could not create application instance");
/*     */           }
/* 134 */         } catch (Exception e) {
/* 135 */           log.error("could not start UI - exception:  " + e, e);
/*     */         } 
/*     */         
/* 138 */         th.join(60000L);
/*     */       } 
/* 140 */     } catch (Exception e) {
/* 141 */       log.error("Unable to execute client - exception: " + e, e);
/* 142 */       throw e;
/*     */     } finally {
/* 144 */       System.exit(0);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\snapshot\client\Startup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */