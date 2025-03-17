/*     */ package com.eoos.gm.tis2web.sps.client.starter.impl;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.client.starter.IStarter;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.Installer;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WindowsStarter
/*     */   implements IStarter
/*     */ {
/*     */   private static final String LIBRARY = "StarterBridge";
/*     */   private static final String INSTALLATION_TYPE_EXE = "EXE";
/*     */   private static final String INSTALLATION_TYPE_MSI = "MSI";
/*     */   private static final int TIMEOUT_INFINITE = -1;
/*  34 */   private static Logger log = Logger.getLogger(WindowsStarter.class);
/*     */   
/*     */   private native boolean nativeIsStartAllowed();
/*     */   
/*     */   private native String[] nativeGetAvailableCommPorts();
/*     */   
/*     */   private native String nativeGetTech2WinComPort();
/*     */   
/*     */   private native boolean nativeStartProcess(String paramString);
/*     */   
/*     */   private native boolean nativeSetEnvironment();
/*     */   
/*     */   private native Integer nativeInstall(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt);
/*     */   
/*     */   public WindowsStarter() throws SecurityException, UnsatisfiedLinkError {
/*  49 */     System.loadLibrary("StarterBridge");
/*     */   }
/*     */   
/*     */   public boolean isStartAllowed() {
/*  53 */     synchronized (WindowsStarter.class) {
/*  54 */       return nativeIsStartAllowed();
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<String> getAvailableCommPorts() {
/*  59 */     synchronized (WindowsStarter.class) {
/*  60 */       List<String> ports = null;
/*  61 */       String[] portsArray = nativeGetAvailableCommPorts();
/*  62 */       if (portsArray != null) {
/*  63 */         ports = new ArrayList<String>();
/*  64 */         for (int index = 0; index < portsArray.length; index++) {
/*  65 */           ports.add(portsArray[index]);
/*     */         }
/*     */       } 
/*  68 */       return ports;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getTech2WinComPort() {
/*  73 */     synchronized (WindowsStarter.class) {
/*  74 */       return nativeGetTech2WinComPort();
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean startProcess(String cmdLine) {
/*  79 */     synchronized (WindowsStarter.class) {
/*  80 */       return nativeStartProcess(cmdLine);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean setEnvironment() {
/*  85 */     synchronized (WindowsStarter.class) {
/*  86 */       return nativeSetEnvironment();
/*     */     } 
/*     */   }
/*     */   
/*     */   public Integer install(File installationPath, Installer installer) {
/*  91 */     synchronized (WindowsStarter.class) {
/*  92 */       Integer retCode = null;
/*     */       
/*  94 */       String path = installationPath.getPath();
/*  95 */       String fileName = installer.getFilename();
/*  96 */       String cmdLineParams = installer.getCmdLineParams();
/*     */       
/*  98 */       if (path == null) {
/*  99 */         path = "";
/*     */       }
/* 101 */       if (fileName == null) {
/* 102 */         fileName = "";
/*     */       }
/* 104 */       if (cmdLineParams == null) {
/* 105 */         cmdLineParams = "";
/*     */       }
/*     */       
/* 108 */       if (installer.getType() == Installer.TYPE_EXE) {
/* 109 */         log.info("Installation type: EXE Path: " + path + " Installer executable: " + fileName + " Installer parameters: " + cmdLineParams + " Timeout: " + -1);
/* 110 */         retCode = nativeInstall("EXE", path, fileName, cmdLineParams, -1);
/* 111 */       } else if (installer.getType() == Installer.TYPE_MSI) {
/* 112 */         log.info("Installation type: MSI Path: " + path + " Installer executable: " + fileName + " Installer parameters: " + cmdLineParams + " Timeout: " + -1);
/* 113 */         retCode = nativeInstall("MSI", path, fileName, cmdLineParams, -1);
/*     */       } else {
/* 115 */         log.error("Unknown installer type: " + installer.getType());
/* 116 */         retCode = null;
/*     */       } 
/* 118 */       return retCode;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\starter\impl\WindowsStarter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */