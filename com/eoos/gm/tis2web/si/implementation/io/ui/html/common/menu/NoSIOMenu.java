/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu;
/*    */ 
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NoSIOMenu
/*    */   extends SITMenu
/*    */ {
/*    */   public NoSIOMenu(ModuleMenu moduleMenu) {
/* 17 */     super(moduleMenu);
/*    */   }
/*    */   
/*    */   public static synchronized NoSIOMenu getInstance(ModuleMenu moduleMenu) {
/* 21 */     NoSIOMenu instance = (NoSIOMenu)moduleMenu.getObject(NoSIOMenu.class);
/* 22 */     if (instance == null) {
/* 23 */       instance = new NoSIOMenu(moduleMenu);
/* 24 */       moduleMenu.storeObject(NoSIOMenu.class, instance);
/*    */     } 
/* 26 */     return instance;
/*    */   }
/*    */   
/*    */   protected boolean isDisabled(HtmlElement element) {
/* 30 */     if (this.linkBack == element) {
/* 31 */       return false;
/*    */     }
/* 33 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\common\menu\NoSIOMenu.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */