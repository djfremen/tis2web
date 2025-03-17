/*    */ package com.eoos.gm.tis2web.frame.implementation.admin.searchlog.ui.html.search;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainer;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import com.eoos.html.element.HtmlElementStack;
/*    */ import com.eoos.html.element.gtwo.PagedElement;
/*    */ import com.eoos.html.element.input.ClickButtonElement;
/*    */ import com.eoos.util.v2.StringUtilities;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class SearchResultPanel
/*    */   extends HtmlElementContainerBase
/*    */ {
/* 21 */   private static final Logger log = Logger.getLogger(SearchResultPanel.class);
/*    */   private static String template;
/*    */   
/*    */   static {
/*    */     try {
/* 26 */       template = ApplicationContext.getInstance().loadFile(SearchResultPanel.class, "searchresultpanel.html", null).toString();
/* 27 */     } catch (Exception e) {
/* 28 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */   
/* 32 */   private List entries = null;
/*    */   
/* 34 */   private FileListElement listElement = null;
/*    */   
/* 36 */   private HtmlElement listElementPagedFront = null;
/*    */   
/*    */   private ClickButtonElement buttonNewSearch;
/*    */ 
/*    */   
/*    */   public SearchResultPanel(final ClientContext context, Collection<?> entries) {
/* 42 */     this.entries = new ArrayList(entries);
/* 43 */     this.listElement = new FileListElement(this.entries, context);
/* 44 */     this.listElementPagedFront = (HtmlElement)new PagedElement(context.createID(), (HtmlElement)this.listElement, 20, 20);
/* 45 */     addElement(this.listElementPagedFront);
/*    */     
/* 47 */     this.buttonNewSearch = new ClickButtonElement(context.createID(), null)
/*    */       {
/*    */         protected String getLabel() {
/* 50 */           return context.getLabel("new.search");
/*    */         }
/*    */         
/*    */         public Object onClick(Map submitParams) {
/*    */           try {
/* 55 */             SearchResultPanel.this.getPanelStack().pop();
/* 56 */           } catch (Exception e) {
/* 57 */             SearchResultPanel.log.error("...unable to process request for 'new search' - exception:" + e, e);
/*    */           } 
/* 59 */           return null;
/*    */         }
/*    */       };
/*    */     
/* 63 */     addElement((HtmlElement)this.buttonNewSearch);
/*    */   }
/*    */ 
/*    */   
/*    */   public HtmlElementStack getPanelStack() {
/* 68 */     HtmlElementContainer container = getContainer();
/* 69 */     while (!(container instanceof HtmlElementStack) && container != null) {
/* 70 */       container = container.getContainer();
/*    */     }
/* 72 */     return (HtmlElementStack)container;
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 76 */     StringBuffer retvalue = new StringBuffer(template);
/* 77 */     StringUtilities.replace(retvalue, "{MESSAGE}", "");
/* 78 */     StringUtilities.replace(retvalue, "{LIST}", this.listElementPagedFront.getHtmlCode(params));
/* 79 */     StringUtilities.replace(retvalue, "{NEW_SEARCH_BUTTON}", this.buttonNewSearch.getHtmlCode(params));
/* 80 */     return retvalue.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\admin\searchlo\\ui\html\search\SearchResultPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */