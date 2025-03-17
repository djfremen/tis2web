/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.textsearch.resulttree;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
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
/*    */ public class SITIconElement
/*    */   extends LinkElement
/*    */ {
/*    */   protected TocTreeElement treeElement;
/*    */   protected CTOCNode node;
/*    */   
/*    */   public SITIconElement(TocTreeElement treeElement, CTOCNode node) {
/* 26 */     super(treeElement.getContext().createID(), null);
/* 27 */     this.treeElement = treeElement;
/* 28 */     this.node = node;
/*    */   }
/*    */   
/*    */   protected boolean flatMode() {
/* 32 */     return (this.treeElement.getMode() == -1);
/*    */   }
/*    */   
/*    */   protected String getLabel() {
/* 36 */     StringBuffer code = new StringBuffer();
/*    */     
/* 38 */     code.append(HtmlImgRenderer.getInstance().getHtmlCode((HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*    */             public String getImageSource() {
/* 40 */               String image = null;
/* 41 */               if (SITIconElement.this.treeElement.getTreeControl().isExpanded(SITIconElement.this.node) && !SITIconElement.this.flatMode()) {
/* 42 */                 image = "common/sit-open.gif";
/*    */               } else {
/*    */                 
/* 45 */                 image = "common/sit-closed.gif";
/*    */               } 
/*    */ 
/*    */               
/* 49 */               return "pic/" + image;
/*    */             }
/*    */             
/*    */             public String getAlternativeText() {
/* 53 */               return null;
/*    */             }
/*    */             
/*    */             public void getAdditionalAttributes(Map<String, String> map) {
/* 57 */               map.put("border", "0");
/*    */             }
/*    */           }));
/*    */     
/* 61 */     return code.toString();
/*    */   }
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 65 */     this.treeElement.getTreeControl().toggleExpanded(this.node);
/*    */ 
/*    */     
/* 68 */     if (flatMode()) {
/* 69 */       this.treeElement.getTreeControl().setSelectedNode(this.node);
/*    */     }
/*    */     
/* 72 */     HtmlElementContainer container = getContainer();
/* 73 */     while (container.getContainer() != null) {
/* 74 */       container = container.getContainer();
/*    */     }
/* 76 */     return container;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\textsearch\resulttree\SITIconElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */