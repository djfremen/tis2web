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
/*    */ public class TSBComparator_Model
/*    */   extends TSBComparator
/*    */ {
/* 18 */   public static final Comparator COMPARATOR_ASC = new TSBComparator_Model(1);
/*    */   
/* 20 */   public static final Comparator COMPARATOR_DESC = new TSBComparator_Model(-1);
/*    */ 
/*    */   
/*    */   protected TSBComparator_Model() {}
/*    */ 
/*    */   
/*    */   protected TSBComparator_Model(int direction) {
/* 27 */     super(direction);
/*    */   }
/*    */   
/*    */   protected int compare(SIOTSB tsb, SIOTSB tsb2) {
/*    */     try {
/* 32 */       return tsb.getModel().compareTo(tsb2.getModel());
/* 33 */     } catch (Exception e) {
/* 34 */       return 0;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\datamodel\domain\tsb\TSBComparator_Model.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */