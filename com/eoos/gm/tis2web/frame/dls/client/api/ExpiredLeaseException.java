/*   */ package com.eoos.gm.tis2web.frame.dls.client.api;
/*   */ 
/*   */ public class ExpiredLeaseException
/*   */   extends InvalidLeaseException {
/*   */   private static final long serialVersionUID = 1L;
/*   */   
/*   */   public ExpiredLeaseException(Lease lease) {
/* 8 */     super(lease);
/*   */   }
/*   */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dls\client\api\ExpiredLeaseException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */