/*    */ package com.eoos.gm.tis2web.frame.dls.client.api;
/*    */ 
/*    */ public class InvalidLeaseException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private final Lease lease;
/*    */   
/*    */   public InvalidLeaseException(Lease lease) {
/* 10 */     this.lease = lease;
/*    */   }
/*    */   
/*    */   public final Lease getLease() {
/* 14 */     return this.lease;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dls\client\api\InvalidLeaseException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */