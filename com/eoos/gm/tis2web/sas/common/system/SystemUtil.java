/*    */ package com.eoos.gm.tis2web.sas.common.system;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SystemUtil
/*    */ {
/*    */   public static boolean isClientVM() {
/* 13 */     return (System.getProperty("com.eoos.gm.tis2web.sas.client") != null);
/*    */   }
/*    */   
/*    */   public static boolean isServerVM() {
/* 17 */     return !isClientVM();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\common\system\SystemUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */