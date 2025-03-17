/*    */ package com.eoos.gm.tis2web.sids.service;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ServiceIDServiceProvider
/*    */ {
/* 12 */   private static final Logger log = Logger.getLogger(ServiceIDServiceProvider.class);
/*    */   
/* 14 */   private ServiceIDService service = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized ServiceIDServiceProvider getInstance() {
/* 21 */     ServiceIDServiceProvider instance = (ServiceIDServiceProvider)ApplicationContext.getInstance().getObject(ServiceIDServiceProvider.class);
/* 22 */     if (instance == null) {
/* 23 */       instance = new ServiceIDServiceProvider();
/* 24 */       ApplicationContext.getInstance().storeObject(ServiceIDServiceProvider.class, instance);
/*    */     } 
/*    */     
/* 27 */     return instance;
/*    */   }
/*    */   
/*    */   public synchronized ServiceIDService getService() {
/* 31 */     if (this.service == null) {
/*    */       try {
/* 33 */         Class<?> clazz = Class.forName("com.eoos.gm.tis2web.sids.implementation.ServiceIDServiceImpl");
/*    */         
/* 35 */         this.service = (ServiceIDService)clazz.newInstance();
/* 36 */       } catch (Exception e) {
/* 37 */         log.error("unable to instantiate ServiceIDService - exception:" + e);
/* 38 */         throw new ExceptionWrapper(e);
/*    */       } 
/*    */     }
/*    */     
/* 42 */     return this.service;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sids\service\ServiceIDServiceProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */