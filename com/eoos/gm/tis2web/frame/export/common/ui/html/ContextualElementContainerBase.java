/*    */ package com.eoos.gm.tis2web.frame.export.common.ui.html;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainer;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import java.util.Map;
/*    */ 
/*    */ public abstract class ContextualElementContainerBase
/*    */   extends HtmlElementContainerBase
/*    */ {
/*    */   protected ClientContext context;
/*    */   
/*    */   public ContextualElementContainerBase(ClientContext context) {
/* 15 */     this.context = context;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Object getErrorPopup(String message) {
/* 20 */     return getErrorPopup(message, (Object)null);
/*    */   }
/*    */   
/*    */   protected Object getErrorPopup(Throwable t) {
/* 24 */     return getErrorPopup(t, (Object)null);
/*    */   }
/*    */   
/*    */   protected Object getErrorPopup(Throwable t, Object onOKReturnValue) {
/* 28 */     return getErrorPopup(t.getMessage(), onOKReturnValue);
/*    */   }
/*    */   
/*    */   protected Object getErrorPopup(String message, final Object onOKReturnValue) {
/* 32 */     final HtmlElementContainer topLevel = getTopLevelContainer();
/* 33 */     return new ErrorMessageBox(this.context, null, message)
/*    */       {
/*    */         protected Object onOK(Map params) {
/* 36 */           return (onOKReturnValue == null) ? topLevel : onOKReturnValue;
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   protected Object getWarningPopup(String message) {
/* 43 */     return getWarningPopup(message, null);
/*    */   } public static interface ConfirmationCallback {
/*    */     Object onConfirm(); Object onCancel(); }
/*    */   protected Object getWarningPopup(String message, final Object onOKReturnValue) {
/* 47 */     final HtmlElementContainer topLevel = getTopLevelContainer();
/* 48 */     return new WarningMessageBox(this.context, null, message)
/*    */       {
/*    */         protected Object onOK(Map params) {
/* 51 */           return (onOKReturnValue == null) ? topLevel : onOKReturnValue;
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   protected Object getInfoPopup(String message) {
/* 58 */     return getInfoPopup(message, null);
/*    */   }
/*    */   
/*    */   protected Object getInfoPopup(String message, Object onOKReturnValue) {
/* 62 */     HtmlElementContainer htmlElementContainer = getTopLevelContainer();
/* 63 */     return NotificationMessageBox.createInfoMessage(this.context, this.context.getLabel("information"), message, (onOKReturnValue == null) ? htmlElementContainer : onOKReturnValue);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Object getConfirmationPopup(String message, final ConfirmationCallback callback) {
/* 73 */     getTopLevelContainer();
/* 74 */     return new SimpleConfirmationMessageBox(this.context, null, message)
/*    */       {
/*    */         protected Object onOK(Map params) {
/* 77 */           return callback.onConfirm();
/*    */         }
/*    */         
/*    */         protected Object onCancel(Map params) {
/* 81 */           return callback.onCancel();
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\ui\html\ContextualElementContainerBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */