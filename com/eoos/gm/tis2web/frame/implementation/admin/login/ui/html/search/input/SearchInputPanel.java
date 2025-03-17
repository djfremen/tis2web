/*     */ package com.eoos.gm.tis2web.frame.implementation.admin.login.ui.html.search.input;
/*     */ 
/*     */ import com.eoos.filter.Filter;
/*     */ import com.eoos.filter.FilterLinkage;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.input.date.DateSelectionElement;
/*     */ import com.eoos.gm.tis2web.frame.implementation.admin.login.ASServiceImpl_LoginLog;
/*     */ import com.eoos.gm.tis2web.frame.implementation.admin.login.ui.html.search.result.SearchResultPanel;
/*     */ import com.eoos.gm.tis2web.frame.login.log.LoginLog;
/*     */ import com.eoos.gm.tis2web.frame.login.log.LoginLogFacade;
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
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.thread.AsynchronousExecution;
/*     */ import com.eoos.util.v2.StringUtilities;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SearchInputPanel
/*     */   extends HtmlElementContainerBase
/*     */ {
/*  37 */   private static final Logger log = Logger.getLogger(SearchInputPanel.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  42 */       template = ApplicationContext.getInstance().loadFile(SearchInputPanel.class, "searchinputpanel.html", null).toString();
/*  43 */     } catch (Exception e) {
/*  44 */       log.error("error loading template - error:" + e, e);
/*  45 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ClientContext context;
/*     */   
/*     */   private DateSelectionElement selectionDateFrom;
/*     */   
/*     */   private DateSelectionElement selectionDateUntil;
/*     */   private TextInputElement inputUsernamePattern;
/*     */   private SelectBoxSelectionElement selectionStatus;
/*     */   private TextInputElement inputUserGroupPattern;
/*     */   private ClickButtonElement buttonSearch;
/*  59 */   private long maximumSearchDuration = -1L;
/*  60 */   private int hitLimit = -1;
/*     */   
/*     */   private String message;
/*     */ 
/*     */   
/*     */   public SearchInputPanel(final ClientContext context) {
/*  66 */     this.context = context;
/*     */     
/*  68 */     Configuration configuration = ASServiceImpl_LoginLog.getInstance().getConfiguration();
/*  69 */     TypeDecorator tc = new TypeDecorator(configuration);
/*     */     try {
/*  71 */       this.maximumSearchDuration = tc.getNumber("max.search.duration").longValue();
/*  72 */     } catch (Exception e) {
/*  73 */       log.warn("unable to read configuration parameter 'max.search.duration', applying no time restriction for search");
/*     */     } 
/*     */     try {
/*  76 */       this.hitLimit = tc.getNumber("search.hit.limit").intValue();
/*  77 */     } catch (Exception e) {
/*  78 */       log.warn("unable to read configuration parameter 'search.hit.limit', applying no limit for search");
/*     */     } 
/*     */     
/*  81 */     this.selectionDateFrom = new DateSelectionElement(context.createID(), 0, context.getLocale());
/*  82 */     addElement((HtmlElement)this.selectionDateFrom);
/*     */ 
/*     */     
/*  85 */     int offsetStartDate = 0;
/*     */     try {
/*  87 */       offsetStartDate = Math.abs(tc.getNumber("search.start.date.offset").intValue());
/*  88 */     } catch (Exception e) {
/*  89 */       log.warn("unable to read configuration parameter 'search.start.date.offset', setting to default: 24)");
/*  90 */       offsetStartDate = 24;
/*     */     } 
/*  92 */     long defaultTime = System.currentTimeMillis();
/*  93 */     defaultTime -= (offsetStartDate * 3600000);
/*  94 */     this.selectionDateFrom.setValue(new Date(defaultTime));
/*     */     
/*  96 */     this.selectionDateUntil = new DateSelectionElement(context.createID(), 0, context.getLocale());
/*  97 */     addElement((HtmlElement)this.selectionDateUntil);
/*  98 */     this.selectionDateUntil.setValue(new Date(System.currentTimeMillis()));
/*     */     
/* 100 */     this.inputUsernamePattern = new TextInputElement(context.createID());
/* 101 */     addElement((HtmlElement)this.inputUsernamePattern);
/*     */     
/* 103 */     this.selectionStatus = new SelectBoxSelectionElement(context.createID(), true, new DataRetrievalAbstraction.DataCallback()
/*     */         {
/*     */           private List data;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public List getData() {
/* 113 */             return this.data;
/*     */           }
/*     */         },  1)
/*     */       {
/*     */         protected String getDisplayValue(Object option) {
/* 118 */           if (option == null)
/* 119 */             return context.getLabel("no.selection"); 
/* 120 */           if (option.equals(Boolean.TRUE)) {
/* 121 */             return context.getLabel("allow");
/*     */           }
/* 123 */           return context.getLabel("deny");
/*     */         }
/*     */       };
/*     */     
/* 127 */     addElement((HtmlElement)this.selectionStatus);
/*     */     
/* 129 */     this.inputUserGroupPattern = new TextInputElement(context.createID());
/* 130 */     addElement((HtmlElement)this.inputUserGroupPattern);
/*     */     
/* 132 */     this.buttonSearch = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/* 135 */           return context.getLabel("search");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 140 */             SearchInputPanel.this.getPanelStack().push((HtmlElement)SearchInputPanel.this.executeSearch());
/*     */           }
/* 142 */           catch (Exception e) {
/* 143 */             SearchInputPanel.log.error("...unable to execute search - exception:" + e, e);
/*     */           } 
/* 145 */           return null;
/*     */         }
/*     */       };
/*     */     
/* 149 */     addElement((HtmlElement)this.buttonSearch);
/*     */   }
/*     */ 
/*     */   
/*     */   private SearchResultPanel executeSearch() throws Exception {
/* 154 */     log.debug("executing search....");
/* 155 */     log.debug("...retrieving backend filter...");
/* 156 */     final LoginLog.Query.BackendFilter backendFilter = getBackendFilter();
/* 157 */     log.debug("...retrieving entry filter ...");
/* 158 */     final Filter filter = getEntryFilter();
/* 159 */     log.debug("...sending query request ...");
/* 160 */     AsynchronousExecution asyncExecution = AsynchronousExecution.start(new AsynchronousExecution.Operation()
/*     */         {
/*     */           public Object execute(Object[] input) throws Exception {
/* 163 */             return LoginLogFacade.getInstance().getEntries(backendFilter, filter, SearchInputPanel.this.hitLimit);
/*     */           }
/*     */         }null);
/*     */ 
/*     */ 
/*     */     
/* 169 */     Collection entries = null;
/* 170 */     boolean truncated = false;
/*     */     try {
/* 172 */       if (this.maximumSearchDuration == -1L) {
/* 173 */         entries = (Collection)asyncExecution.getResult();
/*     */       } else {
/*     */         try {
/* 176 */           entries = (Collection)asyncExecution.getResult(this.maximumSearchDuration, true);
/* 177 */         } catch (com.eoos.thread.AsynchronousExecution.IneffectiveAbortionException e) {
/* 178 */           truncated = true;
/*     */         } 
/*     */       } 
/* 181 */       if (this.hitLimit != -1 && entries.size() >= this.hitLimit) {
/* 182 */         truncated = true;
/*     */       }
/* 184 */     } catch (com.eoos.gm.tis2web.frame.login.log.LoginLog.Query.AbortionException e) {
/* 185 */       log.warn("...search aborted, working with partial result");
/* 186 */       entries = e.getProcessedEntries();
/* 187 */       truncated = true;
/* 188 */     } catch (InterruptedException e) {
/* 189 */       truncated = true;
/*     */     } 
/*     */     
/* 192 */     return new SearchResultPanel(this.context, entries, truncated);
/*     */   }
/*     */   
/*     */   public HtmlElementStack getPanelStack() {
/* 196 */     HtmlElementContainer container = getContainer();
/* 197 */     while (!(container instanceof HtmlElementStack) && container != null) {
/* 198 */       container = container.getContainer();
/*     */     }
/* 200 */     return (HtmlElementStack)container;
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 204 */     StringBuffer retValue = new StringBuffer(template);
/* 205 */     StringUtilities.replace(retValue, "{MESSAGE}", (this.message != null) ? this.message : "");
/* 206 */     StringUtilities.replace(retValue, "{LABEL_DATE_FROM}", this.context.getLabel("date.filter.start") + ":");
/* 207 */     StringUtilities.replace(retValue, "{INPUT_DATE_FROM}", this.selectionDateFrom.getHtmlCode(params));
/*     */     
/* 209 */     StringUtilities.replace(retValue, "{LABEL_DATE_UNTIL}", this.context.getLabel("date.filter.end") + ":");
/* 210 */     StringUtilities.replace(retValue, "{INPUT_DATE_UNTIL}", this.selectionDateUntil.getHtmlCode(params));
/*     */     
/* 212 */     StringUtilities.replace(retValue, "{LABEL_USERNAME}", this.context.getLabel("username") + ":");
/* 213 */     StringUtilities.replace(retValue, "{INPUT_USERNAME}", this.inputUsernamePattern.getHtmlCode(params));
/*     */     
/* 215 */     StringUtilities.replace(retValue, "{LABEL_STATUS}", this.context.getLabel("status") + ":");
/* 216 */     StringUtilities.replace(retValue, "{INPUT_STATUS}", this.selectionStatus.getHtmlCode(params));
/*     */     
/* 218 */     StringUtilities.replace(retValue, "{LABEL_USERGROUP}", this.context.getLabel("usergroup") + ":");
/* 219 */     StringUtilities.replace(retValue, "{INPUT_USERGROUP}", this.inputUserGroupPattern.getHtmlCode(params));
/*     */     
/* 221 */     StringUtilities.replace(retValue, "{BUTTON_SEARCH}", this.buttonSearch.getHtmlCode(params));
/* 222 */     return retValue.toString();
/*     */   }
/*     */   
/*     */   public LoginLog.Query.BackendFilter getBackendFilter() {
/* 226 */     final Date from = (Date)this.selectionDateFrom.getValue();
/* 227 */     final Date until = (Date)this.selectionDateUntil.getValue();
/*     */     
/* 229 */     String username = (String)this.inputUsernamePattern.getValue();
/* 230 */     if (username != null) {
/* 231 */       username = username.replace('*', '%');
/* 232 */       username = username.replace('?', '_');
/*     */     } 
/* 234 */     String usergroup = (String)this.inputUserGroupPattern.getValue();
/* 235 */     if (usergroup != null) {
/* 236 */       usergroup = usergroup.replace('*', '%');
/* 237 */       usergroup = usergroup.replace('?', '_');
/*     */     } 
/*     */     
/* 240 */     final String fName = Util.isNullOrEmpty(username) ? null : username;
/* 241 */     final String fGroup = Util.isNullOrEmpty(usergroup) ? null : usergroup;
/*     */     
/* 243 */     return new LoginLog.Query.BackendFilter()
/*     */       {
/*     */         public Long getTimestampMAX() {
/* 246 */           return (until != null) ? Long.valueOf(until.getTime()) : null;
/*     */         }
/*     */         
/*     */         public Long getTimestampMIN() {
/* 250 */           return (from != null) ? Long.valueOf(from.getTime()) : null;
/*     */         }
/*     */         
/*     */         public String getUsergroup() {
/* 254 */           return fGroup;
/*     */         }
/*     */         
/*     */         public String getUsername() {
/* 258 */           return fName;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public Filter getEntryFilter() {
/*     */     FilterLinkage filterLinkage;
/* 265 */     Filter retValue = null;
/*     */     
/* 267 */     final Boolean successfulLogin = (Boolean)this.selectionStatus.getValue();
/* 268 */     if (successfulLogin != null) {
/*     */       
/* 270 */       LoginLog.Query.EntryFilter entryFilter1, entryFilter2 = new LoginLog.Query.EntryFilter()
/*     */         {
/*     */           protected boolean include(LoginLog.Entry entry) {
/*     */             try {
/* 274 */               return (entry.successfulLogin() == successfulLogin.booleanValue());
/* 275 */             } catch (Exception e) {
/* 276 */               SearchInputPanel.log.warn("...unable to determine filter status of " + String.valueOf(entry) + " - exception:" + e);
/* 277 */               return false;
/*     */             } 
/*     */           }
/*     */         };
/*     */ 
/*     */       
/* 283 */       if (retValue == null) {
/* 284 */         entryFilter1 = entryFilter2;
/*     */       } else {
/* 286 */         filterLinkage = new FilterLinkage((Filter)entryFilter1, (Filter)entryFilter2, 2);
/*     */       } 
/*     */     } 
/*     */     
/* 290 */     return (Filter)filterLinkage;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\admin\logi\\ui\html\search\input\SearchInputPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */