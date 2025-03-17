/*    */ package com.eoos.gm.tis2web.frame.export.common.permission;
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
/*    */ public class ComponentAccessPermission
/*    */ {
/* 18 */   private static final Logger log = Logger.getLogger(ComponentAccessPermission.class);
/*    */   
/*    */   private ClientContext context;
/*    */   
/*    */   private Set permittedComponents;
/*    */   
/*    */   private ComponentAccessPermission(ClientContext context) {
/* 25 */     this.context = context;
/*    */     try {
/* 27 */       ACLService aclService = ACLServiceProvider.getInstance().getService();
/* 28 */       Map usrGroup2Manuf = context.getSharedContext().getUsrGroup2ManufMap();
/* 29 */       this.permittedComponents = aclService.getAuthorizedResources("Component", ACLService.ALL_RESOURCE_VALUES, usrGroup2Manuf, context.getSharedContext().getCountry());
/* 30 */       log.debug("permitted components: " + String.valueOf(this.permittedComponents));
/*    */     }
/* 32 */     catch (Exception e) {
/* 33 */       log.warn("unable to retrieve collection of permitted components - exception:" + e, e);
/* 34 */       log.warn("disabling access to all components");
/* 35 */       this.permittedComponents = Collections.EMPTY_SET;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static ComponentAccessPermission getInstance(ClientContext context) {
/* 40 */     synchronized (context.getLockObject()) {
/* 41 */       ComponentAccessPermission instance = (ComponentAccessPermission)context.getObject(ComponentAccessPermission.class);
/* 42 */       if (instance == null) {
/* 43 */         instance = new ComponentAccessPermission(context);
/* 44 */         context.storeObject(ComponentAccessPermission.class, instance);
/*    */       } 
/* 46 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean check(String component) {
/*    */     try {
/* 52 */       if (ApplicationContext.getInstance().developMode()) {
/* 53 */         if ("spslog.entries.all".equals(component) && this.context.isPublicAccess()) {
/* 54 */           return false;
/*    */         }
/* 56 */         log.debug("DEVELOP MODE !!!!!allowing access to component " + String.valueOf(component));
/* 57 */         return true;
/*    */       } 
/* 59 */       return this.permittedComponents.contains(component);
/*    */     }
/* 61 */     catch (Exception e) {
/* 62 */       log.warn("unable to determine access permission for context " + String.valueOf(this.context) + " and component " + String.valueOf(component) + ", returning false");
/* 63 */       return false;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\permission\ComponentAccessPermission.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */