/*     */ package com.eoos.html.element.gtwo;
/*     */ 
/*     */ import com.eoos.html.renderer.HtmlSelectBoxRenderer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class SelectBoxSelectionElement
/*     */   extends SelectionElementBase {
/*     */   protected RendererCallback renderingCallback;
/*     */   protected int size;
/*     */   
/*     */   protected class RendererCallback extends HtmlSelectBoxRenderer.CallbackAdapter {
/*     */     private List options;
/*     */     
/*     */     public void init(Map params) {
/*  18 */       this.options = SelectBoxSelectionElement.this.optionsCallback.getData();
/*     */     }
/*     */     
/*     */     public int getSize() {
/*  22 */       return SelectBoxSelectionElement.this.size;
/*     */     }
/*     */     
/*     */     public boolean isDisabled() {
/*  26 */       return SelectBoxSelectionElement.this.isDisabled();
/*     */     }
/*     */     
/*     */     public String getOptionLabel(int optionIndex) {
/*  30 */       return SelectBoxSelectionElement.this.getDisplayValue(this.options.get(optionIndex));
/*     */     }
/*     */     
/*     */     public int getOptionCount() {
/*  34 */       return this.options.size();
/*     */     }
/*     */     
/*     */     public boolean isSelectedOption(int optionIndex) {
/*  38 */       if (SelectBoxSelectionElement.this.singleSelectionMode() && SelectBoxSelectionElement.this.getValue() != null) {
/*  39 */         return SelectBoxSelectionElement.this.getValue().equals(this.options.get(optionIndex));
/*     */       }
/*  41 */       if (!SelectBoxSelectionElement.this.singleSelectionMode() && SelectBoxSelectionElement.this.getValue() != null) {
/*  42 */         return ((Collection)SelectBoxSelectionElement.this.getValue()).contains(this.options.get(optionIndex));
/*     */       }
/*  44 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getOptionSubmitValue(int optionIndex) {
/*  49 */       return SelectBoxSelectionElement.this.getSubmitValue(this.options.get(optionIndex));
/*     */     }
/*     */     
/*     */     public String getParameterName() {
/*  53 */       return SelectBoxSelectionElement.this.parameterName;
/*     */     }
/*     */     
/*     */     public boolean allowMultipleSelects() {
/*  57 */       return !SelectBoxSelectionElement.this.singleSelectionMode();
/*     */     }
/*     */     
/*     */     public void getAdditionalAttributes(Map<String, String> map) {
/*  61 */       map.put("id", SelectBoxSelectionElement.this.parameterName);
/*  62 */       if (SelectBoxSelectionElement.this.autoSubmitOnChange()) {
/*  63 */         String targetFrame = SelectBoxSelectionElement.this.getTargetFrame();
/*  64 */         String function = null;
/*  65 */         if (targetFrame != null) {
/*  66 */           function = "javascript:TFormSubmit('" + SelectBoxSelectionElement.this.clickID + "','1','" + SelectBoxSelectionElement.this.getTargetBookmark() + "','" + SelectBoxSelectionElement.this.getTargetFrame() + "')";
/*     */         } else {
/*  68 */           function = "javascript:FormSubmit('" + SelectBoxSelectionElement.this.clickID + "','1','" + SelectBoxSelectionElement.this.getTargetBookmark() + "')";
/*     */         } 
/*  70 */         map.put("onChange", function);
/*     */       } 
/*     */     }
/*     */     
/*     */     public HtmlSelectBoxRenderer.AdditionalAttributes getAdditionalAttributesOption(int optionIndex) {
/*  75 */       final Map additionalAttributes = SelectBoxSelectionElement.this.getAdditionalAttributes(this.options.get(optionIndex));
/*  76 */       if (additionalAttributes == null) {
/*  77 */         return null;
/*     */       }
/*  79 */       return new HtmlSelectBoxRenderer.AdditionalAttributes() {
/*     */           public HtmlSelectBoxRenderer.AdditionalAttributes getAdditionalAttributesOption(int optionIndex) {
/*  81 */             return null;
/*     */           }
/*     */           
/*     */           public void getAdditionalAttributes(Map map) {
/*  85 */             map.putAll(additionalAttributes);
/*     */           }
/*     */         };
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean clicked = false;
/*     */   
/*     */   protected String clickID;
/*     */   
/*  96 */   protected String targetBookmark = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SelectBoxSelectionElement(String parameterName, boolean singleSelectionMode, DataRetrievalAbstraction.DataCallback optionsCallback, int size, String targetBookmark) {
/* 102 */     super(parameterName, singleSelectionMode, optionsCallback);
/* 103 */     this.size = size;
/* 104 */     this.renderingCallback = new RendererCallback();
/* 105 */     this.clickID = this.parameterName + "cid";
/* 106 */     this.targetBookmark = targetBookmark;
/*     */   }
/*     */   
/*     */   public SelectBoxSelectionElement(String parameterName, boolean singleSelectionMode, DataRetrievalAbstraction.DataCallback optionsCallback, int size) {
/* 110 */     this(parameterName, singleSelectionMode, optionsCallback, size, (String)null);
/*     */   }
/*     */   
/*     */   protected String getTargetFrame() {
/* 114 */     return null;
/*     */   }
/*     */   
/*     */   protected String getTargetBookmark() {
/* 118 */     return (this.targetBookmark == null) ? this.parameterName : this.targetBookmark;
/*     */   }
/*     */   
/*     */   public String getBookmark() {
/* 122 */     return this.parameterName;
/*     */   }
/*     */   
/*     */   protected boolean autoSubmitOnChange() {
/* 126 */     return false;
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 130 */     return HtmlSelectBoxRenderer.getInstance().getHtmlCode((HtmlSelectBoxRenderer.Callback)this.renderingCallback);
/*     */   }
/*     */   
/*     */   public void setValue(Map submitParams) {
/* 134 */     Object value = submitParams.get(this.parameterName);
/*     */     
/* 136 */     if (value != null) {
/* 137 */       if (value instanceof String[] && !singleSelectionMode()) {
/* 138 */         List<Object> valueList = new ArrayList(((String[])value).length);
/*     */         
/* 140 */         for (int i = 0; i < ((String[])value).length; i++) {
/* 141 */           valueList.add(getOption(((String[])value)[i]));
/*     */         }
/*     */         
/* 144 */         setValue(valueList);
/* 145 */       } else if (value instanceof String && !singleSelectionMode()) {
/* 146 */         List<Object> valueList = new ArrayList(1);
/* 147 */         valueList.add(getOption((String)value));
/* 148 */         setValue(valueList);
/* 149 */       } else if (value instanceof String && singleSelectionMode()) {
/* 150 */         setValue(getOption((String)value));
/*     */       } else {
/* 152 */         throw new IllegalArgumentException();
/*     */       } 
/*     */     } else {
/* 155 */       setValue((Object)null);
/*     */     } 
/*     */     
/* 158 */     if (submitParams.containsKey(this.clickID)) {
/* 159 */       this.clicked = true;
/*     */     } else {
/* 161 */       this.clicked = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean clicked() {
/* 166 */     return this.clicked;
/*     */   }
/*     */   
/*     */   public final Object onClick(Map submitParams) {
/* 170 */     return onChange(submitParams);
/*     */   }
/*     */   
/*     */   protected Object onChange(Map submitParams) {
/* 174 */     return null;
/*     */   }
/*     */   
/*     */   protected Map getAdditionalAttributes(Object option) {
/* 178 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\gtwo\SelectBoxSelectionElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */