/*    */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.toc;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.statistic.TopHitMainWorks;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.main.MainPage;
/*    */ import com.eoos.gm.tis2web.lt.service.cai.SIOLT;
/*    */ import com.eoos.html.element.input.LinkElement;
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
/*    */ public class DefaultLeafLabelElement
/*    */   extends LinkElement
/*    */ {
/*    */   protected TocTreeElement treeElement;
/*    */   protected Object node;
/*    */   
/*    */   public DefaultLeafLabelElement(TocTreeElement treeElement, Object node) {
/* 30 */     super(treeElement.determineID(node), null);
/* 31 */     this.treeElement = treeElement;
/* 32 */     this.node = node;
/*    */   }
/*    */   
/*    */   protected String getLabel() {
/* 36 */     return this.treeElement.getTreeControl().getLabel(this.node);
/*    */   }
/*    */   
/*    */   protected String getTargetFrame() {
/* 40 */     return "_top";
/*    */   }
/*    */   
/*    */   protected void getAdditionalAttributes(Map<String, String> map) {
/* 44 */     if (((CTOCNode)this.node).hasProperty((SITOCProperty)CTOCProperty.ChangeFlag)) {
/* 45 */       map.put("class", "ltnew");
/*    */     }
/*    */   }
/*    */   
/*    */   public Object onClick(Map params) {
/* 50 */     this.treeElement.getTreeControl().setSelectedNode(this.node);
/* 51 */     this.treeElement.getTreeControl().setExpanded(this.node, true);
/*    */     
/* 53 */     if (this.node instanceof SIOLT) {
/* 54 */       TopHitMainWorks.getInstance().hit((SIOLT)this.node);
/*    */     }
/*    */     
/* 57 */     return MainPage.getInstance(this.treeElement.getContext());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 63 */     final String code = super.getHtmlCode(params);
/*    */     
/* 65 */     if (isDisabled())
/*    */     {
/* 67 */       return HtmlSpanRenderer.getInstance().getHtmlCode((HtmlSpanRenderer.Callback)new HtmlSpanRenderer.CallbackAdapter()
/*    */           {
/*    */             protected String getClaZZ() {
/* 70 */               return "ltgray";
/*    */             }
/*    */             
/*    */             public String getContent() {
/* 74 */               return code;
/*    */             }
/*    */           });
/*    */     }
/*    */     
/* 79 */     if (this.node.equals(this.treeElement.getTreeControl().getSelectedNode())) {
/* 80 */       return HtmlAnchorRenderer.getInstance().getHtmlCode("selectednode") + HtmlSpanRenderer.getInstance().getHtmlCode((HtmlSpanRenderer.Callback)new HtmlSpanRenderer.CallbackAdapter()
/*    */           {
/*    */             public String getClaZZ() {
/* 83 */               return "selectednode";
/*    */             }
/*    */             
/*    */             public String getContent() {
/* 87 */               return code;
/*    */             }
/*    */           });
/*    */     }
/*    */     
/* 92 */     return code;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\toc\DefaultLeafLabelElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */