/*     */ package com.eoos.html.gtwo.servlet;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import java.io.IOException;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ApplicationServlet
/*     */   extends HttpServlet
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  23 */   private static final Logger log = Logger.getLogger(ApplicationServlet.class);
/*     */   
/*  25 */   protected ApplicationConnector applicationConnector = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(ServletConfig config) throws ServletException {
/*  31 */     log.info("initializing dispatcher");
/*  32 */     super.init(config);
/*  33 */     log.info("calling application startup procedure");
/*  34 */     onApplicationStartup();
/*     */   }
/*     */   
/*     */   public void destroy() {
/*  38 */     log.info("destroying dispatcher");
/*  39 */     log.info("calling application shutdown procedure");
/*  40 */     onApplicationShutdown();
/*  41 */     this.applicationConnector = null;
/*     */   }
/*     */   
/*     */   protected synchronized ApplicationConnector getApplicationConnector() {
/*  45 */     if (this.applicationConnector == null) {
/*     */       try {
/*  47 */         String className = getServletConfig().getInitParameter("application.connector.class");
/*  48 */         Class<?> c = Class.forName(className);
/*  49 */         this.applicationConnector = (ApplicationConnector)c.newInstance();
/*  50 */       } catch (Exception e) {
/*  51 */         log.error("unable to init application connector - error:" + e, e);
/*  52 */         throw new ExceptionWrapper(e);
/*     */       } 
/*     */     }
/*  55 */     return this.applicationConnector;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onApplicationShutdown() {
/*  60 */     getApplicationConnector().onShutdown(this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onApplicationStartup() {
/*  65 */     ApplicationConnector ac = getApplicationConnector();
/*  66 */     ac.onStartup(this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/*  71 */     processRequest(request, response);
/*     */   }
/*     */   
/*     */   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/*  75 */     processRequest(request, response);
/*     */   }
/*     */   
/*     */   public String getServletInfo() {
/*  79 */     return "No description";
/*     */   }
/*     */   
/*     */   protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/*  83 */     HttpSession session = request.getSession(true);
/*  84 */     synchronized (session) {
/*     */       try {
/*  86 */         getApplicationConnector().handle(session, request, response);
/*  87 */       } catch (Throwable t) {
/*  88 */         log.error("unable to process request - exception:" + t + " Trace:", t);
/*  89 */         log.error("throwing wrapping servlet exception");
/*  90 */         throw new ServletException(t);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/*  96 */     if (req.getProtocol().endsWith("1.1")) {
/*  97 */       resp.sendError(405, "");
/*     */     } else {
/*  99 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 104 */     if (req.getProtocol().endsWith("1.1")) {
/* 105 */       resp.sendError(405, "");
/*     */     } else {
/* 107 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 112 */     if (req.getProtocol().endsWith("1.1")) {
/* 113 */       resp.sendError(405, "");
/*     */     } else {
/* 115 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 120 */     if (req.getProtocol().endsWith("1.1")) {
/* 121 */       resp.sendError(405, "");
/*     */     } else {
/* 123 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 128 */     if (req.getProtocol().endsWith("1.1")) {
/* 129 */       resp.sendError(405, "");
/*     */     } else {
/* 131 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\gtwo\servlet\ApplicationServlet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */