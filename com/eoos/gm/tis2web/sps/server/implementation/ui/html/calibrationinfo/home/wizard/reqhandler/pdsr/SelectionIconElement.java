/*    */ package com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.reqhandler.pdsr;
/*    */ 
/*    */ import com.eoos.datatype.selection.SelectionControl;
/*    */ import com.eoos.datatype.selection.implementation.SelectionControlImpl;
/*    */ import com.eoos.datatype.selection.implementation.SelectionControlSPI;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.element.input.tree.TreeControl;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SelectionIconElement
/*    */   extends TreeLinkElement
/*    */ {
/*    */   private SelectionControl selectionControl;
/*    */   private Callback callback;
/*    */   
/*    */   protected SelectionIconElement(ClientContext context, TreeControl control, Object node, SelectionControlSPI selectionControl, Callback callback) {
/* 24 */     super(context, control, node);
/* 25 */     this.selectionControl = (SelectionControl)new SelectionControlImpl(selectionControl);
/* 26 */     this.callback = callback;
/*    */   }
/*    */   
/*    */   protected String getImagePath() {
/* 30 */     if (!this.callback.isSelectable(this.node))
/*    */     {
/* 32 */       return "sps/node-not-selectable.gif";
/*    */     }
/* 34 */     if (this.selectionControl.isSelected(this.node)) {
/* 35 */       return "sps/node-selected.gif";
/*    */     }
/* 37 */     return "sps/node-deselected.gif";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 43 */     this.selectionControl.toggleSelection(this.node);
/* 44 */     return null;
/*    */   }
/*    */   
/*    */   public boolean isDisabled() {
/* 48 */     return !this.callback.isSelectable(this.node);
/*    */   }
/*    */   
/*    */   public static interface Callback {
/*    */     boolean isSelectable(Object param1Object);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementatio\\ui\html\calibrationinfo\home\wizard\reqhandler\pdsr\SelectionIconElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */