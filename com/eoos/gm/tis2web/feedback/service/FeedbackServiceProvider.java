/*    */ package com.eoos.gm.tis2web.feedback.service;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FeedbackServiceProvider
/*    */ {
/* 12 */   private static final Logger log = Logger.getLogger(FeedbackServiceProvider.class);
/*    */   
/*    */   private static final String IMPL_CLASS = "com.eoos.gm.tis2web.feedback.implementation.service.FeedbackServiceImpl";
/*    */   
/* 16 */   private FeedbackService service = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized FeedbackServiceProvider getInstance() {
/* 23 */     FeedbackServiceProvider instance = (FeedbackServiceProvider)ApplicationContext.getInstance().getObject(FeedbackServiceProvider.class);
/* 24 */     if (instance == null) {
/* 25 */       instance = new FeedbackServiceProvider();
/* 26 */       ApplicationContext.getInstance().storeObject(FeedbackServiceProvider.class, instance);
/*    */     } 
/*    */     
/* 29 */     return instance;
/*    */   }
/*    */   
/*    */   public synchronized FeedbackService getService() {
/* 33 */     if (this.service == null) {
/*    */       try {
/* 35 */         Class<?> clazz = Class.forName("com.eoos.gm.tis2web.feedback.implementation.service.FeedbackServiceImpl");
/*    */         
/* 37 */         this.service = (FeedbackService)clazz.newInstance();
/* 38 */       } catch (Exception e) {
/* 39 */         log.error("unable to instantiate FeedbackService - exception:" + e);
/* 40 */         throw new ExceptionWrapper(e);
/*    */       } 
/*    */     }
/*    */     
/* 44 */     return this.service;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\feedback\service\FeedbackServiceProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */