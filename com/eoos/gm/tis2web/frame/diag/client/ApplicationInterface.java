/*     */ package com.eoos.gm.tis2web.frame.diag.client;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.diag.client.logconf.AppParamConfigurationManager;
/*     */ import com.eoos.gm.tis2web.frame.diag.client.logconf.AppParamConfigurationUtilities;
/*     */ import com.eoos.gm.tis2web.frame.dls.SessionKey;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.DLSManagement;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.DLSServiceFactory;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.SoftwareKey;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.DownloadManager;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.DownloadServiceFactory;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IDownloadUnit;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.ClassificationFilter;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.DownloadFile;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.DownloadServer;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.IDownloadServer;
/*     */ import com.eoos.gm.tis2web.frame.mail.relay.client.api.MailRelayingService;
/*     */ import com.eoos.gm.tis2web.frame.mail.relay.client.api.MailRelayingServiceFactory;
/*     */ import com.eoos.mail.FileDataSource;
/*     */ import com.eoos.math.AverageCalculator;
/*     */ import com.eoos.scsm.v2.io.OutputStreamByteCount;
/*     */ import com.eoos.scsm.v2.io.StreamUtil;
/*     */ import com.eoos.scsm.v2.swing.UIUtil;
/*     */ import com.eoos.scsm.v2.util.Counter;
/*     */ import com.eoos.scsm.v2.util.ICounter;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.scsm.v2.util.progress.v2.ProgressInfo;
/*     */ import com.eoos.scsm.v2.util.progress.v2.ProgressObserver;
/*     */ import com.eoos.util.v2.StopWatch;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InterruptedIOException;
/*     */ import java.io.OutputStream;
/*     */ import java.math.BigDecimal;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class ApplicationInterface implements IApplicationCallback {
/*  51 */   private static final Logger log = Logger.getLogger(ApplicationInterface.class);
/*     */   
/*     */   private Locale locale;
/*     */   
/*     */   private LabelResource labelResource;
/*     */   
/*     */   private String logArchiveReference;
/*     */   
/*     */   private AppParamConfigurationManager _AppParamConfigurationManager;
/*     */   
/*     */   private File homeDir;
/*     */   
/*     */   public AppParamConfigurationManager getAppParamConfigurationManager() {
/*  64 */     if (this._AppParamConfigurationManager == null || !this._AppParamConfigurationManager.isPopulated()) {
/*  65 */       this._AppParamConfigurationManager = AppParamConfigurationManager.getInstance().handleConfiguration(this.locale);
/*     */     }
/*  67 */     return this._AppParamConfigurationManager;
/*     */   }
/*     */   
/*     */   private final class PInfo
/*     */     implements ProgressInfo {
/*     */     private String messageKey;
/*     */     private String arg;
/*     */     
/*     */     private PInfo(String messageKey) {
/*  76 */       this(messageKey, null);
/*     */     }
/*     */     
/*     */     private PInfo(String messageKey, String arg) {
/*  80 */       this.messageKey = messageKey;
/*  81 */       this.arg = arg;
/*     */     }
/*     */     
/*     */     public String getMessage() {
/*  85 */       String ret = ApplicationInterface.this.labelResource.getMessage(this.messageKey);
/*  86 */       if (this.arg != null) {
/*  87 */         ret = ret + this.arg;
/*     */       }
/*  89 */       return ret;
/*     */     }
/*     */     
/*     */     public String toString() {
/*  93 */       return getMessage();
/*     */     }
/*     */   }
/*     */   
/*     */   public ApplicationInterface(File homeDir) {
/*  98 */     this.homeDir = homeDir;
/*  99 */     this.locale = Util.parseLocale(System.getProperty("language.id"));
/* 100 */     if (this.locale == null) {
/* 101 */       this.locale = Locale.ENGLISH;
/*     */     }
/*     */     
/* 104 */     this.labelResource = new LabelResource(this.locale);
/*     */     
/* 106 */     this.logArchiveReference = null;
/* 107 */     getAppParamConfigurationManager();
/*     */   }
/*     */   
/*     */   public Locale getLocale() {
/* 111 */     return this.locale;
/*     */   }
/*     */   
/*     */   public void deleteSoftwareKey(ProgressObserver progressObserver) throws Exception {
/* 115 */     progressObserver.onProgress(new PInfo("connecting.to.dls.service"));
/* 116 */     DLSManagement dlsManagement = (DLSManagement)DLSServiceFactory.createService(null);
/* 117 */     progressObserver.onProgress(new PInfo("deleting.swk"));
/* 118 */     dlsManagement.deleteSoftwareKey();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getText(String key) {
/* 123 */     return this.labelResource.getLabel(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLogArchiveLoaded() {
/* 128 */     return (this.logArchiveReference != null);
/*     */   }
/*     */   
/*     */   public String getLogArchiveReference() {
/* 132 */     return this.logArchiveReference;
/*     */   }
/*     */   
/*     */   public boolean deleteLogArchive() {
/* 136 */     boolean ret = false;
/* 137 */     if (isLogArchiveLoaded()) {
/* 138 */       File fTmp = new File(this.logArchiveReference);
/* 139 */       ret = fTmp.delete();
/* 140 */       this.logArchiveReference = null;
/*     */     } 
/* 142 */     return ret;
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
/*     */   private void writeLogArchive(final ProgressObserver progressObserver, FileOutputStream fos) throws Exception, DisplayException, IOException {
/* 157 */     if (this._AppParamConfigurationManager.getCountFileContainer() > 0) {
/*     */       
/* 159 */       String user_home = System.getProperty("user.home");
/* 160 */       String user_tmp_dir = System.getProperty("java.io.tmpdir");
/* 161 */       String sDirLogs = "\\logs_" + System.currentTimeMillis();
/*     */       
/* 163 */       String log_maindir = null;
/* 164 */       if (user_tmp_dir != null && user_tmp_dir.length() > 0) {
/* 165 */         log_maindir = user_tmp_dir + sDirLogs;
/*     */       } else {
/* 167 */         log_maindir = user_home + sDirLogs;
/*     */       } 
/* 169 */       String logs_home = log_maindir + sDirLogs + "\\";
/* 170 */       File fileLogHome = new File(logs_home);
/* 171 */       fileLogHome.mkdirs();
/* 172 */       fileLogHome = new File(log_maindir);
/*     */ 
/*     */       
/* 175 */       HashMap hmpLogFileList = this._AppParamConfigurationManager.getHmpLogFileList();
/* 176 */       for (Iterator<String> iterCont = hmpLogFileList.keySet().iterator(); iterCont.hasNext(); ) {
/* 177 */         String appName = iterCont.next();
/* 178 */         ArrayList fileList = (ArrayList)hmpLogFileList.get(appName);
/* 179 */         if (fileList != null) {
/*     */           
/* 181 */           File fileApp = new File(logs_home + appName);
/* 182 */           fileApp.mkdir();
/* 183 */           for (Iterator<HashMap> iterFile = fileList.iterator(); iterFile.hasNext(); ) {
/* 184 */             HashMap hmp = iterFile.next();
/* 185 */             String fileNameWithPath = hmp.keySet().iterator().next();
/* 186 */             String basicPath = (String)hmp.get(fileNameWithPath);
/* 187 */             String filename = (new File(fileNameWithPath)).getName();
/* 188 */             String internPath = fileNameWithPath.substring(basicPath.length(), fileNameWithPath.indexOf(filename));
/* 189 */             if (internPath == null || internPath.equals("")) {
/* 190 */               internPath = "\\";
/*     */             }
/*     */ 
/*     */             
/* 194 */             File copyToDir = new File(logs_home + appName + internPath);
/* 195 */             if (!copyToDir.exists())
/* 196 */               copyToDir.mkdirs(); 
/* 197 */             AppParamConfigurationUtilities.copyAFileTo(new File(fileNameWithPath), new File(copyToDir.getAbsolutePath() + "\\" + filename));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 203 */       addSysInfoToLogArchive(progressObserver, new File(logs_home));
/*     */ 
/*     */       
/* 206 */       addSysJavaToLogArchive(progressObserver, new File(logs_home));
/*     */ 
/*     */       
/* 209 */       progressObserver.onProgress(new PInfo("creating.archive"));
/*     */ 
/*     */       
/* 212 */       final List logFiles = new LinkedList();
/* 213 */       Util.FileVisitor visitor = new Util.FileVisitor()
/*     */         {
/*     */           public boolean onVisit(File file) {
/* 216 */             if (file.isFile()) {
/* 217 */               progressObserver.onProgress(new ApplicationInterface.PInfo("found.log.file", ": " + file.getName()));
/* 218 */               logFiles.add(file);
/*     */             } 
/* 220 */             return true;
/*     */           }
/*     */         };
/* 223 */       Util.visitFiles(fileLogHome, visitor);
/* 224 */       if (Util.isNullOrEmpty(logFiles)) {
/* 225 */         throw new DisplayException(this.labelResource.getMessage("no.log.files.found"));
/*     */       }
/* 227 */       progressObserver.onProgress(new PInfo("creating.archive"));
/*     */       try {
/* 229 */         Util.createZIPArchive(fos, logFiles, fileLogHome);
/* 230 */       } catch (IOException ex) {
/* 231 */         throw ex;
/*     */       } finally {
/* 233 */         fos.flush();
/* 234 */         fos.close();
/*     */       } 
/* 236 */       if (fileLogHome.exists() && 
/* 237 */         !Util.deleteDir(fileLogHome)) {
/* 238 */         throw new IllegalStateException("unable to delete directory: " + fileLogHome);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 243 */       throw new DisplayException(this.labelResource.getMessage("no.log.files.found"));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addSysInfoToLogArchive(ProgressObserver progressObserver, File logHome) throws Exception {
/* 249 */     Properties sysInfo = retrieveSystemInformation(progressObserver);
/*     */     
/* 251 */     File systemInfoTxt = new File(logHome + "\\SysInfo\\");
/* 252 */     systemInfoTxt.mkdir();
/* 253 */     systemInfoTxt = new File(logHome + "\\SysInfo\\SystemInformation.txt");
/* 254 */     if (sysInfo != null && !sysInfo.isEmpty()) {
/* 255 */       AppParamConfigurationUtilities.makeFileFromProperties(sysInfo, systemInfoTxt);
/*     */     }
/*     */   }
/*     */   
/*     */   public void addSysJavaToLogArchive(ProgressObserver progressObserver, File logHome) throws Exception {
/* 260 */     Properties sysJava = System.getProperties();
/* 261 */     if (sysJava != null && !sysJava.isEmpty()) {
/* 262 */       File systemJavaInfoTxt = new File(logHome + "\\SysJava\\");
/* 263 */       systemJavaInfoTxt.mkdir();
/* 264 */       systemJavaInfoTxt = new File(logHome + "\\SysJava\\SystemJavaInformation.txt");
/* 265 */       AppParamConfigurationUtilities.makeFileFromProperties(sysJava, systemJavaInfoTxt);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void saveLogArchive(ProgressObserver progressObserver, File targetFile) throws Exception {
/* 270 */     if (!targetFile.isAbsolute() || targetFile.isDirectory() || targetFile.getParent() == null) {
/* 271 */       throw new DisplayException(this.labelResource.getMessage("error.invalid.target.file"));
/*     */     }
/*     */     
/* 274 */     if (!Util.contains(targetFile.getName(), ".")) {
/* 275 */       targetFile = new File(targetFile.getParent(), targetFile.getName() + ".piz");
/*     */     }
/*     */ 
/*     */     
/* 279 */     FileOutputStream fos = new FileOutputStream(targetFile);
/*     */     try {
/* 281 */       writeLogArchive(progressObserver, fos);
/* 282 */       this.logArchiveReference = targetFile.getAbsolutePath();
/*     */     } finally {
/* 284 */       if (fos != null) {
/* 285 */         fos.close();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void sendLogArchive(ProgressObserver progressObserver, String mailRecipient, String mailSender, String mailSubject, String mailBody) throws Exception {
/* 291 */     boolean isLogToDeleteAfterSend = false;
/*     */     
/* 293 */     if (!isLogArchiveLoaded()) {
/* 294 */       File fTmp = new File(System.getProperty("user.home") + "\\logs_" + System.currentTimeMillis() + ".piz");
/* 295 */       isLogToDeleteAfterSend = true;
/* 296 */       saveLogArchive(progressObserver, fTmp);
/*     */     } 
/*     */     
/* 299 */     if (isLogArchiveLoaded()) {
/* 300 */       FileOutputStream fos = new FileOutputStream(this.logArchiveReference);
/*     */       try {
/* 302 */         progressObserver.onProgress(new PInfo("preparing.mail"));
/* 303 */         writeLogArchive(progressObserver, fos);
/*     */       } finally {
/* 305 */         if (fos != null) {
/* 306 */           fos.flush();
/* 307 */           fos.close();
/*     */         } 
/*     */       } 
/*     */       
/* 311 */       MailRelayingService mrs = MailRelayingServiceFactory.createService(null);
/* 312 */       File fTmp = new File(this.logArchiveReference);
/*     */       
/* 314 */       Collection<FileDataSource> eoosDataSources = Collections.singleton(new FileDataSource(fTmp)
/*     */           {
/*     */             public String getContentType() {
/* 317 */               return "application/zip";
/*     */             }
/*     */           });
/* 320 */       progressObserver.onProgress(new PInfo("sending.email"));
/* 321 */       mrs.send(mailSender, null, Collections.singleton(mailRecipient), mailSubject, mailBody, eoosDataSources);
/* 322 */       if (isLogArchiveLoaded() && isLogToDeleteAfterSend && 
/* 323 */         !deleteLogArchive()) {
/* 324 */         throw new Exception(getText("archive.not.deleted"));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 330 */   private static final Pattern VALID_EMAIL = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", 2);
/*     */   
/*     */   public boolean checkEmailAddress(String email) {
/* 333 */     return VALID_EMAIL.matcher(email).matches();
/*     */   }
/*     */   
/*     */   public Properties retrieveSystemInformation(ProgressObserver progessObserver) throws Exception {
/* 337 */     return SystemInfoProvider.getInstance().getSystemInfo();
/*     */   }
/*     */   
/*     */   private String getSessionID() {
/* 341 */     return System.getProperty("session.id");
/*     */   }
/*     */   
/*     */   public IApplicationCallback.SpeedTestResult executeConnectionSpeedTest(UIUtil.ProgressObserver progressObserver) throws InterruptedException {
/* 345 */     log.debug("executing connection speed test...");
/* 346 */     File wrkDir = new File(this.homeDir, ".tmp");
/* 347 */     if (!wrkDir.exists()) {
/* 348 */       wrkDir.mkdir();
/*     */     }
/*     */     
/* 351 */     log.debug("...retrieving testing units");
/* 352 */     progressObserver.setProgress(getText("retrieving.test.units"));
/* 353 */     final Map<IDownloadServer, AverageCalculator> serverToAverage = new ConcurrentHashMap<IDownloadServer, AverageCalculator>();
/* 354 */     DownloadManager dwnldService = (DownloadManager)DownloadServiceFactory.createInstance((SoftwareKey)new SessionKey(getSessionID()), null, wrkDir, null);
/* 355 */     Collection<IDownloadUnit> units = dwnldService.getDownloadUnits(Collections.singleton(ClassificationFilter.SPEED_TEST));
/* 356 */     for (Iterator<IDownloadUnit> iterUnit = units.iterator(); iterUnit.hasNext(); ) {
/* 357 */       IDownloadUnit unit = iterUnit.next();
/* 358 */       Util.checkInterruption();
/* 359 */       log.debug("....current unit: " + unit);
/* 360 */       for (Iterator<DownloadFile> iterFile = unit.getFiles().iterator(); iterFile.hasNext(); ) {
/* 361 */         DownloadFile file = iterFile.next();
/* 362 */         Util.checkInterruption();
/* 363 */         log.debug("...current file: " + file);
/* 364 */         for (Iterator<DownloadServer> iterServer = unit.getDownloadServers().iterator(); iterServer.hasNext(); ) {
/* 365 */           DownloadServer server = iterServer.next();
/* 366 */           Util.checkInterruption();
/* 367 */           log.debug("...current server: " + server);
/* 368 */           progressObserver.setProgress(getText("executing.speed.test"));
/*     */           
/*     */           try {
/* 371 */             StopWatch sw = StopWatch.getInstance();
/*     */             try {
/* 373 */               Counter counter = new Counter();
/* 374 */               OutputStream os = StreamUtil.NILOutputStream;
/* 375 */               OutputStreamByteCount outputStreamByteCount = new OutputStreamByteCount(os, (ICounter)counter);
/*     */               
/*     */               try {
/* 378 */                 sw.start();
/* 379 */                 dwnldService.downloadFile(file, (IDownloadServer)server, (OutputStream)outputStreamByteCount);
/* 380 */                 long time = sw.stop();
/*     */ 
/*     */                 
/* 383 */                 BigDecimal speed = new BigDecimal(counter.getCount());
/* 384 */                 speed = speed.setScale(5);
/* 385 */                 speed = speed.divide(BigDecimal.valueOf(Math.max(time, 1L)), 1);
/* 386 */                 log.debug("...transfered " + counter.getCount() + " bytes in " + time + " ms (" + speed + " KByte/sec)");
/* 387 */                 AverageCalculator averageCalc = serverToAverage.get(server);
/* 388 */                 if (averageCalc == null) {
/* 389 */                   averageCalc = new AverageCalculator(0);
/* 390 */                   serverToAverage.put(server, averageCalc);
/*     */                 } 
/* 392 */                 averageCalc.add(speed);
/*     */               } finally {
/*     */                 
/* 395 */                 StreamUtil.close((OutputStream)outputStreamByteCount, log);
/*     */               } 
/*     */             } finally {
/*     */               
/* 399 */               StopWatch.freeInstance(sw);
/*     */             } 
/* 401 */           } catch (InterruptedException e) {
/* 402 */             throw e;
/* 403 */           } catch (InterruptedIOException e) {
/* 404 */             throw new InterruptedException();
/* 405 */           } catch (Exception e) {
/* 406 */             log.warn("unable to download file: " + String.valueOf(file) + " from server: " + String.valueOf(server) + ", ignoring - exception:", e);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 414 */     log.debug("...done");
/* 415 */     return new IApplicationCallback.SpeedTestResult()
/*     */       {
/*     */         public BigDecimal getConnectionSpeed(IDownloadServer server) {
/* 418 */           return ((AverageCalculator)serverToAverage.get(server)).getCurrentAverage();
/*     */         }
/*     */         
/*     */         public Set<IDownloadServer> getTestedServers() {
/* 422 */           return serverToAverage.keySet();
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\diag\client\ApplicationInterface.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */