/*    */ package com.eoos.scsm.v2.filter;
/*    */ 
/*    */ public final class InversionFacade
/*    */   implements Filter {
/*    */   Filter filter;
/*    */   
/*    */   public InversionFacade(Filter filter) {
/*  8 */     this.filter = filter;
/*    */   }
/*    */   
/*    */   public boolean include(Object obj) {
/* 12 */     return !this.filter.include(obj);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\filter\InversionFacade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */