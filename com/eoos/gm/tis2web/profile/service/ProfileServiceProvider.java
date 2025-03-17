/*    */ package com.eoos.gm.tis2web.profile.service;
/*    */ 
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProfileServiceProvider
/*    */ {
/*  9 */   private static final Logger log = Logger.getLogger(ProfileServiceProvider.class);
/*    */   
/*    */   private static final String IMPL_CLASS = "com.eoos.gm.tis2web.profile.implementation.service.ProfileServiceImpl";
/*    */   
/* 13 */   private static ProfileServiceProvider instance = null;
/*    */   
/* 15 */   private ProfileService service = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized ProfileServiceProvider getInstance() {
/* 23 */     if (instance == null) {
/* 24 */       instance = new ProfileServiceProvider();
/*    */     }
/* 26 */     return instance;
/*    */   }
/*    */   
/*    */   public synchronized ProfileService getService() {
/* 30 */     if (this.service == null) {
/*    */       try {
/* 32 */         Class<?> c = Class.forName("com.eoos.gm.tis2web.profile.implementation.service.ProfileServiceImpl");
/* 33 */         this.service = (ProfileService)c.newInstance();
/* 34 */       } catch (Exception e) {
/* 35 */         log.error("unable to instantiate ProfileService (IMPL:com.eoos.gm.tis2web.profile.implementation.service.ProfileServiceImpl) - exception:" + e, e);
/*    */       } 
/*    */     }
/* 38 */     return this.service;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\profile\service\ProfileServiceProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */