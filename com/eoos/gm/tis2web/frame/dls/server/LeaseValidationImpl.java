/*    */ package com.eoos.gm.tis2web.frame.dls.server;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.dls.ILeaseInternal;
/*    */ import com.eoos.gm.tis2web.frame.dls.SessionKey;
/*    */ import com.eoos.gm.tis2web.frame.dls.client.api.Lease;
/*    */ import com.eoos.gm.tis2web.frame.dls.client.api.SoftwareKey;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.Tis2webUtil;
/*    */ import com.eoos.scsm.v2.reflect.ReflectionUtil;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class LeaseValidationImpl
/*    */   implements LeaseValidation
/*    */ {
/* 15 */   private static final Logger log = Logger.getLogger(LeaseValidationImpl.class);
/*    */   
/* 17 */   private static LeaseValidationImpl instance = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 22 */   private LeaseValidation cachedBackend = (LeaseValidation)ReflectionUtil.createCachingProxy(DatabaseAdapterProvider.getDatabaseAdapter(), Tis2webUtil.createStdCache(), ReflectionUtil.CachingProxyCallback.STD);
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized LeaseValidationImpl getInstance() {
/* 27 */     if (instance == null) {
/* 28 */       instance = new LeaseValidationImpl();
/*    */     }
/* 30 */     return instance;
/*    */   }
/*    */   
/*    */   public static synchronized void clearCache() {
/* 34 */     instance = null;
/*    */   }
/*    */   
/*    */   public boolean validateLease(SoftwareKey swk, Lease lease) {
/* 38 */     if (swk instanceof SessionKey) {
/* 39 */       String sessionID = ((SessionKey)swk).getSessionID();
/* 40 */       log.debug("validating session key: " + sessionID);
/* 41 */       return ClientContextProvider.getInstance().isActive(sessionID);
/*    */     } 
/* 43 */     boolean ret = (swk != null);
/* 44 */     ret = (ret && lease != null);
/* 45 */     ret = (ret && ((ILeaseInternal)lease).getExpirationDate() > System.currentTimeMillis());
/* 46 */     ret = (ret && this.cachedBackend.validateLease(swk, lease));
/* 47 */     return ret;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dls\server\LeaseValidationImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */