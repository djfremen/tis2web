/*    */ package com.eoos.gm.tis2web.frame.export.common.ui.html;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.ImageElement;
/*    */ import com.eoos.html.element.input.CheckBoxElement;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class NotificationMsgBox2
/*    */   extends NotificationMessageBox
/*    */ {
/*    */   private boolean warning;
/*    */   private CheckBoxElement ieCheckBox;
/*    */   
/*    */   public NotificationMsgBox2(ClientContext context, String caption, String message, boolean warning) {
/* 22 */     super(context, caption, message);
/* 23 */     this.warning = warning;
/*    */     
/* 25 */     this.ieCheckBox = new CheckBoxElement(context.createID());
/* 26 */     addElement((HtmlElement)this.ieCheckBox);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ImageElement createIcon() {
/* 31 */     return new ImageElement() {
/*    */         protected String getImageURL() {
/* 33 */           return NotificationMsgBox2.this.warning ? "pic/common/warning.gif" : "pic/common/information.gif";
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   protected String getContent(Map params) {
/* 39 */     return "<table width=\"100%\"><tr><td align=\"center\">" + getMessage(params) + "</td></tr><tr><td align=\"center\">" + getButtonsCode(params) + "</td></tr><tr><td><hr width=\"100%\" /></td</tr><tr><td align=\"left\">" + getDontShowAgain(params) + "</td></tr></table>";
/*    */   }
/*    */   
/*    */   protected String getDontShowAgain(Map params) {
/* 43 */     return "<table><tr><td>" + this.ieCheckBox.getHtmlCode(params) + "</td><td>" + this.context.getMessage("do.not.show.again") + "</td></tr></table>";
/*    */   }
/*    */ 
/*    */   
/*    */   protected Object onOK(Map params) {
/* 48 */     return onOK(((Boolean)this.ieCheckBox.getValue()).booleanValue());
/*    */   }
/*    */   
/*    */   protected abstract Object onOK(boolean paramBoolean);
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\ui\html\NotificationMsgBox2.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */