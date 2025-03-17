/*    */ package com.eoos.gm.tis2web.frame.export.common.ui.html;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.element.ImageElement;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class WarningMessageBox
/*    */   extends NotificationMessageBox
/*    */ {
/*    */   public WarningMessageBox(ClientContext context, String caption, String message) {
/* 15 */     super(context, (caption != null) ? caption : context.getLabel("warning"), message);
/*    */   }
/*    */   
/*    */   protected ImageElement createIcon() {
/* 19 */     return new ImageElement() {
/*    */         protected String getImageURL() {
/* 21 */           return "pic/common/warning.gif";
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\ui\html\WarningMessageBox.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */