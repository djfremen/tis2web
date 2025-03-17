/*    */ package com.eoos.gm.tis2web.frame.msg.admin.ui;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import com.eoos.html.element.HtmlElementStack;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class MainPanel
/*    */   extends HtmlElementContainerBase
/*    */ {
/*    */   private HtmlElementStack elementStack;
/*    */   
/*    */   private MainPanel(ClientContext context) {
/* 15 */     this.elementStack = new HtmlElementStack();
/* 16 */     addElement((HtmlElement)this.elementStack);
/*    */     
/* 18 */     this.elementStack.push((HtmlElement)new SearchInputPanel(context));
/*    */   }
/*    */ 
/*    */   
/*    */   public static MainPanel getInstance(ClientContext context) {
/* 23 */     synchronized (context.getLockObject()) {
/* 24 */       MainPanel instance = (MainPanel)context.getObject(MainPanel.class);
/* 25 */       if (instance == null) {
/* 26 */         instance = new MainPanel(context);
/* 27 */         context.storeObject(MainPanel.class, instance);
/*    */       } 
/* 29 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 34 */     return this.elementStack.getHtmlCode(params);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\msg\admi\\ui\MainPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */