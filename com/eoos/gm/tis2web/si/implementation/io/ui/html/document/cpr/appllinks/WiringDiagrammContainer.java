/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.appllinks;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ui.html.Page;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu.BackLink;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.page.DocumentPage;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import com.eoos.html.element.HtmlElementStack;
/*    */ import com.eoos.util.StringUtilities;
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
/*    */ public class WiringDiagrammContainer
/*    */   extends HtmlElementContainerBase
/*    */ {
/*    */   private WiringDiagrammPage page;
/*    */   private WdIFrame wdIFrame;
/*    */   private BackLink backLink;
/*    */   private DocumentPage wdIFramelPage;
/*    */   
/*    */   public WiringDiagrammContainer(ClientContext context, SIO node, HtmlElementStack stack, WiringDiagrammPage page) {
/* 32 */     this.page = page;
/* 33 */     this.wdIFramelPage = new DocumentPage(context) {
/*    */         public Page getMainPage() {
/* 35 */           return (Page)WiringDiagrammContainer.this.page;
/*    */         }
/*    */       };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 44 */     this.wdIFrame = new WdIFrame(context, node, this.wdIFramelPage);
/* 45 */     this.backLink = new BackLink(context) {
/*    */         public boolean isDisabled() {
/* 47 */           return false;
/*    */         }
/*    */         
/*    */         public Object onClick(Map submitParams) {
/* 51 */           WiringDiagrammContainer.this.wdIFramelPage.onBack();
/* 52 */           return WiringDiagrammContainer.this.page;
/*    */         }
/*    */       };
/*    */     
/* 56 */     addElement((HtmlElement)this.backLink);
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 60 */     StringBuffer code = new StringBuffer("<table width=\"100%\"><tr><td align=\"center\">{backLink}</td></tr><tr><td style=\"background-color: #FFFFFF\">{DOCUMENT}</td></tr></table>");
/*    */     
/* 62 */     StringUtilities.replace(code, "{DOCUMENT}", this.wdIFrame.getHtmlCode(params));
/* 63 */     StringUtilities.replace(code, "{backLink}", this.backLink.getHtmlCode(params));
/* 64 */     return code.toString();
/*    */   }
/*    */   
/*    */   public void unregister() {
/* 68 */     this.wdIFrame.unregister();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\cpr\appllinks\WiringDiagrammContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */