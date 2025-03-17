/*    */ package com.eoos.gm.tis2web.frame.qos;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Monitor
/*    */ {
/* 10 */   private static Map queue = new HashMap<Object, Object>();
/* 11 */   private static float controlVariable = 0.0F;
/*    */   
/*    */   public static synchronized void registerEntry(String user, Thread thread) {
/* 14 */     queue.put(user, user);
/*    */   }
/*    */   
/*    */   public static synchronized void registerExit(String user, long serviceTime) {
/* 18 */     queue.remove(user);
/* 19 */     if (queue.size() == 0) {
/* 20 */       controlVariable = 0.0F;
/*    */     } else {
/* 22 */       controlVariable = (controlVariable + (float)serviceTime) / 2.0F;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static int getQueueSize() {
/* 27 */     return queue.size();
/*    */   }
/*    */   
/*    */   public static float getServiceTime() {
/* 31 */     return controlVariable;
/*    */   }
/*    */   
/*    */   public static synchronized void restartObservationInterval() {
/* 35 */     queue.clear();
/* 36 */     controlVariable = 0.0F;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\qos\Monitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */