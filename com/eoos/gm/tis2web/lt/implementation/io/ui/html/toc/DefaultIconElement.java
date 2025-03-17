/*    */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.toc;
/*    */ 
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.main.MainPage;
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
/*    */ public class DefaultIconElement
/*    */   extends LinkElement
/*    */ {
/*    */   protected TocTreeElement treeElement;
/*    */   protected Object node;
/*    */   
/*    */   public DefaultIconElement(TocTreeElement treeElement, Object node) {
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 44 */     code.append(HtmlImgRenderer.getInstance().getHtmlCode((HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*    */             public String getImageSource() {
/* 46 */               String image = null;
/* 47 */               if (DefaultIconElement.this.treeElement.getTreeControl().isLeaf(DefaultIconElement.this.node)) {
/* 48 */                 if (DefaultIconElement.this.node instanceof com.eoos.gm.tis2web.lt.service.cai.SIOLT) {
/* 49 */                   image = "lt/mainwork.gif";
/*    */                 } else {
/* 51 */                   image = "lt/ltdoc.gif";
/*    */                 }
/*    */               
/* 54 */               } else if (DefaultIconElement.this.treeElement.getTreeControl().getParent(DefaultIconElement.this.node) != null) {
/* 55 */                 if (DefaultIconElement.this.treeElement.getTreeControl().isExpanded(DefaultIconElement.this.node) && !DefaultIconElement.this.flatMode()) {
/* 56 */                   image = "lt/position-open.gif";
/*    */                 } else {
/* 58 */                   image = "lt/position.gif";
/*    */                 }
/*    */               
/* 61 */               } else if (DefaultIconElement.this.treeElement.getTreeControl().isExpanded(DefaultIconElement.this.node) && !DefaultIconElement.this.flatMode()) {
/* 62 */                 image = "lt/group-open.gif";
/*    */               } else {
/* 64 */                 image = "lt/group.gif";
/*    */               } 
/*    */ 
/*    */ 
/*    */               
/* 69 */               return "pic/" + image;
/*    */             }
/*    */             
/*    */             public void getAdditionalAttributes(Map<String, String> map) {
/* 73 */               map.put("border", "0");
/*    */             }
/*    */           }));
/*    */     
/* 77 */     return code.toString();
/*    */   }
/*    */   
/*    */   protected String getTargetFrame() {
/* 81 */     return "_top";
/*    */   }
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 85 */     this.treeElement.getTreeControl().toggleExpanded(this.node);
/*    */ 
/*    */ 
/*    */     
/* 89 */     this.treeElement.getTreeControl().setSelectedNode(this.node);
/*    */ 
/*    */     
/* 92 */     return MainPage.getInstance(this.treeElement.getContext());
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\toc\DefaultIconElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */