/*     */ package com.eoos.gm.tis2web.news.implementation.service;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.CTOCServiceProvider;
/*     */ import com.eoos.gm.tis2web.ctoc.service.NewsCTOCService;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCDomain;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.IOFactory;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import com.eoos.gm.tis2web.frame.export.ConfigurationServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
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
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.FrameService;
/*     */ import com.eoos.gm.tis2web.news.implementation.NewsProvider;
/*     */ import com.eoos.gm.tis2web.news.implementation.ui.html.main.MainPage;
/*     */ import com.eoos.gm.tis2web.news.service.NewsService;
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
/*     */ public class NewsServiceImpl
/*     */   implements NewsService
/*     */ {
/*  42 */   private static final Logger log = Logger.getLogger(NewsServiceImpl.class);
/*     */   
/*  44 */   private final Object SYNC_MODULEINFO = new Object();
/*     */   
/*  46 */   private ModuleInformation moduleInfo = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String KEY_VERSION = "news.version";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NewsServiceImpl() {
/*  56 */     (new Thread() {
/*     */         public void run() {
/*  58 */           NewsServiceImpl.log.debug("initializing news service...");
/*  59 */           CTOCServiceProvider.getInstance().getService(CTOCServiceProvider.NEWS).getCTOC();
/*  60 */           NewsServiceImpl.log.debug("...done initializing news service");
/*     */         }
/*     */       }).start();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getType() {
/*  67 */     return "news";
/*     */   }
/*     */   
/*     */   public Object getUI(String sessionID, Map parameters) {
/*  71 */     containsNewItems(sessionID, true);
/*  72 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/*  73 */     SharedContextProxy.getInstance(context).update();
/*  74 */     MainPage page = MainPage.constructInstance(context, parameters);
/*  75 */     return page.getHtmlCode(parameters);
/*     */   }
/*     */   
/*     */   public Boolean isActive(String sessionID) {
/*  79 */     return new Boolean(ClientContextProvider.getInstance().isActive(sessionID));
/*     */   }
/*     */   
/*     */   public Boolean invalidateSession(String sessionID) {
/*  83 */     return new Boolean(ClientContextProvider.getInstance().invalidateSession(sessionID));
/*     */   }
/*     */   
/*     */   public ModuleInformation getModuleInformation() {
/*  87 */     synchronized (this.SYNC_MODULEINFO) {
/*  88 */       if (this.moduleInfo == null) {
/*  89 */         String sBaseConfig = "frame.adapter.news.";
/*  90 */         ConfigurationService configurationService = ConfigurationServiceProvider.getService();
/*  91 */         SubConfigurationWrapper configIDs = new SubConfigurationWrapper((Configuration)configurationService, "frame.adapter.news.");
/*     */         
/*  93 */         HashMap<Object, Object> keyMap = null;
/*  94 */         IDatabaseLink dbLink = null;
/*  95 */         List<DBVersionInformation> dbInfo = new LinkedList();
/*     */         
/*  97 */         SubConfigurationWrapper scOneConfig = new SubConfigurationWrapper((Configuration)configurationService, "frame.adapter.news.db.");
/*  98 */         if (scOneConfig != null && scOneConfig.getKeys().size() > 0) {
/*  99 */           dbLink = DatabaseLink.openDatabase((Configuration)scOneConfig);
/* 100 */           DBVersionInformation dbInfos = getDBVersionInfo(dbLink);
/* 101 */           if (dbInfos != null) {
/* 102 */             dbInfo.add(dbInfos);
/*     */           }
/*     */         } else {
/*     */           
/* 106 */           keyMap = new HashMap<Object, Object>();
/* 107 */           for (Iterator<String> subiter = configIDs.getKeys().iterator(); subiter.hasNext(); ) {
/* 108 */             String nextKey = subiter.next();
/* 109 */             if (nextKey.startsWith("."))
/* 110 */               nextKey = nextKey.substring(1); 
/* 111 */             String fullKey = nextKey.substring(0, (nextKey.indexOf('.') != -1) ? nextKey.indexOf('.') : nextKey.length());
/* 112 */             keyMap.put(fullKey, fullKey);
/*     */           } 
/*     */           
/* 115 */           for (Iterator<String> iterMap = keyMap.keySet().iterator(); iterMap.hasNext(); ) {
/* 116 */             String nextKey = iterMap.next();
/* 117 */             scOneConfig = new SubConfigurationWrapper((Configuration)configurationService, "frame.adapter.news." + nextKey + ".db.");
/* 118 */             if (scOneConfig != null && scOneConfig.getKeys().size() > 0) {
/* 119 */               dbLink = DatabaseLink.openDatabase((Configuration)scOneConfig);
/* 120 */               DBVersionInformation dbInfos = getDBVersionInfo(dbLink);
/* 121 */               if (dbInfos != null) {
/* 122 */                 dbInfo.add(dbInfos);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/* 127 */         if (dbInfo.size() > 0) {
/* 128 */           this.moduleInfo = ModuleInformationFactory.create((Module)this, null, null, dbInfo);
/*     */         }
/*     */       } 
/*     */     } 
/* 132 */     return this.moduleInfo;
/*     */   }
/*     */   
/*     */   public DBVersionInformation getDBVersionInfo(IDatabaseLink dbLink) {
/*     */     try {
/* 137 */       Connection connection = dbLink.requestConnection();
/*     */       try {
/* 139 */         PreparedStatement stmt = connection.prepareStatement("select release_id,release_date,description,version from release");
/*     */         try {
/* 141 */           ResultSet rs = stmt.executeQuery();
/*     */           try {
/* 143 */             if (rs.next()) {
/* 144 */               String release = rs.getString(1);
/* 145 */               Date date = rs.getDate(2);
/* 146 */               String description = rs.getString(3);
/* 147 */               String version = rs.getString(4);
/*     */               
/* 149 */               return new DBVersionInformation(release, date, description, version);
/*     */             } 
/* 151 */             return null;
/*     */           } finally {
/*     */             
/* 154 */             rs.close();
/*     */           } 
/*     */         } finally {
/* 157 */           stmt.close();
/*     */         } 
/*     */       } finally {
/* 160 */         dbLink.releaseConnection(connection);
/*     */       } 
/* 162 */     } catch (Exception e) {
/* 163 */       log.error("unable to retrieve version information, returning null - exception: " + e, e);
/* 164 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private Boolean containsNewItems(String sessionID, boolean writeNewVersion) {
/* 169 */     Boolean retValue = Boolean.FALSE;
/*     */     try {
/* 171 */       ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/* 172 */       SharedContextProxy.getInstance(context).update();
/*     */       
/* 174 */       NewsCTOCService service = (NewsCTOCService)CTOCServiceProvider.getInstance().getService(CTOCServiceProvider.NEWS);
/* 175 */       CTOCNode root = service.getCTOC().getCTOC(CTOCDomain.NEWS);
/* 176 */       String version = (String)root.getProperty((SITOCProperty)CTOCProperty.VERSION);
/*     */       
/* 178 */       FrameService fmi = (FrameService)FrameServiceProvider.getInstance().getService(FrameService.class);
/* 179 */       String storedVersion = (String)fmi.getPersistentObject(sessionID, "news.version");
/* 180 */       if (storedVersion == null) {
/* 181 */         storedVersion = "";
/*     */       }
/*     */       
/* 184 */       retValue = new Boolean(!storedVersion.equals(version));
/*     */       
/* 186 */       if (retValue.booleanValue() && writeNewVersion) {
/* 187 */         fmi.setPersistentObject(sessionID, "news.version", version);
/*     */       }
/* 189 */     } catch (Exception e) {
/* 190 */       log.error("::containsNewItems - error:" + e, e);
/* 191 */       log.info(":: containsNewItems - returning false");
/*     */     } 
/* 193 */     return retValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean containsNewItems(String sessionID) {
/* 203 */     return containsNewItems(sessionID, false);
/*     */   }
/*     */   
/*     */   public boolean isSupported(String salesmake) {
/* 207 */     return true;
/*     */   }
/*     */   
/*     */   public Object getIdentifier() {
/* 211 */     return "newsservice";
/*     */   }
/*     */   
/*     */   public void reset() {
/* 215 */     log.info("resetting news service");
/* 216 */     NewsProvider.reset();
/* 217 */     NewsCTOCService service = (NewsCTOCService)CTOCServiceProvider.getInstance().getService(CTOCServiceProvider.NEWS);
/* 218 */     service.reset();
/* 219 */     this.moduleInfo = null;
/*     */   }
/*     */   
/*     */   public Iterator getInstances() {
/* 223 */     return NewsProvider.getInstances();
/*     */   }
/*     */   
/*     */   public IOFactory getIOFactory(String key) {
/* 227 */     return NewsProvider.getFactory(key);
/*     */   }
/*     */   
/*     */   public IDatabaseLink getDatabaseLink(String key) {
/* 231 */     return NewsProvider.getDatabaseLink(key);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\news\implementation\service\NewsServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */