/*     */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.common.gtwo.menu;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.ltview.ltlist.page.LTListPage;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.main.MainPage;
/*     */ import com.eoos.gm.tis2web.lt.service.cai.SIOLT;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SITMenu
/*     */   extends HtmlElementContainerBase
/*     */ {
/*     */   public static final String ID_PREFIX = "smenu";
/*     */   protected ModuleMenu moduleMenu;
/*     */   protected ClientContext context;
/*     */   protected BackLink linkBack;
/*     */   protected AddBookmarkLink linkAddBookmark;
/*     */   protected PrintViewLink linkPrintView;
/*     */   protected ICLLink linkICL;
/*     */   
/*     */   private SITMenu(ModuleMenu moduleMenu) {
/*  40 */     this.moduleMenu = moduleMenu;
/*  41 */     this.context = moduleMenu.getClientContext();
/*     */     
/*  43 */     final ModuleMenu.Connector connector = moduleMenu.getConnector();
/*     */     
/*  45 */     this.linkBack = new BackLink(this.context)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*  48 */           SITMenu.this.hookOnClick((HtmlElement)this);
/*  49 */           connector.back();
/*  50 */           return MainPage.getInstance(SITMenu.this.context).getHtmlCode(submitParams);
/*     */         }
/*     */         
/*     */         public boolean isDisabled() {
/*  54 */           return (SITMenu.this.isDisabled((HtmlElement)this) || !connector.enableBack());
/*     */         }
/*     */       };
/*  57 */     addElement((HtmlElement)this.linkBack);
/*     */     
/*  59 */     this.linkAddBookmark = new AddBookmarkLink(this.context) {
/*     */         public SIOLT getSIOLTElement() {
/*  61 */           return connector.getSIOLTElement();
/*     */         }
/*     */         
/*     */         public Object onClick(Map params) {
/*  65 */           SITMenu.this.hookOnClick((HtmlElement)this);
/*  66 */           return super.onClick(params);
/*     */         }
/*     */         
/*     */         public boolean isDisabled() {
/*  70 */           return (SITMenu.this.isDisabled((HtmlElement)this) || super.isDisabled());
/*     */         }
/*     */       };
/*  73 */     addElement((HtmlElement)this.linkAddBookmark);
/*     */     
/*  75 */     this.linkPrintView = new PrintViewLink(this.context)
/*     */       {
/*     */         public String getCode() {
/*  78 */           return connector.getPageCode();
/*     */         }
/*     */         
/*     */         public Object onClick(Map params) {
/*  82 */           SITMenu.this.hookOnClick((HtmlElement)this);
/*  83 */           return super.onClick(params);
/*     */         }
/*     */         
/*     */         public boolean isDisabled() {
/*  87 */           return SITMenu.this.isDisabled((HtmlElement)this);
/*     */         }
/*     */       };
/*     */     
/*  91 */     addElement((HtmlElement)this.linkPrintView);
/*     */     
/*  93 */     this.linkICL = new ICLLink(this.context, connector.getLTListPage()) {
/*     */         public Object onClick(Map params) {
/*  95 */           SITMenu.this.hookOnClick((HtmlElement)this);
/*  96 */           return super.onClick(params);
/*     */         }
/*     */         
/*     */         public boolean isDisabled() {
/* 100 */           return (SITMenu.this.isDisabled((HtmlElement)this) || super.isDisabled());
/*     */         }
/*     */       };
/* 103 */     addElement((HtmlElement)this.linkICL);
/*     */   }
/*     */   
/*     */   public static synchronized SITMenu getInstance(ModuleMenu moduleMenu) {
/* 107 */     SITMenu instance = (SITMenu)moduleMenu.getObject(SITMenu.class);
/* 108 */     if (instance == null) {
/* 109 */       instance = new SITMenu(moduleMenu);
/* 110 */       moduleMenu.storeObject(SITMenu.class, instance);
/*     */     } 
/* 112 */     return instance;
/*     */   }
/*     */   
/*     */   protected void hookOnClick(HtmlElement element) {
/* 116 */     this.moduleMenu.hookOnClick(element);
/*     */   }
/*     */   
/*     */   protected boolean isDisabled(HtmlElement element) {
/* 120 */     return this.moduleMenu.isDisabled(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 127 */     StringBuffer retValue = new StringBuffer("<table><tr>{LINK}</tr></table>");
/* 128 */     for (Iterator<HtmlElement> iter = this.elements.iterator(); iter.hasNext(); ) {
/* 129 */       HtmlElement element = iter.next();
/* 130 */       StringUtilities.replace(retValue, "{LINK}", "<td>" + element.getHtmlCode(params) + "</td>{LINK}");
/*     */     } 
/* 132 */     StringUtilities.replace(retValue, "{LINK}", "");
/* 133 */     return retValue.toString();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\common\gtwo\menu\SITMenu.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */