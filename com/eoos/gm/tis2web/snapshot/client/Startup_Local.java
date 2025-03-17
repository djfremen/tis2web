/*     */ package com.eoos.gm.tis2web.snapshot.client;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.snapshot.client.app.impl.SnapshotBridgeImpl;
/*     */ import com.eoos.gm.tis2web.snapshot.client.common.ClientAppContext;
/*     */ import com.eoos.gm.tis2web.snapshot.client.common.ClientAppContextProvider;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.spstool.impl.DTCUploadProvider;
/*     */ import com.eoos.util.Log4jUtil;
/*     */ import java.io.File;
/*     */ import org.apache.log4j.LogManager;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class Startup_Local
/*     */ {
/*  17 */   private static final Logger log = Logger.getLogger(Startup_Local.class);
/*     */   
/*     */   public static void main(String[] args) throws Exception {
/*  20 */     System.setProperty("language.id", "en_US");
/*  21 */     System.setProperty("session.id", "snapshot-local");
/*  22 */     System.setProperty("bac.code", "BACCOD78901");
/*  23 */     System.setProperty("language.native.id", "RUS");
/*  24 */     System.setProperty("task.execution.url", "http://localhost:8080/tis2web/exectask");
/*     */     
/*  26 */     System.setProperty("mail.enabled", "true");
/*  27 */     System.setProperty("installation.type", "central");
/*     */     
/*  29 */     LogManager.getLoggerRepository().resetConfiguration();
/*  30 */     File logFile = new File(System.getProperty("user.home"));
/*  31 */     logFile = new File(logFile, "snapshot");
/*  32 */     logFile.mkdirs();
/*  33 */     logFile = new File(logFile, "snapshot-client.log");
/*  34 */     Log4jUtil.attachConsoleAppender(Logger.getLogger("com.eoos.gm.tis2web.snapshot"), null);
/*  35 */     Log4jUtil.attachRollingFileAppender(Logger.getLogger("com.eoos.gm.tis2web.snapshot"), logFile);
/*     */     
/*     */     try {
/*  38 */       ClientAppContext appContext = ClientAppContextProvider.getClientAppContext();
/*  39 */       appContext.init();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  59 */       Thread th = new Thread() {
/*     */           public void run() {
/*     */             try {
/*  62 */               Startup_Local.log.info("begin to check for stored dtcs.");
/*  63 */               DTCUploadProvider.getDTCUpload().upload(null);
/*     */               
/*  65 */               Startup_Local.log.info("finish to check for stored dtcs.");
/*  66 */             } catch (Exception e) {
/*  67 */               Startup_Local.log.error(e, e);
/*     */             } 
/*     */           }
/*     */         };
/*  71 */       th.start();
/*     */       
/*     */       try {
/*  74 */         Pair[] properties = new Pair[4];
/*     */         
/*  76 */         String nativeLanguage = appContext.getNativeLanguage();
/*     */         
/*  78 */         if (nativeLanguage != null && nativeLanguage.length() != 0) {
/*  79 */           properties[0] = (Pair)new PairImpl("language", nativeLanguage);
/*     */         } else {
/*  81 */           properties[0] = (Pair)new PairImpl("language", "ENU");
/*     */         } 
/*     */         
/*  84 */         if (appContext.mailEnabled() == true) {
/*  85 */           properties[1] = (Pair)new PairImpl("email", "enabled");
/*     */         } else {
/*  87 */           properties[1] = (Pair)new PairImpl("email", "disabled");
/*     */         } 
/*     */         
/*  90 */         if (appContext.getInstallationType().compareToIgnoreCase("central") == 0) {
/*  91 */           properties[2] = (Pair)new PairImpl("dtcupload", "enabled");
/*     */         } else {
/*  93 */           properties[2] = (Pair)new PairImpl("dtcupload", "disabled");
/*     */         } 
/*     */         
/*  96 */         String tech2WinComport = appContext.getTech2WinComPort();
/*  97 */         log.debug("Tech2Win COM port: " + tech2WinComport);
/*     */         
/*  99 */         if (tech2WinComport != null && tech2WinComport.length() > 0) {
/* 100 */           properties[3] = (Pair)new PairImpl("tech2win_com_port", tech2WinComport);
/*     */         }
/*     */         
/* 103 */         if (!SnapshotBridgeImpl.getInstance().createInstance()) {
/* 104 */           throw new RuntimeException("Could not create application instance");
/*     */         }
/*     */         
/* 107 */         if (!SnapshotBridgeImpl.getInstance().setProperties(properties)) {
/* 108 */           throw new RuntimeException("Could not set application properties");
/*     */         }
/*     */         
/* 111 */         if (!SnapshotBridgeImpl.getInstance().installDTCCallback()) {
/* 112 */           throw new RuntimeException("Could not install callbacks");
/*     */         }
/*     */         
/* 115 */         if (!SnapshotBridgeImpl.getInstance().installEmailCallback()) {
/* 116 */           throw new RuntimeException("Could not install callbacks");
/*     */         }
/*     */         
/* 119 */         if (!SnapshotBridgeImpl.getInstance().startUI()) {
/* 120 */           throw new RuntimeException("Could not start ui");
/*     */         }
/*     */         
/* 123 */         if (!SnapshotBridgeImpl.getInstance().uninstallDTCCallback()) {
/* 124 */           throw new RuntimeException("Could not uninstall callbacks");
/*     */         }
/*     */         
/* 127 */         if (!SnapshotBridgeImpl.getInstance().uninstallEmailCallback()) {
/* 128 */           throw new RuntimeException("Could not uninstall callbacks");
/*     */         }
/*     */         
/* 131 */         if (!SnapshotBridgeImpl.getInstance().discardInstance()) {
/* 132 */           throw new RuntimeException("Could not create application instance");
/*     */         }
/*     */       }
/* 135 */       catch (Exception e) {
/* 136 */         log.error("could not start UI - exception:  " + e, e);
/*     */       } 
/*     */       
/* 139 */       th.join(60000L);
/* 140 */       System.exit(0);
/* 141 */     } catch (Exception e) {
/* 142 */       log.error("unable to execute client - exception: " + e, e);
/* 143 */       throw e;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\snapshot\client\Startup_Local.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */