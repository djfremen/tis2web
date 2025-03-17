/*    */ package com.eoos.scsm.v2.filter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface Filter
/*    */ {
/*  9 */   public static final Filter INCLUDE_ALL = new Filter()
/*    */     {
/*    */       public boolean include(Object obj) {
/* 12 */         return true;
/*    */       }
/*    */     };
/*    */ 
/*    */   
/* 17 */   public static final Filter EXCLUDE_ALL = new Filter()
/*    */     {
/*    */       public boolean include(Object obj) {
/* 20 */         return false;
/*    */       }
/*    */     };
/*    */   
/*    */   boolean include(Object paramObject);
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\filter\Filter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */