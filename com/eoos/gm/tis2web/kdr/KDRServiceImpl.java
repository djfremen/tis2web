/*     */ package com.eoos.gm.tis2web.kdr;
/*     */ 
/*     */ import com.eoos.datatype.marker.Configurable;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.server.DatabaseAdapter;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.DBVersionInformation;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.ModuleInformation;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.ModuleInformationFactory;
/*     */ import com.eoos.gm.tis2web.frame.export.common.service.Module;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*     */ import com.eoos.gm.tis2web.kdr.ui.html.main.MainPage;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KDRServiceImpl
/*     */   implements KDRService, Configurable
/*     */ {
/*  25 */   private static final Logger log = Logger.getLogger(KDRServiceImpl.class);
/*     */   
/*     */   private Configuration cfg;
/*     */   
/*  29 */   private final Object SYNC_MODULEINFO = new Object();
/*     */   
/*  31 */   private ModuleInformation moduleInfoClient = null;
/*     */   
/*     */   public KDRServiceImpl(Configuration configuration) {
/*  34 */     this.cfg = configuration;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getType() {
/*  39 */     return "kdr";
/*     */   }
/*     */   
/*     */   public Object getUI(String sessionID, Map parameter) {
/*  43 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/*  44 */     SharedContextProxy.getInstance(context).update();
/*  45 */     return MainPage.getInstance(context).getHtmlCode(parameter);
/*     */   }
/*     */   
/*     */   public Boolean isActive(String sessionID) {
/*  49 */     return new Boolean(ClientContextProvider.getInstance().isActive(sessionID));
/*     */   }
/*     */   
/*     */   public Boolean invalidateSession(String sessionID) {
/*  53 */     return new Boolean(ClientContextProvider.getInstance().invalidateSession(sessionID));
/*     */   }
/*     */   
/*     */   public ModuleInformation getModuleInformation() {
/*  57 */     DBVersionInformation dbInfo = DatabaseAdapter.getInstance().getDBVersionInfo();
/*  58 */     String version = this.cfg.getProperty("version");
/*  59 */     return ModuleInformationFactory.create((Module)this, version, null, dbInfo);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupported(String salesmake) {
/*  64 */     return true;
/*     */   }
/*     */   
/*     */   public Object getIdentifier() {
/*  68 */     return "kdrservice";
/*     */   }
/*     */   
/*  71 */   private static final Pattern PATTERN_CV = Pattern.compile("<title>.*?(\\d+\\.\\d+\\.?.*?)\\s*</title>", 2);
/*     */   
/*     */   private String getClientVersion() {
/*  74 */     String ret = null;
/*     */     try {
/*  76 */       String jnlp = ApplicationContext.getInstance().loadTextResource("/kdr/client/kdr.jnlp", null);
/*  77 */       Matcher matcher = PATTERN_CV.matcher(jnlp);
/*  78 */       if (matcher.find()) {
/*  79 */         ret = matcher.group(1);
/*     */       }
/*  81 */     } catch (Exception e) {
/*  82 */       log.error("unable to determine client version, returning null - exception: " + e, e);
/*     */     } 
/*  84 */     return ret;
/*     */   }
/*     */   
/*  87 */   private static final Pattern PATTERN_CDESC = Pattern.compile("<title>\\s*(.*?)V\\.", 2);
/*     */   
/*     */   private String getClientDescription() {
/*  90 */     String ret = null;
/*     */     try {
/*  92 */       String jnlp = ApplicationContext.getInstance().loadTextResource("/kdr/client/kdr.jnlp", null);
/*  93 */       Matcher matcher = PATTERN_CDESC.matcher(jnlp);
/*  94 */       if (matcher.find()) {
/*  95 */         ret = matcher.group(1);
/*     */       }
/*  97 */     } catch (Exception e) {
/*  98 */       log.error("unable to determine client description, returning null - exception: " + e, e);
/*     */     } 
/* 100 */     return ret;
/*     */   }
/*     */   
/*     */   public ModuleInformation getClientModuleInformation() {
/* 104 */     synchronized (this.SYNC_MODULEINFO) {
/* 105 */       if (this.moduleInfoClient == null) {
/* 106 */         String desc = getClientDescription();
/* 107 */         this.moduleInfoClient = ModuleInformationFactory.create(desc, getClientVersion(), null, null);
/*     */       } 
/* 109 */       return this.moduleInfoClient;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\kdr\KDRServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */