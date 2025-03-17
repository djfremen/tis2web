/*    */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.toc;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.main.MainPage;
/*    */ import com.eoos.html.element.input.LinkElement;
/*    */ import com.eoos.html.renderer.HtmlAnchorRenderer;
/*    */ import com.eoos.html.renderer.HtmlSpanRenderer;
/*    */ import com.eoos.scsm.v2.util.Util;
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
/*    */ public class DefaultLabelElement
/*    */   extends LinkElement
/*    */ {
/*    */   protected TocTreeElement treeElement;
/*    */   protected Object node;
/*    */   
/*    */   public DefaultLabelElement(TocTreeElement treeElement, Object node) {
/* 29 */     super(treeElement.getContext().createID(), null);
/* 30 */     this.treeElement = treeElement;
/* 31 */     this.node = node;
/*    */   }
/*    */   
/*    */   protected String getLabel() {
/* 35 */     StringBuffer retValue = new StringBuffer();
/* 36 */     CTOCNode elem = (CTOCNode)this.node;
/* 37 */     String oNr = (String)elem.getProperty((SITOCProperty)CTOCProperty.LT);
/* 38 */     if (oNr != null && oNr.startsWith("pc=")) {
/* 39 */       retValue.append(oNr.substring(3) + " " + this.treeElement.getTreeControl().getLabel(this.node));
/*    */     } else {
/* 41 */       retValue.append(this.treeElement.getTreeControl().getLabel(this.node));
/*    */     } 
/* 43 */     Util.escapeReservedHTMLChars(retValue);
/* 44 */     return retValue.toString();
/*    */   }
/*    */   
/*    */   protected String getTargetFrame() {
/* 48 */     return "_top";
/*    */   }
/*    */   
/*    */   public Object onClick(Map params) {
/* 52 */     this.treeElement.getTreeControl().setSelectedNode(this.node);
/* 53 */     this.treeElement.getTreeControl().toggleExpanded(this.node);
/*    */     
/* 55 */     return MainPage.getInstance(this.treeElement.getContext());
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 59 */     final String code = super.getHtmlCode(params);
/* 60 */     if (this.node.equals(this.treeElement.getTreeControl().getSelectedNode())) {
/* 61 */       return HtmlAnchorRenderer.getInstance().getHtmlCode("selectednode") + HtmlSpanRenderer.getInstance().getHtmlCode((HtmlSpanRenderer.Callback)new HtmlSpanRenderer.CallbackAdapter() {
/*    */             public String getClaZZ() {
/* 63 */               return "selectednode";
/*    */             }
/*    */             
/*    */             public String getContent() {
/* 67 */               return code;
/*    */             }
/*    */           });
/*    */     }
/* 71 */     return code;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void getAdditionalAttributes(Map<String, String> map) {
/* 76 */     if (((CTOCNode)this.node).hasProperty((SITOCProperty)CTOCProperty.ChangeFlag))
/* 77 */       map.put("class", "ltnew"); 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\toc\DefaultLabelElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */