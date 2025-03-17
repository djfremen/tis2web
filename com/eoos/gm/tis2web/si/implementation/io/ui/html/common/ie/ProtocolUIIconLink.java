/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.common.ie;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.History;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.protocol.ProtocolDialog;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainPage;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import com.eoos.html.element.input.TimezoneOffsetInputElement;
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
/*     */ public class ProtocolUIIconLink
/*     */   extends LinkElement
/*     */ {
/*     */   private HtmlImgRenderer.Callback imgRendererCallback;
/*     */   private ClientContext context;
/*  31 */   private History history = null;
/*     */   private ProtocolDialog.Callback callback;
/*  33 */   private TimezoneOffsetInputElement timezoneDiff = null;
/*     */ 
/*     */   
/*     */   public void setTimezoneDiff(TimezoneOffsetInputElement timezoneDiff) {
/*  37 */     this.timezoneDiff = timezoneDiff;
/*  38 */     if (this.history != null) {
/*  39 */       this.history.setTimezoneDiffElement(timezoneDiff);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public ProtocolUIIconLink(final ClientContext context, ProtocolDialog.Callback callback) {
/*  45 */     super(context.createID(), null);
/*  46 */     setDisabled(new Boolean(true));
/*  47 */     this.context = context;
/*  48 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*     */         public String getImageSource() {
/*  50 */           String image = "si/cprprotocol.gif";
/*  51 */           if (ProtocolUIIconLink.this.isDisabled()) {
/*  52 */             image = "si/cprprotocol-disabled.gif";
/*     */           }
/*  54 */           return "pic/" + image;
/*     */         }
/*     */         
/*     */         public String getAlternativeText() {
/*  58 */           return context.getLabel("diagnostic.protocol");
/*     */         }
/*     */         
/*     */         public void getAdditionalAttributes(Map<String, String> map) {
/*  62 */           map.put("border", "0");
/*     */         }
/*     */       };
/*     */ 
/*     */     
/*  67 */     setDisabled(new Boolean(false));
/*  68 */     this.callback = callback;
/*     */   }
/*     */   
/*     */   public ProtocolUIIconLink(ClientContext context) {
/*  72 */     this(context, new ProtocolDialog.Callback(context) {
/*     */           public Object onReturn(Map submitParams) {
/*  74 */             return MainPage.getInstance(context);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getLabel() {
/*  81 */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*     */   }
/*     */   
/*     */   public Object onClick(Map submitParams) {
/*  85 */     if (this.history != null) {
/*  86 */       ProtocolDialog dialog = new ProtocolDialog(this.context, this.history, this.callback);
/*  87 */       return new ResultObject(0, dialog.getHtmlCode(submitParams));
/*     */     } 
/*  89 */     return new ResultObject(9, new Object());
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized boolean show() {
/*  94 */     return (this.history != null);
/*     */   }
/*     */   
/*     */   public synchronized void setNode(Object node, History history) {
/*  98 */     if (node != null && node instanceof com.eoos.gm.tis2web.si.service.cai.SIOCPR) {
/*  99 */       this.history = history;
/* 100 */       this.history.setTimezoneDiffElement(this.timezoneDiff);
/*     */     } else {
/* 102 */       this.history = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public synchronized void setHistory(History history) {
/* 107 */     this.history = history;
/* 108 */     this.history.setTimezoneDiffElement(this.timezoneDiff);
/*     */   }
/*     */   
/*     */   public boolean clicked() {
/* 112 */     return (this.clicked && !isDisabled());
/*     */   }
/*     */   
/*     */   public boolean isDisabled() {
/* 116 */     return !show();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\common\ie\ProtocolUIIconLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */