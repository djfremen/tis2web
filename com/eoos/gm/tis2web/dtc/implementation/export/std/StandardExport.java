/*     */ package com.eoos.gm.tis2web.dtc.implementation.export.std;
/*     */ 
/*     */ import com.eoos.collection.v2.CollectionUtil;
/*     */ import com.eoos.datatype.Time;
/*     */ import com.eoos.datatype.marker.Configurable;
/*     */ import com.eoos.gm.tis2web.dtc.implementation.DTCLog;
/*     */ import com.eoos.gm.tis2web.dtc.implementation.DTCLogFacade;
/*     */ import com.eoos.gm.tis2web.dtc.implementation.export.AutomaticExport;
/*     */ import com.eoos.gm.tis2web.dtc.implementation.util.DTCLogUtil;
/*     */ import com.eoos.gm.tis2web.dtc.service.cai.DTC;
/*     */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.FrameService;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.util.v2.TimedExecution;
/*     */ import com.eoos.util.v2.Util;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class StandardExport
/*     */   implements AutomaticExport, Configurable
/*     */ {
/*  29 */   private static final Logger log = Logger.getLogger(StandardExport.class);
/*     */   
/*     */   private File exportFile;
/*     */   
/*     */   private TimedExecution timedExecution;
/*     */   
/*     */   private String identifier;
/*     */   
/*  37 */   private String STORAGE_KEY_NEW = "dtc.log.export.lts.";
/*     */   
/*     */   public StandardExport(String identifier, Configuration configuration) throws Exception {
/*  40 */     this.identifier = identifier;
/*  41 */     log.debug("initializing " + this);
/*  42 */     this.STORAGE_KEY_NEW += identifier;
/*  43 */     String filename = configuration.getProperty("file");
/*  44 */     this.exportFile = new File(filename);
/*  45 */     boolean ok = this.exportFile.getParentFile().exists();
/*  46 */     ok = (ok && this.exportFile.getParentFile().canWrite());
/*  47 */     if (!ok) {
/*  48 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/*  51 */     List<Time> executionTimes = new LinkedList();
/*  52 */     SubConfigurationWrapper subConfigurationWrapper = new SubConfigurationWrapper(configuration, "exec.time.");
/*  53 */     for (Iterator<String> iter = subConfigurationWrapper.getKeys().iterator(); iter.hasNext(); ) {
/*  54 */       String key = iter.next();
/*  55 */       String time = subConfigurationWrapper.getProperty(key);
/*     */       try {
/*  57 */         Time t = Time.parse(time);
/*  58 */         executionTimes.add(t);
/*  59 */       } catch (Exception e) {
/*  60 */         log.warn("unable to read execution time with key:" + key + " - exception:" + e + ", skipping");
/*     */       } 
/*     */     } 
/*  63 */     CollectionUtil.unify(executionTimes);
/*  64 */     Collections.sort(executionTimes);
/*  65 */     log.debug("...execution times: " + executionTimes);
/*  66 */     this.timedExecution = new TimedExecution(new TimedExecution.Operation()
/*     */         {
/*     */           public boolean execute(Time executionTime) {
/*  69 */             StandardExport.this.export(executionTime);
/*  70 */             return true;
/*     */           }
/*     */         },  executionTimes);
/*     */   }
/*     */   private static final String STORAGE_KEY_LAST_EXPORTED_ID = "dtc.log.export.lts";
/*     */   
/*     */   public void start() {
/*  77 */     this.timedExecution.start();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private long getLastExportedID() throws Exception {
/*  83 */     FrameService frameService = (FrameService)FrameServiceProvider.getInstance().getService(FrameService.class);
/*  84 */     return ((Long)frameService.getPersistentObject("dtc.log.export.lts")).longValue();
/*     */   }
/*     */   
/*     */   private void setLastExportedID(long id) throws Exception {
/*  88 */     FrameService frameService = (FrameService)FrameServiceProvider.getInstance().getService(FrameService.class);
/*  89 */     frameService.storePersistentObject("dtc.log.export.lts", Long.valueOf(id));
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized void export(Time executionTime) {
/*  94 */     log.info("running " + Util.getClassName(getClass()));
/*     */     try {
/*  96 */       log.debug("....determining last exported id...");
/*  97 */       long lastID = -1L;
/*     */       try {
/*  99 */         lastID = getLastExportedID();
/* 100 */         log.debug("....found id:" + lastID + ", export will begin with:" + (lastID + 1L));
/* 101 */       } catch (Exception e) {
/* 102 */         log.warn("unable to determine last exported id, assuming first run");
/*     */       } 
/*     */       
/* 105 */       final Long minID = (lastID != -1L) ? Long.valueOf(lastID + 1L) : null;
/*     */       
/* 107 */       DTCLog.BackendFilter backendFilter = new DTCLog.BackendFilter()
/*     */         {
/*     */           public String getBACCodePattern() {
/* 110 */             return null;
/*     */           }
/*     */           
/*     */           public Long getIdMax() {
/* 114 */             return null;
/*     */           }
/*     */           
/*     */           public Long getIdMin() {
/* 118 */             return minID;
/*     */           }
/*     */           
/*     */           public Long getTimestampMAX() {
/* 122 */             return null;
/*     */           }
/*     */           
/*     */           public Long getTimestampMIN() {
/* 126 */             return null;
/*     */           }
/*     */           
/*     */           public List getApplicationIDs() {
/* 130 */             return null;
/*     */           }
/*     */           
/*     */           public List getCountryCodes() {
/* 134 */             return null;
/*     */           }
/*     */           
/*     */           public List getPortalIDs(DTCLog.BackendFilter.Type type) {
/* 138 */             return null;
/*     */           }
/*     */         };
/*     */ 
/*     */       
/* 143 */       log.debug("....querying login log for entries ");
/* 144 */       Collection loggedDTCs = DTCLogFacade.getInstance().getEntries(backendFilter, -1);
/* 145 */       log.debug("....retrieved " + loggedDTCs.size() + " entries");
/*     */       
/* 147 */       log.debug(".... writing export file");
/* 148 */       FileOutputStream fos = new FileOutputStream(this.exportFile);
/* 149 */       DTCLogUtil.createExport(loggedDTCs, fos);
/* 150 */       fos.close();
/* 151 */       log.debug("...successfully executed export");
/*     */       
/* 153 */       long maxID = 0L;
/* 154 */       for (Iterator<DTC.Logged> iter = loggedDTCs.iterator(); iter.hasNext(); ) {
/* 155 */         DTC.Logged dtc = iter.next();
/* 156 */         maxID = Math.max(maxID, dtc.getID());
/*     */       } 
/* 158 */       log.debug("...saving max id :" + maxID);
/* 159 */       setLastExportedID(maxID);
/* 160 */     } catch (Exception e) {
/* 161 */       log.error("unable to execute standard export - exception:" + e, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 167 */     return Util.getClassName(getClass()) + "[" + this.identifier + "]";
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\dtc\implementation\export\std\StandardExport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */