/*    */ package com.eoos.gm.tis2web.sas.server.implementation.service;
/*    */ 
/*    */ import com.eoos.datatype.marker.Configurable;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.datatype.ModuleInformation;
/*    */ import com.eoos.gm.tis2web.frame.export.common.datatype.ModuleInformationFactory;
/*    */ import com.eoos.gm.tis2web.frame.export.common.service.Module;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*    */ import com.eoos.gm.tis2web.sas.server.implementation.ui.html.main.SASMainPage;
/*    */ import com.eoos.gm.tis2web.sas.service.SecurityAccessService;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import java.util.Map;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SecurityAccessServiceImpl
/*    */   implements SecurityAccessService, Configurable
/*    */ {
/* 25 */   private static final Logger log = Logger.getLogger(SecurityAccessServiceImpl.class);
/*    */   
/* 27 */   private final Object SYNC_MODULEINFO = new Object();
/*    */   
/* 29 */   private ModuleInformation moduleInfo = null;
/* 30 */   private ModuleInformation moduleInfoClient = null;
/*    */   
/*    */   private Configuration configuration;
/*    */   
/* 34 */   private static final Pattern PATTERN_CV = Pattern.compile("<title>.*?(\\d+\\.\\d+\\.\\d+)\\s*</title>", 2);
/*    */   
/*    */   public SecurityAccessServiceImpl(Configuration configuration) {
/* 37 */     this.configuration = configuration;
/*    */   }
/*    */   
/*    */   private String getClientVersion() {
/* 41 */     String ret = null;
/*    */     try {
/* 43 */       String jnlp = ApplicationContext.getInstance().loadTextResource("/sas/client/sas.jnlp", null);
/* 44 */       Matcher matcher = PATTERN_CV.matcher(jnlp);
/* 45 */       if (matcher.find()) {
/* 46 */         ret = matcher.group(1);
/*    */       }
/* 48 */     } catch (Exception e) {
/* 49 */       log.error("unable to determin client version, returning null - exception: " + e, e);
/*    */     } 
/* 51 */     return ret;
/*    */   }
/*    */   
/*    */   public Object getUI(String sessionID, Map parameters) {
/* 55 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/* 56 */     SharedContextProxy.getInstance(context).update();
/* 57 */     return SASMainPage.getInstance(context).getHtmlCode(parameters);
/*    */   }
/*    */ 
/*    */   
/*    */   public Boolean isActive(String sessionID) {
/* 62 */     return new Boolean(ClientContextProvider.getInstance().isActive(sessionID));
/*    */   }
/*    */   
/*    */   public Boolean invalidateSession(String sessionID) {
/* 66 */     return new Boolean(ClientContextProvider.getInstance().invalidateSession(sessionID));
/*    */   }
/*    */   
/*    */   public String getType() {
/* 70 */     return "sas";
/*    */   }
/*    */   
/*    */   public ModuleInformation getModuleInformation() {
/* 74 */     synchronized (this.SYNC_MODULEINFO) {
/* 75 */       if (this.moduleInfo == null) {
/* 76 */         String version = this.configuration.getProperty("version");
/* 77 */         this.moduleInfo = ModuleInformationFactory.create((Module)this, version, null, null);
/*    */       } 
/* 79 */       return this.moduleInfo;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getIdentifier() {
/* 85 */     return "securityaccessservice";
/*    */   }
/*    */   
/*    */   public ModuleInformation getClientModuleInformation() {
/* 89 */     synchronized (this.SYNC_MODULEINFO) {
/* 90 */       if (this.moduleInfoClient == null) {
/* 91 */         String desc = ModuleInformationFactory.getDefaultDescription((Module)this) + " - Client";
/* 92 */         this.moduleInfoClient = ModuleInformationFactory.create(desc, getClientVersion(), null, null);
/*    */       } 
/* 94 */       return this.moduleInfoClient;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\server\implementation\service\SecurityAccessServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */