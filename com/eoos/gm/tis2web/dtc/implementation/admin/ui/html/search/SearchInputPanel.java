/*     */ package com.eoos.gm.tis2web.dtc.implementation.admin.ui.html.search;
/*     */ 
/*     */ import com.eoos.gm.tis2web.dtc.implementation.DTCLog;
/*     */ import com.eoos.gm.tis2web.dtc.implementation.DTCLogFacade;
/*     */ import com.eoos.gm.tis2web.dtc.implementation.admin.ASServiceImpl_DTCLog;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.input.date.DateSelectionElement;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.HtmlElementStack;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.html.element.input.TextInputElement;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.TypeDecorator;
/*     */ import com.eoos.thread.AsynchronousExecution;
/*     */ import com.eoos.util.v2.StringUtilities;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SearchInputPanel
/*     */   extends HtmlElementContainerBase
/*     */ {
/*  30 */   private static final Logger log = Logger.getLogger(SearchInputPanel.class);
/*     */   
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  36 */       template = ApplicationContext.getInstance().loadFile(SearchInputPanel.class, "searchinputpanel.html", null).toString();
/*  37 */     } catch (Exception e) {
/*  38 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ClientContext context;
/*     */   
/*     */   private DateSelectionElement selectionDateFrom;
/*     */   
/*     */   private DateSelectionElement selectionDateUntil;
/*     */   
/*     */   private TextInputElement inputBACCodePattern;
/*     */   
/*     */   private ClickButtonElement buttonSearch;
/*  52 */   private long maximumSearchDuration = -1L;
/*     */   
/*  54 */   private int hitLimit = -1;
/*     */ 
/*     */   
/*     */   public SearchInputPanel(final ClientContext context) {
/*  58 */     this.context = context;
/*  59 */     Configuration configuration = ASServiceImpl_DTCLog.getInstance().getConfiguration();
/*  60 */     TypeDecorator tc = new TypeDecorator(configuration);
/*     */     try {
/*  62 */       this.maximumSearchDuration = tc.getNumber("max.search.duration").longValue();
/*  63 */     } catch (Exception e) {
/*  64 */       log.warn("unable to read configuration parameter 'max.search.duration', applying no time restriction for search");
/*     */     } 
/*     */     
/*     */     try {
/*  68 */       this.hitLimit = tc.getNumber("search.hit.limit").intValue();
/*  69 */     } catch (Exception e) {
/*  70 */       log.warn("unable to read configuration parameter 'search.hit.limit', applying no limit for search");
/*     */     } 
/*     */     
/*  73 */     this.selectionDateFrom = new DateSelectionElement(context.createID(), 0, context.getLocale());
/*  74 */     addElement((HtmlElement)this.selectionDateFrom);
/*     */ 
/*     */     
/*  77 */     int offsetStartDate = 0;
/*     */     try {
/*  79 */       offsetStartDate = Math.abs(tc.getNumber("search.start.date.offset").intValue());
/*  80 */     } catch (Exception e) {
/*  81 */       log.warn("unable to read configuration parameter 'search.start.date.offset', setting to default: 24)");
/*  82 */       offsetStartDate = 24;
/*     */     } 
/*  84 */     long defaultTime = System.currentTimeMillis();
/*  85 */     defaultTime -= (offsetStartDate * 3600000);
/*  86 */     this.selectionDateFrom.setValue(new Date(defaultTime));
/*     */     
/*  88 */     this.selectionDateUntil = new DateSelectionElement(context.createID(), 0, context.getLocale());
/*  89 */     addElement((HtmlElement)this.selectionDateUntil);
/*  90 */     this.selectionDateUntil.setValue(new Date(System.currentTimeMillis()));
/*     */     
/*  92 */     this.inputBACCodePattern = new TextInputElement(context.createID());
/*  93 */     addElement((HtmlElement)this.inputBACCodePattern);
/*     */     
/*  95 */     this.buttonSearch = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/*  98 */           return context.getLabel("search");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 103 */             SearchInputPanel.this.getPanelStack().push((HtmlElement)SearchInputPanel.this.executeSearch());
/* 104 */           } catch (Exception e) {
/* 105 */             SearchInputPanel.log.error("...unable to execute search - exception:" + e, e);
/*     */           } 
/* 107 */           return null;
/*     */         }
/*     */       };
/*     */     
/* 111 */     addElement((HtmlElement)this.buttonSearch);
/*     */   }
/*     */ 
/*     */   
/*     */   private SearchResultPanel executeSearch() throws Exception {
/* 116 */     log.debug("executing search....");
/* 117 */     log.debug("...retrieving backend filter...");
/* 118 */     final DTCLog.BackendFilter backendFilter = getBackendFilter();
/* 119 */     log.debug("...sending query request ...");
/* 120 */     AsynchronousExecution asyncExecution = AsynchronousExecution.start(new AsynchronousExecution.Operation()
/*     */         {
/*     */           public Object execute(Object[] input) throws Exception {
/* 123 */             return DTCLogFacade.getInstance().getEntries(backendFilter, SearchInputPanel.this.hitLimit);
/*     */           }
/*     */         }null);
/*     */ 
/*     */     
/* 128 */     Collection entries = null;
/* 129 */     boolean truncated = false;
/*     */     try {
/* 131 */       if (this.maximumSearchDuration == -1L) {
/* 132 */         entries = (Collection)asyncExecution.getResult();
/*     */       } else {
/*     */         try {
/* 135 */           entries = (Collection)asyncExecution.getResult(this.maximumSearchDuration, true);
/* 136 */         } catch (com.eoos.thread.AsynchronousExecution.IneffectiveAbortionException e) {
/* 137 */           truncated = true;
/*     */         } 
/*     */       } 
/* 140 */       if (this.hitLimit != -1 && entries.size() >= this.hitLimit) {
/* 141 */         truncated = true;
/*     */       }
/* 143 */     } catch (com.eoos.gm.tis2web.dtc.implementation.DTCLog.AbortionException e) {
/* 144 */       log.warn("...search aborted, working with partial result");
/* 145 */       entries = e.getProcessedEntries();
/* 146 */       truncated = true;
/* 147 */     } catch (InterruptedException e) {
/* 148 */       truncated = true;
/*     */     } 
/*     */     
/* 151 */     return new SearchResultPanel(this.context, entries, truncated);
/*     */   }
/*     */   
/*     */   public HtmlElementStack getPanelStack() {
/* 155 */     HtmlElementContainer container = getContainer();
/* 156 */     while (!(container instanceof HtmlElementStack) && container != null) {
/* 157 */       container = container.getContainer();
/*     */     }
/* 159 */     return (HtmlElementStack)container;
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 163 */     StringBuffer retValue = new StringBuffer(template);
/*     */     
/* 165 */     StringUtilities.replace(retValue, "{LABEL_DATE_FROM}", this.context.getLabel("date.filter.start") + ":");
/* 166 */     StringUtilities.replace(retValue, "{INPUT_DATE_FROM}", this.selectionDateFrom.getHtmlCode(params));
/*     */     
/* 168 */     StringUtilities.replace(retValue, "{LABEL_DATE_UNTIL}", this.context.getLabel("date.filter.end") + ":");
/* 169 */     StringUtilities.replace(retValue, "{INPUT_DATE_UNTIL}", this.selectionDateUntil.getHtmlCode(params));
/*     */     
/* 171 */     StringUtilities.replace(retValue, "{LABEL_BACCODE}", this.context.getLabel("dealercode") + ":");
/* 172 */     StringUtilities.replace(retValue, "{INPUT_BACCODE}", this.inputBACCodePattern.getHtmlCode(params));
/*     */     
/* 174 */     StringUtilities.replace(retValue, "{BUTTON_SEARCH}", this.buttonSearch.getHtmlCode(params));
/* 175 */     return retValue.toString();
/*     */   }
/*     */   
/*     */   private String adjustWildcards(String string) {
/* 179 */     if (string != null) {
/* 180 */       string = StringUtilities.replace(string, "*", "%");
/* 181 */       string = StringUtilities.replace(string, "?", "_");
/*     */     } 
/* 183 */     return string;
/*     */   }
/*     */   
/*     */   public DTCLog.BackendFilter getBackendFilter() {
/* 187 */     final Date from = (Date)this.selectionDateFrom.getValue();
/* 188 */     final Date until = (Date)this.selectionDateUntil.getValue();
/*     */     
/* 190 */     final String baccodePattern = adjustWildcards((String)this.inputBACCodePattern.getValue());
/*     */     
/* 192 */     return new DTCLog.BackendFilter()
/*     */       {
/*     */         public String getBACCodePattern() {
/* 195 */           return (baccodePattern != null && baccodePattern.length() > 0) ? baccodePattern : null;
/*     */         }
/*     */         
/*     */         public Long getIdMax() {
/* 199 */           return null;
/*     */         }
/*     */         
/*     */         public Long getIdMin() {
/* 203 */           return null;
/*     */         }
/*     */         
/*     */         public Long getTimestampMAX() {
/* 207 */           return (until != null) ? Long.valueOf(until.getTime()) : null;
/*     */         }
/*     */ 
/*     */         
/*     */         public Long getTimestampMIN() {
/* 212 */           return (from != null) ? Long.valueOf(from.getTime()) : null;
/*     */         }
/*     */ 
/*     */         
/*     */         public List getApplicationIDs() {
/* 217 */           return null;
/*     */         }
/*     */         
/*     */         public List getCountryCodes() {
/* 221 */           return null;
/*     */         }
/*     */         
/*     */         public List getPortalIDs(DTCLog.BackendFilter.Type type) {
/* 225 */           return null;
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\dtc\implementation\admi\\ui\html\search\SearchInputPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */