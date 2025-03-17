/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.toc.page.extended;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.specialbrochures.ContentTreeControl;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.specialbrochures.Node;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainPage;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.toc.page.TOCPage;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.input.tree.gtwo.HtmlTreeElement;
/*    */ import com.eoos.html.element.input.tree.gtwo.TreeControl;
/*    */ import java.util.Iterator;
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
/*    */ 
/*    */ public class TreeElement
/*    */   extends HtmlTreeElement
/*    */ {
/*    */   protected ClientContext context;
/*    */   protected TOCPage tocPage;
/*    */   
/*    */   public TreeElement(ClientContext context, ContentTreeControl treeControl, TOCPage tocPage) {
/* 31 */     super((TreeControl)treeControl);
/* 32 */     this.tocPage = tocPage;
/* 33 */     this.context = context;
/*    */   }
/*    */   
/*    */   protected HtmlElement createIconElement(Object node) {
/* 37 */     if (this.control.isLeaf(node)) {
/* 38 */       return (HtmlElement)new IconElement(this.context, this, (Node)node) {
/*    */           protected String getTargetFrame() {
/* 40 */             return "_top";
/*    */           }
/*    */           
/*    */           public Object onClick(Map params) {
/* 44 */             super.onClick(params);
/* 45 */             return MainPage.getInstance(this.context);
/*    */           }
/*    */         };
/*    */     }
/* 49 */     return (HtmlElement)new IconElement(this.context, this, (Node)node);
/*    */   }
/*    */ 
/*    */   
/*    */   protected HtmlElement createLabelElement(Object node) {
/* 54 */     if (this.control.isLeaf(node)) {
/* 55 */       return (HtmlElement)new LabelElement(this.context, this, (Node)node) {
/*    */           protected String getTargetFrame() {
/* 57 */             return "_top";
/*    */           }
/*    */           
/*    */           public Object onClick(Map params) {
/* 61 */             super.onClick(params);
/* 62 */             return MainPage.getInstance(this.context);
/*    */           }
/*    */         };
/*    */     }
/* 66 */     return (HtmlElement)new LabelElement(this.context, this, (Node)node);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 73 */     Object selectedNode = this.control.getSelectedNode();
/*    */     try {
/* 75 */       Iterator iter = this.control.getSelectedPath().iterator();
/* 76 */       while (iter.hasNext()) {
/* 77 */         Object node = iter.next();
/* 78 */         if (!node.equals(selectedNode)) {
/* 79 */           setExpanded(node, true);
/*    */         }
/*    */       } 
/* 82 */     } catch (Exception e) {}
/*    */     
/* 84 */     return super.getHtmlCode(params);
/*    */   }
/*    */   
/*    */   public boolean isExpanded(Object node) {
/* 88 */     return this.tocPage.isExpanded(node);
/*    */   }
/*    */   
/*    */   public void setExpanded(Object node, boolean expanded) {
/* 92 */     this.tocPage.setExpanded(node, expanded);
/*    */   }
/*    */   
/*    */   public void toggleExpanded(Object node) {
/* 96 */     this.tocPage.toggleExpanded(node);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\specialbrochures\toc\page\extended\TreeElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */