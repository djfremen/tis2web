/*     */ package com.eoos.html.element.input;
/*     */ 
/*     */ import com.eoos.html.renderer.HtmlLinkRenderer;
/*     */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ public abstract class LinkElement
/*     */   extends HtmlInputElementBase
/*     */ {
/*     */   protected RenderingCallback renderingCallback;
/*     */   
/*     */   public class RenderingCallback
/*     */     extends HtmlLinkRenderer.CallbackAdapter
/*     */   {
/*     */     public String getLink() {
/*  17 */       String targetFrame = LinkElement.this.getTargetFrame();
/*     */       
/*  19 */       return LinkElement.this.getLink(targetFrame);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getLabel() {
/*  24 */       return LinkElement.this.getLabel();
/*     */     }
/*     */     
/*     */     public void getAdditionalAttributes(Map<String, String> map) {
/*  28 */       LinkElement.this.getAdditionalAttributes(map);
/*  29 */       if (map.get("id") == null) {
/*  30 */         map.put("id", LinkElement.this.parameterName);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  36 */   protected String targetBookmark = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean clicked = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LinkElement(String parameterName, String targetBookmark) {
/*  48 */     super(parameterName);
/*  49 */     this.targetBookmark = targetBookmark;
/*  50 */     this.renderingCallback = new RenderingCallback();
/*     */   }
/*     */   
/*     */   protected String getTargetFrame() {
/*  54 */     return null;
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/*  58 */     StringBuffer code = StringBufferPool.getThreadInstance().get();
/*     */     
/*     */     try {
/*  61 */       if (!isDisabled()) {
/*  62 */         code.append(HtmlLinkRenderer.getInstance().getHtmlCode((HtmlLinkRenderer.Callback)this.renderingCallback));
/*     */       } else {
/*  64 */         code.append(this.renderingCallback.getLabel());
/*     */       } 
/*     */       
/*  67 */       return code.toString();
/*     */     } finally {
/*     */       
/*  70 */       StringBufferPool.getThreadInstance().free(code);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean clicked() {
/*  75 */     return this.clicked;
/*     */   }
/*     */   
/*     */   public void setValue(Map submitParams) {
/*  79 */     if (submitParams.containsKey(this.parameterName)) {
/*  80 */       this.clicked = true;
/*     */     } else {
/*  82 */       this.clicked = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected String getTargetBookmark() {
/*  87 */     return (this.targetBookmark != null) ? this.targetBookmark : this.parameterName;
/*     */   }
/*     */   
/*     */   public String getBookmark() {
/*  91 */     return this.parameterName;
/*     */   }
/*     */   
/*     */   public void setValue(Object value) {
/*  95 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public Object getValue() {
/*  99 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract Object onClick(Map paramMap);
/*     */ 
/*     */   
/*     */   protected abstract String getLabel();
/*     */ 
/*     */   
/*     */   protected void getAdditionalAttributes(Map map) {}
/*     */   
/*     */   protected String getLink(String targetFrame) {
/* 112 */     if (targetFrame != null) {
/* 113 */       return "javascript:TFormSubmit('" + this.parameterName + "','1','" + getTargetBookmark() + "','" + targetFrame + "')";
/*     */     }
/* 115 */     return "javascript:TFormSubmit('" + this.parameterName + "','1','" + getTargetBookmark() + "',null)";
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\input\LinkElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */