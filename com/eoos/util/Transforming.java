/*    */ package com.eoos.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface Transforming<S, T>
/*    */ {
/*  9 */   public static final Transforming<? extends Object, ? extends Object> NULL_TRANSFORMER = new Transforming<Object, Object>()
/*    */     {
/*    */       public Object transform(Object object) {
/* 12 */         return null;
/*    */       }
/*    */     };
/*    */   
/*    */   T transform(S paramS);
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoo\\util\Transforming.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */