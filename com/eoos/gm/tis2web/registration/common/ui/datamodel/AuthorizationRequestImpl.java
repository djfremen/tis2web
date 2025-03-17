/*    */ package com.eoos.gm.tis2web.registration.common.ui.datamodel;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.adapter.SubscriptionAdapter;
/*    */ import com.eoos.gm.tis2web.registration.server.db.AuthorizationData;
/*    */ import com.eoos.gm.tis2web.registration.service.cai.Registration;
/*    */ import com.eoos.gm.tis2web.registration.service.cai.RequestStatus;
/*    */ import com.eoos.gm.tis2web.registration.service.cai.RequestType;
/*    */ import com.eoos.gm.tis2web.registration.service.cai.Subscription;
/*    */ import com.eoos.gm.tis2web.registration.standalone.authorization.DealershipAdapter;
/*    */ 
/*    */ public class AuthorizationRequestImpl
/*    */   implements AuthorizationRequest {
/*    */   private Registration registration;
/*    */   private DealershipInfo dealership;
/*    */   
/*    */   public AuthorizationRequestImpl(ClientContext context, Registration registration) {
/* 18 */     this.registration = registration;
/* 19 */     if (registration.getDealership() != null) {
/* 20 */       this.dealership = (DealershipInfo)new DealershipAdapter(context, registration.getDealership());
/*    */     }
/*    */   }
/*    */   
/*    */   public DealershipInfo getDealershipInfo() {
/* 25 */     return this.dealership;
/*    */   }
/*    */   
/*    */   public long getRequestDate() {
/* 29 */     return this.registration.getRequestDate().getTime();
/*    */   }
/*    */   
/*    */   public String getRequestID() {
/* 33 */     return this.registration.getRequestID();
/*    */   }
/*    */   
/*    */   public SoftwareKey getSoftwareKey() {
/* 37 */     if (this.registration.getSoftwareKey() != null) {
/* 38 */       return SoftwareKeyFactory.createSoftwareKey(this.registration.getSoftwareKey());
/*    */     }
/* 40 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public AuthorizationRequest.Status getStatus() {
/* 45 */     if (this.registration.getRequestStatus() == RequestStatus.PENDING) {
/* 46 */       return STATUS_PENDING;
/*    */     }
/* 48 */     return STATUS_AUTHORIZED;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSubscriberID() {
/* 53 */     return this.registration.getSubscriberID();
/*    */   }
/*    */   
/*    */   public Subscription getSubscription() {
/* 57 */     if (this.registration.getAuthorizationID() == null) {
/* 58 */       return null;
/*    */     }
/* 60 */     return (Subscription)new SubscriptionAdapter((Subscription)AuthorizationData.getAuthorization(this.registration.getAuthorizationID()));
/*    */   }
/*    */   
/*    */   public Registration getRegistration() {
/* 64 */     return this.registration;
/*    */   }
/*    */   
/*    */   public RequestType getType() {
/* 68 */     return this.registration.getRequestType();
/*    */   }
/*    */   
/*    */   public Integer getSessions() {
/* 72 */     return this.registration.getSessionCount();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\commo\\ui\datamodel\AuthorizationRequestImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */