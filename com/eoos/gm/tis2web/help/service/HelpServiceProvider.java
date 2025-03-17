/*    */ package com.eoos.gm.tis2web.help.service;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HelpServiceProvider
/*    */ {
/* 12 */   private static final Logger log = Logger.getLogger(HelpServiceProvider.class);
/*    */   
/*    */   private static final String IMPL_CLASS = "com.eoos.gm.tis2web.help.implementation.service.HelpServiceImpl";
/* 15 */   private HelpService service = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized HelpServiceProvider getInstance() {
/* 22 */     HelpServiceProvider instance = (HelpServiceProvider)ApplicationContext.getInstance().getObject(HelpServiceProvider.class);
/* 23 */     if (instance == null) {
/* 24 */       instance = new HelpServiceProvider();
/* 25 */       ApplicationContext.getInstance().storeObject(HelpServiceProvider.class, instance);
/*    */     } 
/*    */     
/* 28 */     return instance;
/*    */   }
/*    */   
/*    */   public synchronized HelpService getService() {
/* 32 */     if (this.service == null) {
/*    */       try {
/* 34 */         Class<?> clazz = Class.forName("com.eoos.gm.tis2web.help.implementation.service.HelpServiceImpl");
/*    */         
/* 36 */         this.service = (HelpService)clazz.newInstance();
/* 37 */       } catch (Exception e) {
/* 38 */         log.error("unable to instantiate HelpService - exception:" + e);
/* 39 */         throw new ExceptionWrapper(e);
/*    */       } 
/*    */     }
/*    */     
/* 43 */     return this.service;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\help\service\HelpServiceProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */