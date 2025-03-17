/*     */ package com.eoos.gm.tis2web.swdl.client.driver;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.gm.tis2web.swdl.client.ctrl.SDFileSel;
/*     */ import com.eoos.gm.tis2web.swdl.client.model.ApplicationInfo;
/*     */ import com.eoos.gm.tis2web.swdl.client.model.DeviceInfo;
/*     */ import com.eoos.gm.tis2web.swdl.client.model.SDFileInfo;
/*     */ import com.eoos.gm.tis2web.swdl.client.msg.SDNotifHandler;
/*     */ import com.eoos.scsm.v2.util.StringUtilities;
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
/*     */ public class TechDriver
/*     */   implements SDDriver
/*     */ {
/*  32 */   private static Logger log = Logger.getLogger(TechDriver.class);
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
/*  52 */   private static TechDriver instance = null;
/*     */   
/*  54 */   private static Object discardInstanceLock = new Object(); private static final String LIBRARY_NAME = "swdlbridge";
/*     */   private native boolean nativeSetInitData(String paramString);
/*     */   private native void nativeSelectDriver(String paramString);
/*     */   private native void nativeDiscardDriver();
/*     */   
/*     */   private native DeviceInfo nativeGetDeviceContent();
/*     */   
/*     */   public static SDDriver getInstance() {
/*  62 */     synchronized (TechDriver.class) {
/*  63 */       if (instance == null) {
/*     */         try {
/*  65 */           System.loadLibrary("swdlbridge");
/*  66 */           instance = new TechDriver();
/*  67 */         } catch (UnsatisfiedLinkError e) {
/*  68 */           log.error("Could not load library: " + e);
/*  69 */         } catch (Exception e) {
/*  70 */           log.error("Could not create instance: " + e);
/*     */         } 
/*     */       }
/*  73 */       return instance;
/*     */     } 
/*     */   } private native byte[] nativeDTCUpload(String paramString); private native boolean nativeDownload(ApplicationInfo paramApplicationInfo, SDFileInfo[] paramArrayOfSDFileInfo, SDFileSel paramSDFileSel, SDNotifHandler paramSDNotifHandler, boolean paramBoolean); private native int nativeTestDeviceDriver(String paramString); private native boolean nativeSetDriverProperties(Pair[] paramArrayOfPair);
/*     */   private native void nativeAbortDeviceCommunication();
/*     */   public static void discardInstance() {
/*  78 */     synchronized (discardInstanceLock) {
/*  79 */       if (instance != null) {
/*  80 */         instance.discardDriver();
/*  81 */         instance = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void abortDeviceCommunication() {
/*  87 */     synchronized (discardInstanceLock) {
/*  88 */       if (instance != null) {
/*  89 */         instance.nativeAbortDeviceCommunication();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void selectDriver(String devName) {
/*  95 */     synchronized (TechDriver.class) {
/*  96 */       nativeSelectDriver(StringUtilities.removeWhitespace(devName));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void discardDriver() {
/* 101 */     synchronized (discardInstanceLock) {
/* 102 */       nativeDiscardDriver();
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean initDriver(String initParams) {
/* 107 */     synchronized (TechDriver.class) {
/* 108 */       return nativeSetInitData(initParams);
/*     */     } 
/*     */   }
/*     */   
/*     */   public DeviceInfo getDeviceContent() {
/* 113 */     synchronized (TechDriver.class) {
/* 114 */       return nativeGetDeviceContent();
/*     */     } 
/*     */   }
/*     */   
/*     */   public byte[] dtcUpload(String dealerCode) {
/* 119 */     synchronized (TechDriver.class) {
/* 120 */       return nativeDTCUpload(dealerCode);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean download(ApplicationInfo appInfo, SDFileInfo[] filesInfo, SDFileSel fileSelector, SDNotifHandler handler, boolean erase) {
/* 125 */     synchronized (TechDriver.class) {
/* 126 */       return nativeDownload(appInfo, filesInfo, fileSelector, handler, erase);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int testDeviceDriver(String initParams) {
/* 131 */     synchronized (TechDriver.class) {
/* 132 */       return nativeTestDeviceDriver(initParams);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean setDriverProperties(Pair[] properties) {
/* 137 */     synchronized (TechDriver.class) {
/* 138 */       return nativeSetDriverProperties(properties);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\client\driver\TechDriver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */