/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu.BackLink;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu.ModuleMenu;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainPage;
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
/*    */ 
/*    */ 
/*    */ public class FaultDiagMenu
/*    */   extends ModuleMenu
/*    */ {
/*    */   protected BackLink linkBack;
/*    */   
/*    */   public FaultDiagMenu(ClientContext context, ModuleMenu.Connector connector) {
/* 25 */     super(context, connector);
/* 26 */     this.linkBack = new BackLink(context)
/*    */       {
/*    */         public Object onClick(Map submitParams) {
/* 29 */           FaultDiagMenu.this.connector.back(submitParams);
/* 30 */           return MainPage.getInstance(FaultDiagMenu.this.context).getHtmlCode(submitParams);
/*    */         }
/*    */         
/*    */         public boolean isDisabled() {
/* 34 */           return (FaultDiagMenu.this.connector.getSIO() != null || !FaultDiagMenu.this.connector.enableBack());
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 43 */     removeElement((HtmlElement)this.linkBack);
/* 44 */     if (this.connector.getSIO() == null && this.connector.enableBack()) {
/* 45 */       addElement((HtmlElement)this.linkBack);
/*    */     }
/* 47 */     return super.getHtmlCode(params);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\faultdiag\FaultDiagMenu.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */