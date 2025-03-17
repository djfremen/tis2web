/*     */ package com.eoos.gm.tis2web.sps.server.implementation.log.admin.ui.html.search.result;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.SimpleConfirmationMessageBox;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.log.SPSEventLogFacade;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.log.util.SPSEventLogUtil;
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
/*  35 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ClientContext context;
/*  41 */   private List entries = null;
/*     */   
/*  43 */   private EntriesListElement listElement = null;
/*     */   
/*  45 */   private HtmlElement listElementPagedFront = null;
/*     */   
/*  47 */   private static final Object ACTION_DELETE = new Object();
/*     */   
/*  49 */   private static final Object ACTION_EXPORT = new Object();
/*     */   
/*  51 */   private SelectBoxSelectionElement selectionAction = null;
/*     */   
/*  53 */   private ClickButtonElement buttonExecuteAction = null;
/*     */   
/*  55 */   private ClickButtonElement buttonMarkAll = null;
/*     */   
/*  57 */   private ClickButtonElement buttonReset = null;
/*     */   
/*  59 */   private ClickButtonElement buttonInvert = null;
/*     */   
/*     */   private ClickButtonElement buttonNewSearch;
/*     */   
/*     */   private boolean truncated = false;
/*     */ 
/*     */   
/*     */   public SearchResultPanel(final ClientContext context, Collection<?> entries, boolean truncated) {
/*  67 */     this.context = context;
/*  68 */     this.truncated = truncated;
/*     */     
/*  70 */     if (entries != null && entries.size() > 0) {
/*  71 */       this.entries = new ArrayList(entries);
/*  72 */       this.listElement = new EntriesListElement(this.entries, context);
/*  73 */       this.listElementPagedFront = (HtmlElement)new PagedElement(context.createID(), (HtmlElement)this.listElement, 20, 20);
/*  74 */       addElement(this.listElementPagedFront);
/*     */       
/*  76 */       this.selectionAction = new SelectBoxSelectionElement(context.createID(), true, new DataRetrievalAbstraction.DataCallback()
/*     */           {
/*  78 */             private List data = Arrays.asList(new Object[] { SearchResultPanel.access$000(), SearchResultPanel.access$100() }, );
/*     */             
/*     */             public List getData() {
/*  81 */               return this.data;
/*     */             }
/*     */           },  1)
/*     */         {
/*     */           protected String getDisplayValue(Object option) {
/*  86 */             if (option == SearchResultPanel.ACTION_DELETE)
/*  87 */               return context.getLabel("delete"); 
/*  88 */             if (option == SearchResultPanel.ACTION_EXPORT) {
/*  89 */               return context.getLabel("export");
/*     */             }
/*  91 */             return "-";
/*     */           }
/*     */         };
/*     */       
/*  95 */       addElement((HtmlElement)this.selectionAction);
/*  96 */       this.selectionAction.setValue(ACTION_EXPORT);
/*     */       
/*  98 */       this.buttonExecuteAction = new ClickButtonElement(context.createID(), null)
/*     */         {
/*     */           protected String getLabel() {
/* 101 */             return context.getLabel("execute");
/*     */           }
/*     */           
/*     */           public Object onClick(Map submitParams) {
/*     */             try {
/* 106 */               return SearchResultPanel.this.executeSelectedAction();
/* 107 */             } catch (Exception e) {
/* 108 */               SearchResultPanel.log.error("...unable to execute action - exception: " + e, e);
/* 109 */               return null;
/*     */             } 
/*     */           }
/*     */         };
/*     */       
/* 114 */       addElement((HtmlElement)this.buttonExecuteAction);
/*     */     } 
/*     */     
/* 117 */     this.buttonMarkAll = new ClickButtonElement(context.createID(), null) {
/*     */         protected String getLabel() {
/* 119 */           return context.getLabel("mark.all");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 124 */             SearchResultPanel.this.listElement.markAll();
/* 125 */           } catch (Exception e) {
/* 126 */             SearchResultPanel.log.error("...unable to mark all entries - exception:" + e, e);
/*     */           } 
/* 128 */           return null;
/*     */         }
/*     */       };
/* 131 */     addElement((HtmlElement)this.buttonMarkAll);
/*     */     
/* 133 */     this.buttonReset = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/* 136 */           return context.getLabel("reset.marking");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 141 */             SearchResultPanel.this.listElement.unmarkAll();
/* 142 */           } catch (Exception e) {
/* 143 */             SearchResultPanel.log.error("...unable to unmark all entries - exception:" + e, e);
/*     */           } 
/* 145 */           return null;
/*     */         }
/*     */       };
/*     */     
/* 149 */     addElement((HtmlElement)this.buttonReset);
/*     */     
/* 151 */     this.buttonInvert = new ClickButtonElement(context.createID(), null) {
/*     */         protected String getLabel() {
/* 153 */           return context.getLabel("invert.marking");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 158 */             SearchResultPanel.this.listElement.invertMarking();
/* 159 */           } catch (Exception e) {
/* 160 */             SearchResultPanel.log.error("...unable to invert marking - exception:" + e, e);
/*     */           } 
/* 162 */           return null;
/*     */         }
/*     */       };
/* 165 */     addElement((HtmlElement)this.buttonInvert);
/*     */     
/* 167 */     this.buttonNewSearch = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/* 170 */           return context.getLabel("new.search");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 175 */             SearchResultPanel.this.getPanelStack().pop();
/* 176 */           } catch (Exception e) {
/* 177 */             SearchResultPanel.log.error("...unable to process request for 'new search' - exception:" + e, e);
/*     */           } 
/* 179 */           return null;
/*     */         }
/*     */       };
/*     */     
/* 183 */     addElement((HtmlElement)this.buttonNewSearch);
/*     */   }
/*     */ 
/*     */   
/*     */   private Object executeSelectedAction() throws Exception {
/* 188 */     Collection entries = this.listElement.getMarkedEntries();
/* 189 */     if (entries != null && entries.size() > 0) {
/* 190 */       if (this.selectionAction.getValue() == ACTION_DELETE)
/* 191 */         return executeDeletion(entries); 
/* 192 */       if (this.selectionAction.getValue() == ACTION_EXPORT) {
/* 193 */         return executeExport(entries);
/*     */       }
/*     */     } 
/*     */     
/* 197 */     return null;
/*     */   }
/*     */   
/*     */   private Object executeDeletion(final Collection entries) throws Exception {
/* 201 */     return new SimpleConfirmationMessageBox(this.context, this.context.getLabel("confirmation"), this.context.getMessage("confirmation.deletion"))
/*     */       {
/*     */         protected Object onCancel(Map params) {
/* 204 */           return SearchResultPanel.this.getTopLevelContainer();
/*     */         }
/*     */         
/*     */         protected Object onOK(Map params) {
/*     */           try {
/* 209 */             SearchResultPanel.log.info("deleting marked entries (" + entries.size() + ")");
/* 210 */             SPSEventLogFacade.getInstance().delete(entries);
/* 211 */           } catch (Exception e) {
/* 212 */             SearchResultPanel.log.error("....unable to delete entries - exception:" + e, e);
/*     */           } 
/* 214 */           HtmlElementContainer topLevel = SearchResultPanel.this.getTopLevelContainer();
/*     */ 
/*     */           
/* 217 */           SearchResultPanel.this.getPanelStack().pop();
/* 218 */           return topLevel;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object executeExport(Collection entries) throws Exception {
/* 227 */     Object retValue = null;
/* 228 */     if (entries != null && entries.size() > 0) {
/*     */       try {
/* 230 */         byte[] data = SPSEventLogUtil.createExport(entries);
/* 231 */         ResultObject.FileProperties props = new ResultObject.FileProperties();
/* 232 */         props.filename = "sps-event.log";
/* 233 */         props.mime = "text/plain; charset=utf-8";
/* 234 */         props.data = data;
/* 235 */         retValue = new ResultObject(13, true, true, props);
/* 236 */       } catch (Exception e) {
/* 237 */         log.error("unalbe to export entries - exception:" + e, e);
/*     */       } 
/*     */     }
/* 240 */     return retValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public HtmlElementStack getPanelStack() {
/* 245 */     HtmlElementContainer container = getContainer();
/* 246 */     while (!(container instanceof HtmlElementStack) && container != null) {
/* 247 */       container = container.getContainer();
/*     */     }
/* 249 */     return (HtmlElementStack)container;
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 253 */     StringBuffer retvalue = new StringBuffer(template);
/* 254 */     StringUtilities.replace(retvalue, "{MESSAGE}", this.truncated ? this.context.getMessage("search.result.truncated") : "");
/* 255 */     if (this.listElementPagedFront != null) {
/* 256 */       StringUtilities.replace(retvalue, "{LIST}", this.listElementPagedFront.getHtmlCode(params));
/* 257 */       StringUtilities.replace(retvalue, "{BUTTON_MARK_ALL}", this.buttonMarkAll.getHtmlCode(params));
/* 258 */       StringUtilities.replace(retvalue, "{BUTTON_INVERT}", this.buttonInvert.getHtmlCode(params));
/* 259 */       StringUtilities.replace(retvalue, "{BUTTON_RESET}", this.buttonReset.getHtmlCode(params));
/*     */     } else {
/*     */       
/* 262 */       StringUtilities.replace(retvalue, "{LIST}", this.context.getMessage("empty.search.result"));
/* 263 */       StringUtilities.replace(retvalue, "{BUTTON_MARK_ALL}", "");
/* 264 */       StringUtilities.replace(retvalue, "{BUTTON_INVERT}", "");
/* 265 */       StringUtilities.replace(retvalue, "{BUTTON_RESET}", "");
/*     */     } 
/*     */     
/* 268 */     if (this.selectionAction != null) {
/* 269 */       StringUtilities.replace(retvalue, "{INPUT_ACTION}", this.selectionAction.getHtmlCode(params));
/* 270 */       StringUtilities.replace(retvalue, "{BUTTON_ACTION}", this.buttonExecuteAction.getHtmlCode(params));
/*     */     } else {
/* 272 */       StringUtilities.replace(retvalue, "{INPUT_ACTION}", "");
/* 273 */       StringUtilities.replace(retvalue, "{BUTTON_ACTION}", "");
/*     */     } 
/* 275 */     StringUtilities.replace(retvalue, "{NEW_SEARCH_BUTTON}", this.buttonNewSearch.getHtmlCode(params));
/* 276 */     return retvalue.toString();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\log\admi\\ui\html\search\result\SearchResultPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */