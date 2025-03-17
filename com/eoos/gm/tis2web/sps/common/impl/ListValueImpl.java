/*    */ package com.eoos.gm.tis2web.sps.common.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.ListValue;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ListValueImpl
/*    */   implements ListValue {
/*    */   protected List list;
/*    */   
/*    */   public ListValueImpl(List list) {
/* 11 */     this.list = list;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 15 */     return (this.list == null) ? "<empty>" : ("<list(" + this.list.size() + ")>");
/*    */   }
/*    */   
/*    */   public List getItems() {
/* 19 */     return this.list;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\impl\ListValueImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */