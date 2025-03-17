/*     */ package com.eoos.gm.tis2web.help.implementation.service;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.CTOCServiceProvider;
/*     */ import com.eoos.gm.tis2web.ctoc.service.HelpCTOCService;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.IOFactory;
/*     */ import com.eoos.gm.tis2web.frame.export.ConfigurationServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.DBVersionInformation;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.ModuleInformation;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.ModuleInformationFactory;
/*     */ import com.eoos.gm.tis2web.frame.export.common.service.Module;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.ConfigurationService;
/*     */ import com.eoos.gm.tis2web.help.implementation.ui.html.document.container.HelpDocumentContainer;
/*     */ import com.eoos.gm.tis2web.help.implementation.ui.html.main.MainPage;
/*     */ import com.eoos.gm.tis2web.help.service.HelpService;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import java.sql.Connection;
/*     */ import java.sql.Date;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HelpServiceImpl
/*     */   implements HelpService
/*     */ {
/*  40 */   private static final Logger log = Logger.getLogger(HelpServiceImpl.class);
/*     */   
/*  42 */   private final Object SYNC_MODULEINFO = new Object();
/*     */   
/*  44 */   private ModuleInformation moduleInfo = null;
/*     */ 
/*     */ 
/*     */   
/*     */   public HelpServiceImpl() {
/*  49 */     (new Thread() {
/*     */         public void run() {
/*  51 */           HelpServiceImpl.log.debug("initializing help service...");
/*  52 */           CTOCServiceProvider.getInstance().getService(CTOCServiceProvider.HELP).getCTOC();
/*  53 */           HelpServiceImpl.log.debug("...done initializing help service");
/*     */         }
/*     */       }).start();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getType() {
/*  60 */     return "help";
/*     */   }
/*     */   
/*     */   public void reset() {
/*  64 */     log.info("resetting help service");
/*  65 */     HelpDocumentContainer.reset();
/*  66 */     HelpProvider.reset();
/*  67 */     HelpDocumentContainer.reset();
/*  68 */     HelpCTOCService service = (HelpCTOCService)CTOCServiceProvider.getInstance().getService(CTOCServiceProvider.HELP);
/*  69 */     service.reset();
/*  70 */     this.moduleInfo = null;
/*     */   }
/*     */   
/*     */   public Object getUI(String sessionID, Map parameters) {
/*  74 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/*  75 */     SharedContextProxy.getInstance(context).update();
/*     */     
/*  77 */     MainPage page = MainPage.constructInstance(context, parameters);
/*  78 */     return page.getHtmlCode(parameters);
/*     */   }
/*     */   
/*     */   public Boolean isActive(String sessionID) {
/*  82 */     return new Boolean(ClientContextProvider.getInstance().isActive(sessionID));
/*     */   }
/*     */   
/*     */   public Boolean invalidateSession(String sessionID) {
/*  86 */     return new Boolean(ClientContextProvider.getInstance().invalidateSession(sessionID));
/*     */   }
/*     */   
/*     */   public ModuleInformation getModuleInformation() {
/*  90 */     synchronized (this.SYNC_MODULEINFO) {
/*  91 */       if (this.moduleInfo == null) {
/*  92 */         String sBaseConfig = "frame.adapter.help.";
/*  93 */         ConfigurationService configurationService = ConfigurationServiceProvider.getService();
/*  94 */         SubConfigurationWrapper configIDs = new SubConfigurationWrapper((Configuration)configurationService, "frame.adapter.help.");
/*     */         
/*  96 */         HashMap<Object, Object> keyMap = null;
/*  97 */         IDatabaseLink dbLink = null;
/*  98 */         List<DBVersionInformation> dbInfo = new LinkedList();
/*     */         
/* 100 */         SubConfigurationWrapper scOneConfig = new SubConfigurationWrapper((Configuration)configurationService, "frame.adapter.help.db.");
/* 101 */         if (scOneConfig != null && scOneConfig.getKeys().size() > 0) {
/* 102 */           dbLink = DatabaseLink.openDatabase((Configuration)scOneConfig);
/* 103 */           DBVersionInformation dbInfos = getDBVersionInfo(dbLink);
/* 104 */           if (dbInfos != null) {
/* 105 */             dbInfo.add(dbInfos);
/*     */           }
/*     */         } else {
/*     */           
/* 109 */           keyMap = new HashMap<Object, Object>();
/* 110 */           for (Iterator<String> subiter = configIDs.getKeys().iterator(); subiter.hasNext(); ) {
/* 111 */             String nextKey = subiter.next();
/* 112 */             if (nextKey.startsWith("."))
/* 113 */               nextKey = nextKey.substring(1); 
/* 114 */             String fullKey = nextKey.substring(0, (nextKey.indexOf('.') != -1) ? nextKey.indexOf('.') : nextKey.length());
/* 115 */             keyMap.put(fullKey, fullKey);
/*     */           } 
/*     */           
/* 118 */           for (Iterator<String> iterMap = keyMap.keySet().iterator(); iterMap.hasNext(); ) {
/* 119 */             String nextKey = iterMap.next();
/* 120 */             scOneConfig = new SubConfigurationWrapper((Configuration)configurationService, "frame.adapter.help." + nextKey + ".db.");
/* 121 */             if (scOneConfig != null && scOneConfig.getKeys().size() > 0) {
/* 122 */               dbLink = DatabaseLink.openDatabase((Configuration)scOneConfig);
/* 123 */               DBVersionInformation dbInfos = getDBVersionInfo(dbLink);
/* 124 */               if (dbInfos != null) {
/* 125 */                 dbInfo.add(dbInfos);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/* 130 */         if (dbInfo.size() > 0) {
/* 131 */           this.moduleInfo = ModuleInformationFactory.create((Module)this, null, null, dbInfo);
/*     */         }
/*     */       } 
/*     */     } 
/* 135 */     return this.moduleInfo;
/*     */   }
/*     */   
/*     */   public DBVersionInformation getDBVersionInfo(IDatabaseLink dbLink) {
/*     */     try {
/* 140 */       Connection connection = dbLink.requestConnection();
/*     */       try {
/* 142 */         PreparedStatement stmt = connection.prepareStatement("select release_id,release_date,description,version from release");
/*     */         try {
/* 144 */           ResultSet rs = stmt.executeQuery();
/*     */           try {
/* 146 */             if (rs.next()) {
/* 147 */               String release = rs.getString(1);
/* 148 */               Date date = rs.getDate(2);
/* 149 */               String description = rs.getString(3);
/* 150 */               String version = rs.getString(4);
/*     */               
/* 152 */               return new DBVersionInformation(release, date, description, version);
/*     */             } 
/* 154 */             return null;
/*     */           } finally {
/*     */             
/* 157 */             rs.close();
/*     */           } 
/*     */         } finally {
/* 160 */           stmt.close();
/*     */         } 
/*     */       } finally {
/* 163 */         dbLink.releaseConnection(connection);
/*     */       } 
/* 165 */     } catch (Exception e) {
/* 166 */       log.error("unable to retrieve version information, returning null - exception: " + e, e);
/* 167 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isSupported(String salesmake) {
/* 172 */     return true;
/*     */   }
/*     */   
/*     */   public Object getIdentifier() {
/* 176 */     return "helpservice";
/*     */   }
/*     */   
/*     */   public Iterator getInstances() {
/* 180 */     return HelpProvider.getInstances();
/*     */   }
/*     */   
/*     */   public IOFactory getIOFactory(String key) {
/* 184 */     return HelpProvider.getFactory(key);
/*     */   }
/*     */   
/*     */   public IDatabaseLink getDatabaseLink(String key) {
/* 188 */     return HelpProvider.getDatabaseLink(key);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\help\implementation\service\HelpServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */