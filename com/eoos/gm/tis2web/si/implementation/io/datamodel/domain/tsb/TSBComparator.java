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
/*    */ 
/*    */ 
/*    */ public abstract class TSBComparator
/*    */   implements Comparator
/*    */ {
/*    */   public static final int DIRECTION_ASCENDING = 1;
/*    */   public static final int DIRECTION_DESCENDING = -1;
/*    */   protected int direction;
/*    */   
/*    */   protected TSBComparator(int direction) {
/* 25 */     this.direction = direction;
/*    */   }
/*    */   
/*    */   protected TSBComparator() {
/* 29 */     this(1);
/*    */   }
/*    */   
/*    */   public int compare(Object o1, Object o2) {
/* 33 */     return compare((SIOTSB)o1, (SIOTSB)o2) * this.direction;
/*    */   }
/*    */   
/*    */   protected abstract int compare(SIOTSB paramSIOTSB1, SIOTSB paramSIOTSB2);
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\datamodel\domain\tsb\TSBComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */