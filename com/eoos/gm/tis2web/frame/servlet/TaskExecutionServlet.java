/*     */ package com.eoos.gm.tis2web.frame.servlet;
/*     */ 
/*     */ import com.eoos.gm.tis2web.common.StreamingTask;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.TypeDecorator;
/*     */ import com.eoos.scsm.v2.io.StreamUtil;
/*     */ import com.eoos.util.Task;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.URL;
/*     */ import java.util.Iterator;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import java.util.zip.GZIPOutputStream;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TaskExecutionServlet
/*     */   extends HttpServlet
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  33 */   private static final Logger log = Logger.getLogger(TaskExecutionServlet.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/*  49 */     processRequest(request, response);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/*  61 */     processRequest(request, response);
/*     */   }
/*     */   
/*     */   protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/*  65 */     log.debug("processing task execution request...");
/*     */     try {
/*  67 */       request.getSession(true);
/*     */       
/*  69 */       Task task = null;
/*  70 */       ObjectInputStream ois = null;
/*     */       try {
/*  72 */         ois = new ObjectInputStream(new GZIPInputStream((InputStream)request.getInputStream()));
/*  73 */       } catch (EOFException eof) {
/*  74 */         StreamUtil.close(ois, log);
/*  75 */         log.error("EOF " + request.getPathInfo());
/*  76 */         throw eof;
/*     */       } 
/*     */       try {
/*  79 */         task = (Task)ois.readObject();
/*  80 */         log.debug("... deserizalized task " + String.valueOf(task));
/*     */       } finally {
/*  82 */         ois.close();
/*     */       } 
/*     */       
/*  85 */       if (!allowExecution(task, request)) {
/*  86 */         log.debug("...execution prohibited, throwing exception");
/*  87 */         throw new IllegalArgumentException();
/*     */       } 
/*     */       
/*  90 */       if (task instanceof Task.InjectHttpRequest) {
/*  91 */         ((Task.InjectHttpRequest)task).setHttpServletRequest(request);
/*     */       }
/*  93 */       if (task instanceof Task.InjectHttpResponse) {
/*  94 */         ((Task.InjectHttpResponse)task).setHttpServletResponse(response);
/*     */       }
/*     */       
/*  97 */       if (task instanceof StreamingTask) {
/*  98 */         ((StreamingTask)task).execute((OutputStream)response.getOutputStream());
/*     */       } else {
/*     */         
/* 101 */         ObjectOutputStream oos = new ObjectOutputStream(new GZIPOutputStream((OutputStream)response.getOutputStream(), 50000));
/*     */         try {
/* 103 */           Object result = task.execute();
/* 104 */           log.debug("...executed task ");
/* 105 */           oos.writeObject(result);
/* 106 */           log.debug("...serialized result");
/*     */         } finally {
/* 108 */           oos.close();
/*     */         } 
/*     */       } 
/* 111 */       log.debug("...done processing task execution request");
/*     */     }
/* 113 */     catch (Throwable e) {
/* 114 */       log.error("unable to handle request - exception:" + e, e);
/* 115 */       throw new ServletException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean allowExecution(Task task, HttpServletRequest request) {
/* 121 */     if (!TypeDecorator.getBoolean("frame.task.execution.check.enabled", (Configuration)ApplicationContext.getInstance(), true)) {
/* 122 */       return true;
/*     */     }
/*     */     
/* 125 */     log.debug("...checking execution permission for task...");
/* 126 */     boolean ret = true;
/* 127 */     if (task instanceof Task.LocalExecution) {
/* 128 */       log.debug("...task is marked for local execution only");
/* 129 */       ret = false;
/* 130 */     } else if (task instanceof Task.ClusterExecution) {
/* 131 */       log.debug("...task is marked for cluster execution only");
/* 132 */       ret = false;
/* 133 */       String remoteHost = request.getRemoteHost();
/* 134 */       String remoteAddr = request.getRemoteAddr();
/* 135 */       log.debug("...sending host: name <" + String.valueOf(remoteHost) + ">, ip <" + String.valueOf(remoteAddr) + ">");
/*     */       
/* 137 */       ret = (remoteHost.equalsIgnoreCase("localhost") || remoteAddr.equals("127.0.0.1"));
/* 138 */       for (Iterator<URL> iter = ApplicationContext.getInstance().getClusterURLs().iterator(); iter.hasNext() && !ret; ) {
/* 139 */         URL url = iter.next();
/*     */         
/* 141 */         ret = (remoteAddr != null && remoteAddr.equalsIgnoreCase(url.getHost()));
/* 142 */         ret = (ret || (remoteHost != null && remoteHost.equalsIgnoreCase(url.getHost())));
/* 143 */         if (ret) {
/* 144 */           log.debug("...request url identified as cluster url");
/*     */         }
/*     */       } 
/*     */     } 
/* 148 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 153 */     if (req.getProtocol().endsWith("1.1")) {
/* 154 */       resp.sendError(405, "");
/*     */     } else {
/* 156 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 161 */     if (req.getProtocol().endsWith("1.1")) {
/* 162 */       resp.sendError(405, "");
/*     */     } else {
/* 164 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 169 */     if (req.getProtocol().endsWith("1.1")) {
/* 170 */       resp.sendError(405, "");
/*     */     } else {
/* 172 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 177 */     if (req.getProtocol().endsWith("1.1")) {
/* 178 */       resp.sendError(405, "");
/*     */     } else {
/* 180 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 185 */     if (req.getProtocol().endsWith("1.1")) {
/* 186 */       resp.sendError(405, "");
/*     */     } else {
/* 188 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\servlet\TaskExecutionServlet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */