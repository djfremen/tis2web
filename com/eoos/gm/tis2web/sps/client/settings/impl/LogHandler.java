/*    */ package com.eoos.gm.tis2web.sps.client.settings.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.settings.ObservableSubject;
/*    */ import com.eoos.gm.tis2web.sps.client.settings.PersistentClientSettings;
/*    */ import com.eoos.gm.tis2web.sps.client.settings.SettingsObserver;
/*    */ import java.io.File;
/*    */ import java.util.Locale;
/*    */ import java.util.Properties;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.apache.log4j.PropertyConfigurator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LogHandler
/*    */   implements SettingsObserver
/*    */ {
/*    */   private TemplateProperties logProperties;
/*    */   
/*    */   public LogHandler() {
/* 27 */     this.logProperties = null;
/*    */     System.setProperty("log4j.defaultInitOverride", "true");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update(ObservableSubject savedSettings, Object o) {
/* 35 */     Properties p = ((PersistentClientSettings)savedSettings).getSettings();
/* 36 */     String logging = p.getProperty("log.logging");
/* 37 */     if (logging != null && logging.toUpperCase(Locale.ENGLISH).compareTo("TRUE") == 0) {
/*    */       
/* 39 */       this.logProperties.put("log4j.logger.com.eoos", "DEBUG,CONSOLE,STDLOG");
/* 40 */       this.logProperties.put("log4j.appender.STDLOG", "org.apache.log4j.RollingFileAppender");
/* 41 */       String fileName = p.getProperty("log.fileName");
/* 42 */       if (fileName != null) {
/* 43 */         this.logProperties.put("log4j.appender.STDLOG.File", getAbsolutePath(fileName));
/*    */       } else {
/* 45 */         this.logProperties.put("log4j.appender.STDLOG.File", getAbsolutePath("sps" + System.getProperty("file.separator") + "spsDefaultLog.txt"));
/*    */       } 
/* 47 */       String maxFileSize = p.getProperty("log.maxFileSize");
/* 48 */       if (maxFileSize != null) {
/* 49 */         this.logProperties.put("log4j.appender.STDLOG.MaxFileSize", maxFileSize);
/*    */       }
/* 51 */       String detailedLog = p.getProperty("log.detailed");
/* 52 */       if (detailedLog != null && detailedLog.toUpperCase(Locale.ENGLISH).compareTo("TRUE") == 0) {
/* 53 */         this.logProperties.put("log4j.appender.STDLOG.threshold", "DEBUG");
/*    */       } else {
/* 55 */         this.logProperties.put("log4j.appender.STDLOG.threshold", "INFO");
/*    */       } 
/*    */     } else {
/* 58 */       this.logProperties.put("log4j.rootLogger", "INFO, CONSOLE");
/*    */     } 
/*    */     try {
/* 61 */       PropertyConfigurator.configure(this.logProperties);
/* 62 */       Logger.getLogger(LogHandler.class).info(o);
/* 63 */     } catch (Exception e) {}
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean init() {
/* 71 */     boolean result = false;
/* 72 */     this.logProperties = new TemplateProperties();
/*    */     try {
/* 74 */       this.logProperties.load("com/eoos/gm/tis2web/sps/client/settings/log4jTemplate.properties");
/* 75 */       result = true;
/* 76 */     } catch (Exception e) {
/* 77 */       System.out.println("WARNING: Cannot load log4j configuration");
/*    */     } 
/* 79 */     return result;
/*    */   }
/*    */   
/*    */   private String getAbsolutePath(String path) {
/* 83 */     String result = null;
/* 84 */     File f = new File(path);
/* 85 */     if (!f.isAbsolute()) {
/* 86 */       result = System.getProperty("user.home") + System.getProperty("file.separator") + "sps" + System.getProperty("file.separator") + path;
/*    */     } else {
/* 88 */       result = path;
/*    */     } 
/* 90 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\settings\impl\LogHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */