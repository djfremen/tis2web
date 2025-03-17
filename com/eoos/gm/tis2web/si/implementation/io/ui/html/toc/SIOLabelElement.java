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
/*    */ public class SIOLabelElement
/*    */   extends LinkElement
/*    */ {
/*    */   protected TocTreeElement treeElement;
/*    */   protected CTOCTree.NodeWrapper wrapper;
/*    */   
/*    */   public SIOLabelElement(TocTreeElement treeElement, CTOCTree.NodeWrapper wrapper) {
/* 30 */     super("leaf." + treeElement.getContext().createID(), null);
/* 31 */     this.treeElement = treeElement;
/* 32 */     this.wrapper = wrapper;
/*    */   }
/*    */   
/*    */   protected String getLabel() {
/*    */     try {
/* 37 */       return this.treeElement.getTreeControl().getLabel(this.wrapper);
/* 38 */     } catch (Exception e) {
/*    */       try {
/* 40 */         return "label unavailable (node='" + this.wrapper.node.getID() + "')";
/* 41 */       } catch (Exception x) {
/* 42 */         return "label unavailable";
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   protected String getTargetFrame() {
/* 48 */     return "_top";
/*    */   }
/*    */   
/*    */   public Object onClick(Map params) {
/* 52 */     this.treeElement.getTreeControl().setSelectedNode(this.wrapper);
/* 53 */     this.treeElement.getTreeControl().toggleExpanded(this.wrapper);
/* 54 */     DocumentPage.getInstance(this.treeElement.getContext()).setPage(this.wrapper.node, null);
/*    */     
/* 56 */     return MainPage.getInstance(this.treeElement.getContext());
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 61 */     final String code = super.getHtmlCode(params);
/* 62 */     if (this.wrapper.equals(this.treeElement.getTreeControl().getSelectedNode())) {
/* 63 */       return HtmlAnchorRenderer.getInstance().getHtmlCode("selectednode") + HtmlSpanRenderer.getInstance().getHtmlCode((HtmlSpanRenderer.Callback)new HtmlSpanRenderer.CallbackAdapter()
/*    */           {
/*    */             public String getClaZZ() {
/* 66 */               return "selectednode";
/*    */             }
/*    */             
/*    */             public String getContent() {
/* 70 */               return code;
/*    */             }
/*    */           });
/*    */     }
/* 74 */     return code;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\toc\SIOLabelElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */