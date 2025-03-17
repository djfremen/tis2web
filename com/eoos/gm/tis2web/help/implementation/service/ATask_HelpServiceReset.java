/*    */ package com.eoos.gm.tis2web.help.implementation.service;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.AdministrativeTask;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClusterTaskExecution;
/*    */ import com.eoos.gm.tis2web.frame.export.common.permission.ComponentAccessPermission;
/*    */ import com.eoos.gm.tis2web.frame.servlet.ClusterTask;
/*    */ import com.eoos.gm.tis2web.help.service.HelpServiceProvider;
/*    */ import com.eoos.util.Task;
/*    */ import java.net.URL;
/*    */ import java.util.Iterator;
/*    */ import java.util.Locale;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ATask_HelpServiceReset
/*    */   implements AdministrativeTask
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 22 */   private static final Logger log = Logger.getLogger(ATask_HelpServiceReset.class);
/*    */   
/*    */   public void execute(ClientContext principal) throws Exception {
/* 25 */     log.info("executing");
/* 26 */     log.debug("...creating task");
/* 27 */     ClusterTask clusterTask = new ClusterTask()
/*    */       {
/*    */         private static final long serialVersionUID = 1L;
/*    */         
/*    */         public Object execute() {
/* 32 */           Object retValue = null;
/*    */           try {
/* 34 */             HelpServiceProvider.getInstance().getService().reset();
/* 35 */           } catch (Exception e) {
/* 36 */             retValue = e;
/*    */           } 
/* 38 */           return retValue;
/*    */         }
/*    */       };
/*    */     
/* 42 */     log.debug("...creating cluster task wrapper");
/* 43 */     ClusterTaskExecution clusterTaskExecution = new ClusterTaskExecution((Task)clusterTask, principal);
/* 44 */     ClusterTaskExecution.Result result = clusterTaskExecution.execute();
/* 45 */     for (Iterator<URL> iter = result.getClusterURLs().iterator(); iter.hasNext(); ) {
/* 46 */       URL url = iter.next();
/* 47 */       if (result.getResult(url) instanceof Exception) {
/* 48 */         log.warn("failed to reset help service for cluster server :" + url);
/*    */       }
/*    */     } 
/* 51 */     if (result.getLocalResult() instanceof Exception) {
/* 52 */       log.debug("rethrowing exception (result of local execution)");
/* 53 */       throw (Exception)result.getLocalResult();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDenotation(Locale locale) {
/* 59 */     return ApplicationContext.getInstance().getLabel(locale, "help.service.reset");
/*    */   }
/*    */   
/*    */   public String getErrorMessage(Exception e, Locale locale) {
/* 63 */     return ApplicationContext.getInstance().getMessage(locale, "help.service.reset.failed");
/*    */   }
/*    */   
/*    */   public String getSuccessMessage(Locale locale) {
/* 67 */     return ApplicationContext.getInstance().getMessage(locale, "help.service.reset.succeeded");
/*    */   }
/*    */   
/*    */   public boolean isAvailable(ClientContext context) {
/* 71 */     return ComponentAccessPermission.getInstance(context).check("admin.help.service.reset");
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\help\implementation\service\ATask_HelpServiceReset.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */