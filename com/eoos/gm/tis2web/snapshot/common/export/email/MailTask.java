/*    */ package com.eoos.gm.tis2web.snapshot.common.export.email;
/*    */ 
/*    */ import com.eoos.gm.tis2web.snapshot.common.export.system.EmailServiceProvider;
/*    */ import com.eoos.util.Task;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MailTask
/*    */   implements Task, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 15 */   protected Email email = null;
/*    */   
/*    */   public MailTask(Email email) {
/* 18 */     this.email = email;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object execute() {
/*    */     try {
/* 24 */       EmailServiceProvider.getInstance().getEmailService().sendEmail(this.email);
/* 25 */       return new Boolean(true);
/* 26 */     } catch (Throwable t) {
/* 27 */       return t;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\snapshot\common\export\email\MailTask.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */