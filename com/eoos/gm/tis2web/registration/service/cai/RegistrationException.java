/*    */ package com.eoos.gm.tis2web.registration.service.cai;
/*    */ 
/*    */ public class RegistrationException
/*    */   extends RuntimeException {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final String DUPLICATE_REGISTRATION_REQUEST = "registration.duplicate";
/*    */   public static final String REPEAT_REGISTRATION_REQUEST = "registration.repeat";
/*    */   public static final String FAILED_TO_LOAD_REGISTRATION_DATA = "registration.db.load.failed";
/*    */   public static final String FAILED_TO_STORE_REGISTRATION_DATA = "registration.db.store.failed";
/*    */   public static final String FAILED_TO_UPDATE_REGISTRATION_DATA = "registration.db.update.failed";
/*    */   public static final String FAILED_TO_DELETE_REGISTRATION_DATA = "registration.db.delete.failed";
/*    */   public static final String FAILED_TO_LOAD_DEALERSHIP_DATA = "registration.db.load.dealership.failed";
/*    */   public static final String FAILED_TO_STORE_DEALERSHIP_DATA = "registration.db.store.dealership.failed";
/*    */   public static final String FAILED_TO_UPDATE_DEALERSHIP_DATA = "registration.db.update.dealership.failed";
/*    */   public static final String FAILED_TO_DELETE_DEALERSHIP_DATA = "registration.db.delete.dealership.failed";
/*    */   public static final String FAILED_TO_LOAD_AUTHORIZATION_DATA = "registration.db.load.authorizations.failed";
/*    */   
/*    */   public RegistrationException(String which) {
/* 19 */     super(which);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\service\cai\RegistrationException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */