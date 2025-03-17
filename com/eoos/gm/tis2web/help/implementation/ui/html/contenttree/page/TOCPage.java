/*    */ package com.eoos.gm.tis2web.help.implementation.ui.html.contenttree.page;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ui.html.Page;
/*    */ import com.eoos.gm.tis2web.help.implementation.ui.html.contenttree.panel.TOCPanel;
/*    */ import com.eoos.gm.tis2web.help.implementation.ui.html.document.page.DocumentPage;
/*    */ import com.eoos.html.Dispatchable;
/*    */ import com.eoos.html.ResultObject;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TOCPage
/*    */   extends Page
/*    */ {
/*    */   protected TOCPanel tocPanel;
/*    */   
/*    */   public TOCPage(ClientContext context, Map parameter, DocumentPage documentPage) {
/* 25 */     super(context);
/*    */     
/* 27 */     this.tocPanel = new TOCPanel(context, parameter, documentPage);
/* 28 */     addElement((HtmlElement)this.tocPanel);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getURL(Map params) {
/* 33 */     String url = this.context.constructDispatchURL((Dispatchable)this, "getPage");
/* 34 */     String requestedBookmark = (params != null) ? (String)params.get("bm") : null;
/* 35 */     if (requestedBookmark != null && containesBookmark(requestedBookmark)) {
/* 36 */       url = url + "#" + requestedBookmark;
/*    */     } else {
/* 38 */       url = url + "#selectednode";
/*    */     } 
/* 40 */     return url;
/*    */   }
/*    */   
/*    */   public ResultObject getPage(Map params) {
/* 44 */     synchronized (this.context.getLockObject()) {
/* 45 */       return new ResultObject(0, getHtmlCode(params));
/*    */     } 
/*    */   }
/*    */   
/*    */   protected String getFormContent(Map params) {
/* 50 */     return this.tocPanel.getHtmlCode(params);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\help\implementatio\\ui\html\contenttree\page\TOCPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */