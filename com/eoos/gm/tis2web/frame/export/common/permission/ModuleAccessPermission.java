/*    */ package com.eoos.gm.tis2web.frame.export.common.permission;
/*    */ 
/*    */ import com.eoos.gm.tis2web.acl.service.ACLService;
/*    */ import com.eoos.gm.tis2web.acl.service.ACLServiceProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.service.Module;
/*    */ import java.util.Collections;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ModuleAccessPermission
/*    */ {
/* 19 */   private static final Logger log = Logger.getLogger(ModuleAccessPermission.class);
/*    */   
/*    */   private ClientContext context;
/*    */   
/*    */   private Set permittedModules;
/*    */   
/*    */   private ModuleAccessPermission(ClientContext context) {
/* 26 */     this.context = context;
/*    */     try {
/* 28 */       ACLService aclService = ACLServiceProvider.getInstance().getService();
/* 29 */       Map usrGroup2Manuf = context.getSharedContext().getUsrGroup2ManufMap();
/* 30 */       this.permittedModules = aclService.getAuthorizedResources("Application", ACLService.ALL_RESOURCE_VALUES, usrGroup2Manuf, context.getSharedContext().getCountry());
/* 31 */       log.debug("permitted modules: " + String.valueOf(this.permittedModules));
/*    */     }
/* 33 */     catch (Exception e) {
/* 34 */       log.warn("unable to retrieve collection of permitted modules - exception:" + e, e);
/* 35 */       log.warn("disabling access to all modules");
/* 36 */       this.permittedModules = Collections.EMPTY_SET;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static ModuleAccessPermission getInstance(ClientContext context) {
/* 41 */     synchronized (context.getLockObject()) {
/* 42 */       ModuleAccessPermission instance = (ModuleAccessPermission)context.getObject(ModuleAccessPermission.class);
/* 43 */       if (instance == null) {
/* 44 */         instance = new ModuleAccessPermission(context);
/* 45 */         context.storeObject(ModuleAccessPermission.class, instance);
/*    */       } 
/* 47 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean check(Module module) {
/* 52 */     return check(module.getType());
/*    */   }
/*    */   
/*    */   public boolean check(String type) {
/* 56 */     boolean retValue = false;
/*    */     try {
/* 58 */       if ("admin".equals(type) && ApplicationContext.getInstance().isStandalone()) {
/* 59 */         retValue = true;
/* 60 */       } else if (ApplicationContext.getInstance().developMode()) {
/* 61 */         log.debug("DEVELOP MODE !!!!!allowing access to module " + String.valueOf(type));
/* 62 */         retValue = true;
/*    */       } else {
/* 64 */         retValue = this.permittedModules.contains(type);
/*    */       } 
/* 66 */     } catch (Exception e) {
/* 67 */       log.warn("unable to determine access permission for context " + String.valueOf(this.context) + " and moduletype " + String.valueOf(type) + ", returning false");
/* 68 */       retValue = false;
/*    */     } 
/* 70 */     return retValue;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\permission\ModuleAccessPermission.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */