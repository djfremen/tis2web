/*    */ package com.eoos.gm.tis2web.news.implementation.ui.html.contenttree.panel;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.news.implementation.datamodel.domain.toc.Node;
/*    */ import com.eoos.html.element.HtmlElementContainer;
/*    */ import com.eoos.html.element.input.LinkElement;
/*    */ import com.eoos.html.renderer.HtmlAnchorRenderer;
/*    */ import com.eoos.html.renderer.HtmlSpanRenderer;
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
/*    */ 
/*    */ public class LabelElement
/*    */   extends LinkElement
/*    */ {
/*    */   protected ClientContext context;
/*    */   protected NewsTOCTreeElement treeElement;
/*    */   protected Node node;
/*    */   
/*    */   public LabelElement(ClientContext context, NewsTOCTreeElement treeElement, Node node) {
/* 30 */     super(context.createID(), null);
/* 31 */     this.context = context;
/* 32 */     this.treeElement = treeElement;
/* 33 */     this.node = node;
/*    */   }
/*    */   
/*    */   protected String getLabel() {
/* 37 */     return this.treeElement.getTreeControl().getLabel(this.node);
/*    */   }
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 41 */     this.treeElement.toggleExpanded(this.node);
/* 42 */     this.treeElement.getTreeControl().setSelectedNode(this.node);
/*    */     
/* 44 */     HtmlElementContainer container = getContainer();
/* 45 */     while (container.getContainer() != null) {
/* 46 */       container = container.getContainer();
/*    */     }
/* 48 */     return container;
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 52 */     final String code = super.getHtmlCode(params);
/* 53 */     if (this.node.equals(this.treeElement.getTreeControl().getSelectedNode())) {
/* 54 */       return HtmlAnchorRenderer.getInstance().getHtmlCode("selectednode") + HtmlSpanRenderer.getInstance().getHtmlCode((HtmlSpanRenderer.Callback)new HtmlSpanRenderer.CallbackAdapter()
/*    */           {
/*    */             public String getClaZZ() {
/* 57 */               return "selectednode";
/*    */             }
/*    */             
/*    */             public String getContent() {
/* 61 */               return code;
/*    */             }
/*    */           });
/*    */     }
/* 65 */     return code;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\news\implementatio\\ui\html\contenttree\panel\LabelElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */