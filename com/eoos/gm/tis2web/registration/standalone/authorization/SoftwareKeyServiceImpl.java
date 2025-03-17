/*     */ package com.eoos.gm.tis2web.registration.standalone.authorization;
/*     */ 
/*     */ import com.eoos.gm.tis2web.registration.common.RegistrationForm;
/*     */ import com.eoos.gm.tis2web.registration.common.SoftwareKey;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.adapter.SubscriptionAdapter;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.AuthorizationStatus;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.Dealership;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.InstallationType;
/*     */ import com.eoos.gm.tis2web.registration.standalone.authorization.service.SoftwareKeyService;
/*     */ import com.eoos.gm.tis2web.registration.standalone.authorization.service.cai.Authorization;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.text.DateFormat;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ public class SoftwareKeyServiceImpl
/*     */   implements SoftwareKeyService
/*     */ {
/*     */   private ConfigurationMediator configuration;
/*  26 */   private SoftwareKeyController controller = null;
/*     */   
/*  28 */   private DealershipInformation dealership = null;
/*     */   
/*     */   public void acquireSoftwareKey(Configuration configuration) {
/*  31 */     if (this.configuration == null) {
/*  32 */       this.configuration = new ConfigurationMediator(configuration);
/*  33 */       this.dealership = DealershipInformation.load(new File(this.configuration.getDealershipFile()));
/*  34 */       this.controller = new SoftwareKeyController(configuration);
/*  35 */       this.controller.constructSoftwareKey();
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getGroupACL() {
/*  40 */     if (this.controller == null || this.controller.getSoftwareKey() == null) {
/*  41 */       return null;
/*     */     }
/*  43 */     SoftwareKey swk = this.controller.getSoftwareKey();
/*  44 */     return !hasValidAuthorization(swk) ? null : AuthorizationImpl.lookup(swk.getAuthorizationID()).getGroup();
/*     */   }
/*     */   
/*     */   public String getHardwareID() {
/*  48 */     if (this.controller == null || this.controller.getSoftwareKey() == null) {
/*  49 */       return null;
/*     */     }
/*  51 */     SoftwareKey swk = this.controller.getSoftwareKey();
/*  52 */     return swk.getHardwareHashID();
/*     */   }
/*     */   
/*     */   protected boolean hasValidAuthorization(SoftwareKey swk) {
/*  56 */     return (swk.getAuthorizationID() != null && swk.getAuthorizationStatus() != AuthorizationStatus.TEMPORARY);
/*     */   }
/*     */   
/*     */   public boolean hasValidAuthorization() {
/*  60 */     if (this.controller == null || this.controller.getSoftwareKey() == null) {
/*  61 */       return false;
/*     */     }
/*  63 */     return hasValidAuthorization(this.controller.getSoftwareKey());
/*     */   }
/*     */   
/*     */   public boolean hasMigratedHardwareKeyAuthorization() {
/*  67 */     if (this.controller == null || this.controller.getSoftwareKey() == null) {
/*  68 */       return false;
/*     */     }
/*  70 */     return (this.controller.getSoftwareKey().getAuthorizationStatus() == AuthorizationStatus.MIGRATED);
/*     */   }
/*     */   
/*     */   public int getMaxSessionCount() {
/*  74 */     if (this.controller == null || this.controller.getSoftwareKey() == null) {
/*  75 */       return 0;
/*     */     }
/*  77 */     SoftwareKey swk = this.controller.getSoftwareKey();
/*  78 */     if (swk.getInstallationType() == InstallationType.SERVER) {
/*  79 */       return Math.max(1, swk.getUsers());
/*     */     }
/*  81 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLicensedSessionCount() {
/*  86 */     if (this.controller == null || this.controller.getSoftwareKey() == null) {
/*  87 */       return 0;
/*     */     }
/*  89 */     SoftwareKey swk = this.controller.getSoftwareKey();
/*  90 */     if (swk.getInstallationType() == InstallationType.SERVER) {
/*  91 */       return Math.max(1, swk.getUsers());
/*     */     }
/*  93 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public Dealership getDealershipInformation() {
/*  98 */     return this.dealership;
/*     */   }
/*     */   
/*     */   public void setDealershipInformation(Dealership dealership) throws IOException {
/* 102 */     this.dealership = (DealershipInformation)dealership;
/* 103 */     storeDealershipInformation();
/*     */   }
/*     */   
/*     */   public void storeDealershipInformation() throws IOException {
/* 107 */     this.dealership.store(new File(this.configuration.getDealershipFile()));
/*     */   }
/*     */   
/*     */   public String getSubscriberID() {
/* 111 */     if (this.controller == null || this.controller.getSoftwareKey() == null) {
/* 112 */       return null;
/*     */     }
/* 114 */     SoftwareKey swk = this.controller.getSoftwareKey();
/* 115 */     return (swk.getAuthorizationStatus() != AuthorizationStatus.TEMPORARY) ? swk.getSubscriberID() : null;
/*     */   }
/*     */   
/*     */   public String getSoftwareKey() {
/* 119 */     if (this.controller == null || this.controller.getSoftwareKey() == null) {
/* 120 */       return null;
/*     */     }
/* 122 */     SoftwareKey swk = this.controller.getSoftwareKey();
/* 123 */     return swk.getSoftwareKey();
/*     */   }
/*     */   
/*     */   public Authorization getSubscription() {
/* 127 */     if (this.controller == null || this.controller.getSoftwareKey() == null) {
/* 128 */       return null;
/*     */     }
/* 130 */     return AuthorizationImpl.lookup(this.controller.getSoftwareKey().getAuthorizationID());
/*     */   }
/*     */   
/*     */   public Authorization getDefaultSubscription() {
/* 134 */     if (this.controller == null) {
/* 135 */       return null;
/*     */     }
/* 137 */     return this.controller.getDefaultAuthorization();
/*     */   }
/*     */   
/*     */   public Collection getAuthorizations() {
/* 141 */     return AuthorizationImpl.getAuthorizations();
/*     */   }
/*     */   
/*     */   public void updateLicense(String subscriberID, String licenseKey) {
/* 145 */     this.controller.updateLicense(subscriberID, licenseKey);
/*     */   }
/*     */   
/*     */   public boolean isRepeatRegistration(Set authorizations) {
/* 149 */     if (this.controller == null || this.controller.getSoftwareKey() == null)
/* 150 */       return false; 
/* 151 */     if (authorizations == null || authorizations.size() == 0) {
/* 152 */       return false;
/*     */     }
/* 154 */     Object subscription = authorizations.iterator().next();
/* 155 */     if (subscription instanceof Authorization) {
/* 156 */       Authorization authorization1 = (Authorization)subscription;
/* 157 */       return this.controller.getSoftwareKey().getAuthorizationID().equals(authorization1.getAuthorizationID());
/*     */     } 
/* 159 */     SubscriptionAdapter authorization = (SubscriptionAdapter)subscription;
/* 160 */     return this.controller.getSoftwareKey().getAuthorizationID().equals(authorization.getSubscriptionID());
/*     */   }
/*     */ 
/*     */   
/*     */   public void extendAuthorization(Set authorizations) {
/* 165 */     this.controller.registerLicenceExtensionRequest();
/*     */   }
/*     */   
/*     */   private static Locale createLocale(String lang, String country) {
/* 169 */     Locale ret = Util.parseLocale(lang);
/* 170 */     if (Util.isNullOrEmpty(ret.getCountry())) {
/* 171 */       ret = new Locale(ret.getLanguage(), country);
/*     */     }
/* 173 */     return ret;
/*     */   }
/*     */   
/*     */   public byte[] createManualRegistrationForm(Collection subscriptions, Integer licensedSessions, SoftwareKeyService.Output output) {
/* 177 */     String requestDate = DateFormat.getDateTimeInstance(1, 1).format(new Date());
/* 178 */     String requestID = String.valueOf(Math.abs(requestDate.hashCode()));
/* 179 */     requestID = "R" + requestID.substring(0, Math.min(9, requestID.length()));
/* 180 */     if (output != null) {
/* 181 */       output.setRequestID(requestID);
/*     */     }
/* 183 */     String subscriberID = getSubscriberID();
/*     */     
/* 185 */     requestDate = DateFormat.getDateInstance(3).format(new Date());
/* 186 */     RegistrationForm form = new RegistrationForm(requestID, requestDate, subscriberID, licensedSessions);
/* 187 */     form.setDealershipInformation(this.dealership);
/* 188 */     form.setSoftwareKey(getSoftwareKey());
/* 189 */     if (subscriptions != null) {
/* 190 */       form.setSubscriptions(Locale.getDefault(), subscriptions);
/*     */     } else {
/* 192 */       form.setSubscription(Locale.getDefault(), this.configuration.getDefaultAuthorization());
/*     */     } 
/* 194 */     PDFRegistration pdf = new PDFRegistration();
/* 195 */     Locale locale = createLocale(this.dealership.getDealershipLanguage(), this.dealership.getDealershipCountry());
/* 196 */     return pdf.create("registration", locale, form);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\standalone\authorization\SoftwareKeyServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */