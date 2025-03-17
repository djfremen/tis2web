/*    */ package com.eoos.gm.tis2web.dtc.service.cai;
/*    */ 
/*    */ import com.eoos.util.v2.Util;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public interface DTC
/*    */   extends Serializable
/*    */ {
/*    */   public static abstract class Comparator
/*    */     implements java.util.Comparator
/*    */   {
/*    */     public int compare(Object o1, Object o2) {
/* 13 */       return compare((DTC)o1, (DTC)o2);
/*    */     }
/*    */     
/*    */     protected abstract int compare(DTC param1DTC1, DTC param1DTC2);
/*    */   }
/*    */   
/* 19 */   public static final Comparator COMPARATOR_BACCode = new Comparator()
/*    */     {
/*    */       protected int compare(DTC dtc1, DTC dtc2) {
/* 22 */         return Util.compare(dtc1.getBACCode(), dtc2.getBACCode());
/*    */       }
/*    */     };
/*    */ 
/*    */   
/* 27 */   public static final Comparator COMPARATOR_COUNTRYCode = new Comparator()
/*    */     {
/*    */       protected int compare(DTC dtc1, DTC dtc2) {
/* 30 */         return Util.compare(dtc1.getCountryCode(), dtc2.getCountryCode());
/*    */       }
/*    */     }; byte[] getContent(); String getBACCode();
/*    */   String getCountryCode();
/*    */   String getApplicationID();
/*    */   String getPortalID();
/* 36 */   public static interface Logged { public static final DTC.Comparator COMPARATOR_TIMESTAMP = new DTC.Comparator()
/*    */       {
/*    */         protected int compare(DTC dtc1, DTC dtc2) {
/* 39 */           return Util.compare(((DTC.Logged)dtc1).getTimestamp(), ((DTC.Logged)dtc2).getTimestamp());
/*    */         }
/*    */       };
/*    */     
/*    */     long getID();
/*    */     
/*    */     long getTimestamp(); }
/*    */ 
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\dtc\service\cai\DTC.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */