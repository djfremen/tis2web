/*     */ package com.eoos.gm.tis2web.snapshot.client.app.impl;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.gm.tis2web.snapshot.client.app.DTCCallback;
/*     */ import com.eoos.gm.tis2web.snapshot.client.app.EmailCallback;
/*     */ import com.eoos.gm.tis2web.snapshot.client.app.SnapshotBridge;
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
/*     */ public class SnapshotBridgeImpl
/*     */   implements SnapshotBridge
/*     */ {
/*  24 */   private static final Logger log = Logger.getLogger(SnapshotBridgeImpl.class);
/*     */   
/*     */   private static final String LIBRARY = "SnapshotBridge";
/*     */   
/*     */   private static final String APPLICATION_NAME = "snapshot";
/*     */   
/*     */   private static final String TOOL_NAME = "tech2";
/*     */   
/*  32 */   private static SnapshotBridgeImpl instance = null;
/*     */   
/*     */   private native boolean nativeCreateInstance();
/*     */   
/*     */   private native boolean nativeDiscardInstance();
/*     */   
/*     */   private native boolean nativeSetProperties(Pair[] paramArrayOfPair);
/*     */   
/*     */   private native boolean nativeInstallDTCCallback(DTCCallback paramDTCCallback);
/*     */   
/*     */   private native boolean nativeUninstallDTCCallback();
/*     */   
/*     */   private native boolean nativeInstallEmailCallback(EmailCallback paramEmailCallback);
/*     */   
/*     */   private native boolean nativeUninstallEmailCallback();
/*     */   
/*     */   private native boolean nativeStartUI(String paramString1, String paramString2);
/*     */   
/*     */   private SnapshotBridgeImpl() {
/*     */     try {
/*  52 */       System.loadLibrary("SnapshotBridge");
/*  53 */     } catch (Exception e) {
/*  54 */       log.fatal("Unable to load SnapshotBridge library - exception:" + e);
/*  55 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static SnapshotBridge getInstance() {
/*  60 */     synchronized (SnapshotBridgeImpl.class) {
/*  61 */       if (instance == null) {
/*  62 */         instance = new SnapshotBridgeImpl();
/*     */       }
/*  64 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean createInstance() {
/*  69 */     synchronized (SnapshotBridgeImpl.class) {
/*  70 */       log.info("Create application instance");
/*  71 */       boolean ret = nativeCreateInstance();
/*  72 */       log.info("Create application instance: " + ((ret == true) ? "OK" : "FAILED"));
/*  73 */       return ret;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean discardInstance() {
/*  78 */     synchronized (SnapshotBridgeImpl.class) {
/*  79 */       log.info("Discard application instance");
/*  80 */       boolean ret = nativeDiscardInstance();
/*  81 */       log.info("Discard application instance: " + ((ret == true) ? "OK" : "FAILED"));
/*  82 */       return ret;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean setProperties(Pair[] properties) {
/*  87 */     synchronized (SnapshotBridgeImpl.class) {
/*  88 */       boolean ret = false;
/*  89 */       if (properties != null) {
/*  90 */         if (properties.length != 0) {
/*  91 */           for (int i = 0; i < properties.length; i++) {
/*  92 */             log.info("Set property: " + (String)properties[i].getFirst() + " = " + (String)properties[i].getSecond());
/*     */           }
/*  94 */           if (nativeSetProperties(properties) == true) {
/*  95 */             log.info("Set application properties - OK");
/*  96 */             ret = true;
/*     */           } else {
/*  98 */             log.error("Could not set application properties");
/*  99 */             ret = false;
/*     */           } 
/*     */         } 
/*     */       } else {
/* 103 */         log.error("Invalid properties array (null pointer)");
/*     */       } 
/* 105 */       return ret;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean installDTCCallback() {
/* 110 */     synchronized (SnapshotBridgeImpl.class) {
/* 111 */       if (nativeInstallDTCCallback(DTCCallbackImpl.getInstance()) == true) {
/* 112 */         log.info("DTC callback: installed");
/* 113 */         return true;
/*     */       } 
/* 115 */       log.error("Could not install DTC callback");
/* 116 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean installEmailCallback() {
/* 122 */     synchronized (SnapshotBridgeImpl.class) {
/* 123 */       if (nativeInstallEmailCallback(EmailCallbackImpl.getInstance()) == true) {
/* 124 */         log.info("E-Mail callback: installed");
/* 125 */         return true;
/*     */       } 
/* 127 */       log.error("Could not install e-mail callback");
/* 128 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean uninstallDTCCallback() {
/* 134 */     synchronized (SnapshotBridgeImpl.class) {
/* 135 */       if (nativeUninstallDTCCallback() == true) {
/* 136 */         log.error("DTC callback: uninstalled");
/* 137 */         return true;
/*     */       } 
/* 139 */       log.error("Could not uninstall DTC callback");
/* 140 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean uninstallEmailCallback() {
/* 146 */     synchronized (SnapshotBridgeImpl.class) {
/* 147 */       if (nativeUninstallEmailCallback() == true) {
/* 148 */         log.error("E-Mail callback: uninstalled");
/* 149 */         return true;
/*     */       } 
/* 151 */       log.error("Could not uninstall e-mail callback");
/* 152 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean startUI() {
/* 158 */     synchronized (SnapshotBridgeImpl.class) {
/* 159 */       log.info("StartUI");
/* 160 */       boolean ret = nativeStartUI("snapshot", "tech2");
/* 161 */       log.info("StartUI exited with: " + ret);
/* 162 */       return ret;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\snapshot\client\app\impl\SnapshotBridgeImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */