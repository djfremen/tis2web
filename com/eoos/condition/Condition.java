/*    */ package com.eoos.condition;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface Condition
/*    */ {
/* 10 */   public static final Condition TRUE = new Condition()
/*    */     {
/*    */       public boolean check(Object obj) {
/* 13 */         return true;
/*    */       }
/*    */     };
/*    */   
/*    */   boolean check(Object paramObject);
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\condition\Condition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */