/*    */ package com.eoos.gm.tis2web.kdr.client;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.dls.client.SoftwareKeyCheckClient;
/*    */ import com.eoos.log.Log4jUtil;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.io.File;
/*    */ import java.io.InterruptedIOException;
/*    */ import javax.swing.JOptionPane;
/*    */ import org.apache.log4j.Appender;
/*    */ import org.apache.log4j.Layout;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.apache.log4j.RollingFileAppender;
/*    */ 
/*    */ public class Startup
/*    */ {
/* 16 */   private static final Logger log = Logger.getLogger(Startup.class);
/*    */   
/*    */   public static void main(String[] args) throws Throwable {
/* 19 */     Log4jUtil.attachConsoleAppender(Logger.getRootLogger(), Log4jUtil.PL_SIMPLE_WITH_SHORT_CATEGORY);
/* 20 */     File homeDir = new File(System.getProperty("user.home"));
/* 21 */     homeDir = new File(homeDir, ".gdsweblaunch");
/* 22 */     homeDir.mkdirs();
/*    */     
/* 24 */     RollingFileAppender appender = new RollingFileAppender();
/* 25 */     appender.setLayout((Layout)Log4jUtil.PL_SIMPLE_WITH_SHORT_CATEGORY);
/* 26 */     appender.setFile((new File(homeDir, "gdsweblaunch.log")).getAbsolutePath());
/* 27 */     appender.setAppend(false);
/* 28 */     appender.setImmediateFlush(true);
/* 29 */     appender.setMaximumFileSize(52428800L);
/* 30 */     appender.setMaxBackupIndex(10);
/* 31 */     appender.activateOptions();
/*    */     
/* 33 */     Logger.getRootLogger().addAppender((Appender)appender);
/*    */     
/* 35 */     boolean additivity = false;
/* 36 */     if (Boolean.getBoolean("debug")) {
/* 37 */       additivity = true;
/*    */     }
/* 39 */     Logger.getLogger("org.apache").setAdditivity(additivity);
/* 40 */     Logger.getLogger("httpclient.wire").setAdditivity(additivity);
/*    */     
/*    */     try {
/* 43 */       if (SoftwareKeyCheckClient.checkSoftwareKey("com.eoos.gm.tis2web.kdr.client.lbl_msg") == 0) {
/*    */         
/*    */         try {
/* 46 */           log.debug("***application START ");
/* 47 */           KDRClient client = new KDRClient(homeDir);
/* 48 */           client.startup();
/* 49 */           log.debug("***application EXITED normally");
/* 50 */         } catch (RuntimeException e) {
/* 51 */           Throwable t = e;
/* 52 */           if (t.getClass() == RuntimeException.class && t.getCause() != null) {
/* 53 */             log.info("unwrapping exception of type " + t.getClass().getName());
/* 54 */             t = t.getCause();
/*    */           } 
/* 56 */           throw t;
/*    */         } 
/*    */       }
/* 59 */     } catch (InterruptedException e) {
/* 60 */       log.warn("!!! interrupted !!!", e);
/* 61 */     } catch (InterruptedIOException e) {
/* 62 */       log.warn("!!! interrupted !!!!", e);
/* 63 */     } catch (Throwable t) {
/* 64 */       log.error("unable to execute - exception: " + t, t);
/* 65 */       Util.executeOnAWTThread(new Runnable()
/*    */           {
/*    */             public void run() {
/* 68 */               JOptionPane.showMessageDialog(null, "Unable to launch GDS (see log for details)", "Error", 0);
/*    */             }
/*    */           }true);
/*    */     } finally {
/*    */       
/* 73 */       System.exit(0);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\kdr\client\Startup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */