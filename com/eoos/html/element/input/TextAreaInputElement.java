/*     */ package com.eoos.html.element.input;
/*     */ 
/*     */ import com.eoos.html.renderer.HtmlAnchorRenderer;
/*     */ import com.eoos.html.renderer.HtmlTextAreaRenderer;
/*     */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class TextAreaInputElement
/*     */   extends TextInputElementBase {
/*     */   private RendererCallback renderingCallback;
/*     */   
/*     */   private class RendererCallback extends HtmlTextAreaRenderer.CallbackAdapter {
/*     */     private RendererCallback() {}
/*     */     
/*     */     public void getAdditionalAttributes(Map<String, String> map) {
/*  16 */       TextAreaInputElement.this.getAdditionalAttributes(map);
/*     */       
/*  18 */       if (TextAreaInputElement.this.getID() != null) {
/*  19 */         map.put("id", TextAreaInputElement.this.getID());
/*     */       }
/*     */       
/*  22 */       if (TextAreaInputElement.this.getStyleClass() != null) {
/*  23 */         map.put("class", TextAreaInputElement.this.getStyleClass());
/*     */       }
/*     */     }
/*     */     
/*     */     public String getParameterName() {
/*  28 */       return TextAreaInputElement.this.parameterName;
/*     */     }
/*     */     
/*     */     public boolean isDisabled() {
/*  32 */       return TextAreaInputElement.this.isDisabled();
/*     */     }
/*     */     
/*     */     public boolean isReadonly() {
/*  36 */       return TextAreaInputElement.this.isReadonly();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getCols() {
/*  41 */       return TextAreaInputElement.this.getCols();
/*     */     }
/*     */     
/*     */     public String getRows() {
/*  45 */       return TextAreaInputElement.this.getRows();
/*     */     }
/*     */     
/*     */     public String getValue() {
/*  49 */       return (String)TextAreaInputElement.this.getValue();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   private String rows = null;
/*  57 */   private String cols = null;
/*     */ 
/*     */   
/*     */   public TextAreaInputElement(String parameterName) {
/*  61 */     this(parameterName, (String)null, (String)null);
/*     */   }
/*     */   
/*     */   public boolean isReadonly() {
/*  65 */     return false;
/*     */   }
/*     */   
/*     */   public TextAreaInputElement(String parameterName, String rows, String cols) {
/*  69 */     super(parameterName);
/*  70 */     this.renderingCallback = new RendererCallback();
/*  71 */     this.rows = rows;
/*  72 */     this.cols = cols;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBookmark() {
/*  77 */     return this.parameterName;
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/*  81 */     StringBuffer code = StringBufferPool.getThreadInstance().get();
/*     */     try {
/*  83 */       if (renderAnchor()) {
/*  84 */         code.append(HtmlAnchorRenderer.getInstance().getHtmlCode((HtmlAnchorRenderer.Callback)new HtmlAnchorRenderer.CallbackAdapter()
/*     */               {
/*     */                 public String getName() {
/*  87 */                   return TextAreaInputElement.this.getBookmark();
/*     */                 }
/*     */               }));
/*     */       }
/*  91 */       code.append(HtmlTextAreaRenderer.getInstance().getHtmlCode((HtmlTextAreaRenderer.Callback)this.renderingCallback));
/*  92 */       return code.toString();
/*     */     } finally {
/*     */       
/*  95 */       StringBufferPool.getThreadInstance().free(code);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean renderAnchor() {
/* 100 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void getAdditionalAttributes(Map map) {}
/*     */ 
/*     */   
/*     */   protected String getID() {
/* 108 */     return null;
/*     */   }
/*     */   
/*     */   protected String getStyleClass() {
/* 112 */     return null;
/*     */   }
/*     */   
/*     */   protected String getRows() {
/* 116 */     return this.rows;
/*     */   }
/*     */   
/*     */   protected String getCols() {
/* 120 */     return this.cols;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\input\TextAreaInputElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */