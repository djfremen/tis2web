/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import com.eoos.html.element.IFrameElement;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DocumentIFramePanel
/*    */   extends HtmlElementContainerBase
/*    */ {
/* 19 */   private static String template = "<table width=\"100%\"><tr><td>{IFRAME}</td></tr></table>";
/*    */   
/*    */   private IFrameElement iframe;
/*    */ 
/*    */   
/*    */   public DocumentIFramePanel(ClientContext context) {
/* 25 */     this(context, new DocumentIFrame(context));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public DocumentIFramePanel(ClientContext context, IFrameElement iframe) {
/* 31 */     this.iframe = iframe;
/* 32 */     addElement((HtmlElement)this.iframe);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 37 */     StringBuffer code = new StringBuffer(template);
/*    */     
/* 39 */     StringUtilities.replace(code, "{IFRAME}", this.iframe.getHtmlCode(params));
/*    */     
/* 41 */     return code.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\DocumentIFramePanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */