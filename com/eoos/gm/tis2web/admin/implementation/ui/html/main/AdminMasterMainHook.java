/*    */ package com.eoos.gm.tis2web.admin.implementation.ui.html.main;
/*    */ 
/*    */ import com.eoos.gm.tis2web.admin.implementation.ui.html.home.AdminMasterHomePanel;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
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
/*    */ public class AdminMasterMainHook
/*    */   extends HtmlElementHook
/*    */ {
/*    */   private ClientContext context;
/*    */   
/*    */   public AdminMasterMainHook(ClientContext context) {
/* 21 */     this.context = context;
/*    */   }
/*    */   
/*    */   protected HtmlElement getActiveElement() {
/* 25 */     return (HtmlElement)AdminMasterHomePanel.getInstance(this.context);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\admin\implementatio\\ui\html\main\AdminMasterMainHook.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */