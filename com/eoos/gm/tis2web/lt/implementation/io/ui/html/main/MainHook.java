/*    */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.main;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.ModuleContext;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.bookmarks.BookmarkPanel;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.stdinfo.StandardInfoPanel;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.textsearch.TextSearchPanel;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MainHook
/*    */   extends HtmlElementHook
/*    */ {
/*    */   public static final int STANDARD_INFO = 1;
/*    */   public static final int TEXT_SEARCH = 2;
/*    */   public static final int BOOKMARKS = 3;
/* 28 */   private int ui = 1;
/*    */   
/*    */   private ClientContext context;
/*    */ 
/*    */   
/*    */   private MainHook(ClientContext context) {
/* 34 */     this.context = context;
/*    */   }
/*    */   
/*    */   public static MainHook getInstance(ClientContext context) {
/* 38 */     synchronized (context.getLockObject()) {
/* 39 */       MainHook instance = (MainHook)context.getObject(MainHook.class);
/* 40 */       if (instance == null) {
/* 41 */         instance = new MainHook(context);
/* 42 */         context.storeObject(MainHook.class, instance);
/*    */       } 
/* 44 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   protected HtmlElement getActiveElement() {
/* 49 */     switch (this.ui) {
/*    */       
/*    */       case 1:
/* 52 */         return (HtmlElement)StandardInfoPanel.getInstance(this.context);
/*    */       case 2:
/* 54 */         return (HtmlElement)TextSearchPanel.getInstance(this.context);
/*    */       case 3:
/* 56 */         return (HtmlElement)BookmarkPanel.getInstance(this.context);
/*    */     } 
/* 58 */     throw new IllegalArgumentException();
/*    */   }
/*    */ 
/*    */   
/*    */   public void switchUI(int ui) {
/* 63 */     this.ui = ui;
/*    */ 
/*    */     
/* 66 */     String pageid = null;
/* 67 */     switch (this.ui) {
/*    */       case 1:
/* 69 */         pageid = "LT_DISPLAY";
/*    */         break;
/*    */       case 2:
/* 72 */         pageid = "LT_TEXT_SEARCH";
/*    */         break;
/*    */       case 3:
/* 75 */         pageid = "LT_BOOKMARK";
/*    */         break;
/*    */     } 
/*    */     
/* 79 */     ModuleContext.getInstance(this.context.getSessionID()).setPageIdentifier(pageid);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\main\MainHook.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */