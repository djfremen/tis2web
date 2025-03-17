/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.stdinfo;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu.ModuleMenu;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu.cpr.NodeChangeListener;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.History;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.page.DocumentPage;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.input.TimezoneOffsetInputElement;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class StandardInfoPanel
/*     */   extends HtmlElementContainerBase
/*     */ {
/*  32 */   private static Logger log = Logger.getLogger(StandardInfoPanel.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  37 */       template = ApplicationContext.getInstance().loadFile(StandardInfoPanel.class, "stdinfo.html", null).toString();
/*  38 */     } catch (Exception e) {
/*  39 */       log.error("unable to load template - error:" + e, e);
/*  40 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ClientContext context;
/*     */   
/*     */   protected ModuleMenu moduleMenue;
/*     */   
/*     */   private StdInfoContentHook contentHook;
/*     */   
/*     */   private CprBackUIIconLink linkBack;
/*     */   
/*     */   private TimezoneOffsetInputElement timezoneDiff;
/*  54 */   private Set nodeChangeListeners = new HashSet();
/*     */ 
/*     */   
/*     */   public StandardInfoPanel(ClientContext context) {
/*  58 */     this.context = context;
/*     */     
/*  60 */     final DocumentPage page = DocumentPage.getInstance(context);
/*     */     
/*  62 */     ModuleMenu.Connector connector = new ModuleMenu.Connector() {
/*     */         public SIO getSIO() {
/*     */           try {
/*  65 */             return page.getSIO();
/*  66 */           } catch (NullPointerException e) {
/*  67 */             return null;
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean enableBack() {
/*  73 */           return true;
/*     */         }
/*     */         
/*     */         public void back(Map submitParams) {
/*  77 */           SIO sio = getSIO();
/*  78 */           if (sio instanceof com.eoos.gm.tis2web.si.service.cai.SIOCPR && StandardInfoPanel.this.linkBack.show()) {
/*  79 */             StandardInfoPanel.this.linkBack.onClick(submitParams);
/*     */           } else {
/*  81 */             page.onBack();
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*     */         public String getPageCode() {
/*  87 */           return page.getHtmlCode(null);
/*     */         }
/*     */         
/*     */         public boolean enableNavToggle() {
/*  91 */           return true;
/*     */         }
/*     */         
/*     */         public void toggleNavigation() {
/*  95 */           StandardInfoPanel.this.toggleNavigationMode();
/*     */         }
/*     */ 
/*     */         
/*     */         public void registerNodeChangeListener(NodeChangeListener listener) {
/* 100 */           StandardInfoPanel.this.nodeChangeListeners.add(listener);
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 105 */     this.moduleMenue = new ModuleMenu(context, connector);
/* 106 */     addElement((HtmlElement)this.moduleMenue);
/*     */     
/* 108 */     this.contentHook = new StdInfoContentHook(context);
/* 109 */     addElement((HtmlElement)this.contentHook);
/*     */     
/* 111 */     this.linkBack = new CprBackUIIconLink(context);
/* 112 */     addElement((HtmlElement)this.linkBack);
/* 113 */     this.nodeChangeListeners.add(this.linkBack);
/*     */     
/* 115 */     this.timezoneDiff = new TimezoneOffsetInputElement(context.createID());
/* 116 */     addElement((HtmlElement)this.timezoneDiff);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ClientContext getContext() {
/* 121 */     return this.context;
/*     */   }
/*     */   
/*     */   public static StandardInfoPanel getInstance(ClientContext context) {
/* 125 */     synchronized (context.getLockObject()) {
/* 126 */       StandardInfoPanel instance = (StandardInfoPanel)context.getObject(StandardInfoPanel.class);
/* 127 */       if (instance == null) {
/* 128 */         instance = new StandardInfoPanel(context);
/* 129 */         context.storeObject(StandardInfoPanel.class, instance);
/*     */       } 
/* 131 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 136 */     StringBuffer code = new StringBuffer(template);
/*     */     
/* 138 */     StringUtilities.replace(code, "{MODULE_MENU}", this.moduleMenue.getHtmlCode(params));
/* 139 */     StringUtilities.replace(code, "{CONTENT_HOOK}", this.contentHook.getHtmlCode(params));
/* 140 */     StringUtilities.replace(code, "{timezoneDiff}", this.timezoneDiff.getHtmlCode(params));
/*     */     
/* 142 */     return code.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public void toggleNavigationMode() {
/* 147 */     this.contentHook.toggleView();
/*     */   }
/*     */   
/*     */   public void setNode(Object node, History history) {
/* 151 */     for (Iterator<NodeChangeListener> iter = this.nodeChangeListeners.iterator(); iter.hasNext(); ) {
/* 152 */       NodeChangeListener listener = iter.next();
/* 153 */       listener.onSetNode(node, history);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetNodes() {
/* 162 */     for (Iterator<NodeChangeListener> iter = this.nodeChangeListeners.iterator(); iter.hasNext(); ) {
/* 163 */       NodeChangeListener listener = iter.next();
/* 164 */       listener.onResetNodes();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearNodes() {
/* 171 */     for (Iterator<NodeChangeListener> iter = this.nodeChangeListeners.iterator(); iter.hasNext(); ) {
/* 172 */       NodeChangeListener listener = iter.next();
/* 173 */       listener.onClearNodes();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\stdinfo\StandardInfoPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */