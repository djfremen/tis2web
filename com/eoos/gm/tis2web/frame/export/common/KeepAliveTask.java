/*    */ package com.eoos.gm.tis2web.frame.export.common;
/*    */ 
/*    */ import com.eoos.util.Task;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class KeepAliveTask
/*    */   implements Task, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String sessionID;
/*    */   
/*    */   public KeepAliveTask(String sessionID) {
/* 16 */     this.sessionID = sessionID;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object execute() {
/*    */     try {
/* 26 */       ClientContext context = ClientContextProvider.getInstance().getContext(this.sessionID);
/* 27 */       if (context != null) {
/* 28 */         context.keepAlive();
/* 29 */         return null;
/*    */       } 
/* 31 */       throw new InvalidSessionException(this.sessionID);
/*    */     }
/* 33 */     catch (Exception e) {
/* 34 */       return e;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\KeepAliveTask.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */