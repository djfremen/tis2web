/*     */ package com.eoos.gm.tis2web.frame.implementation.admin.caches.ui.html.main;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.CacheAdapter;
/*     */ import com.eoos.gm.tis2web.frame.implementation.admin.caches.ASServiceImpl_Caches;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.util.v2.StringUtilities;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class MainPanel
/*     */   extends HtmlElementContainerBase {
/*  22 */   private static final Logger log = Logger.getLogger(MainPanel.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  27 */       template = ApplicationContext.getInstance().loadFile(MainPanel.class, "mainpanel.html", null).toString();
/*  28 */     } catch (Exception e) {
/*  29 */       log.error("error loading template - error:" + e, e);
/*  30 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ClientContext context;
/*  36 */   private LinkedList adapters = new LinkedList();
/*     */   
/*  38 */   private HashMap adapterToButton = new HashMap<Object, Object>();
/*     */   
/*  40 */   private String statusMessage = null;
/*     */   private static final String ROW_TEMPLATE = "<tr><td>{DESC}</td><td>{BUTTON}</td></tr>{ROWS}";
/*     */   
/*     */   private MainPanel(ClientContext context) {
/*  44 */     this.context = context;
/*     */     
/*  46 */     log.debug("initializing...");
/*     */     try {
/*  48 */       Configuration configuration = ASServiceImpl_Caches.getInstance().getConfiguration();
/*  49 */       String keyPrefix = "cache.adapter.";
/*  50 */       SubConfigurationWrapper subConfigurationWrapper = new SubConfigurationWrapper(configuration, "cache.adapter.");
/*  51 */       LinkedList<Comparable> keys = new LinkedList(subConfigurationWrapper.getKeys());
/*  52 */       Collections.sort(keys);
/*  53 */       for (Iterator<Comparable> iter = keys.iterator(); iter.hasNext(); ) {
/*  54 */         String key = (String)iter.next();
/*  55 */         String className = subConfigurationWrapper.getProperty(key);
/*  56 */         log.debug("...instantiating cache adapter: " + key + " (class:" + className + ")");
/*     */         try {
/*  58 */           CacheAdapter adapter = (CacheAdapter)Class.forName(className).newInstance();
/*  59 */           this.adapters.add(adapter);
/*  60 */           addElement((HtmlElement)getButton(adapter));
/*  61 */         } catch (Exception e) {
/*  62 */           log.error("...unable to instantiate cache adapter: " + key + " - exception:" + e, e);
/*     */         }
/*     */       
/*     */       } 
/*  66 */     } catch (Exception e) {
/*  67 */       log.error("...unable to initialize - exception: " + e, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ClickButtonElement getButton(final CacheAdapter adapter) {
/*  73 */     synchronized (this.adapterToButton) {
/*  74 */       ClickButtonElement button = (ClickButtonElement)this.adapterToButton.get(adapter);
/*  75 */       if (button == null) {
/*  76 */         button = new ClickButtonElement(this.context.createID(), null)
/*     */           {
/*     */             protected String getLabel() {
/*  79 */               return MainPanel.this.context.getLabel("clear");
/*     */             }
/*     */             
/*     */             public Object onClick(Map submitParams) {
/*     */               try {
/*  84 */                 MainPanel.this.statusMessage = null;
/*  85 */                 Integer size = null;
/*  86 */                 if (adapter instanceof CacheAdapter.Size) {
/*  87 */                   size = Integer.valueOf(((CacheAdapter.Size)adapter).getSize());
/*     */                 }
/*  89 */                 adapter.clear(MainPanel.this.context);
/*  90 */                 MainPanel.this.statusMessage = MainPanel.this.context.getMessage("cache.cleared");
/*     */                 
/*  92 */                 if (size != null) {
/*  93 */                   MainPanel.this.statusMessage += " (" + size + " " + MainPanel.this.context.getLabel("entries") + ")";
/*     */                 }
/*  95 */               } catch (Exception e) {
/*  96 */                 MainPanel.this.statusMessage = MainPanel.this.context.getMessage("error.see.log");
/*  97 */                 MainPanel.log.error("unable to clear cache facaded by " + String.valueOf(adapter) + " - exception:" + e, e);
/*     */               } 
/*  99 */               return null;
/*     */             }
/*     */           };
/*     */         
/* 103 */         this.adapterToButton.put(adapter, button);
/*     */       } 
/* 105 */       return button;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static MainPanel getInstance(ClientContext context) {
/* 110 */     synchronized (context.getLockObject()) {
/* 111 */       MainPanel instance = (MainPanel)context.getObject(MainPanel.class);
/* 112 */       if (instance == null) {
/* 113 */         instance = new MainPanel(context);
/* 114 */         context.storeObject(MainPanel.class, instance);
/*     */       } 
/* 116 */       return instance;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 123 */     if (this.adapters.size() == 0) {
/* 124 */       return "";
/*     */     }
/* 126 */     StringBuffer tmp = new StringBuffer(template);
/* 127 */     for (Iterator<CacheAdapter> iter = this.adapters.iterator(); iter.hasNext(); ) {
/* 128 */       CacheAdapter adapter = iter.next();
/* 129 */       ClickButtonElement button = getButton(adapter);
/* 130 */       StringBuffer row = new StringBuffer("<tr><td>{DESC}</td><td>{BUTTON}</td></tr>{ROWS}");
/* 131 */       StringUtilities.replace(row, "{DESC}", adapter.getCacheDescription(this.context.getLocale()));
/* 132 */       StringUtilities.replace(row, "{BUTTON}", button.getHtmlCode(params));
/*     */       
/* 134 */       StringUtilities.replace(tmp, "{ROWS}", row.toString());
/*     */     } 
/* 136 */     StringUtilities.replace(tmp, "{ROWS}", "");
/*     */     
/* 138 */     StringUtilities.replace(tmp, "{STATUS}", (this.statusMessage != null) ? this.statusMessage : "");
/*     */     
/* 140 */     this.statusMessage = null;
/* 141 */     return tmp.toString();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\admin\cache\\ui\html\main\MainPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */