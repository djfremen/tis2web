/*    */ package com.eoos.gm.tis2web.sas.server.implementation.serverside.handler.saab;
/*    */ 
/*    */ import com.eoos.datatype.marker.Configurable;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*    */ import com.eoos.gm.tis2web.sas.common.model.VIN;
/*    */ import com.eoos.gm.tis2web.sas.common.model.exception.InvalidVINException;
/*    */ import com.eoos.gm.tis2web.sas.common.model.reqres.SSARequest2;
/*    */ import com.eoos.gm.tis2web.sas.common.model.reqres.SSAResponse;
/*    */ import com.eoos.gm.tis2web.sas.common.model.reqres.SecurityAccessRequest;
/*    */ import com.eoos.gm.tis2web.sas.common.model.reqres.SecurityAccessResponse;
/*    */ import com.eoos.gm.tis2web.sas.common.model.reqres.SecurityRequestHandler;
/*    */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*    */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*    */ import com.eoos.gm.tis2web.vc.v2.vin.GlobalVINResolver;
/*    */ import com.eoos.gm.tis2web.vc.v2.vin.VIN;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import com.eoos.propcfg.SubConfigurationWrapper;
/*    */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class SaabSecurityHandler
/*    */   implements SecurityRequestHandler, Configurable
/*    */ {
/* 29 */   private static final Logger log = Logger.getLogger(SaabSecurityHandler.class);
/*    */   
/*    */   private DatabaseAdapter dbAdapter;
/*    */   
/*    */   public SaabSecurityHandler(Configuration configuration) {
/* 34 */     this.dbAdapter = new DatabaseAdapterImpl(DatabaseLink.openDatabase((Configuration)new SubConfigurationWrapper(configuration, "db.")));
/*    */   }
/*    */ 
/*    */   
/*    */   public SecurityAccessResponse handle(SecurityAccessRequest request, String sessionID) throws SecurityRequestHandler.Exception {
/* 39 */     if (request instanceof SSARequest2) {
/* 40 */       SSARequest2 ssaRequest = (SSARequest2)request;
/*    */ 
/*    */       
/* 43 */       VIN firstVIN = ssaRequest.getVINs().get(0);
/* 44 */       if (firstVIN != null && !validate(firstVIN, ClientContextProvider.getInstance().getContext(sessionID, false))) {
/* 45 */         throw new SecurityRequestHandler.Exception(new InvalidVINException(firstVIN));
/*    */       }
/*    */       
/*    */       try {
/* 49 */         Integer dbVersion = this.dbAdapter.getVersion();
/* 50 */         Integer freeshot = this.dbAdapter.getFreeShot();
/* 51 */         List securityCodes = this.dbAdapter.getSecurityCodes(ssaRequest.getVINs(), ssaRequest.getHardwareKey());
/* 52 */         SSAResponse sSAResponse = ssaRequest.createResponse(dbVersion, freeshot, securityCodes);
/*    */ 
/*    */         
/* 55 */         if (firstVIN != null) {
/* 56 */           setVC(firstVIN, ClientContextProvider.getInstance().getContext(sessionID, false));
/*    */         }
/* 58 */         return (SecurityAccessResponse)sSAResponse;
/*    */       }
/* 60 */       catch (Exception e) {
/* 61 */         log.error("unable to handle " + String.valueOf(request) + " - exception :" + e, e);
/* 62 */         log.error("rethrowing as wrapped exception");
/* 63 */         throw new SecurityRequestHandler.Exception(e);
/*    */       } 
/*    */     } 
/* 66 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 71 */     return "SAAB Security Handler";
/*    */   }
/*    */   
/*    */   private boolean validate(VIN _vin, ClientContext context) {
/*    */     try {
/* 76 */       VIN vin = VCFacade.getInstance(context).asVIN(_vin.toString());
/* 77 */       Set vcCfgs = GlobalVINResolver.getInstance(context).resolveVIN(vin);
/* 78 */       return (vcCfgs != null && vcCfgs.size() > 0);
/* 79 */     } catch (Exception e) {
/* 80 */       log.warn("unable to validate VIN: " + _vin + ", returning false - exception: " + e, e);
/*    */ 
/*    */       
/* 83 */       return false;
/*    */     } 
/*    */   }
/*    */   private void setVC(VIN _vin, ClientContext context) throws SecurityRequestHandler.Exception {
/*    */     try {
/* 88 */       VIN vin = VCFacade.getInstance(context).asVIN(_vin.toString());
/* 89 */       Set vcCfgs = GlobalVINResolver.getInstance(context).resolveVIN(vin);
/* 90 */       if (vcCfgs != null && vcCfgs.size() > 0) {
/* 91 */         IConfiguration cfg = (IConfiguration)CollectionUtil.getFirst(vcCfgs);
/* 92 */         VCFacade.getInstance(context).storeCfg(cfg, vin);
/*    */       } else {
/* 94 */         log.warn("unable to set VC (VIN: " + String.valueOf(_vin) + " not resolvable for context: " + context + ")");
/*    */       } 
/* 96 */     } catch (Exception e1) {
/* 97 */       log.error("unable to set VC - exception: " + e1, e1);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\server\implementation\serverside\handler\saab\SaabSecurityHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */