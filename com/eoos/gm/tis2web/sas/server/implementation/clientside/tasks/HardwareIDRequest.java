/*    */ package com.eoos.gm.tis2web.sas.server.implementation.clientside.tasks;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.InvalidSessionException;
/*    */ import com.eoos.gm.tis2web.sas.server.implementation.serverside.SASServer;
/*    */ import com.eoos.util.Task;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class HardwareIDRequest
/*    */   implements Task, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String sessionID;
/*    */   
/*    */   public HardwareIDRequest(String sessionID) {
/* 15 */     this.sessionID = sessionID;
/*    */   }
/*    */   
/*    */   protected SASServer getSASServer() throws InvalidSessionException {
/* 19 */     return SASServer.getInstance(this.sessionID);
/*    */   }
/*    */   
/*    */   public Object execute() {
/*    */     try {
/* 24 */       Object result = getSASServer().getHardwareID();
/* 25 */       return result;
/* 26 */     } catch (Exception e) {
/* 27 */       return e;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\server\implementation\clientside\tasks\HardwareIDRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */