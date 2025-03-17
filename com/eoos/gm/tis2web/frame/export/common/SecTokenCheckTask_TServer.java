/*    */ package com.eoos.gm.tis2web.frame.export.common;
/*    */ 
/*    */ import com.eoos.util.Task;
/*    */ 
/*    */ 
/*    */ public class SecTokenCheckTask_TServer
/*    */   implements TaskServer
/*    */ {
/*    */   public Object execute(Task _task) throws Exception {
/* 10 */     SecTokenCheckTask task = (SecTokenCheckTask)_task;
/* 11 */     Boolean ret = Boolean.FALSE;
/* 12 */     ClientContext context = ClientContextProvider.getInstance().getContext(task.sessionID);
/* 13 */     if (context != null) {
/* 14 */       ret = Boolean.valueOf(SecurityToken.getInstance(context).checkToken(task.token));
/*    */     } else {
/* 16 */       throw new InvalidSessionException(task.sessionID);
/*    */     } 
/*    */     
/* 19 */     return ret;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\SecTokenCheckTask_TServer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */