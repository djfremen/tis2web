/*     */ package com.eoos.gm.tis2web.common;
/*     */ 
/*     */ import com.eoos.math.AverageCalculator;
/*     */ import com.eoos.scsm.v2.io.InputStreamByteCount;
/*     */ import com.eoos.scsm.v2.io.OutputStreamByteCount;
/*     */ import com.eoos.scsm.v2.io.StreamUtil;
/*     */ import com.eoos.scsm.v2.util.Counter;
/*     */ import com.eoos.scsm.v2.util.ICounter;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.Task;
/*     */ import com.eoos.util.v2.StopWatch;
/*     */ import com.sonalb.net.http.cookie.Cookie;
/*     */ import com.sonalb.net.http.cookie.RFC2965CookieParser;
/*     */ import com.sonalb.net.http.cookie.Session;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InterruptedIOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectStreamClass;
/*     */ import java.io.OutputStream;
/*     */ import java.math.BigDecimal;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import java.util.zip.GZIPOutputStream;
/*     */ import javax.net.ssl.HostnameVerifier;
/*     */ import javax.net.ssl.HttpsURLConnection;
/*     */ import javax.net.ssl.SSLSession;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TaskExecutionClient
/*     */ {
/*  43 */   private static final Logger log = Logger.getLogger(TaskExecutionClient.class);
/*     */   
/*  45 */   private static Map instances = Collections.synchronizedMap(new HashMap<Object, Object>());
/*     */   
/*     */   static {
/*  48 */     ProxyUtil.setDefaultProxySelector();
/*  49 */     HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier()
/*     */         {
/*     */           public boolean verify(String hostname, SSLSession session) {
/*  52 */             return true;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*  57 */   private ClientClassLoader ccl = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   private AverageCalculator averageSpeedCalculator = new AverageCalculator(0);
/*     */   
/*     */   private URL url;
/*     */   
/*  66 */   private Session cookieHandler = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TaskExecutionClient(URL url) {
/*  75 */     log.debug("creating instance for url: " + String.valueOf(url) + "...");
/*  76 */     this.url = url;
/*     */ 
/*     */     
/*  79 */     this.averageSpeedCalculator.add(BigDecimal.valueOf(1L));
/*     */     
/*  81 */     ClassLoader cl = getClass().getClassLoader();
/*  82 */     if (cl instanceof ClientClassLoader) {
/*  83 */       this.ccl = (ClientClassLoader)cl;
/*     */     } else {
/*  85 */       this.ccl = new ClientClassLoader(getClass().getClassLoader(), this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TaskExecutionClient(URL url, String cookie) {
/*  95 */     this(url);
/*     */     
/*  97 */     this.cookieHandler = new Session();
/*  98 */     if (cookie != null) {
/*  99 */       Cookie _cookie = null;
/*     */       try {
/* 101 */         if (RFC2965CookieParser.isRFC2965CookieString(cookie)) {
/* 102 */           _cookie = RFC2965CookieParser.parseSingleCookieV1(cookie, url, false);
/*     */         } else {
/* 104 */           _cookie = RFC2965CookieParser.parseSingleCookieV0(cookie, url, false);
/*     */         } 
/*     */         
/* 107 */         this.cookieHandler.getCookieJar().add(_cookie);
/* 108 */         log.debug("...added cookie: " + _cookie);
/* 109 */       } catch (Exception e) {
/* 110 */         log.error("failed to add cookie: " + cookie);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public URL getURL() {
/* 116 */     return this.url;
/*     */   }
/*     */   
/*     */   private synchronized Session getCookieHandler() {
/* 120 */     if (this.cookieHandler == null) {
/* 121 */       this.cookieHandler = new Session();
/*     */       
/* 123 */       URI uri = Util.toURI(this.url);
/*     */       
/* 125 */       String cookie = HttpSettings.getInstance(uri).getCookie();
/* 126 */       if (cookie != null) {
/* 127 */         Cookie _cookie = null;
/*     */         try {
/* 129 */           if (RFC2965CookieParser.isRFC2965CookieString(cookie)) {
/* 130 */             _cookie = RFC2965CookieParser.parseSingleCookieV1(cookie, this.url, false);
/*     */           } else {
/* 132 */             _cookie = RFC2965CookieParser.parseSingleCookieV0(cookie, this.url, false);
/*     */           } 
/*     */           
/* 135 */           this.cookieHandler.getCookieJar().add(_cookie);
/* 136 */           log.debug("...added cookie: " + _cookie);
/* 137 */         } catch (Exception e) {
/* 138 */           log.error("failed to add cookie: " + cookie);
/*     */         } 
/*     */       } 
/*     */     } 
/* 142 */     return this.cookieHandler;
/*     */   }
/*     */   
/*     */   public static synchronized TaskExecutionClient getInstance(URL url) {
/* 146 */     URI uri = Util.toURI(url);
/* 147 */     TaskExecutionClient instance = (TaskExecutionClient)instances.get(uri);
/* 148 */     if (instance == null) {
/* 149 */       instance = new TaskExecutionClient(url);
/* 150 */       instances.put(uri, instance);
/*     */     } 
/* 152 */     return instance;
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkInterruption() throws InterruptedIOException {
/* 157 */     if (Thread.interrupted()) {
/* 158 */       Thread.currentThread().interrupt();
/* 159 */       throw new InterruptedIOException();
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object execute(Task task) throws IOException {
/* 164 */     Object ret = null;
/* 165 */     log.debug("sending task: " + String.valueOf(task) + " to server (url:" + this.url + ")");
/* 166 */     HttpURLConnection connection = (HttpURLConnection)this.url.openConnection();
/*     */     try {
/* 168 */       connection.setAllowUserInteraction(true);
/* 169 */       connection.setUseCaches(false);
/* 170 */       connection.setDoOutput(true);
/* 171 */       connection.setRequestMethod("POST");
/*     */       
/* 173 */       getCookieHandler().setCookies(connection);
/*     */       
/* 175 */       if (connection.usingProxy()) {
/* 176 */         log.debug("...connection is using a proxy");
/*     */       }
/*     */ 
/*     */       
/* 180 */       Counter counter = new Counter();
/* 181 */       StopWatch sw = StopWatch.getInstance();
/*     */       
/* 183 */       checkInterruption();
/*     */       
/* 185 */       log.debug("...serializing/writing task ");
/* 186 */       connection.setRequestProperty("Content-Type", "application/octet-stream");
/*     */       
/* 188 */       OutputStream os = new BufferedOutputStream(connection.getOutputStream());
/* 189 */       GZIPOutputStream gzos = new GZIPOutputStream(os, 50000);
/* 190 */       ObjectOutputStream oos = new ObjectOutputStream((OutputStream)new OutputStreamByteCount(gzos, (ICounter)counter));
/*     */       try {
/* 192 */         oos.writeObject(task);
/*     */       } finally {
/* 194 */         StreamUtil.close(oos, log);
/*     */       } 
/*     */       
/* 197 */       sw.start();
/* 198 */       int httpStatus = connection.getResponseCode();
/* 199 */       log.debug("...received status: " + httpStatus);
/*     */       
/* 201 */       if (httpStatus != 200) {
/* 202 */         throw new IOException("unhandled http status code: " + httpStatus);
/*     */       }
/*     */ 
/*     */       
/* 206 */       getCookieHandler().getCookies(connection);
/*     */       
/* 208 */       log.debug("...reading response body ");
/* 209 */       InputStream inputStream = new BufferedInputStream(connection.getInputStream());
/*     */       try {
/* 211 */         if (task instanceof StreamingTask) {
/* 212 */           log.debug("...task supports streaming, delegating response stream reading...");
/* 213 */           ((StreamingTask)task).handleResult(inputStream);
/* 214 */           log.debug("....reading done (delegation is ignored by transfer speed calculation)");
/* 215 */           ret = null;
/*     */         } else {
/* 217 */           inputStream = new GZIPInputStream(inputStream, 50000);
/* 218 */           InputStreamByteCount inputStreamByteCount = new InputStreamByteCount(inputStream, (ICounter)counter);
/* 219 */           inputStream = new ObjectInputStream((InputStream)inputStreamByteCount) {
/*     */               protected Class resolveClass(ObjectStreamClass osc) throws ClassNotFoundException, IOException {
/*     */                 try {
/* 222 */                   return Class.forName(osc.getName(), true, TaskExecutionClient.this.ccl);
/* 223 */                 } catch (ClassNotFoundException e) {
/*     */                   
/* 225 */                   return super.resolveClass(osc);
/*     */                 } 
/*     */               }
/*     */             };
/*     */           
/* 230 */           checkInterruption();
/*     */           try {
/* 232 */             ret = inputStream.readObject();
/* 233 */           } catch (ClassNotFoundException e) {
/* 234 */             throw new RuntimeException(e);
/*     */           } 
/*     */           
/* 237 */           log.debug("...reading done, updating transfer speed average");
/* 238 */           long transferTime = Math.max(1L, sw.stop());
/* 239 */           BigDecimal bytesPerMillisecond = (new BigDecimal(counter.getCount())).divide(BigDecimal.valueOf(transferTime), 4);
/* 240 */           this.averageSpeedCalculator.add(bytesPerMillisecond);
/* 241 */           StopWatch.freeInstance(sw);
/*     */         } 
/*     */         
/* 244 */         return ret;
/*     */       } finally {
/*     */         
/* 247 */         StreamUtil.close(inputStream, log);
/*     */       } 
/*     */     } finally {
/*     */       
/* 251 */       connection.disconnect();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public long getTimeEstimate(long byteCount) {
/* 257 */     return BigDecimal.valueOf(1L, 10).divide(this.averageSpeedCalculator.getCurrentAverage(), 4).multiply(BigDecimal.valueOf(byteCount)).longValue();
/*     */   }
/*     */   
/*     */   public synchronized boolean login(URL loginURL) throws IOException {
/* 261 */     log.debug("sending login request: " + loginURL);
/* 262 */     HttpURLConnection connection = (HttpURLConnection)loginURL.openConnection();
/*     */     try {
/* 264 */       connection.setAllowUserInteraction(true);
/* 265 */       connection.setRequestMethod("GET");
/*     */       
/* 267 */       getCookieHandler().setCookies(connection);
/*     */       
/* 269 */       if (connection.usingProxy()) {
/* 270 */         log.debug("...connection is using a proxy");
/*     */       }
/*     */       
/* 273 */       checkInterruption();
/*     */       
/* 275 */       int httpStatus = connection.getResponseCode();
/* 276 */       log.debug("...received status: " + httpStatus);
/* 277 */       if (httpStatus != 200) {
/* 278 */         throw new IOException("unhandled http status code: " + httpStatus);
/*     */       }
/*     */       
/* 281 */       getCookieHandler().getCookies(connection);
/*     */       
/* 283 */       return true;
/*     */     } finally {
/* 285 */       connection.disconnect();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\common\TaskExecutionClient.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */