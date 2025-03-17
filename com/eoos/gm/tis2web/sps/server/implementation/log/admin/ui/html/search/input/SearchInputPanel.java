/*     */ package com.eoos.gm.tis2web.sps.server.implementation.log.admin.ui.html.search.input;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.permission.ComponentAccessPermission;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.input.date.DateSelectionElement;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.log.Adapter;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.log.SPSEventLog;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.log.SPSEventLogFacade;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.log.admin.ASServiceImpl_SPSEventLog;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.log.admin.ui.html.search.result.SearchResultPanel;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.HtmlElementStack;
/*     */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*     */ import com.eoos.html.element.gtwo.SelectBoxSelectionElement;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.html.element.input.TextInputElement;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.TypeDecorator;
/*     */ import com.eoos.thread.AsynchronousExecution;
/*     */ import com.eoos.util.v2.StringUtilities;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SearchInputPanel
/*     */   extends HtmlElementContainerBase
/*     */ {
/*  39 */   private static final Logger log = Logger.getLogger(SearchInputPanel.class);
/*     */   
/*     */   private static final int COUNT_ATTRIBUTE_FILTER = 3;
/*     */   private static String template;
/*     */   private static String templateAttributeFilter;
/*     */   private ClientContext context;
/*     */   
/*     */   static {
/*     */     try {
/*  48 */       template = ApplicationContext.getInstance().loadFile(SearchInputPanel.class, "searchinputpanel.html", null).toString();
/*  49 */       templateAttributeFilter = ApplicationContext.getInstance().loadFile(SearchInputPanel.class, "attributefilter.html", null).toString();
/*  50 */     } catch (Exception e) {
/*  51 */       log.error("error loading template - error:" + e, e);
/*  52 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private DateSelectionElement selectionDateFrom;
/*     */   
/*     */   private DateSelectionElement selectionDateUntil;
/*     */   
/*     */   private TextInputElement inputEventnamePattern;
/*     */   
/*     */   private SelectBoxSelectionElement selectionAdapter;
/*     */   
/*  66 */   private TextInputElement[] inputAttributeNamePattern = new TextInputElement[3];
/*     */   
/*  68 */   private TextInputElement[] inputAttributeValuePattern = new TextInputElement[3];
/*     */   
/*     */   private ClickButtonElement buttonSearch;
/*     */   
/*  72 */   private long maximumSearchDuration = -1L;
/*     */   
/*  74 */   private int hitLimit = -1;
/*     */   
/*  76 */   private String message = null;
/*     */ 
/*     */   
/*     */   public SearchInputPanel(final ClientContext context) {
/*  80 */     this.context = context;
/*  81 */     Configuration configuration = ASServiceImpl_SPSEventLog.getInstance().getConfiguration();
/*  82 */     TypeDecorator tc = new TypeDecorator(configuration);
/*     */     try {
/*  84 */       this.maximumSearchDuration = tc.getNumber("max.search.duration").longValue();
/*  85 */     } catch (Exception e) {
/*  86 */       log.warn("unable to read configuration parameter 'max.search.duration', applying no time restriction for search");
/*     */     } 
/*     */     
/*     */     try {
/*  90 */       this.hitLimit = tc.getNumber("search.hit.limit").intValue();
/*  91 */     } catch (Exception e) {
/*  92 */       log.warn("unable to read configuration parameter 'search.hit.limit', applying no limit for search");
/*     */     } 
/*     */     
/*  95 */     this.selectionDateFrom = new DateSelectionElement(context.createID(), 0, context.getLocale());
/*  96 */     addElement((HtmlElement)this.selectionDateFrom);
/*     */ 
/*     */     
/*  99 */     int offsetStartDate = 0;
/*     */     try {
/* 101 */       offsetStartDate = Math.abs(tc.getNumber("search.start.date.offset").intValue());
/* 102 */     } catch (Exception e) {
/* 103 */       log.warn("unable to read configuration parameter 'search.start.date.offset', setting to default: 24)");
/* 104 */       offsetStartDate = 24;
/*     */     } 
/* 106 */     long defaultTime = System.currentTimeMillis();
/* 107 */     defaultTime -= (offsetStartDate * 3600000);
/* 108 */     this.selectionDateFrom.setValue(new Date(defaultTime));
/*     */     
/* 110 */     this.selectionDateUntil = new DateSelectionElement(context.createID(), 0, context.getLocale());
/* 111 */     addElement((HtmlElement)this.selectionDateUntil);
/* 112 */     this.selectionDateUntil.setValue(new Date(System.currentTimeMillis()));
/*     */     
/* 114 */     this.inputEventnamePattern = new TextInputElement(context.createID());
/* 115 */     addElement((HtmlElement)this.inputEventnamePattern);
/*     */     
/* 117 */     this.selectionAdapter = new SelectBoxSelectionElement(context.createID(), true, new DataRetrievalAbstraction.DataCallback()
/*     */         {
/*     */           private List data;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public List getData() {
/* 128 */             return this.data;
/*     */           }
/*     */         },  1)
/*     */       {
/*     */         protected String getDisplayValue(Object option) {
/* 133 */           if (option == null)
/* 134 */             return context.getLabel("no.selection"); 
/* 135 */           if (option.equals(Adapter.GME))
/* 136 */             return context.getLabel("sps.adapter.gme"); 
/* 137 */           if (option.equals(Adapter.NAO)) {
/* 138 */             return context.getLabel("sps.adapter.nao");
/*     */           }
/* 140 */           return context.getLabel("sps.adapter.global");
/*     */         }
/*     */       };
/*     */     
/* 144 */     addElement((HtmlElement)this.selectionAdapter);
/*     */     
/* 146 */     for (int i = 0; i < 3; i++) {
/* 147 */       this.inputAttributeNamePattern[i] = new TextInputElement(context.createID());
/* 148 */       addElement((HtmlElement)this.inputAttributeNamePattern[i]);
/*     */       
/* 150 */       this.inputAttributeValuePattern[i] = new TextInputElement(context.createID());
/* 151 */       addElement((HtmlElement)this.inputAttributeValuePattern[i]);
/*     */     } 
/*     */     
/* 154 */     this.buttonSearch = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/* 157 */           return context.getLabel("search");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 162 */             SearchInputPanel.this.getPanelStack().push((HtmlElement)SearchInputPanel.this.executeSearch());
/* 163 */           } catch (Exception e) {
/* 164 */             SearchInputPanel.log.error("...unable to execute search - exception:" + e, e);
/*     */           } 
/* 166 */           return null;
/*     */         }
/*     */       };
/*     */     
/* 170 */     addElement((HtmlElement)this.buttonSearch);
/*     */   }
/*     */ 
/*     */   
/*     */   private SearchResultPanel executeSearch() throws Exception {
/* 175 */     log.debug("executing search....");
/* 176 */     log.debug("...retrieving backend filter...");
/* 177 */     final SPSEventLog.Query.BackendFilter backendFilter = getBackendFilter();
/* 178 */     log.debug("...sending query request ...");
/* 179 */     AsynchronousExecution asyncExecution = AsynchronousExecution.start(new AsynchronousExecution.Operation()
/*     */         {
/*     */           public Object execute(Object[] input) throws Exception {
/* 182 */             return SPSEventLogFacade.getInstance().getEntries(backendFilter, SearchInputPanel.this.hitLimit);
/*     */           }
/*     */         }null);
/*     */ 
/*     */ 
/*     */     
/* 188 */     Collection entries = null;
/* 189 */     boolean truncated = false;
/*     */     try {
/* 191 */       if (this.maximumSearchDuration == -1L) {
/* 192 */         entries = (Collection)asyncExecution.getResult();
/*     */       } else {
/*     */         try {
/* 195 */           entries = (Collection)asyncExecution.getResult(this.maximumSearchDuration, true);
/* 196 */         } catch (com.eoos.thread.AsynchronousExecution.IneffectiveAbortionException e) {
/* 197 */           truncated = true;
/*     */         } 
/*     */       } 
/* 200 */       if (this.hitLimit != -1 && entries.size() >= this.hitLimit) {
/* 201 */         truncated = true;
/*     */       }
/* 203 */     } catch (com.eoos.gm.tis2web.sps.server.implementation.log.SPSEventLog.Query.AbortionException e) {
/* 204 */       log.warn("...search aborted, working with partial result");
/* 205 */       entries = e.getProcessedEntries();
/* 206 */       truncated = true;
/* 207 */     } catch (InterruptedException e) {
/* 208 */       truncated = true;
/*     */     } 
/*     */     
/* 211 */     return new SearchResultPanel(this.context, entries, truncated);
/*     */   }
/*     */   
/*     */   public HtmlElementStack getPanelStack() {
/* 215 */     HtmlElementContainer container = getContainer();
/* 216 */     while (!(container instanceof HtmlElementStack) && container != null) {
/* 217 */       container = container.getContainer();
/*     */     }
/* 219 */     return (HtmlElementStack)container;
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 223 */     StringBuffer retValue = new StringBuffer(template);
/* 224 */     StringUtilities.replace(retValue, "{MESSAGE}", (this.message != null) ? this.message : "");
/*     */     
/* 226 */     StringUtilities.replace(retValue, "{LABEL_DATE_FROM}", this.context.getLabel("date.filter.start") + ":");
/* 227 */     StringUtilities.replace(retValue, "{INPUT_DATE_FROM}", this.selectionDateFrom.getHtmlCode(params));
/*     */     
/* 229 */     StringUtilities.replace(retValue, "{LABEL_DATE_UNTIL}", this.context.getLabel("date.filter.end") + ":");
/* 230 */     StringUtilities.replace(retValue, "{INPUT_DATE_UNTIL}", this.selectionDateUntil.getHtmlCode(params));
/*     */     
/* 232 */     StringUtilities.replace(retValue, "{LABEL_EVENTNAME}", this.context.getLabel("eventname") + ":");
/* 233 */     StringUtilities.replace(retValue, "{INPUT_EVENTNAME}", this.inputEventnamePattern.getHtmlCode(params));
/*     */     
/* 235 */     StringUtilities.replace(retValue, "{LABEL_ADAPTER}", this.context.getLabel("adapter") + ":");
/* 236 */     StringUtilities.replace(retValue, "{INPUT_ADAPTER}", this.selectionAdapter.getHtmlCode(params));
/*     */     
/* 238 */     for (int i = 0; i < 3; i++) {
/* 239 */       StringBuffer tmp = new StringBuffer(templateAttributeFilter);
/*     */       
/* 241 */       StringUtilities.replace(tmp, "{LABEL_ATTRIBUTE_NAME}", this.context.getLabel("attribute") + " " + (i + 1) + ":");
/* 242 */       StringUtilities.replace(tmp, "{INPUT_ATTRIBUTE_NAME}", this.inputAttributeNamePattern[i].getHtmlCode(params));
/*     */       
/* 244 */       StringUtilities.replace(tmp, "{LABEL_ATTRIBUTE_VALUE}", this.context.getLabel("value") + " " + (i + 1) + ":");
/* 245 */       StringUtilities.replace(tmp, "{INPUT_ATTRIBUTE_VALUE}", this.inputAttributeValuePattern[i].getHtmlCode(params));
/*     */       
/* 247 */       StringUtilities.replace(retValue, "{ATTRIBUTE_FILTER}", tmp.toString() + "\n{ATTRIBUTE_FILTER}");
/*     */     } 
/* 249 */     StringUtilities.replace(retValue, "{ATTRIBUTE_FILTER}", "");
/*     */     
/* 251 */     StringUtilities.replace(retValue, "{BUTTON_SEARCH}", this.buttonSearch.getHtmlCode(params));
/* 252 */     return retValue.toString();
/*     */   }
/*     */   
/*     */   private String adjustWildcards(String string) {
/* 256 */     if (string != null) {
/* 257 */       string = StringUtilities.replace(string, "*", "%");
/* 258 */       string = StringUtilities.replace(string, "?", "_");
/*     */     } 
/* 260 */     return string;
/*     */   }
/*     */   
/*     */   public SPSEventLog.Query.BackendFilter getBackendFilter() {
/* 264 */     final Date from = (Date)this.selectionDateFrom.getValue();
/* 265 */     final Date until = (Date)this.selectionDateUntil.getValue();
/*     */     
/* 267 */     final Adapter adapter = (Adapter)this.selectionAdapter.getValue();
/*     */     
/* 269 */     final String eventName = adjustWildcards((String)this.inputEventnamePattern.getValue());
/*     */     
/* 271 */     final Collection<SPSEventLog.Flag> flags = new HashSet();
/* 272 */     flags.add(SPSEventLog.FLAG_NONE);
/*     */     
/* 274 */     if (ComponentAccessPermission.getInstance(this.context).check("spslog.entries.all")) {
/* 275 */       flags.add(SPSEventLog.FLAG_ALL);
/*     */     }
/* 277 */     if (ComponentAccessPermission.getInstance(this.context).check("spslog.entries.onstar")) {
/* 278 */       flags.add(SPSEventLog.FLAG_ONSTAR);
/*     */     }
/*     */     
/* 281 */     List<SPSEventLog.Query.BackendFilter.AttributeFilter> attributeFilters = new LinkedList();
/* 282 */     for (int i = 0; i < 3; i++) {
/* 283 */       String attributeNamePattern = (String)this.inputAttributeNamePattern[i].getValue();
/* 284 */       if (attributeNamePattern != null && attributeNamePattern.length() > 0) {
/* 285 */         final String nameFilter = adjustWildcards(attributeNamePattern);
/* 286 */         String attributeValuePattern = (String)this.inputAttributeValuePattern[i].getValue();
/* 287 */         if (attributeValuePattern != null && attributeValuePattern.length() > 0) {
/* 288 */           final String valueFilter = adjustWildcards(attributeValuePattern);
/* 289 */           attributeFilters.add(new SPSEventLog.Query.BackendFilter.AttributeFilter()
/*     */               {
/*     */                 public String getValuePattern() {
/* 292 */                   return valueFilter;
/*     */                 }
/*     */                 
/*     */                 public String getNamePattern() {
/* 296 */                   return nameFilter;
/*     */                 }
/*     */               });
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 303 */     final SPSEventLog.Query.BackendFilter.AttributeFilter[] filters = (attributeFilters.size() == 0) ? null : attributeFilters.<SPSEventLog.Query.BackendFilter.AttributeFilter>toArray(new SPSEventLog.Query.BackendFilter.AttributeFilter[attributeFilters.size()]);
/*     */     
/* 305 */     return new SPSEventLog.Query.BackendFilter()
/*     */       {
/*     */         public Long getTimestampMAX() {
/* 308 */           return (until != null) ? Long.valueOf(until.getTime()) : null;
/*     */         }
/*     */         
/*     */         public Long getTimestampMIN() {
/* 312 */           return (from != null) ? Long.valueOf(from.getTime()) : null;
/*     */         }
/*     */         
/*     */         public Adapter getAdapter() {
/* 316 */           return adapter;
/*     */         }
/*     */         
/*     */         public String getNamePattern() {
/* 320 */           if (eventName != null && eventName.length() > 0) {
/* 321 */             return eventName.trim();
/*     */           }
/* 323 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         public Collection getFlags() {
/* 328 */           return flags;
/*     */         }
/*     */         
/*     */         public SPSEventLog.Query.BackendFilter.AttributeFilter[] getAttributeFilters() {
/* 332 */           return filters;
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\log\admi\\ui\html\search\input\SearchInputPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */