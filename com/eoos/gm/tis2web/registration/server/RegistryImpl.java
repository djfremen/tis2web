/*     */ package com.eoos.gm.tis2web.registration.server;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.registration.common.RegistrationForm;
/*     */ import com.eoos.gm.tis2web.registration.common.SoftwareKey;
/*     */ import com.eoos.gm.tis2web.registration.server.db.AuthorizationData;
/*     */ import com.eoos.gm.tis2web.registration.server.db.AuthorizationEntity;
/*     */ import com.eoos.gm.tis2web.registration.server.db.DealershipEntity;
/*     */ import com.eoos.gm.tis2web.registration.server.db.RegistrationData;
/*     */ import com.eoos.gm.tis2web.registration.server.db.RegistrationEntity;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.AuthorizationStatus;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.Dealership;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.Registration;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.RegistrationAttribute;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.RegistrationException;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.RegistrationFilter;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.RegistrationFlag;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.Registry;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.RequestStatus;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.RequestType;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.SalesOrganization;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.SoftwareKeyException;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.SortDirection;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.Subscription;
/*     */ import com.eoos.gm.tis2web.registration.standalone.authorization.PDFRegistration;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.v2.StringUtilities;
/*     */ import java.text.DateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class RegistryImpl
/*     */   implements Registry
/*     */ {
/*  41 */   private static final Logger log = Logger.getLogger(RegistryImpl.class);
/*     */ 
/*     */   
/*     */   private ConfigurationMediator configuration;
/*     */ 
/*     */   
/*     */   private DatabaseLink db;
/*     */ 
/*     */   
/*     */   public void init(Configuration configuration, DatabaseLink db) {
/*  51 */     this.configuration = new ConfigurationMediator(configuration);
/*  52 */     this.db = db;
/*  53 */     if (getAuthorizations() == null) {
/*  54 */       throw new RuntimeException("failed to load authorization definitions");
/*     */     }
/*     */   }
/*     */   
/*     */   public DatabaseLink getDatabaseLink() {
/*  59 */     return this.db;
/*     */   }
/*     */   
/*     */   public List getAuthorizations() {
/*  63 */     return AuthorizationData.getAuthorizations(this);
/*     */   }
/*     */   
/*     */   public String getRequestID(RequestType type) throws Exception {
/*  67 */     return RegistrationData.getRequestID(this, type);
/*     */   }
/*     */   
/*     */   public String getRequestID(Registration registration) throws Exception {
/*  71 */     if (registration.getRequestStatus() == RequestStatus.PENDING) {
/*  72 */       throw new RegistrationException("registration.duplicate");
/*     */     }
/*  74 */     RequestType type = determineRequestType(registration.getRequestType(), registration.getRequestStatus());
/*  75 */     return RegistrationData.getRequestID(this, type);
/*     */   }
/*     */   
/*     */   public Registration loadRegistrationRecord(String subscriberID) throws Exception {
/*  79 */     return RegistrationData.loadRegistrationRecord(this, subscriberID.toUpperCase(Locale.ENGLISH));
/*     */   }
/*     */   
/*     */   public Registration loadRegistrationRecordByDealershipID(String dealershipID) throws Exception {
/*  83 */     String subscriberID = RegistrationData.existsRegistrationRecord(this, "", dealershipID);
/*  84 */     if (subscriberID != null) {
/*  85 */       return loadRegistrationRecord(subscriberID);
/*     */     }
/*  87 */     return null;
/*     */   }
/*     */   
/*     */   public void deleteRegistrationRecord(Registration registration) throws Exception {
/*  91 */     RegistrationData.deleteRegistrationRecord(this, registration);
/*     */   }
/*     */   
/*     */   public void updateDealershipInformation(Dealership dealership) throws Exception {
/*  95 */     RegistrationData.updateDealershipInformation(this, dealership);
/*     */   }
/*     */   
/*     */   public String registerRegistrationRequest(String requestID, Subscription subscription, Dealership dealership, String softwareKey, Integer sessions) throws Exception {
/*  99 */     SoftwareKey swk = new SoftwareKey("TIS2WEB", softwareKey);
/* 100 */     if (swk.getAuthorizationStatus() != AuthorizationStatus.TEMPORARY || !subscription.getSubscriptionID().equals(swk.getAuthorizationID())) {
/* 101 */       throw new SoftwareKeyException();
/*     */     }
/* 103 */     if (!checkDuplicate(swk.getHardwareHashID(), dealership.getDealershipID())) {
/* 104 */       throw new RegistrationException("registration.repeat");
/*     */     }
/* 106 */     RegistrationEntity registrationEntity = new RegistrationEntity();
/* 107 */     registrationEntity.setDealership((Dealership)new DealershipEntity(dealership));
/* 108 */     registrationEntity.setRequestID(requestID);
/* 109 */     registrationEntity.setRequestStatus(RequestStatus.PENDING);
/* 110 */     registrationEntity.setRequestType(RequestType.STANDARD);
/* 111 */     registrationEntity.setRequestDate(new Date());
/* 112 */     registrationEntity.setSessionCount(sessions);
/* 113 */     registrationEntity.setSubscriberID(RegistrationData.getSubscriberID(this));
/* 114 */     AuthorizationEntity authorization = (AuthorizationEntity)subscription;
/* 115 */     registrationEntity.setOrganization(authorization.getOrganization());
/* 116 */     registrationEntity.setAuthorizationID(authorization.getAuthorizationID());
/* 117 */     swk.setSubscriberID(registrationEntity.getSubscriberID());
/* 118 */     swk.invalidate();
/* 119 */     registrationEntity.setSoftwareKey(swk.getSoftwareKey());
/* 120 */     store((Registration)registrationEntity);
/*     */     try {
/* 122 */       sendRegistrationNotice(authorization, (Registration)registrationEntity);
/* 123 */     } catch (Exception e) {
/* 124 */       log.error("failed to send registration request notice", e);
/*     */     } 
/* 126 */     return registrationEntity.getSubscriberID();
/*     */   }
/*     */   
/*     */   public void registerRegistrationRequest(String requestID, Registration registration) throws Exception {
/* 130 */     RequestType type = determineRequestType(registration.getRequestType(), registration.getRequestStatus());
/* 131 */     registration.setRequestID(requestID);
/* 132 */     registration.setRequestStatus(RequestStatus.PENDING);
/* 133 */     registration.setRequestType(type);
/* 134 */     registration.setRequestDate(new Date());
/* 135 */     update(registration);
/*     */     try {
/* 137 */       sendRegistrationNotice(registration.getOrganization(), registration);
/* 138 */     } catch (Exception e) {
/* 139 */       log.error("failed to send registration request notice", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void registerRegistrationRequest(String requestID, Registration registration, List authorizations) throws Exception {
/* 144 */     registration.setRequestID(requestID);
/* 145 */     registration.setRequestStatus(RequestStatus.PENDING);
/* 146 */     registration.setRequestType(RequestType.EXTENSION);
/* 147 */     registration.setRequestDate(new Date());
/* 148 */     registration.setAuthorizationList(authorizations);
/* 149 */     update(registration);
/*     */     try {
/* 151 */       sendRegistrationNotice(authorizations, registration);
/* 152 */     } catch (Exception e) {
/* 153 */       log.error("failed to send registration request notice", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public List loadRegistrationRecords(SalesOrganization organization, RequestStatus status, RegistrationFilter filter, RegistrationAttribute sort, SortDirection direction) throws Exception {
/* 158 */     return RegistrationData.load(this, organization, status, filter, sort, direction);
/*     */   }
/*     */   
/*     */   public void computeLicenceKey(Registration registration, String subscriberID2, Integer users) {
/* 162 */     if (registration.getSubscriberID() == null) {
/* 163 */       throw new SoftwareKeyException();
/*     */     }
/* 165 */     if (registration.getSoftwareKey() == null) {
/* 166 */       throw new SoftwareKeyException();
/*     */     }
/* 168 */     if (registration.getAuthorizationID() == null) {
/* 169 */       throw new SoftwareKeyException();
/*     */     }
/* 171 */     String subscriberID = (registration.getRequestType() == RequestType.TEMPORARY) ? "TIS2WEB" : registration.getSubscriberID();
/* 172 */     SoftwareKey softwareKey = null;
/*     */     try {
/* 174 */       softwareKey = new SoftwareKey(subscriberID, registration.getSoftwareKey());
/* 175 */     } catch (Exception e) {
/*     */       try {
/* 177 */         if ("TIS2WEB".equals(subscriberID)) {
/* 178 */           softwareKey = new SoftwareKey(registration.getSubscriberID(), registration.getSoftwareKey());
/*     */         } else {
/* 180 */           softwareKey = new SoftwareKey("TIS2WEB", registration.getSoftwareKey());
/*     */         } 
/* 182 */       } catch (Exception x) {
/* 183 */         if (subscriberID2 != null) {
/* 184 */           softwareKey = new SoftwareKey(subscriberID2, registration.getSoftwareKey());
/* 185 */           subscriberID = subscriberID2;
/*     */         } 
/*     */       } 
/*     */     } 
/* 189 */     softwareKey.setSubscriberID(registration.getSubscriberID());
/* 190 */     softwareKey.setAuthorizationID(registration.getAuthorizationID());
/* 191 */     if (users != null) {
/* 192 */       softwareKey.setUsers(users.intValue());
/*     */     }
/* 194 */     softwareKey.authorize();
/* 195 */     softwareKey.invalidate();
/* 196 */     registration.setLicenseKey(softwareKey.getSoftwareKey());
/*     */     
/* 198 */     SoftwareKey swk = new SoftwareKey(registration.getSubscriberID(), registration.getLicenseKey());
/* 199 */     log.debug("license key: " + registration.getLicenseKey());
/* 200 */     log.debug("   authorization: " + swk.getAuthorizationStatus());
/* 201 */     log.debug("   subscription : " + swk.getAuthorizationID());
/* 202 */     log.debug("   hardware-id  : " + swk.getHardwareHashID());
/* 203 */     log.debug("   installation : " + swk.getInstallationType());
/* 204 */     log.debug("   sessions     : " + swk.getUsers());
/*     */   }
/*     */ 
/*     */   
/*     */   public Registration createRegistrationRequest() throws Exception {
/* 209 */     RegistrationEntity registration = new RegistrationEntity();
/* 210 */     registration.setRequestID(getRequestID((RequestType)null));
/* 211 */     registration.setSubscriberID(RegistrationData.getSubscriberID(this));
/* 212 */     registration.setRequestStatus(RequestStatus.PENDING);
/* 213 */     registration.setRequestType(RequestType.TEMPORARY);
/* 214 */     registration.setRequestDate(new Date());
/* 215 */     return (Registration)registration;
/*     */   }
/*     */   
/*     */   public void authorizeRegistrationRequest(Registration registration) throws Exception {
/* 219 */     registration.setRequestStatus(RequestStatus.AUTHORIZED);
/* 220 */     if (registration.getRequestType() == RequestType.TEMPORARY) {
/* 221 */       registration.setRequestType(RequestType.STANDARD);
/* 222 */       AuthorizationEntity authorization = AuthorizationData.getAuthorization(registration.getAuthorizationID());
/* 223 */       registration.setOrganization(authorization.getOrganization());
/* 224 */       store(registration);
/*     */     } else {
/* 226 */       if (registration.getRequestType() == RequestType.HWKLOCAL) {
/* 227 */         registration.setRequestType(RequestType.HWKMIGRATION);
/*     */       } else {
/* 229 */         registration.setRequestType(RequestType.STANDARD);
/*     */       } 
/* 231 */       update(registration);
/*     */     } 
/* 233 */     sendAuthorizationNotice(registration);
/*     */   }
/*     */   
/*     */   public void revokeAuthorization(Registration registration) throws Exception {
/* 237 */     registration.setRequestStatus(RequestStatus.REVOKED);
/* 238 */     update(registration);
/*     */   }
/*     */   
/*     */   public byte[] generateFaxNotification(Registration registration, Integer licensedSessions) throws Exception {
/* 242 */     String requestDate = DateFormat.getDateInstance(3).format(registration.getRequestDate());
/* 243 */     RegistrationForm form = new RegistrationForm(registration.getRequestID(), requestDate, registration.getSubscriberID(), licensedSessions);
/* 244 */     form.setDealershipInformation(registration.getDealership());
/* 245 */     form.setLicenseKey(registration.getLicenseKey());
/* 246 */     String language = registration.getDealership().getDealershipLanguage();
/* 247 */     String country = registration.getDealership().getDealershipCountry();
/* 248 */     Locale locale = Util.parseLocale(language);
/* 249 */     if (Util.isNullOrEmpty(locale.getCountry())) {
/* 250 */       locale = new Locale(locale.getLanguage(), country);
/*     */     }
/* 252 */     form.setSubscription(locale, (Subscription)AuthorizationData.getAuthorization(registration.getAuthorizationID()));
/* 253 */     PDFRegistration pdf = new PDFRegistration();
/* 254 */     return pdf.create("license", locale, form);
/*     */   }
/*     */   
/*     */   protected boolean isHardwareKeyMigrationEntry(Registration registration) {
/* 258 */     if (registration.getLicenseKey() != null && "n/a".equalsIgnoreCase(registration.getLicenseKey()))
/* 259 */       return true; 
/* 260 */     if (registration.getSoftwareKey() != null && "n/a".equalsIgnoreCase(registration.getSoftwareKey())) {
/* 261 */       return true;
/*     */     }
/* 263 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String exportRegistrationDatabase(SalesOrganization organization) throws Exception {
/* 268 */     RegistrationData.prepareRegistrationDatabaseExport(this);
/* 269 */     StringBuffer db = new StringBuffer(100000);
/* 270 */     List<Registration> registrations = loadRegistrationRecords(organization, RequestStatus.AUTHORIZED, null, null, null); int i;
/* 271 */     for (i = 0; i < registrations.size(); i++) {
/* 272 */       Registration registration = registrations.get(i);
/* 273 */       SoftwareKey swk = null;
/* 274 */       if (!isHardwareKeyMigrationEntry(registration)) {
/* 275 */         swk = new SoftwareKey(registration.getSubscriberID(), registration.getLicenseKey());
/*     */       }
/* 277 */       if (db.length() > 0) {
/* 278 */         db.append("\r\n");
/*     */       }
/* 280 */       db.append(registration.getSubscriberID());
/* 281 */       db.append(",");
/* 282 */       db.append((swk == null) ? "n/a" : swk.getHardwareHashID());
/* 283 */       db.append(",");
/* 284 */       db.append((swk == null) ? RegistrationFlag.MIGRATION.ord() : RegistrationFlag.AUTHORIZED.ord());
/* 285 */       db.append(",");
/* 286 */       db.append(registration.getAuthorizationID());
/*     */     } 
/* 288 */     registrations = loadRegistrationRecords(organization, RequestStatus.PENDING, null, null, null);
/* 289 */     for (i = 0; i < registrations.size(); i++) {
/* 290 */       Registration registration = registrations.get(i);
/* 291 */       if (registration.getRequestType() == RequestType.HWKMIGRATION) {
/* 292 */         if (db.length() > 0) {
/* 293 */           db.append("\r\n");
/*     */         }
/* 295 */         db.append(registration.getSubscriberID());
/* 296 */         db.append(",");
/* 297 */         db.append("n/a");
/* 298 */         db.append(",");
/* 299 */         db.append(RegistrationFlag.MIGRATION.ord());
/* 300 */         db.append(",");
/* 301 */         db.append(registration.getAuthorizationID());
/*     */       } 
/*     */     } 
/* 304 */     registrations = loadRegistrationRecords(organization, RequestStatus.REVOKED, null, null, null);
/* 305 */     for (i = 0; i < registrations.size(); i++) {
/* 306 */       Registration registration = registrations.get(i);
/* 307 */       if (db.length() > 0) {
/* 308 */         db.append("\r\n");
/*     */       }
/* 310 */       db.append(registration.getSubscriberID());
/* 311 */       db.append(",");
/* 312 */       if (isHardwareKeyMigrationEntry(registration)) {
/* 313 */         db.append(registration.getSubscriberID());
/*     */       } else {
/* 315 */         SoftwareKey swk = new SoftwareKey(registration.getSubscriberID(), registration.getLicenseKey());
/* 316 */         db.append(swk.getHardwareHashID());
/*     */       } 
/* 318 */       db.append(",");
/* 319 */       db.append(RegistrationFlag.REVOKED.ord());
/* 320 */       db.append(",");
/* 321 */       db.append(registration.getAuthorizationID());
/*     */     } 
/* 323 */     RegistrationData.cleanupRegistrationDatabaseExport(this);
/* 324 */     return db.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public void load(Registration registration) {
/* 329 */     RegistrationData.load(this, registration);
/*     */   }
/*     */   
/*     */   public void load(Dealership dealership) {
/* 333 */     RegistrationData.load(this, dealership);
/*     */   }
/*     */   
/*     */   private void store(Registration registration) throws Exception {
/* 337 */     RegistrationData.store(this, registration);
/*     */   }
/*     */   
/*     */   public void update(Registration registration) throws Exception {
/* 341 */     RegistrationData.update(this, registration);
/*     */   }
/*     */   
/*     */   private boolean checkDuplicate(String hwid, String dealerID) {
/* 345 */     String subscriberID = RegistrationData.existsRegistrationRecord(this, hwid, dealerID);
/* 346 */     if (subscriberID != null) {
/* 347 */       log.debug("duplicate registration request '" + subscriberID + "' encountered.");
/* 348 */       return false;
/*     */     } 
/* 350 */     return true;
/*     */   }
/*     */   
/*     */   private RequestType determineRequestType(RequestType type, RequestStatus status) {
/* 354 */     if (type == RequestType.HWKLOCAL)
/* 355 */       return RequestType.HWKMIGRATION; 
/* 356 */     if (type == RequestType.STANDARD || type == RequestType.REPEAT) {
/* 357 */       return RequestType.REPEAT;
/*     */     }
/* 359 */     return RequestType.EXTENSION;
/*     */   }
/*     */ 
/*     */   
/*     */   private String format(String data) {
/* 364 */     StringBuffer buf = new StringBuffer();
/* 365 */     for (int i = 0; i < data.length(); i++) {
/* 366 */       buf.append(data.charAt(i));
/* 367 */       if ((i + 1) % 4 == 0 && i < data.length() - 1) {
/* 368 */         buf.append("-");
/*     */       }
/*     */     } 
/* 371 */     return buf.toString();
/*     */   }
/*     */   
/*     */   private String replaceParameters(Registration registration, String message) {
/* 375 */     message = StringUtilities.replace(message, "{REQUEST-ID}", registration.getRequestID());
/* 376 */     message = StringUtilities.replace(message, "{SUBSCRIBER-ID}", registration.getSubscriberID());
/* 377 */     return StringUtilities.replace(message, "{LICENSE-KEY}", format(registration.getLicenseKey()));
/*     */   }
/*     */   
/*     */   private void sendAuthorizationNotice(Registration registration) throws Exception {
/* 381 */     String email = registration.getDealership().getDealershipEmail();
/* 382 */     if (email == null) {
/*     */       return;
/*     */     }
/* 385 */     List<String> recipients = new ArrayList();
/* 386 */     recipients.add(email);
/* 387 */     Locale locale = Util.parseLocale(registration.getDealership().getDealershipLanguage());
/* 388 */     String body = ApplicationContext.getInstance().getMessage(locale, "auth.req.email.license.body");
/* 389 */     body = replaceParameters(registration, body);
/* 390 */     String subject = ApplicationContext.getInstance().getMessage(locale, "auth.req.email.license.subject");
/* 391 */     subject = replaceParameters(registration, subject);
/* 392 */     RegistrationMail.sendMail(recipients, subject, body);
/*     */   }
/*     */   
/*     */   private void sendRegistrationNotice(AuthorizationEntity authorization, Registration registration) throws Exception {
/* 396 */     String email = this.configuration.getEmail(authorization.getOrganization());
/* 397 */     List<String> recipients = new ArrayList();
/* 398 */     recipients.add(email);
/* 399 */     String body = ApplicationContext.getInstance().getMessage(Locale.ENGLISH, "auth.req.email.notification");
/* 400 */     String subject = "TIS2WEB Registration (" + registration.getRequestID() + "," + registration.getDealership().getDealershipID() + ")";
/* 401 */     RegistrationMail.sendMail(recipients, subject, body);
/*     */   }
/*     */   
/*     */   private void sendRegistrationNotice(SalesOrganization organization, Registration registration) throws Exception {
/* 405 */     String email = this.configuration.getEmail(organization);
/* 406 */     List<String> recipients = new ArrayList();
/* 407 */     recipients.add(email);
/* 408 */     String body = ApplicationContext.getInstance().getMessage(Locale.ENGLISH, "auth.req.email.notification");
/* 409 */     String subject = "TIS2WEB Registration (" + registration.getRequestID() + "," + registration.getDealership().getDealershipID() + ")";
/* 410 */     RegistrationMail.sendMail(recipients, subject, body);
/*     */   }
/*     */   
/*     */   private void sendRegistrationNotice(List<AuthorizationEntity> authorizations, Registration registration) throws Exception {
/* 414 */     List<SalesOrganization> organizations = new ArrayList();
/* 415 */     for (int i = 0; i < authorizations.size(); i++) {
/* 416 */       AuthorizationEntity authorization = null;
/* 417 */       if (authorizations.get(i) instanceof AuthorizationEntity) {
/* 418 */         authorization = authorizations.get(i);
/*     */       } else {
/* 420 */         String authorizationID = (String)authorizations.get(i);
/* 421 */         authorization = AuthorizationData.getAuthorization(authorizationID);
/*     */       } 
/* 423 */       if (authorization != null && 
/* 424 */         !organizations.contains(authorization.getOrganization())) {
/* 425 */         organizations.add(authorization.getOrganization());
/*     */       }
/*     */     } 
/*     */     
/* 429 */     List<String> recipients = new ArrayList();
/* 430 */     for (int j = 0; j < organizations.size(); j++) {
/* 431 */       String email = this.configuration.getEmail(organizations.get(j));
/* 432 */       recipients.add(email);
/*     */     } 
/* 434 */     String body = ApplicationContext.getInstance().getMessage(Locale.ENGLISH, "auth.req.email.notification");
/* 435 */     String subject = "TIS2WEB Registration (" + registration.getRequestID() + "," + registration.getDealership().getDealershipID() + ")";
/* 436 */     RegistrationMail.sendMail(recipients, subject, body);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\server\RegistryImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */