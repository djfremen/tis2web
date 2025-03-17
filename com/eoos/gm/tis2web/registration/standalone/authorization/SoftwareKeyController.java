/*     */ package com.eoos.gm.tis2web.registration.standalone.authorization;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.hwk.exception.SystemDriverNotInstalledException;
/*     */ import com.eoos.gm.tis2web.frame.export.common.hwk.exception.UnavailableHardwareKeyException;
/*     */ import com.eoos.gm.tis2web.registration.common.SoftwareKey;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.AuthorizationStatus;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.InstallationType;
/*     */ import com.eoos.gm.tis2web.registration.service.cai.RegistrationFlag;
/*     */ import com.eoos.gm.tis2web.registration.standalone.authorization.service.cai.Authorization;
/*     */ import com.eoos.gm.tis2web.registration.standalone.authorization.service.cai.exceptions.HardwareKeyException;
/*     */ import com.eoos.gm.tis2web.registration.standalone.authorization.service.cai.exceptions.InvalidLicenseException;
/*     */ import com.eoos.hardwarekey.impl.HardwareKeyImpl;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.softwarekey.common.SoftwareKey;
/*     */ import com.eoos.softwarekey.common.SoftwareKeyCallback;
/*     */ import com.eoos.softwarekey.impl.SoftwareKeyImpl;
/*     */ import java.io.File;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class SoftwareKeyController
/*     */   implements SoftwareKeyCallback
/*     */ {
/*  23 */   private static final Logger log = Logger.getLogger(SoftwareKey.class);
/*     */   
/*     */   public static boolean CHECKMODE;
/*     */   
/*     */   private ConfigurationMediator configuration;
/*     */   private long seed;
/*     */   private SoftwareKey softwareKey;
/*     */   
/*     */   public SoftwareKey getSoftwareKey() {
/*  32 */     return this.softwareKey;
/*     */   }
/*     */   
/*     */   public Authorization getDefaultAuthorization() {
/*  36 */     return (Authorization)this.configuration.getDefaultAuthorization();
/*     */   }
/*     */   
/*     */   public SoftwareKeyController(Configuration configuration) {
/*  40 */     this.configuration = new ConfigurationMediator(configuration);
/*  41 */     AuthorizationImpl.load(new File(this.configuration.getAuthorizationDefinition()));
/*  42 */     CHECKMODE = this.configuration.checkMode();
/*     */   }
/*     */   
/*     */   public void constructSoftwareKey() {
/*  46 */     boolean hardwareKeyMigrationFailed = false;
/*  47 */     License license = checkLicense();
/*  48 */     if (license != null) {
/*  49 */       this.seed = license.getTimestamp();
/*  50 */       SoftwareKey swk = SoftwareKeyImpl.getInstance();
/*  51 */       swk.initialize(this);
/*  52 */       if (CHECKMODE || swk.isValidHWID(license.getHardwareID())) {
/*  53 */         this.softwareKey = license.getSoftwareKey();
/*  54 */         RegistryEntry registryEntry = Registry.queryDatabase(this.configuration.getRegistryDatabase(), this.softwareKey.getHardwareHashID(), this.softwareKey.getSubscriberID());
/*  55 */         if (registryEntry == null)
/*     */           return; 
/*  57 */         if (registryEntry.getFlag() == RegistrationFlag.REVOKED) {
/*  58 */           if (this.softwareKey.getAuthorizationStatus() == AuthorizationStatus.TEMPORARY)
/*     */             return; 
/*     */         } else {
/*  61 */           if (registryEntry.getFlag() == RegistrationFlag.AUTHORIZED) {
/*  62 */             updateLicense(license, this.softwareKey, registryEntry); return;
/*     */           } 
/*  64 */           if (registryEntry.getFlag() == RegistrationFlag.MIGRATION) {
/*  65 */             if (this.softwareKey.getAuthorizationStatus() == AuthorizationStatus.MIGRATED) {
/*     */               return;
/*     */             }
/*  68 */             if (this.softwareKey.getAuthorizationStatus() == AuthorizationStatus.AUTHORIZED)
/*     */               return; 
/*     */           } 
/*     */         } 
/*     */       } else {
/*  73 */         throw new InvalidLicenseException();
/*     */       } 
/*     */     } 
/*  76 */     createSoftwareKey();
/*  77 */     this.softwareKey.setInstallationType(this.configuration.getInstallationType());
/*  78 */     String hwk = getHardwareKey();
/*  79 */     RegistryEntry registration = Registry.queryDatabase(this.configuration.getRegistryDatabase(), this.softwareKey.getHardwareHashID(), hwk);
/*  80 */     if (registration == null) {
/*  81 */       this.softwareKey.setAuthorizationID(this.configuration.getDefaultAuthorization().getSubscriptionID());
/*  82 */       if (this.configuration.getInstallationType() == InstallationType.SERVER) {
/*  83 */         this.softwareKey.setUsers(this.configuration.getDefaultMaxSessionCount());
/*     */       }
/*  85 */       log.debug("create temporary software key");
/*  86 */     } else if (registration.getFlag() == RegistrationFlag.REVOKED) {
/*  87 */       log.debug("revoked software key authorization");
/*  88 */     } else if (registration.getFlag() == RegistrationFlag.AUTHORIZED) {
/*  89 */       this.softwareKey.authorize();
/*  90 */       this.softwareKey.setAuthorizationID(registration.getAuthorization().getAuthorizationID());
/*  91 */       this.softwareKey.setSubscriberID(registration.getSubscriberID());
/*  92 */       log.debug("authorized software key (registration database)");
/*  93 */     } else if (registration.getFlag() == RegistrationFlag.MIGRATION) {
/*  94 */       if (hwk != null && markHardwareKeyAsUsed()) {
/*  95 */         this.softwareKey.migrate();
/*  96 */         this.softwareKey.setAuthorizationID(registration.getAuthorization().getAuthorizationID());
/*  97 */         this.softwareKey.setSubscriberID(registration.getSubscriberID());
/*  98 */         if (this.configuration.getInstallationType() == InstallationType.SERVER) {
/*  99 */           this.softwareKey.setUsers(this.configuration.getDefaultMaxSessionCount());
/*     */         }
/* 101 */         log.debug("migrated software key authorization");
/*     */       } else {
/* 103 */         hardwareKeyMigrationFailed = true;
/* 104 */         this.softwareKey.setAuthorizationID(this.configuration.getDefaultAuthorization().getSubscriptionID());
/* 105 */         log.debug("hardware key migration failed");
/*     */       } 
/*     */     } 
/* 108 */     license = License.create(this.configuration.getLicenseFile(), this.softwareKey);
/* 109 */     license.store();
/* 110 */     if (hardwareKeyMigrationFailed) {
/* 111 */       throw new HardwareKeyException();
/*     */     }
/*     */   }
/*     */   
/*     */   public void updateLicense(String subscriberID, String licenseKey) {
/* 116 */     License license = checkLicense();
/* 117 */     if (license != null) {
/* 118 */       SoftwareKey swk = SoftwareKeyImpl.getInstance();
/* 119 */       if (CHECKMODE || swk.isValidHWID(license.getHardwareID())) {
/* 120 */         this.softwareKey = new SoftwareKey(subscriberID, licenseKey, license.getHardwareID(), license.getTimestamp());
/* 121 */         this.softwareKey.setHardwareInfo(swk.getHWInfo());
/* 122 */         if (this.softwareKey.getHardwareHashID().equals(license.getSoftwareKey().getHardwareHashID())) {
/* 123 */           license.setSoftwareKey(this.softwareKey);
/* 124 */           checkLicensedUserSessions(this.softwareKey);
/* 125 */           license.setStatus(this.softwareKey.getAuthorizationStatus());
/* 126 */           license.store();
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/* 131 */     if (this.softwareKey != null) {
/* 132 */       this.softwareKey.reject();
/*     */     }
/* 134 */     throw new InvalidLicenseException();
/*     */   }
/*     */   
/*     */   public void registerLicenceExtensionRequest() {
/* 138 */     License license = checkLicense();
/* 139 */     license.setStatus(AuthorizationStatus.PENDING);
/* 140 */     license.store();
/*     */   }
/*     */   
/*     */   private void createSoftwareKey() {
/* 144 */     this.softwareKey = new SoftwareKey();
/* 145 */     this.seed = this.softwareKey.getTimestamp();
/* 146 */     SoftwareKey swk = SoftwareKeyImpl.getInstance();
/* 147 */     swk.initialize(this);
/* 148 */     if (CHECKMODE) {
/* 149 */       this.softwareKey.setHardwareID("C07764C36EA28D5324000F435AEA736F3415508B29A5F32F77394520F1890EC2");
/*     */     } else {
/* 151 */       this.softwareKey.setHardwareID(swk.getHWID());
/* 152 */       this.softwareKey.setHardwareInfo(swk.getHWInfo());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateLicense(License license, SoftwareKey softwareKey, RegistryEntry registration) {
/* 157 */     softwareKey.setHardwareInfo(SoftwareKeyImpl.getInstance().getHWInfo());
/* 158 */     boolean registered = false;
/* 159 */     if (softwareKey.getAuthorizationStatus() != AuthorizationStatus.AUTHORIZED) {
/* 160 */       softwareKey.authorize();
/* 161 */       softwareKey.setSubscriberID(registration.getSubscriberID());
/* 162 */       registered = true;
/* 163 */       log.debug("authorized software key (registration database)");
/*     */     } 
/* 165 */     if (!softwareKey.getAuthorizationID().equals(registration.getAuthorization().getAuthorizationID()) && 
/* 166 */       license.getStatus() == AuthorizationStatus.PENDING) {
/* 167 */       softwareKey.setAuthorizationID(registration.getAuthorization().getAuthorizationID());
/* 168 */       log.debug("updated software key (registration database)");
/* 169 */       registered = true;
/*     */     } 
/*     */     
/* 172 */     if (registered) {
/* 173 */       checkLicensedUserSessions(softwareKey);
/* 174 */       license.setStatus(softwareKey.getAuthorizationStatus());
/* 175 */       license.store();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkLicensedUserSessions(SoftwareKey softwareKey) {
/* 180 */     if (this.configuration.getInstallationType() == InstallationType.SERVER && 
/* 181 */       softwareKey.getUsers() == 0) {
/* 182 */       softwareKey.setUsers(this.configuration.getDefaultMaxSessionCount());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private License checkLicense() {
/* 188 */     File license = new File(this.configuration.getLicenseFile());
/* 189 */     return (license != null) ? License.load(license) : null;
/*     */   }
/*     */   
/*     */   private String getHardwareKey() {
/*     */     try {
/* 194 */       if (this.configuration.skipHardwareKeyCheck())
/* 195 */         return "NO-HWK-CHECK"; 
/* 196 */       if (CHECKMODE)
/* 197 */         return "HWK0000001"; 
/* 198 */       if (!HardwareKeyImpl.getInstance().isMigratedHardwareKey()) {
/* 199 */         return HardwareKeyImpl.getInstance().getHardwareKey();
/*     */       }
/* 201 */     } catch (SystemDriverNotInstalledException es) {
/* 202 */       log.debug("failed to read hardware key");
/* 203 */     } catch (UnavailableHardwareKeyException eu) {
/* 204 */       log.debug("failed to read hardware key");
/* 205 */     } catch (Exception e) {
/* 206 */       log.debug("problem with hwk-bridge", e);
/*     */     } 
/* 208 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean markHardwareKeyAsUsed() {
/* 213 */     if (this.configuration.skipHardwareKeyCheck() || CHECKMODE) {
/* 214 */       return true;
/*     */     }
/*     */     try {
/* 217 */       return HardwareKeyImpl.getInstance().migrateHardwareKey();
/* 218 */     } catch (Exception e) {
/* 219 */       log.debug("marking hardware key failed");
/* 220 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public long getSoftwareKeySeed() {
/* 225 */     return (this.seed != 0L) ? this.seed : this.softwareKey.getTimestamp();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\standalone\authorization\SoftwareKeyController.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */