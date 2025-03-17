/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.textsearch.resulttree;
/*    */ 
/*    */ import com.eoos.html.element.HtmlElementContainer;
/*    */ import com.eoos.html.element.input.LinkElement;
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
/*    */ public class DefaultLabelElement
/*    */   extends LinkElement
/*    */ {
/*    */   protected TocTreeElement treeElement;
/*    */   protected Object node;
/*    */   
/*    */   public DefaultLabelElement(TocTreeElement treeElement, Object node) {
/* 25 */     super(treeElement.getContext().createID(), null);
/* 26 */     this.treeElement = treeElement;
/* 27 */     this.node = node;
/*    */   }
/*    */   
/*    */   protected String getLabel() {
/* 31 */     return this.treeElement.getTreeControl().getLabel(this.node);
/*    */   }
/*    */   
/*    */   public Object onClick(Map params) {
/* 35 */     this.treeElement.getTreeControl().setSelectedNode(this.node);
/* 36 */     this.treeElement.getTreeControl().toggleExpanded(this.node);
/*    */     
/* 38 */     HtmlElementContainer container = getContainer();
/* 39 */     while (container.getContainer() != null) {
/* 40 */       container = container.getContainer();
/*    */     }
/* 42 */     return container;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 47 */     final String code = super.getHtmlCode(params);
/* 48 */     if (this.node.equals(this.treeElement.getTreeControl().getSelectedNode())) {
/* 49 */       return HtmlSpanRenderer.getInstance().getHtmlCode((HtmlSpanRenderer.Callback)new HtmlSpanRenderer.CallbackAdapter()
/*    */           {
/*    */             public String getClaZZ() {
/* 52 */               return "selectednode";
/*    */             }
/*    */             
/*    */             public String getContent() {
/* 56 */               return code;
/*    */             }
/*    */           });
/*    */     }
/* 60 */     return code;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\textsearch\resulttree\DefaultLabelElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */