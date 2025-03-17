/*    */ package com.eoos.gm.tis2web.frame.diag.client;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.diag.client.ui.MainFrame;
/*    */ import com.eoos.gm.tis2web.frame.dls.client.SoftwareKeyCheckClient;
/*    */ import com.eoos.log.Log4jUtil;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.io.File;
/*    */ import java.io.FileFilter;
/*    */ import org.apache.log4j.Appender;
/*    */ import org.apache.log4j.ConsoleAppender;
/*    */ import org.apache.log4j.Layout;
/*    */ import org.apache.log4j.Level;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.apache.log4j.PatternLayout;
/*    */ import org.apache.log4j.RollingFileAppender;
/*    */ 
/*    */ public class Startup {
/* 18 */   private static final Logger log = Logger.getLogger(Startup.class);
/*    */   
/*    */   private static void deleteOldLogFiles(File homeDir) {
/* 21 */     if (homeDir.exists()) {
/* 22 */       File[] files = homeDir.listFiles(new FileFilter()
/*    */           {
/*    */             public boolean accept(File file) {
/* 25 */               return (file.getName().startsWith("diag.log") || file.getName().startsWith("diag.comm.log"));
/*    */             }
/*    */           });
/* 28 */       if (!Util.isNullOrEmpty(files)) {
/* 29 */         for (int i = 0; i < files.length; i++) {
/* 30 */           files[0].delete();
/*    */         }
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void main(String[] args) throws Throwable {
/* 37 */     File homeDir = new File(System.getProperty("user.home"));
/* 38 */     homeDir = new File(homeDir, "diag");
/* 39 */     if (homeDir.exists()) {
/* 40 */       deleteOldLogFiles(homeDir);
/*    */     }
/* 42 */     else if (!homeDir.mkdirs()) {
/* 43 */       throw new IllegalStateException("unable to create application home dir (" + homeDir.getAbsolutePath() + ")");
/*    */     } 
/*    */ 
/*    */     
/* 47 */     boolean debug = Boolean.getBoolean("debug");
/*    */ 
/*    */ 
/*    */     
/* 51 */     RollingFileAppender appender = new RollingFileAppender();
/* 52 */     appender.setLayout((Layout)Log4jUtil.PL_SIMPLE_WITH_SHORT_CATEGORY);
/* 53 */     appender.setFile((new File(homeDir, "diag.log")).getAbsolutePath());
/* 54 */     appender.setAppend(false);
/* 55 */     appender.setImmediateFlush(true);
/* 56 */     appender.setMaximumFileSize(10485760L);
/* 57 */     appender.setMaxBackupIndex(10);
/* 58 */     appender.activateOptions();
/*    */     
/* 60 */     ConsoleAppender consoleAppender = new ConsoleAppender((Layout)new PatternLayout("[%c{1}] %m%n"));
/* 61 */     consoleAppender.setImmediateFlush(true);
/* 62 */     consoleAppender.activateOptions();
/*    */     
/* 64 */     Logger.getRootLogger().addAppender((Appender)appender);
/* 65 */     Logger.getRootLogger().addAppender((Appender)consoleAppender);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 70 */     appender = new RollingFileAppender();
/* 71 */     appender.setLayout((Layout)Log4jUtil.PL_SIMPLE_WITH_SHORT_CATEGORY);
/* 72 */     appender.setFile((new File(homeDir, "diag.comm.log")).getAbsolutePath());
/* 73 */     appender.setAppend(false);
/* 74 */     appender.setImmediateFlush(true);
/* 75 */     appender.setMaximumFileSize(10485760L);
/* 76 */     appender.setMaxBackupIndex(10);
/* 77 */     appender.activateOptions();
/*    */     
/* 79 */     Logger apacheLogger = Logger.getLogger("org.apache");
/* 80 */     apacheLogger.addAppender((Appender)appender);
/* 81 */     apacheLogger.setAdditivity(false);
/* 82 */     apacheLogger.setLevel(debug ? Level.DEBUG : Level.INFO);
/*    */     
/* 84 */     Logger httpClientLogger = Logger.getLogger("httpclient");
/* 85 */     httpClientLogger.addAppender((Appender)appender);
/* 86 */     httpClientLogger.setAdditivity(false);
/* 87 */     httpClientLogger.setLevel(debug ? Level.DEBUG : Level.INFO);
/*    */ 
/*    */     
/*    */     try {
/* 91 */       if (SoftwareKeyCheckClient.checkSoftwareKey("com.eoos.gm.tis2web.frame.diag.client.lbl_msg") == 0) {
/* 92 */         MainFrame.show(new ApplicationInterface(homeDir));
/*    */       }
/* 94 */       System.exit(0);
/* 95 */     } catch (Throwable t) {
/* 96 */       log.error("unable to execute - exception: " + t, t);
/* 97 */       throw t;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\diag\client\Startup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */