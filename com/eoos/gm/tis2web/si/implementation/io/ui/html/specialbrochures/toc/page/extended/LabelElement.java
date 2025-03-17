/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.toc.page.extended;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.specialbrochures.ContentTreeControl;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.specialbrochures.Node;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.specialbrochures.SpecialBrochuresContext;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainPage;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.toc.page.common.NodeLinkElement;
/*    */ import com.eoos.html.renderer.HtmlAnchorRenderer;
/*    */ import com.eoos.html.renderer.HtmlSpanRenderer;
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
/*    */ public class LabelElement
/*    */   extends NodeLinkElement
/*    */ {
/*    */   protected TreeElement treeElement;
/*    */   protected ContentTreeControl treeControl;
/*    */   
/*    */   public LabelElement(ClientContext context, TreeElement treeElement, Node node) {
/* 31 */     super(context, node);
/* 32 */     this.treeElement = treeElement;
/* 33 */     this.treeControl = SpecialBrochuresContext.getInstance(context).getContentTreeControl();
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getLabel() {
/* 38 */     return this.treeControl.getLabel(this.node);
/*    */   }
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 42 */     Object retValue = super.onClick(submitParams);
/* 43 */     if (retValue == null) {
/* 44 */       this.treeElement.toggleExpanded(this.node);
/* 45 */       this.treeControl.setSelectedNode(this.node);
/*    */       
/* 47 */       retValue = MainPage.getInstance(this.context);
/*    */     } 
/*    */     
/* 50 */     return retValue;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 55 */     final String code = super.getHtmlCode(params);
/* 56 */     if (this.node.equals(this.treeControl.getSelectedNode())) {
/* 57 */       return HtmlAnchorRenderer.getInstance().getHtmlCode("selectednode") + HtmlSpanRenderer.getInstance().getHtmlCode((HtmlSpanRenderer.Callback)new HtmlSpanRenderer.CallbackAdapter()
/*    */           {
/*    */             public String getClaZZ() {
/* 60 */               return "selectednode";
/*    */             }
/*    */             
/*    */             public String getContent() {
/* 64 */               return code;
/*    */             }
/*    */           });
/*    */     }
/* 68 */     return code;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onReturnVCDialog(boolean result) {
/* 73 */     if (result) {
/* 74 */       this.treeElement.setExpanded(this.node, true);
/* 75 */       this.treeControl.setSelectedNode(this.node);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\specialbrochures\toc\page\extended\LabelElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */