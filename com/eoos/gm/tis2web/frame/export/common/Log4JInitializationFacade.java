/*     */ package com.eoos.gm.tis2web.frame.export.common;
/*     */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.ConfigurationService;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.propcfg.util.ConfigurationUtil;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import java.util.logging.Handler;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.LogManager;
/*     */ import java.util.logging.LogRecord;
/*     */ import java.util.logging.Logger;
/*     */ import java.util.logging.SimpleFormatter;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ import org.apache.log4j.PropertyConfigurator;
/*     */ 
/*     */ public class Log4JInitializationFacade {
/*     */   private static final class MyHandler extends Handler {
/*     */     public void publish(LogRecord record) {
/*  26 */       Logger.getLogger(record.getLoggerName()).log((Priority)Log4JInitializationFacade.getLevel(record.getLevel()), Log4JInitializationFacade.JLOGGING_FORMATTER.format(record));
/*     */     }
/*     */     private MyHandler() {}
/*     */     
/*     */     public void flush() {}
/*     */     
/*     */     public void close() throws SecurityException {
/*  33 */       Logger.getLogger("").removeHandler(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  38 */   private static final Logger log = Logger.getLogger(Log4JInitializationFacade.class);
/*     */   
/*  40 */   private static Log4JInitializationFacade instance = null;
/*     */   
/*  42 */   private int configHash = -1;
/*     */   
/*  44 */   private static SimpleFormatter JLOGGING_FORMATTER = new SimpleFormatter();
/*     */ 
/*     */   
/*     */   private Log4JInitializationFacade() {
/*  48 */     final ConfigurationService cs = (ConfigurationService)FrameServiceProvider.getInstance().getService(ConfigurationService.class);
/*  49 */     cs.addObserver(new ConfigurationService.Observer()
/*     */         {
/*     */           public void onModification() {
/*  52 */             Log4JInitializationFacade.this.init((Configuration)cs);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized Log4JInitializationFacade getInstance() {
/*  60 */     if (instance == null) {
/*  61 */       instance = new Log4JInitializationFacade();
/*     */     }
/*  63 */     return instance;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {}
/*     */ 
/*     */   
/*     */   public synchronized void init(Configuration configuration) {
/*  71 */     int currentHash = ConfigurationUtil.configurationHash(configuration, Collections.singleton("log4j."), ConfigurationUtil.MODE_PREFIXES);
/*  72 */     if (currentHash != this.configHash) {
/*  73 */       SubConfigurationWrapper subConfigurationWrapper = new SubConfigurationWrapper(ApplicationContext.getInstance(), "log4j.");
/*  74 */       Properties properties = new Properties();
/*  75 */       for (Iterator<String> iter = subConfigurationWrapper.getKeys().iterator(); iter.hasNext(); ) {
/*  76 */         String key = iter.next();
/*  77 */         String value = subConfigurationWrapper.getProperty(key);
/*  78 */         properties.put("log4j." + key, (value != null) ? value : "");
/*     */       } 
/*     */ 
/*     */       
/*  82 */       PropertyConfigurator.configure(properties);
/*  83 */       this.configHash = currentHash;
/*     */ 
/*     */       
/*  86 */       LogManager.getLogManager().reset();
/*  87 */       Handler connector = new MyHandler();
/*  88 */       Logger.getLogger("").addHandler(connector);
/*     */       
/*  90 */       Logger.getLogger("test").setLevel(Level.ALL);
/*  91 */       Logger.getLogger("test").fine("testing fine level");
/*  92 */       Logger.getLogger("test").info("testing info level");
/*  93 */       Logger.getLogger("test").warning("testing warning level");
/*  94 */       Logger.getLogger("test").severe("testing error level");
/*     */     } else {
/*     */       
/*  97 */       log.debug("...log4j configuration did not change since last call");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 103 */   private static final List TO_DEBUG = Arrays.asList(new Level[] { Level.FINEST, Level.FINER, Level.FINE });
/*     */   
/* 105 */   private static final List TO_INFO = Arrays.asList(new Level[] { Level.CONFIG, Level.INFO });
/*     */   
/* 107 */   private static final List TO_WARN = Arrays.asList(new Level[] { Level.WARNING });
/*     */   
/* 109 */   private static final List TO_ERROR = Arrays.asList(new Level[] { Level.SEVERE });
/*     */   
/*     */   private static Level getLevel(Level level) {
/* 112 */     if (TO_DEBUG.contains(level))
/* 113 */       return Level.DEBUG; 
/* 114 */     if (TO_INFO.contains(level))
/* 115 */       return Level.INFO; 
/* 116 */     if (TO_WARN.contains(level))
/* 117 */       return Level.WARN; 
/* 118 */     if (TO_ERROR.contains(level)) {
/* 119 */       return Level.ERROR;
/*     */     }
/* 121 */     log.warn("unknown/unhandled source level, mapping to level OFF");
/* 122 */     return Level.OFF;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\Log4JInitializationFacade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */