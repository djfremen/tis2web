/*    */ package com.eoos.gm.tis2web.frame.mail.relay;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.ConfigurationServiceProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.declaration.service.MailService;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import com.eoos.util.Task;
/*    */ import java.util.Collections;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class SendMailTask_ServerPart
/*    */   implements Task
/*    */ {
/* 14 */   private static final Logger log = Logger.getLogger(SendMailTask_ServerPart.class);
/*    */   
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   private final SendMailTask clientPart;
/*    */   
/*    */   SendMailTask_ServerPart(SendMailTask clientPart) {
/* 21 */     this.clientPart = clientPart;
/*    */   }
/*    */   
/*    */   public Object execute() {
/*    */     try {
/* 26 */       if (log.isDebugEnabled()) {
/* 27 */         log.debug("sending email - original data: ");
/* 28 */         log.debug("...sender: " + this.clientPart.sender);
/* 29 */         log.debug("...recipient(s): " + this.clientPart.recipients);
/* 30 */         log.debug("...subject: " + this.clientPart.subject);
/* 31 */         log.debug("...reply-to: " + this.clientPart.replyTo);
/*    */       } 
/* 33 */       MailService mailService = (MailService)FrameServiceProvider.getInstance().getService(MailService.class);
/*    */       
/* 35 */       String sender = ConfigurationServiceProvider.getService().getProperty("frame.default.mail.sender");
/*    */       
/* 37 */       if (this.clientPart.sender != null) {
/* 38 */         if (Util.isNullOrEmpty(this.clientPart.replyTo)) {
/* 39 */           log.debug("...transferring original sender to reply-to field");
/* 40 */           this.clientPart.replyTo = Collections.singleton(this.clientPart.sender);
/*    */         } else {
/* 42 */           log.debug("...reply-to field is not null, dropping original sender field");
/*    */         } 
/*    */       }
/*    */       
/* 46 */       if (log.isDebugEnabled()) {
/* 47 */         log.debug("sending email - adjusted data: ");
/* 48 */         log.debug("...sender: " + sender);
/* 49 */         log.debug("...reply-to: " + this.clientPart.replyTo);
/*    */       } 
/* 51 */       mailService.send(sender, this.clientPart.replyTo, this.clientPart.recipients, this.clientPart.subject, this.clientPart.text, this.clientPart.eoosDataSources);
/* 52 */       return null;
/* 53 */     } catch (Exception e) {
/* 54 */       log.error("...SendMailTask_ServerPart.execute() - exception:" + e, e);
/* 55 */       return e;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\mail\relay\SendMailTask_ServerPart.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */