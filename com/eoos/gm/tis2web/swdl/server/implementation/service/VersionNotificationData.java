/*    */ package com.eoos.gm.tis2web.swdl.server.implementation.service;
/*    */ 
/*    */ import com.eoos.collection.v2.CollectionUtil;
/*    */ import com.eoos.filter.Filter;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContext;
/*    */ import com.eoos.gm.tis2web.swdl.common.domain.application.Application;
/*    */ import com.eoos.gm.tis2web.swdl.common.domain.application.Version;
/*    */ import com.eoos.gm.tis2web.swdl.common.domain.device.Device;
/*    */ import com.eoos.gm.tis2web.swdl.server.datamodel.system.ApplicationRegistry;
/*    */ import com.eoos.gm.tis2web.swdl.server.datamodel.system.IApplicationRegistry;
/*    */ import java.util.Collections;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedHashSet;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class VersionNotificationData
/*    */ {
/* 22 */   private static final Logger log = Logger.getLogger(VersionNotificationData.class);
/*    */   
/* 24 */   protected static final Object KEY_NOTIFIED_VERSIONS = LoginDialogAccessImpl.KEY_NOTIFIED_VERSIONS;
/*    */   
/*    */   private ClientContext context;
/*    */   
/* 28 */   private final Object SYNC = new Object();
/*    */   
/* 30 */   private Set newestVersions = null;
/*    */   
/*    */   private VersionNotificationData(ClientContext context) {
/* 33 */     this.context = context;
/*    */   }
/*    */   
/*    */   public static VersionNotificationData getInstance(ClientContext context) {
/* 37 */     synchronized (context.getLockObject()) {
/* 38 */       VersionNotificationData instance = (VersionNotificationData)context.getObject(VersionNotificationData.class);
/* 39 */       if (instance == null) {
/* 40 */         instance = new VersionNotificationData(context);
/* 41 */         context.storeObject(VersionNotificationData.class, instance);
/*    */       } 
/* 43 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   private Set getNewestVersions(int retry) {
/* 48 */     synchronized (this.SYNC) {
/* 49 */       if (this.newestVersions == null) {
/* 50 */         this.newestVersions = Collections.synchronizedSet(new LinkedHashSet());
/* 51 */         IApplicationRegistry applicationRegistry = ApplicationRegistry.getInstance(this.context);
/*    */         try {
/* 53 */           Set applications = applicationRegistry.getApplications(Device.TECH2);
/* 54 */           for (Iterator<Application> iter = applications.iterator(); iter.hasNext(); ) {
/* 55 */             Application application = iter.next();
/* 56 */             this.newestVersions.add(applicationRegistry.getNewestVersion(application));
/*    */           } 
/* 58 */         } catch (com.eoos.gm.tis2web.swdl.server.datamodel.system.IApplicationRegistry.ReInitializationException e) {
/* 59 */           ApplicationRegistry.newInstance(this.context);
/* 60 */           if (retry > 0) {
/* 61 */             return getNewestVersions(--retry);
/*    */           }
/* 63 */           throw e;
/*    */         } 
/*    */       } 
/*    */       
/* 67 */       return this.newestVersions;
/*    */     } 
/*    */   }
/*    */   
/*    */   public Set getNotificationVersions() {
/* 72 */     Set ret = new LinkedHashSet(getNewestVersions(1));
/* 73 */     final Map alreadyNotified = (Map)SharedContext.getInstance(this.context).getPersistentObject(KEY_NOTIFIED_VERSIONS);
/* 74 */     if (alreadyNotified != null) {
/* 75 */       CollectionUtil.filter(ret, new Filter()
/*    */           {
/*    */             public boolean include(Object obj) {
/* 78 */               boolean ret = true;
/* 79 */               Version version = (Version)obj;
/*    */               try {
/* 81 */                 Object key = version.getApplication().getIdentifier();
/* 82 */                 Long latestDate = (Long)alreadyNotified.get(key);
/* 83 */                 if (latestDate != null) {
/* 84 */                   ret = (version.getDate().compareTo(latestDate) > 0);
/*    */                 }
/* 86 */               } catch (Exception e) {
/* 87 */                 VersionNotificationData.log.error("unable to determine notification status for version: " + String.valueOf(version) + ", including version - exception:" + e, e);
/*    */               } 
/* 89 */               return ret;
/*    */             }
/*    */           });
/*    */     }
/*    */     
/* 94 */     return ret;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\server\implementation\service\VersionNotificationData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */