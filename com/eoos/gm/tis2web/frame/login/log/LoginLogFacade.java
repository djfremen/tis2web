/*     */ package com.eoos.gm.tis2web.frame.login.log;
/*     */ 
/*     */ import com.eoos.filter.Filter;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.propcfg.TypeDecorator;
/*     */ import com.eoos.propcfg.util.ConfigurationUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class LoginLogFacade
/*     */   implements LoginLog, LoginLog.Deletion
/*     */ {
/*     */   private final class EntriesFlush
/*     */     implements Runnable
/*     */   {
/*     */     private boolean immediateFlush;
/*     */     
/*     */     private EntriesFlush(boolean immediateFlush) {
/*  24 */       this.immediateFlush = immediateFlush;
/*     */     }
/*     */     
/*     */     private EntriesFlush() {
/*  28 */       this(false);
/*     */     }
/*     */     public void run() {
/*     */       try {
/*     */         List toFlush;
/*  33 */         LoginLogFacade.log.debug("executing entries flushing thread ...");
/*  34 */         if (!this.immediateFlush) {
/*  35 */           LoginLogFacade.log.debug("...waiting " + LoginLogFacade.this.writeDelay + " ms ...");
/*  36 */           Thread.sleep(LoginLogFacade.this.writeDelay);
/*     */         } else {
/*  38 */           LoginLogFacade.log.debug("...immediate flush is activated");
/*     */         } 
/*     */         
/*  41 */         synchronized (LoginLogFacade.this.entries) {
/*  42 */           toFlush = LoginLogFacade.this.entries;
/*  43 */           LoginLogFacade.this.entries = new LinkedList();
/*     */         } 
/*  45 */         LoginLogFacade.log.debug("...retrieved " + toFlush.size() + " entries to flush");
/*  46 */         if (!Util.isNullOrEmpty(toFlush)) {
/*  47 */           LoginLogFacade.log.debug("....flushing ");
/*  48 */           LoginLogFacade.this.backend.add(toFlush);
/*     */         } 
/*     */         
/*  51 */         LoginLogFacade.this.tFlush = null;
/*  52 */         LoginLogFacade.log.debug("...done");
/*  53 */       } catch (InterruptedException e) {
/*  54 */         LoginLogFacade.log.debug("...interrupted, resetting interruption state");
/*  55 */         Thread.currentThread().interrupt();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  61 */   private static final Logger log = Logger.getLogger(LoginLogFacade.class);
/*     */   
/*  63 */   private static LoginLogFacade instance = null;
/*     */   
/*  65 */   private LoginLog.SPI backend = null;
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
/*     */   private static final String TYPE_LOG4J = "log4j";
/*     */ 
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
/*     */   
/*     */   private long writeDelay;
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
/*     */   private Thread tFlush;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LoginLogFacade getInstance() {
/* 116 */     synchronized (ApplicationContext.getInstance().getLockObject()) {
/* 117 */       if (instance == null) {
/* 118 */         instance = new LoginLogFacade();
/*     */       }
/* 120 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/* 124 */   private LoginLogFacade() { this.entries = new LinkedList();
/*     */     
/* 126 */     this.tFlush = null; log.info("initializing login log"); try { SubConfigurationWrapper subConfigurationWrapper = new SubConfigurationWrapper((Configuration)ApplicationContext.getInstance(), "frame.login.log."); if (subConfigurationWrapper.getProperty("enable") != null && (new TypeDecorator((Configuration)subConfigurationWrapper)).getBoolean("enable").booleanValue()) { String type = subConfigurationWrapper.getProperty("type"); if ("log4j".equalsIgnoreCase(type)) { this.backend = new LoginLog_Log4j((Configuration)new SubConfigurationWrapper((Configuration)subConfigurationWrapper, "file.")); } else if ("db".equalsIgnoreCase(type)) { this.backend = new LoginLog_DB((Configuration)new SubConfigurationWrapper((Configuration)subConfigurationWrapper, "db.")); } else { log.info("....disabling login log , unknown type"); this.backend = null; }  this.enabled = (this.backend != null); if (this.enabled) { Number writeDelay = ConfigurationUtil.getNumber("write.delay", (Configuration)subConfigurationWrapper); if (writeDelay != null) { this.writeDelay = writeDelay.longValue(); } else { this.writeDelay = 60000L; }  log.debug("...write delay: " + this.writeDelay + " ms"); log.info("...initialized login log"); }  }  ApplicationContext.getInstance().addShutdownListener(new ApplicationContext.ShutdownListener() { public void onShutdown() { (new LoginLogFacade.EntriesFlush(true)).run(); } }
/*     */         ); }
/*     */     catch (Exception e) { log.warn("unable to initialize - exception: " + e); log.warn("...disabling login log"); }
/* 129 */      } public void add(LoginLog.Entry entry) throws Exception { if (this.enabled && entry != null) {
/* 130 */       synchronized (this.entries) {
/* 131 */         this.entries.add(entry);
/* 132 */         if (this.tFlush == null) {
/* 133 */           log.debug("starting write-back thread");
/* 134 */           this.tFlush = Util.createAndStartThread(new EntriesFlush());
/*     */         } 
/*     */       } 
/*     */     } }
/*     */ 
/*     */   
/*     */   public boolean isEnabled() {
/* 141 */     return this.enabled;
/*     */   }
/*     */   
/*     */   public boolean supportsQuery() {
/* 145 */     return this.backend instanceof LoginLog.Query;
/*     */   }
/*     */   
/*     */   public Collection getEntries(LoginLog.Query.BackendFilter backendFilter, Filter entryFilter, int hitLimit) throws Exception {
/* 149 */     if (!(this.backend instanceof LoginLog.Query)) {
/* 150 */       throw new UnsupportedOperationException("facaded log facility does not support this feature");
/*     */     }
/* 152 */     return ((LoginLog.Query)this.backend).getEntries(backendFilter, entryFilter, hitLimit);
/*     */   }
/*     */ 
/*     */   
/*     */   public void delete(Collection entries) throws Exception {
/* 157 */     if (!(this.backend instanceof LoginLog.Deletion)) {
/* 158 */       throw new UnsupportedOperationException("facaded log facility does not support this feature");
/*     */     }
/* 160 */     ((LoginLog.Deletion)this.backend).delete(entries);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\login\log\LoginLogFacade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */