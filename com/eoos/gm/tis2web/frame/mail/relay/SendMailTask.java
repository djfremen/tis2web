/*    */ package com.eoos.gm.tis2web.frame.mail.relay;
/*    */ 
/*    */ import com.eoos.mail.DataSource;
/*    */ import com.eoos.mail.SerializableDataSource;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import com.eoos.util.Task;
/*    */ import java.io.ObjectStreamException;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SendMailTask
/*    */   implements Task
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   String sender;
/*    */   Collection replyTo;
/*    */   Collection recipients;
/*    */   Collection ccRecipients;
/*    */   Collection bccRecipients;
/*    */   String subject;
/*    */   String text;
/* 31 */   Collection eoosDataSources = null;
/*    */   
/*    */   public SendMailTask(String sender, Collection replyTo, Collection recipients, String subject, String text, Collection eoosDataSources) {
/* 34 */     this.sender = sender;
/* 35 */     this.replyTo = replyTo;
/* 36 */     this.recipients = recipients;
/* 37 */     this.subject = subject;
/* 38 */     this.text = text;
/*    */     
/* 40 */     if (!Util.isNullOrEmpty(eoosDataSources)) {
/* 41 */       this.eoosDataSources = new LinkedList();
/* 42 */       for (Iterator<DataSource> iter = eoosDataSources.iterator(); iter.hasNext();) {
/* 43 */         this.eoosDataSources.add(new SerializableDataSource(iter.next()));
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public Object execute() {
/* 49 */     return null;
/*    */   }
/*    */   
/*    */   private Object readResolve() throws ObjectStreamException {
/* 53 */     return new SendMailTask_ServerPart(this);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\mail\relay\SendMailTask.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */