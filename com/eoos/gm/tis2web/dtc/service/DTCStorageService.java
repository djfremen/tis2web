/*    */ package com.eoos.gm.tis2web.dtc.service;
/*    */ public interface DTCStorageService extends Service {
/*    */   boolean store(DTC paramDTC) throws DTCStorageServiceException;
/*    */   
/*    */   boolean store(Collection paramCollection) throws DTCStorageServiceException;
/*    */   
/*    */   boolean store(Collection paramCollection, Callback paramCallback) throws DTCStorageServiceException;
/*    */   
/*    */   void init() throws DTCStorageServiceException;
/*    */   
/*    */   public static interface Callback { String getBAC();
/*    */     
/*    */     String getCountry(); }
/*    */   
/*    */   public static class DTCStorageServiceException extends Exception {
/*    */     public DTCStorageServiceException(Throwable cause) {
/* 17 */       super(cause);
/*    */     }
/*    */     
/*    */     private static final long serialVersionUID = 1L;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\dtc\service\DTCStorageService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */