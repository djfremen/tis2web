/*    */ package com.eoos.gm.tis2web.news.implementation;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.CTOCServiceProvider;
/*    */ import com.eoos.gm.tis2web.ctoc.service.NewsCTOCService;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.IOFactory;
/*    */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*    */ import com.eoos.gm.tis2web.frame.export.declaration.service.ConfigurationService;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import com.eoos.propcfg.SubConfigurationWrapper;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class NewsProvider
/*    */ {
/* 20 */   protected static final Logger log = Logger.getLogger(NewsProvider.class);
/*    */   
/* 22 */   protected static Map databases = null;
/* 23 */   protected static Map factories = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void reset() {
/* 29 */     log.debug("reset news provider");
/* 30 */     databases = null;
/*    */   }
/*    */   
/*    */   public static synchronized Iterator getInstances() {
/* 34 */     if (databases == null) {
/*    */       try {
/* 36 */         init();
/* 37 */       } catch (Exception e) {
/* 38 */         log.error("unable to init News Provider - error:" + e, e);
/* 39 */         throw new RuntimeException();
/*    */       } 
/*    */     }
/* 42 */     return databases.keySet().iterator();
/*    */   }
/*    */   
/*    */   public static synchronized IDatabaseLink getDatabaseLink(String key) {
/* 46 */     if (databases == null) {
/* 47 */       throw new IllegalArgumentException();
/*    */     }
/* 49 */     return (IDatabaseLink)databases.get(key);
/*    */   }
/*    */   
/*    */   public static synchronized IOFactory getFactory(String key) {
/* 53 */     if (factories == null) {
/* 54 */       throw new IllegalArgumentException();
/*    */     }
/* 56 */     return (IOFactory)factories.get(key);
/*    */   }
/*    */   
/*    */   public static void init() throws Exception {
/* 60 */     databases = new HashMap<Object, Object>();
/* 61 */     factories = new HashMap<Object, Object>();
/* 62 */     NewsCTOCService service = (NewsCTOCService)CTOCServiceProvider.getInstance().getService(CTOCServiceProvider.NEWS);
/* 63 */     ConfigurationService configurationService = (ConfigurationService)FrameServiceProvider.getInstance().getService(ConfigurationService.class);
/*    */     try {
/* 65 */       DatabaseLink dblink = DatabaseLink.openDatabase((Configuration)configurationService, "frame.adapter.news.db");
/* 66 */       if (dblink != null) {
/* 67 */         databases.put("TIS2WEB", dblink);
/* 68 */         factories.put("TIS2WEB", service.createIOFactory((IDatabaseLink)dblink));
/*    */       } 
/* 70 */     } catch (Exception x) {
/* 71 */       log.info("no default news database configured.");
/*    */     } 
/* 73 */     SubConfigurationWrapper adapterIDs = new SubConfigurationWrapper((Configuration)configurationService, "frame.adapter.news.");
/* 74 */     for (Iterator<String> iter = adapterIDs.getKeys().iterator(); iter.hasNext(); ) {
/* 75 */       String key = iter.next();
/* 76 */       if (key.endsWith(".db.data-source") || key.endsWith(".db.url")) {
/* 77 */         String identifier = key.substring(0, key.indexOf('.'));
/* 78 */         log.info("... creating news adapter for " + String.valueOf(identifier));
/* 79 */         databases.put(identifier, DatabaseLink.openDatabase((Configuration)adapterIDs, identifier + ".db"));
/* 80 */         factories.put(identifier, service.createIOFactory((IDatabaseLink)databases.get(identifier)));
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\news\implementation\NewsProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */