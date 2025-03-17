/*    */ package com.eoos.gm.tis2web.frame.implementation.fallback.ui.html.main;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.implementation.fallback.ui.html.home.HomePanel;
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
/*    */ 
/*    */ 
/*    */ public class MainHook
/*    */   extends HtmlElementHook
/*    */ {
/*    */   private HomePanel homePanel;
/*    */   
/*    */   public MainHook(ClientContext context) {
/* 23 */     this.homePanel = new HomePanel(context);
/*    */   }
/*    */   
/*    */   protected HtmlElement getActiveElement() {
/* 27 */     return (HtmlElement)this.homePanel;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\fallbac\\ui\html\main\MainHook.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */