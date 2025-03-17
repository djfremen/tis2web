/*    */ package com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.reqhandler.pdsr;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.element.input.tree.TreeControl;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ExpanderIconElement
/*    */   extends TreeLinkElement
/*    */ {
/*    */   protected ExpanderIconElement(ClientContext context, TreeControl control, Object node) {
/* 14 */     super(context, control, node);
/*    */   }
/*    */   
/*    */   protected String getImagePath() {
/* 18 */     if (this.control.isLeaf(this.node) || this.control.isExpanded(this.node)) {
/* 19 */       return "sps/expander-expanded.gif";
/*    */     }
/* 21 */     return "sps/expander-collapsed.gif";
/*    */   }
/*    */ 
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 26 */     this.control.toggleExpanded(this.node);
/* 27 */     return null;
/*    */   }
/*    */   
/*    */   public boolean isDisabled() {
/* 31 */     return this.control.isLeaf(this.node);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementatio\\ui\html\calibrationinfo\home\wizard\reqhandler\pdsr\ExpanderIconElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */