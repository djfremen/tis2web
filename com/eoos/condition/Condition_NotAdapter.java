/*    */ package com.eoos.condition;
/*    */ 
/*    */ 
/*    */ public class Condition_NotAdapter
/*    */   implements Condition
/*    */ {
/*  7 */   private Condition condition = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Condition_NotAdapter(Condition condition) {
/* 13 */     this.condition = condition;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean check(Object obj) {
/* 25 */     return !this.condition.check(obj);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\condition\Condition_NotAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */