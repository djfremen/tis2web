/*    */ package com.eoos.gm.tis2web.frame.dls;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.dls.client.api.Lease;
/*    */ import com.eoos.gm.tis2web.frame.dls.server.DLSServiceServer;
/*    */ import com.eoos.util.Task;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class LeaseRequest_ServerPart
/*    */   implements Task, Task.InjectHttpRequest
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private LeaseRequest clientPart;
/*    */   private String clientIP;
/*    */   
/*    */   public LeaseRequest_ServerPart(LeaseRequest clientPart) {
/* 19 */     this.clientPart = clientPart;
/*    */   }
/*    */   
/*    */   public void setHttpServletRequest(HttpServletRequest request) {
/* 23 */     this.clientIP = request.getRemoteAddr();
/*    */   }
/*    */   
/*    */   public Object execute() {
/*    */     try {
/* 28 */       return DLSServiceServer.getInstance(this.clientPart.sessionID).requestLease(this.clientPart.swk, this.clientIP);
/* 29 */     } catch (Exception e) {
/* 30 */       return e;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static Lease resolveResult(Object obj) throws InvalidSoftwareKeyException {
/* 35 */     if (obj instanceof InvalidSoftwareKeyException)
/* 36 */       throw (InvalidSoftwareKeyException)obj; 
/* 37 */     if (obj instanceof RuntimeException) {
/* 38 */       throw (RuntimeException)obj;
/*    */     }
/* 40 */     return (Lease)obj;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dls\LeaseRequest_ServerPart.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */