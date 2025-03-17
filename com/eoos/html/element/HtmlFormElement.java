/*     */ package com.eoos.html.element;
/*     */ 
/*     */ import com.eoos.html.Dispatchable;
/*     */ import com.eoos.html.HtmlCodeRenderer;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.base.ClientContextBase;
/*     */ import com.eoos.html.renderer.HtmlFormRenderer;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public abstract class HtmlFormElement
/*     */   extends HtmlElementContainerBase
/*     */   implements Dispatchable
/*     */ {
/*  15 */   private static Logger log = Logger.getLogger(HtmlFormElement.class);
/*     */   
/*     */   private class MyRendererCallback
/*     */     extends HtmlFormRenderer.CallbackAdapter {
/*     */     private Map params;
/*     */     
/*     */     public void init(Map params) {
/*  22 */       this.params = params;
/*     */     }
/*     */     private MyRendererCallback() {}
/*     */     public String getActionUrl() {
/*  26 */       return HtmlFormElement.this.context.constructDispatchURL(HtmlFormElement.this, "onSubmit");
/*     */     }
/*     */     
/*     */     public String getFormBody() {
/*  30 */       return HtmlFormElement.this.getFormContent(this.params);
/*     */     }
/*     */     
/*     */     public String getMethod() {
/*  34 */       return "post";
/*     */     }
/*     */     
/*     */     public void getAdditionalAttributes(Map<String, String> map) {
/*  38 */       map.put("accept-charset", "UTF-8");
/*  39 */       if (!HtmlFormElement.this.allowTextFieldSubmit()) {
/*  40 */         map.put("onSubmit", "javascript:return false");
/*     */       }
/*     */       
/*  43 */       HtmlFormElement.this.getAdditionalAttributes(map);
/*     */     }
/*     */   }
/*     */   
/*  47 */   protected String identifier = null;
/*  48 */   private MyRendererCallback rendererCallback = null;
/*  49 */   protected ClientContextBase context = null;
/*     */ 
/*     */   
/*     */   public HtmlFormElement(ClientContextBase context, String identifier) {
/*  53 */     this.identifier = identifier;
/*  54 */     this.rendererCallback = new MyRendererCallback();
/*  55 */     this.context = context;
/*  56 */     context.registerDispatchable(this);
/*     */   }
/*     */   
/*     */   protected void getAdditionalAttributes(Map<String, String> map) {
/*  60 */     map.put("id", this.identifier);
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/*  64 */     return HtmlFormRenderer.getInstance().getHtmlCode((HtmlFormRenderer.Callback)this.rendererCallback, params);
/*     */   }
/*     */   
/*     */   public Object getIdentifier() {
/*  68 */     return this.identifier;
/*     */   }
/*     */   
/*     */   public void unregister() {
/*  72 */     this.context.unregisterDispatchable(this);
/*     */   }
/*     */   
/*     */   public ResultObject onSubmit(Map params) {
/*  76 */     log.debug("trying to aquire lock for " + this.context);
/*  77 */     synchronized (this.context.getLockObject()) {
/*  78 */       log.debug("aquired lock for " + this.context);
/*     */       try {
/*  80 */         ResultObject retValue = null;
/*  81 */         setValue(params);
/*  82 */         Object clickResult = onClick(params);
/*     */         
/*  84 */         if (clickResult == null) {
/*  85 */           clickResult = onUnhandledSubmit(params);
/*     */         }
/*     */         
/*  88 */         if (clickResult instanceof ResultObject) {
/*  89 */           retValue = (ResultObject)clickResult;
/*  90 */         } else if (clickResult instanceof HtmlCodeRenderer) {
/*  91 */           String code = ((HtmlCodeRenderer)clickResult).getHtmlCode(params);
/*  92 */           retValue = new ResultObject(0, code);
/*  93 */         } else if (clickResult instanceof String) {
/*  94 */           retValue = new ResultObject(0, clickResult);
/*     */         } 
/*  96 */         return retValue;
/*     */       } finally {
/*  98 */         log.debug("released lock for " + this.context);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean allowTextFieldSubmit() {
/* 104 */     return false;
/*     */   }
/*     */   
/*     */   protected abstract Object onUnhandledSubmit(Map paramMap);
/*     */   
/*     */   protected abstract String getFormContent(Map paramMap);
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\HtmlFormElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */