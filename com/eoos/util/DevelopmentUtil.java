/*    */ package com.eoos.util;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DevelopmentUtil
/*    */ {
/*    */   public static long createRandom(long min, long max) {
/* 13 */     long offset = (long)(Math.random() * (max - min));
/* 14 */     return min + offset;
/*    */   }
/*    */   
/*    */   public static void sleep(long millis) {
/*    */     try {
/* 19 */       Thread.sleep(millis);
/* 20 */     } catch (InterruptedException e) {
/* 21 */       throw new ExceptionWrapper(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void sleepRandom(long min, long max) {
/* 27 */     sleep(createRandom(min, max));
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoo\\util\DevelopmentUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */