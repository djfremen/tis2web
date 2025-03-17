/*    */ package com.eoos.gm.tis2web.frame.diag.client;
/*    */ 
/*    */ import java.util.Properties;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class SystemInfoProvider {
/*  7 */   private static Logger log = Logger.getLogger(SystemInfoProvider.class);
/*    */ 
/*    */ 
/*    */   
/* 11 */   private static SystemInfoProvider instance = null;
/* 12 */   private static String LIBRARY_NAME = "clientdiag";
/*    */   
/*    */   public static SystemInfoProvider getInstance() {
/* 15 */     synchronized (SystemInfoProvider.class) {
/* 16 */       if (SystemInfoProvider.instance == null) {
/*    */         try {
/* 18 */           System.loadLibrary(LIBRARY_NAME);
/* 19 */           SystemInfoProvider instance = new SystemInfoProvider();
/* 20 */           if (instance != null) {
/* 21 */             SystemInfoProvider.instance = instance;
/*    */           } else {
/* 23 */             log.error("Could not create system information provider");
/*    */           } 
/* 25 */         } catch (Exception e) {
/* 26 */           log.error("Could not load diagnostic library. " + e);
/*    */         } 
/*    */       }
/* 29 */       return SystemInfoProvider.instance;
/*    */     } 
/*    */   }
/*    */   private native Properties nativeGetSystemInfo();
/*    */   Properties getSystemInfo() {
/* 34 */     return nativeGetSystemInfo();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\diag\client\SystemInfoProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */