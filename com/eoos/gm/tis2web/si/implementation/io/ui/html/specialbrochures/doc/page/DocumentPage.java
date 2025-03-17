/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.doc.page;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.Page;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.specialbrochures.SpecialBrochuresContext;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.doc.page.content.DefaultDocumentContainer;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.doc.page.content.DocumentContainer;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.doc.page.content.DocumentContainerConstructionException;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.doc.page.content.DocumentNotFoundException;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.doc.page.content.SIOLUDocumentContainer;
/*     */ import com.eoos.gm.tis2web.si.implementation.log.SIEventLogFacade;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOLU;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOProperty;
/*     */ import com.eoos.html.Dispatchable;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DocumentPage
/*     */   extends Page
/*     */ {
/*     */   protected DocumentContainer documentContainer;
/*     */   
/*     */   public DocumentPage(ClientContext context) {
/*  34 */     super(context);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getURL(Map params) {
/*  39 */     String url = this.context.constructDispatchURL((Dispatchable)this, "getPage");
/*  40 */     String bookmark = (params != null) ? (String)params.get("bm") : null;
/*  41 */     if (bookmark != null) {
/*  42 */       url = url + "#" + bookmark;
/*     */     }
/*  44 */     return url;
/*     */   }
/*     */   
/*     */   public ResultObject getPage(Map params) {
/*  48 */     String code = getHtmlCode(params);
/*     */     try {
/*  50 */       byte[] data = code.getBytes(getEncoding());
/*  51 */       return new ResultObject(11, data);
/*  52 */     } catch (UnsupportedEncodingException e) {
/*  53 */       return new ResultObject(0, getHtmlCode(params));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHtmlCode(Map params) {
/*  59 */     String html = super.getHtmlCode(params);
/*  60 */     if (this.documentContainer instanceof SIOLUDocumentContainer) {
/*  61 */       String styleTag = ((SIOLUDocumentContainer)this.documentContainer).getStyleTag();
/*  62 */       if (styleTag != null) {
/*  63 */         int styleSheetLinkStart = html.indexOf("<link rel=\"stylesheet\"");
/*  64 */         if (styleSheetLinkStart > 0) {
/*  65 */           int styleSheetLinkEnd = html.indexOf(">", styleSheetLinkStart);
/*  66 */           if (styleSheetLinkEnd > 0) {
/*  67 */             StringBuffer tmp = new StringBuffer(html.length() + styleTag.length());
/*  68 */             tmp.append(html.substring(0, styleSheetLinkStart - 1));
/*  69 */             tmp.append(styleTag);
/*  70 */             tmp.append(html.substring(styleSheetLinkEnd + 1));
/*  71 */             html = tmp.toString();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  77 */     return html;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void update() {
/*  83 */     SIO sio = SpecialBrochuresContext.getInstance((ClientContext)this.context).getSelectedSIO();
/*  84 */     if (sio != null) {
/*  85 */       if (this.documentContainer == null || this.documentContainer.getSIO() == null || !this.documentContainer.getSIO().equals(sio)) {
/*     */         DefaultDocumentContainer defaultDocumentContainer;
/*     */         
/*     */         try {
/*  89 */           if (sio instanceof SIOLU) {
/*  90 */             SIOLUDocumentContainer sIOLUDocumentContainer = new SIOLUDocumentContainer((ClientContext)this.context, (SIOLU)sio, this);
/*  91 */             SIEventLogFacade.getInstance().createEntry("LU," + sio.getProperty((SITOCProperty)SIOProperty.LU), (ClientContext)this.context);
/*     */           } else {
/*     */             
/*  94 */             defaultDocumentContainer = new DefaultDocumentContainer((ClientContext)this.context, this.context.getMessage("si.document.not.available"));
/*     */           }
/*     */         
/*  97 */         } catch (DocumentNotFoundException e) {
/*  98 */           defaultDocumentContainer = new DefaultDocumentContainer((ClientContext)this.context, this.context.getMessage("si.document.not.found"));
/*  99 */         } catch (DocumentContainerConstructionException e) {
/* 100 */           defaultDocumentContainer = new DefaultDocumentContainer((ClientContext)this.context, this.context.getMessage("si.document.not.available"));
/*     */         } 
/*     */         
/* 103 */         removeElement((HtmlElement)this.documentContainer);
/* 104 */         this.documentContainer = (DocumentContainer)defaultDocumentContainer;
/* 105 */         addElement((HtmlElement)this.documentContainer);
/*     */       } 
/*     */     } else {
/*     */       
/* 109 */       removeElement((HtmlElement)this.documentContainer);
/* 110 */       this.documentContainer = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getFormContent(Map params) {
/* 116 */     update();
/* 117 */     if (this.documentContainer != null) {
/* 118 */       return this.documentContainer.getHtmlCode(params);
/*     */     }
/* 120 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getStyleSheetURL() {
/* 125 */     String styleSheet = null;
/*     */     try {
/* 127 */       styleSheet = this.documentContainer.getStyleSheet();
/* 128 */     } catch (NullPointerException e) {}
/*     */ 
/*     */     
/* 131 */     styleSheet = (styleSheet != null) ? ("si/styles/" + styleSheet) : "common/style.css";
/*     */     
/* 133 */     return "res/" + styleSheet;
/*     */   }
/*     */   
/*     */   protected String getTitle() {
/* 137 */     return "";
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\specialbrochures\doc\page\DocumentPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */