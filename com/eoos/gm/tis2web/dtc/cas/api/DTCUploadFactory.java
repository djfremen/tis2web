/*     */ package com.eoos.gm.tis2web.dtc.cas.api;
/*     */ 
/*     */ import com.eoos.gm.tis2web.common.Authentication;
/*     */ import com.eoos.gm.tis2web.common.AuthenticationQuery;
/*     */ import com.eoos.gm.tis2web.common.HostInfo;
/*     */ import com.eoos.gm.tis2web.dtc.cas.DTCUpload;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public final class DTCUploadFactory
/*     */ {
/*  16 */   private static final Logger log = Logger.getLogger(DTCUploadFactory.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  21 */   public static final IDTCUpload DISABLED = new IDTCUpload()
/*     */     {
/*     */       public boolean uploadEnabled() throws IOException {
/*  24 */         return false;
/*     */       }
/*     */       
/*     */       public Identifier upload(Set data) throws SecurityException, IOException, UnavailableServiceException {
/*  28 */         throw new UnavailableServiceException();
/*     */       }
/*     */ 
/*     */       
/*     */       public void reset() {}
/*     */       
/*     */       public boolean finished(Identifier id) {
/*  35 */         return true;
/*     */       }
/*     */     };
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
/*     */   public static IDTCUpload createInstance(File wrkDir, IDTCUpload.Callback callback) {
/*  50 */     return createInstance("GDS", wrkDir, callback);
/*     */   }
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
/*     */   public static IDTCUpload createInstance(String applicationID, File wrkDir, final IDTCUpload.Callback callback) {
/*  65 */     AuthenticationQuery authQuery = null;
/*  66 */     if (callback != null) {
/*  67 */       authQuery = new AuthenticationQuery()
/*     */         {
/*     */           public Authentication getAuthentication(final InetSocketAddress address, String scheme, String realm) {
/*  70 */             return callback.getAuthentication(new HostInfo()
/*     */                 {
/*     */                   private static final long serialVersionUID = 1L;
/*     */                   
/*     */                   public int getPort() {
/*  75 */                     return address.getPort();
/*     */                   }
/*     */                   
/*     */                   public String getHostname() {
/*  79 */                     return address.getHostName();
/*     */                   }
/*     */                 });
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*  86 */     return createInstance(applicationID, wrkDir, authQuery);
/*     */   }
/*     */ 
/*     */   
/*     */   public static IDTCUpload createInstance(File wrkDir, AuthenticationQuery authenticationCallback) {
/*  91 */     return createInstance("GDS", wrkDir, authenticationCallback);
/*     */   }
/*     */   
/*  94 */   private static final File disablingFile1 = new File(System.getProperty("user.home"), ".disabledtc");
/*  95 */   private static final File disablingFile2 = new File(System.getProperty("user.dir"), ".disabledtc");
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
/*     */   public static IDTCUpload createInstance(String applicationID, File wrkDir, AuthenticationQuery authenticationCallback) {
/*     */     DTCUpload dTCUpload;
/* 109 */     IDTCUpload ret = null;
/* 110 */     log.debug("creating DTC upload service...");
/* 111 */     if (Boolean.getBoolean("dtc.upload.disabled") || disablingFile1.exists() || disablingFile2.exists()) {
/* 112 */       log.debug("...DTC upload functionality is disabled, returning disabled proxy");
/* 113 */       ret = DISABLED;
/*     */     } else {
/* 115 */       dTCUpload = new DTCUpload(applicationID, wrkDir, authenticationCallback);
/*     */     } 
/* 117 */     log.debug("...done");
/* 118 */     return (IDTCUpload)dTCUpload;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\dtc\cas\api\DTCUploadFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */