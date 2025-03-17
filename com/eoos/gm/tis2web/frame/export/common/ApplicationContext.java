/*     */ package com.eoos.gm.tis2web.frame.export.common;
/*     */ 
/*     */ import com.eoos.datatype.marker.Configurable;
/*     */ import com.eoos.gm.tis2web.dtc.implementation.export.DTCExportManager;
/*     */ import com.eoos.gm.tis2web.frame.export.ConfigurationServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.ConfiguredServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.Address;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDFactory;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.ConfigurationService;
/*     */ import com.eoos.gm.tis2web.frame.login.log.export.ExportManager;
/*     */ import com.eoos.gm.tis2web.help.service.HelpServiceProvider;
/*     */ import com.eoos.gm.tis2web.news.service.NewsServiceProvider;
/*     */ import com.eoos.html.base.ApplicationContext;
/*     */ import com.eoos.instantiation.Singleton;
/*     */ import com.eoos.log.AppenderProxy;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.propcfg.util.ConfigurationUtil;
/*     */ import com.eoos.resource.loading.DirectoryResourceLoading;
/*     */ import com.eoos.resource.loading.ResourceLoading;
/*     */ import com.eoos.resource.loading.ResourceLoadingChain;
/*     */ import com.eoos.scsm.v2.io.StreamUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.ClassUtil;
/*     */ import com.eoos.util.IDFactory;
/*     */ import com.eoos.util.Task;
/*     */ import com.eoos.util.Transforming;
/*     */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.InetAddress;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.log4j.Appender;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ @SuppressWarnings({"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"})
/*     */ public class ApplicationContext
/*     */   extends ApplicationContext
/*     */   implements Configuration
/*     */ {
/*  58 */   private static final Logger log = Logger.getLogger(ApplicationContext.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   private static ApplicationContext instance = null;
/*     */   
/*  66 */   private IDFactory idfactory = new IDFactory();
/*     */   
/*  68 */   private String ipAddr = null;
/*     */   
/*  70 */   private String hostName = null;
/*     */   
/*  72 */   private Set localHostDenotations = new LinkedHashSet();
/*     */   
/*     */   private String deploymentName;
/*     */   
/*  76 */   private Set shutdownListeners = new LinkedHashSet();
/*     */   
/*  78 */   private Configuration configurationDelegate = null;
/*     */   
/*  80 */   private Long sessionTimeout = null;
/*     */   
/*  82 */   private Long sessionTimeoutPublicAccess = null;
/*     */   
/*  84 */   private Boolean developMode = null;
/*     */   
/*  86 */   private final Object SYNC_CLUSTERURLS = new Object();
/*     */   
/*  88 */   private Set clusterURLs = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String SYNC_BN;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String buildNumber;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AtomicReference<String> arServerUrl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AtomicInteger port;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long tsStartup;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized Configuration getConfigurationDelegate() {
/* 127 */     if (this.configurationDelegate == null) {
/* 128 */       this.configurationDelegate = (Configuration)FrameServiceProvider.getInstance().getService(ConfigurationService.class);
/*     */     }
/* 130 */     return this.configurationDelegate;
/*     */   }
/*     */   
/*     */   public void onShutdown() {
/* 134 */     Iterator<ShutdownListener> iter = this.shutdownListeners.iterator();
/* 135 */     while (iter.hasNext()) {
/* 136 */       ((ShutdownListener)iter.next()).onShutdown();
/*     */     }
/* 138 */     this.shutdownListeners.clear();
/* 139 */     Util.signalShutdown();
/* 140 */     super.onShutdown();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addShutdownListener(ShutdownListener listener) {
/* 145 */     return this.shutdownListeners.add(listener);
/*     */   }
/*     */   
/*     */   public static synchronized ApplicationContext getInstance() {
/* 149 */     if (instance == null) {
/* 150 */       instance = new ApplicationContext();
/*     */     }
/* 152 */     return instance;
/*     */   }
/*     */   
/*     */   public IDFactory getIDFactory() {
/* 156 */     return (IDFactory)this.idfactory;
/*     */   }
/*     */   
/*     */   public void setDeploymentName(String deploymentName) {
/* 160 */     this.deploymentName = deploymentName;
/*     */   }
/*     */   
/*     */   public String getDeploymentName() {
/* 164 */     return this.deploymentName;
/*     */   }
/*     */   
/*     */   public String getIPAddr() {
/* 168 */     return this.ipAddr;
/*     */   }
/*     */   
/*     */   public String getHostName() {
/* 172 */     return this.hostName;
/*     */   }
/*     */   
/*     */   public void onStartup() {
/* 176 */     onStartup(-1);
/*     */   }
/*     */   
/*     */   public void onStartup(final int port) {
/* 180 */     Thread t = new Thread(new Runnable()
/*     */         {
/*     */           public void run() {
/*     */             try {
/* 184 */               if (port != -1) {
/* 185 */                 ApplicationContext.getInstance().setPort(port);
/*     */               } else {
/* 187 */                 ApplicationContext.log.debug("sending port identification request to cluster...");
/*     */                 
/* 189 */                 ClusterTaskExecution cte = new ClusterTaskExecution((Task)new CTSetPort(), null);
/* 190 */                 cte.execute();
/*     */                 
/* 192 */                 int maxTry = 12;
/* 193 */                 while (!ApplicationContext.this.isPortSet() && --maxTry >= 0) {
/* 194 */                   ApplicationContext.log.debug("...port has not been set yet, waiting 10 sec and repeating cluster task");
/* 195 */                   Thread.sleep(10000L);
/* 196 */                   if (!ApplicationContext.this.isPortSet()) {
/* 197 */                     cte.execute();
/*     */                   }
/*     */                 } 
/*     */               } 
/*     */               
/* 202 */               if (!ApplicationContext.this.isPortSet()) {
/* 203 */                 throw Util.toRuntimeException(new IllegalStateException("unable to send/retrieve port identification request"));
/*     */               }
/* 205 */               ApplicationContext.log.debug("local port: " + ApplicationContext.this.getPort());
/* 206 */               ApplicationContext.log.debug("local server URL: " + ApplicationContext.this.getLocalURL());
/*     */ 
/*     */               
/* 209 */               Log4JInitializationFacade.getInstance().init(ApplicationContext.this);
/*     */ 
/*     */               
/* 212 */               ConfigurationServiceProvider.getService().dumpConfiguration();
/*     */               
/*     */               try {
/* 215 */                 ApplicationContext.log.info("initializing DTC export");
/* 216 */                 DTCExportManager.getInstance().init();
/* 217 */               } catch (Exception e) {
/* 218 */                 ApplicationContext.log.warn("unable to start DTC export - exception:" + e, e);
/*     */               } 
/* 220 */               ApplicationContext.log.info("initializing configured service provider");
/* 221 */               ConfiguredServiceProvider.getInstance().init();
/*     */               
/*     */               try {
/* 224 */                 ApplicationContext.log.info("starting export manager for login log");
/* 225 */                 ExportManager.getInstance().start();
/* 226 */               } catch (Exception e) {
/* 227 */                 ApplicationContext.log.warn("unable to start login log export manager - exception:" + e, e);
/*     */               } 
/*     */               
/* 230 */               NewsServiceProvider.getInstance().getService();
/* 231 */               HelpServiceProvider.getInstance().getService();
/*     */ 
/*     */               
/*     */               try {
/* 235 */                 SubConfigurationWrapper subConfigurationWrapper = new SubConfigurationWrapper(ApplicationContext.this, "frame.startup.task.");
/*     */                 
/* 237 */                 List<Comparable> tmp = new ArrayList(subConfigurationWrapper.getKeys());
/* 238 */                 Collections.sort(tmp);
/* 239 */                 for (Iterator<Comparable> iter = tmp.iterator(); iter.hasNext(); ) {
/* 240 */                   String key = (String)iter.next();
/* 241 */                   if (key.endsWith(".class")) {
/* 242 */                     String startupTaskKey = key.substring(0, key.length() - 6);
/* 243 */                     ApplicationContext.log.info("....initializing startup task: " + startupTaskKey);
/* 244 */                     SubConfigurationWrapper subConfigurationWrapper1 = new SubConfigurationWrapper((Configuration)subConfigurationWrapper, startupTaskKey + ".");
/*     */                     try {
/* 246 */                       Class<?> clazz = Class.forName(subConfigurationWrapper.getProperty(key));
/* 247 */                       Collection interfaces = ClassUtil.getAllInterfaces(clazz);
/* 248 */                       Task task = null;
/* 249 */                       if (interfaces.contains(Configurable.class)) {
/* 250 */                         if (interfaces.contains(Singleton.class)) {
/* 251 */                           task = (Task)clazz.getMethod("createInstance", new Class[] { Configuration.class }).invoke(null, new Object[] { subConfigurationWrapper1 });
/*     */                         } else {
/* 253 */                           task = clazz.getConstructor(new Class[] { Configuration.class }).newInstance(new Object[] { subConfigurationWrapper1 });
/*     */                         }
/*     */                       
/* 256 */                       } else if (interfaces.contains(Singleton.class)) {
/* 257 */                         task = (Task)clazz.getMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
/*     */                       } else {
/* 259 */                         task = (Task)clazz.newInstance();
/*     */                       } 
/*     */ 
/*     */                       
/* 263 */                       ApplicationContext.log.info("...executing startup task: " + startupTaskKey);
/* 264 */                       task.execute();
/*     */                     }
/* 266 */                     catch (Exception e) {
/* 267 */                       ApplicationContext.log.warn("...unable to initialize subservice: " + startupTaskKey + " - exception:" + e, e);
/*     */                     } 
/*     */                   } 
/*     */                 } 
/* 271 */               } catch (Exception e) {
/* 272 */                 ApplicationContext.log.warn("unable to execute startup tasks - exception:" + e, e);
/*     */               } 
/* 274 */             } catch (InterruptedException e) {
/* 275 */               Thread.currentThread().interrupt();
/*     */             } 
/*     */           }
/*     */         });
/* 279 */     t.setPriority(10);
/* 280 */     t.start();
/*     */   }
/*     */   public static interface ShutdownListener {
/*     */     void onShutdown(); }
/*     */   protected boolean isPortSet() {
/* 285 */     return (this.port.get() != -1);
/*     */   }
/*     */   
/*     */   public String getProperty(String key) {
/* 289 */     return getConfigurationDelegate().getProperty(key);
/*     */   }
/*     */   
/*     */   public Set getKeys() {
/* 293 */     return getConfigurationDelegate().getKeys();
/*     */   }
/*     */   
/*     */   public long getSessionTimeout(boolean publicAccess) {
/* 297 */     if (publicAccess) {
/* 298 */       return getSessionTimeoutPublicAccess();
/*     */     }
/* 300 */     return getSessionTimeoutNormal();
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized long getSessionTimeoutNormal() {
/* 305 */     if (this.sessionTimeout == null) {
/*     */       try {
/* 307 */         this.sessionTimeout = Long.valueOf(Long.parseLong(getProperty("frame.session.timeout")) * 60L * 1000L);
/* 308 */       } catch (Exception e) {
/* 309 */         log.warn("unable to parse configured session timeout, using default (one hour) - exception: " + e, e);
/* 310 */         this.sessionTimeout = Long.valueOf(3600000L);
/*     */       } 
/*     */     }
/* 313 */     return this.sessionTimeout.longValue();
/*     */   }
/*     */   
/*     */   private synchronized long getSessionTimeoutPublicAccess() {
/* 317 */     if (this.sessionTimeoutPublicAccess == null) {
/*     */       try {
/* 319 */         this.sessionTimeoutPublicAccess = Long.valueOf(Long.parseLong(getProperty("frame.session.timeout.public.access")) * 60L * 1000L);
/* 320 */       } catch (Exception e) {
/* 321 */         log.warn("unable to parse configured session timeout for public session, using default (five minutes) - exception: " + e, e);
/* 322 */         this.sessionTimeoutPublicAccess = Long.valueOf(300000L);
/*     */       } 
/*     */     }
/* 325 */     return this.sessionTimeoutPublicAccess.longValue();
/*     */   }
/*     */   
/*     */   public boolean developMode() {
/* 329 */     if (this.developMode == null) {
/*     */       try {
/* 331 */         this.developMode = ConfigurationUtil.getBoolean("frame.mode.develop", this);
/* 332 */       } catch (Throwable t) {
/* 333 */         this.developMode = Boolean.FALSE;
/*     */       } 
/*     */     }
/* 336 */     return this.developMode.booleanValue();
/*     */   }
/*     */   
/*     */   public Set getClusterURLs() {
/* 340 */     synchronized (this.SYNC_CLUSTERURLS) {
/* 341 */       if (this.clusterURLs == null) {
/* 342 */         log.info("reading cluster servers urls");
/* 343 */         this.clusterURLs = new LinkedHashSet();
/* 344 */         SubConfigurationWrapper subConfigurationWrapper = new SubConfigurationWrapper(getInstance(), "frame.url.cluster.server.");
/* 345 */         for (Iterator<String> iter = subConfigurationWrapper.getKeys().iterator(); iter.hasNext(); ) {
/* 346 */           String strUrl = subConfigurationWrapper.getProperty(iter.next());
/*     */           try {
/* 348 */             URL url = new URL(strUrl);
/* 349 */             this.clusterURLs.add(url);
/* 350 */             log.info("....added server url:" + url);
/* 351 */           } catch (MalformedURLException e) {
/* 352 */             log.error("...ignoring malformed url: " + strUrl);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 357 */       return this.clusterURLs;
/*     */     } 
/*     */   }
/*     */   
/*     */   public URL getLocalURL() {
/* 362 */     for (Iterator<URL> iter = getClusterURLs().iterator(); iter.hasNext(); ) {
/* 363 */       URL url = iter.next();
/* 364 */       if (isLocalURL(url)) {
/* 365 */         return url;
/*     */       }
/*     */     } 
/* 368 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isLocalURL(URL url) {
/* 372 */     boolean ret = false;
/* 373 */     if (url != null && url.getHost() != null) {
/* 374 */       for (Iterator<String> iter = this.localHostDenotations.iterator(); iter.hasNext() && !ret; ) {
/* 375 */         String host = iter.next();
/* 376 */         ret = (ret || url.getHost().equalsIgnoreCase(host));
/* 377 */         ret = (ret || host.startsWith(url.getHost()));
/* 378 */         ret = (ret || url.getHost().startsWith(host));
/*     */       } 
/*     */       
/* 381 */       ret = (ret && getPort() == url.getPort());
/*     */     } 
/* 383 */     return ret;
/*     */   }
/*     */   
/*     */   public boolean isStandalone() {
/* 387 */     return ("standalone".equalsIgnoreCase(getProperty("frame.installation.type")) || "server".equalsIgnoreCase(getProperty("frame.installation.type")));
/*     */   }
/*     */   
/*     */   public boolean isStandaloneServer() {
/* 391 */     return "server".equalsIgnoreCase(getProperty("frame.installation.type"));
/*     */   }
/*     */   
/*     */   public String getFullKey(String key) {
/* 395 */     return getConfigurationDelegate().getFullKey(key);
/*     */   }
/*     */   
/* 398 */   private ApplicationContext() { this.SYNC_BN = new String();
/*     */     
/* 400 */     this.buildNumber = this.SYNC_BN;
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
/* 424 */     this.arServerUrl = new AtomicReference<String>();
/*     */     
/* 426 */     this.port = new AtomicInteger(80);
/*     */     
/* 428 */     this.tsStartup = System.currentTimeMillis(); log.debug("determining local net address(es) ..."); Address addr = new Address(); this.ipAddr = addr.getHostAddress(); this.hostName = addr.getHostName(); this.localHostDenotations.add("127.0.0.1"); this.localHostDenotations.add("localhost"); if (this.hostName != null) this.localHostDenotations.add(this.hostName);  if (this.ipAddr != null) this.localHostDenotations.add(this.ipAddr);  if (addr.getCanonicalHostName() != null)
/*     */       this.localHostDenotations.add(addr.getCanonicalHostName());  try { InetAddress[] addresses = InetAddress.getAllByName(addr.getCanonicalHostName()); if (!Util.isNullOrEmpty(addresses))
/*     */         for (int i = 0; i < addresses.length; i++)
/* 431 */           this.localHostDenotations.add(addresses[i].getHostAddress());   } catch (Exception e) { log.warn("unable to check, ignoring - exception: " + e, e); }  log.debug("...local address(es)/names: " + this.localHostDenotations); this.port.set(-1); } public String getServerURL() { String ret = this.arServerUrl.get();
/* 432 */     if (ret == null) {
/* 433 */       ret = getProperty("tis2web.server.url").toLowerCase(Locale.ENGLISH).trim();
/* 434 */       if (!ret.endsWith("/")) {
/* 435 */         ret = ret + "/";
/*     */       }
/* 437 */       if (ConfigurationUtil.getBoolean("frame.disable.https", this).booleanValue()) {
/* 438 */         ret = ret.replaceFirst("(?i)https://", "http://");
/*     */       }
/*     */       
/* 441 */       this.arServerUrl.compareAndSet(null, ret);
/*     */     } 
/*     */     
/* 444 */     return ret; }
/*     */   public String getBuildNumber() { synchronized (this.SYNC_BN) { if (this.buildNumber == this.SYNC_BN) { this.buildNumber = null; InputStream is = getClass().getClassLoader().getResourceAsStream("build.number"); if (is != null)
/*     */           try { Properties properties = new Properties(); properties.load(is); this.buildNumber = properties.getProperty("build.number"); } catch (IOException e) { log.error("unable to read build number file, ignoring - exception: " + e, e); } finally { StreamUtil.close(is, log); }   }
/*     */        return this.buildNumber; }
/* 448 */      } public static void localStartup(File projectDir, String env, int port) { AppenderProxy appender = new AppenderProxy("Console", -1L);
/* 449 */     Logger.getRootLogger().addAppender((Appender)appender);
/*     */     
/*     */     try {
/* 452 */       DirectoryResourceLoading directoryResourceLoading = new DirectoryResourceLoading(new File(projectDir, "sources/war/resources"));
/*     */       
/* 454 */       ResourceLoading rl2 = Util.isNullOrEmpty(env) ? ResourceLoading.DUMMY : (ResourceLoading)new DirectoryResourceLoading(new File(projectDir, "build/env/" + env + "/overwrite/resources"));
/*     */       
/* 456 */       FrameServiceProvider.create((ResourceLoading)new ResourceLoadingChain(new ResourceLoading[] { (ResourceLoading)directoryResourceLoading, rl2 }));
/* 457 */       getInstance().onStartup(port);
/* 458 */     } catch (Exception e) {
/* 459 */       throw new RuntimeException(e);
/*     */     }  }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPort(int serverPort) {
/* 465 */     this.port.set(serverPort);
/* 466 */     log.debug("set port to : " + serverPort);
/*     */   }
/*     */   
/*     */   public int getPort() {
/* 470 */     return this.port.get();
/*     */   }
/*     */   
/*     */   public Collection<String> getLocalhostDenotations() {
/* 474 */     return Util.transformCollection(this.localHostDenotations, new Transforming<String, String>()
/*     */         {
/*     */           public String transform(String object) {
/* 477 */             return Util.normalize(object);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/* 482 */   private static final Pattern P1 = Pattern.compile("(?i)https?://");
/*     */   
/*     */   public boolean isLocalServer(String server) {
/* 485 */     if (!Util.isNullOrEmpty(server)) {
/* 486 */       String exportServerName = null;
/* 487 */       int exportServerPort = 80;
/* 488 */       Matcher matcher = P1.matcher(server);
/* 489 */       if (matcher.find() && matcher.start() == 0) {
/*     */         try {
/* 491 */           URL url = new URL(server);
/* 492 */           exportServerName = Util.normalize(url.getHost());
/* 493 */           exportServerPort = url.getPort();
/* 494 */         } catch (MalformedURLException e) {
/* 495 */           log.warn("could not parse server spec - exception:", e);
/*     */         } 
/*     */       } else {
/*     */         
/* 499 */         String[] parts = server.split(":");
/* 500 */         if (!Util.isNullOrEmpty(parts)) {
/* 501 */           exportServerName = Util.normalize(parts[0]);
/* 502 */           if (parts.length > 1) {
/* 503 */             exportServerPort = Integer.parseInt(parts[1]);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 508 */       boolean ret = (exportServerPort == getInstance().getPort());
/* 509 */       ret = (ret && getInstance().getLocalhostDenotations().contains(exportServerName));
/* 510 */       return ret;
/*     */     } 
/* 512 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getStartupTimestamp() {
/* 517 */     return this.tsStartup;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\ApplicationContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */