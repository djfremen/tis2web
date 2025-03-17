/*     */ package com.eoos.gm.tis2web.snapshot.client.app.impl;
/*     */ 
/*     */ import com.eoos.file.FileUtil;
/*     */ import com.eoos.gm.tis2web.snapshot.client.common.ClientAppContextProvider;
/*     */ import com.eoos.gm.tis2web.snapshot.client.system.ServerTaskExecution;
/*     */ import com.eoos.gm.tis2web.snapshot.common.export.email.Email;
/*     */ import com.eoos.gm.tis2web.snapshot.common.export.email.MailTask;
/*     */ import com.eoos.util.Task;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EmailAutoSend
/*     */ {
/*  22 */   private static final Logger log = Logger.getLogger(EmailAutoSend.class);
/*     */   
/*  24 */   private static EmailAutoSend instance = null;
/*     */ 
/*     */   
/*     */   private File directory;
/*     */ 
/*     */   
/*  30 */   private final Object SYNC_STORE = new Object();
/*     */   
/*     */   public static synchronized EmailAutoSend getInstance() {
/*  33 */     if (instance == null) {
/*  34 */       String _directory = ClientAppContextProvider.getClientAppContext().getHomeDir();
/*  35 */       File directory = new File(_directory, "email");
/*  36 */       instance = new EmailAutoSend(directory);
/*     */     } 
/*  38 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EmailAutoSend(File directory) {
/*  44 */     this.directory = directory;
/*  45 */     FileUtil.ensureExistence(this.directory);
/*     */   }
/*     */   
/*     */   protected boolean sendEmail(Email email) {
/*     */     try {
/*  50 */       MailTask task = new MailTask(email);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  56 */       if (((Boolean)sendTask((Task)task)).booleanValue()) {
/*  57 */         log.debug("successfully sent email: " + String.valueOf(email));
/*     */       } else {
/*  59 */         throw new Exception();
/*     */       } 
/*  61 */       return true;
/*  62 */     } catch (Exception e) {
/*  63 */       log.warn("could not send email - exception:" + e, e);
/*  64 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkDeferred() {
/*  70 */     synchronized (this.SYNC_STORE) {
/*  71 */       File[] files = this.directory.listFiles();
/*  72 */       if (files != null && files.length > 0) {
/*  73 */         log.debug("found emails in deferred transmission directory");
/*  74 */         for (int i = 0; i < files.length; i++) {
/*  75 */           Email email = null;
/*  76 */           File file = files[i];
/*  77 */           if (file.canRead() && file.isFile()) {
/*     */             try {
/*  79 */               FileInputStream fis = new FileInputStream(file);
/*  80 */               ObjectInputStream ois = new ObjectInputStream(fis);
/*  81 */               email = (Email)ois.readObject();
/*  82 */               ois.close();
/*  83 */               log.debug("...read email: " + String.valueOf(email));
/*  84 */             } catch (Exception e) {
/*  85 */               log.error("unable to read email - exception:" + e, e);
/*     */             } 
/*     */           }
/*  88 */           if (email != null && sendEmail(email)) {
/*  89 */             file.delete();
/*  90 */             log.info("email deleted: " + String.valueOf(email));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void storeEmail(Email email) {
/*  98 */     File file = new File(this.directory, System.currentTimeMillis() + ".eml");
/*     */     try {
/* 100 */       FileOutputStream fos = new FileOutputStream(file);
/* 101 */       ObjectOutputStream oos = new ObjectOutputStream(fos);
/* 102 */       oos.writeObject(email);
/* 103 */       oos.close();
/* 104 */     } catch (Exception e) {
/* 105 */       log.error("unable to save email - exception:" + e, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected Object sendTask(Task task) throws Exception {
/* 110 */     return ServerTaskExecution.getInstance().execute(task);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\snapshot\client\app\impl\EmailAutoSend.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */