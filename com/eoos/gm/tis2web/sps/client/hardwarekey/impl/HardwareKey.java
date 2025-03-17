/*    */ package com.eoos.gm.tis2web.sps.client.hardwarekey.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.hwk.exception.SystemDriverNotInstalledException;
/*    */ import com.eoos.gm.tis2web.sps.client.hardwarekey.IHardwareKey;
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
/*    */ public class HardwareKey
/*    */   implements IHardwareKey
/*    */ {
/*    */   private static final String LIBRARY = "HWKBridge";
/* 22 */   private static HardwareKey instance = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 32 */   private static Logger log = Logger.getLogger(HardwareKey.class);
/*    */   private native String nativeGetHWK() throws SystemDriverNotInstalledException;
/*    */   
/*    */   private native String nativeGetEncodedHWK_16() throws SystemDriverNotInstalledException;
/*    */   
/*    */   public static IHardwareKey getInstance() {
/* 38 */     synchronized (HardwareKey.class) {
/* 39 */       if (instance == null) {
/*    */         try {
/* 41 */           System.loadLibrary("HWKBridge");
/* 42 */           instance = new HardwareKey();
/* 43 */         } catch (Exception e) {
/* 44 */           log.error("Can't load HWKBridge library. ", e);
/*    */         } 
/*    */       }
/* 47 */       return instance;
/*    */     } 
/*    */   } private native String nativeGetEncodedHWK_32() throws SystemDriverNotInstalledException;
/*    */   private native boolean nativeReleaseLibraries();
/*    */   public String getHWK() throws SystemDriverNotInstalledException {
/* 52 */     synchronized (HardwareKey.class) {
/*    */       
/* 54 */       return nativeGetHWK();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getEncodedHWK_16() throws SystemDriverNotInstalledException {
/* 63 */     synchronized (HardwareKey.class) {
/*    */       
/* 65 */       return nativeGetEncodedHWK_16();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getEncodedHWK_32() throws SystemDriverNotInstalledException {
/* 74 */     synchronized (HardwareKey.class) {
/*    */       
/* 76 */       return nativeGetEncodedHWK_32();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean releaseLibraries() {
/* 85 */     return nativeReleaseLibraries();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\hardwarekey\impl\HardwareKey.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */