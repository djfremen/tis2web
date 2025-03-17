/*     */ package com.eoos.gm.tis2web.frame.export.common;
/*     */ 
/*     */ import com.eoos.resource.loading.ResourceLoading;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Collection;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletOutputStream;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ResourceDispatcher
/*     */   extends HttpServlet
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  25 */   protected ResourceLoading resourceLoading = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(ServletConfig config) throws ServletException {
/*  31 */     super.init(config);
/*  32 */     this.resourceLoading = new ResourceLoading() {
/*     */         public Object loadResource(Object identifier) {
/*  34 */           byte[] retValue = null;
/*     */           try {
/*  36 */             ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*  37 */             InputStream is = ResourceDispatcher.this.getResourceAsStream((String)identifier);
/*  38 */             byte[] buffer = new byte[1024];
/*  39 */             int count = 0;
/*  40 */             while ((count = is.read(buffer)) != -1) {
/*  41 */               baos.write(buffer, 0, count);
/*     */             }
/*  43 */             is.close();
/*  44 */             baos.close();
/*  45 */             retValue = baos.toByteArray();
/*  46 */           } catch (Exception e) {}
/*     */           
/*  48 */           return retValue;
/*     */         }
/*     */         
/*     */         public InputStream getResource(Object identifier) throws IOException {
/*  52 */           return ResourceDispatcher.this.getResourceAsStream((String)identifier);
/*     */         }
/*     */         
/*     */         public boolean existsResource(Object identifier) {
/*  56 */           boolean retValue = false;
/*     */           try {
/*  58 */             retValue = (getResource(identifier) != null);
/*  59 */           } catch (IOException e) {
/*  60 */             retValue = false;
/*     */           } 
/*  62 */           return retValue;
/*     */         }
/*     */         
/*     */         public Collection searchResources(Object searchPattern) {
/*  66 */           return null;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public void destroy() {
/*  73 */     super.destroy();
/*     */   }
/*     */   
/*     */   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/*  77 */     processRequest(request, response);
/*     */   }
/*     */   
/*     */   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/*  81 */     processRequest(request, response);
/*     */   }
/*     */   
/*     */   public String getServletInfo() {
/*  85 */     return "No description";
/*     */   }
/*     */   
/*     */   protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/*  89 */     HttpSession session = request.getSession(true);
/*  90 */     synchronized (session) {
/*     */       try {
/*  92 */         String resourceName = translateToResourceName(request);
/*  93 */         byte[] data = (byte[])this.resourceLoading.loadResource(resourceName);
/*  94 */         ServletOutputStream sos = response.getOutputStream();
/*  95 */         sos.write(data);
/*  96 */       } catch (Exception e) {
/*  97 */         throw new ServletException(e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected InputStream getResourceAsStream(String name) {
/* 103 */     if (name.indexOf("/") != 0) {
/* 104 */       name = "/" + name;
/*     */     }
/* 106 */     return getServletContext().getResourceAsStream(name);
/*     */   }
/*     */   
/*     */   protected String translateToResourceName(HttpServletRequest request) {
/* 110 */     return request.getPathInfo();
/*     */   }
/*     */   
/*     */   protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 114 */     if (req.getProtocol().endsWith("1.1")) {
/* 115 */       resp.sendError(405, "");
/*     */     } else {
/* 117 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 122 */     if (req.getProtocol().endsWith("1.1")) {
/* 123 */       resp.sendError(405, "");
/*     */     } else {
/* 125 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 130 */     if (req.getProtocol().endsWith("1.1")) {
/* 131 */       resp.sendError(405, "");
/*     */     } else {
/* 133 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 138 */     if (req.getProtocol().endsWith("1.1")) {
/* 139 */       resp.sendError(405, "");
/*     */     } else {
/* 141 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 146 */     if (req.getProtocol().endsWith("1.1")) {
/* 147 */       resp.sendError(405, "");
/*     */     } else {
/* 149 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\ResourceDispatcher.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */