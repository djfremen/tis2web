/*     */ package com.eoos.gm.tis2web.sps.server.implementation.service;
/*     */ 
/*     */ import com.eoos.datatype.marker.Configurable;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.ModuleInformation;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.ModuleInformationFactory;
/*     */ import com.eoos.gm.tis2web.frame.export.common.permission.ModuleAccessPermission;
/*     */ import com.eoos.gm.tis2web.frame.export.common.service.Module;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.log.export.ExportManager;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.system.serverside.adapter.SchemaAdapterFacade;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.system.serverside.adapter.ServiceResolution;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.sps.main.SPSMainPage;
/*     */ import com.eoos.gm.tis2web.sps.service.SPSService;
/*     */ import com.eoos.gm.tis2web.vc.v2.provider.CfgProviderRetrieval;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VINResolverRetrieval;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SPSServiceImpl
/*     */   implements SPSService, Configurable, CfgProviderRetrieval, VINResolverRetrieval
/*     */ {
/*  33 */   private static final Logger log = Logger.getLogger(SPSServiceImpl.class);
/*     */   
/*  35 */   private final Object SYNC_MODULEINFO = new Object();
/*     */   
/*  37 */   private ModuleInformation moduleInfoClient = null;
/*     */   
/*  39 */   private Configuration configuration = null;
/*     */ 
/*     */   
/*     */   public SPSServiceImpl(Configuration configuration) {
/*  43 */     this.configuration = configuration;
/*  44 */     log.info("starting sps service initializer thread");
/*  45 */     Thread initThread = new Thread("initializing thread for SPS") {
/*     */         public void run() {
/*  47 */           SPSServiceImpl.this.getModuleInformation();
/*  48 */           SchemaAdapterFacade.getInstance().init();
/*  49 */           ExportManager.getInstance().start();
/*     */         }
/*     */       };
/*  52 */     initThread.setPriority(1);
/*  53 */     initThread.start();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getType() {
/*  58 */     return "sps";
/*     */   }
/*     */   
/*     */   public Object getUI(String sessionID, Map parameter) {
/*  62 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/*  63 */     SharedContextProxy.getInstance(context).update();
/*  64 */     return SPSMainPage.getInstance(context).getHtmlCode(parameter);
/*     */   }
/*     */   
/*     */   public Boolean isActive(String sessionID) {
/*  68 */     return new Boolean(ClientContextProvider.getInstance().isActive(sessionID));
/*     */   }
/*     */   
/*     */   public Boolean invalidateSession(String sessionID) {
/*  72 */     return new Boolean(ClientContextProvider.getInstance().invalidateSession(sessionID));
/*     */   }
/*     */   
/*     */   public ModuleInformation getModuleInformation() {
/*  76 */     Object dbVersionInfo = ServiceResolution.getInstance().getVersionInfo();
/*  77 */     if (dbVersionInfo != null && ((Collection)dbVersionInfo).size() == 0) {
/*  78 */       dbVersionInfo = null;
/*     */     }
/*  80 */     String version = this.configuration.getProperty("version");
/*  81 */     return ModuleInformationFactory.create((Module)this, version, null, dbVersionInfo);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSupported(String salesmake) {
/*  89 */     return true;
/*     */   }
/*     */   
/*     */   public Object getIdentifier() {
/*  93 */     return "spsservice";
/*     */   }
/*     */   
/*     */   public Set getCfgProviders(ClientContext context) {
/*  97 */     if (ModuleAccessPermission.getInstance(context).check(getType())) {
/*  98 */       return SchemaAdapterFacade.getInstance().getCfgProviders();
/*     */     }
/* 100 */     return Collections.EMPTY_SET;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set getCfgProviders() {
/* 105 */     return SchemaAdapterFacade.getInstance().getCfgProviders();
/*     */   }
/*     */   
/*     */   public Set getVINResolvers(ClientContext context) {
/* 109 */     if (context == null || ModuleAccessPermission.getInstance(context).check(getType())) {
/* 110 */       return SchemaAdapterFacade.getInstance().getVINResolvers(context);
/*     */     }
/* 112 */     return Collections.EMPTY_SET;
/*     */   }
/*     */ 
/*     */   
/* 116 */   private static final Pattern PATTERN_CV = Pattern.compile("<title>.*?(\\d+\\.\\d+\\..*?)\\s*</title>", 2);
/*     */   
/*     */   public String getClientVersion() {
/* 119 */     String ret = null;
/*     */     try {
/* 121 */       String jnlp = ApplicationContext.getInstance().loadTextResource("/sps/download/sps.jnlp", null);
/* 122 */       Matcher matcher = PATTERN_CV.matcher(jnlp);
/* 123 */       if (matcher.find()) {
/* 124 */         ret = matcher.group(1);
/*     */       }
/* 126 */     } catch (Exception e) {
/* 127 */       log.error("unable to determin client version, returning null - exception: " + e, e);
/*     */     } 
/* 129 */     return ret;
/*     */   }
/*     */   
/*     */   public ModuleInformation getClientModuleInformation() {
/* 133 */     synchronized (this.SYNC_MODULEINFO) {
/* 134 */       if (this.moduleInfoClient == null) {
/* 135 */         String desc = ModuleInformationFactory.getDefaultDescription((Module)this) + " - Client";
/* 136 */         this.moduleInfoClient = ModuleInformationFactory.create(desc, getClientVersion(), null, null);
/*     */       } 
/* 138 */       return this.moduleInfoClient;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\service\SPSServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */