/*     */ package com.eoos.html;
/*     */ 
/*     */ import com.eoos.util.ZipUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletOutputStream;
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
/*     */ public abstract class Dispatcher
/*     */   extends HttpServlet
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  35 */   private static final Logger log = Logger.getLogger(Dispatcher.class);
/*     */   
/*     */   private static final String IDS_TARGET = "target";
/*     */   
/*     */   private static final String IDS_METHOD = "target.method";
/*     */   
/*     */   private static final String BOUNDARY = "boundary=";
/*     */   
/*     */   private static final String MULTIPART = "multipart/form-data";
/*  44 */   protected static List instances = new LinkedList();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(ServletConfig config) throws ServletException {
/*  50 */     log.debug("initializing dispatcher");
/*  51 */     super.init(config);
/*  52 */     synchronized (Dispatcher.class) {
/*  53 */       if (instances.size() == 0) {
/*  54 */         log.debug("calling application startup procedure");
/*  55 */         onApplicationStartup();
/*     */       } 
/*  57 */       instances.add(this);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract void onApplicationStartup();
/*     */   
/*     */   protected abstract void onApplicationShutdown();
/*     */   
/*     */   protected static synchronized Object getTarget(String oid) {
/*  66 */     return DispatchMap.getInstance().get(oid);
/*     */   }
/*     */   
/*     */   protected abstract Object getDefaultTarget();
/*     */   
/*     */   public void destroy() {
/*  72 */     log.debug("destroying dispatcher");
/*  73 */     synchronized (Dispatcher.class) {
/*  74 */       instances.remove(this);
/*  75 */       if (instances.size() == 0) {
/*  76 */         log.debug("calling application shutdown procedure");
/*  77 */         onApplicationShutdown();
/*     */       } 
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
/*  91 */     processRequest(request, response);
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
/* 103 */     processRequest(request, response);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getServletInfo() {
/* 110 */     return "Short description";
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
/*     */   protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/*     */     try {
/* 124 */       Map<String, HttpSession> mapParameters = getParameterMap(request);
/* 125 */       if (log.isTraceEnabled()) {
/* 126 */         log.trace("...request parameters: " + mapParameters);
/*     */       }
/*     */ 
/*     */       
/* 130 */       HttpSession session = request.getSession(true);
/* 131 */       mapParameters.put("session", session);
/* 132 */       mapParameters.put("request", request);
/* 133 */       mapParameters.put("response", response);
/*     */ 
/*     */       
/* 136 */       String oid = (String)mapParameters.get(getTargetKey());
/* 137 */       String methodName = (String)mapParameters.get(getMethodKey());
/*     */       
/* 139 */       if (log.isTraceEnabled()) {
/* 140 */         log.trace("...dispatch target: " + String.valueOf(oid) + " , dispatch method: " + String.valueOf(methodName));
/*     */       }
/* 142 */       Object target = null;
/* 143 */       Method method = null;
/* 144 */       if (oid == null) {
/*     */         try {
/* 146 */           target = getDefaultTarget();
/* 147 */           method = target.getClass().getMethod("init", new Class[] { Map.class });
/* 148 */         } catch (Exception e) {
/* 149 */           log.warn("unable to retrieve default dispatch target - exception :", e);
/*     */         } 
/*     */       } else {
/*     */         try {
/* 153 */           target = DispatchMap.getInstance().get(oid);
/* 154 */           method = target.getClass().getMethod(methodName, new Class[] { Map.class });
/* 155 */         } catch (Exception e) {
/* 156 */           log.warn("unable to retrieve dispatch target/method");
/*     */         } 
/*     */       } 
/*     */       
/* 160 */       ResultObject result = null;
/* 161 */       if (target == null || method == null) {
/* 162 */         result = onInvalidDispatchTarget(mapParameters);
/*     */       } else {
/*     */         
/*     */         try {
/* 166 */           result = (ResultObject)method.invoke(target, new Object[] { mapParameters });
/* 167 */         } catch (Exception e1) {
/* 168 */           if (DispatchMap.getInstance().get(oid) == null) {
/* 169 */             result = onInvalidDispatchTarget(mapParameters);
/*     */           } else {
/* 171 */             throw e1;
/*     */           } 
/*     */         } 
/*     */       } 
/* 175 */       handleResult(request, response, result);
/* 176 */     } catch (InvocationTargetException e) {
/* 177 */       handleException(e.getTargetException(), response);
/*     */     }
/* 179 */     catch (Exception e) {
/* 180 */       handleException(e, response);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected ResultObject onInvalidDispatchTarget(Map parameters) throws Exception {
/* 185 */     Object target = getDefaultTarget();
/* 186 */     Method method = target.getClass().getMethod("init", new Class[] { Map.class });
/*     */     
/* 188 */     return (ResultObject)method.invoke(target, new Object[] { parameters });
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract void handleResult(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, ResultObject paramResultObject) throws Exception;
/*     */   
/*     */   protected Map getParameterMap(HttpServletRequest request) {
/* 195 */     Map<Object, Object> result = new HashMap<Object, Object>();
/*     */ 
/*     */ 
/*     */     
/* 199 */     Enumeration<String> enumeration = request.getHeaderNames();
/* 200 */     while (enumeration.hasMoreElements()) {
/* 201 */       String strKey = enumeration.nextElement();
/* 202 */       result.put(strKey, request.getHeader(strKey));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 207 */     HttpSession session = request.getSession(false);
/* 208 */     if (session != null) {
/* 209 */       enumeration = session.getAttributeNames();
/* 210 */       while (enumeration.hasMoreElements()) {
/* 211 */         String strKey = enumeration.nextElement();
/* 212 */         result.put(strKey, session.getAttribute(strKey));
/* 213 */         log.debug("Session: " + strKey + ":" + result.get(strKey));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 218 */     enumeration = request.getAttributeNames();
/* 219 */     while (enumeration.hasMoreElements()) {
/* 220 */       String strKey = enumeration.nextElement();
/* 221 */       result.put(strKey, request.getAttribute(strKey));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 226 */     enumeration = request.getParameterNames();
/* 227 */     while (enumeration.hasMoreElements()) {
/* 228 */       String strKey = enumeration.nextElement();
/* 229 */       String[] values = request.getParameterValues(strKey);
/* 230 */       if (values != null && values.length > 1) {
/* 231 */         result.put(strKey, request.getParameterValues(strKey));
/*     */         continue;
/*     */       } 
/* 234 */       result.put(strKey, request.getParameter(strKey));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 240 */     String cType = request.getContentType();
/* 241 */     if (cType != null && cType.indexOf("multipart/form-data") >= 0) {
/* 242 */       HttpMultiPartParser mpp = new HttpMultiPartParser();
/*     */ 
/*     */ 
/*     */       
/* 246 */       String boundary = cType.substring(cType.indexOf("boundary=") + "boundary=".length());
/* 247 */       Hashtable mpResult = null;
/*     */       try {
/* 249 */         mpResult = mpp.parseData(request.getInputStream(), boundary);
/* 250 */       } catch (IOException io) {
/* 251 */         result.clear();
/* 252 */         return result;
/*     */       } 
/*     */       
/* 255 */       enumeration = mpResult.keys();
/* 256 */       while (enumeration.hasMoreElements()) {
/* 257 */         String strKey = enumeration.nextElement();
/* 258 */         Object obj = mpResult.get(strKey);
/* 259 */         if (obj instanceof FileInfo) {
/* 260 */           result.put(strKey, mpResult.get(strKey)); continue;
/*     */         } 
/* 262 */         ArrayList<String> al = (ArrayList)obj;
/* 263 */         if (al.size() == 1) {
/* 264 */           result.put(strKey, al.get(0)); continue;
/*     */         } 
/* 266 */         String[] strs = new String[al.size()];
/* 267 */         for (int i = 0; i < al.size(); i++)
/* 268 */           strs[i] = al.get(i); 
/* 269 */         result.put(strKey, strs);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 274 */     return result;
/*     */   }
/*     */   
/*     */   public static void registerTarget(Dispatchable dispatchable) {
/* 278 */     DispatchMap.getInstance().register(dispatchable);
/*     */   }
/*     */   
/*     */   public static void unregisterTarget(Dispatchable dispatchable) {
/* 282 */     DispatchMap.getInstance().unregister(dispatchable);
/*     */   }
/*     */   
/*     */   public String getTargetKey() {
/* 286 */     return "target";
/*     */   }
/*     */   
/*     */   public String getMethodKey() {
/* 290 */     return "target.method";
/*     */   }
/*     */   
/*     */   protected void handleException(Throwable e, HttpServletResponse response) throws ServletException {
/* 294 */     log.error("caught exception  :" + e, e);
/*     */     try {
/* 296 */       response.sendError(500);
/* 297 */       log.info("sent internal server error code to client");
/* 298 */     } catch (Exception ee) {
/* 299 */       log.error("could not handle exception - throwing ServletException");
/* 300 */       throw new ServletException(ee);
/*     */     } 
/*     */   }
/*     */   
/*     */   public InputStream getResourceAsStream(String name) {
/* 305 */     if (name.indexOf("/") != 0) {
/* 306 */       name = "/" + name;
/*     */     }
/* 308 */     return getServletContext().getResourceAsStream(name);
/*     */   }
/*     */   
/*     */   protected void handleText(HttpServletRequest request, HttpServletResponse response, String data, String mime) {
/*     */     try {
/* 313 */       handleBinary(request, response, data.getBytes("utf-8"), (mime != null) ? mime : "text/html;charset=utf-8", true);
/* 314 */     } catch (UnsupportedEncodingException e) {
/* 315 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void handleBinary(HttpServletRequest request, HttpServletResponse response, byte[] data, String mime, boolean compress) {
/* 320 */     ServletOutputStream out = null;
/*     */     try {
/* 322 */       response.setContentType(mime);
/* 323 */       if (compress) {
/* 324 */         String encoding = request.getHeader("Accept-Encoding");
/* 325 */         if (encoding != null && encoding.indexOf("gzip") != -1) {
/* 326 */           data = ZipUtil.gzip(data);
/* 327 */           response.setHeader("Content-Encoding", "gzip");
/*     */         } 
/*     */       } 
/* 330 */       response.setContentLength(data.length);
/* 331 */       out = response.getOutputStream();
/* 332 */       out.write(data);
/* 333 */     } catch (Exception e) {
/* 334 */       log.error("::handleBinary - error :" + e, e);
/*     */     } finally {
/*     */       try {
/* 337 */         out.close();
/* 338 */       } catch (Exception ee) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 345 */     if (req.getProtocol().endsWith("1.1")) {
/* 346 */       resp.sendError(405, "");
/*     */     } else {
/* 348 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 353 */     if (req.getProtocol().endsWith("1.1")) {
/* 354 */       resp.sendError(405, "");
/*     */     } else {
/* 356 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 361 */     if (req.getProtocol().endsWith("1.1")) {
/* 362 */       resp.sendError(405, "");
/*     */     } else {
/* 364 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 369 */     if (req.getProtocol().endsWith("1.1")) {
/* 370 */       resp.sendError(405, "");
/*     */     } else {
/* 372 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 377 */     if (req.getProtocol().endsWith("1.1")) {
/* 378 */       resp.sendError(405, "");
/*     */     } else {
/* 380 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\Dispatcher.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */