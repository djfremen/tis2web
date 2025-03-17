/*     */ package com.eoos.gm.tis2web.registration.common.task;
/*     */ 
/*     */ import com.eoos.gm.tis2web.registration.server.db.AuthorizationData;
/*     */ import com.eoos.gm.tis2web.registration.server.db.AuthorizationEntity;
/*     */ import com.eoos.gm.tis2web.registration.service.RegistrationProvider;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.Dealership;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.Registration;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.RegistrationException;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.Registry;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.RequestType;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.SoftwareKeyException;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.Subscription;
/*     */ import com.eoos.util.Task;
/*     */ import java.io.Serializable;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class RegistrationTask
/*     */   implements Task, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  22 */   private static final Logger log = Logger.getLogger(RegistrationTask.class);
/*     */   
/*     */   private Dealership dealership;
/*     */   private boolean repeatRegistration;
/*     */   private String subscriberID;
/*     */   private String subscriptionID;
/*     */   private String softwareKey;
/*     */   private List authorizations;
/*     */   private Integer sessions;
/*     */   
/*     */   public RegistrationTask(Dealership dealership, String subscriptionID, String softwareKey, Integer sessions) {
/*  33 */     this.dealership = dealership;
/*  34 */     this.subscriptionID = subscriptionID;
/*  35 */     this.softwareKey = softwareKey;
/*  36 */     this.sessions = sessions;
/*     */   }
/*     */   
/*     */   public RegistrationTask(boolean repeatRegistration, String subscriberID, String softwareKey, Integer sessions) {
/*  40 */     this.repeatRegistration = repeatRegistration;
/*  41 */     this.subscriberID = subscriberID;
/*  42 */     this.softwareKey = softwareKey;
/*  43 */     this.sessions = sessions;
/*     */   }
/*     */   
/*     */   public RegistrationTask(String subscriberID, List authorizations, Integer sessions) {
/*  47 */     this.subscriberID = subscriberID;
/*  48 */     this.authorizations = authorizations;
/*  49 */     this.sessions = sessions;
/*     */   }
/*     */   
/*     */   public Object execute() {
/*  53 */     if (this.authorizations != null)
/*  54 */       return handleExtendAuthorization(); 
/*  55 */     if (this.subscriberID != null) {
/*  56 */       return this.repeatRegistration ? handleRepeatRegistration() : handleHardwareKeyMigration();
/*     */     }
/*  58 */     return handleInitialRegistration();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object handleRepeatRegistration() {
/*  63 */     Registry registry = RegistrationProvider.getInstance().getService();
/*  64 */     String requestID = null;
/*     */     try {
/*  66 */       Registration registration = registry.loadRegistrationRecord(this.subscriberID);
/*  67 */       if (registration == null) {
/*  68 */         log.info("invalid registration request encountered (subscriber=" + this.subscriberID + ").");
/*  69 */         return new RegistrationException("registration.db.load.failed");
/*     */       } 
/*  71 */       requestID = registry.getRequestID(registration);
/*  72 */       if (requestID == null) {
/*  73 */         return null;
/*     */       }
/*  75 */       registration.setSoftwareKey(this.softwareKey);
/*  76 */       registration.setSessionCount(this.sessions);
/*  77 */       registry.registerRegistrationRequest(requestID, registration);
/*  78 */       return requestID;
/*  79 */     } catch (SoftwareKeyException s) {
/*  80 */       log.info("failed to handle registration '" + requestID + "' (subscriber=" + this.subscriberID + ")");
/*  81 */     } catch (RegistrationException r) {
/*  82 */       if (r.getMessage().equals("registration.duplicate")) {
/*  83 */         log.info("ignore duplicate registration request'" + requestID + "' (subscriber=" + this.subscriberID + ")");
/*  84 */         return r;
/*     */       } 
/*  86 */       log.error("failed to handle registration '" + requestID + "' (subscriber=" + this.subscriberID + ")", (Throwable)r);
/*     */     }
/*  88 */     catch (Exception e) {
/*  89 */       log.error("failed to handle registration '" + requestID + "' (subscriber=" + this.subscriberID + ")", e);
/*     */     } 
/*  91 */     return null;
/*     */   }
/*     */   
/*     */   public Object handleExtendAuthorization() {
/*  95 */     Registry registry = RegistrationProvider.getInstance().getService();
/*  96 */     String requestID = null;
/*     */     try {
/*  98 */       Registration registration = registry.loadRegistrationRecord(this.subscriberID);
/*  99 */       if (registration == null) {
/* 100 */         log.info("invalid registration request encountered (subscriber=" + this.subscriberID + ").");
/* 101 */         return new RegistrationException("registration.db.load.failed");
/*     */       } 
/* 103 */       requestID = registry.getRequestID(registration);
/* 104 */       if (requestID == null) {
/* 105 */         return null;
/*     */       }
/* 107 */       registration.setAuthorizationList(this.authorizations);
/* 108 */       registration.setRequestType(RequestType.EXTENSION);
/* 109 */       registration.setSessionCount(this.sessions);
/* 110 */       registry.registerRegistrationRequest(requestID, registration);
/* 111 */       return requestID;
/* 112 */     } catch (SoftwareKeyException s) {
/* 113 */       log.info("failed to handle registration '" + requestID + "' (subscriber=" + this.subscriberID + ")");
/* 114 */     } catch (RegistrationException r) {
/* 115 */       if (r.getMessage().equals("registration.duplicate")) {
/* 116 */         log.info("ignore duplicate registration request'" + requestID + "' (subscriber=" + this.subscriberID + ")");
/* 117 */         return r;
/*     */       } 
/* 119 */       log.error("failed to handle registration '" + requestID + "' (subscriber=" + this.subscriberID + ")", (Throwable)r);
/*     */     }
/* 121 */     catch (Exception e) {
/* 122 */       log.error("failed to handle registration '" + requestID + "' (subscriber=" + this.subscriberID + ")", e);
/*     */     } 
/* 124 */     return null;
/*     */   }
/*     */   
/*     */   public Object handleHardwareKeyMigration() {
/* 128 */     Registry registry = RegistrationProvider.getInstance().getService();
/* 129 */     String requestID = null;
/*     */     try {
/* 131 */       Registration registration = registry.loadRegistrationRecord(this.subscriberID);
/* 132 */       if (registration == null) {
/* 133 */         log.info("invalid hardware migration request encountered (subscriber=" + this.subscriberID + ").");
/* 134 */         return new RegistrationException("registration.db.load.failed");
/*     */       } 
/* 136 */       requestID = registry.getRequestID(registration);
/* 137 */       if (requestID == null) {
/* 138 */         return null;
/*     */       }
/* 140 */       registration.setSoftwareKey(this.softwareKey);
/* 141 */       registration.setSessionCount(this.sessions);
/* 142 */       registry.registerRegistrationRequest(requestID, registration);
/* 143 */       return requestID;
/* 144 */     } catch (SoftwareKeyException s) {
/* 145 */       log.info("failed to handle registration '" + requestID + "' (subscriber=" + this.subscriberID + ")");
/* 146 */     } catch (RegistrationException r) {
/* 147 */       if (r.getMessage().equals("registration.duplicate")) {
/* 148 */         log.info("ignore duplicate registration request'" + requestID + "' (subscriber=" + this.subscriberID + ")");
/* 149 */         return r;
/*     */       } 
/* 151 */       log.error("failed to handle registration '" + requestID + "' (subscriber=" + this.subscriberID + ")", (Throwable)r);
/*     */     }
/* 153 */     catch (Exception e) {
/* 154 */       log.error("failed to handle registration '" + requestID + "' (subscriber=" + this.subscriberID + ")", e);
/*     */     } 
/* 156 */     return null;
/*     */   }
/*     */   
/*     */   public Object handleInitialRegistration() {
/* 160 */     Registry registry = RegistrationProvider.getInstance().getService();
/* 161 */     String requestID = null;
/*     */     try {
/* 163 */       requestID = registry.getRequestID(RequestType.STANDARD);
/* 164 */       if (requestID == null) {
/* 165 */         return null;
/*     */       }
/* 167 */       AuthorizationEntity authorizationEntity = AuthorizationData.getAuthorization(this.subscriptionID);
/* 168 */       this.subscriberID = registry.registerRegistrationRequest(requestID, (Subscription)authorizationEntity, this.dealership, this.softwareKey, this.sessions);
/* 169 */       return requestID;
/* 170 */     } catch (SoftwareKeyException s) {
/* 171 */       log.info("failed to handle registration '" + requestID + "' (dealer=" + this.dealership.getDealershipID() + ")");
/* 172 */     } catch (RegistrationException r) {
/* 173 */       if (r.getMessage().equals("registration.repeat")) {
/* 174 */         log.info("ignore repeat registration request'" + requestID + "' (dealer=" + this.dealership.getDealershipID() + ")");
/* 175 */         return r;
/* 176 */       }  if (r.getMessage().equals("registration.duplicate")) {
/* 177 */         log.info("ignore duplicate registration request'" + requestID + "' (dealer=" + this.dealership.getDealershipID() + ")");
/* 178 */         return r;
/*     */       } 
/* 180 */       log.error("failed to handle registration '" + requestID + "' (dealer=" + this.dealership.getDealershipID() + ")", (Throwable)r);
/*     */     }
/* 182 */     catch (Exception e) {
/* 183 */       log.error("failed to handle registration '" + requestID + "' (dealer=" + this.dealership.getDealershipID() + ")", e);
/*     */     } 
/* 185 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\common\task\RegistrationTask.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */