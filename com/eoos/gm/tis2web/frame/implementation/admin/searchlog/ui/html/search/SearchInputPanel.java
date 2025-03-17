/*     */ package com.eoos.gm.tis2web.frame.implementation.admin.searchlog.ui.html.search;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClusterTaskExecution;
/*     */ import com.eoos.gm.tis2web.frame.implementation.admin.searchlog.SearchTask;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.HtmlElementStack;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.html.element.input.TextInputElement;
/*     */ import com.eoos.util.Task;
/*     */ import com.eoos.util.v2.StringUtilities;
/*     */ import java.io.File;
/*     */ import java.net.URL;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class SearchInputPanel extends HtmlElementContainerBase {
/*  26 */   private static final Logger log = Logger.getLogger(SearchInputPanel.class);
/*     */   private static String template;
/*     */   private ClientContext context;
/*     */   
/*     */   static {
/*     */     try {
/*  32 */       template = ApplicationContext.getInstance().loadFile(SearchInputPanel.class, "searchinputpanel.html", null).toString();
/*  33 */     } catch (Exception e) {
/*  34 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private TextInputElement inputPattern;
/*     */   
/*     */   private TextInputElement inputFilename;
/*     */   
/*     */   private ClickButtonElement buttonSearch;
/*     */ 
/*     */   
/*     */   public SearchInputPanel(final ClientContext context) {
/*  48 */     this.context = context;
/*     */     
/*  50 */     this.inputPattern = new TextInputElement(context.createID());
/*  51 */     addElement((HtmlElement)this.inputPattern);
/*     */     
/*  53 */     this.inputFilename = new TextInputElement(context.createID());
/*  54 */     addElement((HtmlElement)this.inputFilename);
/*     */     
/*  56 */     this.buttonSearch = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/*  59 */           return context.getLabel("search");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/*  64 */             SearchResultPanel panel = SearchInputPanel.this.executeSearch();
/*  65 */             if (panel != null) {
/*  66 */               SearchInputPanel.this.getPanelStack().push((HtmlElement)panel);
/*     */             }
/*  68 */           } catch (Exception e) {
/*  69 */             SearchInputPanel.log.error("...unable to execute search - exception:" + e, e);
/*     */           } 
/*  71 */           return null;
/*     */         }
/*     */       };
/*     */     
/*  75 */     addElement((HtmlElement)this.buttonSearch);
/*     */   }
/*     */ 
/*     */   
/*     */   private SearchResultPanel executeSearch() throws Exception {
/*  80 */     log.debug("executing search....");
/*  81 */     String filenamePattern = (String)this.inputFilename.getValue();
/*  82 */     String[] includeFilenamePatterns = null;
/*  83 */     if (filenamePattern != null && filenamePattern.trim().length() > 0) {
/*  84 */       includeFilenamePatterns = filenamePattern.trim().split(";");
/*     */     }
/*     */     
/*  87 */     String pattern = (String)this.inputPattern.getValue();
/*  88 */     if (pattern == null || pattern.trim().length() == 0) {
/*  89 */       return null;
/*     */     }
/*     */     
/*  92 */     ClusterTaskExecution task = new ClusterTaskExecution((Task)new SearchTask(includeFilenamePatterns, null, pattern), this.context);
/*  93 */     ClusterTaskExecution.Result result = task.execute();
/*     */     
/*  95 */     List<RemoteFile> list = new LinkedList();
/*  96 */     for (Iterator<URL> iter = result.getClusterURLs().iterator(); iter.hasNext(); ) {
/*  97 */       URL url = iter.next();
/*  98 */       if (!(result.getResult(url) instanceof Exception)) {
/*  99 */         for (Iterator<File> iter2 = ((Collection)result.getResult(url)).iterator(); iter2.hasNext(); ) {
/* 100 */           File file = iter2.next();
/* 101 */           list.add(new RemoteFile(file, url));
/*     */         }  continue;
/*     */       } 
/* 104 */       log.warn("unable to retrieve result list from server: " + url + " - exception: ", (Exception)result.getResult(url));
/*     */     } 
/*     */     
/* 107 */     if (list.size() == 0) {
/* 108 */       return null;
/*     */     }
/* 110 */     Collections.sort(list);
/*     */     
/* 112 */     return new SearchResultPanel(this.context, list);
/*     */   }
/*     */ 
/*     */   
/*     */   public HtmlElementStack getPanelStack() {
/* 117 */     HtmlElementContainer container = getContainer();
/* 118 */     while (!(container instanceof HtmlElementStack) && container != null) {
/* 119 */       container = container.getContainer();
/*     */     }
/* 121 */     return (HtmlElementStack)container;
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 125 */     StringBuffer retValue = new StringBuffer(template);
/*     */     
/* 127 */     StringUtilities.replace(retValue, "{LABEL_REGEXP}", this.context.getLabel("pattern") + ":");
/* 128 */     StringUtilities.replace(retValue, "{INPUT_REGEXP}", this.inputPattern.getHtmlCode(params));
/*     */     
/* 130 */     StringUtilities.replace(retValue, "{LABEL_FILENAME}", this.context.getLabel("filename") + ":");
/* 131 */     StringUtilities.replace(retValue, "{INPUT_FILENAME}", this.inputFilename.getHtmlCode(params));
/*     */     
/* 133 */     StringUtilities.replace(retValue, "{BUTTON_SEARCH}", this.buttonSearch.getHtmlCode(params));
/* 134 */     return retValue.toString();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\admin\searchlo\\ui\html\search\SearchInputPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */