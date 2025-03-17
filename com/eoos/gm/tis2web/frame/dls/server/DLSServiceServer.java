/*     */ package com.eoos.gm.tis2web.frame.dls.server;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.dls.InvalidSoftwareKeyException;
/*     */ import com.eoos.gm.tis2web.frame.dls.LeaseRI;
/*     */ import com.eoos.gm.tis2web.frame.dls.SessionKey;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.Lease;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.SoftwareKey;
/*     */ import com.eoos.gm.tis2web.frame.export.ConfigurationServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.InvalidSessionException;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContext;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.scsm.v2.util.LockObjectProvider;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class DLSServiceServer
/*     */ {
/*  25 */   private static final Logger log = Logger.getLogger(DLSServiceServer.class);
/*     */   
/*  27 */   private static LockObjectProvider lockObjectProvider = new LockObjectProvider();
/*     */   
/*     */   private ClientContext context;
/*     */   
/*     */   private DLSServiceServer(ClientContext context) {
/*  32 */     this.context = context;
/*     */   }
/*     */ 
/*     */   
/*     */   public static DLSServiceServer getInstance(String sessionID) throws InvalidSessionException {
/*  37 */     synchronized (lockObjectProvider.getLockObject(sessionID)) {
/*  38 */       ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/*     */       
/*  40 */       if (context == null) {
/*  41 */         log.warn("unable to provide instance for session " + sessionID + ", throwing InvalidSessionException");
/*  42 */         throw new InvalidSessionException(sessionID);
/*     */       } 
/*  44 */       return getInstance(context);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static DLSServiceServer getInstance(ClientContext context) {
/*  50 */     synchronized (context.getLockObject()) {
/*  51 */       DLSServiceServer instance = (DLSServiceServer)context.getObject(DLSServiceServer.class);
/*  52 */       if (instance == null) {
/*  53 */         log.debug("creating DLS service server instance for " + context.getSessionID());
/*  54 */         instance = new DLSServiceServer(context);
/*  55 */         context.storeObject(DLSServiceServer.class, instance);
/*     */       } 
/*  57 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean register(SoftwareKey swk, String clientIP) {
/*  62 */     if (log.isDebugEnabled()) {
/*  63 */       log.debug("registering software key: " + swk + " (" + this.context.getSessionID() + ", " + clientIP + ")");
/*     */     }
/*     */     
/*  66 */     boolean ret = DatabaseAdapterProvider.getDatabaseAdapter().registerSoftwareKey(swk, this.context.getSessionID(), clientIP);
/*  67 */     if (log.isDebugEnabled()) {
/*  68 */       log.debug("...registration " + (ret ? "successful" : "FAILED"));
/*     */     }
/*  70 */     return ret;
/*     */   }
/*     */   
/*     */   public static boolean validateSoftwareKey(SoftwareKey swk) {
/*  74 */     log.debug("validating software key (delegation) ...");
/*  75 */     boolean ret = DatabaseAdapterProvider.getDatabaseAdapter().validateSoftwareKey(swk);
/*  76 */     log.debug("...valid=" + ret);
/*  77 */     return ret;
/*     */   }
/*     */   
/*     */   private Lease createLease(SoftwareKey swk, long duration) {
/*     */     try {
/*  82 */       MessageDigest digest = MessageDigest.getInstance("SHA-512");
/*  83 */       digest.update(swk.toExternal());
/*  84 */       digest.update(String.valueOf(System.currentTimeMillis()).getBytes());
/*  85 */       digest.update(this.context.getSessionID().getBytes());
/*  86 */       String securityToken = Util.toHexString(digest.digest());
/*  87 */       return (Lease)new LeaseRI(System.currentTimeMillis(), System.currentTimeMillis() + duration, securityToken);
/*  88 */     } catch (NoSuchAlgorithmException e) {
/*  89 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getUserGroup() {
/*  94 */     return (String)CollectionUtil.getFirst(SharedContext.getInstance(this.context).getUserGroups());
/*     */   }
/*     */   
/*     */   private long getMaximumSessionDuration() {
/*  98 */     long ret = -1L;
/*     */     try {
/* 100 */       ret = SharedContext.getInstance(this.context).getLoginInfo().getMaximumSessionDuration();
/* 101 */     } catch (Exception e) {
/* 102 */       log.warn("unable to determine maximum session duration, ignoring - exception: " + e, e);
/*     */     } 
/* 104 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   private long getValidityPeriod(String userGroup) {
/* 109 */     if (log.isDebugEnabled()) {
/* 110 */       log.debug("determining validity period for user group: " + String.valueOf(userGroup) + " ...");
/*     */     }
/* 112 */     long ret = 3600000L;
/*     */     try {
/* 114 */       ret = Util.parseMillis(ConfigurationServiceProvider.getService().getProperty("frame.dls.lease.expiration.delay." + Util.toLowerCase(userGroup)));
/* 115 */     } catch (Exception e) {
/* 116 */       log.warn("unable to determine lease expiration delay for user group: " + userGroup + ", using default");
/*     */       try {
/* 118 */         ret = Util.parseMillis(ConfigurationServiceProvider.getService().getProperty("frame.dls.lease.expiration.delay"));
/* 119 */       } catch (Exception ee) {
/* 120 */         log.warn("unable to determine default lease expiration delay, using 1 hour");
/*     */       } 
/*     */     } 
/* 123 */     if (log.isDebugEnabled()) {
/* 124 */       log.debug("....result: " + ret + " ms");
/*     */     }
/* 126 */     return ret;
/*     */   }
/*     */   
/*     */   public Lease requestLease(SoftwareKey swk, String clientIP) throws InvalidSoftwareKeyException {
/* 130 */     if (log.isDebugEnabled()) {
/* 131 */       log.debug("requesting lease for software key: " + swk + " (" + this.context.getSessionID() + ", " + clientIP + ") ...");
/*     */     }
/*     */     
/* 134 */     if (!validateSoftwareKey(swk)) {
/* 135 */       log.debug("the software key is invalid, throwing exception");
/* 136 */       throw new InvalidSoftwareKeyException();
/*     */     } 
/*     */     
/* 139 */     long validityPeriod = getValidityPeriod(getUserGroup());
/* 140 */     long maxSessionDuration = getMaximumSessionDuration();
/* 141 */     if (maxSessionDuration != -1L) {
/* 142 */       log.debug("...maximum session duration is set (" + maxSessionDuration + " ms), determining minimum");
/* 143 */       validityPeriod = Math.min(validityPeriod, maxSessionDuration);
/*     */     } 
/* 145 */     if (log.isDebugEnabled()) {
/* 146 */       log.debug("...validity period is " + Util.formatDuration(validityPeriod, "${days} day(s), ${hours} hour(s), ${minutes} minute(s)"));
/*     */     }
/* 148 */     Lease lease = createLease(swk, validityPeriod);
/* 149 */     SharedContext sc = SharedContext.getInstance(this.context);
/* 150 */     if (!DatabaseAdapterProvider.getDatabaseAdapter().registerLease(swk, lease, this.context.getSessionID(), getUserGroup(), clientIP, sc.getDealercode(), sc.getCountry())) {
/* 151 */       throw new IllegalStateException("unable to register lease ");
/*     */     }
/* 153 */     return lease;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getUserGroup(SoftwareKey swk, Lease lease) {
/* 158 */     if (swk instanceof SessionKey) {
/* 159 */       ClientContext context = ClientContextProvider.getInstance().getContext(((SessionKey)swk).getSessionID());
/* 160 */       return (String)CollectionUtil.getFirst(SharedContext.getInstance(context).getUserGroups());
/*     */     } 
/* 162 */     return DatabaseAdapterProvider.getDatabaseAdapter().getUserGroup(swk, lease);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Collection getSessionIDs(SoftwareKey swk) {
/* 167 */     if (swk instanceof SessionKey) {
/* 168 */       return Collections.singleton(((SessionKey)swk).getSessionID());
/*     */     }
/* 170 */     return DatabaseAdapterProvider.getDatabaseAdapter().getSessionIDs(swk);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void deleteSoftwareKeys(Collection userids) throws Exception {
/* 175 */     DatabaseAdapterProvider.getDatabaseAdapter().deleteSoftwareKeys(userids);
/* 176 */     LeaseValidationProvider.clearCache();
/*     */   }
/*     */   
/*     */   public static void deleteLeases(Collection userids) throws Exception {
/* 180 */     DatabaseAdapterProvider.getDatabaseAdapter().deleteLeases(userids);
/* 181 */     LeaseValidationProvider.clearCache();
/*     */   }
/*     */   
/*     */   public boolean checkClientStartup(SoftwareKey swk, String clientIP) {
/* 185 */     String maxCount = SoftwareKeyCheckServer.getValueOf(SoftwareKeyCheckServer.getPortal(this.context.getSessionID()));
/* 186 */     if (Util.isNullOrEmpty(maxCount)) {
/* 187 */       return true;
/*     */     }
/* 189 */     return (swk != null) ? register(swk, clientIP) : false;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dls\server\DLSServiceServer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */