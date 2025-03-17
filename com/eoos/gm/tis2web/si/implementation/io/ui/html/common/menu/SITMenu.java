/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainPage;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
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
/*     */ public class SITMenu
/*     */   extends HtmlElementContainerBase
/*     */ {
/*     */   public static final String ID_PREFIX = "smenu";
/*     */   protected ModuleMenu moduleMenu;
/*     */   protected ClientContext context;
/*     */   protected BackLink linkBack;
/*     */   protected AddBookmarkLink linkAddBookmark;
/*     */   protected PrintViewLink linkPrintView;
/*     */   
/*     */   public SITMenu(ModuleMenu moduleMenu) {
/*  37 */     this.moduleMenu = moduleMenu;
/*  38 */     this.context = moduleMenu.getClientContext();
/*     */     
/*  40 */     final ModuleMenu.Connector connector = moduleMenu.getConnector();
/*     */     
/*  42 */     this.linkBack = new BackLink(this.context)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*  45 */           SITMenu.this.hookOnClick((HtmlElement)this);
/*  46 */           connector.back(submitParams);
/*  47 */           return MainPage.getInstance(SITMenu.this.context).getHtmlCode(submitParams);
/*     */         }
/*     */         
/*     */         public boolean isDisabled() {
/*  51 */           return (SITMenu.this.isDisabled((HtmlElement)this) || !connector.enableBack());
/*     */         }
/*     */       };
/*  54 */     addElement((HtmlElement)this.linkBack);
/*     */     
/*  56 */     this.linkAddBookmark = new AddBookmarkLink(this.context) {
/*     */         public SIO getSIO() {
/*  58 */           return connector.getSIO();
/*     */         }
/*     */         
/*     */         public Object onClick(Map params) {
/*  62 */           SITMenu.this.hookOnClick((HtmlElement)this);
/*  63 */           return super.onClick(params);
/*     */         }
/*     */         
/*     */         public boolean isDisabled() {
/*  67 */           return (SITMenu.this.isDisabled((HtmlElement)this) || super.isDisabled());
/*     */         }
/*     */       };
/*  70 */     addElement((HtmlElement)this.linkAddBookmark);
/*     */     
/*  72 */     this.linkPrintView = new PrintViewLink(this.context)
/*     */       {
/*     */         public String getCode() {
/*  75 */           return connector.getPageCode();
/*     */         }
/*     */         
/*     */         public Object onClick(Map params) {
/*  79 */           SITMenu.this.hookOnClick((HtmlElement)this);
/*  80 */           return super.onClick(params);
/*     */         }
/*     */         
/*     */         public boolean isDisabled() {
/*  84 */           return SITMenu.this.isDisabled((HtmlElement)this);
/*     */         }
/*     */       };
/*     */     
/*  88 */     addElement((HtmlElement)this.linkPrintView);
/*     */   }
/*     */   
/*     */   protected void hookOnClick(HtmlElement element) {
/*  92 */     this.moduleMenu.hookOnClick(element);
/*     */   }
/*     */   
/*     */   protected boolean isDisabled(HtmlElement element) {
/*  96 */     return this.moduleMenu.isDisabled(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 103 */     StringBuffer retValue = new StringBuffer("<table><tr>{LINK}</tr></table>");
/* 104 */     for (Iterator<HtmlElement> iter = this.elements.iterator(); iter.hasNext(); ) {
/* 105 */       HtmlElement element = iter.next();
/* 106 */       StringUtilities.replace(retValue, "{LINK}", "<td>" + element.getHtmlCode(params) + "</td>{LINK}");
/*     */     } 
/* 108 */     StringUtilities.replace(retValue, "{LINK}", "");
/* 109 */     return retValue.toString();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\common\menu\SITMenu.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */