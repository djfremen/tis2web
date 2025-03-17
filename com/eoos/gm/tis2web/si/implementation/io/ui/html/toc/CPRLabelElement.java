/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.toc;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.ctoctree.datamodel.CTOCTree;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.page.DocumentPage;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainPage;
/*    */ import com.eoos.html.element.input.LinkElement;
/*    */ import com.eoos.html.renderer.HtmlAnchorRenderer;
/*    */ import com.eoos.html.renderer.HtmlSpanRenderer;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ public class CPRLabelElement
/*    */   extends LinkElement
/*    */ {
/*    */   protected TocTreeElement treeElement;
/*    */   protected CTOCTree.NodeWrapper wrapperCTOC;
/*    */   protected CTOCTree.NodeWrapper wrapperSIO;
/*    */   
/*    */   public CPRLabelElement(TocTreeElement treeElement, CTOCTree.NodeWrapper wrapperES, CTOCTree.NodeWrapper wrapperA) {
/* 20 */     super(treeElement.getContext().createID(), null);
/* 21 */     this.treeElement = treeElement;
/* 22 */     this.wrapperCTOC = wrapperES;
/* 23 */     this.wrapperSIO = wrapperA;
/*    */   }
/*    */   
/*    */   protected String getLabel() {
/* 27 */     return this.treeElement.getTreeControl().getLabel(this.wrapperCTOC);
/*    */   }
/*    */   
/*    */   protected String getTargetFrame() {
/* 31 */     return "_top";
/*    */   }
/*    */   
/*    */   public Object onClick(Map params) {
/* 35 */     if (this.treeElement.getTreeControl().isExpanded(this.wrapperCTOC)) {
/* 36 */       this.treeElement.getTreeControl().toggleExpanded(this.wrapperCTOC);
/* 37 */       this.treeElement.getTreeControl().setSelectedNode(this.wrapperCTOC);
/*    */     } else {
/* 39 */       this.treeElement.getTreeControl().toggleExpanded(this.wrapperCTOC);
/* 40 */       this.treeElement.getTreeControl().setSelectedNode(this.wrapperSIO);
/*    */     } 
/* 42 */     DocumentPage.getInstance(this.treeElement.getContext()).setPage(this.wrapperSIO.node, null);
/* 43 */     return MainPage.getInstance(this.treeElement.getContext());
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 47 */     final String code = super.getHtmlCode(params);
/* 48 */     if (this.wrapperCTOC.equals(this.treeElement.getTreeControl().getSelectedNode())) {
/* 49 */       return HtmlAnchorRenderer.getInstance().getHtmlCode("selectednode") + HtmlSpanRenderer.getInstance().getHtmlCode((HtmlSpanRenderer.Callback)new HtmlSpanRenderer.CallbackAdapter()
/*    */           {
/*    */             protected String getClaZZ() {
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


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\toc\CPRLabelElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */