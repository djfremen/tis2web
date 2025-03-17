/*     */ package com.eoos.html.element.input;
/*     */ 
/*     */ import com.eoos.html.renderer.HtmlAnchorRenderer;
/*     */ import com.eoos.html.renderer.HtmlTextInputFieldRenderer;
/*     */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class TextInputElement
/*     */   extends TextInputElementBase {
/*     */   private RendererCallback renderingCallback;
/*     */   
/*     */   private class RendererCallback extends HtmlTextInputFieldRenderer.CallbackAdapter {
/*     */     private RendererCallback() {}
/*     */     
/*     */     public void getAdditionalAttributes(Map<String, String> map) {
/*  16 */       TextInputElement.this.getAdditionalAttributes(map);
/*     */       
/*  18 */       if (TextInputElement.this.getMaxLength() != -1) {
/*  19 */         map.put("maxlength", String.valueOf(TextInputElement.this.getMaxLength()));
/*     */       }
/*     */       
/*  22 */       if (TextInputElement.this.getSize() != -1) {
/*  23 */         map.put("size", String.valueOf(TextInputElement.this.getSize()));
/*     */       }
/*     */       
/*  26 */       if (TextInputElement.this.getID() != null) {
/*  27 */         map.put("id", TextInputElement.this.getID());
/*     */       }
/*     */       
/*  30 */       if (TextInputElement.this.getStyleClass() != null) {
/*  31 */         map.put("class", TextInputElement.this.getStyleClass());
/*     */       }
/*     */     }
/*     */     
/*     */     public String getParameterName() {
/*  36 */       return TextInputElement.this.parameterName;
/*     */     }
/*     */     
/*     */     public String getValue() {
/*  40 */       if (TextInputElement.this.getValue() == null) {
/*  41 */         return "";
/*     */       }
/*  43 */       return (String)TextInputElement.this.getValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isDisabled() {
/*  48 */       return TextInputElement.this.isDisabled();
/*     */     }
/*     */     
/*     */     public boolean isMasked() {
/*  52 */       return TextInputElement.this.isMasked();
/*     */     }
/*     */     
/*     */     public boolean isReadonly() {
/*  56 */       return TextInputElement.this.isReadonly();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   private int size = -1;
/*     */   
/*  66 */   private int maxlength = -1;
/*     */ 
/*     */   
/*     */   public TextInputElement(String parameterName) {
/*  70 */     this(parameterName, -1, -1);
/*     */   }
/*     */   
/*     */   public boolean isReadonly() {
/*  74 */     return false;
/*     */   }
/*     */   
/*     */   public TextInputElement(String parameterName, int size, int maxlength) {
/*  78 */     super(parameterName);
/*  79 */     this.renderingCallback = new RendererCallback();
/*  80 */     this.size = size;
/*  81 */     this.maxlength = maxlength;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBookmark() {
/*  86 */     return this.parameterName;
/*     */   }
/*     */   
/*     */   protected int getMaxLength() {
/*  90 */     return this.maxlength;
/*     */   }
/*     */   
/*     */   protected int getSize() {
/*  94 */     return this.size;
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/*  98 */     StringBuffer code = StringBufferPool.getThreadInstance().get();
/*     */     try {
/* 100 */       if (renderAnchor()) {
/* 101 */         code.append(HtmlAnchorRenderer.getInstance().getHtmlCode((HtmlAnchorRenderer.Callback)new HtmlAnchorRenderer.CallbackAdapter()
/*     */               {
/*     */                 public String getName() {
/* 104 */                   return TextInputElement.this.getBookmark();
/*     */                 }
/*     */               }));
/*     */       }
/* 108 */       code.append(HtmlTextInputFieldRenderer.getInstance().getHtmlCode((HtmlTextInputFieldRenderer.Callback)this.renderingCallback));
/* 109 */       return code.toString();
/*     */     } finally {
/*     */       
/* 112 */       StringBufferPool.getThreadInstance().free(code);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean renderAnchor() {
/* 117 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void getAdditionalAttributes(Map map) {}
/*     */   
/*     */   protected boolean isMasked() {
/* 124 */     return false;
/*     */   }
/*     */   
/*     */   protected String getID() {
/* 128 */     return null;
/*     */   }
/*     */   
/*     */   protected String getStyleClass() {
/* 132 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\input\TextInputElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */