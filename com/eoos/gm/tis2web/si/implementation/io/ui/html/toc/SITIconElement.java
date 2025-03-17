/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.toc;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.ctoctree.datamodel.CTOCTree;
/*    */ import com.eoos.html.element.HtmlElementContainer;
/*    */ import com.eoos.html.element.input.LinkElement;
/*    */ import com.eoos.html.renderer.HtmlImgRenderer;
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
/*    */ public class SITIconElement
/*    */   extends LinkElement
/*    */ {
/*    */   protected TocTreeElement treeElement;
/*    */   protected CTOCTree.NodeWrapper wrapper;
/*    */   
/*    */   public SITIconElement(TocTreeElement treeElement, CTOCTree.NodeWrapper wrapper) {
/* 28 */     super(treeElement.getContext().createID(), null);
/* 29 */     this.treeElement = treeElement;
/* 30 */     this.wrapper = wrapper;
/*    */   }
/*    */   
/*    */   protected boolean flatMode() {
/* 34 */     return (this.treeElement.getMode() == -1);
/*    */   }
/*    */   
/*    */   protected String getLabel() {
/* 38 */     StringBuffer code = new StringBuffer();
/*    */     
/* 40 */     code.append(HtmlImgRenderer.getInstance().getHtmlCode((HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*    */             public String getImageSource() {
/* 42 */               String image = null;
/* 43 */               if (SITIconElement.this.treeElement.getTreeControl().isExpanded(SITIconElement.this.wrapper) && !SITIconElement.this.flatMode()) {
/* 44 */                 image = "common/sit-open.gif";
/*    */               } else {
/*    */                 
/* 47 */                 image = "common/sit-closed.gif";
/*    */               } 
/*    */ 
/*    */               
/* 51 */               return "pic/" + image;
/*    */             }
/*    */             
/*    */             public String getAlternativeText() {
/* 55 */               return null;
/*    */             }
/*    */             
/*    */             public void getAdditionalAttributes(Map<String, String> map) {
/* 59 */               map.put("border", "0");
/*    */             }
/*    */           }));
/*    */     
/* 63 */     return code.toString();
/*    */   }
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 67 */     this.treeElement.getTreeControl().toggleExpanded(this.wrapper);
/*    */ 
/*    */     
/* 70 */     if (flatMode()) {
/* 71 */       this.treeElement.getTreeControl().setSelectedNode(this.wrapper);
/*    */     }
/*    */     
/* 74 */     HtmlElementContainer container = getContainer();
/* 75 */     while (container.getContainer() != null) {
/* 76 */       container = container.getContainer();
/*    */     }
/* 78 */     return container;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\toc\SITIconElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */