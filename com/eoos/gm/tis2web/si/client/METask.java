/*    */ package com.eoos.gm.tis2web.si.client;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.dls.SessionKey;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.InvalidSessionException;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SI;
/*    */ import com.eoos.gm.tis2web.si.v2.SIDataAdapterFacade;
/*    */ import com.eoos.scsm.v2.reflect.ReflectionUtil;
/*    */ import com.eoos.util.Task;
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.lang.reflect.Method;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class METask
/*    */   implements Task
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private SessionKey swk;
/*    */   private String methodName;
/*    */   private Object[] args;
/*    */   
/*    */   public METask(SessionKey sessionKey, String methodName, Object[] args) {
/* 26 */     this.swk = sessionKey;
/* 27 */     this.methodName = methodName;
/* 28 */     this.args = args;
/*    */   }
/*    */   
/*    */   public Object execute() {
/* 32 */     ClientContext context = ClientContextProvider.getInstance().getContext(this.swk.getSessionID());
/* 33 */     if (context == null) {
/* 34 */       return new InvalidSessionException(this.swk.getSessionID());
/*    */     }
/*    */     try {
/* 37 */       Method m = ReflectionUtil.getSuitableMethod(SI.class, this.methodName);
/* 38 */       return m.invoke(SIDataAdapterFacade.getInstance(context).getSI(), this.args);
/* 39 */     } catch (InvocationTargetException e) {
/* 40 */       return e.getCause();
/* 41 */     } catch (Exception e) {
/* 42 */       return e;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 48 */     return "METask[SI#" + this.methodName + "()]";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\client\METask.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */