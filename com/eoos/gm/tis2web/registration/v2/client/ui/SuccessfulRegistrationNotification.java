/*    */ package com.eoos.gm.tis2web.registration.v2.client.ui;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ui.html.NotificationMessageBox;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ui.html.main.toplink.LogoutLinkElement;
/*    */ import com.eoos.html.base.ClientContextBase;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.input.ClickButtonElement;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class SuccessfulRegistrationNotification extends NotificationMessageBox {
/*    */   private LogoutLinkElement link;
/*    */   
/*    */   public SuccessfulRegistrationNotification(ClientContext context) {
/* 15 */     super(context, context.getLabel("information"), context.getMessage("auth.req.dialog.license.registration.suceeded"));
/*    */     
/* 17 */     this.link = new LogoutLinkElement(context);
/* 18 */     addElement((HtmlElement)this.link);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Object onOK(Map params) {
/* 23 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   protected ClickButtonElement createOKButton() {
/* 27 */     return new ClickButtonElement(this.context.createID(), null)
/*    */       {
/*    */         protected String getLabel() {
/* 30 */           return SuccessfulRegistrationNotification.this.context.getLabel("logout");
/*    */         }
/*    */         
/*    */         public Object onClick(Map submitParams) {
/* 34 */           return SuccessfulRegistrationNotification.this.link.logout();
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\v2\clien\\ui\SuccessfulRegistrationNotification.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */