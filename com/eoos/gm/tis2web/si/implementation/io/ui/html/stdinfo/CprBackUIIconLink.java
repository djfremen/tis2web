/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.stdinfo;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu.cpr.NodeChangeListener;
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
/*     */ public class CprBackUIIconLink
/*     */   extends LinkElement
/*     */   implements NodeChangeListener
/*     */ {
/*     */   private HtmlImgRenderer.Callback imgRendererCallback;
/*     */   private ClientContext context;
/*  35 */   private History history = null;
/*     */ 
/*     */   
/*     */   DocumentPage doc;
/*     */ 
/*     */   
/*     */   public CprBackUIIconLink(final ClientContext context, DocumentPage doc) {
/*  42 */     super(context.createID(), null);
/*  43 */     this.doc = doc;
/*  44 */     setDisabled(new Boolean(true));
/*  45 */     this.context = context;
/*  46 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter()
/*     */       {
/*     */ 
/*     */         
/*     */         public String getImageSource()
/*     */         {
/*  52 */           return "pic/lt/back.gif";
/*     */         }
/*     */         
/*     */         public String getAlternativeText() {
/*  56 */           return context.getLabel("back");
/*     */         }
/*     */         
/*     */         public void getAdditionalAttributes(Map<String, String> map) {
/*  60 */           map.put("border", "0");
/*     */         }
/*     */       };
/*     */ 
/*     */     
/*  65 */     setDisabled(new Boolean(false));
/*     */   }
/*     */   
/*     */   public CprBackUIIconLink(ClientContext context) {
/*  69 */     this(context, DocumentPage.getInstance(context));
/*     */   }
/*     */   
/*     */   protected String getLabel() {
/*  73 */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*     */   }
/*     */   
/*     */   public Object onClick(Map<String, String> submitParams) {
/*  77 */     Object obj = null;
/*  78 */     if (this.history != null) {
/*  79 */       this.history.popBack();
/*  80 */       obj = this.history.popBackStack();
/*  81 */       if (obj != null) {
/*  82 */         if (obj instanceof DocumentHistory) {
/*     */           
/*  84 */           int iSIOID = ((DocumentHistory)obj).getSioId();
/*     */           try {
/*  86 */             SIO sio = SIDataAdapterFacade.getInstance(this.context).getSI().getSIO(iSIOID);
/*  87 */             this.doc.setClean(((DocumentHistory)obj).getLinkFrom(), true);
/*  88 */             this.doc.setPageDontClear(sio);
/*  89 */           } catch (IllegalArgumentException e) {}
/*     */           
/*  91 */           obj = this.history.popBackStack();
/*     */         } 
/*  93 */         if (obj != null && obj instanceof StepResults) {
/*  94 */           String bm = ((StepResults)obj).getBookMark();
/*  95 */           if (submitParams != null) {
/*  96 */             submitParams.put("bm", bm);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 103 */     if (obj != null) {
/* 104 */       HtmlElementContainer container = getContainer();
/* 105 */       while (container.getContainer() != null) {
/* 106 */         container = container.getContainer();
/*     */       }
/* 108 */       return container.getHtmlCode(submitParams);
/*     */     } 
/* 110 */     return new ResultObject(9, new Object());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean show() {
/* 117 */     return (this.history != null);
/*     */   }
/*     */   
/*     */   public synchronized void setNode(Object node, History history) {
/* 121 */     if (node != null && node instanceof com.eoos.gm.tis2web.si.service.cai.SIOCPR) {
/* 122 */       this.history = history;
/*     */     } else {
/* 124 */       this.history = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public synchronized void setHistory(History history) {
/* 129 */     this.history = history;
/*     */   }
/*     */   
/*     */   public boolean clicked() {
/* 133 */     return (this.clicked && !isDisabled());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSetNode(Object node, History history) {
/* 145 */     setNode(node, history);
/*     */   }
/*     */   
/*     */   public void onResetNodes() {}
/*     */   
/*     */   public void onClearNodes() {}
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\stdinfo\CprBackUIIconLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */