/*    */ package com.eoos.gm.tis2web.sps.client.common;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ExecutionMode
/*    */ {
/*    */   public static boolean isLoadTest() {
/* 10 */     return "true".equalsIgnoreCase(System.getProperty("execution.mode.load.test"));
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\common\ExecutionMode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */