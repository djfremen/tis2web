/*    */ package com.eoos.gm.tis2web.frame.export.common.ui.html.menu;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.renderer.menu.TopTabMenuRenderer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MenuRenderer
/*    */   extends TopTabMenuRenderer
/*    */ {
/*    */   public MenuRenderer(ClientContext context) {}
/*    */   
/*    */   public static MenuRenderer getInstance(ClientContext context) {
/* 19 */     synchronized (context.getLockObject()) {
/* 20 */       MenuRenderer instance = (MenuRenderer)context.getObject(MenuRenderer.class);
/* 21 */       if (instance == null) {
/* 22 */         instance = new MenuRenderer(context);
/* 23 */         context.storeObject(MenuRenderer.class, instance);
/*    */       } 
/* 25 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   protected String getImageSrc(String name) {
/* 30 */     return "pic/common/" + name;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\ui\html\menu\MenuRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */