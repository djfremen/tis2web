/*     */ package com.eoos.gm.tis2web.swdl.server.datamodel.system.metrics.admin.ui.html.search.input;
/*     */ 
/*     */ import com.eoos.automat.StringAcceptor;
/*     */ import com.eoos.filter.Filter;
/*     */ import com.eoos.filter.FilterLinkage;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.input.date.DateSelectionElement;
/*     */ import com.eoos.gm.tis2web.swdl.server.datamodel.system.metrics.SWDLMetricsLog;
/*     */ import com.eoos.gm.tis2web.swdl.server.datamodel.system.metrics.SWDLMetricsLogFacade;
/*     */ import com.eoos.gm.tis2web.swdl.server.datamodel.system.metrics.admin.ASServiceImpl_SWDLLog;
/*     */ import com.eoos.gm.tis2web.swdl.server.datamodel.system.metrics.admin.ui.html.search.result.SearchResultPanel;
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
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SearchInputPanel
/*     */   extends HtmlElementContainerBase
/*     */ {
/*  33 */   private static final Logger log = Logger.getLogger(SearchInputPanel.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  38 */       template = ApplicationContext.getInstance().loadFile(SearchInputPanel.class, "searchinputpanel.html", null).toString();
/*  39 */     } catch (Exception e) {
/*  40 */       log.error("error loading template - error:" + e, e);
/*  41 */       throw new RuntimeException();
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
/*     */   private TextInputElement inputDevicePattern;
/*     */   
/*     */   private TextInputElement inputApplicationPattern;
/*     */   
/*     */   private TextInputElement inputVersionPattern;
/*     */   
/*     */   private TextInputElement inputLanguagePattern;
/*     */   
/*     */   private ClickButtonElement buttonSearch;
/*  61 */   private long maximumSearchDuration = -1L;
/*     */   
/*  63 */   private int hitLimit = -1;
/*     */   
/*     */   private String message;
/*     */ 
/*     */   
/*     */   public SearchInputPanel(final ClientContext context) {
/*  69 */     this.context = context;
/*  70 */     Configuration configuration = ASServiceImpl_SWDLLog.getInstance().getConfiguration();
/*  71 */     TypeDecorator tc = new TypeDecorator(configuration);
/*     */     try {
/*  73 */       this.maximumSearchDuration = tc.getNumber("max.search.duration").longValue();
/*  74 */     } catch (Exception e) {
/*  75 */       log.warn("unable to read configuration parameter 'max.search.duration', applying no time restriction for search");
/*     */     } 
/*     */     
/*     */     try {
/*  79 */       this.hitLimit = tc.getNumber("search.hit.limit").intValue();
/*  80 */     } catch (Exception e) {
/*  81 */       log.warn("unable to read configuration parameter 'search.hit.limit', applying no limit for search");
/*     */     } 
/*     */     
/*  84 */     this.selectionDateFrom = new DateSelectionElement(context.createID(), 0, context.getLocale());
/*  85 */     addElement((HtmlElement)this.selectionDateFrom);
/*     */ 
/*     */     
/*  88 */     int offsetStartDate = 0;
/*     */     try {
/*  90 */       offsetStartDate = Math.abs(tc.getNumber("search.start.date.offset").intValue());
/*  91 */     } catch (Exception e) {
/*  92 */       log.warn("unable to read configuration parameter 'search.start.date.offset', setting to default: 24)");
/*  93 */       offsetStartDate = 24;
/*     */     } 
/*  95 */     long defaultTime = System.currentTimeMillis();
/*  96 */     defaultTime -= (offsetStartDate * 3600000);
/*  97 */     this.selectionDateFrom.setValue(new Date(defaultTime));
/*     */     
/*  99 */     this.selectionDateUntil = new DateSelectionElement(context.createID(), 0, context.getLocale());
/* 100 */     addElement((HtmlElement)this.selectionDateUntil);
/* 101 */     this.selectionDateUntil.setValue(new Date(System.currentTimeMillis()));
/*     */     
/* 103 */     this.inputDevicePattern = new TextInputElement(context.createID());
/* 104 */     addElement((HtmlElement)this.inputDevicePattern);
/*     */     
/* 106 */     this.inputApplicationPattern = new TextInputElement(context.createID());
/* 107 */     addElement((HtmlElement)this.inputApplicationPattern);
/*     */     
/* 109 */     this.inputVersionPattern = new TextInputElement(context.createID());
/* 110 */     addElement((HtmlElement)this.inputVersionPattern);
/*     */     
/* 112 */     this.inputLanguagePattern = new TextInputElement(context.createID());
/* 113 */     addElement((HtmlElement)this.inputLanguagePattern);
/*     */     
/* 115 */     this.buttonSearch = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/* 118 */           return context.getLabel("search");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 123 */             SearchInputPanel.this.getPanelStack().push((HtmlElement)SearchInputPanel.this.executeSearch());
/*     */           }
/* 125 */           catch (Exception e) {
/* 126 */             SearchInputPanel.log.error("...unable to execute search - exception:" + e, e);
/*     */           } 
/* 128 */           return null;
/*     */         }
/*     */       };
/*     */     
/* 132 */     addElement((HtmlElement)this.buttonSearch);
/*     */   }
/*     */ 
/*     */   
/*     */   private SearchResultPanel executeSearch() throws Exception {
/* 137 */     log.debug("executing search....");
/* 138 */     log.debug("...retrieving backend filter...");
/* 139 */     final SWDLMetricsLog.Query.BackendFilter backendFilter = getBackendFilter();
/* 140 */     log.debug("...retrieving entry filter ...");
/* 141 */     final Filter filter = getEntryFilter();
/* 142 */     log.debug("...sending query request ...");
/* 143 */     AsynchronousExecution asyncExecution = AsynchronousExecution.start(new AsynchronousExecution.Operation()
/*     */         {
/*     */           public Object execute(Object[] input) throws Exception {
/* 146 */             return SWDLMetricsLogFacade.getInstance().getEntries(backendFilter, filter, SearchInputPanel.this.hitLimit);
/*     */           }
/*     */         }null);
/*     */ 
/*     */ 
/*     */     
/* 152 */     Collection entries = null;
/* 153 */     boolean truncated = false;
/*     */     try {
/* 155 */       if (this.maximumSearchDuration == -1L) {
/* 156 */         entries = (Collection)asyncExecution.getResult();
/*     */       } else {
/*     */         try {
/* 159 */           entries = (Collection)asyncExecution.getResult(this.maximumSearchDuration, true);
/* 160 */         } catch (com.eoos.thread.AsynchronousExecution.IneffectiveAbortionException e) {
/* 161 */           truncated = true;
/*     */         } 
/*     */       } 
/* 164 */       if (this.hitLimit != -1 && entries.size() >= this.hitLimit) {
/* 165 */         truncated = true;
/*     */       }
/* 167 */     } catch (com.eoos.gm.tis2web.swdl.server.datamodel.system.metrics.SWDLMetricsLog.Query.AbortionException e) {
/* 168 */       log.warn("...search aborted, working with partial result");
/* 169 */       entries = e.getProcessedEntries();
/* 170 */       truncated = true;
/* 171 */     } catch (InterruptedException e) {
/* 172 */       truncated = true;
/*     */     } 
/*     */     
/* 175 */     return new SearchResultPanel(this.context, entries, truncated);
/*     */   }
/*     */   
/*     */   public HtmlElementStack getPanelStack() {
/* 179 */     HtmlElementContainer container = getContainer();
/* 180 */     while (!(container instanceof HtmlElementStack) && container != null) {
/* 181 */       container = container.getContainer();
/*     */     }
/* 183 */     return (HtmlElementStack)container;
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 187 */     StringBuffer retValue = new StringBuffer(template);
/* 188 */     StringUtilities.replace(retValue, "{MESSAGE}", (this.message != null) ? this.message : "");
/*     */     
/* 190 */     StringUtilities.replace(retValue, "{LABEL_DATE_FROM}", this.context.getLabel("date.filter.start") + ":");
/* 191 */     StringUtilities.replace(retValue, "{INPUT_DATE_FROM}", this.selectionDateFrom.getHtmlCode(params));
/*     */     
/* 193 */     StringUtilities.replace(retValue, "{LABEL_DATE_UNTIL}", this.context.getLabel("date.filter.end") + ":");
/* 194 */     StringUtilities.replace(retValue, "{INPUT_DATE_UNTIL}", this.selectionDateUntil.getHtmlCode(params));
/*     */     
/* 196 */     StringUtilities.replace(retValue, "{LABEL_DEVICE}", this.context.getLabel("device") + ":");
/* 197 */     StringUtilities.replace(retValue, "{INPUT_DEVICE}", this.inputDevicePattern.getHtmlCode(params));
/*     */     
/* 199 */     StringUtilities.replace(retValue, "{LABEL_APPLICATION}", this.context.getLabel("application") + ":");
/* 200 */     StringUtilities.replace(retValue, "{INPUT_APPLICATION}", this.inputApplicationPattern.getHtmlCode(params));
/*     */     
/* 202 */     StringUtilities.replace(retValue, "{LABEL_VERSION}", this.context.getLabel("version") + ":");
/* 203 */     StringUtilities.replace(retValue, "{INPUT_VERSION}", this.inputVersionPattern.getHtmlCode(params));
/*     */     
/* 205 */     StringUtilities.replace(retValue, "{LABEL_LANG}", this.context.getLabel("language") + ":");
/* 206 */     StringUtilities.replace(retValue, "{INPUT_LANG}", this.inputLanguagePattern.getHtmlCode(params));
/*     */     
/* 208 */     StringUtilities.replace(retValue, "{BUTTON_SEARCH}", this.buttonSearch.getHtmlCode(params));
/* 209 */     return retValue.toString();
/*     */   }
/*     */   
/*     */   public SWDLMetricsLog.Query.BackendFilter getBackendFilter() {
/* 213 */     final Date from = (Date)this.selectionDateFrom.getValue();
/* 214 */     final Date until = (Date)this.selectionDateUntil.getValue();
/*     */     
/* 216 */     return new SWDLMetricsLog.Query.BackendFilter()
/*     */       {
/*     */         public Long getTimestampMAX() {
/* 219 */           return (until != null) ? Long.valueOf(until.getTime()) : null;
/*     */         }
/*     */         
/*     */         public Long getTimestampMIN() {
/* 223 */           return (from != null) ? Long.valueOf(from.getTime()) : null;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public Filter getEntryFilter() {
/*     */     FilterLinkage filterLinkage;
/* 230 */     Filter retValue = null;
/*     */     
/* 232 */     String devicePattern = (String)this.inputDevicePattern.getValue();
/* 233 */     if (devicePattern != null && devicePattern.length() > 0) {
/* 234 */       SWDLMetricsLog.Query.EntryFilter entryFilter1; final StringAcceptor deviceAcceptor = StringAcceptor.create(devicePattern, true);
/* 235 */       SWDLMetricsLog.Query.EntryFilter entryFilter2 = new SWDLMetricsLog.Query.EntryFilter()
/*     */         {
/*     */           protected boolean include(SWDLMetricsLog.Entry entry) {
/*     */             try {
/* 239 */               String device = entry.getDevice();
/* 240 */               if (device == null)
/* 241 */                 device = ""; 
/* 242 */               return deviceAcceptor.accept(device);
/* 243 */             } catch (Exception e) {
/* 244 */               SearchInputPanel.log.warn("...unable to determine filter status of " + String.valueOf(entry) + " - exception:" + e);
/* 245 */               return false;
/*     */             } 
/*     */           }
/*     */         };
/*     */ 
/*     */       
/* 251 */       if (retValue == null) {
/* 252 */         entryFilter1 = entryFilter2;
/*     */       } else {
/* 254 */         filterLinkage = new FilterLinkage((Filter)entryFilter1, (Filter)entryFilter2, 2);
/*     */       } 
/*     */     } 
/*     */     
/* 258 */     String applicationPattern = (String)this.inputApplicationPattern.getValue();
/* 259 */     if (applicationPattern != null && applicationPattern.length() > 0) {
/* 260 */       SWDLMetricsLog.Query.EntryFilter entryFilter1; final StringAcceptor applicationAcceptor = StringAcceptor.create(applicationPattern, true);
/* 261 */       SWDLMetricsLog.Query.EntryFilter entryFilter2 = new SWDLMetricsLog.Query.EntryFilter()
/*     */         {
/*     */           protected boolean include(SWDLMetricsLog.Entry entry) {
/*     */             try {
/* 265 */               String application = entry.getApplication();
/* 266 */               if (application == null)
/* 267 */                 application = ""; 
/* 268 */               return applicationAcceptor.accept(application);
/* 269 */             } catch (Exception e) {
/* 270 */               SearchInputPanel.log.warn("...unable to determine filter status of " + String.valueOf(entry) + " - exception:" + e);
/* 271 */               return false;
/*     */             } 
/*     */           }
/*     */         };
/*     */ 
/*     */       
/* 277 */       if (filterLinkage == null) {
/* 278 */         entryFilter1 = entryFilter2;
/*     */       } else {
/* 280 */         filterLinkage = new FilterLinkage((Filter)entryFilter1, (Filter)entryFilter2, 2);
/*     */       } 
/*     */     } 
/*     */     
/* 284 */     String versionPattern = (String)this.inputVersionPattern.getValue();
/* 285 */     if (versionPattern != null && versionPattern.length() > 0) {
/* 286 */       SWDLMetricsLog.Query.EntryFilter entryFilter1; final StringAcceptor versionAcceptor = StringAcceptor.create(versionPattern, true);
/* 287 */       SWDLMetricsLog.Query.EntryFilter entryFilter2 = new SWDLMetricsLog.Query.EntryFilter()
/*     */         {
/*     */           protected boolean include(SWDLMetricsLog.Entry entry) {
/*     */             try {
/* 291 */               String version = entry.getVersion();
/* 292 */               if (version == null)
/* 293 */                 version = ""; 
/* 294 */               return versionAcceptor.accept(version);
/* 295 */             } catch (Exception e) {
/* 296 */               SearchInputPanel.log.warn("...unable to determine filter status of " + String.valueOf(entry) + " - exception:" + e);
/* 297 */               return false;
/*     */             } 
/*     */           }
/*     */         };
/*     */ 
/*     */       
/* 303 */       if (filterLinkage == null) {
/* 304 */         entryFilter1 = entryFilter2;
/*     */       } else {
/* 306 */         filterLinkage = new FilterLinkage((Filter)entryFilter1, (Filter)entryFilter2, 2);
/*     */       } 
/*     */     } 
/*     */     
/* 310 */     String langPattern = (String)this.inputLanguagePattern.getValue();
/* 311 */     if (langPattern != null && langPattern.length() > 0) {
/* 312 */       SWDLMetricsLog.Query.EntryFilter entryFilter1; final StringAcceptor langAcceptor = StringAcceptor.create(langPattern, true);
/* 313 */       SWDLMetricsLog.Query.EntryFilter entryFilter2 = new SWDLMetricsLog.Query.EntryFilter()
/*     */         {
/*     */           protected boolean include(SWDLMetricsLog.Entry entry) {
/*     */             try {
/* 317 */               String language = entry.getLanguage();
/* 318 */               if (language == null)
/* 319 */                 language = ""; 
/* 320 */               return langAcceptor.accept(language);
/* 321 */             } catch (Exception e) {
/* 322 */               SearchInputPanel.log.warn("...unable to determine filter status of " + String.valueOf(entry) + " - exception:" + e);
/* 323 */               return false;
/*     */             } 
/*     */           }
/*     */         };
/*     */ 
/*     */       
/* 329 */       if (filterLinkage == null) {
/* 330 */         entryFilter1 = entryFilter2;
/*     */       } else {
/* 332 */         filterLinkage = new FilterLinkage((Filter)entryFilter1, (Filter)entryFilter2, 2);
/*     */       } 
/*     */     } 
/*     */     
/* 336 */     return (Filter)filterLinkage;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\server\datamodel\system\metrics\admi\\ui\html\search\input\SearchInputPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */