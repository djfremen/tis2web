/*     */ package com.eoos.log;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.Enumeration;
/*     */ import org.apache.log4j.Appender;
/*     */ import org.apache.log4j.Category;
/*     */ import org.apache.log4j.ConsoleAppender;
/*     */ import org.apache.log4j.FileAppender;
/*     */ import org.apache.log4j.Layout;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.PatternLayout;
/*     */ import org.apache.log4j.Priority;
/*     */ import org.apache.log4j.spi.ErrorHandler;
/*     */ import org.apache.log4j.spi.Filter;
/*     */ import org.apache.log4j.spi.LoggingEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Log4jUtil
/*     */ {
/*  22 */   public static final PatternLayout PL_SIMPLE = new PatternLayout("%d - %m%n");
/*  23 */   public static final PatternLayout PL_SIMPLE_WITH_SHORT_CATEGORY = new PatternLayout("%d - [%c{1}] %m%n");
/*  24 */   public static final PatternLayout PL_SIMPLE_MULTI_THREAD = new PatternLayout("%d - %p %t - %m%n");
/*     */   
/*  26 */   public static final Appender DUMMY_APPENDER = new Appender()
/*     */     {
/*     */       public boolean requiresLayout() {
/*  29 */         return false;
/*     */       }
/*     */ 
/*     */       
/*     */       public void setName(String arg0) {}
/*     */       
/*     */       public Layout getLayout() {
/*  36 */         return null;
/*     */       }
/*     */ 
/*     */       
/*     */       public void setLayout(Layout arg0) {}
/*     */       
/*     */       public ErrorHandler getErrorHandler() {
/*  43 */         return null;
/*     */       }
/*     */ 
/*     */       
/*     */       public void setErrorHandler(ErrorHandler arg0) {}
/*     */       
/*     */       public String getName() {
/*  50 */         return "DUMMY";
/*     */       }
/*     */ 
/*     */       
/*     */       public void doAppend(LoggingEvent arg0) {}
/*     */ 
/*     */       
/*     */       public void close() {}
/*     */ 
/*     */       
/*     */       public void clearFilters() {}
/*     */       
/*     */       public Filter getFilter() {
/*  63 */         return null;
/*     */       }
/*     */       
/*     */       public void addFilter(Filter arg0) {}
/*     */     };
/*     */   
/*     */   public static interface Callback
/*     */   {
/*     */     void writeLog(Logger param1Logger);
/*     */   }
/*     */   
/*     */   public static void attachConsoleAppender() {
/*  75 */     attachConsoleAppender(null, null);
/*     */   }
/*     */   
/*     */   public static void attachConsoleAppender(Logger log, PatternLayout layout) {
/*  79 */     ConsoleAppender appender = new ConsoleAppender((layout != null) ? (Layout)layout : (Layout)PL_SIMPLE);
/*  80 */     appender.setImmediateFlush(true);
/*  81 */     appender.setThreshold((Priority)Level.ALL);
/*  82 */     appender.activateOptions();
/*     */     
/*  84 */     log = (log == null) ? Logger.getRootLogger() : log;
/*  85 */     log.addAppender((Appender)appender);
/*     */   }
/*     */   
/*     */   public static File searchFileAppenderDirectory(Logger log) {
/*  89 */     File directory = null;
/*  90 */     Logger logger = log;
/*  91 */     while (logger != null && directory == null) {
/*  92 */       Enumeration<Appender> e = logger.getAllAppenders();
/*  93 */       while (e.hasMoreElements() && directory == null) {
/*  94 */         Appender appender = e.nextElement();
/*  95 */         if (appender instanceof FileAppender) {
/*  96 */           directory = (new File(((FileAppender)appender).getFile())).getParentFile();
/*     */         }
/*     */       } 
/*  99 */       Category category = logger.getParent();
/*     */     } 
/*     */     
/* 102 */     return directory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void logSynchronized(Logger syncLogger, Logger log, Callback callback) {
/* 110 */     synchronized (syncLogger) {
/* 111 */       if (Logger.getRootLogger().equals(syncLogger)) {
/* 112 */         callback.writeLog(log);
/*     */       } else {
/* 114 */         logSynchronized((Logger)syncLogger.getParent(), log, callback);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void logSynchronized(Logger log, Callback callback) {
/* 120 */     logSynchronized(log, log, callback);
/*     */   }
/*     */   
/*     */   public static Logger getSpecialLogger(String prefix, Class clazz) {
/* 124 */     return Logger.getLogger(prefix + "." + clazz.getName());
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\log\Log4jUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */