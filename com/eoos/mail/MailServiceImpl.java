/*     */ package com.eoos.mail;
/*     */ 
/*     */ import com.eoos.collection.v2.CollectionUtil;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.activation.DataSource;
/*     */ import javax.mail.Address;
/*     */ import javax.mail.Authenticator;
/*     */ import javax.mail.BodyPart;
/*     */ import javax.mail.Message;
/*     */ import javax.mail.MessagingException;
/*     */ import javax.mail.Multipart;
/*     */ import javax.mail.PasswordAuthentication;
/*     */ import javax.mail.Session;
/*     */ import javax.mail.Transport;
/*     */ import javax.mail.internet.InternetAddress;
/*     */ import javax.mail.internet.MimeBodyPart;
/*     */ import javax.mail.internet.MimeMessage;
/*     */ import javax.mail.internet.MimeMultipart;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MailServiceImpl
/*     */   implements MailService
/*     */ {
/*  41 */   private static Logger log = Logger.getLogger(MailServiceImpl.class);
/*     */   
/*     */   protected class MailAuthenticator
/*     */     extends Authenticator {
/*  45 */     private String name = null;
/*     */     
/*  47 */     private String pswd = null;
/*     */     
/*     */     public MailAuthenticator(String name, String pswd) {
/*  50 */       this.name = name;
/*  51 */       this.pswd = pswd;
/*     */     }
/*     */     
/*     */     public PasswordAuthentication getPasswordAuthentication() {
/*  55 */       return new PasswordAuthentication(this.name, this.pswd);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  60 */   private String smtpHost = null;
/*     */   
/*  62 */   private String smtpPort = null;
/*     */   
/*  64 */   private String smtpAuth = null;
/*     */   
/*  66 */   private String smtpUser = null;
/*     */   
/*  68 */   private String smtpPswd = null;
/*     */   
/*  70 */   private Authenticator mailAuth = null;
/*     */ 
/*     */   
/*     */   public MailServiceImpl(Configuration configuration) {
/*  74 */     this.smtpHost = configuration.getProperty("host");
/*  75 */     this.smtpPort = configuration.getProperty("port");
/*  76 */     this.smtpAuth = configuration.getProperty("auth");
/*  77 */     this.smtpUser = configuration.getProperty("user");
/*  78 */     this.smtpPswd = configuration.getProperty("pswd");
/*     */     
/*  80 */     this.mailAuth = new MailAuthenticator(this.smtpUser, this.smtpPswd);
/*     */   }
/*     */ 
/*     */   
/*     */   public void send(MailService.MailDataCallback mailDataCallback) throws MessagingException {
/*  85 */     Session session = null;
/*  86 */     MimeMessage message = null;
/*     */ 
/*     */ 
/*     */     
/*  90 */     Properties prop = new Properties();
/*     */     
/*  92 */     prop.put("mail.smtp.host", this.smtpHost);
/*  93 */     prop.put("mail.smtp.user", this.smtpUser);
/*  94 */     prop.put("mail.smtp.port", this.smtpPort);
/*  95 */     prop.put("mail.smtp.auth", this.smtpAuth);
/*     */ 
/*     */ 
/*     */     
/*  99 */     session = Session.getInstance(prop, this.mailAuth);
/*     */ 
/*     */ 
/*     */     
/* 103 */     message = new MimeMessage(session);
/*     */ 
/*     */ 
/*     */     
/* 107 */     message.setFrom((Address)new InternetAddress(mailDataCallback.getSender()));
/*     */     
/* 109 */     List<String> replyTo = CollectionUtil.toList(mailDataCallback.getReplyTo());
/* 110 */     if (replyTo != null) {
/* 111 */       Address[] aa = new Address[replyTo.size()];
/* 112 */       for (int j = 0; j < replyTo.size(); j++) {
/* 113 */         aa[j] = (Address)new InternetAddress(replyTo.get(j));
/*     */       }
/* 115 */       message.setReplyTo(aa);
/*     */     } 
/*     */     
/* 118 */     List<InternetAddress> recipientAddresses = new LinkedList();
/* 119 */     for (Iterator<String> iter = mailDataCallback.getRecipients().iterator(); iter.hasNext(); ) {
/* 120 */       String recipient = iter.next();
/*     */       try {
/* 122 */         recipientAddresses.add(new InternetAddress(recipient));
/*     */       }
/* 124 */       catch (Exception e) {
/* 125 */         log.warn("unable to create recipient address for:" + recipient + " -error:" + e + ", skipping");
/*     */       } 
/*     */     } 
/* 128 */     InternetAddress[] recipients = new InternetAddress[recipientAddresses.size()];
/* 129 */     for (int i = 0; i < recipients.length; i++) {
/* 130 */       recipients[i] = recipientAddresses.get(i);
/*     */     }
/*     */     
/* 133 */     message.setRecipients(Message.RecipientType.TO, (Address[])recipients);
/* 134 */     message.setSubject(mailDataCallback.getSubject(), "UTF-8");
/* 135 */     message.setSentDate(new Date());
/*     */     
/* 137 */     String text = mailDataCallback.getText();
/* 138 */     DataSource[] attachments = mailDataCallback.getAttachments();
/* 139 */     if (attachments == null) {
/* 140 */       message.setText(text, "UTF-8");
/*     */     } else {
/* 142 */       MimeMultipart mimeMultipart = new MimeMultipart();
/*     */       
/* 144 */       MimeBodyPart messageBodyPart = new MimeBodyPart();
/* 145 */       messageBodyPart.setText(text, "UTF-8");
/* 146 */       mimeMultipart.addBodyPart((BodyPart)messageBodyPart);
/*     */       
/* 148 */       for (int j = 0; j < attachments.length; j++) {
/* 149 */         DataSource ds = attachments[j];
/*     */         
/* 151 */         messageBodyPart = new MimeBodyPart();
/* 152 */         messageBodyPart.setDataHandler(new DataHandler(ds));
/* 153 */         messageBodyPart.setFileName(ds.getName());
/* 154 */         mimeMultipart.addBodyPart((BodyPart)messageBodyPart);
/*     */       } 
/* 156 */       message.setContent((Multipart)mimeMultipart);
/*     */     } 
/*     */ 
/*     */     
/* 160 */     message.saveChanges();
/*     */ 
/*     */ 
/*     */     
/* 164 */     Transport.send((Message)message);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void send(final String sender, final Collection replyTo, final Collection recipients, final String subject, final String text, Collection eoosDataSource) throws MessagingException {
/*     */     final List tmp;
/* 171 */     if (!Util.isNullOrEmpty(eoosDataSource)) {
/* 172 */       tmp = new LinkedList();
/* 173 */       for (Iterator<DataSource> iter = eoosDataSource.iterator(); iter.hasNext();) {
/* 174 */         tmp.add(new DataSourceAdapter(iter.next()));
/*     */       }
/*     */     } else {
/* 177 */       tmp = null;
/*     */     } 
/*     */     
/* 180 */     send(new MailService.MailDataCallback()
/*     */         {
/*     */           public String getText() {
/* 183 */             return text;
/*     */           }
/*     */           
/*     */           public String getSubject() {
/* 187 */             return subject;
/*     */           }
/*     */           
/*     */           public String getSender() {
/* 191 */             return sender;
/*     */           }
/*     */           
/*     */           public Collection getReplyTo() {
/* 195 */             return replyTo;
/*     */           }
/*     */           
/*     */           public Collection getRecipients() {
/* 199 */             return recipients;
/*     */           }
/*     */           
/*     */           public DataSource[] getAttachments() {
/* 203 */             return (tmp != null) ? (DataSource[])tmp.toArray((Object[])new DataSource[tmp.size()]) : null;
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\mail\MailServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */