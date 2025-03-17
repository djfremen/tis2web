/*     */ package com.eoos.gm.tis2web.frame.dwnld.client;
/*     */ 
/*     */ import com.eoos.file.FileUtil;
/*     */ import com.eoos.gm.tis2web.common.AuthenticationQuery;
/*     */ import com.eoos.gm.tis2web.common.AuthenticatorCI;
/*     */ import com.eoos.gm.tis2web.common.MissingAuthenticationException;
/*     */ import com.eoos.gm.tis2web.common.TaskExecutionClient;
/*     */ import com.eoos.gm.tis2web.common.TaskExecutionClientFactory;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.DLSService;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.DLSServiceFactory;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.Lease;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.SoftwareKey;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.DownloadObserverAdapter;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.DwnldFilter;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IDownloadFile;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IDownloadPackage;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IDownloadService;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IDownloadStatus;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IDownloadUnit;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IGDSFacade;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.Identifier;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.DownloadFile;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.DownloadServer;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.DownloadUnit;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.IDownloadServer;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.IdentifierAdapter;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.VersionFilter;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.WellKnownFilter;
/*     */ import com.eoos.idfactory.IDFactory;
/*     */ import com.eoos.idfactory.SystemTimeBasedIDFactory;
/*     */ import com.eoos.math.AverageCalculator;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.util.SystemPropertiesAdapter;
/*     */ import com.eoos.scsm.v2.io.OutputStreamByteCount;
/*     */ import com.eoos.scsm.v2.io.StreamUtil;
/*     */ import com.eoos.scsm.v2.util.Counter;
/*     */ import com.eoos.scsm.v2.util.ICounter;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.v2.StopWatch;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileFilter;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.math.BigDecimal;
/*     */ import java.net.Authenticator;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.security.DigestException;
/*     */ import java.security.DigestInputStream;
/*     */ import java.security.DigestOutputStream;
/*     */ import java.security.MessageDigest;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class DownloadManager
/*     */   implements IDownloadService {
/*  71 */   private static final Logger log = Logger.getLogger(DownloadManager.class); private static final DownloadServer DWNLDSERVER_APPSERVER;
/*     */   private static final String MD_ALGO = "MD5";
/*     */   
/*     */   static {
/*     */     try {
/*  76 */       DWNLDSERVER_APPSERVER = new DownloadServer(new URL("http://appserver"), false, "Application Server");
/*  77 */     } catch (Exception e) {
/*  78 */       throw new Error(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  84 */   private IDFactory idFactory = null;
/*     */   
/*  86 */   private ServerFacade serverFacade = null;
/*     */   
/*  88 */   private File wrkDir = null;
/*     */   
/*     */   private static final String DIRPREFIX_PKG = "pkg.";
/*     */   
/*  92 */   private Map idToPkg = Collections.synchronizedMap(new HashMap<Object, Object>());
/*     */   
/*  94 */   private Map pkgToDownloadThread = Collections.synchronizedMap(new HashMap<Object, Object>());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   private AverageCalculator averageFileProcessingSpeed = new AverageCalculator(6);
/*     */   
/*     */   private Configuration configuration;
/*     */   
/* 103 */   private final Object SYNC_DLS = new Object();
/*     */   
/* 105 */   private DLSService dlsService = null;
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
/*     */   private final Object SYNC_APPSERVERMODE;
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
/*     */   private String appServerDwnldMode;
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
/*     */   public DownloadManager(SoftwareKey swk, Lease lease, File wrkDir, AuthenticationQuery callback, Configuration configuration) {
/* 153 */     this(swk, lease, wrkDir, callback, configuration, null);
/*     */   }
/*     */ 
/*     */   
/*     */   private DLSService getDLSService() {
/* 158 */     synchronized (this.SYNC_DLS) {
/* 159 */       if (this.dlsService == null) {
/* 160 */         this.dlsService = DLSServiceFactory.createService(null);
/*     */       }
/* 162 */       return this.dlsService;
/*     */     } 
/*     */   }
/*     */   
/*     */   public File getWrkDir() {
/* 167 */     return this.wrkDir;
/*     */   }
/*     */   
/*     */   private Identifier createIdentifier() {
/* 171 */     return (Identifier)new IdentifierAdapter(this.idFactory.createID());
/*     */   }
/*     */ 
/*     */   
/*     */   private File getPkgDir(Identifier identifier) {
/* 176 */     return new File(this.wrkDir, "pkg." + identifier);
/*     */   }
/*     */   
/*     */   public IDownloadPackage createPackage(Collection units, File destDir) throws IOException {
/* 180 */     if (log.isDebugEnabled()) {
/* 181 */       log.debug("creating download package for unit: " + units + ", destination directory: " + destDir);
/*     */     }
/*     */     
/* 184 */     DownloadPackage ret = new DownloadPackage(createIdentifier(), units, destDir);
/*     */     
/* 186 */     File pkgDir = getPkgDir(ret.getIdentifier());
/* 187 */     log.debug("...creating internal package directory: " + pkgDir);
/* 188 */     if (!pkgDir.mkdirs()) {
/* 189 */       throw new IllegalStateException("unable to create directory: " + pkgDir);
/*     */     }
/*     */     
/* 192 */     log.debug("...storing package meta data");
/* 193 */     File meta = new File(pkgDir, ".meta");
/* 194 */     FileOutputStream fos = new FileOutputStream(meta);
/* 195 */     ObjectOutputStream oos = new ObjectOutputStream(fos);
/*     */     try {
/* 197 */       oos.writeObject(ret);
/*     */     } finally {
/* 199 */       oos.close();
/*     */     } 
/*     */     
/* 202 */     this.idToPkg.put(ret.getIdentifier(), ret);
/*     */     
/* 204 */     log.debug("...done creating package");
/* 205 */     return ret;
/*     */   }
/*     */   
/*     */   public IDownloadPackage getPackage(Identifier identifier) {
/* 209 */     log.debug("retrieving download package for id: " + identifier);
/*     */     
/* 211 */     DownloadPackage ret = (DownloadPackage)this.idToPkg.get(identifier);
/* 212 */     if (ret == null) {
/* 213 */       File metaFile = new File(getPkgDir(identifier), ".meta");
/* 214 */       log.debug("...reading meta data from: " + metaFile);
/* 215 */       if (!metaFile.exists()) {
/* 216 */         log.warn("unable to retrieve package for id:" + identifier + " (no corresponding meta info file found)");
/* 217 */         ret = null;
/*     */       } else {
/*     */         try {
/* 220 */           ret = readPackage(metaFile);
/* 221 */           this.idToPkg.put(identifier, ret);
/* 222 */         } catch (Exception e) {
/* 223 */           throw new RuntimeException("unable to retrieve package for id: " + identifier + " (unable to read meta data)", e);
/*     */         } 
/*     */       } 
/*     */     } 
/* 227 */     log.debug("...done retrieving package");
/* 228 */     return ret;
/*     */   }
/*     */   
/*     */   private IDownloadService.DownloadObserver toDownloadObserver(IDownloadService.DownloadObserver observer) {
/*     */     final IDownloadService.DownloadObserver delegate;
/* 233 */     if (observer == null) {
/* 234 */       DownloadObserverAdapter downloadObserverAdapter = DownloadObserverAdapter.DUMMY;
/*     */     } else {
/* 236 */       delegate = DwnldObserverAsyncNotification.create(observer);
/*     */     } 
/*     */     
/* 239 */     return new IDownloadService.DownloadObserver() {
/* 240 */         private long tsStart = System.currentTimeMillis();
/*     */         
/*     */         public void onFinished(IDownloadPackage pkg, IDownloadUnit unit, IDownloadFile file) {
/* 243 */           delegate.onFinished(pkg, unit, file);
/*     */         }
/*     */         
/*     */         public void onFinished(IDownloadPackage pkg, IDownloadUnit unit) {
/* 247 */           delegate.onFinished(pkg, unit);
/*     */         }
/*     */         
/*     */         public void onFinished(final IDownloadPackage pkg) {
/* 251 */           final long tsEnd = System.currentTimeMillis();
/* 252 */           Util.createAndStartThread(new Runnable()
/*     */               {
/*     */                 public void run() {
/*     */                   try {
/* 256 */                     DownloadManager.this.serverFacade.logDwnldEvent(DownloadManager.null.this.tsStart, tsEnd, pkg.getUnits(), "SUCCESS");
/* 257 */                   } catch (Exception e) {
/* 258 */                     DownloadManager.log.error("unable to log event - exception: " + e, e);
/*     */                   } 
/*     */                 }
/*     */               });
/* 262 */           delegate.onFinished(pkg);
/*     */         }
/*     */         
/*     */         public void onError(final IDownloadPackage pkg, Exception e) {
/* 266 */           final long tsEnd = System.currentTimeMillis();
/* 267 */           Util.createAndStartThread(new Runnable()
/*     */               {
/*     */                 public void run() {
/*     */                   try {
/* 271 */                     DownloadManager.this.serverFacade.logDwnldEvent(DownloadManager.null.this.tsStart, tsEnd, pkg.getUnits(), "ERROR");
/* 272 */                   } catch (Exception e) {
/* 273 */                     DownloadManager.log.error("unable to log event - exception: " + e, e);
/*     */                   } 
/*     */                 }
/*     */               });
/* 277 */           delegate.onError(pkg, e);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   void startDownload(final DownloadPackage pkg, final IDownloadService.DownloadObserver _observer) {
/* 284 */     log.debug("...starting download for packacke: " + pkg);
/* 285 */     Thread t = (Thread)this.pkgToDownloadThread.get(pkg);
/* 286 */     if (t == null || !t.isAlive()) {
/* 287 */       log.debug("...creating and starting download thread");
/* 288 */       t = new Thread("download thread for package: " + pkg) {
/*     */           public void run() {
/* 290 */             DownloadManager.log.debug("download thread " + this + " is running");
/* 291 */             IDownloadService.DownloadObserver observer = DownloadManager.this.toDownloadObserver(_observer);
/*     */             try {
/* 293 */               DownloadManager.this.downloadPkg(pkg, observer);
/* 294 */               DownloadManager.log.debug("...finished downloading, notifying observer");
/* 295 */               observer.onFinished(pkg);
/* 296 */             } catch (InterruptedException e) {
/* 297 */               DownloadManager.log.warn("...download has been interrupted !");
/* 298 */             } catch (Exception e) {
/* 299 */               DownloadManager.log.warn("...unable to download, notifying observer - exception:" + e, e);
/* 300 */               observer.onError(pkg, e);
/*     */             } finally {
/* 302 */               DownloadManager.log.debug("...download thread " + this + " is finished");
/* 303 */               DownloadManager.this.pkgToDownloadThread.remove(pkg);
/*     */             } 
/*     */           }
/*     */         };
/*     */       
/* 308 */       this.pkgToDownloadThread.put(pkg, t);
/* 309 */       t.start();
/*     */     } else {
/* 311 */       log.debug("...there is already a working thread, notifying observer with IllegalStateException");
/* 312 */       _observer.onError(pkg, new IllegalStateException("download already in progress"));
/*     */     } 
/* 314 */     log.debug("...done starting download");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFinished(DownloadPackage pkg) {
/* 319 */     Thread t = (Thread)this.pkgToDownloadThread.get(pkg);
/* 320 */     return (t == null || !t.isAlive());
/*     */   }
/*     */   
/*     */   private String toString(Object obj) {
/* 324 */     if (obj == null) {
/* 325 */       return "<null>";
/*     */     }
/* 327 */     return obj.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean handleFile(DownloadFile file, File destFile) throws Exception {
/* 332 */     boolean handle = !destFile.exists();
/* 333 */     handle = (handle || destFile.length() != file.getSize());
/*     */     
/* 335 */     if (!handle) {
/* 336 */       MessageDigest digest = MessageDigest.getInstance("MD5");
/* 337 */       InputStream is = new DigestInputStream(new BufferedInputStream(new FileInputStream(destFile)), digest);
/*     */       try {
/* 339 */         StreamUtil.transfer(is, StreamUtil.NILOutputStream);
/*     */       } finally {
/* 341 */         is.close();
/*     */       } 
/* 343 */       handle = !Arrays.equals(file.getChecksum(), digest.digest());
/* 344 */       if (handle) {
/* 345 */         log.debug("...found corrupt target file: " + destFile + " (checksum validation failed)");
/*     */       }
/*     */     } 
/*     */     
/* 349 */     return handle;
/*     */   }
/*     */   
/*     */   private void updateDownloadSpeed(Counter counter, StopWatch sw) {
/* 353 */     long time = Math.max(1L, sw.getElapsedTime());
/* 354 */     long bytecount = counter.getCount().longValue();
/* 355 */     if (bytecount != 0L) {
/* 356 */       BigDecimal throughput = BigDecimal.valueOf(bytecount).setScale(6).divide(BigDecimal.valueOf(time), 4);
/* 357 */       log.debug("...updating average file processing speed with current throughput: " + throughput + " kB/s");
/* 358 */       this.averageFileProcessingSpeed.add(throughput);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void downloadUnit(DownloadPackage pkg, DownloadUnit unit, IDownloadServer downloadServer, IDownloadService.DownloadObserver observer) throws Exception {
/* 364 */     log.debug("...downloading unit: " + toString(unit) + " from server: " + toString(downloadServer));
/*     */     
/* 366 */     for (Iterator<DownloadFile> iterFiles = unit.getFiles().iterator(); iterFiles.hasNext(); Util.checkInterruption()) {
/* 367 */       DownloadFile file = iterFiles.next();
/* 368 */       log.debug("...handling file: " + toString(file));
/*     */       
/* 370 */       File destFile = getDestinationFile(pkg, (IDownloadUnit)unit, (IDownloadFile)file);
/* 371 */       log.debug("...destination directory for file: " + destFile);
/* 372 */       if (file.isDirectory() && !destFile.exists()) {
/* 373 */         if (destFile.mkdirs()) {
/* 374 */           log.warn("unable to create directory " + String.valueOf(destFile) + ", ignoring");
/*     */         }
/* 376 */       } else if (handleFile(file, destFile)) {
/* 377 */         final StopWatch sw = StopWatch.getInstance().start(); try {
/*     */           OutputStreamByteCount outputStreamByteCount;
/* 379 */           destFile.getParentFile().mkdirs();
/* 380 */           OutputStream os = new FileOutputStream(destFile);
/* 381 */           if (log.isDebugEnabled());
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           try {
/* 387 */             final Counter counter = new Counter();
/* 388 */             outputStreamByteCount = new OutputStreamByteCount(os, (ICounter)counter);
/* 389 */             Thread updateSpeedCalc = Util.createAndStartThread(new Runnable()
/*     */                 {
/*     */                   public void run() {
/*     */                     try {
/*     */                       while (true) {
/* 394 */                         Thread.sleep(1000L);
/* 395 */                         DownloadManager.this.updateDownloadSpeed(counter, sw);
/*     */                       } 
/* 397 */                     } catch (InterruptedException e) {
/* 398 */                       Thread.currentThread().interrupt();
/*     */                       return;
/*     */                     } 
/*     */                   }
/*     */                 });
/*     */             try {
/* 404 */               downloadFile(file, downloadServer, (OutputStream)outputStreamByteCount);
/*     */             } finally {
/* 406 */               updateSpeedCalc.interrupt();
/*     */             } 
/* 408 */             updateDownloadSpeed(counter, sw);
/* 409 */             log.debug("...download successful");
/*     */           } finally {
/*     */             
/* 412 */             outputStreamByteCount.close();
/*     */           } 
/*     */         } finally {
/* 415 */           StopWatch.freeInstance(sw);
/*     */         } 
/*     */       } else {
/*     */         
/* 419 */         log.debug("...file is already downloaded");
/*     */       } 
/* 421 */       if (file.isZIPArchive()) {
/* 422 */         Util.extractZIP(destFile, null);
/* 423 */         destFile.delete();
/*     */       } 
/* 425 */       observer.onFinished(pkg, (IDownloadUnit)unit, (IDownloadFile)file);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List getDownloadServers(DownloadUnit unit) {
/* 431 */     log.debug("...retrieving list of download servers for unit: " + toString(unit));
/* 432 */     if ("exclusive".equalsIgnoreCase(getAppServerDwnldMode())) {
/* 433 */       log.warn("...appserver mode is exclusive, returning application server as download server(s)");
/* 434 */       return Collections.singletonList(DWNLDSERVER_APPSERVER);
/*     */     } 
/* 436 */     List<DownloadServer> tmp = new LinkedList(unit.getDownloadServers());
/* 437 */     boolean includesAppServer = tmp.contains(DWNLDSERVER_APPSERVER);
/* 438 */     if (includesAppServer) {
/* 439 */       while (tmp.remove(DWNLDSERVER_APPSERVER));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 444 */     if (includesAppServer && !"false".equalsIgnoreCase(getAppServerDwnldMode())) {
/* 445 */       tmp.add(DWNLDSERVER_APPSERVER);
/*     */     }
/* 447 */     log.debug("...returning list: " + toString(tmp));
/* 448 */     return tmp;
/*     */   }
/*     */ 
/*     */   
/*     */   public void downloadPkg(DownloadPackage pkg, IDownloadService.DownloadObserver observer) throws Exception {
/* 453 */     log.debug("...downloading package: " + toString(pkg));
/* 454 */     for (Iterator<DownloadUnit> iterUnits = pkg.getUnits().iterator(); iterUnits.hasNext(); ) {
/* 455 */       DownloadUnit unit = iterUnits.next();
/* 456 */       log.debug("...handling unit: " + toString(unit));
/*     */       
/* 458 */       boolean done = false;
/* 459 */       for (Iterator<IDownloadServer> iter = getDownloadServers(unit).iterator(); iter.hasNext() && !done; ) {
/* 460 */         IDownloadServer downloadServer = iter.next();
/*     */         try {
/* 462 */           downloadUnit(pkg, unit, downloadServer, observer);
/* 463 */           done = true;
/* 464 */           log.debug("... successfully downloaded unit, notifying observer");
/* 465 */           observer.onFinished(pkg, (IDownloadUnit)unit);
/* 466 */         } catch (InterruptedException e) {
/* 467 */           log.debug("....download has been interrupted");
/* 468 */           throw e;
/* 469 */         } catch (Exception e) {
/*     */ 
/*     */           
/* 472 */           Util.checkInterruption();
/* 473 */           if (!iter.hasNext()) {
/* 474 */             throw e;
/*     */           }
/* 476 */           log.warn("unable to download unit: " + unit + " from server: " + String.valueOf(downloadServer) + ", trying next download source - exception:" + e, e);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropPackage(IDownloadPackage pkg, boolean clean) {
/* 485 */     log.debug("dropping download package: " + toString(pkg));
/* 486 */     cancelDownload(pkg);
/* 487 */     if (clean) {
/* 488 */       File pkgDir = getPkgDir(pkg.getIdentifier());
/*     */       try {
/* 490 */         log.debug("...deleting package directory: " + toString(pkgDir));
/* 491 */         FileUtil.delete(pkgDir, false);
/* 492 */       } catch (Exception e) {
/* 493 */         throw new RuntimeException(e);
/*     */       } 
/*     */     } 
/* 496 */     this.idToPkg.remove(pkg.getIdentifier());
/* 497 */     log.debug("...done dropping package");
/*     */   }
/*     */   
/*     */   public void cancelDownload(IDownloadPackage pkg) {
/* 501 */     log.debug("...cancelling download for package: " + toString(pkg));
/* 502 */     Thread t = (Thread)this.pkgToDownloadThread.get(pkg);
/* 503 */     if (t != null) {
/* 504 */       log.debug("...interrupting and removing download thread " + t.toString());
/* 505 */       t.interrupt();
/* 506 */       this.pkgToDownloadThread.remove(pkg);
/*     */     } 
/* 508 */     log.debug("...done cancelling download");
/*     */   }
/*     */   
/*     */   public IDownloadStatus getStatus(IDownloadPackage pkg) {
/* 512 */     return DownloadStatusWrapper.getInstance(this, pkg, null);
/*     */   }
/*     */   
/*     */   IDownloadStatus calcStatus(final IDownloadPackage pkg) {
/* 516 */     log.debug("calculating status for package: " + pkg);
/* 517 */     long localBytes = 0L;
/* 518 */     for (Iterator<IDownloadUnit> iterUnits = pkg.getUnits().iterator(); iterUnits.hasNext(); ) {
/* 519 */       IDownloadUnit unit = iterUnits.next();
/*     */       
/* 521 */       IDownloadStatus statusUnit = getStatus(pkg, unit);
/* 522 */       localBytes += statusUnit.getTransferredByteCount();
/*     */     } 
/*     */     
/* 525 */     final long downloadedBytes = localBytes;
/*     */     
/* 527 */     log.debug("...done retrieving package status");
/* 528 */     return new IDownloadStatus()
/*     */       {
/*     */         public long getTransferredByteCount() {
/* 531 */           return downloadedBytes;
/*     */         }
/*     */         
/*     */         public long getRemainingTimeEstimate() {
/* 535 */           return DownloadManager.this.getTimeEstimate(pkg.getTotalBytes() - downloadedBytes);
/*     */         }
/*     */         
/*     */         public int getPercentage() {
/* 539 */           if (downloadedBytes != 0L) {
/* 540 */             return BigDecimal.valueOf(downloadedBytes).setScale(3).divide(BigDecimal.valueOf(pkg.getTotalBytes()), 4).multiply(BigDecimal.valueOf(100L)).intValue();
/*     */           }
/* 542 */           return 0;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IDownloadStatus getStatus(IDownloadPackage pkg, IDownloadUnit unit) {
/* 552 */     return DownloadStatusWrapper.getInstance(this, pkg, unit);
/*     */   }
/*     */   
/*     */   IDownloadStatus calcStatus(IDownloadPackage pkg, final IDownloadUnit unit) {
/* 556 */     log.debug("calculating download status for unit: " + toString(unit));
/*     */     
/* 558 */     long localBytes = 0L;
/*     */     
/* 560 */     for (Iterator<IDownloadFile> iterFiles = unit.getFiles().iterator(); iterFiles.hasNext(); ) {
/* 561 */       IDownloadFile file = iterFiles.next();
/*     */       
/* 563 */       File destFile = getDestinationFile(pkg, unit, file);
/*     */       
/* 565 */       if (destFile.exists()) {
/* 566 */         localBytes += destFile.length();
/*     */       }
/*     */     } 
/*     */     
/* 570 */     final long downloadedBytes = localBytes;
/*     */     
/* 572 */     log.debug("...done retrieving unit status");
/* 573 */     return new IDownloadStatus()
/*     */       {
/*     */         public long getTransferredByteCount() {
/* 576 */           return downloadedBytes;
/*     */         }
/*     */         
/*     */         public long getRemainingTimeEstimate() {
/* 580 */           return DownloadManager.this.getTimeEstimate(unit.getTotalBytes() - downloadedBytes);
/*     */         }
/*     */         
/*     */         public int getPercentage() {
/* 584 */           if (downloadedBytes != 0L) {
/* 585 */             return BigDecimal.valueOf(downloadedBytes).setScale(3).divide(BigDecimal.valueOf(unit.getTotalBytes()), 4).multiply(BigDecimal.valueOf(100L)).intValue();
/*     */           }
/* 587 */           return 0;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private File getDestinationFile(IDownloadPackage pkg, IDownloadUnit unit, IDownloadFile file) {
/* 598 */     File destFile = pkg.getDestinationDir();
/* 599 */     if (!Util.isNullOrEmpty(file.getPath())) {
/* 600 */       destFile = new File(destFile, file.getPath());
/*     */     }
/* 602 */     return new File(destFile, file.getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public void resumeDownloads(IDownloadService.DownloadObserver observer) {
/* 607 */     log.debug("resuming downloads ...");
/* 608 */     for (Iterator<IDownloadPackage> iter = getPackages().iterator(); iter.hasNext();) {
/* 609 */       startOrResumeDwnld(iter.next(), observer);
/*     */     }
/* 611 */     log.debug("...done resuming downloads");
/*     */   }
/*     */   
/*     */   private DownloadPackage readPackage(File metaFile) throws Exception {
/* 615 */     ObjectInputStream ois = new ObjectInputStream(new FileInputStream(metaFile));
/*     */     try {
/* 617 */       return (DownloadPackage)ois.readObject();
/*     */     } finally {
/* 619 */       StreamUtil.close(ois, log);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Collection getPackages() {
/* 624 */     log.debug("retrieving all packages...");
/* 625 */     Collection<DownloadPackage> ret = new HashSet();
/* 626 */     File[] pkgDirs = this.wrkDir.listFiles(new FileFilter()
/*     */         {
/*     */           public boolean accept(File file) {
/* 629 */             return (file.isDirectory() && file.getName().startsWith("pkg."));
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 634 */     for (int i = 0; i < pkgDirs.length; i++) {
/* 635 */       File meta = new File(pkgDirs[i], ".meta");
/* 636 */       log.debug("...reading meta data from: " + toString(meta));
/*     */       try {
/* 638 */         ret.add(readPackage(meta));
/* 639 */       } catch (Exception e) {
/* 640 */         log.error("unable to resume download from file: " + meta + ", skipping - exception: " + e, e);
/*     */       } 
/*     */     } 
/* 643 */     return ret;
/*     */   }
/*     */   
/*     */   public Collection getDownloadUnits(Collection filters) {
/* 647 */     log.debug("retrieving download units...");
/*     */     
/* 649 */     Collection ret = null;
/* 650 */     boolean done = false;
/* 651 */     while (!done) {
/*     */       try {
/* 653 */         ret = this.serverFacade.getDownloadUnits(filters);
/* 654 */         done = true;
/* 655 */       } catch (MissingAuthenticationException e) {
/* 656 */         handleMissingAuthentication(e);
/*     */       } 
/*     */     } 
/* 659 */     log.debug("...done retieving download units, returning " + ret.size() + " units");
/* 660 */     return ret;
/*     */   }
/*     */   
/*     */   public long getTimeEstimate(long byteCount) {
/*     */     long l;
/* 665 */     log.debug("calculating time estimate for : " + byteCount + " bytes");
/* 666 */     log.debug("...current average: " + this.averageFileProcessingSpeed.getCurrentAverage() + " kB/s");
/*     */     try {
/* 668 */       l = BigDecimal.valueOf(1L).setScale(10).divide(this.averageFileProcessingSpeed.getCurrentAverage(), 4).multiply(BigDecimal.valueOf(byteCount)).longValue();
/* 669 */     } catch (ArithmeticException e) {
/* 670 */       l = -1L;
/*     */     } 
/* 672 */     if (log.isDebugEnabled()) {
/* 673 */       log.debug("....result: " + l + " ms");
/*     */     }
/* 675 */     return l;
/*     */   }
/*     */   
/*     */   public DwnldFilter createVersionFilter(String version) {
/* 679 */     return (DwnldFilter)new VersionFilter(version);
/*     */   }
/*     */   
/*     */   public void downloadFile(DownloadFile file, IDownloadServer downloadServer, OutputStream os) throws Exception {
/* 683 */     log.debug("downloading file: " + file + " from server: " + toString(downloadServer));
/* 684 */     FileDataProvider dataProvider = null;
/* 685 */     if (downloadServer == null || downloadServer.equals(DWNLDSERVER_APPSERVER)) {
/* 686 */       dataProvider = this.serverFacade;
/*     */     } else {
/* 688 */       synchronized (FileDataProviderRI.class) {
/*     */         try {
/* 690 */           dataProvider = FileDataProviderRI.getInstance(downloadServer);
/* 691 */         } catch (MissingInstanceException e) {
/* 692 */           dataProvider = e.create(downloadServer, this.configuration, this.serverFacade);
/*     */         } 
/*     */       } 
/*     */     } 
/* 696 */     MessageDigest streamDigest = MessageDigest.getInstance("MD5");
/* 697 */     OutputStream _os = new DigestOutputStream(os, streamDigest);
/*     */     try {
/* 699 */       boolean done = false;
/* 700 */       while (!done) {
/*     */         try {
/* 702 */           log.debug("...transferring data ");
/* 703 */           dataProvider.getData(file, _os);
/* 704 */           done = true;
/* 705 */         } catch (MissingAuthenticationException e) {
/* 706 */           throw e;
/*     */         } 
/*     */       } 
/*     */     } finally {
/*     */       
/* 711 */       _os.flush();
/*     */     } 
/* 713 */     if (file.getChecksum() != null) {
/* 714 */       log.debug("...verifying checksum");
/* 715 */       byte[] calculatedChksum = streamDigest.digest();
/* 716 */       if (!Arrays.equals(file.getChecksum(), calculatedChksum)) {
/* 717 */         log.debug("...verification failed,  throwing DigestException");
/* 718 */         throw new DigestException("calculated digest:" + Util.toHexString(calculatedChksum));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public IDownloadUnit getNewestDownloadUnit(Collection<? extends WellKnownFilter> filters) {
/* 725 */     Collection<WellKnownFilter> _filters = new LinkedList();
/* 726 */     _filters.add(WellKnownFilter.NEWEST_VERSION);
/* 727 */     if (!Util.isNullOrEmpty(filters)) {
/* 728 */       _filters.addAll(filters);
/*     */     }
/* 730 */     Collection<DownloadUnit> result = getDownloadUnits(_filters);
/* 731 */     if (Util.isNullOrEmpty(result)) {
/* 732 */       return null;
/*     */     }
/* 734 */     return (IDownloadUnit)result.iterator().next();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection getRelatedDownloadUnits(Collection downloadUnits) {
/* 740 */     Collection ret = null;
/* 741 */     boolean done = false;
/* 742 */     while (!done) {
/*     */       try {
/* 744 */         ret = this.serverFacade.getRelatedUnits(downloadUnits);
/* 745 */         done = true;
/* 746 */       } catch (MissingAuthenticationException e) {
/* 747 */         handleMissingAuthentication(e);
/*     */       } 
/*     */     } 
/* 750 */     return ret;
/*     */   }
/*     */   
/*     */   public IGDSFacade getGDSFacade() {
/* 754 */     return new GDSFacade(this);
/*     */   }
/*     */   
/*     */   public void startOrResumeDwnld(IDownloadPackage pkg, IDownloadService.DownloadObserver observer) {
/* 758 */     startDownload((DownloadPackage)pkg, observer);
/*     */   }
/*     */   
/*     */   public void download(IDownloadUnit unit, IDownloadFile file, OutputStream os) throws Exception {
/* 762 */     log.debug("downloading file : " + file);
/* 763 */     boolean successful = false;
/* 764 */     for (Iterator<IDownloadServer> iter = ((DownloadUnit)unit).getDownloadServers().iterator(); iter.hasNext() && !successful; ) {
/* 765 */       IDownloadServer downloadServer = iter.next();
/*     */       try {
/* 767 */         downloadFile((DownloadFile)file, downloadServer, os);
/* 768 */         successful = true;
/* 769 */       } catch (Exception e) {
/* 770 */         log.warn("unable to download file: " + String.valueOf(file) + " from server: " + String.valueOf(downloadServer) + " - exception:" + e, e);
/* 771 */         if (!iter.hasNext()) {
/* 772 */           log.error("unable to download file: " + file + ", rethrowing last exception, since there is no more server to try");
/* 773 */           throw new RuntimeException(e);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void downloadPackage(IDownloadPackage pkg) throws Exception {
/* 780 */     log.debug("downloading package: " + toString(pkg) + " synchronously ...");
/* 781 */     final Object sync = new Object();
/*     */     
/* 783 */     final Exception[] aException = new Exception[1];
/* 784 */     synchronized (sync) {
/*     */       
/* 786 */       DownloadObserverAdapter downloadObserverAdapter = new DownloadObserverAdapter()
/*     */         {
/*     */           private void finished() {
/* 789 */             synchronized (sync) {
/* 790 */               sync.notifyAll();
/*     */             } 
/*     */           }
/*     */           
/*     */           public void onFinished(IDownloadPackage pkg) {
/* 795 */             finished();
/*     */           }
/*     */           
/*     */           public void onError(IDownloadPackage pkg, Exception e) {
/* 799 */             aException[0] = e;
/* 800 */             finished();
/*     */           }
/*     */         };
/*     */       
/* 804 */       log.debug("...starting download");
/* 805 */       startOrResumeDwnld(pkg, (IDownloadService.DownloadObserver)downloadObserverAdapter);
/*     */       try {
/* 807 */         log.debug("...waiting for termination");
/* 808 */         sync.wait();
/*     */       } finally {
/* 810 */         cancelDownload(pkg);
/*     */       } 
/*     */     } 
/* 813 */     log.debug("...received termination signal");
/*     */     
/* 815 */     if (aException[0] != null) {
/* 816 */       log.debug("...rethrowing signaled exception");
/* 817 */       throw aException[0];
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void waitForCompletion(IDownloadPackage pkg) {
/* 823 */     log.debug("waiting for completion of package: " + toString(pkg));
/* 824 */     Thread t = (Thread)this.pkgToDownloadThread.get(pkg);
/* 825 */     if (t != null) {
/*     */       try {
/* 827 */         while (t.isAlive())
/*     */         {
/* 829 */           Thread.sleep(1000L);
/*     */         }
/* 831 */       } catch (InterruptedException e) {
/* 832 */         log.debug("...wait has been interrupted, returning");
/* 833 */         Thread.currentThread().interrupt();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void handleMissingAuthentication(MissingAuthenticationException e) {
/* 840 */     throw new RuntimeException(e);
/*     */   }
/*     */   
/*     */   public Collection getAllDownloadURIs() {
/* 844 */     Collection ret = null;
/* 845 */     boolean done = false;
/* 846 */     while (!done) {
/*     */       try {
/* 848 */         ret = this.serverFacade.getAllDownloadURIs();
/* 849 */         done = true;
/* 850 */       } catch (MissingAuthenticationException e) {
/* 851 */         handleMissingAuthentication(e);
/*     */       } 
/*     */     } 
/* 854 */     return ret;
/*     */   }
/*     */   
/* 857 */   public DownloadManager(SoftwareKey swk, Lease lease, File wrkDir, AuthenticationQuery proxyAuthenticationCallback, Configuration configuration, TaskExecutionClient taskExecution) { this.SYNC_APPSERVERMODE = new Object();
/*     */     
/* 859 */     this.appServerDwnldMode = null; log.debug("initializing...."); log.debug("...creating server facade "); this.configuration = (configuration != null) ? configuration : (Configuration)SystemPropertiesAdapter.getInstance(); if (proxyAuthenticationCallback != null)
/*     */       Authenticator.setDefault((Authenticator)new AuthenticatorCI(proxyAuthenticationCallback));  if (taskExecution == null) { log.debug("...retrieving server url"); URL tecURL = null; String _url = configuration.getProperty("task.execution.url"); if (Util.isNullOrEmpty(_url)) { log.debug("...no property found, using lease"); tecURL = getDLSService().getURL(lease); if (tecURL == null)
/*     */           throw new IllegalStateException("unable to retrieve server url");  } else { try { tecURL = new URL(_url); } catch (MalformedURLException e) { throw new RuntimeException("unalbe to retrieve server URL", e); }  }  taskExecution = TaskExecutionClientFactory.createTaskExecutionClient(tecURL); }  this.serverFacade = new ServerFacade(swk, lease, this.configuration, taskExecution); log.debug("...working directory is: " + wrkDir); if (!wrkDir.exists()) { log.debug("...creating it"); wrkDir.mkdirs(); }
/* 862 */      this.wrkDir = wrkDir; log.debug("...initializing average file processing speed calculation"); this.idFactory = (IDFactory)SystemTimeBasedIDFactory.getInstance(); log.debug("...done initializing"); } public String getAppServerDwnldMode() { synchronized (this.SYNC_APPSERVERMODE) {
/* 863 */       if (this.appServerDwnldMode == null) {
/* 864 */         log.debug("determining application server download mode...");
/* 865 */         this.appServerDwnldMode = this.configuration.getProperty("dwnld.include.appserver");
/* 866 */         if (this.appServerDwnldMode == null) {
/* 867 */           boolean done = false;
/* 868 */           while (!done) {
/*     */             try {
/* 870 */               this.appServerDwnldMode = this.serverFacade.getAppServerDwnldMode();
/* 871 */               done = true;
/* 872 */             } catch (MissingAuthenticationException e) {
/* 873 */               handleMissingAuthentication(e);
/*     */             } 
/*     */           } 
/*     */         } else {
/* 877 */           log.debug("...found configuration property...");
/*     */         } 
/* 879 */         log.debug("....value is: " + this.appServerDwnldMode);
/*     */       } 
/*     */       
/* 882 */       return this.appServerDwnldMode;
/*     */     }  }
/*     */ 
/*     */   
/*     */   public void cancelAll() {
/* 887 */     log.debug("cancelling all downloads...");
/* 888 */     for (Iterator<Thread> iter = this.pkgToDownloadThread.values().iterator(); iter.hasNext(); ) {
/* 889 */       Thread t = iter.next();
/* 890 */       log.debug("...interrupting " + t);
/* 891 */       t.interrupt();
/*     */     } 
/* 893 */     this.pkgToDownloadThread.clear();
/*     */   }
/*     */   
/*     */   public boolean isApplicationServer(DownloadServer server) {
/* 897 */     return DWNLDSERVER_APPSERVER.equals(server);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\client\DownloadManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */