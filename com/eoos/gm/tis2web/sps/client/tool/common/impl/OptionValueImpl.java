/*    */ package com.eoos.gm.tis2web.sps.client.tool.common.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.tool.common.export.OptionValue;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.util.ToolUtils;
/*    */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OptionValueImpl
/*    */   extends ToolValueImpl
/*    */   implements OptionValue
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String resourcePrefix;
/*    */   
/*    */   public OptionValueImpl(String _toolId, String _valueKey) {
/* 21 */     this.resourcePrefix = (new ToolUtils()).trim(_toolId);
/* 22 */     this.key = _valueKey;
/*    */   }
/*    */   
/*    */   public void setKey(String key) {
/* 26 */     this.key = key;
/*    */   }
/*    */   
/*    */   public String getPropertyKey() {
/* 30 */     return (new ToolUtils()).trim(this.resourcePrefix + ".optionvalue." + this.key);
/*    */   }
/*    */   
/*    */   public boolean equals(Object o) {
/* 34 */     boolean result = false;
/* 35 */     if (o instanceof OptionValue && this.key.compareTo(((OptionValue)o).getKey()) == 0) {
/* 36 */       result = true;
/*    */     }
/* 38 */     return result;
/*    */   }
/*    */   
/*    */   public String getDenotation(Locale locale) {
/* 42 */     String result = null;
/*    */     try {
/* 44 */       result = LabelResourceProvider.getInstance().getLabelResource().getLabel(locale, getPropertyKey());
/* 45 */     } catch (Exception e) {}
/*    */     
/* 47 */     if (result == null || result.compareTo(getPropertyKey()) == 0) {
/* 48 */       result = this.key;
/*    */     }
/* 50 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\common\impl\OptionValueImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */