/*    */ package com.eoos.gm.tis2web.feedback.implementation.ui.html.main;
/*    */ 
/*    */ import com.eoos.gm.tis2web.feedback.implementation.ui.html.home.FeedbackPanel;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ui.html.Page;
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
/*    */   protected FeedbackPanel homePanel;
/*    */   
/*    */   public MainPage(ClientContext context, String moduleType, Map moduleParams) {
/* 24 */     super(context);
/* 25 */     this.homePanel = FeedbackPanel.getInstance(context, moduleType, moduleParams);
/* 26 */     addElement((HtmlElement)this.homePanel);
/*    */   }
/*    */   
/*    */   protected String getFormContent(Map params) {
/* 30 */     return this.homePanel.getHtmlCode(params);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\feedback\implementatio\\ui\html\main\MainPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */