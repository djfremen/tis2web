/*    */ package com.eoos.gm.tis2web.frame.dwnld.server;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.datatype.gtwo.PairImpl;
/*    */ import com.eoos.gm.tis2web.frame.dls.server.SoftwareKeyCheckServer;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.CookieUtil;
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.URLTweak;
/*    */ import com.eoos.gm.tis2web.frame.implementation.jnlp.JnlpDownloadServlet;
/*    */ import com.eoos.html.ResultObject;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import com.eoos.util.v2.Base64EncodingUtil;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URL;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FileDwnldUtil
/*    */ {
/* 29 */   private static final Logger log = Logger.getLogger(FileDwnldUtil.class);
/*    */   
/*    */   private static final long DOWNLOAD_REGISTRATION_VALIDITY = 3600000L;
/*    */   
/*    */   private static String jnlpTemplate;
/*    */   
/*    */   static {
/*    */     try {
/* 37 */       jnlpTemplate = ApplicationContext.getInstance().loadTextResource("/common/client/filedwnld.jnlp", null);
/* 38 */     } catch (Exception e) {
/* 39 */       throw new ExceptionWrapper(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ResultObject getJNLP(ClientContext context, HttpServletRequest request, String filename) {
/*    */     try {
/* 48 */       return new ResultObject(10, true, false, new PairImpl("application/x-java-jnlp-file", getJNLPDescriptor(context, request, filename).getBytes("utf-8")));
/* 49 */     } catch (UnsupportedEncodingException e) {
/* 50 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static String getJNLPDescriptor(ClientContext context, HttpServletRequest request, String filename) {
/*    */     URL codebase;
/* 56 */     String cookie = request.getHeader("Cookie");
/*    */     
/* 58 */     StringBuffer jnlp = new StringBuffer(jnlpTemplate);
/* 59 */     String codeBaseURL = URLTweak.fixURL(ApplicationContext.getInstance().getProperty("frame.filedwnld.url.download.server"), request);
/*    */ 
/*    */     
/*    */     try {
/* 63 */       codebase = JnlpDownloadServlet.prepareDownloadPermission(context, new URL(codeBaseURL), 3600000L);
/* 64 */     } catch (MalformedURLException e) {
/* 65 */       throw new RuntimeException(e);
/*    */     } 
/*    */     
/* 68 */     String dispatchURL = URLTweak.fixURL(ApplicationContext.getInstance().getProperty("frame.url.task.execution"), request);
/*    */     
/* 70 */     StringUtilities.replace(jnlp, "{CODEBASEURL}", codebase.toExternalForm());
/* 71 */     StringUtilities.replace(jnlp, "{DISPATCHURL}", dispatchURL);
/*    */     
/* 73 */     String serverURL = URLTweak.fixURL(ApplicationContext.getInstance().getProperty("tis2web.server.url"), request);
/* 74 */     StringUtilities.replace(jnlp, "{SERVER.URL}", serverURL);
/*    */     
/* 76 */     StringUtilities.replace(jnlp, "{SESSIONID}", context.getSessionID());
/* 77 */     StringUtilities.replace(jnlp, "{SESSION_TIMEOUT}", String.valueOf(ApplicationContext.getInstance().getSessionTimeout(false)));
/*    */     
/* 79 */     StringUtilities.replace(jnlp, "{LANGUAGEID}", String.valueOf(context.getLocale()));
/* 80 */     StringUtilities.replace(jnlp, "{COOKIE}", Base64EncodingUtil.encode(Util.getUTF8Bytes(CookieUtil.adjustCookieOrdering(String.valueOf(cookie)))));
/*    */     
/* 82 */     String appServerDwnld = ApplicationContext.getInstance().getProperty("frame.dwnld.include.appserver");
/* 83 */     if (Util.isNullOrEmpty(appServerDwnld)) {
/* 84 */       appServerDwnld = "true";
/*    */     }
/* 86 */     StringUtilities.replace(jnlp, "{APPSERVER_DOWNLOAD}", appServerDwnld);
/*    */ 
/*    */     
/* 89 */     jnlp = SoftwareKeyCheckServer.setSoftwareKeyCheck(context.getSessionID(), jnlp);
/*    */     
/* 91 */     StringUtilities.replace(jnlp, "{FILENAME}", filename);
/*    */     
/* 93 */     return jnlp.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\server\FileDwnldUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */