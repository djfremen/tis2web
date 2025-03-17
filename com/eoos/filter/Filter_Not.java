/*    */ package com.eoos.filter;
/*    */ 
/*    */ 
/*    */ public class Filter_Not
/*    */   implements Filter
/*    */ {
/*  7 */   private Filter filter = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Filter_Not(Filter filter) {
/* 13 */     this.filter = filter;
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
/*    */   public boolean include(Object obj) {
/* 25 */     return !this.filter.include(obj);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\filter\Filter_Not.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */