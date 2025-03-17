/*    */ package com.eoos.gm.tis2web.frame.msg.util;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ui.html.NotificationMsgBox2;
/*    */ import com.eoos.gm.tis2web.frame.msg.MsgFacade;
/*    */ import com.eoos.gm.tis2web.frame.msg.admin.IMessage;
/*    */ 
/*    */ 
/*    */ public class MsgUIDialog
/*    */   extends NotificationMsgBox2
/*    */ {
/*    */   private Object returnValue;
/*    */   private IMessage msg;
/*    */   
/*    */   public MsgUIDialog(ClientContext context, String caption, String message, boolean warning, Object returnValue, IMessage msg) {
/* 16 */     super(context, caption, message, warning);
/* 17 */     this.returnValue = returnValue;
/* 18 */     this.msg = msg;
/*    */   }
/*    */   
/*    */   public static MsgUIDialog create(ClientContext context, IMessage msg, Object returnValue) {
/* 22 */     IMessage.IContent content = msg.getContent(context.getLocale());
/* 23 */     String caption = content.getTitle();
/* 24 */     String message = MessageUtil.convertLineBreaksToHTML(content.getText());
/* 25 */     boolean warning = msg.getType().equals(IMessage.Type.WARNING);
/* 26 */     return new MsgUIDialog(context, caption, message, warning, returnValue, msg);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Object onOK(boolean dontShowAgain) {
/* 31 */     if (dontShowAgain) {
/* 32 */       MsgFacade.getInstance(getContext()).excludeMessage(this.msg);
/*    */     }
/* 34 */     return this.returnValue;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ms\\util\MsgUIDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */