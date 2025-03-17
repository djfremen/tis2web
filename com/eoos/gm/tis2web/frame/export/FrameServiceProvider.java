/*    */ package com.eoos.gm.tis2web.frame.export;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.service.Service;
/*    */ import com.eoos.gm.tis2web.frame.export.declaration.service.ConfigurationService;
/*    */ import com.eoos.gm.tis2web.frame.export.declaration.service.FrameService;
/*    */ import com.eoos.gm.tis2web.frame.export.declaration.service.MailService;
/*    */ import com.eoos.gm.tis2web.frame.export.declaration.service.ResourceService;
/*    */ import com.eoos.gm.tis2web.frame.export.declaration.service.StorageService;
/*    */ import com.eoos.gm.tis2web.frame.implementation.service.ConfigurationServiceImpl;
/*    */ import com.eoos.gm.tis2web.frame.implementation.service.FrameServiceImpl;
/*    */ import com.eoos.gm.tis2web.frame.implementation.service.MailServiceImpl;
/*    */ import com.eoos.gm.tis2web.frame.implementation.service.ResourceServiceImpl;
/*    */ import com.eoos.gm.tis2web.frame.implementation.service.StorageServiceImplV2;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import com.eoos.propcfg.SubConfigurationWrapper;
/*    */ import com.eoos.resource.loading.ResourceLoading;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FrameServiceProvider
/*    */ {
/* 29 */   private static final Logger log = Logger.getLogger(FrameServiceProvider.class);
/*    */   
/* 31 */   private Map services = new HashMap<Object, Object>();
/*    */   
/*    */   private ResourceLoading resourceLoading;
/*    */ 
/*    */   
/*    */   private FrameServiceProvider(ResourceLoading resourceLoading) {
/* 37 */     this.resourceLoading = resourceLoading;
/*    */   }
/*    */   
/*    */   public static FrameServiceProvider create(ResourceLoading resourceLoading) {
/* 41 */     log.debug("creating FrameServiceProvider");
/* 42 */     FrameServiceProvider instance = new FrameServiceProvider(resourceLoading);
/* 43 */     ApplicationContext.getInstance().storeObject(FrameServiceProvider.class, instance);
/* 44 */     log.debug("FrameServiceProvider created");
/* 45 */     return instance;
/*    */   }
/*    */   
/*    */   public static synchronized FrameServiceProvider getInstance() {
/* 49 */     return (FrameServiceProvider)ApplicationContext.getInstance().getObject(FrameServiceProvider.class);
/*    */   }
/*    */   
/*    */   public synchronized Service getService(Class<?> serviceInterface) {
/* 53 */     Service service = (Service)this.services.get(serviceInterface);
/* 54 */     if (service == null) {
/* 55 */       service = createService(serviceInterface);
/* 56 */       this.services.put(serviceInterface, service);
/*    */     } 
/* 58 */     return service;
/*    */   }
/*    */   
/*    */   private Service createService(Class<ConfigurationService> serviceInterface) {
/* 62 */     Service service = null; try {
/*    */       StorageService storageService;
/* 64 */       if (serviceInterface == ConfigurationService.class) {
/* 65 */         ResourceService resourceService = (ResourceService)getService(ResourceService.class);
/* 66 */         ConfigurationServiceImpl configurationServiceImpl = new ConfigurationServiceImpl(resourceService);
/* 67 */       } else if (serviceInterface == ResourceService.class) {
/* 68 */         ResourceServiceImpl resourceServiceImpl = new ResourceServiceImpl(this.resourceLoading);
/* 69 */       } else if (serviceInterface == MailService.class) {
/* 70 */         ConfigurationService configurationService = (ConfigurationService)getService(ConfigurationService.class);
/* 71 */         SubConfigurationWrapper subConfigurationWrapper = new SubConfigurationWrapper((Configuration)configurationService, "frame.mail.service.");
/* 72 */         MailServiceImpl mailServiceImpl = new MailServiceImpl((Configuration)subConfigurationWrapper);
/* 73 */       } else if (serviceInterface == FrameService.class) {
/* 74 */         FrameServiceImpl frameServiceImpl = new FrameServiceImpl();
/* 75 */       } else if (serviceInterface == StorageService.class) {
/* 76 */         ConfigurationService configurationService = (ConfigurationService)getService(ConfigurationService.class);
/* 77 */         SubConfigurationWrapper subConfigurationWrapper = new SubConfigurationWrapper((Configuration)configurationService, "frame.storage.service.");
/* 78 */         storageService = StorageServiceImplV2.create((Configuration)subConfigurationWrapper);
/*    */       } else {
/* 80 */         throw new IllegalArgumentException("unknown service type:" + serviceInterface.getName());
/*    */       } 
/* 82 */       return (Service)storageService;
/* 83 */     } catch (Exception e) {
/* 84 */       log.error("unable to instantiate service " + serviceInterface.getName() + " - exception:" + e);
/* 85 */       throw new ExceptionWrapper(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\FrameServiceProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */