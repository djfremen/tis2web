/*    */ package com.eoos.gm.tis2web.frame.dwnld.common;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.dls.client.api.Lease;
/*    */ import com.eoos.gm.tis2web.frame.dls.client.api.SoftwareKey;
/*    */ import com.eoos.gm.tis2web.frame.dwnld.server.DwnldServiceServer;
/*    */ import com.eoos.scsm.v2.reflect.ReflectionUtil;
/*    */ import com.eoos.util.Task;
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.lang.reflect.Method;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class METask
/*    */   implements Task
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private SoftwareKey swk;
/*    */   private Lease lease;
/*    */   private String methodName;
/*    */   private Object[] args;
/*    */   
/*    */   public METask(SoftwareKey swk, Lease lease, String methodName, Object[] args) {
/* 25 */     this.swk = swk;
/* 26 */     this.lease = lease;
/* 27 */     this.methodName = methodName;
/* 28 */     this.args = args;
/*    */   }
/*    */   
/*    */   public Object execute() {
/*    */     try {
/* 33 */       Method m = ReflectionUtil.getSuitableMethod(DwnldServiceServer.class, this.methodName);
/* 34 */       return m.invoke(DwnldServiceServer.getInstance(this.swk, this.lease), this.args);
/* 35 */     } catch (InvocationTargetException e) {
/* 36 */       return e.getCause();
/* 37 */     } catch (Exception e) {
/* 38 */       return e;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String toString() {
/* 43 */     return "METask[DwnldServiceServer#" + this.methodName + "()]";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\common\METask.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */