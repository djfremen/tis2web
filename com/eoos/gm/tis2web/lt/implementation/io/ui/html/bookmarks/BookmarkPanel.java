/*     */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.bookmarks;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.common.gtwo.menu.ModuleMenu;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.ltview.ltlist.page.LTListPage;
/*     */ import com.eoos.gm.tis2web.lt.service.cai.SIOLT;
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
/*     */ public class BookmarkPanel
/*     */   extends HtmlElementContainerBase
/*     */ {
/*  23 */   private static Logger log = Logger.getLogger(BookmarkPanel.class); private static String template; private MyHook hook;
/*     */   private ClientContext context;
/*     */   
/*     */   static {
/*     */     try {
/*  28 */       template = ApplicationContext.getInstance().loadFile(BookmarkPanel.class, "bookmarkpanel.html", null).toString();
/*  29 */     } catch (Exception e) {
/*  30 */       log.error("unable to load template - error:" + e, e);
/*  31 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */   protected ModuleMenu ieMenu; public static final int BOOKMARK_LIST = 0;
/*     */   public static final int DOCUMENT_VIEW = 1;
/*     */   
/*     */   private class MyHook extends HtmlElementHook { public BookmarkListPanel bookmarkListPanel;
/*     */     public DocumentViewPanel documentViewPanel;
/*     */     
/*     */     public MyHook() {
/*  41 */       this.bookmarkListPanel = new BookmarkListPanel(BookmarkPanel.this.context);
/*  42 */       addElement((HtmlElement)this.bookmarkListPanel);
/*     */       
/*  44 */       this.documentViewPanel = new DocumentViewPanel(BookmarkPanel.this.context);
/*  45 */       addElement((HtmlElement)this.documentViewPanel);
/*     */     }
/*     */     
/*     */     protected HtmlElement getActiveElement() {
/*  49 */       switch (BookmarkPanel.this.currentView) {
/*     */         case 0:
/*  51 */           return (HtmlElement)this.bookmarkListPanel;
/*     */         case 1:
/*  53 */           return (HtmlElement)this.documentViewPanel;
/*     */       } 
/*  55 */       throw new IllegalArgumentException();
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
/*  70 */   private int currentView = 0;
/*     */ 
/*     */   
/*     */   private BookmarkPanel(ClientContext context) {
/*  74 */     this.context = context;
/*     */     
/*  76 */     ModuleMenu.Connector connector = new ModuleMenu.Connector() {
/*     */         public SIOLT getSIOLTElement() {
/*  78 */           SIOLT retValue = null;
/*     */           try {
/*  80 */             if (BookmarkPanel.this.currentView == 1) {
/*  81 */               return BookmarkPanel.this.hook.documentViewPanel.getLTListPage().getCurrentElement();
/*     */             }
/*  83 */           } catch (Exception e) {
/*  84 */             BookmarkPanel.log.warn("unable to retieve sio, returning null - exception: " + e, e);
/*     */           } 
/*     */           
/*  87 */           return retValue;
/*     */         }
/*     */         
/*     */         public boolean enableBack() {
/*  91 */           return (BookmarkPanel.this.currentView == 1);
/*     */         }
/*     */         
/*     */         public void back() {
/*  95 */           BookmarkPanel.this.currentView = 0;
/*     */         }
/*     */         
/*     */         public String getPageCode() {
/*  99 */           return BookmarkPanel.this.hook.documentViewPanel.getLTListPage().getHtmlCode(null);
/*     */         }
/*     */         
/*     */         public boolean enableNavToggle() {
/* 103 */           return false;
/*     */         }
/*     */ 
/*     */         
/*     */         public void toggleNavigation() {}
/*     */ 
/*     */         
/*     */         public LTListPage getLTListPage() {
/* 111 */           return null;
/*     */         }
/*     */       };
/*     */     
/* 115 */     this.ieMenu = new ModuleMenu(context, connector) {
/*     */         protected void hookOnClick(HtmlElement element) {
/* 117 */           if (element == this.linkBookmarks) {
/* 118 */             BookmarkPanel.this.currentView = 0;
/*     */           }
/*     */         }
/*     */       };
/*     */     
/* 123 */     addElement((HtmlElement)this.ieMenu);
/*     */     
/* 125 */     this.hook = new MyHook();
/* 126 */     addElement((HtmlElement)this.hook);
/*     */   }
/*     */ 
/*     */   
/*     */   public static BookmarkPanel getInstance(ClientContext context) {
/* 131 */     synchronized (context.getLockObject()) {
/* 132 */       BookmarkPanel instance = (BookmarkPanel)context.getObject(BookmarkPanel.class);
/* 133 */       if (instance == null) {
/* 134 */         instance = new BookmarkPanel(context);
/* 135 */         context.storeObject(BookmarkPanel.class, instance);
/*     */       } 
/* 137 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 142 */     StringBuffer code = new StringBuffer(template);
/* 143 */     StringUtilities.replace(code, "{MODULE_MENU}", this.ieMenu.getHtmlCode(params));
/*     */     
/* 145 */     StringUtilities.replace(code, "{ACTIVE_ELEMENT}", this.hook.getHtmlCode(params));
/* 146 */     return code.toString();
/*     */   }
/*     */   
/*     */   protected void showDocument(Object node) {
/* 150 */     this.hook.documentViewPanel.setPage(node, null);
/* 151 */     this.currentView = 1;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\bookmarks\BookmarkPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */