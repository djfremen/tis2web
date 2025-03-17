/*    */ package com.eoos.gm.tis2web.dtc.implementation;
/*    */ 
/*    */ import com.eoos.gm.tis2web.dtc.service.DTCStorageService;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import java.util.Collection;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class DTCLogFacade
/*    */   implements DTCLog
/*    */ {
/* 12 */   private static final Logger log = Logger.getLogger(DTCLogFacade.class);
/*    */   
/* 14 */   private DTCLogImpl delegate = null;
/*    */   
/*    */   private DTCLogFacade() {
/*    */     try {
/* 18 */       this.delegate = new DTCLogImpl();
/* 19 */     } catch (Exception e) {
/* 20 */       log.error("unable to init dtc log, disabling dtc logging (callers will receive UnsupportedOperationException) - exception:" + e, e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static synchronized DTCLogFacade getInstance() {
/* 25 */     ApplicationContext applicationContext = ApplicationContext.getInstance();
/* 26 */     DTCLogFacade instance = (DTCLogFacade)applicationContext.getObject(DTCLogFacade.class);
/* 27 */     if (instance == null) {
/* 28 */       instance = new DTCLogFacade();
/* 29 */       applicationContext.storeObject(DTCLogFacade.class, instance);
/*    */     } 
/* 31 */     return instance;
/*    */   }
/*    */   
/*    */   public boolean isAvailable() {
/* 35 */     return (this.delegate != null);
/*    */   }
/*    */   
/*    */   public void add(Collection dtcs, DTCStorageService.Callback callback) throws Exception {
/* 39 */     if (this.delegate != null) {
/* 40 */       this.delegate.add(dtcs, callback);
/*    */     } else {
/* 42 */       throw new UnsupportedOperationException("dtc log is disabled");
/*    */     } 
/*    */   }
/*    */   
/*    */   public Collection getEntries(DTCLog.BackendFilter backendFilter, int hitLimit) throws Exception {
/* 47 */     if (this.delegate != null) {
/* 48 */       return this.delegate.getEntries(backendFilter, hitLimit);
/*    */     }
/* 50 */     throw new UnsupportedOperationException("dtc log is disabled");
/*    */   }
/*    */ 
/*    */   
/*    */   public void delete(Collection loggedDTCs) throws Exception {
/* 55 */     if (this.delegate != null) {
/* 56 */       this.delegate.delete(loggedDTCs);
/*    */     } else {
/* 58 */       throw new UnsupportedOperationException("dtc log is disabled");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\dtc\implementation\DTCLogFacade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */