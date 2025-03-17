/*    */ package com.eoos.gm.tis2web.frame.dls;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.dls.client.api.Lease;
/*    */ import com.eoos.gm.tis2web.frame.dls.client.api.SoftwareKey;
/*    */ import com.eoos.util.Task;
/*    */ import java.io.ObjectStreamException;
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class LeaseRequest
/*    */   implements Task
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   SoftwareKey swk;
/*    */   String sessionID;
/*    */   
/*    */   public LeaseRequest(SoftwareKey swk, String sessionID) {
/* 18 */     this.swk = swk;
/* 19 */     this.sessionID = sessionID;
/*    */   }
/*    */   
/*    */   public Object execute() {
/* 23 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public static Lease resolveResult(Object obj) throws InvalidSoftwareKeyException {
/* 27 */     if (obj instanceof InvalidSoftwareKeyException)
/* 28 */       throw (InvalidSoftwareKeyException)obj; 
/* 29 */     if (obj instanceof RuntimeException) {
/* 30 */       throw (RuntimeException)obj;
/*    */     }
/* 32 */     return (Lease)obj;
/*    */   }
/*    */ 
/*    */   
/*    */   private Object readResolve() throws ObjectStreamException {
/* 37 */     return new LeaseRequest_ServerPart(this);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dls\LeaseRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */