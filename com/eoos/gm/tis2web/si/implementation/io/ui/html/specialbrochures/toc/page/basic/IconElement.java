/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.toc.page.basic;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.specialbrochures.ContentTreeControl;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.specialbrochures.Node;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.specialbrochures.SpecialBrochuresContext;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainPage;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.toc.page.common.NodeLinkElement;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIOProperty;
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
/*    */ public class IconElement
/*    */   extends NodeLinkElement
/*    */ {
/*    */   protected FlatTreeElement treeElement;
/*    */   protected ContentTreeControl treeControl;
/*    */   
/*    */   public IconElement(ClientContext context, FlatTreeElement treeElement, Node node) {
/* 32 */     super(context, node);
/* 33 */     this.treeElement = treeElement;
/* 34 */     this.treeControl = SpecialBrochuresContext.getInstance(context).getContentTreeControl();
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getLabel() {
/* 39 */     StringBuffer code = new StringBuffer();
/*    */     
/* 41 */     code.append(HtmlImgRenderer.getInstance().getHtmlCode((HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*    */             public String getImageSource() {
/* 43 */               String image = null;
/* 44 */               if (IconElement.this.treeControl.isLeaf(IconElement.this.node)) {
/* 45 */                 image = "common/leaf-icon.gif";
/*    */               
/*    */               }
/*    */               else {
/*    */                 
/* 50 */                 image = "common/folder-closed.gif";
/*    */               } 
/*    */               
/* 53 */               return "pic/" + image;
/*    */             }
/*    */             
/*    */             public String getAlternativeText() {
/* 57 */               String retValue = null;
/* 58 */               if (IconElement.this.treeControl.isLeaf(IconElement.this.node)) {
/*    */                 try {
/* 60 */                   SIO sio = (SIO)IconElement.this.node.content;
/* 61 */                   if (sio.hasProperty((SITOCProperty)SIOProperty.LU)) {
/* 62 */                     retValue = (String)sio.getProperty((SITOCProperty)SIOProperty.LU);
/*    */                   }
/* 64 */                 } catch (Exception e) {}
/*    */               }
/*    */               
/* 67 */               return retValue;
/*    */             }
/*    */             
/*    */             public void getAdditionalAttributes(Map<String, String> map) {
/* 71 */               map.put("border", "0");
/*    */             }
/*    */           }));
/*    */     
/* 75 */     return code.toString();
/*    */   }
/*    */   
/*    */   protected String getTargetFrame() {
/* 79 */     return "_top";
/*    */   }
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 83 */     Object retValue = super.onClick(submitParams);
/* 84 */     if (retValue == null) {
/* 85 */       this.treeElement.setExpanded(this.node, true);
/* 86 */       this.treeControl.setSelectedNode(this.node);
/*    */       
/* 88 */       retValue = MainPage.getInstance(this.context);
/*    */     } 
/* 90 */     return retValue;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onReturnVCDialog(boolean result) {
/* 95 */     if (result) {
/* 96 */       this.treeElement.setExpanded(this.node, true);
/* 97 */       this.treeControl.setSelectedNode(this.node);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\specialbrochures\toc\page\basic\IconElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */