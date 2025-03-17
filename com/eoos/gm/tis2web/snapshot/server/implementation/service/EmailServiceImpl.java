/*     */ package com.eoos.gm.tis2web.snapshot.server.implementation.service;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.MailService;
/*     */ import com.eoos.gm.tis2web.snapshot.common.export.email.Email;
/*     */ import com.eoos.gm.tis2web.snapshot.common.export.system.EmailService;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedList;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.activation.DataSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EmailServiceImpl
/*     */   implements EmailService
/*     */ {
/*  26 */   private static EmailService instance = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  31 */   private String sender = ApplicationContext.getInstance().getProperty("component.snapshot.mail.sender");
/*     */ 
/*     */   
/*     */   public static synchronized EmailService getInstance() {
/*  35 */     if (instance == null) {
/*  36 */       instance = new EmailServiceImpl();
/*     */     }
/*  38 */     return instance;
/*     */   }
/*     */   
/*     */   public void sendEmail(Email eml) throws Exception {
/*  42 */     final Email email = eml;
/*  43 */     final DataSource[] attachments = new DataSource[email.getAttachments().size()];
/*     */     
/*  45 */     for (int i = 0; i < email.getAttachments().size(); i++) {
/*  46 */       final int indx = i;
/*  47 */       DataSource ds = new DataSource()
/*     */         {
/*     */           public String getContentType() {
/*  50 */             return "application/octet-stream";
/*     */           }
/*     */           
/*     */           public InputStream getInputStream() throws IOException {
/*  54 */             Pair name2attach = email.getAttachments().get(indx);
/*  55 */             return new ByteArrayInputStream((byte[])name2attach.getSecond());
/*     */           }
/*     */           
/*     */           public String getName() {
/*  59 */             Pair name2attach = email.getAttachments().get(indx);
/*  60 */             return (String)name2attach.getFirst();
/*     */           }
/*     */           
/*     */           public OutputStream getOutputStream() throws IOException {
/*  64 */             throw new IOException("read only");
/*     */           }
/*     */         };
/*     */       
/*  68 */       attachments[i] = ds;
/*     */     } 
/*     */     
/*  71 */     MailService.Callback callback = new MailService.Callback()
/*     */       {
/*     */         public String getSender() {
/*  74 */           return EmailServiceImpl.this.sender;
/*     */         }
/*     */         
/*     */         public Collection getReplyTo() {
/*  78 */           return Arrays.asList(new String[] { this.val$email.getFrom() });
/*     */         }
/*     */         
/*     */         public Collection getRecipients() {
/*  82 */           Collection<String> retValue = new LinkedList();
/*  83 */           StringTokenizer st = new StringTokenizer(email.getRecipient(), ";,");
/*  84 */           while (st.hasMoreTokens()) {
/*  85 */             retValue.add(st.nextToken());
/*     */           }
/*  87 */           return retValue;
/*     */         }
/*     */         
/*     */         public String getSubject() {
/*  91 */           return email.getSubject();
/*     */         }
/*     */         
/*     */         public String getText() {
/*  95 */           return email.getContent();
/*     */         }
/*     */         
/*     */         public DataSource[] getAttachments() {
/*  99 */           return attachments;
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 104 */     MailService ms = (MailService)FrameServiceProvider.getInstance().getService(MailService.class);
/* 105 */     ms.send(callback);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\snapshot\server\implementation\service\EmailServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */