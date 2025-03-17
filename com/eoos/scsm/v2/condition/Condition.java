/*    */ package com.eoos.scsm.v2.condition;
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
/*    */   
/* 18 */   public static final Condition FALSE = new Condition()
/*    */     {
/*    */       public boolean check(Object obj) {
/* 21 */         return false;
/*    */       }
/*    */     };
/*    */   
/*    */   boolean check(Object paramObject);
/*    */   
/*    */   public static class InversionAdapter
/*    */     implements Condition {
/*    */     public InversionAdapter(Condition condition) {
/* 30 */       this.condition = condition;
/*    */     }
/*    */     private Condition condition;
/*    */     public boolean check(Object obj) {
/* 34 */       return !this.condition.check(obj);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\condition\Condition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */