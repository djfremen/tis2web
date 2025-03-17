/*    */ package com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.filter;
/*    */ 
/*    */ import com.eoos.filter.Filter;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIOTSB;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class TSBFilter
/*    */   implements Filter
/*    */ {
/*    */   public boolean include(Object obj) {
/* 19 */     return include((SIOTSB)obj);
/*    */   }
/*    */   
/*    */   protected abstract boolean include(SIOTSB paramSIOTSB);
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\datamodel\domain\tsb\filter\TSBFilter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */