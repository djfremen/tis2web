/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.toc.page.basic;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.specialbrochures.ContentTreeControl;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.specialbrochures.Node;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.toc.page.TOCPage;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.input.tree.gtwo.HtmlFlatTreeElement;
/*    */ import com.eoos.html.element.input.tree.gtwo.TreeControl;
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
/*    */ public class FlatTreeElement
/*    */   extends HtmlFlatTreeElement
/*    */ {
/*    */   protected ClientContext context;
/*    */   protected TOCPage tocPage;
/*    */   
/*    */   public FlatTreeElement(ClientContext context, ContentTreeControl treeControl, TOCPage tocPage) {
/* 27 */     super((TreeControl)treeControl);
/* 28 */     this.context = context;
/* 29 */     this.tocPage = tocPage;
/*    */   }
/*    */   
/*    */   protected HtmlElement createIconElement(Object node) {
/* 33 */     return (HtmlElement)new IconElement(this.context, this, (Node)node);
/*    */   }
/*    */   
/*    */   protected HtmlElement createLabelElement(Object node) {
/* 37 */     return (HtmlElement)new LabelElement(this.context, this, (Node)node);
/*    */   }
/*    */   
/*    */   public boolean isExpanded(Object node) {
/* 41 */     return this.tocPage.isExpanded(node);
/*    */   }
/*    */   
/*    */   public void setExpanded(Object node, boolean expanded) {
/* 45 */     this.tocPage.setExpanded(node, expanded);
/*    */   }
/*    */   
/*    */   public void toggleExpanded(Object node) {
/* 49 */     this.tocPage.toggleExpanded(node);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\specialbrochures\toc\page\basic\FlatTreeElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */