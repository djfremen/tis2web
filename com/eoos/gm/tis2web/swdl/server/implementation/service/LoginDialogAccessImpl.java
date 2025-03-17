/*    */ package com.eoos.gm.tis2web.swdl.server.implementation.service;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.permission.ModuleAccessPermission;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContext;
/*    */ import com.eoos.gm.tis2web.frame.login.dialog.LoginDialogAccess;
/*    */ import com.eoos.gm.tis2web.swdl.common.domain.application.Version;
/*    */ import com.eoos.html.ResultObject;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class LoginDialogAccessImpl
/*    */   implements LoginDialogAccess
/*    */ {
/* 18 */   private static final Logger log = Logger.getLogger(LoginDialogAccessImpl.class);
/*    */   
/* 20 */   protected static final Object KEY_NOTIFIED_VERSIONS = "swdl.notified.versions";
/*    */ 
/*    */   
/*    */   public String getDialogCode(Map params, ClientContext context, final ResultObject next) {
/* 24 */     VersionsNotificationDialog dialog = new VersionsNotificationDialog(context, VersionNotificationData.getInstance(context).getNotificationVersions())
/*    */       {
/*    */         protected ResultObject onClose(Map submitParams) {
/* 27 */           if (!keepNotifying()) {
/*    */             
/* 29 */             Map<Object, Object> alreadyNotified = (Map)SharedContext.getInstance((ClientContext)this.context).getPersistentObject(LoginDialogAccessImpl.KEY_NOTIFIED_VERSIONS);
/* 30 */             if (alreadyNotified == null) {
/* 31 */               alreadyNotified = new HashMap<Object, Object>();
/*    */             }
/* 33 */             for (Iterator<Version> iter = this.versions.iterator(); iter.hasNext();) {
/*    */               try {
/* 35 */                 Version version = iter.next();
/* 36 */                 alreadyNotified.put(version.getApplication().getIdentifier(), version.getDate());
/* 37 */               } catch (Exception e) {
/* 38 */                 LoginDialogAccessImpl.log.error("unable to add entry, skipping version - exception:" + e, e);
/*    */               } 
/*    */             } 
/* 41 */             SharedContext.getInstance((ClientContext)this.context).setPersistentObject(LoginDialogAccessImpl.KEY_NOTIFIED_VERSIONS, alreadyNotified);
/*    */           } 
/* 43 */           unregister();
/* 44 */           return next;
/*    */         }
/*    */       };
/* 47 */     return dialog.getHtmlCode(params);
/*    */   }
/*    */   
/*    */   public boolean isAvailable(ClientContext context) {
/* 51 */     boolean ret = true;
/*    */     try {
/* 53 */       ret = ModuleAccessPermission.getInstance(context).check("swdl");
/* 54 */       ret = (ret && VersionNotificationData.getInstance(context).getNotificationVersions().size() > 0);
/* 55 */     } catch (Exception e) {
/* 56 */       log.error("unable to check access permission, denying - exception:" + e, e);
/* 57 */       ret = false;
/*    */     } 
/* 59 */     return ret;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\server\implementation\service\LoginDialogAccessImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */