/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.protocol;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ui.html.Page;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.History;
/*    */ import com.eoos.html.Dispatchable;
/*    */ import com.eoos.html.ResultObject;
/*    */ import com.eoos.html.element.HtmlElementContainer;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProtocolPage
/*    */   extends Page
/*    */ {
/*    */   private History history;
/*    */   private ClientContext context;
/*    */   
/*    */   public ProtocolPage(ClientContext context, History history) {
/* 25 */     super(context);
/* 26 */     this.history = history;
/* 27 */     this.context = context;
/* 28 */     history.addInputElements((HtmlElementContainer)this);
/*    */   }
/*    */   
/*    */   protected String getFormContent(Map params) {
/* 32 */     return this.history.getHtmlProtocol(this.context, params);
/*    */   }
/*    */   
/*    */   public String getURL(Map params) {
/* 36 */     String url = this.context.constructDispatchURL((Dispatchable)this, "getPage");
/* 37 */     return url;
/*    */   }
/*    */   
/*    */   public ResultObject getPage(Map params) {
/* 41 */     return new ResultObject(0, getHtmlCode(params));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getStyleSheetURL() {
/* 47 */     return "res/si/styles/cpr-style.css";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\cpr\protocol\ProtocolPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */