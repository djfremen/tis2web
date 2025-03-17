/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu.lu;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu.ModuleMenu;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu.SITMenu;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LUMenu
/*    */   extends SITMenu
/*    */ {
/*    */   protected PageInfoLink linkPageInfo;
/*    */   
/*    */   public LUMenu(ModuleMenu moduleMenu) {
/* 18 */     super(moduleMenu);
/*    */     
/* 20 */     final ModuleMenu.Connector connector = moduleMenu.getConnector();
/*    */     
/* 22 */     this.linkPageInfo = new PageInfoLink(this.context) {
/*    */         public SIO getSIO() {
/* 24 */           return connector.getSIO();
/*    */         }
/*    */         
/*    */         public boolean isDisabled() {
/* 28 */           return (LUMenu.this.isDisabled((HtmlElement)this) || getSIO() == null);
/*    */         }
/*    */       };
/* 31 */     addElement((HtmlElement)this.linkPageInfo);
/*    */   }
/*    */   
/*    */   public static synchronized LUMenu getInstance(ModuleMenu moduleMenu) {
/* 35 */     LUMenu instance = (LUMenu)moduleMenu.getObject(LUMenu.class);
/* 36 */     if (instance == null) {
/* 37 */       instance = new LUMenu(moduleMenu);
/* 38 */       moduleMenu.storeObject(LUMenu.class, instance);
/*    */     } 
/* 40 */     return instance;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\common\menu\lu\LUMenu.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */