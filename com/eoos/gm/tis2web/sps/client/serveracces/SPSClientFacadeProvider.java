/*    */ package com.eoos.gm.tis2web.sps.client.serveracces;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.gm.tis2web.sps.client.system.classloader.ClientClassLoader;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.serveraccess.SPSClientFacade;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SPSClientFacadeProvider
/*    */ {
/*    */   private static final String FACADE_IMPL_CLASS = "com.eoos.gm.tis2web.sps.server.implementation.system.clientside.SPSClientFacadeImpl";
/* 13 */   private static SPSClientFacadeProvider instance = null;
/*    */   
/* 15 */   private SPSClientFacade facade = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized SPSClientFacadeProvider getInstance() {
/* 22 */     if (instance == null) {
/* 23 */       instance = new SPSClientFacadeProvider();
/*    */     }
/* 25 */     return instance;
/*    */   }
/*    */   
/*    */   public synchronized SPSClientFacade getFacade() {
/* 29 */     if (this.facade == null) {
/*    */       try {
/* 31 */         ClientClassLoader clientClassLoader; ClassLoader ccl = getClass().getClassLoader();
/* 32 */         if (!(ccl instanceof ClientClassLoader)) {
/* 33 */           clientClassLoader = new ClientClassLoader(ccl);
/*    */         }
/* 35 */         Class<?> clazz = clientClassLoader.loadClass("com.eoos.gm.tis2web.sps.server.implementation.system.clientside.SPSClientFacadeImpl");
/* 36 */         this.facade = (SPSClientFacade)clazz.newInstance();
/* 37 */       } catch (Exception e) {
/* 38 */         throw new ExceptionWrapper(e);
/*    */       } 
/*    */     }
/*    */     
/* 42 */     return this.facade;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\serveracces\SPSClientFacadeProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */