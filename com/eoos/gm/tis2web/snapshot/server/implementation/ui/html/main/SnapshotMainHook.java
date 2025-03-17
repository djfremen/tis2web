/*    */ package com.eoos.gm.tis2web.snapshot.server.implementation.ui.html.main;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.snapshot.server.implementation.ui.html.home.SnapshotHomePanel;
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
/*    */ public class SnapshotMainHook
/*    */   extends HtmlElementHook
/*    */ {
/*    */   private ClientContext context;
/*    */   
/*    */   public SnapshotMainHook(ClientContext context) {
/* 21 */     this.context = context;
/*    */   }
/*    */   
/*    */   protected HtmlElement getActiveElement() {
/* 25 */     return (HtmlElement)SnapshotHomePanel.getInstance(this.context);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\snapshot\server\implementatio\\ui\html\main\SnapshotMainHook.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */