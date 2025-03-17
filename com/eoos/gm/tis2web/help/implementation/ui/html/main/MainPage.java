/*    */ package com.eoos.gm.tis2web.help.implementation.ui.html.main;
/*    */ 
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
/*    */ public class MainPage
/*    */   extends Page
/*    */ {
/*    */   protected MainHook hook;
/*    */   
/*    */   private MainPage(ClientContext context, Map parameters) {
/* 22 */     super(context);
/*    */     
/* 24 */     this.hook = new MainHook(context, parameters);
/* 25 */     addElement((HtmlElement)this.hook);
/*    */   }
/*    */ 
/*    */   
/*    */   public static MainPage getInstance(ClientContext context) {
/* 30 */     return (MainPage)context.getObject(MainPage.class);
/*    */   }
/*    */   
/*    */   public static MainPage constructInstance(ClientContext context, Map parameters) {
/* 34 */     MainPage instance = new MainPage(context, parameters);
/* 35 */     context.storeObject(MainPage.class, instance);
/* 36 */     return instance;
/*    */   }
/*    */   
/*    */   protected String getFormContent(Map params) {
/* 40 */     return this.hook.getHtmlCode(params);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\help\implementatio\\ui\html\main\MainPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */