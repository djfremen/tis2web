/*     */ package com.eoos.gm.tis2web.lt.icop.cfgclient;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.dls.SessionKey;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.ExpiredLeaseException;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.MissingLeaseException;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.SoftwareKey;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.DownloadServiceFactory;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IDownloadFile;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IDownloadPackage;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IDownloadService;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IDownloadUnit;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.ClassificationFilter;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.VersionFilter;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.WellKnownFilter;
/*     */ import com.eoos.gm.tis2web.lt.icop.cfgclient.ui.CfgDialog;
/*     */ import com.eoos.gm.tis2web.lt.icop.cfgclient.ui.UICallback;
/*     */ import com.eoos.io.StreamUtil;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.scsm.v2.io.StreamUtil;
/*     */ import com.eoos.scsm.v2.swing.UIUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.scsm.v2.util.VersionNumber;
/*     */ import com.eoos.util.Transforming;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.LineNumberReader;
/*     */ import java.math.BigInteger;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import javax.swing.JOptionPane;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class ICOPCfgClient
/*     */ {
/*  51 */   private static final Logger log = Logger.getLogger(ICOPCfgClient.class);
/*     */   
/*  53 */   private static final ClassificationFilter CLASSIFICATION_ICOPCLIENT = ClassificationFilter.create("ICOPCLIENT");
/*     */   
/*     */   private File homeDir;
/*  56 */   private final Object SYNC_DWNLD_SRV = new Object();
/*     */   
/*     */   private IDownloadService downloadService;
/*     */   
/*     */   private Configuration cfg;
/*     */   
/*     */   private Locale locale;
/*     */   
/*     */   private LabelResource labelResource;
/*     */   
/*  66 */   private File icopClientHome = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ICOPCfgClient(File homeDir, Configuration cfg, Locale locale, LabelResource labelResource) {
/*  73 */     this.homeDir = homeDir;
/*     */     
/*  75 */     this.cfg = cfg;
/*  76 */     this.locale = locale;
/*  77 */     this.labelResource = labelResource;
/*     */   }
/*     */ 
/*     */   
/*     */   private IDownloadService getDownloadService() throws ExpiredLeaseException, MissingLeaseException {
/*  82 */     synchronized (this.SYNC_DWNLD_SRV) {
/*  83 */       if (this.downloadService == null) {
/*  84 */         File wrkDir = new File(this.homeDir, ".wrk");
/*  85 */         this.downloadService = DownloadServiceFactory.createInstance((SoftwareKey)new SessionKey(getSessionID()), null, wrkDir, null);
/*     */       } 
/*  87 */       return this.downloadService;
/*     */     } 
/*     */   }
/*     */   
/*     */   private synchronized File getICOPClientHome() {
/*  92 */     if (this.icopClientHome == null) {
/*     */       
/*  94 */       File file = new File(this.homeDir, "icop.client.home");
/*  95 */       if (file.exists()) {
/*  96 */         String home = null;
/*     */         try {
/*  98 */           FileInputStream fis = new FileInputStream(file);
/*     */           try {
/* 100 */             LineNumberReader lnr = new LineNumberReader(new InputStreamReader(fis));
/* 101 */             home = lnr.readLine();
/* 102 */             if (!Util.isNullOrEmpty(home)) {
/* 103 */               this.icopClientHome = new File(home);
/*     */             }
/* 105 */             lnr.close();
/*     */           } finally {
/* 107 */             fis.close();
/*     */           }
/*     */         
/* 110 */         } catch (Exception e) {
/* 111 */           log.error("unable to determine ICOP client home dir - exception:", e);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 116 */     return this.icopClientHome;
/*     */   }
/*     */ 
/*     */   
/*     */   private VersionNumber getInstalledVersion() {
/* 121 */     VersionNumber ret = null;
/*     */     try {
/* 123 */       File clientHome = getICOPClientHome();
/* 124 */       if (clientHome != null) {
/* 125 */         File versionFile = new File(clientHome, "version.txt");
/* 126 */         if (versionFile.exists()) {
/* 127 */           LineNumberReader lnr = new LineNumberReader(new InputStreamReader(new FileInputStream(versionFile), Charset.forName("utf-8")));
/*     */           try {
/* 129 */             ret = Util.parseVersionNumber(lnr.readLine());
/*     */           } finally {
/* 131 */             lnr.close();
/*     */           }
/*     */         
/*     */         } 
/*     */       } 
/* 136 */     } catch (Exception e) {
/* 137 */       log.error("unable to determine installed version, returning null - exception: ", e);
/*     */     } 
/* 139 */     return ret;
/*     */   }
/*     */   
/*     */   private String getRemainingTimeDisplayString(long remainingTime) {
/* 143 */     BigInteger remaining = BigInteger.valueOf(remainingTime);
/* 144 */     BigInteger[] tmp = Util.getMinutes(remaining);
/* 145 */     BigInteger minutes = tmp[0];
/* 146 */     tmp = Util.getSeconds(tmp[1]);
/* 147 */     BigInteger seconds = tmp[0];
/* 148 */     StringBuffer time = new StringBuffer();
/* 149 */     if (minutes.longValue() > 0L) {
/* 150 */       time.append(minutes.longValue() + " min, ");
/*     */     }
/* 152 */     time.append(seconds.longValue() + " sec");
/* 153 */     return this.labelResource.getLabel("remaining.time") + ": " + time.toString();
/*     */   }
/*     */   
/*     */   private void update(final IDownloadUnit duICOPClient, final UIUtil.ProgressObserver po) throws Exception {
/* 157 */     log.debug("...updating client");
/*     */     
/* 159 */     po.setProgress(this.labelResource.getMessage("downloading.installer") + " V" + duICOPClient.getVersionNumber() + " ...");
/*     */     
/* 161 */     File targetDir = new File(this.homeDir, "installer.dwnld");
/*     */     
/* 163 */     log.debug("...downloading installer to dir: " + String.valueOf(targetDir));
/* 164 */     final IDownloadPackage pkg = getDownloadService().createPackage(Collections.singleton(duICOPClient), targetDir);
/* 165 */     Thread poUpdater = Util.createAndStartThread(new Runnable() {
/*     */           public void run() {
/*     */             try {
/* 168 */               Thread.sleep(2000L);
/*     */               while (true) {
/* 170 */                 Thread.sleep(2000L);
/*     */                 
/*     */                 try {
/* 173 */                   long remainingTime = ICOPCfgClient.this.getDownloadService().getStatus(pkg).getRemainingTimeEstimate();
/* 174 */                   po.setProgress(ICOPCfgClient.this.labelResource.getMessage("downloading.installer") + " V" + duICOPClient.getVersionNumber() + " (" + ICOPCfgClient.this.getRemainingTimeDisplayString(remainingTime) + ")");
/* 175 */                 } catch (Exception e) {
/* 176 */                   ICOPCfgClient.log.error("unable to retrieve remaining time, ignoring - exception : " + e, e);
/*     */                 }
/*     */               
/*     */               } 
/* 180 */             } catch (InterruptedException e) {
/* 181 */               Thread.currentThread().interrupt();
/*     */               return;
/*     */             }  }
/*     */         });
/*     */     try {
/* 186 */       getDownloadService().downloadPackage(pkg);
/*     */     } finally {
/* 188 */       poUpdater.interrupt();
/*     */     } 
/* 190 */     File installerDir = new File(this.homeDir, "installer");
/* 191 */     if (installerDir.exists() && 
/* 192 */       !Util.deleteDir(installerDir)) {
/* 193 */       throw new IllegalStateException("unable to delete directory: " + installerDir);
/*     */     }
/*     */     
/* 196 */     if (!targetDir.renameTo(installerDir)) {
/* 197 */       throw new IllegalStateException("unable to rename directory: " + targetDir + " to " + installerDir);
/*     */     }
/*     */     
/* 200 */     po.setProgress(this.labelResource.getMessage("installing.icop.client"));
/* 201 */     log.debug("....executing the installer");
/*     */     
/* 203 */     IDownloadFile file = null;
/* 204 */     IDownloadFile.IExecutable startFile = null;
/* 205 */     for (Iterator<IDownloadFile> iter = duICOPClient.getFiles().iterator(); iter.hasNext() && startFile == null; ) {
/* 206 */       file = iter.next();
/* 207 */       if (file instanceof IDownloadFile.IExecutable) {
/* 208 */         startFile = (IDownloadFile.IExecutable)file;
/*     */       }
/*     */     } 
/* 211 */     if (startFile == null) {
/* 212 */       throw new IllegalStateException("no starter information found");
/*     */     }
/*     */     
/* 215 */     StringBuffer cmdLine = new StringBuffer();
/* 216 */     cmdLine.append(startFile.getExecutablePath());
/* 217 */     if (!Util.isNullOrEmpty(startFile.getCmdLineParams())) {
/* 218 */       cmdLine.append(" ").append(startFile.getCmdLineParams());
/*     */     }
/*     */     
/* 221 */     Process process = Runtime.getRuntime().exec(new String[] { "cmd.exe", "/C", cmdLine.toString() }, (String[])null, installerDir);
/*     */     
/* 223 */     process.getOutputStream().close();
/*     */ 
/*     */ 
/*     */     
/* 227 */     Util.createAndStartThread(StreamUtil.connect(new BufferedInputStream(process.getInputStream()), System.out));
/* 228 */     Util.createAndStartThread(StreamUtil.connect(new BufferedInputStream(process.getErrorStream()), System.err));
/*     */     
/* 230 */     log.debug("...waiting for termination");
/*     */     
/* 232 */     int status = process.waitFor();
/* 233 */     String lgMsg = "installer process status code: " + status;
/* 234 */     if (status == 1 && System.getProperty("os.name").toUpperCase().indexOf("VISTA") >= 0) {
/* 235 */       lgMsg = lgMsg + " (no elevation?)";
/*     */     }
/* 237 */     log.debug(lgMsg);
/* 238 */     if (!Util.isNullOrEmpty(startFile.getFailureCodes()) && Util.parseList(startFile.getFailureCodes()).contains(String.valueOf(status))) {
/* 239 */       throw new IllegalStateException("installer failed");
/*     */     }
/*     */     
/* 242 */     po.setProgress(this.labelResource.getMessage("done.installing.client"));
/*     */   }
/*     */ 
/*     */   
/*     */   private IDownloadUnit getNewestICOPClientVersion() throws Exception {
/* 247 */     log.debug("...retrieving newest ICOP client version");
/* 248 */     Collection<ClassificationFilter> filter = new HashSet();
/* 249 */     filter.add(CLASSIFICATION_ICOPCLIENT);
/* 250 */     filter.add(WellKnownFilter.NEWEST_VERSION);
/* 251 */     return (IDownloadUnit)CollectionUtil.getFirst(getDownloadService().getDownloadUnits(filter));
/*     */   }
/*     */   
/*     */   private IDownloadUnit getICOPClientVersion(String version) throws Exception {
/* 255 */     Collection<ClassificationFilter> filter = new HashSet();
/* 256 */     filter.add(CLASSIFICATION_ICOPCLIENT);
/* 257 */     filter.add(new VersionFilter(version));
/* 258 */     return (IDownloadUnit)CollectionUtil.getFirst(getDownloadService().getDownloadUnits(filter));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean confirmDowngrade(final VersionNumber installedVersion, final VersionNumber requestedVersion) throws InterruptedException {
/* 264 */     StringBuffer msg = new StringBuffer(this.labelResource.getMessage("confirm.downgrade"));
/* 265 */     Util.replace(msg, Util.PATTERN_VARIABLE, new Util.ReplacementCallback()
/*     */         {
/*     */           public CharSequence getReplacement(CharSequence match, Util.ReplacementCallback.MatcherCallback matcherCallback) {
/* 268 */             String name = matcherCallback.getGroup(1);
/* 269 */             if (name.equalsIgnoreCase("installed.version.number"))
/* 270 */               return installedVersion.toString(); 
/* 271 */             if (name.equalsIgnoreCase("requested.version.number")) {
/* 272 */               return requestedVersion.toString();
/*     */             }
/* 274 */             return match;
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 279 */     int result = JOptionPane.showConfirmDialog(null, msg.toString());
/* 280 */     if (result == 2)
/* 281 */       throw new InterruptedException("user abortion"); 
/* 282 */     if (result == 1)
/* 283 */       return false; 
/* 284 */     if (result == 0) {
/* 285 */       return true;
/*     */     }
/* 287 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   
/*     */   private String getSessionID() {
/* 292 */     return this.cfg.getProperty("session.id");
/*     */   }
/*     */ 
/*     */   
/*     */   private void cancelDownloads() {
/* 297 */     synchronized (this.SYNC_DWNLD_SRV) {
/* 298 */       if (this.downloadService != null) {
/* 299 */         this.downloadService.cancelAll();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void checkInstallation(UIUtil.ProgressObserver po) throws Exception {
/* 305 */     IDownloadUnit duICOPClientInstaller = null;
/*     */     
/* 307 */     VersionNumber installedVersion = getInstalledVersion();
/* 308 */     log.debug("...installed version: " + String.valueOf(installedVersion));
/*     */     
/* 310 */     String _requestedVersion = this.cfg.getProperty("target.version");
/* 311 */     log.debug("...requested version is: " + String.valueOf(_requestedVersion));
/* 312 */     if (Util.isNullOrEmpty(_requestedVersion)) {
/* 313 */       duICOPClientInstaller = getNewestICOPClientVersion();
/* 314 */       if (duICOPClientInstaller != null) {
/* 315 */         _requestedVersion = String.valueOf(duICOPClientInstaller.getVersionNumber());
/* 316 */         log.debug("...setting requested version to newest: " + _requestedVersion);
/*     */       } 
/*     */     } 
/*     */     
/* 320 */     VersionNumber requestedVersion = Util.parseVersionNumber(_requestedVersion);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 327 */     if (requestedVersion != null && !Util.equals(installedVersion, requestedVersion) && (installedVersion == null || Util.isHigher((Comparable)requestedVersion, (Comparable)installedVersion))) {
/*     */       
/* 329 */       if (duICOPClientInstaller == null) {
/* 330 */         duICOPClientInstaller = getICOPClientVersion(requestedVersion.toString());
/*     */       }
/* 332 */       update(duICOPClientInstaller, po);
/*     */     } else {
/* 334 */       log.debug("...installed environment version is ok");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Properties loadICOPClientCfg() {
/* 340 */     File file = new File(this.homeDir, "config.properties");
/* 341 */     if (file.exists()) {
/*     */       try {
/* 343 */         return Util.readProperties(file);
/* 344 */       } catch (IOException e) {
/* 345 */         log.error("unable to read configuration file, ignoring - exception: ", e);
/*     */       } 
/*     */     }
/* 348 */     return new Properties();
/*     */   }
/*     */   
/*     */   private void writeICOPClientCfg(Properties properties) {
/* 352 */     File propertiesFile = new File(this.homeDir, "config.properties");
/*     */     try {
/* 354 */       String timeout = System.getProperty("communication.timeout");
/* 355 */       if (!Util.isNullOrEmpty(timeout)) {
/* 356 */         properties.put("COMMUNICATIONTIMEOUT", timeout);
/*     */       }
/* 358 */       Util.writeProperties(propertiesFile, properties);
/* 359 */     } catch (IOException e) {
/* 360 */       log.error("unable to write settings to file: " + propertiesFile + " - exception: ", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Map<String, String> getServerOptions() {
/* 365 */     List<String> options = null;
/* 366 */     Map<String, String> serverMap = null;
/*     */     
/* 368 */     File file = new File(this.homeDir, "server.options");
/* 369 */     if (file.exists()) {
/*     */       try {
/* 371 */         options = StreamUtil.readTextFile(file, Charset.forName("utf-8"));
/* 372 */       } catch (IOException e) {
/* 373 */         throw new RuntimeException(e);
/*     */       } 
/*     */     } else {
/* 376 */       options = new LinkedList<String>();
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 381 */       boolean update = false;
/* 382 */       serverMap = list2Map(options);
/* 383 */       for (String option : Util.fromExternal(this.cfg.getProperty("options.server"))) {
/* 384 */         String sName = getKey(option);
/* 385 */         String sUrl = getValue(option);
/* 386 */         if (!serverMap.containsKey(sName) || serverMap.get(sName) == null || ((String)serverMap.get(sName)).compareTo(sUrl) != 0) {
/* 387 */           serverMap.put(sName, sUrl);
/* 388 */           update = true;
/*     */         } 
/*     */       } 
/*     */       
/* 392 */       if (update) {
/*     */         
/* 394 */         options = map2List(serverMap);
/* 395 */         StreamUtil.writeTextFile(file, Charset.forName("utf-8"), options);
/*     */       } 
/* 397 */     } catch (Exception e) {
/* 398 */       log.error("unable to retrieve server options from jnlp, ignoring - exception: " + e, e);
/*     */     } 
/* 400 */     return serverMap;
/*     */   }
/*     */ 
/*     */   
/*     */   public void execute() throws Exception {
/*     */     try {
/* 406 */       UIUtil.ProgressObserver po = UIUtil.showProgressObserver(null, this.labelResource, this.labelResource.getMessage("checking.icop.installation.status"));
/*     */       try {
/* 408 */         checkInstallation(po);
/*     */         
/* 410 */         po.setProgress(null, 1, 5);
/* 411 */         final Properties settings = loadICOPClientCfg();
/*     */         
/* 413 */         po.setProgress(null, 2, 5);
/* 414 */         final Map<String, String> serverMap = getServerOptions();
/* 415 */         final List<String> servers = new ArrayList<String>(serverMap.keySet());
/* 416 */         Collections.sort(servers, Util.COMPARATOR_TOSTRING);
/*     */         
/* 418 */         po.setProgress(null, 3, 5);
/* 419 */         final List<String> makes = (List<String>)Util.fromExternal(this.cfg.getProperty("options.make"));
/* 420 */         Collections.sort(makes, Util.COMPARATOR_TOSTRING);
/*     */         
/* 422 */         po.setProgress(null, 4, 5);
/* 423 */         final Map<Locale, String> _countries = filterDups((Map<Locale, String>)Util.fromExternal(this.cfg.getProperty("options.country")));
/* 424 */         final List<?> countries = new LinkedList(_countries.keySet());
/* 425 */         Collections.sort(countries, Util.createTransformingComparator(new Transforming()
/*     */               {
/*     */                 public Object transform(Object object) {
/* 428 */                   return ((Locale)object).getDisplayCountry(ICOPCfgClient.this.locale);
/*     */                 }
/*     */               }));
/*     */         
/* 432 */         po.setProgress(null, 5, 5);
/* 433 */         final Map<Locale, String> _languages = (Map<Locale, String>)Util.fromExternal(this.cfg.getProperty("options.language"));
/* 434 */         final List<?> languages = new LinkedList(_languages.keySet());
/* 435 */         Collections.sort(languages, Util.createTransformingComparator(new Transforming()
/*     */               {
/*     */                 public Object transform(Object object) {
/* 438 */                   return ((Locale)object).getDisplayName(ICOPCfgClient.this.locale);
/*     */                 }
/*     */               }));
/*     */         
/* 442 */         po.close();
/*     */         
/* 444 */         UICallback callback = new UICallback()
/*     */           {
/*     */             public String toString(Object value) {
/* 447 */               return String.valueOf(value);
/*     */             }
/*     */             
/*     */             public void onOK(UICallback.Selection selection) {
/* 451 */               Properties properties = new Properties();
/* 452 */               for (UICallback.ID id : UICallback.ID.values()) {
/* 453 */                 if (selection.getSelection(id) != null) {
/* 454 */                   if (id == UICallback.ID.COUNTRY) {
/* 455 */                     properties.put(id.toString(), _countries.get(selection.getSelection(id)));
/* 456 */                   } else if (id == UICallback.ID.LANGUAGE) {
/* 457 */                     properties.put(id.toString(), _languages.get(selection.getSelection(id)));
/* 458 */                   } else if (id == UICallback.ID.SERVER) {
/* 459 */                     String key = (String)selection.getSelection(id);
/* 460 */                     serverMap.get(key);
/* 461 */                     properties.put(id.toString(), serverMap.get(key));
/*     */                   } else {
/* 463 */                     properties.put(id.toString(), selection.getSelection(id));
/*     */                   } 
/*     */                 }
/*     */               } 
/* 467 */               ICOPCfgClient.this.writeICOPClientCfg(properties);
/*     */             }
/*     */ 
/*     */             
/*     */             public void onCancel() {}
/*     */             
/*     */             public List getOptions(UICallback.ID id) {
/* 474 */               switch (id) {
/*     */                 case SERVER:
/* 476 */                   return servers;
/*     */                 case MAKE:
/* 478 */                   return makes;
/*     */                 case COUNTRY:
/* 480 */                   return countries;
/*     */                 case LANGUAGE:
/* 482 */                   return languages;
/*     */               } 
/*     */               
/* 485 */               throw new IllegalStateException();
/*     */             }
/*     */ 
/*     */             
/*     */             public String getLabel(String key) {
/* 490 */               return ICOPCfgClient.this.labelResource.getLabel(key);
/*     */             }
/*     */             
/*     */             public Object getCurrentValue(UICallback.ID id) {
/* 494 */               Object ret = settings.getProperty(id.toString());
/* 495 */               if (ret != null && id == UICallback.ID.COUNTRY) {
/* 496 */                 for (Iterator<Map.Entry> iter = _countries.entrySet().iterator(); iter.hasNext(); ) {
/* 497 */                   Map.Entry entry = iter.next();
/* 498 */                   if (ret.equals(entry.getValue())) {
/* 499 */                     return entry.getKey();
/*     */                   }
/*     */                 } 
/* 502 */                 ret = null;
/* 503 */               } else if (id == UICallback.ID.LANGUAGE && ret != null) {
/* 504 */                 for (Iterator<Map.Entry> iter = _languages.entrySet().iterator(); iter.hasNext(); ) {
/* 505 */                   Map.Entry entry = iter.next();
/* 506 */                   if (ret.equals(entry.getValue())) {
/* 507 */                     return entry.getKey();
/*     */                   }
/*     */                 } 
/* 510 */                 ret = null;
/* 511 */               } else if (id == UICallback.ID.SERVER && ret != null) {
/* 512 */                 for (Iterator<Map.Entry> iter = serverMap.entrySet().iterator(); iter.hasNext(); ) {
/* 513 */                   Map.Entry entry = iter.next();
/* 514 */                   if (ret.equals(entry.getValue())) {
/* 515 */                     return entry.getKey();
/*     */                   }
/*     */                 } 
/* 518 */                 ret = null;
/*     */               } 
/* 520 */               return ret;
/*     */             }
/*     */ 
/*     */             
/*     */             public Locale getLocale() {
/* 525 */               return ICOPCfgClient.this.locale;
/*     */             }
/*     */           };
/*     */         
/* 529 */         CfgDialog.show(callback);
/*     */       } finally {
/*     */         
/* 532 */         po.close();
/*     */       } 
/*     */     } finally {
/*     */       
/* 536 */       cancelDownloads();
/*     */     } 
/*     */   }
/*     */   
/*     */   private Map<String, String> list2Map(List<String> strList) {
/* 541 */     Map<String, String> result = new HashMap<String, String>();
/* 542 */     String option = null;
/*     */     try {
/* 544 */       for (int i = 0; i < strList.size(); i++) {
/* 545 */         option = strList.get(i);
/* 546 */         String sName = option.substring(0, option.indexOf("="));
/* 547 */         String sUrl = option.substring(option.indexOf("=") + 1);
/* 548 */         result.put(sName, sUrl);
/*     */       } 
/* 550 */     } catch (Exception e) {
/* 551 */       log.error("Cannot convert list entry [" + option + "] to map: " + e, e);
/*     */     } 
/* 553 */     return result;
/*     */   }
/*     */   
/*     */   private List<String> map2List(Map<String, String> map) {
/* 557 */     List<String> result = new ArrayList<String>();
/* 558 */     Iterator<String> it = map.keySet().iterator();
/* 559 */     while (it.hasNext()) {
/* 560 */       String key = it.next();
/* 561 */       result.add(key + "=" + (String)map.get(key));
/*     */     } 
/* 563 */     return result;
/*     */   }
/*     */   
/*     */   private String getKey(String str) {
/* 567 */     String result = null;
/*     */     try {
/* 569 */       result = str.substring(0, str.indexOf("="));
/* 570 */     } catch (Exception e) {
/* 571 */       log.error("getServerName(" + str + ") failed: " + e);
/*     */     } 
/* 573 */     return result;
/*     */   }
/*     */   
/*     */   private String getValue(String str) {
/* 577 */     String result = null;
/*     */     try {
/* 579 */       result = str.substring(str.indexOf("=") + 1, str.length());
/* 580 */     } catch (Exception e) {
/* 581 */       log.error("getServerUrl(" + str + ") failed: " + e);
/*     */     } 
/* 583 */     return result;
/*     */   }
/*     */   
/*     */   private Map<Locale, String> filterDups(Map<Locale, String> map) {
/* 587 */     Map<Locale, String> result = new HashMap<Locale, String>();
/* 588 */     Iterator<Locale> it = map.keySet().iterator();
/* 589 */     while (it.hasNext()) {
/* 590 */       Locale loc = it.next();
/* 591 */       if (!containsCountry(result, loc.getCountry())) {
/* 592 */         result.put(loc, map.get(loc));
/*     */       }
/*     */     } 
/* 595 */     return result;
/*     */   }
/*     */   
/*     */   private boolean containsCountry(Map<Locale, String> map, String country) {
/* 599 */     boolean result = false;
/* 600 */     Set<String> countries = new HashSet<String>();
/* 601 */     Iterator<Locale> it = map.keySet().iterator();
/* 602 */     while (it.hasNext()) {
/* 603 */       countries.add(((Locale)it.next()).getCountry());
/*     */     }
/* 605 */     if (countries.contains(country)) {
/* 606 */       result = true;
/*     */     }
/* 608 */     return result;
/*     */   }
/*     */   
/*     */   public ICOPCfgClient() {}
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\icop\cfgclient\ICOPCfgClient.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */