/*    */ package com.eoos.gm.tis2web.frame.dls;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.dls.server.DLSServiceServer;
/*    */ import com.eoos.util.Task;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RegisterSWKTask_ServerPart
/*    */   implements Task, Task.InjectHttpRequest
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private RegisterSWKTask clientPart;
/*    */   private String clientIP;
/*    */   
/*    */   public RegisterSWKTask_ServerPart(RegisterSWKTask clientPart) {
/* 17 */     this.clientPart = clientPart;
/*    */   }
/*    */   
/*    */   public Object execute() {
/*    */     try {
/* 22 */       return Boolean.valueOf(DLSServiceServer.getInstance(this.clientPart.sessionID).register(this.clientPart.swk, this.clientIP));
/* 23 */     } catch (Exception e) {
/* 24 */       return e;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void setHttpServletRequest(HttpServletRequest request) {
/* 29 */     this.clientIP = request.getRemoteAddr();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dls\RegisterSWKTask_ServerPart.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */