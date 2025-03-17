/*    */ package com.eoos.gm.tis2web.registration.standalone.authorization.service;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class SoftwareKeyProvider
/*    */ {
/* 10 */   private static final Logger log = Logger.getLogger(SoftwareKeyProvider.class);
/*    */   
/*    */   private static final String IMPL_CLASS = "com.eoos.gm.tis2web.registration.standalone.authorization.SoftwareKeyServiceImpl";
/*    */   
/*    */   private static final String IMPL_CLASS2 = "com.eoos.gm.tis2web.registration.standalone.authorization.SoftwareKeyService_DevelopMode";
/*    */   
/* 16 */   private static SoftwareKeyService service = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized SoftwareKeyProvider getInstance() {
/* 22 */     SoftwareKeyProvider instance = (SoftwareKeyProvider)ApplicationContext.getInstance().getObject(SoftwareKeyProvider.class);
/* 23 */     if (instance == null) {
/* 24 */       instance = new SoftwareKeyProvider();
/* 25 */       ApplicationContext.getInstance().storeObject(SoftwareKeyProvider.class, instance);
/*    */     } 
/*    */     
/* 28 */     return instance;
/*    */   }
/*    */   
/*    */   public synchronized SoftwareKeyService getService() {
/* 32 */     if (service == null) {
/*    */       try {
/* 34 */         Class<?> clazz = null;
/* 35 */         if (ApplicationContext.getInstance().developMode()) {
/* 36 */           clazz = Class.forName("com.eoos.gm.tis2web.registration.standalone.authorization.SoftwareKeyService_DevelopMode");
/*    */         } else {
/* 38 */           clazz = Class.forName("com.eoos.gm.tis2web.registration.standalone.authorization.SoftwareKeyServiceImpl");
/*    */         } 
/* 40 */         service = (SoftwareKeyService)clazz.newInstance();
/* 41 */       } catch (Exception e) {
/* 42 */         log.error("unable to instantiate SoftwareKeyService - exception:" + e);
/* 43 */         throw new ExceptionWrapper(e);
/*    */       } 
/*    */     }
/* 46 */     return service;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\standalone\authorization\service\SoftwareKeyProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */