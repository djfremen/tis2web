/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.faultdiag.refdocs;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIO;
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
/*    */ 
/*    */ public class RefDocContainer
/*    */   extends HtmlElementContainerBase
/*    */ {
/*    */   RefDocIFrame wdIFrame;
/*    */   
/*    */   public RefDocContainer(ClientContext context, SIO node, HtmlElementStack stack) {
/* 26 */     this.wdIFrame = new RefDocIFrame(context, node);
/*    */   }
/*    */   
/*    */   public RefDocIFrame getRefDocFrame() {
/* 30 */     return this.wdIFrame;
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 34 */     StringBuffer code = new StringBuffer("<table width=\"100%\"><tr><td style=\"background-color: #FFFFFF\">{DOCUMENT}</td></tr></table>");
/*    */     
/* 36 */     StringUtilities.replace(code, "{DOCUMENT}", this.wdIFrame.getHtmlCode(params));
/* 37 */     return code.toString();
/*    */   }
/*    */   
/*    */   public void unregister() {
/* 41 */     this.wdIFrame.unregister();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\faultdiag\refdocs\RefDocContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */