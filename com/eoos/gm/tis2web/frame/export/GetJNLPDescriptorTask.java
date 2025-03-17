/*    */ package com.eoos.gm.tis2web.frame.export;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*    */ import com.eoos.util.Task;
/*    */ import java.io.Serializable;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GetJNLPDescriptorTask
/*    */   implements Task, Task.InjectHttpRequest, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String sessionID;
/*    */   private String className;
/*    */   private transient HttpServletRequest request;
/*    */   
/*    */   public GetJNLPDescriptorTask(String sessionID, String className) {
/* 22 */     this.sessionID = sessionID;
/* 23 */     this.className = className;
/*    */   }
/*    */   
/*    */   public Object execute() {
/*    */     try {
/* 28 */       ClientContext context = ClientContextProvider.getInstance().getContext(this.sessionID);
/* 29 */       if (context == null) {
/* 30 */         throw new Exception("invalid session: " + this.sessionID);
/*    */       }
/* 32 */       synchronized (context.getLockObject()) {
/* 33 */         Class<?> clazz = Class.forName(this.className);
/* 34 */         Object instance = clazz.getMethod("getInstance", new Class[] { ClientContext.class }).invoke(null, new Object[] { context });
/* 35 */         String descriptor = (String)clazz.getMethod("getJNLPDescriptor", new Class[] { HttpServletRequest.class }).invoke(instance, new Object[] { this.request });
/* 36 */         return descriptor;
/*    */       } 
/* 38 */     } catch (Throwable t) {
/* 39 */       return t;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void setHttpServletRequest(HttpServletRequest request) {
/* 44 */     this.request = request;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\GetJNLPDescriptorTask.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */