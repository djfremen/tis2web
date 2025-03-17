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
/*     */ import com.eoos.propcfg.util.ConfigurationUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.v2.TimedExecution;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class FilteredExport
/*     */   implements AutomaticExport, Configurable
/*     */ {
/*  33 */   private static final Logger log = Logger.getLogger(FilteredExport.class);
/*     */   
/*  35 */   private static final DateFormat DFormat = new SimpleDateFormat("MMddyyyyHHmm");
/*     */   
/*     */   private String identifier;
/*     */   
/*     */   private File exportDir;
/*     */   
/*     */   private String filenamePrefix;
/*     */   private String filenameSuffix;
/*     */   private List portalIDs;
/*     */   private List excludedPortalIDs;
/*     */   private List applicationIDs;
/*     */   private List countryCodes;
/*     */   private long maxAge;
/*     */   private TimedExecution timedExecution;
/*     */   private String STORAGE_KEY_LAST_EXPORTED_ID;
/*     */   
/*     */   public FilteredExport(String identifier, Configuration configuration) throws Exception {
/*  52 */     this.identifier = identifier;
/*  53 */     this.STORAGE_KEY_LAST_EXPORTED_ID = "dtc.log.export.lts." + identifier;
/*  54 */     log.debug("initializing " + this);
/*     */     
/*  56 */     this.exportDir = new File(configuration.getProperty("export.dir"));
/*  57 */     if (!this.exportDir.exists()) {
/*  58 */       throw new IllegalArgumentException("the export directory: " + this.exportDir + " does not exist");
/*     */     }
/*  60 */     if (!this.exportDir.canWrite()) {
/*  61 */       throw new IllegalArgumentException("unable to write to export directory: " + this.exportDir);
/*     */     }
/*     */ 
/*     */     
/*  65 */     this.filenamePrefix = configuration.getProperty("filename.prefix");
/*  66 */     log.debug("...filename prefix: " + this.filenamePrefix);
/*     */     
/*  68 */     this.filenameSuffix = configuration.getProperty("filename.suffix");
/*  69 */     log.debug("...filename suffix: " + this.filenameSuffix);
/*     */     
/*  71 */     this.portalIDs = ConfigurationUtil.getValueList(configuration, "portal.ids");
/*  72 */     log.debug("...portal filter: " + this.portalIDs);
/*  73 */     if (Util.isNullOrEmpty(this.portalIDs)) {
/*  74 */       this.excludedPortalIDs = ConfigurationUtil.getValueList(configuration, "excluded.portal.ids");
/*     */     }
/*     */     
/*  77 */     this.applicationIDs = ConfigurationUtil.getValueList(configuration, "application.ids");
/*  78 */     log.debug("...application filter: " + this.portalIDs);
/*     */     
/*  80 */     this.countryCodes = ConfigurationUtil.getValueList(configuration, "country.codes");
/*  81 */     log.debug("...country filter: " + this.countryCodes);
/*     */ 
/*     */     
/*  84 */     this.maxAge = 86400000L;
/*     */     
/*  86 */     Util.Duration duration = ConfigurationUtil.getDuration(configuration, "max.age");
/*  87 */     if (duration != null) {
/*  88 */       this.maxAge = duration.getAsMillis();
/*     */     }
/*  90 */     log.debug("...max age: " + Util.formatDuration(this.maxAge, "${hours} hours"));
/*     */     
/*  92 */     List<Time> executionTimes = new LinkedList();
/*  93 */     for (Iterator<String> iter = ConfigurationUtil.getValueList(configuration, "exec.time").iterator(); iter.hasNext(); ) {
/*  94 */       String time = iter.next();
/*     */       try {
/*  96 */         Time t = Time.parse(time);
/*  97 */         executionTimes.add(t);
/*  98 */       } catch (Exception e) {
/*  99 */         log.warn("unable to parse and add execution time: " + time + ", ignoring - exception: " + e, e);
/*     */       } 
/*     */     } 
/* 102 */     CollectionUtil.unify(executionTimes);
/* 103 */     Collections.sort(executionTimes);
/* 104 */     log.debug("...execution times: " + executionTimes);
/* 105 */     this.timedExecution = new TimedExecution(new TimedExecution.Operation()
/*     */         {
/*     */           public boolean execute(Time executionTime) {
/* 108 */             FilteredExport.this.export(executionTime);
/* 109 */             return true;
/*     */           }
/*     */         },  executionTimes);
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/* 116 */     this.timedExecution.start();
/*     */   }
/*     */   
/*     */   private long getLastExportedID() throws Exception {
/* 120 */     FrameService frameService = (FrameService)FrameServiceProvider.getInstance().getService(FrameService.class);
/* 121 */     Long ret = (Long)frameService.getPersistentObject(this.STORAGE_KEY_LAST_EXPORTED_ID);
/* 122 */     if (ret == null) {
/* 123 */       ret = (Long)frameService.getPersistentObject("dtc.log.export.lts");
/*     */     }
/* 125 */     return ret.longValue();
/*     */   }
/*     */   
/*     */   private void setLastExportedID(long id) throws Exception {
/* 129 */     FrameService frameService = (FrameService)FrameServiceProvider.getInstance().getService(FrameService.class);
/* 130 */     frameService.storePersistentObject(this.STORAGE_KEY_LAST_EXPORTED_ID, Long.valueOf(id));
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized void export(Time executionTime) {
/* 135 */     log.info("running " + Util.getClassName(getClass()));
/*     */     try {
/* 137 */       log.debug("....determining last exported id...");
/* 138 */       long lastID = -1L;
/*     */       try {
/* 140 */         lastID = getLastExportedID();
/* 141 */         log.debug("....found id:" + lastID + ", export will begin with:" + (lastID + 1L));
/* 142 */       } catch (Exception e) {
/* 143 */         log.warn("unable to determine last exported id, assuming first run");
/*     */       } 
/*     */       
/* 146 */       final Long minID = (lastID != -1L) ? Long.valueOf(lastID + 1L) : null;
/*     */       
/* 148 */       DTCLog.BackendFilter backendFilter = new DTCLog.BackendFilter()
/*     */         {
/*     */           public String getBACCodePattern() {
/* 151 */             return null;
/*     */           }
/*     */           
/*     */           public Long getIdMax() {
/* 155 */             return null;
/*     */           }
/*     */           
/*     */           public Long getIdMin() {
/* 159 */             return minID;
/*     */           }
/*     */           
/*     */           public Long getTimestampMAX() {
/* 163 */             return null;
/*     */           }
/*     */           
/*     */           public Long getTimestampMIN() {
/* 167 */             return Long.valueOf(System.currentTimeMillis() - FilteredExport.this.maxAge);
/*     */           }
/*     */           
/*     */           public List getApplicationIDs() {
/* 171 */             return FilteredExport.this.applicationIDs;
/*     */           }
/*     */           
/*     */           public List getPortalIDs(DTCLog.BackendFilter.Type type) {
/* 175 */             if (type == DTCLog.BackendFilter.Type.INCLUDE) {
/* 176 */               return FilteredExport.this.portalIDs;
/*     */             }
/* 178 */             return FilteredExport.this.excludedPortalIDs;
/*     */           }
/*     */ 
/*     */           
/*     */           public List getCountryCodes() {
/* 183 */             return FilteredExport.this.countryCodes;
/*     */           }
/*     */         };
/*     */ 
/*     */       
/* 188 */       log.debug("....querying login log for entries ");
/* 189 */       Collection loggedDTCs = DTCLogFacade.getInstance().getEntries(backendFilter, -1);
/* 190 */       log.debug("....retrieved " + loggedDTCs.size() + " entries");
/* 191 */       if (loggedDTCs.size() != 0) {
/* 192 */         log.debug(".... writing export file");
/*     */         
/* 194 */         StringBuilder filename = new StringBuilder();
/* 195 */         if (!Util.isNullOrEmpty(this.filenamePrefix)) {
/* 196 */           filename.append(this.filenamePrefix);
/*     */         }
/* 198 */         filename.append(DFormat.format(new Date()));
/* 199 */         if (!Util.isNullOrEmpty(this.filenameSuffix)) {
/* 200 */           filename.append(this.filenameSuffix);
/*     */         }
/*     */         
/* 203 */         File exportFile = new File(this.exportDir, filename.toString());
/*     */         
/* 205 */         FileOutputStream fos = new FileOutputStream(exportFile);
/* 206 */         DTCLogUtil.createExport(loggedDTCs, fos);
/* 207 */         fos.close();
/* 208 */         log.debug("...successfully executed export");
/*     */         
/* 210 */         long maxID = 0L;
/* 211 */         for (Iterator<DTC.Logged> iter = loggedDTCs.iterator(); iter.hasNext(); ) {
/* 212 */           DTC.Logged dtc = iter.next();
/* 213 */           maxID = Math.max(maxID, dtc.getID());
/*     */         } 
/* 215 */         log.debug("...saving max id :" + maxID);
/* 216 */         setLastExportedID(maxID);
/*     */       } else {
/* 218 */         log.debug("...nothing to export");
/*     */       } 
/* 220 */     } catch (Exception e) {
/* 221 */       log.error("unable to execute standard export - exception:" + e, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 227 */     return Util.getClassName(getClass()) + "[" + this.identifier + "]";
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\dtc\implementation\export\std\FilteredExport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */