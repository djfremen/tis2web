/*    */ package com.eoos.gm.tis2web.frame.dls.server.admin;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import com.eoos.html.element.HtmlElementStack;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class MainPanelSWK
/*    */   extends HtmlElementContainerBase
/*    */ {
/*    */   private HtmlElementStack elementStack;
/*    */   
/*    */   private MainPanelSWK(ClientContext context) {
/* 15 */     this.elementStack = new HtmlElementStack();
/* 16 */     addElement((HtmlElement)this.elementStack);
/*    */     
/* 18 */     this.elementStack.push((HtmlElement)new SearchInputPanelSWK(context));
/*    */   }
/*    */ 
/*    */   
/*    */   public static MainPanelSWK getInstance(ClientContext context) {
/* 23 */     synchronized (context.getLockObject()) {
/* 24 */       MainPanelSWK instance = (MainPanelSWK)context.getObject(MainPanelSWK.class);
/* 25 */       if (instance == null) {
/* 26 */         instance = new MainPanelSWK(context);
/* 27 */         context.storeObject(MainPanelSWK.class, instance);
/*    */       } 
/* 29 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 34 */     return this.elementStack.getHtmlCode(params);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dls\server\admin\MainPanelSWK.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */