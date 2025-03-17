/*     */ package com.eoos.gm.tis2web.sps.client.tool.common.impl;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.OptionValue;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.ToolOption;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.util.ToolUtils;
/*     */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ 
/*     */ public class ToolOptionImpl
/*     */   extends ToolAttributeImpl
/*     */   implements ToolOption, Serializable {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private String resourcePrefix;
/*  18 */   private List values = new ArrayList();
/*  19 */   private int defaultValueIndex = 0;
/*     */ 
/*     */   
/*     */   private ToolOptionImpl() {}
/*     */ 
/*     */   
/*     */   public ToolOptionImpl(String _toolId, String _key, List _values) {
/*  26 */     this.resourcePrefix = (new ToolUtils()).trim(_toolId);
/*  27 */     this.key = _key;
/*  28 */     this.values.addAll(_values);
/*     */   }
/*     */   
/*     */   public String getDenotation(Locale locale) {
/*  32 */     String result = null;
/*     */     try {
/*  34 */       result = LabelResourceProvider.getInstance().getLabelResource().getLabel(locale, getPropertyKey());
/*  35 */     } catch (Exception e) {}
/*     */     
/*  37 */     if (result == null || result.compareTo(getPropertyKey()) == 0) {
/*  38 */       result = this.key;
/*     */     }
/*  40 */     return result;
/*     */   }
/*     */   
/*     */   public OptionValue getOptionValueByPropertyValue(String valueKey) {
/*  44 */     OptionValue result = null;
/*  45 */     Iterator<OptionValue> it = this.values.iterator();
/*  46 */     while (it.hasNext()) {
/*  47 */       OptionValue val = it.next();
/*  48 */       String id = val.getKey();
/*  49 */       if (id.compareTo(valueKey) == 0) {
/*  50 */         result = val;
/*     */         break;
/*     */       } 
/*     */     } 
/*  54 */     return result;
/*     */   }
/*     */   
/*     */   public OptionValue getOptionValue(int index) {
/*  58 */     OptionValue result = null;
/*  59 */     if (index < this.values.size()) {
/*  60 */       result = this.values.get(index);
/*     */     }
/*  62 */     return result;
/*     */   }
/*     */   
/*     */   public int valueIndex(OptionValue val) {
/*  66 */     int i = this.values.indexOf(val);
/*  67 */     return i;
/*     */   }
/*     */   
/*     */   public List getValues() {
/*  71 */     List<OptionValue> result = new ArrayList();
/*  72 */     if (this.defaultValueIndex < this.values.size()) {
/*  73 */       OptionValue first = this.values.get(this.defaultValueIndex);
/*  74 */       result.add(first);
/*  75 */       Iterator<OptionValue> it = this.values.iterator();
/*  76 */       while (it.hasNext()) {
/*  77 */         OptionValue val = it.next();
/*  78 */         if (!val.equals(first)) {
/*  79 */           result.add(val);
/*     */         }
/*     */       } 
/*     */     } 
/*  83 */     return result;
/*     */   }
/*     */   
/*     */   public void addValue(OptionValue value) {
/*  87 */     this.values.add(value);
/*     */   }
/*     */   
/*     */   public int getDefaultValueIndex() {
/*  91 */     return this.defaultValueIndex;
/*     */   }
/*     */   
/*     */   public void setDefaultValueIndex(int i) {
/*  95 */     this.defaultValueIndex = i;
/*     */   }
/*     */   
/*     */   public boolean equals(Object o) {
/*  99 */     boolean result = false;
/* 100 */     if (o instanceof ToolOption && this.key.compareTo(((ToolOption)o).getKey()) == 0) {
/* 101 */       result = true;
/*     */     }
/* 103 */     return result;
/*     */   }
/*     */   
/*     */   public String getPropertyKey() {
/* 107 */     return (new ToolUtils()).trim(this.resourcePrefix + ".option." + this.key);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\common\impl\ToolOptionImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */