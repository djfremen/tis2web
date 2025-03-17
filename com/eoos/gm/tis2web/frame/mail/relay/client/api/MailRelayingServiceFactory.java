/*    */ package com.eoos.gm.tis2web.frame.mail.relay.client.api;
/*    */ 
/*    */ import com.eoos.gm.tis2web.common.AuthenticationQuery;
/*    */ import com.eoos.gm.tis2web.frame.dls.SessionKey;
/*    */ import com.eoos.gm.tis2web.frame.dls.client.api.DLSService;
/*    */ import com.eoos.gm.tis2web.frame.dls.client.api.DLSServiceFactory;
/*    */ import com.eoos.gm.tis2web.frame.dls.client.api.Lease;
/*    */ import com.eoos.gm.tis2web.frame.dls.client.api.SoftwareKey;
/*    */ import com.eoos.gm.tis2web.frame.mail.relay.client.MailRelayingServiceImpl;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.util.Collection;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class MailRelayingServiceFactory
/*    */ {
/* 20 */   private static final Logger log = Logger.getLogger(MailRelayingServiceFactory.class);
/*    */   
/* 22 */   public static final MailRelayingService DISABLED = new MailRelayingService()
/*    */     {
/*    */       public void send(String sender, Collection replyTo, Collection recipients, String subject, String text, Collection eoosDataSource) throws Exception {
/* 25 */         throw new Exception("service is disabled");
/*    */       }
/*    */     };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static MailRelayingService createService(AuthenticationQuery authenticationCallback) {
/*    */     MailRelayingService mailRelayingService;
/* 35 */     log.debug("creating mail relaying service..."); try {
/*    */       SessionKey sessionKey;
/* 37 */       String sessionID = System.getProperty("session.id");
/*    */       
/* 39 */       Lease lease = null;
/* 40 */       if (Util.isNullOrEmpty(sessionID)) {
/* 41 */         log.debug("...offline mode");
/* 42 */         DLSService dlsService = DLSServiceFactory.createService(authenticationCallback, System.getProperties());
/* 43 */         SoftwareKey swk = dlsService.getSoftwareKey();
/* 44 */         lease = dlsService.getNewestValidLease();
/*    */       } else {
/* 46 */         log.debug("...detected session identifier, assuming online mode");
/* 47 */         sessionKey = new SessionKey(sessionID);
/*    */       } 
/*    */       
/* 50 */       mailRelayingService = createService((SoftwareKey)sessionKey, lease, authenticationCallback);
/*    */     }
/* 52 */     catch (Exception e) {
/* 53 */       log.error("unable to create service, returning disabled proxy - exception: " + e, e);
/* 54 */       mailRelayingService = DISABLED;
/*    */     } 
/* 56 */     return mailRelayingService;
/*    */   }
/*    */ 
/*    */   
/*    */   public static MailRelayingService createService(SoftwareKey swk, Lease lease, AuthenticationQuery authenticationCallback) {
/* 61 */     MailRelayingService ret = null;
/*    */     try {
/* 63 */       MailRelayingServiceImpl mailRelayingServiceImpl = new MailRelayingServiceImpl(swk, lease, authenticationCallback);
/* 64 */     } catch (Exception e) {
/* 65 */       log.error("unable to create service, returning disabled proxy - exception: " + e, e);
/* 66 */       ret = DISABLED;
/*    */     } 
/* 68 */     return ret;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\mail\relay\client\api\MailRelayingServiceFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */