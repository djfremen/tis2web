/*     */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.textsearch;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.common.gtwo.menu.ModuleMenu;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.ltview.ltlist.page.LTListPage;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.textsearch.resultlist.SearchResultPanel;
/*     */ import com.eoos.gm.tis2web.lt.service.cai.SIOLT;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.HtmlElementHook;
/*     */ import com.eoos.html.element.input.UnhandledSubmit;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TextSearchPanel
/*     */   extends HtmlElementContainerBase
/*     */   implements UnhandledSubmit
/*     */ {
/*  28 */   private static Logger log = Logger.getLogger(TextSearchPanel.class); private static String template; private MyHook hook;
/*     */   private ClientContext context;
/*     */   
/*     */   static {
/*     */     try {
/*  33 */       template = ApplicationContext.getInstance().loadFile(TextSearchPanel.class, "textsearch.html", null).toString();
/*  34 */     } catch (Exception e) {
/*  35 */       log.error("unable to load template - error:" + e, e);
/*  36 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected ModuleMenu ieMenu;
/*     */   public static final int SEARCH_INPUT = 0;
/*     */   public static final int DOCUMENT_VIEW = 1;
/*     */   public static final int RESULT_VIEW = 2;
/*     */   
/*     */   private class MyHook extends HtmlElementHook implements UnhandledSubmit { public SearchInputPanel searchInputPanel;
/*     */     
/*     */     public MyHook() {
/*  48 */       this.searchInputPanel = new SearchInputPanel(TextSearchPanel.this.context);
/*  49 */       addElement((HtmlElement)this.searchInputPanel);
/*     */       
/*  51 */       this.documentViewPanel = new DocumentViewPanel(TextSearchPanel.this.context);
/*  52 */       addElement((HtmlElement)this.documentViewPanel);
/*     */       
/*  54 */       this.searchResultPanel = new SearchResultPanel(TextSearchPanel.this.context);
/*  55 */       addElement((HtmlElement)this.searchResultPanel);
/*     */     }
/*     */     public DocumentViewPanel documentViewPanel; public SearchResultPanel searchResultPanel;
/*     */     protected HtmlElement getActiveElement() {
/*  59 */       switch (TextSearchPanel.this.currentView) {
/*     */         case 0:
/*  61 */           return (HtmlElement)this.searchInputPanel;
/*     */         case 1:
/*  63 */           return (HtmlElement)this.documentViewPanel;
/*     */         case 2:
/*  65 */           return (HtmlElement)this.searchResultPanel;
/*     */       } 
/*  67 */       throw new IllegalArgumentException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object handleSubmit(Map params) {
/*  77 */       if (TextSearchPanel.this.currentView == 0) {
/*  78 */         return this.searchInputPanel.handleSubmit(params);
/*     */       }
/*  80 */       return null;
/*     */     } }
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
/*  97 */   private int currentView = 0;
/*     */ 
/*     */   
/*     */   private TextSearchPanel(ClientContext context) {
/* 101 */     this.context = context;
/* 102 */     this.hook = new MyHook();
/* 103 */     addElement((HtmlElement)this.hook);
/*     */     
/* 105 */     ModuleMenu.Connector connector = new ModuleMenu.Connector() {
/*     */         public SIOLT getSIOLTElement() {
/* 107 */           SIOLT retValue = null;
/*     */           try {
/* 109 */             if (TextSearchPanel.this.currentView != 0) {
/* 110 */               retValue = TextSearchPanel.this.hook.documentViewPanel.getListPage().getCurrentElement();
/*     */             }
/* 112 */           } catch (Exception e) {
/* 113 */             TextSearchPanel.log.warn("unable to retrieve sio, returning null - exception: " + e, e);
/*     */           } 
/* 115 */           return retValue;
/*     */         }
/*     */         
/*     */         public boolean enableBack() {
/* 119 */           return (TextSearchPanel.this.currentView != 0);
/*     */         }
/*     */         
/*     */         public void back() {
/* 123 */           if (TextSearchPanel.this.currentView == 1) {
/* 124 */             TextSearchPanel.this.currentView = 2;
/* 125 */           } else if (TextSearchPanel.this.currentView == 2) {
/* 126 */             TextSearchPanel.this.currentView = 0;
/*     */           } 
/*     */         }
/*     */         
/*     */         public String getPageCode() {
/* 131 */           return TextSearchPanel.this.hook.documentViewPanel.getListPage().getHtmlCode(null);
/*     */         }
/*     */         
/*     */         public boolean enableNavToggle() {
/* 135 */           return false;
/*     */         }
/*     */ 
/*     */         
/*     */         public void toggleNavigation() {}
/*     */         
/*     */         public LTListPage getLTListPage() {
/* 142 */           return TextSearchPanel.this.hook.documentViewPanel.getListPage();
/*     */         }
/*     */       };
/*     */     
/* 146 */     this.ieMenu = new ModuleMenu(context, connector) {
/*     */         protected void hookOnClick(HtmlElement element) {
/* 148 */           if (element == this.linkTextSearch) {
/* 149 */             TextSearchPanel.this.currentView = 0;
/*     */           }
/*     */         }
/*     */       };
/* 153 */     addElement((HtmlElement)this.ieMenu);
/*     */   }
/*     */ 
/*     */   
/*     */   public static TextSearchPanel getInstance(ClientContext context) {
/* 158 */     synchronized (context.getLockObject()) {
/* 159 */       TextSearchPanel instance = (TextSearchPanel)context.getObject(TextSearchPanel.class);
/* 160 */       if (instance == null) {
/* 161 */         instance = new TextSearchPanel(context);
/* 162 */         context.storeObject(TextSearchPanel.class, instance);
/*     */       } 
/* 164 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 169 */     StringBuffer code = new StringBuffer(template);
/* 170 */     StringUtilities.replace(code, "{MODULE_MENU}", this.ieMenu.getHtmlCode(params));
/*     */     
/* 172 */     StringUtilities.replace(code, "{ACTIVE_ELEMENT}", this.hook.getHtmlCode(params));
/* 173 */     return code.toString();
/*     */   }
/*     */   
/*     */   public void showDocument(Object node) {
/* 177 */     this.hook.documentViewPanel.setPage(node, null);
/* 178 */     this.currentView = 1;
/*     */   }
/*     */   
/*     */   public void showSearchResult(List elementList) {
/* 182 */     this.hook.searchResultPanel.setResultList(elementList);
/* 183 */     this.currentView = 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object handleSubmit(Map params) {
/* 192 */     return this.hook.handleSubmit(params);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\textsearch\TextSearchPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */