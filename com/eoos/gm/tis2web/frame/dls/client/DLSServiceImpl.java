/*     */ package com.eoos.gm.tis2web.frame.dls.client;
/*     */ 
/*     */ import com.eoos.gm.tis2web.common.AuthenticationQuery;
/*     */ import com.eoos.gm.tis2web.common.AuthenticatorCI;
/*     */ import com.eoos.gm.tis2web.frame.dls.ILeaseInternal;
/*     */ import com.eoos.gm.tis2web.frame.dls.LeaseRI;
/*     */ import com.eoos.gm.tis2web.frame.dls.SoftwareKeyRI;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.DLSManagement;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.DLSService;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.ExpiredLeaseException;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.Lease;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.MissingLeaseException;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.SoftwareKey;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.softwarekey.common.SoftwareKey;
/*     */ import com.eoos.softwarekey.common.SoftwareKeyCallback;
/*     */ import com.eoos.softwarekey.impl.SoftwareKeyImpl;
/*     */ import java.io.File;
/*     */ import java.net.Authenticator;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class DLSServiceImpl
/*     */   implements DLSService, DLSManagement {
/*  34 */   private static final Logger log = Logger.getLogger(DLSServiceImpl.class);
/*     */   
/*  36 */   private final Object SYNC_COMPUTED_SWK = new Object();
/*     */   
/*  38 */   private SoftwareKey computedSWK = null;
/*     */   
/*     */   private Callback callback;
/*     */   
/*     */   private final Object SYNC_REGISTRY;
/*     */   private LeaseRegistry leaseRegistry;
/*     */   private SoftwareKeyRegistry swkRegistry;
/*     */   private static final long ONE_HOUR = 3600000L;
/*     */   
/*     */   public DLSServiceImpl(AuthenticationQuery proxyAuthenticationCallback, Configuration config) throws MalformedURLException {
/*  48 */     this(proxyAuthenticationCallback, config, new Callback()
/*     */         {
/*     */           public File getHomeDir() {
/*  51 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface Callback
/*     */   {
/*     */     File getHomeDir();
/*     */   }
/*     */ 
/*     */   
/*     */   public DLSServiceImpl(AuthenticationQuery proxyAuthenticationCallback, Configuration config, Callback callback) throws MalformedURLException {
/*  65 */     this.SYNC_REGISTRY = new Object();
/*  66 */     this.leaseRegistry = null;
/*  67 */     this.swkRegistry = null; if (proxyAuthenticationCallback != null) {
/*     */       log.debug("setting authenticator"); Authenticator.setDefault((Authenticator)new AuthenticatorCI(proxyAuthenticationCallback));
/*     */     } 
/*  70 */     this.callback = callback; } private LeaseRegistry getLeaseRegistry() { synchronized (this.SYNC_REGISTRY) {
/*  71 */       if (this.leaseRegistry == null) {
/*  72 */         this.leaseRegistry = new LeaseRegistry_Dir(this.callback.getHomeDir());
/*     */       }
/*  74 */       return this.leaseRegistry;
/*     */     }  }
/*     */ 
/*     */ 
/*     */   
/*     */   private SoftwareKeyRegistry getSWKRegistry() {
/*  80 */     synchronized (this.SYNC_REGISTRY) {
/*  81 */       if (this.swkRegistry == null)
/*     */       {
/*     */         
/*  84 */         this.swkRegistry = SoftwareKeyRegistry_Dir.createGlobalInstance();
/*     */       }
/*  86 */       return this.swkRegistry;
/*     */     } 
/*     */   }
/*     */   
/*     */   public long getExpirationDate(Lease lease) {
/*  91 */     return ((ILeaseInternal)lease).getExpirationDate();
/*     */   }
/*     */   
/*     */   public Lease getLease(DLSService.LeaseSelection leaseSelection) throws MissingLeaseException, ExpiredLeaseException {
/*  95 */     log.debug("retrieving lease...");
/*  96 */     Lease lease = null;
/*     */     
/*  98 */     Map leases = getLeaseRegistry().getLeaseMap();
/*  99 */     if (leases.size() > 1) {
/* 100 */       log.debug("...more than one lease available, using selection callback");
/* 101 */       lease = leaseSelection.selectLease(new HashSet(leases.entrySet()));
/*     */     }
/* 103 */     else if (!leases.isEmpty()) {
/* 104 */       lease = (Lease)CollectionUtil.getFirst(leases.values());
/*     */     } 
/*     */     
/* 107 */     if (lease == null) {
/* 108 */       log.debug("...no lease available, throwing MissingLeaseException");
/* 109 */       throw new MissingLeaseException();
/* 110 */     }  if (!isValid(lease)) {
/* 111 */       log.debug("...the lease is invalid, throwing ExpiredLeaseException");
/* 112 */       throw new ExpiredLeaseException(lease);
/*     */     } 
/* 114 */     log.debug("...returning lease " + lease);
/* 115 */     return lease;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isValid(Lease lease) {
/* 123 */     log.debug("checking validity for lease " + String.valueOf(lease));
/* 124 */     ILeaseInternal _lease = (ILeaseInternal)lease;
/* 125 */     boolean ret = (_lease.getExpirationDate() > System.currentTimeMillis());
/* 126 */     ret = (ret && _lease.getCreationDate() < System.currentTimeMillis() + 3600000L);
/* 127 */     log.debug("...lease is " + (ret ? "valid" : "INVALID"));
/* 128 */     return ret;
/*     */   }
/*     */   
/*     */   private SoftwareKey getHWIDProvider() {
/* 132 */     SoftwareKey ret = SoftwareKeyImpl.getInstance();
/* 133 */     ret.initialize(new SoftwareKeyCallback()
/*     */         {
/*     */           public long getSoftwareKeySeed() {
/* 136 */             return 0L;
/*     */           }
/*     */         });
/* 139 */     return ret;
/*     */   }
/*     */   
/*     */   public SoftwareKey computeSoftwareKey() {
/* 143 */     synchronized (this.SYNC_COMPUTED_SWK) {
/* 144 */       if (this.computedSWK == null) {
/* 145 */         log.debug("computing swk...");
/*     */         
/* 147 */         String hwID = getHWIDProvider().getHWID();
/* 148 */         this.computedSWK = (SoftwareKey)new SoftwareKeyRI(hwID);
/* 149 */         log.debug("...done");
/*     */       } 
/* 151 */       return this.computedSWK;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private SoftwareKey getStoredSoftwareKey() {
/* 157 */     return getSWKRegistry().getSoftwareKey();
/*     */   }
/*     */   
/*     */   public boolean validateSoftwarekey() {
/* 161 */     log.debug("validating software key...");
/* 162 */     boolean ret = false;
/* 163 */     SoftwareKey storedSWK = getStoredSoftwareKey();
/* 164 */     if (storedSWK != null) {
/* 165 */       ret = getHWIDProvider().isValidHWID(storedSWK.toString());
/* 166 */       log.debug("....stored key is " + (ret ? "valid" : "INVALID"));
/*     */     } else {
/* 168 */       log.debug("...there is no stored software key, returning false");
/*     */     } 
/*     */     
/* 171 */     return ret;
/*     */   }
/*     */   
/*     */   public void registerSoftwareKey(SoftwareKey swk) {
/* 175 */     log.debug("registering software key...");
/* 176 */     getSWKRegistry().registerSoftwareKey(swk);
/* 177 */     log.debug("...done");
/*     */   }
/*     */   
/*     */   public void registerLease(Lease lease, URL serverURL) {
/* 181 */     log.debug("registering lease " + lease + " for " + serverURL + " ...");
/* 182 */     getLeaseRegistry().registerLease(lease, serverURL);
/* 183 */     log.debug("...done");
/*     */   }
/*     */   
/*     */   public SoftwareKey getSoftwareKey() {
/* 187 */     return getStoredSoftwareKey();
/*     */   }
/*     */   
/*     */   public URL getURL(Lease lease) {
/* 191 */     URL ret = null;
/* 192 */     log.debug("retrieving url for lease " + String.valueOf(lease) + " ...");
/* 193 */     Map leaseMap = getLeaseRegistry().getLeaseMap();
/* 194 */     if (!Util.isNullOrEmpty(leaseMap)) {
/* 195 */       for (Iterator<Map.Entry> iter = leaseMap.entrySet().iterator(); iter.hasNext() && ret == null; ) {
/* 196 */         Map.Entry entry = iter.next();
/* 197 */         if (entry.getValue().equals(lease)) {
/* 198 */           ret = (URL)entry.getKey();
/* 199 */           log.debug("...url found: " + ret);
/*     */         } 
/*     */       } 
/*     */     } else {
/* 203 */       log.debug("...lease map is empty");
/*     */     } 
/* 205 */     return ret;
/*     */   }
/*     */   
/*     */   public void deleteSoftwareKey() {
/* 209 */     getSWKRegistry().deleteSoftwareKey();
/* 210 */     getLeaseRegistry().clear();
/*     */   }
/*     */   
/*     */   public Collection getValidLeases() {
/* 214 */     log.debug("retrieving valid leases ...");
/* 215 */     Map leaseMap = getLeaseRegistry().getLeaseMap();
/* 216 */     if (!Util.isNullOrEmpty(leaseMap)) {
/* 217 */       Collection<Lease> ret = new LinkedHashSet();
/* 218 */       for (Iterator<Map.Entry> iter = leaseMap.entrySet().iterator(); iter.hasNext(); ) {
/* 219 */         Lease lease = (Lease)((Map.Entry)iter.next()).getValue();
/* 220 */         if (isValid(lease)) {
/* 221 */           ret.add(lease);
/* 222 */           log.debug("...added valid lease: " + lease);
/*     */         } 
/*     */       } 
/* 225 */       return ret;
/*     */     } 
/* 227 */     return Collections.EMPTY_SET;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean validateAuthorization() {
/* 232 */     boolean ret = validateSoftwarekey();
/* 233 */     return (ret && !Util.isNullOrEmpty(getValidLeases()));
/*     */   }
/*     */   
/*     */   public Lease getNewestValidLease() {
/* 237 */     LeaseRI ret = null;
/* 238 */     for (Iterator<LeaseRI> iter = getValidLeases().iterator(); iter.hasNext(); ) {
/* 239 */       LeaseRI lease = iter.next();
/* 240 */       if (ret == null || lease.getCreationDate() > ret.getCreationDate()) {
/* 241 */         ret = lease;
/*     */       }
/*     */     } 
/* 244 */     return (Lease)ret;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dls\client\DLSServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */