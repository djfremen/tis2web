/*    */ package com.eoos.gm.tis2web.dtc.service.cai;
/*    */ 
/*    */ import com.eoos.gm.tis2web.dtc.service.DTCStorageService;
/*    */ import com.eoos.gm.tis2web.dtc.service.DTCStorageServiceProvider;
/*    */ import com.eoos.gm.tis2web.frame.dls.server.DatabaseAdapterProvider;
/*    */ import com.eoos.gm.tis2web.frame.dls.server.LeaseValidationProvider;
/*    */ import com.eoos.util.Task;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DTCStorageTask_ServerPart
/*    */   implements Task
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private final DTCStorageTask clientPart;
/*    */   
/*    */   public DTCStorageTask_ServerPart(DTCStorageTask clientPart) {
/* 18 */     this.clientPart = clientPart;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object execute() {
/*    */     try {
/* 24 */       if (LeaseValidationProvider.getLeaseValidation().validateLease(this.clientPart.swk, this.clientPart.lease)) {
/* 25 */         DTCStorageService service = DTCStorageServiceProvider.getInstance().getService();
/* 26 */         return new Boolean(service.store(this.clientPart.dtcs, new DTCStorageService.Callback()
/*    */               {
/* 28 */                 private final String NOTINITIALZED = new String();
/*    */                 
/* 30 */                 private String country = this.NOTINITIALZED;
/*    */                 
/* 32 */                 private String bac = this.NOTINITIALZED;
/*    */                 
/*    */                 public synchronized String getCountry() {
/* 35 */                   if (this.country == this.NOTINITIALZED) {
/* 36 */                     this.country = DatabaseAdapterProvider.getDatabaseAdapter().getCountryCode(DTCStorageTask_ServerPart.this.clientPart.swk, DTCStorageTask_ServerPart.this.clientPart.lease);
/*    */                   }
/* 38 */                   return this.country;
/*    */                 }
/*    */                 
/*    */                 public synchronized String getBAC() {
/* 42 */                   if (this.bac == this.NOTINITIALZED) {
/* 43 */                     this.bac = DatabaseAdapterProvider.getDatabaseAdapter().getBAC(DTCStorageTask_ServerPart.this.clientPart.swk, DTCStorageTask_ServerPart.this.clientPart.lease);
/*    */                   }
/* 45 */                   return this.bac;
/*    */                 }
/*    */               }));
/*    */       } 
/* 49 */       return Boolean.FALSE;
/*    */     }
/* 51 */     catch (Exception e) {
/* 52 */       return e;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\dtc\service\cai\DTCStorageTask_ServerPart.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */