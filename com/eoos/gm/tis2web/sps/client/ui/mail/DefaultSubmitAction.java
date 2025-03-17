/*     */ package com.eoos.gm.tis2web.sps.client.ui.mail;
/*     */ 
/*     */ import com.eoos.condition.Condition;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*     */ import com.eoos.gm.tis2web.sps.client.serveracces.SPSClientFacadeProvider;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.MailAttachment;
/*     */ import com.eoos.io.StreamUtil;
/*     */ import com.eoos.tokenizer.v2.CSTokenizer;
/*     */ import com.eoos.util.v2.AssertUtil;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FilenameFilter;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultSubmitAction
/*     */   implements LogFilesMailDialog.Action
/*     */ {
/*  25 */   private static final Logger log = Logger.getLogger(DefaultSubmitAction.class);
/*     */   
/*     */   private static class InvalidInputException
/*     */     extends Exception {
/*     */     private static final long serialVersionUID = 1L;
/*  30 */     private static final InvalidInputException INVALID_SENDER = new InvalidInputException();
/*     */     
/*  32 */     private static final InvalidInputException INVALID_RECIPIENTS = new InvalidInputException();
/*     */   }
/*     */   
/*  35 */   private static final Exception NO_FILES_FOUND = new Exception();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(LogFilesMailDialog.DataAccess dataAccess) throws Exception {
/*  41 */     String sender = dataAccess.getSender();
/*  42 */     if (sender == null || sender.trim().length() == 0) {
/*  43 */       throw InvalidInputException.INVALID_SENDER;
/*     */     }
/*     */     
/*  46 */     List<String> recipients = new LinkedList();
/*     */     try {
/*  48 */       CSTokenizer tokenizer = new CSTokenizer(dataAccess.getRecipients(), new String[] { " ", ",", ";" });
/*  49 */       while (tokenizer.hasNext()) {
/*  50 */         String recipient = (String)tokenizer.next();
/*  51 */         recipient = recipient.trim();
/*  52 */         if (recipient.length() > 0) {
/*  53 */           recipients.add(recipient);
/*     */         }
/*     */       } 
/*  56 */       AssertUtil.ensure(recipients, new Condition()
/*     */           {
/*     */             public boolean check(Object obj) {
/*  59 */               return (((List)obj).size() > 0);
/*     */             }
/*     */           });
/*     */     }
/*  63 */     catch (Exception e) {
/*  64 */       throw InvalidInputException.INVALID_RECIPIENTS;
/*     */     } 
/*     */     
/*     */     try {
/*  68 */       Set<MailAttachment> attachments = new HashSet();
/*  69 */       File logDir = (new File(ClientAppContextProvider.getClientAppContext().getClientSettings().getLogPath())).getParentFile();
/*  70 */       File[] logFiles = logDir.listFiles(new FilenameFilter()
/*     */           {
/*     */             public boolean accept(File dir, String name) {
/*  73 */               return name.toLowerCase(Locale.ENGLISH).endsWith(".log");
/*     */             }
/*     */           });
/*     */       
/*  77 */       if (logFiles == null || logFiles.length == 0) {
/*  78 */         throw NO_FILES_FOUND;
/*     */       }
/*  80 */       for (int i = 0; i < logFiles.length; i++) {
/*  81 */         File logFile = logFiles[i];
/*     */         try {
/*  83 */           FileInputStream fis = new FileInputStream(logFile);
/*  84 */           byte[] data = StreamUtil.readFully(fis);
/*     */           
/*  86 */           MailAttachment attachment = new MailAttachment(logFile.getName(), "text/plain", data);
/*  87 */           attachments.add(attachment);
/*  88 */         } catch (Exception e) {
/*  89 */           log.error("unable to create mail attachment for file: " + logFile.getAbsolutePath() + ", skipping - exception:" + e, e);
/*     */         } 
/*     */       } 
/*     */       
/*  93 */       SPSClientFacadeProvider.getInstance().getFacade().sendMail(sender, recipients, dataAccess.getSubject(), dataAccess.getText(), attachments);
/*  94 */     } catch (Exception e) {
/*  95 */       log.error("unable to send email, indicating exception to caller - exception :" + e, e);
/*  96 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean handleException(Exception e, LogFilesMailDialog.ErrorNotification errorNotification) {
/* 102 */     boolean dispose = false;
/* 103 */     if (e == InvalidInputException.INVALID_SENDER) {
/* 104 */       errorNotification.showNotification("maildialog.error.invalid.sender");
/* 105 */     } else if (e == InvalidInputException.INVALID_RECIPIENTS) {
/* 106 */       errorNotification.showNotification("maildialog.error.invalid.recipients");
/* 107 */     } else if (e == NO_FILES_FOUND) {
/* 108 */       errorNotification.showNotification("maildialog.error.no.log.files.found");
/*     */     } else {
/* 110 */       errorNotification.showNotification("maildialog.error.unable.to.send");
/* 111 */       dispose = true;
/*     */     } 
/* 113 */     return dispose;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\mail\DefaultSubmitAction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */