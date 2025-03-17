/*    */ package com.eoos.scsm.v2.objectpool.util;
/*    */ 
/*    */ import com.eoos.scsm.v2.objectpool.ObjectPool;
/*    */ import java.util.LinkedList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LinkedListSPI
/*    */   implements ObjectPool.SPI
/*    */ {
/* 12 */   public static LinkedListSPI INSTANCE = new LinkedListSPI();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object create() {
/* 18 */     return new LinkedList();
/*    */   }
/*    */   
/*    */   public void reset(Object object) {
/* 22 */     ((LinkedList)object).clear();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\objectpoo\\util\LinkedListSPI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */