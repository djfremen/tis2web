/*     */ package com.eoos.gm.tis2web.kdr.client;
/*     */ 
/*     */ import ca.beq.util.win32.registry.RegistryKey;
/*     */ import ca.beq.util.win32.registry.RegistryValue;
/*     */ import ca.beq.util.win32.registry.RootKey;
/*     */ import com.eoos.gm.tis2web.common.ProxyUtil;
/*     */ import com.eoos.gm.tis2web.common.TaskExecutionClient;
/*     */ import com.eoos.gm.tis2web.common.TaskExecutionClientFactory;
/*     */ import com.eoos.gm.tis2web.frame.dls.LeaseRequest;
/*     */ import com.eoos.gm.tis2web.frame.dls.RegisterSWKTask;
/*     */ import com.eoos.gm.tis2web.frame.dls.SessionKey;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.DLSManagement;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.DLSService;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.DLSServiceFactory;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.ExpiredLeaseException;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.Lease;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.MissingLeaseException;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.SoftwareKey;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.DownloadManager;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.DownloadServiceFactory;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IDownloadFile;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IDownloadPackage;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IDownloadService;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IDownloadUnit;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.ClassificationFilter;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.VersionFilter;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.WellKnownFilter;
/*     */ import com.eoos.gm.tis2web.windows.SystemInfo;
/*     */ import com.eoos.io.StreamUtil;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.util.ConfigurationUtil;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.scsm.v2.swing.v2.ProgressDialog;
/*     */ import com.eoos.scsm.v2.swing.v2.ProgressInfoRI;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.scsm.v2.util.VersionNumber;
/*     */ import com.eoos.scsm.v2.util.progress.v2.CancellationListener;
/*     */ import com.eoos.scsm.v2.util.progress.v2.ProgressInfo;
/*     */ import com.eoos.scsm.v2.util.progress.v2.ProgressObserver;
/*     */ import com.eoos.util.Task;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.lang.reflect.Method;
/*     */ import java.math.BigInteger;
/*     */ import java.net.URI;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.swing.JOptionPane;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KDRClient
/*     */ {
/*     */   private static final String REGKEY_ENV_VERSION = "GDS_ENV_VERSION";
/*     */   protected static final String REGKEY_GDS_HOMEDIR = "GDS_HOME";
/*     */   protected static final String REGKEY_GDS = "Software\\GM\\GDS";
/*  73 */   private static final String[] fqLanguages = new String[] { "zh_CN", "zh_TW", "pt_BR" };
/*     */   
/*  75 */   private static final Logger log = Logger.getLogger(KDRClient.class);
/*     */   
/*     */   private File homeDir;
/*     */   
/*  79 */   private final Object SYNC_DWNLD_SRV = new Object();
/*     */   
/*     */   private IDownloadService downloadService;
/*     */   
/*     */   private Configuration cfg;
/*     */   
/*     */   private Locale locale;
/*     */   
/*     */   private LabelResource labelResource;
/*     */   
/*  89 */   private final Object SYNC_DLSSERV = new Object();
/*     */   
/*  91 */   private DLSService dlsService = null;
/*     */   
/*     */   private static final int PARTID_ENV = 1;
/*     */   
/*     */   private static final int PARTID_SMARTSTART = 2;
/*     */   
/*     */   public KDRClient(File homeDir) {
/*  98 */     this.homeDir = homeDir;
/*     */     
/* 100 */     this.cfg = ConfigurationUtil.getSystemPropertiesAdapter();
/* 101 */     this.locale = Util.parseLocale(this.cfg.getProperty("language.id"));
/* 102 */     this.labelResource = new LabelResource(this.locale);
/*     */   }
/*     */ 
/*     */   
/*     */   private IDownloadService getDownloadService() throws ExpiredLeaseException, MissingLeaseException {
/* 107 */     synchronized (this.SYNC_DWNLD_SRV) {
/* 108 */       if (this.downloadService == null) {
/* 109 */         File wrkDir = new File(this.homeDir, ".wrk");
/* 110 */         this.downloadService = DownloadServiceFactory.createInstance((SoftwareKey)new SessionKey(getSessionID()), null, wrkDir, null);
/*     */       } 
/* 112 */       return this.downloadService;
/*     */     } 
/*     */   }
/*     */   
/*     */   private DLSService getDLSService() {
/* 117 */     synchronized (this.SYNC_DLSSERV) {
/* 118 */       if (this.dlsService == null) {
/* 119 */         this.dlsService = DLSServiceFactory.createService(null);
/*     */       }
/* 121 */       return this.dlsService;
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getInstalledEnvVersion() {
/* 126 */     String ret = null;
/* 127 */     RegistryKey r = new RegistryKey(RootKey.HKEY_LOCAL_MACHINE, "Software\\GM\\GDS");
/* 128 */     if (r.exists() && r.hasValue("GDS_ENV_VERSION")) {
/* 129 */       RegistryValue v = r.getValue("GDS_ENV_VERSION");
/* 130 */       ret = v.getStringValue();
/*     */     } 
/*     */     
/* 133 */     return toSpecificationVersion(ret);
/*     */   }
/*     */   
/*     */   private String toSpecificationVersion(String version) {
/* 137 */     if (version == null) {
/* 138 */       return null;
/*     */     }
/* 140 */     Pattern p = Pattern.compile("\\d*\\.\\d*\\.\\d*");
/* 141 */     Matcher m = p.matcher(version);
/* 142 */     if (m.find()) {
/* 143 */       return m.group();
/*     */     }
/* 145 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private File getGDSHomeDir() {
/* 150 */     String ret = null;
/* 151 */     RegistryKey r = new RegistryKey(RootKey.HKEY_LOCAL_MACHINE, "Software\\GM\\GDS");
/* 152 */     if (r.exists() && r.hasValue("GDS_HOME")) {
/* 153 */       RegistryValue v = r.getValue("GDS_HOME");
/* 154 */       ret = v.getStringValue();
/*     */     } 
/* 156 */     return (ret != null) ? new File(ret) : null;
/*     */   }
/*     */   
/*     */   private File getSmartStartDir() {
/* 160 */     File gdsDir = getGDSHomeDir();
/* 161 */     return (gdsDir != null) ? new File(getGDSHomeDir(), "smartstart") : null;
/*     */   }
/*     */   
/*     */   private File getJREDir() {
/* 165 */     File gdsDir = getGDSHomeDir();
/* 166 */     return (gdsDir != null) ? new File(getGDSHomeDir(), "jre") : null;
/*     */   }
/*     */   
/*     */   private String getRemainingTimeDisplayString(long remainingTime) {
/* 170 */     BigInteger remaining = BigInteger.valueOf(remainingTime);
/* 171 */     BigInteger[] tmp = Util.getMinutes(remaining);
/* 172 */     BigInteger minutes = tmp[0];
/* 173 */     tmp = Util.getSeconds(tmp[1]);
/* 174 */     BigInteger seconds = tmp[0];
/* 175 */     StringBuffer time = new StringBuffer();
/* 176 */     if (minutes.longValue() > 0L) {
/* 177 */       time.append(minutes.longValue() + " min, ");
/*     */     }
/* 179 */     time.append(seconds.longValue() + " sec");
/* 180 */     return this.labelResource.getLabel("remaining.time") + ": " + time.toString();
/*     */   }
/*     */   
/*     */   private void updateEnv(final IDownloadUnit env, final ProgressObserver po, final ProgressInfoRI pi) throws Exception {
/* 184 */     log.debug("...updating smartstart environment");
/*     */     
/* 186 */     po.onProgress((ProgressInfo)pi.setMessage(this.labelResource.getLabel("downloading.installer") + " V" + env.getVersionNumber() + " ..."));
/*     */     
/* 188 */     File targetDir = new File(this.homeDir, "smartstart.env.installer.dwnld");
/*     */     
/* 190 */     log.debug("...downloading installer to dir: " + String.valueOf(targetDir));
/* 191 */     final IDownloadPackage pkg = getDownloadService().createPackage(Collections.singleton(env), targetDir);
/* 192 */     Thread poUpdater = Util.createAndStartThread(new Runnable() {
/*     */           public void run() {
/*     */             try {
/* 195 */               Thread.sleep(2000L);
/*     */               while (true) {
/* 197 */                 Thread.sleep(2000L);
/*     */                 
/*     */                 try {
/* 200 */                   long remainingTime = KDRClient.this.getDownloadService().getStatus(pkg).getRemainingTimeEstimate();
/* 201 */                   po.onProgress((ProgressInfo)pi.setMessage(KDRClient.this.labelResource.getLabel("downloading.installer") + " V" + env.getVersionNumber() + " (" + KDRClient.this.getRemainingTimeDisplayString(remainingTime) + ")"));
/* 202 */                 } catch (Exception e) {
/* 203 */                   KDRClient.log.error("unable to retrieve remaining time, ignoring - exception : " + e, e);
/*     */                 }
/*     */               
/*     */               } 
/* 207 */             } catch (InterruptedException e) {
/* 208 */               Thread.currentThread().interrupt();
/*     */               return;
/*     */             }  }
/*     */         });
/*     */     try {
/* 213 */       getDownloadService().downloadPackage(pkg);
/*     */     } finally {
/* 215 */       poUpdater.interrupt();
/*     */     } 
/* 217 */     File installerDir = new File(this.homeDir, "smartstart.env.installer");
/* 218 */     if (installerDir.exists() && 
/* 219 */       !Util.deleteDir(installerDir)) {
/* 220 */       throw new IllegalStateException("unable to delete directory: " + installerDir);
/*     */     }
/*     */     
/* 223 */     if (!targetDir.renameTo(installerDir)) {
/* 224 */       throw new IllegalStateException("unable to rename directory: " + targetDir + " to " + installerDir);
/*     */     }
/*     */     
/* 227 */     po.onProgress((ProgressInfo)pi.setMessage(this.labelResource.getLabel("installing.starter.environment")));
/* 228 */     log.debug("....executing the installer");
/*     */     
/* 230 */     IDownloadFile file = null;
/* 231 */     IDownloadFile.IExecutable startFile = null;
/* 232 */     for (Iterator<IDownloadFile> iter = env.getFiles().iterator(); iter.hasNext() && startFile == null; ) {
/* 233 */       file = iter.next();
/* 234 */       if (file instanceof IDownloadFile.IExecutable) {
/* 235 */         startFile = (IDownloadFile.IExecutable)file;
/*     */       }
/*     */     } 
/* 238 */     if (startFile == null) {
/* 239 */       throw new IllegalStateException("no starter information found");
/*     */     }
/*     */     
/* 242 */     StringBuffer cmdLine = new StringBuffer();
/* 243 */     cmdLine.append(startFile.getExecutablePath());
/* 244 */     if (!Util.isNullOrEmpty(startFile.getCmdLineParams())) {
/* 245 */       cmdLine.append(" ").append(startFile.getCmdLineParams());
/*     */     }
/*     */     
/* 248 */     File installTargetDir = getGDSHomeDir();
/* 249 */     String defaultDir = getDefaultDir();
/* 250 */     while (installTargetDir == null) {
/* 251 */       String _installTargetDir = JOptionPane.showInputDialog(null, this.labelResource.getLabel("please.specify.dgs.target.directory"), defaultDir);
/* 252 */       if (_installTargetDir == null) {
/* 253 */         throw new InterruptedException("user abortion");
/*     */       }
/*     */       try {
/* 256 */         installTargetDir = new File(_installTargetDir);
/*     */         
/* 258 */         if (!installDirAccepted(_installTargetDir)) {
/* 259 */           throw new IllegalArgumentException();
/*     */         }
/* 261 */       } catch (IllegalArgumentException e) {
/* 262 */         installTargetDir = null;
/* 263 */         if (2 == JOptionPane.showConfirmDialog(null, this.labelResource.getLabel("invalid.input.retry"), null, 2, 0)) {
/* 264 */           throw new InterruptedException("user abortion");
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 269 */     cmdLine.append(" ").append("-DUSER_INSTALL_DIR=\"" + installTargetDir.getAbsolutePath() + "\"");
/* 270 */     cmdLine.append(" ").append("-DGDS_TIS2WEB_INSTALL=true");
/* 271 */     if (this.locale != null && this.locale.getLanguage().compareToIgnoreCase("tr") == 0) {
/* 272 */       cmdLine.append(" ").append("-i SILENT");
/*     */     } else {
/* 274 */       cmdLine.append(" ").append("-i silent");
/*     */     } 
/* 276 */     cmdLine.append(" ").append("-l " + getUninstallerLanguage());
/*     */     
/* 278 */     Process process = Runtime.getRuntime().exec(new String[] { "cmd.exe", "/C", cmdLine.toString() }, (String[])null, installerDir);
/*     */     
/* 280 */     process.getOutputStream().close();
/*     */ 
/*     */ 
/*     */     
/* 284 */     Util.createAndStartThread(StreamUtil.connect(new BufferedInputStream(process.getInputStream()), System.out));
/* 285 */     Util.createAndStartThread(StreamUtil.connect(new BufferedInputStream(process.getErrorStream()), System.err));
/*     */     
/* 287 */     log.debug("...waiting for termination");
/*     */     
/* 289 */     int status = process.waitFor();
/* 290 */     String lgMsg = "Smartstart environment installer process status code: " + status;
/* 291 */     if (status == 1 && System.getProperty("os.name").toUpperCase(Locale.ENGLISH).indexOf("VISTA") >= 0) {
/* 292 */       lgMsg = lgMsg + " (no elevation?)";
/*     */     }
/* 294 */     log.debug(lgMsg);
/* 295 */     if (!Util.isNullOrEmpty(startFile.getFailureCodes()) && Util.parseList(startFile.getFailureCodes()).contains(String.valueOf(status))) {
/* 296 */       throw new IllegalStateException("smart start installer failed");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateSmartStart(final IDownloadUnit smartStart, final ProgressObserver po, final ProgressInfoRI pi) throws Exception {
/* 302 */     log.debug("...updating smartstart");
/* 303 */     File gdsDir = getGDSHomeDir();
/* 304 */     if (gdsDir == null) {
/* 305 */       throw new IllegalStateException("unable to determine gds home directory");
/*     */     }
/*     */     
/* 308 */     po.onProgress((ProgressInfo)pi.setMessage(this.labelResource.getLabel("updating.starter") + " V" + smartStart.getVersionNumber()));
/*     */     
/* 310 */     File targetDir = new File(gdsDir, "smartstart.dwnld");
/*     */     
/* 312 */     final IDownloadPackage pkg = getDownloadService().createPackage(Collections.singleton(smartStart), targetDir);
/* 313 */     Thread poUpdater = Util.createAndStartThread(new Runnable() {
/*     */           public void run() {
/*     */             try {
/* 316 */               Thread.sleep(2000L);
/*     */               
/*     */               while (true) {
/* 319 */                 Thread.sleep(2000L);
/*     */                 try {
/* 321 */                   long remainingTime = KDRClient.this.getDownloadService().getStatus(pkg).getRemainingTimeEstimate();
/* 322 */                   po.onProgress((ProgressInfo)pi.setMessage(KDRClient.this.labelResource.getLabel("updating.starter") + " V" + smartStart.getVersionNumber() + " (" + KDRClient.this.getRemainingTimeDisplayString(remainingTime) + ")"));
/* 323 */                 } catch (Exception e) {
/* 324 */                   KDRClient.log.error("unable to calc remaining time, ignoring - exception: " + e, e);
/*     */                 } 
/*     */               } 
/* 327 */             } catch (InterruptedException e) {
/* 328 */               Thread.currentThread().interrupt();
/*     */               return;
/*     */             }  }
/*     */         });
/*     */     try {
/* 333 */       getDownloadService().downloadPackage(pkg);
/*     */     } finally {
/* 335 */       poUpdater.interrupt();
/*     */     } 
/* 337 */     File smartStartDir = getSmartStartDir();
/* 338 */     if (smartStartDir == null)
/* 339 */       throw new IllegalStateException("unable to determine smart start directory"); 
/* 340 */     if (smartStartDir.exists() && 
/* 341 */       !Util.deleteDir(smartStartDir)) {
/* 342 */       throw new IllegalStateException("unable to delete directory: " + smartStartDir);
/*     */     }
/*     */     
/* 345 */     if (!targetDir.renameTo(smartStartDir)) {
/* 346 */       throw new IllegalStateException("unable to rename directory: " + targetDir + " to " + smartStartDir);
/*     */     }
/*     */     
/* 349 */     SmartStartVersion.getInstance().setInstalledSmartStartVersion(smartStart.getVersionNumber().toString());
/*     */   }
/*     */   
/*     */   private IDownloadUnit getNewestSmartStartVersion() throws Exception {
/* 353 */     log.debug("...retrieving newest smartstart version");
/* 354 */     Collection<ClassificationFilter> filter = new HashSet();
/* 355 */     filter.add(ClassificationFilter.KDR_SMART_START);
/* 356 */     filter.add(WellKnownFilter.NEWEST_VERSION);
/* 357 */     return (IDownloadUnit)CollectionUtil.getFirst(getDownloadService().getDownloadUnits(filter));
/*     */   }
/*     */   
/*     */   private IDownloadUnit getNewestEnvInstallerVersion() throws Exception {
/* 361 */     log.debug("...retrieving newest environment installer version");
/* 362 */     Collection<ClassificationFilter> filter = new HashSet();
/* 363 */     filter.add(ClassificationFilter.SMARTSTART_INSTALLER);
/* 364 */     filter.add(WellKnownFilter.NEWEST_VERSION);
/* 365 */     return (IDownloadUnit)CollectionUtil.getFirst(getDownloadService().getDownloadUnits(filter));
/*     */   }
/*     */   
/*     */   private IDownloadUnit getSmartStartVersion(String version) throws Exception {
/* 369 */     Collection<ClassificationFilter> filter = new HashSet();
/* 370 */     filter.add(ClassificationFilter.KDR_SMART_START);
/* 371 */     filter.add(new VersionFilter(version));
/* 372 */     return (IDownloadUnit)CollectionUtil.getFirst(getDownloadService().getDownloadUnits(filter));
/*     */   }
/*     */   
/*     */   private IDownloadUnit getEnvInstallerVersion(String version) throws Exception {
/* 376 */     Collection<ClassificationFilter> filter = new HashSet();
/* 377 */     filter.add(ClassificationFilter.SMARTSTART_INSTALLER);
/* 378 */     filter.add(new VersionFilter(version));
/* 379 */     return (IDownloadUnit)CollectionUtil.getFirst(getDownloadService().getDownloadUnits(filter));
/*     */   }
/*     */   
/*     */   private void checkInterruption() throws InterruptedException {
/* 383 */     if (Thread.interrupted()) {
/* 384 */       throw new InterruptedException();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean confirmDowngrade(final VersionNumber installedVersion, final VersionNumber requestedVersion, int partid) throws InterruptedException {
/*     */     String msgKey;
/* 394 */     if (partid == 1) {
/* 395 */       msgKey = "confirm.downgrade.env";
/* 396 */     } else if (partid == 2) {
/* 397 */       msgKey = "confirm.downgrade.smartstart";
/*     */     } else {
/* 399 */       throw new IllegalArgumentException();
/*     */     } 
/*     */     
/* 402 */     StringBuffer msg = new StringBuffer(this.labelResource.getMessage(msgKey));
/* 403 */     Util.replace(msg, Util.PATTERN_VARIABLE, new Util.ReplacementCallback()
/*     */         {
/*     */           public CharSequence getReplacement(CharSequence match, Util.ReplacementCallback.MatcherCallback matcherCallback) {
/* 406 */             String name = matcherCallback.getGroup(1);
/* 407 */             if (name.equalsIgnoreCase("installed.version.number"))
/* 408 */               return installedVersion.toString(); 
/* 409 */             if (name.equalsIgnoreCase("requested.version.number")) {
/* 410 */               return requestedVersion.toString();
/*     */             }
/* 412 */             return match;
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 417 */     int result = JOptionPane.showConfirmDialog(null, msg.toString());
/* 418 */     if (result == 2)
/* 419 */       throw new InterruptedException("user abortion"); 
/* 420 */     if (result == 1)
/* 421 */       return false; 
/* 422 */     if (result == 0) {
/* 423 */       return true;
/*     */     }
/* 425 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void startup() throws Exception {
/*     */     try {
/* 431 */       log.info("beginning smartstart retrieval and execution sequence");
/* 432 */       ProgressObserver po = ProgressDialog.create(null, new ProgressDialog.Callback()
/*     */           {
/*     */             public String getLabel(String key) {
/* 435 */               return KDRClient.this.labelResource.getLabel(key);
/*     */             }
/*     */             
/*     */             public int getEstimatedMessageWidth() {
/* 439 */               return 30;
/*     */             }
/*     */           },  true);
/*     */ 
/*     */       
/* 444 */       final Thread executionThread = Thread.currentThread();
/* 445 */       po.setCancellationListener(new CancellationListener()
/*     */           {
/*     */             public void onCancel() {
/* 448 */               if (Util.isAlive(executionThread)) {
/* 449 */                 executionThread.interrupt();
/*     */               }
/*     */             }
/*     */           });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 458 */       ProgressInfoRI pi = new ProgressInfoRI();
/* 459 */       po.onProgress((ProgressInfo)pi.setMessage(this.labelResource.getLabel("preparing.env")));
/*     */       
/* 461 */       IDownloadUnit unitEnvironmentInstaller = null;
/*     */       
/* 463 */       String installedEnvironmentVersion = getInstalledEnvVersion();
/* 464 */       log.debug("...installed environment version: " + String.valueOf(installedEnvironmentVersion));
/*     */       
/* 466 */       String requestedEnvironmentVersion = this.cfg.getProperty("env.version");
/* 467 */       log.debug("...requested environment version is: " + String.valueOf(requestedEnvironmentVersion));
/* 468 */       if (Util.isNullOrEmpty(requestedEnvironmentVersion)) {
/* 469 */         unitEnvironmentInstaller = getNewestEnvInstallerVersion();
/* 470 */         requestedEnvironmentVersion = unitEnvironmentInstaller.getVersionNumber().toString();
/* 471 */         log.debug("...setting requested version to newest: " + requestedEnvironmentVersion);
/*     */       } 
/*     */       
/* 474 */       VersionNumber installedEnvVN = Util.parseVersionNumber(installedEnvironmentVersion);
/* 475 */       VersionNumber requestedEnvVN = Util.parseVersionNumber(requestedEnvironmentVersion);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 482 */       if (requestedEnvVN != null && !Util.equals(installedEnvVN, requestedEnvVN) && (installedEnvVN == null || Util.isHigher((Comparable)requestedEnvVN, (Comparable)installedEnvVN) || confirmDowngrade(installedEnvVN, requestedEnvVN, 1))) {
/*     */         
/* 484 */         if (unitEnvironmentInstaller == null) {
/* 485 */           unitEnvironmentInstaller = getEnvInstallerVersion(requestedEnvironmentVersion);
/*     */         }
/* 487 */         po.onProgress((ProgressInfo)pi.setMessage(this.labelResource.getLabel("updating.environment")));
/* 488 */         updateEnv(unitEnvironmentInstaller, po, pi);
/*     */       } else {
/* 490 */         log.debug("...installed environment version is ok");
/*     */       } 
/*     */ 
/*     */       
/* 494 */       File smartStartDir = getSmartStartDir();
/*     */       
/* 496 */       IDownloadUnit duSmartStart = null;
/*     */       
/* 498 */       checkInterruption();
/* 499 */       po.onProgress((ProgressInfo)pi.setMessage(this.labelResource.getLabel("checking.starter.version")));
/*     */       
/* 501 */       String installedSmStVersion = SmartStartVersion.getInstance().getInstalledSmartStartVersion();
/* 502 */       log.debug("...installed smartstart version: " + String.valueOf(installedSmStVersion));
/*     */ 
/*     */       
/* 505 */       String requestedSmartStartVersion = this.cfg.getProperty("smart.start.version");
/* 506 */       log.debug("...requested smartstart version is: " + String.valueOf(requestedSmartStartVersion));
/* 507 */       if (Util.isNullOrEmpty(requestedSmartStartVersion)) {
/* 508 */         duSmartStart = getNewestSmartStartVersion();
/* 509 */         requestedSmartStartVersion = duSmartStart.getVersionNumber().toString();
/* 510 */         log.debug("...setting requested version to newest: " + requestedSmartStartVersion);
/*     */       } 
/*     */       
/* 513 */       VersionNumber installedSmartStartVN = Util.parseVersionNumber(installedSmStVersion);
/* 514 */       VersionNumber requestedSmartStartVN = Util.parseVersionNumber(requestedSmartStartVersion);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 521 */       if (requestedSmartStartVN != null && !Util.equals(installedSmartStartVN, requestedSmartStartVN) && (installedSmartStartVN == null || Util.isHigher((Comparable)requestedSmartStartVN, (Comparable)installedSmartStartVN) || confirmDowngrade(installedSmartStartVN, requestedSmartStartVN, 2))) {
/* 522 */         if (duSmartStart == null) {
/* 523 */           duSmartStart = getSmartStartVersion(requestedSmartStartVersion);
/*     */         }
/* 525 */         po.onProgress((ProgressInfo)pi.setMessage(this.labelResource.getLabel("updating.starter")));
/* 526 */         updateSmartStart(duSmartStart, po, pi);
/*     */       } else {
/* 528 */         log.debug("...installed smartstart version is ok");
/*     */       } 
/*     */ 
/*     */       
/* 532 */       File jreExe = new File(getJREDir(), "bin/javaw.exe");
/* 533 */       if (!jreExe.exists()) {
/* 534 */         throw new MissingResourceException("unable to find JRE executable (" + jreExe.getAbsolutePath() + ")");
/*     */       }
/*     */       
/* 537 */       File smartStartJar = new File(smartStartDir, "smartstart.jar");
/* 538 */       if (!smartStartJar.exists()) {
/* 539 */         throw new MissingResourceException("unable to find smartstart archive (" + smartStartJar.getAbsolutePath() + ")");
/*     */       }
/*     */ 
/*     */       
/* 543 */       renewLease(po);
/*     */ 
/*     */       
/* 546 */       checkInterruption();
/* 547 */       po.onProgress((ProgressInfo)pi.setMessage(this.labelResource.getLabel("executing.starter")));
/*     */       
/* 549 */       log.debug("wrkaround: loading all download URIs and retrieving proxy settings in order to access from standalone start");
/* 550 */       Collection<URI> uris = ((DownloadManager)this.downloadService).getAllDownloadURIs();
/* 551 */       uris.add(URI.create(this.cfg.getProperty("task.execution.url")));
/*     */       
/* 553 */       ProxyUtil.writeProxyMapFile(ProxyUtil.createProxyMap(uris));
/*     */       
/* 555 */       Properties properties = getTis2webEnvironment();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 564 */       log.debug("...starting smart start");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 588 */       String[] pStrings = toCmdLineProperties(properties);
/* 589 */       String[] cmdArray = new String[4 + pStrings.length];
/* 590 */       cmdArray[0] = jreExe.getCanonicalPath();
/* 591 */       cmdArray[1] = "-cp";
/* 592 */       cmdArray[2] = ".;SmartStart.jar";
/* 593 */       for (int i = 0; i < pStrings.length; i++) {
/* 594 */         cmdArray[3 + i] = pStrings[i];
/*     */       }
/* 596 */       cmdArray[cmdArray.length - 1] = "de.dsa.rts.smartstart.SmartStartMain";
/* 597 */       Process process = Runtime.getRuntime().exec(cmdArray, (String[])null, smartStartDir);
/*     */       
/* 599 */       (new Thread(StreamUtil.connect(new BufferedInputStream(process.getInputStream()), System.out))).start();
/* 600 */       (new Thread(StreamUtil.connect(new BufferedInputStream(process.getErrorStream()), System.err))).start();
/*     */ 
/*     */       
/* 603 */       log.debug("...startup done");
/* 604 */       po.onProgress(ProgressInfo.FINSIHED);
/*     */     } finally {
/* 606 */       cancelDownloads();
/*     */     } 
/*     */   }
/*     */   
/* 610 */   private static Collection KEYS = null;
/*     */   static {
/* 612 */     String[] keys = { "session.id", "server.installation", "task.execution.url", "security.token", "language.id", "bac.code", "cookie", "country.code", "session.timeout", "dwnld.include.appserver", "dtc.upload.disabled", "e.astor", "debug" };
/* 613 */     KEYS = Arrays.asList(keys);
/*     */   }
/*     */   
/*     */   public static Properties getTis2webEnvironment() {
/* 617 */     Properties ret = new Properties();
/* 618 */     for (Iterator<Map.Entry<Object, Object>> iter = System.getProperties().entrySet().iterator(); iter.hasNext(); ) {
/* 619 */       Map.Entry entry = iter.next();
/* 620 */       if (KEYS.contains(entry.getKey())) {
/* 621 */         ret.put(entry.getKey(), entry.getValue());
/*     */       }
/*     */     } 
/* 624 */     return ret;
/*     */   }
/*     */   
/*     */   public static String[] toCmdLineProperties(Properties properties) {
/* 628 */     List<String> tmp = new LinkedList();
/* 629 */     for (Iterator<Map.Entry<Object, Object>> iter = properties.entrySet().iterator(); iter.hasNext(); ) {
/* 630 */       Map.Entry entry = iter.next();
/* 631 */       tmp.add("-D" + entry.getKey() + "=" + entry.getValue());
/*     */     } 
/* 633 */     return tmp.<String>toArray(new String[tmp.size()]);
/*     */   }
/*     */   
/*     */   private String getDefaultDir() {
/* 637 */     String parentDir = null;
/*     */     
/*     */     try {
/* 640 */       parentDir = System.getenv("ProgramData");
/* 641 */       if (parentDir == null) {
/* 642 */         parentDir = System.getenv("ProgramFiles");
/*     */       }
/* 644 */     } catch (Throwable t) {}
/*     */ 
/*     */     
/* 647 */     if (parentDir == null || parentDir.length() == 0) {
/* 648 */       StringTokenizer tok = new StringTokenizer(System.getProperty("java.home"), File.separator);
/* 649 */       String drive = null;
/* 650 */       String tlDir = null;
/*     */       try {
/* 652 */         drive = tok.nextToken();
/* 653 */         tlDir = tok.nextToken();
/* 654 */         parentDir = drive + File.separator + tlDir;
/* 655 */       } catch (Exception e) {
/* 656 */         parentDir = File.separator;
/*     */       } 
/*     */     } 
/* 659 */     return (new File(parentDir, "GDS")).getAbsolutePath();
/*     */   }
/*     */   
/*     */   private String getUninstallerLanguage() {
/* 663 */     String result = null;
/*     */     try {
/* 665 */       String localeStr = this.locale.toString();
/* 666 */       for (int i = 0; i < fqLanguages.length; i++) {
/* 667 */         if (localeStr.compareTo(fqLanguages[i]) == 0) {
/* 668 */           result = localeStr;
/*     */           break;
/*     */         } 
/*     */       } 
/* 672 */       if (result == null) {
/* 673 */         result = this.locale.getLanguage();
/*     */       }
/* 675 */     } catch (Exception e) {}
/*     */ 
/*     */     
/* 678 */     if (result == null) {
/* 679 */       result = "en";
/*     */     }
/* 681 */     return result;
/*     */   }
/*     */   
/*     */   private ProgressInfo toProgressInfo(final String messageKey) {
/* 685 */     return new ProgressInfo() {
/*     */         public String toString() {
/* 687 */           return KDRClient.this.labelResource.getMessage(messageKey);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   private String getSessionID() {
/* 694 */     return this.cfg.getProperty("session.id");
/*     */   }
/*     */ 
/*     */   
/*     */   private void renewLease(ProgressObserver po) throws Exception {
/* 699 */     log.debug("renewing lease ...");
/* 700 */     po.onProgress(toProgressInfo("renewing.lease"));
/*     */     
/* 702 */     TaskExecutionClient tec = TaskExecutionClientFactory.createTaskExecutionClient();
/* 703 */     DLSService service = getDLSService();
/* 704 */     SoftwareKey swk = service.getSoftwareKey();
/* 705 */     String sessionID = getSessionID();
/* 706 */     if (swk == null) {
/* 707 */       log.debug("computing and authorizing software key ...");
/* 708 */       DLSManagement dlsMngmt = (DLSManagement)service;
/*     */       
/* 710 */       swk = dlsMngmt.computeSoftwareKey();
/*     */       
/* 712 */       if (Util.isNullOrEmpty(sessionID)) {
/* 713 */         throw new IllegalStateException();
/*     */       }
/* 715 */       RegisterSWKTask task = new RegisterSWKTask(swk, sessionID);
/*     */       
/* 717 */       if (!RegisterSWKTask.evaluateResult(tec.execute((Task)task))) {
/* 718 */         throw new Exception("unable to register swk");
/*     */       }
/* 720 */       dlsMngmt.registerSoftwareKey(swk);
/*     */       
/* 722 */       log.debug("...done");
/*     */     } else {
/*     */       
/* 725 */       RegisterSWKTask task = new RegisterSWKTask(swk, sessionID);
/*     */       
/* 727 */       if (!RegisterSWKTask.evaluateResult(tec.execute((Task)task))) {
/* 728 */         throw new Exception("unable to register swk");
/*     */       }
/* 730 */       log.debug("verified software key ");
/*     */     } 
/*     */     
/* 733 */     log.debug("requesting lease");
/* 734 */     LeaseRequest leaseRequest = new LeaseRequest(swk, getSessionID());
/* 735 */     Lease lease = LeaseRequest.resolveResult(tec.execute((Task)leaseRequest));
/* 736 */     log.debug("registering lease: " + String.valueOf(lease));
/* 737 */     ((DLSManagement)service).registerLease(lease, tec.getURL());
/*     */   }
/*     */   
/*     */   private void cancelDownloads() {
/* 741 */     synchronized (this.SYNC_DWNLD_SRV) {
/* 742 */       if (this.downloadService != null) {
/* 743 */         this.downloadService.cancelAll();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean installDirAccepted(String path) {
/* 749 */     boolean result = false;
/* 750 */     boolean passed_1 = false;
/* 751 */     boolean passed_2 = false;
/* 752 */     boolean passed_3 = false;
/* 753 */     File instDir = new File(path);
/*     */     
/* 755 */     if (instDir.isAbsolute() && !instDir.isFile() && instDir.getParent() != null) {
/* 756 */       passed_1 = true;
/*     */     } else {
/* 758 */       log.info("Path not absolute or existing file selected for installation directory: " + path);
/*     */     } 
/*     */     
/* 761 */     if (passed_1) {
/* 762 */       if (!instDir.exists() || (instDir.exists() && instDir.isDirectory() && (instDir.list()).length == 0)) {
/* 763 */         passed_2 = true;
/*     */       } else {
/* 765 */         log.info("Installation directory exists but is not empty: " + path);
/*     */       } 
/*     */     }
/*     */     
/* 769 */     if (passed_2) {
/* 770 */       if (System.getProperty("os.name").toUpperCase(Locale.ENGLISH).indexOf("VISTA") < 0) {
/* 771 */         passed_3 = true;
/*     */       } else {
/* 773 */         StringTokenizer strTok = new StringTokenizer(System.getProperty("excluded.dirs.vista"), ",");
/* 774 */         StringBuffer expandedDirs = new StringBuffer();
/* 775 */         while (strTok.hasMoreElements()) {
/* 776 */           String expDir = System.getenv(strTok.nextToken().replaceAll("%", ""));
/* 777 */           expandedDirs.append(expDir);
/* 778 */           expandedDirs.append(";");
/*     */         } 
/* 780 */         log.debug("Excluded directories by configuration: " + expandedDirs.toString());
/* 781 */         strTok = new StringTokenizer(expandedDirs.toString(), ";");
/* 782 */         boolean failed_3 = false;
/* 783 */         while (strTok.hasMoreElements()) {
/* 784 */           if (path.toUpperCase().indexOf(strTok.nextToken().toUpperCase()) >= 0) {
/* 785 */             failed_3 = true;
/* 786 */             log.info("Installation directory is in exclude list: " + path);
/*     */             break;
/*     */           } 
/*     */         } 
/* 790 */         if (!failed_3) {
/* 791 */           passed_3 = true;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 796 */     if (passed_3 && hasSufficientDiskSpace(instDir)) {
/* 797 */       result = true;
/*     */     }
/* 799 */     log.info("Installion directory " + (result ? "accepted." : "not accepted."));
/* 800 */     return result;
/*     */   }
/*     */   
/*     */   private boolean hasSufficientDiskSpace(File instDir) {
/* 804 */     boolean result = false;
/* 805 */     long availableSpace = getAvailableSpace(instDir);
/* 806 */     long requestedSpace = 157286400L;
/*     */     try {
/* 808 */       requestedSpace = Long.parseLong(System.getProperty("required.disk.space")) * 1024L * 1024L;
/* 809 */     } catch (Exception e) {
/* 810 */       log.debug("Error when determining requested disk space (using default instead): " + e.toString());
/*     */     } 
/* 812 */     log.info("Available disk space[bytes]: " + availableSpace + " / Requested disk space[bytes]: " + requestedSpace);
/*     */     
/* 814 */     if (requestedSpace <= availableSpace) {
/* 815 */       result = true;
/*     */     }
/*     */     
/* 818 */     return result;
/*     */   }
/*     */   
/*     */   private long getAvailableSpace(File dir) {
/* 822 */     long availableSpace = -1L;
/* 823 */     Method theMethod = null;
/* 824 */     log.debug("Running on " + System.getProperty("java.version") + ".");
/* 825 */     File testDir = dir;
/*     */     
/* 827 */     File parentDir = null;
/* 828 */     while (!testDir.exists()) {
/* 829 */       parentDir = testDir.getParentFile();
/* 830 */       if (parentDir == null || parentDir.getPath().compareTo(testDir.getPath()) == 0) {
/* 831 */         log.error("Cannot find existing parent directory: " + testDir);
/*     */         break;
/*     */       } 
/* 834 */       testDir = parentDir;
/*     */     } 
/* 836 */     if (testDir.exists()) {
/* 837 */       log.debug("Checking available disk space for path: " + testDir);
/*     */       try {
/* 839 */         theMethod = File.class.getMethod("getUsableSpace", (Class[])null);
/* 840 */         if (theMethod != null) {
/* 841 */           availableSpace = ((Long)theMethod.invoke(testDir, (Object[])null)).longValue();
/*     */         }
/* 843 */       } catch (Exception e) {
/* 844 */         log.debug("Method getUsableSpace() not implemented in class java.io.File: " + e.toString() + " (probably not Java 1.6+)");
/*     */       } 
/*     */       
/* 847 */       if (availableSpace == -1L) {
/* 848 */         log.debug("Using native method to determine disk space.");
/* 849 */         String drive = null;
/*     */         try {
/* 851 */           int driveSep = dir.getCanonicalPath().indexOf(":");
/* 852 */           if (driveSep > 0) {
/* 853 */             drive = dir.getCanonicalPath().substring(0, driveSep + 1);
/*     */           } else {
/* 855 */             drive = System.getenv("SystemDrive");
/*     */           } 
/* 857 */           availableSpace = SystemInfo.getInstance().getFreeSpace(drive);
/*     */         }
/* 859 */         catch (Exception e) {
/* 860 */           log.error("Unable to verify  disk space requirements: " + e, e);
/*     */         } 
/*     */       } 
/* 863 */       log.debug("Available disk space for path [" + dir.getPath() + "]: " + availableSpace);
/*     */     } else {
/* 865 */       log.error("Could not find existing ancestor directory (returning -1L): " + dir);
/*     */     } 
/* 867 */     return availableSpace;
/*     */   }
/*     */   
/*     */   public KDRClient() {}
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\kdr\client\KDRClient.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */