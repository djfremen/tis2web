/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.bookmarks;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu.ModuleMenu;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu.cpr.NodeChangeListener;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.page.DocumentPage;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.HtmlElementHook;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public class BookmarkPanel
/*     */   extends HtmlElementContainerBase
/*     */ {
/*  28 */   private static Logger log = Logger.getLogger(BookmarkPanel.class); private static String template; private MyHook hook;
/*     */   private ClientContext context;
/*     */   
/*     */   static {
/*     */     try {
/*  33 */       template = ApplicationContext.getInstance().loadFile(BookmarkPanel.class, "bookmarkpanel.html", null).toString();
/*  34 */     } catch (Exception e) {
/*  35 */       log.error("unable to load template - error:" + e, e);
/*  36 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */   protected ModuleMenu moduleMenu; public static final int BOOKMARK_LIST = 0;
/*     */   public static final int DOCUMENT_VIEW = 1;
/*     */   
/*     */   private class MyHook extends HtmlElementHook { public BookmarkListPanel bookmarkListPanel;
/*     */     public DocumentViewPanel documentViewPanel;
/*     */     
/*     */     public MyHook() {
/*  46 */       this.bookmarkListPanel = new BookmarkListPanel(BookmarkPanel.this.context);
/*  47 */       addElement((HtmlElement)this.bookmarkListPanel);
/*     */       
/*  49 */       this.documentViewPanel = new DocumentViewPanel(BookmarkPanel.this.context);
/*  50 */       addElement((HtmlElement)this.documentViewPanel);
/*     */     }
/*     */     
/*     */     protected HtmlElement getActiveElement() {
/*  54 */       switch (BookmarkPanel.this.currentView) {
/*     */         case 0:
/*  56 */           return (HtmlElement)this.bookmarkListPanel;
/*     */         case 1:
/*  58 */           return (HtmlElement)this.documentViewPanel;
/*     */       } 
/*  60 */       throw new IllegalArgumentException();
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
/*  75 */   private int currentView = 0;
/*     */ 
/*     */   
/*     */   private BookmarkPanel(ClientContext context) {
/*  79 */     this.context = context;
/*     */     
/*  81 */     this.hook = new MyHook();
/*  82 */     addElement((HtmlElement)this.hook);
/*     */     
/*  84 */     final DocumentPage page = this.hook.documentViewPanel.getDocumentPage();
/*     */     
/*  86 */     ModuleMenu.Connector connector = new ModuleMenu.Connector() {
/*     */         public SIO getSIO() {
/*  88 */           if (BookmarkPanel.this.currentView == 1) {
/*     */             try {
/*  90 */               return page.getSIO();
/*  91 */             } catch (NullPointerException e) {}
/*     */           }
/*     */           
/*  94 */           return null;
/*     */         }
/*     */         
/*     */         public boolean enableBack() {
/*  98 */           return (BookmarkPanel.this.currentView == 1);
/*     */         }
/*     */         
/*     */         public void back(Map params) {
/* 102 */           BookmarkPanel.this.currentView = 0;
/*     */         }
/*     */         
/*     */         public String getPageCode() {
/* 106 */           return page.getHtmlCode(null);
/*     */         }
/*     */         
/*     */         public boolean enableNavToggle() {
/* 110 */           return false;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public void toggleNavigation() {}
/*     */ 
/*     */ 
/*     */         
/*     */         public void registerNodeChangeListener(NodeChangeListener listener) {}
/*     */       };
/* 122 */     this.moduleMenu = new ModuleMenu(context, connector) {
/*     */         protected void hookOnClick(HtmlElement element) {
/* 124 */           if (element == this.linkBookmarks) {
/* 125 */             BookmarkPanel.this.currentView = 0;
/*     */           }
/*     */         }
/*     */       };
/* 129 */     addElement((HtmlElement)this.moduleMenu);
/*     */   }
/*     */ 
/*     */   
/*     */   public static BookmarkPanel getInstance(ClientContext context) {
/* 134 */     synchronized (context.getLockObject()) {
/* 135 */       BookmarkPanel instance = (BookmarkPanel)context.getObject(BookmarkPanel.class);
/* 136 */       if (instance == null) {
/* 137 */         instance = new BookmarkPanel(context);
/* 138 */         context.storeObject(BookmarkPanel.class, instance);
/*     */       } 
/* 140 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public DocumentPage getDocumentPage() {
/* 145 */     return this.hook.documentViewPanel.getDocumentPage();
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 149 */     StringBuffer code = new StringBuffer(template);
/* 150 */     StringUtilities.replace(code, "{MODULE_MENU}", this.moduleMenu.getHtmlCode(params));
/*     */     
/* 152 */     StringUtilities.replace(code, "{ACTIVE_ELEMENT}", this.hook.getHtmlCode(params));
/* 153 */     return code.toString();
/*     */   }
/*     */   
/*     */   protected void showDocument(Object node) {
/* 157 */     this.hook.documentViewPanel.setPage(node, null);
/* 158 */     this.currentView = 1;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\bookmarks\BookmarkPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */