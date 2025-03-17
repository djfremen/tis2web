/*    */ package com.eoos.gm.tis2web.frame.export;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.DefaultController;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*    */ import com.eoos.security.execution.delimiter.ExecutionDelimiter;
/*    */ import com.eoos.util.Task;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Properties;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LoginTask
/*    */   implements Task, Task.InjectHttpRequest, Task.InjectHttpResponse
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String ticket;
/*    */   private Properties properties;
/*    */   private transient HttpServletRequest request;
/*    */   
/*    */   public LoginTask(Properties properties, String ticket) {
/* 28 */     this.properties = properties;
/* 29 */     this.ticket = ticket;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object execute() {
/* 34 */     Properties ret = null;
/* 35 */     if (ExecutionDelimiter.check(this.ticket)) {
/* 36 */       ClientContext context = ClientContextProvider.getInstance().getContext(this.properties.getProperty("user"));
/* 37 */       if (context != null) {
/* 38 */         ClientContextProvider.getInstance().invalidateSession(context.getSessionID());
/*    */       }
/*    */       
/* 41 */       Map<Object, Object> params = new HashMap<Object, Object>();
/* 42 */       params.putAll(this.properties);
/* 43 */       DefaultController.getInstance().init(params);
/*    */       
/* 45 */       context = ClientContextProvider.getInstance().getContext(this.properties.getProperty("user"));
/* 46 */       if (context != null) {
/* 47 */         ret = new Properties();
/* 48 */         ret.put("session.id", context.getSessionID());
/* 49 */         ret.put("cookie", this.request.getHeader("Cookie"));
/* 50 */         ret.put("task.execution.url", ApplicationContext.getInstance().getProperty("frame.url.task.execution"));
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 55 */     return ret;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setHttpServletRequest(HttpServletRequest request) {
/* 60 */     this.request = request;
/*    */   }
/*    */   
/*    */   public void setHttpServletResponse(HttpServletResponse response) {}
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\LoginTask.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */