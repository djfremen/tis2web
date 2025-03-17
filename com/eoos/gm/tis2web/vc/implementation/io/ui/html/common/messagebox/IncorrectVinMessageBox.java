/*    */ package com.eoos.gm.tis2web.vc.implementation.io.ui.html.common.messagebox;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ui.html.messagebox.MessageBoxBase;
/*    */ import java.util.ArrayList;
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
/*    */ 
/*    */ public abstract class IncorrectVinMessageBox
/*    */   extends MessageBoxBase
/*    */ {
/*    */   public IncorrectVinMessageBox(ClientContext context) {
/* 22 */     super(context);
/*    */   }
/*    */   
/*    */   protected String getIcon() {
/* 26 */     return "common/warning.gif";
/*    */   }
/*    */   
/*    */   protected String getCaption() {
/* 30 */     return this.context.getLabel("module.type.vc");
/*    */   }
/*    */   
/*    */   protected String getContent() {
/* 34 */     return this.context.getMessage("vc.message.vin.incorrect");
/*    */   }
/*    */   
/*    */   protected List getButtons() {
/* 38 */     List<String> buttons = new ArrayList();
/* 39 */     buttons.add(this.context.getLabel("button.ok"));
/* 40 */     return buttons;
/*    */   }
/*    */   
/*    */   protected Object onClick(String buttonLabel) {
/* 44 */     Object retValue = null;
/* 45 */     if (buttonLabel.equalsIgnoreCase(this.context.getLabel("button.ok"))) {
/* 46 */       retValue = onOK();
/*    */     }
/* 48 */     return retValue;
/*    */   }
/*    */   
/*    */   protected abstract Object onOK();
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\implementation\i\\ui\html\common\messagebox\IncorrectVinMessageBox.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */