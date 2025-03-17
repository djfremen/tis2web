/*    */ package com.eoos.gm.tis2web.frame.mail.relay.client;
/*    */ 
/*    */ import com.eoos.gm.tis2web.common.AuthenticationQuery;
/*    */ import com.eoos.gm.tis2web.common.AuthenticatorCI;
/*    */ import com.eoos.gm.tis2web.common.TaskExecutionClient;
/*    */ import com.eoos.gm.tis2web.common.TaskExecutionClientFactory;
/*    */ import com.eoos.gm.tis2web.frame.dls.client.api.DLSServiceFactory;
/*    */ import com.eoos.gm.tis2web.frame.dls.client.api.Lease;
/*    */ import com.eoos.gm.tis2web.frame.dls.client.api.SoftwareKey;
/*    */ import com.eoos.gm.tis2web.frame.mail.relay.SendMailTask;
/*    */ import com.eoos.gm.tis2web.frame.mail.relay.client.api.MailRelayingService;
/*    */ import com.eoos.util.Task;
/*    */ import java.net.Authenticator;
/*    */ import java.net.URL;
/*    */ import java.util.Collection;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class MailRelayingServiceImpl
/*    */   implements MailRelayingService {
/* 20 */   private static final Logger log = Logger.getLogger(MailRelayingServiceImpl.class);
/*    */   
/*    */   private Lease lease;
/*    */   
/*    */   private TaskExecutionClient tec;
/*    */   
/*    */   public MailRelayingServiceImpl(SoftwareKey swk, Lease lease, AuthenticationQuery authenticationCallback) {
/* 27 */     this.lease = lease;
/* 28 */     if (authenticationCallback != null) {
/* 29 */       Authenticator.setDefault((Authenticator)new AuthenticatorCI(authenticationCallback));
/*    */     }
/*    */     
/* 32 */     if (this.lease != null) {
/* 33 */       URL url = DLSServiceFactory.createService(authenticationCallback).getURL(lease);
/* 34 */       log.debug("...retrieved server URL for lease: " + String.valueOf(url));
/* 35 */       this.tec = TaskExecutionClientFactory.createTaskExecutionClient(url);
/*    */     } else {
/* 37 */       this.tec = TaskExecutionClientFactory.createTaskExecutionClient();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void send(String sender, Collection replyTo, Collection recipients, String subject, String text, Collection eoosDataSource) throws Exception {
/* 44 */     log.info("sending mail....");
/* 45 */     SendMailTask sendMailTask = new SendMailTask(sender, replyTo, recipients, subject, text, eoosDataSource);
/* 46 */     boolean done = false;
/* 47 */     while (!done) {
/*    */       try {
/* 49 */         rethrow(this.tec.execute((Task)sendMailTask));
/* 50 */         done = true;
/* 51 */         log.debug("...mail successfully send");
/* 52 */       } catch (Exception e) {
/* 53 */         log.error("unable to send mail, rethrowing Exception - exception: " + e, e);
/* 54 */         throw e;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private void rethrow(Object obj) throws Exception {
/* 61 */     if (obj != null && obj instanceof Exception)
/* 62 */       throw (Exception)obj; 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\mail\relay\client\MailRelayingServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */