/*     */ package com.eoos.gm.tis2web.registration.server.db;
/*     */ 
/*     */ import com.eoos.gm.tis2web.registration.server.RegistryImpl;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.Dealership;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.Registration;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.Registry;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.RequestStatus;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.RequestType;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.SalesOrganization;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ 
/*     */ public class RegistrationEntity
/*     */   implements Registration
/*     */ {
/*     */   private Registry registry;
/*     */   private int status;
/*  18 */   private int registrationpk = -1;
/*     */   private String requestid;
/*     */   private SalesOrganization organization;
/*     */   private RequestStatus requeststatus;
/*     */   private RequestType requesttype;
/*     */   private Date requestdate;
/*     */   private Dealership dealership;
/*     */   private String subscriberid;
/*     */   private String authorizationid;
/*     */   private List authorizations;
/*     */   private Integer sessions;
/*     */   private String softwarekey;
/*     */   private String licensekey;
/*     */   
/*     */   public RegistrationEntity() {
/*  33 */     this.status = 0;
/*     */   }
/*     */   
/*     */   public RegistrationEntity(Registry registry) {
/*  37 */     this.registry = registry;
/*  38 */     this.status = 1;
/*     */   }
/*     */   
/*     */   public RegistrationEntity(Registry registry, int status) {
/*  42 */     this.registry = registry;
/*  43 */     this.status = status;
/*     */   }
/*     */   
/*     */   public void setStatus(int status) {
/*  47 */     this.status = status;
/*     */   }
/*     */   
/*     */   public void setRequestPK(int registrationpk) {
/*  51 */     this.registrationpk = registrationpk;
/*     */   }
/*     */   
/*     */   public int getRequestPK() {
/*  55 */     return this.registrationpk;
/*     */   }
/*     */   
/*     */   public void setRequestID(String requestID) {
/*  59 */     this.requestid = requestID;
/*     */   }
/*     */   
/*     */   public String getRequestID() {
/*  63 */     return this.requestid;
/*     */   }
/*     */   
/*     */   public void setOrganization(SalesOrganization organization) {
/*  67 */     this.organization = organization;
/*     */   }
/*     */   
/*     */   public SalesOrganization getOrganization() {
/*  71 */     return this.organization;
/*     */   }
/*     */   
/*     */   public void setRequestStatus(RequestStatus requestStatus) {
/*  75 */     this.requeststatus = requestStatus;
/*     */   }
/*     */   
/*     */   public RequestStatus getRequestStatus() {
/*  79 */     return this.requeststatus;
/*     */   }
/*     */   
/*     */   public void setRequestType(RequestType requestType) {
/*  83 */     this.requesttype = requestType;
/*     */   }
/*     */   
/*     */   public RequestType getRequestType() {
/*  87 */     return this.requesttype;
/*     */   }
/*     */   
/*     */   public void setRequestDate(Date requestDate) {
/*  91 */     this.requestdate = requestDate;
/*     */   }
/*     */   
/*     */   public Date getRequestDate() {
/*  95 */     return this.requestdate;
/*     */   }
/*     */   
/*     */   public void setDealership(Dealership dealership) {
/*  99 */     this.dealership = dealership;
/*     */   }
/*     */   
/*     */   public Dealership getDealership() {
/* 103 */     return this.dealership;
/*     */   }
/*     */   
/*     */   public void setSubscriberID(String subscriberID) {
/* 107 */     this.subscriberid = subscriberID;
/*     */   }
/*     */   
/*     */   public String getSubscriberID() {
/* 111 */     checkStatus();
/* 112 */     return this.subscriberid;
/*     */   }
/*     */   
/*     */   public void setSoftwareKey(String softwareKey) {
/* 116 */     this.softwarekey = softwareKey;
/*     */   }
/*     */   
/*     */   public String getSoftwareKey() {
/* 120 */     checkStatus();
/* 121 */     return this.softwarekey;
/*     */   }
/*     */   
/*     */   public void setLicenseKey(String licenseKey) {
/* 125 */     this.licensekey = licenseKey;
/*     */   }
/*     */   
/*     */   public String getLicenseKey() {
/* 129 */     checkStatus();
/* 130 */     return this.licensekey;
/*     */   }
/*     */   
/*     */   public void setAuthorizationID(String authorizationID) {
/* 134 */     this.authorizationid = authorizationID;
/*     */   }
/*     */   
/*     */   public String getAuthorizationID() {
/* 138 */     checkStatus();
/* 139 */     return this.authorizationid;
/*     */   }
/*     */   
/*     */   public void setAuthorizationList(List authorizations) {
/* 143 */     this.authorizations = authorizations;
/*     */   }
/*     */   
/*     */   public List getAuthorizationList() {
/* 147 */     checkStatus();
/* 148 */     return this.authorizations;
/*     */   }
/*     */   
/*     */   private void checkStatus() {
/* 152 */     if (this.status == 1) {
/* 153 */       ((RegistryImpl)this.registry).load(this);
/* 154 */       this.status = 2;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Integer getSessionCount() {
/* 159 */     return this.sessions;
/*     */   }
/*     */   
/*     */   public void setSessionCount(Integer sessions) {
/* 163 */     this.sessions = sessions;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\server\db\RegistrationEntity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */