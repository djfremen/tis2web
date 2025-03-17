/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.numbersearch;
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
/*     */ public class NumberSearchPanel
/*     */   extends HtmlElementContainerBase
/*     */ {
/*     */   private static String template;
/*     */   private MyHook hook;
/*  29 */   private static Logger log = Logger.getLogger(NumberSearchPanel.class);
/*     */   private ClientContext context;
/*     */   
/*     */   static {
/*     */     try {
/*  34 */       template = ApplicationContext.getInstance().loadFile(NumberSearchPanel.class, "numbersearch.html", null).toString();
/*  35 */     } catch (Exception e) {
/*  36 */       log.error("unable to load template - error:" + e, e);
/*  37 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected ModuleMenu moduleMenu;
/*     */   public static final int SEARCH_INPUT = 0;
/*     */   public static final int DOCUMENT_VIEW = 1;
/*     */   
/*     */   private class MyHook extends HtmlElementHook { public SearchInputPanel searchInputPanel;
/*     */     
/*     */     public MyHook() {
/*  48 */       this.searchInputPanel = new SearchInputPanel(NumberSearchPanel.this.context);
/*  49 */       addElement((HtmlElement)this.searchInputPanel);
/*     */       
/*  51 */       this.documentViewPanel = new DocumentViewPanel(NumberSearchPanel.this.context);
/*  52 */       addElement((HtmlElement)this.documentViewPanel);
/*     */     }
/*     */     public DocumentViewPanel documentViewPanel;
/*     */     protected HtmlElement getActiveElement() {
/*  56 */       switch (NumberSearchPanel.this.currentView) {
/*     */         case 0:
/*  58 */           return (HtmlElement)this.searchInputPanel;
/*     */         case 1:
/*  60 */           return (HtmlElement)this.documentViewPanel;
/*     */       } 
/*  62 */       throw new IllegalArgumentException();
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
/*  77 */   private int currentView = 0;
/*     */ 
/*     */   
/*     */   private NumberSearchPanel(ClientContext context) {
/*  81 */     this.context = context;
/*     */     
/*  83 */     this.hook = new MyHook();
/*  84 */     addElement((HtmlElement)this.hook);
/*     */     
/*  86 */     final DocumentPage page = this.hook.documentViewPanel.getDocumentPage();
/*     */     
/*  88 */     ModuleMenu.Connector connector = new ModuleMenu.Connector() {
/*     */         public SIO getSIO() {
/*  90 */           if (NumberSearchPanel.this.currentView == 1) {
/*     */             try {
/*  92 */               return page.getSIO();
/*  93 */             } catch (NullPointerException e) {}
/*     */           }
/*     */           
/*  96 */           return null;
/*     */         }
/*     */         
/*     */         public boolean enableBack() {
/* 100 */           return (NumberSearchPanel.this.currentView == 1);
/*     */         }
/*     */         
/*     */         public void back(Map params) {
/* 104 */           if (!page.onBack()) {
/* 105 */             NumberSearchPanel.this.currentView = 0;
/*     */           }
/*     */         }
/*     */         
/*     */         public String getPageCode() {
/* 110 */           return page.getHtmlCode(null);
/*     */         }
/*     */         
/*     */         public boolean enableNavToggle() {
/* 114 */           return false;
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
/* 126 */     this.moduleMenu = new ModuleMenu(context, connector) {
/*     */         protected void hookOnClick(HtmlElement element) {
/* 128 */           if (element == this.linkNumberSearch) {
/* 129 */             NumberSearchPanel.this.currentView = 0;
/*     */           }
/*     */         }
/*     */       };
/* 133 */     addElement((HtmlElement)this.moduleMenu);
/* 134 */     VCFacade.getInstance(context).addObserver(new VehicleCfgStorage.Observer()
/*     */         {
/*     */           public void onVehicleConfigurationChange() {
/* 137 */             NumberSearchPanel.this.currentView = 0;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static NumberSearchPanel getInstance(ClientContext context) {
/* 145 */     synchronized (context.getLockObject()) {
/* 146 */       NumberSearchPanel instance = (NumberSearchPanel)context.getObject(NumberSearchPanel.class);
/* 147 */       if (instance == null) {
/* 148 */         instance = new NumberSearchPanel(context);
/* 149 */         context.storeObject(NumberSearchPanel.class, instance);
/*     */       } 
/* 151 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public DocumentPage getDocumentPage() {
/* 156 */     return this.hook.documentViewPanel.getDocumentPage();
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 160 */     StringBuffer code = new StringBuffer(template);
/* 161 */     StringUtilities.replace(code, "{MODULE_MENU}", this.moduleMenu.getHtmlCode(params));
/*     */     
/* 163 */     StringUtilities.replace(code, "{ACTIVE_ELEMENT}", this.hook.getHtmlCode(params));
/* 164 */     return code.toString();
/*     */   }
/*     */   
/*     */   protected void showDocument(Object node) {
/* 168 */     this.hook.documentViewPanel.setPage(node, null);
/* 169 */     this.currentView = 1;
/*     */   }
/*     */   
/*     */   public Object searchDocument(String number) {
/* 173 */     return this.hook.searchInputPanel.searchDocument(number);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\numbersearch\NumberSearchPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */