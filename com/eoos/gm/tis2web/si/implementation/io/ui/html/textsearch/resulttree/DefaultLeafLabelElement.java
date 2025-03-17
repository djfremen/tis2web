/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.textsearch.resulttree;
/*    */ 
/*    */ import com.eoos.gm.tis2web.fts.implementation.service.FTSSIO;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainPage;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.textsearch.TextSearchPanel;
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
/*    */ public class DefaultLeafLabelElement
/*    */   extends LinkElement
/*    */ {
/*    */   protected TocTreeElement treeElement;
/*    */   protected Object node;
/*    */   
/*    */   public DefaultLeafLabelElement(TocTreeElement treeElement, Object node) {
/* 27 */     super("leaf." + treeElement.getContext().createID(), null);
/* 28 */     this.treeElement = treeElement;
/* 29 */     this.node = node;
/*    */   }
/*    */   
/*    */   protected String getLabel() {
/* 33 */     return this.treeElement.getTreeControl().getLabel(this.node);
/*    */   }
/*    */   
/*    */   protected String getTargetFrame() {
/* 37 */     return "_top";
/*    */   }
/*    */   
/*    */   public Object onClick(Map params) {
/* 41 */     this.treeElement.getTreeControl().setSelectedNode(this.node);
/* 42 */     this.treeElement.getTreeControl().toggleExpanded(this.node);
/* 43 */     if (this.node instanceof FTSSIO) {
/* 44 */       this.node = ((FTSSIO)this.node).lookupSIO();
/*    */     }
/* 46 */     TextSearchPanel.getInstance(this.treeElement.getContext()).showDocument(this.node);
/*    */     
/* 48 */     return MainPage.getInstance(this.treeElement.getContext());
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 53 */     final String code = super.getHtmlCode(params);
/* 54 */     if (this.node.equals(this.treeElement.getTreeControl().getSelectedNode())) {
/* 55 */       return HtmlSpanRenderer.getInstance().getHtmlCode((HtmlSpanRenderer.Callback)new HtmlSpanRenderer.CallbackAdapter()
/*    */           {
/*    */             public String getClaZZ() {
/* 58 */               return "selectednode";
/*    */             }
/*    */             
/*    */             public String getContent() {
/* 62 */               return code;
/*    */             }
/*    */           });
/*    */     }
/* 66 */     return code;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\textsearch\resulttree\DefaultLeafLabelElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */