/*    */ package com.eoos.gm.tis2web.news.service;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NewsServiceProvider
/*    */ {
/* 12 */   private static final Logger log = Logger.getLogger(NewsServiceProvider.class);
/*    */   
/*    */   private static final String IMPL_CLASS = "com.eoos.gm.tis2web.news.implementation.service.NewsServiceImpl";
/* 15 */   private NewsService service = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized NewsServiceProvider getInstance() {
/* 22 */     NewsServiceProvider instance = (NewsServiceProvider)ApplicationContext.getInstance().getObject(NewsServiceProvider.class);
/* 23 */     if (instance == null) {
/* 24 */       instance = new NewsServiceProvider();
/* 25 */       ApplicationContext.getInstance().storeObject(NewsServiceProvider.class, instance);
/*    */     } 
/*    */     
/* 28 */     return instance;
/*    */   }
/*    */   
/*    */   public synchronized NewsService getService() {
/* 32 */     if (this.service == null) {
/*    */       try {
/* 34 */         Class<?> clazz = Class.forName("com.eoos.gm.tis2web.news.implementation.service.NewsServiceImpl");
/*    */         
/* 36 */         this.service = (NewsService)clazz.newInstance();
/* 37 */       } catch (Exception e) {
/* 38 */         log.error("unable to instantiate NewsService - exception:" + e);
/* 39 */         throw new ExceptionWrapper(e);
/*    */       } 
/*    */     }
/*    */     
/* 43 */     return this.service;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\news\service\NewsServiceProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */