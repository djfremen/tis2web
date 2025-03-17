/*    */ package com.eoos.gm.tis2web.acl.service;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.gm.tis2web.acl.implementation.service.ACLServiceImpl;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ACLServiceProvider
/*    */ {
/* 13 */   private static final Logger log = Logger.getLogger(ACLServiceProvider.class);
/*    */   
/* 15 */   private ACLService service = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized ACLServiceProvider getInstance() {
/* 22 */     ACLServiceProvider instance = (ACLServiceProvider)ApplicationContext.getInstance().getObject(ACLServiceProvider.class);
/* 23 */     if (instance == null) {
/* 24 */       instance = new ACLServiceProvider();
/* 25 */       ApplicationContext.getInstance().storeObject(ACLServiceProvider.class, instance);
/*    */     } 
/*    */     
/* 28 */     return instance;
/*    */   }
/*    */   
/*    */   public synchronized ACLService getService() {
/* 32 */     if (this.service == null) {
/*    */       
/*    */       try {
/* 35 */         this.service = (ACLService)new ACLServiceImpl();
/* 36 */         this.service = new ACLService_ResultLogFacade(this.service);
/*    */       }
/* 38 */       catch (Exception e) {
/* 39 */         log.error("unable to instantiate ACLService - exception:" + e);
/* 40 */         throw new ExceptionWrapper(e);
/*    */       } 
/*    */     }
/*    */     
/* 44 */     return this.service;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\acl\service\ACLServiceProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */