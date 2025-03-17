/*    */ package com.eoos.gm.tis2web.techlineprint.server.implementation.ui.html.main;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.techlineprint.server.implementation.ui.html.home.TechlinePrintHomePanel;
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
/*    */ public class TechlinePrintMainHook
/*    */   extends HtmlElementHook
/*    */ {
/*    */   private ClientContext context;
/*    */   
/*    */   public TechlinePrintMainHook(ClientContext context) {
/* 21 */     this.context = context;
/*    */   }
/*    */   
/*    */   protected HtmlElement getActiveElement() {
/* 25 */     return (HtmlElement)TechlinePrintHomePanel.getInstance(this.context);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\techlineprint\server\implementatio\\ui\html\main\TechlinePrintMainHook.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */