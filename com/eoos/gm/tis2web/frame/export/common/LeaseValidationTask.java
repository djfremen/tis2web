/*    */ package com.eoos.gm.tis2web.frame.export.common;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.dls.client.api.Lease;
/*    */ import com.eoos.gm.tis2web.frame.dls.client.api.SoftwareKey;
/*    */ import com.eoos.util.Task;
/*    */ import java.io.ObjectStreamException;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LeaseValidationTask
/*    */   implements Task
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   SoftwareKey swk;
/*    */   Lease lease;
/*    */   
/*    */   public LeaseValidationTask(SoftwareKey swk, Lease lease) {
/* 18 */     this.swk = swk;
/* 19 */     this.lease = lease;
/*    */   }
/*    */   
/*    */   public Object execute() {
/* 23 */     return null;
/*    */   }
/*    */   
/*    */   public static Boolean evaluateResult(Object result) throws Exception {
/* 27 */     if (result instanceof Exception) {
/* 28 */       throw (Exception)result;
/*    */     }
/* 30 */     return (Boolean)result;
/*    */   }
/*    */ 
/*    */   
/*    */   private Object readResolve() throws ObjectStreamException {
/* 35 */     return new LeaseValidationTask_ServerPart(this);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\LeaseValidationTask.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */