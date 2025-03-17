/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.export.system;
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
/*    */   public static boolean isClientSide() {
/* 13 */     return (System.getProperty("com.eoos.gm.tis2web.sps.client") != null);
/*    */   }
/*    */   
/*    */   public static boolean isServerSide() {
/* 17 */     return !isClientSide();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\export\system\SystemUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */