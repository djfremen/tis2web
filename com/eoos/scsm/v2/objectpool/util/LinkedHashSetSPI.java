/*    */ package com.eoos.scsm.v2.objectpool.util;
/*    */ 
/*    */ import com.eoos.scsm.v2.objectpool.ObjectPool;
/*    */ import java.util.LinkedHashSet;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LinkedHashSetSPI
/*    */   implements ObjectPool.SPI
/*    */ {
/* 12 */   public static LinkedHashSetSPI INSTANCE = new LinkedHashSetSPI();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object create() {
/* 18 */     return new LinkedHashSet();
/*    */   }
/*    */   
/*    */   public void reset(Object object) {
/* 22 */     ((LinkedHashSet)object).clear();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\objectpoo\\util\LinkedHashSetSPI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */