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
/*     */ import com.eoos.html.element.gtwo.PagedElement;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.util.v2.StringUtilities;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class SearchResultPanelSWK
/*     */   extends HtmlElementContainerBase
/*     */ {
/*  25 */   private static final Logger log = Logger.getLogger(SearchResultPanelSWK.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  30 */       template = ApplicationContext.getInstance().loadFile(SearchResultPanelSWK.class, "searchresultpanel.html", null).toString();
/*  31 */     } catch (Exception e) {
/*  32 */       log.error("error loading template - error:" + e, e);
/*  33 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ClientContext context;
/*  39 */   private List entries = null;
/*     */   
/*  41 */   private EntriesListElement listElement = null;
/*     */   
/*  43 */   private HtmlElement listElementPagedFront = null;
/*     */   
/*  45 */   private ClickButtonElement buttonExecuteAction = null;
/*     */   
/*  47 */   private ClickButtonElement buttonMarkAll = null;
/*     */   
/*  49 */   private ClickButtonElement buttonReset = null;
/*     */   
/*  51 */   private ClickButtonElement buttonInvert = null;
/*     */   
/*     */   private ClickButtonElement buttonNewSearch;
/*     */   
/*     */   private boolean truncated = false;
/*     */ 
/*     */   
/*     */   public SearchResultPanelSWK(final ClientContext context, Collection<?> entries) {
/*  59 */     this.context = context;
/*     */     
/*  61 */     if (entries != null && entries.size() > 0) {
/*  62 */       this.entries = new ArrayList(entries);
/*  63 */       this.listElement = new EntriesListElement(this.entries, context);
/*  64 */       this.listElementPagedFront = (HtmlElement)new PagedElement(context.createID(), (HtmlElement)this.listElement, 20, 20);
/*  65 */       addElement(this.listElementPagedFront);
/*     */       
/*  67 */       this.buttonExecuteAction = new ClickButtonElement(context.createID(), null)
/*     */         {
/*     */           protected String getLabel() {
/*  70 */             return context.getLabel("frame.dls.admin.delete.softwarekeys");
/*     */           }
/*     */           
/*     */           public Object onClick(Map submitParams) {
/*     */             try {
/*  75 */               return SearchResultPanelSWK.this.executeSelectedAction();
/*  76 */             } catch (Exception e) {
/*  77 */               SearchResultPanelSWK.log.error("...unable to execute action - exception: " + e, e);
/*  78 */               return null;
/*     */             } 
/*     */           }
/*     */         };
/*     */       
/*  83 */       addElement((HtmlElement)this.buttonExecuteAction);
/*     */     } 
/*     */     
/*  86 */     this.buttonMarkAll = new ClickButtonElement(context.createID(), null) {
/*     */         protected String getLabel() {
/*  88 */           return context.getLabel("mark.all");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/*  93 */             SearchResultPanelSWK.this.listElement.markAll();
/*  94 */           } catch (Exception e) {
/*  95 */             SearchResultPanelSWK.log.error("...unable to mark all entries - exception:" + e, e);
/*     */           } 
/*  97 */           return null;
/*     */         }
/*     */       };
/* 100 */     addElement((HtmlElement)this.buttonMarkAll);
/*     */     
/* 102 */     this.buttonReset = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/* 105 */           return context.getLabel("reset.marking");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 110 */             SearchResultPanelSWK.this.listElement.unmarkAll();
/* 111 */           } catch (Exception e) {
/* 112 */             SearchResultPanelSWK.log.error("...unable to unmark all entries - exception:" + e, e);
/*     */           } 
/* 114 */           return null;
/*     */         }
/*     */       };
/*     */     
/* 118 */     addElement((HtmlElement)this.buttonReset);
/*     */     
/* 120 */     this.buttonInvert = new ClickButtonElement(context.createID(), null) {
/*     */         protected String getLabel() {
/* 122 */           return context.getLabel("invert.marking");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 127 */             SearchResultPanelSWK.this.listElement.invertMarking();
/* 128 */           } catch (Exception e) {
/* 129 */             SearchResultPanelSWK.log.error("...unable to invert marking - exception:" + e, e);
/*     */           } 
/* 131 */           return null;
/*     */         }
/*     */       };
/* 134 */     addElement((HtmlElement)this.buttonInvert);
/*     */     
/* 136 */     this.buttonNewSearch = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/* 139 */           return context.getLabel("new.search");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 144 */             SearchResultPanelSWK.this.getPanelStack().pop();
/* 145 */           } catch (Exception e) {
/* 146 */             SearchResultPanelSWK.log.error("...unable to process request for 'new search' - exception:" + e, e);
/*     */           } 
/* 148 */           return null;
/*     */         }
/*     */       };
/*     */     
/* 152 */     addElement((HtmlElement)this.buttonNewSearch);
/*     */   }
/*     */ 
/*     */   
/*     */   private Object executeSelectedAction() {
/*     */     Object ret;
/*     */     try {
/* 159 */       Collection entries = this.listElement.getMarkedEntries();
/*     */       
/* 161 */       if (entries != null && entries.size() > 0) {
/* 162 */         ret = executeSWKDeletion(entries);
/*     */       } else {
/* 164 */         ret = NotificationMessageBox.createWarningMessage(this.context, null, this.context.getMessage("nothing.selected"), getTopLevelContainer());
/*     */       } 
/* 166 */     } catch (Exception e) {
/* 167 */       log.error("unable to execute action - exception: " + e, e);
/* 168 */       return ErrorMessageBox.create(this.context, getTopLevelContainer());
/*     */     } 
/* 170 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   private Object executeSWKDeletion(final Collection entries) throws Exception {
/* 175 */     return new SimpleConfirmationMessageBox(this.context, this.context.getLabel("confirmation"), this.context.getMessage("confirmation.deletion"))
/*     */       {
/*     */         protected Object onCancel(Map params) {
/* 178 */           return SearchResultPanelSWK.this.getTopLevelContainer();
/*     */         }
/*     */         
/*     */         protected Object onOK(Map params) {
/*     */           try {
/* 183 */             DLSServiceServer.deleteSoftwareKeys(entries);
/* 184 */             HtmlElementContainer htmlElementContainer = SearchResultPanelSWK.this.getTopLevelContainer();
/* 185 */             SearchResultPanelSWK.this.getPanelStack().pop();
/* 186 */             return NotificationMessageBox.createInfoMessage((ClientContext)this.context, null, this.context.getMessage("deletion.succeeded"), htmlElementContainer);
/* 187 */           } catch (Exception e) {
/* 188 */             SearchResultPanelSWK.log.error("....unable to delete entries - exception:" + e, e);
/* 189 */             return ErrorMessageBox.create((ClientContext)this.context, SearchResultPanelSWK.this.getTopLevelContainer());
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HtmlElementStack getPanelStack() {
/* 199 */     HtmlElementContainer container = getContainer();
/* 200 */     while (!(container instanceof HtmlElementStack) && container != null) {
/* 201 */       container = container.getContainer();
/*     */     }
/* 203 */     return (HtmlElementStack)container;
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 207 */     StringBuffer retvalue = new StringBuffer(template);
/* 208 */     StringUtilities.replace(retvalue, "{MESSAGE}", this.truncated ? this.context.getMessage("search.result.truncated") : "");
/* 209 */     if (this.listElementPagedFront != null) {
/* 210 */       StringUtilities.replace(retvalue, "{LIST}", this.listElementPagedFront.getHtmlCode(params));
/* 211 */       StringUtilities.replace(retvalue, "{BUTTON_MARK_ALL}", this.buttonMarkAll.getHtmlCode(params));
/* 212 */       StringUtilities.replace(retvalue, "{BUTTON_INVERT}", this.buttonInvert.getHtmlCode(params));
/* 213 */       StringUtilities.replace(retvalue, "{BUTTON_RESET}", this.buttonReset.getHtmlCode(params));
/*     */     } else {
/*     */       
/* 216 */       StringUtilities.replace(retvalue, "{LIST}", this.context.getMessage("empty.search.result"));
/* 217 */       StringUtilities.replace(retvalue, "{BUTTON_MARK_ALL}", "");
/* 218 */       StringUtilities.replace(retvalue, "{BUTTON_INVERT}", "");
/* 219 */       StringUtilities.replace(retvalue, "{BUTTON_RESET}", "");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 224 */     StringUtilities.replace(retvalue, "{BUTTON_ACTION}", this.buttonExecuteAction.getHtmlCode(params));
/*     */     
/* 226 */     StringUtilities.replace(retvalue, "{INPUT_ACTION}", "");
/*     */ 
/*     */     
/* 229 */     StringUtilities.replace(retvalue, "{NEW_SEARCH_BUTTON}", this.buttonNewSearch.getHtmlCode(params));
/* 230 */     return retvalue.toString();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dls\server\admin\SearchResultPanelSWK.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */