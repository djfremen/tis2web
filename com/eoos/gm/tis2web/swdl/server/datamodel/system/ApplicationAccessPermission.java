/*    */ package com.eoos.gm.tis2web.swdl.server.datamodel.system;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.gm.tis2web.acl.service.ACLService;
/*    */ import com.eoos.gm.tis2web.acl.service.ACLServiceProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*    */ import com.eoos.gm.tis2web.swdl.common.domain.application.Application;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ApplicationAccessPermission
/*    */ {
/* 20 */   private static final Logger log = Logger.getLogger(ApplicationAccessPermission.class);
/*    */   
/*    */   private ClientContext context;
/*    */   
/*    */   private Set authorizedResources;
/*    */   
/*    */   public ApplicationAccessPermission(ClientContext context) {
/* 27 */     this.context = context;
/* 28 */     SharedContextProxy scp = SharedContextProxy.getInstance(context);
/*    */     
/* 30 */     Map usrGroup2Manuf = scp.getUsrGroup2Manuf();
/* 31 */     String country = scp.getCountry();
/*    */     try {
/* 33 */       ACLService aclMI = ACLServiceProvider.getInstance().getService();
/* 34 */       this.authorizedResources = aclMI.getAuthorizedResources("Data", usrGroup2Manuf, country);
/* 35 */       if (log.isDebugEnabled()) {
/* 36 */         log.debug("accessible swdl resource ids (" + String.valueOf(context) + "):" + this.authorizedResources);
/*    */       }
/* 38 */     } catch (Exception fme) {
/* 39 */       log.error("unable to determine swdl resource ids, rethrowing wrapped exception - exception: " + fme, fme);
/* 40 */       throw new ExceptionWrapper(fme);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean check(Application application) {
/* 46 */     if (log.isDebugEnabled()) {
/* 47 */       log.debug("checking access to application: " + String.valueOf(application) + " for " + String.valueOf(this.context));
/*    */     }
/* 49 */     boolean retValue = false;
/*    */     try {
/* 51 */       Set<?> resourceIDs = application.getResourceIDs();
/* 52 */       if (resourceIDs != null && resourceIDs.size() != 0) {
/* 53 */         if (this.authorizedResources != null) {
/* 54 */           retValue = this.authorizedResources.containsAll(resourceIDs);
/* 55 */           if (log.isDebugEnabled()) {
/* 56 */             log.debug("...access " + (retValue ? "granted, all resource ids found" : (" DENIED, not all resource ids " + resourceIDs + " found")));
/*    */           }
/*    */         } else {
/* 59 */           if (log.isDebugEnabled()) {
/* 60 */             log.debug("...access - no authorized resource id");
/*    */           }
/* 62 */           retValue = false;
/*    */         }
/*    */       
/*    */       } else {
/*    */         
/* 67 */         log.debug("...access granted, since application is not protected");
/* 68 */         retValue = true;
/*    */       }
/*    */     
/* 71 */     } catch (Exception e) {
/* 72 */       log.warn("unable to check application access permission -error:" + e + ", denying access");
/*    */     } 
/* 74 */     return retValue;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\server\datamodel\system\ApplicationAccessPermission.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */