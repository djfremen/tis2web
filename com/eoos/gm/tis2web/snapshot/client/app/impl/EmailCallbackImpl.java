/*    */ package com.eoos.gm.tis2web.snapshot.client.app.impl;
/*    */ 
/*    */ import com.eoos.datatype.gtwo.PairImpl;
/*    */ import com.eoos.gm.tis2web.snapshot.client.app.EmailCallback;
/*    */ import com.eoos.gm.tis2web.snapshot.client.common.ClientAppContextProvider;
/*    */ import com.eoos.gm.tis2web.snapshot.common.export.email.Email;
/*    */ import com.eoos.gm.tis2web.snapshot.common.export.email.EmailImpl;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class EmailCallbackImpl
/*    */   implements EmailCallback
/*    */ {
/* 14 */   private static final Logger log = Logger.getLogger(EmailCallback.class);
/*    */   
/* 16 */   protected Email email = (Email)new EmailImpl();
/*    */   
/* 18 */   private static EmailCallbackImpl instance = null;
/*    */   
/*    */   public static EmailCallback getInstance() {
/* 21 */     synchronized (EmailCallbackImpl.class) {
/* 22 */       if (instance == null) {
/* 23 */         instance = new EmailCallbackImpl();
/*    */       }
/* 25 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void onSetRecipient(String recipient) {
/* 30 */     this.email.setRecipient(new String(recipient));
/*    */   }
/*    */   
/*    */   public void onSetFrom(String from) {
/* 34 */     this.email.setFrom(new String(from));
/*    */   }
/*    */   
/*    */   public void onSetSubject(String subject) {
/* 38 */     this.email.setSubject("[BAC: " + ClientAppContextProvider.getClientAppContext().getBACCode() + "] " + new String(subject));
/*    */   }
/*    */   
/*    */   public void onSetContent(String content) {
/* 42 */     this.email.setContent(new String(content));
/*    */   }
/*    */   
/*    */   public void onSetAttachment(String name, byte[] attachment) {
/* 46 */     byte[] buffer = new byte[attachment.length];
/* 47 */     System.arraycopy(attachment, 0, buffer, 0, attachment.length);
/* 48 */     PairImpl pairImpl = new PairImpl(name, buffer);
/* 49 */     this.email.getAttachments().clear();
/* 50 */     this.email.getAttachments().add(pairImpl);
/*    */   }
/*    */   
/*    */   public boolean onSendEmail() {
/* 54 */     boolean ret = false;
/* 55 */     log.info("Try to send email To: " + this.email.getRecipient() + " From: " + this.email.getFrom() + " Subject: " + this.email.getSubject() + " Content: " + this.email.getContent());
/*    */     try {
/* 57 */       log.info("begin send email...");
/* 58 */       if (EmailAutoSend.getInstance().sendEmail(this.email) == true) {
/* 59 */         log.info("e-mail was successfully send");
/* 60 */         ret = true;
/*    */       } else {
/* 62 */         log.info("could not send e-mail");
/*    */       } 
/* 64 */       log.info("finish send email");
/* 65 */     } catch (Exception e) {
/* 66 */       log.error(e, e);
/*    */     } 
/* 68 */     return ret;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\snapshot\client\app\impl\EmailCallbackImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */