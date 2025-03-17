/*     */ package com.eoos.gm.tis2web.sps.server.implementation.log;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.ConfigurationService;
/*     */ import com.eoos.log.Log4jUtil;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.propcfg.TypeDecorator;
/*     */ import com.eoos.propcfg.util.ConfigurationUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.Task;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class SPSEventLogFacade
/*     */   implements SPSEventLog, SPSEventLog.Deletion
/*     */ {
/*  23 */   private static final Logger log = Logger.getLogger(SPSEventLogFacade.class);
/*     */   
/*     */   private final class EntriesFlush
/*     */     implements Runnable {
/*     */     private boolean immediateFlush;
/*     */     
/*     */     private EntriesFlush(boolean immediateFlush) {
/*  30 */       this.immediateFlush = immediateFlush;
/*     */     }
/*     */     
/*     */     private EntriesFlush() {
/*  34 */       this(false);
/*     */     }
/*     */     public void run() {
/*     */       try {
/*     */         List toFlush;
/*  39 */         SPSEventLogFacade.log.debug("executing entries flushing thread ...");
/*  40 */         if (!this.immediateFlush) {
/*  41 */           SPSEventLogFacade.log.debug("...waiting " + SPSEventLogFacade.this.writeDelay + " ms ...");
/*  42 */           Thread.sleep(SPSEventLogFacade.this.writeDelay);
/*     */         } else {
/*  44 */           SPSEventLogFacade.log.debug("...immediate flush is activated");
/*     */         } 
/*     */         
/*  47 */         synchronized (SPSEventLogFacade.this.metaEntries) {
/*  48 */           toFlush = SPSEventLogFacade.this.metaEntries;
/*  49 */           SPSEventLogFacade.this.metaEntries = new LinkedList();
/*     */         } 
/*  51 */         SPSEventLogFacade.log.debug("...retrieved " + toFlush.size() + " entries to flush");
/*  52 */         if (!Util.isNullOrEmpty(toFlush)) {
/*  53 */           SPSEventLogFacade.log.debug("....flushing ");
/*     */           try {
/*  55 */             SPSEventLogFacade.this.getBackend().add(toFlush);
/*  56 */           } catch (Exception e) {
/*  57 */             SPSEventLogFacade.log.error("unable to flush " + toFlush.size() + " entries - exception: " + e, e);
/*     */           } 
/*     */         } 
/*     */         
/*  61 */         SPSEventLogFacade.this.tFlush = null;
/*  62 */         SPSEventLogFacade.log.debug("...done");
/*  63 */       } catch (InterruptedException e) {
/*  64 */         SPSEventLogFacade.log.debug("...interrupted, resetting interruption state");
/*  65 */         Thread.currentThread().interrupt();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static class EagerInitialization
/*     */     implements Task, Task.LocalExecution
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*  74 */     private static final Logger log = Logger.getLogger(EagerInitialization.class);
/*     */     
/*     */     public Object execute() {
/*  77 */       log.debug("ensuring initialization of sps event log");
/*  78 */       SPSEventLogFacade.getInstance().ensureInit();
/*  79 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  84 */   private static SPSEventLogFacade instance = null;
/*     */   
/*  86 */   private final Object SYNC_BACKEND = new Object();
/*     */   
/*  88 */   private SPSEventLog.SPI backend = null;
/*     */   
/*  90 */   private int configHash = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String TYPE_LOG4J = "log4j";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String TYPE_DB = "db";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long writeDelay;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List metaEntries;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Thread tFlush;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int configHash() {
/* 127 */     ConfigurationService configService = (ConfigurationService)FrameServiceProvider.getInstance().getService(ConfigurationService.class);
/* 128 */     return ConfigurationUtil.configurationHash((Configuration)configService, Collections.singleton("component.sps.event.log."), ConfigurationUtil.MODE_PREFIXES);
/*     */   }
/*     */   
/*     */   private void reset() {
/* 132 */     synchronized (this.SYNC_BACKEND) {
/* 133 */       this.backend = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void init() {
/* 138 */     getBackend();
/*     */   }
/*     */   
/*     */   private SPSEventLog.SPI getBackend() {
/* 142 */     synchronized (this.SYNC_BACKEND) {
/* 143 */       if (this.backend == null) {
/* 144 */         log.info("initializing sps event logging");
/* 145 */         this.backend = new SPSEventLog_Disabled();
/*     */         try {
/* 147 */           SubConfigurationWrapper subConfigurationWrapper = new SubConfigurationWrapper((Configuration)ApplicationContext.getInstance(), "component.sps.event.log.");
/* 148 */           Number writeDelay = ConfigurationUtil.getNumber("write.delay", (Configuration)subConfigurationWrapper);
/* 149 */           if (writeDelay == null) {
/* 150 */             this.writeDelay = 60000L;
/*     */           } else {
/* 152 */             this.writeDelay = writeDelay.longValue();
/*     */           } 
/* 154 */           log.debug("...write delay: " + this.writeDelay + " ms ");
/*     */           
/* 156 */           if ((new TypeDecorator((Configuration)subConfigurationWrapper)).getBoolean("enable").booleanValue()) {
/* 157 */             String type = subConfigurationWrapper.getProperty("type");
/* 158 */             if ("log4j".equalsIgnoreCase(type)) {
/* 159 */               log.debug("...creating log4j backend");
/* 160 */               this.backend = new SPSEventLog_Log4j((Configuration)new SubConfigurationWrapper((Configuration)subConfigurationWrapper, "file."));
/* 161 */             } else if ("db".equalsIgnoreCase(type)) {
/* 162 */               log.debug("...creating database backend");
/* 163 */               this.backend = new SPSEventLogDB((Configuration)new SubConfigurationWrapper((Configuration)subConfigurationWrapper, "db."));
/*     */             } else {
/* 165 */               throw new IllegalArgumentException("unknown type");
/*     */             } 
/* 167 */             this.backend.ensureInit();
/* 168 */             log.info("...initialized sps event logging");
/*     */           } else {
/*     */             
/* 171 */             log.debug("...sps event logging is disabled by configuration");
/*     */           } 
/* 173 */         } catch (Exception e) {
/* 174 */           log.warn("unable to initialize - exception: " + e);
/* 175 */           log.warn("...disabling sps event logging");
/* 176 */           this.backend = new SPSEventLog_Disabled();
/*     */         } 
/* 178 */         this.configHash = configHash();
/*     */       } 
/* 180 */       return this.backend;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static SPSEventLogFacade getInstance() {
/* 185 */     synchronized (ApplicationContext.getInstance().getLockObject()) {
/* 186 */       if (instance == null) {
/* 187 */         instance = new SPSEventLogFacade();
/*     */       }
/* 189 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/* 193 */   private SPSEventLogFacade() { this.metaEntries = new LinkedList();
/*     */     
/* 195 */     this.tFlush = null; ConfigurationService configService = (ConfigurationService)FrameServiceProvider.getInstance().getService(ConfigurationService.class); configService.addObserver(new ConfigurationService.Observer() { public void onModification() { synchronized (SPSEventLogFacade.this.SYNC_BACKEND) { if (SPSEventLogFacade.this.backend != null && SPSEventLogFacade.configHash() != SPSEventLogFacade.this.configHash) { SPSEventLogFacade.log.info("configuration changed - resetting "); SPSEventLogFacade.this.reset(); SPSEventLogFacade.log.info("...and re-initializing"); SPSEventLogFacade.this.init(); }  }
/*     */              } }
/*     */       ); init(); ApplicationContext.getInstance().addShutdownListener(new ApplicationContext.ShutdownListener() { public void onShutdown() { (new SPSEventLogFacade.EntriesFlush(true)).run(); } }
/* 198 */       ); } public void add(SPSEventLog.Entry entry, Collection flags, Attachment[] attachments) { if (entry != null) {
/* 199 */       SPSEventLog.MetaEntry mEntry = new SPSEventLog.MetaEntry();
/* 200 */       mEntry.entry = entry;
/* 201 */       mEntry.flags = flags;
/* 202 */       mEntry.attachments = attachments;
/*     */       
/* 204 */       synchronized (this.metaEntries) {
/* 205 */         this.metaEntries.add(mEntry);
/* 206 */         if (this.tFlush == null) {
/* 207 */           log.debug("starting write-back thread");
/* 208 */           this.tFlush = Util.createAndStartThread(new EntriesFlush());
/*     */         } 
/*     */       } 
/*     */     }  }
/*     */ 
/*     */   
/*     */   public Collection getEntries(SPSEventLog.Query.BackendFilter backendFilter, int hitLimit) throws Exception {
/* 215 */     Collection retValue = null;
/* 216 */     if (!(getBackend() instanceof SPSEventLog.Query)) {
/* 217 */       log.warn("ignoring query request, since the backend instance does not support this feature");
/* 218 */       retValue = Collections.EMPTY_LIST;
/*     */     } else {
/* 220 */       retValue = ((SPSEventLog.Query)getBackend()).getEntries(backendFilter, hitLimit);
/*     */     } 
/* 222 */     return retValue;
/*     */   }
/*     */   
/*     */   public boolean isEnabled() {
/* 226 */     return !(getBackend() instanceof SPSEventLog_Disabled);
/*     */   }
/*     */   
/*     */   public boolean supportsQuery() {
/* 230 */     return getBackend() instanceof SPSEventLog.Query;
/*     */   }
/*     */   
/*     */   public void delete(Collection entries) throws Exception {
/* 234 */     if (getBackend() instanceof SPSEventLog.Deletion) {
/* 235 */       ((SPSEventLog.Deletion)getBackend()).delete(entries);
/*     */     } else {
/* 237 */       throw new UnsupportedOperationException("backend implementation does not support deletion");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getAttachedObject(SPSEventLog.Entry entry, Attachment.Key key) throws Exception {
/* 243 */     if (!(getBackend() instanceof SPSEventLog.Query)) {
/* 244 */       log.warn("ignoring query request, since the backend instance does not support this feature");
/* 245 */       return null;
/*     */     } 
/* 247 */     return ((SPSEventLog.Query)getBackend()).getAttachedObject(entry, key);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void ensureInit() {
/* 253 */     getBackend().ensureInit();
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 257 */     Log4jUtil.attachConsoleAppender();
/*     */     try {
/* 259 */       String name = EagerInitialization.class.getName();
/* 260 */       log.info("name: " + name);
/* 261 */       Class.forName(name);
/* 262 */       log.info("class loaded");
/* 263 */     } catch (ClassNotFoundException e) {
/* 264 */       log.error("exception: " + e, e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\log\SPSEventLogFacade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */