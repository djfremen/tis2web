/*    */ package com.eoos.gm.tis2web.sas.server.implementation.clientside;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.hwk.exception.UnavailableHardwareKeyException;
/*    */ import com.eoos.gm.tis2web.sas.client.hwk.HWKProviderFacade;
/*    */ import com.eoos.gm.tis2web.sas.client.system.SASClientContextProvider;
/*    */ import com.eoos.gm.tis2web.sas.common.model.HardwareKey;
/*    */ import com.eoos.gm.tis2web.sas.common.model.exception.InvalidHardwareKeyException;
/*    */ import com.eoos.gm.tis2web.sas.common.model.reqres.SCASKARequestWithHWK;
/*    */ import com.eoos.gm.tis2web.sas.common.model.reqres.SCASKARequestWithoutHWK;
/*    */ import com.eoos.gm.tis2web.sas.common.model.reqres.SecurityAccessRequest;
/*    */ import com.eoos.gm.tis2web.sas.common.model.reqres.SecurityAccessResponse;
/*    */ import com.eoos.gm.tis2web.sas.common.model.reqres.SecurityRequestHandler;
/*    */ import com.eoos.gm.tis2web.sas.common.system.ISASServer;
/*    */ import com.eoos.gm.tis2web.sas.common.system.SASServerProvider;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LocalRequestHandler
/*    */   implements SecurityRequestHandler
/*    */ {
/* 26 */   private static final Logger log = Logger.getLogger(LocalRequestHandler.class);
/*    */   
/* 28 */   private static LocalRequestHandler instance = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized LocalRequestHandler getInstance() {
/* 35 */     if (instance == null) {
/* 36 */       instance = new LocalRequestHandler();
/*    */     }
/* 38 */     return instance;
/*    */   }
/*    */   
/*    */   public SecurityAccessResponse handle(SecurityAccessRequest _request, String sessionID) throws SecurityRequestHandler.Exception {
/* 42 */     if (_request instanceof com.eoos.gm.tis2web.sas.common.model.reqres.SCASKARequest) {
/* 43 */       if (_request instanceof SCASKARequestWithHWK) {
/* 44 */         SCASKARequestWithHWK request = (SCASKARequestWithHWK)_request;
/*    */         try {
/* 46 */           HardwareKey hardwareKey = null;
/*    */           try {
/* 48 */             hardwareKey = HWKProviderFacade.getInstance().getHardwareKey();
/* 49 */           } catch (UnavailableHardwareKeyException e) {
/* 50 */             log.debug("hardware key is not available");
/*    */           } 
/* 52 */           ISASServer server = SASServerProvider.getInstance().getServer(SASClientContextProvider.getInstance().getContext().getSessionID());
/* 53 */           if (server.isValid(hardwareKey)) {
/* 54 */             return (SecurityAccessResponse)request.createResponse(hardwareKey);
/*    */           }
/* 56 */           throw new InvalidHardwareKeyException();
/*    */         }
/* 58 */         catch (Exception e) {
/* 59 */           throw new SecurityRequestHandler.Exception(e);
/*    */         } 
/* 61 */       }  if (_request instanceof SCASKARequestWithoutHWK) {
/* 62 */         SCASKARequestWithoutHWK request = (SCASKARequestWithoutHWK)_request;
/* 63 */         return (SecurityAccessResponse)request.createResponse();
/*    */       } 
/*    */     } 
/*    */     
/* 67 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\server\implementation\clientside\LocalRequestHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */