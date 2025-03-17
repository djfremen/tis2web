/*    */ package com.eoos.gm.tis2web.sids.implementation;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.AdministrativeTask;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClusterTaskExecution;
/*    */ import com.eoos.gm.tis2web.frame.export.common.permission.ComponentAccessPermission;
/*    */ import com.eoos.gm.tis2web.frame.servlet.ClusterTask;
/*    */ import com.eoos.gm.tis2web.sids.service.ServiceIDServiceProvider;
/*    */ import com.eoos.util.Task;
/*    */ import java.net.URL;
/*    */ import java.util.Iterator;
/*    */ import java.util.Locale;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ResetTaskSIDS
/*    */   implements AdministrativeTask
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 24 */   private static final Logger log = Logger.getLogger(ResetTaskSIDS.class);
/*    */   
/*    */   public void execute(ClientContext principal) throws Exception {
/* 27 */     log.info("executing");
/* 28 */     log.debug("...creating task");
/* 29 */     ClusterTask clusterTask = new ClusterTask()
/*    */       {
/*    */         private static final long serialVersionUID = 1L;
/*    */         
/*    */         public Object execute() {
/* 34 */           Object retValue = null;
/*    */           try {
/* 36 */             ServiceIDServiceProvider.getInstance().getService().reset();
/* 37 */           } catch (Exception e) {
/* 38 */             retValue = e;
/*    */           } 
/* 40 */           return retValue;
/*    */         }
/*    */       };
/*    */     
/* 44 */     log.debug("...creating cluster task wrapper");
/* 45 */     ClusterTaskExecution clusterTaskExecution = new ClusterTaskExecution((Task)clusterTask, principal);
/* 46 */     ClusterTaskExecution.Result result = clusterTaskExecution.execute();
/* 47 */     for (Iterator<URL> iter = result.getClusterURLs().iterator(); iter.hasNext(); ) {
/* 48 */       URL url = iter.next();
/* 49 */       if (result.getResult(url) instanceof Exception) {
/* 50 */         log.warn("failed to reset swdl service for cluster server :" + url);
/*    */       }
/*    */     } 
/* 53 */     if (result.getLocalResult() instanceof Exception) {
/* 54 */       log.debug("rethrowing exception (result of local execution)");
/* 55 */       throw (Exception)result.getLocalResult();
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getDenotation(Locale locale) {
/* 60 */     return ApplicationContext.getInstance().getLabel(locale, "sids.service.reset");
/*    */   }
/*    */   
/*    */   public String getErrorMessage(Exception e, Locale locale) {
/* 64 */     return ApplicationContext.getInstance().getMessage(locale, "sids.service.reset.failed");
/*    */   }
/*    */   
/*    */   public String getSuccessMessage(Locale locale) {
/* 68 */     return ApplicationContext.getInstance().getMessage(locale, "sids.service.reset.succeeded");
/*    */   }
/*    */   
/*    */   public boolean isAvailable(ClientContext context) {
/* 72 */     return ComponentAccessPermission.getInstance(context).check("admin.sids.service.reset");
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sids\implementation\ResetTaskSIDS.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */