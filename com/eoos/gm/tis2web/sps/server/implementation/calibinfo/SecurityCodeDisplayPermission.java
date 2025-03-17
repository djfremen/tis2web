/*    */ package com.eoos.gm.tis2web.sps.server.implementation.calibinfo;
/*    */ 
/*    */ import com.eoos.gm.tis2web.acl.service.ACLService;
/*    */ import com.eoos.gm.tis2web.acl.service.ACLServiceProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import java.util.HashSet;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class SecurityCodeDisplayPermission
/*    */ {
/* 15 */   private static final Logger log = Logger.getLogger(SecurityCodeDisplayPermission.class);
/*    */   
/*    */   private boolean permitted = false;
/*    */   
/*    */   private SecurityCodeDisplayPermission(ClientContext context) {
/*    */     try {
/* 21 */       ACLService aclService = ACLServiceProvider.getInstance().getService();
/* 22 */       Map usrGroup2Manuf = context.getSharedContext().getUsrGroup2ManufMap();
/* 23 */       Set<String> tmp = new HashSet();
/* 24 */       tmp.add("security-code-access");
/* 25 */       this.permitted = (aclService.getAuthorizedResources("AdapterData", tmp, usrGroup2Manuf, context.getSharedContext().getCountry()).size() != 0);
/*    */     }
/* 27 */     catch (Exception e) {
/* 28 */       log.warn("unable to decide, setting permission to false - exception: " + e);
/* 29 */       this.permitted = false;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static SecurityCodeDisplayPermission getInstance(ClientContext context) {
/* 34 */     synchronized (context.getLockObject()) {
/* 35 */       SecurityCodeDisplayPermission instance = (SecurityCodeDisplayPermission)context.getObject(SecurityCodeDisplayPermission.class);
/* 36 */       if (instance == null) {
/* 37 */         instance = new SecurityCodeDisplayPermission(context);
/* 38 */         context.storeObject(DealerVCIDisplayPermission.class, instance);
/*    */       } 
/* 40 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean check() {
/* 45 */     if (ApplicationContext.getInstance().developMode()) {
/* 46 */       log.debug("DEVELOP MODE !!!!! allowing display of security code");
/* 47 */       return true;
/*    */     } 
/* 49 */     return this.permitted;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\calibinfo\SecurityCodeDisplayPermission.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */