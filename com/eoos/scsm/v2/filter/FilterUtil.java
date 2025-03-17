/*    */ package com.eoos.scsm.v2.filter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FilterUtil
/*    */ {
/*    */   public static Filter invert(Filter filter) {
/*  9 */     if (filter instanceof InversionFacade) {
/* 10 */       return ((InversionFacade)filter).filter;
/*    */     }
/* 12 */     return new InversionFacade(filter);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\filter\FilterUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */