/*    */ package com.eoos.gm.tis2web.swdl.server.datamodel.system.metrics.export;
/*    */ 
/*    */ import com.eoos.datatype.marker.Configurable;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.swdl.server.datamodel.system.metrics.SWDLMetricsLogFacade;
/*    */ import com.eoos.instantiation.Singleton;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import com.eoos.propcfg.SubConfigurationWrapper;
/*    */ import com.eoos.propcfg.TypeDecorator;
/*    */ import com.eoos.util.ClassUtil;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class ExportManager
/*    */ {
/* 18 */   private static final Logger log = Logger.getLogger(ExportManager.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized ExportManager getInstance() {
/* 25 */     ExportManager instance = (ExportManager)ApplicationContext.getInstance().getObject(ExportManager.class);
/* 26 */     if (instance == null) {
/* 27 */       instance = new ExportManager();
/* 28 */       ApplicationContext.getInstance().storeObject(ExportManager.class, instance);
/*    */     } 
/* 30 */     return instance;
/*    */   }
/*    */   
/*    */   public void start() {
/* 34 */     log.info("starting SWDL export manager");
/* 35 */     if (SWDLMetricsLogFacade.getInstance().isEnabled() && SWDLMetricsLogFacade.getInstance().supportsQuery()) {
/* 36 */       SubConfigurationWrapper subConfigurationWrapper = new SubConfigurationWrapper((Configuration)ApplicationContext.getInstance(), "component.swdl.metrics.export.");
/*    */ 
/*    */       
/* 39 */       String exportServer = subConfigurationWrapper.getProperty("server");
/* 40 */       if (ApplicationContext.getInstance().isLocalServer(exportServer)) {
/* 41 */         log.info("...initializing configured exports");
/* 42 */         for (Iterator<String> iter = subConfigurationWrapper.getKeys().iterator(); iter.hasNext(); ) {
/* 43 */           String key = iter.next();
/* 44 */           if (key.endsWith(".class")) {
/* 45 */             String exportName = key.substring(0, key.length() - 6);
/* 46 */             log.info("....initializing/starting export: " + exportName);
/* 47 */             SubConfigurationWrapper subConfigurationWrapper1 = new SubConfigurationWrapper((Configuration)subConfigurationWrapper, exportName + ".");
/*    */             try {
/* 49 */               Class<?> clazz = Class.forName(subConfigurationWrapper.getProperty(key));
/* 50 */               AutomaticExport export = null;
/* 51 */               Collection interfaces = ClassUtil.getAllInterfaces(clazz);
/* 52 */               if (interfaces.contains(Configurable.class)) {
/* 53 */                 if (interfaces.contains(Singleton.class)) {
/* 54 */                   export = (AutomaticExport)clazz.getMethod("createInstance", new Class[] { Configuration.class }).invoke(null, new Object[] { subConfigurationWrapper1 });
/*    */                 } else {
/* 56 */                   export = clazz.getConstructor(new Class[] { Configuration.class }).newInstance(new Object[] { subConfigurationWrapper1 });
/*    */                 }
/*    */               
/* 59 */               } else if (interfaces.contains(Singleton.class)) {
/* 60 */                 export = (AutomaticExport)clazz.getMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
/*    */               } else {
/* 62 */                 export = (AutomaticExport)clazz.newInstance();
/*    */               } 
/*    */               
/* 65 */               Boolean enabled = (new TypeDecorator((Configuration)subConfigurationWrapper1)).getBoolean("enable");
/* 66 */               if (enabled != null && enabled.booleanValue()) {
/* 67 */                 log.info("...starting " + exportName + "...");
/* 68 */                 export.start(); continue;
/*    */               } 
/* 70 */               log.info("...export " + exportName + " not started (disabled)");
/*    */             }
/* 72 */             catch (Exception e) {
/* 73 */               log.warn("....unable to init export: " + exportName + " - exception:" + e);
/*    */             }
/*    */           
/*    */           } 
/*    */         } 
/*    */       } else {
/*    */         
/* 80 */         log.info("... this server is not the configured swdl export server");
/*    */       } 
/*    */     } else {
/*    */       
/* 84 */       log.info("...disabling all exports since configured swdl metrics facility is disabled or does not support queries");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\server\datamodel\system\metrics\export\ExportManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */