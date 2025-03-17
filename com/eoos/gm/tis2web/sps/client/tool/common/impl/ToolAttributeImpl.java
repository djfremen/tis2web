/*    */ package com.eoos.gm.tis2web.sps.client.tool.common.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.tool.common.export.ToolAttribute;
/*    */ import java.io.Serializable;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ public abstract class ToolAttributeImpl
/*    */   implements ToolAttribute, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected String key;
/*    */   private String label;
/*    */   
/*    */   protected ToolAttributeImpl() {}
/*    */   
/*    */   public ToolAttributeImpl(String _key, String _label) {
/* 18 */     this.key = _key;
/* 19 */     this.label = _label;
/*    */   }
/*    */   
/*    */   public String getKey() {
/* 23 */     return this.key;
/*    */   }
/*    */   
/*    */   public String getDenotation(Locale locale) {
/* 27 */     return this.label;
/*    */   }
/*    */   
/*    */   public boolean equals(Object o) {
/* 31 */     boolean result = false;
/* 32 */     if (o instanceof ToolAttribute && this.key.compareTo(((ToolAttribute)o).getKey()) == 0) {
/* 33 */       result = true;
/*    */     }
/* 35 */     return result;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 39 */     return this.key.hashCode();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\common\impl\ToolAttributeImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */