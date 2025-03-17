/*    */ package com.eoos.gm.tis2web.swdl.server.servlet;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.Dispatcher;
/*    */ import com.eoos.gm.tis2web.swdl.server.datamodel.system.SWDLControllerFacade;
/*    */ import com.eoos.html.ResultObject;
/*    */ import java.io.IOException;
/*    */ import java.util.Map;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SWDLServlet
/*    */   extends Dispatcher
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 27 */   private static final Logger log = Logger.getLogger(SWDLServlet.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/*    */     try {
/* 36 */       Map<String, HttpSession> mapParameters = getParameterMap(request);
/*    */ 
/*    */       
/* 39 */       HttpSession session = request.getSession(true);
/* 40 */       mapParameters.put("session", session);
/* 41 */       mapParameters.put("request", request);
/* 42 */       mapParameters.put("response", response);
/*    */       
/* 44 */       ResultObject result = SWDLControllerFacade.getInstance().execute(mapParameters);
/*    */       
/* 46 */       handleResult(request, response, result);
/*    */     }
/* 48 */     catch (Exception e) {
/* 49 */       log.error("Exception handling request - error:" + e, e);
/* 50 */       handleException(e, response);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\server\servlet\SWDLServlet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */