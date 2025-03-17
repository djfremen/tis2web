/*    */ package com.eoos.gm.tis2web.frame.export.common;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.servlet.ClusterTask;
/*    */ import com.eoos.util.Task;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ 
/*    */ public class CTSetPort
/*    */   implements ClusterTask, Task.InjectHttpRequest
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private volatile HttpServletRequest request;
/*    */   
/*    */   public Object execute() {
/* 15 */     ApplicationContext.getInstance().setPort(this.request.getServerPort());
/* 16 */     return Boolean.TRUE;
/*    */   }
/*    */   
/*    */   public void setHttpServletRequest(HttpServletRequest request) {
/* 20 */     this.request = request;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\CTSetPort.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */