/*    */ package com.eoos.gm.tis2web.frame.implementation.service;
/*    */ 
/*    */ import com.eoos.datatype.marker.Configurable;
/*    */ import com.eoos.gm.tis2web.frame.export.declaration.service.MailService;
/*    */ import com.eoos.mail.MailService;
/*    */ import com.eoos.mail.MailServiceFilesystemDummy;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import java.io.File;
/*    */ import java.util.Collection;
/*    */ import javax.mail.MessagingException;
/*    */ import javax.mail.internet.AddressException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MailServiceImpl
/*    */   implements MailService, Configurable
/*    */ {
/* 19 */   private final Object SYNC_DELEGATE = new Object();
/*    */   
/* 21 */   private MailService delegate = null;
/*    */   
/*    */   private Configuration configuration;
/*    */ 
/*    */   
/*    */   public MailServiceImpl(Configuration configuration) {
/* 27 */     this.configuration = configuration;
/*    */   }
/*    */   
/*    */   private MailService getDelegate() {
/* 31 */     synchronized (this.SYNC_DELEGATE) {
/* 32 */       if (this.delegate == null) {
/* 33 */         String directory = this.configuration.getProperty("dir");
/* 34 */         if (directory != null) {
/* 35 */           this.delegate = (MailService)new MailServiceFilesystemDummy(new File(directory));
/*    */         } else {
/* 37 */           this.delegate = (MailService)new com.eoos.mail.MailServiceImpl(this.configuration);
/*    */         } 
/*    */       } 
/* 40 */       return this.delegate;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void send(MailService.Callback callback) throws MessagingException, AddressException {
/* 45 */     getDelegate().send((MailService.MailDataCallback)callback);
/*    */   }
/*    */   
/*    */   public Object getIdentifier() {
/* 49 */     return null;
/*    */   }
/*    */   
/*    */   public void send(String sender, Collection replyTo, Collection recipients, String subject, String text, Collection eoosDataSources) throws MessagingException {
/* 53 */     getDelegate().send(sender, replyTo, recipients, subject, text, eoosDataSources);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\service\MailServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */