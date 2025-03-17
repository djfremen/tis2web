/*    */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.toc;
/*    */ 
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.statistic.TopHitMainWorks;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.main.MainPage;
/*    */ import com.eoos.gm.tis2web.lt.service.cai.SIOLT;
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
/*    */ public class DefaultLeafIconElement
/*    */   extends LinkElement
/*    */ {
/*    */   protected TocTreeElement treeElement;
/*    */   protected Object node;
/*    */   
/*    */   public DefaultLeafIconElement(TocTreeElement treeElement, Object node) {
/* 27 */     super("leaf." + treeElement.getContext().createID(), null);
/* 28 */     this.treeElement = treeElement;
/* 29 */     this.node = node;
/*    */   }
/*    */   
/*    */   protected boolean flatMode() {
/* 33 */     return (this.treeElement.getMode() == -1);
/*    */   }
/*    */   
/*    */   protected String getLabel() {
/* 37 */     StringBuffer code = new StringBuffer();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 45 */     code.append(HtmlImgRenderer.getInstance().getHtmlCode((HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter()
/*    */           {
/*    */             public String getImageSource() {
/* 48 */               String image = null;
/* 49 */               if (DefaultLeafIconElement.this.node instanceof SIOLT) {
/* 50 */                 image = "lt/mainwork.gif";
/*    */               } else {
/* 52 */                 image = "lt/ltdoc.gif";
/*    */               } 
/* 54 */               return "pic/" + image;
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
/*    */   protected String getTargetFrame() {
/* 66 */     return "_top";
/*    */   }
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 70 */     this.treeElement.getTreeControl().setSelectedNode(this.node);
/* 71 */     this.treeElement.getTreeControl().setExpanded(this.node, true);
/*    */     
/* 73 */     if (this.node instanceof SIOLT) {
/* 74 */       TopHitMainWorks.getInstance().hit((SIOLT)this.node);
/*    */     }
/*    */     
/* 77 */     return MainPage.getInstance(this.treeElement.getContext());
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\toc\DefaultLeafIconElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */