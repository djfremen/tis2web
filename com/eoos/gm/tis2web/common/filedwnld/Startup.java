/*    */ package com.eoos.gm.tis2web.common.filedwnld;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.dls.client.SoftwareKeyCheckClient;
/*    */ import com.eoos.log.Log4jUtil;
/*    */ import com.eoos.propcfg.util.SystemPropertiesAdapter;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.io.File;
/*    */ import java.util.Locale;
/*    */ import javax.swing.JOptionPane;
/*    */ import org.apache.log4j.Appender;
/*    */ import org.apache.log4j.Layout;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.apache.log4j.RollingFileAppender;
/*    */ 
/*    */ 
/*    */ public class Startup
/*    */ {
/* 18 */   private static final Logger log = Logger.getLogger(Startup.class);
/*    */   
/*    */   public static void main(String[] args) throws Throwable {
/* 21 */     Log4jUtil.attachConsoleAppender(Logger.getRootLogger(), Log4jUtil.PL_SIMPLE_WITH_SHORT_CATEGORY);
/* 22 */     File homeDir = new File(System.getProperty("user.home"));
/* 23 */     homeDir = new File(homeDir, "tis2web/filedwnld");
/* 24 */     homeDir.mkdirs();
/*    */     
/* 26 */     RollingFileAppender appender = new RollingFileAppender();
/* 27 */     appender.setLayout((Layout)Log4jUtil.PL_SIMPLE_WITH_SHORT_CATEGORY);
/* 28 */     appender.setFile((new File(homeDir, ".log")).getAbsolutePath());
/* 29 */     appender.setAppend(false);
/* 30 */     appender.setImmediateFlush(true);
/* 31 */     appender.setMaximumFileSize(52428800L);
/* 32 */     appender.setMaxBackupIndex(10);
/* 33 */     appender.activateOptions();
/*    */     
/* 35 */     Logger.getRootLogger().addAppender((Appender)appender);
/*    */     
/*    */     try {
/* 38 */       SystemPropertiesAdapter systemPropertiesAdapter = SystemPropertiesAdapter.getInstance();
/* 39 */       Locale locale = Util.parseLocale(systemPropertiesAdapter.getProperty("language.id"));
/* 40 */       locale = (locale != null) ? locale : Locale.ENGLISH;
/* 41 */       final LabelResource labelResource = new LabelResource(locale);
/*    */       
/*    */       try {
/* 44 */         if (SoftwareKeyCheckClient.checkSoftwareKey(null) != -1) {
/*    */           
/* 46 */           log.debug("***application START ");
/* 47 */           FileDwnld.execute(homeDir, labelResource, locale);
/* 48 */           log.debug("***application EXITED normally");
/*    */         } else {
/* 50 */           throw new FeedbackException(labelResource.getMessage("software.key.check.failure"), null);
/*    */         } 
/* 52 */       } catch (InterruptedException e) {
/* 53 */         throw e;
/* 54 */       } catch (Exception e) {
/* 55 */         final String message = (e instanceof FeedbackException) ? ((FeedbackException)e).getMessage() : labelResource.getMessage("error.see.log");
/* 56 */         Util.executeOnAWTThread(new Runnable()
/*    */             {
/*    */               public void run() {
/* 59 */                 JOptionPane.showMessageDialog(null, message, labelResource.getLabel("error"), 0);
/*    */               }
/*    */             }true);
/*    */         
/* 63 */         if (e.getCause() != null) {
/* 64 */           throw e.getCause();
/*    */         }
/* 66 */         log.error(e.getMessage());
/*    */       }
/*    */     
/* 69 */     } catch (InterruptedException e) {
/* 70 */       log.info("!!! interrupted !!!", e);
/* 71 */     } catch (Throwable t) {
/* 72 */       log.error("unable to execute - exception: " + t, t);
/*    */     } finally {
/* 74 */       System.exit(0);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\common\filedwnld\Startup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */