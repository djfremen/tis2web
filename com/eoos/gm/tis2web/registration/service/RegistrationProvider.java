/*    */ package com.eoos.gm.tis2web.registration.service;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*    */ import com.eoos.gm.tis2web.registration.service.cai.Registry;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class RegistrationProvider
/*    */ {
/* 12 */   private static final Logger log = Logger.getLogger(RegistrationProvider.class);
/*    */ 
/*    */   
/*    */   private static final String IMPL_CLASS = "com.eoos.gm.tis2web.registration.server.RegistryImpl";
/*    */ 
/*    */   
/*    */   private static Registry registration;
/*    */ 
/*    */   
/*    */   public static synchronized RegistrationProvider getInstance() {
/* 22 */     RegistrationProvider instance = (RegistrationProvider)ApplicationContext.getInstance().getObject(RegistrationProvider.class);
/* 23 */     if (instance == null) {
/* 24 */       instance = new RegistrationProvider();
/* 25 */       ApplicationContext.getInstance().storeObject(RegistrationProvider.class, instance);
/*    */     } 
/*    */     
/* 28 */     return instance;
/*    */   }
/*    */   
/*    */   public synchronized Registry getService() {
/* 32 */     if (registration == null) {
/*    */       try {
/* 34 */         Class<?> clazz = Class.forName("com.eoos.gm.tis2web.registration.server.RegistryImpl");
/* 35 */         registration = (Registry)clazz.newInstance();
/* 36 */         DatabaseLink db = DatabaseLink.openDatabase((Configuration)ApplicationContext.getInstance(), "component.registration.server.db");
/* 37 */         registration.init((Configuration)ApplicationContext.getInstance(), db);
/* 38 */       } catch (Exception e) {
/* 39 */         log.error("unable to instantiate RegistrationService - exception:" + e);
/* 40 */         throw new ExceptionWrapper(e);
/*    */       } 
/*    */     }
/*    */     
/* 44 */     return registration;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\service\RegistrationProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */