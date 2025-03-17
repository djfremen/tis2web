/*    */ package com.eoos.gm.tis2web.tech2view.server.implementation.ui.html.main;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.tech2view.server.implementation.ui.html.home.Tech2ViewHomePanel;
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
/*    */ public class Tech2ViewMainHook
/*    */   extends HtmlElementHook
/*    */ {
/*    */   private ClientContext context;
/*    */   
/*    */   public Tech2ViewMainHook(ClientContext context) {
/* 21 */     this.context = context;
/*    */   }
/*    */   
/*    */   protected HtmlElement getActiveElement() {
/* 25 */     return (HtmlElement)Tech2ViewHomePanel.getInstance(this.context);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\tech2view\server\implementatio\\ui\html\main\Tech2ViewMainHook.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */