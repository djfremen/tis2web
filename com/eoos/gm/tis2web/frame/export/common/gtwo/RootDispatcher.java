/*    */ package com.eoos.gm.tis2web.frame.export.common.gtwo;
/*    */ 
/*    */ import com.eoos.html.gtwo.servlet.dispatching.DispatcherImpl;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.http.HttpSession;
/*    */ 
/*    */ public class RootDispatcher
/*    */   extends DispatcherImpl
/*    */ {
/* 11 */   private static RootDispatcher instance = null;
/*    */   
/* 13 */   private final Object SYNC_PATH = new Object();
/* 14 */   private String contextPath = null;
/* 15 */   private String servletPath = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized RootDispatcher getInstance() {
/* 21 */     if (instance == null) {
/* 22 */       instance = new RootDispatcher();
/*    */     }
/* 24 */     return instance;
/*    */   }
/*    */   
/*    */   public void handle(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/* 28 */     synchronized (this.SYNC_PATH) {
/* 29 */       if (this.contextPath == null) {
/* 30 */         this.contextPath = request.getContextPath();
/* 31 */         this.servletPath = request.getServletPath();
/*    */       } 
/*    */     } 
/* 34 */     super.handle(session, request, response);
/*    */   }
/*    */   
/*    */   public String getContextPath() {
/* 38 */     return this.contextPath;
/*    */   }
/*    */   
/*    */   public String getServletPath() {
/* 42 */     return this.servletPath;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\gtwo\RootDispatcher.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */