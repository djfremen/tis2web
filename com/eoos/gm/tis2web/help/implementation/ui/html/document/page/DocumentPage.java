/*     */ package com.eoos.gm.tis2web.help.implementation.ui.html.document.page;
/*     */ 
/*     */ import com.eoos.datatype.SectionIndex;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.FileReference;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.IIOElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.Page;
/*     */ import com.eoos.gm.tis2web.help.implementation.ui.html.document.container.DefaultDocumentContainer;
/*     */ import com.eoos.gm.tis2web.help.implementation.ui.html.document.container.DocumentContainer;
/*     */ import com.eoos.gm.tis2web.help.implementation.ui.html.document.container.HelpDocumentContainer;
/*     */ import com.eoos.gm.tis2web.help.implementation.ui.html.document.container.VersionDocumentContainer;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOProperty;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOType;
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
/*  37 */   private static final Logger log = Logger.getLogger(DocumentPage.class);
/*     */   
/*  39 */   protected static final Logger documentHitLog = Logger.getLogger("si.document.hits");
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  44 */       template = ApplicationContext.getInstance().loadFile(DocumentPage.class, "documentpage.html", null).toString();
/*  45 */     } catch (Exception e) {
/*  46 */       log.error("unable to load template - error:" + e, e);
/*  47 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ClickButtonElement buttonBack;
/*     */   
/*     */   private PrintViewLink linkPrintView;
/*     */   
/*     */   protected DocumentContainer documentContainer;
/*  57 */   protected String bookmark = null;
/*     */ 
/*     */   
/*     */   public DocumentPage(ClientContext context) {
/*  61 */     super(context);
/*     */     
/*  63 */     this.buttonBack = new BackButton(this, context.createID(), null, context);
/*  64 */     addElement((HtmlElement)this.buttonBack);
/*  65 */     this.linkPrintView = new PrintViewLink(context)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*  68 */           StringBuffer tmp = new StringBuffer(getContainer().getHtmlCode(submitParams));
/*     */ 
/*     */           
/*  71 */           SectionIndex index = null;
/*  72 */           while ((index = StringUtilities.getSectionIndex(tmp.toString(), "<!--XPRINT-->", "<!--/XPRINT-->", 0, true, false)) != null) {
/*  73 */             StringUtilities.replaceSectionContent(tmp, index, "");
/*     */           }
/*     */ 
/*     */           
/*  77 */           index = null;
/*  78 */           RE reLinkStart = new RE(reLinkStartProg);
/*     */           
/*  80 */           RE reLinkEnd = new RE(reLinkEndProg);
/*  81 */           while ((index = StringUtilities.getSectionIndex(tmp.toString(), reLinkStart, reLinkEnd, 0, true, false)) != null) {
/*  82 */             String all = StringUtilities.getSectionContent(tmp, index);
/*  83 */             SectionIndex contentIndex = StringUtilities.getSectionIndex(all, reLinkStart, reLinkEnd, 0, false, false);
/*     */             
/*  85 */             StringUtilities.replaceSectionContent(tmp, index, "<u>" + StringUtilities.getSectionContent(all, contentIndex) + "</u>");
/*     */           } 
/*     */ 
/*     */           
/*  89 */           if ((index = StringUtilities.getSectionIndex(tmp.toString(), "//JAVASCRIPT-DISABLE", "//JAVASCRIPT-DISABLE-END", 0, true, false)) != null) {
/*  90 */             StringUtilities.replaceSectionContent(tmp, index, "");
/*     */           }
/*     */           
/*  93 */           return new ResultObject(0, tmp.toString());
/*     */         }
/*     */       };
/*     */     
/*  97 */     addElement((HtmlElement)this.linkPrintView);
/*     */   }
/*     */ 
/*     */   
/*     */   private String getDocumentBookmark() {
/* 102 */     String bookmark = null;
/*     */     try {
/* 104 */       IIOElement sio = (IIOElement)((HelpDocumentContainer)this.documentContainer).getSIO();
/* 105 */       LocaleInfo locale = LocaleInfoProvider.getInstance().getLocale(this.context.getLocale());
/*     */       
/* 107 */       FileReference reference = (FileReference)sio.getProperty((SITOCProperty)SIOProperty.File);
/* 108 */       if (reference == null || reference.get(locale) == null) {
/* 109 */         return null;
/*     */       }
/* 111 */       String target = reference.get(locale);
/* 112 */       int pos = target.indexOf('#');
/* 113 */       if (pos > 0) {
/* 114 */         bookmark = target.substring(pos + 1);
/*     */       }
/*     */     }
/* 117 */     catch (Exception e) {
/* 118 */       bookmark = null;
/*     */     } 
/* 120 */     return bookmark;
/*     */   }
/*     */   
/*     */   public String getURL(Map params) {
/* 124 */     String url = this.context.constructDispatchURL((Dispatchable)this, "getPage");
/* 125 */     String bookmark = (params != null) ? (String)params.get("documentbm") : null;
/* 126 */     if (bookmark == null) {
/* 127 */       bookmark = getDocumentBookmark();
/*     */     }
/*     */     
/* 130 */     if (bookmark != null) {
/* 131 */       url = url + "#" + bookmark;
/*     */     }
/* 133 */     return url;
/*     */   }
/*     */   
/*     */   public ResultObject getPage(Map params) {
/* 137 */     synchronized (this.context.getLockObject()) {
/* 138 */       String code = getHtmlCode(params);
/*     */       try {
/* 140 */         byte[] data = code.getBytes(getEncoding());
/* 141 */         return new ResultObject(11, data);
/* 142 */       } catch (UnsupportedEncodingException e) {
/* 143 */         return new ResultObject(0, getHtmlCode(params));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public DocumentContainer setPage(SIO sio) {
/*     */     DefaultDocumentContainer defaultDocumentContainer;
/* 149 */     DocumentContainer container = null;
/*     */     try {
/* 151 */       if (sio.getType().equals(SIOType.VERSION)) {
/* 152 */         VersionDocumentContainer versionDocumentContainer = new VersionDocumentContainer((ClientContext)this.context, (IIOElement)sio, this);
/*     */       } else {
/* 154 */         HelpDocumentContainer helpDocumentContainer = new HelpDocumentContainer((ClientContext)this.context, (IIOElement)sio, this);
/*     */       } 
/* 156 */     } catch (Exception e) {
/* 157 */       defaultDocumentContainer = new DefaultDocumentContainer((ClientContext)this.context, "");
/*     */     } 
/* 159 */     setDocumentContainer((DocumentContainer)defaultDocumentContainer);
/* 160 */     return (DocumentContainer)defaultDocumentContainer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDocumentContainer(DocumentContainer documentContainer) {
/* 166 */     removeElement((HtmlElement)this.documentContainer);
/* 167 */     this.documentContainer = documentContainer;
/* 168 */     addElement((HtmlElement)this.documentContainer);
/*     */   }
/*     */   
/*     */   protected String getFormContent(Map params) {
/* 172 */     if (this.documentContainer != null) {
/* 173 */       StringBuffer code = new StringBuffer(template);
/* 174 */       StringUtilities.replace(code, "{BACK_BUTTON}", (this.documentContainer.getPredecessor() != null) ? this.buttonBack.getHtmlCode(params) : "");
/* 175 */       StringUtilities.replace(code, "{DOCUMENT}", this.documentContainer.getHtmlCode(params));
/* 176 */       StringUtilities.replace(code, "{PRINT_BUTTON}", this.linkPrintView.getHtmlCode(params));
/* 177 */       return code.toString();
/*     */     } 
/* 179 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getStyleSheetURL() {
/* 184 */     String styleSheet = null;
/*     */     try {
/* 186 */       styleSheet = this.documentContainer.getStyleSheet();
/* 187 */     } catch (NullPointerException e) {}
/*     */ 
/*     */     
/* 190 */     return (styleSheet != null) ? ("res/help/styles/" + styleSheet) : super.getStyleSheetURL();
/*     */   }
/*     */   
/*     */   protected String getTitle() {
/* 194 */     return "";
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\help\implementatio\\ui\html\document\page\DocumentPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */