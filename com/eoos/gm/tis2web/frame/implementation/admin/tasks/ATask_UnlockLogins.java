/*    */ package com.eoos.gm.tis2web.frame.implementation.admin.tasks;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.DefaultController;
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
/*    */ public class ATask_UnlockLogins
/*    */   implements AdministrativeTask
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 21 */   private static final Logger log = Logger.getLogger(ATask_UnlockLogins.class);
/*    */   
/*    */   public boolean isAvailable(ClientContext context) {
/* 24 */     return (context.isSpecialAccess() || ComponentAccessPermission.getInstance(context).check("update.configuration"));
/*    */   }
/*    */   
/*    */   public String getDenotation(Locale locale) {
/* 28 */     return ApplicationContext.getInstance().getLabel(locale, "enable.user.logins");
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ClientContext principal) throws Exception {
/* 33 */     ClusterTask clusterTask = new ClusterTask()
/*    */       {
/*    */         private static final long serialVersionUID = 1L;
/*    */         
/*    */         public Object execute() {
/* 38 */           Object retValue = null;
/*    */           try {
/* 40 */             ATask_UnlockLogins.log.info("enabling user logins");
/* 41 */             DefaultController.getInstance().enableLogins();
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
/* 56 */         log.warn("failed to enable user logins for cluster server :" + url);
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
/* 67 */     return ApplicationContext.getInstance().getMessage(locale, "enabled.user.logins.failed");
/*    */   }
/*    */   
/*    */   public String getSuccessMessage(Locale locale) {
/* 71 */     return ApplicationContext.getInstance().getMessage(locale, "enabled.user.logins.successful");
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\admin\tasks\ATask_UnlockLogins.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */