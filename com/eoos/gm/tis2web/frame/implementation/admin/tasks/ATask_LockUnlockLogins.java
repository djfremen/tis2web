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
/*    */ public class ATask_LockUnlockLogins
/*    */   implements AdministrativeTask
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   private static final class MyClusterTask
/*    */     implements ClusterTask {
/*    */     private final boolean enable;
/*    */     private static final long serialVersionUID = 1L;
/*    */     
/*    */     private MyClusterTask(boolean enable) {
/* 27 */       this.enable = enable;
/*    */     }
/*    */     
/*    */     public Object execute() {
/* 31 */       Object retValue = null;
/*    */       try {
/* 33 */         if (this.enable) {
/* 34 */           ATask_LockUnlockLogins.log.info("enabling user logins");
/* 35 */           DefaultController.getInstance().enableLogins();
/*    */         } else {
/* 37 */           ATask_LockUnlockLogins.log.info("disabling user logins");
/* 38 */           DefaultController.getInstance().disableLogins();
/*    */         }
/*    */       
/* 41 */       } catch (Exception e) {
/* 42 */         retValue = e;
/*    */       } 
/* 44 */       return retValue;
/*    */     }
/*    */   }
/*    */   
/* 48 */   private static final Logger log = Logger.getLogger(ATask_LockUnlockLogins.class);
/*    */   
/*    */   public boolean isAvailable(ClientContext context) {
/* 51 */     return (context.isSpecialAccess() || ComponentAccessPermission.getInstance(context).check("update.configuration"));
/*    */   }
/*    */   
/*    */   public String getDenotation(Locale locale) {
/* 55 */     String key = DefaultController.getInstance().loginsDisabled() ? "enable.user.logins" : "disable.user.logins";
/* 56 */     return ApplicationContext.getInstance().getLabel(locale, key);
/*    */   }
/*    */   
/*    */   public void execute(ClientContext principal) throws Exception {
/* 60 */     boolean enable = DefaultController.getInstance().loginsDisabled();
/*    */     
/* 62 */     MyClusterTask myClusterTask = new MyClusterTask(enable);
/*    */     
/* 64 */     ClusterTaskExecution clusterTaskExecution = new ClusterTaskExecution((Task)myClusterTask, principal);
/* 65 */     ClusterTaskExecution.Result result = clusterTaskExecution.execute();
/* 66 */     for (Iterator<URL> iter = result.getClusterURLs().iterator(); iter.hasNext(); ) {
/* 67 */       URL url = iter.next();
/* 68 */       if (result.getResult(url) instanceof Exception) {
/* 69 */         log.warn("failed to disable/enable user logins for cluster server :" + url);
/*    */       }
/*    */     } 
/*    */     
/* 73 */     if (result.getLocalResult() instanceof Exception) {
/* 74 */       throw (Exception)result.getLocalResult();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String getErrorMessage(Exception e, Locale locale) {
/* 80 */     return ApplicationContext.getInstance().getMessage(locale, "user.logins.trigger.update.failed");
/*    */   }
/*    */   
/*    */   public String getSuccessMessage(Locale locale) {
/* 84 */     return ApplicationContext.getInstance().getMessage(locale, "user.logins.trigger.update.successful");
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\admin\tasks\ATask_LockUnlockLogins.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */