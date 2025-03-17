/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.ListValue;
/*    */ import java.io.Serializable;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ListValueImpl
/*    */   implements ListValue, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected List list;
/*    */   
/*    */   public ListValueImpl(List list) {
/* 14 */     this.list = list;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 18 */     return (this.list == null) ? "<empty>" : ("<list(" + this.list.size() + ")>");
/*    */   }
/*    */   
/*    */   public List getItems() {
/* 22 */     return this.list;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\ListValueImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */