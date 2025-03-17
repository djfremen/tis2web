/*    */ package com.eoos.gm.tis2web.registration.standalone.authorization;
/*    */ 
/*    */ import com.eoos.gm.tis2web.registration.service.cai.Dealership;
/*    */ import com.eoos.gm.tis2web.registration.standalone.authorization.service.SoftwareKeyService;
/*    */ import com.eoos.gm.tis2web.registration.standalone.authorization.service.cai.Authorization;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import java.io.IOException;
/*    */ import java.util.Collection;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ public class SoftwareKeyService_DevelopMode
/*    */   implements SoftwareKeyService
/*    */ {
/*    */   public void acquireSoftwareKey(Configuration configuration) {}
/*    */   
/*    */   public byte[] createManualRegistrationForm(Collection authorizations, Integer licensedSessions, SoftwareKeyService.Output output) {
/* 18 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void extendAuthorization(Set authorizations) {}
/*    */   
/*    */   public Collection getAuthorizations() {
/* 25 */     return null;
/*    */   }
/*    */   
/*    */   public Dealership getDealershipInformation() {
/* 29 */     return null;
/*    */   }
/*    */   
/*    */   public Authorization getDefaultSubscription() {
/* 33 */     return null;
/*    */   }
/*    */   
/*    */   public String getGroupACL() {
/* 37 */     return null;
/*    */   }
/*    */   
/*    */   public String getHardwareID() {
/* 41 */     return null;
/*    */   }
/*    */   
/*    */   public int getMaxSessionCount() {
/* 45 */     return 2;
/*    */   }
/*    */   
/*    */   public String getSoftwareKey() {
/* 49 */     return null;
/*    */   }
/*    */   
/*    */   public String getSubscriberID() {
/* 53 */     return null;
/*    */   }
/*    */   
/*    */   public Authorization getSubscription() {
/* 57 */     return null;
/*    */   }
/*    */   
/*    */   public boolean hasMigratedHardwareKeyAuthorization() {
/* 61 */     return false;
/*    */   }
/*    */   
/*    */   public boolean hasValidAuthorization() {
/* 65 */     return false;
/*    */   }
/*    */   
/*    */   public boolean isRepeatRegistration(Set authorizations) {
/* 69 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setDealershipInformation(Dealership dealership) throws IOException {}
/*    */ 
/*    */   
/*    */   public void updateLicense(String subscriberID, String licenseKey) {}
/*    */   
/*    */   public int getLicensedSessionCount() {
/* 79 */     return 0;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\standalone\authorization\SoftwareKeyService_DevelopMode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */