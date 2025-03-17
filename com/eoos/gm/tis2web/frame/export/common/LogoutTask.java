/*    */ package com.eoos.gm.tis2web.frame.export.common;
/*    */ 
/*    */ import com.eoos.util.Task;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class LogoutTask
/*    */   implements Task, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String sessionID;
/*    */   
/*    */   public LogoutTask(String sessionID) {
/* 13 */     this.sessionID = sessionID;
/*    */   }
/*    */   
/*    */   public Object execute() {
/* 17 */     return new Boolean(ClientContextProvider.getInstance().invalidateSession(this.sessionID));
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\LogoutTask.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */