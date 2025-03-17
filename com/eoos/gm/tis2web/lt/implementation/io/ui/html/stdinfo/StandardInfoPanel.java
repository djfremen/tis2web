/*     */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.stdinfo;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTClientContext;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.common.gtwo.menu.ModuleMenu;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.ltview.ltlist.page.LTListPage;
/*     */ import com.eoos.gm.tis2web.lt.service.cai.SIOLT;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
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
/*     */ public class StandardInfoPanel
/*     */   extends HtmlElementContainerBase
/*     */ {
/*  24 */   private static Logger log = Logger.getLogger(StandardInfoPanel.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  29 */       template = ApplicationContext.getInstance().loadFile(StandardInfoPanel.class, "stdinfo.html", null).toString();
/*  30 */     } catch (Exception e) {
/*  31 */       log.error("unable to load template - error:" + e, e);
/*  32 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private StdInfoContentHook contentHook;
/*     */   
/*     */   private ClientContext context;
/*     */   
/*     */   protected ModuleMenu ieMenu;
/*     */   
/*     */   private NOVCPanel novc;
/*     */   
/*     */   public StandardInfoPanel(final ClientContext context) {
/*  46 */     this.context = context;
/*     */     
/*  48 */     ModuleMenu.Connector connector = new ModuleMenu.Connector() {
/*     */         public SIOLT getSIOLTElement() {
/*  50 */           SIOLT retValue = null;
/*     */           try {
/*  52 */             retValue = LTListPage.getInstance(context).getCurrentElement();
/*  53 */           } catch (Exception e) {
/*  54 */             StandardInfoPanel.log.warn("unable to retrieve sio, returning null - exception:" + e, e);
/*     */           } 
/*     */           
/*  57 */           return retValue;
/*     */         }
/*     */         
/*     */         public boolean enableBack() {
/*  61 */           return false;
/*     */         }
/*     */ 
/*     */         
/*     */         public void back() {}
/*     */         
/*     */         public String getPageCode() {
/*  68 */           return LTListPage.getInstance(context).getHtmlCode(null);
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean enableNavToggle() {
/*  73 */           return true;
/*     */         }
/*     */         
/*     */         public void toggleNavigation() {
/*  77 */           StandardInfoPanel.this.toggleNavigationMode();
/*     */         }
/*     */         
/*     */         public LTListPage getLTListPage() {
/*  81 */           return LTListPage.getInstance(context);
/*     */         }
/*     */       };
/*     */     
/*  85 */     this.ieMenu = new ModuleMenu(context, connector);
/*  86 */     addElement((HtmlElement)this.ieMenu);
/*     */     
/*  88 */     this.contentHook = new StdInfoContentHook(context);
/*  89 */     addElement((HtmlElement)this.contentHook);
/*     */     
/*  91 */     this.novc = new NOVCPanel(context);
/*  92 */     addElement((HtmlElement)this.novc);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ClientContext getContext() {
/*  97 */     return this.context;
/*     */   }
/*     */   
/*     */   public static StandardInfoPanel getInstance(ClientContext context) {
/* 101 */     synchronized (context.getLockObject()) {
/* 102 */       StandardInfoPanel instance = (StandardInfoPanel)context.getObject(StandardInfoPanel.class);
/* 103 */       if (instance == null) {
/* 104 */         instance = new StandardInfoPanel(context);
/* 105 */         context.storeObject(StandardInfoPanel.class, instance);
/*     */       } 
/* 107 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public synchronized String getHtmlCode(Map params) {
/* 112 */     if (LTClientContext.getInstance(this.context).getModelCode().intValue() == -1) {
/* 113 */       return this.novc.getHtmlCode(params);
/*     */     }
/* 115 */     StringBuffer code = new StringBuffer(template);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 120 */     StringUtilities.replace(code, "{MODULE_MENU}", this.ieMenu.getHtmlCode(params));
/*     */     
/* 122 */     StringUtilities.replace(code, "{CONTENT_HOOK}", this.contentHook.getHtmlCode(params));
/*     */     
/* 124 */     return code.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void toggleNavigationMode() {
/* 136 */     this.contentHook.toggleView();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\stdinfo\StandardInfoPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */