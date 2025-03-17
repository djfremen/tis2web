/*     */ package com.eoos.gm.tis2web.tech2view.server.implementation.service;
/*     */ 
/*     */ import com.eoos.datatype.marker.Configurable;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.ModuleInformation;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.ModuleInformationFactory;
/*     */ import com.eoos.gm.tis2web.frame.export.common.service.Module;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*     */ import com.eoos.gm.tis2web.tech2view.server.implementation.ui.html.main.Tech2ViewMainPage;
/*     */ import com.eoos.gm.tis2web.tech2view.service.Tech2ViewService;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Tech2ViewServiceImpl
/*     */   implements Tech2ViewService, Configurable
/*     */ {
/*  25 */   private static final Logger log = Logger.getLogger(Tech2ViewServiceImpl.class);
/*     */   
/*     */   private Configuration configuration;
/*     */   
/*  29 */   private final Object SYNC_MODULEINFO = new Object();
/*     */   
/*  31 */   private ModuleInformation moduleInfo = null;
/*     */   
/*  33 */   private ModuleInformation moduleInfoClient = null;
/*     */   
/*     */   public Tech2ViewServiceImpl(Configuration configuration) {
/*  36 */     this.configuration = configuration;
/*     */   }
/*     */   
/*     */   public Object getUI(String sessionID, Map parameters) {
/*  40 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/*  41 */     SharedContextProxy.getInstance(context).update();
/*  42 */     return Tech2ViewMainPage.getInstance(context).getHtmlCode(parameters);
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean isActive(String sessionID) {
/*  47 */     return new Boolean(ClientContextProvider.getInstance().isActive(sessionID));
/*     */   }
/*     */   
/*     */   public Boolean invalidateSession(String sessionID) {
/*  51 */     return new Boolean(ClientContextProvider.getInstance().invalidateSession(sessionID));
/*     */   }
/*     */   
/*     */   public String getType() {
/*  55 */     return "tech2view";
/*     */   }
/*     */   
/*     */   public ModuleInformation getModuleInformation() {
/*  59 */     synchronized (this.SYNC_MODULEINFO) {
/*  60 */       if (this.moduleInfo == null) {
/*  61 */         String version = this.configuration.getProperty("version");
/*  62 */         this.moduleInfo = ModuleInformationFactory.create((Module)this, version, null, null);
/*     */       } 
/*  64 */       return this.moduleInfo;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSupported(String salesmake) {
/*  71 */     return true;
/*     */   }
/*     */   
/*     */   public Object getIdentifier() {
/*  75 */     return "tech2view";
/*     */   }
/*     */   
/*  78 */   private static final Pattern PATTERN_CV = Pattern.compile("<title>.*?(\\d+\\.\\d+\\.\\d+)\\s*</title>", 2);
/*     */   
/*     */   private String getClientVersion() {
/*  81 */     String ret = null;
/*     */     try {
/*  83 */       String jnlp = ApplicationContext.getInstance().loadTextResource("/tech2view/client/tech2view.jnlp", null);
/*  84 */       Matcher matcher = PATTERN_CV.matcher(jnlp);
/*  85 */       if (matcher.find()) {
/*  86 */         ret = matcher.group(1);
/*     */       }
/*  88 */     } catch (Exception e) {
/*  89 */       log.error("unable to determine client version, returning null - exception: " + e, e);
/*     */     } 
/*  91 */     return ret;
/*     */   }
/*     */   
/*     */   public ModuleInformation getClientModuleInformation() {
/*  95 */     synchronized (this.SYNC_MODULEINFO) {
/*  96 */       if (this.moduleInfoClient == null) {
/*  97 */         String desc = ModuleInformationFactory.getDefaultDescription((Module)this) + " - Client";
/*  98 */         this.moduleInfoClient = ModuleInformationFactory.create(desc, getClientVersion(), null, null);
/*     */       } 
/* 100 */       return this.moduleInfoClient;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\tech2view\server\implementation\service\Tech2ViewServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */