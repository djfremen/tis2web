/*     */ package com.eoos.html.element.input;
/*     */ 
/*     */ import com.eoos.html.renderer.HtmlSelectBoxRenderer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class SelectBoxSelectionElement extends SelectionElementBase {
/*     */   private RendererCallback renderingCallback;
/*     */   private int size;
/*     */   
/*     */   protected class RendererCallback extends HtmlSelectBoxRenderer.CallbackAdapter {
/*     */     private List options;
/*     */     
/*     */     public void init(Map params) {
/*  17 */       this.options = SelectBoxSelectionElement.this.getOptions();
/*     */     }
/*     */     
/*     */     public int getSize() {
/*  21 */       return SelectBoxSelectionElement.this.size;
/*     */     }
/*     */     
/*     */     public boolean isDisabled() {
/*  25 */       return SelectBoxSelectionElement.this.isDisabled();
/*     */     }
/*     */     
/*     */     public String getOptionLabel(int optionIndex) {
/*  29 */       return SelectBoxSelectionElement.this.getDisplayValue(this.options.get(optionIndex));
/*     */     }
/*     */     
/*     */     public int getOptionCount() {
/*  33 */       return this.options.size();
/*     */     }
/*     */     
/*     */     public boolean isSelectedOption(int optionIndex) {
/*  37 */       if (SelectBoxSelectionElement.this.singleSelectionMode() && SelectBoxSelectionElement.this.getValue() != null) {
/*  38 */         return SelectBoxSelectionElement.this.getValue().equals(this.options.get(optionIndex));
/*     */       }
/*  40 */       if (!SelectBoxSelectionElement.this.singleSelectionMode() && SelectBoxSelectionElement.this.getValue() != null) {
/*  41 */         return ((Collection)SelectBoxSelectionElement.this.getValue()).contains(this.options.get(optionIndex));
/*     */       }
/*  43 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getOptionSubmitValue(int optionIndex) {
/*  48 */       return SelectBoxSelectionElement.this.getSubmitValue(this.options.get(optionIndex));
/*     */     }
/*     */     
/*     */     public String getParameterName() {
/*  52 */       return SelectBoxSelectionElement.this.parameterName;
/*     */     }
/*     */     
/*     */     public boolean allowMultipleSelects() {
/*  56 */       return !SelectBoxSelectionElement.this.singleSelectionMode();
/*     */     }
/*     */     
/*     */     public void getAdditionalAttributes(Map<String, String> map) {
/*  60 */       map.put("id", SelectBoxSelectionElement.this.parameterName);
/*  61 */       if (SelectBoxSelectionElement.this.autoSubmitOnChange()) {
/*  62 */         String targetFrame = SelectBoxSelectionElement.this.getTargetFrame();
/*  63 */         String function = null;
/*  64 */         if (targetFrame != null) {
/*  65 */           function = "javascript:TFormSubmit('" + SelectBoxSelectionElement.this.clickID + "','1','" + SelectBoxSelectionElement.this.getTargetBookmark() + "','" + SelectBoxSelectionElement.this.getTargetFrame() + "')";
/*     */         } else {
/*  67 */           function = "javascript:FormSubmit('" + SelectBoxSelectionElement.this.clickID + "','1','" + SelectBoxSelectionElement.this.getTargetBookmark() + "')";
/*     */         } 
/*  69 */         map.put("onChange", function);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean clicked = false;
/*     */ 
/*     */   
/*     */   private String clickID;
/*     */ 
/*     */   
/*  82 */   private String targetBookmark = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SelectBoxSelectionElement(String parameterName, boolean singleSelectionMode, List options, int size, String targetBookmark) {
/*  88 */     super(parameterName, singleSelectionMode, options);
/*  89 */     this.size = size;
/*  90 */     this.renderingCallback = new RendererCallback();
/*  91 */     this.clickID = this.parameterName + "cid";
/*  92 */     this.targetBookmark = targetBookmark;
/*     */   }
/*     */   
/*     */   public SelectBoxSelectionElement(String parameterName, boolean singleSelectionMode, List options, int size) {
/*  96 */     this(parameterName, singleSelectionMode, options, size, (String)null);
/*     */   }
/*     */   
/*     */   protected String getTargetFrame() {
/* 100 */     return null;
/*     */   }
/*     */   
/*     */   protected String getTargetBookmark() {
/* 104 */     return (this.targetBookmark == null) ? this.parameterName : this.targetBookmark;
/*     */   }
/*     */   
/*     */   public String getBookmark() {
/* 108 */     return this.parameterName;
/*     */   }
/*     */   
/*     */   protected boolean autoSubmitOnChange() {
/* 112 */     return false;
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 116 */     return HtmlSelectBoxRenderer.getInstance().getHtmlCode((HtmlSelectBoxRenderer.Callback)this.renderingCallback);
/*     */   }
/*     */   
/*     */   public void setValue(Map submitParams) {
/* 120 */     if (!isDisabled()) {
/* 121 */       Object value = submitParams.get(this.parameterName);
/*     */       
/* 123 */       if (value != null) {
/* 124 */         if (value instanceof String[] && !singleSelectionMode()) {
/* 125 */           List<Object> valueList = new ArrayList(((String[])value).length);
/*     */           
/* 127 */           for (int i = 0; i < ((String[])value).length; i++) {
/* 128 */             valueList.add(getOption(((String[])value)[i]));
/*     */           }
/*     */           
/* 131 */           setValue(valueList);
/* 132 */         } else if (value instanceof String && !singleSelectionMode()) {
/* 133 */           List<Object> valueList = new ArrayList(1);
/* 134 */           valueList.add(getOption((String)value));
/* 135 */           setValue(valueList);
/* 136 */         } else if (value instanceof String && singleSelectionMode()) {
/* 137 */           setValue(getOption((String)value));
/*     */         } else {
/* 139 */           throw new IllegalArgumentException();
/*     */         } 
/*     */       } else {
/* 142 */         setValue((Object)null);
/*     */       } 
/*     */       
/* 145 */       if (submitParams.containsKey(this.clickID)) {
/* 146 */         this.clicked = true;
/*     */       } else {
/* 148 */         this.clicked = false;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean clicked() {
/* 154 */     return this.clicked;
/*     */   }
/*     */   
/*     */   public final Object onClick(Map submitParams) {
/* 158 */     return onChange(submitParams);
/*     */   }
/*     */   
/*     */   protected Object onChange(Map submitParams) {
/* 162 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\input\SelectBoxSelectionElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */