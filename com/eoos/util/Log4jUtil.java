/*     */ package com.eoos.util;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import org.apache.log4j.Appender;
/*     */ import org.apache.log4j.ConsoleAppender;
/*     */ import org.apache.log4j.Layout;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.PatternLayout;
/*     */ import org.apache.log4j.Priority;
/*     */ import org.apache.log4j.RollingFileAppender;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Log4jUtil
/*     */ {
/*     */   public static RollingFileAppender createRollingFileAppender(File file) throws IOException {
/*  22 */     return createRollingFileAppender(file, true, null, null, 1048576L, 10);
/*     */   }
/*     */   
/*     */   public static RollingFileAppender createRollingFileAppender(File file, boolean create, String encoding, PatternLayout layout, long size, int maxBackups) throws IOException {
/*     */     PatternLayout _layout;
/*  27 */     if (!file.exists() && create) {
/*  28 */       File _file = file.getAbsoluteFile();
/*  29 */       File _parent = _file.getParentFile();
/*  30 */       if (_parent != null) {
/*  31 */         _parent.mkdirs();
/*     */       }
/*  33 */       file.createNewFile();
/*     */     } 
/*     */ 
/*     */     
/*  37 */     if (layout == null) {
/*  38 */       _layout = new PatternLayout("%d{ISO8601}/%p/%t/ [%c{1}] %m %n");
/*     */     } else {
/*  40 */       _layout = layout;
/*     */     } 
/*     */     
/*  43 */     RollingFileAppender appender = new RollingFileAppender();
/*  44 */     appender.setLayout((Layout)_layout);
/*  45 */     appender.setFile(file.getAbsolutePath());
/*  46 */     appender.setAppend(false);
/*  47 */     appender.setImmediateFlush(true);
/*  48 */     appender.setThreshold((Priority)Level.DEBUG);
/*  49 */     if (encoding != null) {
/*  50 */       appender.setEncoding(encoding);
/*     */     }
/*  52 */     appender.setMaximumFileSize(size);
/*  53 */     appender.setMaxBackupIndex(maxBackups);
/*  54 */     appender.activateOptions();
/*  55 */     return appender;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Logger createRollingFileLog(File file, boolean create, String encoding, PatternLayout layout, long size, int maxBackups) throws IOException {
/*  60 */     Logger retValue = Logger.getLogger(file.getAbsolutePath());
/*  61 */     RollingFileAppender rollingFileAppender = createRollingFileAppender(file, create, encoding, layout, size, maxBackups);
/*  62 */     retValue.addAppender((Appender)rollingFileAppender);
/*  63 */     return retValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Logger createRollingFileLog(File file) throws IOException {
/*  68 */     return createRollingFileLog(file, true, null, null, 10485760L, 0);
/*     */   }
/*     */   
/*     */   public static void attachRollingFileAppender(Logger log, File file) throws IOException {
/*  72 */     log.addAppender((Appender)createRollingFileAppender(file));
/*     */   }
/*     */   
/*     */   public static void attachConsoleAppender(Logger log, PatternLayout layout) {
/*     */     PatternLayout _layout;
/*  77 */     if (layout == null) {
/*  78 */       _layout = new PatternLayout("%p/%t [%c{1}] %m %n");
/*     */     } else {
/*  80 */       _layout = layout;
/*     */     } 
/*     */     
/*  83 */     ConsoleAppender appender = new ConsoleAppender((Layout)_layout);
/*  84 */     appender.setImmediateFlush(true);
/*  85 */     appender.setThreshold((Priority)Level.DEBUG);
/*  86 */     appender.activateOptions();
/*     */     
/*  88 */     log.addAppender((Appender)appender);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Appender createConsoleAppender(PatternLayout layout) {
/*     */     PatternLayout _layout;
/*  94 */     if (layout == null) {
/*  95 */       _layout = new PatternLayout("%p\t%t\t%c\t- %m %n");
/*     */     } else {
/*  97 */       _layout = layout;
/*     */     } 
/*     */     
/* 100 */     ConsoleAppender appender = new ConsoleAppender((Layout)_layout);
/* 101 */     appender.setImmediateFlush(true);
/* 102 */     appender.setThreshold((Priority)Level.DEBUG);
/* 103 */     appender.activateOptions();
/*     */     
/* 105 */     return (Appender)appender;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoo\\util\Log4jUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */