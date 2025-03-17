/*    */ package com.eoos.gm.tis2web.frame.export.common.ui.html;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.element.ImageElement;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ErrorMessageBox
/*    */   extends NotificationMessageBox
/*    */ {
/*    */   public ErrorMessageBox(ClientContext context, String caption, String message) {
/* 17 */     super(context, (caption != null) ? caption : context.getLabel("error"), message);
/*    */   }
/*    */   
/*    */   protected ImageElement createIcon() {
/* 21 */     return new ImageElement() {
/*    */         protected String getImageURL() {
/* 23 */           return "pic/common/error.gif";
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   public static ErrorMessageBox create(ClientContext context, String caption, String message, final Object onCloseResult) {
/* 29 */     return new ErrorMessageBox(context, caption, message)
/*    */       {
/*    */         protected Object onOK(Map params) {
/* 32 */           return onCloseResult;
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   public static ErrorMessageBox create(ClientContext context, String message, final Object onCloseResult) {
/* 39 */     return new ErrorMessageBox(context, null, message)
/*    */       {
/*    */         protected Object onOK(Map params) {
/* 42 */           return onCloseResult;
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   public static ErrorMessageBox create(ClientContext context, Object onCloseResult) {
/* 49 */     return create(context, context.getMessage("error.see.log"), onCloseResult);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\ui\html\ErrorMessageBox.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */