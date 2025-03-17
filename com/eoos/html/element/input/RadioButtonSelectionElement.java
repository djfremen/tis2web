/*     */ package com.eoos.html.element.input;
/*     */ 
/*     */ import com.eoos.html.renderer.HtmlRadioButtonRenderer;
/*     */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class RadioButtonSelectionElement
/*     */   extends SelectionElementBase {
/*     */   private String parameterName;
/*     */   
/*     */   private class RendererCallback
/*     */     extends HtmlRadioButtonRenderer.CallbackAdapter {
/*     */     private Object option;
/*     */     
/*     */     public RendererCallback(Object option) {
/*  18 */       this.option = option;
/*     */     }
/*     */     
/*     */     public String getSubmitValue() {
/*  22 */       return RadioButtonSelectionElement.this.getSubmitValue(this.option);
/*     */     }
/*     */     
/*     */     public boolean isChecked() {
/*  26 */       return (RadioButtonSelectionElement.this.value != null && RadioButtonSelectionElement.this.value.equals(this.option));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isDisabled() {
/*  31 */       return RadioButtonSelectionElement.this.isDisabled();
/*     */     }
/*     */     
/*     */     public String getLabel() {
/*  35 */       return RadioButtonSelectionElement.this.getDisplayValue(this.option);
/*     */     }
/*     */     
/*     */     public String getParameterName() {
/*  39 */       return RadioButtonSelectionElement.this.parameterName;
/*     */     }
/*     */     
/*     */     public void getAdditionalAttributes(Map<String, String> map) {
/*  43 */       map.put("id", RadioButtonSelectionElement.this.parameterName);
/*  44 */       if (RadioButtonSelectionElement.this.autoSubmitOnClick()) {
/*  45 */         map.put("onClick", "javascript:FormSubmit('" + RadioButtonSelectionElement.this.clickID + "','1','" + RadioButtonSelectionElement.this.getBookmark() + "')");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean clicked = false;
/*     */   
/*     */   private String clickID;
/*     */ 
/*     */   
/*     */   public RadioButtonSelectionElement(String parameterName, List options) {
/*  56 */     super(parameterName, true, options);
/*  57 */     this.parameterName = "C" + parameterName;
/*  58 */     this.clickID = this.parameterName + "oc";
/*     */   }
/*     */   
/*     */   public String getBookmark() {
/*  62 */     return this.parameterName;
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/*  66 */     List options = getOptions();
/*     */     
/*  68 */     StringBuffer code = StringBufferPool.getThreadInstance().get();
/*     */     try {
/*  70 */       code.append("<table>{OPTIONS}</table>");
/*     */       
/*  72 */       for (int i = 0; i < options.size(); i++) {
/*  73 */         Object option = options.get(i);
/*     */         
/*  75 */         StringBuffer optionCode = StringBufferPool.getThreadInstance().get("<tr><td>{OPTION}</td></tr>");
/*     */         try {
/*  77 */           StringUtilities.replace(optionCode, "{OPTION}", HtmlRadioButtonRenderer.getInstance().getHtmlCode((HtmlRadioButtonRenderer.Callback)new RendererCallback(option)));
/*     */           
/*  79 */           StringUtilities.replace(code, "{OPTIONS}", optionCode.toString() + "{OPTIONS}");
/*     */         } finally {
/*     */           
/*  82 */           StringBufferPool.getThreadInstance().free(optionCode);
/*     */         } 
/*     */       } 
/*  85 */       StringUtilities.replace(code, "{OPTIONS}", "");
/*  86 */       return code.toString();
/*     */     } finally {
/*     */       
/*  89 */       StringBufferPool.getThreadInstance().free(code);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setValue(Map submitParams) {
/*  94 */     if (submitParams.containsKey(this.clickID)) {
/*  95 */       this.clicked = true;
/*     */     } else {
/*  97 */       this.clicked = false;
/*     */     } 
/*     */     
/* 100 */     Object value = submitParams.get(this.parameterName);
/* 101 */     if (value != null) {
/* 102 */       this.value = getOption((String)value);
/*     */     } else {
/* 104 */       this.value = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean autoSubmitOnClick() {
/* 109 */     return false;
/*     */   }
/*     */   
/*     */   public boolean clicked() {
/* 113 */     return this.clicked;
/*     */   }
/*     */   
/*     */   public final Object onClick(Map submitParams) {
/* 117 */     return onChange(submitParams);
/*     */   }
/*     */   
/*     */   protected Object onChange(Map submitParams) {
/* 121 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\input\RadioButtonSelectionElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */