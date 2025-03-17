/*    */ package com.eoos.gm.tis2web.dtc.implementation.export;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import com.eoos.propcfg.SubConfigurationWrapper;
/*    */ import com.eoos.scsm.v2.reflect.ReflectionUtil;
/*    */ import java.lang.reflect.Constructor;
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.Iterator;
/*    */ import java.util.Locale;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class DTCExportManager
/*    */ {
/* 16 */   private static final Logger log = Logger.getLogger(DTCExportManager.class);
/*    */ 
/*    */ 
/*    */   
/*    */   private boolean initialized = false;
/*    */ 
/*    */ 
/*    */   
/*    */   private static String normalize(String string) {
/* 25 */     String retValue = string;
/* 26 */     if (retValue != null) {
/* 27 */       retValue = retValue.toLowerCase(Locale.ENGLISH);
/* 28 */       retValue = retValue.trim();
/*    */     } else {
/* 30 */       retValue = "";
/*    */     } 
/* 32 */     return retValue;
/*    */   }
/*    */   
/*    */   public static synchronized DTCExportManager getInstance() {
/* 36 */     DTCExportManager instance = (DTCExportManager)ApplicationContext.getInstance().getObject(DTCExportManager.class);
/* 37 */     if (instance == null) {
/* 38 */       instance = new DTCExportManager();
/* 39 */       ApplicationContext.getInstance().storeObject(DTCExportManager.class, instance);
/*    */     } 
/* 41 */     return instance;
/*    */   }
/*    */   
/*    */   public synchronized void init() {
/* 45 */     if (!this.initialized) {
/* 46 */       log.info("initializing ...");
/* 47 */       SubConfigurationWrapper subConfigurationWrapper = new SubConfigurationWrapper((Configuration)ApplicationContext.getInstance(), "frame.dtc.log.export.");
/* 48 */       if (subConfigurationWrapper.getProperty("enabled") != null && subConfigurationWrapper.getProperty("enabled").equalsIgnoreCase("true")) {
/*    */ 
/*    */ 
/*    */         
/* 52 */         String exportServer = normalize(subConfigurationWrapper.getProperty("server"));
/* 53 */         if (ApplicationContext.getInstance().isLocalServer(exportServer)) {
/* 54 */           log.info("...initializing configured exports");
/* 55 */           for (Iterator<String> iter = subConfigurationWrapper.getKeys().iterator(); iter.hasNext(); ) {
/* 56 */             String key = iter.next();
/* 57 */             if (key.endsWith(".class")) {
/* 58 */               final String exportName = key.substring(0, key.length() - 6);
/* 59 */               log.info("....initializing/starting export: " + exportName);
/* 60 */               final SubConfigurationWrapper exportConfig = new SubConfigurationWrapper((Configuration)subConfigurationWrapper, exportName + ".");
/*    */               try {
/* 62 */                 AutomaticExport export = (AutomaticExport)ReflectionUtil.createInstance(subConfigurationWrapper.getProperty(key), new ReflectionUtil.CreationCallback()
/*    */                     {
/*    */                       public boolean useMethod(Method method) {
/* 65 */                         return false;
/*    */                       }
/*    */                       
/*    */                       public boolean useConstructor(Constructor constructor) {
/* 69 */                         return true;
/*    */                       }
/*    */                       
/*    */                       public Object[] getParameters() {
/* 73 */                         return new Object[] { this.val$exportName, this.val$exportConfig };
/*    */                       }
/*    */                     });
/* 76 */                 log.info("...starting " + exportName + "...");
/* 77 */                 export.start();
/* 78 */               } catch (Exception e) {
/* 79 */                 log.warn("....unable to init export: " + exportName + " - exception:" + e, e);
/*    */               } 
/*    */             } 
/*    */           } 
/*    */         } else {
/*    */           
/* 85 */           log.info("... this server is not the configured  export server");
/*    */         } 
/*    */       } else {
/*    */         
/* 89 */         log.info("...export functionality is not enabled ");
/*    */       } 
/* 91 */       this.initialized = true;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\dtc\implementation\export\DTCExportManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */