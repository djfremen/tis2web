/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.textsearch.resulttree;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.db.FTSSIElement;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIOProperty;
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
/*    */ public class SIOIconElement
/*    */   extends LinkElement
/*    */ {
/*    */   protected TocTreeElement treeElement;
/*    */   protected Object node;
/*    */   
/*    */   public SIOIconElement(TocTreeElement treeElement, Object node) {
/* 28 */     super("leaf." + treeElement.getContext().createID(), null);
/* 29 */     this.treeElement = treeElement;
/* 30 */     this.node = node;
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
/* 42 */               String image = "common/leaf-icon.gif";
/* 43 */               return "pic/" + image;
/*    */             }
/*    */             
/*    */             public String getAlternativeText() {
/* 47 */               String retValue = null;
/*    */               
/*    */               try {
/* 50 */                 if (SIOIconElement.this.node instanceof FTSSIElement) {
/* 51 */                   retValue = ((FTSSIElement)SIOIconElement.this.node).getLiteratureNumber();
/*    */                 }
/* 53 */                 else if (SIOIconElement.this.node instanceof SIO && (
/* 54 */                   (SIO)SIOIconElement.this.node).hasProperty((SITOCProperty)SIOProperty.LU)) {
/* 55 */                   retValue = (String)((SIO)SIOIconElement.this.node).getProperty((SITOCProperty)SIOProperty.LU);
/*    */                 }
/*    */               
/* 58 */               } catch (Exception e) {}
/*    */               
/* 60 */               return retValue;
/*    */             }
/*    */             
/*    */             public void getAdditionalAttributes(Map<String, String> map) {
/* 64 */               map.put("border", "0");
/*    */             }
/*    */           }));
/*    */     
/* 68 */     return code.toString();
/*    */   }
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 72 */     this.treeElement.getTreeControl().toggleExpanded(this.node);
/*    */ 
/*    */     
/* 75 */     if (flatMode()) {
/* 76 */       this.treeElement.getTreeControl().setSelectedNode(this.node);
/*    */     }
/*    */     
/* 79 */     HtmlElementContainer container = getContainer();
/* 80 */     while (container.getContainer() != null) {
/* 81 */       container = container.getContainer();
/*    */     }
/* 83 */     return container;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\textsearch\resulttree\SIOIconElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */