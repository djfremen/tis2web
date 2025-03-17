/*     */ package com.eoos.gm.tis2web.frame.dls.server.admin;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.dls.server.DLSServiceServer;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.ErrorMessageBox;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.NotificationMessageBox;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.SimpleConfirmationMessageBox;
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
/*  48 */   private static final Object ACTION_DELETE_SOFTWAREKEYS = new Object();
/*     */   
/*  50 */   private static final Object ACTION_DELETE_LEASES = new Object();
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
/*     */   public SearchResultPanel(final ClientContext context, Collection<?> entries) {
/*  68 */     this.context = context;
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
/*  86 */             if (option == SearchResultPanel.ACTION_DELETE_LEASES)
/*  87 */               return context.getLabel("frame.dls.admin.delete.leases.only"); 
/*  88 */             if (option == SearchResultPanel.ACTION_DELETE_SOFTWAREKEYS) {
/*  89 */               return context.getLabel("frame.dls.admin.delete.softwarekeys.and.leases");
/*     */             }
/*  91 */             return "-";
/*     */           }
/*     */         };
/*     */       
/*  95 */       addElement((HtmlElement)this.selectionAction);
/*  96 */       this.selectionAction.setValue(ACTION_DELETE_LEASES);
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
/*     */   private Object executeSelectedAction() {
/*     */     Object ret;
/*     */     try {
/* 190 */       Collection entries = this.listElement.getMarkedEntries();
/*     */       
/* 192 */       if (entries != null && entries.size() > 0) {
/* 193 */         if (this.selectionAction.getValue() == ACTION_DELETE_LEASES) {
/* 194 */           ret = executeLeaseDeletion(entries);
/* 195 */         } else if (this.selectionAction.getValue() == ACTION_DELETE_SOFTWAREKEYS) {
/* 196 */           ret = executeSWKDeletion(entries);
/*     */         } else {
/* 198 */           throw new IllegalStateException("unknown action");
/*     */         } 
/*     */       } else {
/*     */         
/* 202 */         ret = NotificationMessageBox.createWarningMessage(this.context, null, this.context.getMessage("nothing.selected"), getTopLevelContainer());
/*     */       } 
/* 204 */     } catch (Exception e) {
/* 205 */       log.error("unable to execute action - exception: " + e, e);
/* 206 */       return ErrorMessageBox.create(this.context, getTopLevelContainer());
/*     */     } 
/* 208 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   private Object executeLeaseDeletion(final Collection entries) throws Exception {
/* 213 */     return new SimpleConfirmationMessageBox(this.context, this.context.getLabel("confirmation"), this.context.getMessage("confirmation.deletion"))
/*     */       {
/*     */         protected Object onCancel(Map params) {
/* 216 */           return SearchResultPanel.this.getTopLevelContainer();
/*     */         }
/*     */         
/*     */         protected Object onOK(Map params) {
/*     */           try {
/* 221 */             DLSServiceServer.deleteLeases(entries);
/* 222 */             HtmlElementContainer htmlElementContainer = SearchResultPanel.this.getTopLevelContainer();
/* 223 */             SearchResultPanel.this.getPanelStack().pop();
/* 224 */             return NotificationMessageBox.createInfoMessage((ClientContext)this.context, null, this.context.getMessage("deletion.succeeded"), htmlElementContainer);
/* 225 */           } catch (Exception e) {
/* 226 */             SearchResultPanel.log.error("....unable to delete entries - exception:" + e, e);
/* 227 */             return ErrorMessageBox.create((ClientContext)this.context, SearchResultPanel.this.getTopLevelContainer());
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object executeSWKDeletion(final Collection entries) throws Exception {
/* 238 */     return new SimpleConfirmationMessageBox(this.context, this.context.getLabel("confirmation"), this.context.getMessage("confirmation.deletion"))
/*     */       {
/*     */         protected Object onCancel(Map params) {
/* 241 */           return SearchResultPanel.this.getTopLevelContainer();
/*     */         }
/*     */         
/*     */         protected Object onOK(Map params) {
/*     */           try {
/* 246 */             DLSServiceServer.deleteSoftwareKeys(entries);
/* 247 */             HtmlElementContainer htmlElementContainer = SearchResultPanel.this.getTopLevelContainer();
/* 248 */             SearchResultPanel.this.getPanelStack().pop();
/* 249 */             return NotificationMessageBox.createInfoMessage((ClientContext)this.context, null, this.context.getMessage("deletion.succeeded"), htmlElementContainer);
/* 250 */           } catch (Exception e) {
/* 251 */             SearchResultPanel.log.error("....unable to delete entries - exception:" + e, e);
/* 252 */             return ErrorMessageBox.create((ClientContext)this.context, SearchResultPanel.this.getTopLevelContainer());
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HtmlElementStack getPanelStack() {
/* 262 */     HtmlElementContainer container = getContainer();
/* 263 */     while (!(container instanceof HtmlElementStack) && container != null) {
/* 264 */       container = container.getContainer();
/*     */     }
/* 266 */     return (HtmlElementStack)container;
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 270 */     StringBuffer retvalue = new StringBuffer(template);
/* 271 */     StringUtilities.replace(retvalue, "{MESSAGE}", this.truncated ? this.context.getMessage("search.result.truncated") : "");
/* 272 */     if (this.listElementPagedFront != null) {
/* 273 */       StringUtilities.replace(retvalue, "{LIST}", this.listElementPagedFront.getHtmlCode(params));
/* 274 */       StringUtilities.replace(retvalue, "{BUTTON_MARK_ALL}", this.buttonMarkAll.getHtmlCode(params));
/* 275 */       StringUtilities.replace(retvalue, "{BUTTON_INVERT}", this.buttonInvert.getHtmlCode(params));
/* 276 */       StringUtilities.replace(retvalue, "{BUTTON_RESET}", this.buttonReset.getHtmlCode(params));
/*     */     } else {
/*     */       
/* 279 */       StringUtilities.replace(retvalue, "{LIST}", this.context.getMessage("empty.search.result"));
/* 280 */       StringUtilities.replace(retvalue, "{BUTTON_MARK_ALL}", "");
/* 281 */       StringUtilities.replace(retvalue, "{BUTTON_INVERT}", "");
/* 282 */       StringUtilities.replace(retvalue, "{BUTTON_RESET}", "");
/*     */     } 
/*     */     
/* 285 */     if (this.selectionAction != null) {
/* 286 */       StringUtilities.replace(retvalue, "{INPUT_ACTION}", this.selectionAction.getHtmlCode(params));
/* 287 */       StringUtilities.replace(retvalue, "{BUTTON_ACTION}", this.buttonExecuteAction.getHtmlCode(params));
/*     */     } else {
/* 289 */       StringUtilities.replace(retvalue, "{INPUT_ACTION}", "");
/* 290 */       StringUtilities.replace(retvalue, "{BUTTON_ACTION}", "");
/*     */     } 
/* 292 */     StringUtilities.replace(retvalue, "{NEW_SEARCH_BUTTON}", this.buttonNewSearch.getHtmlCode(params));
/* 293 */     return retvalue.toString();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dls\server\admin\SearchResultPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */