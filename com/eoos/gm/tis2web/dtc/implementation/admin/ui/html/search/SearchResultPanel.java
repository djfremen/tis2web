/*     */ package com.eoos.gm.tis2web.dtc.implementation.admin.ui.html.search;
/*     */ 
/*     */ import com.eoos.gm.tis2web.dtc.implementation.DTCLogFacade;
/*     */ import com.eoos.gm.tis2web.dtc.implementation.util.DTCLogUtil;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.SimpleConfirmationMessageBox;
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
/*     */ import java.io.ByteArrayOutputStream;
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
/*  29 */   private static final Logger log = Logger.getLogger(SearchResultPanel.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  34 */       template = ApplicationContext.getInstance().loadFile(SearchResultPanel.class, "searchresultpanel.html", null).toString();
/*  35 */     } catch (Exception e) {
/*  36 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ClientContext context;
/*  42 */   private List entries = null;
/*     */   
/*  44 */   private DTCListElement listElement = null;
/*     */   
/*  46 */   private HtmlElement listElementPagedFront = null;
/*     */   
/*  48 */   private static final Object ACTION_DELETE = new Object();
/*     */   
/*  50 */   private static final Object ACTION_EXPORT = new Object();
/*     */   
/*  52 */   private SelectBoxSelectionElement selectionAction = null;
/*     */   
/*  54 */   private ClickButtonElement buttonExecuteAction = null;
/*     */   
/*  56 */   private ClickButtonElement buttonMarkAll = null;
/*     */   
/*  58 */   private ClickButtonElement buttonReset = null;
/*     */   
/*  60 */   private ClickButtonElement buttonInvert = null;
/*     */   
/*     */   private ClickButtonElement buttonNewSearch;
/*     */   
/*     */   private boolean truncated = false;
/*     */ 
/*     */   
/*     */   public SearchResultPanel(final ClientContext context, Collection<?> entries, boolean truncated) {
/*  68 */     this.context = context;
/*  69 */     this.truncated = truncated;
/*     */     
/*  71 */     if (entries != null && entries.size() > 0) {
/*  72 */       this.entries = new ArrayList(entries);
/*  73 */       this.listElement = new DTCListElement(this.entries, context);
/*  74 */       this.listElementPagedFront = (HtmlElement)new PagedElement(context.createID(), (HtmlElement)this.listElement, 20, 20);
/*  75 */       addElement(this.listElementPagedFront);
/*     */       
/*  77 */       this.selectionAction = new SelectBoxSelectionElement(context.createID(), true, new DataRetrievalAbstraction.DataCallback()
/*     */           {
/*  79 */             private List data = Arrays.asList(new Object[] { SearchResultPanel.access$000(), SearchResultPanel.access$100() }, );
/*     */             
/*     */             public List getData() {
/*  82 */               return this.data;
/*     */             }
/*     */           },  1)
/*     */         {
/*     */           protected String getDisplayValue(Object option) {
/*  87 */             if (option == SearchResultPanel.ACTION_DELETE)
/*  88 */               return context.getLabel("delete"); 
/*  89 */             if (option == SearchResultPanel.ACTION_EXPORT) {
/*  90 */               return context.getLabel("export");
/*     */             }
/*  92 */             return "-";
/*     */           }
/*     */         };
/*     */       
/*  96 */       addElement((HtmlElement)this.selectionAction);
/*  97 */       this.selectionAction.setValue(ACTION_EXPORT);
/*     */       
/*  99 */       this.buttonExecuteAction = new ClickButtonElement(context.createID(), null)
/*     */         {
/*     */           protected String getLabel() {
/* 102 */             return context.getLabel("execute");
/*     */           }
/*     */           
/*     */           public Object onClick(Map submitParams) {
/*     */             try {
/* 107 */               return SearchResultPanel.this.executeSelectedAction();
/* 108 */             } catch (Exception e) {
/* 109 */               SearchResultPanel.log.error("...unable to execute action - exception: " + e, e);
/* 110 */               return null;
/*     */             } 
/*     */           }
/*     */         };
/*     */       
/* 115 */       addElement((HtmlElement)this.buttonExecuteAction);
/*     */     } 
/*     */     
/* 118 */     this.buttonMarkAll = new ClickButtonElement(context.createID(), null) {
/*     */         protected String getLabel() {
/* 120 */           return context.getLabel("mark.all");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 125 */             SearchResultPanel.this.listElement.markAll();
/* 126 */           } catch (Exception e) {
/* 127 */             SearchResultPanel.log.error("...unable to mark all entries - exception:" + e, e);
/*     */           } 
/* 129 */           return null;
/*     */         }
/*     */       };
/* 132 */     addElement((HtmlElement)this.buttonMarkAll);
/*     */     
/* 134 */     this.buttonReset = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/* 137 */           return context.getLabel("reset.marking");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 142 */             SearchResultPanel.this.listElement.unmarkAll();
/* 143 */           } catch (Exception e) {
/* 144 */             SearchResultPanel.log.error("...unable to unmark all entries - exception:" + e, e);
/*     */           } 
/* 146 */           return null;
/*     */         }
/*     */       };
/*     */     
/* 150 */     addElement((HtmlElement)this.buttonReset);
/*     */     
/* 152 */     this.buttonInvert = new ClickButtonElement(context.createID(), null) {
/*     */         protected String getLabel() {
/* 154 */           return context.getLabel("invert.marking");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 159 */             SearchResultPanel.this.listElement.invertMarking();
/* 160 */           } catch (Exception e) {
/* 161 */             SearchResultPanel.log.error("...unable to invert marking - exception:" + e, e);
/*     */           } 
/* 163 */           return null;
/*     */         }
/*     */       };
/* 166 */     addElement((HtmlElement)this.buttonInvert);
/*     */     
/* 168 */     this.buttonNewSearch = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/* 171 */           return context.getLabel("new.search");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 176 */             SearchResultPanel.this.getPanelStack().pop();
/* 177 */           } catch (Exception e) {
/* 178 */             SearchResultPanel.log.error("...unable to process request for 'new search' - exception:" + e, e);
/*     */           } 
/* 180 */           return null;
/*     */         }
/*     */       };
/*     */     
/* 184 */     addElement((HtmlElement)this.buttonNewSearch);
/*     */   }
/*     */ 
/*     */   
/*     */   private Object executeSelectedAction() throws Exception {
/* 189 */     Collection entries = this.listElement.getMarkedEntries();
/* 190 */     if (entries != null && entries.size() > 0) {
/* 191 */       if (this.selectionAction.getValue() == ACTION_DELETE)
/* 192 */         return executeDeletion(entries); 
/* 193 */       if (this.selectionAction.getValue() == ACTION_EXPORT) {
/* 194 */         return executeExport(entries);
/*     */       }
/*     */     } 
/*     */     
/* 198 */     return null;
/*     */   }
/*     */   
/*     */   private Object executeDeletion(final Collection entries) throws Exception {
/* 202 */     return new SimpleConfirmationMessageBox(this.context, this.context.getLabel("confirmation"), this.context.getMessage("confirmation.deletion"))
/*     */       {
/*     */         protected Object onCancel(Map params) {
/* 205 */           return SearchResultPanel.this.getTopLevelContainer();
/*     */         }
/*     */         
/*     */         protected Object onOK(Map params) {
/*     */           try {
/* 210 */             SearchResultPanel.log.info("deleting marked entries (" + entries.size() + ")");
/* 211 */             DTCLogFacade.getInstance().delete(entries);
/* 212 */           } catch (Exception e) {
/* 213 */             SearchResultPanel.log.error("....unable to delete entries - exception:" + e, e);
/*     */           } 
/* 215 */           HtmlElementContainer topLevel = SearchResultPanel.this.getTopLevelContainer();
/*     */ 
/*     */           
/* 218 */           SearchResultPanel.this.getPanelStack().pop();
/* 219 */           return topLevel;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object executeExport(Collection entries) throws Exception {
/* 228 */     Object retValue = null;
/* 229 */     if (entries != null && entries.size() > 0) {
/*     */       try {
/* 231 */         ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 232 */         DTCLogUtil.createExport(entries, baos);
/* 233 */         baos.close();
/* 234 */         ResultObject.FileProperties props = new ResultObject.FileProperties();
/* 235 */         props.filename = "dtc-export.dat";
/* 236 */         props.mime = "application/octet-stream";
/* 237 */         props.data = baos.toByteArray();
/* 238 */         retValue = new ResultObject(13, false, true, props);
/* 239 */       } catch (Exception e) {
/* 240 */         log.error("unable to export entries - exception:" + e, e);
/*     */       } 
/*     */     }
/* 243 */     return retValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public HtmlElementStack getPanelStack() {
/* 248 */     HtmlElementContainer container = getContainer();
/* 249 */     while (!(container instanceof HtmlElementStack) && container != null) {
/* 250 */       container = container.getContainer();
/*     */     }
/* 252 */     return (HtmlElementStack)container;
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 256 */     StringBuffer retvalue = new StringBuffer(template);
/* 257 */     StringUtilities.replace(retvalue, "{MESSAGE}", this.truncated ? this.context.getMessage("search.result.truncated") : "");
/* 258 */     if (this.listElementPagedFront != null) {
/* 259 */       StringUtilities.replace(retvalue, "{LIST}", this.listElementPagedFront.getHtmlCode(params));
/* 260 */       StringUtilities.replace(retvalue, "{BUTTON_MARK_ALL}", this.buttonMarkAll.getHtmlCode(params));
/* 261 */       StringUtilities.replace(retvalue, "{BUTTON_INVERT}", this.buttonInvert.getHtmlCode(params));
/* 262 */       StringUtilities.replace(retvalue, "{BUTTON_RESET}", this.buttonReset.getHtmlCode(params));
/*     */     } else {
/*     */       
/* 265 */       StringUtilities.replace(retvalue, "{LIST}", this.context.getMessage("empty.search.result"));
/* 266 */       StringUtilities.replace(retvalue, "{BUTTON_MARK_ALL}", "");
/* 267 */       StringUtilities.replace(retvalue, "{BUTTON_INVERT}", "");
/* 268 */       StringUtilities.replace(retvalue, "{BUTTON_RESET}", "");
/*     */     } 
/*     */     
/* 271 */     if (this.selectionAction != null) {
/* 272 */       StringUtilities.replace(retvalue, "{INPUT_ACTION}", this.selectionAction.getHtmlCode(params));
/* 273 */       StringUtilities.replace(retvalue, "{BUTTON_ACTION}", this.buttonExecuteAction.getHtmlCode(params));
/*     */     } else {
/* 275 */       StringUtilities.replace(retvalue, "{INPUT_ACTION}", "");
/* 276 */       StringUtilities.replace(retvalue, "{BUTTON_ACTION}", "");
/*     */     } 
/* 278 */     StringUtilities.replace(retvalue, "{NEW_SEARCH_BUTTON}", this.buttonNewSearch.getHtmlCode(params));
/* 279 */     return retvalue.toString();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\dtc\implementation\admi\\ui\html\search\SearchResultPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */