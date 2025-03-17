/*    */ package com.eoos.gm.tis2web.help.implementation.service;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.implementation.service.HelpCTOCServiceImpl;
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
/*    */ 
/*    */ 
/*    */ public class HelpProvider
/*    */ {
/* 21 */   protected static final Logger log = Logger.getLogger(HelpProvider.class);
/*    */   
/* 23 */   protected static Map databases = null;
/* 24 */   protected static Map factories = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void reset() {
/* 30 */     log.debug("reset help provider");
/* 31 */     databases = null;
/*    */   }
/*    */   
/*    */   public static synchronized Iterator getInstances() {
/* 35 */     if (databases == null) {
/*    */       try {
/* 37 */         init();
/* 38 */       } catch (Exception e) {
/* 39 */         log.error("unable to init Help Provider - error:" + e, e);
/* 40 */         throw new RuntimeException();
/*    */       } 
/*    */     }
/* 43 */     return databases.keySet().iterator();
/*    */   }
/*    */   
/*    */   public static synchronized IDatabaseLink getDatabaseLink(String key) {
/* 47 */     if (databases == null) {
/* 48 */       throw new IllegalArgumentException();
/*    */     }
/* 50 */     return (IDatabaseLink)databases.get(key);
/*    */   }
/*    */   
/*    */   public static synchronized IOFactory getFactory(String key) {
/* 54 */     if (factories == null) {
/* 55 */       throw new IllegalArgumentException();
/*    */     }
/* 57 */     return (IOFactory)factories.get(key);
/*    */   }
/*    */   
/*    */   public static void init() throws Exception {
/* 61 */     databases = new HashMap<Object, Object>();
/* 62 */     factories = new HashMap<Object, Object>();
/* 63 */     HelpCTOCServiceImpl helpCTOCServiceImpl = new HelpCTOCServiceImpl();
/* 64 */     ConfigurationService configurationService = (ConfigurationService)FrameServiceProvider.getInstance().getService(ConfigurationService.class);
/*    */     try {
/* 66 */       DatabaseLink dblink = DatabaseLink.openDatabase((Configuration)configurationService, "frame.adapter.help.db");
/* 67 */       if (dblink != null) {
/* 68 */         databases.put("TIS2WEB", dblink);
/* 69 */         factories.put("TIS2WEB", helpCTOCServiceImpl.createIOFactory((IDatabaseLink)dblink));
/*    */       } 
/* 71 */     } catch (Exception x) {
/* 72 */       log.info("no default help database configured.");
/*    */     } 
/* 74 */     SubConfigurationWrapper adapterIDs = new SubConfigurationWrapper((Configuration)configurationService, "frame.adapter.help.");
/* 75 */     for (Iterator<String> iter = adapterIDs.getKeys().iterator(); iter.hasNext(); ) {
/* 76 */       String key = iter.next();
/* 77 */       if (key.endsWith(".db.data-source") || key.endsWith(".db.url")) {
/* 78 */         String identifier = key.substring(0, key.indexOf('.'));
/* 79 */         log.info("... creating help adapter for " + String.valueOf(identifier));
/* 80 */         databases.put(identifier, DatabaseLink.openDatabase((Configuration)adapterIDs, identifier + ".db"));
/* 81 */         factories.put(identifier, helpCTOCServiceImpl.createIOFactory((IDatabaseLink)databases.get(identifier)));
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\help\implementation\service\HelpProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */