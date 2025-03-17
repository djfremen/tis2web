/*     */ package com.eoos.gm.tis2web.frame.export.common;
/*     */ 
/*     */ import com.eoos.util.Task;
/*     */ import com.eoos.util.v2.Util;
/*     */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.SocketException;
/*     */ import java.net.URL;
/*     */ import java.security.SecureRandom;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import java.util.zip.GZIPOutputStream;
/*     */ import javax.net.ssl.HostnameVerifier;
/*     */ import javax.net.ssl.HttpsURLConnection;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import javax.net.ssl.SSLSession;
/*     */ import javax.net.ssl.SSLSocketFactory;
/*     */ import javax.net.ssl.TrustManager;
/*     */ import javax.net.ssl.X509TrustManager;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ @SuppressWarnings({"DMI_COLLECTION_OF_URLS"})
/*     */ public class ClusterTaskExecution
/*     */ {
/*  36 */   private static final Logger log = Logger.getLogger(ClusterTaskExecution.class);
/*     */   private Task task;
/*     */   
/*     */   public static final class Result {
/*  40 */     private Map<URL, Object> map = new IdentityHashMap<URL, Object>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Set getClusterURLs() {
/*  47 */       return this.map.keySet();
/*     */     }
/*     */     
/*     */     public Object getResult(URL url) {
/*  51 */       return this.map.get(url);
/*     */     }
/*     */     
/*     */     public Collection getResults() {
/*  55 */       return this.map.values();
/*     */     }
/*     */     
/*     */     public Object getLocalResult() {
/*  59 */       URL localURL = null;
/*  60 */       for (Iterator<URL> iter = getClusterURLs().iterator(); iter.hasNext() && localURL == null; ) {
/*  61 */         URL url = iter.next();
/*  62 */         if (ApplicationContext.getInstance().isLocalURL(url)) {
/*  63 */           localURL = url;
/*     */         }
/*     */       } 
/*  66 */       return (localURL != null) ? getResult(localURL) : null;
/*     */     }
/*     */ 
/*     */     
/*     */     private Result() {}
/*     */   }
/*     */   
/*     */   static {
/*     */     try {
/*  75 */       final SSLSocketFactory restore1 = HttpsURLConnection.getDefaultSSLSocketFactory();
/*  76 */       final HostnameVerifier restore2 = HttpsURLConnection.getDefaultHostnameVerifier();
/*     */       
/*  78 */       ApplicationContext.getInstance().addShutdownListener(new ApplicationContext.ShutdownListener()
/*     */           {
/*     */             public void onShutdown() {
/*  81 */               HttpsURLConnection.setDefaultSSLSocketFactory(restore1);
/*  82 */               HttpsURLConnection.setDefaultHostnameVerifier(restore2);
/*     */             }
/*     */           });
/*     */       
/*  86 */       TrustManager[] trustAllCerts = { new X509TrustManager() {
/*     */             public X509Certificate[] getAcceptedIssuers() {
/*  88 */               return null;
/*     */             }
/*     */ 
/*     */ 
/*     */             
/*     */             public void checkClientTrusted(X509Certificate[] certs, String authType) {}
/*     */ 
/*     */             
/*     */             public void checkServerTrusted(X509Certificate[] certs, String authType) {}
/*     */           } };
/*  98 */       SSLContext sc = SSLContext.getInstance("SSL");
/*  99 */       sc.init(null, trustAllCerts, new SecureRandom());
/* 100 */       HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
/*     */       
/* 102 */       HostnameVerifier hv = new HostnameVerifier()
/*     */         {
/*     */           public boolean verify(String s, SSLSession sslSession) {
/* 105 */             return true;
/*     */           }
/*     */         };
/* 108 */       HttpsURLConnection.setDefaultHostnameVerifier(hv);
/* 109 */     } catch (Exception e) {
/* 110 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public ClusterTaskExecution(Task task, ClientContext context) {
/* 115 */     this.task = task;
/*     */   }
/*     */   
/*     */   protected Collection getClusterURLs() {
/* 119 */     return ApplicationContext.getInstance().getClusterURLs();
/*     */   }
/*     */   
/*     */   private static final class HttpStatusException extends Exception {
/*     */     private static final long serialVersionUID = 1L;
/*     */     private int status;
/*     */     
/*     */     public HttpStatusException(int status) {
/* 127 */       this.status = status;
/*     */     }
/*     */     
/*     */     public int getStatus() {
/* 131 */       return this.status;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Result execute() {
/* 137 */     log.info("sending cluster task ...");
/* 138 */     Object result = null;
/* 139 */     Result retValue = new Result();
/* 140 */     List<?> clusterURLs = new ArrayList(getClusterURLs());
/* 141 */     Collections.sort(clusterURLs, new Comparator()
/*     */         {
/* 143 */           private ApplicationContext appContext = ApplicationContext.getInstance();
/*     */           
/*     */           public int compare(Object o1, Object o2) {
/* 146 */             if (this.appContext.isLocalURL((URL)o1))
/* 147 */               return -1; 
/* 148 */             if (this.appContext.isLocalURL((URL)o2)) {
/* 149 */               return 1;
/*     */             }
/* 151 */             return Util.compare(((URL)o1).toString(), ((URL)o2).toString());
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 156 */     for (Iterator<?> iter = clusterURLs.iterator(); iter.hasNext() && (result == null || doContinue(result)); ) {
/* 157 */       URL url = (URL)iter.next();
/* 158 */       result = null;
/*     */       try {
/* 160 */         if (ApplicationContext.getInstance().isLocalURL(url)) {
/* 161 */           log.info("....executing locally ");
/* 162 */           result = this.task.execute();
/*     */         } else {
/* 164 */           log.info("....executing for cluster server :" + url);
/* 165 */           String path = (url.getPath() != null) ? url.getPath() : "";
/* 166 */           path = path + (path.endsWith("/") ? "exectask" : "/exectask");
/* 167 */           URL taskExecURL = new URL(url.getProtocol(), url.getHost(), url.getPort(), path);
/*     */ 
/*     */           
/* 170 */           HttpURLConnection connection = (HttpURLConnection)taskExecURL.openConnection();
/* 171 */           connection.setRequestMethod("POST");
/* 172 */           connection.setDoOutput(true);
/* 173 */           connection.setDoInput(true);
/*     */           
/* 175 */           connection.connect();
/*     */           try {
/* 177 */             ObjectOutputStream oos = new ObjectOutputStream(new GZIPOutputStream(connection.getOutputStream()));
/*     */             try {
/* 179 */               oos.writeObject(this.task);
/*     */             } finally {
/* 181 */               oos.close();
/*     */             } 
/*     */             
/* 184 */             if (connection.getResponseCode() != 200) {
/* 185 */               throw new HttpStatusException(connection.getResponseCode());
/*     */             }
/*     */             
/* 188 */             ObjectInputStream ois = new ObjectInputStream(new GZIPInputStream(connection.getInputStream()));
/*     */             try {
/* 190 */               result = ois.readObject();
/*     */             } finally {
/* 192 */               ois.close();
/*     */             } 
/* 194 */             log.debug("......done");
/*     */           } finally {
/*     */             
/*     */             try {
/* 198 */               connection.disconnect();
/* 199 */             } catch (Exception e) {}
/*     */           }
/*     */         
/*     */         }
/*     */       
/* 204 */       } catch (HttpStatusException e) {
/* 205 */         log.error("unable to execute task, server returned status: " + e.getStatus());
/* 206 */         result = e;
/* 207 */       } catch (SocketException e) {
/* 208 */         log.error("unable to execute task, socket exception: " + e.getMessage());
/* 209 */         result = e;
/* 210 */       } catch (Exception e) {
/* 211 */         log.error("unable to execute task - exception:" + e, e);
/* 212 */         result = e;
/*     */       } 
/* 214 */       retValue.map.put(url, result);
/*     */     } 
/* 216 */     log.debug("...done");
/* 217 */     return retValue;
/*     */   }
/*     */   
/*     */   protected boolean doContinue(Object lastResult) {
/* 221 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\ClusterTaskExecution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */