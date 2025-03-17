/*    */ package com.eoos.gm.tis2web.frame.export.common.hwk;
/*    */ 
/*    */ import com.eoos.gm.tis2web.acl.service.ACLService;
/*    */ import com.eoos.gm.tis2web.acl.service.ACLServiceProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import java.util.Collections;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NoHWKPermission
/*    */ {
/* 18 */   private static final Logger log = Logger.getLogger(NoHWKPermission.class);
/*    */   
/*    */   public static final String RESOURCE_VALUE_PRIVILED = "no-hwk";
/*    */   
/*    */   private Set resourceValues;
/*    */   
/*    */   private NoHWKPermission(ClientContext context) {
/*    */     try {
/* 26 */       ACLService aclService = ACLServiceProvider.getInstance().getService();
/* 27 */       Map usrGroup2Manuf = context.getSharedContext().getUsrGroup2ManufMap();
/* 28 */       this.resourceValues = aclService.getAuthorizedResources("AdapterData", ACLService.ALL_RESOURCE_VALUES, usrGroup2Manuf, context.getSharedContext().getCountry());
/*    */     }
/* 30 */     catch (Exception e) {
/* 31 */       log.warn("unable to retrieve collection of ACL adapter data - exception:" + e, e);
/* 32 */       log.warn("disabling privileged access for sas");
/* 33 */       this.resourceValues = Collections.EMPTY_SET;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static NoHWKPermission getInstance(ClientContext context) {
/* 38 */     synchronized (context.getLockObject()) {
/* 39 */       NoHWKPermission instance = (NoHWKPermission)context.getObject(NoHWKPermission.class);
/* 40 */       if (instance == null) {
/* 41 */         instance = new NoHWKPermission(context);
/* 42 */         context.storeObject(NoHWKPermission.class, instance);
/*    */       } 
/* 44 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean check() {
/* 49 */     return (this.resourceValues.contains("no-hwk") || ApplicationContext.getInstance().developMode());
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\hwk\NoHWKPermission.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */