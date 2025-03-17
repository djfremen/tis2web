/*    */ package com.eoos.gm.tis2web.frame.implementation.admin.tasks;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.AdministrativeTask;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClusterTaskExecution;
/*    */ import com.eoos.gm.tis2web.frame.export.common.permission.ComponentAccessPermission;
/*    */ import com.eoos.gm.tis2web.frame.export.declaration.service.ConfigurationService;
/*    */ import com.eoos.gm.tis2web.frame.servlet.ClusterTask;
/*    */ import com.eoos.util.Task;
/*    */ import java.net.URL;
/*    */ import java.util.Iterator;
/*    */ import java.util.Locale;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class ATask_UpdateConfiguration
/*    */   implements AdministrativeTask
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 22 */   private static final Logger log = Logger.getLogger(ATask_UpdateConfiguration.class);
/*    */   
/*    */   public boolean isAvailable(ClientContext context) {
/* 25 */     return ComponentAccessPermission.getInstance(context).check("update.configuration");
/*    */   }
/*    */   
/*    */   public String getDenotation(Locale locale) {
/* 29 */     return ApplicationContext.getInstance().getLabel(locale, "update.configuration");
/*    */   }
/*    */   
/*    */   public void execute(ClientContext principal) throws Exception {
/* 33 */     ClusterTask clusterTask = new ClusterTask()
/*    */       {
/*    */         private static final long serialVersionUID = 1L;
/*    */         
/*    */         public Object execute() {
/* 38 */           Object retValue = null;
/*    */           try {
/* 40 */             ConfigurationService configService = (ConfigurationService)FrameServiceProvider.getInstance().getService(ConfigurationService.class);
/* 41 */             configService.update();
/*    */           }
/* 43 */           catch (Exception e) {
/* 44 */             retValue = e;
/*    */           } 
/* 46 */           return retValue;
/*    */         }
/*    */       };
/*    */ 
/*    */     
/* 51 */     ClusterTaskExecution clusterTaskExecution = new ClusterTaskExecution((Task)clusterTask, principal);
/* 52 */     ClusterTaskExecution.Result result = clusterTaskExecution.execute();
/* 53 */     for (Iterator<URL> iter = result.getClusterURLs().iterator(); iter.hasNext(); ) {
/* 54 */       URL url = iter.next();
/* 55 */       if (result.getResult(url) instanceof Exception) {
/* 56 */         log.warn("failed to update configuration for cluster server :" + url);
/*    */       }
/*    */     } 
/*    */     
/* 60 */     if (result.getLocalResult() instanceof Exception) {
/* 61 */       throw (Exception)result.getLocalResult();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String getErrorMessage(Exception e, Locale locale) {
/* 67 */     return ApplicationContext.getInstance().getMessage(locale, "configuration.update.failed");
/*    */   }
/*    */   
/*    */   public String getSuccessMessage(Locale locale) {
/* 71 */     return ApplicationContext.getInstance().getMessage(locale, "configuration.update.successful");
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\admin\tasks\ATask_UpdateConfiguration.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */