/*    */ package com.eoos.gm.tis2web.news.implementation.ui.html.contenttree.panel;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.news.implementation.datamodel.domain.toc.Node;
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
/*    */ public class IconElement
/*    */   extends LinkElement
/*    */ {
/*    */   protected ClientContext context;
/*    */   protected NewsTOCTreeElement treeElement;
/*    */   protected Node node;
/*    */   
/*    */   public IconElement(ClientContext context, NewsTOCTreeElement treeElement, Node node) {
/* 29 */     super(context.createID(), null);
/* 30 */     this.context = context;
/* 31 */     this.treeElement = treeElement;
/* 32 */     this.node = node;
/*    */   }
/*    */   
/*    */   protected String getLabel() {
/* 36 */     StringBuffer code = new StringBuffer();
/*    */     
/* 38 */     code.append(HtmlImgRenderer.getInstance().getHtmlCode((HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*    */             public String getImageSource() {
/* 40 */               String image = null;
/* 41 */               if (IconElement.this.treeElement.getTreeControl().isLeaf(IconElement.this.node)) {
/* 42 */                 image = "common/leaf-icon.gif";
/*    */               }
/* 44 */               else if (IconElement.this.treeElement.isExpanded(IconElement.this.node)) {
/* 45 */                 image = "common/folder-open.gif";
/*    */               } else {
/* 47 */                 image = "common/folder-closed.gif";
/*    */               } 
/*    */               
/* 50 */               return "pic/" + image;
/*    */             }
/*    */             
/*    */             public String getAlternativeText() {
/* 54 */               return null;
/*    */             }
/*    */             
/*    */             public void getAdditionalAttributes(Map<String, String> map) {
/* 58 */               map.put("border", "0");
/*    */             }
/*    */           }));
/*    */     
/* 62 */     return code.toString();
/*    */   }
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 66 */     this.treeElement.toggleExpanded(this.node);
/* 67 */     this.treeElement.getTreeControl().setSelectedNode(this.node);
/*    */     
/* 69 */     HtmlElementContainer container = getContainer();
/* 70 */     while (container.getContainer() != null) {
/* 71 */       container = container.getContainer();
/*    */     }
/* 73 */     return container;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\news\implementatio\\ui\html\contenttree\panel\IconElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */