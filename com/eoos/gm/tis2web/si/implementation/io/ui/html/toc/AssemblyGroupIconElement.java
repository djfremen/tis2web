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
/*    */ public class AssemblyGroupIconElement
/*    */   extends LinkElement
/*    */ {
/*    */   protected TocTreeElement treeElement;
/*    */   protected CTOCTree.NodeWrapper wrapper;
/*    */   
/*    */   public AssemblyGroupIconElement(TocTreeElement treeElement, CTOCTree.NodeWrapper wrapper) {
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
/* 43 */               if (AssemblyGroupIconElement.this.treeElement.getTreeControl().isExpanded(AssemblyGroupIconElement.this.wrapper) && !AssemblyGroupIconElement.this.flatMode()) {
/* 44 */                 image = "common/folder-open.gif";
/*    */               } else {
/* 46 */                 image = "common/folder-closed.gif";
/*    */               } 
/* 48 */               return "pic/" + image;
/*    */             }
/*    */             
/*    */             public String getAlternativeText() {
/* 52 */               return null;
/*    */             }
/*    */             
/*    */             public void getAdditionalAttributes(Map<String, String> map) {
/* 56 */               map.put("border", "0");
/*    */             }
/*    */           }));
/*    */     
/* 60 */     return code.toString();
/*    */   }
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 64 */     this.treeElement.getTreeControl().toggleExpanded(this.wrapper);
/*    */ 
/*    */     
/* 67 */     if (flatMode()) {
/* 68 */       this.treeElement.getTreeControl().setSelectedNode(this.wrapper);
/*    */     }
/*    */     
/* 71 */     HtmlElementContainer container = getContainer();
/* 72 */     while (container != null) {
/* 73 */       container = container.getContainer();
/*    */     }
/* 75 */     return container;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\toc\AssemblyGroupIconElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */