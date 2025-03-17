/*     */ package com.eoos.mail;
/*     */ 
/*     */ import com.eoos.idfactory.IDFactory;
/*     */ import com.eoos.idfactory.NumericIDFactory;
/*     */ import com.eoos.io.StreamUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.DateConvert;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintWriter;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import javax.activation.DataSource;
/*     */ import javax.mail.MessagingException;
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
/*     */ public class MailServiceFilesystemDummy
/*     */   implements MailService
/*     */ {
/*  37 */   private static Logger log = Logger.getLogger(MailServiceFilesystemDummy.class);
/*     */   
/*     */   private File directory;
/*     */   
/*  41 */   private static final IDFactory IDFACTORY = (IDFactory)new NumericIDFactory();
/*     */   
/*  43 */   private static final IDFactory MAILID = (IDFactory)new NumericIDFactory();
/*     */ 
/*     */   
/*     */   public MailServiceFilesystemDummy(File directory) {
/*  47 */     if (directory == null)
/*  48 */       throw new IllegalArgumentException("directory must not be null"); 
/*  49 */     if (!directory.exists()) {
/*  50 */       if (!directory.mkdirs()) {
/*  51 */         throw new IllegalArgumentException("failed to create the directory ");
/*     */       }
/*  53 */     } else if (!directory.isDirectory() || !directory.canWrite()) {
/*  54 */       throw new IllegalArgumentException("directory: " + String.valueOf(directory) + " is not a writeable directory");
/*     */     } 
/*  56 */     this.directory = directory;
/*     */   }
/*     */   
/*     */   public void send(MailService.MailDataCallback mailDataCallback) throws MessagingException {
/*     */     try {
/*  61 */       File directory = new File(this.directory, DateConvert.toISOFormat(new Date()));
/*  62 */       directory.mkdir();
/*  63 */       File file = new File(directory, MAILID.createID() + ".txt");
/*  64 */       OutputStream fos = new BufferedOutputStream(new FileOutputStream(file));
/*  65 */       OutputStreamWriter writer = new OutputStreamWriter(fos, Charset.forName("UTF-8"));
/*  66 */       PrintWriter pw = new PrintWriter(writer);
/*     */       try {
/*  68 */         pw.println("SENDER:\t" + String.valueOf(mailDataCallback.getSender()));
/*     */         
/*  70 */         if (mailDataCallback.getReplyTo() != null) {
/*  71 */           for (Iterator iterator = mailDataCallback.getReplyTo().iterator(); iterator.hasNext();) {
/*  72 */             pw.println("REPLY-TO:\t" + String.valueOf(iterator.next()));
/*     */           }
/*     */         }
/*  75 */         for (Iterator iter = mailDataCallback.getRecipients().iterator(); iter.hasNext();) {
/*  76 */           pw.println("RECIPIENT:\t" + String.valueOf(iter.next()));
/*     */         }
/*  78 */         pw.println("SUBJECT:\t" + String.valueOf(mailDataCallback.getSubject()));
/*     */         
/*  80 */         String text = mailDataCallback.getText();
/*  81 */         if (text != null) {
/*  82 */           pw.println("\nTEXT:\n" + text);
/*     */         }
/*     */         
/*  85 */         DataSource[] dss = mailDataCallback.getAttachments();
/*  86 */         if (dss != null && dss.length > 0) {
/*  87 */           pw.println();
/*  88 */           for (int i = 0; i < dss.length; i++) {
/*     */             
/*  90 */             DataSource ds = dss[i];
/*     */             try {
/*  92 */               pw.println("ATTACHMENT" + (i + 1) + ":\t" + ds.getName());
/*     */               
/*  94 */               File attachmentFile = new File(directory, ds.getName());
/*  95 */               if (attachmentFile.exists()) {
/*  96 */                 attachmentFile.renameTo(new File(directory, ds.getName() + "." + IDFACTORY.createID()));
/*     */               } else {
/*  98 */                 File dir = attachmentFile.getParentFile();
/*  99 */                 if (dir != null && !dir.exists() && !dir.mkdirs()) {
/* 100 */                   throw new IOException("unable to create parent dir: " + String.valueOf(dir));
/*     */                 }
/*     */               } 
/* 103 */               OutputStream fos2 = new BufferedOutputStream(new FileOutputStream(attachmentFile));
/*     */               try {
/* 105 */                 StreamUtil.transfer(ds.getInputStream(), fos2);
/*     */               } finally {
/* 107 */                 fos2.close();
/*     */               } 
/* 109 */             } catch (Exception e) {
/* 110 */               log.error("unable to write attachment: " + String.valueOf(dss[i].getName()));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 115 */         pw.flush();
/*     */       } finally {
/* 117 */         pw.close();
/*     */       } 
/* 119 */       log.debug("successfully wrote mail to directory: " + String.valueOf(directory));
/* 120 */     } catch (Exception e) {
/* 121 */       throw new MessagingException("unable to write mail", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void send(final String sender, final Collection replyTo, final Collection recipients, final String subject, final String text, Collection eoosDataSource) throws MessagingException {
/*     */     final List tmp;
/* 129 */     if (!Util.isNullOrEmpty(eoosDataSource)) {
/* 130 */       tmp = new LinkedList();
/* 131 */       for (Iterator<DataSource> iter = eoosDataSource.iterator(); iter.hasNext();) {
/* 132 */         tmp.add(new DataSourceAdapter(iter.next()));
/*     */       }
/*     */     } else {
/* 135 */       tmp = null;
/*     */     } 
/*     */     
/* 138 */     send(new MailService.MailDataCallback()
/*     */         {
/*     */           public String getText() {
/* 141 */             return text;
/*     */           }
/*     */           
/*     */           public String getSubject() {
/* 145 */             return subject;
/*     */           }
/*     */           
/*     */           public String getSender() {
/* 149 */             return sender;
/*     */           }
/*     */           
/*     */           public Collection getReplyTo() {
/* 153 */             return replyTo;
/*     */           }
/*     */           
/*     */           public Collection getRecipients() {
/* 157 */             return recipients;
/*     */           }
/*     */           
/*     */           public DataSource[] getAttachments() {
/* 161 */             return (DataSource[])tmp.toArray((Object[])new DataSource[tmp.size()]);
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\mail\MailServiceFilesystemDummy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */