/*    */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.toc.page;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ui.html.Page;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.toc.TocPanel;
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
/*    */ public class TocPage
/*    */   extends Page
/*    */ {
/*    */   private TocPanel tocPanel;
/*    */   
/*    */   public TocPage(ClientContext context, int tocMode) {
/* 24 */     super(context);
/*    */     
/* 26 */     this.tocPanel = new TocPanel(context, tocMode);
/* 27 */     addElement((HtmlElement)this.tocPanel);
/*    */   }
/*    */   
/*    */   public String getURL(Map params) {
/* 31 */     String url = this.context.constructDispatchURL((Dispatchable)this, "getPage");
/* 32 */     url = url + "#selectednode";
/* 33 */     return url;
/*    */   }
/*    */   
/*    */   public ResultObject getPage(Map params) {
/* 37 */     synchronized (this.context.getLockObject()) {
/* 38 */       return new ResultObject(0, getHtmlCode(params));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getFormContent(Map params) {
/* 44 */     return this.tocPanel.getHtmlCode(params);
/*    */   }
/*    */   
/*    */   protected boolean allowTextFieldSubmit() {
/* 48 */     return false;
/*    */   }
/*    */   
/*    */   protected Object onUnhandledSubmit(Map params) {
/* 52 */     return this.tocPanel.onUnhandledSubmit(params);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\toc\page\TocPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */