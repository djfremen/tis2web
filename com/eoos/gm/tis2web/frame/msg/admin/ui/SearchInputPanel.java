/*     */ package com.eoos.gm.tis2web.frame.msg.admin.ui;
/*     */ 
/*     */ import com.eoos.gm.tis2web.admin.implementation.ui.html.main.AdminMasterMainPage;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.ErrorMessageBox;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.NotificationMessageBox;
/*     */ import com.eoos.gm.tis2web.frame.msg.admin.ASServiceImpl_Msg;
/*     */ import com.eoos.gm.tis2web.frame.msg.admin.IMessage;
/*     */ import com.eoos.gm.tis2web.frame.msg.admin.MessageManager;
/*     */ import com.eoos.gm.tis2web.frame.msg.admin.QueryFilter;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.HtmlElementStack;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.html.element.input.TextInputElement;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.TypeDecorator;
/*     */ import com.eoos.util.v2.StringUtilities;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class SearchInputPanel
/*     */   extends HtmlElementContainerBase
/*     */ {
/*  28 */   private static final Logger log = Logger.getLogger(SearchInputPanel.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  33 */       template = ApplicationContext.getInstance().loadFile(SearchInputPanel.class, "searchinputpanel.html", null).toString();
/*  34 */     } catch (Exception e) {
/*  35 */       log.error("error loading template - error:" + e, e);
/*  36 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ClientContext context;
/*     */   
/*     */   private TextInputElement inputExtIDPattern;
/*     */   
/*     */   private ClickButtonElement buttonSearch;
/*     */   
/*     */   private ClickButtonElement buttonCreate;
/*     */   
/*     */   private String message;
/*     */   
/*     */   public SearchInputPanel(final ClientContext context) {
/*  52 */     this.context = context;
/*     */     
/*  54 */     Configuration configuration = ASServiceImpl_Msg.getInstance().getConfiguration();
/*  55 */     new TypeDecorator(configuration);
/*     */     
/*  57 */     this.inputExtIDPattern = new TextInputElement(context.createID());
/*  58 */     addElement((HtmlElement)this.inputExtIDPattern);
/*     */     
/*  60 */     this.buttonSearch = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/*  63 */           return context.getLabel("search");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/*  68 */             SearchInputPanel.this.getPanelStack().push((HtmlElement)SearchInputPanel.this.executeSearch());
/*  69 */             return null;
/*  70 */           } catch (Exception e) {
/*  71 */             SearchInputPanel.log.error("...unable to execute search - exception:" + e, e);
/*  72 */             return ErrorMessageBox.create(context, null, context.getMessage("unable.to.execute.search"), getTopLevelContainer());
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/*  77 */     addElement((HtmlElement)this.buttonSearch);
/*     */     
/*  79 */     this.buttonCreate = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/*  82 */           return context.getLabel("create.message");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/*  87 */             return SearchInputPanel.this.onCreateMsg();
/*  88 */           } catch (Exception e) {
/*  89 */             SearchInputPanel.log.error("...unable to create message - exception:" + e, e);
/*  90 */             return ErrorMessageBox.create(context, null, context.getMessage("unable.to.create.messsage"), getTopLevelContainer());
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/*  95 */     addElement((HtmlElement)this.buttonCreate);
/*     */   }
/*     */ 
/*     */   
/*     */   public SearchResultPanel executeSearch() throws Exception {
/* 100 */     log.debug("executing search....");
/* 101 */     QueryFilter filter = null;
/* 102 */     final String idPattern = (String)this.inputExtIDPattern.getValue();
/* 103 */     if (idPattern != null && idPattern.trim().length() != 0) {
/* 104 */       filter = new QueryFilter()
/*     */         {
/*     */           public String getIDPattern() {
/* 107 */             return idPattern;
/*     */           }
/*     */           
/*     */           public IMessage.Status getStatus() {
/* 111 */             return null;
/*     */           }
/*     */         };
/*     */     }
/*     */     
/* 116 */     Collection entries = MessageManager.getInstance().getMessages(filter);
/*     */     
/* 118 */     return new SearchResultPanel(this.context, entries, false, this);
/*     */   }
/*     */   
/*     */   public HtmlElementStack getPanelStack() {
/* 122 */     HtmlElementContainer container = getContainer();
/* 123 */     while (!(container instanceof HtmlElementStack) && container != null) {
/* 124 */       container = container.getContainer();
/*     */     }
/* 126 */     return (HtmlElementStack)container;
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 130 */     StringBuffer retValue = new StringBuffer(template);
/* 131 */     StringUtilities.replace(retValue, "{MESSAGE}", (this.message != null) ? this.message : "");
/* 132 */     StringUtilities.replace(retValue, "{LABEL_IDPATTERN}", this.context.getLabel("identifier") + ":");
/* 133 */     StringUtilities.replace(retValue, "{INPUT_IDPATTERN}", this.inputExtIDPattern.getHtmlCode(params));
/*     */     
/* 135 */     StringUtilities.replace(retValue, "{BUTTON_SEARCH}", this.buttonSearch.getHtmlCode(params));
/* 136 */     StringUtilities.replace(retValue, "{BUTTON_CREATE}", this.buttonCreate.getHtmlCode(params));
/* 137 */     return retValue.toString();
/*     */   }
/*     */   
/*     */   private Object onCreateMsg() throws Exception {
/* 141 */     return new MessageCMDialog(this.context, new MessageCMDialog.Callback()
/*     */         {
/*     */           public Object onCreateOrModify(IMessage oldMsg, IMessage newMsg) throws Exception {
/* 144 */             MessageManager.getInstance().createMessage(newMsg);
/* 145 */             return new NotificationMessageBox(SearchInputPanel.this.context, null, SearchInputPanel.this.context.getMessage("msg.created"))
/*     */               {
/*     */                 protected Object onOK(Map params) {
/* 148 */                   return SearchInputPanel.null.this.onClose();
/*     */                 }
/*     */               };
/*     */           }
/*     */ 
/*     */           
/*     */           public Object onClose() {
/* 155 */             return AdminMasterMainPage.getInstance(SearchInputPanel.this.context);
/*     */           }
/*     */           
/*     */           public int getMode() {
/* 159 */             return 3;
/*     */           }
/*     */           
/*     */           public IMessage getMessage() {
/* 163 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\msg\admi\\ui\SearchInputPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */