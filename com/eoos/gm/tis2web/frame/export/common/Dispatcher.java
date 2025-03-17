/*     */ package com.eoos.gm.tis2web.frame.export.common;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.gm.tis2web.frame.DefaultController;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.ApplicationConnector;
/*     */ import com.eoos.html.Dispatcher;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.base.ApplicationContext;
/*     */ import com.eoos.log.AppenderProxy;
/*     */ import com.eoos.log.StartupConsoleAppender;
/*     */ import com.eoos.util.PeriodicTask;
/*     */ import com.eoos.util.v2.StopWatch;
/*     */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.servlet.RequestDispatcher;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.ServletResponse;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.log4j.Appender;
/*     */ import org.apache.log4j.Layout;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.PatternLayout;
/*     */ import org.apache.log4j.Priority;
/*     */ 
/*     */ @SuppressWarnings({"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"})
/*     */ public class Dispatcher
/*     */   extends Dispatcher
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  45 */   private static Collection<AppenderProxy> appenderProxies = new LinkedList<AppenderProxy>();
/*     */   static {
/*  47 */     List<String> appenderNames = new LinkedList();
/*  48 */     for (int i = 0; i < 5; i++) {
/*  49 */       appenderNames.add("APPENDER_" + i);
/*     */     }
/*     */     
/*  52 */     AppenderProxy proxy = new AppenderProxy(appenderNames, 300000L);
/*  53 */     proxy.setThreshold((Priority)Level.DEBUG);
/*  54 */     Logger.getRootLogger().addAppender((Appender)proxy);
/*  55 */     appenderProxies.add(proxy);
/*     */     
/*  57 */     StartupConsoleAppender startupConsoleAppender = new StartupConsoleAppender((Layout)new PatternLayout("[%c{1}] %m%n"));
/*  58 */     startupConsoleAppender.setThreshold((Priority)Level.INFO);
/*  59 */     Logger.getRootLogger().addAppender((Appender)startupConsoleAppender);
/*     */   }
/*     */ 
/*     */   
/*  63 */   private static final Logger log = Logger.getLogger(Dispatcher.class);
/*  64 */   private static final Logger logPerf = Logger.getLogger("performance");
/*     */   
/*  66 */   private static ApplicationConnector applicationConnector = null;
/*     */ 
/*     */   
/*  69 */   private static long r = 0L;
/*     */ 
/*     */   
/*  72 */   private static double average = 0.0D;
/*     */   
/*  74 */   private static Set pending = Collections.synchronizedSet(new HashSet());
/*     */   
/*  76 */   private static PeriodicTask pt = null;
/*     */ 
/*     */   
/*     */   private static final long ONE_DAY_IN_MILLIS = 86400000L;
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() {
/*  84 */     super.destroy();
/*  85 */     synchronized (Dispatcher.class) {
/*  86 */       if (appenderProxies != null) {
/*  87 */         for (Iterator<AppenderProxy> iter = appenderProxies.iterator(); iter.hasNext();) {
/*  88 */           ((AppenderProxy)iter.next()).dispose();
/*     */         }
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected ApplicationConnector getApplicationConnector() {
/*  97 */     synchronized (Dispatcher.class) {
/*  98 */       if (applicationConnector == null) {
/*     */         try {
/* 100 */           String className = getServletConfig().getInitParameter("application.connector.class");
/* 101 */           Class<?> c = Class.forName(className);
/* 102 */           applicationConnector = (ApplicationConnector)c.newInstance();
/* 103 */         } catch (Exception e) {
/* 104 */           log.error("cannot init application connector - error:" + e, e);
/* 105 */           throw new RuntimeException();
/*     */         } 
/*     */       }
/* 108 */       return applicationConnector;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected Object getDefaultTarget() {
/* 113 */     return getApplicationConnector().getDefaultTarget(); } protected void handleResult(HttpServletRequest request, HttpServletResponse response, ResultObject result) throws Exception {
/*     */     String code;
/*     */     ResultObject.FileProperties properties;
/*     */     String path;
/*     */     byte[] data;
/*     */     RequestDispatcher dispatcher;
/* 119 */     if (result == null) {
/*     */       return;
/*     */     }
/* 122 */     if (!result.isCachable()) {
/* 123 */       response.setHeader("Pragma", "no-cache");
/* 124 */       response.setHeader("Cache-Control", "no-cache");
/* 125 */       response.setDateHeader("Expires", 0L);
/*     */     } else {
/* 127 */       response.setHeader("Cache-Control", "public");
/* 128 */       response.setDateHeader("Expires", System.currentTimeMillis() + 86400000L);
/*     */     } 
/*     */     
/* 131 */     if (result.hasStatusCode()) {
/* 132 */       response.setStatus(result.getStatusCode());
/*     */     }
/*     */     
/* 135 */     switch (result.code) {
/*     */       case 9:
/* 137 */         response.setStatus(204);
/*     */         return;
/*     */       
/*     */       case 0:
/* 141 */         code = (String)result.object;
/*     */         
/* 143 */         data = code.getBytes("utf-8");
/* 144 */         handleBinary(request, response, data, "text/html;charset=utf-8", result.isCompressable());
/*     */         return;
/*     */       
/*     */       case 11:
/* 148 */         handleBinary(request, response, (byte[])result.object, "text/html", result.isCompressable());
/*     */         return;
/*     */       case 6:
/* 151 */         handleText(request, response, (String)result.object, "text/css");
/*     */         return;
/*     */       case 10:
/* 154 */         handleBinary(request, response, (byte[])((Pair)result.object).getSecond(), (String)((Pair)result.object).getFirst(), result.isCompressable());
/*     */         return;
/*     */       case 13:
/* 157 */         properties = (ResultObject.FileProperties)result.object;
/*     */         
/* 159 */         response.setHeader("Content-Disposition", (properties.inline ? "inline" : "attachment") + "; filename=" + properties.filename);
/* 160 */         handleBinary(request, response, properties.data, properties.mime, result.isCompressable());
/*     */         return;
/*     */ 
/*     */       
/*     */       case 8:
/* 165 */         path = (String)result.object;
/* 166 */         dispatcher = request.getRequestDispatcher(path);
/* 167 */         dispatcher.forward((ServletRequest)request, (ServletResponse)response);
/*     */         return;
/*     */       
/*     */       case 12:
/* 171 */         path = (String)result.object;
/* 172 */         response.sendRedirect(path);
/*     */         return;
/*     */     } 
/*     */     
/* 176 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onApplicationShutdown() {
/* 181 */     log.info("**************************************** APPLICATION SHUTDOWN");
/* 182 */     getApplicationConnector().onShutdown();
/* 183 */     if (pt != null) {
/* 184 */       pt.stop();
/* 185 */       pt = null;
/*     */     } 
/* 187 */     log.info("**************************************** APPLICATION SHUTDOWN FINISHED");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onApplicationStartup() {
/* 192 */     System.setProperty("com.sun.xml.ws.fault.SOAPFaultBuilder.disableCaptureStackTrace", "false");
/*     */     
/* 194 */     System.setProperty("javax.xml.soap.MessageFactory", "com.sun.xml.messaging.saaj.soap.ver1_1.SOAPMessageFactory1_1Impl");
/* 195 */     System.setProperty("javax.xml.soap.SOAPConnectionFactory", "weblogic.wsee.saaj.SOAPConnectionFactoryImpl");
/* 196 */     log.info("**************************************** APPLICATION STARTUP");
/* 197 */     synchronized (Dispatcher.class) {
/* 198 */       if (applicationConnector != null) {
/*     */         return;
/*     */       }
/*     */       
/* 202 */       ApplicationContext.getInstance().setResourceLoader(new ApplicationContext.ResourceLoading() {
/*     */             public byte[] loadResource(String name) {
/* 204 */               byte[] retValue = null;
/*     */               try {
/* 206 */                 ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 207 */                 InputStream is = Dispatcher.this.getResourceAsStream(name);
/* 208 */                 byte[] buffer = new byte[1024];
/* 209 */                 int count = 0;
/* 210 */                 while ((count = is.read(buffer)) != -1) {
/* 211 */                   baos.write(buffer, 0, count);
/*     */                 }
/* 213 */                 is.close();
/* 214 */                 baos.close();
/* 215 */                 retValue = baos.toByteArray();
/* 216 */               } catch (Exception e) {}
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 221 */               return retValue;
/*     */             }
/*     */             
/*     */             public boolean existsResource(String name) {
/* 225 */               boolean retValue = false;
/*     */               try {
/* 227 */                 InputStream is = Dispatcher.this.getResourceAsStream(name);
/* 228 */                 if (is != null) {
/* 229 */                   is.close();
/* 230 */                   retValue = true;
/*     */                 } 
/* 232 */               } catch (Exception e) {
/* 233 */                 Dispatcher.log.warn("unable to check for existence, returning false - exception: " + e, e);
/*     */               } 
/*     */               
/* 236 */               return retValue;
/*     */             }
/*     */           });
/*     */       
/* 240 */       ApplicationContext.getInstance().setDeploymentName(getServletContext().getServletContextName());
/*     */       
/* 242 */       getApplicationConnector().onStartup(this);
/*     */       
/* 244 */       pt = new PeriodicTask(new Runnable() {
/*     */             public void run() {
/* 246 */               Dispatcher.log.debug("pending request size:" + Dispatcher.pending.size());
/*     */             }
/*     */           },  20000L);
/* 249 */       pt.start();
/*     */     } 
/* 251 */     log.info("**************************************** APPLICATION STARTUP FINISHED");
/*     */   }
/*     */ 
/*     */   
/*     */   protected ResultObject onInvalidDispatchTarget(Map parameters) throws Exception {
/* 256 */     return DefaultController.getInstance().onInvalidDispatchTarget(parameters);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/* 261 */     HttpSession session = request.getSession(true);
/* 262 */     synchronized (session) {
/*     */       
/* 264 */       log.debug("handling request:" + request.getRequestURL() + "?" + request.getQueryString());
/* 265 */       pending.add(request);
/* 266 */       StopWatch sw = StopWatch.getInstance().start();
/* 267 */       if (request.getCharacterEncoding() == null) {
/* 268 */         log.debug("character encoding of request not found, assuming utf-8");
/* 269 */         request.setCharacterEncoding("utf-8");
/*     */       } else {
/* 271 */         log.debug("character encoding of request found:" + request.getCharacterEncoding());
/*     */       } 
/*     */       
/* 274 */       super.processRequest(request, response);
/* 275 */       long responseTime = sw.stop();
/* 276 */       synchronized (Dispatcher.class) {
/* 277 */         r++;
/* 278 */         average = average * (r - 1.0D) / r + responseTime / r;
/* 279 */         logPerf.info("served request(" + r + ") in: " + responseTime + " ms");
/* 280 */         logPerf.info("average response time : " + NumberFormat.getInstance().format(average) + " ms");
/* 281 */         pending.remove(request);
/*     */       } 
/* 283 */       StopWatch.freeInstance(sw);
/* 284 */       log.debug("handled request (" + responseTime + " ms):" + request.getRequestURL() + "?" + request.getQueryString());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\Dispatcher.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */