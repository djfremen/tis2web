/*    */ package com.eoos.hardwarekey.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.hwk.exception.SystemDriverNotInstalledException;
/*    */ import com.eoos.gm.tis2web.frame.export.common.hwk.exception.UnavailableHardwareKeyException;
/*    */ import com.eoos.hardwarekey.common.HardwareKey;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HardwareKeyImpl
/*    */   implements HardwareKey
/*    */ {
/*    */   private static final String LIBRARY = "HWKBridge";
/* 28 */   private static HardwareKeyImpl instance = null;
/*    */   private native String nativeGetHardwareKey() throws SystemDriverNotInstalledException, UnavailableHardwareKeyException;
/* 30 */   public static Long currentTime = null;
/*    */   
/*    */   public static HardwareKey getInstance() {
/* 33 */     synchronized (HardwareKeyImpl.class) {
/*    */       try {
/* 35 */         System.loadLibrary("HWKBridge");
/* 36 */         if (instance == null) {
/* 37 */           instance = new HardwareKeyImpl();
/*    */         }
/* 39 */       } catch (UnsatisfiedLinkError e) {
/* 40 */         System.out.println("Could not load library" + e);
/* 41 */       } catch (Exception e) {
/* 42 */         System.out.println("Unknown exception" + e);
/*    */       } 
/* 44 */       return instance;
/*    */     } 
/*    */   }
/*    */   private native boolean nativeMigrateHardwareKey() throws SystemDriverNotInstalledException, UnavailableHardwareKeyException;
/*    */   
/*    */   private native boolean nativeIsMigratedHardwareKey() throws SystemDriverNotInstalledException, UnavailableHardwareKeyException;
/*    */   
/*    */   public String getHardwareKey() throws SystemDriverNotInstalledException, UnavailableHardwareKeyException {
/* 52 */     synchronized (HardwareKeyImpl.class) {
/* 53 */       return nativeGetHardwareKey();
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean migrateHardwareKey() throws SystemDriverNotInstalledException, UnavailableHardwareKeyException {
/* 58 */     synchronized (HardwareKeyImpl.class) {
/* 59 */       return nativeMigrateHardwareKey();
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean isMigratedHardwareKey() throws SystemDriverNotInstalledException, UnavailableHardwareKeyException {
/* 64 */     synchronized (HardwareKeyImpl.class) {
/* 65 */       return nativeIsMigratedHardwareKey();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\hardwarekey\impl\HardwareKeyImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */