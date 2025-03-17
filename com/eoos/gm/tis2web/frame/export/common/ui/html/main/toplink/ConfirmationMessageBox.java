/*    */ package com.eoos.gm.tis2web.frame.export.common.ui.html.main.toplink;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ui.html.messagebox.MessageBoxBase;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
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
/*    */ public abstract class ConfirmationMessageBox
/*    */   extends MessageBoxBase
/*    */ {
/*    */   public String BUTTONLABEL_OK;
/*    */   public String BUTTONLABEL_CANCEL;
/*    */   
/*    */   public ConfirmationMessageBox(ClientContext context) {
/* 24 */     super(context);
/*    */   }
/*    */   
/*    */   protected List getButtons() {
/* 28 */     this.BUTTONLABEL_OK = this.context.getLabel("logout");
/* 29 */     this.BUTTONLABEL_CANCEL = this.context.getLabel("cancel");
/*    */     
/* 31 */     return Arrays.asList(new String[] { this.BUTTONLABEL_OK, this.BUTTONLABEL_CANCEL });
/*    */   }
/*    */   
/*    */   protected String getCaption() {
/* 35 */     return this.context.getLabel("confirmation");
/*    */   }
/*    */   
/*    */   protected String getContent() {
/* 39 */     return this.context.getMessage("confirmation.logout");
/*    */   }
/*    */   
/*    */   protected String getIcon() {
/* 43 */     return "common/warning.gif";
/*    */   }
/*    */   
/*    */   protected abstract Object onClick(String paramString);
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\ui\html\main\toplink\ConfirmationMessageBox.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */