/*     */ package com.eoos.gm.tis2web.si.implementation.log;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.system.context.ModuleContext;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.propcfg.TypeDecorator;
/*     */ import com.eoos.propcfg.util.ConfigurationUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SIEventLogFacade
/*     */ {
/*     */   private final class EntriesFlush
/*     */     implements Runnable
/*     */   {
/*     */     private boolean immediateFlush;
/*     */     
/*     */     private EntriesFlush(boolean immediateFlush) {
/*  28 */       this.immediateFlush = immediateFlush;
/*     */     }
/*     */     
/*     */     private EntriesFlush() {
/*  32 */       this(false);
/*     */     }
/*     */     public void run() {
/*     */       try {
/*     */         List toFlush;
/*  37 */         SIEventLogFacade.log.debug("executing entries flushing thread ...");
/*  38 */         if (!this.immediateFlush) {
/*  39 */           SIEventLogFacade.log.debug("...waiting " + SIEventLogFacade.this.writeDelay + " ms ...");
/*  40 */           Thread.sleep(SIEventLogFacade.this.writeDelay);
/*     */         } else {
/*  42 */           SIEventLogFacade.log.debug("...immediate flush is activated");
/*     */         } 
/*     */         
/*  45 */         synchronized (SIEventLogFacade.this.entries) {
/*  46 */           toFlush = SIEventLogFacade.this.entries;
/*  47 */           SIEventLogFacade.this.entries = new LinkedList();
/*     */         } 
/*  49 */         SIEventLogFacade.log.debug("...retrieved " + toFlush.size() + " entries to flush");
/*  50 */         if (!Util.isNullOrEmpty(toFlush)) {
/*  51 */           SIEventLogFacade.log.debug("....flushing ");
/*  52 */           SIEventLogFacade.this.backend.add(toFlush);
/*     */         } 
/*     */         
/*  55 */         SIEventLogFacade.this.tFlush = null;
/*  56 */         SIEventLogFacade.log.debug("...done");
/*  57 */       } catch (InterruptedException e) {
/*  58 */         SIEventLogFacade.log.debug("...interrupted, resetting interruption state");
/*  59 */         Thread.currentThread().interrupt();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  65 */   private static final Logger log = Logger.getLogger(SIEventLogFacade.class);
/*     */   
/*  67 */   private static SIEventLogFacade instance = null;
/*     */   
/*  69 */   private SIEventLog.SPI backend = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean enabled = false;
/*     */ 
/*     */ 
/*     */ 
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
/*     */ 
/*     */   
/*     */   private List entries;
/*     */ 
/*     */ 
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized SIEventLogFacade getInstance() {
/* 113 */     if (instance == null) {
/* 114 */       instance = new SIEventLogFacade();
/*     */     }
/* 116 */     return instance;
/*     */   }
/*     */   
/* 119 */   private SIEventLogFacade() { this.entries = new LinkedList();
/*     */     
/* 121 */     this.tFlush = null; log.info("initializing si event log"); try { SubConfigurationWrapper subConfigurationWrapper = new SubConfigurationWrapper((Configuration)ApplicationContext.getInstance(), "component.si.event.log."); if (subConfigurationWrapper.getProperty("enable") != null && (new TypeDecorator((Configuration)subConfigurationWrapper)).getBoolean("enable").booleanValue()) { this.backend = new SIEventLog_DB((Configuration)new SubConfigurationWrapper((Configuration)subConfigurationWrapper, "db.")); } else { log.info("....disabling si event log , unknown type"); this.backend = null; }  this.enabled = (this.backend != null); if (this.enabled) { Number writeDelay = ConfigurationUtil.getNumber("write.delay", (Configuration)subConfigurationWrapper); if (writeDelay != null) { this.writeDelay = writeDelay.longValue(); } else { this.writeDelay = 60000L; }  log.debug("...write delay: " + this.writeDelay + " ms"); log.info("...initialized si event log"); }  ApplicationContext.getInstance().addShutdownListener(new ApplicationContext.ShutdownListener() { public void onShutdown() { (new SIEventLogFacade.EntriesFlush(true)).run(); } }
/*     */         ); }
/*     */     catch (Exception e) { log.warn("unable to initialize - exception: " + e); log.warn("...disabling login log"); }
/* 124 */      } public void add(SIEventLog.Entry entry) throws Exception { if (this.enabled && entry != null) {
/* 125 */       synchronized (this.entries) {
/* 126 */         this.entries.add(entry);
/* 127 */         if (this.tFlush == null) {
/* 128 */           log.debug("starting write-back thread");
/* 129 */           this.tFlush = Util.createAndStartThread(new EntriesFlush());
/*     */         } 
/*     */       } 
/*     */     } }
/*     */ 
/*     */   
/*     */   public boolean isEnabled() {
/* 136 */     return this.enabled;
/*     */   }
/*     */   
/*     */   public void createEntry(String docInfo, ClientContext context) {
/*     */     try {
/* 141 */       if (isEnabled()) {
/* 142 */         String userName = context.getSessionID() + "@" + context.getLocale();
/*     */         
/* 144 */         IConfiguration cfg = VCFacade.getInstance(context).getCfg();
/* 145 */         String vehicle = VehicleConfigurationUtil.toString(cfg, true);
/* 146 */         String siModuleType = ModuleContext.getInstance(context.getSessionID()).getPageIdentifier();
/* 147 */         add(new SIEventLogAdapter(docInfo, siModuleType, userName, vehicle.toUpperCase()));
/*     */       } 
/* 149 */     } catch (Exception e) {
/* 150 */       log.error("unable to create an entry si static document for si event, " + e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void createEntryStatic(String fileName, ClientContext context) {
/*     */     try {
/* 157 */       if (isEnabled()) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 162 */         String userName = context.getSessionID() + "@" + context.getLocale();
/* 163 */         String vehicle = VCFacade.getInstance(context).getCurrentVCDisplay(false);
/* 164 */         String siModuleType = ModuleContext.getInstance(context.getSessionID()).getPageIdentifier() + "_STATIC";
/* 165 */         add(new SIEventLogAdapter(fileName, siModuleType, userName, vehicle));
/*     */       } 
/* 167 */     } catch (Exception e) {
/* 168 */       log.error("unable to create an entry si static document for si event, " + e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\log\SIEventLogFacade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */