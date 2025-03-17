/*     */ package com.eoos.gm.tis2web.swdl.server.implementation.service;
/*     */ 
/*     */ import com.eoos.datatype.marker.Configurable;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.DBVersionInformation;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.ModuleInformation;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.ModuleInformationFactory;
/*     */ import com.eoos.gm.tis2web.frame.export.common.service.Module;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*     */ import com.eoos.gm.tis2web.frame.login.dialog.LoginDialogAccess;
/*     */ import com.eoos.gm.tis2web.frame.login.dialog.LoginDialogProvider;
/*     */ import com.eoos.gm.tis2web.swdl.server.datamodel.system.ApplicationRegistry;
/*     */ import com.eoos.gm.tis2web.swdl.server.datamodel.system.DeviceRegistry;
/*     */ import com.eoos.gm.tis2web.swdl.server.datamodel.system.LanguageRegistry;
/*     */ import com.eoos.gm.tis2web.swdl.server.datamodel.system.metrics.SWDLMetricsLogFacade;
/*     */ import com.eoos.gm.tis2web.swdl.server.datamodel.system.metrics.export.ExportManager;
/*     */ import com.eoos.gm.tis2web.swdl.server.db.DatabaseAdapter;
/*     */ import com.eoos.gm.tis2web.swdl.server.ui.html.main.MainPage;
/*     */ import com.eoos.gm.tis2web.swdl.service.SWDLService;
/*     */ import com.eoos.instantiation.Singleton;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class SWDLServiceImpl
/*     */   implements SWDLService, Configurable, Singleton, LoginDialogProvider
/*     */ {
/*  37 */   private static final Logger log = Logger.getLogger(SWDLServiceImpl.class);
/*     */   
/*  39 */   private static SWDLServiceImpl instance = null;
/*     */   
/*  41 */   private final Object SYNC_MODULEINFO = new Object();
/*     */   
/*  43 */   private ModuleInformation moduleInfoClient = null;
/*     */   
/*     */   private Configuration configuration;
/*     */   
/*  47 */   private final LoginDialogAccess loginDialogAccess = new LoginDialogAccessImpl();
/*     */ 
/*     */   
/*     */   private SWDLServiceImpl(Configuration configuration) {
/*  51 */     this.configuration = configuration;
/*     */     
/*  53 */     log.info("starting swdl initializer thread");
/*  54 */     (new Thread() {
/*     */         public void run() {
/*  56 */           LanguageRegistry.getInstance().init();
/*  57 */           ApplicationRegistry.getInstance().init();
/*  58 */           SWDLMetricsLogFacade.getInstance();
/*  59 */           ExportManager.getInstance().start();
/*     */         }
/*     */       }).start();
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/*  66 */     log.info("resetting");
/*  67 */     log.debug("...resetting device registry");
/*  68 */     DeviceRegistry.getInstance().reset();
/*  69 */     log.debug("...resetting language registry");
/*  70 */     LanguageRegistry.getInstance().reset();
/*  71 */     log.debug("...resetting application registry");
/*  72 */     ApplicationRegistry.getInstance().reset();
/*     */     
/*  74 */     log.debug("...initializing device registry");
/*  75 */     DeviceRegistry.getInstance().init();
/*  76 */     log.debug("...initializing language registry");
/*  77 */     LanguageRegistry.getInstance().init();
/*  78 */     log.debug("...initializing application registry");
/*  79 */     ApplicationRegistry.getInstance().init();
/*     */   }
/*     */ 
/*     */   
/*     */   public static synchronized SWDLServiceImpl createInstance(Configuration configuration) {
/*  84 */     if (instance != null) {
/*  85 */       throw new IllegalStateException();
/*     */     }
/*  87 */     return instance = new SWDLServiceImpl(configuration);
/*     */   }
/*     */ 
/*     */   
/*     */   public static synchronized SWDLServiceImpl getInstance() {
/*  92 */     if (instance == null) {
/*  93 */       throw new IllegalStateException();
/*     */     }
/*  95 */     return instance;
/*     */   }
/*     */ 
/*     */   
/*     */   public Configuration getConfiguration() {
/* 100 */     return this.configuration;
/*     */   }
/*     */   
/*     */   public String getType() {
/* 104 */     return "swdl";
/*     */   }
/*     */   
/*     */   public Object getUI(String sessionID, Map parameter) {
/* 108 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/* 109 */     SharedContextProxy.getInstance(context).update();
/* 110 */     return MainPage.getInstance(context).getHtmlCode(parameter);
/*     */   }
/*     */   
/*     */   public Boolean isActive(String sessionID) {
/* 114 */     return new Boolean(ClientContextProvider.getInstance().isActive(sessionID));
/*     */   }
/*     */   
/*     */   public Boolean invalidateSession(String sessionID) {
/* 118 */     return new Boolean(ClientContextProvider.getInstance().invalidateSession(sessionID));
/*     */   }
/*     */   
/*     */   public ModuleInformation getModuleInformation() {
/* 122 */     List<DBVersionInformation> dbvi = new ArrayList();
/* 123 */     Set databases = DeviceRegistry.getInstance().getDatabaseAdapters();
/* 124 */     for (Iterator<DatabaseAdapter> iter = databases.iterator(); iter.hasNext(); ) {
/* 125 */       DatabaseAdapter database = iter.next();
/* 126 */       if (database.getVersionInfo() != null) {
/* 127 */         dbvi.add(database.getVersionInfo());
/*     */       }
/*     */     } 
/* 130 */     String version = this.configuration.getProperty("version");
/* 131 */     return ModuleInformationFactory.create((Module)this, version, null, (dbvi.size() == 0) ? null : dbvi);
/*     */   }
/*     */   
/*     */   public boolean isSupported(String salesmake) {
/* 135 */     return true;
/*     */   }
/*     */   
/*     */   public Object getIdentifier() {
/* 139 */     return "swdlservice";
/*     */   }
/*     */   
/*     */   public LoginDialogAccess getLoginDialogAccess() {
/* 143 */     return this.loginDialogAccess;
/*     */   }
/*     */   
/* 146 */   private static final Pattern PATTERN_CV = Pattern.compile("<title>.*?(\\d+\\.\\d+\\.\\d+)\\s*</title>", 2);
/*     */   
/*     */   private String getClientVersion() {
/* 149 */     String ret = null;
/*     */     try {
/* 151 */       String jnlp = ApplicationContext.getInstance().loadTextResource("/swdl/download/swdl.jnlp", null);
/* 152 */       Matcher matcher = PATTERN_CV.matcher(jnlp);
/* 153 */       if (matcher.find()) {
/* 154 */         ret = matcher.group(1);
/*     */       }
/* 156 */     } catch (Exception e) {
/* 157 */       log.error("unable to determine client version, returning null - exception: " + e, e);
/*     */     } 
/* 159 */     return ret;
/*     */   }
/*     */   
/*     */   public ModuleInformation getClientModuleInformation() {
/* 163 */     synchronized (this.SYNC_MODULEINFO) {
/* 164 */       if (this.moduleInfoClient == null) {
/* 165 */         String desc = ModuleInformationFactory.getDefaultDescription((Module)this) + "- Client";
/* 166 */         this.moduleInfoClient = ModuleInformationFactory.create(desc, getClientVersion(), null, null);
/*     */       } 
/* 168 */       return this.moduleInfoClient;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\server\implementation\service\SWDLServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */