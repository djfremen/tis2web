/*    */ package com.eoos.gm.tis2web.sps.client.tool.passthru.j2534.device.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.tool.passthru.j2534.device.J2534Device;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.passthru.j2534.device.J2534Error;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.passthru.j2534.device.J2534Version;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class J2534DeviceImpl
/*    */   implements J2534Device
/*    */ {
/* 22 */   private static Logger log = Logger.getLogger(J2534DeviceImpl.class);
/*    */   private static final String LIBRARY = "J2534Bridge";
/* 24 */   private static J2534DeviceImpl instance = null;
/*    */ 
/*    */   
/*    */   private native boolean nativeLoadLibrary(String paramString1, String paramString2);
/*    */ 
/*    */   
/*    */   private native void nativeFreeLibrary();
/*    */ 
/*    */   
/*    */   private native J2534Error nativePassThruOpen();
/*    */   
/*    */   private native J2534Error nativePassThruClose();
/*    */   
/*    */   private native J2534Error nativePassThruReadVersion(J2534Version paramJ2534Version);
/*    */   
/*    */   public static J2534Device getInstance() {
/* 40 */     synchronized (J2534DeviceImpl.class) {
/* 41 */       if (instance == null) {
/*    */         try {
/* 43 */           System.loadLibrary("J2534Bridge");
/* 44 */           instance = new J2534DeviceImpl();
/* 45 */         } catch (Exception e) {
/* 46 */           log.error("Can't load SPSToolBridge library. " + e);
/*    */         } 
/*    */       }
/* 49 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean loadLibrary(String version, String dllPath) {
/* 54 */     synchronized (J2534DeviceImpl.class) {
/* 55 */       return nativeLoadLibrary(version, dllPath);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void freeLibrary() {
/* 60 */     synchronized (J2534DeviceImpl.class) {
/* 61 */       nativeFreeLibrary();
/*    */     } 
/*    */   }
/*    */   
/*    */   public J2534Error passThruOpen() {
/* 66 */     synchronized (J2534DeviceImpl.class) {
/* 67 */       return nativePassThruOpen();
/*    */     } 
/*    */   }
/*    */   
/*    */   public J2534Error passThruClose() {
/* 72 */     synchronized (J2534DeviceImpl.class) {
/* 73 */       return nativePassThruClose();
/*    */     } 
/*    */   }
/*    */   
/*    */   public J2534Error passThruReadVersion(J2534Version version) {
/* 78 */     synchronized (J2534DeviceImpl.class) {
/* 79 */       return nativePassThruReadVersion(version);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\passthru\j2534\device\impl\J2534DeviceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */