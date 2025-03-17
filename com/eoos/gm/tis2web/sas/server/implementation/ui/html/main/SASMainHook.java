/*    */ package com.eoos.gm.tis2web.sas.server.implementation.ui.html.main;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.sas.server.implementation.ui.html.home.SASHomePanel;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementHook;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SASMainHook
/*    */   extends HtmlElementHook
/*    */ {
/*    */   private ClientContext context;
/*    */   
/*    */   public SASMainHook(ClientContext context) {
/* 21 */     this.context = context;
/*    */   }
/*    */   
/*    */   protected HtmlElement getActiveElement() {
/* 25 */     return (HtmlElement)SASHomePanel.getInstance(this.context);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\server\implementatio\\ui\html\main\SASMainHook.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */