/*    */ package com.eoos.gm.tis2web.frame.export;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.declaration.service.FallbackUIService;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FallbackUIServiceProvider
/*    */ {
/* 13 */   private static final Logger log = Logger.getLogger(FallbackUIServiceProvider.class);
/*    */   
/* 15 */   private FallbackUIService service = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized FallbackUIServiceProvider getInstance() {
/* 22 */     FallbackUIServiceProvider instance = (FallbackUIServiceProvider)ApplicationContext.getInstance().getObject(FallbackUIServiceProvider.class);
/* 23 */     if (instance == null) {
/* 24 */       instance = new FallbackUIServiceProvider();
/* 25 */       ApplicationContext.getInstance().storeObject(FallbackUIServiceProvider.class, instance);
/*    */     } 
/*    */     
/* 28 */     return instance;
/*    */   }
/*    */   
/*    */   public synchronized FallbackUIService getService() {
/* 32 */     if (this.service == null) {
/*    */       try {
/* 34 */         Class<?> clazz = Class.forName("com.eoos.gm.tis2web.frame.implementation.service.FallbackUIServiceImpl");
/*    */         
/* 36 */         this.service = (FallbackUIService)clazz.newInstance();
/* 37 */       } catch (Exception e) {
/* 38 */         log.error("unable to instantiate FallbackUIService - exception:" + e);
/* 39 */         throw new ExceptionWrapper(e);
/*    */       } 
/*    */     }
/*    */     
/* 43 */     return this.service;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\FallbackUIServiceProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */