/*    */ package com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb;
/*    */ 
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
/*    */ public class TSBComparator_Salesmake
/*    */   extends TSBComparator
/*    */ {
/* 18 */   public static final Comparator COMPARATOR_ASC = new TSBComparator_Salesmake(1);
/*    */   
/* 20 */   public static final Comparator COMPARATOR_DESC = new TSBComparator_Salesmake(-1);
/*    */ 
/*    */   
/*    */   protected TSBComparator_Salesmake() {}
/*    */ 
/*    */   
/*    */   protected TSBComparator_Salesmake(int direction) {
/* 27 */     super(direction);
/*    */   }
/*    */   
/*    */   protected int compare(SIOTSB tsb, SIOTSB tsb2) {
/*    */     try {
/* 32 */       return tsb.getSalesMake().compareTo(tsb2.getSalesMake());
/* 33 */     } catch (Exception e) {
/* 34 */       return 0;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\datamodel\domain\tsb\TSBComparator_Salesmake.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */