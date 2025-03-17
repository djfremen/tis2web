/*    */ package com.eoos.gm.tis2web.registration.server;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.declaration.service.MailService;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import javax.activation.DataSource;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RegistrationMail
/*    */ {
/* 15 */   private static final Logger log = Logger.getLogger(RegistrationMail.class);
/*    */   
/*    */   public static void sendMail(final List recipients, final String subject, final String text) {
/*    */     try {
/* 19 */       MailService service = (MailService)FrameServiceProvider.getInstance().getService(MailService.class);
/* 20 */       MailService.Callback callback = new MailService.Callback()
/*    */         {
/*    */           public DataSource[] getAttachments() {
/* 23 */             return null;
/*    */           }
/*    */           
/*    */           public Collection getRecipients() {
/* 27 */             return recipients;
/*    */           }
/*    */           
/*    */           public String getSender() {
/* 31 */             return ApplicationContext.getInstance().getProperty("component.registration.server.email.sender");
/*    */           }
/*    */           
/*    */           public String getSubject() {
/* 35 */             return subject;
/*    */           }
/*    */           
/*    */           public String getText() {
/* 39 */             return text;
/*    */           }
/*    */           
/*    */           public Collection getReplyTo() {
/* 43 */             return null;
/*    */           }
/*    */         };
/*    */       
/* 47 */       service.send(callback);
/* 48 */     } catch (Exception e) {
/* 49 */       log.warn("unable to send mail, ignoring - exception: " + e, e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\server\RegistrationMail.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */