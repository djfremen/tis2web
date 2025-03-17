/*     */ package com.eoos.gm.tis2web.si;
/*     */ 
/*     */ import com.eoos.scsm.v2.util.HashCalc;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.io.IOException;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.servlet.ServletConfig;
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
/*     */ 
/*     */ 
/*     */ public class ResourceServlet
/*     */   extends HttpServlet
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  25 */   private static final Logger log = Logger.getLogger(ResourceServlet.class);
/*  26 */   private long expirationOffset = 86400000L;
/*     */ 
/*     */   
/*     */   private final Pattern patternPath;
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(ServletConfig config) throws ServletException {
/*  34 */     super.init(config);
/*  35 */     log.debug("initialising " + this + "...");
/*     */     try {
/*  37 */       this.expirationOffset = Util.resolveDuration(Util.parseDuration(config.getInitParameter("expiration.offset")));
/*  38 */     } catch (Exception e) {
/*  39 */       log.warn("...unable to parse expiration parameter, using default");
/*     */     } 
/*  41 */     log.debug("...expiration offset: " + Util.formatDuration(this.expirationOffset, "${days} day(s), ${hours} hour(s), ${minutes} minutes"));
/*     */   }
/*     */   
/*     */   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/*  45 */     processRequest(request, response);
/*     */   }
/*     */   
/*     */   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceServlet() {
/*  64 */     this.patternPath = Pattern.compile("/([ig])/(\\d*)/(\\d*)", 2);
/*     */   }
/*     */   public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/*     */     try {
/*  68 */       response.setHeader("Cache-Control", "public");
/*  69 */       response.setDateHeader("Expires", System.currentTimeMillis() + this.expirationOffset);
/*  70 */       String resource = request.getPathInfo();
/*  71 */       if (!Util.isNullOrEmpty(resource)) {
/*     */         
/*  73 */         Matcher matcher = this.patternPath.matcher(resource);
/*  74 */         if (matcher.matches()) {
/*  75 */           int id = Integer.parseInt(matcher.group(3));
/*  76 */           int secid = Integer.parseInt(matcher.group(2));
/*  77 */           if (!validate(id, secid)) {
/*  78 */             throw new SecurityException();
/*     */           }
/*     */           
/*  81 */           if (matcher.group(1).equals("i")) {
/*  82 */             log.debug("retrieving image " + id);
/*  83 */             ResourceServletDelegate.getInstance().processData(ResourceServletDelegate.Type.IMAGE, id, response);
/*  84 */           } else if (matcher.group(1).equals("g")) {
/*  85 */             log.debug("retrievaing graphic " + id);
/*  86 */             ResourceServletDelegate.getInstance().processData(ResourceServletDelegate.Type.GRAPHIC, id, response);
/*     */           } else {
/*  88 */             throw new IllegalArgumentException();
/*     */           }
/*     */         
/*     */         } 
/*     */       } 
/*  93 */     } catch (Throwable t) {
/*  94 */       rethrow(t);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void rethrow(Throwable t) throws ServletException, IOException {
/*  99 */     if (t instanceof ServletException)
/* 100 */       throw (ServletException)t; 
/* 101 */     if (t instanceof IOException)
/* 102 */       throw (IOException)t; 
/* 103 */     if (t instanceof Error) {
/* 104 */       throw (Error)t;
/*     */     }
/* 106 */     throw Util.toRuntimeException((Exception)t);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getURLSuffix(String id, Integer sio) {
/*     */     try {
/* 112 */       return getURLSuffix(Integer.parseInt(id));
/* 113 */     } catch (NumberFormatException e) {
/* 114 */       log.error("unable to create suffix for sioId '" + sio + "', returning request string - exception:", e);
/* 115 */       return id;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String getURLSuffix(int id) {
/* 120 */     return getSecurityID(id) + "/" + id;
/*     */   }
/*     */   
/*     */   private static int getSecurityID(int id) {
/* 124 */     int ret = ResourceServlet.class.hashCode();
/* 125 */     while (id > 0) {
/* 126 */       ret = HashCalc.addHashCode(ret, ret - id);
/* 127 */       id /= 10;
/*     */     } 
/*     */     
/* 130 */     return Math.abs(ret);
/*     */   }
/*     */   
/*     */   public static boolean validate(int id, int securityID) {
/* 134 */     return (getSecurityID(id) == securityID);
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 138 */     for (int i = 4000; i < 160987; i++) {
/* 139 */       System.out.println(getSecurityID(i));
/*     */     }
/*     */   }
/*     */   
/*     */   protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 144 */     if (req.getProtocol().endsWith("1.1")) {
/* 145 */       resp.sendError(405, "");
/*     */     } else {
/* 147 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 152 */     if (req.getProtocol().endsWith("1.1")) {
/* 153 */       resp.sendError(405, "");
/*     */     } else {
/* 155 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 160 */     if (req.getProtocol().endsWith("1.1")) {
/* 161 */       resp.sendError(405, "");
/*     */     } else {
/* 163 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 168 */     if (req.getProtocol().endsWith("1.1")) {
/* 169 */       resp.sendError(405, "");
/*     */     } else {
/* 171 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 176 */     if (req.getProtocol().endsWith("1.1")) {
/* 177 */       resp.sendError(405, "");
/*     */     } else {
/* 179 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\ResourceServlet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */