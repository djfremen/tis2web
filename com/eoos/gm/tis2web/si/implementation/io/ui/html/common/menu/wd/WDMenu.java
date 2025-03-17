/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu.wd;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu.ModuleMenu;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu.SITMenu;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu.lu.PageInfoLink;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WDMenu
/*    */   extends SITMenu
/*    */ {
/*    */   private CPRLink linkCPR;
/*    */   protected PageInfoLink linkPageInfo;
/*    */   
/*    */   public WDMenu(ModuleMenu moduleMenu) {
/* 21 */     super(moduleMenu);
/*    */     
/* 23 */     final ModuleMenu.Connector connector = moduleMenu.getConnector();
/* 24 */     this.linkCPR = new CPRLink(this.context) {
/*    */         public SIO getSIO() {
/* 26 */           return connector.getSIO();
/*    */         }
/*    */         
/*    */         public boolean isDisabled() {
/* 30 */           return (WDMenu.this.isDisabled((HtmlElement)this) || getSIO() == null || super.isDisabled());
/*    */         }
/*    */       };
/* 33 */     addElement((HtmlElement)this.linkCPR);
/* 34 */     this.linkPageInfo = new PageInfoLink(this.context) {
/*    */         public SIO getSIO() {
/* 36 */           return connector.getSIO();
/*    */         }
/*    */         
/*    */         public boolean isDisabled() {
/* 40 */           return (WDMenu.this.isDisabled((HtmlElement)this) || getSIO() == null);
/*    */         }
/*    */       };
/* 43 */     addElement((HtmlElement)this.linkPageInfo);
/*    */   }
/*    */ 
/*    */   
/*    */   public static synchronized WDMenu getInstance(ModuleMenu moduleMenu) {
/* 48 */     WDMenu instance = (WDMenu)moduleMenu.getObject(WDMenu.class);
/* 49 */     if (instance == null) {
/* 50 */       instance = new WDMenu(moduleMenu);
/* 51 */       moduleMenu.storeObject(WDMenu.class, instance);
/*    */     } 
/* 53 */     return instance;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\common\menu\wd\WDMenu.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */