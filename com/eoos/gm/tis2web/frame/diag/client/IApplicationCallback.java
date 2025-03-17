/*     */ package com.eoos.gm.tis2web.frame.diag.client;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.diag.client.logconf.AppParamConfigurationManager;
/*     */ import com.eoos.gm.tis2web.frame.diag.client.logconf.AppParamConfigurationUtilities;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.DownloadServer;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.IDownloadServer;
/*     */ import com.eoos.scsm.v2.swing.UIUtil;
/*     */ import com.eoos.scsm.v2.util.I18NSupport;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.scsm.v2.util.progress.v2.ProgressInfo;
/*     */ import com.eoos.scsm.v2.util.progress.v2.ProgressObserver;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.math.BigDecimal;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ 
/*     */ public interface IApplicationCallback
/*     */   extends I18NSupport.FixedLocale
/*     */ {
/*  32 */   public static final IApplicationCallback TEST = new IApplicationCallback()
/*     */     {
/*     */       
/*  35 */       private AppParamConfigurationManager _AppParamConfigurationManager = null;
/*     */       
/*     */       public Locale getLocale() {
/*  38 */         return Locale.ENGLISH;
/*     */       }
/*     */       
/*     */       public void sendLogArchive(ProgressObserver progressObserver, String recipient, String sender, String subject, String body) throws Exception {
/*  42 */         Util.sleepRandom(2000L, 3000L);
/*  43 */         if (Util.createRandomBoolean()) {
/*  44 */           throw new Exception();
/*     */         }
/*     */       }
/*     */       
/*     */       public AppParamConfigurationManager getAppParamConfigurationManager() {
/*  49 */         if (this._AppParamConfigurationManager == null || !this._AppParamConfigurationManager.isPopulated()) {
/*  50 */           this._AppParamConfigurationManager = AppParamConfigurationManager.getInstance().handleConfiguration(Locale.ENGLISH);
/*     */         }
/*  52 */         return this._AppParamConfigurationManager;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private void writeLogArchive(final ProgressObserver progressObserver, FileOutputStream fos) throws DisplayException, IOException {
/*  60 */         AppParamConfigurationManager manager = getAppParamConfigurationManager();
/*     */         
/*  62 */         if (manager.getCountFileContainer() > 0) {
/*     */           
/*  64 */           String user_home = System.getProperty("user.home");
/*  65 */           String user_tmp_dir = System.getProperty("java.io.tmpdir");
/*  66 */           String sDirLogs = "\\logs_" + System.currentTimeMillis();
/*  67 */           String log_maindir = null;
/*  68 */           if (user_tmp_dir != null && user_tmp_dir.length() > 0) {
/*  69 */             log_maindir = user_tmp_dir + sDirLogs;
/*     */           } else {
/*  71 */             log_maindir = user_home + sDirLogs;
/*     */           } 
/*  73 */           String logs_home = log_maindir + "\\logs_" + System.currentTimeMillis() + "\\";
/*  74 */           File fileLogHome = new File(logs_home);
/*  75 */           fileLogHome.mkdirs();
/*  76 */           fileLogHome = new File(log_maindir);
/*     */ 
/*     */           
/*  79 */           HashMap hmpLogFileList = manager.getHmpLogFileList();
/*  80 */           for (Iterator<String> iterCont = hmpLogFileList.keySet().iterator(); iterCont.hasNext(); ) {
/*  81 */             String appName = iterCont.next();
/*  82 */             ArrayList fileList = (ArrayList)hmpLogFileList.get(appName);
/*  83 */             if (fileList != null) {
/*     */               
/*  85 */               File fileApp = new File(logs_home + appName);
/*  86 */               fileApp.mkdir();
/*  87 */               for (Iterator<String> iterFile = fileList.iterator(); iterFile.hasNext(); ) {
/*  88 */                 String fileNameWithPath = iterFile.next();
/*  89 */                 int indexDir = fileNameWithPath.lastIndexOf("\\");
/*  90 */                 String fileName = "";
/*  91 */                 if (indexDir > 0) {
/*     */ 
/*     */                   
/*  94 */                   fileName = fileNameWithPath.substring(indexDir);
/*  95 */                   AppParamConfigurationUtilities.copyAFileTo(new File(fileNameWithPath), new File(logs_home + appName + "\\" + fileName));
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 102 */           progressObserver.onProgress(new PInfo("creating.archive"));
/*     */ 
/*     */           
/* 105 */           final List logFiles = new LinkedList();
/* 106 */           Util.FileVisitor visitor = new Util.FileVisitor()
/*     */             {
/*     */               public boolean onVisit(File file) {
/* 109 */                 if (file.isFile()) {
/* 110 */                   progressObserver.onProgress(new IApplicationCallback.null.PInfo("found.log.file", ": " + file.getName()));
/* 111 */                   logFiles.add(file);
/*     */                 } 
/* 113 */                 return true;
/*     */               }
/*     */             };
/* 116 */           Util.visitFiles(fileLogHome, visitor);
/* 117 */           if (Util.isNullOrEmpty(logFiles)) {
/* 118 */             throw new DisplayException("no.log.files.found");
/*     */           }
/* 120 */           progressObserver.onProgress(new PInfo("creating.archive"));
/*     */           try {
/* 122 */             Util.createZIPArchive(fos, logFiles, fileLogHome);
/* 123 */           } catch (IOException ex) {
/* 124 */             throw ex;
/*     */           } finally {
/* 126 */             fos.flush();
/* 127 */             fos.close();
/*     */           } 
/* 129 */           if (fileLogHome.exists() && 
/* 130 */             !Util.deleteDir(fileLogHome)) {
/* 131 */             throw new IllegalStateException("unable to delete directory: " + fileLogHome);
/*     */           }
/*     */         }
/*     */         else {
/*     */           
/* 136 */           throw new DisplayException("no.log.files.found");
/*     */         } 
/*     */       }
/*     */       
/*     */       public void saveLogArchive(ProgressObserver progressObserver, File targetFile) throws Exception {
/* 141 */         if (!targetFile.isAbsolute() || targetFile.isDirectory() || targetFile.getParent() == null) {
/* 142 */           throw new DisplayException("error.invalid.target.file");
/*     */         }
/* 144 */         if (!Util.contains(targetFile.getName(), ".")) {
/* 145 */           targetFile = new File(targetFile.getParent(), targetFile.getName() + ".piz");
/*     */         }
/*     */ 
/*     */         
/* 149 */         FileOutputStream fos = new FileOutputStream(targetFile);
/*     */         try {
/* 151 */           writeLogArchive(progressObserver, fos);
/*     */         } finally {
/* 153 */           fos.close();
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       final class PInfo
/*     */         implements ProgressInfo
/*     */       {
/*     */         private String messageKey;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public String getMessage() {
/* 169 */           return this.messageKey;
/*     */         }
/*     */         
/*     */         public String toString() {
/* 173 */           return getMessage();
/*     */         }
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public String getText(String key) {
/* 180 */         return key;
/*     */       }
/*     */       
/*     */       public void deleteSoftwareKey(ProgressObserver progressObserver) throws Exception {
/* 184 */         Util.sleepRandom(100L, 2000L);
/* 185 */         if (Util.createRandomBoolean()) {
/* 186 */           throw new Exception();
/*     */         }
/*     */       }
/*     */       
/*     */       public Properties retrieveSystemInformation(ProgressObserver progessObserver) throws Exception {
/* 191 */         return SystemInfoProvider.getInstance().getSystemInfo();
/*     */       }
/*     */       
/*     */       public boolean checkEmailAddress(String address) {
/* 195 */         return true;
/*     */       }
/*     */       
/*     */       public boolean isLogArchiveLoaded() {
/* 199 */         return true;
/*     */       }
/*     */       
/*     */       public String getLogArchiveReference() {
/* 203 */         return null;
/*     */       }
/*     */       
/*     */       public boolean deleteLogArchive() {
/* 207 */         return true;
/*     */       }
/*     */       
/*     */       public IApplicationCallback.SpeedTestResult executeConnectionSpeedTest(UIUtil.ProgressObserver observer) throws InterruptedException {
/* 211 */         observer.setProgress("simulating.sleep");
/* 212 */         Thread.sleep(2000L);
/*     */         
/* 214 */         return new IApplicationCallback.SpeedTestResult()
/*     */           {
/*     */             public Set<? extends IDownloadServer> getTestedServers() {
/* 217 */               Set<DownloadServer> result = new LinkedHashSet();
/* 218 */               for (int i = 0; i < 5; i++) {
/*     */                 try {
/* 220 */                   result.add(new DownloadServer(new URL("http://mein.server.test2/test" + i), false, "Test server"));
/* 221 */                 } catch (MalformedURLException e) {
/* 222 */                   throw new RuntimeException(e);
/*     */                 } 
/*     */               } 
/* 225 */               return Collections.EMPTY_SET;
/*     */             }
/*     */             
/*     */             public BigDecimal getConnectionSpeed(IDownloadServer server) {
/* 229 */               return BigDecimal.valueOf(3876L);
/*     */             }
/*     */           };
/*     */       }
/*     */     };
/*     */   
/*     */   void sendLogArchive(ProgressObserver paramProgressObserver, String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
/*     */   
/*     */   void saveLogArchive(ProgressObserver paramProgressObserver, File paramFile) throws Exception;
/*     */   
/*     */   void deleteSoftwareKey(ProgressObserver paramProgressObserver) throws Exception;
/*     */   
/*     */   String getText(String paramString);
/*     */   
/*     */   Properties retrieveSystemInformation(ProgressObserver paramProgressObserver) throws Exception;
/*     */   
/*     */   boolean checkEmailAddress(String paramString);
/*     */   
/*     */   boolean isLogArchiveLoaded();
/*     */   
/*     */   String getLogArchiveReference();
/*     */   
/*     */   boolean deleteLogArchive();
/*     */   
/*     */   AppParamConfigurationManager getAppParamConfigurationManager();
/*     */   
/*     */   Locale getLocale();
/*     */   
/*     */   SpeedTestResult executeConnectionSpeedTest(UIUtil.ProgressObserver paramProgressObserver) throws InterruptedException;
/*     */   
/*     */   public static interface SpeedTestResult {
/*     */     Set<? extends IDownloadServer> getTestedServers();
/*     */     
/*     */     BigDecimal getConnectionSpeed(IDownloadServer param1IDownloadServer);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\diag\client\IApplicationCallback.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */