/*    */ package com.eoos.gm.tis2web.sps.server.implementation.log;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import com.eoos.propcfg.SubConfigurationWrapper;
/*    */ import com.eoos.propcfg.TypeDecorator;
/*    */ import java.util.Collection;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SPSEventLogDB
/*    */   implements SPSEventLog.SPI, SPSEventLog.Deletion
/*    */ {
/* 15 */   private static final Logger log = Logger.getLogger(SPSEventLogDB.class);
/*    */ 
/*    */   
/*    */   private Configuration configuration;
/*    */   
/* 20 */   private final Object SYNC_INITIALZED = new Object();
/*    */   
/* 22 */   private SPSEventLogDB2 initializedDelegate = null;
/*    */   
/*    */   public SPSEventLogDB(Configuration configuration) throws Exception {
/* 25 */     this.configuration = configuration;
/*    */   }
/*    */   
/*    */   private SPSEventLogDB2 initialize() {
/* 29 */     String serverName = ApplicationContext.getInstance().getHostName();
/* 30 */     serverName = String.valueOf(serverName) + "(" + ApplicationContext.getInstance().getIPAddr() + ")";
/*    */     
/* 32 */     OnstarAttributeLog onstarLog = null;
/*    */     try {
/* 34 */       SubConfigurationWrapper subConfigurationWrapper = new SubConfigurationWrapper(this.configuration, "onstar.");
/* 35 */       if (Boolean.TRUE.equals((new TypeDecorator((Configuration)subConfigurationWrapper)).getBoolean("enable"))) {
/* 36 */         onstarLog = OnstarAttributeLog.create((Configuration)subConfigurationWrapper);
/*    */       }
/* 38 */     } catch (Exception e) {
/* 39 */       log.warn("...unable to init onstar log, disabling onstar logging - exception:" + e, e);
/*    */     } 
/*    */     
/* 42 */     SPSEventLogDB2 ret = new SPSEventLogDB2(this.configuration, serverName, onstarLog);
/* 43 */     if (!ret.testDBConnection()) {
/* 44 */       throw new RuntimeException("unable to create database connection for sps event log");
/*    */     }
/* 46 */     return ret;
/*    */   }
/*    */   
/*    */   private SPSEventLogDB2 getInitialized() {
/* 50 */     synchronized (this.SYNC_INITIALZED) {
/* 51 */       if (this.initializedDelegate == null) {
/* 52 */         this.initializedDelegate = initialize();
/*    */       }
/* 54 */       return this.initializedDelegate;
/*    */     } 
/*    */   }
/*    */   
/*    */   public synchronized void ensureInit() {
/* 59 */     getInitialized();
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized void add(Collection metaEntries) throws Exception {
/* 64 */     getInitialized().add(metaEntries);
/*    */   }
/*    */   
/*    */   public synchronized Collection getEntries(SPSEventLog.Query.BackendFilter backendFilter, int hitLimit) throws Exception {
/* 68 */     return getInitialized().getEntries(backendFilter, hitLimit);
/*    */   }
/*    */   
/*    */   public synchronized void delete(Collection entries) throws Exception {
/* 72 */     getInitialized().delete(entries);
/*    */   }
/*    */   
/*    */   public synchronized Object getAttachedObject(SPSEventLog.Entry entry, Attachment.Key key) throws Exception {
/* 76 */     return getInitialized().getAttachedObject(entry, key);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\log\SPSEventLogDB.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */