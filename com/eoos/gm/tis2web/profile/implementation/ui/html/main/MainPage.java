/*    */ package com.eoos.gm.tis2web.profile.implementation.ui.html.main;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ui.html.Page;
/*    */ import com.eoos.gm.tis2web.profile.implementation.ui.html.home.HomePanel;
/*    */ import com.eoos.gm.tis2web.profile.service.ProfileService;
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
/*    */ public class MainPage
/*    */   extends Page
/*    */ {
/*    */   private HomePanel homePanel;
/*    */   
/*    */   public MainPage(ClientContext context, ProfileService.ReturnHandler handler) {
/* 25 */     super(context);
/* 26 */     this.homePanel = new HomePanel(this, context, handler);
/* 27 */     addElement((HtmlElement)this.homePanel);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getFormContent(Map params) {
/* 32 */     return this.homePanel.getHtmlCode(params);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\profile\implementatio\\ui\html\main\MainPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */