/*    */ package com.eoos.gm.tis2web.frame.dls.client;
/*    */ 
/*    */ import com.eoos.gm.tis2web.common.TaskExecutionClient;
/*    */ import com.eoos.gm.tis2web.common.TaskExecutionClientFactory;
/*    */ import com.eoos.gm.tis2web.frame.dls.CheckStartupTask;
/*    */ import com.eoos.gm.tis2web.frame.dls.client.api.DLSManagement;
/*    */ import com.eoos.gm.tis2web.frame.dls.client.api.DLSService;
/*    */ import com.eoos.gm.tis2web.frame.dls.client.api.DLSServiceFactory;
/*    */ import com.eoos.gm.tis2web.frame.dls.client.api.SoftwareKey;
/*    */ import com.eoos.propcfg.util.ConfigurationUtil;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import com.eoos.util.Task;
/*    */ import java.io.File;
/*    */ import java.util.Locale;
/*    */ import java.util.ResourceBundle;
/*    */ import javax.swing.JOptionPane;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SoftwareKeyCheckClient
/*    */ {
/* 23 */   private static final Logger log = Logger.getLogger(SoftwareKeyCheckClient.class);
/* 24 */   private static final Object SYNC_DLSSERV = new Object();
/* 25 */   private static DLSService dlsService = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static final String INSTSWKEYLOCAL = "register.swk";
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int checkSoftwareKey(String bundlePath) throws Exception {
/* 42 */     log.debug("checking startup authorization ...");
/* 43 */     TaskExecutionClient tec = TaskExecutionClientFactory.createTaskExecutionClient();
/*    */     
/* 45 */     SoftwareKey swk = null;
/*    */ 
/*    */     
/* 48 */     if (Boolean.getBoolean("register.swk")) {
/* 49 */       log.debug("...computing swk");
/* 50 */       swk = ((DLSManagement)getDLSService()).computeSoftwareKey();
/*    */     } 
/*    */     
/* 53 */     CheckStartupTask task = new CheckStartupTask(swk, System.getProperty("session.id"));
/* 54 */     if (!CheckStartupTask.evaluateResult(tec.execute((Task)task))) {
/* 55 */       if (bundlePath != null) {
/* 56 */         showError(bundlePath);
/*    */       }
/* 58 */       return -1;
/*    */     } 
/* 60 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   private static DLSService getDLSService() {
/* 65 */     synchronized (SYNC_DLSSERV) {
/* 66 */       if (dlsService == null) {
/* 67 */         dlsService = DLSServiceFactory.createServiceSwk(new File(ConfigurationUtil.getSystemPropertiesAdapter().getProperty("user.home")));
/*    */       }
/* 69 */       return dlsService;
/*    */     } 
/*    */   }
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
/*    */   private static void showError(String bundlePath) {
/* 83 */     Locale locale = Util.parseLocale(System.getProperty("language.id"));
/* 84 */     if (locale == null) {
/* 85 */       locale = Locale.ENGLISH;
/*    */     }
/* 87 */     ResourceBundle bundle = ResourceBundle.getBundle(bundlePath, locale);
/*    */     
/* 89 */     final String popupTitle = bundle.getString("softwareKeyCheck.error.panel.title");
/* 90 */     final String popupMsg = bundle.getString("softwareKeyCheck.error.panel.message");
/* 91 */     Util.executeOnAWTThread(new Runnable() {
/*    */           public void run() {
/* 93 */             JOptionPane.showMessageDialog(null, popupMsg, popupTitle, 0);
/*    */           }
/*    */         }true);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dls\client\SoftwareKeyCheckClient.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */