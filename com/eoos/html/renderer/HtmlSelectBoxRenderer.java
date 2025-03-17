/*     */ package com.eoos.html.renderer;
/*     */ 
/*     */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*     */ import com.eoos.util.StringUtilities;
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
/*     */ public class HtmlSelectBoxRenderer
/*     */   extends HtmlTagRenderer
/*     */ {
/*     */   private static final String TEMPLATE_SELECT = "<select name=\"{NAME}\" {MULTIPLE} {DISABLED} {READONLY} {SIZE} {ADDITIONAL}>{OPTIONLIST}</select>";
/*     */   private static final String TEMPLATE_OPTION = "<option value=\"{VALUE}\" {SELECTED} {ADDITIONAL}>{LABEL}</option>";
/*     */   
/*     */   public static abstract class CallbackAdapter
/*     */     implements Callback, AdditionalAttributes
/*     */   {
/*     */     public void getAdditionalAttributes(Map map) {}
/*     */     
/*     */     public HtmlSelectBoxRenderer.AdditionalAttributes getAdditionalAttributesOption(int optionIndex) {
/*  43 */       return null;
/*     */     }
/*     */     
/*     */     public boolean isDisabled() {
/*  47 */       return false;
/*     */     }
/*     */     
/*     */     public boolean isReadonly() {
/*  51 */       return false;
/*     */     }
/*     */     
/*     */     public int getSize() {
/*  55 */       return 1;
/*     */     }
/*     */     
/*     */     public boolean allowMultipleSelects() {
/*  59 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void init(Map params) {}
/*     */ 
/*     */     
/*     */     public void init() {}
/*     */   }
/*     */ 
/*     */   
/*  71 */   private static HtmlSelectBoxRenderer instance = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized HtmlSelectBoxRenderer getInstance() {
/*  80 */     if (instance == null) {
/*  81 */       instance = new HtmlSelectBoxRenderer();
/*     */     }
/*     */     
/*  84 */     return instance;
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Callback callback) {
/*  88 */     return getHtmlCode(callback, (Map)null);
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Callback callback, Map params) {
/*  92 */     callback.init(params);
/*     */     
/*  94 */     int nCount = callback.getOptionCount();
/*  95 */     StringBuffer strbSelect = StringBufferPool.getThreadInstance().get("<select name=\"{NAME}\" {MULTIPLE} {DISABLED} {READONLY} {SIZE} {ADDITIONAL}>{OPTIONLIST}</select>");
/*     */     
/*     */     try {
/*  98 */       StringBuffer strbOptions = StringBufferPool.getThreadInstance().get();
/*     */       
/*     */       try {
/* 101 */         for (int i = 0; i < nCount; i++) {
/* 102 */           StringBuffer strbOption = StringBufferPool.getThreadInstance().get("<option value=\"{VALUE}\" {SELECTED} {ADDITIONAL}>{LABEL}</option>");
/*     */           
/*     */           try {
/* 105 */             StringUtilities.replace(strbOption, "{VALUE}", callback.getOptionSubmitValue(i));
/* 106 */             StringUtilities.replace(strbOption, "{SELECTED}", callback.isSelectedOption(i) ? "selected=\"selected\"" : "");
/* 107 */             StringUtilities.replace(strbOption, "{LABEL}", callback.getOptionLabel(i));
/*     */             
/* 109 */             String str = null;
/*     */             
/* 111 */             if (callback instanceof AdditionalAttributes) {
/* 112 */               str = getAdditionalAttributesCode(((AdditionalAttributes)callback).getAdditionalAttributesOption(i));
/*     */             }
/*     */             
/* 115 */             StringUtilities.replace(strbOption, "{ADDITIONAL}", (str != null) ? str : "");
/*     */             
/* 117 */             strbOptions.append(strbOption);
/*     */           } finally {
/* 119 */             StringBufferPool.getThreadInstance().free(strbOption);
/*     */           } 
/*     */         } 
/*     */         
/* 123 */         StringUtilities.replace(strbSelect, "{NAME}", callback.getParameterName());
/* 124 */         StringUtilities.replace(strbSelect, "{MULTIPLE}", callback.allowMultipleSelects() ? "multiple" : "");
/* 125 */         StringUtilities.replace(strbSelect, "{DISABLED}", callback.isDisabled() ? "disabled" : "");
/* 126 */         StringUtilities.replace(strbSelect, "{READONLY}", callback.isReadonly() ? "readonly" : "");
/* 127 */         StringUtilities.replace(strbSelect, "{SIZE}", "size=\"" + callback.getSize() + "\"");
/* 128 */         StringUtilities.replace(strbSelect, "{OPTIONLIST}", strbOptions.toString());
/*     */       } finally {
/*     */         
/* 131 */         StringBufferPool.getThreadInstance().free(strbOptions);
/*     */       } 
/*     */       
/* 134 */       String additional = null;
/*     */       
/* 136 */       if (callback instanceof AdditionalAttributes) {
/* 137 */         additional = getAdditionalAttributesCode((AdditionalAttributes)callback);
/*     */       }
/*     */       
/* 140 */       StringUtilities.replace(strbSelect, "{ADDITIONAL}", (additional != null) ? additional : "");
/*     */       
/* 142 */       return strbSelect.toString();
/*     */     } finally {
/*     */       
/* 145 */       StringBufferPool.getThreadInstance().free(strbSelect);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected String getHtmlCode(HtmlTagRenderer.Callback callback, Map params) {
/* 150 */     return getHtmlCode((Callback)callback, params);
/*     */   }
/*     */   
/*     */   public static interface Callback extends HtmlTagRenderer.Callback {
/*     */     boolean isDisabled();
/*     */     
/*     */     int getOptionCount();
/*     */     
/*     */     String getOptionLabel(int param1Int);
/*     */     
/*     */     String getOptionSubmitValue(int param1Int);
/*     */     
/*     */     String getParameterName();
/*     */     
/*     */     boolean isReadonly();
/*     */     
/*     */     boolean isSelectedOption(int param1Int);
/*     */     
/*     */     int getSize();
/*     */     
/*     */     boolean allowMultipleSelects();
/*     */     
/*     */     void init();
/*     */   }
/*     */   
/*     */   public static interface AdditionalAttributes extends HtmlTagRenderer.AdditionalAttributes {
/*     */     AdditionalAttributes getAdditionalAttributesOption(int param1Int);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\renderer\HtmlSelectBoxRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */