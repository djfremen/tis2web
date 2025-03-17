/*     */ package com.eoos.gm.tis2web.si.implementation.io;
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
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainPage;
/*     */ import com.eoos.gm.tis2web.si.implementation.statcont.SIServiceOverlay;
/*     */ import com.eoos.gm.tis2web.si.service.SIService;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SI;
/*     */ import com.eoos.gm.tis2web.si.v2.SIDataAdapterFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.provider.CfgProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.provider.CfgProviderRetrieval;
/*     */ import com.eoos.gm.tis2web.vc.v2.provider.ContextualCfgProviderFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VINResolverRetrieval;
/*     */ import com.eoos.instantiation.Singleton;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.AssertUtil;
/*     */ import com.eoos.util.PeriodicTask;
/*     */ import java.net.URL;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SIServiceImpl
/*     */   implements SIService, Configurable, Singleton, CfgProviderRetrieval, VINResolverRetrieval
/*     */ {
/*  39 */   private static final Logger hitLog = Logger.getLogger("hit.log");
/*     */   
/*  41 */   private static SIServiceImpl instance = null;
/*     */   
/*  43 */   private final Object SERVICEID = new Object();
/*     */   
/*  45 */   private final Object SYNC_MODULEINFO = new Object();
/*     */   
/*  47 */   private ModuleInformation moduleInfo = null;
/*     */   
/*  49 */   private Configuration configuration = null;
/*     */   
/*  51 */   private static volatile long hits = 0L;
/*     */   
/*  53 */   private PeriodicTask ptHits = null;
/*     */   
/*     */   private boolean overlayActive = false;
/*     */ 
/*     */   
/*     */   public SIServiceImpl(Configuration configuration) {
/*  59 */     this.configuration = configuration;
/*     */     
/*  61 */     this.overlayActive = (ApplicationContext.getInstance().getProperty("component.si.static.dir") != null);
/*     */     
/*  63 */     startHitLog();
/*     */     
/*  65 */     (new Thread() {
/*     */         public void run() {
/*  67 */           synchronized (SIServiceImpl.class) {
/*     */ 
/*     */             
/*  70 */             SIDataAdapterFacade.getInstance().getCTOCs();
/*  71 */             SIPreload.getInstance().execute();
/*     */           } 
/*     */         }
/*     */       }).start();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static SIServiceImpl createInstance(Configuration configuration) {
/*  80 */     AssertUtil.ensure(instance, AssertUtil.NULL);
/*  81 */     instance = new SIServiceImpl(configuration);
/*  82 */     return instance;
/*     */   }
/*     */ 
/*     */   
/*     */   public static SIServiceImpl getInstance() {
/*  87 */     AssertUtil.ensure(instance, AssertUtil.NOT_NULL);
/*  88 */     return instance;
/*     */   }
/*     */   
/*     */   private void startHitLog() {
/*  92 */     long interval = 0L;
/*  93 */     String intervalProperty = ApplicationContext.getInstance().getProperty("frame.hit.log.interval");
/*     */     try {
/*  95 */       interval = Long.parseLong(intervalProperty);
/*  96 */       interval = interval * 60L * 1000L;
/*  97 */     } catch (NumberFormatException e) {
/*  98 */       interval = 3600000L;
/*     */     } 
/*     */     
/* 101 */     Runnable r = new Runnable() {
/*     */         public void run() {
/* 103 */           SIServiceImpl.hitLog.info("SI hits:" + SIServiceImpl.this.getHits());
/*     */         }
/*     */       };
/* 106 */     hitLog.info("SI hits -------- start logging");
/* 107 */     this.ptHits = new PeriodicTask(r, interval);
/* 108 */     this.ptHits.start();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getType() {
/* 113 */     return "si";
/*     */   }
/*     */   
/*     */   public Object getUI(String sessionID, Map parameter) {
/* 117 */     hits++;
/* 118 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/* 119 */     SharedContextProxy.getInstance(context).update();
/* 120 */     if (this.overlayActive && SIServiceOverlay.getInstance().isActive(context)) {
/* 121 */       return SIServiceOverlay.getInstance().getUI(sessionID, parameter);
/*     */     }
/* 123 */     return MainPage.getInstance(context).getHtmlCode(parameter);
/*     */   }
/*     */ 
/*     */   
/*     */   public Long getHits() {
/* 128 */     Long retValue = Long.valueOf(hits);
/* 129 */     hits = 0L;
/* 130 */     return retValue;
/*     */   }
/*     */   
/*     */   public ModuleInformation getModuleInformation() {
/* 134 */     synchronized (this.SYNC_MODULEINFO) {
/* 135 */       if (this.moduleInfo == null) {
/* 136 */         String version = this.configuration.getProperty("version");
/*     */         
/* 138 */         Object dbVersionInfo = SIDataAdapterFacade.getInstance().getSI().getVersionInfo();
/* 139 */         if (Util.isNullOrEmpty(dbVersionInfo)) {
/* 140 */           dbVersionInfo = null;
/*     */         }
/*     */         
/* 143 */         this.moduleInfo = ModuleInformationFactory.create((Module)this, version, null, dbVersionInfo);
/*     */       } 
/* 145 */       return this.moduleInfo;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getIdentifier() {
/* 151 */     return this.SERVICEID;
/*     */   }
/*     */   
/*     */   public SI getSI(ClientContext context) {
/* 155 */     return SIDataAdapterFacade.getInstance(context).getSI();
/*     */   }
/*     */   
/*     */   public Set getCfgProviders(ClientContext context) {
/* 159 */     if (ModuleAccessPermission.getInstance(context).check(getType())) {
/* 160 */       Set<ContextualCfgProviderFacade> ret = new HashSet();
/* 161 */       ret.addAll(SIDataAdapterFacade.getInstance().getCfgProviders());
/* 162 */       if (this.overlayActive) {
/* 163 */         SIServiceOverlay sIServiceOverlay = SIServiceOverlay.getInstance();
/* 164 */         ret.add(ContextualCfgProviderFacade.getInstance(context, (CfgProvider)sIServiceOverlay));
/*     */       } 
/* 166 */       return ret;
/*     */     } 
/* 168 */     return Collections.EMPTY_SET;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Set getCfgProviders() {
/* 174 */     Set<SIServiceOverlay> ret = new HashSet();
/* 175 */     ret.addAll(SIDataAdapterFacade.getInstance().getCfgProviders());
/* 176 */     if (this.overlayActive) {
/* 177 */       ret.add(SIServiceOverlay.getInstance());
/*     */     }
/* 179 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set getVINResolvers(ClientContext context) {
/* 184 */     if (context == null || ModuleAccessPermission.getInstance(context).check(getType())) {
/* 185 */       return SIDataAdapterFacade.getInstance().getVINResolvers(context);
/*     */     }
/* 187 */     return Collections.EMPTY_SET;
/*     */   }
/*     */ 
/*     */   
/*     */   public URL getSearchURL(ClientContext context, String number) {
/* 192 */     return MainPage.getInstance(context).getSearchURL(number);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\SIServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */