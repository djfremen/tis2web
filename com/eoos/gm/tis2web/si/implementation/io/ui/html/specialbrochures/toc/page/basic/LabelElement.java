/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.toc.page.basic;
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
/*    */   protected FlatTreeElement treeElement;
/*    */   protected ContentTreeControl treeControl;
/*    */   
/*    */   public LabelElement(ClientContext context, FlatTreeElement treeElement, Node node) {
/* 31 */     super(context, node);
/* 32 */     this.treeElement = treeElement;
/* 33 */     this.treeControl = SpecialBrochuresContext.getInstance(context).getContentTreeControl();
/*    */   }
/*    */   
/*    */   protected String getLabel() {
/* 37 */     return this.treeControl.getLabel(this.node);
/*    */   }
/*    */   
/*    */   protected String getTargetFrame() {
/* 41 */     return "_top";
/*    */   }
/*    */   
/*    */   public Object onClick(Map params) {
/* 45 */     Object retValue = super.onClick(params);
/* 46 */     if (retValue == null) {
/* 47 */       this.treeElement.setExpanded(this.node, true);
/* 48 */       this.treeControl.setSelectedNode(this.node);
/*    */       
/* 50 */       retValue = MainPage.getInstance(this.context);
/*    */     } 
/* 52 */     return retValue;
/*    */   }
/*    */   
/*    */   protected void onReturnVCDialog(boolean result) {
/* 56 */     if (result) {
/* 57 */       this.treeElement.setExpanded(this.node, true);
/* 58 */       this.treeControl.setSelectedNode(this.node);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 64 */     final String code = super.getHtmlCode(params);
/* 65 */     if (this.node.equals(this.treeControl.getSelectedNode())) {
/* 66 */       return HtmlAnchorRenderer.getInstance().getHtmlCode("selectednode") + HtmlSpanRenderer.getInstance().getHtmlCode((HtmlSpanRenderer.Callback)new HtmlSpanRenderer.CallbackAdapter()
/*    */           {
/*    */             public String getClaZZ() {
/* 69 */               return "selectednode";
/*    */             }
/*    */             
/*    */             public String getContent() {
/* 73 */               return code;
/*    */             }
/*    */           });
/*    */     }
/* 77 */     return code;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\specialbrochures\toc\page\basic\LabelElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */