/*     */ package com.eoos.gm.tis2web.swdl.server.datamodel.system;
/*     */ 
/*     */ import com.eoos.filter.Filter;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.application.Application;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.application.Version;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.device.Device;
/*     */ import com.eoos.gm.tis2web.swdl.server.db.DatabaseAdapter;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ApplicationRegistryWrapper
/*     */   implements IApplicationRegistry
/*     */ {
/*  19 */   private static final Logger log = Logger.getLogger(ApplicationRegistryWrapper.class);
/*     */   
/*     */   private ClientContext context;
/*     */   
/*     */   private ApplicationRegistry backend;
/*     */   
/*     */   private Filter accessFilter;
/*     */   
/*     */   private long initializationTime;
/*     */   
/*     */   public ApplicationRegistryWrapper(final ClientContext context, ApplicationRegistry backend) {
/*  30 */     this.context = context;
/*  31 */     this.backend = backend;
/*  32 */     this.initializationTime = backend.getInitializationTime();
/*  33 */     this.accessFilter = new Filter() {
/*  34 */         private ApplicationAccessPermission accessPermission = new ApplicationAccessPermission(context);
/*     */         
/*     */         public boolean include(Object obj) {
/*  37 */           boolean retValue = false;
/*  38 */           Application application = (Application)obj;
/*     */           try {
/*  40 */             retValue = this.accessPermission.check(application);
/*  41 */           } catch (Exception e) {
/*  42 */             ApplicationRegistryWrapper.log.error("unable to check access permission for application:" + String.valueOf(application) + " indicating 'no access' - exception: " + e, e);
/*     */           } 
/*  44 */           return retValue;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   private void checkReinitialization() throws IApplicationRegistry.ReInitializationException {
/*  50 */     if (this.backend.getInitializationTime() != this.initializationTime) {
/*  51 */       log.debug("notified reinitialization of application registry - throwing ReInitializationException");
/*  52 */       throw new IApplicationRegistry.ReInitializationException();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DatabaseAdapter getDatabaseAdapter(Device device, String applicationID) {
/*  61 */     checkReinitialization();
/*  62 */     return this.backend.getDatabaseAdapter(device, applicationID);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set getApplications() {
/*  70 */     if (log.isDebugEnabled()) {
/*  71 */       log.debug("retrieving applications for " + String.valueOf(this.context));
/*     */     }
/*  73 */     checkReinitialization();
/*  74 */     Set retValue = this.backend.getApplications();
/*  75 */     if (log.isDebugEnabled()) {
/*  76 */       log.debug("...filtering application set:" + String.valueOf(retValue) + "...");
/*     */     }
/*  78 */     CollectionUtil.filter(retValue, this.accessFilter);
/*  79 */     if (log.isDebugEnabled()) {
/*  80 */       log.debug("...resulting application set: " + String.valueOf(retValue));
/*     */     }
/*  82 */     return retValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set getApplications(Device device) {
/*  90 */     if (log.isDebugEnabled()) {
/*  91 */       log.debug("retrieving application for " + String.valueOf(this.context) + " and device: " + String.valueOf(device));
/*     */     }
/*  93 */     checkReinitialization();
/*  94 */     Set retValue = this.backend.getApplications(device);
/*  95 */     if (log.isDebugEnabled()) {
/*  96 */       log.debug("...filtering application set:" + String.valueOf(retValue) + "...");
/*     */     }
/*  98 */     CollectionUtil.filter(retValue, this.accessFilter);
/*  99 */     if (log.isDebugEnabled()) {
/* 100 */       log.debug("...resulting application set: " + String.valueOf(retValue));
/*     */     }
/* 102 */     return retValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Version getNewestVersion(Device device, String appDesc, String versNo, String langID) {
/* 110 */     checkReinitialization();
/* 111 */     return this.backend.getNewestVersion(device, appDesc, langID);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Version getNewestVersion(Device device, String appDesc, String langID) {
/* 119 */     checkReinitialization();
/* 120 */     return this.backend.getNewestVersion(device, appDesc, langID);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Version getVersion(Device device, String appDesc, String versNo, String langID) {
/* 128 */     checkReinitialization();
/* 129 */     return this.backend.getVersion(device, appDesc, versNo, langID);
/*     */   }
/*     */   
/*     */   public Version getNewestVersion(Application application) {
/* 133 */     checkReinitialization();
/* 134 */     return this.backend.getNewestVersion(application);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\server\datamodel\system\ApplicationRegistryWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */