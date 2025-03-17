/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.textsearch;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu.ModuleMenu;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu.cpr.NodeChangeListener;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.page.DocumentPage;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VehicleCfgStorage;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.HtmlElementHook;
/*     */ import com.eoos.html.element.input.UnhandledSubmit;
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
/*     */ public class TextSearchPanel
/*     */   extends HtmlElementContainerBase
/*     */   implements UnhandledSubmit
/*     */ {
/*  29 */   private static Logger log = Logger.getLogger(TextSearchPanel.class); private static String template; private MyHook hook;
/*     */   private ClientContext context;
/*     */   
/*     */   static {
/*     */     try {
/*  34 */       template = ApplicationContext.getInstance().loadFile(TextSearchPanel.class, "textsearch.html", null).toString();
/*  35 */     } catch (Exception e) {
/*  36 */       log.error("unable to load template - error:" + e, e);
/*  37 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */   protected ModuleMenu moduleMenu; public static final int SEARCH_INPUT = 0;
/*     */   public static final int DOCUMENT_VIEW = 1;
/*     */   
/*     */   private class MyHook extends HtmlElementHook implements UnhandledSubmit { public SearchInputPanel searchInputPanel;
/*     */     public DocumentViewPanel documentViewPanel;
/*     */     
/*     */     public MyHook() {
/*  47 */       this.searchInputPanel = new SearchInputPanel(TextSearchPanel.this.context);
/*  48 */       addElement((HtmlElement)this.searchInputPanel);
/*     */       
/*  50 */       this.documentViewPanel = new DocumentViewPanel(TextSearchPanel.this.context);
/*  51 */       addElement((HtmlElement)this.documentViewPanel);
/*     */     }
/*     */     
/*     */     protected HtmlElement getActiveElement() {
/*  55 */       switch (TextSearchPanel.this.currentView) {
/*     */         case 0:
/*  57 */           return (HtmlElement)this.searchInputPanel;
/*     */         case 1:
/*  59 */           return (HtmlElement)this.documentViewPanel;
/*     */       } 
/*  61 */       throw new IllegalArgumentException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object handleSubmit(Map params) {
/*  71 */       if (TextSearchPanel.this.currentView == 0) {
/*  72 */         return this.searchInputPanel.handleSubmit(params);
/*     */       }
/*  74 */       return null;
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
/*  89 */   private int currentView = 0;
/*     */ 
/*     */   
/*     */   private TextSearchPanel(ClientContext context) {
/*  93 */     this.context = context;
/*     */     
/*  95 */     this.hook = new MyHook();
/*  96 */     addElement((HtmlElement)this.hook);
/*     */     
/*  98 */     final DocumentPage page = this.hook.documentViewPanel.getDocumentPage();
/*     */     
/* 100 */     ModuleMenu.Connector connector = new ModuleMenu.Connector() {
/*     */         public SIO getSIO() {
/* 102 */           if (TextSearchPanel.this.currentView == 1) {
/*     */             try {
/* 104 */               return page.getSIO();
/* 105 */             } catch (NullPointerException e) {}
/*     */           }
/*     */           
/* 108 */           return null;
/*     */         }
/*     */         
/*     */         public boolean enableBack() {
/* 112 */           return (TextSearchPanel.this.currentView == 1);
/*     */         }
/*     */         
/*     */         public void back(Map params) {
/* 116 */           if (!page.onBack()) {
/* 117 */             TextSearchPanel.this.currentView = 0;
/*     */           }
/*     */         }
/*     */         
/*     */         public String getPageCode() {
/* 122 */           return page.getHtmlCode(null);
/*     */         }
/*     */         
/*     */         public boolean enableNavToggle() {
/* 126 */           return false;
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
/* 138 */     this.moduleMenu = new ModuleMenu(context, connector) {
/*     */         protected void hookOnClick(HtmlElement element) {
/* 140 */           if (element == this.linkTextSearch) {
/* 141 */             TextSearchPanel.this.currentView = 0;
/*     */           }
/*     */         }
/*     */       };
/*     */     
/* 146 */     addElement((HtmlElement)this.moduleMenu);
/*     */     
/* 148 */     VCFacade.getInstance(context).addObserver(new VehicleCfgStorage.Observer()
/*     */         {
/*     */           public void onVehicleConfigurationChange() {
/* 151 */             TextSearchPanel.this.currentView = 0;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static TextSearchPanel getInstance(ClientContext context) {
/* 159 */     synchronized (context.getLockObject()) {
/* 160 */       TextSearchPanel instance = (TextSearchPanel)context.getObject(TextSearchPanel.class);
/* 161 */       if (instance == null) {
/* 162 */         instance = new TextSearchPanel(context);
/* 163 */         context.storeObject(TextSearchPanel.class, instance);
/*     */       } 
/* 165 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public DocumentPage getDocumentPage() {
/* 170 */     return this.hook.documentViewPanel.getDocumentPage();
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 174 */     StringBuffer code = new StringBuffer(template);
/* 175 */     StringUtilities.replace(code, "{MODULE_MENU}", this.moduleMenu.getHtmlCode(params));
/*     */     
/* 177 */     StringUtilities.replace(code, "{ACTIVE_ELEMENT}", this.hook.getHtmlCode(params));
/* 178 */     return code.toString();
/*     */   }
/*     */   
/*     */   public void showDocument(Object node) {
/* 182 */     this.hook.documentViewPanel.setPage(node, null);
/* 183 */     this.currentView = 1;
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


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\textsearch\TextSearchPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */