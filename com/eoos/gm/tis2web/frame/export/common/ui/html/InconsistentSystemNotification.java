/*    */ package com.eoos.gm.tis2web.frame.export.common.ui.html;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ui.html.main.toplink.LogoutLinkElement;
/*    */ import com.eoos.html.base.ClientContextBase;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.input.ClickButtonElement;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class InconsistentSystemNotification extends ErrorMessageBox {
/*    */   private LogoutLinkElement link;
/*    */   
/*    */   public InconsistentSystemNotification(ClientContext context, String caption, String message) {
/* 14 */     super(context, caption, message);
/*    */     
/* 16 */     this.link = new LogoutLinkElement(context);
/* 17 */     addElement((HtmlElement)this.link);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Object onOK(Map params) {
/* 22 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   protected ClickButtonElement createOKButton() {
/* 26 */     return new ClickButtonElement(this.context.createID(), null)
/*    */       {
/*    */         protected String getLabel() {
/* 29 */           return InconsistentSystemNotification.this.context.getLabel("logout");
/*    */         }
/*    */         
/*    */         public Object onClick(Map submitParams) {
/* 33 */           return InconsistentSystemNotification.this.link.logout();
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\ui\html\InconsistentSystemNotification.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */