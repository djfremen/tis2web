/*    */ package com.eoos.gm.tis2web.frame.export.common.gtwo;
/*    */ 
/*    */ import com.eoos.html.gtwo.servlet.ApplicationConnector;
/*    */ import javax.servlet.http.HttpServlet;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.http.HttpSession;
/*    */ 
/*    */ public class ApplicationConnector
/*    */   implements ApplicationConnector
/*    */ {
/*    */   public void onStartup(HttpServlet servlet) {}
/*    */   
/*    */   public void onShutdown(HttpServlet servlet) {}
/*    */   
/*    */   public void handle(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/* 17 */     RootDispatcher.getInstance().handle(session, request, response);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\gtwo\ApplicationConnector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */