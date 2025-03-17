/*    */ package com.eoos.gm.tis2web.frame.export.common;
/*    */ 
/*    */ import com.eoos.util.Task;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class SecTokenCheckTask
/*    */   implements Task {
/*  8 */   private static final Logger log = Logger.getLogger(SecTokenCheckTask.class);
/*    */   
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   protected String sessionID;
/*    */   
/*    */   protected String token;
/*    */   
/*    */   public SecTokenCheckTask(String sessionID, String token) {
/* 17 */     this.sessionID = sessionID;
/* 18 */     this.token = token;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object execute() {
/* 23 */     Boolean ret = Boolean.FALSE;
/*    */     try {
/* 25 */       TaskServer ts = (TaskServer)Class.forName(getClass().getName() + "_TServer").newInstance();
/* 26 */       return ts.execute(this);
/* 27 */     } catch (Exception e) {
/* 28 */       log.error("unable to execute SecTokenCheckTask, returning false - exception:" + e, e);
/* 29 */       ret = Boolean.FALSE;
/*    */       
/* 31 */       return ret;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\SecTokenCheckTask.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */