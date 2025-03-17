/*    */ package com.eoos.gm.tis2web.frame.implementation.jnlp;
/*    */ 
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.http.HttpServletResponseWrapper;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LogDownloadResponse
/*    */   extends HttpServletResponseWrapper
/*    */ {
/* 13 */   private static final Logger log = Logger.getLogger(JnlpDownloadServlet.class);
/*    */   
/*    */   private static final String HEADER_JNLP_VERSION = "x-java-jnlp-version-id";
/*    */   
/*    */   private HttpServletResponse response;
/*    */   private String request;
/*    */   private String mime;
/*    */   private String version;
/*    */   private String encoding;
/*    */   private int length;
/*    */   
/*    */   public LogDownloadResponse(HttpServletRequest request, HttpServletResponse response) {
/* 25 */     super(response);
/* 26 */     this.request = request.getRequestURI();
/* 27 */     this.response = response;
/*    */   }
/*    */   
/*    */   public void setContentType(String contentType) {
/* 31 */     this.response.setContentType(contentType);
/* 32 */     this.mime = contentType;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setContentLength(int length) {
/* 37 */     super.setContentLength(length);
/* 38 */     this.length = length;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setHeader(String name, String value) {
/* 43 */     super.setHeader(name, value);
/* 44 */     if (name.equals("x-java-jnlp-version-id")) {
/* 45 */       this.version = value;
/* 46 */     } else if (name.equals("content-encoding")) {
/* 47 */       this.encoding = value;
/* 48 */       log.debug(this.request + " -> " + ((this.version == null) ? "n/a" : this.version) + " (" + this.mime + "," + this.encoding + "=" + this.length + ")");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\jnlp\LogDownloadResponse.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */