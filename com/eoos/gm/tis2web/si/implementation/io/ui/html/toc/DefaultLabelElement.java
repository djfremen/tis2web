/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.toc;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.ctoctree.datamodel.CTOCTree;
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
/*    */ 
/*    */ 
/*    */ public class DefaultLabelElement
/*    */   extends LinkElement
/*    */ {
/*    */   protected TocTreeElement treeElement;
/*    */   protected CTOCTree.NodeWrapper wrapper;
/*    */   
/*    */   public DefaultLabelElement(TocTreeElement treeElement, CTOCTree.NodeWrapper wrapper) {
/* 30 */     super(treeElement.getContext().createID(), null);
/* 31 */     this.treeElement = treeElement;
/* 32 */     this.wrapper = wrapper;
/*    */   }
/*    */   
/*    */   protected String getLabel() {
/*    */     try {
/* 37 */       return this.treeElement.getTreeControl().getLabel(this.wrapper);
/* 38 */     } catch (Exception x) {
/*    */       
/* 40 */       return "(missing label)";
/*    */     } 
/*    */   }
/*    */   public Object onClick(Map params) {
/* 44 */     this.treeElement.getTreeControl().setSelectedNode(this.wrapper);
/* 45 */     this.treeElement.getTreeControl().toggleExpanded(this.wrapper);
/*    */     
/* 47 */     HtmlElementContainer container = getContainer();
/* 48 */     while (container.getContainer() != null) {
/* 49 */       container = container.getContainer();
/*    */     }
/* 51 */     return container;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 56 */     final String code = super.getHtmlCode(params);
/* 57 */     if (this.wrapper.equals(this.treeElement.getTreeControl().getSelectedNode())) {
/* 58 */       return HtmlAnchorRenderer.getInstance().getHtmlCode("selectednode") + HtmlSpanRenderer.getInstance().getHtmlCode((HtmlSpanRenderer.Callback)new HtmlSpanRenderer.CallbackAdapter()
/*    */           {
/*    */             public String getClaZZ() {
/* 61 */               return "selectednode";
/*    */             }
/*    */             
/*    */             public String getContent() {
/* 65 */               return code;
/*    */             }
/*    */           });
/*    */     }
/* 69 */     return code;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\toc\DefaultLabelElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */