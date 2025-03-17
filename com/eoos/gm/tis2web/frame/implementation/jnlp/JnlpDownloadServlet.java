/*     */ package com.eoos.gm.tis2web.frame.implementation.jnlp;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClusterTaskExecution;
/*     */ import com.eoos.util.Task;
/*     */ import com.eoos.util.v2.StringUtilities;
/*     */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import jnlp.sample.servlet.JarDiffHandler;
/*     */ import jnlp.sample.servlet.JnlpDownloadServlet;
/*     */ import jnlp.sample.servlet.Logger;
/*     */ import jnlp.sample.servlet.ResourceCatalog;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ @SuppressWarnings({"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"})
/*     */ public class JnlpDownloadServlet
/*     */   extends JnlpDownloadServlet
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   private static final class Task_CheckDownloadPermission
/*     */     implements Task, Task.ClusterExecution {
/*     */     private static final long serialVersionUID = 1L;
/*     */     private final String path;
/*     */     private final String sessionid;
/*     */     
/*     */     private Task_CheckDownloadPermission(String path, String sessionid) {
/*  47 */       this.path = path;
/*  48 */       this.sessionid = sessionid;
/*     */     }
/*     */     
/*     */     public Object execute() {
/*  52 */       return Boolean.valueOf(JnlpDownloadServlet.checkDownloadPermission(this.path, this.sessionid));
/*     */     }
/*     */   }
/*     */   
/*  56 */   private static final Logger log = Logger.getLogger(JnlpDownloadServlet.class);
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String MODE = "mode";
/*     */ 
/*     */   
/*     */   public static final String POLL = "poll";
/*     */ 
/*     */   
/*     */   protected ServletContext context;
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(ServletConfig config) throws ServletException {
/*  71 */     log.info("name=" + config.getServletName());
/*  72 */     ServletContext context = config.getServletContext();
/*  73 */     log.info(context);
/*  74 */     this.context = new ServletContextDecorator(config.getServletContext());
/*  75 */     super.init(config);
/*  76 */     clearTempJarDiffs();
/*     */     try {
/*  78 */       String mode = config.getInitParameter("mode");
/*  79 */       boolean poll = (mode != null && mode.equalsIgnoreCase("poll"));
/*  80 */       Class<?> superclass = getClass().getSuperclass();
/*  81 */       Field field = superclass.getDeclaredField("_resourceCatalog");
/*  82 */       field.setAccessible(true);
/*  83 */       ResourceCatalog catalog = (ResourceCatalog)field.get(this);
/*  84 */       ResourceCatalog resources = ResourceCatalog.createInstance(getServletContext(), poll, catalog);
/*  85 */       field.set(this, resources);
/*  86 */       field = superclass.getDeclaredField("_log");
/*  87 */       field.setAccessible(true);
/*  88 */       Logger logger = (Logger)field.get(this);
/*  89 */       field = superclass.getDeclaredField("_jarDiffHandler");
/*  90 */       field.setAccessible(true);
/*  91 */       JarDiffHandler replacement = new JarDiffHandler(getServletContext(), logger);
/*  92 */       field.set(this, replacement);
/*  93 */     } catch (Exception e) {
/*  94 */       log.error("failed to intercept jnlp download servlet", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public ServletContext getServletContext() {
/*  99 */     return this.context;
/*     */   }
/*     */   
/* 102 */   private static Set tmpDirs = Collections.synchronizedSet(new HashSet());
/*     */   
/* 104 */   private final Object SYNC_TMP_DIR = new Object();
/*     */   
/* 106 */   private File tmpDir = null;
/*     */   
/*     */   public static Set getTmpDirs() {
/* 109 */     return tmpDirs;
/*     */   }
/*     */   
/*     */   private File getTmpDir() {
/* 113 */     synchronized (this.SYNC_TMP_DIR) {
/* 114 */       if (this.tmpDir == null) {
/* 115 */         this.tmpDir = (File)getServletContext().getAttribute("javax.servlet.context.tempdir");
/* 116 */         tmpDirs.add(this.tmpDir);
/*     */       } 
/* 118 */       return this.tmpDir;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void clearTempJarDiffs() {
/*     */     try {
/* 125 */       File file = getTmpDir();
/* 126 */       if (file != null && file.isDirectory()) {
/* 127 */         File[] files = file.listFiles();
/* 128 */         for (int i = 0; i < files.length; i++) {
/* 129 */           file = files[i];
/* 130 */           if (file.isFile() && file.getName().startsWith("jnlp") && file.getName().endsWith(".jardiff")) {
/* 131 */             file.delete();
/* 132 */           } else if (file.isFile() && file.getName().startsWith("temp") && file.getName().endsWith(".jar")) {
/* 133 */             file.delete();
/*     */           } 
/*     */         } 
/*     */       } 
/* 137 */     } catch (Exception e) {
/* 138 */       log.warn("failed to clear out temporary jardiffs");
/*     */     } 
/*     */   }
/*     */   
/*     */   private static HttpServletRequest hookRequest(final HttpServletRequest request, final String sessionID) {
/* 143 */     InvocationHandler handler = new InvocationHandler()
/*     */       {
/* 145 */         private final List hookStringMethods = Arrays.asList(new String[] { "getPathInfo", "getPathTranslated", "getRequestURI" });
/*     */         
/*     */         public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
/* 148 */           Object result = method.invoke(request, args);
/* 149 */           String methodName = method.getName();
/*     */           
/* 151 */           if (this.hookStringMethods.contains(methodName)) {
/* 152 */             JnlpDownloadServlet.log.debug("hooked method: " + methodName + " originally returned " + String.valueOf(result));
/* 153 */             StringBuffer tmp = new StringBuffer((String)result);
/* 154 */             boolean startsWithSlash = (tmp.indexOf("/") == 0);
/* 155 */             boolean endsWithSlash = (tmp.lastIndexOf("/") == tmp.length() - 1);
/* 156 */             if (!startsWithSlash) {
/* 157 */               tmp.insert(0, "/");
/*     */             }
/* 159 */             if (!endsWithSlash) {
/* 160 */               tmp.append("/");
/*     */             }
/* 162 */             StringUtilities.replace(tmp, "/" + sessionID + "/", "/");
/* 163 */             if (!startsWithSlash) {
/* 164 */               tmp.deleteCharAt(0);
/*     */             }
/* 166 */             if (!endsWithSlash) {
/* 167 */               tmp.deleteCharAt(tmp.length() - 1);
/*     */             }
/*     */             
/* 170 */             result = tmp.toString();
/* 171 */             JnlpDownloadServlet.log.debug("...new result: " + String.valueOf(result));
/*     */           } 
/*     */ 
/*     */           
/* 175 */           return result;
/*     */         }
/*     */       };
/*     */     
/* 179 */     return (HttpServletRequest)Proxy.newProxyInstance(JnlpDownloadServlet.class.getClassLoader(), new Class[] { HttpServletRequest.class }, handler);
/*     */   }
/*     */   
/*     */   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/* 183 */     if (isSecureMode()) {
/* 184 */       if (checkDownloadPermission(request)) {
/* 185 */         String tmpSessionID = request.getPathInfo();
/*     */ 
/*     */         
/* 188 */         tmpSessionID = tmpSessionID.substring(0, tmpSessionID.lastIndexOf("/"));
/* 189 */         String sessionID = StringUtilities.replace(tmpSessionID, "/", "");
/* 190 */         if (log.isDebugEnabled()) {
/* 191 */           super.doGet(hookRequest(request, sessionID), (HttpServletResponse)new LogDownloadResponse(request, response));
/*     */         } else {
/* 193 */           super.doGet(hookRequest(request, sessionID), response);
/*     */         } 
/*     */       } else {
/* 196 */         response.sendError(404);
/*     */       } 
/*     */     } else {
/*     */       try {
/* 200 */         if (log.isDebugEnabled()) {
/* 201 */           super.doGet(request, (HttpServletResponse)new LogDownloadResponse(request, response));
/*     */         } else {
/* 203 */           super.doGet(request, response);
/*     */         } 
/* 205 */       } catch (ServletException se) {
/* 206 */         log.error("JNLP Download Failed: " + request.getPathInfo(), (Throwable)se);
/* 207 */         throw se;
/* 208 */       } catch (IOException ie) {
/* 209 */         log.error("JNLP Download Failed: " + request.getPathInfo(), ie);
/* 210 */         throw ie;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean checkDownloadPermission(HttpServletRequest request) {
/*     */     try {
/* 217 */       URL url = new URL(request.getRequestURL().toString());
/* 218 */       String tmpPath = url.getPath();
/* 219 */       String urlPath = tmpPath.substring(0, tmpPath.lastIndexOf("/"));
/*     */       
/* 221 */       String tmpSessionID = request.getPathInfo();
/*     */       
/* 223 */       tmpSessionID = tmpSessionID.substring(0, tmpSessionID.lastIndexOf("/"));
/* 224 */       String sessionID = StringUtilities.replace(tmpSessionID, "/", "");
/*     */       
/* 226 */       Task task = new Task_CheckDownloadPermission(urlPath, sessionID);
/* 227 */       ClusterTaskExecution cTask = new ClusterTaskExecution(task, null) {
/*     */           protected boolean doContinue(Object lastResult) {
/*     */             try {
/* 230 */               return !((Boolean)lastResult).booleanValue();
/* 231 */             } catch (Exception e) {
/* 232 */               return true;
/*     */             } 
/*     */           }
/*     */         };
/* 236 */       return cTask.execute().getResults().contains(Boolean.TRUE);
/* 237 */     } catch (Exception e) {
/* 238 */       log.error("unable to determine download permission, returning 'false' - exception:" + e, e);
/* 239 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean checkDownloadPermission(String urlpath, String sessionID) {
/* 244 */     log.info("checking for valid download registration (sessionID:" + sessionID + ", urlpath:" + urlpath);
/* 245 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/* 246 */     if (context != null) {
/* 247 */       DownloadRegistration downloadRegistration = DownloadRegistration.getInstance(context, urlpath);
/* 248 */       if (downloadRegistration != null) {
/* 249 */         if (downloadRegistration.isValid()) {
/* 250 */           log.info("...found valid one");
/* 251 */           return true;
/*     */         } 
/* 253 */         log.debug("...found INVALID one");
/*     */       } else {
/*     */         
/* 256 */         log.debug("...did not find download registration");
/*     */       } 
/*     */     } else {
/* 259 */       log.debug("...invalid session");
/*     */     } 
/* 261 */     log.info("...did not find (valid) download registration");
/* 262 */     return false;
/*     */   }
/*     */   
/*     */   public static URL prepareDownloadPermission(ClientContext context, URL codebase, long maxValidity) {
/* 266 */     if (isSecureMode()) {
/* 267 */       StringBuffer newPath = new StringBuffer(codebase.getPath());
/* 268 */       String sessionID = context.getSessionID();
/* 269 */       newPath.append("/" + sessionID);
/* 270 */       StringUtilities.replace(newPath, "//", "/");
/* 271 */       if (DownloadRegistration.createInstance(context, newPath.toString(), maxValidity) == null) {
/* 272 */         throw new RuntimeException("unable to create download registration");
/*     */       }
/*     */       try {
/* 275 */         return new URL(codebase.getProtocol(), codebase.getHost(), codebase.getPort(), newPath.toString());
/* 276 */       } catch (MalformedURLException e) {
/* 277 */         throw new RuntimeException(e);
/*     */       } 
/*     */     } 
/*     */     
/* 281 */     return codebase;
/*     */   }
/*     */ 
/*     */   
/* 285 */   private static Boolean secureMode = null;
/*     */   
/*     */   public static boolean isSecureMode() {
/* 288 */     synchronized (JnlpDownloadServlet.class) {
/* 289 */       if (secureMode == null) {
/* 290 */         log.info("determine jnlp download mode");
/*     */         try {
/* 292 */           secureMode = Boolean.valueOf(ApplicationContext.getInstance().getProperty("frame.jnlp.download.secure.mode").trim());
/* 293 */         } catch (Exception e) {
/* 294 */           secureMode = Boolean.FALSE;
/*     */         } 
/* 296 */         log.info("..." + (secureMode.booleanValue() ? "SECURE MODE" : "OPEN MODE"));
/*     */       } 
/* 298 */       return secureMode.booleanValue();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\jnlp\JnlpDownloadServlet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */