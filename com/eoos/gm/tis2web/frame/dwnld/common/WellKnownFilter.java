/*    */ package com.eoos.gm.tis2web.frame.dwnld.common;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.dwnld.client.api.DwnldFilter;
/*    */ import com.eoos.scsm.v2.multiton.v4.SerializableEnumBase;
/*    */ 
/*    */ public class WellKnownFilter
/*    */   extends SerializableEnumBase
/*    */   implements DwnldFilter {
/*    */   private static final long serialVersionUID = 1L;
/* 10 */   public static final WellKnownFilter NEWEST_VERSION = new WellKnownFilter() {
/*    */       private static final long serialVersionUID = 1L;
/*    */       
/*    */       public String toString() {
/* 14 */         return "Newest Version Filter";
/*    */       }
/*    */     };
/*    */   
/* 18 */   public static final Object[] DOMAIN = new Object[] { NEWEST_VERSION };
/*    */   
/*    */   protected Object[] getInstanceArray() {
/* 21 */     return DOMAIN;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\common\WellKnownFilter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */