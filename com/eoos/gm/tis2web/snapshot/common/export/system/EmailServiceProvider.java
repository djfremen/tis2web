/*    */ package com.eoos.gm.tis2web.snapshot.common.export.system;
/*    */ 
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EmailServiceProvider
/*    */ {
/* 10 */   private static final Logger log = Logger.getLogger(EmailServiceProvider.class);
/*    */   
/*    */   private static final String IMPL = "com.eoos.gm.tis2web.snapshot.server.implementation.service.EmailServiceImpl";
/*    */   
/* 14 */   private static EmailServiceProvider instance = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized EmailServiceProvider getInstance() {
/* 20 */     if (instance == null) {
/* 21 */       instance = new EmailServiceProvider();
/*    */     }
/* 23 */     return instance;
/*    */   }
/*    */   
/*    */   public synchronized EmailService getEmailService() throws Exception {
/* 27 */     EmailService instance = null;
/*    */     try {
/* 29 */       ClassLoader cl = getClass().getClassLoader();
/* 30 */       instance = (EmailService)cl.loadClass("com.eoos.gm.tis2web.snapshot.server.implementation.service.EmailServiceImpl").getMethod("getInstance", (Class[])null).invoke(null, (Object[])null);
/*    */     }
/* 32 */     catch (Exception e) {
/* 33 */       log.error("unable to instantiate EmailServiceImpl instance - exception:" + e, e);
/* 34 */       throw e;
/*    */     } 
/* 36 */     return instance;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\snapshot\common\export\system\EmailServiceProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */