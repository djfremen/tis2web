/*     */ package com.eoos.scsm.v2.jnlp;
/*     */ 
/*     */ import com.eoos.io.StreamUtil;
/*     */ import com.eoos.scsm.v2.io.resource.ResourceProvider_ServletRI;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.v2.StopWatch;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URLDecoder;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JNLPServlet
/*     */   extends HttpServlet
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  27 */   private static final Logger log = Logger.getLogger(JNLPServlet.class);
/*     */   
/*  29 */   private Map pathToManager = Collections.synchronizedMap(new HashMap<Object, Object>());
/*     */   
/*     */   private JNLPResourceManager getManager(String path) throws IOException {
/*  32 */     synchronized (this.pathToManager) {
/*  33 */       JNLPResourceManager instance = (JNLPResourceManager)this.pathToManager.get(path);
/*  34 */       if (instance == null) {
/*  35 */         new ResourceProvider_ServletRI(getServletContext(), path);
/*     */       }
/*  37 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/*  42 */     processRequest(req, resp);
/*     */   }
/*     */   
/*     */   private String getRequestParameter(HttpServletRequest req, String key) {
/*  46 */     String ret = req.getParameter(key);
/*  47 */     if (!Util.isNullOrEmpty(ret)) {
/*     */       try {
/*  49 */         ret = URLDecoder.decode(ret, "utf-8");
/*  50 */       } catch (UnsupportedEncodingException e) {
/*  51 */         throw new RuntimeException(e);
/*     */       } 
/*  53 */       log.debug("...." + key + ": " + ret);
/*  54 */       return ret;
/*     */     } 
/*  56 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/*  61 */     log.debug("processing request...");
/*  62 */     StopWatch sw = StopWatch.getInstance().start();
/*     */     try {
/*     */       try {
/*     */         try {
/*  66 */           String path = req.getServletPath();
/*  67 */           log.debug("...retrieving resource manager for servlet path: " + path);
/*  68 */           JNLPResourceManager manager = getManager(path);
/*     */           
/*  70 */           String resourceName = req.getPathInfo();
/*  71 */           if (resourceName != null && resourceName.startsWith("/")) {
/*  72 */             resourceName = resourceName.substring(1);
/*     */           }
/*  74 */           if (Util.isNullOrEmpty(resourceName)) {
/*  75 */             throw new ResourceNotFoundException("no resource specified");
/*     */           }
/*  77 */           log.debug("...requested resource: " + String.valueOf(resourceName));
/*     */           
/*  79 */           String requestedVersionString = getRequestParameter(req, "version-id");
/*  80 */           String currentVersion = getRequestParameter(req, "current-version-id");
/*  81 */           String arch = getRequestParameter(req, "arch");
/*  82 */           String os = getRequestParameter(req, "os");
/*  83 */           String locale = getRequestParameter(req, "locale");
/*  84 */           String platformVersionString = getRequestParameter(req, "platform-version-id");
/*  85 */           String knownPlatformsString = getRequestParameter(req, "known-platforms");
/*     */           
/*  87 */           Object resource = manager.getResource(resourceName, requestedVersionString, currentVersion, arch, os, locale, platformVersionString, knownPlatformsString);
/*  88 */           resp.setContentType(manager.getMIME(resource).toString());
/*  89 */           resp.setHeader("x-java-jnlp-version-id", manager.getVersionNumber(resource).toString());
/*  90 */           InputStream is = manager.getInputStream(resource);
/*  91 */           StreamUtil.transfer(is, (OutputStream)resp.getOutputStream());
/*     */         }
/*  93 */         catch (ResourceNotFoundException e) {
/*  94 */           throw e;
/*  95 */         } catch (VersionNotFoundException e) {
/*  96 */           throw e;
/*  97 */         } catch (Exception e) {
/*  98 */           log.error("unable to process request, rethrowing as JNLPException UNKNOWN - exception: " + e, e);
/*  99 */           throw new JNLPException(99, "");
/*     */         } 
/* 101 */       } catch (JNLPException e) {
/* 102 */         resp.setContentType("application/x-java-jnlp-error");
/* 103 */         resp.getWriter().println(e.getCode() + " " + String.valueOf(e.getMessage()));
/* 104 */         resp.flushBuffer();
/*     */       } finally {
/* 106 */         log.debug("served request in : " + sw.getElapsedTime() + " ms");
/*     */       } 
/*     */     } finally {
/* 109 */       StopWatch.freeInstance(sw);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 115 */     if (req.getProtocol().endsWith("1.1")) {
/* 116 */       resp.sendError(405, "");
/*     */     } else {
/* 118 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 123 */     if (req.getProtocol().endsWith("1.1")) {
/* 124 */       resp.sendError(405, "");
/*     */     } else {
/* 126 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 131 */     if (req.getProtocol().endsWith("1.1")) {
/* 132 */       resp.sendError(405, "");
/*     */     } else {
/* 134 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 139 */     if (req.getProtocol().endsWith("1.1")) {
/* 140 */       resp.sendError(405, "");
/*     */     } else {
/* 142 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 147 */     if (req.getProtocol().endsWith("1.1")) {
/* 148 */       resp.sendError(405, "");
/*     */     } else {
/* 150 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\jnlp\JNLPServlet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */