/*     */ package com.eoos.gm.tis2web.lt.icop.cfgclient;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.dls.client.SoftwareKeyCheckClient;
/*     */ import com.eoos.log.Log4jUtil;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.util.SystemPropertiesAdapter;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.io.File;
/*     */ import java.util.Locale;
/*     */ import javax.swing.JOptionPane;
/*     */ import org.apache.log4j.Appender;
/*     */ import org.apache.log4j.Layout;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.RollingFileAppender;
/*     */ 
/*     */ public class Startup
/*     */ {
/*  18 */   private static final Logger log = Logger.getLogger(Startup.class);
/*     */   
/*     */   public static void main(String[] args) throws Throwable {
/*  21 */     Log4jUtil.attachConsoleAppender(Logger.getRootLogger(), Log4jUtil.PL_SIMPLE_WITH_SHORT_CATEGORY);
/*  22 */     File logDir = new File(System.getProperty("user.home"));
/*  23 */     logDir = new File(logDir, "icop");
/*  24 */     logDir.mkdirs();
/*     */     
/*  26 */     RollingFileAppender appender = new RollingFileAppender();
/*  27 */     appender.setLayout((Layout)Log4jUtil.PL_SIMPLE_WITH_SHORT_CATEGORY);
/*  28 */     appender.setFile((new File(logDir, "icopcfgclient.log")).getAbsolutePath());
/*  29 */     appender.setAppend(false);
/*  30 */     appender.setImmediateFlush(true);
/*  31 */     appender.setMaximumFileSize(52428800L);
/*  32 */     appender.setMaxBackupIndex(10);
/*  33 */     appender.activateOptions();
/*     */     
/*  35 */     Logger.getRootLogger().addAppender((Appender)appender);
/*     */     
/*  37 */     File homeDir = new File(System.getenv("ALLUSERSPROFILE"));
/*  38 */     homeDir = new File(homeDir, "tis2web/icop");
/*  39 */     if (!homeDir.exists()) {
/*  40 */       homeDir.mkdirs();
/*  41 */       adjustRights(homeDir);
/*     */     } 
/*     */     
/*     */     try {
/*  45 */       SystemPropertiesAdapter systemPropertiesAdapter = SystemPropertiesAdapter.getInstance();
/*  46 */       Locale locale = Util.parseLocale(systemPropertiesAdapter.getProperty("language.id"));
/*  47 */       locale = (locale != null) ? locale : Locale.ENGLISH;
/*  48 */       final LabelResource labelResource = new LabelResource(locale);
/*     */       
/*     */       try {
/*  51 */         if (SoftwareKeyCheckClient.checkSoftwareKey(null) != -1) {
/*     */           
/*  53 */           log.debug("***application START ");
/*  54 */           ICOPCfgClient client = new ICOPCfgClient(homeDir, (Configuration)systemPropertiesAdapter, locale, labelResource);
/*  55 */           client.execute();
/*  56 */           log.debug("***application EXITED normally");
/*     */         } else {
/*  58 */           Util.executeOnAWTThread(new Runnable()
/*     */               {
/*     */                 public void run() {
/*  61 */                   JOptionPane.showMessageDialog(null, labelResource.getMessage("software.key.check.failure"), labelResource.getLabel("error"), 0);
/*     */                 }
/*     */               }true);
/*     */         }
/*     */       
/*     */       }
/*  67 */       catch (InterruptedException e) {
/*  68 */         log.info("!!! interrupted !!!", e);
/*  69 */       } catch (Throwable t) {
/*  70 */         Util.executeOnAWTThread(new Runnable()
/*     */             {
/*     */               public void run() {
/*  73 */                 JOptionPane.showMessageDialog(null, labelResource.getMessage("unable.to.execute"), labelResource.getLabel("error"), 0);
/*     */               }
/*     */             }true);
/*     */         
/*  77 */         throw t;
/*     */       } 
/*  79 */     } catch (Throwable t) {
/*  80 */       log.error("unable to execute - exception: " + t, t);
/*     */     } finally {
/*  82 */       System.exit(0);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void adjustRights(File dir) {
/*  87 */     boolean success = false;
/*  88 */     if (System.getProperty("os.name").toUpperCase(Locale.ENGLISH).indexOf("WINDOWS") >= 0 && System.getProperty("os.version").compareTo("6.0") >= 0) {
/*     */       try {
/*  90 */         String cmd = "CMD.EXE /C ICACLS.EXE " + dir.getCanonicalPath() + " /grant *S-1-5-32-545:(OI)(CI)F";
/*  91 */         Process process = Runtime.getRuntime().exec(cmd);
/*  92 */         process.waitFor();
/*  93 */         int rval = process.exitValue();
/*  94 */         if (rval == 0) {
/*  95 */           success = true;
/*     */         }
/*  97 */       } catch (Exception e) {
/*  98 */         log.error("Cannot adjust folder rights: " + e.toString());
/*  99 */         e.printStackTrace();
/*     */       } 
/* 101 */       log.info("Folder rights adjusted: " + success);
/*     */     } else {
/*     */       
/* 104 */       log.debug("Not Vista or Windows 7 - nothing to do.");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\icop\cfgclient\Startup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */