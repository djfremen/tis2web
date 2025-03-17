/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu.cpr;
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
/*    */ public class CPRMenu
/*    */   extends SITMenu
/*    */ {
/*    */   protected WiringLink linkWiring;
/*    */   protected LTLink linkLT;
/*    */   protected OptionsUIIconLink linkOptions;
/*    */   protected ProtocolUIIconLink linkProtocol;
/*    */   protected PageInfoLink linkPageInfo;
/*    */   
/*    */   public CPRMenu(ModuleMenu moduleMenu) {
/* 24 */     super(moduleMenu);
/*    */     
/* 26 */     final ModuleMenu.Connector connector = moduleMenu.getConnector();
/*    */     
/* 28 */     this.linkWiring = new WiringLink(this.context) {
/*    */         public SIO getSIO() {
/* 30 */           return connector.getSIO();
/*    */         }
/*    */         
/*    */         public boolean isDisabled() {
/* 34 */           return (CPRMenu.this.isDisabled((HtmlElement)this) || super.isDisabled());
/*    */         }
/*    */       };
/* 37 */     addElement((HtmlElement)this.linkWiring);
/*    */     
/* 39 */     this.linkLT = new LTLink(this.context) {
/*    */         public SIO getSIO() {
/* 41 */           return connector.getSIO();
/*    */         }
/*    */         
/*    */         public boolean isDisabled() {
/* 45 */           return (CPRMenu.this.isDisabled((HtmlElement)this) || super.isDisabled());
/*    */         }
/*    */       };
/*    */     
/* 49 */     addElement((HtmlElement)this.linkLT);
/*    */     
/* 51 */     this.linkOptions = new OptionsUIIconLink(this.context) {
/*    */         public boolean isDisabled() {
/* 53 */           return (CPRMenu.this.isDisabled((HtmlElement)this) || super.isDisabled());
/*    */         }
/*    */       };
/* 56 */     addElement((HtmlElement)this.linkOptions);
/* 57 */     connector.registerNodeChangeListener(this.linkOptions);
/*    */     
/* 59 */     this.linkProtocol = new ProtocolUIIconLink(this.context) {
/*    */         public boolean isDisabled() {
/* 61 */           return (CPRMenu.this.isDisabled((HtmlElement)this) || super.isDisabled());
/*    */         }
/*    */       };
/* 64 */     addElement((HtmlElement)this.linkProtocol);
/* 65 */     connector.registerNodeChangeListener(this.linkProtocol);
/* 66 */     this.linkPageInfo = new PageInfoLink(this.context) {
/*    */         public SIO getSIO() {
/* 68 */           return connector.getSIO();
/*    */         }
/*    */         
/*    */         public boolean isDisabled() {
/* 72 */           return (CPRMenu.this.isDisabled((HtmlElement)this) || getSIO() == null);
/*    */         }
/*    */       };
/* 75 */     addElement((HtmlElement)this.linkPageInfo);
/*    */   }
/*    */   
/*    */   public static synchronized CPRMenu getInstance(ModuleMenu moduleMenu) {
/* 79 */     CPRMenu instance = (CPRMenu)moduleMenu.getObject(CPRMenu.class);
/* 80 */     if (instance == null) {
/* 81 */       instance = new CPRMenu(moduleMenu);
/* 82 */       moduleMenu.storeObject(CPRMenu.class, instance);
/*    */     } 
/* 84 */     return instance;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\common\menu\cpr\CPRMenu.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */