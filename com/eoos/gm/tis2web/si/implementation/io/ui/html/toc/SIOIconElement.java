/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.toc;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.ctoctree.datamodel.CTOCTree;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.page.DocumentPage;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainPage;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIOProperty;
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
/*    */ public class SIOIconElement
/*    */   extends LinkElement
/*    */ {
/*    */   protected TocTreeElement treeElement;
/*    */   protected CTOCTree.NodeWrapper wrapper;
/*    */   
/*    */   public SIOIconElement(TocTreeElement treeElement, CTOCTree.NodeWrapper wrapper) {
/* 31 */     super("leaf." + treeElement.getContext().createID(), null);
/* 32 */     this.treeElement = treeElement;
/* 33 */     this.wrapper = wrapper;
/*    */   }
/*    */   
/*    */   protected boolean flatMode() {
/* 37 */     return (this.treeElement.getMode() == -1);
/*    */   }
/*    */   
/*    */   protected String getLabel() {
/* 41 */     StringBuffer code = new StringBuffer();
/*    */     
/* 43 */     code.append(HtmlImgRenderer.getInstance().getHtmlCode((HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*    */             public String getImageSource() {
/* 45 */               String image = "common/leaf-icon.gif";
/* 46 */               return "pic/" + image;
/*    */             }
/*    */             
/*    */             public String getAlternativeText() {
/* 50 */               String retValue = null;
/*    */               try {
/* 52 */                 SIO sio = (SIO)SIOIconElement.this.wrapper.node;
/* 53 */                 if (sio.hasProperty((SITOCProperty)SIOProperty.LU)) {
/* 54 */                   retValue = (String)sio.getProperty((SITOCProperty)SIOProperty.LU);
/*    */                 }
/* 56 */               } catch (Exception e) {}
/*    */               
/* 58 */               return retValue;
/*    */             }
/*    */             
/*    */             public void getAdditionalAttributes(Map<String, String> map) {
/* 62 */               map.put("border", "0");
/*    */             }
/*    */           }));
/*    */     
/* 66 */     return code.toString();
/*    */   }
/*    */   
/*    */   protected String getTargetFrame() {
/* 70 */     return "_top";
/*    */   }
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 74 */     this.treeElement.getTreeControl().setSelectedNode(this.wrapper);
/* 75 */     this.treeElement.getTreeControl().toggleExpanded(this.wrapper);
/* 76 */     DocumentPage.getInstance(this.treeElement.getContext()).setPage(this.wrapper.node, null);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 84 */     return MainPage.getInstance(this.treeElement.getContext());
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\toc\SIOIconElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */