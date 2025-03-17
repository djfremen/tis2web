/*     */ package com.eoos.gm.tis2web.frame.export.common;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.ConfigurationServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.Tis2webUtil;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.util.ConfigurationUtil;
/*     */ import com.eoos.scsm.v2.cache.Cache;
/*     */ import com.eoos.util.ZipUtil;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FileDownloadServlet
/*     */   extends HttpServlet
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  31 */   private static final Logger log = Logger.getLogger(FileDownloadServlet.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  40 */   private Cache cache = Tis2webUtil.createStdCache();
/*     */ 
/*     */   
/*     */   protected InputStream getResourceAsStream(String name) {
/*  44 */     if (name.indexOf("/") != 0) {
/*  45 */       name = "/" + name;
/*     */     }
/*  47 */     return getServletContext().getResourceAsStream(name);
/*     */   }
/*     */   
/*     */   public byte[] loadResource(String name) {
/*  51 */     byte[] retValue = (byte[])this.cache.lookup(name);
/*  52 */     if (retValue == null) {
/*  53 */       log.debug("loading resource:" + name);
/*     */       try {
/*  55 */         ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*  56 */         InputStream is = getResourceAsStream(name);
/*  57 */         byte[] buffer = new byte[Math.min(1024, is.available())];
/*  58 */         int count = 0;
/*  59 */         while ((count = is.read(buffer)) != -1) {
/*  60 */           baos.write(buffer, 0, count);
/*     */         }
/*  62 */         is.close();
/*  63 */         baos.close();
/*  64 */         retValue = baos.toByteArray();
/*  65 */         log.debug("adding resource:" + name + " to cache");
/*  66 */         this.cache.store(name, retValue);
/*  67 */       } catch (Exception e) {
/*  68 */         log.debug("unable to load resource:" + name + " - error:" + e, e);
/*     */       } 
/*     */     } else {
/*  71 */       log.debug("resource:" + name + " found in cache");
/*     */     } 
/*  73 */     return retValue;
/*     */   }
/*     */   
/*  76 */   private Boolean enabled = null;
/*  77 */   private long lastCheck = 0L;
/*     */   
/*     */   private boolean enabled() {
/*     */     try {
/*  81 */       if (this.enabled == null || System.currentTimeMillis() - this.lastCheck > 10000L) {
/*  82 */         this.lastCheck = System.currentTimeMillis();
/*  83 */         this.enabled = ConfigurationUtil.getBoolean("frame.file.download.servlet.enable", (Configuration)ConfigurationServiceProvider.getService());
/*  84 */         if (this.enabled == null) {
/*  85 */           this.enabled = Boolean.FALSE;
/*     */         }
/*     */       } 
/*  88 */       return this.enabled.booleanValue();
/*     */     }
/*  90 */     catch (Exception e) {
/*  91 */       this.enabled = Boolean.FALSE;
/*  92 */       return this.enabled.booleanValue();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/*  97 */     log.debug("received request");
/*     */     try {
/*  99 */       if (!enabled()) {
/* 100 */         throw new IllegalStateException("not enabled");
/*     */       }
/* 102 */       String pathInfo = request.getPathInfo();
/* 103 */       log.debug("trying to load resource:" + pathInfo);
/* 104 */       byte[] result = loadResource(pathInfo);
/* 105 */       log.debug("loaded: " + ((result != null) ? String.valueOf(result.length) : "0") + " bytes");
/* 106 */       if (result == null) {
/* 107 */         log.debug("returning status :no content");
/* 108 */         response.setStatus(204);
/*     */       } else {
/* 110 */         log.debug("writing to output stream");
/* 111 */         ServletOutputStream out = null;
/* 112 */         response.setContentType("application/octet-stream");
/*     */         
/* 114 */         String encoding = request.getHeader("Accept-Encoding");
/* 115 */         if (encoding != null && encoding.indexOf("gzip") != -1) {
/* 116 */           log.debug("zipping response");
/* 117 */           result = ZipUtil.gzip(result);
/* 118 */           response.setHeader("Content-Encoding", "gzip");
/*     */         } 
/*     */         
/* 121 */         response.setContentLength(result.length);
/* 122 */         out = response.getOutputStream();
/* 123 */         out.write(result);
/* 124 */         out.close();
/* 125 */         log.debug("wrote response");
/*     */       }
/*     */     
/* 128 */     } catch (Exception e) {
/* 129 */       log.error("unable to provide resource:" + request.getPathInfo() + " - error:" + e, e);
/* 130 */       throw new ServletException(e);
/*     */     } 
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
/*     */   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/* 143 */     processRequest(request, response);
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
/* 155 */     processRequest(request, response);
/*     */   }
/*     */   
/*     */   protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 159 */     if (req.getProtocol().endsWith("1.1")) {
/* 160 */       resp.sendError(405, "");
/*     */     } else {
/* 162 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 167 */     if (req.getProtocol().endsWith("1.1")) {
/* 168 */       resp.sendError(405, "");
/*     */     } else {
/* 170 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 175 */     if (req.getProtocol().endsWith("1.1")) {
/* 176 */       resp.sendError(405, "");
/*     */     } else {
/* 178 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 183 */     if (req.getProtocol().endsWith("1.1")) {
/* 184 */       resp.sendError(405, "");
/*     */     } else {
/* 186 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 191 */     if (req.getProtocol().endsWith("1.1")) {
/* 192 */       resp.sendError(405, "");
/*     */     } else {
/* 194 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\FileDownloadServlet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */