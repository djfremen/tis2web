/*     */ package com.eoos.gm.tis2web.swdl.server.datamodel.system.metrics.admin.ui.html.search.result;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.SimpleConfirmationMessageBox;
/*     */ import com.eoos.gm.tis2web.swdl.server.datamodel.system.metrics.SWDLMetricsLogFacade;
/*     */ import com.eoos.gm.tis2web.swdl.server.datamodel.system.metrics.util.SWDLMetricsLogUtil;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.HtmlElementStack;
/*     */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*     */ import com.eoos.html.element.gtwo.PagedElement;
/*     */ import com.eoos.html.element.gtwo.SelectBoxSelectionElement;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.util.v2.StringUtilities;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class SearchResultPanel
/*     */   extends HtmlElementContainerBase
/*     */ {
/*  28 */   private static final Logger log = Logger.getLogger(SearchResultPanel.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  33 */       template = ApplicationContext.getInstance().loadFile(SearchResultPanel.class, "searchresultpanel.html", null).toString();
/*  34 */     } catch (Exception e) {
/*  35 */       log.error("error loading template - error:" + e, e);
/*  36 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ClientContext context;
/*  42 */   private List entries = null;
/*     */   
/*  44 */   private EntriesListElement listElement = null;
/*     */   
/*  46 */   private HtmlElement listElementPagedFront = null;
/*     */   
/*  48 */   private static final Object ACTION_DELETE = new Object();
/*     */   
/*  50 */   private static final Object ACTION_EXPORT = new Object();
/*     */   
/*  52 */   private static final Object ACTION_EXPORT_METRICS = new Object();
/*     */   
/*  54 */   private SelectBoxSelectionElement selectionAction = null;
/*     */   
/*  56 */   private ClickButtonElement buttonExecuteAction = null;
/*     */   
/*  58 */   private ClickButtonElement buttonMarkAll = null;
/*     */   
/*  60 */   private ClickButtonElement buttonReset = null;
/*     */   
/*  62 */   private ClickButtonElement buttonInvert = null;
/*     */   
/*     */   private ClickButtonElement buttonNewSearch;
/*     */   
/*     */   private boolean truncated = false;
/*     */ 
/*     */   
/*     */   public SearchResultPanel(final ClientContext context, Collection<?> entries, boolean truncated) {
/*  70 */     this.context = context;
/*  71 */     if (entries != null && entries.size() > 0) {
/*  72 */       this.entries = new ArrayList(entries);
/*  73 */       this.truncated = truncated;
/*  74 */       this.listElement = new EntriesListElement(this.entries, context);
/*  75 */       this.listElementPagedFront = (HtmlElement)new PagedElement(context.createID(), (HtmlElement)this.listElement, 20, 20);
/*  76 */       addElement(this.listElementPagedFront);
/*     */       
/*  78 */       this.selectionAction = new SelectBoxSelectionElement(context.createID(), true, new DataRetrievalAbstraction.DataCallback()
/*     */           {
/*  80 */             private List data = Arrays.asList(new Object[] { SearchResultPanel.access$000(), SearchResultPanel.access$100() }, );
/*     */             
/*     */             public List getData() {
/*  83 */               return this.data;
/*     */             }
/*     */           },  1)
/*     */         {
/*     */           protected String getDisplayValue(Object option) {
/*  88 */             if (option == SearchResultPanel.ACTION_DELETE)
/*  89 */               return context.getLabel("delete"); 
/*  90 */             if (option == SearchResultPanel.ACTION_EXPORT)
/*  91 */               return context.getLabel("export"); 
/*  92 */             if (option == SearchResultPanel.ACTION_EXPORT_METRICS) {
/*  93 */               return context.getLabel("export.metrics");
/*     */             }
/*  95 */             return "-";
/*     */           }
/*     */         };
/*     */       
/*  99 */       addElement((HtmlElement)this.selectionAction);
/* 100 */       this.selectionAction.setValue(ACTION_EXPORT);
/*     */       
/* 102 */       this.buttonExecuteAction = new ClickButtonElement(context.createID(), null)
/*     */         {
/*     */           protected String getLabel() {
/* 105 */             return context.getLabel("execute");
/*     */           }
/*     */           
/*     */           public Object onClick(Map submitParams) {
/*     */             try {
/* 110 */               return SearchResultPanel.this.executeSelectedAction();
/* 111 */             } catch (Exception e) {
/* 112 */               SearchResultPanel.log.error("...unable to execute action - exception: " + e, e);
/* 113 */               return null;
/*     */             } 
/*     */           }
/*     */         };
/*     */       
/* 118 */       addElement((HtmlElement)this.buttonExecuteAction);
/*     */     } 
/*     */     
/* 121 */     this.buttonMarkAll = new ClickButtonElement(context.createID(), null) {
/*     */         protected String getLabel() {
/* 123 */           return context.getLabel("mark.all");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 128 */             SearchResultPanel.this.listElement.markAll();
/* 129 */           } catch (Exception e) {
/* 130 */             SearchResultPanel.log.error("...unable to mark all entries - exception:" + e, e);
/*     */           } 
/* 132 */           return null;
/*     */         }
/*     */       };
/* 135 */     addElement((HtmlElement)this.buttonMarkAll);
/*     */     
/* 137 */     this.buttonReset = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/* 140 */           return context.getLabel("reset.marking");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 145 */             SearchResultPanel.this.listElement.unmarkAll();
/* 146 */           } catch (Exception e) {
/* 147 */             SearchResultPanel.log.error("...unable to unmark all entries - exception:" + e, e);
/*     */           } 
/* 149 */           return null;
/*     */         }
/*     */       };
/*     */     
/* 153 */     addElement((HtmlElement)this.buttonReset);
/*     */     
/* 155 */     this.buttonInvert = new ClickButtonElement(context.createID(), null) {
/*     */         protected String getLabel() {
/* 157 */           return context.getLabel("invert.marking");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 162 */             SearchResultPanel.this.listElement.invertMarking();
/* 163 */           } catch (Exception e) {
/* 164 */             SearchResultPanel.log.error("...unable to invert marking - exception:" + e, e);
/*     */           } 
/* 166 */           return null;
/*     */         }
/*     */       };
/* 169 */     addElement((HtmlElement)this.buttonInvert);
/*     */     
/* 171 */     this.buttonNewSearch = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/* 174 */           return context.getLabel("new.search");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 179 */             SearchResultPanel.this.getPanelStack().pop();
/* 180 */           } catch (Exception e) {
/* 181 */             SearchResultPanel.log.error("...unable to process request for 'new search' - exception:" + e, e);
/*     */           } 
/* 183 */           return null;
/*     */         }
/*     */       };
/*     */     
/* 187 */     addElement((HtmlElement)this.buttonNewSearch);
/*     */   }
/*     */ 
/*     */   
/*     */   private Object executeSelectedAction() throws Exception {
/* 192 */     Collection entries = this.listElement.getMarkedEntries();
/* 193 */     if (entries != null && entries.size() > 0) {
/* 194 */       if (this.selectionAction.getValue() == ACTION_DELETE)
/* 195 */         return executeDeletion(entries); 
/* 196 */       if (this.selectionAction.getValue() == ACTION_EXPORT)
/* 197 */         return executeExport(entries); 
/* 198 */       if (this.selectionAction.getValue() == ACTION_EXPORT_METRICS) {
/* 199 */         return executeExportMetrics(entries);
/*     */       }
/*     */     } 
/*     */     
/* 203 */     return null;
/*     */   }
/*     */   
/*     */   private Object executeDeletion(final Collection entries) throws Exception {
/* 207 */     return new SimpleConfirmationMessageBox(this.context, this.context.getLabel("confirmation"), this.context.getMessage("confirmation.deletion"))
/*     */       {
/*     */         protected Object onCancel(Map params) {
/* 210 */           return SearchResultPanel.this.getTopLevelContainer();
/*     */         }
/*     */         
/*     */         protected Object onOK(Map params) {
/*     */           try {
/* 215 */             SearchResultPanel.log.info("deleting marked entries (" + entries.size() + ")");
/* 216 */             SWDLMetricsLogFacade.getInstance().delete(entries);
/* 217 */           } catch (Exception e) {
/* 218 */             SearchResultPanel.log.error("....unable to delete entries - exception:" + e, e);
/*     */           } 
/* 220 */           HtmlElementContainer topLevel = SearchResultPanel.this.getTopLevelContainer();
/*     */ 
/*     */           
/* 223 */           SearchResultPanel.this.getPanelStack().pop();
/* 224 */           return topLevel;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object executeExport(Collection entries) throws Exception {
/* 233 */     Object retValue = null;
/* 234 */     if (entries != null && entries.size() > 0) {
/*     */       try {
/* 236 */         log.info("exporting marked entries (" + entries.size() + ")...");
/* 237 */         byte[] statistic = SWDLMetricsLogUtil.createExport(entries);
/* 238 */         ResultObject.FileProperties props = new ResultObject.FileProperties();
/* 239 */         props.filename = "swdl.log";
/* 240 */         props.mime = "text/plain; charset=utf-8";
/* 241 */         props.data = statistic;
/*     */         
/* 243 */         retValue = new ResultObject(13, true, true, props);
/* 244 */       } catch (Exception e) {
/* 245 */         log.error("....unable to export entries - exception:" + e, e);
/*     */       } 
/*     */     }
/* 248 */     return retValue;
/*     */   }
/*     */ 
/*     */   
/*     */   private Object executeExportMetrics(Collection entries) throws Exception {
/* 253 */     Object retValue = null;
/* 254 */     if (entries != null && entries.size() > 0) {
/*     */       try {
/* 256 */         log.info("exporting metrics based on marked entries (" + entries.size() + ")...");
/* 257 */         byte[] statistic = SWDLMetricsLogUtil.createStatistic(entries);
/* 258 */         ResultObject.FileProperties props = new ResultObject.FileProperties();
/* 259 */         props.filename = "swdl-metrics.txt";
/* 260 */         props.mime = "text/plain; charset=utf-8";
/* 261 */         props.data = statistic;
/*     */         
/* 263 */         retValue = new ResultObject(13, true, true, props);
/* 264 */       } catch (Exception e) {
/* 265 */         log.error("....unable to export entries - exception:" + e, e);
/*     */       } 
/*     */     }
/* 268 */     return retValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public HtmlElementStack getPanelStack() {
/* 273 */     HtmlElementContainer container = getContainer();
/* 274 */     while (!(container instanceof HtmlElementStack) && container != null) {
/* 275 */       container = container.getContainer();
/*     */     }
/* 277 */     return (HtmlElementStack)container;
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 281 */     StringBuffer retvalue = new StringBuffer(template);
/* 282 */     StringUtilities.replace(retvalue, "{MESSAGE}", this.truncated ? this.context.getMessage("search.result.truncated") : "");
/* 283 */     if (this.listElementPagedFront != null) {
/* 284 */       StringUtilities.replace(retvalue, "{LIST}", this.listElementPagedFront.getHtmlCode(params));
/* 285 */       StringUtilities.replace(retvalue, "{BUTTON_MARK_ALL}", this.buttonMarkAll.getHtmlCode(params));
/* 286 */       StringUtilities.replace(retvalue, "{BUTTON_INVERT}", this.buttonInvert.getHtmlCode(params));
/* 287 */       StringUtilities.replace(retvalue, "{BUTTON_RESET}", this.buttonReset.getHtmlCode(params));
/*     */     } else {
/*     */       
/* 290 */       StringUtilities.replace(retvalue, "{LIST}", this.context.getMessage("empty.search.result"));
/* 291 */       StringUtilities.replace(retvalue, "{BUTTON_MARK_ALL}", "");
/* 292 */       StringUtilities.replace(retvalue, "{BUTTON_INVERT}", "");
/* 293 */       StringUtilities.replace(retvalue, "{BUTTON_RESET}", "");
/*     */     } 
/*     */     
/* 296 */     if (this.selectionAction != null) {
/* 297 */       StringUtilities.replace(retvalue, "{INPUT_ACTION}", this.selectionAction.getHtmlCode(params));
/* 298 */       StringUtilities.replace(retvalue, "{BUTTON_ACTION}", this.buttonExecuteAction.getHtmlCode(params));
/*     */     } else {
/* 300 */       StringUtilities.replace(retvalue, "{INPUT_ACTION}", "");
/* 301 */       StringUtilities.replace(retvalue, "{BUTTON_ACTION}", "");
/*     */     } 
/* 303 */     StringUtilities.replace(retvalue, "{NEW_SEARCH_BUTTON}", this.buttonNewSearch.getHtmlCode(params));
/* 304 */     return retvalue.toString();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\server\datamodel\system\metrics\admi\\ui\html\search\result\SearchResultPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */