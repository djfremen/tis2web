/*    */ package com.eoos.gm.tis2web.sps.service.cai;
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
/*    */ public class GetJNLPDescriptorTask
/*    */   implements Task, Task.InjectHttpRequest, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String sessionID;
/*    */   private HttpServletRequest request;
/*    */   
/*    */   public GetJNLPDescriptorTask(String sessionID) {
/* 20 */     this.sessionID = sessionID;
/*    */   }
/*    */   
/*    */   public Object execute() {
/*    */     try {
/* 25 */       ClientContext context = ClientContextProvider.getInstance().getContext(this.sessionID);
/* 26 */       if (context == null) {
/* 27 */         throw new Exception("invalid session: " + this.sessionID);
/*    */       }
/* 29 */       synchronized (context.getLockObject()) {
/* 30 */         Class<?> clazz = Class.forName("com.eoos.gm.tis2web.sps.server.implementation.ui.html.sps.home.SPSHomePanel");
/* 31 */         Object instance = clazz.getMethod("getInstance", new Class[] { ClientContext.class }).invoke(null, new Object[] { context });
/* 32 */         String descriptor = (String)clazz.getMethod("getJNLPDescriptor", new Class[] { HttpServletRequest.class }).invoke(instance, new Object[] { this.request });
/* 33 */         return descriptor;
/*    */       } 
/* 35 */     } catch (Throwable t) {
/* 36 */       return t;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void setHttpServletRequest(HttpServletRequest request) {
/* 41 */     this.request = request;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\service\cai\GetJNLPDescriptorTask.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */