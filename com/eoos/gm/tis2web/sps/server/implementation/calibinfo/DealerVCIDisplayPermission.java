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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DealerVCIDisplayPermission
/*    */ {
/* 18 */   private static final Logger log = Logger.getLogger(DealerVCIDisplayPermission.class);
/*    */   
/*    */   private boolean permitted = false;
/*    */   
/*    */   private DealerVCIDisplayPermission(ClientContext context) {
/*    */     try {
/* 24 */       ACLService aclService = ACLServiceProvider.getInstance().getService();
/* 25 */       Map usrGroup2Manuf = context.getSharedContext().getUsrGroup2ManufMap();
/* 26 */       Set<String> tmp = new HashSet();
/* 27 */       tmp.add("dealerVCI");
/* 28 */       this.permitted = (aclService.getAuthorizedResources("Component", tmp, usrGroup2Manuf, context.getSharedContext().getCountry()).size() != 0);
/*    */     }
/* 30 */     catch (Exception e) {
/* 31 */       log.warn("unable to decide, setting permission to false - exception: " + e);
/* 32 */       this.permitted = false;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static DealerVCIDisplayPermission getInstance(ClientContext context) {
/* 37 */     synchronized (context.getLockObject()) {
/* 38 */       DealerVCIDisplayPermission instance = (DealerVCIDisplayPermission)context.getObject(DealerVCIDisplayPermission.class);
/* 39 */       if (instance == null) {
/* 40 */         instance = new DealerVCIDisplayPermission(context);
/* 41 */         context.storeObject(DealerVCIDisplayPermission.class, instance);
/*    */       } 
/* 43 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean check() {
/* 48 */     if (ApplicationContext.getInstance().developMode()) {
/* 49 */       log.debug("DEVELOP MODE !!!!! allowing display of dealer vci");
/* 50 */       return true;
/*    */     } 
/* 52 */     return this.permitted;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\calibinfo\DealerVCIDisplayPermission.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */