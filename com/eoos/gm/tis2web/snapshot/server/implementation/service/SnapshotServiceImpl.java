/*    */ package com.eoos.gm.tis2web.snapshot.server.implementation.service;
/*    */ 
/*    */ import com.eoos.datatype.marker.Configurable;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.datatype.ModuleInformation;
/*    */ import com.eoos.gm.tis2web.frame.export.common.datatype.ModuleInformationFactory;
/*    */ import com.eoos.gm.tis2web.frame.export.common.service.Module;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*    */ import com.eoos.gm.tis2web.snapshot.server.implementation.ui.html.main.SnapshotMainPage;
/*    */ import com.eoos.gm.tis2web.snapshot.service.SnapshotService;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import java.util.Map;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SnapshotServiceImpl
/*    */   implements SnapshotService, Configurable
/*    */ {
/* 25 */   private static final Logger log = Logger.getLogger(SnapshotServiceImpl.class);
/*    */   
/*    */   private Configuration configuration;
/*    */   
/* 29 */   private final Object SYNC_MODULEINFO = new Object();
/*    */   
/* 31 */   private ModuleInformation moduleInfo = null;
/*    */   
/* 33 */   private ModuleInformation moduleInfoClient = null;
/*    */   
/*    */   public SnapshotServiceImpl(Configuration configuration) {
/* 36 */     this.configuration = configuration;
/*    */   }
/*    */   
/*    */   public Object getUI(String sessionID, Map parameters) {
/* 40 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/* 41 */     SharedContextProxy.getInstance(context).update();
/* 42 */     return SnapshotMainPage.getInstance(context).getHtmlCode(parameters);
/*    */   }
/*    */ 
/*    */   
/*    */   public Boolean isActive(String sessionID) {
/* 47 */     return new Boolean(ClientContextProvider.getInstance().isActive(sessionID));
/*    */   }
/*    */   
/*    */   public Boolean invalidateSession(String sessionID) {
/* 51 */     return new Boolean(ClientContextProvider.getInstance().invalidateSession(sessionID));
/*    */   }
/*    */   
/*    */   public String getType() {
/* 55 */     return "snapshot";
/*    */   }
/*    */   
/*    */   public ModuleInformation getModuleInformation() {
/* 59 */     synchronized (this.SYNC_MODULEINFO) {
/* 60 */       if (this.moduleInfo == null) {
/* 61 */         String version = this.configuration.getProperty("version");
/* 62 */         this.moduleInfo = ModuleInformationFactory.create((Module)this, version, null, null);
/*    */       } 
/* 64 */       return this.moduleInfo;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSupported(String salesmake) {
/* 70 */     return true;
/*    */   }
/*    */   
/*    */   public Object getIdentifier() {
/* 74 */     return "snapshot";
/*    */   }
/*    */   
/* 77 */   private static final Pattern PATTERN_CV = Pattern.compile("<title>.*?(\\d+\\.\\d+\\.\\d+)\\s*</title>", 2);
/*    */   
/*    */   private String getClientVersion() {
/* 80 */     String ret = null;
/*    */     try {
/* 82 */       String jnlp = ApplicationContext.getInstance().loadTextResource("/snapshot/client/snapshot.jnlp", null);
/* 83 */       Matcher matcher = PATTERN_CV.matcher(jnlp);
/* 84 */       if (matcher.find()) {
/* 85 */         ret = matcher.group(1);
/*    */       }
/* 87 */     } catch (Exception e) {
/* 88 */       log.error("unable to determine client version, returning null - exception: " + e, e);
/*    */     } 
/* 90 */     return ret;
/*    */   }
/*    */   
/*    */   public ModuleInformation getClientModuleInformation() {
/* 94 */     synchronized (this.SYNC_MODULEINFO) {
/* 95 */       if (this.moduleInfoClient == null) {
/* 96 */         String desc = ModuleInformationFactory.getDefaultDescription((Module)this) + " - Client";
/* 97 */         this.moduleInfoClient = ModuleInformationFactory.create(desc, getClientVersion(), null, null);
/*    */       } 
/* 99 */       return this.moduleInfoClient;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\snapshot\server\implementation\service\SnapshotServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */