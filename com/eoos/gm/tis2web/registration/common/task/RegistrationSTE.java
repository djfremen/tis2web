/*    */ package com.eoos.gm.tis2web.registration.common.task;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.gm.tis2web.common.TaskExecutionClient;
/*    */ import com.eoos.gm.tis2web.common.TaskExecutionClientFactory;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.io.ObservableInputStream;
/*    */ import com.eoos.io.ObservableOutputStream;
/*    */ import com.eoos.util.Task;
/*    */ import java.io.IOException;
/*    */ import java.net.URL;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RegistrationSTE
/*    */ {
/*    */   public static class MissingAuthenticationException
/*    */     extends Exception
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */   }
/* 29 */   private static final Logger log = Logger.getLogger(RegistrationSTE.class);
/*    */   
/* 31 */   private static RegistrationSTE instance = null;
/*    */   
/*    */   private URL url;
/*    */   
/* 35 */   private TaskExecutionClient tec = null;
/*    */   
/*    */   private RegistrationSTE() {
/* 38 */     log.debug("initializing...");
/*    */     try {
/* 40 */       String _url = ApplicationContext.getInstance().getProperty("frame.registration.url.task.execution");
/* 41 */       this.url = new URL(_url);
/* 42 */       this.tec = TaskExecutionClientFactory.createTaskExecutionClient(this.url);
/* 43 */     } catch (Exception e) {
/* 44 */       throw new ExceptionWrapper(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static synchronized RegistrationSTE getInstance() {
/* 50 */     if (instance == null) {
/* 51 */       instance = new RegistrationSTE();
/*    */     }
/* 53 */     return instance;
/*    */   }
/*    */   
/*    */   public Object execute(Task task, ObservableInputStream.Observer inputObserver, ObservableOutputStream.Observer outputObserver, Authentication pwdAuth) throws MissingAuthenticationException {
/*    */     try {
/* 58 */       return this.tec.execute(task);
/* 59 */     } catch (IOException e) {
/* 60 */       throw new CommunicationException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Object execute(Task task) throws MissingAuthenticationException {
/* 66 */     return execute(task, null, null, null);
/*    */   }
/*    */   
/*    */   public Object execute(Task task, Authentication pwdAuth) throws MissingAuthenticationException {
/* 70 */     return execute(task, null, null, pwdAuth);
/*    */   }
/*    */   
/*    */   public static interface Authentication {
/*    */     String getUser();
/*    */     
/*    */     String getPassword();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\common\task\RegistrationSTE.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */