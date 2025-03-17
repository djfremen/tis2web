/*    */ package com.eoos.gm.tis2web.frame.dls;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.dls.client.api.SoftwareKey;
/*    */ import com.eoos.util.Task;
/*    */ import java.io.ObjectStreamException;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CheckStartupTask
/*    */   implements Task
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   SoftwareKey swk;
/*    */   String sessionID;
/*    */   
/*    */   public CheckStartupTask(SoftwareKey swk, String sessionID) {
/* 17 */     this.swk = swk;
/* 18 */     this.sessionID = sessionID;
/*    */   }
/*    */   
/*    */   public Object execute() {
/* 22 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public static boolean evaluateResult(Object result) throws Exception {
/* 26 */     if (result instanceof Exception) {
/* 27 */       throw (Exception)result;
/*    */     }
/* 29 */     return ((Boolean)result).booleanValue();
/*    */   }
/*    */ 
/*    */   
/*    */   private Object readResolve() throws ObjectStreamException {
/* 34 */     return new CheckStartupTask_ServerPart(this);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dls\CheckStartupTask.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */