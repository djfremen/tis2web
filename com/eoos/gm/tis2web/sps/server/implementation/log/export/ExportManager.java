/*    */ package com.eoos.gm.tis2web.sps.server.implementation.log.export;
/*    */ 
/*    */ import com.eoos.datatype.marker.Configurable;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.log.SPSEventLogFacade;
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
/*    */   public static ExportManager getInstance() {
/* 25 */     synchronized (ApplicationContext.getInstance().getLockObject()) {
/* 26 */       ExportManager instance = (ExportManager)ApplicationContext.getInstance().getObject(ExportManager.class);
/* 27 */       if (instance == null) {
/* 28 */         instance = new ExportManager();
/* 29 */         ApplicationContext.getInstance().storeObject(ExportManager.class, instance);
/*    */       } 
/* 31 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void start() {
/* 36 */     log.info("starting SPS event log export manager");
/* 37 */     if (SPSEventLogFacade.getInstance().isEnabled() && SPSEventLogFacade.getInstance().supportsQuery()) {
/* 38 */       SubConfigurationWrapper subConfigurationWrapper = new SubConfigurationWrapper((Configuration)ApplicationContext.getInstance(), "component.sps.event.log.export.");
/*    */ 
/*    */       
/* 41 */       String exportServer = subConfigurationWrapper.getProperty("server");
/* 42 */       if (ApplicationContext.getInstance().isLocalServer(exportServer)) {
/* 43 */         log.info("...initializing configured exports");
/* 44 */         for (Iterator<String> iter = subConfigurationWrapper.getKeys().iterator(); iter.hasNext(); ) {
/* 45 */           String key = iter.next();
/* 46 */           if (key.endsWith(".class")) {
/* 47 */             String exportName = key.substring(0, key.length() - 6);
/* 48 */             log.info("....initializing/starting export: " + exportName);
/* 49 */             SubConfigurationWrapper subConfigurationWrapper1 = new SubConfigurationWrapper((Configuration)subConfigurationWrapper, exportName + ".");
/*    */             try {
/* 51 */               Class<?> clazz = Class.forName(subConfigurationWrapper.getProperty(key));
/* 52 */               AutomaticExport export = null;
/* 53 */               Collection interfaces = ClassUtil.getAllInterfaces(clazz);
/* 54 */               if (interfaces.contains(Configurable.class)) {
/* 55 */                 if (interfaces.contains(Singleton.class)) {
/* 56 */                   export = (AutomaticExport)clazz.getMethod("createInstance", new Class[] { Configuration.class }).invoke(null, new Object[] { subConfigurationWrapper1 });
/*    */                 } else {
/* 58 */                   export = clazz.getConstructor(new Class[] { Configuration.class }).newInstance(new Object[] { subConfigurationWrapper1 });
/*    */                 }
/*    */               
/* 61 */               } else if (interfaces.contains(Singleton.class)) {
/* 62 */                 export = (AutomaticExport)clazz.getMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
/*    */               } else {
/* 64 */                 export = (AutomaticExport)clazz.newInstance();
/*    */               } 
/*    */               
/* 67 */               Boolean enabled = (new TypeDecorator((Configuration)subConfigurationWrapper1)).getBoolean("enable");
/* 68 */               if (enabled != null && enabled.booleanValue()) {
/* 69 */                 log.info("...starting " + exportName + "...");
/* 70 */                 export.start(); continue;
/*    */               } 
/* 72 */               log.info("...export " + exportName + " not started (diabled)");
/*    */             }
/* 74 */             catch (Exception e) {
/* 75 */               log.warn("....unable to init export: " + exportName + " - exception:" + e);
/*    */             }
/*    */           
/*    */           } 
/*    */         } 
/*    */       } else {
/*    */         
/* 82 */         log.info("... this server is not the configured sps export server");
/*    */       } 
/*    */     } else {
/*    */       
/* 86 */       log.info("...disabling all exports since configured sps event log facility is disabled or does not support queries");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\log\export\ExportManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */