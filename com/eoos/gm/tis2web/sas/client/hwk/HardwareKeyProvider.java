/*     */ package com.eoos.gm.tis2web.sas.client.hwk;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.gm.tis2web.frame.export.common.InvalidSessionException;
/*     */ import com.eoos.gm.tis2web.frame.export.common.hwk.exception.SystemDriverNotInstalledException;
/*     */ import com.eoos.gm.tis2web.frame.export.common.hwk.exception.UnavailableHardwareKeyException;
/*     */ import com.eoos.gm.tis2web.sas.client.system.SASClientContextProvider;
/*     */ import com.eoos.gm.tis2web.sas.common.model.HardwareKey;
/*     */ import com.eoos.gm.tis2web.sas.common.model.exception.UnprivilegedUserException;
/*     */ import com.eoos.gm.tis2web.sas.common.system.ISASServer;
/*     */ import com.eoos.gm.tis2web.sas.common.system.SASServerProvider;
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
/*     */ public class HardwareKeyProvider
/*     */ {
/*  29 */   private static final Logger log = Logger.getLogger(HardwareKeyProvider.class);
/*     */   
/*     */   private static final String LIBRARY = "HWKBridge";
/*     */   
/*  33 */   private static HardwareKeyProvider instance = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  41 */   private final Object SYNC_HWK = new Object();
/*     */   
/*  43 */   private HardwareKey hwk = null;
/*     */   private native String nativeGetHardwareKey() throws SystemDriverNotInstalledException;
/*     */   private HardwareKeyProvider() {
/*     */     try {
/*  47 */       System.loadLibrary("HWKBridge");
/*  48 */     } catch (Exception e) {
/*  49 */       log.fatal("unable to load HWKBridge library - exception: " + e);
/*  50 */       throw new ExceptionWrapper(e);
/*     */     } 
/*     */   }
/*     */   private native String nativeGetEncodedHardwareKey() throws SystemDriverNotInstalledException;
/*     */   
/*     */   public static synchronized HardwareKeyProvider getInstance() {
/*  56 */     if (instance == null) {
/*  57 */       instance = new HardwareKeyProvider();
/*     */     }
/*  59 */     return instance;
/*     */   }
/*     */   private native boolean nativeReleaseLibraries();
/*     */   public HardwareKey getHardwareKey() throws UnavailableHardwareKeyException {
/*  63 */     synchronized (this.SYNC_HWK) {
/*  64 */       if (this.hwk == null) {
/*     */         
/*  66 */         String decoded = null;
/*  67 */         String encoded = null;
/*  68 */         boolean driverNotInstalled = false;
/*     */         try {
/*  70 */           log.debug("trying to read hwk");
/*  71 */           decoded = nativeGetHardwareKey();
/*  72 */           encoded = nativeGetEncodedHardwareKey();
/*  73 */         } catch (SystemDriverNotInstalledException e) {
/*  74 */           log.debug("...failed - system driver not installed");
/*  75 */           driverNotInstalled = true;
/*     */         } 
/*  77 */         if (decoded != null) {
/*  78 */           log.debug("sucessfully read hwk");
/*  79 */           this.hwk = new HardwareKey(encoded, decoded);
/*     */         } else {
/*  81 */           log.debug("unable to read hwk, trying to get replacement (privileged users)");
/*     */           try {
/*  83 */             this.hwk = getHWKReplacement();
/*  84 */           } catch (UnprivilegedUserException e) {
/*  85 */             log.debug("...failed (unprivileged user)");
/*  86 */             if (driverNotInstalled) {
/*  87 */               throw new SystemDriverNotInstalledException();
/*     */             }
/*  89 */             throw new UnavailableHardwareKeyException();
/*     */           }
/*  91 */           catch (Exception e) {
/*  92 */             log.error("unable to retrieve hwk replacement - exception:" + e, e);
/*  93 */             throw new UnavailableHardwareKeyException();
/*     */           } 
/*     */         } 
/*     */       } 
/*  97 */       return this.hwk;
/*     */     } 
/*     */   }
/*     */   
/*     */   private HardwareKey getHWKReplacement() throws UnprivilegedUserException, InvalidSessionException {
/* 102 */     String sessionID = SASClientContextProvider.getInstance().getContext().getSessionID();
/* 103 */     ISASServer server = SASServerProvider.getInstance().getServer(sessionID);
/* 104 */     return server.getHWKReplacement();
/*     */   }
/*     */   
/*     */   protected void finalize() throws Throwable {
/*     */     try {
/* 109 */       nativeReleaseLibraries();
/*     */     } finally {
/* 111 */       super.finalize();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\client\hwk\HardwareKeyProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */