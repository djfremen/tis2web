/*    */ package com.eoos.gm.tis2web.frame.qos;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Gate
/*    */ {
/*    */   private static final int MAX_SERVICE_THREADS = 10;
/*    */   private static final int MAX_SERVICE_THRESHOLD = 1000;
/*    */   
/*    */   public static boolean admit() {
/* 13 */     if (Monitor.getQueueSize() > 10)
/* 14 */       return false; 
/* 15 */     if (Monitor.getServiceTime() > 1000.0F) {
/* 16 */       if (Monitor.getQueueSize() == 0) {
/* 17 */         Monitor.restartObservationInterval();
/*    */       } else {
/* 19 */         return false;
/*    */       } 
/*    */     }
/* 22 */     return true;
/*    */   }
/*    */   
/*    */   public static boolean reject() {
/* 26 */     return !admit();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\qos\Gate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */