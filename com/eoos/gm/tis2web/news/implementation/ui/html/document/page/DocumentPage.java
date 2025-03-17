/*     */ package com.eoos.gm.tis2web.news.implementation.ui.html.document.page;
/*     */ 
/*     */ import com.eoos.datatype.SectionIndex;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.IIOElement;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.Page;
/*     */ import com.eoos.gm.tis2web.news.implementation.ui.html.document.container.DefaultDocumentContainer;
/*     */ import com.eoos.gm.tis2web.news.implementation.ui.html.document.container.DocumentContainer;
/*     */ import com.eoos.gm.tis2web.news.implementation.ui.html.document.container.NewsDocumentContainer;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*     */ import com.eoos.html.Dispatchable;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.regexp.RE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DocumentPage
/*     */   extends Page
/*     */ {
/*  30 */   private static final Logger log = Logger.getLogger(DocumentPage.class);
/*     */   
/*  32 */   protected static final Logger documentHitLog = Logger.getLogger("si.document.hits");
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  37 */       template = ApplicationContext.getInstance().loadFile(DocumentPage.class, "documentpage.html", null).toString();
/*  38 */     } catch (Exception e) {
/*  39 */       log.error("unable to load template - error:" + e, e);
/*  40 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ClickButtonElement buttonBack;
/*     */   
/*     */   private PrintViewLink linkPrintView;
/*     */   
/*     */   protected DocumentContainer documentContainer;
/*     */   
/*     */   public DocumentPage(ClientContext context) {
/*  52 */     super(context);
/*     */     
/*  54 */     this.buttonBack = new BackButton(this, context.createID(), null, context);
/*  55 */     addElement((HtmlElement)this.buttonBack);
/*     */     
/*  57 */     this.linkPrintView = new PrintViewLink(context)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*  60 */           StringBuffer tmp = new StringBuffer(getContainer().getHtmlCode(submitParams));
/*     */ 
/*     */           
/*  63 */           SectionIndex index = null;
/*  64 */           while ((index = StringUtilities.getSectionIndex(tmp.toString(), "<!--XPRINT-->", "<!--/XPRINT-->", 0, true, false)) != null) {
/*  65 */             StringUtilities.replaceSectionContent(tmp, index, "");
/*     */           }
/*     */ 
/*     */           
/*  69 */           index = null;
/*  70 */           RE reLinkStart = new RE(PrintViewLink.reLinkStartProg);
/*     */           
/*  72 */           RE reLinkEnd = new RE(reLinkEndProg);
/*     */           
/*  74 */           while ((index = StringUtilities.getSectionIndex(tmp.toString(), reLinkStart, reLinkEnd, 0, true, false)) != null) {
/*  75 */             String all = StringUtilities.getSectionContent(tmp, index);
/*  76 */             SectionIndex contentIndex = StringUtilities.getSectionIndex(all, reLinkStart, reLinkEnd, 0, false, false);
/*     */             
/*  78 */             StringUtilities.replaceSectionContent(tmp, index, "<u>" + StringUtilities.getSectionContent(all, contentIndex) + "</u>");
/*     */           } 
/*     */ 
/*     */           
/*  82 */           if ((index = StringUtilities.getSectionIndex(tmp.toString(), "//JAVASCRIPT-DISABLE", "//JAVASCRIPT-DISABLE-END", 0, true, false)) != null) {
/*  83 */             StringUtilities.replaceSectionContent(tmp, index, "");
/*     */           }
/*     */           
/*  86 */           return new ResultObject(0, tmp.toString());
/*     */         }
/*     */       };
/*     */     
/*  90 */     addElement((HtmlElement)this.linkPrintView);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getURL(Map params) {
/*  95 */     String url = this.context.constructDispatchURL((Dispatchable)this, "getPage");
/*  96 */     String bookmark = (params != null) ? (String)params.get("bm") : null;
/*  97 */     if (bookmark != null) {
/*  98 */       url = url + "#" + bookmark;
/*     */     }
/* 100 */     return url;
/*     */   }
/*     */   
/*     */   public ResultObject getPage(Map params) {
/* 104 */     synchronized (this.context.getLockObject()) {
/* 105 */       String code = getHtmlCode(params);
/*     */       try {
/* 107 */         byte[] data = code.getBytes(getEncoding());
/* 108 */         return new ResultObject(11, data);
/* 109 */       } catch (UnsupportedEncodingException e) {
/* 110 */         return new ResultObject(0, getHtmlCode(params));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public DocumentContainer setPage(SIO sio) {
/*     */     DefaultDocumentContainer defaultDocumentContainer;
/* 116 */     DocumentContainer container = null;
/*     */     try {
/* 118 */       NewsDocumentContainer newsDocumentContainer = new NewsDocumentContainer((ClientContext)this.context, (IIOElement)sio, this);
/* 119 */     } catch (Exception e) {
/* 120 */       defaultDocumentContainer = new DefaultDocumentContainer((ClientContext)this.context, "");
/*     */     } 
/* 122 */     setDocumentContainer((DocumentContainer)defaultDocumentContainer);
/* 123 */     return (DocumentContainer)defaultDocumentContainer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDocumentContainer(DocumentContainer documentContainer) {
/* 129 */     removeElement((HtmlElement)this.documentContainer);
/* 130 */     this.documentContainer = documentContainer;
/* 131 */     addElement((HtmlElement)this.documentContainer);
/*     */   }
/*     */   
/*     */   protected String getFormContent(Map params) {
/* 135 */     if (this.documentContainer != null) {
/* 136 */       StringBuffer code = new StringBuffer(template);
/* 137 */       StringUtilities.replace(code, "{BACK_BUTTON}", (this.documentContainer.getPredecessor() != null) ? this.buttonBack.getHtmlCode(params) : "");
/* 138 */       StringUtilities.replace(code, "{DOCUMENT}", this.documentContainer.getHtmlCode(params));
/* 139 */       StringUtilities.replace(code, "{PRINT_BUTTON}", this.linkPrintView.getHtmlCode(params));
/* 140 */       return code.toString();
/*     */     } 
/* 142 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getStyleSheetURL() {
/* 147 */     String styleSheet = null;
/*     */     try {
/* 149 */       styleSheet = this.documentContainer.getStyleSheet();
/* 150 */     } catch (NullPointerException e) {}
/*     */ 
/*     */     
/* 153 */     return (styleSheet != null) ? ("res/news/styles/" + styleSheet) : super.getStyleSheetURL();
/*     */   }
/*     */   
/*     */   protected String getTitle() {
/* 157 */     return "";
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\news\implementatio\\ui\html\document\page\DocumentPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */