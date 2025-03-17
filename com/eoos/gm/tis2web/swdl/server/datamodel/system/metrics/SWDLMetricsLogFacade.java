/*    */ package com.eoos.gm.tis2web.swdl.server.datamodel.system.metrics;
/*    */ 
/*    */ import com.eoos.filter.Filter;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import com.eoos.propcfg.SubConfigurationWrapper;
/*    */ import com.eoos.propcfg.TypeDecorator;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class SWDLMetricsLogFacade
/*    */   implements SWDLMetricsLog, SWDLMetricsLog.Deletion
/*    */ {
/* 15 */   private static final Logger log = Logger.getLogger(SWDLMetricsLogFacade.class);
/*    */   
/* 17 */   private static SWDLMetricsLogFacade instance = null;
/*    */   
/*    */   private boolean enabled = false;
/*    */   
/* 21 */   private static String TYPE_LOG4J = "log4j";
/*    */   
/* 23 */   private static String TYPE_DB = "db";
/*    */   
/* 25 */   private SWDLMetricsLog backend = null;
/*    */   
/*    */   private SWDLMetricsLogFacade() {
/*    */     try {
/* 29 */       log.info("initializing swdl metrics logging");
/* 30 */       SubConfigurationWrapper subConfigurationWrapper = new SubConfigurationWrapper((Configuration)ApplicationContext.getInstance(), "component.swdl.metrics.");
/* 31 */       if ((new TypeDecorator((Configuration)subConfigurationWrapper)).getBoolean("enable").booleanValue()) {
/* 32 */         String type = subConfigurationWrapper.getProperty("type");
/* 33 */         if (TYPE_LOG4J.equalsIgnoreCase(type)) {
/* 34 */           this.backend = new SWDLMetricsLog_Log4j((Configuration)new SubConfigurationWrapper((Configuration)subConfigurationWrapper, TYPE_LOG4J + "."));
/* 35 */         } else if (TYPE_DB.equalsIgnoreCase(type)) {
/* 36 */           this.backend = new SWDLMetricsLog_DB((Configuration)new SubConfigurationWrapper((Configuration)subConfigurationWrapper, TYPE_DB + "."));
/*    */         } else {
/* 38 */           log.warn("... disabling swdl metrics logging - unknown or missing swdl metrics type ");
/* 39 */           this.backend = null;
/*    */         } 
/*    */         
/* 42 */         this.enabled = (this.backend != null);
/* 43 */         if (this.enabled) {
/* 44 */           log.info("... initialized swdl metrics logging");
/*    */         }
/*    */       } else {
/* 47 */         log.info("... disabled swdl metrics logging");
/*    */       } 
/* 49 */     } catch (Exception e) {
/* 50 */       log.warn("unable to initialize swdl metrics log - exception: " + e);
/* 51 */       log.warn("***** disabling swdl metrics logging");
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static synchronized SWDLMetricsLogFacade getInstance() {
/* 57 */     if (instance == null) {
/* 58 */       instance = new SWDLMetricsLogFacade();
/*    */     }
/* 60 */     return instance;
/*    */   }
/*    */   
/*    */   public void add(SWDLMetricsLog.Entry entry) throws Exception {
/* 64 */     if (this.enabled && entry != null) {
/* 65 */       if (!(entry instanceof EntryWrapper)) {
/* 66 */         entry = new EntryWrapper(entry);
/*    */       }
/* 68 */       this.backend.add(entry);
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean isEnabled() {
/* 73 */     return this.enabled;
/*    */   }
/*    */   
/*    */   public boolean supportsQuery() {
/* 77 */     return this.backend instanceof SWDLMetricsLog.Query;
/*    */   }
/*    */   
/*    */   public Collection getEntries(SWDLMetricsLog.Query.BackendFilter backendFilter, Filter entryFilter, int hitLimit) throws Exception {
/* 81 */     if (supportsQuery()) {
/* 82 */       return ((SWDLMetricsLog.Query)this.backend).getEntries(backendFilter, entryFilter, hitLimit);
/*    */     }
/* 84 */     log.warn("ignoring query request, since the backend instance does not support this feature");
/* 85 */     return Collections.EMPTY_LIST;
/*    */   }
/*    */ 
/*    */   
/*    */   public void delete(Collection entries) throws Exception {
/* 90 */     if (this.backend instanceof SWDLMetricsLog.Deletion) {
/* 91 */       ((SWDLMetricsLog.Deletion)this.backend).delete(entries);
/*    */     } else {
/* 93 */       throw new UnsupportedOperationException("backend implementation does not support deletion");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\server\datamodel\system\metrics\SWDLMetricsLogFacade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */