/*     */ package com.eoos.html.element.input;
/*     */ 
/*     */ import com.eoos.html.renderer.HtmlButtonRenderer;
/*     */ import java.util.Map;
/*     */ 
/*     */ public abstract class ClickButtonElement
/*     */   extends HtmlInputElementBase
/*     */ {
/*     */   private class RenderingCallback
/*     */     extends HtmlButtonRenderer.CallbackAdapter {
/*     */     private RenderingCallback() {}
/*     */     
/*     */     public String getLabel() {
/*  14 */       return ClickButtonElement.this.getLabel();
/*     */     }
/*     */     
/*     */     protected String getOnClickHandler() {
/*  18 */       String targetFrame = ClickButtonElement.this.getTargetFrame();
/*  19 */       if (targetFrame != null) {
/*  20 */         if (ClickButtonElement.this.isDispose()) {
/*  21 */           return "javascript:TFormSubmit('" + ClickButtonElement.this.parameterName + "','1','" + ClickButtonElement.this.getTargetBookmark() + "','" + targetFrame + "',true)";
/*     */         }
/*  23 */         return "javascript:TFormSubmit('" + ClickButtonElement.this.parameterName + "','1','" + ClickButtonElement.this.getTargetBookmark() + "','" + targetFrame + "')";
/*     */       } 
/*     */       
/*  26 */       return "javascript:FormSubmit('" + ClickButtonElement.this.parameterName + "','1','" + ClickButtonElement.this.getTargetBookmark() + "')";
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isDisabled() {
/*  31 */       return ClickButtonElement.this.isDisabled();
/*     */     }
/*     */     
/*     */     public void getAdditionalAttributes(Map map) {
/*  35 */       super.getAdditionalAttributes(map);
/*  36 */       ClickButtonElement.this.getAdditionalAttributes(map);
/*     */     }
/*     */     
/*     */     public boolean isSubmitButton() {
/*  40 */       return ClickButtonElement.this.isSubmitButton();
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean clicked = false;
/*  45 */   protected String targetBookmark = null;
/*     */ 
/*     */   
/*     */   private RenderingCallback renderingCallback;
/*     */ 
/*     */ 
/*     */   
/*     */   public ClickButtonElement(String parameterName, String targetBookmark) {
/*  53 */     super(parameterName);
/*  54 */     this.targetBookmark = targetBookmark;
/*  55 */     this.renderingCallback = new RenderingCallback();
/*     */   }
/*     */   
/*     */   protected abstract String getLabel();
/*     */   
/*     */   public String getHtmlCode(Map params) {
/*  61 */     return HtmlButtonRenderer.getInstance().getHtmlCode((HtmlButtonRenderer.Callback)this.renderingCallback);
/*     */   }
/*     */   
/*     */   public boolean clicked() {
/*  65 */     return this.clicked;
/*     */   }
/*     */   
/*     */   public void setValue(Map submitParams) {
/*  69 */     if (submitParams.containsKey(this.parameterName)) {
/*  70 */       this.clicked = true;
/*     */     } else {
/*  72 */       this.clicked = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected String getTargetBookmark() {
/*  77 */     return (this.targetBookmark != null) ? this.targetBookmark : this.parameterName;
/*     */   }
/*     */   
/*     */   public String getBookmark() {
/*  81 */     return this.parameterName;
/*     */   }
/*     */   
/*     */   public void setValue(Object value) {
/*  85 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public Object getValue() {
/*  89 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   protected String getTargetFrame() {
/*  93 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void getAdditionalAttributes(Map map) {}
/*     */   
/*     */   protected boolean isSubmitButton() {
/* 100 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDispose() {
/* 109 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\input\ClickButtonElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */