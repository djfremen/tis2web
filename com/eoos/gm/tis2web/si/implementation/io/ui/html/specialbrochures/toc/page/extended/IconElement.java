/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.toc.page.extended;
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
/*    */ public class IconElement
/*    */   extends NodeLinkElement
/*    */ {
/*    */   protected TreeElement treeElement;
/*    */   protected ContentTreeControl treeControl;
/*    */   
/*    */   public IconElement(ClientContext context, TreeElement treeElement, Node node) {
/* 31 */     super(context, node);
/* 32 */     this.treeElement = treeElement;
/* 33 */     this.treeControl = SpecialBrochuresContext.getInstance(context).getContentTreeControl();
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getLabel() {
/* 38 */     StringBuffer code = new StringBuffer();
/*    */     
/* 40 */     code.append(HtmlImgRenderer.getInstance().getHtmlCode((HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*    */             public String getImageSource() {
/* 42 */               String image = null;
/* 43 */               if (IconElement.this.treeControl.isLeaf(IconElement.this.node)) {
/* 44 */                 image = "common/leaf-icon.gif";
/*    */               }
/* 46 */               else if (IconElement.this.treeElement.isExpanded(IconElement.this.node)) {
/* 47 */                 image = "common/folder-open.gif";
/*    */               } else {
/* 49 */                 image = "common/folder-closed.gif";
/*    */               } 
/*    */               
/* 52 */               return "pic/" + image;
/*    */             }
/*    */             
/*    */             public String getAlternativeText() {
/* 56 */               String retValue = null;
/* 57 */               if (IconElement.this.treeControl.isLeaf(IconElement.this.node)) {
/*    */                 try {
/* 59 */                   SIO sio = (SIO)IconElement.this.node.content;
/* 60 */                   if (sio.hasProperty((SITOCProperty)SIOProperty.LU)) {
/* 61 */                     retValue = (String)sio.getProperty((SITOCProperty)SIOProperty.LU);
/*    */                   }
/* 63 */                 } catch (Exception e) {}
/*    */               }
/*    */               
/* 66 */               return retValue;
/*    */             }
/*    */ 
/*    */             
/*    */             public void getAdditionalAttributes(Map<String, String> map) {
/* 71 */               map.put("border", "0");
/*    */             }
/*    */           }));
/*    */     
/* 75 */     return code.toString();
/*    */   }
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 79 */     Object retValue = super.onClick(submitParams);
/* 80 */     if (retValue == null) {
/* 81 */       this.treeElement.toggleExpanded(this.node);
/* 82 */       this.treeControl.setSelectedNode(this.node);
/*    */       
/* 84 */       retValue = MainPage.getInstance(this.context);
/*    */     } 
/* 86 */     return retValue;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onReturnVCDialog(boolean result) {
/* 91 */     if (result) {
/* 92 */       this.treeElement.setExpanded(this.node, true);
/* 93 */       this.treeControl.setSelectedNode(this.node);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\specialbrochures\toc\page\extended\IconElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */