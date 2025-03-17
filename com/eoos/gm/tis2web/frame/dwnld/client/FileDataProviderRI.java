/*     */ package com.eoos.gm.tis2web.frame.dwnld.client;
/*     */ 
/*     */ import com.eoos.gm.tis2web.common.MissingAuthenticationException;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.CookieWrapper;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.DownloadFile;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.IDownloadServer;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.scsm.v2.io.StreamUtil;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class FileDataProviderRI
/*     */   implements FileDataProvider
/*     */ {
/*  23 */   private static final Logger log = Logger.getLogger(FileDataProviderRI.class);
/*     */   
/*     */   private static final String FILE_SUFFIX = ".dat";
/*     */   
/*  27 */   private static Map serverToInstance = new HashMap<Object, Object>();
/*     */   private IDownloadServer downloadServer;
/*     */   private ServerFacade serverFacade;
/*     */   
/*     */   public static final class ExistingInstanceException
/*     */     extends Exception
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     private ExistingInstanceException() {}
/*     */   }
/*     */   
/*     */   public static final class MissingInstanceException
/*     */     extends Exception
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     private MissingInstanceException() {}
/*     */     
/*     */     public FileDataProviderRI create(IDownloadServer downloadServer, Configuration configuration, ServerFacade serverFacade) throws FileDataProviderRI.ExistingInstanceException {
/*  47 */       synchronized (FileDataProviderRI.class) {
/*  48 */         FileDataProviderRI instance = null;
/*     */         try {
/*  50 */           FileDataProviderRI.getInstance(downloadServer);
/*  51 */           throw new FileDataProviderRI.ExistingInstanceException();
/*  52 */         } catch (MissingInstanceException e) {
/*  53 */           instance = new FileDataProviderRI(downloadServer, configuration, serverFacade);
/*  54 */           FileDataProviderRI.serverToInstance.put(downloadServer, instance);
/*  55 */           return instance;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   private CookieWrapper token = null;
/*     */   
/*  68 */   private final Object SYNC_TOKEN = new Object();
/*     */   
/*     */   private FileDataProviderRI(IDownloadServer downloadServer, Configuration configuration, ServerFacade serverFacade) {
/*  71 */     if (downloadServer == null) {
/*  72 */       throw new IllegalArgumentException("no server specified");
/*     */     }
/*  74 */     this.serverFacade = serverFacade;
/*     */     
/*  76 */     log.debug("initializing for server: " + downloadServer);
/*  77 */     this.downloadServer = downloadServer;
/*     */     
/*  79 */     log.debug("...done initializing");
/*     */   }
/*     */   
/*     */   public static synchronized FileDataProviderRI getInstance(IDownloadServer downloadServer) throws MissingInstanceException {
/*  83 */     FileDataProviderRI instance = (FileDataProviderRI)serverToInstance.get(downloadServer);
/*  84 */     if (instance == null) {
/*  85 */       throw new MissingInstanceException();
/*     */     }
/*  87 */     return instance;
/*     */   }
/*     */   
/*     */   public void getData(DownloadFile file, OutputStream os) throws IOException, MissingAuthenticationException {
/*  91 */     log.debug("retrieving data for file: " + file);
/*     */     
/*  93 */     String path = this.downloadServer.getURL().getPath() + "/" + file.getIdentifier() + ".dat";
/*  94 */     log.debug("...full request path: " + path);
/*     */     
/*  96 */     URL url = new URL(this.downloadServer.getURL().toString() + "/" + file.getIdentifier() + ".dat");
/*  97 */     HttpURLConnection connection = (HttpURLConnection)url.openConnection();
/*     */     try {
/*  99 */       connection.setAllowUserInteraction(true);
/* 100 */       connection.setUseCaches(false);
/* 101 */       connection.setRequestMethod("GET");
/*     */       
/* 103 */       if (this.downloadServer.isAkamai()) {
/* 104 */         String cookie = getAkamaiCookie();
/* 105 */         log.debug("...using akamai cookie: " + String.valueOf(cookie));
/* 106 */         connection.setRequestProperty("Cookie", cookie);
/*     */       } 
/*     */       
/* 109 */       int httpStatus = connection.getResponseCode();
/*     */       
/* 111 */       log.debug("...received status: " + httpStatus);
/* 112 */       if (httpStatus != 200) {
/* 113 */         throw new IOException("unhandled http status code: " + httpStatus);
/*     */       }
/*     */       
/* 116 */       log.debug("...reading response");
/* 117 */       InputStream is = new BufferedInputStream(connection.getInputStream());
/*     */       try {
/* 119 */         if (file.gzipCompressed()) {
/* 120 */           is = new GZIPInputStream(is, 50000);
/*     */         }
/* 122 */         StreamUtil.transfer(is, os, 10000);
/*     */       } finally {
/*     */         
/* 125 */         StreamUtil.close(is, log);
/* 126 */         log.debug("...closed input stream");
/*     */       } 
/*     */     } finally {
/*     */       
/* 130 */       connection.disconnect();
/*     */     } 
/* 132 */     log.debug("...done retrieving data");
/*     */   }
/*     */ 
/*     */   
/*     */   private String getAkamaiCookie() throws MissingAuthenticationException {
/* 137 */     synchronized (this.SYNC_TOKEN) {
/* 138 */       if (this.token == null || this.token.expirationDate <= System.currentTimeMillis()) {
/* 139 */         log.debug("missing or expired akamai cookie, requesting new one...");
/* 140 */         this.token = this.serverFacade.getAkamaiCookie();
/* 141 */         log.debug("...received token : " + String.valueOf(this.token));
/*     */       } 
/* 143 */       return this.token.cookie;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\client\FileDataProviderRI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */