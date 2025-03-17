/*    */ package com.eoos.gm.tis2web.si.implementation.statcont.ui.html.main;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.si.implementation.statcont.ui.html.home.HomePanel;
/*    */ import com.eoos.gm.tis2web.si.implementation.statcont.ui.html.panel.std.StandardPanel;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementHook;
/*    */ import com.eoos.html.element.input.UnhandledSubmit;
/*    */ import java.util.Map;
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
/*    */   implements UnhandledSubmit
/*    */ {
/*    */   private ClientContext context;
/*    */   
/*    */   private MainHook(ClientContext context) {
/* 25 */     this.context = context;
/*    */   }
/*    */   
/*    */   public static MainHook getInstance(ClientContext context) {
/* 29 */     synchronized (context.getLockObject()) {
/* 30 */       MainHook instance = (MainHook)context.getObject(MainHook.class);
/* 31 */       if (instance == null) {
/* 32 */         instance = new MainHook(context);
/* 33 */         context.storeObject(MainHook.class, instance);
/*    */       } 
/* 35 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   protected HtmlElement getActiveElement() {
/* 40 */     if (com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainHook.getInstance(this.context).getCurrentUI() == 1) {
/* 41 */       return (HtmlElement)StandardPanel.getInstance(this.context);
/*    */     }
/* 43 */     return (HtmlElement)HomePanel.getInstance(this.context);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object handleSubmit(Map params) {
/* 53 */     HtmlElement element = getActiveElement();
/* 54 */     if (element instanceof UnhandledSubmit) {
/* 55 */       return ((UnhandledSubmit)element).handleSubmit(params);
/*    */     }
/* 57 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\statcon\\ui\html\main\MainHook.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */