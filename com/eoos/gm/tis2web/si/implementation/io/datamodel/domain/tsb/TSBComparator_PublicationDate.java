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
/*    */ public class TSBComparator_PublicationDate
/*    */   extends TSBComparator
/*    */ {
/* 18 */   public static final Comparator COMPARATOR_ASC = new TSBComparator_PublicationDate(1);
/*    */   
/* 20 */   public static final Comparator COMPARATOR_DESC = new TSBComparator_PublicationDate(-1);
/*    */ 
/*    */   
/*    */   protected TSBComparator_PublicationDate() {}
/*    */ 
/*    */   
/*    */   protected TSBComparator_PublicationDate(int direction) {
/* 27 */     super(direction);
/*    */   }
/*    */   
/*    */   protected int compare(SIOTSB tsb, SIOTSB tsb2) {
/*    */     try {
/* 32 */       return Long.valueOf(tsb.getPublicationDate().getTime()).compareTo(Long.valueOf(tsb2.getPublicationDate().getTime()));
/* 33 */     } catch (Exception e) {
/* 34 */       return 0;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\datamodel\domain\tsb\TSBComparator_PublicationDate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */