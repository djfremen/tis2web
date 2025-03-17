/*    */ package com.eoos.gm.tis2web.frame.dls.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LeaseValidationProvider
/*    */ {
/*    */   public static synchronized LeaseValidation getLeaseValidation() {
/* 11 */     return LeaseValidationImpl.getInstance();
/*    */   }
/*    */ 
/*    */   
/*    */   public static synchronized void clearCache() {
/* 16 */     LeaseValidationImpl.clearCache();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dls\server\LeaseValidationProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */