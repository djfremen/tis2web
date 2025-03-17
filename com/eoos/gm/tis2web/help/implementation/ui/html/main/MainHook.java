/*    */ package com.eoos.gm.tis2web.help.implementation.ui.html.main;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.help.implementation.ui.html.home.HomePanel;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementHook;
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
/*    */ public class MainHook
/*    */   extends HtmlElementHook
/*    */ {
/*    */   private HomePanel homePanel;
/*    */   
/*    */   public MainHook(ClientContext context, Map parameter) {
/* 25 */     this.homePanel = new HomePanel(context, parameter);
/*    */   }
/*    */   
/*    */   protected HtmlElement getActiveElement() {
/* 29 */     return (HtmlElement)this.homePanel;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\help\implementatio\\ui\html\main\MainHook.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */