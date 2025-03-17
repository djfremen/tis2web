/*    */ package com.eoos.util;
/*    */ 
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class CookieHandlerUninstall {
/*  6 */   private static final Logger log = Logger.getLogger(CookieHandlerUninstall.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean uninstallCookieHandler() throws Exception {
/*    */     boolean bool;
/* 14 */     log.debug("trying to uninstall cookie handler ...");
/*    */     try {
/* 16 */       Class<?> clazz = Class.forName("java.net.CookieHandler");
/* 17 */       log.debug("... class \"java.net.CookieHandler\" loaded successfully -> running under JRE 1.5+");
/*    */       
/* 19 */       Object test = clazz.getMethod("getDefault", new Class[0]).invoke(null, new Object[0]);
/* 20 */       if (test != null) {
/* 21 */         log.debug("... the installed default handler is :" + String.valueOf(test) + ", " + String.valueOf(test.getClass()));
/* 22 */         log.debug("... uninstalling default handler");
/* 23 */         clazz.getMethod("setDefault", new Class[] { clazz }).invoke(null, new Object[] { null });
/* 24 */         test = clazz.getMethod("getDefault", new Class[0]).invoke(null, new Object[0]);
/* 25 */         log.debug("... uninstall " + ((test != null) ? "FAILED" : "SUCCESSFULL"));
/* 26 */         bool = (test == null) ? true : false;
/*    */       } else {
/* 28 */         log.debug("... no default handler installed ");
/* 29 */         bool = false;
/*    */       } 
/* 31 */     } catch (ClassNotFoundException t) {
/* 32 */       log.debug("... class \"java.net.CookieHandler\" not found -> running under JRE 1.4-");
/* 33 */       bool = false;
/*    */     } 
/* 35 */     return bool;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoo\\util\CookieHandlerUninstall.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */