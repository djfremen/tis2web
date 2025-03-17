/*    */ package com.eoos.gm.tis2web.frame.export.common.util;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import java.net.URL;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class URLTweak
/*    */ {
/* 13 */   private static Logger log = Logger.getLogger(URLTweak.class);
/*    */   
/*    */   public static String fixURL(String _url, HttpServletRequest request) {
/* 16 */     String result = _url;
/*    */     
/* 18 */     String doItAll = ApplicationContext.getInstance().getProperty("frame.url.explicit.modify.all");
/* 19 */     if (doItAll != null && doItAll.compareTo("true") == 0) {
/*    */       try {
/* 21 */         URL inURL = new URL(_url);
/* 22 */         URL url = new URL(request.getScheme(), request.getServerName(), request.getServerPort(), inURL.getFile());
/* 23 */         result = url.toString();
/* 24 */         log.debug("fixedURL: " + result);
/* 25 */       } catch (Exception e) {
/* 26 */         log.error("download URL handling error: " + e.toString());
/*    */       } 
/*    */     } else {
/* 29 */       String doIt = ApplicationContext.getInstance().getProperty("frame.url.explicit.modify");
/* 30 */       if (doIt != null && doIt.compareTo("true") == 0) {
/*    */         try {
/* 32 */           URL inURL = new URL(_url);
/* 33 */           URL uRL1 = new URL(request.getScheme(), inURL.getHost(), request.getServerPort(), inURL.getFile());
/* 34 */           result = uRL1.toString();
/* 35 */           log.debug("fixedURL: " + result);
/* 36 */         } catch (Exception e) {
/* 37 */           log.error("download URL handling error: " + e.toString());
/*    */         } 
/*    */       }
/*    */     } 
/* 41 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\util\URLTweak.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */