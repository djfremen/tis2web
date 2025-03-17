/*    */ package com.eoos.gm.tis2web.frame.export.common;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.dls.server.LeaseValidationProvider;
/*    */ import com.eoos.util.Task;
/*    */ 
/*    */ public class LeaseValidationTask_ServerPart
/*    */   implements Task
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private LeaseValidationTask clientPart;
/*    */   
/*    */   public LeaseValidationTask_ServerPart(LeaseValidationTask clientPart) {
/* 13 */     this.clientPart = clientPart;
/*    */   }
/*    */   
/*    */   public Object execute() {
/*    */     try {
/* 18 */       return Boolean.valueOf(LeaseValidationProvider.getLeaseValidation().validateLease(this.clientPart.swk, this.clientPart.lease));
/* 19 */     } catch (Exception e) {
/* 20 */       return e;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static boolean evaluateResult(Object result) throws Exception {
/* 25 */     if (result instanceof Exception) {
/* 26 */       throw (Exception)result;
/*    */     }
/* 28 */     return ((Boolean)result).booleanValue();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\LeaseValidationTask_ServerPart.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */