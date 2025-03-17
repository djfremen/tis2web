/*    */ package com.eoos.gm.tis2web.sps.client.tool.common.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.tool.common.export.ToolValue;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ToolValueImpl
/*    */   implements ToolValue, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected String key;
/*    */   
/*    */   protected ToolValueImpl() {}
/*    */   
/*    */   public ToolValueImpl(String _key) {
/* 22 */     this.key = _key;
/*    */   }
/*    */   
/*    */   public String getKey() {
/* 26 */     return this.key;
/*    */   }
/*    */   
/*    */   public boolean equals(Object o) {
/* 30 */     boolean result = false;
/* 31 */     if (o instanceof ToolValue && this.key.compareTo(((ToolValue)o).getKey()) == 0) {
/* 32 */       result = true;
/*    */     }
/* 34 */     return result;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 38 */     return this.key.hashCode();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\common\impl\ToolValueImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */