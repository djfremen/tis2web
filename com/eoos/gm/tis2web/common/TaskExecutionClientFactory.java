/*    */ package com.eoos.gm.tis2web.common;
/*    */ 
/*    */ import com.eoos.propcfg.util.SystemPropertiesAdapter;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import com.eoos.util.CookieHandlerUninstall;
/*    */ import com.eoos.util.v2.Base64EncodingUtil;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URI;
/*    */ import java.net.URISyntaxException;
/*    */ import java.net.URL;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TaskExecutionClientFactory
/*    */ {
/* 18 */   private static final Logger log = Logger.getLogger(TaskExecutionClientFactory.class);
/*    */   
/*    */   public static TaskExecutionClient createTaskExecutionClient() {
/*    */     try {
/* 22 */       SystemPropertiesAdapter systemPropertiesAdapter = SystemPropertiesAdapter.getInstance();
/* 23 */       URL url = new URL(systemPropertiesAdapter.getProperty("task.execution.url"));
/* 24 */       TaskExecutionClient tec = TaskExecutionClient.getInstance(url);
/*    */       
/*    */       try {
/* 27 */         CookieHandlerUninstall.uninstallCookieHandler();
/* 28 */       } catch (Exception e) {
/* 29 */         log.error("unable to uninstall cookie handler - exception:" + e, e);
/*    */       } 
/*    */ 
/*    */       
/* 33 */       String cookie = getCookie();
/*    */       
/* 35 */       if (!Util.isNullOrEmpty(cookie)) {
/*    */         try {
/* 37 */           HttpSettings.getInstance(new URI(url.toString())).setCookie(cookie);
/* 38 */         } catch (URISyntaxException e) {
/* 39 */           throw new MalformedURLException("URL not conforming RFC2396");
/*    */         } 
/*    */       }
/* 42 */       return tec;
/* 43 */     } catch (MalformedURLException e) {
/* 44 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private static String getCookie() {
/* 50 */     String cookie = System.getProperty("cookie");
/*    */     try {
/* 52 */       cookie = new String(Base64EncodingUtil.decode(cookie), "utf-8");
/* 53 */     } catch (UnsupportedEncodingException e1) {
/* 54 */       throw new RuntimeException(e1);
/* 55 */     } catch (NullPointerException e) {
/* 56 */       cookie = null;
/*    */     } 
/*    */     
/* 59 */     return cookie;
/*    */   }
/*    */   
/*    */   public static TaskExecutionClient createTaskExecutionClient(URL url) {
/*    */     try {
/* 64 */       TaskExecutionClient tec = TaskExecutionClient.getInstance(url);
/*    */       
/*    */       try {
/* 67 */         CookieHandlerUninstall.uninstallCookieHandler();
/* 68 */       } catch (Exception e) {
/* 69 */         log.error("unable to uninstall cookie handler - exception:" + e, e);
/*    */       } 
/*    */ 
/*    */       
/* 73 */       String cookie = getCookie();
/* 74 */       if (!Util.isNullOrEmpty(cookie)) {
/*    */         try {
/* 76 */           HttpSettings.getInstance(new URI(url.toString())).setCookie(cookie);
/* 77 */         } catch (URISyntaxException e) {
/* 78 */           throw new MalformedURLException("URL not conforming RFC2396");
/*    */         } 
/*    */       }
/* 81 */       return tec;
/* 82 */     } catch (MalformedURLException e) {
/* 83 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static URL getServerURL() {
/*    */     try {
/* 90 */       SystemPropertiesAdapter systemPropertiesAdapter = SystemPropertiesAdapter.getInstance();
/* 91 */       String tmp = systemPropertiesAdapter.getProperty("task.execution.url");
/* 92 */       if (!Util.isNullOrEmpty(tmp)) {
/* 93 */         return new URL(tmp);
/*    */       }
/* 95 */       return null;
/*    */     }
/* 97 */     catch (MalformedURLException e) {
/* 98 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\common\TaskExecutionClientFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */