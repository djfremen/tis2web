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
/*    */ public abstract class AttributeTiedToVinMessageBox
/*    */   extends MessageBoxBase
/*    */ {
/*    */   public AttributeTiedToVinMessageBox(ClientContext context) {
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
/* 34 */     return this.context.getMessage("vc.message.attribute.tied.to.vin");
/*    */   }
/*    */   
/*    */   protected List getButtons() {
/* 38 */     List<String> buttons = new ArrayList();
/* 39 */     buttons.add(this.context.getLabel("button.yes"));
/* 40 */     buttons.add(this.context.getLabel("button.no"));
/* 41 */     return buttons;
/*    */   }
/*    */   
/*    */   protected Object onClick(String buttonLabel) {
/* 45 */     Object retValue = null;
/* 46 */     if (buttonLabel.equalsIgnoreCase(this.context.getLabel("button.yes"))) {
/* 47 */       retValue = onYes();
/*    */     }
/* 49 */     if (buttonLabel.equalsIgnoreCase(this.context.getLabel("button.no"))) {
/* 50 */       retValue = onNo();
/*    */     }
/* 52 */     return retValue;
/*    */   }
/*    */   
/*    */   protected abstract Object onYes();
/*    */   
/*    */   protected abstract Object onNo();
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\implementation\i\\ui\html\common\messagebox\AttributeTiedToVinMessageBox.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */