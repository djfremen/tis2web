/*    */ package com.eoos.gm.tis2web.dtc.implementation;
/*    */ 
/*    */ import com.eoos.gm.tis2web.dtc.service.DTCStorageService;
/*    */ import com.eoos.gm.tis2web.dtc.service.cai.DTC;
/*    */ import java.util.Collection;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DTCStorageServiceImpl
/*    */   implements DTCStorageService
/*    */ {
/*    */   public boolean store(DTC dtc) throws DTCStorageService.DTCStorageServiceException {
/* 20 */     Set<DTC> dtcs = new HashSet();
/* 21 */     dtcs.add(dtc);
/* 22 */     return store(dtcs);
/*    */   }
/*    */   
/*    */   public Object getIdentifier() {
/* 26 */     return null;
/*    */   }
/*    */   
/*    */   public boolean store(Collection dtcs) throws DTCStorageService.DTCStorageServiceException {
/* 30 */     return store(dtcs, null);
/*    */   }
/*    */ 
/*    */   
/*    */   public void init() throws DTCStorageService.DTCStorageServiceException {}
/*    */   
/*    */   public boolean store(Collection dtcs, DTCStorageService.Callback callback) throws DTCStorageService.DTCStorageServiceException {
/*    */     try {
/* 38 */       DTCLogFacade.getInstance().add(dtcs, callback);
/* 39 */       return true;
/* 40 */     } catch (Exception e) {
/* 41 */       throw new DTCStorageService.DTCStorageServiceException(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\dtc\implementation\DTCStorageServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */