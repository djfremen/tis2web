/*    */ package com.eoos.gm.tis2web.sas.server.implementation.clientside.tasks;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.InvalidSessionException;
/*    */ import com.eoos.gm.tis2web.sas.common.model.reqres.SecurityAccessRequest;
/*    */ import com.eoos.gm.tis2web.sas.server.implementation.serverside.SASServer;
/*    */ import com.eoos.util.Task;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ public class HandleSARequest
/*    */   implements Task, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String sessionID;
/*    */   private SecurityAccessRequest request;
/*    */   
/*    */   public HandleSARequest(String sessionID, SecurityAccessRequest request) {
/* 18 */     this.sessionID = sessionID;
/* 19 */     this.request = request;
/*    */   }
/*    */   
/*    */   protected SASServer getSASServer() throws InvalidSessionException {
/* 23 */     return SASServer.getInstance(this.sessionID);
/*    */   }
/*    */   
/*    */   public Object execute() {
/*    */     try {
/* 28 */       Object result = getSASServer().handle(this.request, this.sessionID);
/* 29 */       return result;
/* 30 */     } catch (Exception e) {
/* 31 */       return e;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\server\implementation\clientside\tasks\HandleSARequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */