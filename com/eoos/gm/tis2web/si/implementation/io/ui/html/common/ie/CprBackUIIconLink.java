/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.common.ie;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.DocumentHistory;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.History;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.StepResults;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.page.DocumentPage;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*     */ import com.eoos.gm.tis2web.si.v2.SIDataAdapterFacade;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import com.eoos.html.renderer.HtmlImgRenderer;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CprBackUIIconLink
/*     */   extends LinkElement
/*     */ {
/*     */   private HtmlImgRenderer.Callback imgRendererCallback;
/*     */   private ClientContext context;
/*  34 */   private History history = null;
/*     */ 
/*     */   
/*     */   DocumentPage doc;
/*     */ 
/*     */   
/*     */   public CprBackUIIconLink(final ClientContext context, DocumentPage doc) {
/*  41 */     super(context.createID(), null);
/*  42 */     this.doc = doc;
/*  43 */     setDisabled(new Boolean(true));
/*  44 */     this.context = context;
/*  45 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter()
/*     */       {
/*     */ 
/*     */         
/*     */         public String getImageSource()
/*     */         {
/*  51 */           return "pic/lt/back.gif";
/*     */         }
/*     */         
/*     */         public String getAlternativeText() {
/*  55 */           return context.getLabel("back");
/*     */         }
/*     */         
/*     */         public void getAdditionalAttributes(Map<String, String> map) {
/*  59 */           map.put("border", "0");
/*     */         }
/*     */       };
/*     */ 
/*     */     
/*  64 */     setDisabled(new Boolean(false));
/*     */   }
/*     */   
/*     */   public CprBackUIIconLink(ClientContext context) {
/*  68 */     this(context, DocumentPage.getInstance(context));
/*     */   }
/*     */   
/*     */   protected String getLabel() {
/*  72 */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*     */   }
/*     */   
/*     */   public Object onClick(Map<String, String> submitParams) {
/*  76 */     Object obj = null;
/*  77 */     if (this.history != null) {
/*  78 */       this.history.popBack();
/*  79 */       obj = this.history.popBackStack();
/*  80 */       if (obj != null) {
/*  81 */         if (obj instanceof DocumentHistory) {
/*     */           
/*  83 */           int iSIOID = ((DocumentHistory)obj).getSioId();
/*     */           try {
/*  85 */             SIO sio = SIDataAdapterFacade.getInstance(this.context).getSI().getSIO(iSIOID);
/*  86 */             this.doc.setClean(((DocumentHistory)obj).getLinkFrom(), true);
/*  87 */             this.doc.setPageDontClear(sio);
/*  88 */           } catch (IllegalArgumentException e) {}
/*     */           
/*  90 */           obj = this.history.popBackStack();
/*     */         } 
/*  92 */         if (obj != null && obj instanceof StepResults) {
/*  93 */           String bm = ((StepResults)obj).getBookMark();
/*  94 */           submitParams.put("bm", bm);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 100 */     if (obj != null) {
/* 101 */       HtmlElementContainer container = getContainer();
/* 102 */       while (container.getContainer() != null) {
/* 103 */         container = container.getContainer();
/*     */       }
/* 105 */       return container.getHtmlCode(submitParams);
/*     */     } 
/* 107 */     return new ResultObject(9, new Object());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean show() {
/* 114 */     return (this.history != null);
/*     */   }
/*     */   
/*     */   public synchronized void setNode(Object node, History history) {
/* 118 */     if (node != null && node instanceof com.eoos.gm.tis2web.si.service.cai.SIOCPR) {
/* 119 */       this.history = history;
/*     */     } else {
/* 121 */       this.history = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public synchronized void setHistory(History history) {
/* 126 */     this.history = history;
/*     */   }
/*     */   
/*     */   public boolean clicked() {
/* 130 */     return (this.clicked && !isDisabled());
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\common\ie\CprBackUIIconLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */