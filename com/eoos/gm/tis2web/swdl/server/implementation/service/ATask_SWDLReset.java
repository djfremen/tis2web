/*    */ package com.eoos.gm.tis2web.swdl.server.implementation.service;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.AdministrativeTask;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClusterTaskExecution;
/*    */ import com.eoos.gm.tis2web.frame.export.common.permission.ComponentAccessPermission;
/*    */ import com.eoos.gm.tis2web.frame.servlet.ClusterTask;
/*    */ import com.eoos.util.Task;
/*    */ import java.net.URL;
/*    */ import java.util.Iterator;
/*    */ import java.util.Locale;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class ATask_SWDLReset
/*    */   implements AdministrativeTask
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 20 */   private static final Logger log = Logger.getLogger(ATask_SWDLReset.class);
/*    */   
/*    */   public void execute(ClientContext principal) throws Exception {
/* 23 */     log.info("executing");
/* 24 */     log.debug("...creating task");
/* 25 */     ClusterTask clusterTask = new ClusterTask()
/*    */       {
/*    */         private static final long serialVersionUID = 1L;
/*    */         
/*    */         public Object execute() {
/* 30 */           Object retValue = null;
/*    */           try {
/* 32 */             SWDLServiceImpl.getInstance().reset();
/* 33 */           } catch (Exception e) {
/* 34 */             retValue = e;
/*    */           } 
/* 36 */           return retValue;
/*    */         }
/*    */       };
/*    */     
/* 40 */     log.debug("...creating cluster task wrapper");
/* 41 */     ClusterTaskExecution clusterTaskExecution = new ClusterTaskExecution((Task)clusterTask, principal);
/* 42 */     ClusterTaskExecution.Result result = clusterTaskExecution.execute();
/* 43 */     for (Iterator<URL> iter = result.getClusterURLs().iterator(); iter.hasNext(); ) {
/* 44 */       URL url = iter.next();
/* 45 */       if (result.getResult(url) instanceof Exception) {
/* 46 */         log.warn("failed to reset swdl service for cluster server :" + url);
/*    */       }
/*    */     } 
/* 49 */     if (result.getLocalResult() instanceof Exception) {
/* 50 */       log.debug("rethrowing exception (result of local execution)");
/* 51 */       throw (Exception)result.getLocalResult();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDenotation(Locale locale) {
/* 57 */     return ApplicationContext.getInstance().getLabel(locale, "swdl.service.reset");
/*    */   }
/*    */   
/*    */   public String getErrorMessage(Exception e, Locale locale) {
/* 61 */     return ApplicationContext.getInstance().getMessage(locale, "swdl.service.reset.failed");
/*    */   }
/*    */   
/*    */   public String getSuccessMessage(Locale locale) {
/* 65 */     return ApplicationContext.getInstance().getMessage(locale, "swdl.service.reset.succeeded");
/*    */   }
/*    */   
/*    */   public boolean isAvailable(ClientContext context) {
/* 69 */     return ComponentAccessPermission.getInstance(context).check("admin.swdl.service.reset");
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\server\implementation\service\ATask_SWDLReset.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */