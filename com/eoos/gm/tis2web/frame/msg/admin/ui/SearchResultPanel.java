/*     */ package com.eoos.gm.tis2web.frame.msg.admin.ui;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.HtmlElementStack;
/*     */ import com.eoos.html.element.gtwo.PagedElement;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.util.v2.StringUtilities;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class SearchResultPanel
/*     */   extends HtmlElementContainerBase
/*     */ {
/*  21 */   private static final Logger log = Logger.getLogger(SearchResultPanel.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  26 */       template = ApplicationContext.getInstance().loadFile(SearchResultPanel.class, "searchresultpanel.html", null).toString();
/*  27 */     } catch (Exception e) {
/*  28 */       log.error("error loading template - error:" + e, e);
/*  29 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ClientContext context;
/*  35 */   private List entries = null;
/*     */   
/*  37 */   private EntriesListElement listElement = null;
/*     */   
/*  39 */   private HtmlElement listElementPagedFront = null;
/*     */   
/*     */   private ClickButtonElement buttonNewSearch;
/*     */   
/*     */   private boolean truncated = false;
/*     */   
/*     */   private SearchInputPanel inputPanel;
/*     */ 
/*     */   
/*     */   public SearchResultPanel(final ClientContext context, Collection<?> entries, boolean truncated, SearchInputPanel searchPanel) {
/*  49 */     this.context = context;
/*  50 */     this.truncated = truncated;
/*  51 */     this.inputPanel = searchPanel;
/*     */     
/*  53 */     if (entries != null && entries.size() > 0) {
/*  54 */       this.entries = new ArrayList(entries);
/*  55 */       this.listElement = new EntriesListElement(this.entries, context, this);
/*  56 */       this.listElementPagedFront = (HtmlElement)new PagedElement(context.createID(), (HtmlElement)this.listElement, 20, 20);
/*  57 */       addElement(this.listElementPagedFront);
/*     */     } 
/*     */ 
/*     */     
/*  61 */     this.buttonNewSearch = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/*  64 */           return context.getLabel("new.search");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/*  69 */             SearchResultPanel.this.getPanelStack().pop();
/*  70 */           } catch (Exception e) {
/*  71 */             SearchResultPanel.log.error("...unable to process request for 'new search' - exception:" + e, e);
/*     */           } 
/*  73 */           return null;
/*     */         }
/*     */       };
/*     */     
/*  77 */     addElement((HtmlElement)this.buttonNewSearch);
/*     */   }
/*     */ 
/*     */   
/*     */   public HtmlElementStack getPanelStack() {
/*  82 */     HtmlElementContainer container = getContainer();
/*  83 */     while (!(container instanceof HtmlElementStack) && container != null) {
/*  84 */       container = container.getContainer();
/*     */     }
/*  86 */     return (HtmlElementStack)container;
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/*  90 */     StringBuffer retvalue = new StringBuffer(template);
/*  91 */     StringUtilities.replace(retvalue, "{MESSAGE}", this.truncated ? this.context.getMessage("search.result.truncated") : "");
/*  92 */     if (this.listElementPagedFront != null) {
/*  93 */       StringUtilities.replace(retvalue, "{LIST}", this.listElementPagedFront.getHtmlCode(params));
/*     */     } else {
/*     */       
/*  96 */       StringUtilities.replace(retvalue, "{LIST}", this.context.getMessage("empty.search.result"));
/*     */     } 
/*     */     
/*  99 */     StringUtilities.replace(retvalue, "{NEW_SEARCH_BUTTON}", this.buttonNewSearch.getHtmlCode(params));
/* 100 */     return retvalue.toString();
/*     */   }
/*     */   
/*     */   public void update() throws Exception {
/* 104 */     log.debug("updating search result panel...");
/* 105 */     HtmlElementStack panelStack = getPanelStack();
/*     */     
/* 107 */     if (panelStack.peek() != this) {
/* 108 */       log.warn("...current shown panel is not this search result panel, throwing IllegalStateException");
/* 109 */       throw new IllegalStateException();
/*     */     } 
/* 111 */     log.debug("...reexecuting search");
/* 112 */     SearchResultPanel resultPanel = this.inputPanel.executeSearch();
/* 113 */     log.debug("...replacing panel");
/* 114 */     panelStack.pop();
/* 115 */     panelStack.push((HtmlElement)resultPanel);
/* 116 */     log.debug("...done");
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\msg\admi\\ui\SearchResultPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */