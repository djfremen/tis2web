/*     */ package com.eoos.gm.tis2web.frame.servlet;
/*     */ 
/*     */ import com.eoos.resource.loading.ServletResourceLoading;
/*     */ import com.eoos.scsm.v2.cache.StreamCache;
/*     */ import com.eoos.scsm.v2.cache.StreamCache_Memory;
/*     */ import com.eoos.scsm.v2.io.MultiplexingOutputStream;
/*     */ import com.eoos.scsm.v2.io.StreamUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletOutputStream;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ResourceServlet
/*     */   extends HttpServlet
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  29 */   private static final Logger log = Logger.getLogger(ResourceServlet.class);
/*     */   
/*  31 */   private long expirationOffset = 86400000L;
/*  32 */   private Pattern acceptPattern = null;
/*  33 */   private StreamCache cache = (StreamCache)new StreamCache_Memory();
/*  34 */   private ServletResourceLoading srL = new ServletResourceLoading(this);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(ServletConfig config) throws ServletException {
/*  41 */     super.init(config);
/*  42 */     log.debug("initialising " + this + "...");
/*  43 */     String pattern = config.getInitParameter("accept.pattern");
/*  44 */     log.debug("...accept pattern: " + String.valueOf(pattern));
/*  45 */     this.acceptPattern = Pattern.compile(pattern);
/*     */     try {
/*  47 */       this.expirationOffset = Util.resolveDuration(Util.parseDuration(config.getInitParameter("expiration.offset")));
/*  48 */     } catch (Exception e) {
/*  49 */       log.warn("...unable to parse expiration parameter, using default");
/*     */     } 
/*  51 */     log.debug("...expiration offset: " + Util.formatDuration(this.expirationOffset, "${days} day(s), ${hours} hour(s), ${minutes} minutes"));
/*  52 */     log.debug("...done");
/*     */   }
/*     */   
/*     */   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/*  56 */     processRequest(request, response);
/*     */   }
/*     */   
/*     */   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/*  60 */     processRequest(request, response);
/*     */   }
/*     */   
/*     */   protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/*     */     try {
/*  65 */       if (this.acceptPattern != null) {
/*  66 */         String resource = request.getPathInfo();
/*  67 */         if (!Util.isNullOrEmpty(resource) && this.acceptPattern.matcher(resource).matches()) {
/*  68 */           log.debug("serving request for " + resource);
/*     */           
/*  70 */           ServletOutputStream servletOutputStream = response.getOutputStream();
/*     */           
/*  72 */           InputStream is = this.cache.lookup(resource);
/*     */           try {
/*  74 */             if (is != null) {
/*  75 */               response.setHeader("Cache-Control", "public");
/*  76 */               response.setDateHeader("Expires", System.currentTimeMillis() + this.expirationOffset);
/*  77 */               StreamUtil.transfer(is, (OutputStream)servletOutputStream);
/*     */             } else {
/*  79 */               is = this.srL.getResource(resource);
/*  80 */               if (is != null) {
/*  81 */                 OutputStream cos = this.cache.getStorageStream(resource);
/*     */                 try {
/*  83 */                   MultiplexingOutputStream multiplexingOutputStream = new MultiplexingOutputStream(new OutputStream[] { (OutputStream)servletOutputStream, cos });
/*     */                   try {
/*  85 */                     response.setHeader("Cache-Control", "public");
/*  86 */                     response.setDateHeader("Expires", System.currentTimeMillis() + this.expirationOffset);
/*  87 */                     StreamUtil.transfer(is, (OutputStream)multiplexingOutputStream);
/*     */                   } finally {
/*  89 */                     multiplexingOutputStream.flush();
/*     */                   } 
/*     */                 } finally {
/*  92 */                   StreamUtil.close(cos, log);
/*     */                 } 
/*     */               } else {
/*  95 */                 log.warn("resource '" + resource + "' not found, returning status code 404");
/*  96 */                 response.setStatus(404);
/*     */               }
/*     */             
/*     */             } 
/*     */           } finally {
/*     */             
/* 102 */             if (is != null) {
/* 103 */               StreamUtil.close(is, log);
/*     */             }
/*     */           } 
/*     */         } else {
/* 107 */           log.debug("ignoring request since resource '" + resource + "' does not match accept pattern: '" + this.acceptPattern + "'");
/*     */         } 
/*     */       } 
/* 110 */     } catch (Throwable t) {
/* 111 */       log.error("unable to server request, rethrowing - exception: " + t, t);
/* 112 */       rethrow(t);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void rethrow(Throwable t) throws ServletException, IOException {
/* 118 */     if (t instanceof ServletException)
/* 119 */       throw (ServletException)t; 
/* 120 */     if (t instanceof IOException)
/* 121 */       throw (IOException)t; 
/* 122 */     if (t instanceof Error) {
/* 123 */       throw (Error)t;
/*     */     }
/* 125 */     throw Util.toRuntimeException((Exception)t);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 130 */     if (req.getProtocol().endsWith("1.1")) {
/* 131 */       resp.sendError(405, "");
/*     */     } else {
/* 133 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 138 */     if (req.getProtocol().endsWith("1.1")) {
/* 139 */       resp.sendError(405, "");
/*     */     } else {
/* 141 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 146 */     if (req.getProtocol().endsWith("1.1")) {
/* 147 */       resp.sendError(405, "");
/*     */     } else {
/* 149 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 154 */     if (req.getProtocol().endsWith("1.1")) {
/* 155 */       resp.sendError(405, "");
/*     */     } else {
/* 157 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 162 */     if (req.getProtocol().endsWith("1.1")) {
/* 163 */       resp.sendError(405, "");
/*     */     } else {
/* 165 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\servlet\ResourceServlet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */