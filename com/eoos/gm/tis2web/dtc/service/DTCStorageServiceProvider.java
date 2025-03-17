/*    */ package com.eoos.gm.tis2web.dtc.service;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DTCStorageServiceProvider
/*    */ {
/* 11 */   private static final Logger log = Logger.getLogger(DTCStorageServiceProvider.class);
/*    */   
/*    */   private static final String IMPL_CLASS = "com.eoos.gm.tis2web.dtc.implementation.DTCStorageServiceImpl";
/*    */   
/* 15 */   private static DTCStorageServiceProvider instance = null;
/*    */   
/* 17 */   private DTCStorageService service = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized DTCStorageServiceProvider getInstance() {
/* 24 */     if (instance == null) {
/* 25 */       instance = new DTCStorageServiceProvider();
/*    */     }
/* 27 */     return instance;
/*    */   }
/*    */   
/*    */   public synchronized DTCStorageService getService() {
/* 31 */     if (this.service == null) {
/*    */       try {
/* 33 */         Class<?> clazz = Class.forName("com.eoos.gm.tis2web.dtc.implementation.DTCStorageServiceImpl");
/*    */         
/* 35 */         this.service = (DTCStorageService)clazz.newInstance();
/* 36 */       } catch (Exception e) {
/* 37 */         log.error("unable to instantiate DTCStorageService - exception:" + e);
/* 38 */         throw new ExceptionWrapper(e);
/*    */       } 
/*    */     }
/*    */     
/* 42 */     return this.service;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\dtc\service\DTCStorageServiceProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */