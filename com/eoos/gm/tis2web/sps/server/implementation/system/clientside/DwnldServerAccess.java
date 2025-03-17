/*    */ package com.eoos.gm.tis2web.sps.server.implementation.system.clientside;
/*    */ 
/*    */ import com.eoos.gm.tis2web.common.MissingAuthenticationException;
/*    */ import com.eoos.gm.tis2web.frame.dwnld.common.CookieWrapper;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.DownloadServer;
/*    */ import com.eoos.scsm.v2.io.StreamUtil;
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.net.HttpURLConnection;
/*    */ import java.net.URL;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class DwnldServerAccess
/*    */ {
/* 19 */   private static final Logger log = Logger.getLogger(DwnldServerAccess.class);
/*    */ 
/*    */ 
/*    */   
/*    */   private Callback callback;
/*    */ 
/*    */ 
/*    */   
/* 27 */   private CookieWrapper token = null;
/*    */   
/* 29 */   private final Object SYNC_TOKEN = new Object();
/*    */   
/*    */   public DwnldServerAccess(Callback serverFacade) {
/* 32 */     this.callback = serverFacade;
/*    */   }
/*    */   
/*    */   public byte[] getData(DownloadServer server, String filename) throws IOException {
/* 36 */     ByteArrayOutputStream os = new ByteArrayOutputStream();
/*    */     try {
/* 38 */       getData(server, filename, os);
/*    */     } finally {
/*    */       
/* 41 */       os.close();
/*    */     } 
/* 43 */     return os.toByteArray();
/*    */   }
/*    */   
/*    */   public void getData(DownloadServer server, String filename, OutputStream os) throws IOException {
/* 47 */     log.debug("retrieving data for file: " + filename);
/*    */     
/* 49 */     String path = server.getURL().getPath() + "/" + filename;
/* 50 */     log.debug("...full request path: " + path);
/*    */     
/* 52 */     URL url = new URL(server.getURL().toString() + "/" + filename);
/* 53 */     HttpURLConnection connection = (HttpURLConnection)url.openConnection();
/*    */     try {
/* 55 */       connection.setAllowUserInteraction(true);
/* 56 */       connection.setUseCaches(false);
/* 57 */       connection.setRequestMethod("GET");
/*    */       
/* 59 */       if (server.isSecured()) {
/* 60 */         String cookie = getAkamaiCookie();
/* 61 */         log.debug("...using akamai cookie");
/* 62 */         connection.setRequestProperty("Cookie", cookie);
/*    */       } 
/* 64 */       int httpStatus = connection.getResponseCode();
/*    */       
/* 66 */       log.debug("...received status: " + httpStatus);
/* 67 */       if (httpStatus != 200) {
/* 68 */         throw new IOException("unhandled http status code: " + httpStatus);
/*    */       }
/*    */       
/* 71 */       log.debug("...reading response");
/* 72 */       InputStream is = new BufferedInputStream(connection.getInputStream());
/*    */       try {
/* 74 */         StreamUtil.transfer(is, os, 10000);
/*    */       } finally {
/*    */         
/* 77 */         StreamUtil.close(is, log);
/* 78 */         log.debug("...closed input stream");
/*    */       } 
/*    */     } finally {
/*    */       
/* 82 */       connection.disconnect();
/*    */     } 
/* 84 */     log.debug("...done retrieving data");
/*    */   }
/*    */ 
/*    */   
/*    */   private String getAkamaiCookie() throws MissingAuthenticationException {
/* 89 */     synchronized (this.SYNC_TOKEN) {
/* 90 */       if (this.token == null || this.token.expirationDate <= System.currentTimeMillis()) {
/* 91 */         log.debug("missing or expired akamai cookie, requesting new one...");
/* 92 */         this.token = this.callback.getAkamaiCookie();
/* 93 */         log.debug("...received token");
/*    */       } 
/* 95 */       return this.token.cookie;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static interface Callback {
/*    */     CookieWrapper getAkamaiCookie();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\system\clientside\DwnldServerAccess.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */