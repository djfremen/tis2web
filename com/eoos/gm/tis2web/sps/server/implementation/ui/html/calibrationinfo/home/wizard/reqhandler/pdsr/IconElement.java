/*    */ package com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.reqhandler.pdsr;
/*    */ 
/*    */ import com.eoos.datatype.selection.implementation.SelectionControlSPI;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import com.eoos.html.element.input.tree.TreeControl;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IconElement
/*    */   extends HtmlElementContainerBase
/*    */ {
/*    */   private ExpanderIconElement expanderElement;
/*    */   private SelectionIconElement selectionElement;
/*    */   
/*    */   public IconElement(ClientContext context, TreeControl control, Object node, SelectionControlSPI selectionControl, SelectionIconElement.Callback callback) {
/* 20 */     this.expanderElement = new ExpanderIconElement(context, control, node);
/* 21 */     addElement((HtmlElement)this.expanderElement);
/*    */     
/* 23 */     this.selectionElement = new SelectionIconElement(context, control, node, selectionControl, callback);
/* 24 */     addElement((HtmlElement)this.selectionElement);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 29 */     StringBuffer tmp = new StringBuffer("<table><tr><td>{EXPANDER}</td><td></td><td>{SELECTION}</td></tr></table>");
/* 30 */     StringUtilities.replace(tmp, "{EXPANDER}", this.expanderElement.getHtmlCode(params));
/* 31 */     StringUtilities.replace(tmp, "{SELECTION}", this.selectionElement.getHtmlCode(params));
/* 32 */     return tmp.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementatio\\ui\html\calibrationinfo\home\wizard\reqhandler\pdsr\IconElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */