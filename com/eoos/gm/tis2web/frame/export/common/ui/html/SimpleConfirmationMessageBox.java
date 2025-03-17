/*    */ package com.eoos.gm.tis2web.frame.export.common.ui.html;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.base.ClientContextBase;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.ImageElement;
/*    */ import com.eoos.html.element.input.ClickButtonElement;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class SimpleConfirmationMessageBox
/*    */   extends MessageBoxBase
/*    */ {
/*    */   protected ImageElement icon;
/*    */   protected ClickButtonElement buttonOK;
/*    */   protected ClickButtonElement buttonCancel;
/*    */   
/*    */   public SimpleConfirmationMessageBox(ClientContext context, String caption, String message) {
/* 27 */     super(context, (caption != null) ? caption : context.getLabel("confirmation"), message);
/*    */     
/* 29 */     this.buttonOK = createOKButton();
/* 30 */     addElement((HtmlElement)this.buttonOK);
/*    */     
/* 32 */     this.buttonCancel = createCancelButton();
/* 33 */     addElement((HtmlElement)this.buttonCancel);
/*    */     
/* 35 */     this.icon = createIcon();
/*    */   }
/*    */   
/*    */   protected ClickButtonElement createOKButton() {
/* 39 */     return new ClickButtonElement(this.context.createID(), null) {
/*    */         protected String getLabel() {
/* 41 */           return SimpleConfirmationMessageBox.this.context.getLabel("ok");
/*    */         }
/*    */         
/*    */         public Object onClick(Map params) {
/* 45 */           return SimpleConfirmationMessageBox.this.onOK(params);
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   protected ClickButtonElement createCancelButton() {
/* 51 */     return new ClickButtonElement(this.context.createID(), null) {
/*    */         protected String getLabel() {
/* 53 */           return SimpleConfirmationMessageBox.this.context.getLabel("cancel");
/*    */         }
/*    */         
/*    */         public Object onClick(Map params) {
/* 57 */           return SimpleConfirmationMessageBox.this.onCancel(params);
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   protected ImageElement createIcon() {
/* 63 */     return new ImageElement() {
/*    */         protected String getImageURL() {
/* 65 */           return "pic/common/information.gif";
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getIconCode(Map params) {
/* 76 */     return this.icon.getHtmlCode(params);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getButtonsCode(Map params) {
/* 85 */     return "<table><tr><td>" + this.buttonOK.getHtmlCode(params) + "</td><td>" + this.buttonCancel.getHtmlCode(params) + "</td></tr></table>";
/*    */   }
/*    */   
/*    */   protected abstract Object onCancel(Map paramMap);
/*    */   
/*    */   protected abstract Object onOK(Map paramMap);
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\ui\html\SimpleConfirmationMessageBox.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */