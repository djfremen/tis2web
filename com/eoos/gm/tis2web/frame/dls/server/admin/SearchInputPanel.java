/*    */ package com.eoos.gm.tis2web.frame.dls.server.admin;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.dls.server.DatabaseAdapterProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ui.html.ErrorMessageBox;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ui.html.NotificationMessageBox;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainer;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import com.eoos.html.element.HtmlElementStack;
/*    */ import com.eoos.html.element.input.ClickButtonElement;
/*    */ import com.eoos.html.element.input.TextInputElement;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import com.eoos.util.v2.StringUtilities;
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class SearchInputPanel
/*    */   extends HtmlElementContainerBase {
/* 22 */   private static final Logger log = Logger.getLogger(SearchInputPanel.class);
/*    */   private static String template;
/*    */   
/*    */   static {
/*    */     try {
/* 27 */       template = ApplicationContext.getInstance().loadFile(SearchInputPanel.class, "searchinputpanel.html", null).toString();
/* 28 */     } catch (Exception e) {
/* 29 */       log.error("error loading template - error:" + e, e);
/* 30 */       throw new RuntimeException();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private ClientContext context;
/*    */   
/*    */   private TextInputElement inputUsernamePattern;
/*    */   
/*    */   private ClickButtonElement buttonSearch;
/*    */   
/*    */   public SearchInputPanel(final ClientContext context) {
/* 42 */     this.context = context;
/*    */     
/* 44 */     this.inputUsernamePattern = new TextInputElement(context.createID());
/* 45 */     addElement((HtmlElement)this.inputUsernamePattern);
/*    */     
/* 47 */     this.buttonSearch = new ClickButtonElement(context.createID(), null)
/*    */       {
/*    */         protected String getLabel() {
/* 50 */           return context.getLabel("search");
/*    */         }
/*    */         
/*    */         public Object onClick(Map submitParams) {
/* 54 */           return SearchInputPanel.this.executeSearch();
/*    */         }
/*    */       };
/*    */ 
/*    */     
/* 59 */     addElement((HtmlElement)this.buttonSearch);
/*    */   }
/*    */ 
/*    */   
/*    */   private Object executeSearch() {
/*    */     Object object;
/*    */     try {
/* 66 */       Collection entries = DatabaseAdapterProvider.getDatabaseAdapter().getUsernames((String)this.inputUsernamePattern.getValue());
/* 67 */       if (Util.isNullOrEmpty(entries)) {
/* 68 */         object = NotificationMessageBox.createInfoMessage(this.context, null, this.context.getMessage("search.no.result"), getTopLevelContainer());
/*    */       } else {
/* 70 */         getPanelStack().push((HtmlElement)new SearchResultPanel(this.context, entries));
/* 71 */         object = null;
/*    */       } 
/* 73 */     } catch (Exception e) {
/* 74 */       log.error("...unable to execute search - exception:" + e, e);
/* 75 */       object = ErrorMessageBox.create(this.context, getTopLevelContainer());
/*    */     } 
/* 77 */     return object;
/*    */   }
/*    */   
/*    */   public HtmlElementStack getPanelStack() {
/* 81 */     HtmlElementContainer container = getContainer();
/* 82 */     while (!(container instanceof HtmlElementStack) && container != null) {
/* 83 */       container = container.getContainer();
/*    */     }
/* 85 */     return (HtmlElementStack)container;
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 89 */     StringBuffer retValue = new StringBuffer(template);
/* 90 */     StringUtilities.replace(retValue, "{MESSAGE}", "");
/*    */     
/* 92 */     StringUtilities.replace(retValue, "{LABEL_USERID_PATTERN}", this.context.getLabel("user.id") + ":");
/* 93 */     StringUtilities.replace(retValue, "{INPUT_USERID_PATTERN}", this.inputUsernamePattern.getHtmlCode(params));
/*    */     
/* 95 */     StringUtilities.replace(retValue, "{BUTTON_SEARCH}", this.buttonSearch.getHtmlCode(params));
/* 96 */     return retValue.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dls\server\admin\SearchInputPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */