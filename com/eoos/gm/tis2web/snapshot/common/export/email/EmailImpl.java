/*    */ package com.eoos.gm.tis2web.snapshot.common.export.email;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EmailImpl
/*    */   implements Serializable, Email
/*    */ {
/*    */   private static final long serialVersionUID = 5784661709085177660L;
/* 14 */   protected String recipient = null;
/* 15 */   protected String from = null;
/* 16 */   protected String subject = null;
/* 17 */   protected String content = null;
/* 18 */   protected List attachments = new ArrayList();
/*    */   
/*    */   public String getContent() {
/* 21 */     return this.content;
/*    */   }
/*    */   
/*    */   public String getFrom() {
/* 25 */     return this.from;
/*    */   }
/*    */   
/*    */   public String getRecipient() {
/* 29 */     return this.recipient;
/*    */   }
/*    */   
/*    */   public String getSubject() {
/* 33 */     return this.subject;
/*    */   }
/*    */   
/*    */   public List getAttachments() {
/* 37 */     return this.attachments;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 41 */     return "Email[to:" + String.valueOf(this.recipient) + ", from:" + String.valueOf(this.from) + "]";
/*    */   }
/*    */   
/*    */   public void setContent(String content) {
/* 45 */     this.content = content;
/*    */   }
/*    */   
/*    */   public void setFrom(String from) {
/* 49 */     this.from = from;
/*    */   }
/*    */   
/*    */   public void setRecipient(String recipient) {
/* 53 */     this.recipient = recipient;
/*    */   }
/*    */   
/*    */   public void setSubject(String subject) {
/* 57 */     this.subject = subject;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\snapshot\common\export\email\EmailImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */