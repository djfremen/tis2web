/*     */ package com.eoos.gm.tis2web.lt.implementation.service;
/*     */ 
/*     */ import com.eoos.datatype.marker.Configurable;
/*     */ import com.eoos.gm.tis2web.frame.export.FallbackUIServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.ModuleInformation;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.ModuleInformationFactory;
/*     */ import com.eoos.gm.tis2web.frame.export.common.permission.ModuleAccessPermission;
/*     */ import com.eoos.gm.tis2web.frame.export.common.service.Module;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.main.toplink.VCLinkElement;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.FallbackUIService;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTClientContext;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.toc.TocTree;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.toc.TocTreeWorkNrNavigator;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.ui.LTUIContext;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.main.MainHook;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.main.MainPage;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.main.VCLinkCallback;
/*     */ import com.eoos.gm.tis2web.lt.service.LTService;
/*     */ import com.eoos.gm.tis2web.lt.v2.LTDataAdapterFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.provider.CfgDataProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*     */ import com.eoos.gm.tis2web.vc.v2.provider.GlobalVCDataProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCService;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCServiceProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VehicleCfgStorage;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.GlobalVINResolver;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VINResolver;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VINResolverRetrieval;
/*     */ import com.eoos.html.HtmlCodeRenderer;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.PeriodicTask;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class LTServiceImpl
/*     */   implements LTService, Configurable, VINResolverRetrieval
/*     */ {
/*  48 */   private static final Logger log = Logger.getLogger(LTServiceImpl.class);
/*     */   
/*  50 */   private static final Logger hitLog = Logger.getLogger("hit.log");
/*     */   
/*  52 */   private final Object SYNC_MODULEINFO = new Object();
/*  53 */   private ModuleInformation moduleInfo = null;
/*     */   
/*  55 */   private static volatile long hits = 0L;
/*     */   
/*  57 */   private PeriodicTask ptHits = null;
/*     */   
/*  59 */   private Configuration configuration = null;
/*     */ 
/*     */   
/*     */   public LTServiceImpl(Configuration configuration) {
/*  63 */     this.configuration = configuration;
/*     */     
/*  65 */     startHitLog();
/*     */     
/*  67 */     (new Thread() {
/*     */         public void run() {
/*  69 */           LTServiceImpl.log.debug("initializing LT...");
/*  70 */           LTDataAdapterFacade.getInstance().getCTOCs();
/*     */         }
/*     */       }).start();
/*     */   }
/*     */ 
/*     */   
/*     */   private void startHitLog() {
/*  77 */     long interval = 0L;
/*  78 */     String intervalProperty = ApplicationContext.getInstance().getProperty("frame.hit.log.interval");
/*     */     try {
/*  80 */       interval = Long.parseLong(intervalProperty);
/*  81 */       interval = interval * 60L * 1000L;
/*  82 */     } catch (NumberFormatException e) {
/*  83 */       interval = 3600000L;
/*     */     } 
/*     */     
/*  86 */     Runnable r = new Runnable()
/*     */       {
/*     */         public void run() {
/*  89 */           LTServiceImpl.hitLog.info("LT hits:" + LTServiceImpl.this.getHits());
/*     */         }
/*     */       };
/*  92 */     hitLog.info("LT hits -------- start logging");
/*  93 */     this.ptHits = new PeriodicTask(r, interval);
/*  94 */     this.ptHits.start();
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean init(String sessionID) {
/*  99 */     return new Boolean(true);
/*     */   }
/*     */   
/*     */   public String getType() {
/* 103 */     return "lt";
/*     */   }
/*     */   
/*     */   private boolean mandatoryVCSet(IConfiguration vc) {
/* 107 */     return (vc != null && VehicleConfigurationUtil.getMake(vc) != null && VehicleConfigurationUtil.getModel(vc) != null);
/*     */   }
/*     */ 
/*     */   
/*     */   private Object getFallbackUI(ClientContext context) {
/* 112 */     FallbackUIService fbService = FallbackUIServiceProvider.getInstance().getService();
/* 113 */     final VCLinkCallback callback = new VCLinkCallback(context);
/* 114 */     return new ResultObject(0, fbService.getUI(context.getSessionID(), null, new FallbackUIService.Callback()
/*     */           {
/*     */             public VCLinkElement.Callback getVCLinkCallback() {
/* 117 */               return (VCLinkElement.Callback)callback;
/*     */             }
/*     */             
/*     */             public String getModuleType() {
/* 121 */               return "lt";
/*     */             }
/*     */           }));
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getUI(final String sessionID, final Map parameter) {
/* 128 */     hits++;
/* 129 */     final ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/* 130 */     SharedContextProxy.getInstance(context).update();
/*     */     
/* 132 */     IConfiguration cfg = VCFacade.getInstance(context).getCfg();
/*     */     
/* 134 */     if (!mandatoryVCSet(cfg)) {
/* 135 */       HtmlCodeRenderer vcDialog = VCFacade.getInstance(context).getDialog(new VCService.DialogCallback()
/*     */           {
/*     */             public Object onClose(boolean cancelled) {
/* 138 */               if (!cancelled) {
/* 139 */                 return LTServiceImpl.this.getUI(sessionID, parameter);
/*     */               }
/* 141 */               return LTServiceImpl.this.getFallbackUI(context);
/*     */             }
/*     */ 
/*     */ 
/*     */             
/*     */             public boolean isReadonly(Object key) {
/* 147 */               return false;
/*     */             }
/*     */             
/*     */             public boolean isMandatory(Object key, IConfiguration currentCfg) {
/* 151 */               boolean ret = (VehicleConfigurationUtil.KEY_MAKE == key);
/* 152 */               ret = (ret || VehicleConfigurationUtil.KEY_MODEL == key);
/* 153 */               return ret;
/*     */             }
/*     */             
/*     */             public VINResolver getVINResolver() {
/* 157 */               return (VINResolver)GlobalVINResolver.getInstance(context);
/*     */             }
/*     */             
/*     */             public VehicleCfgStorage getStorage() {
/* 161 */               return (VehicleCfgStorage)VCServiceProvider.getInstance().getService(context);
/*     */             }
/*     */             
/*     */             public CfgDataProvider getDataProvider() {
/* 165 */               return GlobalVCDataProvider.getInstance(context);
/*     */             }
/*     */           });
/*     */       
/* 169 */       return vcDialog.getHtmlCode(parameter);
/*     */     } 
/* 171 */     boolean supportedVC = true;
/*     */     try {
/* 173 */       supportedVC = LTDataAdapterFacade.getInstance().supports(cfg);
/* 174 */     } catch (Exception e) {
/* 175 */       supportedVC = false;
/* 176 */       log.warn("unable to determine if current vc is supported, continuing with <false> - exception: " + e, e);
/*     */     } 
/* 178 */     if (!supportedVC)
/*     */     {
/* 180 */       return getFallbackUI(context);
/*     */     }
/* 182 */     return MainPage.getInstance(context).getHtmlCode(parameter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMainWork(String sessionID, String nr) throws LTService.LTServiceException {
/* 189 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/* 190 */     SharedContextProxy.getInstance(context).update();
/*     */     
/* 192 */     if (LTClientContext.getInstance(context).getModelCode().intValue() == -1) {
/* 193 */       throw new LTService.LTServiceException("Unsupported vehicle context (no corresponding model code)");
/*     */     }
/*     */     
/* 196 */     LTUIContext.getInstance(context).setSearchNr(nr);
/* 197 */     TocTreeWorkNrNavigator nav = new TocTreeWorkNrNavigator(TocTree.getInstance(context));
/* 198 */     if (!nav.navigateToMainWork(nr)) {
/* 199 */       throw new LTService.LTServiceException("unable to navigate to main work");
/*     */     }
/*     */ 
/*     */     
/* 203 */     MainHook.getInstance(context).switchUI(1);
/*     */   }
/*     */   
/*     */   public Long getHits() {
/* 207 */     Long retValue = Long.valueOf(hits);
/* 208 */     hits = 0L;
/* 209 */     return retValue;
/*     */   }
/*     */   
/*     */   public ModuleInformation getModuleInformation() {
/* 213 */     synchronized (this.SYNC_MODULEINFO) {
/* 214 */       if (this.moduleInfo == null) {
/* 215 */         String version = this.configuration.getProperty("version");
/* 216 */         Object dbVersionInfo = LTDataAdapterFacade.getInstance().getLT().getVersionInfo();
/* 217 */         if (Util.isNullOrEmpty(dbVersionInfo)) {
/* 218 */           dbVersionInfo = null;
/*     */         }
/*     */         
/* 221 */         this.moduleInfo = ModuleInformationFactory.create((Module)this, version, null, dbVersionInfo);
/*     */       } 
/* 223 */       return this.moduleInfo;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getIdentifier() {
/* 229 */     return "ltservice";
/*     */   }
/*     */   
/*     */   public boolean isMainWorkValid(String sessionID, String oNr) {
/* 233 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/* 234 */     LTClientContext ltcontext = LTClientContext.getInstance(context);
/* 235 */     return ltcontext.isMainWorkValid(oNr);
/*     */   }
/*     */   
/*     */   public Set getCfgProviders() {
/* 239 */     return LTDataAdapterFacade.getInstance().getCfgProviders();
/*     */   }
/*     */   
/*     */   public Set getVINResolvers(ClientContext context) {
/* 243 */     if (context == null || ModuleAccessPermission.getInstance(context).check(getType())) {
/* 244 */       return LTDataAdapterFacade.getInstance().getVINResolvers(context);
/*     */     }
/* 246 */     return Collections.EMPTY_SET;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\service\LTServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */