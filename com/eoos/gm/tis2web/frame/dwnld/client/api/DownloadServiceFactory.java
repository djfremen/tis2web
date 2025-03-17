/*    */ package com.eoos.gm.tis2web.frame.dwnld.client.api;
/*    */ 
/*    */ import com.eoos.gm.tis2web.common.Authentication;
/*    */ import com.eoos.gm.tis2web.common.AuthenticationQuery;
/*    */ import com.eoos.gm.tis2web.common.HostInfo;
/*    */ import com.eoos.gm.tis2web.frame.dls.client.api.DLSService;
/*    */ import com.eoos.gm.tis2web.frame.dls.client.api.DLSServiceFactory;
/*    */ import com.eoos.gm.tis2web.frame.dls.client.api.Lease;
/*    */ import com.eoos.gm.tis2web.frame.dls.client.api.SoftwareKey;
/*    */ import com.eoos.gm.tis2web.frame.dwnld.client.DownloadManager;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import com.eoos.propcfg.impl.PropertiesConfigurationImpl;
/*    */ import java.io.File;
/*    */ import java.net.InetSocketAddress;
/*    */ import java.util.Properties;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DownloadServiceFactory
/*    */ {
/* 25 */   private static final Logger log = Logger.getLogger(DownloadServiceFactory.class);
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
/*    */   public static synchronized IDownloadService createInstance(File wrkDir, final IDownloadService.Callback callback) {
/* 40 */     if (callback == null) {
/* 41 */       return createInstance(wrkDir, (AuthenticationQuery)null);
/*    */     }
/* 43 */     return createInstance(wrkDir, new AuthenticationQuery()
/*    */         {
/*    */           public Authentication getAuthentication(final InetSocketAddress address, String scheme, String realm) {
/* 46 */             return callback.getProxyAuthentication(new HostInfo()
/*    */                 {
/*    */                   private static final long serialVersionUID = 1L;
/*    */                   
/*    */                   public int getPort() {
/* 51 */                     return address.getPort();
/*    */                   }
/*    */                   
/*    */                   public String getHostname() {
/* 55 */                     return address.getHostName();
/*    */                   }
/*    */                 });
/*    */           }
/*    */         });
/*    */   }
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
/*    */   public static synchronized IDownloadService createInstance(File wrkDir, AuthenticationQuery proxyAuthenticationCallback) {
/* 73 */     log.debug("creating download service...");
/* 74 */     DLSService dlsService = DLSServiceFactory.createService(proxyAuthenticationCallback);
/* 75 */     SoftwareKey swk = dlsService.getSoftwareKey();
/* 76 */     log.debug("software key " + ((swk != null) ? "found" : "not found"));
/* 77 */     Lease lease = dlsService.getNewestValidLease();
/* 78 */     return createInstance(swk, lease, wrkDir, proxyAuthenticationCallback);
/*    */   }
/*    */   
/*    */   public static synchronized IDownloadService createInstance(SoftwareKey swk, Lease lease, File wrkDir, AuthenticationQuery proxyAuthenticationCallback) {
/* 82 */     return createInstance(swk, lease, wrkDir, proxyAuthenticationCallback, System.getProperties());
/*    */   }
/*    */   
/*    */   public static synchronized IDownloadService createInstance(SoftwareKey swk, Lease lease, File wrkDir, AuthenticationQuery proxyAuthenticationCallback, Properties properties) {
/* 86 */     return (IDownloadService)new DownloadManager(swk, lease, wrkDir, proxyAuthenticationCallback, (Configuration)new PropertiesConfigurationImpl(properties));
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\client\api\DownloadServiceFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */