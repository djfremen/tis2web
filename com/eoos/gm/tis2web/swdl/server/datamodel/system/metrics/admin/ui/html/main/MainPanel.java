/*    */ package com.eoos.gm.tis2web.swdl.server.datamodel.system.metrics.admin.ui.html.main;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.swdl.server.datamodel.system.metrics.admin.ui.html.search.input.SearchInputPanel;
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
/* 16 */     this.elementStack = new HtmlElementStack();
/* 17 */     addElement((HtmlElement)this.elementStack);
/*    */     
/* 19 */     this.elementStack.push((HtmlElement)new SearchInputPanel(context));
/*    */   }
/*    */ 
/*    */   
/*    */   public static MainPanel getInstance(ClientContext context) {
/* 24 */     synchronized (context.getLockObject()) {
/* 25 */       MainPanel instance = (MainPanel)context.getObject(MainPanel.class);
/* 26 */       if (instance == null) {
/* 27 */         instance = new MainPanel(context);
/* 28 */         context.storeObject(MainPanel.class, instance);
/*    */       } 
/* 30 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 35 */     return this.elementStack.getHtmlCode(params);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\server\datamodel\system\metrics\admi\\ui\html\main\MainPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */