/*    */ package com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.comparator;
/*    */ 
/*    */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.TSBComparator;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIOTSB;
/*    */ import java.util.Comparator;
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
/*    */ public class TSBComparator_RemedyNumber
/*    */   extends TSBComparator
/*    */ {
/* 19 */   public static final Comparator COMPARATOR_ASC = (Comparator)new TSBComparator_RemedyNumber(1);
/*    */   
/* 21 */   public static final Comparator COMPARATOR_DESC = (Comparator)new TSBComparator_RemedyNumber(-1);
/*    */   
/*    */   protected TSBComparator_RemedyNumber(int direction) {
/* 24 */     super(direction);
/*    */   }
/*    */   
/*    */   protected int compare(SIOTSB tsb, SIOTSB tsb2) {
/*    */     try {
/* 29 */       return tsb.getRemedyNumber().compareTo(tsb2.getRemedyNumber());
/* 30 */     } catch (Exception e) {
/* 31 */       return 0;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\datamodel\domain\tsb\comparator\TSBComparator_RemedyNumber.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */