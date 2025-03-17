/*    */ package com.eoos.gm.tis2web.techlineprint.server.implementation.service;
/*    */ 
/*    */ import com.eoos.datatype.marker.Configurable;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.datatype.ModuleInformation;
/*    */ import com.eoos.gm.tis2web.frame.export.common.datatype.ModuleInformationFactory;
/*    */ import com.eoos.gm.tis2web.frame.export.common.service.Module;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*    */ import com.eoos.gm.tis2web.techlineprint.server.implementation.ui.html.main.TechlinePrintMainPage;
/*    */ import com.eoos.gm.tis2web.techlineprint.service.TechlinePrintService;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import java.util.Map;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TechlinePrintServiceImpl
/*    */   implements TechlinePrintService, Configurable
/*    */ {
/* 25 */   private static final Logger log = Logger.getLogger(TechlinePrintServiceImpl.class);
/*    */   
/*    */   private Configuration configuration;
/*    */   
/* 29 */   private final Object SYNC_MODULEINFO = new Object();
/*    */   
/* 31 */   private ModuleInformation moduleInfo = null;
/*    */   
/* 33 */   private ModuleInformation moduleInfoClient = null;
/*    */   
/*    */   public TechlinePrintServiceImpl(Configuration configuration) {
/* 36 */     this.configuration = configuration;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getUI(String sessionID, Map parameters) {
/* 41 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/* 42 */     SharedContextProxy.getInstance(context).update();
/* 43 */     return TechlinePrintMainPage.getInstance(context).getHtmlCode(parameters);
/*    */   }
/*    */ 
/*    */   
/*    */   public Boolean isActive(String sessionID) {
/* 48 */     return new Boolean(ClientContextProvider.getInstance().isActive(sessionID));
/*    */   }
/*    */   
/*    */   public Boolean invalidateSession(String sessionID) {
/* 52 */     return new Boolean(ClientContextProvider.getInstance().invalidateSession(sessionID));
/*    */   }
/*    */   
/*    */   public String getType() {
/* 56 */     return "techlineprint";
/*    */   }
/*    */   
/*    */   public ModuleInformation getModuleInformation() {
/* 60 */     synchronized (this.SYNC_MODULEINFO) {
/* 61 */       if (this.moduleInfo == null) {
/* 62 */         String version = this.configuration.getProperty("version");
/* 63 */         this.moduleInfo = ModuleInformationFactory.create((Module)this, version, null, null);
/*    */       } 
/* 65 */       return this.moduleInfo;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getIdentifier() {
/* 72 */     return "techlineprint";
/*    */   }
/*    */   
/* 75 */   private static final Pattern PATTERN_CV = Pattern.compile("<title>.*?(\\d+\\.\\d+\\.\\d+)\\s*</title>", 2);
/*    */   
/*    */   private String getClientVersion() {
/* 78 */     String ret = null;
/*    */     try {
/* 80 */       String jnlp = ApplicationContext.getInstance().loadTextResource("/techlineprint/client/techlineprint.jnlp", null);
/* 81 */       Matcher matcher = PATTERN_CV.matcher(jnlp);
/* 82 */       if (matcher.find()) {
/* 83 */         ret = matcher.group(1);
/*    */       }
/* 85 */     } catch (Exception e) {
/* 86 */       log.error("unable to determin client version, returning null - exception: " + e, e);
/*    */     } 
/* 88 */     return ret;
/*    */   }
/*    */   
/*    */   public ModuleInformation getClientModuleInformation() {
/* 92 */     synchronized (this.SYNC_MODULEINFO) {
/* 93 */       if (this.moduleInfoClient == null) {
/* 94 */         String desc = ModuleInformationFactory.getDefaultDescription((Module)this) + " - Client";
/* 95 */         this.moduleInfoClient = ModuleInformationFactory.create(desc, getClientVersion(), null, null);
/*    */       } 
/* 97 */       return this.moduleInfoClient;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\techlineprint\server\implementation\service\TechlinePrintServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */