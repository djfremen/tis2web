/*     */ package com.eoos.gm.tis2web.frame.export.common.util;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.ConfigurationServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.ConfigurationService;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.propcfg.util.ConfigurationUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DatabaseMetaData;
/*     */ import java.sql.Driver;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.Statement;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.Semaphore;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import java.util.logging.Filter;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.LogRecord;
/*     */ import java.util.logging.Logger;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.InitialContext;
/*     */ import javax.sql.DataSource;
/*     */ import oracle.jdbc.OracleConnection;
/*     */ import oracle.jdbc.driver.OracleConnection;
/*     */ import oracle.jdbc.driver.OracleDriver;
/*     */ import oracle.jdbc.driver.OracleLog;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class DatabaseLink implements IDatabaseLink {
/*  41 */   protected Logger log = Logger.getLogger(DatabaseLink.class);
/*     */   
/*  43 */   protected static Map pools = new HashMap<Object, Object>();
/*     */   
/*  45 */   private static int cfgHash1 = -1;
/*     */   static {
/*  47 */     Util.executeAsynchronous(new Runnable()
/*     */         {
/*     */           public void run() {
/*  50 */             ConfigurationService.Observer observer = new ConfigurationService.Observer()
/*     */               {
/*     */                 private boolean enabled = false;
/*     */                 
/*     */                 public void onModification() {
/*  55 */                   boolean enabled = ConfigurationUtil.getBoolean("frame.database.trace.log.enabled", (Configuration)ConfigurationServiceProvider.getService()).booleanValue();
/*  56 */                   if (enabled != this.enabled) {
/*  57 */                     DatabaseLink.setOracleTrace(enabled);
/*  58 */                     this.enabled = enabled;
/*     */                   } 
/*     */                   
/*  61 */                   SubConfigurationWrapper subConfigurationWrapper = new SubConfigurationWrapper((Configuration)ConfigurationServiceProvider.getService(), "frame.database.max.parallel.connections.");
/*  62 */                   int currentHash = ConfigurationUtil.configurationHash((Configuration)subConfigurationWrapper);
/*  63 */                   if (DatabaseLink.cfgHash1 == -1 || currentHash != DatabaseLink.cfgHash1) {
/*  64 */                     DatabaseLink.semaphores.clear();
/*  65 */                     DatabaseLink.cfgHash1 = currentHash;
/*     */                   } 
/*     */                 }
/*     */               };
/*     */ 
/*     */             
/*  71 */             ConfigurationServiceProvider.getService().addObserver(observer);
/*  72 */             observer.onModification();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  79 */   protected int transactionIsolationLevel = 0;
/*     */   
/*     */   boolean autoCommit = false;
/*     */   
/*     */   protected String description;
/*     */   
/*     */   protected DataSource source;
/*     */   
/*     */   protected String driver;
/*     */   
/*     */   protected String url;
/*     */   
/*     */   protected String user;
/*     */   
/*     */   protected String password;
/*     */   
/*     */   protected int dbms;
/*     */   
/*     */   public boolean isDataSource() {
/*  98 */     return (this.source != null);
/*     */   }
/*     */   
/*     */   public DatabaseLink(String driver, String url, String user, String password) {
/* 102 */     if (driver == null) {
/* 103 */       driver = "oracle.jdbc.OracleDriver";
/*     */     }
/* 105 */     this.driver = driver;
/* 106 */     this.url = url;
/* 107 */     this.user = user;
/* 108 */     this.password = password;
/* 109 */     this.description = url + "(" + user + ")";
/* 110 */     this.log = Logger.getLogger(DatabaseLink.class.getName() + "/" + this.description.replace('.', '_'));
/*     */     try {
/* 112 */       Class.forName(driver);
/* 113 */     } catch (ClassNotFoundException e) {
/* 114 */       this.log.error("failed to load jdbc driver class (" + driver + "): ", e);
/*     */     } 
/* 116 */     deduceDBMS(this.driver);
/*     */   }
/*     */ 
/*     */   
/*     */   public DatabaseLink(String driver, String url, String user, String password, boolean autoCommit) {
/* 121 */     this(driver, url, user, password);
/* 122 */     this.autoCommit = autoCommit;
/*     */   }
/*     */   
/*     */   public DatabaseLink(String driver, String url, String user, String password, boolean autoCommit, int transactionIsolationLevel) {
/* 126 */     this(driver, url, user, password, autoCommit);
/* 127 */     this.transactionIsolationLevel = transactionIsolationLevel;
/*     */   }
/*     */   
/*     */   public DatabaseLink(String dbpool, DataSource source) {
/* 131 */     this.source = source;
/* 132 */     this.description = dbpool;
/* 133 */     this.log = Logger.getLogger(DatabaseLink.class.getName() + "/" + this.description.replace('.', '_'));
/*     */     
/* 135 */     this.driver = getDriverName();
/* 136 */     deduceDBMS(this.driver);
/*     */   }
/*     */   
/*     */   public DatabaseLink(String dbpool, DataSource source, boolean autoCommit) {
/* 140 */     this(dbpool, source);
/* 141 */     this.autoCommit = autoCommit;
/*     */   }
/*     */   
/*     */   public DatabaseLink(String dbpool, DataSource source, boolean autoCommit, int transactionIsolationLevel) {
/* 145 */     this(dbpool, source, autoCommit);
/* 146 */     this.transactionIsolationLevel = transactionIsolationLevel;
/*     */   }
/*     */   
/*     */   public String getDatabaseLinkDescription() {
/* 150 */     return this.description;
/*     */   }
/*     */   
/*     */   protected void deduceDBMS(String driver) {
/* 154 */     if (driver == null) {
/* 155 */       this.dbms = 0;
/* 156 */     } else if (driver.toLowerCase(Locale.ENGLISH).indexOf("transbase") >= 0) {
/* 157 */       this.dbms = 2;
/* 158 */     } else if (driver.toLowerCase(Locale.ENGLISH).indexOf("oracle") >= 0) {
/* 159 */       this.dbms = 1;
/* 160 */     } else if (driver.toLowerCase(Locale.ENGLISH).indexOf("sqlserver") >= 0) {
/* 161 */       this.dbms = 3;
/*     */     } else {
/* 163 */       this.dbms = 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected String getDriverName() {
/* 168 */     Connection connection = null;
/*     */     try {
/* 170 */       connection = requestConnection();
/* 171 */       DatabaseMetaData dmd = connection.getMetaData();
/* 172 */       return dmd.getDriverName();
/* 173 */     } catch (Exception e) {
/* 174 */       this.log.error("unable to retrieve pool driver name:", e);
/*     */     } finally {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 180 */     return null;
/*     */   }
/*     */   
/*     */   public String getDatabaseLinkInformation() {
/*     */     try {
/* 185 */       Connection conn = requestConnection();
/*     */       try {
/* 187 */         return getDatabaseLinkInformation(conn);
/*     */       } finally {
/* 189 */         releaseConnection(conn);
/*     */       } 
/* 191 */     } catch (Exception e) {
/* 192 */       this.log.error("unable to retrieve database meta data: " + e, e);
/* 193 */       return "";
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDatabaseLinkInformation(Connection connection) {
/* 199 */     StringBuffer buffer = new StringBuffer();
/*     */     try {
/* 201 */       DatabaseMetaData dmd = connection.getMetaData();
/* 202 */       String dbName = dmd.getDatabaseProductName();
/* 203 */       String dbVersion = dmd.getDatabaseProductVersion();
/* 204 */       buffer.append("Database:" + dbName + ", Version:" + dbVersion);
/* 205 */       String driverName = dmd.getDriverName();
/* 206 */       String driverVersion = dmd.getDriverVersion();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 212 */       buffer.append(", Driver:" + driverName + ", Version:" + driverVersion);
/* 213 */       if (this.url == null) {
/*     */ 
/*     */         
/* 216 */         Enumeration<Driver> drivers = DriverManager.getDrivers();
/* 217 */         while (drivers.hasMoreElements()) {
/* 218 */           Driver driver = drivers.nextElement();
/* 219 */           if (driver instanceof OracleDriver) {
/* 220 */             buffer.append(" (" + OracleDriver.getCompileTime() + ")");
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } else {
/* 225 */         Driver driver = DriverManager.getDriver(this.url);
/* 226 */         if (driver instanceof OracleDriver) {
/* 227 */           buffer.append(" (" + OracleDriver.getCompileTime() + ")");
/*     */         }
/*     */       } 
/* 230 */     } catch (Exception e) {
/* 231 */       this.log.error("unable to retrieve database meta data:", e);
/*     */     } 
/* 233 */     return buffer.toString();
/*     */   }
/*     */   
/*     */   protected void setClientIdentifier(Connection connection) {
/*     */     try {
/* 238 */       OracleConnection orclConnection = (OracleConnection)connection;
/* 239 */       String[] metrics = new String[4];
/* 240 */       metrics[1] = getDatabaseLinkDescription();
/* 241 */       orclConnection.setEndToEndMetrics(metrics, (short)0);
/* 242 */     } catch (Exception e) {
/* 243 */       this.log.error("failed to set client identifier", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static void setOracleTrace(boolean activate) {
/* 248 */     if (activate) {
/* 249 */       Logger.getLogger(DatabaseLink.class).debug("activating oracle trace log");
/* 250 */       OracleLog.setTrace(true);
/* 251 */       Logger dbLog = Logger.getLogger("oracle.jdbc.driver");
/* 252 */       dbLog.setLevel(Level.FINE);
/* 253 */       dbLog.setFilter(new Filter() {
/*     */             public boolean isLoggable(LogRecord record) {
/* 255 */               return !record.getMessage().startsWith("OracleResultSetImpl");
/*     */             }
/*     */           });
/*     */     } else {
/*     */       
/* 260 */       Logger.getLogger(DatabaseLink.class).debug("deactivating oracle trace log");
/* 261 */       OracleLog.setTrace(false);
/*     */     } 
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
/*     */   protected static Connection getOracleConnection(Connection connection) {
/* 291 */     return null;
/*     */   }
/*     */   
/*     */   public Connection requestConnection() throws Exception {
/* 295 */     return requestConnection(false, 0, false);
/*     */   }
/*     */   
/*     */   public Connection requestConnection_ConfigurationService() throws Exception {
/* 299 */     return requestConnection(false, 0, true);
/*     */   }
/*     */   
/*     */   public void testConnection(Connection connection) throws Exception {
/* 303 */     Statement stmt = null;
/*     */     try {
/* 305 */       stmt = connection.createStatement();
/* 306 */       if (this.dbms == 1) {
/* 307 */         stmt.executeQuery("select 1 from dual");
/*     */       } else {
/* 309 */         stmt.executeQuery("select count(*) from systable");
/*     */       } 
/*     */     } finally {
/* 312 */       if (stmt != null) {
/* 313 */         stmt.close();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/* 318 */   private static final Map<String, Semaphore> semaphores = new ConcurrentHashMap<String, Semaphore>();
/*     */   
/*     */   private static Semaphore getSemaphore(String description) {
/* 321 */     Semaphore ret = semaphores.get(description);
/* 322 */     if (ret == null) {
/* 323 */       synchronized (DatabaseLink.class) {
/* 324 */         if ((ret = semaphores.get(description)) == null) {
/* 325 */           Number n = ConfigurationUtil.getNumber("frame.database.max.parallel.connections." + description, (Configuration)ApplicationContext.getInstance());
/* 326 */           if (n == null) {
/* 327 */             n = ConfigurationUtil.getNumber("frame.database.max.parallel.connections", (Configuration)ApplicationContext.getInstance());
/* 328 */             if (n == null) {
/* 329 */               n = Integer.valueOf(100);
/*     */             }
/*     */           } 
/* 332 */           ret = new Semaphore(n.intValue());
/*     */           
/* 334 */           semaphores.put(description, ret);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 339 */     return ret;
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
/* 350 */   private static final Map<String, CounterAccess> counters = new ConcurrentHashMap<String, CounterAccess>();
/*     */   
/*     */   private static CounterAccess getCounters(String description) {
/* 353 */     CounterAccess ret = counters.get(description);
/* 354 */     if (ret == null) {
/* 355 */       synchronized (DatabaseLink.class) {
/* 356 */         if ((ret = counters.get(description)) == null) {
/* 357 */           final AtomicLong requestCount = new AtomicLong();
/* 358 */           final AtomicLong needPeek = new AtomicLong();
/* 359 */           final AtomicLong connectionCount = new AtomicLong();
/* 360 */           ret = new CounterAccess()
/*     */             {
/*     */               public AtomicLong getNeedPeek() {
/* 363 */                 return needPeek;
/*     */               }
/*     */               
/*     */               public AtomicLong getRequestCounter() {
/* 367 */                 return requestCount;
/*     */               }
/*     */               
/*     */               public AtomicLong getConnectionCounter() {
/* 371 */                 return connectionCount;
/*     */               }
/*     */             };
/* 374 */           counters.put(description, ret);
/*     */         } 
/*     */       } 
/*     */     }
/* 378 */     return ret;
/*     */   }
/*     */   
/*     */   private Connection requestConnection(boolean autoCommit, int transactionIsolationLevel, boolean isConfigServiceAccess) throws Exception {
/* 382 */     if (this.log.isDebugEnabled()) {
/* 383 */       CounterAccess counters = getCounters(this.description);
/*     */       
/* 385 */       long currentNeed = counters.getRequestCounter().incrementAndGet() + counters.getConnectionCounter().get();
/* 386 */       long peek = counters.getNeedPeek().get();
/* 387 */       if (currentNeed > peek && 
/* 388 */         counters.getNeedPeek().compareAndSet(peek, currentNeed)) {
/* 389 */         this.log.debug("new need peek: " + currentNeed + " connections");
/*     */       }
/*     */     } 
/*     */     
/*     */     try {
/* 394 */       Semaphore s = !isConfigServiceAccess ? getSemaphore(this.description) : null;
/* 395 */       if (s != null && !s.tryAcquire(10000L, TimeUnit.MILLISECONDS)) {
/* 396 */         throw new RuntimeException("unable to retrieve connection permit within time limit");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 403 */         long ts = System.currentTimeMillis();
/* 404 */         if (this.log.isDebugEnabled()) {
/* 405 */           this.log.debug("opening database connection for : " + this.description + " [" + ts + "]...");
/* 406 */           this.log.debug("...request trace: " + Util.compactStackTrace(new Throwable(), 1, -1));
/*     */         } 
/*     */ 
/*     */         
/* 410 */         Connection connection = null;
/* 411 */         if (this.source != null) {
/*     */           try {
/* 413 */             connection = this.source.getConnection();
/*     */           }
/* 415 */           catch (Exception e) {
/* 416 */             throw new RuntimeException("failed to acquire jdbc connection (" + getDatabaseLinkDescription() + ")", e);
/*     */           } 
/*     */         } else {
/*     */           
/*     */           try {
/* 421 */             if (this.dbms == 1) {
/* 422 */               Driver driver = (Driver)Class.forName("oracle.jdbc.OracleDriver").newInstance();
/* 423 */               Properties properties = new Properties();
/* 424 */               properties.put("user", this.user);
/* 425 */               properties.put("password", this.password);
/* 426 */               connection = driver.connect(this.url, properties);
/*     */             } else {
/* 428 */               connection = DriverManager.getConnection(this.url, this.user, this.password);
/*     */             } 
/* 430 */           } catch (Exception e) {
/*     */             
/* 432 */             throw new RuntimeException("failed to acquire jdbc connection (" + getDatabaseLinkDescription() + ")", e);
/*     */           } 
/*     */         } 
/* 435 */         if (autoCommit != connection.getAutoCommit()) {
/*     */           try {
/* 437 */             connection.setAutoCommit(autoCommit);
/* 438 */           } catch (Exception e) {
/* 439 */             this.log.error("failed to set auto commit to '" + autoCommit + "' (" + getDatabaseLinkDescription() + "): ", e);
/*     */           } 
/*     */         }
/* 442 */         if (transactionIsolationLevel != 0 && transactionIsolationLevel != connection.getTransactionIsolation()) {
/*     */           try {
/* 444 */             connection.setTransactionIsolation(transactionIsolationLevel);
/* 445 */           } catch (Exception e) {
/* 446 */             this.log.error("failed to set transaction level to '" + transactionIsolationLevel + "' (" + getDatabaseLinkDescription() + "): ", e);
/*     */           } 
/*     */         }
/* 449 */         if (this.log.isDebugEnabled()) {
/* 450 */           long connectionCount = getCounters(this.description).getConnectionCounter().incrementAndGet();
/*     */           
/* 452 */           long delta = System.currentTimeMillis() - ts;
/* 453 */           if (this.dbms == 1);
/*     */ 
/*     */           
/* 456 */           this.log.debug("open connections: " + connectionCount);
/* 457 */           this.log.debug("...done [" + ts + "," + delta + "ms]");
/*     */         } 
/* 459 */         return new ConnectionWrapper(connection);
/* 460 */       } catch (Exception e) {
/* 461 */         if (s != null) {
/* 462 */           s.release();
/*     */         }
/* 464 */         throw e;
/*     */       } 
/*     */     } finally {
/* 467 */       if (this.log.isDebugEnabled()) {
/* 468 */         CounterAccess counters = getCounters(this.description);
/* 469 */         counters.getRequestCounter().decrementAndGet();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void releaseConnection_ConfigurationService(Connection connection) {
/* 476 */     releaseConnection(connection, true);
/*     */   }
/*     */   
/*     */   public void releaseConnection(Connection connection) {
/* 480 */     releaseConnection(connection, false);
/*     */   }
/*     */   
/*     */   public void releaseConnection(Connection connection, boolean isConfigService) {
/* 484 */     long ts = System.currentTimeMillis();
/* 485 */     if (this.log.isDebugEnabled()) {
/* 486 */       this.log.debug("releasing database connection for : " + this.description + " [" + ts + "]...");
/*     */     }
/*     */     try {
/* 489 */       if (this.dbms == 2 && !connection.getAutoCommit()) {
/* 490 */         connection.commit();
/*     */       }
/* 492 */     } catch (Exception x) {}
/*     */     
/*     */     try {
/* 495 */       connection.close();
/* 496 */     } catch (Exception x) {}
/*     */     
/* 498 */     if (!isConfigService) {
/* 499 */       getSemaphore(this.description).release();
/*     */     }
/* 501 */     if (this.log.isDebugEnabled()) {
/* 502 */       long delta = System.currentTimeMillis() - ts;
/* 503 */       this.log.debug("...done releasing [" + ts + "," + delta + "ms]");
/*     */     } 
/* 505 */     long openConnections = getCounters(this.description).getConnectionCounter().decrementAndGet();
/* 506 */     this.log.debug("...remaining (open) connections: " + openConnections);
/*     */   }
/*     */ 
/*     */   
/*     */   public String translate(String sql) {
/* 511 */     return sql;
/*     */   }
/*     */   
/*     */   public void handleError(Connection connection, Exception e) {
/* 515 */     logException(connection, e);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void logException(Connection connection, Exception e) {
/* 521 */     this.log.error("database link error (" + getDatabaseLinkDescription() + "): ", e);
/*     */   }
/*     */   
/*     */   public int getDBMS() {
/* 525 */     return this.dbms;
/*     */   }
/*     */   
/*     */   public void close() {
/* 529 */     this.source = null;
/* 530 */     this.url = null;
/*     */   }
/*     */   
/*     */   protected static DataSource getPoolConnection(Configuration configuration, String source) {
/* 534 */     Context ctx = null;
/*     */     try {
/* 536 */       Hashtable<Object, Object> ht = new Hashtable<Object, Object>();
/* 537 */       String provider = configuration.getProperty("jndi.provider");
/* 538 */       if (provider == null) {
/* 539 */         provider = ApplicationContext.getInstance().getProperty("jndi.provider");
/*     */       }
/* 541 */       if (provider != null) {
/* 542 */         ht.put("java.naming.provider.url", provider);
/*     */       }
/*     */       
/* 545 */       String factory = configuration.getProperty("jndi.context-factory");
/* 546 */       if (factory == null) {
/* 547 */         factory = ApplicationContext.getInstance().getProperty("jndi.context-factory");
/*     */       }
/* 549 */       if (factory != null) {
/* 550 */         ht.put("java.naming.factory.initial", factory);
/*     */       }
/*     */       
/* 553 */       if (ht.size() > 0) {
/* 554 */         ctx = new InitialContext(ht);
/*     */       } else {
/* 556 */         Context ictx = new InitialContext();
/* 557 */         ctx = (Context)ictx.lookup("java:comp/env");
/*     */         try {
/* 559 */           ictx.close();
/* 560 */         } catch (Exception x) {}
/*     */       } 
/*     */       
/* 563 */       return (DataSource)ctx.lookup(source);
/* 564 */     } catch (Exception e) {
/* 565 */       Logger.getLogger(DatabaseLink.class).error("failed to lookup data source (" + source + "): ", e);
/* 566 */       return null;
/*     */     } finally {
/* 568 */       if (ctx != null) {
/*     */         try {
/* 570 */           ctx.close();
/* 571 */         } catch (Exception x) {}
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static DatabaseLink openDatabase(Configuration configuration, String database) throws Exception {
/* 578 */     return openDatabase(configuration, database, false, 0);
/*     */   }
/*     */   
/*     */   public static IDatabaseLink openDatabase(Configuration configuration, String database, boolean autoCommit) throws Exception {
/* 582 */     return openDatabase(configuration, database, autoCommit, 0);
/*     */   }
/*     */   
/*     */   public static DatabaseLink openDatabase(Configuration configuration, String database, boolean autoCommit, int transactionIsolationLevel) throws Exception {
/* 586 */     return openDatabase((Configuration)new SubConfigurationWrapper(configuration, database + "."), autoCommit, transactionIsolationLevel);
/*     */   }
/*     */   
/*     */   public static IDatabaseLink openDatabase(Configuration configuration) {
/* 590 */     return openDatabase(configuration, false, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public static DatabaseLink openDatabase(Configuration configuration, boolean autoCommit, int transactionIsolationLevel) {
/* 595 */     String source = configuration.getProperty("data-source");
/* 596 */     if (source != null) {
/* 597 */       String descriptor = source + ":" + autoCommit + ":" + transactionIsolationLevel;
/* 598 */       DatabaseLink dblink = (DatabaseLink)pools.get(descriptor);
/* 599 */       if (dblink == null) {
/*     */         try {
/* 601 */           dblink = new DatabaseLink(source, getPoolConnection(configuration, source), autoCommit, transactionIsolationLevel);
/* 602 */         } catch (Exception x) {}
/*     */         
/* 604 */         if (dblink != null) {
/* 605 */           pools.put(descriptor, dblink);
/*     */         }
/*     */       } 
/* 608 */       if (dblink != null) {
/* 609 */         return dblink;
/*     */       }
/*     */     } 
/* 612 */     return getPhysicalConnection(configuration, autoCommit, transactionIsolationLevel);
/*     */   }
/*     */   
/*     */   public static DatabaseLink getPhysicalConnection(Configuration configuration, boolean autoCommit, int transactionIsolationLevel) {
/* 616 */     String driver = configuration.getProperty("drv");
/* 617 */     String url = configuration.getProperty("url");
/* 618 */     if (url == null) {
/* 619 */       throw new IllegalArgumentException("no url-property set");
/*     */     }
/* 621 */     String user = configuration.getProperty("usr");
/* 622 */     String password = configuration.getProperty("pwd");
/* 623 */     return new DatabaseLink(driver, url, user, password, autoCommit, transactionIsolationLevel);
/*     */   }
/*     */   
/*     */   protected static void enableStatementCache(Connection connection) {
/* 627 */     if (connection != null) {
/*     */       return;
/*     */     }
/*     */     try {
/* 631 */       OracleConnection oconn = (OracleConnection)getOracleConnection(connection);
/* 632 */       if (oconn != null && 
/* 633 */         !oconn.getImplicitCachingEnabled()) {
/* 634 */         oconn.setImplicitCachingEnabled(true);
/* 635 */         oconn.setStatementCacheSize(5);
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 642 */     catch (Exception x) {}
/*     */   }
/*     */ 
/*     */   
/*     */   protected static void initPreparedStatements(IDatabaseLink db, int count) throws Exception {
/* 647 */     Connection[] connections = new Connection[count]; int k;
/* 648 */     for (k = 0; k < count; k++) {
/* 649 */       connections[k] = db.requestConnection();
/* 650 */       enableStatementCache(connections[k]);
/*     */     } 
/* 652 */     for (k = 0; k < count; k++) {
/* 653 */       PreparedStatement pstmt = connections[k].prepareStatement("select * from locale where locale_id = ?");
/* 654 */       pstmt.close();
/* 655 */       db.releaseConnection(connections[k]);
/* 656 */       connections[k] = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static PreparedStatement[] initPreparedStatements4reuse(IDatabaseLink db, int count) throws Exception {
/* 661 */     PreparedStatement[] pstmts = new PreparedStatement[count];
/* 662 */     for (int k = 0; k < count; k++) {
/* 663 */       Connection connection = db.requestConnection();
/* 664 */       pstmts[k] = connection.prepareStatement("select * from locale where locale_id = ?");
/*     */     } 
/* 666 */     return pstmts;
/*     */   }
/*     */   
/*     */   protected static void releasePreparedStatements(IDatabaseLink db, PreparedStatement[] pstmts) throws Exception {
/* 670 */     for (int k = 0; k < pstmts.length; k++) {
/* 671 */       Connection connection = pstmts[k].getConnection();
/* 672 */       pstmts[k].close();
/* 673 */       db.releaseConnection(connection);
/* 674 */       pstmts[k] = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static void execute(IDatabaseLink db, boolean usePreparedStatements, PreparedStatement[] pool, int nthreads) {
/* 679 */     TestThread[] arrayOfTestThread = new TestThread[nthreads];
/* 680 */     for (int k = 0; k < nthreads; k++) {
/* 681 */       if (pool != null) {
/* 682 */         arrayOfTestThread[k] = new TestThread(pool[k], k);
/*     */       } else {
/* 684 */         arrayOfTestThread[k] = new TestThread(db, usePreparedStatements, k);
/*     */       } 
/*     */     } 
/*     */     
/* 688 */     long startTime = System.currentTimeMillis(); int i;
/* 689 */     for (i = 0; i < arrayOfTestThread.length; i++) {
/* 690 */       arrayOfTestThread[i].start();
/*     */     }
/*     */     try {
/* 693 */       for (i = 0; i < arrayOfTestThread.length; i++)
/* 694 */         arrayOfTestThread[i].join(); 
/* 695 */     } catch (InterruptedException ex) {
/* 696 */       System.out.println("Interrupted");
/*     */     } 
/*     */     
/* 699 */     Logger.getLogger(DatabaseLink.class).info("run time: " + (System.currentTimeMillis() - startTime) + " ms");
/*     */   }
/*     */ 
/*     */   
/*     */   private static class TestThread
/*     */     extends Thread
/*     */   {
/*     */     IDatabaseLink db;
/*     */     
/*     */     Connection connection;
/*     */     PreparedStatement pstmt;
/*     */     boolean usePreparedStatements;
/*     */     int param;
/*     */     
/*     */     public TestThread(IDatabaseLink db, boolean usePreparedStatements, int param) {
/* 714 */       this.db = db;
/* 715 */       this.usePreparedStatements = usePreparedStatements;
/* 716 */       this.param = param;
/*     */     }
/*     */     
/*     */     public TestThread(PreparedStatement pstmt, int param) {
/* 720 */       this.pstmt = pstmt;
/* 721 */       this.usePreparedStatements = true;
/* 722 */       this.param = param;
/*     */     }
/*     */     
/*     */     public void run() {
/*     */       try {
/* 727 */         if (this.db != null) {
/* 728 */           this.connection = this.db.requestConnection();
/*     */         }
/* 730 */         for (int k = 0; k < 10; k++) {
/* 731 */           execute(this.param + k);
/*     */         }
/* 733 */       } catch (Exception e) {
/* 734 */         this.db.handleError(this.connection, e);
/*     */       } finally {
/*     */         try {
/* 737 */           if (this.db != null && this.pstmt != null) {
/* 738 */             this.pstmt.close();
/*     */           }
/* 740 */           if (this.db != null && this.connection != null) {
/* 741 */             this.db.releaseConnection(this.connection);
/*     */           }
/* 743 */         } catch (Exception x) {}
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void execute(int param) {
/* 749 */       Statement stmt = null;
/* 750 */       ResultSet rs = null;
/*     */       try {
/* 752 */         if (this.usePreparedStatements) {
/* 753 */           if (this.pstmt == null) {
/* 754 */             DatabaseLink.enableStatementCache(this.connection);
/* 755 */             this.pstmt = this.connection.prepareStatement("select * from locale where locale_id = ?");
/*     */           } 
/* 757 */           this.pstmt.clearParameters();
/* 758 */           this.pstmt.setInt(1, param);
/* 759 */           rs = this.pstmt.executeQuery();
/*     */         } else {
/* 761 */           stmt = this.connection.createStatement();
/* 762 */           rs = stmt.executeQuery("select * from locale where locale_id = " + param);
/*     */         } 
/* 764 */         while (rs.next()) {
/* 765 */           rs.getString("locale_description");
/*     */         }
/* 767 */       } catch (Exception e) {
/* 768 */         this.db.handleError(this.connection, e);
/*     */       } finally {
/*     */         try {
/* 771 */           if (rs != null) {
/* 772 */             rs.close();
/*     */           }
/* 774 */           if (stmt != null) {
/* 775 */             stmt.close();
/*     */           }
/* 777 */         } catch (Exception x) {}
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface CounterAccess {
/*     */     AtomicLong getRequestCounter();
/*     */     
/*     */     AtomicLong getNeedPeek();
/*     */     
/*     */     AtomicLong getConnectionCounter();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\util\DatabaseLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */